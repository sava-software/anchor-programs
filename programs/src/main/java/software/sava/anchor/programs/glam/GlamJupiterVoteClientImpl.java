package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamProgram;
import software.sava.anchor.programs.jupiter.JupiterAccounts;
import software.sava.anchor.programs.jupiter.voter.BaseJupiterVoteClient;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;

final class GlamJupiterVoteClientImpl extends BaseJupiterVoteClient implements GlamJupiterVoteClient {

  private final GlamAccounts glamAccounts;
  private final PublicKey glamKey;

  GlamJupiterVoteClientImpl(final SolanaAccounts solanaAccounts,
                            final JupiterAccounts jupiterAccounts,
                            final GlamAccounts glamAccounts,
                            final PublicKey glamKey,
                            final PublicKey treasuryKey) {
    super(solanaAccounts, jupiterAccounts, treasuryKey);
    this.glamAccounts = glamAccounts;
    this.glamKey = glamKey;
  }

  @Override
  public PublicKey glamKey() {
    return glamKey;
  }

  @Override
  public Instruction newEscrow(final PublicKey escrowOwnerKey,
                               final PublicKey escrowKey,
                               final PublicKey payer) {
    return GlamProgram.initLockedVoterEscrow(
        glamAccounts.invokedProgram(),
        solanaAccounts,
        glamKey,
        escrowOwnerKey,
        payer,
        jupiterAccounts.lockerKey(),
        escrowKey,
        jupiterAccounts.voteProgram()
    );
  }

  @Override
  public Instruction newVote(final PublicKey proposal,
                             final PublicKey voteKey,
                             final PublicKey payer,
                             final PublicKey voter) {
    if (!voter.equals(escrowOwnerKey)) {
      throw new IllegalStateException("Treasury must correspond to the GLAM fund account.");
    }
    return GlamProgram.newVote(
        glamAccounts.invokedProgram(),
        solanaAccounts,
        glamKey,
        escrowOwnerKey,
        payer,
        proposal,
        voteKey,
        jupiterAccounts.govProgram()
    );
  }

  @Override
  public Instruction castVote(final PublicKey escrowKey,
                              final PublicKey voteDelegate,
                              final PublicKey proposal,
                              final PublicKey voteKey,
                              final int side) {
    return GlamProgram.castVote(
        glamAccounts.invokedProgram(),
        glamKey,
        escrowOwnerKey,
        voteDelegate,
        jupiterAccounts.lockerKey(),
        escrowKey,
        proposal,
        voteKey,
        jupiterAccounts.governorKey(),
        jupiterAccounts.voteProgram(),
        jupiterAccounts.govProgram(),
        side
    );
  }

  @Override
  public Instruction increaseLockedAmount(final PublicKey escrowKey,
                                          final PublicKey escrowTokensKey,
                                          final PublicKey payerKey,
                                          final PublicKey sourceTokensKey,
                                          final long amount) {
    return GlamProgram.increaseLockedAmount(
        glamAccounts.invokedProgram(),
        solanaAccounts,
        glamKey,
        escrowOwnerKey,
        payerKey,
        jupiterAccounts.lockerKey(),
        escrowTokensKey,
        sourceTokensKey,
        escrowKey,
        jupiterAccounts.voteProgram(),
        amount
    );
  }

  @Override
  public Instruction setVoteDelegate(final PublicKey escrowOwnerKey,
                                     final PublicKey escrowKey,
                                     final PublicKey newDelegate) {
    throw new UnsupportedOperationException("TODO: setVoteDelegate");
  }

  @Override
  public Instruction extendLockDuration(final PublicKey lockerKey,
                                        final PublicKey escrowKey,
                                        final PublicKey escrowOwnerKey,
                                        final long duration) {
    throw new UnsupportedOperationException("TODO: extendLockDuration");
  }

  @Override
  public Instruction toggleMaxLock(final PublicKey lockerKey,
                                   final PublicKey escrowKey,
                                   final PublicKey escrowOwnerKey,
                                   final boolean maxLock) {
    throw new UnsupportedOperationException("TODO: toggleMaxLock");
  }

  @Override
  public Instruction withdraw(final PublicKey lockerKey,
                              final PublicKey escrowKey,
                              final PublicKey escrowOwnerKey,
                              final PublicKey escrowTokensKey,
                              final PublicKey payerKey,
                              final PublicKey destinationTokensKey) {
    throw new UnsupportedOperationException("TODO: withdraw");
  }

  @Override
  public Instruction openPartialUnstaking(final PublicKey lockerKey,
                                          final PublicKey escrowKey,
                                          final PublicKey escrowOwnerKey,
                                          final PublicKey partialUnstakeKey,
                                          final long amount,
                                          final String memo) {
    throw new UnsupportedOperationException("TODO: openPartialUnstaking");
  }

  @Override
  public Instruction mergePartialUnstaking(final PublicKey lockerKey,
                                           final PublicKey escrowKey,
                                           final PublicKey escrowOwnerKey,
                                           final PublicKey partialUnstakeKey) {
    throw new UnsupportedOperationException("TODO: mergePartialUnstaking");
  }

  @Override
  public Instruction withdrawPartialUnstaking(final PublicKey lockerKey,
                                              final PublicKey escrowKey,
                                              final PublicKey escrowOwnerKey,
                                              final PublicKey escrowTokensKey,
                                              final PublicKey partialUnstakeKey,
                                              final PublicKey payerKey,
                                              final PublicKey destinationTokensKey) {
    throw new UnsupportedOperationException("TODO: withdrawPartialUnstaking");
  }
}
