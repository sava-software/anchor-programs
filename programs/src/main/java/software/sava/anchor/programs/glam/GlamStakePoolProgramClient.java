package software.sava.anchor.programs.glam;

import software.sava.core.accounts.PublicKey;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.stakepool.StakePoolAccounts;
import software.sava.solana.programs.stakepool.StakePoolState;

public interface GlamStakePoolProgramClient {

  static GlamStakePoolProgramClient createClient(final GlamNativeClient glamClient, final StakePoolAccounts stakePoolAccounts) {
    return new GlamStakePoolProgramClientImpl(glamClient, stakePoolAccounts);
  }

  static GlamStakePoolProgramClient createClient(final GlamNativeClient glamClient) {
    return createClient(glamClient, StakePoolAccounts.MAIN_NET);
  }

  Instruction depositSol(final AccountInfo<StakePoolState> stakePoolStateAccountInfo,
                         final PublicKey poolTokenATA,
                         final long lamportsIn);

  Instruction withdrawSol(final AccountInfo<StakePoolState> stakePoolStateAccountInfo,
                          final PublicKey poolTokenATA,
                          final long poolTokenAmount);

  Instruction withdrawStake(final AccountInfo<StakePoolState> stakePoolStateAccountInfo,
                            final PublicKey validatorOrReserveStakeAccount,
                            final FundPDA stakeAccountPDA,
                            final PublicKey poolTokenATA,
                            final long poolTokenAmount);
}
