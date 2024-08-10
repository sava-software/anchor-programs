package software.sava.anchor.programs;

import software.sava.anchor.AnchorIDL;
import software.sava.anchor.AnchorSourceGenerator;
import software.sava.anchor.AnchorUtil;
import software.sava.anchor.OnChainIDL;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static software.sava.core.accounts.PublicKey.fromBase58Encoded;

public record Entrypoint(SolanaRpcClient rpcClient, int tabLength) {

  private static String formatPackage(final String basePackage, final String moduleName) {
    return String.format("%s.%s.anchor", basePackage, moduleName);
  }

  public AnchorSourceGenerator createGenerator(final String moduleName,
                                               final String programAddress,
                                               final Path sourceDirectory,
                                               final String basePackageName) {
    final var idlAddress = AnchorUtil.createIdlAddress(fromBase58Encoded(programAddress));
    final var idlAccountInfo = rpcClient.getAccountInfo(idlAddress, OnChainIDL.FACTORY).join();
    final var idl = AnchorIDL.parseIDL(JsonIterator.parse(idlAccountInfo.data().json()));
    return new AnchorSourceGenerator(
        sourceDirectory,
        formatPackage(basePackageName, moduleName),
        tabLength,
        idl
    );
  }

  public AnchorSourceGenerator createGenerator(final String moduleName,
                                               final URI url,
                                               final Path sourceDirectory,
                                               final String basePackageName) {
    return AnchorSourceGenerator.createGenerator(
        rpcClient.httpClient(),
        url,
        sourceDirectory,
        formatPackage(basePackageName, moduleName),
        tabLength
    );
  }

  public static void main(final String[] args) {
    final var clas = Entrypoint.class;
    final var moduleName = clas.getModule().getName();
    final int tabLength = Integer.parseInt(System.getProperty(moduleName + ".tabLength", "2"));
    final var sourceDirectory = Path.of(System.getProperty(moduleName + ".sourceDirectory", "anchor-programs/src/main/java")).toAbsolutePath();
    final var basePackageName = System.getProperty(moduleName + ".basePackageName", clas.getPackageName());
    final var rpcEndpoint = System.getProperty(moduleName + ".rpc");
    final var programsCSV = System.getProperty(moduleName + ".programsCSV");

    try (final var lines = Files.lines(Path.of(programsCSV))) {
      final var programs = lines.map(line -> {
            final int comma = line.indexOf(',');
            return Map.entry(line.substring(0, comma), line.substring(comma + 1));
          })
          .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

      try (final var executor = Executors.newVirtualThreadPerTaskExecutor()) {
        try (final var httpClient = HttpClient.newBuilder().executor(executor).build()) {
          final var rpcClient = SolanaRpcClient.createHttpClient(
              rpcEndpoint == null || rpcEndpoint.isBlank() ? SolanaNetwork.MAIN_NET.getEndpoint() : URI.create(rpcEndpoint),
              httpClient
          );
          final var anchorGenerator = new Entrypoint(rpcClient, tabLength);
          final var futures = programs.entrySet().stream()
              .map(entry -> anchorGenerator.createGenerator(entry.getKey(), entry.getValue(), sourceDirectory, basePackageName))
              .map(generator -> CompletableFuture.runAsync(generator, executor))
              .toList();
          futures.forEach(CompletableFuture::join);
        }
      }
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
