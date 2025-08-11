package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SetCustodyConfigParams(boolean depegAdjustment,
                                     OracleParams oracle,
                                     PricingParams pricing,
                                     Permissions permissions,
                                     Fees fees,
                                     BorrowRateParams borrowRate,
                                     TokenRatios[] ratios,
                                     long rewardThreshold,
                                     long minReserveUsd,
                                     long limitPriceBufferBps,
                                     boolean token22) implements Borsh {

  public static SetCustodyConfigParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var depegAdjustment = _data[i] == 1;
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
    i += Borsh.lenVector(ratios);
    final var rewardThreshold = getInt64LE(_data, i);
    i += 8;
    final var minReserveUsd = getInt64LE(_data, i);
    i += 8;
    final var limitPriceBufferBps = getInt64LE(_data, i);
    i += 8;
    final var token22 = _data[i] == 1;
    return new SetCustodyConfigParams(depegAdjustment,
                                      oracle,
                                      pricing,
                                      permissions,
                                      fees,
                                      borrowRate,
                                      ratios,
                                      rewardThreshold,
                                      minReserveUsd,
                                      limitPriceBufferBps,
                                      token22);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) (depegAdjustment ? 1 : 0);
    ++i;
    i += Borsh.write(oracle, _data, i);
    i += Borsh.write(pricing, _data, i);
    i += Borsh.write(permissions, _data, i);
    i += Borsh.write(fees, _data, i);
    i += Borsh.write(borrowRate, _data, i);
    i += Borsh.writeVector(ratios, _data, i);
    putInt64LE(_data, i, rewardThreshold);
    i += 8;
    putInt64LE(_data, i, minReserveUsd);
    i += 8;
    putInt64LE(_data, i, limitPriceBufferBps);
    i += 8;
    _data[i] = (byte) (token22 ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return 1
         + Borsh.len(oracle)
         + Borsh.len(pricing)
         + Borsh.len(permissions)
         + Borsh.len(fees)
         + Borsh.len(borrowRate)
         + Borsh.lenVector(ratios)
         + 8
         + 8
         + 8
         + 1;
  }
}
