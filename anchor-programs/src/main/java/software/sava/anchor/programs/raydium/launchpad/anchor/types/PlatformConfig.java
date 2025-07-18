package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record PlatformConfig(PublicKey _address,
                             Discriminator discriminator,
                             // The epoch for update interval
                             long epoch,
                             // The platform fee wallet
                             PublicKey platformFeeWallet,
                             // The platform nft wallet to receive the platform NFT after migration if platform_scale is not 0(Only support MigrateType::CPSWAP)
                             PublicKey platformNftWallet,
                             // Scale of the platform liquidity quantity rights will be converted into NFT(Only support MigrateType::CPSWAP)
                             long platformScale,
                             // Scale of the token creator liquidity quantity rights will be converted into NFT(Only support MigrateType::CPSWAP)
                             long creatorScale,
                             // Scale of liquidity directly to burn
                             long burnScale,
                             // The platform fee rate
                             long feeRate,
                             // The platform name
                             byte[] name,
                             // The platform website
                             byte[] web,
                             // The platform img link
                             byte[] img,
                             // padding for future updates
                             byte[] padding) implements Borsh {

  public static final int BYTES = 944;
  public static final int NAME_LEN = 64;
  public static final int WEB_LEN = 256;
  public static final int IMG_LEN = 256;
  public static final int PADDING_LEN = 256;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(160, 78, 128, 0, 248, 83, 230, 160);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int EPOCH_OFFSET = 8;
  public static final int PLATFORM_FEE_WALLET_OFFSET = 16;
  public static final int PLATFORM_NFT_WALLET_OFFSET = 48;
  public static final int PLATFORM_SCALE_OFFSET = 80;
  public static final int CREATOR_SCALE_OFFSET = 88;
  public static final int BURN_SCALE_OFFSET = 96;
  public static final int FEE_RATE_OFFSET = 104;
  public static final int NAME_OFFSET = 112;
  public static final int WEB_OFFSET = 176;
  public static final int IMG_OFFSET = 432;
  public static final int PADDING_OFFSET = 688;

  public static Filter createEpochFilter(final long epoch) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, epoch);
    return Filter.createMemCompFilter(EPOCH_OFFSET, _data);
  }

  public static Filter createPlatformFeeWalletFilter(final PublicKey platformFeeWallet) {
    return Filter.createMemCompFilter(PLATFORM_FEE_WALLET_OFFSET, platformFeeWallet);
  }

  public static Filter createPlatformNftWalletFilter(final PublicKey platformNftWallet) {
    return Filter.createMemCompFilter(PLATFORM_NFT_WALLET_OFFSET, platformNftWallet);
  }

  public static Filter createPlatformScaleFilter(final long platformScale) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, platformScale);
    return Filter.createMemCompFilter(PLATFORM_SCALE_OFFSET, _data);
  }

  public static Filter createCreatorScaleFilter(final long creatorScale) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, creatorScale);
    return Filter.createMemCompFilter(CREATOR_SCALE_OFFSET, _data);
  }

  public static Filter createBurnScaleFilter(final long burnScale) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, burnScale);
    return Filter.createMemCompFilter(BURN_SCALE_OFFSET, _data);
  }

  public static Filter createFeeRateFilter(final long feeRate) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, feeRate);
    return Filter.createMemCompFilter(FEE_RATE_OFFSET, _data);
  }

  public static PlatformConfig read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static PlatformConfig read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static PlatformConfig read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], PlatformConfig> FACTORY = PlatformConfig::read;

  public static PlatformConfig read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var platformFeeWallet = readPubKey(_data, i);
    i += 32;
    final var platformNftWallet = readPubKey(_data, i);
    i += 32;
    final var platformScale = getInt64LE(_data, i);
    i += 8;
    final var creatorScale = getInt64LE(_data, i);
    i += 8;
    final var burnScale = getInt64LE(_data, i);
    i += 8;
    final var feeRate = getInt64LE(_data, i);
    i += 8;
    final var name = new byte[64];
    i += Borsh.readArray(name, _data, i);
    final var web = new byte[256];
    i += Borsh.readArray(web, _data, i);
    final var img = new byte[256];
    i += Borsh.readArray(img, _data, i);
    final var padding = new byte[256];
    Borsh.readArray(padding, _data, i);
    return new PlatformConfig(_address,
                              discriminator,
                              epoch,
                              platformFeeWallet,
                              platformNftWallet,
                              platformScale,
                              creatorScale,
                              burnScale,
                              feeRate,
                              name,
                              web,
                              img,
                              padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, epoch);
    i += 8;
    platformFeeWallet.write(_data, i);
    i += 32;
    platformNftWallet.write(_data, i);
    i += 32;
    putInt64LE(_data, i, platformScale);
    i += 8;
    putInt64LE(_data, i, creatorScale);
    i += 8;
    putInt64LE(_data, i, burnScale);
    i += 8;
    putInt64LE(_data, i, feeRate);
    i += 8;
    i += Borsh.writeArray(name, _data, i);
    i += Borsh.writeArray(web, _data, i);
    i += Borsh.writeArray(img, _data, i);
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
