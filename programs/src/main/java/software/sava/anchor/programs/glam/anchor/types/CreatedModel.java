package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record CreatedModel(int[] key, PublicKey manager) implements Borsh {

  public static CreatedModel read(final byte[] _data, final int offset) {
    int i = offset;
    final var key = Borsh.readArray(new int[8], _data, i);
    i += Borsh.fixedLen(key);
    final var manager = _data[i++] == 0 ? null : readPubKey(_data, i);
    return new CreatedModel(key, manager);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.fixedWrite(key, _data, i);
    i += Borsh.writeOptional(manager, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.fixedLen(key) + Borsh.lenOptional(manager, 32);
  }
}
