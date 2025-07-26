package software.sava.anchor.programs.kamino.scope.anchor;

import software.sava.anchor.ProgramError;

public sealed interface ScopeError extends ProgramError permits
    ScopeError.IntegerOverflow,
    ScopeError.ConversionFailure,
    ScopeError.MathOverflow,
    ScopeError.OutOfRangeIntegralConversion,
    ScopeError.UnexpectedAccount,
    ScopeError.PriceNotValid,
    ScopeError.AccountsAndTokenMismatch,
    ScopeError.BadTokenNb,
    ScopeError.BadTokenType,
    ScopeError.SwitchboardV2Error,
    ScopeError.InvalidAccountDiscriminator,
    ScopeError.UnableToDeserializeAccount,
    ScopeError.BadScopeChainOrPrices,
    ScopeError.RefreshInCPI,
    ScopeError.RefreshWithUnexpectedIxs,
    ScopeError.InvalidTokenUpdateMode,
    ScopeError.UnableToDerivePDA,
    ScopeError.BadTimestamp,
    ScopeError.BadSlot,
    ScopeError.PriceAccountNotExpected,
    ScopeError.TwapSourceIndexOutOfRange,
    ScopeError.TwapSampleTooFrequent,
    ScopeError.UnexpectedJlpConfiguration,
    ScopeError.TwapNotEnoughSamplesInPeriod,
    ScopeError.EmptyTokenList,
    ScopeError.StakeFeeTooHigh,
    ScopeError.KTokenUnderlyingPriceNotValid,
    ScopeError.KTokenHoldingsCalculationError,
    ScopeError.CannotResizeAccount,
    ScopeError.FixedPriceInvalid,
    ScopeError.SwitchboardOnDemandError,
    ScopeError.ConfidenceIntervalCheckFailed,
    ScopeError.InvalidGenericData,
    ScopeError.NoChainlinkReportData,
    ScopeError.InvalidChainlinkReportData,
    ScopeError.MostRecentOfInvalidSourceIndices,
    ScopeError.MostRecentOfInvalidMaxDivergence,
    ScopeError.MostRecentOfInvalidMaxAge,
    ScopeError.MostRecentOfMaxAgeViolated,
    ScopeError.MostRecentOfMaxDivergenceBpsViolated,
    ScopeError.PythLazerVerifyIxFailed,
    ScopeError.PythLazerInvalidFeedID,
    ScopeError.PythLazerInvalidExponent,
    ScopeError.PythLazerInvalidConfidenceFactor,
    ScopeError.PythLazerInvalidMessagePayload,
    ScopeError.PythLazerInvalidChannel,
    ScopeError.PythLazerInvalidFeedsLength,
    ScopeError.PythLazerInvalidFeedId2,
    ScopeError.PythLazerPriceNotPresent,
    ScopeError.PythLazerBestBidPriceNotPresent,
    ScopeError.PythLazerBestAskPriceNotPresent,
    ScopeError.PythLazerInvalidAskBidPrices,
    ScopeError.ExpectedPriceAccount,
    ScopeError.WrongAccountOwner,
    ScopeError.CompositeOracleInvalidSourceIndex,
    ScopeError.CappedFlooredBothCapAndFloorAreNone {

  static ScopeError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> IntegerOverflow.INSTANCE;
      case 6001 -> ConversionFailure.INSTANCE;
      case 6002 -> MathOverflow.INSTANCE;
      case 6003 -> OutOfRangeIntegralConversion.INSTANCE;
      case 6004 -> UnexpectedAccount.INSTANCE;
      case 6005 -> PriceNotValid.INSTANCE;
      case 6006 -> AccountsAndTokenMismatch.INSTANCE;
      case 6007 -> BadTokenNb.INSTANCE;
      case 6008 -> BadTokenType.INSTANCE;
      case 6009 -> SwitchboardV2Error.INSTANCE;
      case 6010 -> InvalidAccountDiscriminator.INSTANCE;
      case 6011 -> UnableToDeserializeAccount.INSTANCE;
      case 6012 -> BadScopeChainOrPrices.INSTANCE;
      case 6013 -> RefreshInCPI.INSTANCE;
      case 6014 -> RefreshWithUnexpectedIxs.INSTANCE;
      case 6015 -> InvalidTokenUpdateMode.INSTANCE;
      case 6016 -> UnableToDerivePDA.INSTANCE;
      case 6017 -> BadTimestamp.INSTANCE;
      case 6018 -> BadSlot.INSTANCE;
      case 6019 -> PriceAccountNotExpected.INSTANCE;
      case 6020 -> TwapSourceIndexOutOfRange.INSTANCE;
      case 6021 -> TwapSampleTooFrequent.INSTANCE;
      case 6022 -> UnexpectedJlpConfiguration.INSTANCE;
      case 6023 -> TwapNotEnoughSamplesInPeriod.INSTANCE;
      case 6024 -> EmptyTokenList.INSTANCE;
      case 6025 -> StakeFeeTooHigh.INSTANCE;
      case 6026 -> KTokenUnderlyingPriceNotValid.INSTANCE;
      case 6027 -> KTokenHoldingsCalculationError.INSTANCE;
      case 6028 -> CannotResizeAccount.INSTANCE;
      case 6029 -> FixedPriceInvalid.INSTANCE;
      case 6030 -> SwitchboardOnDemandError.INSTANCE;
      case 6031 -> ConfidenceIntervalCheckFailed.INSTANCE;
      case 6032 -> InvalidGenericData.INSTANCE;
      case 6033 -> NoChainlinkReportData.INSTANCE;
      case 6034 -> InvalidChainlinkReportData.INSTANCE;
      case 6035 -> MostRecentOfInvalidSourceIndices.INSTANCE;
      case 6036 -> MostRecentOfInvalidMaxDivergence.INSTANCE;
      case 6037 -> MostRecentOfInvalidMaxAge.INSTANCE;
      case 6038 -> MostRecentOfMaxAgeViolated.INSTANCE;
      case 6039 -> MostRecentOfMaxDivergenceBpsViolated.INSTANCE;
      case 6040 -> PythLazerVerifyIxFailed.INSTANCE;
      case 6041 -> PythLazerInvalidFeedID.INSTANCE;
      case 6042 -> PythLazerInvalidExponent.INSTANCE;
      case 6043 -> PythLazerInvalidConfidenceFactor.INSTANCE;
      case 6044 -> PythLazerInvalidMessagePayload.INSTANCE;
      case 6045 -> PythLazerInvalidChannel.INSTANCE;
      case 6046 -> PythLazerInvalidFeedsLength.INSTANCE;
      case 6047 -> PythLazerInvalidFeedId2.INSTANCE;
      case 6048 -> PythLazerPriceNotPresent.INSTANCE;
      case 6049 -> PythLazerBestBidPriceNotPresent.INSTANCE;
      case 6050 -> PythLazerBestAskPriceNotPresent.INSTANCE;
      case 6051 -> PythLazerInvalidAskBidPrices.INSTANCE;
      case 6052 -> ExpectedPriceAccount.INSTANCE;
      case 6053 -> WrongAccountOwner.INSTANCE;
      case 6054 -> CompositeOracleInvalidSourceIndex.INSTANCE;
      case 6055 -> CappedFlooredBothCapAndFloorAreNone.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Scope error code: " + errorCode);
    };
  }

  record IntegerOverflow(int code, String msg) implements ScopeError {

    public static final IntegerOverflow INSTANCE = new IntegerOverflow(
        6000, "Integer overflow"
    );
  }

  record ConversionFailure(int code, String msg) implements ScopeError {

    public static final ConversionFailure INSTANCE = new ConversionFailure(
        6001, "Conversion failure"
    );
  }

  record MathOverflow(int code, String msg) implements ScopeError {

    public static final MathOverflow INSTANCE = new MathOverflow(
        6002, "Mathematical operation with overflow"
    );
  }

  record OutOfRangeIntegralConversion(int code, String msg) implements ScopeError {

    public static final OutOfRangeIntegralConversion INSTANCE = new OutOfRangeIntegralConversion(
        6003, "Out of range integral conversion attempted"
    );
  }

  record UnexpectedAccount(int code, String msg) implements ScopeError {

    public static final UnexpectedAccount INSTANCE = new UnexpectedAccount(
        6004, "Unexpected account in instruction"
    );
  }

  record PriceNotValid(int code, String msg) implements ScopeError {

    public static final PriceNotValid INSTANCE = new PriceNotValid(
        6005, "Price is not valid"
    );
  }

  record AccountsAndTokenMismatch(int code, String msg) implements ScopeError {

    public static final AccountsAndTokenMismatch INSTANCE = new AccountsAndTokenMismatch(
        6006, "The number of tokens is different from the number of received accounts"
    );
  }

  record BadTokenNb(int code, String msg) implements ScopeError {

    public static final BadTokenNb INSTANCE = new BadTokenNb(
        6007, "The token index received is out of range"
    );
  }

  record BadTokenType(int code, String msg) implements ScopeError {

    public static final BadTokenType INSTANCE = new BadTokenType(
        6008, "The token type received is invalid"
    );
  }

  record SwitchboardV2Error(int code, String msg) implements ScopeError {

    public static final SwitchboardV2Error INSTANCE = new SwitchboardV2Error(
        6009, "There was an error with the Switchboard V2 retrieval"
    );
  }

  record InvalidAccountDiscriminator(int code, String msg) implements ScopeError {

    public static final InvalidAccountDiscriminator INSTANCE = new InvalidAccountDiscriminator(
        6010, "Invalid account discriminator"
    );
  }

  record UnableToDeserializeAccount(int code, String msg) implements ScopeError {

    public static final UnableToDeserializeAccount INSTANCE = new UnableToDeserializeAccount(
        6011, "Unable to deserialize account"
    );
  }

  record BadScopeChainOrPrices(int code, String msg) implements ScopeError {

    public static final BadScopeChainOrPrices INSTANCE = new BadScopeChainOrPrices(
        6012, "Error while computing price with ScopeChain"
    );
  }

  record RefreshInCPI(int code, String msg) implements ScopeError {

    public static final RefreshInCPI INSTANCE = new RefreshInCPI(
        6013, "Refresh price instruction called in a CPI"
    );
  }

  record RefreshWithUnexpectedIxs(int code, String msg) implements ScopeError {

    public static final RefreshWithUnexpectedIxs INSTANCE = new RefreshWithUnexpectedIxs(
        6014, "Refresh price instruction preceded by unexpected ixs"
    );
  }

  record InvalidTokenUpdateMode(int code, String msg) implements ScopeError {

    public static final InvalidTokenUpdateMode INSTANCE = new InvalidTokenUpdateMode(
        6015, "Invalid token metadata update mode"
    );
  }

  record UnableToDerivePDA(int code, String msg) implements ScopeError {

    public static final UnableToDerivePDA INSTANCE = new UnableToDerivePDA(
        6016, "Unable to derive PDA address"
    );
  }

  record BadTimestamp(int code, String msg) implements ScopeError {

    public static final BadTimestamp INSTANCE = new BadTimestamp(
        6017, "Invalid timestamp"
    );
  }

  record BadSlot(int code, String msg) implements ScopeError {

    public static final BadSlot INSTANCE = new BadSlot(
        6018, "Invalid slot"
    );
  }

  record PriceAccountNotExpected(int code, String msg) implements ScopeError {

    public static final PriceAccountNotExpected INSTANCE = new PriceAccountNotExpected(
        6019, "TWAP price account is different than Scope ID"
    );
  }

  record TwapSourceIndexOutOfRange(int code, String msg) implements ScopeError {

    public static final TwapSourceIndexOutOfRange INSTANCE = new TwapSourceIndexOutOfRange(
        6020, "TWAP source index out of range"
    );
  }

  record TwapSampleTooFrequent(int code, String msg) implements ScopeError {

    public static final TwapSampleTooFrequent INSTANCE = new TwapSampleTooFrequent(
        6021, "TWAP sample is too close to the previous one"
    );
  }

  record UnexpectedJlpConfiguration(int code, String msg) implements ScopeError {

    public static final UnexpectedJlpConfiguration INSTANCE = new UnexpectedJlpConfiguration(
        6022, "Unexpected JLP configuration"
    );
  }

  record TwapNotEnoughSamplesInPeriod(int code, String msg) implements ScopeError {

    public static final TwapNotEnoughSamplesInPeriod INSTANCE = new TwapNotEnoughSamplesInPeriod(
        6023, "Not enough price samples in period to compute TWAP"
    );
  }

  record EmptyTokenList(int code, String msg) implements ScopeError {

    public static final EmptyTokenList INSTANCE = new EmptyTokenList(
        6024, "The provided token list to refresh is empty"
    );
  }

  record StakeFeeTooHigh(int code, String msg) implements ScopeError {

    public static final StakeFeeTooHigh INSTANCE = new StakeFeeTooHigh(
        6025, "The stake pool fee is higher than the maximum allowed"
    );
  }

  record KTokenUnderlyingPriceNotValid(int code, String msg) implements ScopeError {

    public static final KTokenUnderlyingPriceNotValid INSTANCE = new KTokenUnderlyingPriceNotValid(
        6026, "Cannot get a valid price for the tokens composing the Ktoken"
    );
  }

  record KTokenHoldingsCalculationError(int code, String msg) implements ScopeError {

    public static final KTokenHoldingsCalculationError INSTANCE = new KTokenHoldingsCalculationError(
        6027, "Error while computing the Ktoken pool holdings"
    );
  }

  record CannotResizeAccount(int code, String msg) implements ScopeError {

    public static final CannotResizeAccount INSTANCE = new CannotResizeAccount(
        6028, "Cannot resize the account we only allow it to grow in size"
    );
  }

  record FixedPriceInvalid(int code, String msg) implements ScopeError {

    public static final FixedPriceInvalid INSTANCE = new FixedPriceInvalid(
        6029, "The provided fixed price is invalid"
    );
  }

  record SwitchboardOnDemandError(int code, String msg) implements ScopeError {

    public static final SwitchboardOnDemandError INSTANCE = new SwitchboardOnDemandError(
        6030, "Switchboard On Demand price derive error"
    );
  }

  record ConfidenceIntervalCheckFailed(int code, String msg) implements ScopeError {

    public static final ConfidenceIntervalCheckFailed INSTANCE = new ConfidenceIntervalCheckFailed(
        6031, "Confidence interval check failed"
    );
  }

  record InvalidGenericData(int code, String msg) implements ScopeError {

    public static final InvalidGenericData INSTANCE = new InvalidGenericData(
        6032, "Invalid generic data"
    );
  }

  record NoChainlinkReportData(int code, String msg) implements ScopeError {

    public static final NoChainlinkReportData INSTANCE = new NoChainlinkReportData(
        6033, "No valid Chainlink report data found"
    );
  }

  record InvalidChainlinkReportData(int code, String msg) implements ScopeError {

    public static final InvalidChainlinkReportData INSTANCE = new InvalidChainlinkReportData(
        6034, "Invalid Chainlink report data format"
    );
  }

  record MostRecentOfInvalidSourceIndices(int code, String msg) implements ScopeError {

    public static final MostRecentOfInvalidSourceIndices INSTANCE = new MostRecentOfInvalidSourceIndices(
        6035, "MostRecentOf config must contain at least one valid source index"
    );
  }

  record MostRecentOfInvalidMaxDivergence(int code, String msg) implements ScopeError {

    public static final MostRecentOfInvalidMaxDivergence INSTANCE = new MostRecentOfInvalidMaxDivergence(
        6036, "Invalid max divergence (bps) for MostRecentOf oracle"
    );
  }

  record MostRecentOfInvalidMaxAge(int code, String msg) implements ScopeError {

    public static final MostRecentOfInvalidMaxAge INSTANCE = new MostRecentOfInvalidMaxAge(
        6037, "Invalid max age (s) for MostRecentOf oracle"
    );
  }

  record MostRecentOfMaxAgeViolated(int code, String msg) implements ScopeError {

    public static final MostRecentOfMaxAgeViolated INSTANCE = new MostRecentOfMaxAgeViolated(
        6038, "Max age diff constraint violated for MostRecentOf oracle"
    );
  }

  record MostRecentOfMaxDivergenceBpsViolated(int code, String msg) implements ScopeError {

    public static final MostRecentOfMaxDivergenceBpsViolated INSTANCE = new MostRecentOfMaxDivergenceBpsViolated(
        6039, "Max divergence bps constraint violated for MostRecentOf oracle"
    );
  }

  record PythLazerVerifyIxFailed(int code, String msg) implements ScopeError {

    public static final PythLazerVerifyIxFailed INSTANCE = new PythLazerVerifyIxFailed(
        6040, "The invoked pyth lazer verify instruction failed"
    );
  }

  record PythLazerInvalidFeedID(int code, String msg) implements ScopeError {

    public static final PythLazerInvalidFeedID INSTANCE = new PythLazerInvalidFeedID(
        6041, "Invalid feed id passed in to PythLazer oracle"
    );
  }

  record PythLazerInvalidExponent(int code, String msg) implements ScopeError {

    public static final PythLazerInvalidExponent INSTANCE = new PythLazerInvalidExponent(
        6042, "Invalid exponent passed in to PythLazer oracle"
    );
  }

  record PythLazerInvalidConfidenceFactor(int code, String msg) implements ScopeError {

    public static final PythLazerInvalidConfidenceFactor INSTANCE = new PythLazerInvalidConfidenceFactor(
        6043, "Invalid confidence factor passed in to PythLazer oracle"
    );
  }

  record PythLazerInvalidMessagePayload(int code, String msg) implements ScopeError {

    public static final PythLazerInvalidMessagePayload INSTANCE = new PythLazerInvalidMessagePayload(
        6044, "Received an invalid message payload in the PythLazer oracle when refreshing price"
    );
  }

  record PythLazerInvalidChannel(int code, String msg) implements ScopeError {

    public static final PythLazerInvalidChannel INSTANCE = new PythLazerInvalidChannel(
        6045, "Received an invalid channel in the PythLazer payload when refreshing price"
    );
  }

  record PythLazerInvalidFeedsLength(int code, String msg) implements ScopeError {

    public static final PythLazerInvalidFeedsLength INSTANCE = new PythLazerInvalidFeedsLength(
        6046, "Payload should have a single feed in the PythLazer payload when refreshing price"
    );
  }

  record PythLazerInvalidFeedId2(int code, String msg) implements ScopeError {

    public static final PythLazerInvalidFeedId2 INSTANCE = new PythLazerInvalidFeedId2(
        6047, "Invalid feed id in the PythLazer payload when refreshing price"
    );
  }

  record PythLazerPriceNotPresent(int code, String msg) implements ScopeError {

    public static final PythLazerPriceNotPresent INSTANCE = new PythLazerPriceNotPresent(
        6048, "Property fields in the feed of the PythLazer payload do not contain a price"
    );
  }

  record PythLazerBestBidPriceNotPresent(int code, String msg) implements ScopeError {

    public static final PythLazerBestBidPriceNotPresent INSTANCE = new PythLazerBestBidPriceNotPresent(
        6049, "Property fields in the feed of the PythLazer payload do not contain a best bid price"
    );
  }

  record PythLazerBestAskPriceNotPresent(int code, String msg) implements ScopeError {

    public static final PythLazerBestAskPriceNotPresent INSTANCE = new PythLazerBestAskPriceNotPresent(
        6050, "Property fields in the feed of the PythLazer payload do not contain a best ask price"
    );
  }

  record PythLazerInvalidAskBidPrices(int code, String msg) implements ScopeError {

    public static final PythLazerInvalidAskBidPrices INSTANCE = new PythLazerInvalidAskBidPrices(
        6051, "Invalid ask/bid prices provided in the feed of the PythLazer payload"
    );
  }

  record ExpectedPriceAccount(int code, String msg) implements ScopeError {

    public static final ExpectedPriceAccount INSTANCE = new ExpectedPriceAccount(
        6052, "Price account expected when updating mapping"
    );
  }

  record WrongAccountOwner(int code, String msg) implements ScopeError {

    public static final WrongAccountOwner INSTANCE = new WrongAccountOwner(
        6053, "Provided account has a different owner than expected"
    );
  }

  record CompositeOracleInvalidSourceIndex(int code, String msg) implements ScopeError {

    public static final CompositeOracleInvalidSourceIndex INSTANCE = new CompositeOracleInvalidSourceIndex(
        6054, "Provided source index is invalid"
    );
  }

  record CappedFlooredBothCapAndFloorAreNone(int code, String msg) implements ScopeError {

    public static final CappedFlooredBothCapAndFloorAreNone INSTANCE = new CappedFlooredBothCapAndFloorAreNone(
        6055, "Can't set both cap and floor to None for CappedFloored oracle"
    );
  }
}
