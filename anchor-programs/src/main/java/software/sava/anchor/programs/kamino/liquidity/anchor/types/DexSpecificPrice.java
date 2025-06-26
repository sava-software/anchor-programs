package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.RustEnum;

import static software.sava.core.encoding.ByteUtil.getInt128LE;

public sealed interface DexSpecificPrice extends RustEnum permits
  DexSpecificPrice.SqrtPrice,
  DexSpecificPrice.Q6464 {

  static DexSpecificPrice read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> SqrtPrice.read(_data, i);
      case 1 -> Q6464.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [DexSpecificPrice]", ordinal
      ));
    };
  }

  record SqrtPrice(BigInteger val) implements EnumInt128, DexSpecificPrice {

    public static SqrtPrice read(final byte[] _data, int i) {
      return new SqrtPrice(getInt128LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record Q6464(BigInteger val) implements EnumInt128, DexSpecificPrice {

    public static Q6464 read(final byte[] _data, int i) {
      return new Q6464(getInt128LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }
}
