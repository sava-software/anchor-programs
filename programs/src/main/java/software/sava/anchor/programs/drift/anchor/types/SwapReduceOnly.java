package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum SwapReduceOnly implements Borsh.Enum {

  In,
  Out;

  public static SwapReduceOnly read(final byte[] _data, final int offset) {
    return Borsh.read(SwapReduceOnly.values(), _data, offset);
  }
}