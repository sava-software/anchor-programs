package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record CreatedModel(byte[] key, PublicKey manager) implements Borsh {

  public static CreatedModel read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var key = new byte[8];
    i += Borsh.readArray(key, _data, i);
    final var manager = _data[i++] == 0 ? null : readPubKey(_data, i);
    return new CreatedModel(key, manager);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(key, _data, i);
    i += Borsh.writeOptional(manager, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenArray(key) + (manager == null ? 1 : (1 + 32));
  }
}
