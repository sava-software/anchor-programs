package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

public enum OracleType implements Borsh.Enum {

  Pyth,
  DeprecatedPlaceholder1,
  SwitchboardV2,
  DeprecatedPlaceholder2,
  CToken,
  SplStake,
  KToken,
  PythEMA,
  MsolStake,
  KTokenToTokenA,
  KTokenToTokenB,
  JupiterLpFetch,
  ScopeTwap,
  OrcaWhirlpoolAtoB,
  OrcaWhirlpoolBtoA,
  RaydiumAmmV3AtoB,
  RaydiumAmmV3BtoA,
  JupiterLpCompute,
  MeteoraDlmmAtoB,
  MeteoraDlmmBtoA,
  JupiterLpScope,
  PythPull,
  PythPullEMA,
  FixedPrice,
  SwitchboardOnDemand,
  JitoRestaking,
  Chainlink,
  DiscountToMaturity,
  MostRecentOf,
  PythLazer,
  RedStone,
  AdrenaLp,
  Securitize,
  CappedFloored,
  ChainlinkRWA,
  ChainlinkNAV,
  FlashtradeLp,
  ChainlinkX,
  ChainlinkExchangeRate;

  public static OracleType read(final byte[] _data, final int offset) {
    return Borsh.read(OracleType.values(), _data, offset);
  }
}