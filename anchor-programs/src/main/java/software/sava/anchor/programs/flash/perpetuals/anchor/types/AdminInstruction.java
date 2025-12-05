package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public enum AdminInstruction implements Borsh.Enum {

  AddPool,
  RemovePool,
  AddCustody,
  RemoveCustody,
  Reimburse,
  AddMarket,
  RemoveMarket,
  InitStaking,
  SetAdminSigners,
  SetCustodyConfig,
  SetPermissions,
  SetBorrowRate,
  SetPerpetualsConfig,
  SetPoolConfig,
  SetFlpStakeConfig,
  SetMarketConfig,
  AddCollection,
  WithdrawFees,
  WithdrawSolFees,
  SetCustomOraclePrice,
  SetTestTime,
  UpdateCustody,
  UpdateTokenRatios,
  AddInternalOracle,
  RenameFlp,
  SetFeeShare,
  InitCompounding,
  InitTokenVault,
  SetTokenVaultConfig,
  WithdrawInstantFees,
  DistributeTokenReward,
  SetTokenReward,
  SetTokenStakeLevel,
  InitRevenueTokenAccount,
  ResizeInternalOracle,
  WithdrawUnclaimedTokens,
  InitRebateVault;

  public static AdminInstruction read(final byte[] _data, final int offset) {
    return Borsh.read(AdminInstruction.values(), _data, offset);
  }
}