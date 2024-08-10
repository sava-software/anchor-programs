package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ShareClassDynamicFieldName implements Borsh.Enum {

  AskNAV,
  AskNAVDate,
  AuMShareClass,
  AuMShareClassDate,
  BidNAV,
  BidNAVDate,
  DividendAnnouncementDate,
  DividendCurrency,
  DividendExDate,
  DividendGross,
  DividendNet,
  DividendPaymentDate,
  DividendRecordDate,
  DynamicCurrency,
  DynamicDataType,
  DynamicValue,
  EqualisationRate,
  GeneralReferenceDate,
  IsDividendFinal,
  NoSShareClass,
  NoSShareClassDate,
  SplitRatio,
  SplitReferenceDate,
  TaxDeductedReinvestedAmount,
  TaxDeductedReinvestedAmountReferenceDate,
  TaxableIncomeperDividend,
  TaxableIncomeperShareEU,
  TransactionNAV,
  TransactionNAVDate,
  ValuationNAV,
  ValuationNAVDate,
  YieldOneDayGross,
  YieldOneDayNet,
  YieldSevenDayGross,
  YieldSevenDayNet,
  YieldThirtyDayGross,
  YieldThirtyDayNet;

  public static ShareClassDynamicFieldName read(final byte[] _data, final int offset) {
    return Borsh.read(ShareClassDynamicFieldName.values(), _data, offset);
  }
}