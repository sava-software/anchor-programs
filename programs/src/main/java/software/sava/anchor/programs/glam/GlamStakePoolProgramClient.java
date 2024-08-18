package software.sava.anchor.programs.glam;

import software.sava.core.accounts.PublicKey;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.stakepool.StakePoolAccounts;
import software.sava.solana.programs.stakepool.StakePoolProgramClient;
import software.sava.solana.programs.stakepool.StakePoolState;

public interface GlamStakePoolProgramClient extends StakePoolProgramClient {

  static GlamStakePoolProgramClient createClient(final GlamProgramAccountClient glamClient,
                                                 final StakePoolAccounts stakePoolAccounts) {
    return new GlamStakePoolProgramClientImpl(glamClient, stakePoolAccounts);
  }

  static GlamStakePoolProgramClient createClient(final GlamProgramAccountClient glamClient) {
    return createClient(glamClient, StakePoolAccounts.MAIN_NET);
  }

  Instruction withdrawStake(final AccountInfo<StakePoolState> stakePoolStateAccountInfo,
                            final PublicKey validatorOrReserveStakeAccount,
                            final FundPDA stakeAccountPDA,
                            final PublicKey poolTokenATA,
                            final long poolTokenAmount);
}
