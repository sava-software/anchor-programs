package software.sava.anchor.programs.kamino.farms.anchor.types;

import software.sava.core.borsh.Borsh;

public enum GlobalConfigOption implements Borsh.Enum {

  SetPendingGlobalAdmin,
  SetTreasuryFeeBps;

  public static GlobalConfigOption read(final byte[] _data, final int offset) {
    return Borsh.read(GlobalConfigOption.values(), _data, offset);
  }
}