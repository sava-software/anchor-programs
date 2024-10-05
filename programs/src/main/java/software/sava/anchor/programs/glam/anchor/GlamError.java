package software.sava.anchor.programs.glam.anchor;

import software.sava.anchor.ProgramError;

public sealed interface GlamError extends ProgramError permits
    GlamError.FundNotActive,
    GlamError.InvalidShareClass,
    GlamError.InvalidAssetSubscribe,
    GlamError.InvalidPricingOracle,
    GlamError.InvalidRemainingAccounts,
    GlamError.InvalidTreasuryAccount,
    GlamError.InvalidSignerAccount,
    GlamError.InvalidAssetPrice,
    GlamError.InvalidStableCoinPriceForSubscribe,
    GlamError.SubscribeRedeemPaused,
    GlamError.InvalidPolicyAccount {

  static GlamError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> FundNotActive.INSTANCE;
      case 6001 -> InvalidShareClass.INSTANCE;
      case 6002 -> InvalidAssetSubscribe.INSTANCE;
      case 6003 -> InvalidPricingOracle.INSTANCE;
      case 6004 -> InvalidRemainingAccounts.INSTANCE;
      case 6005 -> InvalidTreasuryAccount.INSTANCE;
      case 6006 -> InvalidSignerAccount.INSTANCE;
      case 6007 -> InvalidAssetPrice.INSTANCE;
      case 6008 -> InvalidStableCoinPriceForSubscribe.INSTANCE;
      case 6009 -> SubscribeRedeemPaused.INSTANCE;
      case 6010 -> InvalidPolicyAccount.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Glam error code: " + errorCode);
    };
  }

  record FundNotActive(int code, String msg) implements GlamError {

    public static final FundNotActive INSTANCE = new FundNotActive(
        6000, "Fund is not active"
    );
  }

  record InvalidShareClass(int code, String msg) implements GlamError {

    public static final InvalidShareClass INSTANCE = new InvalidShareClass(
        6001, "Share class not allowed to subscribe"
    );
  }

  record InvalidAssetSubscribe(int code, String msg) implements GlamError {

    public static final InvalidAssetSubscribe INSTANCE = new InvalidAssetSubscribe(
        6002, "Asset not allowed to subscribe"
    );
  }

  record InvalidPricingOracle(int code, String msg) implements GlamError {

    public static final InvalidPricingOracle INSTANCE = new InvalidPricingOracle(
        6003, "Invalid oracle for asset price"
    );
  }

  record InvalidRemainingAccounts(int code, String msg) implements GlamError {

    public static final InvalidRemainingAccounts INSTANCE = new InvalidRemainingAccounts(
        6004, "Invalid accounts: the transaction is malformed"
    );
  }

  record InvalidTreasuryAccount(int code, String msg) implements GlamError {

    public static final InvalidTreasuryAccount INSTANCE = new InvalidTreasuryAccount(
        6005, "Invalid treasury ata"
    );
  }

  record InvalidSignerAccount(int code, String msg) implements GlamError {

    public static final InvalidSignerAccount INSTANCE = new InvalidSignerAccount(
        6006, "Invalid signer ata"
    );
  }

  record InvalidAssetPrice(int code, String msg) implements GlamError {

    public static final InvalidAssetPrice INSTANCE = new InvalidAssetPrice(
        6007, "Invalid asset price"
    );
  }

  record InvalidStableCoinPriceForSubscribe(int code, String msg) implements GlamError {

    public static final InvalidStableCoinPriceForSubscribe INSTANCE = new InvalidStableCoinPriceForSubscribe(
        6008, "Subscription not allowed: invalid stable coin price"
    );
  }

  record SubscribeRedeemPaused(int code, String msg) implements GlamError {

    public static final SubscribeRedeemPaused INSTANCE = new SubscribeRedeemPaused(
        6009, "Fund is paused for subscription and redemption"
    );
  }

  record InvalidPolicyAccount(int code, String msg) implements GlamError {

    public static final InvalidPolicyAccount INSTANCE = new InvalidPolicyAccount(
        6010, "Policy account is mandatory"
    );
  }
}
