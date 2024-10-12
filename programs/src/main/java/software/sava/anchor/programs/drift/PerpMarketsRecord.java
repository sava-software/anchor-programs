package software.sava.anchor.programs.drift;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

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

  @Override
  public Stream<PerpMarketConfig> streamMarkets() {
    return Arrays.stream(marketConfigs);
  }

  @Override
  public Collection<PerpMarketConfig> markets() {
    return byProduct.values();
  }
}
