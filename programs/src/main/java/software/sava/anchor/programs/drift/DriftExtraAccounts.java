package software.sava.anchor.programs.drift;

import software.sava.anchor.programs.drift.anchor.types.User;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

import java.util.HashMap;
import java.util.List;

public interface DriftExtraAccounts {

  static DriftExtraAccounts createExtraAccounts(final DriftAccounts driftAccounts) {
    final int numSpotMarkets = driftAccounts.spotMarkets().numMarkets();
    final int size = Integer.bitCount(numSpotMarkets) == 1
        ? numSpotMarkets
        : Integer.highestOneBit(numSpotMarkets) << 1;
    final var oracles = HashMap.<PublicKey, AccountMeta>newHashMap(size);
    final var spotMarkets = HashMap.<PublicKey, AccountMeta>newHashMap(size);
    final var perpMarkets = HashMap.<PublicKey, AccountMeta>newHashMap(8);
    return new DriftExtraAccountsRecord(driftAccounts, oracles, spotMarkets, perpMarkets);
  }

  DriftAccounts driftAccounts();

  List<AccountMeta> toList();

  void mergeOracle(final AccountMeta accountMeta);

  void mergeSpotMarket(final AccountMeta accountMeta);

  void mergePerpMarket(final AccountMeta accountMeta);

  void userAccounts(final User user, final boolean write);

  void market(final SpotMarketConfig marketConfig, final boolean write);

  void market(final PerpMarketConfig marketConfig, final boolean write);

  void market(final MarketConfig marketConfig, final boolean write);

  void market(final PerpMarketConfig marketConfig, final SpotMarketConfig quoteMarket, final boolean write);

  void userAndMarket(final User user, final SpotMarketConfig marketConfig, final boolean write);

  void userAndMarket(final User user, final PerpMarketConfig marketConfig, final boolean write);

  void userAndMarket(final User user, final MarketConfig marketConfig, final boolean write);
}
