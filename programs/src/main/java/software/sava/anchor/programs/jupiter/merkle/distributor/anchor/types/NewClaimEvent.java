package software.sava.anchor.programs.jupiter.merkle.distributor.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record NewClaimEvent(PublicKey claimant, long timestamp) implements Borsh {

  public static final int BYTES = 40;

  public static NewClaimEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var claimant = readPubKey(_data, i);
    i += 32;
    final var timestamp = getInt64LE(_data, i);
    return new NewClaimEvent(claimant, timestamp);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    claimant.write(_data, i);
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
