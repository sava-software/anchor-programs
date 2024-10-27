package software.sava.anchor.programs.drift;

import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Stream;

public interface SpotMarkets {

  static SpotMarkets createRecord(final SpotMarketConfig[] marketConfigs) {
    final var byAsset = new EnumMap<DriftAsset, SpotMarketConfig>(DriftAsset.class);
    for (final var config : marketConfigs) {
      byAsset.put(DriftAsset.valueOf(config.symbol()), config);
    }
    return new SpotMarketsRecord(marketConfigs, byAsset);
  }

  static SpotMarkets createRecord(final List<SpotMarketConfig> marketConfigs) {
    return createRecord(marketConfigs.toArray(SpotMarketConfig[]::new));
  }

  SpotMarketConfig marketConfig(final int index);

  SpotMarketConfig forAsset(final DriftAsset asset);

  int numMarkets();

  Stream<SpotMarketConfig> streamMarkets();

  Collection<SpotMarketConfig> markets();
}
