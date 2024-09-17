package software.sava.anchor.programs.glam.anchor.types;

import java.lang.Boolean;
import java.lang.String;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;

public record FundOpenfundsModel(String fundDomicileAlpha2, byte[] _fundDomicileAlpha2,
                                 String legalFundNameIncludingUmbrella, byte[] _legalFundNameIncludingUmbrella,
                                 String fiscalYearEnd, byte[] _fiscalYearEnd,
                                 String fundCurrency, byte[] _fundCurrency,
                                 String fundLaunchDate, byte[] _fundLaunchDate,
                                 String investmentObjective, byte[] _investmentObjective,
                                 Boolean isEtc,
                                 Boolean isEuDirectiveRelevant,
                                 Boolean isFundOfFunds,
                                 Boolean isPassiveFund,
                                 Boolean isReit,
                                 String legalForm, byte[] _legalForm,
                                 String legalFundNameOnly, byte[] _legalFundNameOnly,
                                 String openEndedOrClosedEndedFundStructure, byte[] _openEndedOrClosedEndedFundStructure,
                                 String typeOfEuDirective, byte[] _typeOfEuDirective,
                                 String ucitsVersion, byte[] _ucitsVersion) implements Borsh {

  public static FundOpenfundsModel createRecord(final String fundDomicileAlpha2,
                                                final String legalFundNameIncludingUmbrella,
                                                final String fiscalYearEnd,
                                                final String fundCurrency,
                                                final String fundLaunchDate,
                                                final String investmentObjective,
                                                final Boolean isEtc,
                                                final Boolean isEuDirectiveRelevant,
                                                final Boolean isFundOfFunds,
                                                final Boolean isPassiveFund,
                                                final Boolean isReit,
                                                final String legalForm,
                                                final String legalFundNameOnly,
                                                final String openEndedOrClosedEndedFundStructure,
                                                final String typeOfEuDirective,
                                                final String ucitsVersion) {
    return new FundOpenfundsModel(fundDomicileAlpha2, Borsh.getBytes(fundDomicileAlpha2),
                                  legalFundNameIncludingUmbrella, Borsh.getBytes(legalFundNameIncludingUmbrella),
                                  fiscalYearEnd, Borsh.getBytes(fiscalYearEnd),
                                  fundCurrency, Borsh.getBytes(fundCurrency),
                                  fundLaunchDate, Borsh.getBytes(fundLaunchDate),
                                  investmentObjective, Borsh.getBytes(investmentObjective),
                                  isEtc,
                                  isEuDirectiveRelevant,
                                  isFundOfFunds,
                                  isPassiveFund,
                                  isReit,
                                  legalForm, Borsh.getBytes(legalForm),
                                  legalFundNameOnly, Borsh.getBytes(legalFundNameOnly),
                                  openEndedOrClosedEndedFundStructure, Borsh.getBytes(openEndedOrClosedEndedFundStructure),
                                  typeOfEuDirective, Borsh.getBytes(typeOfEuDirective),
                                  ucitsVersion, Borsh.getBytes(ucitsVersion));
  }

  public static FundOpenfundsModel read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var fundDomicileAlpha2 = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (fundDomicileAlpha2 != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var legalFundNameIncludingUmbrella = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (legalFundNameIncludingUmbrella != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var fiscalYearEnd = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (fiscalYearEnd != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var fundCurrency = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (fundCurrency != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var fundLaunchDate = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (fundLaunchDate != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var investmentObjective = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (investmentObjective != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var isEtc = _data[i++] == 0 ? null : _data[i] == 1;
    if (isEtc != null) {
      ++i;
    }
    final var isEuDirectiveRelevant = _data[i++] == 0 ? null : _data[i] == 1;
    if (isEuDirectiveRelevant != null) {
      ++i;
    }
    final var isFundOfFunds = _data[i++] == 0 ? null : _data[i] == 1;
    if (isFundOfFunds != null) {
      ++i;
    }
    final var isPassiveFund = _data[i++] == 0 ? null : _data[i] == 1;
    if (isPassiveFund != null) {
      ++i;
    }
    final var isReit = _data[i++] == 0 ? null : _data[i] == 1;
    if (isReit != null) {
      ++i;
    }
    final var legalForm = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (legalForm != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var legalFundNameOnly = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (legalFundNameOnly != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var openEndedOrClosedEndedFundStructure = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (openEndedOrClosedEndedFundStructure != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var typeOfEuDirective = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (typeOfEuDirective != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var ucitsVersion = _data[i++] == 0 ? null : Borsh.string(_data, i);
    return new FundOpenfundsModel(fundDomicileAlpha2, Borsh.getBytes(fundDomicileAlpha2),
                                  legalFundNameIncludingUmbrella, Borsh.getBytes(legalFundNameIncludingUmbrella),
                                  fiscalYearEnd, Borsh.getBytes(fiscalYearEnd),
                                  fundCurrency, Borsh.getBytes(fundCurrency),
                                  fundLaunchDate, Borsh.getBytes(fundLaunchDate),
                                  investmentObjective, Borsh.getBytes(investmentObjective),
                                  isEtc,
                                  isEuDirectiveRelevant,
                                  isFundOfFunds,
                                  isPassiveFund,
                                  isReit,
                                  legalForm, Borsh.getBytes(legalForm),
                                  legalFundNameOnly, Borsh.getBytes(legalFundNameOnly),
                                  openEndedOrClosedEndedFundStructure, Borsh.getBytes(openEndedOrClosedEndedFundStructure),
                                  typeOfEuDirective, Borsh.getBytes(typeOfEuDirective),
                                  ucitsVersion, Borsh.getBytes(ucitsVersion));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptionalVector(_fundDomicileAlpha2, _data, i);
    i += Borsh.writeOptionalVector(_legalFundNameIncludingUmbrella, _data, i);
    i += Borsh.writeOptionalVector(_fiscalYearEnd, _data, i);
    i += Borsh.writeOptionalVector(_fundCurrency, _data, i);
    i += Borsh.writeOptionalVector(_fundLaunchDate, _data, i);
    i += Borsh.writeOptionalVector(_investmentObjective, _data, i);
    i += Borsh.writeOptional(isEtc, _data, i);
    i += Borsh.writeOptional(isEuDirectiveRelevant, _data, i);
    i += Borsh.writeOptional(isFundOfFunds, _data, i);
    i += Borsh.writeOptional(isPassiveFund, _data, i);
    i += Borsh.writeOptional(isReit, _data, i);
    i += Borsh.writeOptionalVector(_legalForm, _data, i);
    i += Borsh.writeOptionalVector(_legalFundNameOnly, _data, i);
    i += Borsh.writeOptionalVector(_openEndedOrClosedEndedFundStructure, _data, i);
    i += Borsh.writeOptionalVector(_typeOfEuDirective, _data, i);
    i += Borsh.writeOptionalVector(_ucitsVersion, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (_fundDomicileAlpha2 == null || _fundDomicileAlpha2.length == 0 ? 1 : (1 + Borsh.lenVector(_fundDomicileAlpha2)))
         + (_legalFundNameIncludingUmbrella == null || _legalFundNameIncludingUmbrella.length == 0 ? 1 : (1 + Borsh.lenVector(_legalFundNameIncludingUmbrella)))
         + (_fiscalYearEnd == null || _fiscalYearEnd.length == 0 ? 1 : (1 + Borsh.lenVector(_fiscalYearEnd)))
         + (_fundCurrency == null || _fundCurrency.length == 0 ? 1 : (1 + Borsh.lenVector(_fundCurrency)))
         + (_fundLaunchDate == null || _fundLaunchDate.length == 0 ? 1 : (1 + Borsh.lenVector(_fundLaunchDate)))
         + (_investmentObjective == null || _investmentObjective.length == 0 ? 1 : (1 + Borsh.lenVector(_investmentObjective)))
         + (isEtc == null ? 1 : (1 + 1))
         + (isEuDirectiveRelevant == null ? 1 : (1 + 1))
         + (isFundOfFunds == null ? 1 : (1 + 1))
         + (isPassiveFund == null ? 1 : (1 + 1))
         + (isReit == null ? 1 : (1 + 1))
         + (_legalForm == null || _legalForm.length == 0 ? 1 : (1 + Borsh.lenVector(_legalForm)))
         + (_legalFundNameOnly == null || _legalFundNameOnly.length == 0 ? 1 : (1 + Borsh.lenVector(_legalFundNameOnly)))
         + (_openEndedOrClosedEndedFundStructure == null || _openEndedOrClosedEndedFundStructure.length == 0 ? 1 : (1 + Borsh.lenVector(_openEndedOrClosedEndedFundStructure)))
         + (_typeOfEuDirective == null || _typeOfEuDirective.length == 0 ? 1 : (1 + Borsh.lenVector(_typeOfEuDirective)))
         + (_ucitsVersion == null || _ucitsVersion.length == 0 ? 1 : (1 + Borsh.lenVector(_ucitsVersion)));
  }
}
