package software.sava.anchor.programs.glam.mint.anchor.types;

import software.sava.core.borsh.Borsh;

public enum EngineFieldName implements Borsh.Enum {

  Owner,
  PortfolioManagerName,
  Name,
  Uri,
  Assets,
  DelegateAcls,
  IntegrationAcls,
  TimelockDuration,
  Borrowable,
  DefaultAccountStateFrozen,
  PermanentDelegate,
  NotifyAndSettle,
  FeeStructure,
  FeeParams,
  ClaimableFees,
  ClaimedFees;

  public static EngineFieldName read(final byte[] _data, final int offset) {
    return Borsh.read(EngineFieldName.values(), _data, offset);
  }
}