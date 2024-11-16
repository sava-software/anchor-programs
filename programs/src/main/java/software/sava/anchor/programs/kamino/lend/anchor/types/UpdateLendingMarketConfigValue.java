package software.sava.anchor.programs.kamino.lend.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.borsh.RustEnum;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public sealed interface UpdateLendingMarketConfigValue extends RustEnum permits
  UpdateLendingMarketConfigValue.Bool,
  UpdateLendingMarketConfigValue.U8,
  UpdateLendingMarketConfigValue.U8Array,
  UpdateLendingMarketConfigValue.U16,
  UpdateLendingMarketConfigValue.U64,
  UpdateLendingMarketConfigValue.U128,
  UpdateLendingMarketConfigValue.Pubkey,
  UpdateLendingMarketConfigValue.ElevationGroup,
  UpdateLendingMarketConfigValue.Name {

  static UpdateLendingMarketConfigValue read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> Bool.read(_data, i);
      case 1 -> U8.read(_data, i);
      case 2 -> U8Array.read(_data, i);
      case 3 -> U16.read(_data, i);
      case 4 -> U64.read(_data, i);
      case 5 -> U128.read(_data, i);
      case 6 -> Pubkey.read(_data, i);
      case 7 -> ElevationGroup.read(_data, i);
      case 8 -> Name.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [UpdateLendingMarketConfigValue]", ordinal
      ));
    };
  }

  record Bool(boolean val) implements EnumBool, UpdateLendingMarketConfigValue {

    public static final Bool TRUE = new Bool(true);
    public static final Bool FALSE = new Bool(false);

    public static Bool read(final byte[] _data, int i) {
      return _data[i] == 1 ? Bool.TRUE : Bool.FALSE;
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record U8(int val) implements EnumInt8, UpdateLendingMarketConfigValue {

    public static U8 read(final byte[] _data, int i) {
      return new U8(_data[i] & 0xFF);
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }

  record U8Array(byte[] val) implements UpdateLendingMarketConfigValue {

    public static final int BYTES = 8;

    public static U8Array read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var val = new byte[8];
      Borsh.readArray(val, _data, offset);
      return new U8Array(val);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      i += Borsh.writeArray(val, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }

    @Override
    public int ordinal() {
      return 2;
    }
  }

  record U16(int val) implements EnumInt16, UpdateLendingMarketConfigValue {

    public static U16 read(final byte[] _data, int i) {
      return new U16(getInt16LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 3;
    }
  }

  record U64(long val) implements EnumInt64, UpdateLendingMarketConfigValue {

    public static U64 read(final byte[] _data, int i) {
      return new U64(getInt64LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 4;
    }
  }

  record U128(BigInteger val) implements EnumInt128, UpdateLendingMarketConfigValue {

    public static U128 read(final byte[] _data, int i) {
      return new U128(getInt128LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 5;
    }
  }

  record Pubkey(PublicKey val) implements EnumPublicKey, UpdateLendingMarketConfigValue {

    public static Pubkey read(final byte[] _data, int i) {
      return new Pubkey(readPubKey(_data, i));
    }

    @Override
    public int ordinal() {
      return 6;
    }
  }

  record ElevationGroup(ElevationGroup val) implements BorshEnum, UpdateLendingMarketConfigValue {

    public static ElevationGroup read(final byte[] _data, final int offset) {
      return new ElevationGroup(ElevationGroup.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 7;
    }
  }

  record Name(byte[] val) implements UpdateLendingMarketConfigValue {

    public static final int BYTES = 32;

    public static Name read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var val = new byte[32];
      Borsh.readArray(val, _data, offset);
      return new Name(val);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      i += Borsh.writeArray(val, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }

    @Override
    public int ordinal() {
      return 8;
    }
  }
}
