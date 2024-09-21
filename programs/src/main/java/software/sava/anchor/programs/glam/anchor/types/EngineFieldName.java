package software.sava.anchor.programs.glam.anchor.types;

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
  ExternalTreasuryAccounts,
  LockUp;

  public static EngineFieldName read(final byte[] _data, final int offset) {
    return Borsh.read(EngineFieldName.values(), _data, offset);
  }
}