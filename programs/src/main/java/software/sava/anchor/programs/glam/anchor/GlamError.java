package software.sava.anchor.programs.glam.anchor;

import software.sava.anchor.ProgramError;

public sealed interface GlamError extends ProgramError permits
    GlamError.NotAuthorized,
    GlamError.IntegrationDisabled,
    GlamError.StateAccountDisabled,
    GlamError.InvalidSignerAccount,
    GlamError.InvalidAccountType,
    GlamError.InvalidName,
    GlamError.InvalidSymbol,
    GlamError.InvalidUri,
    GlamError.InvalidAssetsLen,
    GlamError.CloseNotEmptyError,
    GlamError.NoShareClass,
    GlamError.ShareClassesNotClosed,
    GlamError.InvalidShareClass,
    GlamError.InvalidAssetSubscribe,
    GlamError.InvalidPricingOracle,
    GlamError.InvalidRemainingAccounts,
    GlamError.InvalidVaultTokenAccount,
    GlamError.ShareClassNotEmpty,
    GlamError.WithdrawDenied,
    GlamError.InvalidAssetForSwap,
    GlamError.InvalidSwap,
    GlamError.InvalidTokenAccount,
    GlamError.InvalidVoteSide,
    GlamError.InvalidAssetPrice,
    GlamError.InvalidStableCoinPriceForSubscribe,
    GlamError.SubscribeRedeemDisable,
    GlamError.InvalidPolicyAccount,
    GlamError.PricingError,
    GlamError.PriceTooOld,
    GlamError.TransfersDisabled,
    GlamError.AmountTooBig,
    GlamError.LockUp {

  static GlamError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 48000 -> NotAuthorized.INSTANCE;
      case 48001 -> IntegrationDisabled.INSTANCE;
      case 48002 -> StateAccountDisabled.INSTANCE;
      case 48003 -> InvalidSignerAccount.INSTANCE;
      case 49000 -> InvalidAccountType.INSTANCE;
      case 49001 -> InvalidName.INSTANCE;
      case 49002 -> InvalidSymbol.INSTANCE;
      case 49003 -> InvalidUri.INSTANCE;
      case 49004 -> InvalidAssetsLen.INSTANCE;
      case 49005 -> CloseNotEmptyError.INSTANCE;
      case 49006 -> NoShareClass.INSTANCE;
      case 49007 -> ShareClassesNotClosed.INSTANCE;
      case 49008 -> InvalidShareClass.INSTANCE;
      case 49009 -> InvalidAssetSubscribe.INSTANCE;
      case 49010 -> InvalidPricingOracle.INSTANCE;
      case 49011 -> InvalidRemainingAccounts.INSTANCE;
      case 49012 -> InvalidVaultTokenAccount.INSTANCE;
      case 49013 -> ShareClassNotEmpty.INSTANCE;
      case 50000 -> WithdrawDenied.INSTANCE;
      case 50001 -> InvalidAssetForSwap.INSTANCE;
      case 50002 -> InvalidSwap.INSTANCE;
      case 50003 -> InvalidTokenAccount.INSTANCE;
      case 50004 -> InvalidVoteSide.INSTANCE;
      case 51000 -> InvalidAssetPrice.INSTANCE;
      case 51001 -> InvalidStableCoinPriceForSubscribe.INSTANCE;
      case 51002 -> SubscribeRedeemDisable.INSTANCE;
      case 51003 -> InvalidPolicyAccount.INSTANCE;
      case 51004 -> PricingError.INSTANCE;
      case 51005 -> PriceTooOld.INSTANCE;
      case 52000 -> TransfersDisabled.INSTANCE;
      case 52001 -> AmountTooBig.INSTANCE;
      case 52002 -> LockUp.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Glam error code: " + errorCode);
    };
  }

  record NotAuthorized(int code, String msg) implements GlamError {

    public static final NotAuthorized INSTANCE = new NotAuthorized(
        48000, "Signer is not authorized"
    );
  }

  record IntegrationDisabled(int code, String msg) implements GlamError {

    public static final IntegrationDisabled INSTANCE = new IntegrationDisabled(
        48001, "Integration is disabled"
    );
  }

  record StateAccountDisabled(int code, String msg) implements GlamError {

    public static final StateAccountDisabled INSTANCE = new StateAccountDisabled(
        48002, "State account is disabled"
    );
  }

  record InvalidSignerAccount(int code, String msg) implements GlamError {

    public static final InvalidSignerAccount INSTANCE = new InvalidSignerAccount(
        48003, "Invalid signer ata"
    );
  }

  record InvalidAccountType(int code, String msg) implements GlamError {

    public static final InvalidAccountType INSTANCE = new InvalidAccountType(
        49000, "Invalid account type"
    );
  }

  record InvalidName(int code, String msg) implements GlamError {

    public static final InvalidName INSTANCE = new InvalidName(
        49001, "Name too long: max 64 chars"
    );
  }

  record InvalidSymbol(int code, String msg) implements GlamError {

    public static final InvalidSymbol INSTANCE = new InvalidSymbol(
        49002, "Symbol too long: max 32 chars"
    );
  }

  record InvalidUri(int code, String msg) implements GlamError {

    public static final InvalidUri INSTANCE = new InvalidUri(
        49003, "Uri too long: max 128 chars"
    );
  }

  record InvalidAssetsLen(int code, String msg) implements GlamError {

    public static final InvalidAssetsLen INSTANCE = new InvalidAssetsLen(
        49004, "Too many assets: max 100"
    );
  }

  record CloseNotEmptyError(int code, String msg) implements GlamError {

    public static final CloseNotEmptyError INSTANCE = new CloseNotEmptyError(
        49005, "Error closing state account: not empty"
    );
  }

  record NoShareClass(int code, String msg) implements GlamError {

    public static final NoShareClass INSTANCE = new NoShareClass(
        49006, "No share class found"
    );
  }

  record ShareClassesNotClosed(int code, String msg) implements GlamError {

    public static final ShareClassesNotClosed INSTANCE = new ShareClassesNotClosed(
        49007, "Glam state account can't be closed. Close share classes first"
    );
  }

  record InvalidShareClass(int code, String msg) implements GlamError {

    public static final InvalidShareClass INSTANCE = new InvalidShareClass(
        49008, "Share class not allowed to subscribe"
    );
  }

  record InvalidAssetSubscribe(int code, String msg) implements GlamError {

    public static final InvalidAssetSubscribe INSTANCE = new InvalidAssetSubscribe(
        49009, "Asset not allowed to subscribe"
    );
  }

  record InvalidPricingOracle(int code, String msg) implements GlamError {

    public static final InvalidPricingOracle INSTANCE = new InvalidPricingOracle(
        49010, "Invalid oracle for asset price"
    );
  }

  record InvalidRemainingAccounts(int code, String msg) implements GlamError {

    public static final InvalidRemainingAccounts INSTANCE = new InvalidRemainingAccounts(
        49011, "Invalid accounts: the transaction is malformed"
    );
  }

  record InvalidVaultTokenAccount(int code, String msg) implements GlamError {

    public static final InvalidVaultTokenAccount INSTANCE = new InvalidVaultTokenAccount(
        49012, "Invalid vault ata"
    );
  }

  record ShareClassNotEmpty(int code, String msg) implements GlamError {

    public static final ShareClassNotEmpty INSTANCE = new ShareClassNotEmpty(
        49013, "Share class mint supply not zero"
    );
  }

  record WithdrawDenied(int code, String msg) implements GlamError {

    public static final WithdrawDenied INSTANCE = new WithdrawDenied(
        50000, "Withdraw denied. Only vaults allow withdraws (funds and mints don't)"
    );
  }

  record InvalidAssetForSwap(int code, String msg) implements GlamError {

    public static final InvalidAssetForSwap INSTANCE = new InvalidAssetForSwap(
        50001, "Asset cannot be swapped"
    );
  }

  record InvalidSwap(int code, String msg) implements GlamError {

    public static final InvalidSwap INSTANCE = new InvalidSwap(
        50002, "Swap failed"
    );
  }

  record InvalidTokenAccount(int code, String msg) implements GlamError {

    public static final InvalidTokenAccount INSTANCE = new InvalidTokenAccount(
        50003, "Invalid token account"
    );
  }

  record InvalidVoteSide(int code, String msg) implements GlamError {

    public static final InvalidVoteSide INSTANCE = new InvalidVoteSide(
        50004, "Invalid vote side"
    );
  }

  record InvalidAssetPrice(int code, String msg) implements GlamError {

    public static final InvalidAssetPrice INSTANCE = new InvalidAssetPrice(
        51000, "Invalid asset price"
    );
  }

  record InvalidStableCoinPriceForSubscribe(int code, String msg) implements GlamError {

    public static final InvalidStableCoinPriceForSubscribe INSTANCE = new InvalidStableCoinPriceForSubscribe(
        51001, "Subscription not allowed: invalid stable coin price"
    );
  }

  record SubscribeRedeemDisable(int code, String msg) implements GlamError {

    public static final SubscribeRedeemDisable INSTANCE = new SubscribeRedeemDisable(
        51002, "Subscription and redemption disabled"
    );
  }

  record InvalidPolicyAccount(int code, String msg) implements GlamError {

    public static final InvalidPolicyAccount INSTANCE = new InvalidPolicyAccount(
        51003, "Policy account is mandatory"
    );
  }

  record PricingError(int code, String msg) implements GlamError {

    public static final PricingError INSTANCE = new PricingError(
        51004, "Pricing error"
    );
  }

  record PriceTooOld(int code, String msg) implements GlamError {

    public static final PriceTooOld INSTANCE = new PriceTooOld(
        51005, "Price is too old"
    );
  }

  record TransfersDisabled(int code, String msg) implements GlamError {

    public static final TransfersDisabled INSTANCE = new TransfersDisabled(
        52000, "Policy violation: transfers disabled"
    );
  }

  record AmountTooBig(int code, String msg) implements GlamError {

    public static final AmountTooBig INSTANCE = new AmountTooBig(
        52001, "Policy violation: amount too big"
    );
  }

  record LockUp(int code, String msg) implements GlamError {

    public static final LockUp INSTANCE = new LockUp(
        52002, "Policy violation: lock-up period"
    );
  }
}
