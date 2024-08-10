package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum SettlePnlExplanation implements Borsh.Enum {

  None,
  ExpiredPosition;

  public static SettlePnlExplanation read(final byte[] _data, final int offset) {
    return Borsh.read(SettlePnlExplanation.values(), _data, offset);
  }
}