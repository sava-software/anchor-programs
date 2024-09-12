package software.sava.anchor.programs.drift;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Markets<T extends MarketConfig> {

  static <T extends MarketConfig> Markets<T> createRecord(T[] marketConfigs) {
    final var bySymbol = Arrays.stream(marketConfigs)
        .collect(Collectors.toUnmodifiableMap(MarketConfig::symbol, Function.identity()));
    return new MarketsRecord<>(marketConfigs, bySymbol);
  }

  T marketConfig(final int index);

  T forSymbol(final String symbol);
}
