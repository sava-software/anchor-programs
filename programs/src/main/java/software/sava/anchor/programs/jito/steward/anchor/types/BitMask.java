package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.borsh.Borsh;

// Data structure used to efficiently pack a binary array, primarily used to store all validators.
// Each validator has an index (its index in the spl_stake_pool::ValidatorList), corresponding to a bit in the bitmask.
// When an operation is executed on a validator, the bit corresponding to that validator's index is set to 1.
// When all bits are 1, the operation is complete.
public record BitMask(long[] values) implements Borsh {

  public static final int BYTES = 632;

  public static BitMask read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var values = new long[79];
    Borsh.readArray(values, _data, offset);
    return new BitMask(values);
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
