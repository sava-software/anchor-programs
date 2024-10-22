package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SwiftServerMessage(byte[] swiftOrderSignature, long slot) implements Borsh {

  public static final int BYTES = 72;

  public static SwiftServerMessage read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var swiftOrderSignature = new byte[64];
    i += Borsh.readArray(swiftOrderSignature, _data, i);
    final var slot = getInt64LE(_data, i);
    return new SwiftServerMessage(swiftOrderSignature, slot);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(swiftOrderSignature, _data, i);
    putInt64LE(_data, i, slot);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
