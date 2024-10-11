package software.sava.anchor.programs.drift;

import software.sava.core.accounts.meta.AccountMeta;

public sealed interface MarketConfig permits SpotMarketConfig, PerpMarketConfig {

  String symbol();

  int marketIndex();

  AccountMeta readOracle();

  AccountMeta readMarketPDA();

  AccountMeta writeMarketPDA();
}
