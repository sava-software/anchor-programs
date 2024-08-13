package software.sava.anchor.programs.marinade;

import software.sava.anchor.programs.marinade.anchor.types.State;
import software.sava.anchor.programs.marinade.anchor.types.TicketAccountData;
import software.sava.core.accounts.AccountWithSeed;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.accounts.token.TokenAccount;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MarinadeProgramClient {

  static MarinadeProgramClient createClient(final SolanaAccounts solanaAccounts,
                                            final MarinadeAccounts marinadeAccounts,
                                            final AccountMeta owner) {
    return new MarinadeProgramClientImpl(solanaAccounts, marinadeAccounts, owner);
  }

  static MarinadeProgramClient createClient(final AccountMeta owner) {
    return createClient(
        SolanaAccounts.MAIN_NET,
        MarinadeAccounts.MAIN_NET,
        owner
    );
  }

  static MarinadeProgramClient createClient(final PublicKey owner) {
    return createClient(AccountMeta.createFeePayer(owner));
  }

  static MarinadeProgramClient createClient(final NativeProgramAccountClient nativeProgramClient,
                                            final MarinadeAccounts marinadeAccounts) {
    return createClient(nativeProgramClient.accounts(), marinadeAccounts, nativeProgramClient.owner());
  }

  static CompletableFuture<Long> getMinimumBalanceForTicketAccount(final SolanaRpcClient rpcClient) {
    return rpcClient.getMinimumBalanceForRentExemption(TicketAccountData.BYTES);
  }

  static CompletableFuture<List<AccountInfo<TicketAccountData>>> fetchTicketAccounts(final SolanaRpcClient rpcClient,
                                                                                     final PublicKey marinadeProgram,
                                                                                     final PublicKey owner) {
    return rpcClient.getProgramAccounts(
        marinadeProgram,
        List.of(TicketAccountData.SIZE_FILTER, TicketAccountData.createBeneficiaryFilter(owner)),
        TicketAccountData.FACTORY
    );
  }

  static long totalVirtualStakedLamports(final State state) {
    final long totalLamportsUnderControl = state.validatorSystem().totalActiveBalance()
        + state.stakeSystem().delayedUnstakeCoolingDown()
        + state.emergencyCoolingDown()
        + state.availableReserveBalance();
    return totalLamportsUnderControl - state.circulatingTicketBalance();
  }

  static double solPrice(final State state) {
    return totalVirtualStakedLamports(state) / (double) state.msolSupply();
  }

  SolanaAccounts solanaAccounts();

  MarinadeAccounts marinadeAccounts();

  CompletableFuture<List<AccountInfo<TokenAccount>>> fetchMSolTokenAccounts(final SolanaRpcClient rpcClient);

  Instruction marinadeDeposit(final PublicKey mSolTokenAccount, final long lamports);

  CompletableFuture<AccountInfo<State>> fetchProgramState(final SolanaRpcClient rpcClient);

  static CompletableFuture<AccountInfo<MarinadeValidatorList>> fetchValidatorList(final SolanaRpcClient rpcClient,
                                                                                  final State programState) {
    final var destinationValidatorList = programState.validatorSystem().validatorList();
    return rpcClient.getAccountInfo(destinationValidatorList.account(), MarinadeValidatorList.FACTORY);
  }

  ProgramDerivedAddress findDuplicationKey(PublicKey validatorPublicKey);

  Instruction depositStakeAccount(final State marinadeProgramState,
                                  final PublicKey stakeAccount,
                                  final PublicKey mSolTokenAccount,
                                  final PublicKey validatorPublicKey,
                                  final int validatorIndex);

  Instruction createTicketAccountIx(final PublicKey ticketAccount, final long minRentLamports);

  AccountWithSeed createOffCurveAccountWithSeed(final String asciiSeed);

  Instruction createTicketAccountWithSeedIx(final AccountWithSeed accountWithSeed, final long minRentLamports);

  Instruction orderUnstake(final PublicKey mSolTokenAccount,
                           final PublicKey ticketAccount,
                           final long lamports);

  CompletableFuture<List<AccountInfo<TicketAccountData>>> fetchTicketAccounts(final SolanaRpcClient rpcClient);

  Instruction claimTicket(final PublicKey ticketAccount);

  List<Instruction> claimTickets(final Collection<PublicKey> ticketAccounts);
}
