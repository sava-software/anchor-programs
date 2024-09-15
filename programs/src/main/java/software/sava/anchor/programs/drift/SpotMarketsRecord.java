package software.sava.anchor.programs.drift;

import java.util.Map;

record SpotMarketsRecord(SpotMarketConfig[] marketConfigs,
                         Map<DriftAsset, SpotMarketConfig> byAsset) implements SpotMarkets {

  @Override
  public SpotMarketConfig marketConfig(final int index) {
    return marketConfigs[index];
  }

  @Override
  public SpotMarketConfig forAsset(final DriftAsset asset) {
    return byAsset.get(asset);
  }

  @Override
  public int numMarkets() {
    return marketConfigs.length;
  }
}
