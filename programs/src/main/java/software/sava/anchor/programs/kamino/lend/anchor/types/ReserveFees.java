package software.sava.anchor.programs.kamino.lend.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ReserveFees(long borrowFeeSf,
                          long flashLoanFeeSf,
                          byte[] padding) implements Borsh {

  public static final int BYTES = 24;

  public static ReserveFees read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var borrowFeeSf = getInt64LE(_data, i);
    i += 8;
    final var flashLoanFeeSf = getInt64LE(_data, i);
    i += 8;
    final var padding = new byte[8];
    Borsh.readArray(padding, _data, i);
    return new ReserveFees(borrowFeeSf, flashLoanFeeSf, padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, borrowFeeSf);
    i += 8;
    putInt64LE(_data, i, flashLoanFeeSf);
    i += 8;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
