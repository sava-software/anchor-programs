package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamProgram;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramAccountClient;
import software.sava.solana.programs.stakepool.StakePoolAccounts;
import software.sava.solana.programs.stakepool.StakePoolProgramClient;
import software.sava.solana.programs.stakepool.StakePoolState;

import static software.sava.solana.programs.stakepool.StakePoolProgramClient.findStakePoolWithdrawAuthority;

final class GlamStakePoolProgramClientImpl implements GlamStakePoolProgramClient {

  private final GlamProgramAccountClient glamProgramAccountClient;
  private final GlamFundAccounts glamFundAccounts;
  private final AccountMeta invokedProgram;
  private final AccountMeta manager;
  private final SolanaAccounts solanaAccounts;
  private final StakePoolAccounts stakePoolAccounts;
  private final StakePoolProgramClient stakePoolProgramClient;

  GlamStakePoolProgramClientImpl(final GlamProgramAccountClient glamProgramAccountClient,
                                 final StakePoolAccounts stakePoolAccounts) {
    this.glamProgramAccountClient = glamProgramAccountClient;
    this.glamFundAccounts = glamProgramAccountClient.fundAccounts();
    this.invokedProgram = glamFundAccounts.glamAccounts().invokedProgram();
    this.manager = glamProgramAccountClient.feePayer();
    this.solanaAccounts = glamProgramAccountClient.solanaAccounts();
    this.stakePoolAccounts = stakePoolAccounts;
    this.stakePoolProgramClient = StakePoolProgramClient.createClient(glamProgramAccountClient, stakePoolAccounts);
  }

  @Override
  public NativeProgramAccountClient nativeProgramAccountClient() {
    return glamProgramAccountClient;
  }

  @Override
  public StakePoolAccounts stakePoolAccounts() {
    return stakePoolAccounts;
  }

  @Override
  public Instruction depositSol(final AccountInfo<StakePoolState> stakePoolStateAccountInfo,
                                final PublicKey poolTokenATA,
                                final long lamportsIn) {
    final var stakePoolState = stakePoolStateAccountInfo.data();
    final var stakePoolWithdrawAuthority = findStakePoolWithdrawAuthority(stakePoolStateAccountInfo);
    return GlamProgram.stakePoolDepositSol(
        invokedProgram,
        solanaAccounts,
        manager.publicKey(),
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        stakePoolStateAccountInfo.owner(),
        stakePoolState.address(),
        stakePoolWithdrawAuthority.publicKey(),
        stakePoolState.reserveStake(),
        stakePoolState.poolMint(),
        stakePoolState.managerFeeAccount(),
        poolTokenATA,
        stakePoolState.tokenProgramId(),
        lamportsIn
    );
  }

  @Override
  public Instruction depositSolWithSlippage(final AccountInfo<StakePoolState> stakePoolStateAccountInfo,
                                            final PublicKey poolTokenATA,
                                            final long lamportsIn,
                                            final long minimumPoolTokensOut) {
    throw new UnsupportedOperationException("TODO: depositSolWithSlippage");
  }

  @Override
  public Instruction depositStake(final AccountInfo<StakePoolState> stakePoolStateAccountInfo,
                                  final PublicKey depositStakeAccount,
                                  final PublicKey validatorStakeAccount,
                                  final PublicKey poolTokenATA) {
    final var stakePoolState = stakePoolStateAccountInfo.data();
    final var stakePoolWithdrawAuthority = findStakePoolWithdrawAuthority(stakePoolStateAccountInfo);
    return GlamProgram.stakePoolDepositStake(
        invokedProgram,
        solanaAccounts,
        manager.publicKey(),
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        depositStakeAccount,
        poolTokenATA,
        stakePoolState.poolMint(),
        stakePoolState.managerFeeAccount(),
        stakePoolState.address(),
        stakePoolState.stakeDepositAuthority(),
        stakePoolWithdrawAuthority.publicKey(),
        stakePoolState.validatorList(),
        validatorStakeAccount,
        stakePoolState.reserveStake(),
        stakePoolStateAccountInfo.owner(),
        stakePoolState.tokenProgramId()
    );
  }

  @Override
  public Instruction depositStakeWithSlippage(final AccountInfo<StakePoolState> stakePoolStateAccountInfo,
                                              final PublicKey depositStakeAccount,
                                              final PublicKey validatorStakeAccount,
                                              final PublicKey poolTokenATA,
                                              final long minimumPoolTokensOut) {
    throw new UnsupportedOperationException("TODO: depositStakeWithSlippage");
  }

