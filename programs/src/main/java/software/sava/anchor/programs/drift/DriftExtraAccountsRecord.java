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

  private void mergeAccount(final Map<PublicKey, AccountMeta> markets, final MarketConfig marketConfig) {
    mergeAccount(markets, marketConfig.readMarketPDA());
    mergeAccount(oracles, marketConfig.readOracle());
  }

  @Override
  public void userAccounts(final User user) {
    for (final var position : user.spotPositions()) {
      if (position.scaledBalance() != 0) {
        final var spotMarket = driftAccounts.spotMarketConfig(position.marketIndex());
        market(spotMarket);
      }
    }
  }

  @Override
  public void market(final SpotMarketConfig marketConfig) {
    mergeAccount(spotMarkets, marketConfig);
  }

  @Override
  public void market(final MarketConfig marketConfig) {
    final var markets = switch (marketConfig) {
      case SpotMarketConfig _ -> spotMarkets;
      case PerpMarketConfig perpMarketConfig -> {
        market(driftAccounts.defaultQuoteMarket());
//        final var baseSpotMarket = driftAccounts.spotMarketConfig(DriftAsset.valueOf(perpMarketConfig.baseAssetSymbol()));
//        market(baseSpotMarket);
        yield perpMarkets;
      }
    };
    mergeAccount(markets, marketConfig);
  }

  @Override
  public void market(final PerpMarketConfig perpMarketConfig, final SpotMarketConfig quoteMarket) {
    market(quoteMarket);
//    final var baseSpotMarket = driftAccounts.spotMarketConfig(DriftAsset.valueOf(perpMarketConfig.baseAssetSymbol()));
//    market(baseSpotMarket);
    mergeAccount(perpMarkets, perpMarketConfig);
  }

  @Override
  public void market(final PerpMarketConfig perpMarketConfig) {
//    final var baseSpotMarket = driftAccounts.spotMarketConfig(DriftAsset.valueOf(perpMarketConfig.baseAssetSymbol()));
//    market(baseSpotMarket);
    market(perpMarketConfig, driftAccounts.defaultQuoteMarket());
  }

  @Override
  public void userAndMarket(final User user, final SpotMarketConfig marketConfig) {
    userAccounts(user);
    market(marketConfig);
  }

  @Override
  public void userAndMarket(final User user, final PerpMarketConfig marketConfig) {
    userAccounts(user);
    market(marketConfig);
  }

  @Override
  public void userAndMarket(final User user, final MarketConfig marketConfig) {
    userAccounts(user);
    market(marketConfig);
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
