package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PlatformConfigInfo(PublicKey feeWallet,
                                 PublicKey nftWallet,
                                 MigrateNftInfo migrateNftInfo,
                                 long feeRate,
                                 String name, byte[] _name,
                                 String web, byte[] _web,
                                 String img, byte[] _img,
                                 PublicKey transferFeeExtensionAuth,
                                 long creatorFeeRate) implements Borsh {

  public static PlatformConfigInfo createRecord(final PublicKey feeWallet,
                                                final PublicKey nftWallet,
                                                final MigrateNftInfo migrateNftInfo,
                                                final long feeRate,
                                                final String name,
                                                final String web,
                                                final String img,
                                                final PublicKey transferFeeExtensionAuth,
                                                final long creatorFeeRate) {
    return new PlatformConfigInfo(feeWallet,
                                  nftWallet,
                                  migrateNftInfo,
                                  feeRate,
                                  name, name.getBytes(UTF_8),
                                  web, web.getBytes(UTF_8),
                                  img, img.getBytes(UTF_8),
                                  transferFeeExtensionAuth,
                                  creatorFeeRate);
  }

  public static PlatformConfigInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var feeWallet = readPubKey(_data, i);
    i += 32;
    final var nftWallet = readPubKey(_data, i);
    i += 32;
    final var migrateNftInfo = MigrateNftInfo.read(_data, i);
    i += Borsh.len(migrateNftInfo);
    final var feeRate = getInt64LE(_data, i);
    i += 8;
    final var name = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var web = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var img = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var transferFeeExtensionAuth = readPubKey(_data, i);
    i += 32;
    final var creatorFeeRate = getInt64LE(_data, i);
    return new PlatformConfigInfo(feeWallet,
                                  nftWallet,
                                  migrateNftInfo,
                                  feeRate,
                                  name, name.getBytes(UTF_8),
                                  web, web.getBytes(UTF_8),
                                  img, img.getBytes(UTF_8),
                                  transferFeeExtensionAuth,
                                  creatorFeeRate);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    feeWallet.write(_data, i);
    i += 32;
    nftWallet.write(_data, i);
    i += 32;
    i += Borsh.write(migrateNftInfo, _data, i);
    putInt64LE(_data, i, feeRate);
    i += 8;
    i += Borsh.writeVector(_name, _data, i);
    i += Borsh.writeVector(_web, _data, i);
    i += Borsh.writeVector(_img, _data, i);
    transferFeeExtensionAuth.write(_data, i);
    i += 32;
    putInt64LE(_data, i, creatorFeeRate);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + 32
         + Borsh.len(migrateNftInfo)
         + 8
         + Borsh.lenVector(_name)
         + Borsh.lenVector(_web)
         + Borsh.lenVector(_img)
         + 32
         + 8;
  }
}
