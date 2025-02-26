package software.sava.anchor.programs.metadao.conditional_vault.anchor.types;

import software.sava.core.borsh.Borsh;

public enum VaultStatus implements Borsh.Enum {

  Active,
  Finalized,
  Reverted;

  public static VaultStatus read(final byte[] _data, final int offset) {
    return Borsh.read(VaultStatus.values(), _data, offset);
  }
}