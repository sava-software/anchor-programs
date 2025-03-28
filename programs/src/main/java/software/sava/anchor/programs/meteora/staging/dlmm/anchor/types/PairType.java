package software.sava.anchor.programs.meteora.staging.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

// Type of the Pair. 0 = Permissionless, 1 = Permission, 2 = CustomizablePermissionless. Putting 0 as permissionless for backward compatibility.
public enum PairType implements Borsh.Enum {

  Permissionless,
  Permission,
  CustomizablePermissionless,
  PermissionlessV2;

  public static PairType read(final byte[] _data, final int offset) {
    return Borsh.read(PairType.values(), _data, offset);
  }
}