package software.sava.anchor.programs.glam;

import software.sava.solana.programs.stakepool.StakePoolAccounts;
import software.sava.solana.programs.stakepool.StakePoolProgramClient;

public interface GlamStakePoolProgramClient extends StakePoolProgramClient {

  static GlamStakePoolProgramClient createClient(final GlamProgramAccountClient glamClient,
                                                 final StakePoolAccounts stakePoolAccounts) {
    return new GlamStakePoolProgramClientImpl(glamClient, stakePoolAccounts);
  }

  static GlamStakePoolProgramClient createClient(final GlamProgramAccountClient glamClient) {
    return createClient(glamClient, StakePoolAccounts.MAIN_NET);
  }
}
