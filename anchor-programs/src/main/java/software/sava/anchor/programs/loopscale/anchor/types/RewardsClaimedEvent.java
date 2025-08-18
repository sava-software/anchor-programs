package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RewardsClaimedEvent(PublicKey vaultAddress,
                                  PublicKey userAddress,
                                  PublicKey stakeAccountAddress,
                                  long amount,
                                  PublicKey mint,
                                  long timestamp) implements Borsh {

  public static final int BYTES = 144;

  public static RewardsClaimedEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var vaultAddress = readPubKey(_data, i);
    i += 32;
    final var userAddress = readPubKey(_data, i);
    i += 32;
    final var stakeAccountAddress = readPubKey(_data, i);
    i += 32;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var timestamp = getInt64LE(_data, i);
    return new RewardsClaimedEvent(vaultAddress,
                                   userAddress,
                                   stakeAccountAddress,
                                   amount,
                                   mint,
                                   timestamp);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    vaultAddress.write(_data, i);
    i += 32;
    userAddress.write(_data, i);
    i += 32;
    stakeAccountAddress.write(_data, i);
    i += 32;
    putInt64LE(_data, i, amount);
    i += 8;
    mint.write(_data, i);
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
