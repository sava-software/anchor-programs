package software.sava.anchor.programs.jupiter.staking.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record IncreaseLockedAmountEvent(PublicKey locker,
                                        PublicKey escrowOwner,
                                        PublicKey tokenMint,
                                        long amount,
                                        long lockerSupply) implements Borsh {

  public static final int BYTES = 112;

  public static IncreaseLockedAmountEvent read(final byte[] _data, final int offset) {
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
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var lockerSupply = getInt64LE(_data, i);
    return new IncreaseLockedAmountEvent(locker,
                                         escrowOwner,
                                         tokenMint,
                                         amount,
                                         lockerSupply);
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
    putInt64LE(_data, i, amount);
    i += 8;
    putInt64LE(_data, i, lockerSupply);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
