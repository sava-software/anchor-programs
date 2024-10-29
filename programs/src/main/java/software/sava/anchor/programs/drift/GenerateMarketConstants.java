package software.sava.anchor.programs.drift;

import software.sava.anchor.programs.drift.anchor.types.OracleSource;
import systems.comodal.jsoniter.JsonIterator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.*;

final class GenerateMarketConstants {

  private static String quoteJson(final String javascript) {
    return javascript.replaceAll("(\\w+):\\s+", "\"$1\": ");
  }

  private static void parseConfigsAndWriteSrc(final String devNetKey,
                                              final String mainNetKey,
                                              final BiFunction<JsonIterator, DriftAccounts, List<? extends SrcGen>> configParser,
                                              final Class<?> clas,
                                              final Set<Class<?>> imports,
                                              final String response,
                                              final String fileName) {
    int from = response.indexOf(devNetKey);
    from = response.indexOf('[', from + devNetKey.length());
    int to = response.indexOf("];", from) + 1;

    final var devNetJson = quoteJson(response.substring(from, to));
    var ji = JsonIterator.parse(devNetJson);
    final var devNetConfigs = configParser.apply(ji, DriftAccounts.DEV_NET);

    from = response.indexOf(mainNetKey, to);
    from = response.indexOf('[', from + mainNetKey.length());
    to = response.indexOf("];", from) + 1;

    final var mainNetJson = quoteJson(response.substring(from, to));

    ji = JsonIterator.parse(mainNetJson);
    final var mainNetConfigs = configParser.apply(ji, DriftAccounts.MAIN_NET);

    writeMarketConfigsSrc(devNetConfigs, mainNetConfigs, clas, imports, fileName);

    if (clas.equals(SpotMarketConfig.class)) {
      writeAssetsSrc(devNetConfigs, mainNetConfigs);
    } else {
      writeProductsSrc(devNetConfigs, mainNetConfigs);
    }
  }

  private static <T extends SrcGen> MarketConfigs<T> parseConfigs(final String devNetKey,
                                                                  final String mainNetKey,
                                                                  final BiFunction<JsonIterator, DriftAccounts, List<T>> configParser,
                                                                  final String response) {
    int from = response.indexOf(devNetKey);
    from = response.indexOf('[', from + devNetKey.length());
    int to = response.indexOf("];", from) + 1;

    final var devNetJson = quoteJson(response.substring(from, to));
    var ji = JsonIterator.parse(devNetJson);
    final var devNetConfigs = configParser.apply(ji, DriftAccounts.DEV_NET);

    from = response.indexOf(mainNetKey, to);
    from = response.indexOf('[', from + mainNetKey.length());
    to = response.indexOf("];", from) + 1;

    final var mainNetJson = quoteJson(response.substring(from, to));

    ji = JsonIterator.parse(mainNetJson);
    final var mainNetConfigs = configParser.apply(ji, DriftAccounts.MAIN_NET);

    return new MarketConfigs<>(mainNetConfigs, devNetConfigs);
  }

  private static String convertJson(final String javascript) {
    return javascript
        .replace('\'', '"')
        .replaceAll("\\s+.*precision.*", "")
        .replaceAll("\\s+.*precisionExp.*", "")
        .replaceAll(",\n\\s+}", "}")
        .replaceAll(",\n\\s*]", "]")
        .replaceAll("//.*", "")
        .replaceAll("\\s+", " ")
        .replaceAll("OracleSource\\.(\\w+)", "\"$1\"")
        .replace("WRAPPED_SOL_MINT", "\"So11111111111111111111111111111111111111112\"")
        .replaceAll("new PublicKey\\( *\"(\\w+)\" *\\)", "\"$1\"");
  }

  private record MarketConfigs<T extends SrcGen>(List<T> mainNet, List<T> devNet) {

  }

  private static CompletableFuture<HttpResponse<String>> fetchSpotMarkets(final HttpClient httpClient) {
    final var marketConstantsURI = URI.create("https://raw.githubusercontent.com/drift-labs/protocol-v2/master/sdk/src/constants/spotMarkets.ts");
    final var request = HttpRequest.newBuilder(marketConstantsURI).GET().build();
    return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
  }

