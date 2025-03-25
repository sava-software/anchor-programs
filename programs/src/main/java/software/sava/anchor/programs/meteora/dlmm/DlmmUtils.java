package software.sava.anchor.programs.meteora.dlmm;

import software.sava.anchor.programs.meteora.MeteoraAccounts;
import software.sava.anchor.programs.meteora.MeteoraPDAs;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.LbPair;
import software.sava.core.accounts.PublicKey;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmConstants.MAX_BIN_PER_ARRAY;

public final class DlmmUtils {

  private static final int BASIS_POINT_MAX_DECIMALS = 4;
  public static final int BASIS_POINT_MAX = 10_000;

  public static int binIdToArrayIndex(final int binId) {
    final int idx = binId / (int) MAX_BIN_PER_ARRAY;
    return binId < 0 && (binId % MAX_BIN_PER_ARRAY) != 0 ? idx - 1 : idx;
  }

  public static int binIdToArrayUpperIndex(final int binId) {
    return binIdToArrayIndex(binId + (int) MAX_BIN_PER_ARRAY);
  }

  public static void main(String[] args) {
    final int idx = binIdToArrayUpperIndex(17285);
    final var binArrayUpperKey = MeteoraPDAs.binArrayPdA(
        PublicKey.fromBase58Encoded("7ubS3GccjhQY99AYNKXjNJqnXjaokEdfdV915xnCb96r"),
        idx,
        MeteoraAccounts.MAIN_NET.dlmmProgram()
    );
    System.out.println(binArrayUpperKey);
  }

  public static MathContext mathContext(final int binId, final int quoteScale) {
    final int length = (int) Math.ceil(Math.log10(binId));
    return new MathContext(length + quoteScale, RoundingMode.DOWN);
  }

  public static BigDecimal binStepBase(final int binStep) {
    final var binStepNum = BigDecimal.valueOf(binStep).movePointLeft(BASIS_POINT_MAX_DECIMALS);
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
