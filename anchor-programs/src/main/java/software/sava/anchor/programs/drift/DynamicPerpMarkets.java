package software.sava.anchor.programs.drift;

import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;

public record DynamicPerpMarkets(PerpMarkets mainNet, PerpMarkets devNet) {

  public static CompletableFuture<DynamicPerpMarkets> fetchMarkets(final HttpClient httpClient) {
    return GenerateMarketConstants.createPerpMarkets(httpClient);
  }

  static void main() {
    final var httpClient = HttpClient.newHttpClient();
    final var markets = fetchMarkets(httpClient).join();
    markets.mainNet().markets().forEach(System.out::println);
  }
}
