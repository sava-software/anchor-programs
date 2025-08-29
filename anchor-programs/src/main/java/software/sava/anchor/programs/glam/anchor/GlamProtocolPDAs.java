package software.sava.anchor.programs.glam.anchor;

import java.util.List;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

import static java.nio.charset.StandardCharsets.US_ASCII;

public final class GlamProtocolPDAs {

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
                                                   final PublicKey claimTokenProgramAccount,
                                                   final PublicKey tokenMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      glamEscrowAccount.toByteArray(),
      claimTokenProgramAccount.toByteArray(),
      tokenMintAccount.toByteArray()
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
                                                    final PublicKey glamStateAccount) {
    return PublicKey.findProgramAddress(List.of(
      "escrow".getBytes(US_ASCII),
      glamStateAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress glamStatePDA(final PublicKey program,
                                                   final PublicKey glamSignerAccount,
                                                   final byte[] stateModelCreated) {
    return PublicKey.findProgramAddress(List.of(
      "state".getBytes(US_ASCII),
      glamSignerAccount.toByteArray(),
      stateModelCreated
    ), program);
  }

  public static ProgramDerivedAddress glamVaultPDA(final PublicKey program,
                                                   final PublicKey glamStateAccount) {
    return PublicKey.findProgramAddress(List.of(
      "vault".getBytes(US_ASCII),
      glamStateAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress inputVaultAtaPDA(final PublicKey program,
                                                       final PublicKey glamVaultAccount,
                                                       final PublicKey inputTokenProgramAccount,
                                                       final PublicKey inputMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      glamVaultAccount.toByteArray(),
      inputTokenProgramAccount.toByteArray(),
      inputMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress managerFeeAuthorityAtaPDA(final PublicKey program,
                                                                final PublicKey glamStateOwnerAccount,
                                                                final PublicKey depositTokenProgramAccount,
                                                                final PublicKey depositAssetAccount) {
    return PublicKey.findProgramAddress(List.of(
      glamStateOwnerAccount.toByteArray(),
      depositTokenProgramAccount.toByteArray(),
      depositAssetAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress metadataPDA(final PublicKey program,
                                                  final PublicKey glamStateAccount) {
    return PublicKey.findProgramAddress(List.of(
      "metadata".getBytes(US_ASCII),
      glamStateAccount.toByteArray()
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

  public static ProgramDerivedAddress openfundsMetadataPDA(final PublicKey program,
                                                           final PublicKey glamStateAccount) {
    return PublicKey.findProgramAddress(List.of(
      "metadata".getBytes(US_ASCII),
      glamStateAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress outputVaultAtaPDA(final PublicKey program,
                                                        final PublicKey glamVaultAccount,
                                                        final PublicKey outputTokenProgramAccount,
                                                        final PublicKey outputMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      glamVaultAccount.toByteArray(),
      outputTokenProgramAccount.toByteArray(),
      outputMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress protocolFeeAuthorityAtaPDA(final PublicKey program,
                                                                 final PublicKey glamConfigFeeAuthorityAccount,
                                                                 final PublicKey depositTokenProgramAccount,
                                                                 final PublicKey depositAssetAccount) {
    return PublicKey.findProgramAddress(List.of(
      glamConfigFeeAuthorityAccount.toByteArray(),
      depositTokenProgramAccount.toByteArray(),
      depositAssetAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress signerAtaPDA(final PublicKey program,
                                                   final PublicKey signerAccount,
                                                   final PublicKey claimTokenProgramAccount,
                                                   final PublicKey tokenMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      signerAccount.toByteArray(),
      claimTokenProgramAccount.toByteArray(),
      tokenMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress signerAta1PDA(final PublicKey program,
                                                    final PublicKey glamSignerAccount,
                                                    final PublicKey tokenProgramAccount,
                                                    final PublicKey assetAccount) {
    return PublicKey.findProgramAddress(List.of(
      glamSignerAccount.toByteArray(),
      tokenProgramAccount.toByteArray(),
      assetAccount.toByteArray()
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

  public static ProgramDerivedAddress vaultAtaPDA(final PublicKey program,
                                                  final PublicKey glamVaultAccount,
                                                  final PublicKey tokenProgramAccount,
                                                  final PublicKey assetAccount) {
    return PublicKey.findProgramAddress(List.of(
      glamVaultAccount.toByteArray(),
      tokenProgramAccount.toByteArray(),
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

  private GlamProtocolPDAs() {
  }
}
