package software.sava.anchor.programs.jupiter.staking.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record NewEscrowEvent(PublicKey escrow,
                             PublicKey escrowOwner,
                             PublicKey locker,
                             long timestamp) implements Borsh {

  public static final int BYTES = 104;

  public static NewEscrowEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var escrow = readPubKey(_data, i);
    i += 32;
    final var escrowOwner = readPubKey(_data, i);
    i += 32;
    final var locker = readPubKey(_data, i);
    i += 32;
    final var timestamp = getInt64LE(_data, i);
    return new NewEscrowEvent(escrow,
                              escrowOwner,
                              locker,
                              timestamp);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    escrow.write(_data, i);
    i += 32;
    escrowOwner.write(_data, i);
    i += 32;
    locker.write(_data, i);
    i += 32;
    putInt64LE(_data, i, timestamp);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}