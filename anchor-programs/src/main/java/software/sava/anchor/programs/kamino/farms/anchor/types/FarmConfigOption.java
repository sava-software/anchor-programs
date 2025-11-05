package software.sava.anchor.programs.kamino.farms.anchor.types;

import software.sava.core.borsh.Borsh;

public enum FarmConfigOption implements Borsh.Enum {

  UpdateRewardRps,
  UpdateRewardMinClaimDuration,
  WithdrawAuthority,
  DepositWarmupPeriod,
  WithdrawCooldownPeriod,
  RewardType,
  RpsDecimals,
  LockingMode,
  LockingStartTimestamp,
  LockingDuration,
  LockingEarlyWithdrawalPenaltyBps,
  DepositCapAmount,
  SlashedAmountSpillAddress,
  ScopePricesAccount,
  ScopeOraclePriceId,
  ScopeOracleMaxAge,
  UpdateRewardScheduleCurvePoints,
  UpdatePendingFarmAdmin,
  UpdateStrategyId,
  UpdateDelegatedRpsAdmin,
  UpdateVaultId,
  UpdateExtraDelegatedAuthority,
  UpdateIsRewardUserOnceEnabled,
  UpdateDelegatedAuthority,
  UpdateIsHarvestingPermissionless;

  public static FarmConfigOption read(final byte[] _data, final int offset) {
    return Borsh.read(FarmConfigOption.values(), _data, offset);
  }
}