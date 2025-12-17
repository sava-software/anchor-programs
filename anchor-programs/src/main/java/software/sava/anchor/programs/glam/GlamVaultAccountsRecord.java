package software.sava.anchor.programs.glam;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

public record GlamVaultAccountsRecord(GlamAccounts glamAccounts,
                                      AccountMeta readFeePayer,
                                      AccountMeta writeFeePayer,
                                      AccountMeta readGlamState,
                                      AccountMeta writeGlamState,
                                      ProgramDerivedAddress vaultPDA,
                                      AccountMeta readVault,
                                      AccountMeta writeVault) implements GlamVaultAccounts {

  @Override
  public PublicKey feePayer() {
    return readFeePayer.publicKey();
  }

  @Override
  public PublicKey glamPublicKey() {
    return readGlamState.publicKey();
  }

  @Override
  public ProgramDerivedAddress mintPDA(final int id) {
    return glamAccounts.mintPDA(glamPublicKey(), id);
  }
}
