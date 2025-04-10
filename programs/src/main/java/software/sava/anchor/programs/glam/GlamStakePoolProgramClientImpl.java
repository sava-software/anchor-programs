package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamProtocolProgram;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramAccountClient;
import software.sava.solana.programs.stakepool.StakePoolAccounts;
import software.sava.solana.programs.stakepool.StakePoolProgram;
import software.sava.solana.programs.stakepool.StakePoolProgramClient;
import software.sava.solana.programs.stakepool.StakePoolState;

final class GlamStakePoolProgramClientImpl implements GlamStakePoolProgramClient {

  private final GlamProgramAccountClient glamProgramAccountClient;
  private final GlamVaultAccounts glamVaultAccounts;
  private final AccountMeta invokedProgram;
  private final AccountMeta feePayer;
  private final SolanaAccounts solanaAccounts;
  private final StakePoolAccounts stakePoolAccounts;
  private final StakePoolProgramClient stakePoolProgramClient;

  GlamStakePoolProgramClientImpl(final GlamProgramAccountClient glamProgramAccountClient,
                                 final StakePoolAccounts stakePoolAccounts) {
    this.glamProgramAccountClient = glamProgramAccountClient;
    this.glamVaultAccounts = glamProgramAccountClient.vaultAccounts();
    this.invokedProgram = glamVaultAccounts.glamAccounts().invokedProgram();
    this.feePayer = glamProgramAccountClient.feePayer();
    this.solanaAccounts = glamProgramAccountClient.solanaAccounts();
    this.stakePoolAccounts = stakePoolAccounts;
    this.stakePoolProgramClient = StakePoolProgramClient.createClient(glamProgramAccountClient, stakePoolAccounts);
  }

  @Override
  public NativeProgramAccountClient nativeProgramAccountClient() {
    return glamProgramAccountClient;
  }

  @Override
  public SolanaAccounts solanaAccounts() {
    return solanaAccounts;
  }

  @Override
  public StakePoolAccounts stakePoolAccounts() {
    return stakePoolAccounts;
  }

  @Override
  public PublicKey ownerPublicKey() {
    return glamVaultAccounts.vaultPublicKey();
  }

  @Override
  public Instruction depositSol(final PublicKey stakePoolProgram,
                                final StakePoolState stakePoolState,
                                final PublicKey poolTokenATA,
                                final long lamportsIn) {
    final var stakePoolWithdrawAuthority = StakePoolProgram.findStakePoolWithdrawAuthority(stakePoolState.address(), stakePoolProgram);
    return GlamProtocolProgram.stakePoolDepositSol(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        stakePoolProgram,
        stakePoolState.address(),
        stakePoolWithdrawAuthority.publicKey(),
        stakePoolState.reserveStake(),
        poolTokenATA,
        stakePoolState.managerFeeAccount(),
        glamVaultAccounts.vaultPublicKey(),
        stakePoolState.poolMint(),
        stakePoolState.tokenProgramId(),
        lamportsIn
    );
  }

  @Override
  public Instruction depositSolWithSlippage(final PublicKey stakePoolProgram,
                                            final StakePoolState stakePoolState,
                                            final PublicKey poolTokenATA,
                                            final long lamportsIn,
                                            final long minimumPoolTokensOut) {
    final var stakePoolWithdrawAuthority = StakePoolProgram.findStakePoolWithdrawAuthority(stakePoolState.address(), stakePoolProgram);
    return GlamProtocolProgram.stakePoolDepositSolWithSlippage(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        stakePoolProgram,
        stakePoolState.address(),
        stakePoolWithdrawAuthority.publicKey(),
        stakePoolState.reserveStake(),
        poolTokenATA,
        stakePoolState.managerFeeAccount(),
        glamVaultAccounts.vaultPublicKey(),
        stakePoolState.poolMint(),
        stakePoolState.tokenProgramId(),
        lamportsIn,
        minimumPoolTokensOut
    );
  }

  @Override
  public Instruction depositStake(final PublicKey stakePoolProgram,
                                  final StakePoolState stakePoolState,
                                  final PublicKey depositStakeAccount,
                                  final PublicKey validatorStakeAccount,
                                  final PublicKey poolTokenATA) {
    final var stakePoolWithdrawAuthority = StakePoolProgram.findStakePoolWithdrawAuthority(stakePoolState.address(), stakePoolProgram);
    return GlamProtocolProgram.stakePoolDepositStake(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        stakePoolProgram,
        stakePoolState.address(),
        stakePoolState.validatorList(),
        stakePoolWithdrawAuthority.publicKey(),
        depositStakeAccount,
        validatorStakeAccount,
        stakePoolState.reserveStake(),
        poolTokenATA,
        stakePoolState.managerFeeAccount(),
        glamVaultAccounts.vaultPublicKey(),
        stakePoolState.poolMint(),
        stakePoolState.tokenProgramId()
    );
  }

