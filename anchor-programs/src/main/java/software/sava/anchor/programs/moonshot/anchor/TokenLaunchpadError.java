package software.sava.anchor.programs.moonshot.anchor;

import software.sava.anchor.programs._commons.ProgramError;

public sealed interface TokenLaunchpadError extends ProgramError permits
    TokenLaunchpadError.InsufficientBalance,
    TokenLaunchpadError.InvalidAmount,
    TokenLaunchpadError.InvalidSlippage,
    TokenLaunchpadError.SlippageOverflow,
    TokenLaunchpadError.ThresholdReached,
    TokenLaunchpadError.InvalidTokenAccount,
    TokenLaunchpadError.InvalidCurveAccount,
    TokenLaunchpadError.InvalidFeeAccount,
    TokenLaunchpadError.CurveLimit,
    TokenLaunchpadError.InvalidCurveType,
    TokenLaunchpadError.InvalidCurrency,
    TokenLaunchpadError.Arithmetics,
    TokenLaunchpadError.ThresholdNotHit,
    TokenLaunchpadError.InvalidAuthority,
    TokenLaunchpadError.TradeAmountTooLow,
    TokenLaunchpadError.ConfigFieldMissing,
    TokenLaunchpadError.DifferentCurrencies,
    TokenLaunchpadError.BasisPointTooHigh,
    TokenLaunchpadError.FeeShareTooHigh,
    TokenLaunchpadError.TokenDecimalsOutOfRange,
    TokenLaunchpadError.TokenNameTooLong,
    TokenLaunchpadError.TokenSymbolTooLong,
    TokenLaunchpadError.TokenURITooLong,
    TokenLaunchpadError.IncorrectDecimalPlacesBounds,
    TokenLaunchpadError.IncorrectTokenSupplyBounds,
    TokenLaunchpadError.TotalSupplyOutOfBounds,
    TokenLaunchpadError.FinalCollateralTooLow,
    TokenLaunchpadError.CoefficientZero,
    TokenLaunchpadError.MarketCapThresholdTooLow,
    TokenLaunchpadError.CoefBOutofBounds,
    TokenLaunchpadError.IncorrectMarketCap,
    TokenLaunchpadError.IncorrectDecimals,
    TokenLaunchpadError.IncorrectMaxSupply,
    TokenLaunchpadError.MarketCapTooHigh,
    TokenLaunchpadError.InvalidMigrationTarget,
    TokenLaunchpadError.General {

  static TokenLaunchpadError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> InsufficientBalance.INSTANCE;
      case 6001 -> InvalidAmount.INSTANCE;
      case 6002 -> InvalidSlippage.INSTANCE;
      case 6003 -> SlippageOverflow.INSTANCE;
      case 6004 -> ThresholdReached.INSTANCE;
      case 6005 -> InvalidTokenAccount.INSTANCE;
      case 6006 -> InvalidCurveAccount.INSTANCE;
      case 6007 -> InvalidFeeAccount.INSTANCE;
      case 6008 -> CurveLimit.INSTANCE;
      case 6009 -> InvalidCurveType.INSTANCE;
      case 6010 -> InvalidCurrency.INSTANCE;
      case 6011 -> Arithmetics.INSTANCE;
      case 6012 -> ThresholdNotHit.INSTANCE;
      case 6013 -> InvalidAuthority.INSTANCE;
      case 6014 -> TradeAmountTooLow.INSTANCE;
      case 6015 -> ConfigFieldMissing.INSTANCE;
      case 6016 -> DifferentCurrencies.INSTANCE;
      case 6017 -> BasisPointTooHigh.INSTANCE;
      case 6018 -> FeeShareTooHigh.INSTANCE;
      case 6019 -> TokenDecimalsOutOfRange.INSTANCE;
      case 6020 -> TokenNameTooLong.INSTANCE;
      case 6021 -> TokenSymbolTooLong.INSTANCE;
      case 6022 -> TokenURITooLong.INSTANCE;
      case 6023 -> IncorrectDecimalPlacesBounds.INSTANCE;
      case 6024 -> IncorrectTokenSupplyBounds.INSTANCE;
      case 6025 -> TotalSupplyOutOfBounds.INSTANCE;
      case 6026 -> FinalCollateralTooLow.INSTANCE;
      case 6027 -> CoefficientZero.INSTANCE;
      case 6028 -> MarketCapThresholdTooLow.INSTANCE;
      case 6029 -> CoefBOutofBounds.INSTANCE;
      case 6030 -> IncorrectMarketCap.INSTANCE;
      case 6031 -> IncorrectDecimals.INSTANCE;
      case 6032 -> IncorrectMaxSupply.INSTANCE;
      case 6033 -> MarketCapTooHigh.INSTANCE;
      case 6034 -> InvalidMigrationTarget.INSTANCE;
      case 6035 -> General.INSTANCE;
      default -> throw new IllegalStateException("Unexpected TokenLaunchpad error code: " + errorCode);
    };
  }

  record InsufficientBalance(int code, String msg) implements TokenLaunchpadError {

    public static final InsufficientBalance INSTANCE = new InsufficientBalance(
        6000, "Insufficient SOL to pay for the transaction."
    );
  }

  record InvalidAmount(int code, String msg) implements TokenLaunchpadError {

    public static final InvalidAmount INSTANCE = new InvalidAmount(
        6001, "The amount must be available in the curve ."
    );
  }

  record InvalidSlippage(int code, String msg) implements TokenLaunchpadError {

    public static final InvalidSlippage INSTANCE = new InvalidSlippage(
        6002, "The slippage must be under 100 percent."
    );
  }

  record SlippageOverflow(int code, String msg) implements TokenLaunchpadError {

    public static final SlippageOverflow INSTANCE = new SlippageOverflow(
        6003, "The cost amount is not in the allowed slippage interval."
    );
  }

  record ThresholdReached(int code, String msg) implements TokenLaunchpadError {

    public static final ThresholdReached INSTANCE = new ThresholdReached(
        6004, "Threshold limit exceeded."
    );
  }

  record InvalidTokenAccount(int code, String msg) implements TokenLaunchpadError {

    public static final InvalidTokenAccount INSTANCE = new InvalidTokenAccount(
        6005, "Trade disabled, market cap threshold reached."
    );
  }

  record InvalidCurveAccount(int code, String msg) implements TokenLaunchpadError {

    public static final InvalidCurveAccount INSTANCE = new InvalidCurveAccount(
        6006, "Invalid curve account."
    );
  }

  record InvalidFeeAccount(int code, String msg) implements TokenLaunchpadError {

    public static final InvalidFeeAccount INSTANCE = new InvalidFeeAccount(
        6007, "Invalid fee account address."
    );
  }

  record CurveLimit(int code, String msg) implements TokenLaunchpadError {

    public static final CurveLimit INSTANCE = new CurveLimit(
        6008, "Curve limit exceeded."
    );
  }

  record InvalidCurveType(int code, String msg) implements TokenLaunchpadError {

    public static final InvalidCurveType INSTANCE = new InvalidCurveType(
        6009, "Invalid curve type."
    );
  }

  record InvalidCurrency(int code, String msg) implements TokenLaunchpadError {

    public static final InvalidCurrency INSTANCE = new InvalidCurrency(
        6010, "Invalid currency."
    );
  }

  record Arithmetics(int code, String msg) implements TokenLaunchpadError {

    public static final Arithmetics INSTANCE = new Arithmetics(
        6011, "Artithmetics error"
    );
  }

  record ThresholdNotHit(int code, String msg) implements TokenLaunchpadError {

    public static final ThresholdNotHit INSTANCE = new ThresholdNotHit(
        6012, "Market Cap threshold not hit, cannot migrate funds yet"
    );
  }

  record InvalidAuthority(int code, String msg) implements TokenLaunchpadError {

    public static final InvalidAuthority INSTANCE = new InvalidAuthority(
        6013, "Invalid Authority provided."
    );
  }

  record TradeAmountTooLow(int code, String msg) implements TokenLaunchpadError {

    public static final TradeAmountTooLow INSTANCE = new TradeAmountTooLow(
        6014, "Trade amount too low , resulting in 0 costs"
    );
  }

  record ConfigFieldMissing(int code, String msg) implements TokenLaunchpadError {

    public static final ConfigFieldMissing INSTANCE = new ConfigFieldMissing(
        6015, "Config field needs to be present during initialization"
    );
  }

  record DifferentCurrencies(int code, String msg) implements TokenLaunchpadError {

    public static final DifferentCurrencies INSTANCE = new DifferentCurrencies(
        6016, "Unsupported different currency types"
    );
  }

  record BasisPointTooHigh(int code, String msg) implements TokenLaunchpadError {

    public static final BasisPointTooHigh INSTANCE = new BasisPointTooHigh(
        6017, "Basis points too high"
    );
  }

  record FeeShareTooHigh(int code, String msg) implements TokenLaunchpadError {

    public static final FeeShareTooHigh INSTANCE = new FeeShareTooHigh(
        6018, "Fee share too High"
    );
  }

  record TokenDecimalsOutOfRange(int code, String msg) implements TokenLaunchpadError {

    public static final TokenDecimalsOutOfRange INSTANCE = new TokenDecimalsOutOfRange(
        6019, "Token decimals are not within the supported range"
    );
  }

  record TokenNameTooLong(int code, String msg) implements TokenLaunchpadError {

    public static final TokenNameTooLong INSTANCE = new TokenNameTooLong(
        6020, "Token Name too long, max supported length is 32 bytes"
    );
  }

  record TokenSymbolTooLong(int code, String msg) implements TokenLaunchpadError {

    public static final TokenSymbolTooLong INSTANCE = new TokenSymbolTooLong(
        6021, "Token Symbol too long, max supported length is 10 bytes"
    );
  }

  record TokenURITooLong(int code, String msg) implements TokenLaunchpadError {

    public static final TokenURITooLong INSTANCE = new TokenURITooLong(
        6022, "Token URI too long, max supported length is 200 bytes"
    );
  }

  record IncorrectDecimalPlacesBounds(int code, String msg) implements TokenLaunchpadError {

    public static final IncorrectDecimalPlacesBounds INSTANCE = new IncorrectDecimalPlacesBounds(
        6023, "Minimum Decimal Places cannot be lower than Maximum Decimal Places"
    );
  }

  record IncorrectTokenSupplyBounds(int code, String msg) implements TokenLaunchpadError {

    public static final IncorrectTokenSupplyBounds INSTANCE = new IncorrectTokenSupplyBounds(
        6024, "Minimum Token Supply cannot be lower than Maximum Token Supply"
    );
  }

  record TotalSupplyOutOfBounds(int code, String msg) implements TokenLaunchpadError {

    public static final TotalSupplyOutOfBounds INSTANCE = new TotalSupplyOutOfBounds(
        6025, "Token Total Supply out of bounds"
    );
  }

  record FinalCollateralTooLow(int code, String msg) implements TokenLaunchpadError {

    public static final FinalCollateralTooLow INSTANCE = new FinalCollateralTooLow(
        6026, "This setup will produce final collateral amount less than the migration fee"
    );
  }

  record CoefficientZero(int code, String msg) implements TokenLaunchpadError {

    public static final CoefficientZero INSTANCE = new CoefficientZero(
        6027, "One of the Coefficients is equal to ZERO"
    );
  }

  record MarketCapThresholdTooLow(int code, String msg) implements TokenLaunchpadError {

    public static final MarketCapThresholdTooLow INSTANCE = new MarketCapThresholdTooLow(
        6028, "Market cap Threshold under the Hard lower bound limits"
    );
  }

  record CoefBOutofBounds(int code, String msg) implements TokenLaunchpadError {

    public static final CoefBOutofBounds INSTANCE = new CoefBOutofBounds(
        6029, "Default coef_b set out of hard limit bounds"
    );
  }

  record IncorrectMarketCap(int code, String msg) implements TokenLaunchpadError {

    public static final IncorrectMarketCap INSTANCE = new IncorrectMarketCap(
        6030, "For Constant Product the Market Cap threshold cannot be higher than 325 SOL"
    );
  }

  record IncorrectDecimals(int code, String msg) implements TokenLaunchpadError {

    public static final IncorrectDecimals INSTANCE = new IncorrectDecimals(
        6031, "For Constant Product the Decimal places cannot be other than 9"
    );
  }

  record IncorrectMaxSupply(int code, String msg) implements TokenLaunchpadError {

    public static final IncorrectMaxSupply INSTANCE = new IncorrectMaxSupply(
        6032, "For Constant Product the Maximal Token Supply cannot be other than 1_000_000_000"
    );
  }

  record MarketCapTooHigh(int code, String msg) implements TokenLaunchpadError {

    public static final MarketCapTooHigh INSTANCE = new MarketCapTooHigh(
        6033, "Market Cap Threshold set too high, will not be hit even if Curve Hard Limit reached"
    );
  }

  record InvalidMigrationTarget(int code, String msg) implements TokenLaunchpadError {

    public static final InvalidMigrationTarget INSTANCE = new InvalidMigrationTarget(
        6034, "This Migration Target is not supported!"
    );
  }

  record General(int code, String msg) implements TokenLaunchpadError {

    public static final General INSTANCE = new General(
        6035, "General error"
    );
  }
}
