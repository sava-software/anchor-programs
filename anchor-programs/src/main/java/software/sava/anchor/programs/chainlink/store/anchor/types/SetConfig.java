package software.sava.anchor.programs.chainlink.store.anchor.types;

import software.sava.core.borsh.Borsh;

public record SetConfig(byte[] configDigest,
                        int f,
                        byte[][] signers) implements Borsh {

  public static final int CONFIG_DIGEST_LEN = 32;
  public static SetConfig read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var configDigest = new byte[32];
    i += Borsh.readArray(configDigest, _data, i);
    final var f = _data[i] & 0xFF;
    ++i;
    final var signers = Borsh.readMultiDimensionbyteVectorArray(20, _data, i);
    return new SetConfig(configDigest, f, signers);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(configDigest, _data, i);
    _data[i] = (byte) f;
    ++i;
    i += Borsh.writeVectorArray(signers, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenArray(configDigest) + 1 + Borsh.lenVectorArray(signers);
  }
}