  @Override
  public Instruction withdrawSol(final AccountInfo<StakePoolState> stakePoolStateAccountInfo,
                                 final PublicKey poolTokenATA,
                                 final long poolTokenAmount) {
    final var stakePoolState = stakePoolStateAccountInfo.data();
    final var stakePoolWithdrawAuthority = findStakePoolWithdrawAuthority(stakePoolStateAccountInfo);
    return GlamProgram.stakePoolWithdrawSol(
        invokedProgram,
        solanaAccounts,
        manager.publicKey(),
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        stakePoolStateAccountInfo.owner(),
        stakePoolState.address(),
        stakePoolWithdrawAuthority.publicKey(),
        stakePoolState.reserveStake(),
        stakePoolState.poolMint(),
        stakePoolState.managerFeeAccount(),
        poolTokenATA,
        stakePoolState.tokenProgramId(),
        poolTokenAmount
    );
  }

  @Override
  public Instruction withdrawSolWithSlippage(final AccountInfo<StakePoolState> stakePoolStateAccountInfo,
                                             final PublicKey poolTokenATA,
                                             final long poolTokenAmount,
                                             final long lamportsOut) {
    throw new UnsupportedOperationException("TODO: withdrawSolWithSlippage");
  }

  @Override
  public Instruction withdrawStake(final AccountInfo<StakePoolState> stakePoolStateAccountInfo,
                                   final PublicKey validatorOrReserveStakeAccount,
                                   final PublicKey uninitializedStakeAccount,
                                   final PublicKey stakeAccountWithdrawalAuthority,
                                   final PublicKey poolTokenATA,
                                   final long poolTokenAmount) {
    throw new UnsupportedOperationException("Use withdrawStake with a provided FundPDA stake account.");
  }

  @Override
  public Instruction withdrawStake(final AccountInfo<StakePoolState> stakePoolStateAccountInfo,
                                   final PublicKey validatorOrReserveStakeAccount,
                                   final PublicKey uninitializedStakeAccount,
                                   final PublicKey poolTokenATA,
                                   final long poolTokenAmount) {
    throw new UnsupportedOperationException("Use withdrawStake with a provided FundPDA stake account.");
  }

  @Override
  public Instruction withdrawStake(final AccountInfo<StakePoolState> stakePoolStateAccountInfo,
                                   final PublicKey validatorOrReserveStakeAccount,
                                   final FundPDA stakeAccountPDA,
                                   final PublicKey poolTokenATA,
                                   final long poolTokenAmount) {
    final var stakePoolState = stakePoolStateAccountInfo.data();
    final var stakePoolWithdrawAuthority = findStakePoolWithdrawAuthority(stakePoolStateAccountInfo);
    return GlamProgram.stakePoolWithdrawStake(
        invokedProgram,
        solanaAccounts,
        manager.publicKey(),
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        stakeAccountPDA.pda().publicKey(),
        stakePoolState.poolMint(),
        stakePoolState.managerFeeAccount(),
        stakePoolState.address(),
        stakePoolWithdrawAuthority.publicKey(),
        stakePoolState.validatorList(),
        validatorOrReserveStakeAccount,
        poolTokenATA,
        stakePoolStateAccountInfo.owner(),
        stakePoolState.tokenProgramId(),
        poolTokenAmount,
        stakeAccountPDA.id(),
        stakeAccountPDA.pda().nonce()
    );
  }

  @Override
  public Instruction withdrawStakeWithSlippage(final AccountInfo<StakePoolState> stakePoolStateAccountInfo,
                                               final PublicKey validatorOrReserveStakeAccount,
                                               final PublicKey uninitializedStakeAccount,
                                               final PublicKey stakeAccountWithdrawalAuthority,
                                               final PublicKey poolTokenATA,
                                               final long poolTokenAmount,
                                               final long lamportsOut) {
    throw new UnsupportedOperationException("TODO: withdrawStakeWithSlippage");
  }

  @Override
  public Instruction withdrawStakeWithSlippage(final AccountInfo<StakePoolState> stakePoolStateAccountInfo,
                                               final PublicKey validatorOrReserveStakeAccount,
                                               final PublicKey uninitializedStakeAccount,
                                               final PublicKey poolTokenATA,
                                               final long poolTokenAmount,
                                               final long lamportsOut) {
    throw new UnsupportedOperationException("TODO: withdrawStakeWithSlippage");
  }

  @Override
  public Instruction updateStakePoolBalance(final AccountInfo<StakePoolState> stakePoolStateAccountInfo) {
    return stakePoolProgramClient.updateStakePoolBalance(stakePoolStateAccountInfo);
  }
}
