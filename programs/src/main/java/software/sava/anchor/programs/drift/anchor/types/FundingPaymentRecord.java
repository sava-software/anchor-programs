package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record FundingPaymentRecord(long ts,
                                   PublicKey userAuthority,
                                   PublicKey user,
                                   int marketIndex,
                                   long fundingPayment,
                                   long baseAssetAmount,
                                   long userLastCumulativeFunding,
                                   BigInteger ammCumulativeFundingLong,
                                   BigInteger ammCumulativeFundingShort) implements Borsh {

  public static final int BYTES = 130;

  public static FundingPaymentRecord read(final byte[] _data, final int offset) {
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var userAuthority = readPubKey(_data, i);
    i += 32;
    final var user = readPubKey(_data, i);
    i += 32;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var fundingPayment = getInt64LE(_data, i);
    i += 8;
    final var baseAssetAmount = getInt64LE(_data, i);
    i += 8;
    final var userLastCumulativeFunding = getInt64LE(_data, i);
    i += 8;
    final var ammCumulativeFundingLong = getInt128LE(_data, i);
    i += 16;
    final var ammCumulativeFundingShort = getInt128LE(_data, i);
    return new FundingPaymentRecord(ts,
                                    userAuthority,
                                    user,
                                    marketIndex,
                                    fundingPayment,
                                    baseAssetAmount,
                                    userLastCumulativeFunding,
                                    ammCumulativeFundingLong,
                                    ammCumulativeFundingShort);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    userAuthority.write(_data, i);
    i += 32;
    user.write(_data, i);
    i += 32;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt64LE(_data, i, fundingPayment);
    i += 8;
    putInt64LE(_data, i, baseAssetAmount);
    i += 8;
    putInt64LE(_data, i, userLastCumulativeFunding);
    i += 8;
    putInt128LE(_data, i, ammCumulativeFundingLong);
    i += 16;
    putInt128LE(_data, i, ammCumulativeFundingShort);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return 8
         + 32
         + 32
         + 2
         + 8
         + 8
         + 8
         + 16
         + 16;
  }
}
