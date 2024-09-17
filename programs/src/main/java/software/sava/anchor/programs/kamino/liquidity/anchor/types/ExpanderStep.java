package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.RustEnum;

import static software.sava.core.encoding.ByteUtil.getInt16LE;

public sealed interface ExpanderStep extends RustEnum permits
  ExpanderStep.ExpandOrContract,
  ExpanderStep.Recenter {

  static ExpanderStep read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> ExpandOrContract.read(_data, i);
      case 1 -> Recenter.INSTANCE;
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [ExpanderStep]", ordinal
      ));
    };
  }

  record ExpandOrContract(int val) implements EnumInt16, ExpanderStep {

    public static ExpandOrContract read(final byte[] _data, int i) {
      return new ExpandOrContract(getInt16LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record Recenter() implements EnumNone, ExpanderStep {

    public static final Recenter INSTANCE = new Recenter();

    @Override
    public int ordinal() {
      return 1;
    }
  }
}
