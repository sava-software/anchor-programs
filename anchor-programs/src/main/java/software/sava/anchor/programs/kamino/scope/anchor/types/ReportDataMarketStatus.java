package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ReportDataMarketStatus implements Borsh.Enum {

  Unknown,
  Closed,
  Open;

  public static ReportDataMarketStatus read(final byte[] _data, final int offset) {
    return Borsh.read(ReportDataMarketStatus.values(), _data, offset);
  }
}