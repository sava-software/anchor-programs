package software.sava.anchor.programs.kamino.lend.anchor.types;

import software.sava.core.borsh.Borsh;

public enum AssetTier implements Borsh.Enum {

  Regular,
  IsolatedCollateral,
  IsolatedDebt;

  public static AssetTier read(final byte[] _data, final int offset) {
    return Borsh.read(AssetTier.values(), _data, offset);
  }
}