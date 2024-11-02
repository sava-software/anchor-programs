package software.sava.anchor.programs.drift;

import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;

public interface SpotMarkets {

  static SpotMarkets createRecord(final SpotMarketConfig[] marketConfigs) {
    final var byAsset = new TreeMap<String, SpotMarketConfig>(String.CASE_INSENSITIVE_ORDER);
    for (final var config : marketConfigs) {
      byAsset.put(config.symbol(), config);
    }
    return new SpotMarketsRecord(marketConfigs, byAsset);
  }

  static SpotMarkets createRecord(final List<SpotMarketConfig> marketConfigs) {
    return createRecord(marketConfigs.toArray(SpotMarketConfig[]::new));
  }

  SpotMarketConfig marketConfig(final int index);

  SpotMarketConfig forAsset(final DriftAsset asset);

  SpotMarketConfig forAsset(final String asset);

  int numMarkets();

  Stream<SpotMarketConfig> streamMarkets();

  Collection<SpotMarketConfig> markets();
}
