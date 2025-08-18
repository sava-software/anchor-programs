package software.sava.anchor.programs.marginfi.v2.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

// Unique per-group. Staked Collateral banks created under a group automatically use these
// settings. Groups that have not created this struct cannot create staked collateral banks. When
// this struct updates, changes must be permissionlessly propogated to staked collateral banks.
// Administrators can also edit the bank manually, i.e. with configure_bank, to temporarily make
// changes such as raising the deposit limit for a single bank.
public record StakedSettings(PublicKey _address,
                             Discriminator discriminator,
                             // This account's own key. A PDA derived from `marginfi_group` and `STAKED_SETTINGS_SEED`
                             PublicKey key,
                             // Group for which these settings apply
                             PublicKey marginfiGroup,
                             // Generally, the Pyth push oracle for SOL
                             PublicKey oracle,
                             WrappedI80F48 assetWeightInit,
                             WrappedI80F48 assetWeightMaint,
                             long depositLimit,
                             long totalAssetValueInitLimit,
                             int oracleMaxAge,
                             RiskTier riskTier,
                             byte[] pad0,
                             // The following values are irrelevant because staked collateral positions do not support
                             // borrowing.
                             byte[] reserved0,
                             byte[] reserved1,
                             byte[] reserved2) implements Borsh {

  public static final int BYTES = 264;
  public static final int PAD_0_LEN = 5;
  public static final int RESERVED_0_LEN = 8;
  public static final int RESERVED_1_LEN = 32;
  public static final int RESERVED_2_LEN = 64;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(157, 140, 6, 77, 89, 173, 173, 125);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int KEY_OFFSET = 8;
  public static final int MARGINFI_GROUP_OFFSET = 40;
  public static final int ORACLE_OFFSET = 72;
  public static final int ASSET_WEIGHT_INIT_OFFSET = 104;
  public static final int ASSET_WEIGHT_MAINT_OFFSET = 120;
  public static final int DEPOSIT_LIMIT_OFFSET = 136;
  public static final int TOTAL_ASSET_VALUE_INIT_LIMIT_OFFSET = 144;
  public static final int ORACLE_MAX_AGE_OFFSET = 152;
  public static final int RISK_TIER_OFFSET = 154;
  public static final int PAD_0_OFFSET = 155;
  public static final int RESERVED_0_OFFSET = 160;
  public static final int RESERVED_1_OFFSET = 168;
  public static final int RESERVED_2_OFFSET = 200;

  public static Filter createKeyFilter(final PublicKey key) {
    return Filter.createMemCompFilter(KEY_OFFSET, key);
  }

  public static Filter createMarginfiGroupFilter(final PublicKey marginfiGroup) {
    return Filter.createMemCompFilter(MARGINFI_GROUP_OFFSET, marginfiGroup);
  }

  public static Filter createOracleFilter(final PublicKey oracle) {
    return Filter.createMemCompFilter(ORACLE_OFFSET, oracle);
  }

  public static Filter createAssetWeightInitFilter(final WrappedI80F48 assetWeightInit) {
    return Filter.createMemCompFilter(ASSET_WEIGHT_INIT_OFFSET, assetWeightInit.write());
  }

  public static Filter createAssetWeightMaintFilter(final WrappedI80F48 assetWeightMaint) {
    return Filter.createMemCompFilter(ASSET_WEIGHT_MAINT_OFFSET, assetWeightMaint.write());
  }

  public static Filter createDepositLimitFilter(final long depositLimit) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, depositLimit);
    return Filter.createMemCompFilter(DEPOSIT_LIMIT_OFFSET, _data);
  }

  public static Filter createTotalAssetValueInitLimitFilter(final long totalAssetValueInitLimit) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalAssetValueInitLimit);
    return Filter.createMemCompFilter(TOTAL_ASSET_VALUE_INIT_LIMIT_OFFSET, _data);
  }

  public static Filter createOracleMaxAgeFilter(final int oracleMaxAge) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, oracleMaxAge);
    return Filter.createMemCompFilter(ORACLE_MAX_AGE_OFFSET, _data);
  }

  public static Filter createRiskTierFilter(final RiskTier riskTier) {
    return Filter.createMemCompFilter(RISK_TIER_OFFSET, riskTier.write());
  }

  public static StakedSettings read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static StakedSettings read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static StakedSettings read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], StakedSettings> FACTORY = StakedSettings::read;

  public static StakedSettings read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var key = readPubKey(_data, i);
    i += 32;
    final var marginfiGroup = readPubKey(_data, i);
    i += 32;
    final var oracle = readPubKey(_data, i);
    i += 32;
    final var assetWeightInit = WrappedI80F48.read(_data, i);
    i += Borsh.len(assetWeightInit);
    final var assetWeightMaint = WrappedI80F48.read(_data, i);
    i += Borsh.len(assetWeightMaint);
    final var depositLimit = getInt64LE(_data, i);
    i += 8;
    final var totalAssetValueInitLimit = getInt64LE(_data, i);
    i += 8;
    final var oracleMaxAge = getInt16LE(_data, i);
    i += 2;
    final var riskTier = RiskTier.read(_data, i);
    i += Borsh.len(riskTier);
    final var pad0 = new byte[5];
    i += Borsh.readArray(pad0, _data, i);
    final var reserved0 = new byte[8];
    i += Borsh.readArray(reserved0, _data, i);
    final var reserved1 = new byte[32];
    i += Borsh.readArray(reserved1, _data, i);
    final var reserved2 = new byte[64];
    Borsh.readArray(reserved2, _data, i);
    return new StakedSettings(_address,
                              discriminator,
                              key,
                              marginfiGroup,
                              oracle,
                              assetWeightInit,
                              assetWeightMaint,
                              depositLimit,
                              totalAssetValueInitLimit,
                              oracleMaxAge,
                              riskTier,
                              pad0,
                              reserved0,
                              reserved1,
                              reserved2);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    key.write(_data, i);
    i += 32;
    marginfiGroup.write(_data, i);
    i += 32;
    oracle.write(_data, i);
    i += 32;
    i += Borsh.write(assetWeightInit, _data, i);
    i += Borsh.write(assetWeightMaint, _data, i);
    putInt64LE(_data, i, depositLimit);
    i += 8;
    putInt64LE(_data, i, totalAssetValueInitLimit);
    i += 8;
    putInt16LE(_data, i, oracleMaxAge);
    i += 2;
    i += Borsh.write(riskTier, _data, i);
    i += Borsh.writeArray(pad0, _data, i);
    i += Borsh.writeArray(reserved0, _data, i);
    i += Borsh.writeArray(reserved1, _data, i);
    i += Borsh.writeArray(reserved2, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
