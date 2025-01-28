package software.sava.anchor.programs.glam_v0.anchor.types;

import software.sava.core.borsh.Borsh;

public enum IntegrationFeature implements Borsh.Enum {

  All;

  public static IntegrationFeature read(final byte[] _data, final int offset) {
    return Borsh.read(IntegrationFeature.values(), _data, offset);
  }
}
