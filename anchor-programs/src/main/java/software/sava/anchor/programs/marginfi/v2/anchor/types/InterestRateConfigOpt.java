package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.borsh.Borsh;

public record InterestRateConfigOpt(WrappedI80F48 optimalUtilizationRate,
                                    WrappedI80F48 plateauInterestRate,
                                    WrappedI80F48 maxInterestRate,
                                    WrappedI80F48 insuranceFeeFixedApr,
                                    WrappedI80F48 insuranceIrFee,
                                    WrappedI80F48 protocolFixedFeeApr,
                                    WrappedI80F48 protocolIrFee,
                                    WrappedI80F48 protocolOriginationFee) implements Borsh {

  public static InterestRateConfigOpt read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var optimalUtilizationRate = _data[i++] == 0 ? null : WrappedI80F48.read(_data, i);
    if (optimalUtilizationRate != null) {
      i += Borsh.len(optimalUtilizationRate);
    }
    final var plateauInterestRate = _data[i++] == 0 ? null : WrappedI80F48.read(_data, i);
    if (plateauInterestRate != null) {
      i += Borsh.len(plateauInterestRate);
    }
    final var maxInterestRate = _data[i++] == 0 ? null : WrappedI80F48.read(_data, i);
    if (maxInterestRate != null) {
      i += Borsh.len(maxInterestRate);
    }
    final var insuranceFeeFixedApr = _data[i++] == 0 ? null : WrappedI80F48.read(_data, i);
    if (insuranceFeeFixedApr != null) {
      i += Borsh.len(insuranceFeeFixedApr);
    }
    final var insuranceIrFee = _data[i++] == 0 ? null : WrappedI80F48.read(_data, i);
    if (insuranceIrFee != null) {
      i += Borsh.len(insuranceIrFee);
    }
    final var protocolFixedFeeApr = _data[i++] == 0 ? null : WrappedI80F48.read(_data, i);
    if (protocolFixedFeeApr != null) {
      i += Borsh.len(protocolFixedFeeApr);
    }
    final var protocolIrFee = _data[i++] == 0 ? null : WrappedI80F48.read(_data, i);
    if (protocolIrFee != null) {
      i += Borsh.len(protocolIrFee);
    }
    final var protocolOriginationFee = _data[i++] == 0 ? null : WrappedI80F48.read(_data, i);
    return new InterestRateConfigOpt(optimalUtilizationRate,
                                     plateauInterestRate,
                                     maxInterestRate,
                                     insuranceFeeFixedApr,
                                     insuranceIrFee,
                                     protocolFixedFeeApr,
                                     protocolIrFee,
                                     protocolOriginationFee);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(optimalUtilizationRate, _data, i);
    i += Borsh.writeOptional(plateauInterestRate, _data, i);
    i += Borsh.writeOptional(maxInterestRate, _data, i);
    i += Borsh.writeOptional(insuranceFeeFixedApr, _data, i);
    i += Borsh.writeOptional(insuranceIrFee, _data, i);
    i += Borsh.writeOptional(protocolFixedFeeApr, _data, i);
    i += Borsh.writeOptional(protocolIrFee, _data, i);
    i += Borsh.writeOptional(protocolOriginationFee, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (optimalUtilizationRate == null ? 1 : (1 + Borsh.len(optimalUtilizationRate)))
         + (plateauInterestRate == null ? 1 : (1 + Borsh.len(plateauInterestRate)))
         + (maxInterestRate == null ? 1 : (1 + Borsh.len(maxInterestRate)))
         + (insuranceFeeFixedApr == null ? 1 : (1 + Borsh.len(insuranceFeeFixedApr)))
         + (insuranceIrFee == null ? 1 : (1 + Borsh.len(insuranceIrFee)))
         + (protocolFixedFeeApr == null ? 1 : (1 + Borsh.len(protocolFixedFeeApr)))
         + (protocolIrFee == null ? 1 : (1 + Borsh.len(protocolIrFee)))
         + (protocolOriginationFee == null ? 1 : (1 + Borsh.len(protocolOriginationFee)));
  }
}
