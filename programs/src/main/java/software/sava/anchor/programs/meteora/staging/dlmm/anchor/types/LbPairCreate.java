package software.sava.anchor.programs.meteora.staging.dlmm.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record LbPairCreate(PublicKey lbPair,
                           int binStep,
                           PublicKey tokenX,
                           PublicKey tokenY) implements Borsh {

  public static final int BYTES = 98;

  public static LbPairCreate read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lbPair = readPubKey(_data, i);
    i += 32;
    final var binStep = getInt16LE(_data, i);
    i += 2;
    final var tokenX = readPubKey(_data, i);
    i += 32;
    final var tokenY = readPubKey(_data, i);
    return new LbPairCreate(lbPair,
                            binStep,
                            tokenX,
                            tokenY);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    lbPair.write(_data, i);
    i += 32;
    putInt16LE(_data, i, binStep);
    i += 2;
    tokenX.write(_data, i);
    i += 32;
    tokenY.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
