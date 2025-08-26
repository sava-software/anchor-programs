package software.sava.anchor.programs.glam.protocol.anchor.types;

import software.sava.core.borsh.Borsh;

public enum OracleSource implements Borsh.Enum {

  Pyth,
  Switchboard,
  QuoteAsset,
  Pyth1K,
  Pyth1M,
  PythStableCoin,
  Prelaunch,
  PythPull,
  Pyth1KPull,
  Pyth1MPull,
  PythStableCoinPull,
  SwitchboardOnDemand,
  PythLazer,
  PythLazer1K,
  PythLazer1M,
  PythLazerStableCoin,
  LstPoolState,
  MarinadeState,
  BaseAsset;

  public static OracleSource read(final byte[] _data, final int offset) {
    return Borsh.read(OracleSource.values(), _data, offset);
  }
}