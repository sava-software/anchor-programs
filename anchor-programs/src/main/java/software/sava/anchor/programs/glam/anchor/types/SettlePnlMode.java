package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

public enum SettlePnlMode implements Borsh.Enum {

  MustSettle,
  TrySettle;

  public static SettlePnlMode read(final byte[] _data, final int offset) {
    return Borsh.read(SettlePnlMode.values(), _data, offset);
  }
}