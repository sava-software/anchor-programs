package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record BondingCurveParam(// Migrate to AMM or CpSwap, 0: amm��� 1: cpswap���
                                // Neither 0 nor 1: invalid
                                int migrateType,
                                // The migrate fee on, 0 means fee on the quote token, 1 means fee on both token
                                // Neither 0 nor 1: invalid
                                int migrateCpmmFeeOn,
                                // The supply of the token,
                                // 0: invalid
                                long supply,
                                // The total base sell of the token
                                // 0: invalid
                                long totalBaseSell,
                                // The total quote fund raising of the token
                                // 0: invalid
                                long totalQuoteFundRaising,
                                // total amount of tokens to be unlocked
                                // u64::MAX: invalid
                                long totalLockedAmount,
                                // Waiting time in seconds before unlocking after fundraising ends
                                // u64::MAX: invalid
                                long cliffPeriod,
                                // Unlocking period in seconds
                                // u64::MAX: invalid
                                long unlockPeriod) implements Borsh {

  public static final int BYTES = 50;

  public static BondingCurveParam read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var migrateType = _data[i] & 0xFF;
    ++i;
    final var migrateCpmmFeeOn = _data[i] & 0xFF;
    ++i;
    final var supply = getInt64LE(_data, i);
    i += 8;
    final var totalBaseSell = getInt64LE(_data, i);
    i += 8;
    final var totalQuoteFundRaising = getInt64LE(_data, i);
    i += 8;
    final var totalLockedAmount = getInt64LE(_data, i);
    i += 8;
    final var cliffPeriod = getInt64LE(_data, i);
    i += 8;
    final var unlockPeriod = getInt64LE(_data, i);
    return new BondingCurveParam(migrateType,
                                 migrateCpmmFeeOn,
                                 supply,
                                 totalBaseSell,
                                 totalQuoteFundRaising,
                                 totalLockedAmount,
                                 cliffPeriod,
                                 unlockPeriod);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) migrateType;
    ++i;
    _data[i] = (byte) migrateCpmmFeeOn;
    ++i;
    putInt64LE(_data, i, supply);
    i += 8;
    putInt64LE(_data, i, totalBaseSell);
    i += 8;
    putInt64LE(_data, i, totalQuoteFundRaising);
    i += 8;
    putInt64LE(_data, i, totalLockedAmount);
    i += 8;
    putInt64LE(_data, i, cliffPeriod);
    i += 8;
    putInt64LE(_data, i, unlockPeriod);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
