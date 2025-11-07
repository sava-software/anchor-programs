package software.sava.anchor.programs.drift;

import org.bouncycastle.util.encoders.Hex;
import software.sava.core.accounts.PublicKey;
import systems.comodal.jsoniter.CharBufferFunction;
import systems.comodal.jsoniter.JsonIterator;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public final class GenerateMarketConstants {

  public static final CharBufferFunction<PublicKey> DECODE_HEX = (buf, offset, len) -> PublicKey
      .createPubKey(Hex.decode(new String(buf, offset + 2, len - 2)));

  static String quoteJson(final String javascript) {
    return javascript.replaceAll("(\\w+):\\s+", "\"$1\": ");
  }

  private static <T extends MarketConfig> MarketConfigs<T> parseConfigs(final String devNetKey,
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

    System.out.println(mainNetJson);
    ji = JsonIterator.parse(mainNetJson);
    final var mainNetConfigs = configParser.apply(ji, DriftAccounts.MAIN_NET);

    return new MarketConfigs<>(mainNetConfigs, devNetConfigs);
  }

  static String convertJson(final String javascript) {
    return javascript
        .replace('\'', '"')
        .replaceAll("\\s+.*precision.*", "")
        .replaceAll("\\s+.*precisionExp.*", "")
        .replaceAll(",\n\\s+}", "}")
        .replaceAll(",\n\\s*]", "]")
        .replaceAll("//.*", "")
        .replaceAll("\\s+", " ")
        .replaceAll("OracleSource\\.(\\w+)", "\"$1\"")
        .replaceAll("MarketStatus\\.(\\w+)", "\"$1\"")
        .replace("WRAPPED_SOL_MINT", "\"So11111111111111111111111111111111111111112\"")
        .replaceAll("new PublicKey\\( *\"(\\w+)\" *\\)", "\"$1\"");
  }

  private record MarketConfigs<T extends MarketConfig>(List<T> mainNet, List<T> devNet) {

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
  static final String MAIN_NET_PERP_KEY = "MainnetPerpMarkets: PerpMarketConfig[]";

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

  private GenerateMarketConstants() {
  }
}
