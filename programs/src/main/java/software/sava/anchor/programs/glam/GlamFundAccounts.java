package software.sava.anchor.programs.glam;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

import static software.sava.anchor.programs.glam.GlamFundAccountsRecord.deriveAddress;

public interface GlamFundAccounts {

  static GlamFundAccounts createAccounts(final GlamAccounts glamAccounts,
                                         final PublicKey signerPublicKey,
                                         final PublicKey fundPublicKey) {
    final var treasuryPDA = deriveAddress("treasury", fundPublicKey, glamAccounts.program());
    final var openFundsPDA = deriveAddress("openfunds", fundPublicKey, glamAccounts.program());
    return new GlamFundAccountsRecord(
        glamAccounts,
        signerPublicKey,
        fundPublicKey,
        treasuryPDA,
        AccountMeta.createWrite(treasuryPDA.publicKey()),
        openFundsPDA
    );
  }

  static GlamFundAccounts createAccounts(final PublicKey signerPublicKey, final PublicKey fundPublicKey) {
    return GlamFundAccounts.createAccounts(GlamAccounts.MAIN_NET, signerPublicKey, fundPublicKey);
  }

  GlamAccounts glamAccounts();

  PublicKey signerPublicKey();

  PublicKey fundPublicKey();

  ProgramDerivedAddress treasuryPDA();

  default PublicKey treasuryPublicKey() {
    return treasuryPDA().publicKey();
  }

  AccountMeta treasuryWriteMeta();

  ProgramDerivedAddress openFundsPDA();

  ProgramDerivedAddress shareClassPDA(final int shareId);
}
