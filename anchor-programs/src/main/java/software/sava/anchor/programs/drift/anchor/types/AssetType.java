package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum AssetType implements Borsh.Enum {

  Base,
  Quote;

  public static AssetType read(final byte[] _data, final int offset) {
    return Borsh.read(AssetType.values(), _data, offset);
  }
}