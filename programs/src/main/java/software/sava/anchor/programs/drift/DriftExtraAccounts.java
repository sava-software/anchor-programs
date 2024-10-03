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

  void userAccounts(final User user);

  void market(final SpotMarketConfig marketConfig);

  void market(final PerpMarketConfig marketConfig);

  void market(final MarketConfig marketConfig);

  void market(final PerpMarketConfig marketConfig, final MarketConfig quoteMarket);

  void userAndMarket(final User user, final SpotMarketConfig marketConfig);

  void userAndMarket(final User user, final PerpMarketConfig marketConfig);

  void userAndMarket(final User user, final MarketConfig marketConfig);
}
