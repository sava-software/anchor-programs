package software.sava.anchor.programs.drift;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

record DriftAccountsRecord(AccountMeta invokedDriftProgram,
                           PublicKey driftSignerPDA,
                           PublicKey marketLookupTable,
                           PublicKey serumLookupTable,
                           PublicKey stateKey,
                           SpotMarketConfig defaultQuoteMarket,
                           SpotMarkets spotMarkets,
                           PerpMarkets perpMarkets) implements DriftAccounts {
  @Override
  public SpotMarketConfig spotMarketConfig(final int index) {
    return spotMarkets.marketConfig(index);
  }

  @Override
  public SpotMarketConfig spotMarketConfig(final DriftAsset symbol) {
    return spotMarkets.forAsset(symbol);
  }

  @Override
  public PerpMarketConfig perpMarketConfig(final int index) {
    return perpMarkets.marketConfig(index);
  }

  @Override
  public PerpMarketConfig perpMarketConfig(final DriftProduct product) {
    return perpMarkets.forProduct(product);
  }
}
