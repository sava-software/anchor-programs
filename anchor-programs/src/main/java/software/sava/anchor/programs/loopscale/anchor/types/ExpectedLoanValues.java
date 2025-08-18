package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ExpectedLoanValues(long expectedApy, int[] expectedLqt) implements Borsh {

  public static final int BYTES = 28;
  public static final int EXPECTED_LQT_LEN = 5;

  public static ExpectedLoanValues read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var expectedApy = getInt64LE(_data, i);
    i += 8;
    final var expectedLqt = new int[5];
    Borsh.readArray(expectedLqt, _data, i);
    return new ExpectedLoanValues(expectedApy, expectedLqt);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, expectedApy);
    i += 8;
    i += Borsh.writeArray(expectedLqt, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
