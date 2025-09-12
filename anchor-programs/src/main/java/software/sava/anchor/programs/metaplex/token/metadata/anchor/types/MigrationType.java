package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.Borsh;

public enum MigrationType implements Borsh.Enum {

  CollectionV1,
  ProgrammableV1;

  public static MigrationType read(final byte[] _data, final int offset) {
    return Borsh.read(MigrationType.values(), _data, offset);
  }
}