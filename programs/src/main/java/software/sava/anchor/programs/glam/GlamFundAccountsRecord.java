package software.sava.anchor.programs.glam;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

record GlamFundAccountsRecord(GlamAccounts glamAccounts,
                              PublicKey signerPublicKey,
                              PublicKey fundPublicKey,
                              ProgramDerivedAddress treasuryPDA,
                              AccountMeta treasuryWriteMeta,
                              ProgramDerivedAddress openFundsPDA) implements GlamFundAccounts {

  @Override
  public ProgramDerivedAddress shareClassPDA(final int shareClassId) {
    return glamAccounts.shareClassMintPDA(fundPublicKey, shareClassId);
  }
}
