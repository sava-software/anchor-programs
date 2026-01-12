package software.sava.anchor.programs.switchboard.on_demand.anchor;

import java.util.List;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;

import static java.nio.charset.StandardCharsets.US_ASCII;

public final class SbOnDemandPDAs {

  public static ProgramDerivedAddress escrowPDA(final PublicKey program,
                                                final PublicKey payerAccount,
                                                final SolanaAccounts solanaAccounts,
                                                final PublicKey wsolMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      payerAccount.toByteArray(),
      solanaAccounts.tokenProgram().toByteArray(),
      wsolMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress oraclePDA(final PublicKey program,
                                                final byte[] paramsQueue,
                                                final byte[] paramsSourceOracleKey) {
    return PublicKey.findProgramAddress(List.of(
      "Oracle".getBytes(US_ASCII),
      paramsQueue,
      paramsSourceOracleKey
    ), program);
  }

  public static ProgramDerivedAddress oracleStatsPDA(final PublicKey program,
                                                     final PublicKey oracleAccount) {
    return PublicKey.findProgramAddress(List.of(
      "OracleStats".getBytes(US_ASCII),
      oracleAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress queuePDA(final PublicKey program,
                                               final byte[] paramsSourceQueueKey) {
    return PublicKey.findProgramAddress(List.of(
      "Queue".getBytes(US_ASCII),
      paramsSourceQueueKey
    ), program);
  }

  public static ProgramDerivedAddress queueEscrowPDA(final PublicKey program,
                                                     final PublicKey queueAccount,
                                                     final SolanaAccounts solanaAccounts,
                                                     final PublicKey nativeMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      queueAccount.toByteArray(),
      solanaAccounts.tokenProgram().toByteArray(),
      nativeMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress rewardEscrowPDA(final PublicKey program,
                                                      final PublicKey pullFeedAccount,
                                                      final SolanaAccounts solanaAccounts,
                                                      final PublicKey wrappedSolMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      pullFeedAccount.toByteArray(),
      solanaAccounts.tokenProgram().toByteArray(),
      wrappedSolMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress rewardEscrow1PDA(final PublicKey program,
                                                       final PublicKey randomnessAccount,
                                                       final SolanaAccounts solanaAccounts,
                                                       final PublicKey wrappedSolMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      randomnessAccount.toByteArray(),
      solanaAccounts.tokenProgram().toByteArray(),
      wrappedSolMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress rewardVaultPDA(final PublicKey program,
                                                     final PublicKey vaultAccount,
                                                     final SolanaAccounts solanaAccounts,
                                                     final PublicKey switchMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      vaultAccount.toByteArray(),
      solanaAccounts.tokenProgram().toByteArray(),
      switchMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress statePDA(final PublicKey program) {
    return PublicKey.findProgramAddress(List.of(
      "STATE".getBytes(US_ASCII)
    ), program);
  }

  public static ProgramDerivedAddress statsPDA(final PublicKey program,
                                               final PublicKey oracleAccount) {
    return PublicKey.findProgramAddress(List.of(
      "OracleRandomnessStats".getBytes(US_ASCII),
      oracleAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress subsidyVaultPDA(final PublicKey program,
                                                      final PublicKey programStateAccount,
                                                      final SolanaAccounts solanaAccounts,
                                                      final PublicKey switchMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      programStateAccount.toByteArray(),
      solanaAccounts.tokenProgram().toByteArray(),
      switchMintAccount.toByteArray()
    ), program);
  }

  private SbOnDemandPDAs() {
  }
}
