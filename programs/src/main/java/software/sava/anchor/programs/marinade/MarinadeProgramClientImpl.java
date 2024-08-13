package software.sava.anchor.programs.marinade;

import software.sava.anchor.programs.marinade.anchor.MarinadeFinanceProgram;
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

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

final class MarinadeProgramClientImpl implements MarinadeProgramClient {

  private final NativeProgramAccountClient nativeProgramClient;
  private final SolanaAccounts solanaAccounts;
  private final MarinadeAccounts marinadeAccounts;
  private final AccountMeta owner;

  MarinadeProgramClientImpl(final SolanaAccounts solanaAccounts,
                            final MarinadeAccounts marinadeAccounts,
                            final AccountMeta owner) {
    this.nativeProgramClient = NativeProgramAccountClient.createClient(solanaAccounts, owner.publicKey());
    this.solanaAccounts = solanaAccounts;
    this.marinadeAccounts = marinadeAccounts;
    this.owner = owner;
  }

  @Override
  public SolanaAccounts solanaAccounts() {
    return solanaAccounts;
  }

  @Override
  public MarinadeAccounts marinadeAccounts() {
    return marinadeAccounts;
  }

  @Override
  public CompletableFuture<List<AccountInfo<TokenAccount>>> fetchMSolTokenAccounts(final SolanaRpcClient rpcClient) {
    return rpcClient.getTokenAccountsForTokenMintByOwner(owner.publicKey(), marinadeAccounts.mSolTokenMint());
  }

  @Override
  public Instruction marinadeDeposit(final PublicKey mSolTokenAccount, final long lamports) {
    return MarinadeFinanceProgram.deposit(
        marinadeAccounts.invokedMarinadeProgram(),
        owner,
        marinadeAccounts.stateProgram(),
        marinadeAccounts.mSolTokenMint(),
        marinadeAccounts.liquidityPoolSolLegAccount(),
        marinadeAccounts.liquidityPoolMSolLegAccount(),
        marinadeAccounts.liquidityPoolMSolLegAuthority(),
        marinadeAccounts.treasuryReserveSolPDA(),
        mSolTokenAccount,
        marinadeAccounts.mSolTokenMintAuthorityPDA(),
        solanaAccounts.systemProgram(),
        solanaAccounts.tokenProgram(),
        lamports
    );
  }

  @Override
  public CompletableFuture<AccountInfo<State>> fetchProgramState(final SolanaRpcClient rpcClient) {
    return rpcClient.getAccountInfo(marinadeAccounts.stateProgram(), State.FACTORY);
  }

  @Override
  public ProgramDerivedAddress findDuplicationKey(final PublicKey validatorPublicKey) {
    return PublicKey.findProgramAddress(List.of(
        marinadeAccounts.stateProgram().toByteArray(),
        "unique_validator".getBytes(StandardCharsets.UTF_8),
        validatorPublicKey.toByteArray()
    ), marinadeAccounts.marinadeProgram());
  }

  @Override
  public Instruction depositStakeAccount(final State marinadeProgramState,
                                         final PublicKey stakeAccount,
                                         final PublicKey mSolTokenAccount,
                                         final PublicKey validatorPublicKey,
                                         final int validatorIndex) {
    return MarinadeFinanceProgram.depositStakeAccount(
        marinadeAccounts.invokedMarinadeProgram(),
        owner,
        owner,
        marinadeAccounts.stateProgram(),
        marinadeProgramState.validatorSystem().validatorList().account(),
        marinadeProgramState.stakeSystem().stakeList().account(),
        stakeAccount,
        findDuplicationKey(validatorPublicKey).publicKey(),
        marinadeAccounts.mSolTokenMint(),
        mSolTokenAccount,
        marinadeAccounts.mSolTokenMintAuthorityPDA(),
        solanaAccounts.clockSysVar(),
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram(),
        solanaAccounts.tokenProgram(),
        solanaAccounts.stakeProgram(),
        validatorIndex
    );
  }

  @Override
  public Instruction createTicketAccountIx(final PublicKey ticketAccount, final long minRentLamports) {
    return nativeProgramClient.createAccount(
        ticketAccount,
        minRentLamports,
        TicketAccountData.BYTES,
        marinadeAccounts.marinadeProgram()
    );
  }

  @Override
  public AccountWithSeed createOffCurveAccountWithSeed(final String asciiSeed) {
    return PublicKey.createOffCurveAccountWithAsciiSeed(
        owner.publicKey(),
        asciiSeed,
        marinadeAccounts.marinadeProgram()
    );
  }

  @Override
  public Instruction createTicketAccountWithSeedIx(final AccountWithSeed accountWithSeed, final long minRentLamports) {
    return nativeProgramClient.createAccountWithSeed(
        accountWithSeed,
        minRentLamports,
        TicketAccountData.BYTES,
        marinadeAccounts.marinadeProgram()
    );
  }

  @Override
  public Instruction orderUnstake(final PublicKey mSolTokenAccount,
                                  final PublicKey ticketAccount,
                                  final long lamports) {
    return MarinadeFinanceProgram.orderUnstake(
        marinadeAccounts.invokedMarinadeProgram(),
        owner,
        marinadeAccounts.stateProgram(),
        marinadeAccounts.mSolTokenMint(),
        mSolTokenAccount,
        ticketAccount,
        solanaAccounts.clockSysVar(),
        solanaAccounts.rentSysVar(),
        solanaAccounts.tokenProgram(),
        lamports
    );
  }

  @Override
  public CompletableFuture<List<AccountInfo<TicketAccountData>>> fetchTicketAccounts(final SolanaRpcClient rpcClient) {
    return MarinadeProgramClient.fetchTicketAccounts(rpcClient, marinadeAccounts.marinadeProgram(), owner.publicKey());
  }

  @Override
  public Instruction claimTicket(final PublicKey ticketAccount) {
    return MarinadeFinanceProgram.claim(
        marinadeAccounts.invokedMarinadeProgram(),
        marinadeAccounts.stateProgram(),
        marinadeAccounts.treasuryReserveSolPDA(),
        ticketAccount,
        owner.publicKey(),
        solanaAccounts.clockSysVar(),
        solanaAccounts.systemProgram()
    );
  }

  @Override
  public List<Instruction> claimTickets(final Collection<PublicKey> ticketAccounts) {
    return ticketAccounts.stream().map(this::claimTicket).toList();
  }
}