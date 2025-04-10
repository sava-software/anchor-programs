package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

public enum EngineFieldName implements Borsh.Enum {

  Allowlist,
  Blocklist,
  ExternalVaultAccounts,
  LockUp,
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
  Valuation,
  Ledger,
  FeeStructure,
  FeeParams,
  ClaimableFees,
  ClaimedFees,
  SubscriptionPaused,
  RedemptionPaused;

  public static EngineFieldName read(final byte[] _data, final int offset) {
    return Borsh.read(EngineFieldName.values(), _data, offset);
  }
}