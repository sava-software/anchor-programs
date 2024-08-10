package software.sava.anchor.programs;

import software.sava.anchor.AnchorSourceGenerator;
import software.sava.core.accounts.PublicKey;
import software.sava.rpc.json.http.SolanaNetwork;
import software.sava.rpc.json.http.client.SolanaRpcClient;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;

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
  private final String basePackageName;
  private final int tabLength;

  private Entrypoint(final Semaphore semaphore,
                     final ConcurrentLinkedQueue<Map.Entry<String, String>> tasks,
                     final AtomicLong errorCount,
                     final long baseDelayMillis,
                     final AtomicLong latestCall,
                     final SolanaRpcClient rpcClient,
                     final Path sourceDirectory,
                     final String basePackageName,
                     final int tabLength) {
    this.semaphore = semaphore;
    this.tasks = tasks;
    this.errorCount = errorCount;
    this.baseDelayMillis = baseDelayMillis;
    this.latestCall = latestCall;
    this.rpcClient = rpcClient;
    this.sourceDirectory = sourceDirectory;
    this.basePackageName = basePackageName;
    this.tabLength = tabLength;
  }

  private String formatPackage(final String moduleName) {
    return String.format("%s.%s.anchor", basePackageName, moduleName);
  }

  private AnchorSourceGenerator createGenerator(final String moduleName, final PublicKey programAddress) {
    final var idl = AnchorSourceGenerator.fetchIDLForProgram(programAddress, rpcClient).join();
    return new AnchorSourceGenerator(
        sourceDirectory,
        formatPackage(moduleName),
        tabLength,
        idl
    );
  }

  private AnchorSourceGenerator createGenerator(final String moduleName, final URI url) {
    return AnchorSourceGenerator.createGenerator(
        rpcClient.httpClient(),
        url,
        sourceDirectory,
        formatPackage(moduleName),
        tabLength
    );
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

  public static void main(final String[] args) throws InterruptedException {
    final var clas = Entrypoint.class;
    final var moduleName = clas.getModule().getName();
    final int tabLength = Integer.parseInt(System.getProperty(
        moduleName + ".tabLength",
        "2"
    ));
    final var sourceDirectory = Path.of(System.getProperty(moduleName + ".sourceDirectory", "anchor-programs/src/main/java")).toAbsolutePath();
    final var basePackageName = System.getProperty(moduleName + ".basePackageName", clas.getPackageName());
    final var rpcEndpoint = System.getProperty(moduleName + ".rpc");
    final var programsCSV = System.getProperty(moduleName + ".programsCSV");
    final int numThreads = Integer.parseInt(System.getProperty(
        moduleName + ".numThreads",
        Integer.toString(Runtime.getRuntime().availableProcessors())
    ));
    final int baseDelayMillis = Integer.parseInt(System.getProperty(
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

          final var threads = IntStream.range(0, numThreads)
              .mapToObj(_ -> new Entrypoint(semaphore, tasks, errorCount, baseDelayMillis, latestCall, rpcClient, sourceDirectory, basePackageName, tabLength))
              .peek(Thread::start)
              .toList();

          for (final var thread : threads) {
            thread.join();
          }
        }
      }
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
