package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.Borsh;

public enum HolderDelegateRole implements Borsh.Enum {

  PrintDelegate;

  public static HolderDelegateRole read(final byte[] _data, final int offset) {
    return Borsh.read(HolderDelegateRole.values(), _data, offset);
  }
}