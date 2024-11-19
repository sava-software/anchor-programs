package software.sava.anchor.programs.jupiter.voter;

import software.sava.anchor.programs.jupiter.JupiterAccounts;
import software.sava.anchor.programs.jupiter.governance.anchor.GovernProgram;
import software.sava.anchor.programs.jupiter.voter.anchor.LockedVoterProgram;
import software.sava.anchor.programs.jupiter.voter.anchor.types.Escrow;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;

final class JupiterVoteClientImpl implements JupiterVoteClient {

  private final SolanaAccounts solanaAccounts;
  private final JupiterAccounts accounts;

  JupiterVoteClientImpl(final SolanaAccounts solanaAccounts,
                        final JupiterAccounts accounts) {
    this.solanaAccounts = solanaAccounts;
    this.accounts = accounts;
  }

  @Override
  public SolanaAccounts solanaAccounts() {
    return solanaAccounts;
  }

  @Override
  public JupiterAccounts jupiterAccounts() {
    return accounts;
  }

  @Override
  public Instruction newVote(final PublicKey proposal,
                             final PublicKey payer,
                             final PublicKey voter) {
    final var voteKey = accounts.deriveVote(proposal, voter).publicKey();
    return GovernProgram.newVote(
        accounts.invokedGovProgram(),
        proposal,
        voteKey,
        payer,
        solanaAccounts.systemProgram(),
        voter
    );
  }

  @Override
  public Instruction castVote(final PublicKey voter,
                              final PublicKey voteDelegate,
                              final PublicKey proposal,
                              final int side) {
    final var lockerKey = accounts.deriveJupLocker().publicKey();
    final var escrowKey = accounts.deriveEscrow(lockerKey, voter).publicKey();
    final var governorKey = accounts.deriveGovernor().publicKey();
    final var voteKey = accounts.deriveVote(proposal, voter).publicKey();
    return LockedVoterProgram.castVote(
        accounts.invokedVoteProgram(),
        lockerKey,
        escrowKey,
        voteDelegate,
        proposal,
        voteKey,
        governorKey,
        accounts.govProgram(),
        side
    );
  }

  @Override
  public Instruction setVoteDelegate(final PublicKey escrowOwnerKey, final PublicKey newDelegate) {
    final var lockerKey = accounts.deriveJupLocker().publicKey();
    final var escrowKey = accounts.deriveEscrow(lockerKey, escrowOwnerKey).publicKey();
    return LockedVoterProgram.setVoteDelegate(
        accounts.invokedVoteProgram(),
        escrowKey,
        escrowOwnerKey,
        newDelegate
    );
  }

  @Override
  public Instruction increaseLockedAmount(final Escrow escrow,
                                          final PublicKey payerKey,
                                          final PublicKey sourceTokensKey,
                                          final long amount) {
    return LockedVoterProgram.increaseLockedAmount(
        accounts.invokedVoteProgram(),
        escrow.locker(),
        escrow._address(),
        escrow.tokens(),
        payerKey,
        sourceTokensKey,
        solanaAccounts.tokenProgram(),
        amount
    );
  }

  @Override
  public Instruction extendLockDuration(final Escrow escrow, final long duration) {
    return LockedVoterProgram.extendLockDuration(
        accounts.invokedVoteProgram(),
        escrow.locker(),
        escrow._address(),
        escrow.owner(),
        duration
    );
  }

  @Override
  public Instruction toggleMaxLock(final Escrow escrow, final boolean maxLock) {
    return LockedVoterProgram.toggleMaxLock(
        accounts.invokedVoteProgram(),
        escrow.locker(),
        escrow._address(),
        escrow.owner(),
        maxLock
    );
  }

  @Override
  public Instruction withdraw(final Escrow escrow,
                              final PublicKey payerKey,
                              final PublicKey destinationTokensKey) {
    return LockedVoterProgram.withdraw(
        accounts.invokedVoteProgram(),
        escrow.locker(),
        escrow._address(),
        escrow.owner(),
        escrow.tokens(),
        destinationTokensKey,
        payerKey,
        solanaAccounts.tokenProgram()
    );
  }

  @Override
  public Instruction openPartialUnstaking(final Escrow escrow,
                                          final PublicKey partialUnstakeKey,
                                          final long amount,
                                          final String memo) {
    return LockedVoterProgram.openPartialUnstaking(
        accounts.invokedVoteProgram(),
        escrow.locker(),
        escrow._address(),
        partialUnstakeKey,
        escrow.owner(),
        solanaAccounts.systemProgram(),
        amount,
        memo
    );
  }

  @Override
  public Instruction mergePartialUnstaking(final Escrow escrow, final PublicKey partialUnstakeKey) {
    return LockedVoterProgram.mergePartialUnstaking(
        accounts.invokedVoteProgram(),
        escrow.locker(),
        escrow._address(),
        partialUnstakeKey,
        escrow.owner()
    );
  }

  @Override
  public Instruction withdrawPartialUnstaking(final Escrow escrow,
                                              final PublicKey partialUnstakeKey,
                                              final PublicKey payerKey,
                                              final PublicKey destinationTokensKey) {
    return LockedVoterProgram.withdrawPartialUnstaking(
        accounts.invokedVoteProgram(),
        escrow.locker(),
        escrow._address(),
        partialUnstakeKey,
        escrow.owner(),
        escrow.tokens(),
        destinationTokensKey,
        payerKey,
        solanaAccounts.tokenProgram()
    );
  }
}
