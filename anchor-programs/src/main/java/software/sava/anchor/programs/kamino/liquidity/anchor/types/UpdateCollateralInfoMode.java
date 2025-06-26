package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum UpdateCollateralInfoMode implements Borsh.Enum {

  CollateralId,
  LowerHeuristic,
  UpperHeuristic,
  ExpHeuristic,
  TwapDivergence,
  UpdateScopeTwap,
  UpdateScopeChain,
  UpdateName,
  UpdatePriceMaxAge,
  UpdateTwapMaxAge,
  UpdateDisabled,
  UpdateStakingRateChain,
  UpdateMaxIgnorableAmountAsReward;

  public static UpdateCollateralInfoMode read(final byte[] _data, final int offset) {
    return Borsh.read(UpdateCollateralInfoMode.values(), _data, offset);
  }
}