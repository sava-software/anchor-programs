package software.sava.anchor.programs.drift;

import software.sava.core.accounts.meta.AccountMeta;

public sealed interface MarketConfig permits SpotMarketConfig, PerpMarketConfig {

  String symbol();

  int marketIndex();

  OracleSource oracleSource();

  AccountMeta readOracle();

  AccountMeta writeOracle();

  AccountMeta oracle(final boolean write);

  AccountMeta readMarketPDA();

  AccountMeta writeMarketPDA();
}
