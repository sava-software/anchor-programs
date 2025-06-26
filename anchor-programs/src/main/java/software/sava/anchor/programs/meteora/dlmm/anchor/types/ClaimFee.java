package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ClaimFee(PublicKey lbPair,
                       PublicKey position,
                       PublicKey owner,
                       long feeX,
                       long feeY) implements Borsh {

  public static final int BYTES = 112;

  public static ClaimFee read(final byte[] _data, final int offset) {
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
    final var feeX = getInt64LE(_data, i);
    i += 8;
    final var feeY = getInt64LE(_data, i);
    return new ClaimFee(lbPair,
                        position,
                        owner,
                        feeX,
                        feeY);
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
    putInt64LE(_data, i, feeX);
    i += 8;
    putInt64LE(_data, i, feeY);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
