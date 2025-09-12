package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.Borsh;

public enum VerificationArgs implements Borsh.Enum {

  CreatorV1,
  CollectionV1;

  public static VerificationArgs read(final byte[] _data, final int offset) {
    return Borsh.read(VerificationArgs.values(), _data, offset);
  }
}