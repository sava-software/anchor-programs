package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum SwapDirection implements Borsh.Enum {

  Add,
  Remove;

  public static SwapDirection read(final byte[] _data, final int offset) {
    return Borsh.read(SwapDirection.values(), _data, offset);
  }
}