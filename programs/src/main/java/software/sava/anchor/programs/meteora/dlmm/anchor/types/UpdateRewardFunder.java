package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record UpdateRewardFunder(PublicKey lbPair,
                                 long rewardIndex,
                                 PublicKey oldFunder,
                                 PublicKey newFunder) implements Borsh {

  public static final int BYTES = 104;

  public static UpdateRewardFunder read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lbPair = readPubKey(_data, i);
    i += 32;
    final var rewardIndex = getInt64LE(_data, i);
    i += 8;
    final var oldFunder = readPubKey(_data, i);
    i += 32;
    final var newFunder = readPubKey(_data, i);
    return new UpdateRewardFunder(lbPair,
                                  rewardIndex,
                                  oldFunder,
                                  newFunder);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    lbPair.write(_data, i);
    i += 32;
    putInt64LE(_data, i, rewardIndex);
    i += 8;
    oldFunder.write(_data, i);
    i += 32;
    newFunder.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
