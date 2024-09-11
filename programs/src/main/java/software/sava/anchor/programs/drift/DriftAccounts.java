package software.sava.anchor.programs.drift;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

import static software.sava.core.accounts.PublicKey.fromBase58Encoded;

public interface DriftAccounts {

  DriftAccounts MAIN_NET = createAddressConstants(
      "dRiftyHA39MWEi3m9aunc5MzRF1JYuBsbn6VPcn33UH"
  );

  AccountMeta invokedDriftProgram();

  default PublicKey driftProgram() {
    return invokedDriftProgram().publicKey();
  }

  PublicKey stateKey();

  static DriftAccounts createAddressConstants(final PublicKey driftProgram) {
    return new DriftAccountsRecord(
        AccountMeta.createInvoked(driftProgram),
        DriftPDAs.deriveStateAccount(driftProgram).publicKey()
    );
  }

  static DriftAccounts createAddressConstants(final String driftProgram) {
    return createAddressConstants(
        fromBase58Encoded(driftProgram)
    );
  }
}
