package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.borsh.Borsh;

public record InterestRateConfigCompact(WrappedI80F48 optimalUtilizationRate,
                                        WrappedI80F48 plateauInterestRate,
                                        WrappedI80F48 maxInterestRate,
                                        WrappedI80F48 insuranceFeeFixedApr,
                                        WrappedI80F48 insuranceIrFee,
                                        WrappedI80F48 protocolFixedFeeApr,
                                        WrappedI80F48 protocolIrFee,
                                        WrappedI80F48 protocolOriginationFee) implements Borsh {

  public static final int BYTES = 128;

  public static InterestRateConfigCompact read(final byte[] _data, final int offset) {
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
    return new InterestRateConfigCompact(optimalUtilizationRate,
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
    i += Borsh.write(optimalUtilizationRate, _data, i);
    i += Borsh.write(plateauInterestRate, _data, i);
    i += Borsh.write(maxInterestRate, _data, i);
    i += Borsh.write(insuranceFeeFixedApr, _data, i);
    i += Borsh.write(insuranceIrFee, _data, i);
    i += Borsh.write(protocolFixedFeeApr, _data, i);
    i += Borsh.write(protocolIrFee, _data, i);
    i += Borsh.write(protocolOriginationFee, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
