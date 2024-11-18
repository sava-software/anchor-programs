package software.sava.anchor.programs.jupiter.governance;

import software.sava.anchor.programs.jupiter.JupiterAccounts;
import software.sava.anchor.programs.jupiter.staking.anchor.types.Escrow;
import software.sava.anchor.programs.jupiter.staking.anchor.types.Locker;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.util.List;

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


  public static void main(final String[] args) {
    final var jupiterAccounts = JupiterAccounts.MAIN_NET;

    try (final var httpClient = HttpClient.newHttpClient()) {
      final var rpcClient = SolanaRpcClient.createClient(URI.create("https://mainnet.helius-rpc.com/?api-key="), httpClient);

      final var voterAccount = PublicKey.fromBase58Encoded("");


      final var lockerAccountFuture = rpcClient.getAccountInfo(jupiterAccounts.jupLockerAccount(), Locker.FACTORY);

      final var escrowAccountsFuture = rpcClient.getProgramAccounts(
          jupiterAccounts.voteProgram(),
          List.of(
              Escrow.SIZE_FILTER,
              Escrow.createVoteDelegateFilter(voterAccount)
          ));

      final var lockerAccountInfo = lockerAccountFuture.join();
      final var locker = lockerAccountInfo.data();
      System.out.println(locker);

//      final var proposalAccountsFuture = rpcClient.getProgramAccounts(
//          jupiterAccounts.govProgram(),
//          List.of(
//              Proposal.createGovernorFilter(locker.governor())
//          ));

      final var escrowAccountInfos = escrowAccountsFuture.join();
      final var escrowAccounts = escrowAccountInfos.stream()
          .map(accountInfo -> Escrow.read(accountInfo.pubKey(), accountInfo.data()))
          .toList();

      for (final var escrowAccount : escrowAccounts) {
        System.out.println(escrowAccount);
      }

//      final var proposals = proposalAccountsFuture.join();
//      final long now = Instant.now().getEpochSecond();
//      final var activeProposals = proposals.stream()
//          .map(accountInfo -> Proposal.read(accountInfo.pubKey(), accountInfo.data()))
//          .filter(proposal -> proposal.votingEndsAt() > now)
//          .sorted(Comparator.comparing(Proposal::votingEndsAt))
//          .peek(System.out::println)
//          .toList();
//
//      final var activeProposal = activeProposals.getFirst();
      final var proposal = PublicKey.fromBase58Encoded("ByQ21v3hqdQVwPHsfwurrtEAH8pB3DYuLdp9jU2Hwnd4");

//      final var voteAccountFuture = rpcClient.getProgramAccounts(
//          jupiterAccounts.govProgram(),
//          List.of(
//              Vote.SIZE_FILTER,
//              Vote.createProposalFilter(activeProposal._address()),
//              Vote.createVoterFilter(voterAccount)
//          ));
//
//      final var voteAccountInfos = voteAccountFuture.join().getFirst();
//      final var voteAccount = Vote.read(voteAccountInfos.pubKey(), voteAccountInfos.data());
//      System.out.println(voteAccount);
      final var voteAccount = PublicKey.fromBase58Encoded("H7m2mhGmfZ8Fu1jDkeeVnrWE6cKmwkppkiSyF2pCPHPp");


      final var voteClient = JupiterVoteClient.createClient();

      final var castVoteIx = voteClient.castVote(
          locker,
          escrowAccounts.getFirst(),
          voterAccount,
//          activeProposal._address(),
          proposal,
//          voteAccount._address(),
          voteAccount,
          0
      );

      final var nativeClient = NativeProgramAccountClient.createClient(voterAccount);
      final var castVoteTx = nativeClient.createTransaction(castVoteIx);

      final var simulation = rpcClient.simulateTransaction(castVoteTx).join();
      System.out.println(simulation);
    }
  }

  Instruction castVote(final Locker locker,
                       final Escrow escrow,
                       final PublicKey voteDelegate,
                       final PublicKey proposal,
                       final PublicKey vote,
                       final int side);
}
