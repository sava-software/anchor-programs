package software.sava.anchor.programs.jupiter.voter;

import software.sava.anchor.programs.jupiter.JupiterAccounts;
import software.sava.anchor.programs.jupiter.governance.anchor.types.Proposal;
import software.sava.anchor.programs.jupiter.voter.anchor.types.Escrow;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface JupiterVoteClient {

  static JupiterVoteClient createClient(final SolanaAccounts solanaAccounts,
                                        final JupiterAccounts accounts) {
    return new JupiterVoteClientImpl(solanaAccounts, accounts);
  }

  static JupiterVoteClient createClient() {
    return createClient(SolanaAccounts.MAIN_NET, JupiterAccounts.MAIN_NET);
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
        jupiterAccounts().voteProgram(),
        List.of(
            Proposal.createGovernorFilter(governorKey)
        ),
        Proposal.FACTORY
    );
  }

  Instruction newVote(final PublicKey proposal,
                      final PublicKey payer,
                      final PublicKey voter);

  default Instruction newVote(final PublicKey proposal,
                              final PublicKey voter) {
    return newVote(proposal, voter, voter);
  }

  Instruction castVote(final PublicKey voter,
                       final PublicKey voteDelegate,
                       final PublicKey proposal,
                       final int side);

  default Instruction castVote(final PublicKey voter,
                               final PublicKey proposal,
                               final int side) {
    return castVote(voter, voter, proposal, side);
  }

  Instruction setVoteDelegate(final PublicKey escrowOwnerKey, final PublicKey newDelegate);

  Instruction increaseLockedAmount(final Escrow escrow,
                                   final PublicKey payerKey,
                                   final PublicKey sourceTokensKey,
                                   final long amount);

  default Instruction increaseLockedAmount(final Escrow escrow,
                                           final PublicKey sourceTokensKey,
                                           final long amount) {
    return increaseLockedAmount(escrow, escrow.owner(), sourceTokensKey, amount);
  }

  Instruction extendLockDuration(final Escrow escrow, final long duration);

  Instruction toggleMaxLock(final Escrow escrow, final boolean maxLock);

  Instruction withdraw(final Escrow escrow,
                       final PublicKey payerKey,
                       final PublicKey destinationTokensKey);

  default Instruction withdraw(final Escrow escrow, final PublicKey destinationTokensKey) {
    return withdraw(escrow, escrow.owner(), destinationTokensKey);
  }

  Instruction openPartialUnstaking(final Escrow escrow,
                                   final PublicKey partialUnstakeKey,
                                   final long amount,
                                   final String memo);

  Instruction mergePartialUnstaking(final Escrow escrow, final PublicKey partialUnstakeKey);

  Instruction withdrawPartialUnstaking(final Escrow escrow,
                                       final PublicKey partialUnstakeKey,
                                       final PublicKey payerKey,
                                       final PublicKey destinationTokensKey);

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
}
