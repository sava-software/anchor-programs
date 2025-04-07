package software.sava.anchor.programs.jito.tip_router.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ConfigAdminRole implements Borsh.Enum {

  FeeAdmin,
  TieBreakerAdmin;

  public static ConfigAdminRole read(final byte[] _data, final int offset) {
    return Borsh.read(ConfigAdminRole.values(), _data, offset);
  }
}