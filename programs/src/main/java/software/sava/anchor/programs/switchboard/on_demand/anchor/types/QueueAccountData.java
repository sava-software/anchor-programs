package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

// An Queue represents a round-robin queue of oracle oracles who attest on-chain
// whether a Switchboard Function was executed within an enclave against an expected set of
// enclave measurements.
// 
// For an oracle to join the queue, the oracle must first submit their enclave quote on-chain and
// wait for an existing oracle to attest their quote. If the oracle's quote matches an expected
// measurement within the queues mr_enclaves config, it is granted permissions and will start
// being assigned update requests.
public record QueueAccountData(PublicKey _address,
                               Discriminator discriminator,
                               // The address of the authority which is permitted to add/remove allowed enclave measurements.
                               PublicKey authority,
                               // Allowed enclave measurements.
                               byte[][] mrEnclaves,
                               // The addresses of the quote oracles who have a valid
                               // verification status and have heartbeated on-chain recently.
                               PublicKey[] oracleKeys,
                               // The maximum allowable time until a EnclaveAccount needs to be re-verified on-chain.
                               long maxQuoteVerificationAge,
                               // The unix timestamp when the last quote oracle heartbeated on-chain.
                               long lastHeartbeat,
                               long nodeTimeout,
                               // The minimum number of lamports a quote oracle needs to lock-up in order to heartbeat and verify other quotes.
                               long oracleMinStake,
                               long allowAuthorityOverrideAfter,
                               // The number of allowed enclave measurements.
                               int mrEnclavesLen,
                               // The length of valid quote oracles for the given attestation queue.
                               int oracleKeysLen,
                               // The reward paid to quote oracles for attesting on-chain.
                               int reward,
                               // Incrementer used to track the current quote oracle permitted to run any available functions.
                               int currIdx,
                               // Incrementer used to garbage collect and remove stale quote oracles.
                               int gcIdx,
                               int requireAuthorityHeartbeatPermission,
                               int requireAuthorityVerifyPermission,
                               int requireUsagePermissions,
                               int signerBump,
                               PublicKey mint,
                               long lutSlot,
                               int allowSubsidies,
                               // Reserved.
                               byte[] ebuf6,
                               byte[] ebuf5,
                               byte[] ebuf4,
                               byte[] ebuf3,
                               byte[] ebuf2,
                               byte[] ebuf1) implements Borsh {

  public static final int BYTES = 6280;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(217, 194, 55, 127, 184, 83, 138, 1);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int AUTHORITY_OFFSET = 8;
  public static final int MR_ENCLAVES_OFFSET = 40;
  public static final int ORACLE_KEYS_OFFSET = 1064;
  public static final int MAX_QUOTE_VERIFICATION_AGE_OFFSET = 5160;
  public static final int LAST_HEARTBEAT_OFFSET = 5168;
  public static final int NODE_TIMEOUT_OFFSET = 5176;
  public static final int ORACLE_MIN_STAKE_OFFSET = 5184;
  public static final int ALLOW_AUTHORITY_OVERRIDE_AFTER_OFFSET = 5192;
  public static final int MR_ENCLAVES_LEN_OFFSET = 5200;
  public static final int ORACLE_KEYS_LEN_OFFSET = 5204;
  public static final int REWARD_OFFSET = 5208;
  public static final int CURR_IDX_OFFSET = 5212;
  public static final int GC_IDX_OFFSET = 5216;
  public static final int REQUIRE_AUTHORITY_HEARTBEAT_PERMISSION_OFFSET = 5220;
  public static final int REQUIRE_AUTHORITY_VERIFY_PERMISSION_OFFSET = 5221;
  public static final int REQUIRE_USAGE_PERMISSIONS_OFFSET = 5222;
  public static final int SIGNER_BUMP_OFFSET = 5223;
  public static final int MINT_OFFSET = 5224;
  public static final int LUT_SLOT_OFFSET = 5256;
  public static final int ALLOW_SUBSIDIES_OFFSET = 5264;
  public static final int EBUF6_OFFSET = 5265;
  public static final int EBUF5_OFFSET = 5288;
  public static final int EBUF4_OFFSET = 5320;
  public static final int EBUF3_OFFSET = 5384;
  public static final int EBUF2_OFFSET = 5512;
  public static final int EBUF1_OFFSET = 5768;

  public static Filter createAuthorityFilter(final PublicKey authority) {
    return Filter.createMemCompFilter(AUTHORITY_OFFSET, authority);
  }

  public static Filter createMaxQuoteVerificationAgeFilter(final long maxQuoteVerificationAge) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxQuoteVerificationAge);
    return Filter.createMemCompFilter(MAX_QUOTE_VERIFICATION_AGE_OFFSET, _data);
  }

  public static Filter createLastHeartbeatFilter(final long lastHeartbeat) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastHeartbeat);
    return Filter.createMemCompFilter(LAST_HEARTBEAT_OFFSET, _data);
  }

  public static Filter createNodeTimeoutFilter(final long nodeTimeout) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, nodeTimeout);
    return Filter.createMemCompFilter(NODE_TIMEOUT_OFFSET, _data);
  }

  public static Filter createOracleMinStakeFilter(final long oracleMinStake) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, oracleMinStake);
    return Filter.createMemCompFilter(ORACLE_MIN_STAKE_OFFSET, _data);
  }

  public static Filter createAllowAuthorityOverrideAfterFilter(final long allowAuthorityOverrideAfter) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, allowAuthorityOverrideAfter);
    return Filter.createMemCompFilter(ALLOW_AUTHORITY_OVERRIDE_AFTER_OFFSET, _data);
  }

  public static Filter createMrEnclavesLenFilter(final int mrEnclavesLen) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, mrEnclavesLen);
    return Filter.createMemCompFilter(MR_ENCLAVES_LEN_OFFSET, _data);
  }

  public static Filter createOracleKeysLenFilter(final int oracleKeysLen) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, oracleKeysLen);
    return Filter.createMemCompFilter(ORACLE_KEYS_LEN_OFFSET, _data);
  }

  public static Filter createRewardFilter(final int reward) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, reward);
    return Filter.createMemCompFilter(REWARD_OFFSET, _data);
  }

  public static Filter createCurrIdxFilter(final int currIdx) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, currIdx);
    return Filter.createMemCompFilter(CURR_IDX_OFFSET, _data);
  }

  public static Filter createGcIdxFilter(final int gcIdx) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, gcIdx);
    return Filter.createMemCompFilter(GC_IDX_OFFSET, _data);
  }

  public static Filter createRequireAuthorityHeartbeatPermissionFilter(final int requireAuthorityHeartbeatPermission) {
    return Filter.createMemCompFilter(REQUIRE_AUTHORITY_HEARTBEAT_PERMISSION_OFFSET, new byte[]{(byte) requireAuthorityHeartbeatPermission});
  }

  public static Filter createRequireAuthorityVerifyPermissionFilter(final int requireAuthorityVerifyPermission) {
    return Filter.createMemCompFilter(REQUIRE_AUTHORITY_VERIFY_PERMISSION_OFFSET, new byte[]{(byte) requireAuthorityVerifyPermission});
  }

  public static Filter createRequireUsagePermissionsFilter(final int requireUsagePermissions) {
    return Filter.createMemCompFilter(REQUIRE_USAGE_PERMISSIONS_OFFSET, new byte[]{(byte) requireUsagePermissions});
  }

  public static Filter createSignerBumpFilter(final int signerBump) {
    return Filter.createMemCompFilter(SIGNER_BUMP_OFFSET, new byte[]{(byte) signerBump});
  }

  public static Filter createMintFilter(final PublicKey mint) {
    return Filter.createMemCompFilter(MINT_OFFSET, mint);
  }

  public static Filter createLutSlotFilter(final long lutSlot) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lutSlot);
    return Filter.createMemCompFilter(LUT_SLOT_OFFSET, _data);
  }

  public static Filter createAllowSubsidiesFilter(final int allowSubsidies) {
    return Filter.createMemCompFilter(ALLOW_SUBSIDIES_OFFSET, new byte[]{(byte) allowSubsidies});
  }

  public static QueueAccountData read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static QueueAccountData read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], QueueAccountData> FACTORY = QueueAccountData::read;

  public static QueueAccountData read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var authority = readPubKey(_data, i);
    i += 32;
    final var mrEnclaves = new byte[32][32];
    i += Borsh.readArray(mrEnclaves, _data, i);
    final var oracleKeys = new PublicKey[128];
    i += Borsh.readArray(oracleKeys, _data, i);
    final var maxQuoteVerificationAge = getInt64LE(_data, i);
    i += 8;
    final var lastHeartbeat = getInt64LE(_data, i);
    i += 8;
    final var nodeTimeout = getInt64LE(_data, i);
    i += 8;
    final var oracleMinStake = getInt64LE(_data, i);
    i += 8;
    final var allowAuthorityOverrideAfter = getInt64LE(_data, i);
    i += 8;
    final var mrEnclavesLen = getInt32LE(_data, i);
    i += 4;
    final var oracleKeysLen = getInt32LE(_data, i);
    i += 4;
    final var reward = getInt32LE(_data, i);
    i += 4;
    final var currIdx = getInt32LE(_data, i);
    i += 4;
    final var gcIdx = getInt32LE(_data, i);
    i += 4;
    final var requireAuthorityHeartbeatPermission = _data[i] & 0xFF;
    ++i;
    final var requireAuthorityVerifyPermission = _data[i] & 0xFF;
    ++i;
    final var requireUsagePermissions = _data[i] & 0xFF;
    ++i;
    final var signerBump = _data[i] & 0xFF;
    ++i;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var lutSlot = getInt64LE(_data, i);
    i += 8;
    final var allowSubsidies = _data[i] & 0xFF;
    ++i;
    final var ebuf6 = new byte[23];
    i += Borsh.readArray(ebuf6, _data, i);
    final var ebuf5 = new byte[32];
    i += Borsh.readArray(ebuf5, _data, i);
    final var ebuf4 = new byte[64];
    i += Borsh.readArray(ebuf4, _data, i);
    final var ebuf3 = new byte[128];
    i += Borsh.readArray(ebuf3, _data, i);
    final var ebuf2 = new byte[256];
    i += Borsh.readArray(ebuf2, _data, i);
    final var ebuf1 = new byte[512];
    Borsh.readArray(ebuf1, _data, i);
    return new QueueAccountData(_address,
                                discriminator,
                                authority,
                                mrEnclaves,
                                oracleKeys,
                                maxQuoteVerificationAge,
                                lastHeartbeat,
                                nodeTimeout,
                                oracleMinStake,
                                allowAuthorityOverrideAfter,
                                mrEnclavesLen,
                                oracleKeysLen,
                                reward,
                                currIdx,
                                gcIdx,
                                requireAuthorityHeartbeatPermission,
                                requireAuthorityVerifyPermission,
                                requireUsagePermissions,
                                signerBump,
                                mint,
                                lutSlot,
                                allowSubsidies,
                                ebuf6,
                                ebuf5,
                                ebuf4,
                                ebuf3,
                                ebuf2,
                                ebuf1);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    authority.write(_data, i);
    i += 32;
    i += Borsh.writeArray(mrEnclaves, _data, i);
    i += Borsh.writeArray(oracleKeys, _data, i);
    putInt64LE(_data, i, maxQuoteVerificationAge);
    i += 8;
    putInt64LE(_data, i, lastHeartbeat);
    i += 8;
    putInt64LE(_data, i, nodeTimeout);
    i += 8;
    putInt64LE(_data, i, oracleMinStake);
    i += 8;
    putInt64LE(_data, i, allowAuthorityOverrideAfter);
    i += 8;
    putInt32LE(_data, i, mrEnclavesLen);
    i += 4;
    putInt32LE(_data, i, oracleKeysLen);
    i += 4;
    putInt32LE(_data, i, reward);
    i += 4;
    putInt32LE(_data, i, currIdx);
    i += 4;
    putInt32LE(_data, i, gcIdx);
    i += 4;
    _data[i] = (byte) requireAuthorityHeartbeatPermission;
    ++i;
    _data[i] = (byte) requireAuthorityVerifyPermission;
    ++i;
    _data[i] = (byte) requireUsagePermissions;
    ++i;
    _data[i] = (byte) signerBump;
    ++i;
    mint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, lutSlot);
    i += 8;
    _data[i] = (byte) allowSubsidies;
    ++i;
    i += Borsh.writeArray(ebuf6, _data, i);
    i += Borsh.writeArray(ebuf5, _data, i);
    i += Borsh.writeArray(ebuf4, _data, i);
    i += Borsh.writeArray(ebuf3, _data, i);
    i += Borsh.writeArray(ebuf2, _data, i);
    i += Borsh.writeArray(ebuf1, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