  private static CompletableFuture<HttpResponse<String>> fetchPerpMarkets(final HttpClient httpClient) {
    final var marketConstantsURI = URI.create("https://raw.githubusercontent.com/drift-labs/protocol-v2/master/sdk/src/constants/perpMarkets.ts");
    final var request = HttpRequest.newBuilder(marketConstantsURI).GET().build();
    return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
  }

  private static final String DEV_NET_SPOT_KEY = "DevnetSpotMarkets: SpotMarketConfig[]";
  private static final String MAIN_NET_SPOT_KEY = "MainnetSpotMarkets: SpotMarketConfig[]";
  private static final String DEV_NET_PERP_KEY = "DevnetPerpMarkets: PerpMarketConfig[]";
  private static final String MAIN_NET_PERP_KEY = "MainnetPerpMarkets: PerpMarketConfig[]";

  static CompletableFuture<DynamicSpotMarkets> createSpotMarkets(final HttpClient httpClient) {
    final var responseFuture = fetchSpotMarkets(httpClient);
    return responseFuture.thenApply(response -> {
      final var responseJson = convertJson(response.body());
      final var configs = parseConfigs(
          DEV_NET_SPOT_KEY,
          MAIN_NET_SPOT_KEY,
          SpotMarketConfig::parseConfigs,
          responseJson
      );
      return new DynamicSpotMarkets(
          SpotMarkets.createRecord(configs.mainNet),
          SpotMarkets.createRecord(configs.devNet)
      );
    });
  }

  static CompletableFuture<DynamicPerpMarkets> createPerpMarkets(final HttpClient httpClient) {
    final var responseFuture = fetchPerpMarkets(httpClient);

    return responseFuture.thenApply(response -> {
      final var responseJson = convertJson(response.body());
      final var configs = parseConfigs(
          DEV_NET_PERP_KEY,
          MAIN_NET_PERP_KEY,
          PerpMarketConfig::parseConfigs,
          responseJson
      );
      return new DynamicPerpMarkets(
          PerpMarkets.createRecord(configs.mainNet),
          PerpMarkets.createRecord(configs.devNet)
      );
    });
  }

  private static void genSpotMarkets(final HttpClient httpClient) {
    final var responseFuture = fetchSpotMarkets(httpClient);
    final var response = convertJson(responseFuture.join().body());
    parseConfigsAndWriteSrc(
        DEV_NET_SPOT_KEY,
        MAIN_NET_SPOT_KEY,
        SpotMarketConfig::parseConfigs,
        SpotMarketConfig.class,
        Set.of(),
        response,
        "SpotMarketConfigs"
    );
  }

  private static void genPerpMarkets(final HttpClient httpClient) {
    final var responseFuture = fetchPerpMarkets(httpClient);
    final var response = convertJson(responseFuture.join().body());
    parseConfigsAndWriteSrc(
        DEV_NET_PERP_KEY,
        MAIN_NET_PERP_KEY,
        PerpMarketConfig::parseConfigs,
        PerpMarketConfig.class,
        Set.of(Set.class),
        response,
        "PerpMarketConfigs"
    );
  }

  private static void writeProductsSrc(final List<? extends SrcGen> devNetConfigs, final List<? extends SrcGen> mainNetConfigs) {
    final var distinct = HashSet.<String>newHashSet(devNetConfigs.size() + mainNetConfigs.size());
    devNetConfigs.stream().map(SrcGen::symbol).forEach(distinct::add);
    mainNetConfigs.stream().map(SrcGen::symbol).forEach(distinct::add);

    final var sourceCode = String.format("""
            package %s;
            
            public enum DriftProduct {
            
            %s}
            """,
        GenerateMarketConstants.class.getPackageName(),
        distinct.stream()
            .map(symbol -> symbol.replace('-', '_'))
            .map(symbol -> Character.isAlphabetic(symbol.charAt(0)) ? symbol : '_' + symbol)
            .sorted()
            .collect(Collectors.joining(",\n"))
            .indent(4)
    );
    try {
      Files.writeString(Path.of(
              "programs/src/main/java/" + GenerateMarketConstants.class.getPackageName().replace('.', '/') + '/' + "DriftProduct.java"),
          sourceCode,
          CREATE, WRITE, TRUNCATE_EXISTING
      );
    } catch (final IOException e) {
      throw new UncheckedIOException("Failed to write DriftProduct", e);
    }
  }

