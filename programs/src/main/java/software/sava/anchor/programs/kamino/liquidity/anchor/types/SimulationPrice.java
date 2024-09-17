package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.RustEnum;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;

public sealed interface SimulationPrice extends RustEnum permits
  SimulationPrice.PoolPrice,
  SimulationPrice.SqrtPrice,
  SimulationPrice.TickIndex {

  static SimulationPrice read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> PoolPrice.INSTANCE;
      case 1 -> SqrtPrice.read(_data, i);
      case 2 -> TickIndex.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [SimulationPrice]", ordinal
      ));
    };
  }

  record PoolPrice() implements EnumNone, SimulationPrice {

    public static final PoolPrice INSTANCE = new PoolPrice();

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record SqrtPrice(BigInteger val) implements EnumInt128, SimulationPrice {

    public static SqrtPrice read(final byte[] _data, int i) {
      return new SqrtPrice(getInt128LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }

  record TickIndex(int val) implements EnumInt32, SimulationPrice {

    public static TickIndex read(final byte[] _data, int i) {
      return new TickIndex(getInt32LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 2;
    }
  }
}
