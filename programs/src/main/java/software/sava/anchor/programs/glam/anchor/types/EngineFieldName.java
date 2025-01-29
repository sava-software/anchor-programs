package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

public enum EngineFieldName implements Borsh.Enum {

  ShareClassAllowlist,
  ShareClassBlocklist,
  ExternalVaultAccounts,
  LockUp,
  DriftMarketIndexesPerp,
  DriftMarketIndexesSpot,
  DriftOrderTypes;

  public static EngineFieldName read(final byte[] _data, final int offset) {
    return Borsh.read(EngineFieldName.values(), _data, offset);
  }
}