package software.sava.anchor.programs.jupiter.voter;

import software.sava.anchor.programs.jupiter.JupiterAccounts;
import software.sava.anchor.programs.jupiter.governance.anchor.GovernProgram;
import software.sava.anchor.programs.jupiter.governance.anchor.types.GovernanceParameters;
import software.sava.anchor.programs.jupiter.governance.anchor.types.ProposalInstruction;
import software.sava.anchor.programs.jupiter.voter.anchor.LockedVoterProgram;
import software.sava.anchor.programs.jupiter.voter.anchor.types.LockerParams;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;

final class JupiterVoteClientImpl extends BaseJupiterVoteClient implements JupiterVoteClient {

  JupiterVoteClientImpl(final SolanaAccounts solanaAccounts,
                        final JupiterAccounts accounts,
                        final PublicKey escrowOwnerKey) {
    super(solanaAccounts, accounts, escrowOwnerKey);
  }

  @Override
  public Instruction newEscrow(final PublicKey escrowOwnerKey,
                               final PublicKey escrowKey,
                               final PublicKey payer) {
    return LockedVoterProgram.newEscrow(
        jupiterAccounts.invokedGovProgram(),
        jupiterAccounts.lockerKey(),
        escrowKey,
        escrowOwnerKey,
        payer,
        solanaAccounts.systemProgram()
    );
  }

  @Override
  public Instruction newVote(final PublicKey proposal,
                             final PublicKey voteKey,
                             final PublicKey payer,
                             final PublicKey voter) {
    return GovernProgram.newVote(
        jupiterAccounts.invokedGovProgram(),
        proposal,
        voteKey,
        payer,
        solanaAccounts.systemProgram(),
        voter
    );
  }

  @Override
  public Instruction castVote(final PublicKey escrowKey,
                              final PublicKey voteDelegate,
                              final PublicKey proposal,
                              final PublicKey voteKey,
                              final int side) {
    return LockedVoterProgram.castVote(
        jupiterAccounts.invokedVoteProgram(),
        jupiterAccounts.lockerKey(),
        escrowKey,
        voteDelegate,
        proposal,
        voteKey,
        jupiterAccounts.governorKey(),
        jupiterAccounts.govProgram(),
        side
    );
  }

  @Override
  public Instruction setVoteDelegate(final PublicKey escrowOwnerKey,
                                     final PublicKey escrowKey,
                                     final PublicKey newDelegate) {
    return LockedVoterProgram.setVoteDelegate(
        jupiterAccounts.invokedVoteProgram(),
        escrowKey,
        escrowOwnerKey,
        newDelegate
    );
  }

  @Override
  public Instruction increaseLockedAmount(final PublicKey escrowKey,
                                          final PublicKey escrowTokensKey,
                                          final PublicKey payerKey,
                                          final PublicKey sourceTokensKey,
                                          final long amount) {
    return LockedVoterProgram.increaseLockedAmount(
        jupiterAccounts.invokedVoteProgram(),
        jupiterAccounts.lockerKey(),
        escrowKey,
        escrowTokensKey,
        payerKey,
        sourceTokensKey,
        solanaAccounts.tokenProgram(),
        amount
    );
  }

  @Override
  public Instruction extendLockDuration(final PublicKey lockerKey,
                                        final PublicKey escrowKey,
                                        final PublicKey escrowOwnerKey,
                                        final long duration) {
    return LockedVoterProgram.extendLockDuration(
        jupiterAccounts.invokedVoteProgram(),
        lockerKey,
        escrowKey,
        escrowOwnerKey,
        duration
    );
  }

  @Override
  public Instruction toggleMaxLock(final PublicKey lockerKey,
                                   final PublicKey escrowKey,
                                   final PublicKey escrowOwnerKey,
                                   final boolean maxLock) {
    return LockedVoterProgram.toggleMaxLock(
        jupiterAccounts.invokedVoteProgram(),
        lockerKey,
        escrowKey,
        escrowOwnerKey,
        maxLock
    );
  }

  @Override
  public Instruction withdraw(final PublicKey lockerKey,
                              final PublicKey escrowKey,
                              final PublicKey escrowOwnerKey,
                              final PublicKey escrowTokensKey,
                              final PublicKey payerKey,
                              final PublicKey destinationTokensKey) {
    return LockedVoterProgram.withdraw(
        jupiterAccounts.invokedVoteProgram(),
        lockerKey,
        escrowKey,
        escrowOwnerKey,
        escrowTokensKey,
        destinationTokensKey,
        payerKey,
        solanaAccounts.tokenProgram()
    );
  }

