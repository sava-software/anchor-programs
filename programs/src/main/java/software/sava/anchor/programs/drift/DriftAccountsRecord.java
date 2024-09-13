package software.sava.anchor.programs.drift;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

record DriftAccountsRecord(AccountMeta invokedDriftProgram,
                           PublicKey stateKey,
                           SpotMarketConfig defaultQuoteMarket,
                           Markets<SpotMarketConfig> spotMarkets,
                           Markets<PerpMarketConfig> perpMarkets) implements DriftAccounts {
  @Override
  public SpotMarketConfig spotMarketConfig(final int index) {
    return spotMarkets.marketConfig(index);
  }

  @Override
  public SpotMarketConfig spotMarketConfig(final String symbol) {
    return spotMarkets.forSymbol(symbol);
  }

  @Override
  public PerpMarketConfig perpMarketConfig(final int index) {
    return perpMarkets.marketConfig(index);
  }

  @Override
  public PerpMarketConfig perpMarketConfig(final String symbol) {
    return perpMarkets.forSymbol(symbol);
  }
}
