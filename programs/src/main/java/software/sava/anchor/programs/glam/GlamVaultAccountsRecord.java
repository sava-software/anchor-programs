package software.sava.anchor.programs.glam;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

public record GlamVaultAccountsRecord(GlamAccounts glamAccounts,
                                      PublicKey signerPublicKey,
                                      PublicKey glamPublicKey,
                                      ProgramDerivedAddress vaultPDA,
                                      AccountMeta vaultWriteMeta,
                                      ProgramDerivedAddress metadataPDA) implements GlamVaultAccounts {

  @Override
  public ProgramDerivedAddress shareClassPDA(final int shareClassId) {
    return glamAccounts.mintPDA(glamPublicKey, shareClassId);
  }
}
