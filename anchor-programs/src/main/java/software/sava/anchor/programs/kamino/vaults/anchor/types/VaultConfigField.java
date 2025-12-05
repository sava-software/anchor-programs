package software.sava.anchor.programs.kamino.vaults.anchor.types;

import software.sava.core.borsh.Borsh;

public enum VaultConfigField implements Borsh.Enum {

  PerformanceFeeBps,
  ManagementFeeBps,
  MinDepositAmount,
  MinWithdrawAmount,
  MinInvestAmount,
  MinInvestDelaySlots,
  CrankFundFeePerReserve,
  PendingVaultAdmin,
  Name,
  LookupTable,
  Farm,
  AllocationAdmin,
  UnallocatedWeight,
  UnallocatedTokensCap,
  WithdrawalPenaltyLamports,
  WithdrawalPenaltyBps,
  FirstLossCapitalFarm,
  AllowAllocationsInWhitelistedReservesOnly,
  AllowInvestInWhitelistedReservesOnly;

  public static VaultConfigField read(final byte[] _data, final int offset) {
    return Borsh.read(VaultConfigField.values(), _data, offset);
  }
}