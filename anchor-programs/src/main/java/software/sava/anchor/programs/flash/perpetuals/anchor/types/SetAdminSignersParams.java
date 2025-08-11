package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record SetAdminSignersParams(int minSignatures) implements Borsh {

  public static final int BYTES = 1;

  public static SetAdminSignersParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var minSignatures = _data[offset] & 0xFF;
    return new SetAdminSignersParams(minSignatures);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) minSignatures;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
