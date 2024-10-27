package software.sava.anchor.programs.drift;

import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;

public record DynamicPerpMarkets(PerpMarkets mainNet, PerpMarkets devNet) {

  public static CompletableFuture<DynamicPerpMarkets> fetchMarkets(final HttpClient httpClient) {
    return GenerateMarketConstants.createPerpMarkets(httpClient);
  }
}
