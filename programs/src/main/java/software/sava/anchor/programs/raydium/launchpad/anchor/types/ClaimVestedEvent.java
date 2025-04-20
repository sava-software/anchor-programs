package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Emitted when vesting token claimed by beneficiary
public record ClaimVestedEvent(PublicKey poolState,
                               PublicKey beneficiary,
                               long claimAmount) implements Borsh {

  public static final int BYTES = 72;

  public static ClaimVestedEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var poolState = readPubKey(_data, i);
    i += 32;
    final var beneficiary = readPubKey(_data, i);
    i += 32;
    final var claimAmount = getInt64LE(_data, i);
    return new ClaimVestedEvent(poolState, beneficiary, claimAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    poolState.write(_data, i);
    i += 32;
    beneficiary.write(_data, i);
    i += 32;
    putInt64LE(_data, i, claimAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
