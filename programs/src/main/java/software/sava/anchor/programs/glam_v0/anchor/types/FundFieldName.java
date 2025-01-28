package software.sava.anchor.programs.glam_v0.anchor.types;

import software.sava.core.borsh.Borsh;

public enum FundFieldName implements Borsh.Enum {

  FundDomicileAlpha2,
  FundDomicileAlpha3,
  LegalFundNameIncludingUmbrella,
  FiscalYearEnd,
  FundCurrency,
  FundLaunchDate,
  InvestmentObjective,
  IsETC,
  IsEUDirectiveRelevant,
  IsFundOfFunds,
  IsPassiveFund,
  IsREIT,
  LegalForm,
  LegalFundNameOnly,
  OpenEndedOrClosedEndedFundStructure,
  TypeOfEUDirective,
  UCITSVersion,
  CurrencyHedgePortfolio,
  DepositoryName,
  FundValuationPoint,
  FundValuationPointTimeZone,
  FundValuationPointTimeZoneUsingTZDatabase,
  HasCollateralManager,
  HasEmbeddedDerivatives,
  HasSecuritiesLending,
  HasSwap,
  IsLeveraged,
  IsShariaCompliant,
  IsShort,
  LEIofDepositoryBank,
  LEIOfFund,
  LocationOfBearerShare,
  LocationOfShareRegister,
  MaximumLeverageInFund,
  MiFIDSecuritiesClassification,
  MoneyMarketTypeOfFund,
  TrusteeName,
  AuMFund,
  AuMFundDate,
  NoSFund,
  NoSFundDate;

  public static FundFieldName read(final byte[] _data, final int offset) {
    return Borsh.read(FundFieldName.values(), _data, offset);
  }
}
