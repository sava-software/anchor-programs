package software.sava.anchor.programs.drift;

import systems.comodal.jsoniter.JsonIterator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.*;

final class GenerateDriftMarketConstants {

  public static void main(final String[] args) {

    try (final var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      try (final var httpClient = HttpClient.newBuilder()
          .executor(executor)
          .proxy(HttpClient.Builder.NO_PROXY)
          .build()) {
        final var perpMarketConstantsURI = URI.create("https://raw.githubusercontent.com/drift-labs/protocol-v2/master/sdk/src/constants/perpMarkets.ts");
        final var request = HttpRequest.newBuilder(perpMarketConstantsURI).GET().build();
        final var responseFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        final var response = responseFuture.join().body()
            .replace('\'', '"')
            .replaceAll("new\\s+PublicKey\\(\"(\\w+)\"\\)", "\"$1\"")
            .replaceAll("\\s+.*oracleSource.*", "")
            .replaceAll(",\n\\s+}", "}")
            .replaceAll(",\n\\s*]", "]")
            .replaceAll("//.*", "");

        var fromKey = "DevnetPerpMarkets: PerpMarketConfig[]";
        int from = response.indexOf(fromKey);
        from = response.indexOf('[', from + fromKey.length());
        int to = response.indexOf("];", from) + 1;

        final var devNetJson = response.substring(from, to)
            .replaceAll("(\\w+):\\s+", "\"$1\": ");
        var ji = JsonIterator.parse(devNetJson);
        final var devNetConfigs = PerpMarketConfig.parseConfigs(ji);

        fromKey = "MainnetPerpMarkets: PerpMarketConfig[]";
        from = response.indexOf(fromKey, to);
        from = response.indexOf('[', from + fromKey.length());
        to = response.indexOf("];", from) + 1;

        final var mainNetJson = response.substring(from, to)
            .replaceAll("(\\w+):\\s+", "\"$1\": ");

        ji = JsonIterator.parse(mainNetJson);
        final var mainNetConfigs = PerpMarketConfig.parseConfigs(ji);

        final var src = new StringBuilder(4_096);
        src.append("package ").append(GenerateDriftMarketConstants.class.getPackageName()).append(';');

        final var fileName = "PerpMarkets";
        src.append(String.format("""
                
                
                import java.util.Set;
                
                import static %s.createConfig;
                
                public final class %s {
                
                """,
            PerpMarketConfig.class.getName(),
            fileName
        ));

        appendSrc(src, "DEV", devNetConfigs);
        appendSrc(src, "MAIN", mainNetConfigs);

        src.append(String.format("""
              private %s() {
              }
            }""", fileName));

        final var sourceCode = src.toString();
        try {
          Files.writeString(Path.of(
                  "programs/src/main/java/" + GenerateDriftMarketConstants.class.getPackageName().replace('.', '/') + '/' + fileName + ".java"),
              sourceCode,
              CREATE, WRITE, TRUNCATE_EXISTING
          );
        } catch (final IOException e) {
          throw new UncheckedIOException("Failed to write Drift Perp", e);
        }
      }
    }
  }

  private static void appendSrc(final StringBuilder src,
                                final String network,
                                final List<PerpMarketConfig> configs) {
    src.append(String.format("""
              public static final %s[] %s_NET = new %s[] {
            """,
        PerpMarketConfig.class.getSimpleName(), network, PerpMarketConfig.class.getSimpleName()
    ));

    final var configsInit = configs.stream()
        .map(PerpMarketConfig::toSrc)
        .collect(Collectors.joining(",\n"))
        .indent(4);
    src.append(configsInit);
    src.append("""
          };
        
        """);
  }

  private GenerateDriftMarketConstants() {
  }
}
