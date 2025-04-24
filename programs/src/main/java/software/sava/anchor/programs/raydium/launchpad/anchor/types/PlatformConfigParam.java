package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.borsh.RustEnum;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public sealed interface PlatformConfigParam extends RustEnum permits
  PlatformConfigParam.FeeWallet,
  PlatformConfigParam.NFTWallet,
  PlatformConfigParam.MigrateNftInfo,
  PlatformConfigParam.FeeRate,
  PlatformConfigParam.Name,
  PlatformConfigParam.Web,
  PlatformConfigParam.Img {

  static PlatformConfigParam read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> FeeWallet.read(_data, i);
      case 1 -> NFTWallet.read(_data, i);
      case 2 -> MigrateNftInfo.read(_data, i);
      case 3 -> FeeRate.read(_data, i);
      case 4 -> Name.read(_data, i);
      case 5 -> Web.read(_data, i);
      case 6 -> Img.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [PlatformConfigParam]", ordinal
      ));
    };
  }

  record FeeWallet(PublicKey val) implements EnumPublicKey, PlatformConfigParam {

    public static FeeWallet read(final byte[] _data, int i) {
      return new FeeWallet(readPubKey(_data, i));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record NFTWallet(PublicKey val) implements EnumPublicKey, PlatformConfigParam {

    public static NFTWallet read(final byte[] _data, int i) {
      return new NFTWallet(readPubKey(_data, i));
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }

  record MigrateNftInfo(software.sava.anchor.programs.raydium.launchpad.anchor.types.MigrateNftInfo val) implements BorshEnum, PlatformConfigParam {

    public static MigrateNftInfo read(final byte[] _data, final int offset) {
      return new MigrateNftInfo(software.sava.anchor.programs.raydium.launchpad.anchor.types.MigrateNftInfo.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 2;
    }
  }

  record FeeRate(long val) implements EnumInt64, PlatformConfigParam {

    public static FeeRate read(final byte[] _data, int i) {
      return new FeeRate(getInt64LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 3;
    }
  }

  record Name(byte[] val, java.lang.String _val) implements EnumString, PlatformConfigParam {

    public static Name createRecord(final java.lang.String val) {
      return new Name(val.getBytes(UTF_8), val);
    }

    public static Name read(final byte[] data, final int offset) {
      return createRecord(Borsh.string(data, offset));
    }

    @Override
    public int ordinal() {
      return 4;
    }
  }

  record Web(byte[] val, java.lang.String _val) implements EnumString, PlatformConfigParam {

    public static Web createRecord(final java.lang.String val) {
      return new Web(val.getBytes(UTF_8), val);
    }

    public static Web read(final byte[] data, final int offset) {
      return createRecord(Borsh.string(data, offset));
    }

    @Override
    public int ordinal() {
      return 5;
    }
  }

  record Img(byte[] val, java.lang.String _val) implements EnumString, PlatformConfigParam {

    public static Img createRecord(final java.lang.String val) {
      return new Img(val.getBytes(UTF_8), val);
    }

    public static Img read(final byte[] data, final int offset) {
      return createRecord(Borsh.string(data, offset));
    }

    @Override
    public int ordinal() {
      return 6;
    }
  }
}
