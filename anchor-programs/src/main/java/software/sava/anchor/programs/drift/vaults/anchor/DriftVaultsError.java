package software.sava.anchor.programs.drift.vaults.anchor;

import software.sava.anchor.programs._commons.ProgramError;

public sealed interface DriftVaultsError extends ProgramError permits
    DriftVaultsError.Default,
    DriftVaultsError.InvalidVaultRebase,
    DriftVaultsError.InvalidVaultSharesDetected,
    DriftVaultsError.CannotWithdrawBeforeRedeemPeriodEnd,
    DriftVaultsError.InvalidVaultWithdraw,
    DriftVaultsError.InsufficientVaultShares,
    DriftVaultsError.InvalidVaultWithdrawSize,
    DriftVaultsError.InvalidVaultForNewDepositors,
    DriftVaultsError.VaultWithdrawRequestInProgress,
    DriftVaultsError.VaultIsAtCapacity,
    DriftVaultsError.InvalidVaultDepositorInitialization,
    DriftVaultsError.DelegateNotAvailableForLiquidation,
    DriftVaultsError.InvalidEquityValue,
    DriftVaultsError.VaultInLiquidation,
    DriftVaultsError.DriftError,
    DriftVaultsError.InvalidVaultInitialization,
    DriftVaultsError.InvalidVaultUpdate,
    DriftVaultsError.PermissionedVault,
    DriftVaultsError.WithdrawInProgress,
    DriftVaultsError.SharesPercentTooLarge,
    DriftVaultsError.InvalidVaultDeposit,
    DriftVaultsError.OngoingLiquidation,
    DriftVaultsError.VaultProtocolMissing,
    DriftVaultsError.InvalidTokenization,
    DriftVaultsError.InvalidFuelDistributionMode,
    DriftVaultsError.FeeUpdateMissing,
    DriftVaultsError.InvalidFeeUpdateStatus,
    DriftVaultsError.InvalidVaultClass,
    DriftVaultsError.InvalidBorrowAmount,
    DriftVaultsError.InvalidRepayAmount {

  static DriftVaultsError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> Default.INSTANCE;
      case 6001 -> InvalidVaultRebase.INSTANCE;
      case 6002 -> InvalidVaultSharesDetected.INSTANCE;
      case 6003 -> CannotWithdrawBeforeRedeemPeriodEnd.INSTANCE;
      case 6004 -> InvalidVaultWithdraw.INSTANCE;
      case 6005 -> InsufficientVaultShares.INSTANCE;
      case 6006 -> InvalidVaultWithdrawSize.INSTANCE;
      case 6007 -> InvalidVaultForNewDepositors.INSTANCE;
      case 6008 -> VaultWithdrawRequestInProgress.INSTANCE;
      case 6009 -> VaultIsAtCapacity.INSTANCE;
      case 6010 -> InvalidVaultDepositorInitialization.INSTANCE;
      case 6011 -> DelegateNotAvailableForLiquidation.INSTANCE;
      case 6012 -> InvalidEquityValue.INSTANCE;
      case 6013 -> VaultInLiquidation.INSTANCE;
      case 6014 -> DriftError.INSTANCE;
      case 6015 -> InvalidVaultInitialization.INSTANCE;
      case 6016 -> InvalidVaultUpdate.INSTANCE;
      case 6017 -> PermissionedVault.INSTANCE;
      case 6018 -> WithdrawInProgress.INSTANCE;
      case 6019 -> SharesPercentTooLarge.INSTANCE;
      case 6020 -> InvalidVaultDeposit.INSTANCE;
      case 6021 -> OngoingLiquidation.INSTANCE;
      case 6022 -> VaultProtocolMissing.INSTANCE;
      case 6023 -> InvalidTokenization.INSTANCE;
      case 6024 -> InvalidFuelDistributionMode.INSTANCE;
      case 6025 -> FeeUpdateMissing.INSTANCE;
      case 6026 -> InvalidFeeUpdateStatus.INSTANCE;
      case 6027 -> InvalidVaultClass.INSTANCE;
      case 6028 -> InvalidBorrowAmount.INSTANCE;
      case 6029 -> InvalidRepayAmount.INSTANCE;
      default -> throw new IllegalStateException("Unexpected DriftVaults error code: " + errorCode);
    };
  }

  record Default(int code, String msg) implements DriftVaultsError {

    public static final Default INSTANCE = new Default(
        6000, "Default"
    );
  }

  record InvalidVaultRebase(int code, String msg) implements DriftVaultsError {

    public static final InvalidVaultRebase INSTANCE = new InvalidVaultRebase(
        6001, "InvalidVaultRebase"
    );
  }

  record InvalidVaultSharesDetected(int code, String msg) implements DriftVaultsError {

    public static final InvalidVaultSharesDetected INSTANCE = new InvalidVaultSharesDetected(
        6002, "InvalidVaultSharesDetected"
    );
  }

  record CannotWithdrawBeforeRedeemPeriodEnd(int code, String msg) implements DriftVaultsError {

    public static final CannotWithdrawBeforeRedeemPeriodEnd INSTANCE = new CannotWithdrawBeforeRedeemPeriodEnd(
        6003, "CannotWithdrawBeforeRedeemPeriodEnd"
    );
  }

  record InvalidVaultWithdraw(int code, String msg) implements DriftVaultsError {

    public static final InvalidVaultWithdraw INSTANCE = new InvalidVaultWithdraw(
        6004, "InvalidVaultWithdraw"
    );
  }

  record InsufficientVaultShares(int code, String msg) implements DriftVaultsError {

    public static final InsufficientVaultShares INSTANCE = new InsufficientVaultShares(
        6005, "InsufficientVaultShares"
    );
  }

  record InvalidVaultWithdrawSize(int code, String msg) implements DriftVaultsError {

    public static final InvalidVaultWithdrawSize INSTANCE = new InvalidVaultWithdrawSize(
        6006, "InvalidVaultWithdrawSize"
    );
  }

  record InvalidVaultForNewDepositors(int code, String msg) implements DriftVaultsError {

    public static final InvalidVaultForNewDepositors INSTANCE = new InvalidVaultForNewDepositors(
        6007, "InvalidVaultForNewDepositors"
    );
  }

  record VaultWithdrawRequestInProgress(int code, String msg) implements DriftVaultsError {

    public static final VaultWithdrawRequestInProgress INSTANCE = new VaultWithdrawRequestInProgress(
        6008, "VaultWithdrawRequestInProgress"
    );
  }

  record VaultIsAtCapacity(int code, String msg) implements DriftVaultsError {

    public static final VaultIsAtCapacity INSTANCE = new VaultIsAtCapacity(
        6009, "VaultIsAtCapacity"
    );
  }

  record InvalidVaultDepositorInitialization(int code, String msg) implements DriftVaultsError {

    public static final InvalidVaultDepositorInitialization INSTANCE = new InvalidVaultDepositorInitialization(
        6010, "InvalidVaultDepositorInitialization"
    );
  }

  record DelegateNotAvailableForLiquidation(int code, String msg) implements DriftVaultsError {

    public static final DelegateNotAvailableForLiquidation INSTANCE = new DelegateNotAvailableForLiquidation(
        6011, "DelegateNotAvailableForLiquidation"
    );
  }

  record InvalidEquityValue(int code, String msg) implements DriftVaultsError {

    public static final InvalidEquityValue INSTANCE = new InvalidEquityValue(
        6012, "InvalidEquityValue"
    );
  }

  record VaultInLiquidation(int code, String msg) implements DriftVaultsError {

    public static final VaultInLiquidation INSTANCE = new VaultInLiquidation(
        6013, "VaultInLiquidation"
    );
  }

  record DriftError(int code, String msg) implements DriftVaultsError {

    public static final DriftError INSTANCE = new DriftError(
        6014, "DriftError"
    );
  }

  record InvalidVaultInitialization(int code, String msg) implements DriftVaultsError {

    public static final InvalidVaultInitialization INSTANCE = new InvalidVaultInitialization(
        6015, "InvalidVaultInitialization"
    );
  }

  record InvalidVaultUpdate(int code, String msg) implements DriftVaultsError {

    public static final InvalidVaultUpdate INSTANCE = new InvalidVaultUpdate(
        6016, "InvalidVaultUpdate"
    );
  }

  record PermissionedVault(int code, String msg) implements DriftVaultsError {

    public static final PermissionedVault INSTANCE = new PermissionedVault(
        6017, "PermissionedVault"
    );
  }

  record WithdrawInProgress(int code, String msg) implements DriftVaultsError {

    public static final WithdrawInProgress INSTANCE = new WithdrawInProgress(
        6018, "WithdrawInProgress"
    );
  }

  record SharesPercentTooLarge(int code, String msg) implements DriftVaultsError {

    public static final SharesPercentTooLarge INSTANCE = new SharesPercentTooLarge(
        6019, "SharesPercentTooLarge"
    );
  }

  record InvalidVaultDeposit(int code, String msg) implements DriftVaultsError {

    public static final InvalidVaultDeposit INSTANCE = new InvalidVaultDeposit(
        6020, "InvalidVaultDeposit"
    );
  }

  record OngoingLiquidation(int code, String msg) implements DriftVaultsError {

    public static final OngoingLiquidation INSTANCE = new OngoingLiquidation(
        6021, "OngoingLiquidation"
    );
  }

  record VaultProtocolMissing(int code, String msg) implements DriftVaultsError {

    public static final VaultProtocolMissing INSTANCE = new VaultProtocolMissing(
        6022, "VaultProtocolMissing"
    );
  }

  record InvalidTokenization(int code, String msg) implements DriftVaultsError {

    public static final InvalidTokenization INSTANCE = new InvalidTokenization(
        6023, "InvalidTokenization"
    );
  }

  record InvalidFuelDistributionMode(int code, String msg) implements DriftVaultsError {

    public static final InvalidFuelDistributionMode INSTANCE = new InvalidFuelDistributionMode(
        6024, "InvalidFuelDistributionMode"
    );
  }

  record FeeUpdateMissing(int code, String msg) implements DriftVaultsError {

    public static final FeeUpdateMissing INSTANCE = new FeeUpdateMissing(
        6025, "FeeUpdateMissing"
    );
  }

  record InvalidFeeUpdateStatus(int code, String msg) implements DriftVaultsError {

    public static final InvalidFeeUpdateStatus INSTANCE = new InvalidFeeUpdateStatus(
        6026, "InvalidFeeUpdateStatus"
    );
  }

  record InvalidVaultClass(int code, String msg) implements DriftVaultsError {

    public static final InvalidVaultClass INSTANCE = new InvalidVaultClass(
        6027, "InvalidVaultClass"
    );
  }

  record InvalidBorrowAmount(int code, String msg) implements DriftVaultsError {

    public static final InvalidBorrowAmount INSTANCE = new InvalidBorrowAmount(
        6028, "InvalidBorrowAmount"
    );
  }

  record InvalidRepayAmount(int code, String msg) implements DriftVaultsError {

    public static final InvalidRepayAmount INSTANCE = new InvalidRepayAmount(
        6029, "InvalidRepayAmount"
    );
  }
}
