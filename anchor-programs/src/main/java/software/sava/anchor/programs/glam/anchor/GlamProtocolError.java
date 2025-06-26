package software.sava.anchor.programs.glam.anchor;

import software.sava.anchor.ProgramError;

public sealed interface GlamProtocolError extends ProgramError permits
    GlamProtocolError.NotAuthorized,
    GlamProtocolError.IntegrationDisabled,
    GlamProtocolError.GlamStateDisabled,
    GlamProtocolError.InvalidSignerAccount,
    GlamProtocolError.EmergencyUpdateDenied,
    GlamProtocolError.TimelockStillActive,
    GlamProtocolError.AssetNotBorrowable,
    GlamProtocolError.InvalidAccountOwner,
    GlamProtocolError.InvalidAuthority,
    GlamProtocolError.InvalidAccountType,
    GlamProtocolError.InvalidName,
    GlamProtocolError.InvalidSymbol,
    GlamProtocolError.InvalidUri,
    GlamProtocolError.InvalidAssetsLen,
    GlamProtocolError.GlamMintNotFound,
    GlamProtocolError.CannotCloseState,
    GlamProtocolError.InvalidMintId,
    GlamProtocolError.InvalidRemainingAccounts,
    GlamProtocolError.InvalidVaultTokenAccount,
    GlamProtocolError.NonZeroSupply,
    GlamProtocolError.MissingAccount,
    GlamProtocolError.InvalidTimestamp,
    GlamProtocolError.EngineFieldNotFound,
    GlamProtocolError.WithdrawDenied,
    GlamProtocolError.InvalidAssetForSwap,
    GlamProtocolError.UnsupportedSwapIx,
    GlamProtocolError.SlippageLimitExceeded,
    GlamProtocolError.InvalidPlatformFeeForSwap,
    GlamProtocolError.InvalidTokenAccount,
    GlamProtocolError.InvalidVoteSide,
    GlamProtocolError.MultipleStakeAccountsDisallowed,
    GlamProtocolError.InvalidAssetPrice,
    GlamProtocolError.InvalidStableCoinPriceForSubscribe,
    GlamProtocolError.ActionPaused,
    GlamProtocolError.InvalidAsset,
    GlamProtocolError.LedgerNotFound,
    GlamProtocolError.InvalidLedgerEntry,
    GlamProtocolError.InvalidAmount,
    GlamProtocolError.MaxCapExceeded,
    GlamProtocolError.InvalidPricingOracle,
    GlamProtocolError.PricingError,
    GlamProtocolError.PriceTooOld,
    GlamProtocolError.ExternalAccountsNotPriced,
    GlamProtocolError.VaultAssetsNotPriced,
    GlamProtocolError.VaultNotPriced,
    GlamProtocolError.PositiveAumRequired,
    GlamProtocolError.MathError,
    GlamProtocolError.TypeCastingError,
    GlamProtocolError.BaseAssetNotSupported,
    GlamProtocolError.InvalidQuoteSpotMarket,
    GlamProtocolError.UnknownExternalVaultAsset,
    GlamProtocolError.InvalidPriceDenom,
    GlamProtocolError.UnexpectedDiscriminator,
    GlamProtocolError.TransfersDisabled,
    GlamProtocolError.InvalidPolicyAccount,
    GlamProtocolError.AmountTooBig,
    GlamProtocolError.LockUp {

  static GlamProtocolError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 48000 -> NotAuthorized.INSTANCE;
      case 48001 -> IntegrationDisabled.INSTANCE;
      case 48002 -> GlamStateDisabled.INSTANCE;
      case 48003 -> InvalidSignerAccount.INSTANCE;
      case 48004 -> EmergencyUpdateDenied.INSTANCE;
      case 48005 -> TimelockStillActive.INSTANCE;
      case 48006 -> AssetNotBorrowable.INSTANCE;
      case 48007 -> InvalidAccountOwner.INSTANCE;
      case 48008 -> InvalidAuthority.INSTANCE;
      case 49000 -> InvalidAccountType.INSTANCE;
      case 49001 -> InvalidName.INSTANCE;
      case 49002 -> InvalidSymbol.INSTANCE;
      case 49003 -> InvalidUri.INSTANCE;
      case 49004 -> InvalidAssetsLen.INSTANCE;
      case 49005 -> GlamMintNotFound.INSTANCE;
      case 49006 -> CannotCloseState.INSTANCE;
      case 49007 -> InvalidMintId.INSTANCE;
      case 49008 -> InvalidRemainingAccounts.INSTANCE;
      case 49009 -> InvalidVaultTokenAccount.INSTANCE;
      case 49010 -> NonZeroSupply.INSTANCE;
      case 49011 -> MissingAccount.INSTANCE;
      case 49012 -> InvalidTimestamp.INSTANCE;
      case 49013 -> EngineFieldNotFound.INSTANCE;
      case 50000 -> WithdrawDenied.INSTANCE;
      case 50001 -> InvalidAssetForSwap.INSTANCE;
      case 50002 -> UnsupportedSwapIx.INSTANCE;
      case 50003 -> SlippageLimitExceeded.INSTANCE;
      case 50004 -> InvalidPlatformFeeForSwap.INSTANCE;
      case 50005 -> InvalidTokenAccount.INSTANCE;
      case 50006 -> InvalidVoteSide.INSTANCE;
      case 50007 -> MultipleStakeAccountsDisallowed.INSTANCE;
      case 51000 -> InvalidAssetPrice.INSTANCE;
      case 51001 -> InvalidStableCoinPriceForSubscribe.INSTANCE;
      case 51002 -> ActionPaused.INSTANCE;
      case 51003 -> InvalidAsset.INSTANCE;
      case 51004 -> LedgerNotFound.INSTANCE;
      case 51005 -> InvalidLedgerEntry.INSTANCE;
      case 51006 -> InvalidAmount.INSTANCE;
      case 51007 -> MaxCapExceeded.INSTANCE;
      case 51100 -> InvalidPricingOracle.INSTANCE;
      case 51101 -> PricingError.INSTANCE;
      case 51102 -> PriceTooOld.INSTANCE;
      case 51103 -> ExternalAccountsNotPriced.INSTANCE;
      case 51104 -> VaultAssetsNotPriced.INSTANCE;
      case 51105 -> VaultNotPriced.INSTANCE;
      case 51106 -> PositiveAumRequired.INSTANCE;
      case 51107 -> MathError.INSTANCE;
      case 51108 -> TypeCastingError.INSTANCE;
      case 51109 -> BaseAssetNotSupported.INSTANCE;
      case 51110 -> InvalidQuoteSpotMarket.INSTANCE;
      case 51111 -> UnknownExternalVaultAsset.INSTANCE;
      case 51112 -> InvalidPriceDenom.INSTANCE;
      case 51113 -> UnexpectedDiscriminator.INSTANCE;
      case 52000 -> TransfersDisabled.INSTANCE;
      case 52001 -> InvalidPolicyAccount.INSTANCE;
      case 52002 -> AmountTooBig.INSTANCE;
      case 52003 -> LockUp.INSTANCE;
      default -> throw new IllegalStateException("Unexpected GlamProtocol error code: " + errorCode);
    };
  }

  record NotAuthorized(int code, String msg) implements GlamProtocolError {

    public static final NotAuthorized INSTANCE = new NotAuthorized(
        48000, "Signer is not authorized"
    );
  }

  record IntegrationDisabled(int code, String msg) implements GlamProtocolError {

    public static final IntegrationDisabled INSTANCE = new IntegrationDisabled(
        48001, "Integration is disabled"
    );
  }

  record GlamStateDisabled(int code, String msg) implements GlamProtocolError {

    public static final GlamStateDisabled INSTANCE = new GlamStateDisabled(
        48002, "GLAM state is disabled"
    );
  }

  record InvalidSignerAccount(int code, String msg) implements GlamProtocolError {

    public static final InvalidSignerAccount INSTANCE = new InvalidSignerAccount(
        48003, "Invalid signer token account"
    );
  }

  record EmergencyUpdateDenied(int code, String msg) implements GlamProtocolError {

    public static final EmergencyUpdateDenied INSTANCE = new EmergencyUpdateDenied(
        48004, "Emergency update denied"
    );
  }

  record TimelockStillActive(int code, String msg) implements GlamProtocolError {

    public static final TimelockStillActive INSTANCE = new TimelockStillActive(
        48005, "Timelock still active"
    );
  }

  record AssetNotBorrowable(int code, String msg) implements GlamProtocolError {

    public static final AssetNotBorrowable INSTANCE = new AssetNotBorrowable(
        48006, "Asset is not allowed to borrow"
    );
  }

  record InvalidAccountOwner(int code, String msg) implements GlamProtocolError {

    public static final InvalidAccountOwner INSTANCE = new InvalidAccountOwner(
        48007, "Account owned by an invalid program"
    );
  }

  record InvalidAuthority(int code, String msg) implements GlamProtocolError {

    public static final InvalidAuthority INSTANCE = new InvalidAuthority(
        48008, "Invalid authority"
    );
  }

  record InvalidAccountType(int code, String msg) implements GlamProtocolError {

    public static final InvalidAccountType INSTANCE = new InvalidAccountType(
        49000, "Invalid account type"
    );
  }

  record InvalidName(int code, String msg) implements GlamProtocolError {

    public static final InvalidName INSTANCE = new InvalidName(
        49001, "Name too long: max 64 chars"
    );
  }

  record InvalidSymbol(int code, String msg) implements GlamProtocolError {

    public static final InvalidSymbol INSTANCE = new InvalidSymbol(
        49002, "Symbol too long: max 32 chars"
    );
  }

  record InvalidUri(int code, String msg) implements GlamProtocolError {

    public static final InvalidUri INSTANCE = new InvalidUri(
        49003, "Uri too long: max 128 chars"
    );
  }

  record InvalidAssetsLen(int code, String msg) implements GlamProtocolError {

    public static final InvalidAssetsLen INSTANCE = new InvalidAssetsLen(
        49004, "Too many assets: max 100"
    );
  }

  record GlamMintNotFound(int code, String msg) implements GlamProtocolError {

    public static final GlamMintNotFound INSTANCE = new GlamMintNotFound(
        49005, "Glam mint not found"
    );
  }

  record CannotCloseState(int code, String msg) implements GlamProtocolError {

    public static final CannotCloseState INSTANCE = new CannotCloseState(
        49006, "Glam state cannot be closed, all mints must be closed first"
    );
  }

  record InvalidMintId(int code, String msg) implements GlamProtocolError {

    public static final InvalidMintId INSTANCE = new InvalidMintId(
        49007, "Invalid mint id"
    );
  }

  record InvalidRemainingAccounts(int code, String msg) implements GlamProtocolError {

    public static final InvalidRemainingAccounts INSTANCE = new InvalidRemainingAccounts(
        49008, "Invalid accounts: the transaction is malformed"
    );
  }

  record InvalidVaultTokenAccount(int code, String msg) implements GlamProtocolError {

    public static final InvalidVaultTokenAccount INSTANCE = new InvalidVaultTokenAccount(
        49009, "Invalid vault ata"
    );
  }

  record NonZeroSupply(int code, String msg) implements GlamProtocolError {

    public static final NonZeroSupply INSTANCE = new NonZeroSupply(
        49010, "Glam mint supply not zero"
    );
  }

  record MissingAccount(int code, String msg) implements GlamProtocolError {

    public static final MissingAccount INSTANCE = new MissingAccount(
        49011, "An account required by the instruction is missing"
    );
  }

  record InvalidTimestamp(int code, String msg) implements GlamProtocolError {

    public static final InvalidTimestamp INSTANCE = new InvalidTimestamp(
        49012, "Invalid timestamp"
    );
  }

  record EngineFieldNotFound(int code, String msg) implements GlamProtocolError {

    public static final EngineFieldNotFound INSTANCE = new EngineFieldNotFound(
        49013, "Engine field not found"
    );
  }

  record WithdrawDenied(int code, String msg) implements GlamProtocolError {

    public static final WithdrawDenied INSTANCE = new WithdrawDenied(
        50000, "Withdraw denied. Only vaults allow withdraws (funds and mints don't)"
    );
  }

  record InvalidAssetForSwap(int code, String msg) implements GlamProtocolError {

    public static final InvalidAssetForSwap INSTANCE = new InvalidAssetForSwap(
        50001, "Asset cannot be swapped"
    );
  }

  record UnsupportedSwapIx(int code, String msg) implements GlamProtocolError {

    public static final UnsupportedSwapIx INSTANCE = new UnsupportedSwapIx(
        50002, "Unsupported swap instruction"
    );
  }

  record SlippageLimitExceeded(int code, String msg) implements GlamProtocolError {

    public static final SlippageLimitExceeded INSTANCE = new SlippageLimitExceeded(
        50003, "Max slippage exceeded"
    );
  }

  record InvalidPlatformFeeForSwap(int code, String msg) implements GlamProtocolError {

    public static final InvalidPlatformFeeForSwap INSTANCE = new InvalidPlatformFeeForSwap(
        50004, "Invalid platform fee"
    );
  }

  record InvalidTokenAccount(int code, String msg) implements GlamProtocolError {

    public static final InvalidTokenAccount INSTANCE = new InvalidTokenAccount(
        50005, "Invalid token account"
    );
  }

  record InvalidVoteSide(int code, String msg) implements GlamProtocolError {

    public static final InvalidVoteSide INSTANCE = new InvalidVoteSide(
        50006, "Invalid vote side"
    );
  }

  record MultipleStakeAccountsDisallowed(int code, String msg) implements GlamProtocolError {

    public static final MultipleStakeAccountsDisallowed INSTANCE = new MultipleStakeAccountsDisallowed(
        50007, "Multiple stake accounts disallowed"
    );
  }

  record InvalidAssetPrice(int code, String msg) implements GlamProtocolError {

    public static final InvalidAssetPrice INSTANCE = new InvalidAssetPrice(
        51000, "Invalid asset price"
    );
  }

  record InvalidStableCoinPriceForSubscribe(int code, String msg) implements GlamProtocolError {

    public static final InvalidStableCoinPriceForSubscribe INSTANCE = new InvalidStableCoinPriceForSubscribe(
        51001, "Subscription not allowed: invalid stable coin price"
    );
  }

  record ActionPaused(int code, String msg) implements GlamProtocolError {

    public static final ActionPaused INSTANCE = new ActionPaused(
        51002, "Requested action is paused"
    );
  }

  record InvalidAsset(int code, String msg) implements GlamProtocolError {

    public static final InvalidAsset INSTANCE = new InvalidAsset(
        51003, "Asset not allowed to subscribe"
    );
  }

  record LedgerNotFound(int code, String msg) implements GlamProtocolError {

    public static final LedgerNotFound INSTANCE = new LedgerNotFound(
        51004, "Ledger not found"
    );
  }

  record InvalidLedgerEntry(int code, String msg) implements GlamProtocolError {

    public static final InvalidLedgerEntry INSTANCE = new InvalidLedgerEntry(
        51005, "Invalid ledger entry"
    );
  }

  record InvalidAmount(int code, String msg) implements GlamProtocolError {

    public static final InvalidAmount INSTANCE = new InvalidAmount(
        51006, "Invalid amount for subscription or redemption"
    );
  }

  record MaxCapExceeded(int code, String msg) implements GlamProtocolError {

    public static final MaxCapExceeded INSTANCE = new MaxCapExceeded(
        51007, "Max cap exceeded"
    );
  }

  record InvalidPricingOracle(int code, String msg) implements GlamProtocolError {

    public static final InvalidPricingOracle INSTANCE = new InvalidPricingOracle(
        51100, "Invalid oracle for asset price"
    );
  }

  record PricingError(int code, String msg) implements GlamProtocolError {

    public static final PricingError INSTANCE = new PricingError(
        51101, "Pricing error"
    );
  }

  record PriceTooOld(int code, String msg) implements GlamProtocolError {

    public static final PriceTooOld INSTANCE = new PriceTooOld(
        51102, "Price is too old"
    );
  }

  record ExternalAccountsNotPriced(int code, String msg) implements GlamProtocolError {

    public static final ExternalAccountsNotPriced INSTANCE = new ExternalAccountsNotPriced(
        51103, "Not all external vault accounts are priced"
    );
  }

  record VaultAssetsNotPriced(int code, String msg) implements GlamProtocolError {

    public static final VaultAssetsNotPriced INSTANCE = new VaultAssetsNotPriced(
        51104, "Not all vault assets are priced"
    );
  }

  record VaultNotPriced(int code, String msg) implements GlamProtocolError {

    public static final VaultNotPriced INSTANCE = new VaultNotPriced(
        51105, "No priced assets found"
    );
  }

  record PositiveAumRequired(int code, String msg) implements GlamProtocolError {

    public static final PositiveAumRequired INSTANCE = new PositiveAumRequired(
        51106, "AUM must be positive"
    );
  }

  record MathError(int code, String msg) implements GlamProtocolError {

    public static final MathError INSTANCE = new MathError(
        51107, "Math error"
    );
  }

  record TypeCastingError(int code, String msg) implements GlamProtocolError {

    public static final TypeCastingError INSTANCE = new TypeCastingError(
        51108, "Type casting error"
    );
  }

  record BaseAssetNotSupported(int code, String msg) implements GlamProtocolError {

    public static final BaseAssetNotSupported INSTANCE = new BaseAssetNotSupported(
        51109, "Base asset must have 6 decimals."
    );
  }

  record InvalidQuoteSpotMarket(int code, String msg) implements GlamProtocolError {

    public static final InvalidQuoteSpotMarket INSTANCE = new InvalidQuoteSpotMarket(
        51110, "Unsupported spot market for perp quotes"
    );
  }

  record UnknownExternalVaultAsset(int code, String msg) implements GlamProtocolError {

    public static final UnknownExternalVaultAsset INSTANCE = new UnknownExternalVaultAsset(
        51111, "Unknown external vault account"
    );
  }

  record InvalidPriceDenom(int code, String msg) implements GlamProtocolError {

    public static final InvalidPriceDenom INSTANCE = new InvalidPriceDenom(
        51112, "Invalid price denom"
    );
  }

  record UnexpectedDiscriminator(int code, String msg) implements GlamProtocolError {

    public static final UnexpectedDiscriminator INSTANCE = new UnexpectedDiscriminator(
        51113, "Invalid account: discriminator mismatch"
    );
  }

  record TransfersDisabled(int code, String msg) implements GlamProtocolError {

    public static final TransfersDisabled INSTANCE = new TransfersDisabled(
        52000, "Policy violation: transfers disabled"
    );
  }

  record InvalidPolicyAccount(int code, String msg) implements GlamProtocolError {

    public static final InvalidPolicyAccount INSTANCE = new InvalidPolicyAccount(
        52001, "Policy account is mandatory"
    );
  }

  record AmountTooBig(int code, String msg) implements GlamProtocolError {

    public static final AmountTooBig INSTANCE = new AmountTooBig(
        52002, "Policy violation: amount too big"
    );
  }

  record LockUp(int code, String msg) implements GlamProtocolError {

    public static final LockUp INSTANCE = new LockUp(
        52003, "Policy violation: lock-up has not expired"
    );
  }
}
