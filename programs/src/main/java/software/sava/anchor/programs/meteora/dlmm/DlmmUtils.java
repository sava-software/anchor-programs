package software.sava.anchor.programs.meteora.dlmm;

import software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmConstants;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.LbPair;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public final class DlmmUtils {

  private static final int BASIS_POINT_MAX = 4;

  public static int bidIdToArrayIndex(final int binId) {
    final int idx = binId / (int) LbClmmConstants.MAX_BIN_PER_ARRAY;
    return binId < 0 && (binId % LbClmmConstants.MAX_BIN_PER_ARRAY) != 0 ? idx - 1 : idx;
  }

  public static MathContext mathContext(final int binId, final int quoteScale) {
    final int length = (int) Math.ceil(Math.log10(binId));
    return new MathContext(length + quoteScale, RoundingMode.DOWN);
  }

  public static BigDecimal binStepBase(final int stepSize) {
    final var binStepNum = BigDecimal.valueOf(stepSize).movePointLeft(BASIS_POINT_MAX);
    return BigDecimal.ONE.add(binStepNum);
  }

  public static BigDecimal binStepBase(final LbPair lbPair) {
    return binStepBase(lbPair.binStep());
  }

  public static BigDecimal binPrice(final BigDecimal binStepBase,
                                    final int binId,
                                    final int scaleDifference,
                                    final MathContext mathContext) {
    return binStepBase.pow(binId, mathContext).movePointLeft(scaleDifference);
  }

  private DlmmUtils() {
  }
}
