package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Stores the state relevant for tracking liquidity mining rewards
public record RewardInfo(// Reward token mint.
                         PublicKey mint,
                         // Reward vault token account.
                         PublicKey vault,
                         // Authority account that allows to fund rewards
                         PublicKey funder,
                         // TODO check whether we need to store it in pool
                         long rewardDuration,
                         // TODO check whether we need to store it in pool
                         long rewardDurationEnd,
                         // TODO check whether we need to store it in pool
                         BigInteger rewardRate,
                         // The last time reward states were updated.
                         long lastUpdateTime,
                         // Accumulated seconds where when farm distribute rewards, but the bin is empty. The reward will be accumulated for next reward time window.
                         long cumulativeSecondsWithEmptyLiquidityReward) implements Borsh {

  public static final int BYTES = 144;

  public static RewardInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var funder = readPubKey(_data, i);
    i += 32;
    final var rewardDuration = getInt64LE(_data, i);
    i += 8;
    final var rewardDurationEnd = getInt64LE(_data, i);
    i += 8;
    final var rewardRate = getInt128LE(_data, i);
    i += 16;
    final var lastUpdateTime = getInt64LE(_data, i);
    i += 8;
    final var cumulativeSecondsWithEmptyLiquidityReward = getInt64LE(_data, i);
    return new RewardInfo(mint,
                          vault,
                          funder,
                          rewardDuration,
                          rewardDurationEnd,
                          rewardRate,
                          lastUpdateTime,
                          cumulativeSecondsWithEmptyLiquidityReward);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    mint.write(_data, i);
    i += 32;
    vault.write(_data, i);
    i += 32;
    funder.write(_data, i);
    i += 32;
    putInt64LE(_data, i, rewardDuration);
    i += 8;
    putInt64LE(_data, i, rewardDurationEnd);
    i += 8;
    putInt128LE(_data, i, rewardRate);
    i += 16;
    putInt64LE(_data, i, lastUpdateTime);
    i += 8;
    putInt64LE(_data, i, cumulativeSecondsWithEmptyLiquidityReward);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