  @Override
  public Instruction depositStakeWithSlippage(final PublicKey stakePoolProgram,
                                              final StakePoolState stakePoolState,
                                              final PublicKey depositStakeAccount,
                                              final PublicKey validatorStakeAccount,
                                              final PublicKey poolTokenATA,
                                              final long minimumPoolTokensOut) {
    final var stakePoolWithdrawAuthority = StakePoolProgram.findStakePoolWithdrawAuthority(stakePoolState.address(), stakePoolProgram);
    return GlamProtocolProgram.stakePoolDepositStakeWithSlippage(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        stakePoolProgram,
        stakePoolState.address(),
        stakePoolState.validatorList(),
        stakePoolWithdrawAuthority.publicKey(),
        depositStakeAccount,
        validatorStakeAccount,
        stakePoolState.reserveStake(),
        poolTokenATA,
        stakePoolState.managerFeeAccount(),
        glamVaultAccounts.vaultPublicKey(),
        stakePoolState.poolMint(),
        stakePoolState.tokenProgramId(),
        minimumPoolTokensOut
    );
  }

  @Override
  public Instruction withdrawSol(final PublicKey stakePoolProgram,
                                 final StakePoolState stakePoolState,
                                 final PublicKey poolTokenATA,
                                 final long poolTokenAmount) {
    final var stakePoolWithdrawAuthority = StakePoolProgram.findStakePoolWithdrawAuthority(stakePoolState.address(), stakePoolProgram);
    return GlamProtocolProgram.stakePoolWithdrawSol(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        stakePoolProgram,
        stakePoolState.address(),
        stakePoolWithdrawAuthority.publicKey(),
        poolTokenATA,
        stakePoolState.reserveStake(),
        stakePoolState.managerFeeAccount(),
        stakePoolState.poolMint(),
        stakePoolState.tokenProgramId(),
        poolTokenAmount
    );
  }

  @Override
  public Instruction withdrawSolWithSlippage(final PublicKey stakePoolProgram,
                                             final StakePoolState stakePoolState,
                                             final PublicKey poolTokenATA,
                                             final long poolTokenAmount,
                                             final long lamportsOut) {
    final var stakePoolWithdrawAuthority = StakePoolProgram.findStakePoolWithdrawAuthority(stakePoolState.address(), stakePoolProgram);
    return GlamProtocolProgram.stakePoolWithdrawSolWithSlippage(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        stakePoolProgram,
        stakePoolState.address(),
        stakePoolWithdrawAuthority.publicKey(),
        poolTokenATA,
        stakePoolState.reserveStake(),
        stakePoolState.managerFeeAccount(),
        stakePoolState.poolMint(),
        stakePoolState.tokenProgramId(),
        poolTokenAmount,
        lamportsOut
    );
  }

  @Override
  public Instruction withdrawStake(final PublicKey poolProgram,
                                   final StakePoolState stakePoolState,
                                   final PublicKey validatorOrReserveStakeAccount,
                                   final PublicKey uninitializedStakeAccount,
                                   final PublicKey stakeAccountWithdrawalAuthority,
                                   final PublicKey poolTokenATA,
                                   final long poolTokenAmount) {
    final var stakePoolWithdrawAuthority = StakePoolProgram.findStakePoolWithdrawAuthority(stakePoolState.address(), poolProgram);
    return GlamProtocolProgram.stakePoolWithdrawStake(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        poolProgram,
        stakePoolState.address(),
        stakePoolState.validatorList(),
        stakePoolWithdrawAuthority.publicKey(),
        validatorOrReserveStakeAccount,
        uninitializedStakeAccount,
        poolTokenATA,
        stakePoolState.managerFeeAccount(),
        stakePoolState.poolMint(),
        stakePoolState.tokenProgramId(),
        poolTokenAmount
    );
  }

  @Override
  public Instruction withdrawStakeWithSlippage(final PublicKey poolProgram,
                                               final StakePoolState stakePoolState,
                                               final PublicKey validatorOrReserveStakeAccount,
                                               final PublicKey uninitializedStakeAccount,
                                               final PublicKey stakeAccountWithdrawalAuthority,
                                               final PublicKey poolTokenATA,
                                               final long poolTokenAmount,
                                               final long lamportsOut) {
    final var stakePoolWithdrawAuthority = StakePoolProgram.findStakePoolWithdrawAuthority(stakePoolState.address(), poolProgram);
    return GlamProtocolProgram.stakePoolWithdrawStakeWithSlippage(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        poolProgram,
        stakePoolState.address(),
        stakePoolState.validatorList(),
        stakePoolWithdrawAuthority.publicKey(),
        validatorOrReserveStakeAccount,
        uninitializedStakeAccount,
        poolTokenATA,
        stakePoolState.managerFeeAccount(),
        stakePoolState.poolMint(),
        stakePoolState.tokenProgramId(),
        poolTokenAmount,
        lamportsOut
    );
  }

  @Override
  public Instruction updateStakePoolBalance(final AccountInfo<StakePoolState> stakePoolStateAccountInfo) {
    return stakePoolProgramClient.updateStakePoolBalance(stakePoolStateAccountInfo);
  }
}
