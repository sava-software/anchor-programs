package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

// Pair status. 0 = Enabled, 1 = Disabled. Putting 0 as enabled for backward compatibility.
public enum PairStatus implements Borsh.Enum {

  Enabled,
  Disabled;

  public static PairStatus read(final byte[] _data, final int offset) {
    return Borsh.read(PairStatus.values(), _data, offset);
  }
}