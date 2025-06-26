package software.sava.anchor.programs.jupiter.merkle.distributor.anchor.types;

import software.sava.core.borsh.Borsh;

// Type of the activation
public enum ClaimType implements Borsh.Enum {

  Permissionless,
  Permissioned,
  PermissionlessWithStaking,
  PermissionedWithStaking;

  public static ClaimType read(final byte[] _data, final int offset) {
    return Borsh.read(ClaimType.values(), _data, offset);
  }
}