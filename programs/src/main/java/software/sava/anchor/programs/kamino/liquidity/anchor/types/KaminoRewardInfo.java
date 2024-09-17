package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record KaminoRewardInfo(long decimals,
                               PublicKey rewardVault,
                               PublicKey rewardMint,
                               long rewardCollateralId,
                               long lastIssuanceTs,
                               long rewardPerSecond,
                               long amountUncollected,
                               long amountIssuedCumulative,
                               long amountAvailable) implements Borsh {

  public static final int BYTES = 120;

  public static KaminoRewardInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var decimals = getInt64LE(_data, i);
    i += 8;
    final var rewardVault = readPubKey(_data, i);
    i += 32;
    final var rewardMint = readPubKey(_data, i);
    i += 32;
    final var rewardCollateralId = getInt64LE(_data, i);
    i += 8;
    final var lastIssuanceTs = getInt64LE(_data, i);
    i += 8;
    final var rewardPerSecond = getInt64LE(_data, i);
    i += 8;
    final var amountUncollected = getInt64LE(_data, i);
    i += 8;
    final var amountIssuedCumulative = getInt64LE(_data, i);
    i += 8;
    final var amountAvailable = getInt64LE(_data, i);
    return new KaminoRewardInfo(decimals,
                                rewardVault,
                                rewardMint,
                                rewardCollateralId,
                                lastIssuanceTs,
                                rewardPerSecond,
                                amountUncollected,
                                amountIssuedCumulative,
                                amountAvailable);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, decimals);
    i += 8;
    rewardVault.write(_data, i);
    i += 32;
    rewardMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, rewardCollateralId);
    i += 8;
    putInt64LE(_data, i, lastIssuanceTs);
    i += 8;
    putInt64LE(_data, i, rewardPerSecond);
    i += 8;
    putInt64LE(_data, i, amountUncollected);
    i += 8;
    putInt64LE(_data, i, amountIssuedCumulative);
    i += 8;
    putInt64LE(_data, i, amountAvailable);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
