package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ReportDataV8MarketStatus implements Borsh.Enum {

  Unknown,
  Closed,
  Open;

  public static ReportDataV8MarketStatus read(final byte[] _data, final int offset) {
    return Borsh.read(ReportDataV8MarketStatus.values(), _data, offset);
  }
}