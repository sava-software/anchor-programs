package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record ClientVersion(int major,
                            int minor,
                            int patch) implements Borsh {

  public static final int BYTES = 4;

  public static ClientVersion read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var major = _data[i] & 0xFF;
    ++i;
    final var minor = _data[i] & 0xFF;
    ++i;
    final var patch = getInt16LE(_data, i);
    return new ClientVersion(major, minor, patch);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) major;
    ++i;
    _data[i] = (byte) minor;
    ++i;
    putInt16LE(_data, i, patch);
    i += 2;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
