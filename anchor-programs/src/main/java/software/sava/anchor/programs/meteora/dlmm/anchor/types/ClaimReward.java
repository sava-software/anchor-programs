package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ClaimReward(PublicKey lbPair,
                          PublicKey position,
                          PublicKey owner,
                          long rewardIndex,
                          long totalReward) implements Borsh {

  public static final int BYTES = 112;

  public static ClaimReward read(final byte[] _data, final int offset) {
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
    final var rewardIndex = getInt64LE(_data, i);
    i += 8;
    final var totalReward = getInt64LE(_data, i);
    return new ClaimReward(lbPair,
                           position,
                           owner,
                           rewardIndex,
                           totalReward);
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
    putInt64LE(_data, i, rewardIndex);
    i += 8;
    putInt64LE(_data, i, totalReward);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
