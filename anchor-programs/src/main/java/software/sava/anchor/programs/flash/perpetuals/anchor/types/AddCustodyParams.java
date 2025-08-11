package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record AddCustodyParams(boolean isStable,
                               boolean depegAdjustment,
                               boolean isVirtual,
                               boolean token22,
                               OracleParams oracle,
                               PricingParams pricing,
                               Permissions permissions,
                               Fees fees,
                               BorrowRateParams borrowRate,
                               TokenRatios[] ratios) implements Borsh {

  public static AddCustodyParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var isStable = _data[i] == 1;
    ++i;
    final var depegAdjustment = _data[i] == 1;
    ++i;
    final var isVirtual = _data[i] == 1;
    ++i;
    final var token22 = _data[i] == 1;
    ++i;
    final var oracle = OracleParams.read(_data, i);
    i += Borsh.len(oracle);
    final var pricing = PricingParams.read(_data, i);
    i += Borsh.len(pricing);
    final var permissions = Permissions.read(_data, i);
    i += Borsh.len(permissions);
    final var fees = Fees.read(_data, i);
    i += Borsh.len(fees);
    final var borrowRate = BorrowRateParams.read(_data, i);
    i += Borsh.len(borrowRate);
    final var ratios = Borsh.readVector(TokenRatios.class, TokenRatios::read, _data, i);
    return new AddCustodyParams(isStable,
                                depegAdjustment,
                                isVirtual,
                                token22,
                                oracle,
                                pricing,
                                permissions,
                                fees,
                                borrowRate,
                                ratios);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) (isStable ? 1 : 0);
    ++i;
    _data[i] = (byte) (depegAdjustment ? 1 : 0);
    ++i;
    _data[i] = (byte) (isVirtual ? 1 : 0);
    ++i;
    _data[i] = (byte) (token22 ? 1 : 0);
    ++i;
    i += Borsh.write(oracle, _data, i);
    i += Borsh.write(pricing, _data, i);
    i += Borsh.write(permissions, _data, i);
    i += Borsh.write(fees, _data, i);
    i += Borsh.write(borrowRate, _data, i);
    i += Borsh.writeVector(ratios, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 1
         + 1
         + 1
         + 1
         + Borsh.len(oracle)
         + Borsh.len(pricing)
         + Borsh.len(permissions)
         + Borsh.len(fees)
         + Borsh.len(borrowRate)
         + Borsh.lenVector(ratios);
  }
}
