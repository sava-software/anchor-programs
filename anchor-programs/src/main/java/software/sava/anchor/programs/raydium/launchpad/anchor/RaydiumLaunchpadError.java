package software.sava.anchor.programs.raydium.launchpad.anchor;

import software.sava.anchor.ProgramError;

public sealed interface RaydiumLaunchpadError extends ProgramError permits
    RaydiumLaunchpadError.NotApproved,
    RaydiumLaunchpadError.InvalidOwner,
    RaydiumLaunchpadError.InvalidInput,
    RaydiumLaunchpadError.InputNotMatchCurveConfig,
    RaydiumLaunchpadError.ExceededSlippage,
    RaydiumLaunchpadError.PoolFunding,
    RaydiumLaunchpadError.PoolMigrated,
    RaydiumLaunchpadError.MigrateTypeNotMatch,
    RaydiumLaunchpadError.MathOverflow,
    RaydiumLaunchpadError.NoAssetsToCollect,
    RaydiumLaunchpadError.VestingRatioTooHigh,
    RaydiumLaunchpadError.VestingSettingEnded,
    RaydiumLaunchpadError.VestingNotStarted,
    RaydiumLaunchpadError.NoVestingSchedule,
    RaydiumLaunchpadError.InvalidPlatformInfo,
    RaydiumLaunchpadError.PoolNotMigrated {

  static RaydiumLaunchpadError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> NotApproved.INSTANCE;
      case 6001 -> InvalidOwner.INSTANCE;
      case 6002 -> InvalidInput.INSTANCE;
      case 6003 -> InputNotMatchCurveConfig.INSTANCE;
      case 6004 -> ExceededSlippage.INSTANCE;
      case 6005 -> PoolFunding.INSTANCE;
      case 6006 -> PoolMigrated.INSTANCE;
      case 6007 -> MigrateTypeNotMatch.INSTANCE;
      case 6008 -> MathOverflow.INSTANCE;
      case 6009 -> NoAssetsToCollect.INSTANCE;
      case 6010 -> VestingRatioTooHigh.INSTANCE;
      case 6011 -> VestingSettingEnded.INSTANCE;
      case 6012 -> VestingNotStarted.INSTANCE;
      case 6013 -> NoVestingSchedule.INSTANCE;
      case 6014 -> InvalidPlatformInfo.INSTANCE;
      case 6015 -> PoolNotMigrated.INSTANCE;
      default -> throw new IllegalStateException("Unexpected RaydiumLaunchpad error code: " + errorCode);
    };
  }

  record NotApproved(int code, String msg) implements RaydiumLaunchpadError {

    public static final NotApproved INSTANCE = new NotApproved(
        6000, "Not approved"
    );
  }

  record InvalidOwner(int code, String msg) implements RaydiumLaunchpadError {

    public static final InvalidOwner INSTANCE = new InvalidOwner(
        6001, "Input account owner is not the program address"
    );
  }

  record InvalidInput(int code, String msg) implements RaydiumLaunchpadError {

    public static final InvalidInput INSTANCE = new InvalidInput(
        6002, "InvalidInput"
    );
  }

  record InputNotMatchCurveConfig(int code, String msg) implements RaydiumLaunchpadError {

    public static final InputNotMatchCurveConfig INSTANCE = new InputNotMatchCurveConfig(
        6003, "The input params are not match with curve type in config"
    );
  }

  record ExceededSlippage(int code, String msg) implements RaydiumLaunchpadError {

    public static final ExceededSlippage INSTANCE = new ExceededSlippage(
        6004, "Exceeds desired slippage limit"
    );
  }

  record PoolFunding(int code, String msg) implements RaydiumLaunchpadError {

    public static final PoolFunding INSTANCE = new PoolFunding(
        6005, "Pool funding"
    );
  }

  record PoolMigrated(int code, String msg) implements RaydiumLaunchpadError {

    public static final PoolMigrated INSTANCE = new PoolMigrated(
        6006, "Pool migrated"
    );
  }

  record MigrateTypeNotMatch(int code, String msg) implements RaydiumLaunchpadError {

    public static final MigrateTypeNotMatch INSTANCE = new MigrateTypeNotMatch(
        6007, "Migrate type not match"
    );
  }

  record MathOverflow(int code, String msg) implements RaydiumLaunchpadError {

    public static final MathOverflow INSTANCE = new MathOverflow(
        6008, "Math overflow"
    );
  }

  record NoAssetsToCollect(int code, String msg) implements RaydiumLaunchpadError {

    public static final NoAssetsToCollect INSTANCE = new NoAssetsToCollect(
        6009, "No assets to collect"
    );
  }

  record VestingRatioTooHigh(int code, String msg) implements RaydiumLaunchpadError {

    public static final VestingRatioTooHigh INSTANCE = new VestingRatioTooHigh(
        6010, "Vesting ratio too high"
    );
  }

  record VestingSettingEnded(int code, String msg) implements RaydiumLaunchpadError {

    public static final VestingSettingEnded INSTANCE = new VestingSettingEnded(
        6011, "Vesting setting ended"
    );
  }

  record VestingNotStarted(int code, String msg) implements RaydiumLaunchpadError {

    public static final VestingNotStarted INSTANCE = new VestingNotStarted(
        6012, "Vesting not started"
    );
  }

  record NoVestingSchedule(int code, String msg) implements RaydiumLaunchpadError {

    public static final NoVestingSchedule INSTANCE = new NoVestingSchedule(
        6013, "No vesting schedule"
    );
  }

  record InvalidPlatformInfo(int code, String msg) implements RaydiumLaunchpadError {

    public static final InvalidPlatformInfo INSTANCE = new InvalidPlatformInfo(
        6014, "The platform info input is invalid"
    );
  }

  record PoolNotMigrated(int code, String msg) implements RaydiumLaunchpadError {

    public static final PoolNotMigrated INSTANCE = new PoolNotMigrated(
        6015, "Pool not migrated"
    );
  }
}
