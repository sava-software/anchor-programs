package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record AddCompoundingLiquidityLog(String poolName, byte[] _poolName,
                                         PublicKey owner,
                                         long custodyUid,
                                         long amountIn,
                                         long compoundingAmountOut,
                                         long feeAmount,
                                         long rewardPerLpStaked,
                                         long compoundingPriceUsd,
                                         long tokenInPrice,
                                         int tokenInPriceExponent) implements Borsh {

  public static AddCompoundingLiquidityLog createRecord(final String poolName,
                                                        final PublicKey owner,
                                                        final long custodyUid,
                                                        final long amountIn,
                                                        final long compoundingAmountOut,
                                                        final long feeAmount,
                                                        final long rewardPerLpStaked,
                                                        final long compoundingPriceUsd,
                                                        final long tokenInPrice,
                                                        final int tokenInPriceExponent) {
    return new AddCompoundingLiquidityLog(poolName, poolName.getBytes(UTF_8),
                                          owner,
                                          custodyUid,
                                          amountIn,
                                          compoundingAmountOut,
                                          feeAmount,
                                          rewardPerLpStaked,
                                          compoundingPriceUsd,
                                          tokenInPrice,
                                          tokenInPriceExponent);
  }

  public static AddCompoundingLiquidityLog read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var poolName = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var owner = readPubKey(_data, i);
    i += 32;
    final var custodyUid = getInt64LE(_data, i);
    i += 8;
    final var amountIn = getInt64LE(_data, i);
    i += 8;
    final var compoundingAmountOut = getInt64LE(_data, i);
    i += 8;
    final var feeAmount = getInt64LE(_data, i);
    i += 8;
    final var rewardPerLpStaked = getInt64LE(_data, i);
    i += 8;
    final var compoundingPriceUsd = getInt64LE(_data, i);
    i += 8;
    final var tokenInPrice = getInt64LE(_data, i);
    i += 8;
    final var tokenInPriceExponent = getInt32LE(_data, i);
    return new AddCompoundingLiquidityLog(poolName, poolName.getBytes(UTF_8),
                                          owner,
                                          custodyUid,
                                          amountIn,
                                          compoundingAmountOut,
                                          feeAmount,
                                          rewardPerLpStaked,
                                          compoundingPriceUsd,
                                          tokenInPrice,
                                          tokenInPriceExponent);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_poolName, _data, i);
    owner.write(_data, i);
    i += 32;
    putInt64LE(_data, i, custodyUid);
    i += 8;
    putInt64LE(_data, i, amountIn);
    i += 8;
    putInt64LE(_data, i, compoundingAmountOut);
    i += 8;
    putInt64LE(_data, i, feeAmount);
    i += 8;
    putInt64LE(_data, i, rewardPerLpStaked);
    i += 8;
    putInt64LE(_data, i, compoundingPriceUsd);
    i += 8;
    putInt64LE(_data, i, tokenInPrice);
    i += 8;
    putInt32LE(_data, i, tokenInPriceExponent);
    i += 4;
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
         + 8
         + 8
         + 4;
  }
}
