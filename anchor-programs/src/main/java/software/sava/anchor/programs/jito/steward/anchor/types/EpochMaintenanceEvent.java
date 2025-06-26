package software.sava.anchor.programs.jito.steward.anchor.types;

import java.util.OptionalLong;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record EpochMaintenanceEvent(OptionalLong validatorIndexToRemove,
                                    long validatorListLength,
                                    long numPoolValidators,
                                    long validatorsToRemove,
                                    long validatorsToAdd,
                                    boolean maintenanceComplete) implements Borsh {

  public static EpochMaintenanceEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var validatorIndexToRemove = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (validatorIndexToRemove.isPresent()) {
      i += 8;
    }
    final var validatorListLength = getInt64LE(_data, i);
    i += 8;
    final var numPoolValidators = getInt64LE(_data, i);
    i += 8;
    final var validatorsToRemove = getInt64LE(_data, i);
    i += 8;
    final var validatorsToAdd = getInt64LE(_data, i);
    i += 8;
    final var maintenanceComplete = _data[i] == 1;
    return new EpochMaintenanceEvent(validatorIndexToRemove,
                                     validatorListLength,
                                     numPoolValidators,
                                     validatorsToRemove,
                                     validatorsToAdd,
                                     maintenanceComplete);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(validatorIndexToRemove, _data, i);
    putInt64LE(_data, i, validatorListLength);
    i += 8;
    putInt64LE(_data, i, numPoolValidators);
    i += 8;
    putInt64LE(_data, i, validatorsToRemove);
    i += 8;
    putInt64LE(_data, i, validatorsToAdd);
    i += 8;
    _data[i] = (byte) (maintenanceComplete ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return (validatorIndexToRemove == null || validatorIndexToRemove.isEmpty() ? 1 : (1 + 8))
         + 8
         + 8
         + 8
         + 8
         + 1;
  }
}
