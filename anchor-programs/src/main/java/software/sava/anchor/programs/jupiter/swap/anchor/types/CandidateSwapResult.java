package software.sava.anchor.programs.jupiter.swap.anchor.types;

import software.sava.core.borsh.RustEnum;

import static software.sava.core.encoding.ByteUtil.getInt64LE;

public sealed interface CandidateSwapResult extends RustEnum permits
  CandidateSwapResult.OutAmount,
  CandidateSwapResult.ProgramError {

  static CandidateSwapResult read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> OutAmount.read(_data, i);
      case 1 -> ProgramError.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [CandidateSwapResult]", ordinal
      ));
    };
  }

  record OutAmount(long val) implements EnumInt64, CandidateSwapResult {

    public static OutAmount read(final byte[] _data, int i) {
      return new OutAmount(getInt64LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record ProgramError(long val) implements EnumInt64, CandidateSwapResult {

    public static ProgramError read(final byte[] _data, int i) {
      return new ProgramError(getInt64LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }
}
