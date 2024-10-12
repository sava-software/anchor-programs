package software.sava.anchor.programs.drift;

import java.util.Map;

public record PerpMarketsRecord(PerpMarketConfig[] marketConfigs,
                                Map<DriftProduct, PerpMarketConfig> byProduct) implements PerpMarkets {

  @Override
  public PerpMarketConfig marketConfig(final int index) {
    return marketConfigs[index];
  }

  @Override
  public PerpMarketConfig forProduct(final DriftProduct product) {
    return byProduct.get(product);
  }

  @Override
  public int numMarkets() {
    return marketConfigs.length;
  }
}
