package software.sava.anchor.programs.glam.anchor;

import software.sava.anchor.ProgramError;

public sealed interface GlamError extends ProgramError permits
    GlamError.InvalidShareClass,
    GlamError.InvalidAssetSubscribe,
    GlamError.InvalidPricingOracle,
    GlamError.InvalidRemainingAccounts,
    GlamError.InvalidTreasuryAccount,
    GlamError.InvalidSignerAccount,
    GlamError.InvalidAssetPrice,
    GlamError.InvalidStableCoinPriceForSubscribe,
    GlamError.SubscribeRedeemDisable,
    GlamError.InvalidPolicyAccount,
    GlamError.PriceTooOld {

  static GlamError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> InvalidShareClass.INSTANCE;
      case 6001 -> InvalidAssetSubscribe.INSTANCE;
      case 6002 -> InvalidPricingOracle.INSTANCE;
      case 6003 -> InvalidRemainingAccounts.INSTANCE;
      case 6004 -> InvalidTreasuryAccount.INSTANCE;
      case 6005 -> InvalidSignerAccount.INSTANCE;
      case 6006 -> InvalidAssetPrice.INSTANCE;
      case 6007 -> InvalidStableCoinPriceForSubscribe.INSTANCE;
      case 6008 -> SubscribeRedeemDisable.INSTANCE;
      case 6009 -> InvalidPolicyAccount.INSTANCE;
      case 6010 -> PriceTooOld.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Glam error code: " + errorCode);
    };
  }

  record InvalidShareClass(int code, String msg) implements GlamError {

    public static final InvalidShareClass INSTANCE = new InvalidShareClass(
        6000, "Share class not allowed to subscribe"
    );
  }

  record InvalidAssetSubscribe(int code, String msg) implements GlamError {

    public static final InvalidAssetSubscribe INSTANCE = new InvalidAssetSubscribe(
        6001, "Asset not allowed to subscribe"
    );
  }

  record InvalidPricingOracle(int code, String msg) implements GlamError {

    public static final InvalidPricingOracle INSTANCE = new InvalidPricingOracle(
        6002, "Invalid oracle for asset price"
    );
  }

  record InvalidRemainingAccounts(int code, String msg) implements GlamError {

    public static final InvalidRemainingAccounts INSTANCE = new InvalidRemainingAccounts(
        6003, "Invalid accounts: the transaction is malformed"
    );
  }

  record InvalidTreasuryAccount(int code, String msg) implements GlamError {

    public static final InvalidTreasuryAccount INSTANCE = new InvalidTreasuryAccount(
        6004, "Invalid treasury ata"
    );
  }

  record InvalidSignerAccount(int code, String msg) implements GlamError {

    public static final InvalidSignerAccount INSTANCE = new InvalidSignerAccount(
        6005, "Invalid signer ata"
    );
  }

  record InvalidAssetPrice(int code, String msg) implements GlamError {

    public static final InvalidAssetPrice INSTANCE = new InvalidAssetPrice(
        6006, "Invalid asset price"
    );
  }

  record InvalidStableCoinPriceForSubscribe(int code, String msg) implements GlamError {

    public static final InvalidStableCoinPriceForSubscribe INSTANCE = new InvalidStableCoinPriceForSubscribe(
        6007, "Subscription not allowed: invalid stable coin price"
    );
  }

  record SubscribeRedeemDisable(int code, String msg) implements GlamError {

    public static final SubscribeRedeemDisable INSTANCE = new SubscribeRedeemDisable(
        6008, "Fund is disabled for subscription and redemption"
    );
  }

  record InvalidPolicyAccount(int code, String msg) implements GlamError {

    public static final InvalidPolicyAccount INSTANCE = new InvalidPolicyAccount(
        6009, "Policy account is mandatory"
    );
  }

  record PriceTooOld(int code, String msg) implements GlamError {

    public static final PriceTooOld INSTANCE = new PriceTooOld(
        6010, "Price is too old"
    );
  }
}
