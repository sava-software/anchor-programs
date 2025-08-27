package software.sava.anchor.programs.glam.protocol.anchor.types;

import software.sava.core.borsh.Borsh;

public enum CompanyFieldName implements Borsh.Enum {

  FundGroupName,
  ManCo,
  DomicileOfManCo,
  BICOfCustodian,
  CollateralManagerName,
  CustodianBankName,
  DomicileOfCustodianBank,
  FundAdministratorName,
  FundAdvisorName,
  FundPromoterName,
  IsSelfManagedInvestmentCompany,
  LEIOfCustodianBank,
  LEIOfManCo,
  PortfolioManagingCompanyName,
  SecuritiesLendingCounterpartyName,
  SwapCounterpartyName,
  AddressofManCo,
  AuditorName,
  CityofManCo,
  EmailAddressOfManCo,
  FundWebsiteOfManCo,
  IsUNPRISignatory,
  PhoneCountryCodeofManCo,
  PhoneNumberofManCo,
  SubInvestmentAdvisorName,
  ZIPCodeofManCo,
  DomicileOfUmbrella,
  HasUmbrella,
  LEIOfUmbrella,
  Umbrella,
  GlobalIntermediaryIdentificationNumberOfUmbrella;

  public static CompanyFieldName read(final byte[] _data, final int offset) {
    return Borsh.read(CompanyFieldName.values(), _data, offset);
  }
}