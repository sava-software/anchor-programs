package software.sava.anchor.programs.glam;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

record GlamVaultAccountsRecord(GlamAccounts glamAccounts,
                               PublicKey signerPublicKey,
                               PublicKey glamPublicKey,
                               ProgramDerivedAddress vaultPDA,
                               AccountMeta vaultWriteMeta,
                               ProgramDerivedAddress openFundsPDA) implements GlamVaultAccounts {

  @Override
  public ProgramDerivedAddress shareClassPDA(final int shareClassId) {
    return glamAccounts.shareClassMintPDA(glamPublicKey, shareClassId);
  }
}
