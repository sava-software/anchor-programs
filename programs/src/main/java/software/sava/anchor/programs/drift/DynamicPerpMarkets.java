package software.sava.anchor.programs.drift;

import java.net.http.HttpClient;

record DynamicPerpMarkets(PerpMarkets mainNet, PerpMarkets devNet) {

  public static DynamicPerpMarkets fetchMarkets(final HttpClient httpClient) {
    return GenerateMarketConstants.createPerpMarkets(httpClient);
  }
}
