package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.RustEnum;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public sealed interface RebalanceEffects extends RustEnum permits
  RebalanceEffects.NewRange,
  RebalanceEffects.WithdrawAndFreeze {

  static RebalanceEffects read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> NewRange.read(_data, i);
      case 1 -> WithdrawAndFreeze.INSTANCE;
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [RebalanceEffects]", ordinal
      ));
    };
  }

  record NewRange(int _i32, int _i321) implements RebalanceEffects {

    public static final int BYTES = 8;

    public static NewRange read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var _i32 = getInt32LE(_data, i);
      i += 4;
      final var _i321 = getInt32LE(_data, i);
      return new NewRange(_i32, _i321);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      putInt32LE(_data, i, _i32);
      i += 4;
      putInt32LE(_data, i, _i321);
      i += 4;
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

  record WithdrawAndFreeze() implements EnumNone, RebalanceEffects {

    public static final WithdrawAndFreeze INSTANCE = new WithdrawAndFreeze();

    @Override
    public int ordinal() {
      return 1;
    }
  }
}
