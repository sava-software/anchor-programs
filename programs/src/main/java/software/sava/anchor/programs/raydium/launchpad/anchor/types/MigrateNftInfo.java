package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Represents the parameters for initializing a platform config account(Only support MigrateType::CPSWAP)
// # Fields
// * `platform_scale` - Scale of the platform liquidity quantity rights will be converted into NFT
// * `creator_scale` - Scale of the token creator liquidity quantity rights will be converted into NFT
// * `burn_scale` - Scale of liquidity directly to burn
// 
// * platform_scale + creator_scale + burn_scale = RATE_DENOMINATOR_VALUE
public record MigrateNftInfo(long platformScale,
                             long creatorScale,
                             long burnScale) implements Borsh {

  public static final int BYTES = 24;

  public static MigrateNftInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var platformScale = getInt64LE(_data, i);
    i += 8;
    final var creatorScale = getInt64LE(_data, i);
    i += 8;
    final var burnScale = getInt64LE(_data, i);
    return new MigrateNftInfo(platformScale, creatorScale, burnScale);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, platformScale);
    i += 8;
    putInt64LE(_data, i, creatorScale);
    i += 8;
    putInt64LE(_data, i, burnScale);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
