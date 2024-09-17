package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;
import software.sava.core.borsh.RustEnum;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public sealed interface RebalanceAction extends RustEnum permits
  RebalanceAction.NewPriceRange,
  RebalanceAction.NewTickRange,
  RebalanceAction.WithdrawAndFreeze {

  static RebalanceAction read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> NewPriceRange.read(_data, i);
      case 1 -> NewTickRange.read(_data, i);
      case 2 -> WithdrawAndFreeze.INSTANCE;
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [RebalanceAction]", ordinal
      ));
    };
  }

  record NewPriceRange(DexSpecificPrice _defined, DexSpecificPrice _defined1) implements RebalanceAction {

    public static NewPriceRange read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var _defined = DexSpecificPrice.read(_data, i);
      i += Borsh.len(_defined);
      final var _defined1 = DexSpecificPrice.read(_data, i);
      return new NewPriceRange(_defined, _defined1);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      i += Borsh.write(_defined, _data, i);
      i += Borsh.write(_defined1, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 1 + Borsh.len(_defined) + Borsh.len(_defined1);
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record NewTickRange(int _i32, int _i321) implements RebalanceAction {

    public static final int BYTES = 8;

    public static NewTickRange read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var _i32 = getInt32LE(_data, i);
      i += 4;
      final var _i321 = getInt32LE(_data, i);
      return new NewTickRange(_i32, _i321);
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
      return 1;
    }
  }

  record WithdrawAndFreeze() implements EnumNone, RebalanceAction {

    public static final WithdrawAndFreeze INSTANCE = new WithdrawAndFreeze();

    @Override
    public int ordinal() {
      return 2;
    }
  }
}
