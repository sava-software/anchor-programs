package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamProgram;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.accounts.token.TokenAccount;
import software.sava.core.tx.Instruction;
import software.sava.core.tx.Transaction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramClient;
import software.sava.solana.programs.token.AssociatedTokenProgram;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static software.sava.core.accounts.meta.AccountMeta.createFeePayer;

final class GlamNativeClientImpl implements GlamNativeClient {

  private final SolanaAccounts solanaAccounts;
  private final NativeProgramClient nativeProgramClient;
  private final GlamFundAccounts glamFundAccounts;
  private final AccountMeta invokedProgram;
  private final AccountMeta feePayer;
  private final ProgramDerivedAddress wrappedSolPDA;

  GlamNativeClientImpl(final SolanaAccounts solanaAccounts, final GlamFundAccounts glamFundAccounts) {
    this.solanaAccounts = solanaAccounts;
    this.nativeProgramClient = NativeProgramClient.createClient(solanaAccounts);
    this.glamFundAccounts = glamFundAccounts;
    this.invokedProgram = glamFundAccounts.glamAccounts().invokedProgram();
    this.feePayer = createFeePayer(glamFundAccounts.signerPublicKey());
    this.wrappedSolPDA = findAssociatedTokenProgramAddress(solanaAccounts.wrappedSolTokenMint());
  }

  @Override
  public SolanaAccounts solanaAccounts() {
    return solanaAccounts;
  }

  @Override
  public GlamFundAccounts fundAccounts() {
    return glamFundAccounts;
  }

  @Override
  public AccountMeta feePayer() {
    return feePayer;
  }

  @Override
  public ProgramDerivedAddress wrappedSolPDA() {
    return wrappedSolPDA;
  }

  @Override
  public ProgramDerivedAddress findAssociatedTokenProgramAddress(final PublicKey tokenMintAddress) {
    return AssociatedTokenProgram.findAssociatedTokenProgramAddress(solanaAccounts, glamFundAccounts.treasuryPublicKey(), tokenMintAddress);
  }

  @Override
  public Transaction createTransaction(final Instruction instruction) {
    return Transaction.createTx(glamFundAccounts.signerPublicKey(), instruction);
  }

  @Override
  public Transaction createTransaction(final List<Instruction> instructions) {
    return Transaction.createTx(glamFundAccounts.signerPublicKey(), instructions);
  }

  @Override
  public Transaction createTransaction(final int computeUnitLimit, final long microLamportComputeUnitPrice, final Instruction instruction) {
    return Transaction.createTx(glamFundAccounts.signerPublicKey(), List.of(
        nativeProgramClient.computeUnitLimit(computeUnitLimit),
        nativeProgramClient.computeUnitPrice(microLamportComputeUnitPrice),
        instruction
    ));
  }

  @Override
  public CompletableFuture<List<AccountInfo<TokenAccount>>> fetchTokenAccounts(final SolanaRpcClient rpcClient, final PublicKey tokenMintAddress) {
    return rpcClient.getTokenAccountsForTokenMintByOwner(glamFundAccounts.treasuryPublicKey(), tokenMintAddress);
  }

  @Override
  public CompletableFuture<List<AccountInfo<TokenAccount>>> fetchTokenAccounts(final SolanaRpcClient rpcClient) {
    return rpcClient.getTokenAccountsForProgramByOwner(glamFundAccounts.treasuryPublicKey(), solanaAccounts.tokenProgram());
  }

  @Override
  public Instruction wrapSol(final long lamports) {
    return GlamProgram.wsolWrap(
        invokedProgram,
        feePayer,
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        wrappedSolPDA.publicKey(),
        solanaAccounts.wrappedSolTokenMint(),
        solanaAccounts.systemProgram(),
        solanaAccounts.tokenProgram(),
        solanaAccounts.associatedTokenAccountProgram(),
        lamports
    );
  }

  @Override
  public Instruction unWrapSol() {
    return GlamProgram.wsolUnwrap(
        invokedProgram,
        feePayer,
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        wrappedSolPDA.publicKey(),
        solanaAccounts.wrappedSolTokenMint(),
        solanaAccounts.tokenProgram()
    );
  }

  @Override
  public Instruction createTreasuryATA(final PublicKey programDerivedAddress, final PublicKey mint) {
    return AssociatedTokenProgram.createATA(
        false,
        solanaAccounts,
        feePayer,
        programDerivedAddress,
        glamFundAccounts.treasuryPublicKey(),
        mint
    );
  }

  @Override
  public FundPDA createStakeAccountPDA() {
    return FundPDA.createPDA("stake_account", glamFundAccounts.fundPublicKey(), invokedProgram.publicKey());
  }

  @Override
  public Instruction initializeAndDelegateStake(final FundPDA stakeAccountPDA,
                                                final PublicKey validatorVoteAccount,
                                                final long lamports) {
    return GlamProgram.initializeAndDelegateStake(
        invokedProgram,
        feePayer,
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        stakeAccountPDA.pda().publicKey(),
        validatorVoteAccount,
        solanaAccounts.stakeConfig(),
        solanaAccounts.clockSysVar(),
        solanaAccounts.rentSysVar(),
        solanaAccounts.stakeHistorySysVar(),
        solanaAccounts.stakeProgram(),
        solanaAccounts.systemProgram(),
        lamports,
        stakeAccountPDA.accountId(),
        stakeAccountPDA.pda().nonce()
    );
  }

  private Instruction deactivateStakeAccounts() {
    return GlamProgram.deactivateStakeAccounts(
        invokedProgram,
        feePayer,
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        solanaAccounts.clockSysVar(),
        solanaAccounts.stakeProgram()
    );
  }

  @Override
  public Instruction deactivateStakeAccounts(final Collection<PublicKey> stakeAccounts) {
    return deactivateStakeAccounts().extraAccounts(stakeAccounts, AccountMeta.CREATE_WRITE);
  }

  @Override
  public Instruction deactivateStakeAccount(final PublicKey stakeAccount) {
    return deactivateStakeAccounts().extraAccount(stakeAccount, AccountMeta.CREATE_WRITE);
  }

  private Instruction withdrawFromStakeAccounts() {
    return GlamProgram.withdrawFromStakeAccounts(
        invokedProgram,
        feePayer,
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        solanaAccounts.clockSysVar(),
        solanaAccounts.stakeHistorySysVar(),
        solanaAccounts.stakeProgram()
    );
  }

  @Override
  public Instruction withdrawFromStakeAccounts(final Collection<PublicKey> stakeAccounts) {
    return withdrawFromStakeAccounts().extraAccounts(stakeAccounts, AccountMeta.CREATE_WRITE);
  }

  @Override
  public Instruction withdrawFromStakeAccount(final PublicKey stakeAccount) {
    return withdrawFromStakeAccounts().extraAccount(stakeAccount, AccountMeta.CREATE_WRITE);
  }
}
