package software.sava.anchor.programs.glam.anchor;

import java.util.List;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;

import static java.nio.charset.StandardCharsets.US_ASCII;

public final class GlamPDAs {

  public static ProgramDerivedAddress fundPDA(final PublicKey program,
                                              final PublicKey managerAccount,
                                              final byte[] fundModelCreated) {
    return PublicKey.findProgramAddress(List.of(
      "fund".getBytes(US_ASCII),
      managerAccount.toByteArray(),
      fundModelCreated
    ), program);
  }

  public static ProgramDerivedAddress inputSignerAtaPDA(final PublicKey program,
                                                        final PublicKey signerAccount,
                                                        final SolanaAccounts solanaAccounts,
                                                        final PublicKey inputMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      signerAccount.toByteArray(),
      solanaAccounts.tokenProgram().toByteArray(),
      inputMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress mintToPDA(final PublicKey program,
                                                final PublicKey treasuryAccount,
                                                final SolanaAccounts solanaAccounts,
                                                final PublicKey msolMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      treasuryAccount.toByteArray(),
      solanaAccounts.tokenProgram().toByteArray(),
      msolMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress mintTo1PDA(final PublicKey program,
                                                 final PublicKey treasuryAccount,
                                                 final SolanaAccounts solanaAccounts,
                                                 final PublicKey poolMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      treasuryAccount.toByteArray(),
      solanaAccounts.tokenProgram().toByteArray(),
      poolMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress openfundsPDA(final PublicKey program,
                                                   final PublicKey fundAccount) {
    return PublicKey.findProgramAddress(List.of(
      "openfunds".getBytes(US_ASCII),
      fundAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress outputTreasuryAtaPDA(final PublicKey program,
                                                           final PublicKey treasuryAccount,
                                                           final SolanaAccounts solanaAccounts,
                                                           final PublicKey outputMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      treasuryAccount.toByteArray(),
      solanaAccounts.tokenProgram().toByteArray(),
      outputMintAccount.toByteArray()
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

  public static ProgramDerivedAddress treasuryPDA(final PublicKey program,
                                                  final PublicKey fundAccount) {
    return PublicKey.findProgramAddress(List.of(
      "treasury".getBytes(US_ASCII),
      fundAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress treasuryWsolAtaPDA(final PublicKey program,
                                                         final PublicKey treasuryAccount,
                                                         final SolanaAccounts solanaAccounts,
                                                         final PublicKey wsolMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      treasuryAccount.toByteArray(),
      solanaAccounts.tokenProgram().toByteArray(),
      wsolMintAccount.toByteArray()
    ), program);
  }

  private GlamPDAs() {
  }
}