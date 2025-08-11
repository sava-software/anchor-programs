package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CompoundingTokenData(long lpPrice,
                                   long compoundingPrice,
                                   long[] ratios) implements Borsh {

  public static CompoundingTokenData read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lpPrice = getInt64LE(_data, i);
    i += 8;
    final var compoundingPrice = getInt64LE(_data, i);
    i += 8;
    final var ratios = Borsh.readlongVector(_data, i);
    return new CompoundingTokenData(lpPrice, compoundingPrice, ratios);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, lpPrice);
    i += 8;
    putInt64LE(_data, i, compoundingPrice);
    i += 8;
    i += Borsh.writeVector(ratios, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 8 + Borsh.lenVector(ratios);
  }
}
