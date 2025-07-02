package software.sava.anchor.programs.drift;

import software.sava.anchor.programs.drift.anchor.types.User;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DriftExtraAccounts {

  static DriftExtraAccounts createExtraAccounts(final SpotMarkets spotMarkets, final PerpMarkets perpMarkets) {
    final int numSpotMarkets = spotMarkets.numMarkets();
    final int size = Integer.bitCount(numSpotMarkets) == 1
        ? numSpotMarkets
        : Integer.highestOneBit(numSpotMarkets) << 1;
    final var oracleMetas = HashMap.<PublicKey, AccountMeta>newHashMap(size);
    final var spotMarketMetas = HashMap.<PublicKey, AccountMeta>newHashMap(size);
    final var perpMarketMetas = HashMap.<PublicKey, AccountMeta>newHashMap(8);
    return new DriftExtraAccountsRecord(
        spotMarkets.marketConfig(0),
        spotMarkets,
        perpMarkets,
        oracleMetas,
        spotMarketMetas,
        perpMarketMetas
    );
  }

  SpotMarkets spotMarkets();

  PerpMarkets perpMarkets();

  Map<PublicKey, AccountMeta> oracleMetas();

  Map<PublicKey, AccountMeta> spotMarketMetas();

  Map<PublicKey, AccountMeta> perpMarketMetas();

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
