package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ScopePriceIdTest implements Borsh.Enum {

  SOL,
  ETH,
  BTC,
  SRM,
  RAY,
  FTT,
  MSOL,
  ScnSOLSOL,
  BNB,
  AVAX,
  DaoSOLSOL,
  SaberMSOLSOL,
  USDH,
  StSOL,
  CSOLSOL,
  CETHETH,
  CBTCBTC,
  CMSOLSOL,
  WstETH,
  LDO,
  USDC,
  CUSDCUSDC,
  USDT,
  ORCA,
  MNDE,
  HBB,
  CORCAORCA,
  CSLNDSLND,
  CSRMSRM,
  CRAYRAY,
  CFTTFTT,
  CSTSOLSTSOL,
  SLND,
  DAI,
  JSOLSOL,
  USH,
  UXD,
  USDHTWAP,
  USHTWAP,
  UXDTWAP,
  HDG,
  DUST,
  USDR,
  USDRTWAP,
  RATIO,
  UXP,
  KUXDUSDCORCA,
  JITOSOLSOL,
  SOLEMA,
  ETHEMA,
  BTCEMA,
  SRMEMA,
  RAYEMA,
  FTTEMA,
  MSOLEMA,
  BNBEMA,
  AVAXEMA,
  STSOLEMA,
  USDCEMA,
  USDTEMA,
  SLNDEMA,
  DAIEMA,
  WstETHTWAP,
  DUSTTWAP,
  BONK,
  BONKTWAP,
  SAMO,
  SAMOTWAP,
  BSOL,
  LaineSOL;

  public static ScopePriceIdTest read(final byte[] _data, final int offset) {
    return Borsh.read(ScopePriceIdTest.values(), _data, offset);
  }
}