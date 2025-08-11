package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SetPerpetualsConfigParams(boolean allowUngatedTrading,
                                        long[] tradingDiscount,
                                        long[] referralRebate,
                                        long defaultRebate,
                                        VoltageMultiplier voltageMultiplier,
                                        int tradeLimit,
                                        int rebateLimitUsd) implements Borsh {

  public static final int BYTES = 135;
  public static final int TRADING_DISCOUNT_LEN = 6;
  public static final int REFERRAL_REBATE_LEN = 6;

  public static SetPerpetualsConfigParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var allowUngatedTrading = _data[i] == 1;
    ++i;
    final var tradingDiscount = new long[6];
    i += Borsh.readArray(tradingDiscount, _data, i);
    final var referralRebate = new long[6];
    i += Borsh.readArray(referralRebate, _data, i);
    final var defaultRebate = getInt64LE(_data, i);
    i += 8;
    final var voltageMultiplier = VoltageMultiplier.read(_data, i);
    i += Borsh.len(voltageMultiplier);
    final var tradeLimit = getInt16LE(_data, i);
    i += 2;
    final var rebateLimitUsd = getInt32LE(_data, i);
    return new SetPerpetualsConfigParams(allowUngatedTrading,
                                         tradingDiscount,
                                         referralRebate,
                                         defaultRebate,
                                         voltageMultiplier,
                                         tradeLimit,
                                         rebateLimitUsd);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) (allowUngatedTrading ? 1 : 0);
    ++i;
    i += Borsh.writeArray(tradingDiscount, _data, i);
    i += Borsh.writeArray(referralRebate, _data, i);
    putInt64LE(_data, i, defaultRebate);
    i += 8;
    i += Borsh.write(voltageMultiplier, _data, i);
    putInt16LE(_data, i, tradeLimit);
    i += 2;
    putInt32LE(_data, i, rebateLimitUsd);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
