package software.sava.anchor.programs;

import software.sava.anchor.AnchorIDL;
import software.sava.anchor.AnchorSourceGenerator;
import software.sava.core.accounts.PublicKey;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.SolanaNetwork;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import systems.comodal.jsoniter.JsonIterator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.nio.file.StandardOpenOption.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static software.sava.core.accounts.PublicKey.fromBase58Encoded;

public final class Entrypoint extends Thread {

  private final Semaphore semaphore;
  private final ConcurrentLinkedQueue<Map.Entry<String, String>> tasks;
  private final AtomicLong errorCount;
  private final long baseDelayMillis;
  private final AtomicLong latestCall;
  private final SolanaRpcClient rpcClient;
  private final Path sourceDirectory;
  private final String outputModuleName;
  private final String basePackageName;
  private final Set<String> exports;
  private final int tabLength;

  private Entrypoint(final Semaphore semaphore,
                     final ConcurrentLinkedQueue<Map.Entry<String, String>> tasks,
                     final AtomicLong errorCount,
                     final long baseDelayMillis,
                     final AtomicLong latestCall,
                     final SolanaRpcClient rpcClient,
                     final Path sourceDirectory,
                     final String outputModuleName,
                     final String basePackageName, final Set<String> exports,
                     final int tabLength) {
    this.semaphore = semaphore;
    this.tasks = tasks;
    this.errorCount = errorCount;
    this.baseDelayMillis = baseDelayMillis;
    this.latestCall = latestCall;
    this.rpcClient = rpcClient;
    this.sourceDirectory = sourceDirectory;
    this.outputModuleName = outputModuleName;
    this.basePackageName = basePackageName;
    this.exports = exports;
    this.tabLength = tabLength;
  }

  private String formatPackage(final String moduleName) {
    return String.format("%s.%s.anchor", basePackageName, moduleName);
  }

  private AnchorSourceGenerator createGenerator(final String moduleName, final AnchorIDL idl) {
    return new AnchorSourceGenerator(
        sourceDirectory,
        formatPackage(moduleName),
        tabLength,
        idl
    );
  }

  private AnchorSourceGenerator createGenerator(final String moduleName, final PublicKey programAddress) {
    final var idl = AnchorSourceGenerator.fetchIDLForProgram(programAddress, rpcClient).join();
    return createGenerator(moduleName, idl);
  }

  private AnchorSourceGenerator createGenerator(final String moduleName, final URI url) {
    final var idl = AnchorSourceGenerator.fetchIDL(rpcClient.httpClient(), url).join();
    return createGenerator(moduleName, idl);
  }

  private AnchorSourceGenerator createGenerator(final String moduleName, final String addressOrURL) {
    return addressOrURL.startsWith("https://")
        ? createGenerator(moduleName, URI.create(addressOrURL))
        : createGenerator(moduleName, fromBase58Encoded(addressOrURL));
  }

  private AnchorSourceGenerator createGenerator(final Map.Entry<String, String> task) {
    return createGenerator(task.getKey(), task.getValue());
  }

  private static final LongBinaryOperator MAX = Long::max;

  @Override
  public void run() {
    Map.Entry<String, String> task = null;
    for (long delayMillis, latestCall, now, sleep; ; ) {
      try {
        task = this.tasks.poll();
        if (task == null) {
          return;
        }
        this.semaphore.acquire();
        delayMillis = this.baseDelayMillis * (this.errorCount.get() + 1);
        latestCall = this.latestCall.get();
        now = System.currentTimeMillis();
        sleep = (latestCall + delayMillis) - now;
        if (sleep > 0) {
          MILLISECONDS.sleep(sleep);
          now = System.currentTimeMillis();
        }
        this.latestCall.getAndAccumulate(now, MAX);
        final var generator = createGenerator(task);
        generator.run();
        generator.addExports(exports);
        this.latestCall.getAndAccumulate(now + ((System.currentTimeMillis() - now) >> 1), MAX);
        this.errorCount.getAndUpdate(x -> x > 0 ? x - 1 : x);
      } catch (final RuntimeException e) {
        this.errorCount.getAndUpdate(x -> x < 100 ? x + 1 : x);
        this.tasks.add(task);
      } catch (final InterruptedException e) {
        throw new RuntimeException(e);
      } finally {
        this.semaphore.release();
      }
    }
  }

