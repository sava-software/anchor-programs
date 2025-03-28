package software.sava.anchor.programs.meteora.staging.dlmm.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record AddLiquidity(PublicKey lbPair,
                           PublicKey from,
                           PublicKey position,
                           long[] amounts,
                           int activeBinId) implements Borsh {

  public static final int BYTES = 116;

  public static AddLiquidity read(final byte[] _data, final int offset) {
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
    final var amounts = new long[2];
    i += Borsh.readArray(amounts, _data, i);
    final var activeBinId = getInt32LE(_data, i);
    return new AddLiquidity(lbPair,
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
    i += Borsh.writeArray(amounts, _data, i);
    putInt32LE(_data, i, activeBinId);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
