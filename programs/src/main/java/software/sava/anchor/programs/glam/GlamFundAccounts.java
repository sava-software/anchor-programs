package software.sava.anchor.programs.glam;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

public interface GlamFundAccounts {

  static GlamFundAccounts createAccounts(final GlamAccounts glamAccounts,
                                         final PublicKey signerPublicKey,
                                         final PublicKey fundPublicKey) {
    final var treasuryPDA = glamAccounts.treasuryPDA(fundPublicKey);
    final var openFundsPDA = glamAccounts.openFundsPDA(fundPublicKey);
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
