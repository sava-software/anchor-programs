package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

public enum UpdateTokenMetadataMode implements Borsh.Enum {

  Name,
  MaxPriceAgeSlots,
  GroupIds;

  public static UpdateTokenMetadataMode read(final byte[] _data, final int offset) {
    return Borsh.read(UpdateTokenMetadataMode.values(), _data, offset);
  }
}