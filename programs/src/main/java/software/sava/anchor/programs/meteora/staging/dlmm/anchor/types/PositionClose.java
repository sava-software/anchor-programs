package software.sava.anchor.programs.meteora.staging.dlmm.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record PositionClose(PublicKey position, PublicKey owner) implements Borsh {

  public static final int BYTES = 64;

  public static PositionClose read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var position = readPubKey(_data, i);
    i += 32;
    final var owner = readPubKey(_data, i);
    return new PositionClose(position, owner);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    position.write(_data, i);
    i += 32;
    owner.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
