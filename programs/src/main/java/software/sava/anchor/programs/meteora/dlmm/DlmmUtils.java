package software.sava.anchor.programs.meteora.dlmm;

import software.sava.anchor.programs.meteora.dlmm.anchor.types.LbPair;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmConstants.BIN_ARRAY_BITMAP_SIZE;
import static software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmConstants.MAX_BIN_PER_ARRAY;

public final class DlmmUtils {

  private static final int BASIS_POINT_MAX_DECIMALS = 4;
  public static final int BASIS_POINT_MAX = 10_000;
  public static final int LOWER_BITMAP_BIN_ARRAY_INDEX = -BIN_ARRAY_BITMAP_SIZE;
  public static final int UPPER_BITMAP_BIN_ARRAY_INDEX = BIN_ARRAY_BITMAP_SIZE - 1;

  public static boolean isOverflowDefaultBinArrayBitmap(final int binArrayIndex) {
    return binArrayIndex > UPPER_BITMAP_BIN_ARRAY_INDEX || binArrayIndex < LOWER_BITMAP_BIN_ARRAY_INDEX;
  }

  public static boolean useExtension(final int minBinArrayIndex, final int maxBinArrayIndex) {
    return isOverflowDefaultBinArrayBitmap(minBinArrayIndex)
        || isOverflowDefaultBinArrayBitmap(maxBinArrayIndex);
  }

  public static int binIdToArrayIndex(final int binId) {
    final int idx = binId / (int) MAX_BIN_PER_ARRAY;
    return binId < 0 && (binId % MAX_BIN_PER_ARRAY) != 0 ? idx - 1 : idx;
  }

  public static int binIdToArrayUpperIndex(final int binId) {
    return binIdToArrayIndex(binId + (int) MAX_BIN_PER_ARRAY);
  }
  
  public static int scaleDifference(final int xTokenDecimals, final int yTokenDecimals) {
    return yTokenDecimals - xTokenDecimals;
  }

  public static MathContext mathContext(final int binId, final int quoteScale) {
    final int length = (int) Math.ceil(Math.log10(binId));
    return new MathContext(length + quoteScale, RoundingMode.DOWN);
  }

  public static BigDecimal binStepBase(final int binStep) {
    final var binStepNum = BigDecimal.valueOf(binStep).movePointLeft(BASIS_POINT_MAX_DECIMALS);
    return BigDecimal.ONE.add(binStepNum);
  }

  public static double powTen(final double val, final int scaleDifference) {
    return val * Math.pow(10, scaleDifference);
  }

  public static double binStepBaseDouble(final int binStep) {
    return 1 + (binStep * 0.0001);
  }

  public static BigDecimal binStepBase(final LbPair lbPair) {
    return binStepBase(lbPair.binStep());
  }

  /// p = (1 + (stepSize * 0.0001)) ^ binId
  /// binStepBase = 1 + (stepSize * 0.0001)
  public static BigDecimal binPrice(final BigDecimal binStepBase,
                                    final int binId,
                                    final int scaleDifference,
                                    final MathContext mathContext) {
    return binStepBase.pow(binId, mathContext).movePointLeft(scaleDifference);
  }

  public static double unscaledBinPrice(final double binStepBase, final int binId) {
    return Math.pow(binStepBase, binId);
  }

  public static double binPrice(final double binStepBase, final int binId, final int scaleDifference) {
    return powTen(unscaledBinPrice(binStepBase, binId), -scaleDifference);
  }

  public static double calculateBinId(final double unscaledPrice, final double binStepBase) {
    return Math.log(unscaledPrice) / Math.log(binStepBase);
  }

  public static double calculateBinId(final double price, final double binStepBase, final int scaleDifference) {
    return calculateBinId(powTen(price, scaleDifference), binStepBase);
  }

  public static double calculateBinId(final BigDecimal price, final BigDecimal binStepBase, final int scaleDifference) {
    final var adjustedPrice = price.movePointRight(scaleDifference);
    return calculateBinId(adjustedPrice.doubleValue(), binStepBase.doubleValue());
  }

  private DlmmUtils() {
  }
}
