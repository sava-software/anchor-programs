package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

// # Ripcord Flag
// - `0` (false): Feed's data provider is OK. Fund's data provider and accuracy is as expected.
// - `1` (true): Feed's data provider is flagging a pause. Data provider detected outliers,
// deviated thresholds, or operational issues. **DO NOT consume NAV data when ripcord=1.**
public enum ReportDataV9RipcordFlag implements Borsh.Enum {

  Normal,
  Paused;

  public static ReportDataV9RipcordFlag read(final byte[] _data, final int offset) {
    return Borsh.read(ReportDataV9RipcordFlag.values(), _data, offset);
  }
}