package software.sava.anchor.programs.drift;

import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;

public interface PerpMarkets {

  static PerpMarkets createRecord(final PerpMarketConfig[] marketConfigs) {
    final var byProduct = new TreeMap<String, PerpMarketConfig>(String.CASE_INSENSITIVE_ORDER);
    for (final var config : marketConfigs) {
      byProduct.put(config.symbol(), config);
    }
    return new PerpMarketsRecord(marketConfigs, byProduct);
  }

  static PerpMarkets createRecord(final List<PerpMarketConfig> marketConfigs) {
    return createRecord(marketConfigs.toArray(PerpMarketConfig[]::new));
  }


  PerpMarketConfig marketConfig(final int index);

  PerpMarketConfig forProduct(final DriftProduct product);

  PerpMarketConfig forProduct(final String product);

  int numMarkets();

  Stream<PerpMarketConfig> streamMarkets();

  Collection<PerpMarketConfig> markets();
}
