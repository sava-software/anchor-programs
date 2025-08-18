package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.borsh.Borsh;

public record InterestRateConfig(WrappedI80F48 optimalUtilizationRate,
                                 WrappedI80F48 plateauInterestRate,
                                 WrappedI80F48 maxInterestRate,
                                 // Goes to insurance, funds `collected_insurance_fees_outstanding`
                                 WrappedI80F48 insuranceFeeFixedApr,
                                 // Goes to insurance, funds `collected_insurance_fees_outstanding`
                                 WrappedI80F48 insuranceIrFee,
                                 // Earned by the group, goes to `collected_group_fees_outstanding`
                                 WrappedI80F48 protocolFixedFeeApr,
                                 // Earned by the group, goes to `collected_group_fees_outstanding`
                                 WrappedI80F48 protocolIrFee,
                                 WrappedI80F48 protocolOriginationFee,
                                 byte[] padding0,
                                 byte[][] padding1) implements Borsh {

  public static final int BYTES = 240;
  public static final int PADDING_0_LEN = 16;
  public static final int PADDING_1_LEN = 3;

  public static InterestRateConfig read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var optimalUtilizationRate = WrappedI80F48.read(_data, i);
    i += Borsh.len(optimalUtilizationRate);
    final var plateauInterestRate = WrappedI80F48.read(_data, i);
    i += Borsh.len(plateauInterestRate);
    final var maxInterestRate = WrappedI80F48.read(_data, i);
    i += Borsh.len(maxInterestRate);
    final var insuranceFeeFixedApr = WrappedI80F48.read(_data, i);
    i += Borsh.len(insuranceFeeFixedApr);
    final var insuranceIrFee = WrappedI80F48.read(_data, i);
    i += Borsh.len(insuranceIrFee);
    final var protocolFixedFeeApr = WrappedI80F48.read(_data, i);
    i += Borsh.len(protocolFixedFeeApr);
    final var protocolIrFee = WrappedI80F48.read(_data, i);
    i += Borsh.len(protocolIrFee);
    final var protocolOriginationFee = WrappedI80F48.read(_data, i);
    i += Borsh.len(protocolOriginationFee);
    final var padding0 = new byte[16];
    i += Borsh.readArray(padding0, _data, i);
    final var padding1 = new byte[3][32];
    Borsh.readArray(padding1, _data, i);
    return new InterestRateConfig(optimalUtilizationRate,
                                  plateauInterestRate,
                                  maxInterestRate,
                                  insuranceFeeFixedApr,
                                  insuranceIrFee,
                                  protocolFixedFeeApr,
                                  protocolIrFee,
                                  protocolOriginationFee,
                                  padding0,
                                  padding1);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(optimalUtilizationRate, _data, i);
    i += Borsh.write(plateauInterestRate, _data, i);
    i += Borsh.write(maxInterestRate, _data, i);
    i += Borsh.write(insuranceFeeFixedApr, _data, i);
    i += Borsh.write(insuranceIrFee, _data, i);
    i += Borsh.write(protocolFixedFeeApr, _data, i);
    i += Borsh.write(protocolIrFee, _data, i);
    i += Borsh.write(protocolOriginationFee, _data, i);
    i += Borsh.writeArray(padding0, _data, i);
    i += Borsh.writeArray(padding1, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
