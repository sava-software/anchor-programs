package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import software.sava.core.borsh.Borsh;

// Type of the Pair. 0 = Permissionless, 1 = Permission. Putting 0 as permissionless for backward compatibility.
public enum PoolType implements Borsh.Enum {

  Dlmm,
  DynamicPool;

  public static PoolType read(final byte[] _data, final int offset) {
    return Borsh.read(PoolType.values(), _data, offset);
  }
}