package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record Delegation(int numerator, int denominator) implements Borsh {

  public static final int BYTES = 8;

  public static Delegation read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var numerator = getInt32LE(_data, i);
    i += 4;
    final var denominator = getInt32LE(_data, i);
    return new Delegation(numerator, denominator);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, numerator);
    i += 4;
    putInt32LE(_data, i, denominator);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