  private static void writeAssetsSrc(final List<? extends SrcGen> devNetConfigs, final List<? extends SrcGen> mainNetConfigs) {
    final var distinct = new TreeSet<String>();
    devNetConfigs.stream().map(SrcGen::symbol).forEach(distinct::add);
    mainNetConfigs.stream().map(SrcGen::symbol).forEach(distinct::add);

    final var sourceCode = String.format("""
            package %s;
            
            public enum DriftAsset {
            
            %s}
            """,
        GenerateMarketConstants.class.getPackageName(),
        String.join(",\n", distinct).indent(4)
    );
    try {
      Files.writeString(Path.of(
              "programs/src/main/java/" + GenerateMarketConstants.class.getPackageName().replace('.', '/') + '/' + "DriftAsset.java"),
          sourceCode,
          CREATE, WRITE, TRUNCATE_EXISTING
      );
    } catch (final IOException e) {
      throw new UncheckedIOException("Failed to write " + DriftAsset.class.getSimpleName(), e);
    }
  }

  private static void writeMarketConfigsSrc(final List<? extends SrcGen> devNetConfigs,
                                            final List<? extends SrcGen> mainNetConfigs,
                                            final Class<?> clas,
                                            final Set<Class<?>> imports,
                                            final String fileName) {
    final var src = new StringBuilder(4_096);
    src.append("package ").append(GenerateMarketConstants.class.getPackageName()).append(';');

    final var allImports = new HashSet<>(imports);
    allImports.add(OracleSource.class);

    final var importLines = allImports.stream()
        .map(Class::getName)
        .map(name -> String.format("import %s;", name))
        .collect(Collectors.joining("\n", "\n", "\n"));
    src.append(String.format("""
            
            %s
            import static %s.createConfig;
            
            public final class %s {
            
            """,
        importLines, clas.getName(), fileName
    ));

    final var simpleClassName = clas.getSimpleName();
    final var marketsClas = clas.equals(SpotMarketConfig.class) ? SpotMarkets.class : PerpMarkets.class;
    appendSrc(src, "MAIN", DriftAccounts.MAIN_NET, mainNetConfigs, simpleClassName, marketsClas);
    appendSrc(src, "DEV", DriftAccounts.DEV_NET, devNetConfigs, simpleClassName, marketsClas);

    src.append(String.format("""
          private %s() {
          }
        }""", fileName));

    final var sourceCode = src.toString();
    try {
      Files.writeString(Path.of(
              "programs/src/main/java/" + GenerateMarketConstants.class.getPackageName().replace('.', '/') + '/' + fileName + ".java"),
          sourceCode,
          CREATE, WRITE, TRUNCATE_EXISTING
      );
    } catch (final IOException e) {
      throw new UncheckedIOException("Failed to write Drift " + fileName, e);
    }
  }

  private static void appendSrc(final StringBuilder src,
                                final String network,
                                final DriftAccounts driftAccounts,
                                final List<? extends SrcGen> configs,
                                final String simpleClassName,
                                final Class<?> marketsClass) {
    src.append(String.format("""
              public static final %s %s_NET = %s.createRecord(new %s[]{
            """,
        marketsClass.getSimpleName(), network, marketsClass.getSimpleName(), simpleClassName
    ));

    final var configsInit = configs.stream()
        .map(srcGen -> srcGen.toSrc(driftAccounts))
        .collect(Collectors.joining(",\n"))
        .indent(6);
    src.append(configsInit);
    src.append("""
          });
        
        """);
  }

  public static void main(final String[] args) {
    try (final var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      try (final var httpClient = HttpClient.newBuilder()
          .executor(executor)
          .proxy(HttpClient.Builder.NO_PROXY)
          .build()) {
//        final var spotMarkets = DynamicSpotMarkets.fetchMarkets(httpClient);
//        spotMarkets.mainNet().streamMarkets().forEach(System.out::println);
        genSpotMarkets(httpClient);
        genPerpMarkets(httpClient);
      }
    }
  }

  private GenerateMarketConstants() {
  }
}
