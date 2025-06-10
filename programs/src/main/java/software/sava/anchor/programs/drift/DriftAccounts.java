package software.sava.anchor.programs.drift;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

import java.util.List;

import static software.sava.anchor.programs.drift.DriftPDAs.deriveSignerAccount;
import static software.sava.core.accounts.PublicKey.fromBase58Encoded;

public interface DriftAccounts {

  DriftAccounts MAIN_NET = createAddressConstants(
      "dRiftyHA39MWEi3m9aunc5MzRF1JYuBsbn6VPcn33UH",
      "D9cnvzswDikQDf53k4HpQ3KJ9y1Fv3HGGDFYMXnK5T6c",
      "EiWSskK5HXnBTptiS5DH6gpAJRVNQ3cAhTKBGaiaysAb",
      "GPZkp76cJtNL2mphCvT6FXkJCVPpouidnacckR6rzKDN",
      "vAuLTsyrvSfZRuRB3XgvkPwNGgYSs9YRYymVebLKoxR"
  );

  DriftAccounts DEV_NET = createAddressConstants(
      "dRiftyHA39MWEi3m9aunc5MzRF1JYuBsbn6VPcn33UH",
      "FaMS3U4uBojvGn5FSDEPimddcXsCfwkKsFgMVVnDdxGb",
      null,
      null,
      null
  );

  AccountMeta invokedDriftProgram();

  default PublicKey driftProgram() {
    return invokedDriftProgram().publicKey();
  }

  PublicKey driftSignerPDA();

  PublicKey stateKey();

  PublicKey marketLookupTable();

  List<PublicKey> marketLookupTables();

  PublicKey serumLookupTable();

  AccountMeta invokedDriftVaultsProgram();

  default PublicKey driftVaultsProgram() {
    return invokedDriftVaultsProgram().publicKey();
  }

  static DriftAccounts createAddressConstants(final PublicKey driftProgram,
                                              final PublicKey marketLookupTable,
                                              final PublicKey marketLookupTable2,
                                              final PublicKey serumLookupTable,
                                              final PublicKey driftVaultsProgram) {
    final var driftSigner = deriveSignerAccount(driftProgram).publicKey();
    return new DriftAccountsRecord(
        AccountMeta.createInvoked(driftProgram),
        driftSigner,
        marketLookupTable,
        marketLookupTable2 == null ? List.of(marketLookupTable) : List.of(marketLookupTable, marketLookupTable2),
        serumLookupTable,
        DriftPDAs.deriveStateAccount(driftProgram).publicKey(),
        driftVaultsProgram == null ? null : AccountMeta.createInvoked(driftVaultsProgram)
    );
  }

  static DriftAccounts createAddressConstants(final String driftProgram,
                                              final String marketLookupTable,
                                              final String marketLookupTable2,
                                              final String serumLookupTable,
                                              final String driftVaultsProgram) {
    return createAddressConstants(
        fromBase58Encoded(driftProgram),
        fromBase58Encoded(marketLookupTable),
        marketLookupTable2 == null ? null : fromBase58Encoded(marketLookupTable2),
        serumLookupTable == null ? null : fromBase58Encoded(driftProgram),
        driftVaultsProgram == null ? null : fromBase58Encoded(driftVaultsProgram)
    );
  }
}
