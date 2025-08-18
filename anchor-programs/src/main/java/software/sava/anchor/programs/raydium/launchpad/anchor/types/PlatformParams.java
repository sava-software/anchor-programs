package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Represents the parameters for initializing a platform config account
// # Fields
// * `migrate_nft_info` - The platform configures liquidity info during migration(Only support MigrateType::CPSWAP)
// * `fee_rate` - Fee rate of the platform
// * `name` - Name of the platform
// * `web` - Website of the platform
// * `img` - Image link of the platform
// /// * `creator_fee_rate` - The fee rate charged by the creator for each transaction.
public record PlatformParams(MigrateNftInfo migrateNftInfo,
                             long feeRate,
                             String name, byte[] _name,
                             String web, byte[] _web,
                             String img, byte[] _img,
                             long creatorFeeRate) implements Borsh {

  public static PlatformParams createRecord(final MigrateNftInfo migrateNftInfo,
                                            final long feeRate,
                                            final String name,
                                            final String web,
                                            final String img,
                                            final long creatorFeeRate) {
    return new PlatformParams(migrateNftInfo,
                              feeRate,
                              name, name.getBytes(UTF_8),
                              web, web.getBytes(UTF_8),
                              img, img.getBytes(UTF_8),
                              creatorFeeRate);
  }

  public static PlatformParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
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
    final var creatorFeeRate = getInt64LE(_data, i);
    return new PlatformParams(migrateNftInfo,
                              feeRate,
                              name, name.getBytes(UTF_8),
                              web, web.getBytes(UTF_8),
                              img, img.getBytes(UTF_8),
                              creatorFeeRate);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(migrateNftInfo, _data, i);
    putInt64LE(_data, i, feeRate);
    i += 8;
    i += Borsh.writeVector(_name, _data, i);
    i += Borsh.writeVector(_web, _data, i);
    i += Borsh.writeVector(_img, _data, i);
    putInt64LE(_data, i, creatorFeeRate);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(migrateNftInfo)
         + 8
         + Borsh.lenVector(_name)
         + Borsh.lenVector(_web)
         + Borsh.lenVector(_img)
         + 8;
  }
}
