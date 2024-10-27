package software.sava.anchor.programs.drift;

import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Stream;

public interface PerpMarkets {

  static PerpMarkets createRecord(final PerpMarketConfig[] marketConfigs) {
    final var byProduct = new EnumMap<DriftProduct, PerpMarketConfig>(DriftProduct.class);
    for (final var config : marketConfigs) {
      final var symbol = config.symbol().replace('-', '_');
      final var product = Character.isAlphabetic(symbol.charAt(0))
          ? DriftProduct.valueOf(symbol)
          : DriftProduct.valueOf('_' + symbol);
      byProduct.put(product, config);
    }
    return new PerpMarketsRecord(marketConfigs, byProduct);
  }

  static PerpMarkets createRecord(final List<PerpMarketConfig> marketConfigs) {
    return createRecord(marketConfigs.toArray(PerpMarketConfig[]::new));
  }


  PerpMarketConfig marketConfig(final int index);

  PerpMarketConfig forProduct(final DriftProduct product);

  int numMarkets();

  Stream<PerpMarketConfig> streamMarkets();

  Collection<PerpMarketConfig> markets();
}
