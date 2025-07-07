package software.sava.anchor.programs.drift.vaults.anchor.types;

import software.sava.core.borsh.Borsh;

public enum VaultClass implements Borsh.Enum {

  Normal,
  Trusted;

  public static VaultClass read(final byte[] _data, final int offset) {
    return Borsh.read(VaultClass.values(), _data, offset);
  }
}