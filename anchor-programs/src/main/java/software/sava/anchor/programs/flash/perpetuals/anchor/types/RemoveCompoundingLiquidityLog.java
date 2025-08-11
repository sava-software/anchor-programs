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

public record RemoveCompoundingLiquidityLog(String poolName, byte[] _poolName,
                                            PublicKey owner,
                                            long custodyUid,
                                            long compoundingAmountIn,
                                            long amountOut,
                                            long feeAmount,
                                            long rewardPerLpStaked,
                                            long compoundingPriceUsd,
                                            long tokenOutPrice,
                                            int tokenOutPriceExponent) implements Borsh {

  public static RemoveCompoundingLiquidityLog createRecord(final String poolName,
                                                           final PublicKey owner,
                                                           final long custodyUid,
                                                           final long compoundingAmountIn,
                                                           final long amountOut,
                                                           final long feeAmount,
                                                           final long rewardPerLpStaked,
                                                           final long compoundingPriceUsd,
                                                           final long tokenOutPrice,
                                                           final int tokenOutPriceExponent) {
    return new RemoveCompoundingLiquidityLog(poolName, poolName.getBytes(UTF_8),
                                             owner,
                                             custodyUid,
                                             compoundingAmountIn,
                                             amountOut,
                                             feeAmount,
                                             rewardPerLpStaked,
                                             compoundingPriceUsd,
                                             tokenOutPrice,
                                             tokenOutPriceExponent);
  }

  public static RemoveCompoundingLiquidityLog read(final byte[] _data, final int offset) {
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
    final var compoundingAmountIn = getInt64LE(_data, i);
    i += 8;
    final var amountOut = getInt64LE(_data, i);
    i += 8;
    final var feeAmount = getInt64LE(_data, i);
    i += 8;
    final var rewardPerLpStaked = getInt64LE(_data, i);
    i += 8;
    final var compoundingPriceUsd = getInt64LE(_data, i);
    i += 8;
    final var tokenOutPrice = getInt64LE(_data, i);
    i += 8;
    final var tokenOutPriceExponent = getInt32LE(_data, i);
    return new RemoveCompoundingLiquidityLog(poolName, poolName.getBytes(UTF_8),
                                             owner,
                                             custodyUid,
                                             compoundingAmountIn,
                                             amountOut,
                                             feeAmount,
                                             rewardPerLpStaked,
                                             compoundingPriceUsd,
                                             tokenOutPrice,
                                             tokenOutPriceExponent);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_poolName, _data, i);
    owner.write(_data, i);
    i += 32;
    putInt64LE(_data, i, custodyUid);
    i += 8;
    putInt64LE(_data, i, compoundingAmountIn);
    i += 8;
    putInt64LE(_data, i, amountOut);
    i += 8;
    putInt64LE(_data, i, feeAmount);
    i += 8;
    putInt64LE(_data, i, rewardPerLpStaked);
    i += 8;
    putInt64LE(_data, i, compoundingPriceUsd);
    i += 8;
    putInt64LE(_data, i, tokenOutPrice);
    i += 8;
    putInt32LE(_data, i, tokenOutPriceExponent);
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