  @Override
  public Instruction openPartialUnstaking(final PublicKey lockerKey,
                                          final PublicKey escrowKey,
                                          final PublicKey escrowOwnerKey,
                                          final PublicKey partialUnstakeKey,
                                          final long amount,
                                          final String memo) {
    return LockedVoterProgram.openPartialUnstaking(
        jupiterAccounts.invokedVoteProgram(),
        lockerKey,
        escrowKey,
        partialUnstakeKey,
        escrowOwnerKey,
        solanaAccounts.systemProgram(),
        amount,
        memo
    );
  }

  @Override
  public Instruction mergePartialUnstaking(final PublicKey lockerKey,
                                           final PublicKey escrowKey,
                                           final PublicKey escrowOwnerKey,
                                           final PublicKey partialUnstakeKey) {
    return LockedVoterProgram.mergePartialUnstaking(
        jupiterAccounts.invokedVoteProgram(),
        lockerKey,
        escrowKey,
        partialUnstakeKey,
        escrowOwnerKey
    );
  }

  @Override
  public Instruction withdrawPartialUnstaking(final PublicKey lockerKey,
                                              final PublicKey escrowKey,
                                              final PublicKey escrowOwnerKey,
                                              final PublicKey escrowTokensKey,
                                              final PublicKey partialUnstakeKey,
                                              final PublicKey payerKey,
                                              final PublicKey destinationTokensKey) {
    return LockedVoterProgram.withdrawPartialUnstaking(
        jupiterAccounts.invokedVoteProgram(),
        lockerKey,
        escrowKey,
        partialUnstakeKey,
        escrowOwnerKey,
        escrowTokensKey,
        destinationTokensKey,
        payerKey,
        solanaAccounts.tokenProgram()
    );
  }

  @Override
  public Instruction newLocker(final PublicKey baseKey,
                               final PublicKey lockerKey,
                               final PublicKey tokenMintKey,
                               final PublicKey governorKey,
                               final PublicKey payerKey,
                               final LockerParams params) {
    return LockedVoterProgram.newLocker(
        jupiterAccounts.invokedVoteProgram(),
        baseKey,
        lockerKey,
        tokenMintKey,
        governorKey,
        payerKey,
        solanaAccounts.systemProgram(),
        params
    );
  }

  @Override
  public Instruction activateProposal(final PublicKey proposalKey, final PublicKey smartWalletKey) {
    return LockedVoterProgram.activateProposal(
        jupiterAccounts.invokedVoteProgram(),
        jupiterAccounts.lockerKey(),
        jupiterAccounts.governorKey(),
        proposalKey,
        jupiterAccounts.govProgram(),
        smartWalletKey
    );

  }

  @Override
  public Instruction setLockerParams(final PublicKey smartWalletKey, final LockerParams params) {
    return LockedVoterProgram.setLockerParams(
        jupiterAccounts.invokedVoteProgram(),
        jupiterAccounts.lockerKey(),
        jupiterAccounts.governorKey(),
        smartWalletKey,
        params);
  }

  @Override
  public Instruction createGovernor(final PublicKey baseKey,
                                    final PublicKey governorKey,
                                    final PublicKey smartWalletKey,
                                    final PublicKey payerKey,
                                    final PublicKey locker,
                                    final GovernanceParameters params) {
    return GovernProgram.createGovernor(
        jupiterAccounts.invokedGovProgram(),
        baseKey,
        governorKey,
        smartWalletKey,
        payerKey,
        solanaAccounts.systemProgram(),
        locker,
        params
    );
  }

  @Override
  public Instruction createProposal(final PublicKey governorKey,
                                    final PublicKey proposalKey,
                                    final PublicKey smartWalletKey,
                                    final PublicKey proposerKey,
                                    final PublicKey payerKey,
                                    final PublicKey eventAuthorityKey,
                                    final int proposalType,
                                    final int maxOption,
                                    final ProposalInstruction[] instructions) {
    return GovernProgram.createProposal(
        jupiterAccounts.invokedGovProgram(),
        governorKey,
        proposalKey,
        smartWalletKey,
        proposerKey,
        payerKey,
        solanaAccounts.systemProgram(),
        eventAuthorityKey,
        jupiterAccounts.govProgram(),
        proposalType,
        maxOption,
        instructions
    );
  }
}
