package software.sava.anchor.programs.loopscale.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RewardInfo(// Reward state
                         int rewardState,
                         // Reward open time
                         long openTime,
                         // Reward end time
                         long endTime,
                         // Reward last update time
                         long lastUpdateTime,
                         // Q64.64 number indicates how many tokens per second are earned per unit of liquidity.
                         BigInteger emissionsPerSecondX64,
                         // The total amount of reward emissioned
                         long rewardTotalEmissioned,
                         // The total amount of claimed reward
                         long rewardClaimed,
                         // Reward token mint.
                         PublicKey tokenMint,
                         // Reward vault token account.
                         PublicKey tokenVault,
                         // The owner that has permission to set reward param
                         PublicKey authority,
                         // Q64.64 number that tracks the total tokens earned per unit of liquidity since the reward
                         // emissions were turned on.
                         BigInteger rewardGrowthGlobalX64) implements Borsh {

  public static final int BYTES = 169;

  public static RewardInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var rewardState = _data[i] & 0xFF;
    ++i;
    final var openTime = getInt64LE(_data, i);
    i += 8;
    final var endTime = getInt64LE(_data, i);
    i += 8;
    final var lastUpdateTime = getInt64LE(_data, i);
    i += 8;
    final var emissionsPerSecondX64 = getInt128LE(_data, i);
    i += 16;
    final var rewardTotalEmissioned = getInt64LE(_data, i);
    i += 8;
    final var rewardClaimed = getInt64LE(_data, i);
    i += 8;
    final var tokenMint = readPubKey(_data, i);
    i += 32;
    final var tokenVault = readPubKey(_data, i);
    i += 32;
    final var authority = readPubKey(_data, i);
    i += 32;
    final var rewardGrowthGlobalX64 = getInt128LE(_data, i);
    return new RewardInfo(rewardState,
                          openTime,
                          endTime,
                          lastUpdateTime,
                          emissionsPerSecondX64,
                          rewardTotalEmissioned,
                          rewardClaimed,
                          tokenMint,
                          tokenVault,
                          authority,
                          rewardGrowthGlobalX64);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) rewardState;
    ++i;
    putInt64LE(_data, i, openTime);
    i += 8;
    putInt64LE(_data, i, endTime);
    i += 8;
    putInt64LE(_data, i, lastUpdateTime);
    i += 8;
    putInt128LE(_data, i, emissionsPerSecondX64);
    i += 16;
    putInt64LE(_data, i, rewardTotalEmissioned);
    i += 8;
    putInt64LE(_data, i, rewardClaimed);
    i += 8;
    tokenMint.write(_data, i);
    i += 32;
    tokenVault.write(_data, i);
    i += 32;
    authority.write(_data, i);
    i += 32;
    putInt128LE(_data, i, rewardGrowthGlobalX64);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
