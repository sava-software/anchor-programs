package software.sava.anchor.programs.jupiter.voter.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record OpenPartialStakingEvent(PublicKey partialUnstake,
                                      PublicKey escrow,
                                      long amount,
                                      long expiration) implements Borsh {

  public static final int BYTES = 80;

  public static OpenPartialStakingEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var partialUnstake = readPubKey(_data, i);
    i += 32;
    final var escrow = readPubKey(_data, i);
    i += 32;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var expiration = getInt64LE(_data, i);
    return new OpenPartialStakingEvent(partialUnstake,
                                       escrow,
                                       amount,
                                       expiration);
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
    putInt64LE(_data, i, expiration);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
