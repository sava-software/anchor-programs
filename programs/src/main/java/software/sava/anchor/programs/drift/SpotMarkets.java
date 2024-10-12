package software.sava.anchor.programs.drift;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.stream.Stream;

public interface SpotMarkets {

  static SpotMarkets createRecord(final SpotMarketConfig[] marketConfigs) {
    final var byAsset = new EnumMap<DriftAsset, SpotMarketConfig>(DriftAsset.class);
    Arrays.stream(marketConfigs).forEach(config -> byAsset.put(DriftAsset.valueOf(config.symbol()), config));
    return new SpotMarketsRecord(marketConfigs, byAsset);
  }

  SpotMarketConfig marketConfig(final int index);

  SpotMarketConfig forAsset(final DriftAsset asset);

  int numMarkets();

  Stream<SpotMarketConfig> streamMarkets();

  Collection<SpotMarketConfig> markets();
}
