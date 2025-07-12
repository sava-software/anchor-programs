package software.sava.anchor.programs.kamino.lend.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Reserve liquidity
public record ReserveLiquidity(// Reserve liquidity mint address
                               PublicKey mintPubkey,
                               // Reserve liquidity supply address
                               PublicKey supplyVault,
                               // Reserve liquidity fee collection address
                               PublicKey feeVault,
                               // Reserve liquidity available
                               long availableAmount,
                               // Reserve liquidity borrowed (scaled fraction)
                               BigInteger borrowedAmountSf,
                               // Reserve liquidity market price in quote currency (scaled fraction)
                               BigInteger marketPriceSf,
                               // Unix timestamp of the market price (from the oracle)
                               long marketPriceLastUpdatedTs,
                               // Reserve liquidity mint decimals
                               long mintDecimals,
                               // Timestamp when the last refresh reserve detected that the liquidity amount is above the deposit cap. When this threshold is crossed, then redemptions (auto-deleverage) are enabled.
                               // If the threshold is not crossed, then the timestamp is set to 0
                               long depositLimitCrossedTimestamp,
                               // Timestamp when the last refresh reserve detected that the borrowed amount is above the borrow cap. When this threshold is crossed, then redemptions (auto-deleverage) are enabled.
                               // If the threshold is not crossed, then the timestamp is set to 0
                               long borrowLimitCrossedTimestamp,
                               // Reserve liquidity cumulative borrow rate (scaled fraction)
                               BigFractionBytes cumulativeBorrowRateBsf,
                               // Reserve cumulative protocol fees (scaled fraction)
                               BigInteger accumulatedProtocolFeesSf,
                               // Reserve cumulative referrer fees (scaled fraction)
                               BigInteger accumulatedReferrerFeesSf,
                               // Reserve pending referrer fees, to be claimed in refresh_obligation by referrer or protocol (scaled fraction)
                               BigInteger pendingReferrerFeesSf,
                               // Reserve referrer fee absolute rate calculated at each refresh_reserve operation (scaled fraction)
                               BigInteger absoluteReferralRateSf,
                               // Token program of the liquidity mint
                               PublicKey tokenProgram,
                               long[] padding2,
                               BigInteger[] padding3) implements Borsh {

  public static final int BYTES = 1232;
  public static final int PADDING_2_LEN = 51;
  public static final int PADDING_3_LEN = 32;

  public static ReserveLiquidity read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var mintPubkey = readPubKey(_data, i);
    i += 32;
    final var supplyVault = readPubKey(_data, i);
    i += 32;
    final var feeVault = readPubKey(_data, i);
    i += 32;
    final var availableAmount = getInt64LE(_data, i);
    i += 8;
    final var borrowedAmountSf = getInt128LE(_data, i);
    i += 16;
    final var marketPriceSf = getInt128LE(_data, i);
    i += 16;
    final var marketPriceLastUpdatedTs = getInt64LE(_data, i);
    i += 8;
    final var mintDecimals = getInt64LE(_data, i);
    i += 8;
    final var depositLimitCrossedTimestamp = getInt64LE(_data, i);
    i += 8;
    final var borrowLimitCrossedTimestamp = getInt64LE(_data, i);
    i += 8;
    final var cumulativeBorrowRateBsf = BigFractionBytes.read(_data, i);
    i += Borsh.len(cumulativeBorrowRateBsf);
    final var accumulatedProtocolFeesSf = getInt128LE(_data, i);
    i += 16;
    final var accumulatedReferrerFeesSf = getInt128LE(_data, i);
    i += 16;
    final var pendingReferrerFeesSf = getInt128LE(_data, i);
    i += 16;
    final var absoluteReferralRateSf = getInt128LE(_data, i);
    i += 16;
    final var tokenProgram = readPubKey(_data, i);
    i += 32;
    final var padding2 = new long[51];
    i += Borsh.readArray(padding2, _data, i);
    final var padding3 = new BigInteger[32];
    Borsh.read128Array(padding3, _data, i);
    return new ReserveLiquidity(mintPubkey,
                                supplyVault,
                                feeVault,
                                availableAmount,
                                borrowedAmountSf,
                                marketPriceSf,
                                marketPriceLastUpdatedTs,
                                mintDecimals,
                                depositLimitCrossedTimestamp,
                                borrowLimitCrossedTimestamp,
                                cumulativeBorrowRateBsf,
                                accumulatedProtocolFeesSf,
                                accumulatedReferrerFeesSf,
                                pendingReferrerFeesSf,
                                absoluteReferralRateSf,
                                tokenProgram,
                                padding2,
                                padding3);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    mintPubkey.write(_data, i);
    i += 32;
    supplyVault.write(_data, i);
    i += 32;
    feeVault.write(_data, i);
    i += 32;
    putInt64LE(_data, i, availableAmount);
    i += 8;
    putInt128LE(_data, i, borrowedAmountSf);
    i += 16;
    putInt128LE(_data, i, marketPriceSf);
    i += 16;
    putInt64LE(_data, i, marketPriceLastUpdatedTs);
    i += 8;
    putInt64LE(_data, i, mintDecimals);
    i += 8;
    putInt64LE(_data, i, depositLimitCrossedTimestamp);
    i += 8;
    putInt64LE(_data, i, borrowLimitCrossedTimestamp);
    i += 8;
    i += Borsh.write(cumulativeBorrowRateBsf, _data, i);
    putInt128LE(_data, i, accumulatedProtocolFeesSf);
    i += 16;
    putInt128LE(_data, i, accumulatedReferrerFeesSf);
    i += 16;
    putInt128LE(_data, i, pendingReferrerFeesSf);
    i += 16;
    putInt128LE(_data, i, absoluteReferralRateSf);
    i += 16;
    tokenProgram.write(_data, i);
    i += 32;
    i += Borsh.writeArray(padding2, _data, i);
    i += Borsh.write128Array(padding3, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
