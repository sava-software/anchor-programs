package software.sava.anchor.programs.drift;

import java.util.Map;

record MarketsRecord<T extends MarketConfig>(T[] marketConfigs,
                                             Map<String, T> bySymbol) implements Markets<T> {

  @Override
  public T marketConfig(final int index) {
    return marketConfigs[index];
  }

  @Override
  public T forSymbol(final String symbol) {
    return bySymbol.get(symbol);
  }
}
