package software.sava.anchor.programs.glam.mint.anchor;

import java.util.List;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

import static java.nio.charset.StandardCharsets.US_ASCII;

public final class GlamMintPDAs {

  public static ProgramDerivedAddress claimUserAtaPDA(final PublicKey program,
                                                      final PublicKey claimUserAccount,
                                                      final PublicKey claimTokenProgramAccount,
                                                      final PublicKey claimTokenMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      claimUserAccount.toByteArray(),
      claimTokenProgramAccount.toByteArray(),
      claimTokenMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress escrowAssetAtaPDA(final PublicKey program,
                                                        final PublicKey glamEscrowAccount,
                                                        final PublicKey depositTokenProgramAccount,
                                                        final PublicKey assetAccount) {
    return PublicKey.findProgramAddress(List.of(
      glamEscrowAccount.toByteArray(),
      depositTokenProgramAccount.toByteArray(),
      assetAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress escrowAtaPDA(final PublicKey program,
                                                   final PublicKey glamEscrowAccount,
                                                   final PublicKey recoverTokenProgramAccount,
                                                   final PublicKey recoverTokenMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      glamEscrowAccount.toByteArray(),
      recoverTokenProgramAccount.toByteArray(),
      recoverTokenMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress escrowAta1PDA(final PublicKey program,
                                                    final PublicKey glamEscrowAccount,
                                                    final PublicKey claimTokenProgramAccount,
                                                    final PublicKey claimTokenMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      glamEscrowAccount.toByteArray(),
      claimTokenProgramAccount.toByteArray(),
      claimTokenMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress escrowDepositAtaPDA(final PublicKey program,
                                                          final PublicKey glamEscrowAccount,
                                                          final PublicKey depositTokenProgramAccount,
                                                          final PublicKey depositAssetAccount) {
    return PublicKey.findProgramAddress(List.of(
      glamEscrowAccount.toByteArray(),
      depositTokenProgramAccount.toByteArray(),
      depositAssetAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress escrowMintAtaPDA(final PublicKey program,
                                                       final PublicKey glamEscrowAccount,
                                                       final PublicKey token2022ProgramAccount,
                                                       final PublicKey glamMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      glamEscrowAccount.toByteArray(),
      token2022ProgramAccount.toByteArray(),
      glamMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress fromAtaPDA(final PublicKey program,
                                                 final PublicKey fromAccount,
                                                 final PublicKey token2022ProgramAccount,
                                                 final PublicKey glamMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      fromAccount.toByteArray(),
      token2022ProgramAccount.toByteArray(),
      glamMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress glamConfigPDA(final PublicKey program) {
    return PublicKey.findProgramAddress(List.of(
      "global-config".getBytes(US_ASCII)
    ), program);
  }

  public static ProgramDerivedAddress glamEscrowPDA(final PublicKey program,
                                                    final PublicKey glamMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      "escrow".getBytes(US_ASCII),
      glamMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress glamVaultPDA(final PublicKey program,
                                                   final PublicKey glamStateAccount) {
    return PublicKey.findProgramAddress(List.of(
      "vault".getBytes(US_ASCII),
      glamStateAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress integrationAuthorityPDA(final PublicKey program) {
    return PublicKey.findProgramAddress(List.of(
      "integration-authority".getBytes(US_ASCII)
    ), program);
  }

  public static ProgramDerivedAddress managerFeeAuthorityAtaPDA(final PublicKey program,
                                                                final PublicKey managerFeeAuthorityAccount,
                                                                final PublicKey depositTokenProgramAccount,
                                                                final PublicKey depositAssetAccount) {
    return PublicKey.findProgramAddress(List.of(
      managerFeeAuthorityAccount.toByteArray(),
      depositTokenProgramAccount.toByteArray(),
      depositAssetAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress mintToPDA(final PublicKey program,
                                                final PublicKey recipientAccount,
                                                final PublicKey token2022ProgramAccount,
                                                final PublicKey glamMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      recipientAccount.toByteArray(),
      token2022ProgramAccount.toByteArray(),
      glamMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress protocolFeeAuthorityAtaPDA(final PublicKey program,
                                                                 final PublicKey protocolFeeAuthorityAccount,
                                                                 final PublicKey depositTokenProgramAccount,
                                                                 final PublicKey depositAssetAccount) {
    return PublicKey.findProgramAddress(List.of(
      protocolFeeAuthorityAccount.toByteArray(),
      depositTokenProgramAccount.toByteArray(),
      depositAssetAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress requestQueuePDA(final PublicKey program,
                                                      final PublicKey glamMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      "request-queue".getBytes(US_ASCII),
      glamMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress requestQueue1PDA(final PublicKey program,
                                                       final PublicKey newMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      "request-queue".getBytes(US_ASCII),
      newMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress signerDepositAtaPDA(final PublicKey program,
                                                          final PublicKey signerAccount,
                                                          final PublicKey depositTokenProgramAccount,
                                                          final PublicKey depositAssetAccount) {
    return PublicKey.findProgramAddress(List.of(
      signerAccount.toByteArray(),
      depositTokenProgramAccount.toByteArray(),
      depositAssetAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress signerMintAtaPDA(final PublicKey program,
                                                       final PublicKey signerAccount,
                                                       final PublicKey token2022ProgramAccount,
                                                       final PublicKey glamMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      signerAccount.toByteArray(),
      token2022ProgramAccount.toByteArray(),
      glamMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress toAtaPDA(final PublicKey program,
                                               final PublicKey toAccount,
                                               final PublicKey token2022ProgramAccount,
                                               final PublicKey glamMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      toAccount.toByteArray(),
      token2022ProgramAccount.toByteArray(),
      glamMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress userAtaPDA(final PublicKey program,
                                                 final PublicKey userAccount,
                                                 final PublicKey recoverTokenProgramAccount,
                                                 final PublicKey recoverTokenMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      userAccount.toByteArray(),
      recoverTokenProgramAccount.toByteArray(),
      recoverTokenMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress vaultAssetAtaPDA(final PublicKey program,
                                                       final PublicKey glamVaultAccount,
                                                       final PublicKey depositTokenProgramAccount,
                                                       final PublicKey assetAccount) {
    return PublicKey.findProgramAddress(List.of(
      glamVaultAccount.toByteArray(),
      depositTokenProgramAccount.toByteArray(),
      assetAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress vaultDepositAtaPDA(final PublicKey program,
                                                         final PublicKey glamVaultAccount,
                                                         final PublicKey depositTokenProgramAccount,
                                                         final PublicKey depositAssetAccount) {
    return PublicKey.findProgramAddress(List.of(
      glamVaultAccount.toByteArray(),
      depositTokenProgramAccount.toByteArray(),
      depositAssetAccount.toByteArray()
    ), program);
  }

  private GlamMintPDAs() {
  }
}
