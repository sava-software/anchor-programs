package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.Borsh;

public enum MetadataDelegateRole implements Borsh.Enum {

  AuthorityItem,
  Collection,
  Use,
  Data,
  ProgrammableConfig,
  DataItem,
  CollectionItem,
  ProgrammableConfigItem;

  public static MetadataDelegateRole read(final byte[] _data, final int offset) {
    return Borsh.read(MetadataDelegateRole.values(), _data, offset);
  }
}