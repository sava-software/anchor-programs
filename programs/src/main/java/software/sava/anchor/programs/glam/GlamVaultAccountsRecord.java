package software.sava.anchor.programs.glam;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

public record GlamVaultAccountsRecord(GlamAccounts glamAccounts,
                                      AccountMeta readFeePayer,
                                      AccountMeta writeFeePayer,
                                      AccountMeta readGlamAccount,
                                      AccountMeta writeGlamAccount,
                                      ProgramDerivedAddress vaultPDA,
                                      AccountMeta readVault,
                                      AccountMeta writeVault,
                                      ProgramDerivedAddress metadataPDA,
                                      AccountMeta readMetadata,
                                      AccountMeta writeMetadata) implements GlamVaultAccounts {

  @Override
  public PublicKey feePayer() {
    return readFeePayer.publicKey();
  }

  @Override
  public PublicKey glamPublicKey() {
    return readGlamAccount.publicKey();
  }

  @Override
  public ProgramDerivedAddress shareClassPDA(final int shareClassId) {
    return glamAccounts.mintPDA(glamPublicKey(), shareClassId);
  }

  @Override
  public AccountMeta accountMeta(final DynamicGlamAccount dynamicGlamAccount, final boolean write) {
    return switch (dynamicGlamAccount) {
      case SIGNER ->  write ? writeFeePayer : readFeePayer;
      case STATE -> write ? writeGlamAccount : readGlamAccount;
      case VAULT -> write ? writeVault : readVault;
      case METADATA -> write ? writeMetadata : readMetadata;
    };
  }
}
