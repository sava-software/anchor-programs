package software.sava.anchor.programs.drift;

import software.sava.anchor.programs.drift.anchor.types.User;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

record DriftExtraAccountsRecord(DriftAccounts driftAccounts,
                                Map<PublicKey, AccountMeta> oracles,
                                Map<PublicKey, AccountMeta> spotMarkets,
                                Map<PublicKey, AccountMeta> perpMarkets) implements DriftExtraAccounts {

  private static void mergeAccount(final Map<PublicKey, AccountMeta> metaMap, final AccountMeta meta) {
    metaMap.merge(meta.publicKey(), meta, Transaction.MERGE_ACCOUNT_META);
  }

  private void mergeAccount(final Map<PublicKey, AccountMeta> markets,
                            final MarketConfig marketConfig,
                            final boolean write) {
    mergeAccount(markets, write ? marketConfig.writeMarketPDA() : marketConfig.readMarketPDA());
    final var oracle = marketConfig.oracle(write);
    if (oracle != null && !oracle.publicKey().equals(PublicKey.NONE)) {
      mergeAccount(oracles, oracle);
    }
  }

  @Override
  public void mergeOracle(final AccountMeta accountMeta) {
    mergeAccount(oracles, accountMeta);
  }

  @Override
  public void mergeSpotMarket(final AccountMeta accountMeta) {
    mergeAccount(spotMarkets, accountMeta);
  }

  @Override
  public void mergePerpMarket(final AccountMeta accountMeta) {
    mergeAccount(perpMarkets, accountMeta);
  }

  @Override
  public void userAccounts(final User user, final boolean write) {
    for (final var position : user.spotPositions()) {
      if (position.scaledBalance() != 0) {
        final var spotMarket = driftAccounts.spotMarketConfig(position.marketIndex());
        market(spotMarket, write);
      }
    }
    for (final var position : user.perpPositions()) {
      if (position.quoteAssetAmount() != 0) {
        final var perpMarket = driftAccounts.perpMarketConfig(position.marketIndex());
        market(perpMarket, write);
      }
    }
  }

  @Override
  public void market(final SpotMarketConfig marketConfig, final boolean write) {
    mergeAccount(spotMarkets, marketConfig, write);
  }

  @Override
  public void market(final PerpMarketConfig perpMarketConfig,
                     final SpotMarketConfig quoteMarket,
                     final boolean write) {
    market(quoteMarket, write);
    mergeAccount(perpMarkets, perpMarketConfig, write);
  }

  @Override
  public void market(final PerpMarketConfig perpMarketConfig, final boolean write) {
    market(perpMarketConfig, driftAccounts.defaultQuoteMarket(), write);
  }

  @Override
  public void market(final MarketConfig marketConfig, final boolean write) {
    switch (marketConfig) {
      case SpotMarketConfig spotMarketConfig -> market(spotMarketConfig, write);
      case PerpMarketConfig perpMarketConfig -> market(perpMarketConfig, write);
    }
  }

  @Override
  public void userAndMarket(final User user, final SpotMarketConfig marketConfig, final boolean write) {
    userAccounts(user, write);
    market(marketConfig, write);
  }

  @Override
  public void userAndMarket(final User user, final PerpMarketConfig marketConfig, final boolean write) {
    userAccounts(user, write);
    market(marketConfig, write);
  }

  @Override
  public void userAndMarket(final User user, final MarketConfig marketConfig, final boolean write) {
    userAccounts(user, write);
    market(marketConfig, write);
  }

  @Override
  public List<AccountMeta> toList() {
    final int numAccounts = oracles.size() + spotMarkets.size() + perpMarkets.size();
    final var metas = new AccountMeta[numAccounts];
    int i = 0;
    for (final var meta : oracles.values()) {
      metas[i++] = meta;
    }
    for (final var meta : spotMarkets.values()) {
      metas[i++] = meta;
    }
    for (final var meta : perpMarkets.values()) {
      metas[i++] = meta;
    }
    return Arrays.asList(metas);
  }
}
