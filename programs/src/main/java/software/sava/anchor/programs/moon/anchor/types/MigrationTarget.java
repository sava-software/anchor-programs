package software.sava.anchor.programs.moon.anchor.types;

import software.sava.core.borsh.Borsh;

public enum MigrationTarget implements Borsh.Enum {

  Raydium,
  Meteora;

  public static MigrationTarget read(final byte[] _data, final int offset) {
    return Borsh.read(MigrationTarget.values(), _data, offset);
  }
}