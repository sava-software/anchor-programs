package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum GlobalConfigOption implements Borsh.Enum {

  EmergencyMode,
  BlockDeposit,
  BlockInvest,
  BlockWithdraw,
  BlockCollectFees,
  BlockCollectRewards,
  BlockSwapRewards,
  BlockSwapUnevenVaults,
  WithdrawalFeeBps,
  DeprecatedSwapDiscountBps,
  ActionsAuthority,
  DeprecatedTreasuryFeeVaults,
  AdminAuthority,
  BlockEmergencySwap,
  BlockLocalAdmin,
  UpdateTokenInfos,
  ScopeProgramId,
  ScopePriceId,
  MinPerformanceFeeBps,
  MinSwapUnevenSlippageToleranceBps,
  MinReferencePriceSlippageToleranceBps,
  ActionsAfterRebalanceDelaySeconds,
  TreasuryFeeVaultReceiver;

  public static GlobalConfigOption read(final byte[] _data, final int offset) {
    return Borsh.read(GlobalConfigOption.values(), _data, offset);
  }
}