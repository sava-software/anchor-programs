package software.sava.anchor.programs.chainlink.ocr2.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ProposedOracles(ProposedOracle[] xs, long len) implements Borsh {

  public static final int BYTES = 1680;
  public static final int XS_LEN = 19;

  public static ProposedOracles read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var xs = new ProposedOracle[19];
    i += Borsh.readArray(xs, ProposedOracle::read, _data, i);
    final var len = getInt64LE(_data, i);
    return new ProposedOracles(xs, len);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(xs, _data, i);
    putInt64LE(_data, i, len);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
