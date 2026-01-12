package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record QueueSetConfigsParams(PublicKey authority,
                                    OptionalInt reward,
                                    OptionalLong nodeTimeout,
                                    OptionalInt oracleFeeProportionBps) implements Borsh {

  public static QueueSetConfigsParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var authority = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (authority != null) {
      i += 32;
    }
    final var reward = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (reward.isPresent()) {
      i += 4;
    }
    final var nodeTimeout = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (nodeTimeout.isPresent()) {
      i += 8;
    }
    final var oracleFeeProportionBps = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    return new QueueSetConfigsParams(authority,
                                     reward,
                                     nodeTimeout,
                                     oracleFeeProportionBps);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(authority, _data, i);
    i += Borsh.writeOptional(reward, _data, i);
    i += Borsh.writeOptional(nodeTimeout, _data, i);
    i += Borsh.writeOptional(oracleFeeProportionBps, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (authority == null ? 1 : (1 + 32)) + (reward == null || reward.isEmpty() ? 1 : (1 + 4)) + (nodeTimeout == null || nodeTimeout.isEmpty() ? 1 : (1 + 8)) + (oracleFeeProportionBps == null || oracleFeeProportionBps.isEmpty() ? 1 : (1 + 4));
  }
}
