package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

public enum MetadataTemplate implements Borsh.Enum {

  Openfunds;

  public static MetadataTemplate read(final byte[] _data, final int offset) {
    return Borsh.read(MetadataTemplate.values(), _data, offset);
  }
}