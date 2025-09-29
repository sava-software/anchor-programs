package software.sava.anchor.programs.jito.tip_router.anchor.types;

import software.sava.core.borsh.Borsh;

public record Ballot(byte[] metaMerkleRoot,
                     boolean isValid,
                     byte[] reserved) implements Borsh {

  public static final int BYTES = 96;
  public static final int META_MERKLE_ROOT_LEN = 32;
  public static final int RESERVED_LEN = 63;

  public static Ballot read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var metaMerkleRoot = new byte[32];
    i += Borsh.readArray(metaMerkleRoot, _data, i);
    final var isValid = _data[i] == 1;
    ++i;
    final var reserved = new byte[63];
    Borsh.readArray(reserved, _data, i);
    return new Ballot(metaMerkleRoot, isValid, reserved);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArrayChecked(metaMerkleRoot, 32, _data, i);
    _data[i] = (byte) (isValid ? 1 : 0);
    ++i;
    i += Borsh.writeArrayChecked(reserved, 63, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
