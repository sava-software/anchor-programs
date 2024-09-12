package software.sava.anchor.programs.drift;

import software.sava.anchor.programs.drift.anchor.types.User;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Transaction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

record DriftExtraAccounts(DriftAccounts driftAccounts,
                          Map<PublicKey, AccountMeta> oracles,
                          Map<PublicKey, AccountMeta> spotMarkets,
                          Map<PublicKey, AccountMeta> perpMarkets) {

  static DriftExtraAccounts createExtraAccounts(final DriftAccounts driftAccounts) {
    final var oracles = HashMap.<PublicKey, AccountMeta>newHashMap(32);
    final var spotMarkets = HashMap.<PublicKey, AccountMeta>newHashMap(32);
    final var perpMarkets = HashMap.<PublicKey, AccountMeta>newHashMap(32);
    return new DriftExtraAccounts(driftAccounts, oracles, spotMarkets, perpMarkets);
  }

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

  public void userAccounts(final User user) {
    for (final var position : user.spotPositions()) {
      if (position.cumulativeDeposits() != 0) {
        final var spotMarket = driftAccounts.spotMarketConfig(position.marketIndex());
        market(spotMarket);
      }
    }
  }

  private static void mergeAccount(final Map<PublicKey, AccountMeta> metaMap, final AccountMeta meta) {
    metaMap.merge(meta.publicKey(), meta, Transaction.MERGE_ACCOUNT_META);
  }

  public void market(final MarketConfig marketConfig) {
    final var markets = switch (marketConfig) {
      case SpotMarketConfig _ -> spotMarkets;
      case PerpMarketConfig _ -> {
        final var quoteMarket = driftAccounts.spotMarketConfig(0);
        market(quoteMarket);
        yield perpMarkets;
      }
    };
    mergeAccount(markets, marketConfig.readMarketPDA());
    mergeAccount(oracles, marketConfig.readOracle());
  }
}
