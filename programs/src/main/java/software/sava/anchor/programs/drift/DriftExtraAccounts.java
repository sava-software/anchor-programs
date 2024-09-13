package software.sava.anchor.programs.drift;

import software.sava.anchor.programs.drift.anchor.types.User;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

import java.util.HashMap;
import java.util.List;

public interface DriftExtraAccounts {

  static DriftExtraAccounts createExtraAccounts(final DriftAccounts driftAccounts) {
    final var oracles = HashMap.<PublicKey, AccountMeta>newHashMap(32);
    final var spotMarkets = HashMap.<PublicKey, AccountMeta>newHashMap(32);
    final var perpMarkets = HashMap.<PublicKey, AccountMeta>newHashMap(32);
    return new DriftExtraAccountsRecord(driftAccounts, oracles, spotMarkets, perpMarkets);
  }

  DriftAccounts driftAccounts();

  List<AccountMeta> toList();

  void userAccounts(final User user);

  void market(final MarketConfig marketConfig);

  void market(final MarketConfig marketConfig, final MarketConfig quoteMarket);

  void placeOrder(final User user, final MarketConfig marketConfig);
}
