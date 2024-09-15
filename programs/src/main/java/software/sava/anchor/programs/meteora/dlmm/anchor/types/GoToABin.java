package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record GoToABin(PublicKey lbPair,
                       int fromBinId,
                       int toBinId) implements Borsh {

  public static final int BYTES = 40;

  public static GoToABin read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lbPair = readPubKey(_data, i);
    i += 32;
    final var fromBinId = getInt32LE(_data, i);
    i += 4;
    final var toBinId = getInt32LE(_data, i);
    return new GoToABin(lbPair, fromBinId, toBinId);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    lbPair.write(_data, i);
    i += 32;
    putInt32LE(_data, i, fromBinId);
    i += 4;
    putInt32LE(_data, i, toBinId);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
