package software.sava.anchor.programs.moonshot.anchor.types;

import software.sava.core.borsh.Borsh;

public enum Currency implements Borsh.Enum {

  Sol;

  public static Currency read(final byte[] _data, final int offset) {
    return Borsh.read(Currency.values(), _data, offset);
  }
}