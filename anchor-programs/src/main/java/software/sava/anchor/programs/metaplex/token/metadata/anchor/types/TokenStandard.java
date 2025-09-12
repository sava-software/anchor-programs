package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.Borsh;

public enum TokenStandard implements Borsh.Enum {

  NonFungible,
  FungibleAsset,
  Fungible,
  NonFungibleEdition,
  ProgrammableNonFungible,
  ProgrammableNonFungibleEdition;

  public static TokenStandard read(final byte[] _data, final int offset) {
    return Borsh.read(TokenStandard.values(), _data, offset);
  }
}