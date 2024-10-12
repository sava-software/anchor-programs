package software.sava.anchor.programs.drift;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

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

  @Override
  public Stream<SpotMarketConfig> streamMarkets() {
    return Arrays.stream(marketConfigs);
  }

  @Override
  public Collection<SpotMarketConfig> markets() {
    return byAsset.values();
  }
}
