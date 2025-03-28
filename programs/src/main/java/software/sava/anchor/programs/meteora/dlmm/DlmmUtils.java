package software.sava.anchor.programs.meteora.dlmm;

import software.sava.anchor.programs.meteora.dlmm.anchor.types.LbPair;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.RemainingAccountsInfo;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.RemainingAccountsSlice;

import java.math.BigDecimal;
import java.math.MathContext;

import static software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmConstants.BIN_ARRAY_BITMAP_SIZE;
import static software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmConstants.MAX_BIN_PER_ARRAY;

public final class DlmmUtils {

  public static final RemainingAccountsInfo NO_REMAINING_ACCOUNTS = new RemainingAccountsInfo(new RemainingAccountsSlice[0]);

  private static final int BASIS_POINT_MAX_DECIMALS = 4;
  public static final int BASIS_POINT_MAX = 10_000;
  public static final int LOWER_BITMAP_BIN_ARRAY_INDEX = -BIN_ARRAY_BITMAP_SIZE;
  public static final int UPPER_BITMAP_BIN_ARRAY_INDEX = BIN_ARRAY_BITMAP_SIZE - 1;

  public static boolean isOverflowDefaultBinArrayBitmap(final int binArrayIndex) {
    return binArrayIndex > UPPER_BITMAP_BIN_ARRAY_INDEX || binArrayIndex < LOWER_BITMAP_BIN_ARRAY_INDEX;
  }

  public static boolean useExtension(final int minBinArrayIndex, final int maxBinArrayIndex) {
    return isOverflowDefaultBinArrayBitmap(minBinArrayIndex) || isOverflowDefaultBinArrayBitmap(maxBinArrayIndex);
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

  public static BigDecimal binStepBase(final int binStep) {
    final var binStepNum = BigDecimal.valueOf(binStep).movePointLeft(BASIS_POINT_MAX_DECIMALS);
    return BigDecimal.ONE.add(binStepNum);
  }

  public static double priceScaleFactor(final int scaleDifference) {
    return StrictMath.pow(10, scaleDifference);
  }

  public static double powTen(final double val, final int scaleDifference) {
    return val * priceScaleFactor(scaleDifference);
  }

  public static double binStepBaseDouble(final int binStep) {
    return Math.fma(binStep, 0.0001d, 1);
  }

  public static BigDecimal binStepBase(final LbPair lbPair) {
    return binStepBase(lbPair.binStep());
  }

  /// binStepBase = 1 + (stepSize * 0.0001)
  /// price = binStepBase ^ binId
  /// binId = ln(price) / ln(binStepBase)
  public static BigDecimal binPrice(final BigDecimal binStepBase,
                                    final int binId,
                                    final int scaleDifference,
                                    final MathContext mathContext) {
    return binStepBase.pow(binId, mathContext).movePointLeft(scaleDifference);
  }

  public static double binId(final BigDecimal price, final int scaleDifference, final BigDecimal binStepBase) {
    final var adjustedPrice = price.movePointRight(scaleDifference);
    return binId(adjustedPrice.doubleValue(), binStepBase.doubleValue());
  }

  public static double unscaledBinPrice(final double binStepBase, final int binId) {
    return StrictMath.pow(binStepBase, binId);
  }

  public static double binPrice(final double binStepBase, final int binId, final int scaleDifference) {
    return powTen(unscaledBinPrice(binStepBase, binId), -scaleDifference);
  }

  public static double binPrice(final double binStepBase, final int binId, final double scaleFactor) {
    return unscaledBinPrice(binStepBase, binId) * scaleFactor;
  }

  public static double binIdFromLogBinStepBase(final double unscaledPrice, final double logBinStepBase) {
    return StrictMath.log(unscaledPrice) / logBinStepBase;
  }

  public static double binId(final double unscaledPrice, final double binStepBase) {
    return binIdFromLogBinStepBase(unscaledPrice, StrictMath.log(binStepBase));
  }

  public static double binIdFromLogBinStepBase(final double price,
                                               final int scaleDifference,
                                               final double logBinStepBase) {
    return binIdFromLogBinStepBase(powTen(price, scaleDifference), logBinStepBase);
  }

  public static double binId(final double price, final int scaleDifference, final double binStepBase) {
    return binId(powTen(price, scaleDifference), binStepBase);
  }

  public static double binIdFromInverseLogBinStepBase(final double unscaledPrice,
                                                      final double inverseLogBinStepBase) {
    return StrictMath.log(unscaledPrice) * inverseLogBinStepBase;
  }

  public static double binIdFromInverseLogBinStepBase(final double price,
                                                      final int scaleDifference,
                                                      final double inverseLogBinStepBase) {
    return binIdFromInverseLogBinStepBase(powTen(price, scaleDifference), inverseLogBinStepBase);
  }

  public static double binIdFromLogBinStepBase(final double price,
                                               final double scaleFactor,
                                               final double logBinStepBase) {
    return binIdFromLogBinStepBase(price * scaleFactor, logBinStepBase);
  }

  public static double binId(final double price, final double scaleFactor, final double binStepBase) {
    return binId(price * scaleFactor, binStepBase);
  }

  public static double binIdFromInverseLogBinStepBase(final double price,
                                                      final double scaleFactor,
                                                      final double inverseLogBinStepBase) {
    return binIdFromInverseLogBinStepBase(price * scaleFactor, inverseLogBinStepBase);
  }

  private DlmmUtils() {
  }
}
