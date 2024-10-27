package software.sava.anchor.programs.drift;

import java.net.http.HttpClient;

public record DynamicSpotMarkets(SpotMarkets mainNet, SpotMarkets devNet) {

  public static DynamicSpotMarkets fetchMarkets(final HttpClient httpClient) {
    return GenerateMarketConstants.createSpotMarkets(httpClient);
  }
}
