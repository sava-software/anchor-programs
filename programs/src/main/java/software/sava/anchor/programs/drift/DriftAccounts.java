package software.sava.anchor.programs.drift;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

import static software.sava.core.accounts.PublicKey.fromBase58Encoded;

public interface DriftAccounts {

  DriftAccounts MAIN_NET = createAddressConstants(
      "dRiftyHA39MWEi3m9aunc5MzRF1JYuBsbn6VPcn33UH",
      SpotMarkets.MAIN_NET,
      PerpMarkets.MAIN_NET
  );

  DriftAccounts DEV_NET = createAddressConstants(
      "dRiftyHA39MWEi3m9aunc5MzRF1JYuBsbn6VPcn33UH",
      SpotMarkets.DEV_NET,
      PerpMarkets.DEV_NET
  );

  AccountMeta invokedDriftProgram();

  default PublicKey driftProgram() {
    return invokedDriftProgram().publicKey();
  }

  PublicKey stateKey();

  Markets<SpotMarketConfig> spotMarkets();

  SpotMarketConfig spotMarketConfig(final int index);

  Markets<PerpMarketConfig> perpMarkets();

  PerpMarketConfig perpMarketConfig(final int index);

  static DriftAccounts createAddressConstants(final PublicKey driftProgram,
                                              Markets<SpotMarketConfig> spotMarkets,
                                              Markets<PerpMarketConfig> perpMarkets) {
    return new DriftAccountsRecord(
        AccountMeta.createInvoked(driftProgram),
        DriftPDAs.deriveStateAccount(driftProgram).publicKey(),
        spotMarkets,
        perpMarkets
    );
  }

  static DriftAccounts createAddressConstants(final String driftProgram,
                                              Markets<SpotMarketConfig> spotMarkets,
                                              Markets<PerpMarketConfig> perpMarkets) {
    return createAddressConstants(
        fromBase58Encoded(driftProgram),
        spotMarkets,
        perpMarkets
    );
  }
}
