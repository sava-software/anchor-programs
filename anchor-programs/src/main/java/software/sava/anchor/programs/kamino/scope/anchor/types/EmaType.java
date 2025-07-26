package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

public enum EmaType implements Borsh.Enum {

  Ema1h;

  public static EmaType read(final byte[] _data, final int offset) {
    return Borsh.read(EmaType.values(), _data, offset);
  }
}