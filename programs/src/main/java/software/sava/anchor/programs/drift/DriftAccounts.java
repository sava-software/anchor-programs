package software.sava.anchor.programs.drift;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

import static software.sava.anchor.programs.drift.DriftAsset.USDC;
import static software.sava.core.accounts.PublicKey.fromBase58Encoded;

public interface DriftAccounts {

  DriftAccounts MAIN_NET = createAddressConstants(
      "dRiftyHA39MWEi3m9aunc5MzRF1JYuBsbn6VPcn33UH",
      SpotMarketsConfigs.MAIN_NET,
      PerpMarketConfigs.MAIN_NET
  );

  DriftAccounts DEV_NET = createAddressConstants(
      "dRiftyHA39MWEi3m9aunc5MzRF1JYuBsbn6VPcn33UH",
      SpotMarketsConfigs.DEV_NET,
      PerpMarketConfigs.DEV_NET
  );

  AccountMeta invokedDriftProgram();

  default PublicKey driftProgram() {
    return invokedDriftProgram().publicKey();
  }

  PublicKey stateKey();

  SpotMarketConfig defaultQuoteMarket();

  SpotMarkets spotMarkets();

  SpotMarketConfig spotMarketConfig(final int index);

  SpotMarketConfig spotMarketConfig(final DriftAsset symbol);

  PerpMarkets perpMarkets();

  PerpMarketConfig perpMarketConfig(final int index);

  PerpMarketConfig perpMarketConfig(final DriftProduct product);

  static DriftAccounts createAddressConstants(final PublicKey driftProgram,
                                              final SpotMarkets spotMarkets,
                                              final PerpMarkets perpMarkets) {
    return new DriftAccountsRecord(
        AccountMeta.createInvoked(driftProgram),
        DriftPDAs.deriveStateAccount(driftProgram).publicKey(),
        spotMarkets.forAsset(USDC),
        spotMarkets,
        perpMarkets
    );
  }

  static DriftAccounts createAddressConstants(final String driftProgram,
                                              final SpotMarkets spotMarkets,
                                              final PerpMarkets perpMarkets) {
    return createAddressConstants(
        fromBase58Encoded(driftProgram),
        spotMarkets,
        perpMarkets
    );
  }
}
