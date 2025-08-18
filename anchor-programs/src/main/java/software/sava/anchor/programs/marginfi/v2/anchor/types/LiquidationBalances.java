package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getFloat64LE;
import static software.sava.core.encoding.ByteUtil.putFloat64LE;

public record LiquidationBalances(double liquidateeAssetBalance,
                                  double liquidateeLiabilityBalance,
                                  double liquidatorAssetBalance,
                                  double liquidatorLiabilityBalance) implements Borsh {

  public static final int BYTES = 32;

  public static LiquidationBalances read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var liquidateeAssetBalance = getFloat64LE(_data, i);
    i += 8;
    final var liquidateeLiabilityBalance = getFloat64LE(_data, i);
    i += 8;
    final var liquidatorAssetBalance = getFloat64LE(_data, i);
    i += 8;
    final var liquidatorLiabilityBalance = getFloat64LE(_data, i);
    return new LiquidationBalances(liquidateeAssetBalance,
                                   liquidateeLiabilityBalance,
                                   liquidatorAssetBalance,
                                   liquidatorLiabilityBalance);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putFloat64LE(_data, i, liquidateeAssetBalance);
    i += 8;
    putFloat64LE(_data, i, liquidateeLiabilityBalance);
    i += 8;
    putFloat64LE(_data, i, liquidatorAssetBalance);
    i += 8;
    putFloat64LE(_data, i, liquidatorLiabilityBalance);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
