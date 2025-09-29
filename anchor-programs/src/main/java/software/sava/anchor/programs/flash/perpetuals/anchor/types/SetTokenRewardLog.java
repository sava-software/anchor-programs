package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SetTokenRewardLog(PublicKey tokenStake,
                                long amount,
                                int epochCount,
                                long[] padding) implements Borsh {

  public static final int BYTES = 60;
  public static final int PADDING_LEN = 2;

  public static SetTokenRewardLog read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var tokenStake = readPubKey(_data, i);
    i += 32;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var epochCount = getInt32LE(_data, i);
    i += 4;
    final var padding = new long[2];
    Borsh.readArray(padding, _data, i);
    return new SetTokenRewardLog(tokenStake,
                                 amount,
                                 epochCount,
                                 padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    tokenStake.write(_data, i);
    i += 32;
    putInt64LE(_data, i, amount);
    i += 8;
    putInt32LE(_data, i, epochCount);
    i += 4;
    i += Borsh.writeArrayChecked(padding, 2, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
