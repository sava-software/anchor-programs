package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record State(PublicKey _address,
                    Discriminator discriminator,
                    int bump,
                    int testOnlyDisableMrEnclaveCheck,
                    int enableStaking,
                    byte[] padding1,
                    PublicKey authority,
                    PublicKey guardianQueue,
                    long reserved1,
                    long epochLength,
                    byte[] reserved2,
                    PublicKey switchMint,
                    short[] sgxAdvisories,
                    int advisoriesLen,
                    int padding2,
                    int flatRewardCutPercentage,
                    int enableSlashing,
                    int padding3,
                    long lutSlot,
                    int baseReward,
                    byte[] padding4,
                    long subsidyAmount,
                    byte[] ebuf6,
                    byte[] ebuf5,
                    byte[] ebuf4,
                    byte[] ebuf3,
                    byte[] ebuf2,
                    // Cost whitelist by authority
                    PublicKey[] costWhitelist) implements Borsh {

  public static final int BYTES = 2136;
  public static final int PADDING_1_LEN = 5;
  public static final int RESERVED_2_LEN = 136;
  public static final int SGX_ADVISORIES_LEN = 32;
  public static final int PADDING_4_LEN = 4;
  public static final int EBUF_6_LEN = 16;
  public static final int EBUF_5_LEN = 32;
  public static final int EBUF_4_LEN = 64;
  public static final int EBUF_3_LEN = 128;
  public static final int EBUF_2_LEN = 512;
  public static final int COST_WHITELIST_LEN = 32;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(216, 146, 107, 94, 104, 75, 182, 177);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int BUMP_OFFSET = 8;
  public static final int TEST_ONLY_DISABLE_MR_ENCLAVE_CHECK_OFFSET = 9;
  public static final int ENABLE_STAKING_OFFSET = 10;
  public static final int PADDING_1_OFFSET = 11;
  public static final int AUTHORITY_OFFSET = 16;
  public static final int GUARDIAN_QUEUE_OFFSET = 48;
  public static final int RESERVED_1_OFFSET = 80;
  public static final int EPOCH_LENGTH_OFFSET = 88;
  public static final int RESERVED_2_OFFSET = 96;
  public static final int SWITCH_MINT_OFFSET = 232;
  public static final int SGX_ADVISORIES_OFFSET = 264;
  public static final int ADVISORIES_LEN_OFFSET = 328;
  public static final int PADDING_2_OFFSET = 329;
  public static final int FLAT_REWARD_CUT_PERCENTAGE_OFFSET = 330;
  public static final int ENABLE_SLASHING_OFFSET = 331;
  public static final int PADDING_3_OFFSET = 332;
  public static final int LUT_SLOT_OFFSET = 336;
  public static final int BASE_REWARD_OFFSET = 344;
  public static final int PADDING_4_OFFSET = 348;
  public static final int SUBSIDY_AMOUNT_OFFSET = 352;
  public static final int EBUF_6_OFFSET = 360;
  public static final int EBUF_5_OFFSET = 376;
  public static final int EBUF_4_OFFSET = 408;
  public static final int EBUF_3_OFFSET = 472;
  public static final int EBUF_2_OFFSET = 600;
  public static final int COST_WHITELIST_OFFSET = 1112;

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createTestOnlyDisableMrEnclaveCheckFilter(final int testOnlyDisableMrEnclaveCheck) {
    return Filter.createMemCompFilter(TEST_ONLY_DISABLE_MR_ENCLAVE_CHECK_OFFSET, new byte[]{(byte) testOnlyDisableMrEnclaveCheck});
  }

  public static Filter createEnableStakingFilter(final int enableStaking) {
    return Filter.createMemCompFilter(ENABLE_STAKING_OFFSET, new byte[]{(byte) enableStaking});
  }

  public static Filter createAuthorityFilter(final PublicKey authority) {
    return Filter.createMemCompFilter(AUTHORITY_OFFSET, authority);
  }

  public static Filter createGuardianQueueFilter(final PublicKey guardianQueue) {
    return Filter.createMemCompFilter(GUARDIAN_QUEUE_OFFSET, guardianQueue);
  }

  public static Filter createReserved1Filter(final long reserved1) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, reserved1);
    return Filter.createMemCompFilter(RESERVED_1_OFFSET, _data);
  }

  public static Filter createEpochLengthFilter(final long epochLength) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, epochLength);
    return Filter.createMemCompFilter(EPOCH_LENGTH_OFFSET, _data);
  }

  public static Filter createSwitchMintFilter(final PublicKey switchMint) {
    return Filter.createMemCompFilter(SWITCH_MINT_OFFSET, switchMint);
  }

  public static Filter createAdvisoriesLenFilter(final int advisoriesLen) {
    return Filter.createMemCompFilter(ADVISORIES_LEN_OFFSET, new byte[]{(byte) advisoriesLen});
  }

  public static Filter createPadding2Filter(final int padding2) {
    return Filter.createMemCompFilter(PADDING_2_OFFSET, new byte[]{(byte) padding2});
  }

  public static Filter createFlatRewardCutPercentageFilter(final int flatRewardCutPercentage) {
    return Filter.createMemCompFilter(FLAT_REWARD_CUT_PERCENTAGE_OFFSET, new byte[]{(byte) flatRewardCutPercentage});
  }

  public static Filter createEnableSlashingFilter(final int enableSlashing) {
    return Filter.createMemCompFilter(ENABLE_SLASHING_OFFSET, new byte[]{(byte) enableSlashing});
  }

  public static Filter createPadding3Filter(final int padding3) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, padding3);
    return Filter.createMemCompFilter(PADDING_3_OFFSET, _data);
  }

  public static Filter createLutSlotFilter(final long lutSlot) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lutSlot);
    return Filter.createMemCompFilter(LUT_SLOT_OFFSET, _data);
  }

  public static Filter createBaseRewardFilter(final int baseReward) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, baseReward);
    return Filter.createMemCompFilter(BASE_REWARD_OFFSET, _data);
  }

  public static Filter createSubsidyAmountFilter(final long subsidyAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, subsidyAmount);
    return Filter.createMemCompFilter(SUBSIDY_AMOUNT_OFFSET, _data);
  }

  public static State read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static State read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static State read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], State> FACTORY = State::read;

  public static State read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var bump = _data[i] & 0xFF;
    ++i;
    final var testOnlyDisableMrEnclaveCheck = _data[i] & 0xFF;
    ++i;
    final var enableStaking = _data[i] & 0xFF;
    ++i;
    final var padding1 = new byte[5];
    i += Borsh.readArray(padding1, _data, i);
    final var authority = readPubKey(_data, i);
    i += 32;
    final var guardianQueue = readPubKey(_data, i);
    i += 32;
    final var reserved1 = getInt64LE(_data, i);
    i += 8;
    final var epochLength = getInt64LE(_data, i);
    i += 8;
    final var reserved2 = new byte[136];
    i += Borsh.readArray(reserved2, _data, i);
    final var switchMint = readPubKey(_data, i);
    i += 32;
    final var sgxAdvisories = new short[32];
    i += Borsh.readArray(sgxAdvisories, _data, i);
    final var advisoriesLen = _data[i] & 0xFF;
    ++i;
    final var padding2 = _data[i] & 0xFF;
    ++i;
    final var flatRewardCutPercentage = _data[i] & 0xFF;
    ++i;
    final var enableSlashing = _data[i] & 0xFF;
    ++i;
    final var padding3 = getInt32LE(_data, i);
    i += 4;
    final var lutSlot = getInt64LE(_data, i);
    i += 8;
    final var baseReward = getInt32LE(_data, i);
    i += 4;
    final var padding4 = new byte[4];
    i += Borsh.readArray(padding4, _data, i);
    final var subsidyAmount = getInt64LE(_data, i);
    i += 8;
    final var ebuf6 = new byte[16];
    i += Borsh.readArray(ebuf6, _data, i);
    final var ebuf5 = new byte[32];
    i += Borsh.readArray(ebuf5, _data, i);
    final var ebuf4 = new byte[64];
    i += Borsh.readArray(ebuf4, _data, i);
    final var ebuf3 = new byte[128];
    i += Borsh.readArray(ebuf3, _data, i);
    final var ebuf2 = new byte[512];
    i += Borsh.readArray(ebuf2, _data, i);
    final var costWhitelist = new PublicKey[32];
    Borsh.readArray(costWhitelist, _data, i);
    return new State(_address,
                     discriminator,
                     bump,
                     testOnlyDisableMrEnclaveCheck,
                     enableStaking,
                     padding1,
                     authority,
                     guardianQueue,
                     reserved1,
                     epochLength,
                     reserved2,
                     switchMint,
                     sgxAdvisories,
                     advisoriesLen,
                     padding2,
                     flatRewardCutPercentage,
                     enableSlashing,
                     padding3,
                     lutSlot,
                     baseReward,
                     padding4,
                     subsidyAmount,
                     ebuf6,
                     ebuf5,
                     ebuf4,
                     ebuf3,
                     ebuf2,
                     costWhitelist);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    _data[i] = (byte) bump;
    ++i;
    _data[i] = (byte) testOnlyDisableMrEnclaveCheck;
    ++i;
    _data[i] = (byte) enableStaking;
    ++i;
    i += Borsh.writeArray(padding1, _data, i);
    authority.write(_data, i);
    i += 32;
    guardianQueue.write(_data, i);
    i += 32;
    putInt64LE(_data, i, reserved1);
    i += 8;
    putInt64LE(_data, i, epochLength);
    i += 8;
    i += Borsh.writeArray(reserved2, _data, i);
    switchMint.write(_data, i);
    i += 32;
    i += Borsh.writeArray(sgxAdvisories, _data, i);
    _data[i] = (byte) advisoriesLen;
    ++i;
    _data[i] = (byte) padding2;
    ++i;
    _data[i] = (byte) flatRewardCutPercentage;
    ++i;
    _data[i] = (byte) enableSlashing;
    ++i;
    putInt32LE(_data, i, padding3);
    i += 4;
    putInt64LE(_data, i, lutSlot);
    i += 8;
    putInt32LE(_data, i, baseReward);
    i += 4;
    i += Borsh.writeArray(padding4, _data, i);
    putInt64LE(_data, i, subsidyAmount);
    i += 8;
    i += Borsh.writeArray(ebuf6, _data, i);
    i += Borsh.writeArray(ebuf5, _data, i);
    i += Borsh.writeArray(ebuf4, _data, i);
    i += Borsh.writeArray(ebuf3, _data, i);
    i += Borsh.writeArray(ebuf2, _data, i);
    i += Borsh.writeArray(costWhitelist, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
