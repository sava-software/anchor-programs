package software.sava.anchor.programs.meteora.staging.dlmm.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record DecreasePositionLength(PublicKey lbPair,
                                     PublicKey position,
                                     PublicKey owner,
                                     int lengthToRemove,
                                     int side) implements Borsh {

  public static final int BYTES = 99;

  public static DecreasePositionLength read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lbPair = readPubKey(_data, i);
    i += 32;
    final var position = readPubKey(_data, i);
    i += 32;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var lengthToRemove = getInt16LE(_data, i);
    i += 2;
    final var side = _data[i] & 0xFF;
    return new DecreasePositionLength(lbPair,
                                      position,
                                      owner,
                                      lengthToRemove,
                                      side);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    lbPair.write(_data, i);
    i += 32;
    position.write(_data, i);
    i += 32;
    owner.write(_data, i);
    i += 32;
    putInt16LE(_data, i, lengthToRemove);
    i += 2;
    _data[i] = (byte) side;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
