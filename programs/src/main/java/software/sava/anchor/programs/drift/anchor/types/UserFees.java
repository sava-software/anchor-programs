package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record UserFees(// Total taker fee paid
                       // precision: QUOTE_PRECISION
                       long totalFeePaid,
                       // Total maker fee rebate
                       // precision: QUOTE_PRECISION
                       long totalFeeRebate,
                       // Total discount from holding token
                       // precision: QUOTE_PRECISION
                       long totalTokenDiscount,
                       // Total discount from being referred
                       // precision: QUOTE_PRECISION
                       long totalRefereeDiscount,
                       // Total reward to referrer
                       // precision: QUOTE_PRECISION
                       long totalReferrerReward,
                       // Total reward to referrer this epoch
                       // precision: QUOTE_PRECISION
                       long currentEpochReferrerReward) implements Borsh {

  public static final int BYTES = 48;

  public static UserFees read(final byte[] _data, final int offset) {
    int i = offset;
    final var totalFeePaid = getInt64LE(_data, i);
    i += 8;
    final var totalFeeRebate = getInt64LE(_data, i);
    i += 8;
    final var totalTokenDiscount = getInt64LE(_data, i);
    i += 8;
    final var totalRefereeDiscount = getInt64LE(_data, i);
    i += 8;
    final var totalReferrerReward = getInt64LE(_data, i);
    i += 8;
    final var currentEpochReferrerReward = getInt64LE(_data, i);
    return new UserFees(totalFeePaid,
                        totalFeeRebate,
                        totalTokenDiscount,
                        totalRefereeDiscount,
                        totalReferrerReward,
                        currentEpochReferrerReward);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, totalFeePaid);
    i += 8;
    putInt64LE(_data, i, totalFeeRebate);
    i += 8;
    putInt64LE(_data, i, totalTokenDiscount);
    i += 8;
    putInt64LE(_data, i, totalRefereeDiscount);
    i += 8;
    putInt64LE(_data, i, totalReferrerReward);
    i += 8;
    putInt64LE(_data, i, currentEpochReferrerReward);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return 8
         + 8
         + 8
         + 8
         + 8
         + 8;
  }
}