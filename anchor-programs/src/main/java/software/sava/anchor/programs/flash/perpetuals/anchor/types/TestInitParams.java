package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record TestInitParams(int minSignatures, Permissions permissions) implements Borsh {

  public static final int BYTES = 14;

  public static TestInitParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var minSignatures = _data[i] & 0xFF;
    ++i;
    final var permissions = Permissions.read(_data, i);
    return new TestInitParams(minSignatures, permissions);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) minSignatures;
    ++i;
    i += Borsh.write(permissions, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
