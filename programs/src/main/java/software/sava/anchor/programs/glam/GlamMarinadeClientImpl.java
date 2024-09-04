package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamProgram;
import software.sava.anchor.programs.marinade.MarinadeAccounts;
import software.sava.anchor.programs.marinade.MarinadeProgramClient;
import software.sava.anchor.programs.marinade.anchor.types.State;
import software.sava.anchor.programs.marinade.anchor.types.TicketAccountData;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.accounts.token.TokenAccount;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

final class GlamMarinadeClientImpl implements GlamMarinadeClient {

  private final SolanaAccounts solanaAccounts;
  private final MarinadeAccounts marinadeAccounts;
  private final GlamFundAccounts glamFundAccounts;
  private final AccountMeta invokedProgram;
  private final AccountMeta feePayer;
  private final MarinadeProgramClient marinadeProgramClient;

  GlamMarinadeClientImpl(final GlamProgramAccountClient glamClient, final MarinadeAccounts marinadeAccounts) {
    this.solanaAccounts = glamClient.solanaAccounts();
    this.marinadeAccounts = marinadeAccounts;
    this.glamFundAccounts = glamClient.fundAccounts();
    this.invokedProgram = glamFundAccounts.glamAccounts().invokedProgram();
    this.feePayer = glamClient.feePayer();
    this.marinadeProgramClient = MarinadeProgramClient.createClient(glamClient, marinadeAccounts);
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
  public FundPDA createMarinadeTicket() {
    return FundPDA.createPDA("ticket", glamFundAccounts.fundPublicKey(), invokedProgram.publicKey());
  }

  @Override
  public CompletableFuture<List<AccountInfo<TokenAccount>>> fetchMSolTokenAccounts(final SolanaRpcClient rpcClient) {
    return marinadeProgramClient.fetchMSolTokenAccounts(rpcClient);
  }

  @Override
  public CompletableFuture<List<AccountInfo<TicketAccountData>>> fetchTicketAccounts(final SolanaRpcClient rpcClient) {
    return marinadeProgramClient.fetchTicketAccounts(rpcClient);
  }

  @Override
  public Instruction marinadeDeposit(final PublicKey mSolTokenAccount, final long lamports) {
    return GlamProgram.marinadeDepositSol(
        invokedProgram,
        solanaAccounts,
        feePayer.publicKey(),
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        marinadeAccounts.stateProgram(),
        marinadeAccounts.treasuryReserveSolPDA(),
        marinadeAccounts.mSolTokenMint(),
        marinadeAccounts.mSolTokenMintAuthorityPDA(),
        marinadeAccounts.liquidityPoolMSolLegAccount(),
        marinadeAccounts.liquidityPoolMSolLegAuthority(),
        marinadeAccounts.liquidityPoolSolLegAccount(),
        mSolTokenAccount,
        marinadeAccounts.marinadeProgram(),
        lamports
    );
  }

  @Override
  public CompletableFuture<AccountInfo<State>> fetchProgramState(final SolanaRpcClient rpcClient) {
    return marinadeProgramClient.fetchProgramState(rpcClient);
  }

  @Override
  public ProgramDerivedAddress findDuplicationKey(final PublicKey validatorPublicKey) {
    return marinadeProgramClient.findDuplicationKey(validatorPublicKey);
  }

  @Override
  public Instruction depositStakeAccount(final State marinadeProgramState,
                                         final PublicKey stakeAccount,
                                         final PublicKey mSolTokenAccount,
                                         final PublicKey validatorPublicKey,
                                         final int validatorIndex) {
    return GlamProgram.marinadeDepositStake(
        invokedProgram,
        solanaAccounts,
        feePayer.publicKey(),
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        marinadeAccounts.stateProgram(),
        marinadeProgramState.validatorSystem().validatorList().account(),
        marinadeProgramState.stakeSystem().stakeList().account(),
        stakeAccount,
        findDuplicationKey(validatorPublicKey).publicKey(),
        marinadeAccounts.mSolTokenMint(),
        marinadeAccounts.mSolTokenMintAuthorityPDA(),
        mSolTokenAccount,
        marinadeAccounts.marinadeProgram(),
        validatorIndex
    );
  }

  @Override
  public Instruction orderUnstake(final PublicKey mSolTokenAccount,
                                  final FundPDA ticketAccount,
                                  final long lamports) {
    final var ticketPDA = ticketAccount.pda();
    return GlamProgram.marinadeDelayedUnstake(
        invokedProgram,
        solanaAccounts,
        feePayer.publicKey(),
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        ticketPDA.publicKey(),
        marinadeAccounts.mSolTokenMint(),
        mSolTokenAccount,
        marinadeAccounts.stateProgram(),
        marinadeAccounts.treasuryReserveSolPDA(),
        marinadeAccounts.marinadeProgram(),
        lamports,
        ticketAccount.accountId(),
        ticketPDA.nonce()
    );
  }

  private Instruction claimTickets() {
    return GlamProgram.marinadeClaimTickets(
        glamFundAccounts.glamAccounts().invokedProgram(),
        solanaAccounts,
        feePayer.publicKey(),
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        marinadeAccounts.stateProgram(),
        marinadeAccounts.treasuryReserveSolPDA(),
        marinadeAccounts.marinadeProgram()
    );
  }

  @Override
  public Instruction claimTicket(final PublicKey ticketAccount) {
    return claimTickets().extraAccount(AccountMeta.createWrite(ticketAccount));
  }

  @Override
  public List<Instruction> claimTickets(final Collection<PublicKey> ticketAccounts) {
    return List.of(claimTickets().extraAccounts(ticketAccounts, AccountMeta.CREATE_WRITE));
  }
}
