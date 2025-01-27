package software.sava.anchor.programs.jupiter.voter;

import software.sava.anchor.programs.jupiter.JupiterAccounts;
import software.sava.anchor.programs.jupiter.governance.anchor.types.GovernanceParameters;
import software.sava.anchor.programs.jupiter.governance.anchor.types.Proposal;
import software.sava.anchor.programs.jupiter.governance.anchor.types.ProposalInstruction;
import software.sava.anchor.programs.jupiter.voter.anchor.types.Escrow;
import software.sava.anchor.programs.jupiter.voter.anchor.types.LockerParams;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface JupiterVoteClient {

  static JupiterVoteClient createClient(final SolanaAccounts solanaAccounts,
                                        final JupiterAccounts jupiterAccounts,
                                        final PublicKey escrowOwnerKey) {
    return new JupiterVoteClientImpl(solanaAccounts, jupiterAccounts, escrowOwnerKey);
  }

  static JupiterVoteClient createClient(final PublicKey escrowOwnerKey) {
    return createClient(SolanaAccounts.MAIN_NET, JupiterAccounts.MAIN_NET, escrowOwnerKey);
  }

  SolanaAccounts solanaAccounts();

  JupiterAccounts jupiterAccounts();

  default CompletableFuture<List<AccountInfo<Escrow>>> fetchEscrowAccountsForDelegate(final SolanaRpcClient rpcClient,
                                                                                      final PublicKey delegate) {
    return rpcClient.getProgramAccounts(
        jupiterAccounts().voteProgram(),
        List.of(
            Escrow.SIZE_FILTER,
            Escrow.createVoteDelegateFilter(delegate)
        ),
        Escrow.FACTORY
    );
  }

  default CompletableFuture<List<AccountInfo<Proposal>>> fetchProposals(final SolanaRpcClient rpcClient) {
    final var accounts = jupiterAccounts();
    final var governorKey = accounts.deriveGovernor().publicKey();
    return rpcClient.getProgramAccounts(
        jupiterAccounts().govProgram(),
        List.of(Proposal.createGovernorFilter(governorKey)),
        Proposal.FACTORY
    );
  }

  PublicKey escrowOwnerKey();

  PublicKey escrowOwnerKeyATA();

  PublicKey escrowKey();

  PublicKey escrowATA();

  PublicKey deriveVoteKey(final PublicKey proposal);

  Instruction newEscrow(final PublicKey escrowOwnerKey,
                        final PublicKey escrowKey,
                        final PublicKey payer);

  Instruction newEscrow(final PublicKey payer);

  Instruction newVote(final PublicKey proposal,
                      final PublicKey voteKey,
                      final PublicKey payer,
                      final PublicKey voter);

  Instruction newVote(final PublicKey proposal,
                      final PublicKey voteKey,
                      final PublicKey payer);

  Instruction newVote(final PublicKey proposal, final PublicKey payer);

  Instruction castVote(final PublicKey escrowKey,
                       final PublicKey voteDelegate,
                       final PublicKey proposal,
                       final PublicKey voteKey,
                       final int side);

  Instruction castVote(final PublicKey proposal,
                       final PublicKey voteKey,
                       final int side);

  Instruction setVoteDelegate(final PublicKey escrowOwnerKey,
                              final PublicKey escrowKey,
                              final PublicKey newDelegate);

  Instruction setVoteDelegate(final PublicKey newDelegate);

  Instruction increaseLockedAmount(final PublicKey escrowKey,
                                   final PublicKey escrowTokensKey,
                                   final PublicKey payerKey,
                                   final PublicKey sourceTokensKey,
                                   final long amount);

  Instruction increaseLockedAmount(final PublicKey escrowTokensKey,
                                   final PublicKey payerKey,
                                   final PublicKey sourceTokensKey,
                                   final long amount);

  Instruction increaseLockedAmount(final PublicKey payerKey,
                                   final long amount);

  default Instruction increaseLockedAmount(final Escrow escrow,
                                           final PublicKey payerKey,
                                           final PublicKey sourceTokensKey,
                                           final long amount) {
    return increaseLockedAmount(
        escrow._address(),
        escrow.tokens(),
        payerKey,
        sourceTokensKey,
        amount
    );
  }

  default Instruction increaseLockedAmount(final Escrow escrow,
                                           final PublicKey sourceTokensKey,
                                           final long amount) {
    return increaseLockedAmount(escrow, escrow.owner(), sourceTokensKey, amount);
  }

  Instruction extendLockDuration(final PublicKey lockerKey,
                                 final PublicKey escrowKey,
                                 final PublicKey escrowOwnerKey,
                                 final long duration);

  Instruction extendLockDuration(final Escrow escrow, final long duration);

  Instruction extendLockDuration(final long duration);

  Instruction toggleMaxLock(final PublicKey lockerKey,
                            final PublicKey escrowKey,
                            final PublicKey escrowOwnerKey,
                            final boolean maxLock);

  Instruction toggleMaxLock(final Escrow escrow, final boolean maxLock);

  Instruction toggleMaxLock(final PublicKey escrowOwner,
                            final PublicKey escrowKey,
                            final boolean maxLock);

  Instruction toggleMaxLock(final boolean maxLock);

  default Instruction toggleMaxLock(PublicKey escrowOwner, boolean maxLock) {
    final var escrowKey = jupiterAccounts().deriveEscrow(escrowOwner).publicKey();
    return toggleMaxLock(escrowOwner, escrowKey, maxLock);
  }

  Instruction withdraw(final PublicKey lockerKey,
                       final PublicKey escrowKey,
                       final PublicKey escrowOwnerKey,
                       final PublicKey escrowTokensKey,
                       final PublicKey payerKey,
                       final PublicKey destinationTokensKey);

  Instruction withdraw(final Escrow escrow,
                       final PublicKey payerKey,
                       final PublicKey destinationTokensKey);

  Instruction withdraw(final PublicKey payerKey);

  Instruction withdraw(final Escrow escrow);

  Instruction withdraw();

  Instruction openPartialUnstaking(final PublicKey lockerKey,
                                   final PublicKey escrowKey,
                                   final PublicKey escrowOwnerKey,
                                   final PublicKey partialUnstakeKey,
                                   final long amount,
                                   final String memo);

  Instruction openPartialUnstaking(final Escrow escrow,
                                   final PublicKey partialUnstakeKey,
                                   final long amount,
                                   final String memo);

  Instruction openPartialUnstaking(final PublicKey partialUnstakeKey,
                                   final long amount,
                                   final String memo);

  Instruction mergePartialUnstaking(final PublicKey lockerKey,
                                    final PublicKey escrowKey,
                                    final PublicKey escrowOwnerKey,
                                    final PublicKey partialUnstakeKey);

  Instruction mergePartialUnstaking(final Escrow escrow, final PublicKey partialUnstakeKey);

  Instruction mergePartialUnstaking(final PublicKey partialUnstakeKey);

  Instruction withdrawPartialUnstaking(final PublicKey lockerKey,
                                       final PublicKey escrowKey,
                                       final PublicKey escrowOwnerKey,
                                       final PublicKey escrowTokensKey,
                                       final PublicKey partialUnstakeKey,
                                       final PublicKey payerKey,
                                       final PublicKey destinationTokensKey);

  Instruction withdrawPartialUnstaking(final Escrow escrow,
                                       final PublicKey partialUnstakeKey,
                                       final PublicKey payerKey,
                                       final PublicKey destinationTokensKey);

  Instruction withdrawPartialUnstaking(final PublicKey partialUnstakeKey, final PublicKey payerKey);

  default Instruction withdrawPartialUnstaking(final Escrow escrow,
                                               final PublicKey partialUnstakeKey,
                                               final PublicKey destinationTokensKey) {
    return withdrawPartialUnstaking(
        escrow,
        partialUnstakeKey,
        escrow.owner(),
        destinationTokensKey
    );
  }

  Instruction newLocker(final PublicKey baseKey,
                        final PublicKey lockerKey,
                        final PublicKey tokenMintKey,
                        final PublicKey governorKey,
                        final PublicKey payerKey,
                        final LockerParams params);

  Instruction activateProposal(final PublicKey proposalKey, final PublicKey smartWalletKey);

  Instruction setLockerParams(final PublicKey smartWalletKey, final LockerParams params);

  Instruction createGovernor(final PublicKey baseKey,
                             final PublicKey governorKey,
                             final PublicKey smartWalletKey,
                             final PublicKey payerKey,
                             final PublicKey locker,
                             final GovernanceParameters params);

  Instruction createProposal(final PublicKey governorKey,
                             final PublicKey proposalKey,
                             final PublicKey smartWalletKey,
                             final PublicKey proposerKey,
                             final PublicKey payerKey,
                             final PublicKey eventAuthorityKey,
                             final int proposalType,
                             final int maxOption,
                             final ProposalInstruction[] instructions);
}
