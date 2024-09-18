package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getFloat64LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putFloat64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record ScoreDetails(// Max MEV commission observed
                           int maxMevCommission,
                           // Epoch of max MEV commission
                           int maxMevCommissionEpoch,
                           // Epoch when superminority was detected
                           int superminorityEpoch,
                           // Ratio that failed delinquency check
                           double delinquencyRatio,
                           // Epoch when delinquency was detected
                           int delinquencyEpoch,
                           // Max commission observed
                           int maxCommission,
                           // Epoch of max commission
                           int maxCommissionEpoch,
                           // Max historical commission observed
                           int maxHistoricalCommission,
                           // Epoch of max historical commission
                           int maxHistoricalCommissionEpoch) implements Borsh {

  public static final int BYTES = 22;

  public static ScoreDetails read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var maxMevCommission = getInt16LE(_data, i);
    i += 2;
    final var maxMevCommissionEpoch = getInt16LE(_data, i);
    i += 2;
    final var superminorityEpoch = getInt16LE(_data, i);
    i += 2;
    final var delinquencyRatio = getFloat64LE(_data, i);
    i += 8;
    final var delinquencyEpoch = getInt16LE(_data, i);
    i += 2;
    final var maxCommission = _data[i] & 0xFF;
    ++i;
    final var maxCommissionEpoch = getInt16LE(_data, i);
    i += 2;
    final var maxHistoricalCommission = _data[i] & 0xFF;
    ++i;
    final var maxHistoricalCommissionEpoch = getInt16LE(_data, i);
    return new ScoreDetails(maxMevCommission,
                            maxMevCommissionEpoch,
                            superminorityEpoch,
                            delinquencyRatio,
                            delinquencyEpoch,
                            maxCommission,
                            maxCommissionEpoch,
                            maxHistoricalCommission,
                            maxHistoricalCommissionEpoch);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, maxMevCommission);
    i += 2;
    putInt16LE(_data, i, maxMevCommissionEpoch);
    i += 2;
    putInt16LE(_data, i, superminorityEpoch);
    i += 2;
    putFloat64LE(_data, i, delinquencyRatio);
    i += 8;
    putInt16LE(_data, i, delinquencyEpoch);
    i += 2;
    _data[i] = (byte) maxCommission;
    ++i;
    putInt16LE(_data, i, maxCommissionEpoch);
    i += 2;
    _data[i] = (byte) maxHistoricalCommission;
    ++i;
    putInt16LE(_data, i, maxHistoricalCommissionEpoch);
    i += 2;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
