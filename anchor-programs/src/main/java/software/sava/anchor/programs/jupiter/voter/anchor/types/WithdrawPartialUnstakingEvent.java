package software.sava.anchor.programs.jupiter.voter.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record WithdrawPartialUnstakingEvent(PublicKey escrowOwner,
                                            PublicKey locker,
                                            PublicKey partialUnstaking,
                                            long timestamp,
                                            long lockerSupply,
                                            long releasedAmount) implements Borsh {

  public static final int BYTES = 120;

  public static WithdrawPartialUnstakingEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var escrowOwner = readPubKey(_data, i);
    i += 32;
    final var locker = readPubKey(_data, i);
    i += 32;
    final var partialUnstaking = readPubKey(_data, i);
    i += 32;
    final var timestamp = getInt64LE(_data, i);
    i += 8;
    final var lockerSupply = getInt64LE(_data, i);
    i += 8;
    final var releasedAmount = getInt64LE(_data, i);
    return new WithdrawPartialUnstakingEvent(escrowOwner,
                                             locker,
                                             partialUnstaking,
                                             timestamp,
                                             lockerSupply,
                                             releasedAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    escrowOwner.write(_data, i);
    i += 32;
    locker.write(_data, i);
    i += 32;
    partialUnstaking.write(_data, i);
    i += 32;
    putInt64LE(_data, i, timestamp);
    i += 8;
    putInt64LE(_data, i, lockerSupply);
    i += 8;
    putInt64LE(_data, i, releasedAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
