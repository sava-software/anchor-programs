package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import software.sava.core.borsh.RustEnum;

public sealed interface CurveParams extends RustEnum permits
  CurveParams.Constant,
  CurveParams.Fixed,
  CurveParams.Linear {

  static CurveParams read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> Constant.read(_data, i);
      case 1 -> Fixed.read(_data, i);
      case 2 -> Linear.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [CurveParams]", ordinal
      ));
    };
  }

  record Constant(ConstantCurve val) implements BorshEnum, CurveParams {

    public static Constant read(final byte[] _data, final int offset) {
      return new Constant(ConstantCurve.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record Fixed(FixedCurve val) implements BorshEnum, CurveParams {

    public static Fixed read(final byte[] _data, final int offset) {
      return new Fixed(FixedCurve.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }

  record Linear(LinearCurve val) implements BorshEnum, CurveParams {

    public static Linear read(final byte[] _data, final int offset) {
      return new Linear(LinearCurve.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 2;
    }
  }
}
