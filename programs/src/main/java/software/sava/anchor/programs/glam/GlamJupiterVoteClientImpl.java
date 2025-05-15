package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamProtocolProgram;
import software.sava.anchor.programs.jupiter.JupiterAccounts;
import software.sava.anchor.programs.jupiter.governance.anchor.types.GovernanceParameters;
import software.sava.anchor.programs.jupiter.governance.anchor.types.ProposalInstruction;
import software.sava.anchor.programs.jupiter.voter.BaseJupiterVoteClient;
import software.sava.anchor.programs.jupiter.voter.anchor.types.LockerParams;
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
                            final PublicKey glamVaultKey,
                            final PublicKey feePayer) {
    super(solanaAccounts, jupiterAccounts, glamVaultKey, feePayer);
    this.glamAccounts = glamAccounts;
    this.glamKey = glamKey;
  }

  @Override
  public GlamAccounts glamAccounts() {
    return glamAccounts;
  }

  @Override
  public PublicKey glamKey() {
    return glamKey;
  }

  @Override
  public Instruction newEscrow(final PublicKey escrowOwnerKey,
                               final PublicKey escrowKey,
                               final PublicKey payer) {
    return GlamProtocolProgram.jupiterVoteNewEscrow(
        glamAccounts.invokedProgram(),
        solanaAccounts,
        glamKey,
        escrowOwnerKey,
        payer,
        jupiterAccounts.voteProgram(),
        jupiterAccounts.lockerKey(),
        escrowKey,
        payer
    );
  }

  @Override
  public Instruction newVote(final PublicKey proposal,
                             final PublicKey voteKey,
                             final PublicKey payer,
                             final PublicKey voter) {
    if (!voter.equals(escrowOwnerKey)) {
      throw new IllegalStateException(String.format(
          "Voter must correspond to the GLAM vault account. [expected=%s] [observed=%s]",
          escrowOwnerKey, voter
      ));
    }
    return GlamProtocolProgram.jupiterGovNewVote(
        glamAccounts.invokedProgram(),
        solanaAccounts,
        glamKey,
        escrowOwnerKey,
        payer,
        jupiterAccounts.govProgram(),
        proposal,
        voteKey,
        payer,
        voter
    );
  }

  @Override
  public Instruction castVote(final PublicKey escrowKey,
                              final PublicKey voteDelegate,
                              final PublicKey proposal,
                              final PublicKey voteKey,
                              final int side) {
    return GlamProtocolProgram.jupiterVoteCastVote(
        glamAccounts.invokedProgram(),
        glamKey,
        escrowOwnerKey,
        feePayer,
        jupiterAccounts.voteProgram(),
        jupiterAccounts.lockerKey(),
        escrowKey,
        proposal,
        voteKey,
        jupiterAccounts.governorKey(),
        jupiterAccounts.govProgram(),
        side
    );
  }

  @Override
  public Instruction castVote(final PublicKey proposal,
                              final PublicKey voteKey,
                              final int side,
                              final int expectedSide) {
    return GlamProtocolProgram.jupiterVoteCastVoteChecked(
        glamAccounts.invokedProgram(),
        glamKey,
        escrowOwnerKey,
        feePayer,
        jupiterAccounts.voteProgram(),
        jupiterAccounts.lockerKey(),
        escrowKey,
        proposal,
        voteKey,
        jupiterAccounts.governorKey(),
        jupiterAccounts.govProgram(),
        side,
        expectedSide
    );
  }

  @Override
  public Instruction increaseLockedAmount(final PublicKey escrowKey,
                                          final PublicKey escrowTokensKey,
                                          final PublicKey payerKey,
                                          final PublicKey sourceTokensKey,
                                          final long amount) {
    return GlamProtocolProgram.jupiterVoteIncreaseLockedAmount(
        glamAccounts.invokedProgram(),
        glamKey,
        escrowOwnerKey,
        payerKey,
        jupiterAccounts.voteProgram(),
        jupiterAccounts.lockerKey(),
        glamKey,
        escrowTokensKey,
        sourceTokensKey,
        solanaAccounts.tokenProgram(),
        amount
    );
  }

  @Override
  public Instruction toggleMaxLock(final PublicKey lockerKey,
                                   final PublicKey escrowKey,
                                   final PublicKey escrowOwnerKey,
                                   final boolean maxLock) {
    return GlamProtocolProgram.jupiterVoteToggleMaxLock(
        glamAccounts.invokedProgram(),
        glamKey,
        escrowOwnerKey,
        feePayer,
        jupiterAccounts.voteProgram(),
        lockerKey,
        escrowKey,
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
    return GlamProtocolProgram.jupiterVoteWithdraw(
        glamAccounts.invokedProgram(),
        glamKey,
        escrowOwnerKey,
        payerKey,
        jupiterAccounts.voteProgram(),
        lockerKey,
        escrowKey,
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
    throw new UnsupportedOperationException();
//    return GlamProtocolProgram.jupiterVoteOpenPartialUnstaking(
//        glamAccounts.invokedProgram(),
//        solanaAccounts,
//        glamKey,
//        escrowOwnerKey,
//        feePayer,
//        jupiterAccounts.voteProgram(),
//        lockerKey,
//        escrowKey,
//        partialUnstakeKey,
//        amount,
//        memo
//    );
  }

  @Override
  public Instruction mergePartialUnstaking(final PublicKey lockerKey,
                                           final PublicKey escrowKey,
                                           final PublicKey escrowOwnerKey,
                                           final PublicKey partialUnstakeKey) {
    throw new UnsupportedOperationException();
//    return GlamProtocolProgram.jupiterVoteMergePartialUnstaking(
//        glamAccounts.invokedProgram(),
//        glamKey,
//        escrowOwnerKey,
//        feePayer,
//        jupiterAccounts.voteProgram(),
//        lockerKey,
//        escrowKey,
//        partialUnstakeKey
//    );
  }

  @Override
  public Instruction withdrawPartialUnstaking(final PublicKey lockerKey,
                                              final PublicKey escrowKey,
                                              final PublicKey escrowOwnerKey,
                                              final PublicKey escrowTokensKey,
                                              final PublicKey partialUnstakeKey,
                                              final PublicKey payerKey,
                                              final PublicKey destinationTokensKey) {
    throw new UnsupportedOperationException();
//    return GlamProtocolProgram.jupiterVoteWithdrawPartialUnstaking(
//        glamAccounts.invokedProgram(),
//        glamKey,
//        escrowOwnerKey,
//        feePayer,
//        jupiterAccounts.voteProgram(),
//        lockerKey,
//        escrowKey,
//        partialUnstakeKey,
//        escrowTokensKey,
//        destinationTokensKey,
//        solanaAccounts.tokenProgram()
//    );
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
  public Instruction newLocker(final PublicKey baseKey,
                               final PublicKey lockerKey,
                               final PublicKey tokenMintKey,
                               final PublicKey governorKey,
                               final PublicKey payerKey,
                               final LockerParams params) {
    throw new UnsupportedOperationException("TODO: newLocker");
  }

  @Override
  public Instruction activateProposal(final PublicKey proposalKey, final PublicKey smartWalletKey) {
    throw new UnsupportedOperationException("TODO: activateProposal");
  }

  @Override
  public Instruction setLockerParams(final PublicKey smartWalletKey, final LockerParams params) {
    throw new UnsupportedOperationException("TODO: setLockerParams");
  }

  @Override
  public Instruction createGovernor(final PublicKey baseKey,
                                    final PublicKey governorKey,
                                    final PublicKey smartWalletKey,
                                    final PublicKey payerKey,
                                    final PublicKey locker,
                                    final GovernanceParameters params) {
    throw new UnsupportedOperationException("TODO: createGovernor");
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
    throw new UnsupportedOperationException("TODO: createProposal");
  }

  @Override
  public Instruction newClaimAndStake(final PublicKey claimStatusKey,
                                      final PublicKey fromKey,
                                      final PublicKey distributor,
                                      final PublicKey operator,
                                      final PublicKey escrowTokensKey,
                                      final PublicKey tokenProgram,
                                      final long amountUnlocked,
                                      final long amountLocked,
                                      final byte[][] proof) {
    return GlamProtocolProgram.merkleDistributorNewClaimAndStake(
        glamAccounts.invokedProgram(),
        solanaAccounts,
        glamKey,
        escrowOwnerKey,
        feePayer,
        jupiterAccounts.invokedMerkleDistributorProgram().publicKey(),
        distributor,
        claimStatusKey,
        fromKey,
        tokenProgram,
        jupiterAccounts.voteProgram(),
        jupiterAccounts.lockerKey(),
        escrowKey,
        escrowTokensKey,
        amountUnlocked,
        amountLocked,
        proof
    );
  }
}
