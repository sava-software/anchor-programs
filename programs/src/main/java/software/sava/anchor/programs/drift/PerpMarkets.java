package software.sava.anchor.programs.drift;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.stream.Stream;

public interface PerpMarkets {

  static PerpMarkets createRecord(final PerpMarketConfig[] marketConfigs) {
    final var byProduct = new EnumMap<DriftProduct, PerpMarketConfig>(DriftProduct.class);
    Arrays.stream(marketConfigs).forEach(config -> {
      final var symbol = config.symbol().replace('-', '_');
      final var product = Character.isAlphabetic(symbol.charAt(0))
          ? DriftProduct.valueOf(symbol)
          : DriftProduct.valueOf('_' + symbol);
      byProduct.put(product, config);
    });
    return new PerpMarketsRecord(marketConfigs, byProduct);
  }

  PerpMarketConfig marketConfig(final int index);

  PerpMarketConfig forProduct(final DriftProduct product);

  int numMarkets();

  Stream<PerpMarketConfig> streamMarkets();

  Collection<PerpMarketConfig> markets();
}
