package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record MigrateStakeLog(String poolName, byte[] _poolName,
                              PublicKey owner,
                              long amount,
                              long compoundingAmountOut,
                              long poolLpAmount,
                              long rewardPerLpStaked,
                              long lpPriceUsd,
                              long compoundingPriceUsd) implements Borsh {

  public static MigrateStakeLog createRecord(final String poolName,
                                             final PublicKey owner,
                                             final long amount,
                                             final long compoundingAmountOut,
                                             final long poolLpAmount,
                                             final long rewardPerLpStaked,
                                             final long lpPriceUsd,
                                             final long compoundingPriceUsd) {
    return new MigrateStakeLog(poolName, poolName.getBytes(UTF_8),
                               owner,
                               amount,
                               compoundingAmountOut,
                               poolLpAmount,
                               rewardPerLpStaked,
                               lpPriceUsd,
                               compoundingPriceUsd);
  }

  public static MigrateStakeLog read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var poolName = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var owner = readPubKey(_data, i);
    i += 32;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var compoundingAmountOut = getInt64LE(_data, i);
    i += 8;
    final var poolLpAmount = getInt64LE(_data, i);
    i += 8;
    final var rewardPerLpStaked = getInt64LE(_data, i);
    i += 8;
    final var lpPriceUsd = getInt64LE(_data, i);
    i += 8;
    final var compoundingPriceUsd = getInt64LE(_data, i);
    return new MigrateStakeLog(poolName, poolName.getBytes(UTF_8),
                               owner,
                               amount,
                               compoundingAmountOut,
                               poolLpAmount,
                               rewardPerLpStaked,
                               lpPriceUsd,
                               compoundingPriceUsd);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_poolName, _data, i);
    owner.write(_data, i);
    i += 32;
    putInt64LE(_data, i, amount);
    i += 8;
    putInt64LE(_data, i, compoundingAmountOut);
    i += 8;
    putInt64LE(_data, i, poolLpAmount);
    i += 8;
    putInt64LE(_data, i, rewardPerLpStaked);
    i += 8;
    putInt64LE(_data, i, lpPriceUsd);
    i += 8;
    putInt64LE(_data, i, compoundingPriceUsd);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_poolName)
         + 32
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8;
  }
}
