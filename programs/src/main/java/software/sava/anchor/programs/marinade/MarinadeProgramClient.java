package software.sava.anchor.programs.marinade;

import software.sava.anchor.programs.marinade.anchor.types.State;
import software.sava.anchor.programs.marinade.anchor.types.TicketAccountData;
import software.sava.core.accounts.AccountWithSeed;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.token.TokenAccount;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MarinadeProgramClient {

  static MarinadeProgramClient createClient(final NativeProgramAccountClient nativeProgramAccountClient,
                                            final MarinadeAccounts marinadeAccounts) {
    return new MarinadeProgramClientImpl(nativeProgramAccountClient, marinadeAccounts);
  }

  static MarinadeProgramClient createClient(final NativeProgramAccountClient nativeProgramAccountClient) {
    return createClient(nativeProgramAccountClient, MarinadeAccounts.MAIN_NET);
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

  NativeProgramAccountClient nativeProgramAccountClient();

  PublicKey owner();

  PublicKey feePayer();

  default CompletableFuture<List<AccountInfo<TokenAccount>>> fetchMSolTokenAccounts(final SolanaRpcClient rpcClient) {
    return rpcClient.getTokenAccountsForTokenMintByOwner(owner(), marinadeAccounts().mSolTokenMint());
  }

  Instruction marinadeDeposit(final PublicKey mSolTokenAccount, final long lamports);

  default CompletableFuture<AccountInfo<State>> fetchProgramState(final SolanaRpcClient rpcClient) {
    return rpcClient.getAccountInfo(marinadeAccounts().stateProgram(), State.FACTORY);
  }

  static CompletableFuture<AccountInfo<MarinadeValidatorList>> fetchValidatorList(final SolanaRpcClient rpcClient,
                                                                                  final State programState) {
    final var destinationValidatorList = programState.validatorSystem().validatorList();
    return rpcClient.getAccountInfo(destinationValidatorList.account(), MarinadeValidatorList.FACTORY);
  }

  default CompletableFuture<List<AccountInfo<TicketAccountData>>> fetchTicketAccounts(final SolanaRpcClient rpcClient) {
    return fetchTicketAccounts(rpcClient, marinadeAccounts().marinadeProgram(), owner());
  }

  default ProgramDerivedAddress findDuplicationKey(final PublicKey validatorPublicKey) {
    final var marinadeAccounts = marinadeAccounts();
    return PublicKey.findProgramAddress(List.of(
            marinadeAccounts.stateProgram().toByteArray(),
            "unique_validator".getBytes(StandardCharsets.UTF_8),
            validatorPublicKey.toByteArray()
        ), marinadeAccounts.marinadeProgram()
    );
  }

  @Deprecated
  default AccountWithSeed createOffCurveAccountWithSeed(final String asciiSeed) {
    return PublicKey.createOffCurveAccountWithAsciiSeed(
        feePayer(),
        asciiSeed,
        marinadeAccounts().marinadeProgram()
    );
  }

  @Deprecated
  default Instruction createTicketAccountIx(final PublicKey ticketAccount, final long minRentLamports) {
    return nativeProgramAccountClient().createAccount(
        ticketAccount,
        minRentLamports,
        TicketAccountData.BYTES,
        marinadeAccounts().marinadeProgram()
    );
  }

  @Deprecated
  default Instruction createTicketAccountWithSeedIx(final AccountWithSeed accountWithSeed, final long minRentLamports) {
    return nativeProgramAccountClient().createAccountWithSeed(
        accountWithSeed,
        minRentLamports,
        TicketAccountData.BYTES,
        marinadeAccounts().marinadeProgram()
    );
  }

  Instruction depositStakeAccount(final PublicKey validatorListKey,
                                  final PublicKey stakeListKey,
                                  final PublicKey stakeAccount,
                                  final PublicKey duplicationFlagKey,
                                  final PublicKey mSolTokenAccount,
                                  final int validatorIndex);

  default Instruction depositStakeAccount(final State marinadeProgramState,
                                          final PublicKey stakeAccount,
                                          final PublicKey mSolTokenAccount,
                                          final PublicKey validatorPublicKey,
                                          final int validatorIndex) {
    return depositStakeAccount(
        marinadeProgramState.validatorSystem().validatorList().account(),
        marinadeProgramState.stakeSystem().stakeList().account(),
        stakeAccount,
        findDuplicationKey(validatorPublicKey).publicKey(),
        mSolTokenAccount,
        validatorIndex
    );
  }

  @Deprecated
  Instruction orderUnstake(final PublicKey mSolTokenAccount,
                           final PublicKey ticketAccount,
                           final long lamports);

  Instruction claimTicket(final PublicKey ticketAccount);

  @Deprecated
  default List<Instruction> claimTickets(final Collection<PublicKey> ticketAccounts) {
    return ticketAccounts.stream().map(this::claimTicket).toList();
  }

  Instruction withdrawStakeAccount(final PublicKey mSolTokenAccount,
                                   final PublicKey validatorListKey,
                                   final PublicKey stakeListKey,
                                   final PublicKey stakeWithdrawalAuthorityKey,
                                   final PublicKey stakeDepositAuthorityKey,
                                   final PublicKey stakeAccount,
                                   final PublicKey splitStakeAccountKey,
                                   final int stakeIndex,
                                   final int validatorIndex,
                                   final long msolAmount);

  default Instruction withdrawStakeAccount(final State marinadeProgramState,
                                           final PublicKey mSolTokenAccount,
                                           final PublicKey stakeAccount,
                                           final PublicKey splitStakeAccountKey,
                                           final int stakeIndex,
                                           final int validatorIndex,
                                           final long msolAmount) {
    final var marinadeAccounts = marinadeAccounts();
    return withdrawStakeAccount(
        mSolTokenAccount,
        marinadeProgramState.validatorSystem().validatorList().account(),
        marinadeProgramState.stakeSystem().stakeList().account(),
        marinadeAccounts.deriveStakeWithdrawAuthority().publicKey(),
        marinadeAccounts.deriveStakeDepositAuthority().publicKey(),
        stakeAccount,
        splitStakeAccountKey,
        stakeIndex,
        validatorIndex,
        msolAmount
    );
  }
}
