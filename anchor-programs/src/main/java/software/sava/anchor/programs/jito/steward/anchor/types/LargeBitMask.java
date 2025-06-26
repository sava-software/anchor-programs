package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.borsh.Borsh;

// Data structure used to efficiently pack a binary array, primarily used to store all validators.
// Each validator has an index (its index in the spl_stake_pool::ValidatorList), corresponding to a bit in the bitmask.
// When an operation is executed on a validator, the bit corresponding to that validator's index is set to 1.
// When all bits are 1, the operation is complete.
public record LargeBitMask(long[] values) implements Borsh {

  public static final int BYTES = 2504;

  public static LargeBitMask read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var values = new long[313];
    Borsh.readArray(values, _data, offset);
    return new LargeBitMask(values);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(values, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
