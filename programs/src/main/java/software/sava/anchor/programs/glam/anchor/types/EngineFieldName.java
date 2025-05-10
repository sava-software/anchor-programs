package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

public enum EngineFieldName implements Borsh.Enum {

  Allowlist,
  Blocklist,
  ExternalVaultAccounts,
  LockUpPeriod,
  DriftMarketIndexesPerp,
  DriftMarketIndexesSpot,
  DriftOrderTypes,
  MaxSwapSlippageBps,
  TransferToAllowlist,
  PricedAssets,
  BaseAsset,
  MaxCap,
  MinSubscription,
  MinRedemption,
  NotifyAndSettle,
  Ledger,
  FeeStructure,
  FeeParams,
  ClaimableFees,
  ClaimedFees,
  SubscriptionPaused,
  RedemptionPaused,
  Owner,
  Enabled,
  Name,
  Uri,
  Assets,
  DelegateAcls,
  Integrations,
  UpdateTimelock,
  TimelockExpiresAt,
  DefaultAccountStateFrozen,
  PermanentDelegate,
  TimeUnit,
  KaminoLendingMarkets,
  MeteoraDlmmPools;

  public static EngineFieldName read(final byte[] _data, final int offset) {
    return Borsh.read(EngineFieldName.values(), _data, offset);
  }
}