package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum OracleValidity implements Borsh.Enum {

  NonPositive,
  TooVolatile,
  TooUncertain,
  StaleForMargin,
  InsufficientDataPoints,
  StaleForAMM,
  Valid;

  public static OracleValidity read(final byte[] _data, final int offset) {
    return Borsh.read(OracleValidity.values(), _data, offset);
  }
}