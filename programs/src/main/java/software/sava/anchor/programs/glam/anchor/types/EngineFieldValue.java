package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.borsh.RustEnum;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public sealed interface EngineFieldValue extends RustEnum permits
  EngineFieldValue.Boolean,
  EngineFieldValue.Date,
  EngineFieldValue.Double,
  EngineFieldValue.Integer,
  EngineFieldValue.String,
  EngineFieldValue.Time,
  EngineFieldValue.U8,
  EngineFieldValue.U64,
  EngineFieldValue.Pubkey,
  EngineFieldValue.Percentage,
  EngineFieldValue.URI,
  EngineFieldValue.Timestamp,
  EngineFieldValue.VecPubkey,
  EngineFieldValue.VecU32,
  EngineFieldValue.VecDelegateAcl,
  EngineFieldValue.VecIntegrationAcl {

  static EngineFieldValue read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> Boolean.read(_data, i);
      case 1 -> Date.read(_data, i);
      case 2 -> Double.read(_data, i);
      case 3 -> Integer.read(_data, i);
      case 4 -> String.read(_data, i);
      case 5 -> Time.read(_data, i);
      case 6 -> U8.read(_data, i);
      case 7 -> U64.read(_data, i);
      case 8 -> Pubkey.read(_data, i);
      case 9 -> Percentage.read(_data, i);
      case 10 -> URI.read(_data, i);
      case 11 -> Timestamp.read(_data, i);
      case 12 -> VecPubkey.read(_data, i);
      case 13 -> VecU32.read(_data, i);
      case 14 -> VecDelegateAcl.read(_data, i);
      case 15 -> VecIntegrationAcl.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [EngineFieldValue]", ordinal
      ));
    };
  }

  record Boolean(boolean val) implements EnumBool, EngineFieldValue {

    public static final Boolean TRUE = new Boolean(true);
    public static final Boolean FALSE = new Boolean(false);

    public static Boolean read(final byte[] _data, int i) {
      return _data[i] == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record Date(byte[] val, java.lang.String _val) implements EnumString, EngineFieldValue {

    public static Date createRecord(final java.lang.String val) {
      return new Date(val.getBytes(UTF_8), val);
    }

    public static Date read(final byte[] data, final int offset) {
      return createRecord(Borsh.string(data, offset));
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }

  record Double(long val) implements EnumInt64, EngineFieldValue {

    public static Double read(final byte[] _data, int i) {
      return new Double(getInt64LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 2;
    }
  }

  record Integer(int val) implements EnumInt32, EngineFieldValue {

    public static Integer read(final byte[] _data, int i) {
      return new Integer(getInt32LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 3;
    }
  }

  record String(byte[] val, java.lang.String _val) implements EnumString, EngineFieldValue {

    public static String createRecord(final java.lang.String val) {
      return new String(val.getBytes(UTF_8), val);
    }

    public static String read(final byte[] data, final int offset) {
      return createRecord(Borsh.string(data, offset));
    }

    @Override
    public int ordinal() {
      return 4;
    }
  }

  record Time(byte[] val, java.lang.String _val) implements EnumString, EngineFieldValue {

    public static Time createRecord(final java.lang.String val) {
      return new Time(val.getBytes(UTF_8), val);
    }

    public static Time read(final byte[] data, final int offset) {
      return createRecord(Borsh.string(data, offset));
    }

    @Override
    public int ordinal() {
      return 5;
    }
  }

  record U8(int val) implements EnumInt8, EngineFieldValue {

    public static U8 read(final byte[] _data, int i) {
      return new U8(_data[i] & 0xFF);
    }

    @Override
    public int ordinal() {
      return 6;
    }
  }

  record U64(long val) implements EnumInt64, EngineFieldValue {

    public static U64 read(final byte[] _data, int i) {
      return new U64(getInt64LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 7;
    }
  }

  record Pubkey(PublicKey val) implements EnumPublicKey, EngineFieldValue {

    public static Pubkey read(final byte[] _data, int i) {
      return new Pubkey(readPubKey(_data, i));
    }

    @Override
    public int ordinal() {
      return 8;
    }
  }

  record Percentage(int val) implements EnumInt32, EngineFieldValue {

    public static Percentage read(final byte[] _data, int i) {
      return new Percentage(getInt32LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 9;
    }
  }

  record URI(byte[] val, java.lang.String _val) implements EnumString, EngineFieldValue {

    public static URI createRecord(final java.lang.String val) {
      return new URI(val.getBytes(UTF_8), val);
    }

    public static URI read(final byte[] data, final int offset) {
      return createRecord(Borsh.string(data, offset));
    }

    @Override
    public int ordinal() {
      return 10;
    }
  }

  record Timestamp(long val) implements EnumInt64, EngineFieldValue {

    public static Timestamp read(final byte[] _data, int i) {
      return new Timestamp(getInt64LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 11;
    }
  }

  record VecPubkey(PublicKey[] val) implements EngineFieldValue {

    public static VecPubkey read(final byte[] _data, final int offset) {
      final var val = Borsh.readPublicKeyVector(_data, offset);
      return new VecPubkey(val);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      i += Borsh.write(val, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 1 + Borsh.len(val);
    }

    @Override
    public int ordinal() {
      return 12;
    }
  }

  record VecU32(int[] val) implements EngineFieldValue {

    public static VecU32 read(final byte[] _data, final int offset) {
      final var val = Borsh.readintVector(_data, offset);
      return new VecU32(val);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      i += Borsh.write(val, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 1 + Borsh.len(val);
    }

    @Override
    public int ordinal() {
      return 13;
    }
  }

  record VecDelegateAcl(DelegateAcl[] val) implements EngineFieldValue {

    public static VecDelegateAcl read(final byte[] _data, final int offset) {
      final var val = Borsh.readVector(DelegateAcl.class, DelegateAcl::read, _data, offset);
      return new VecDelegateAcl(val);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      i += Borsh.write(val, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 1 + Borsh.len(val);
    }

    @Override
    public int ordinal() {
      return 14;
    }
  }

  record VecIntegrationAcl(IntegrationAcl[] val) implements EngineFieldValue {

    public static VecIntegrationAcl read(final byte[] _data, final int offset) {
      final var val = Borsh.readVector(IntegrationAcl.class, IntegrationAcl::read, _data, offset);
      return new VecIntegrationAcl(val);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      i += Borsh.write(val, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 1 + Borsh.len(val);
    }

    @Override
    public int ordinal() {
      return 15;
    }
  }
}
