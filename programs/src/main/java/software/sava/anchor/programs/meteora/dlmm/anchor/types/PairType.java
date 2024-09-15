package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

// Type of the Pair. 0 = Permissionless, 1 = Permission. Putting 0 as permissionless for backward compatibility.
public enum PairType implements Borsh.Enum {

  Permissionless,
  Permission;

  public static PairType read(final byte[] _data, final int offset) {
    return Borsh.read(PairType.values(), _data, offset);
  }
}