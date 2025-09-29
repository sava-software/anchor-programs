package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// TODO: Convert weights to (u64, u64) to avoid precision loss (maybe?)
public record BankConfig(WrappedI80F48 assetWeightInit,
                         WrappedI80F48 assetWeightMaint,
                         WrappedI80F48 liabilityWeightInit,
                         WrappedI80F48 liabilityWeightMaint,
                         long depositLimit,
                         InterestRateConfig interestRateConfig,
                         BankOperationalState operationalState,
                         OracleSetup oracleSetup,
                         PublicKey[] oracleKeys,
                         byte[] pad0,
                         long borrowLimit,
                         RiskTier riskTier,
                         // Determines what kinds of assets users of this bank can interact with.
                         // Options:
                         // * ASSET_TAG_DEFAULT (0) - A regular asset that can be comingled with any other regular asset
                         // or with `ASSET_TAG_SOL`
                         // * ASSET_TAG_SOL (1) - Accounts with a SOL position can comingle with **either**
                         // `ASSET_TAG_DEFAULT` or `ASSET_TAG_STAKED` positions, but not both
                         // * ASSET_TAG_STAKED (2) - Staked SOL assets. Accounts with a STAKED position can only deposit
                         // other STAKED assets or SOL (`ASSET_TAG_SOL`) and can only borrow SOL
                         int assetTag,
                         // Flags for various config options
                         // * 1 - Always set if bank created in 0.1.4 or later, or if migrated to the new pyth
                         // oracle setup from a prior version. Not set in 0.1.3 or earlier banks using pyth that have
                         // not yet migrated. Does nothing for banks that use switchboard.
                         // * 2, 4, 8, 16, etc - reserved for future use.
                         int configFlags,
                         byte[] pad1,
                         // USD denominated limit for calculating asset value for initialization margin requirements.
                         // Example, if total SOL deposits are equal to $1M and the limit it set to $500K,
                         // then SOL assets will be discounted by 50%.
                         // 
                         // In other words the max value of liabilities that can be backed by the asset is $500K.
                         // This is useful for limiting the damage of orcale attacks.
                         // 
                         // Value is UI USD value, for example value 100 -> $100
                         long totalAssetValueInitLimit,
                         // Time window in seconds for the oracle price feed to be considered live.
                         int oracleMaxAge,
                         byte[] padding0,
                         // From 0-100%, if the confidence exceeds this value, the oracle is considered invalid. Note:
                         // the confidence adjustment is capped at 5% regardless of this value.
                         // * 0 falls back to using the default 10% instead, i.e., U32_MAX_DIV_10
                         // * A %, as u32, e.g. 100% = u32::MAX, 50% = u32::MAX/2, etc.
                         int oracleMaxConfidence,
                         byte[] padding1) implements Borsh {

  public static final int BYTES = 544;
  public static final int ORACLE_KEYS_LEN = 5;
  public static final int PAD_0_LEN = 6;
  public static final int PAD_1_LEN = 5;
  public static final int PADDING_0_LEN = 2;
  public static final int PADDING_1_LEN = 32;

  public static BankConfig read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var assetWeightInit = WrappedI80F48.read(_data, i);
    i += Borsh.len(assetWeightInit);
    final var assetWeightMaint = WrappedI80F48.read(_data, i);
    i += Borsh.len(assetWeightMaint);
    final var liabilityWeightInit = WrappedI80F48.read(_data, i);
    i += Borsh.len(liabilityWeightInit);
    final var liabilityWeightMaint = WrappedI80F48.read(_data, i);
    i += Borsh.len(liabilityWeightMaint);
    final var depositLimit = getInt64LE(_data, i);
    i += 8;
    final var interestRateConfig = InterestRateConfig.read(_data, i);
    i += Borsh.len(interestRateConfig);
    final var operationalState = BankOperationalState.read(_data, i);
    i += Borsh.len(operationalState);
    final var oracleSetup = OracleSetup.read(_data, i);
    i += Borsh.len(oracleSetup);
    final var oracleKeys = new PublicKey[5];
    i += Borsh.readArray(oracleKeys, _data, i);
    final var pad0 = new byte[6];
    i += Borsh.readArray(pad0, _data, i);
    final var borrowLimit = getInt64LE(_data, i);
    i += 8;
    final var riskTier = RiskTier.read(_data, i);
    i += Borsh.len(riskTier);
    final var assetTag = _data[i] & 0xFF;
    ++i;
    final var configFlags = _data[i] & 0xFF;
    ++i;
    final var pad1 = new byte[5];
    i += Borsh.readArray(pad1, _data, i);
    final var totalAssetValueInitLimit = getInt64LE(_data, i);
    i += 8;
    final var oracleMaxAge = getInt16LE(_data, i);
    i += 2;
    final var padding0 = new byte[2];
    i += Borsh.readArray(padding0, _data, i);
    final var oracleMaxConfidence = getInt32LE(_data, i);
    i += 4;
    final var padding1 = new byte[32];
    Borsh.readArray(padding1, _data, i);
    return new BankConfig(assetWeightInit,
                          assetWeightMaint,
                          liabilityWeightInit,
                          liabilityWeightMaint,
                          depositLimit,
                          interestRateConfig,
                          operationalState,
                          oracleSetup,
                          oracleKeys,
                          pad0,
                          borrowLimit,
                          riskTier,
                          assetTag,
                          configFlags,
                          pad1,
                          totalAssetValueInitLimit,
                          oracleMaxAge,
                          padding0,
                          oracleMaxConfidence,
                          padding1);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(assetWeightInit, _data, i);
    i += Borsh.write(assetWeightMaint, _data, i);
    i += Borsh.write(liabilityWeightInit, _data, i);
    i += Borsh.write(liabilityWeightMaint, _data, i);
    putInt64LE(_data, i, depositLimit);
    i += 8;
    i += Borsh.write(interestRateConfig, _data, i);
    i += Borsh.write(operationalState, _data, i);
    i += Borsh.write(oracleSetup, _data, i);
    i += Borsh.writeArrayChecked(oracleKeys, 5, _data, i);
    i += Borsh.writeArrayChecked(pad0, 6, _data, i);
    putInt64LE(_data, i, borrowLimit);
    i += 8;
    i += Borsh.write(riskTier, _data, i);
    _data[i] = (byte) assetTag;
    ++i;
    _data[i] = (byte) configFlags;
    ++i;
    i += Borsh.writeArrayChecked(pad1, 5, _data, i);
    putInt64LE(_data, i, totalAssetValueInitLimit);
    i += 8;
    putInt16LE(_data, i, oracleMaxAge);
    i += 2;
    i += Borsh.writeArrayChecked(padding0, 2, _data, i);
    putInt32LE(_data, i, oracleMaxConfidence);
    i += 4;
    i += Borsh.writeArrayChecked(padding1, 32, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
