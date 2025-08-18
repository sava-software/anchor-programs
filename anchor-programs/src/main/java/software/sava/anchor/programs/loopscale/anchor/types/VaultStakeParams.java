package software.sava.anchor.programs.loopscale.anchor.types;

import java.lang.Boolean;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record VaultStakeParams(long amount,
                               long principalAmount,
                               Boolean stakeAll,
                               int duration,
                               int durationType,
                               int actionType) implements Borsh {

  public static VaultStakeParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var principalAmount = getInt64LE(_data, i);
    i += 8;
    final var stakeAll = _data[i++] == 0 ? null : _data[i] == 1;
    if (stakeAll != null) {
      ++i;
    }
    final var duration = getInt32LE(_data, i);
    i += 4;
    final var durationType = _data[i] & 0xFF;
    ++i;
    final var actionType = _data[i] & 0xFF;
    return new VaultStakeParams(amount,
                                principalAmount,
                                stakeAll,
                                duration,
                                durationType,
                                actionType);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amount);
    i += 8;
    putInt64LE(_data, i, principalAmount);
    i += 8;
    i += Borsh.writeOptional(stakeAll, _data, i);
    putInt32LE(_data, i, duration);
    i += 4;
    _data[i] = (byte) durationType;
    ++i;
    _data[i] = (byte) actionType;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return 8
         + 8
         + (stakeAll == null ? 1 : (1 + 1))
         + 4
         + 1
         + 1;
  }
}
