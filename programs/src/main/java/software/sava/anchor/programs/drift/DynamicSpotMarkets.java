package software.sava.anchor.programs.drift;

import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;

public record DynamicSpotMarkets(SpotMarkets mainNet, SpotMarkets devNet) {

  public static CompletableFuture<DynamicSpotMarkets> fetchMarkets(final HttpClient httpClient) {
    return GenerateMarketConstants.createSpotMarkets(httpClient);
  }
}
