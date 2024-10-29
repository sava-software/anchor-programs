package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record QueueInitParams(int allowAuthorityOverrideAfter,
                              boolean requireAuthorityHeartbeatPermission,
                              boolean requireUsagePermissions,
                              int maxQuoteVerificationAge,
                              int reward,
                              int nodeTimeout,
                              long recentSlot) implements Borsh {

  public static final int BYTES = 26;

  public static QueueInitParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var allowAuthorityOverrideAfter = getInt32LE(_data, i);
    i += 4;
    final var requireAuthorityHeartbeatPermission = _data[i] == 1;
    ++i;
    final var requireUsagePermissions = _data[i] == 1;
    ++i;
    final var maxQuoteVerificationAge = getInt32LE(_data, i);
    i += 4;
    final var reward = getInt32LE(_data, i);
    i += 4;
    final var nodeTimeout = getInt32LE(_data, i);
    i += 4;
    final var recentSlot = getInt64LE(_data, i);
    return new QueueInitParams(allowAuthorityOverrideAfter,
                               requireAuthorityHeartbeatPermission,
                               requireUsagePermissions,
                               maxQuoteVerificationAge,
                               reward,
                               nodeTimeout,
                               recentSlot);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, allowAuthorityOverrideAfter);
    i += 4;
    _data[i] = (byte) (requireAuthorityHeartbeatPermission ? 1 : 0);
    ++i;
    _data[i] = (byte) (requireUsagePermissions ? 1 : 0);
    ++i;
    putInt32LE(_data, i, maxQuoteVerificationAge);
    i += 4;
    putInt32LE(_data, i, reward);
    i += 4;
    putInt32LE(_data, i, nodeTimeout);
    i += 4;
    putInt64LE(_data, i, recentSlot);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
