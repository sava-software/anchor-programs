package software.sava.anchor.programs.jupiter.swap.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record BestSwapOutAmountViolation(long expectedOutAmount, long outAmount) implements Borsh {

  public static final int BYTES = 16;

  public static BestSwapOutAmountViolation read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var expectedOutAmount = getInt64LE(_data, i);
    i += 8;
    final var outAmount = getInt64LE(_data, i);
    return new BestSwapOutAmountViolation(expectedOutAmount, outAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, expectedOutAmount);
    i += 8;
    putInt64LE(_data, i, outAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
