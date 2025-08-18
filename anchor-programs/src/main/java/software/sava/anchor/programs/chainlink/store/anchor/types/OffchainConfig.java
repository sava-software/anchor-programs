package software.sava.anchor.programs.chainlink.store.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record OffchainConfig(long version,
                             byte[] xs,
                             long len) implements Borsh {

  public static final int BYTES = 4112;
  public static final int XS_LEN = 4096;

  public static OffchainConfig read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var version = getInt64LE(_data, i);
    i += 8;
    final var xs = new byte[4096];
    i += Borsh.readArray(xs, _data, i);
    final var len = getInt64LE(_data, i);
    return new OffchainConfig(version, xs, len);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, version);
    i += 8;
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
