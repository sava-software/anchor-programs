package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InsuranceClaim(// The amount of revenue last settled
                             // Positive if funds left the perp market,
                             // negative if funds were pulled into the perp market
                             // precision: QUOTE_PRECISION
                             long revenueWithdrawSinceLastSettle,
                             // The max amount of revenue that can be withdrawn per period
                             // precision: QUOTE_PRECISION
                             long maxRevenueWithdrawPerPeriod,
                             // The max amount of insurance that perp market can use to resolve bankruptcy and pnl deficits
                             // precision: QUOTE_PRECISION
                             long quoteMaxInsurance,
                             // The amount of insurance that has been used to resolve bankruptcy and pnl deficits
                             // precision: QUOTE_PRECISION
                             long quoteSettledInsurance,
                             // The last time revenue was settled in/out of market
                             long lastRevenueWithdrawTs) implements Borsh {

  public static final int BYTES = 40;

  public static InsuranceClaim read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var revenueWithdrawSinceLastSettle = getInt64LE(_data, i);
    i += 8;
    final var maxRevenueWithdrawPerPeriod = getInt64LE(_data, i);
    i += 8;
    final var quoteMaxInsurance = getInt64LE(_data, i);
    i += 8;
    final var quoteSettledInsurance = getInt64LE(_data, i);
    i += 8;
    final var lastRevenueWithdrawTs = getInt64LE(_data, i);
    return new InsuranceClaim(revenueWithdrawSinceLastSettle,
                              maxRevenueWithdrawPerPeriod,
                              quoteMaxInsurance,
                              quoteSettledInsurance,
                              lastRevenueWithdrawTs);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, revenueWithdrawSinceLastSettle);
    i += 8;
    putInt64LE(_data, i, maxRevenueWithdrawPerPeriod);
    i += 8;
    putInt64LE(_data, i, quoteMaxInsurance);
    i += 8;
    putInt64LE(_data, i, quoteSettledInsurance);
    i += 8;
    putInt64LE(_data, i, lastRevenueWithdrawTs);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
