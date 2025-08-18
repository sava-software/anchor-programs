package software.sava.anchor.programs.loopscale.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;

// Stores the state relevant for tracking liquidity mining rewards at the `Whirlpool` level.
// These values are used in conjunction with `PositionRewardInfo`, `Tick.reward_growths_outside`,
// and `Whirlpool.reward_last_updated_timestamp` to determine how many rewards are earned by open
// positions.
public record WhirlpoolRewardInfo(// Reward token mint.
                                  PublicKey mint,
                                  // Reward vault token account.
                                  PublicKey vault,
                                  // Authority account that has permission to initialize the reward and set emissions.
                                  PublicKey authority,
                                  // Q64.64 number that indicates how many tokens per second are earned per unit of liquidity.
                                  BigInteger emissionsPerSecondX64,
                                  // Q64.64 number that tracks the total tokens earned per unit of liquidity since the reward
                                  // emissions were turned on.
                                  BigInteger growthGlobalX64) implements Borsh {

  public static final int BYTES = 128;

  public static WhirlpoolRewardInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var authority = readPubKey(_data, i);
    i += 32;
    final var emissionsPerSecondX64 = getInt128LE(_data, i);
    i += 16;
    final var growthGlobalX64 = getInt128LE(_data, i);
    return new WhirlpoolRewardInfo(mint,
                                   vault,
                                   authority,
                                   emissionsPerSecondX64,
                                   growthGlobalX64);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    mint.write(_data, i);
    i += 32;
    vault.write(_data, i);
    i += 32;
    authority.write(_data, i);
    i += 32;
    putInt128LE(_data, i, emissionsPerSecondX64);
    i += 16;
    putInt128LE(_data, i, growthGlobalX64);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
