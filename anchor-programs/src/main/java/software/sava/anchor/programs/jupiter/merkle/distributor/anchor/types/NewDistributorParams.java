package software.sava.anchor.programs.jupiter.merkle.distributor.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record NewDistributorParams(long version,
                                   byte[] root,
                                   long totalClaim,
                                   long maxNumNodes,
                                   long startVestingTs,
                                   long endVestingTs,
                                   long clawbackStartTs,
                                   long activationPoint,
                                   int activationType,
                                   boolean closable,
                                   long totalBonus,
                                   long bonusVestingDuration,
                                   int claimType,
                                   PublicKey operator,
                                   PublicKey locker) implements Borsh {

  public static final int BYTES = 171;
  public static final int ROOT_LEN = 32;

  public static NewDistributorParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var version = getInt64LE(_data, i);
    i += 8;
    final var root = new byte[32];
    i += Borsh.readArray(root, _data, i);
    final var totalClaim = getInt64LE(_data, i);
    i += 8;
    final var maxNumNodes = getInt64LE(_data, i);
    i += 8;
    final var startVestingTs = getInt64LE(_data, i);
    i += 8;
    final var endVestingTs = getInt64LE(_data, i);
    i += 8;
    final var clawbackStartTs = getInt64LE(_data, i);
    i += 8;
    final var activationPoint = getInt64LE(_data, i);
    i += 8;
    final var activationType = _data[i] & 0xFF;
    ++i;
    final var closable = _data[i] == 1;
    ++i;
    final var totalBonus = getInt64LE(_data, i);
    i += 8;
    final var bonusVestingDuration = getInt64LE(_data, i);
    i += 8;
    final var claimType = _data[i] & 0xFF;
    ++i;
    final var operator = readPubKey(_data, i);
    i += 32;
    final var locker = readPubKey(_data, i);
    return new NewDistributorParams(version,
                                    root,
                                    totalClaim,
                                    maxNumNodes,
                                    startVestingTs,
                                    endVestingTs,
                                    clawbackStartTs,
                                    activationPoint,
                                    activationType,
                                    closable,
                                    totalBonus,
                                    bonusVestingDuration,
                                    claimType,
                                    operator,
                                    locker);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, version);
    i += 8;
    i += Borsh.writeArrayChecked(root, 32, _data, i);
    putInt64LE(_data, i, totalClaim);
    i += 8;
    putInt64LE(_data, i, maxNumNodes);
    i += 8;
    putInt64LE(_data, i, startVestingTs);
    i += 8;
    putInt64LE(_data, i, endVestingTs);
    i += 8;
    putInt64LE(_data, i, clawbackStartTs);
    i += 8;
    putInt64LE(_data, i, activationPoint);
    i += 8;
    _data[i] = (byte) activationType;
    ++i;
    _data[i] = (byte) (closable ? 1 : 0);
    ++i;
    putInt64LE(_data, i, totalBonus);
    i += 8;
    putInt64LE(_data, i, bonusVestingDuration);
    i += 8;
    _data[i] = (byte) claimType;
    ++i;
    operator.write(_data, i);
    i += 32;
    locker.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
