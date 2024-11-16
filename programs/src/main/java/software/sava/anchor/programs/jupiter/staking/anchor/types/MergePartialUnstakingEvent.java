package software.sava.anchor.programs.jupiter.staking.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record MergePartialUnstakingEvent(PublicKey partialUnstake,
                                         PublicKey escrow,
                                         long amount) implements Borsh {

  public static final int BYTES = 72;

  public static MergePartialUnstakingEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var partialUnstake = readPubKey(_data, i);
    i += 32;
    final var escrow = readPubKey(_data, i);
    i += 32;
    final var amount = getInt64LE(_data, i);
    return new MergePartialUnstakingEvent(partialUnstake, escrow, amount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    partialUnstake.write(_data, i);
    i += 32;
    escrow.write(_data, i);
    i += 32;
    putInt64LE(_data, i, amount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
