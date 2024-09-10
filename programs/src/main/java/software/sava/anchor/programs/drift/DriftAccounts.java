package software.sava.anchor.programs.drift;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

import static software.sava.core.accounts.PublicKey.fromBase58Encoded;

public interface DriftAccounts {

  DriftAccounts MAIN_NET = createAddressConstants(
      "dRiftyHA39MWEi3m9aunc5MzRF1JYuBsbn6VPcn33UH",
      "5zpq7DvB6UdFFvpmBPspGPNfUGoBRRCE2HHg5u3gxcsN"
  );

  AccountMeta invokedDriftProgram();

  PublicKey stateKey();

  static DriftAccounts createAddressConstants(
      final PublicKey driftProgram,
      final PublicKey stateKey) {
    return new DriftAccountsRecord(
        AccountMeta.createInvoked(driftProgram),
        stateKey
    );
  }

  static DriftAccounts createAddressConstants(
      final String driftProgram,
      final String stateKey) {
    return createAddressConstants(
        fromBase58Encoded(driftProgram),
        fromBase58Encoded(stateKey)
    );
  }
}
