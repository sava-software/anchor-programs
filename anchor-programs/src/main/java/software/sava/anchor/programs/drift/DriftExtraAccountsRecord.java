package software.sava.anchor.programs.drift;

import software.sava.anchor.programs.drift.anchor.types.User;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

record DriftExtraAccountsRecord(SpotMarketConfig quoteMarket,
                                SpotMarkets spotMarkets,
                                PerpMarkets perpMarkets,
                                Map<PublicKey, AccountMeta> oracleMetas,
                                Map<PublicKey, AccountMeta> spotMarketMetas,
                                Map<PublicKey, AccountMeta> perpMarketMetas) implements DriftExtraAccounts {

  private static void mergeAccount(final Map<PublicKey, AccountMeta> metaMap, final AccountMeta meta) {
    metaMap.merge(meta.publicKey(), meta, Transaction.MERGE_ACCOUNT_META);
  }

  private void mergeAccount(final Map<PublicKey, AccountMeta> markets,
                            final MarketConfig marketConfig,
                            final boolean write) {
    mergeAccount(markets, write ? marketConfig.writeMarketPDA() : marketConfig.readMarketPDA());
    final var oracle = marketConfig.oracle(write);
    if (oracle != null && !oracle.publicKey().equals(PublicKey.NONE)) {
      mergeAccount(oracleMetas, oracle);
    }
  }

  @Override
  public void mergeOracle(final AccountMeta accountMeta) {
    mergeAccount(oracleMetas, accountMeta);
  }

  @Override
  public void mergeSpotMarket(final AccountMeta accountMeta) {
    mergeAccount(spotMarketMetas, accountMeta);
  }

  @Override
  public void mergePerpMarket(final AccountMeta accountMeta) {
    mergeAccount(perpMarketMetas, accountMeta);
  }

  @Override
  public void userAccounts(final User user, final boolean write) {
    for (final var position : user.spotPositions()) {
      if (position.scaledBalance() != 0) {
        final var spotMarket = spotMarkets.marketConfig(position.marketIndex());
        market(spotMarket, write);
      }
    }
    for (final var position : user.perpPositions()) {
      if (position.quoteAssetAmount() != 0) {
        final var perpMarket = perpMarkets.marketConfig(position.marketIndex());
        market(perpMarket, write);
      }
    }
  }

  @Override
  public void market(final SpotMarketConfig marketConfig, final boolean write) {
    mergeAccount(spotMarketMetas, marketConfig, write);
  }

  @Override
  public void market(final PerpMarketConfig perpMarketConfig,
                     final SpotMarketConfig quoteMarket,
                     final boolean write) {
    market(quoteMarket, write);
    mergeAccount(perpMarketMetas, perpMarketConfig, write);
  }

  @Override
  public void market(final PerpMarketConfig perpMarketConfig, final boolean write) {
    market(perpMarketConfig, quoteMarket, write);
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
    final int numAccounts = oracleMetas.size() + spotMarketMetas.size() + perpMarketMetas.size();
    final var metas = new AccountMeta[numAccounts];
    int i = 0;
    for (final var meta : oracleMetas.values()) {
      metas[i++] = meta;
    }
    for (final var meta : spotMarketMetas.values()) {
      metas[i++] = meta;
    }
    for (final var meta : perpMarketMetas.values()) {
      metas[i++] = meta;
    }
    return Arrays.asList(metas);
  }
}
