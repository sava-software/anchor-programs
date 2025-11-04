package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum LogMode implements Borsh.Enum {

  None,
  ExchangeOracle,
  MMOracle,
  SafeMMOracle,
  Margin;

  public static LogMode read(final byte[] _data, final int offset) {
    return Borsh.read(LogMode.values(), _data, offset);
  }
}