package software.sava.anchor.programs.jupiter.voter.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ExtendLockDurationEvent(PublicKey locker,
                                      PublicKey escrowOwner,
                                      PublicKey tokenMint,
                                      long lockerSupply,
                                      long duration,
                                      long prevEscrowEndsAt,
                                      long nextEscrowEndsAt,
                                      long nextEscrowStartedAt) implements Borsh {

  public static final int BYTES = 136;

  public static ExtendLockDurationEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var locker = readPubKey(_data, i);
    i += 32;
    final var escrowOwner = readPubKey(_data, i);
    i += 32;
    final var tokenMint = readPubKey(_data, i);
    i += 32;
    final var lockerSupply = getInt64LE(_data, i);
    i += 8;
    final var duration = getInt64LE(_data, i);
    i += 8;
    final var prevEscrowEndsAt = getInt64LE(_data, i);
    i += 8;
    final var nextEscrowEndsAt = getInt64LE(_data, i);
    i += 8;
    final var nextEscrowStartedAt = getInt64LE(_data, i);
    return new ExtendLockDurationEvent(locker,
                                       escrowOwner,
                                       tokenMint,
                                       lockerSupply,
                                       duration,
                                       prevEscrowEndsAt,
                                       nextEscrowEndsAt,
                                       nextEscrowStartedAt);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    locker.write(_data, i);
    i += 32;
    escrowOwner.write(_data, i);
    i += 32;
    tokenMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, lockerSupply);
    i += 8;
    putInt64LE(_data, i, duration);
    i += 8;
    putInt64LE(_data, i, prevEscrowEndsAt);
    i += 8;
    putInt64LE(_data, i, nextEscrowEndsAt);
    i += 8;
    putInt64LE(_data, i, nextEscrowStartedAt);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
