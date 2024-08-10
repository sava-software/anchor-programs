package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum AssetTier implements Borsh.Enum {

  Collateral,
  Protected,
  Cross,
  Isolated,
  Unlisted;

  public static AssetTier read(final byte[] _data, final int offset) {
    return Borsh.read(AssetTier.values(), _data, offset);
  }
}