  private static String mandatoryProperty(final String key) {
    return Objects.requireNonNull(System.getProperty(key, "Must pass property "), key);
  }

  private static String propertyOrElse(final String key, final String orElse) {
    final var property = System.getProperty(key);
    return property == null || property.isBlank() ? orElse : property;
  }

  public static void main(final String[] args) throws InterruptedException {
    final var clas = Entrypoint.class;
    final var moduleName = clas.getModule().getName();
    final int tabLength = Integer.parseInt(propertyOrElse(
        moduleName + ".tabLength",
        "2"
    ));
    final var sourceDirectory = Path.of(propertyOrElse(moduleName + ".sourceDirectory", "anchor-programs/src/main/java")).toAbsolutePath();
    final var outputModuleName = propertyOrElse(moduleName + ".moduleName", moduleName.substring(0, moduleName.lastIndexOf('.')) + ".anchor_programs");
    final var basePackageName = propertyOrElse(moduleName + ".basePackageName", clas.getPackageName());
    final var rpcEndpoint = System.getProperty(moduleName + ".rpc");
    final var programsCSV = mandatoryProperty(moduleName + ".programsCSV");
    final int numThreads = Integer.parseInt(propertyOrElse(
        moduleName + ".numThreads",
        Integer.toString(Runtime.getRuntime().availableProcessors())
    ));
    final int baseDelayMillis = Integer.parseInt(propertyOrElse(
        moduleName + ".baseDelayMillis",
        "200"
    ));

    try (final var lines = Files.lines(Path.of(programsCSV))) {
      final var semaphore = new Semaphore(numThreads, false);
      final var tasks = new ConcurrentLinkedQueue<Map.Entry<String, String>>();
      lines.map(line -> {
        final int comma = line.indexOf(',');
        return Map.entry(line.substring(0, comma), line.substring(comma + 1));
      }).forEach(tasks::add);

      final var errorCount = new AtomicLong();
      final var latestCall = new AtomicLong();

      try (final var executor = Executors.newVirtualThreadPerTaskExecutor()) {
        try (final var httpClient = HttpClient.newBuilder().executor(executor).build()) {
          final var rpcClient = SolanaRpcClient.createHttpClient(
              rpcEndpoint == null || rpcEndpoint.isBlank() ? SolanaNetwork.MAIN_NET.getEndpoint() : URI.create(rpcEndpoint),
              httpClient
          );

          final var exports = new ConcurrentSkipListSet<String>();
          final var threads = IntStream.range(0, numThreads)
              .mapToObj(_ -> new Entrypoint(
                  semaphore, tasks, errorCount, baseDelayMillis, latestCall,
                  rpcClient,
                  sourceDirectory, outputModuleName, basePackageName,
                  exports,
                  tabLength
              ))
              .peek(Thread::start)
              .toList();

          exports.add(String.format("requires %s;", HttpClient.class.getModule().getName()));
          exports.add(String.format("requires %s;", JsonIterator.class.getModule().getName()));
          exports.add(String.format("requires %s;", Instruction.class.getModule().getName()));
          exports.add(String.format("requires %s;", SolanaRpcClient.class.getModule().getName()));
          exports.add(String.format("requires %s;", System.class.getModule().getName()));
          exports.add(String.format("requires %s;", AnchorSourceGenerator.class.getModule().getName()));
          final var moduleFilePath = sourceDirectory.resolve("module-info.java");

          if (Files.exists(moduleFilePath)) {
            try (final var moduleFileLines = Files.lines(moduleFilePath)) {
              moduleFileLines
                  .map(String::strip)
                  .filter(line -> !line.isBlank() && !line.startsWith("module") && !line.equals("}"))
                  .forEach(exports::add);
            }
          }
          final var builder = new StringBuilder(2_048);
          builder.append(String.format("module %s {%n", outputModuleName));

          for (final var thread : threads) {
            thread.join();
          }

          builder.append(exports.stream().sorted(String::compareToIgnoreCase).collect(Collectors.joining("\n")).indent(tabLength));
          builder.append('}').append('\n');
          Files.writeString(moduleFilePath, builder.toString(), CREATE, TRUNCATE_EXISTING, WRITE);
        }
      }
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
