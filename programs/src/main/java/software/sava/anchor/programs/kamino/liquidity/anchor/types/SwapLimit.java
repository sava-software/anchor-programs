package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.RustEnum;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public sealed interface SwapLimit extends RustEnum permits
  SwapLimit.Bps,
  SwapLimit.Absolute {

  static SwapLimit read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> Bps.read(_data, i);
      case 1 -> Absolute.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [SwapLimit]", ordinal
      ));
    };
  }

  record Bps(long val) implements EnumInt64, SwapLimit {

    public static Bps read(final byte[] _data, int i) {
      return new Bps(getInt64LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record Absolute(// Amount of src token expected by the user to perform the swap
                  long srcAmountToSwap,
                  // Amount of dst token the user provides in exchange
                  long dstAmountToVault,
                  boolean aToB) implements SwapLimit {

    public static final int BYTES = 17;

    public static Absolute read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var srcAmountToSwap = getInt64LE(_data, i);
      i += 8;
      final var dstAmountToVault = getInt64LE(_data, i);
      i += 8;
      final var aToB = _data[i] == 1;
      return new Absolute(srcAmountToSwap, dstAmountToVault, aToB);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      putInt64LE(_data, i, srcAmountToSwap);
      i += 8;
      putInt64LE(_data, i, dstAmountToVault);
      i += 8;
      _data[i] = (byte) (aToB ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }
}
