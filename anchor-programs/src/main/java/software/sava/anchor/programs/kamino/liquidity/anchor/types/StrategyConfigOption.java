package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum StrategyConfigOption implements Borsh.Enum {

  UpdateDepositCap,
  UpdateDepositCapIxn,
  UpdateWithdrawalCapACapacity,
  UpdateWithdrawalCapAInterval,
  UpdateWithdrawalCapACurrentTotal,
  UpdateWithdrawalCapBCapacity,
  UpdateWithdrawalCapBInterval,
  UpdateWithdrawalCapBCurrentTotal,
  UpdateMaxDeviationBps,
  UpdateSwapVaultMaxSlippage,
  UpdateStrategyType,
  UpdateDepositFee,
  UpdateWithdrawFee,
  UpdateCollectFeesFee,
  UpdateReward0Fee,
  UpdateReward1Fee,
  UpdateReward2Fee,
  UpdateAdminAuthority,
  KaminoRewardIndex0TS,
  KaminoRewardIndex1TS,
  KaminoRewardIndex2TS,
  KaminoRewardIndex0RewardPerSecond,
  KaminoRewardIndex1RewardPerSecond,
  KaminoRewardIndex2RewardPerSecond,
  UpdateDepositBlocked,
  UpdateRaydiumProtocolPositionOrBaseVaultAuthority,
  UpdateRaydiumPoolConfigOrBaseVaultAuthority,
  UpdateInvestBlocked,
  UpdateWithdrawBlocked,
  UpdateLocalAdminBlocked,
  DeprecatedUpdateCollateralIdA,
  DeprecatedUpdateCollateralIdB,
  UpdateFlashVaultSwap,
  AllowDepositWithoutInvest,
  UpdateSwapVaultMaxSlippageFromRef,
  ResetReferencePrices,
  UpdateStrategyCreationState,
  UpdateIsCommunity,
  UpdateRebalanceType,
  UpdateRebalanceParams,
  UpdateDepositMintingMethod,
  UpdateLookupTable,
  UpdateReferencePriceType,
  UpdateReward0Amount,
  UpdateReward1Amount,
  UpdateReward2Amount,
  UpdateFarm,
  UpdateRebalancesCapCapacity,
  UpdateRebalancesCapInterval,
  UpdateRebalancesCapCurrentTotal,
  UpdateSwapUnevenAuthority,
  UpdatePendingStrategyAdmin;

  public static StrategyConfigOption read(final byte[] _data, final int offset) {
    return Borsh.read(StrategyConfigOption.values(), _data, offset);
  }
}