package software.sava.anchor.programs.drift;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

record SpotMarketsRecord(List<SpotMarketConfig> markets,
                         Map<String, SpotMarketConfig> byAsset) implements SpotMarkets {

  @Override
  public SpotMarketConfig marketConfig(final int index) {
    return markets.get(index);
  }

  @Override
  public SpotMarketConfig forAsset(final DriftAsset asset) {
    return forAsset(asset.name());
  }

  @Override
  public SpotMarketConfig forAsset(final String asset) {
    return byAsset.get(asset);
  }

  @Override
  public int numMarkets() {
    return markets.size();
  }

  @Override
  public Stream<SpotMarketConfig> streamMarkets() {
    return markets.stream();
  }
}
