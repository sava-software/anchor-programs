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
import software.sava.solana.programs.stake.StakeAccount;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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

  static int accountIndex(final byte[] key,
                          final byte[] listData,
                          final int itemSize) {
    for (int i = 8, s = 0; i < listData.length; i += itemSize, ++s) {
      if (Arrays.equals(
          key, 0, PublicKey.PUBLIC_KEY_LENGTH,
          listData, i, i + PublicKey.PUBLIC_KEY_LENGTH
      )) {
        return s;
      }
    }
    return -1;
  }

  static int stakeAccountIndex(final byte[] stakeAccountKey,
                               final byte[] stakeListData,
                               final State state) {
    return accountIndex(stakeAccountKey, stakeListData, state.stakeSystem().stakeList().itemSize());
  }

  static int stakeAccountIndex(final StakeAccount stakeAccount,
                               final byte[] validatorListData,
                               final State state) {
    return stakeAccountIndex(stakeAccount.address().toByteArray(), validatorListData, state);
  }

  static int validatorIndex(final byte[] validatorKey,
                            final byte[] validatorListData,
                            final State state) {
    return accountIndex(validatorKey, validatorListData, state.validatorSystem().validatorList().itemSize());
  }

  static int validatorIndex(final StakeAccount stakeAccount,
                            final byte[] validatorListData,
                            final State state) {
    return validatorIndex(stakeAccount.voterPublicKey().toByteArray(), validatorListData, state);
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

  Instruction claimTicket(final PublicKey ticketAccount);

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
