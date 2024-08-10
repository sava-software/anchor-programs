package software.sava.anchor.programs.glam;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.stakepool.StakePoolAccounts;
import software.sava.solana.programs.stakepool.StakePoolState;
import software.sava.anchor.programs.glam.anchor.GlamProgram;

import static software.sava.solana.programs.stakepool.StakePoolProgramClient.findStakePoolWithdrawAuthority;

final class GlamStakePoolProgramClientImpl implements GlamStakePoolProgramClient {

  private final GlamNativeClient glamNativeClient;
  private final GlamFundAccounts glamFundAccounts;
  private final AccountMeta invokedProgram;
  private final AccountMeta feePayer;
  private final SolanaAccounts accounts;
  private final StakePoolAccounts stakePoolAccounts;

  GlamStakePoolProgramClientImpl(final GlamNativeClient glamNativeClient,
                                 final StakePoolAccounts stakePoolAccounts) {
    this.glamNativeClient = glamNativeClient;
    this.glamFundAccounts = glamNativeClient.fundAccounts();
    this.invokedProgram = glamFundAccounts.glamAccounts().invokedProgram();
    this.feePayer = glamNativeClient.feePayer();
    this.accounts = glamNativeClient.solanaAccounts();
    this.stakePoolAccounts = stakePoolAccounts;
  }

  @Override
  public Instruction depositSol(final AccountInfo<StakePoolState> stakePoolStateAccountInfo,
                                final PublicKey poolTokenATA,
                                final long lamportsIn) {
    final var stakePoolState = stakePoolStateAccountInfo.data();
    final var stakePoolWithdrawAuthority = findStakePoolWithdrawAuthority(stakePoolStateAccountInfo);
    return GlamProgram.stakePoolDeposit(
        invokedProgram,
        feePayer,
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        stakePoolStateAccountInfo.owner(),
        stakePoolState.address(),
        stakePoolWithdrawAuthority.publicKey(),
        stakePoolState.reserveStake(),
        stakePoolState.poolMint(),
        stakePoolState.managerFeeAccount(),
        poolTokenATA,
        accounts.associatedTokenAccountProgram(),
        accounts.systemProgram(),
        stakePoolState.tokenProgramId(),
        lamportsIn
    );
  }

  @Override
  public Instruction withdrawSol(final AccountInfo<StakePoolState> stakePoolStateAccountInfo,
                                 final PublicKey poolTokenATA,
                                 final long poolTokenAmount) {
    final var stakePoolState = stakePoolStateAccountInfo.data();
    final var stakePoolWithdrawAuthority = findStakePoolWithdrawAuthority(stakePoolStateAccountInfo);
    return GlamProgram.stakePoolWithdrawSol(
        invokedProgram,
        feePayer,
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        stakePoolStateAccountInfo.owner(),
        stakePoolState.address(),
        stakePoolWithdrawAuthority.publicKey(),
        stakePoolState.reserveStake(),
        stakePoolState.poolMint(),
        stakePoolState.managerFeeAccount(),
        poolTokenATA,
        accounts.clockSysVar(),
        accounts.stakeHistorySysVar(),
        accounts.systemProgram(),
        stakePoolState.tokenProgramId(),
        accounts.stakeProgram(),
        poolTokenAmount
    );
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
        feePayer,
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
        accounts.clockSysVar(),
        accounts.systemProgram(),
        stakePoolState.tokenProgramId(),
        accounts.stakeProgram(),
        poolTokenAmount,
        stakeAccountPDA.accountId(),
        stakeAccountPDA.pda().nonce()
    );
  }
}
