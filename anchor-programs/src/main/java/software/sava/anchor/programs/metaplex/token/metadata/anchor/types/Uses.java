package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Uses(UseMethod useMethod,
                   long remaining,
                   long total) implements Borsh {

  public static final int BYTES = 17;

  public static Uses read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var useMethod = UseMethod.read(_data, i);
    i += Borsh.len(useMethod);
    final var remaining = getInt64LE(_data, i);
    i += 8;
    final var total = getInt64LE(_data, i);
    return new Uses(useMethod, remaining, total);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(useMethod, _data, i);
    putInt64LE(_data, i, remaining);
    i += 8;
    putInt64LE(_data, i, total);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
