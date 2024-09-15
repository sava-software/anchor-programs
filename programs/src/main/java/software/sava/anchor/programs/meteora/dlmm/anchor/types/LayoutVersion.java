package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

// Layout version
public enum LayoutVersion implements Borsh.Enum {

  V0,
  V1;

  public static LayoutVersion read(final byte[] _data, final int offset) {
    return Borsh.read(LayoutVersion.values(), _data, offset);
  }
}