package software.sava.anchor.programs.glam_v0.anchor;

import java.util.List;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;

import static java.nio.charset.StandardCharsets.US_ASCII;

public final class GlamPDAs {

  public static ProgramDerivedAddress dstAccountPolicyPDA(final PublicKey program,
                                                          final PublicKey dstAccountAccount) {
    return PublicKey.findProgramAddress(List.of(
      "account-policy".getBytes(US_ASCII),
      dstAccountAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress extraAccountMetaListPDA(final PublicKey program,
                                                              final PublicKey shareClassMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      "extra-account-metas".getBytes(US_ASCII),
      shareClassMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress extraAccountMetaList1PDA(final PublicKey program,
                                                               final PublicKey mintAccount) {
    return PublicKey.findProgramAddress(List.of(
      "extra-account-metas".getBytes(US_ASCII),
      mintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress fromAtaPDA(final PublicKey program,
                                                 final PublicKey fromAccount,
                                                 final PublicKey token2022ProgramAccount,
                                                 final PublicKey shareClassMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      fromAccount.toByteArray(),
      token2022ProgramAccount.toByteArray(),
      shareClassMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress inputSignerAtaPDA(final PublicKey program,
                                                        final PublicKey signerAccount,
                                                        final PublicKey inputTokenProgramAccount,
                                                        final PublicKey inputMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      signerAccount.toByteArray(),
      inputTokenProgramAccount.toByteArray(),
      inputMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress metadataPDA(final PublicKey program,
                                                  final PublicKey stateAccount) {
    return PublicKey.findProgramAddress(List.of(
      "openfunds".getBytes(US_ASCII),
      stateAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress mintToPDA(final PublicKey program,
                                                final PublicKey vaultAccount,
                                                final SolanaAccounts solanaAccounts,
                                                final PublicKey msolMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      vaultAccount.toByteArray(),
      solanaAccounts.tokenProgram().toByteArray(),
      msolMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress mintTo1PDA(final PublicKey program,
                                                 final PublicKey recipientAccount,
                                                 final PublicKey token2022ProgramAccount,
                                                 final PublicKey shareClassMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      recipientAccount.toByteArray(),
      token2022ProgramAccount.toByteArray(),
      shareClassMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress mintTo2PDA(final PublicKey program,
                                                 final PublicKey vaultAccount,
                                                 final SolanaAccounts solanaAccounts,
                                                 final PublicKey poolMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      vaultAccount.toByteArray(),
      solanaAccounts.tokenProgram().toByteArray(),
      poolMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress outputVaultAtaPDA(final PublicKey program,
                                                        final PublicKey vaultAccount,
                                                        final PublicKey outputTokenProgramAccount,
                                                        final PublicKey outputMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      vaultAccount.toByteArray(),
      outputTokenProgramAccount.toByteArray(),
      outputMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress signerAtaPDA(final PublicKey program,
                                                   final PublicKey signerAccount,
                                                   final PublicKey tokenProgramAccount,
                                                   final PublicKey assetAccount) {
    return PublicKey.findProgramAddress(List.of(
      signerAccount.toByteArray(),
      tokenProgramAccount.toByteArray(),
      assetAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress signerPolicyPDA(final PublicKey program,
                                                      final PublicKey signerShareAtaAccount) {
    return PublicKey.findProgramAddress(List.of(
      "account-policy".getBytes(US_ASCII),
      signerShareAtaAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress signerShareAtaPDA(final PublicKey program,
                                                        final PublicKey signerAccount,
                                                        final PublicKey token2022ProgramAccount,
                                                        final PublicKey shareClassAccount) {
    return PublicKey.findProgramAddress(List.of(
      signerAccount.toByteArray(),
      token2022ProgramAccount.toByteArray(),
      shareClassAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress srcAccountPolicyPDA(final PublicKey program,
                                                          final PublicKey srcAccountAccount) {
    return PublicKey.findProgramAddress(List.of(
      "account-policy".getBytes(US_ASCII),
      srcAccountAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress statePDA(final PublicKey program,
                                               final PublicKey signerAccount,
                                               final byte[] stateModelCreated) {
    return PublicKey.findProgramAddress(List.of(
      "fund".getBytes(US_ASCII),
      signerAccount.toByteArray(),
      stateModelCreated
    ), program);
  }

  public static ProgramDerivedAddress toAtaPDA(final PublicKey program,
                                               final PublicKey toAccount,
                                               final PublicKey token2022ProgramAccount,
                                               final PublicKey shareClassMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      toAccount.toByteArray(),
      token2022ProgramAccount.toByteArray(),
      shareClassMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress vaultPDA(final PublicKey program,
                                               final PublicKey stateAccount) {
    return PublicKey.findProgramAddress(List.of(
      "treasury".getBytes(US_ASCII),
      stateAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress vaultAtaPDA(final PublicKey program,
                                                  final PublicKey vaultAccount,
                                                  final PublicKey tokenProgramAccount,
                                                  final PublicKey assetAccount) {
    return PublicKey.findProgramAddress(List.of(
      vaultAccount.toByteArray(),
      tokenProgramAccount.toByteArray(),
      assetAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress vaultWsolAtaPDA(final PublicKey program,
                                                      final PublicKey vaultAccount,
                                                      final SolanaAccounts solanaAccounts,
                                                      final PublicKey wsolMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      vaultAccount.toByteArray(),
      solanaAccounts.tokenProgram().toByteArray(),
      wsolMintAccount.toByteArray()
    ), program);
  }

  private GlamPDAs() {
  }
}
