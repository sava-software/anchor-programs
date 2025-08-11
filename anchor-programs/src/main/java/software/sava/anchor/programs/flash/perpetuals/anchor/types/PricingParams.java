package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PricingParams(long tradeSpreadMin,
                            long tradeSpreadMax,
                            long swapSpread,
                            int minInitLeverage,
                            int minInitDegenLeverage,
                            int maxInitLeverage,
                            int maxInitDegenLeverage,
                            int maxLeverage,
                            int maxDegenLeverage,
                            int minCollateralUsd,
                            int minDegenCollateralUsd,
                            long delaySeconds,
                            int maxUtilization,
                            int degenPositionFactor,
                            int degenExposureFactor,
                            long maxPositionLockedUsd,
                            long maxExposureUsd) implements Borsh {

  public static final int BYTES = 88;

  public static PricingParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var tradeSpreadMin = getInt64LE(_data, i);
    i += 8;
    final var tradeSpreadMax = getInt64LE(_data, i);
    i += 8;
    final var swapSpread = getInt64LE(_data, i);
    i += 8;
    final var minInitLeverage = getInt32LE(_data, i);
    i += 4;
    final var minInitDegenLeverage = getInt32LE(_data, i);
    i += 4;
    final var maxInitLeverage = getInt32LE(_data, i);
    i += 4;
    final var maxInitDegenLeverage = getInt32LE(_data, i);
    i += 4;
    final var maxLeverage = getInt32LE(_data, i);
    i += 4;
    final var maxDegenLeverage = getInt32LE(_data, i);
    i += 4;
    final var minCollateralUsd = getInt32LE(_data, i);
    i += 4;
    final var minDegenCollateralUsd = getInt32LE(_data, i);
    i += 4;
    final var delaySeconds = getInt64LE(_data, i);
    i += 8;
    final var maxUtilization = getInt32LE(_data, i);
    i += 4;
    final var degenPositionFactor = getInt16LE(_data, i);
    i += 2;
    final var degenExposureFactor = getInt16LE(_data, i);
    i += 2;
    final var maxPositionLockedUsd = getInt64LE(_data, i);
    i += 8;
    final var maxExposureUsd = getInt64LE(_data, i);
    return new PricingParams(tradeSpreadMin,
                             tradeSpreadMax,
                             swapSpread,
                             minInitLeverage,
                             minInitDegenLeverage,
                             maxInitLeverage,
                             maxInitDegenLeverage,
                             maxLeverage,
                             maxDegenLeverage,
                             minCollateralUsd,
                             minDegenCollateralUsd,
                             delaySeconds,
                             maxUtilization,
                             degenPositionFactor,
                             degenExposureFactor,
                             maxPositionLockedUsd,
                             maxExposureUsd);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, tradeSpreadMin);
    i += 8;
    putInt64LE(_data, i, tradeSpreadMax);
    i += 8;
    putInt64LE(_data, i, swapSpread);
    i += 8;
    putInt32LE(_data, i, minInitLeverage);
    i += 4;
    putInt32LE(_data, i, minInitDegenLeverage);
    i += 4;
    putInt32LE(_data, i, maxInitLeverage);
    i += 4;
    putInt32LE(_data, i, maxInitDegenLeverage);
    i += 4;
    putInt32LE(_data, i, maxLeverage);
    i += 4;
    putInt32LE(_data, i, maxDegenLeverage);
    i += 4;
    putInt32LE(_data, i, minCollateralUsd);
    i += 4;
    putInt32LE(_data, i, minDegenCollateralUsd);
    i += 4;
    putInt64LE(_data, i, delaySeconds);
    i += 8;
    putInt32LE(_data, i, maxUtilization);
    i += 4;
    putInt16LE(_data, i, degenPositionFactor);
    i += 2;
    putInt16LE(_data, i, degenExposureFactor);
    i += 2;
    putInt64LE(_data, i, maxPositionLockedUsd);
    i += 8;
    putInt64LE(_data, i, maxExposureUsd);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
