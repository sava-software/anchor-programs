package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.RustEnum;

public sealed interface LpParams extends RustEnum permits
  LpParams.ExactIn,
  LpParams.ExactOut {

  static LpParams read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> ExactIn.read(_data, i);
      case 1 -> ExactOut.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [LpParams]", ordinal
      ));
    };
  }

  record ExactIn(ExactInParams val) implements BorshEnum, LpParams {

    public static ExactIn read(final byte[] _data, final int offset) {
      return new ExactIn(ExactInParams.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record ExactOut(ExactOutParams val) implements BorshEnum, LpParams {

    public static ExactOut read(final byte[] _data, final int offset) {
      return new ExactOut(ExactOutParams.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }
}
