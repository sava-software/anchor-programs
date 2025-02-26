package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamPDAs;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

public interface GlamVaultAccounts {

  static GlamVaultAccounts createAccounts(final GlamAccounts glamAccounts,
                                          final PublicKey feePayer,
                                          final PublicKey glamPublicKey) {
    final var program = glamAccounts.program();
    final var vaultPDA = GlamPDAs.vaultPDA(program, glamPublicKey);
    final var metadataPDA = GlamPDAs.metadataPDA(program, glamPublicKey);
    return new GlamVaultAccountsRecord(
        glamAccounts,
        AccountMeta.createReadOnlySigner(feePayer),
        AccountMeta.createWritableSigner(feePayer),
        AccountMeta.createRead(glamPublicKey),
        AccountMeta.createWrite(glamPublicKey),
        vaultPDA,
        AccountMeta.createRead(vaultPDA.publicKey()),
        AccountMeta.createWrite(vaultPDA.publicKey()),
        metadataPDA,
        AccountMeta.createRead(metadataPDA.publicKey()),
        AccountMeta.createWrite(metadataPDA.publicKey())
    );
  }

  static GlamVaultAccounts createAccounts(final PublicKey feePayer, final PublicKey glamPublicKey) {
    return GlamVaultAccounts.createAccounts(GlamAccounts.MAIN_NET, feePayer, glamPublicKey);
  }

  static GlamVaultAccounts createV0Accounts(final GlamAccounts glamAccounts,
                                            final PublicKey feePayer,
                                            final PublicKey glamPublicKey) {
    final var program = glamAccounts.program();
    final var vaultPDA = software.sava.anchor.programs.glam_v0.anchor.GlamPDAs.vaultPDA(program, glamPublicKey);
    final var metadataPDA = software.sava.anchor.programs.glam_v0.anchor.GlamPDAs.metadataPDA(program, glamPublicKey);
    return new GlamVaultAccountsRecord(
        glamAccounts,
        AccountMeta.createReadOnlySigner(feePayer),
        AccountMeta.createWritableSigner(feePayer),
        AccountMeta.createRead(glamPublicKey),
        AccountMeta.createWrite(glamPublicKey),
        vaultPDA,
        AccountMeta.createRead(vaultPDA.publicKey()),
        AccountMeta.createWrite(vaultPDA.publicKey()),
        metadataPDA,
        AccountMeta.createRead(metadataPDA.publicKey()),
        AccountMeta.createWrite(metadataPDA.publicKey())
    );
  }

  static GlamVaultAccounts createV0Accounts(final PublicKey feePayer, final PublicKey glamPublicKey) {
    return GlamVaultAccounts.createV0Accounts(GlamAccounts.MAIN_NET_VO, feePayer, glamPublicKey);
  }

  GlamAccounts glamAccounts();

  PublicKey feePayer();

  PublicKey glamPublicKey();

  AccountMeta writeGlamState();

  AccountMeta readGlamState();

  ProgramDerivedAddress vaultPDA();

  default PublicKey vaultPublicKey() {
    return vaultPDA().publicKey();
  }

  AccountMeta writeVault();

  AccountMeta readVault();

  ProgramDerivedAddress metadataPDA();

  ProgramDerivedAddress shareClassPDA(final int shareId);
}
