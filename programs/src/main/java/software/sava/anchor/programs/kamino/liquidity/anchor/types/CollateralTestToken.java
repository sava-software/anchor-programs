package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum CollateralTestToken implements Borsh.Enum {

  USDC,
  USDH,
  SOL,
  ETH,
  BTC,
  MSOL,
  STSOL,
  USDT,
  ORCA,
  MNDE,
  HBB,
  JSOL,
  USH,
  DAI,
  LDO,
  SCNSOL,
  UXD,
  HDG,
  DUST,
  USDR,
  RATIO,
  UXP,
  JITOSOL,
  RAY,
  BONK,
  SAMO,
  LaineSOL,
  BSOL;

  public static CollateralTestToken read(final byte[] _data, final int offset) {
    return Borsh.read(CollateralTestToken.values(), _data, offset);
  }
}