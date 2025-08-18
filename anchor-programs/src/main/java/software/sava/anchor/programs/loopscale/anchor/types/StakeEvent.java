package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// metadata for vault actions
public record StakeEvent(PublicKey user,
                         PublicKey address,
                         PublicKey vaultAddress,
                         PublicKey principalMint,
                         int durationType,
                         long amount,
                         int actionType,
                         long principalAmount) implements Borsh {

  public static final int BYTES = 146;

  public static StakeEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var user = readPubKey(_data, i);
    i += 32;
    final var address = readPubKey(_data, i);
    i += 32;
    final var vaultAddress = readPubKey(_data, i);
    i += 32;
    final var principalMint = readPubKey(_data, i);
    i += 32;
    final var durationType = _data[i] & 0xFF;
    ++i;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var actionType = _data[i] & 0xFF;
    ++i;
    final var principalAmount = getInt64LE(_data, i);
    return new StakeEvent(user,
                          address,
                          vaultAddress,
                          principalMint,
                          durationType,
                          amount,
                          actionType,
                          principalAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    user.write(_data, i);
    i += 32;
    address.write(_data, i);
    i += 32;
    vaultAddress.write(_data, i);
    i += 32;
    principalMint.write(_data, i);
    i += 32;
    _data[i] = (byte) durationType;
    ++i;
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) actionType;
    ++i;
    putInt64LE(_data, i, principalAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
