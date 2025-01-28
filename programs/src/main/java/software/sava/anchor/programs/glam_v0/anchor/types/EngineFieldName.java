package software.sava.anchor.programs.glam_v0.anchor.types;

import software.sava.core.borsh.Borsh;

public enum EngineFieldName implements Borsh.Enum {

  TimeCreated,
  IsEnabled,
  Assets,
  AssetsWeights,
  ShareClassAllowlist,
  ShareClassBlocklist,
  DelegateAcls,
  IntegrationAcls,
  ExternalVaultAccounts,
  LockUp,
  DriftMarketIndexesPerp,
  DriftMarketIndexesSpot,
  DriftOrderTypes;

  public static EngineFieldName read(final byte[] _data, final int offset) {
    return Borsh.read(EngineFieldName.values(), _data, offset);
  }
}
