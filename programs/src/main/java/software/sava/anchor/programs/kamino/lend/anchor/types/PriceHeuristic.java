package software.sava.anchor.programs.kamino.lend.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PriceHeuristic(long lower,
                             long upper,
                             long exp) implements Borsh {

  public static final int BYTES = 24;

  public static PriceHeuristic read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lower = getInt64LE(_data, i);
    i += 8;
    final var upper = getInt64LE(_data, i);
    i += 8;
    final var exp = getInt64LE(_data, i);
    return new PriceHeuristic(lower, upper, exp);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, lower);
    i += 8;
    putInt64LE(_data, i, upper);
    i += 8;
    putInt64LE(_data, i, exp);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
