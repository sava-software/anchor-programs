package software.sava.anchor.programs.jito.tip_router.anchor.types;

import software.sava.core.borsh.RustEnum;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public sealed interface StakePoolInstruction extends RustEnum permits
  StakePoolInstruction.DepositSol,
  StakePoolInstruction.DepositSolWithSlippage {

  static StakePoolInstruction read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> DepositSol.read(_data, i);
      case 1 -> DepositSolWithSlippage.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [StakePoolInstruction]", ordinal
      ));
    };
  }

  record DepositSol(long val) implements EnumInt64, StakePoolInstruction {

    public static DepositSol read(final byte[] _data, int i) {
      return new DepositSol(getInt64LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record DepositSolWithSlippage(long lamportsIn, long minimumPoolTokensOut) implements StakePoolInstruction {

    public static final int BYTES = 16;

    public static DepositSolWithSlippage read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var lamportsIn = getInt64LE(_data, i);
      i += 8;
      final var minimumPoolTokensOut = getInt64LE(_data, i);
      return new DepositSolWithSlippage(lamportsIn, minimumPoolTokensOut);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      putInt64LE(_data, i, lamportsIn);
      i += 8;
      putInt64LE(_data, i, minimumPoolTokensOut);
      i += 8;
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
