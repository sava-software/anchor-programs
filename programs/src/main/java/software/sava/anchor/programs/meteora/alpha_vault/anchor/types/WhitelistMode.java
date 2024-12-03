package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import software.sava.core.borsh.Borsh;

public enum WhitelistMode implements Borsh.Enum {

  Permissionless,
  PermissionWithMerkleProof,
  PermissionWithAuthority;

  public static WhitelistMode read(final byte[] _data, final int offset) {
    return Borsh.read(WhitelistMode.values(), _data, offset);
  }
}