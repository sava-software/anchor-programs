package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.Borsh;

public enum AuthorityType implements Borsh.Enum {

  None,
  Metadata,
  Holder,
  MetadataDelegate,
  TokenDelegate;

  public static AuthorityType read(final byte[] _data, final int offset) {
    return Borsh.read(AuthorityType.values(), _data, offset);
  }
}