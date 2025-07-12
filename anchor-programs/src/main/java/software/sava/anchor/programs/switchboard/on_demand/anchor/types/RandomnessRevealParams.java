package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.borsh.Borsh;

public record RandomnessRevealParams(byte[] signature,
                                     int recoveryId,
                                     byte[] value) implements Borsh {

  public static final int BYTES = 97;
  public static final int SIGNATURE_LEN = 64;
  public static final int VALUE_LEN = 32;

  public static RandomnessRevealParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var signature = new byte[64];
    i += Borsh.readArray(signature, _data, i);
    final var recoveryId = _data[i] & 0xFF;
    ++i;
    final var value = new byte[32];
    Borsh.readArray(value, _data, i);
    return new RandomnessRevealParams(signature, recoveryId, value);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(signature, _data, i);
    _data[i] = (byte) recoveryId;
    ++i;
    i += Borsh.writeArray(value, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
