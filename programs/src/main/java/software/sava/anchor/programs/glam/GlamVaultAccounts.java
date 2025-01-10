package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamPDAs;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

public interface GlamVaultAccounts {

  static GlamVaultAccounts createAccounts(final GlamAccounts glamAccounts,
                                          final PublicKey signerPublicKey,
                                          final PublicKey glamPublicKey) {
    final var program = glamAccounts.program();
    final var vaultPDA = GlamPDAs.vaultPDA(program, glamPublicKey);
    final var openFundsPDA = GlamPDAs.openfundsPDA(program, glamPublicKey);
    return new GlamVaultAccountsRecord(
        glamAccounts,
        signerPublicKey,
        glamPublicKey,
        vaultPDA,
        AccountMeta.createWrite(vaultPDA.publicKey()),
        openFundsPDA
    );
  }

  static GlamVaultAccounts createAccounts(final PublicKey signerPublicKey, final PublicKey glamPublicKey) {
    return GlamVaultAccounts.createAccounts(GlamAccounts.MAIN_NET, signerPublicKey, glamPublicKey);
  }

  GlamAccounts glamAccounts();

  PublicKey signerPublicKey();

  PublicKey glamPublicKey();

  ProgramDerivedAddress vaultPDA();

  default PublicKey vaultPublicKey() {
    return vaultPDA().publicKey();
  }

  AccountMeta vaultWriteMeta();

  ProgramDerivedAddress openFundsPDA();

  ProgramDerivedAddress shareClassPDA(final int shareId);
}
