package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record FundingRateRecord(long ts,
                                long recordId,
                                int marketIndex,
                                long fundingRate,
                                BigInteger fundingRateLong,
                                BigInteger fundingRateShort,
                                BigInteger cumulativeFundingRateLong,
                                BigInteger cumulativeFundingRateShort,
                                long oraclePriceTwap,
                                long markPriceTwap,
                                long periodRevenue,
                                BigInteger baseAssetAmountWithAmm,
                                BigInteger baseAssetAmountWithUnsettledLp) implements Borsh {

  public static final int BYTES = 146;

  public static FundingRateRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var recordId = getInt64LE(_data, i);
    i += 8;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var fundingRate = getInt64LE(_data, i);
    i += 8;
    final var fundingRateLong = getInt128LE(_data, i);
    i += 16;
    final var fundingRateShort = getInt128LE(_data, i);
    i += 16;
    final var cumulativeFundingRateLong = getInt128LE(_data, i);
    i += 16;
    final var cumulativeFundingRateShort = getInt128LE(_data, i);
    i += 16;
    final var oraclePriceTwap = getInt64LE(_data, i);
    i += 8;
    final var markPriceTwap = getInt64LE(_data, i);
    i += 8;
    final var periodRevenue = getInt64LE(_data, i);
    i += 8;
    final var baseAssetAmountWithAmm = getInt128LE(_data, i);
    i += 16;
    final var baseAssetAmountWithUnsettledLp = getInt128LE(_data, i);
    return new FundingRateRecord(ts,
                                 recordId,
                                 marketIndex,
                                 fundingRate,
                                 fundingRateLong,
                                 fundingRateShort,
                                 cumulativeFundingRateLong,
                                 cumulativeFundingRateShort,
                                 oraclePriceTwap,
                                 markPriceTwap,
                                 periodRevenue,
                                 baseAssetAmountWithAmm,
                                 baseAssetAmountWithUnsettledLp);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    putInt64LE(_data, i, recordId);
    i += 8;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt64LE(_data, i, fundingRate);
    i += 8;
    putInt128LE(_data, i, fundingRateLong);
    i += 16;
    putInt128LE(_data, i, fundingRateShort);
    i += 16;
    putInt128LE(_data, i, cumulativeFundingRateLong);
    i += 16;
    putInt128LE(_data, i, cumulativeFundingRateShort);
    i += 16;
    putInt64LE(_data, i, oraclePriceTwap);
    i += 8;
    putInt64LE(_data, i, markPriceTwap);
    i += 8;
    putInt64LE(_data, i, periodRevenue);
    i += 8;
    putInt128LE(_data, i, baseAssetAmountWithAmm);
    i += 16;
    putInt128LE(_data, i, baseAssetAmountWithUnsettledLp);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
