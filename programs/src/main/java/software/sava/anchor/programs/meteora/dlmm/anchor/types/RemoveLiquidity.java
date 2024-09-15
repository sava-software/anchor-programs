package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record RemoveLiquidity(PublicKey lbPair,
                              PublicKey from,
                              PublicKey position,
                              long[] amounts,
                              int activeBinId) implements Borsh {

  public static final int BYTES = 116;

  public static RemoveLiquidity read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lbPair = readPubKey(_data, i);
    i += 32;
    final var from = readPubKey(_data, i);
    i += 32;
    final var position = readPubKey(_data, i);
    i += 32;
    final var amounts = Borsh.readArray(new long[2], _data, i);
    i += Borsh.fixedLen(amounts);
    final var activeBinId = getInt32LE(_data, i);
    return new RemoveLiquidity(lbPair,
                               from,
                               position,
                               amounts,
                               activeBinId);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    lbPair.write(_data, i);
    i += 32;
    from.write(_data, i);
    i += 32;
    position.write(_data, i);
    i += 32;
    i += Borsh.fixedWrite(amounts, _data, i);
    putInt32LE(_data, i, activeBinId);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
