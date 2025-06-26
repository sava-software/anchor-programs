package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.RustEnum;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public sealed interface BinAddLiquidityStrategy extends RustEnum permits
  BinAddLiquidityStrategy.Uniform,
  BinAddLiquidityStrategy.CurrentTick {

  static BinAddLiquidityStrategy read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> Uniform.read(_data, i);
      case 1 -> CurrentTick.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [BinAddLiquidityStrategy]", ordinal
      ));
    };
  }

  record Uniform(int currentBinIndex,
                 int lowerBinIndex,
                 int upperBinIndex,
                 long amountXToDeposit,
                 long amountYToDeposit,
                 long xCurrentBin,
                 long yCurrentBin) implements BinAddLiquidityStrategy {

    public static final int BYTES = 44;

    public static Uniform read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var currentBinIndex = getInt32LE(_data, i);
      i += 4;
      final var lowerBinIndex = getInt32LE(_data, i);
      i += 4;
      final var upperBinIndex = getInt32LE(_data, i);
      i += 4;
      final var amountXToDeposit = getInt64LE(_data, i);
      i += 8;
      final var amountYToDeposit = getInt64LE(_data, i);
      i += 8;
      final var xCurrentBin = getInt64LE(_data, i);
      i += 8;
      final var yCurrentBin = getInt64LE(_data, i);
      return new Uniform(currentBinIndex,
                         lowerBinIndex,
                         upperBinIndex,
                         amountXToDeposit,
                         amountYToDeposit,
                         xCurrentBin,
                         yCurrentBin);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      putInt32LE(_data, i, currentBinIndex);
      i += 4;
      putInt32LE(_data, i, lowerBinIndex);
      i += 4;
      putInt32LE(_data, i, upperBinIndex);
      i += 4;
      putInt64LE(_data, i, amountXToDeposit);
      i += 8;
      putInt64LE(_data, i, amountYToDeposit);
      i += 8;
      putInt64LE(_data, i, xCurrentBin);
      i += 8;
      putInt64LE(_data, i, yCurrentBin);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record CurrentTick(int val) implements EnumInt32, BinAddLiquidityStrategy {

    public static CurrentTick read(final byte[] _data, int i) {
      return new CurrentTick(getInt32LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }
}
