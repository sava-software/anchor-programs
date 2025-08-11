package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record WithdrawTokenLog(PublicKey owner,
                               PublicKey tokenStake,
                               long withdrawAmount,
                               long currentTimestamp,
                               long lastUpdatedTimestamp,
                               int level,
                               long activeStakeAmount) implements Borsh {

  public static final int BYTES = 97;

  public static WithdrawTokenLog read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var tokenStake = readPubKey(_data, i);
    i += 32;
    final var withdrawAmount = getInt64LE(_data, i);
    i += 8;
    final var currentTimestamp = getInt64LE(_data, i);
    i += 8;
    final var lastUpdatedTimestamp = getInt64LE(_data, i);
    i += 8;
    final var level = _data[i] & 0xFF;
    ++i;
    final var activeStakeAmount = getInt64LE(_data, i);
    return new WithdrawTokenLog(owner,
                                tokenStake,
                                withdrawAmount,
                                currentTimestamp,
                                lastUpdatedTimestamp,
                                level,
                                activeStakeAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    owner.write(_data, i);
    i += 32;
    tokenStake.write(_data, i);
    i += 32;
    putInt64LE(_data, i, withdrawAmount);
    i += 8;
    putInt64LE(_data, i, currentTimestamp);
    i += 8;
    putInt64LE(_data, i, lastUpdatedTimestamp);
    i += 8;
    _data[i] = (byte) level;
    ++i;
    putInt64LE(_data, i, activeStakeAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
