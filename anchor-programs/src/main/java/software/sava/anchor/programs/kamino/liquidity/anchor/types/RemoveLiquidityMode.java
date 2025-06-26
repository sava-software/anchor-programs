package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.RustEnum;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;

public sealed interface RemoveLiquidityMode extends RustEnum permits
  RemoveLiquidityMode.Liquidity,
  RemoveLiquidityMode.Bps,
  RemoveLiquidityMode.All {

  static RemoveLiquidityMode read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> Liquidity.read(_data, i);
      case 1 -> Bps.read(_data, i);
      case 2 -> All.INSTANCE;
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [RemoveLiquidityMode]", ordinal
      ));
    };
  }

  record Liquidity(BigInteger val) implements EnumInt128, RemoveLiquidityMode {

    public static Liquidity read(final byte[] _data, int i) {
      return new Liquidity(getInt128LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record Bps(int val) implements EnumInt16, RemoveLiquidityMode {

    public static Bps read(final byte[] _data, int i) {
      return new Bps(getInt16LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }

  record All() implements EnumNone, RemoveLiquidityMode {

    public static final All INSTANCE = new All();

    @Override
    public int ordinal() {
      return 2;
    }
  }
}
