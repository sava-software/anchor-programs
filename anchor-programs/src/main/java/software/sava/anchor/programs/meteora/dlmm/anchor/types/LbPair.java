package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LbPair(PublicKey _address,
                     Discriminator discriminator,
                     StaticParameters parameters,
                     VariableParameters vParameters,
                     byte[] bumpSeed,
                     // Bin step signer seed
                     byte[] binStepSeed,
                     // Type of the pair
                     int pairType,
                     // Active bin id
                     int activeId,
                     // Bin step. Represent the price increment / decrement.
                     int binStep,
                     // Status of the pair. Check PairStatus enum.
                     int status,
                     // Require base factor seed
                     int requireBaseFactorSeed,
                     // Base factor seed
                     byte[] baseFactorSeed,
                     // Activation type
                     int activationType,
                     // Allow pool creator to enable/disable pool with restricted validation. Only applicable for customizable permissionless pair type.
                     int creatorPoolOnOffControl,
                     // Token X mint
                     PublicKey tokenXMint,
                     // Token Y mint
                     PublicKey tokenYMint,
                     // LB token X vault
                     PublicKey reserveX,
                     // LB token Y vault
                     PublicKey reserveY,
                     // Uncollected protocol fee
                     ProtocolFee protocolFee,
                     // _padding_1, previous Fee owner, BE CAREFUL FOR TOMBSTONE WHEN REUSE !!
                     byte[] padding1,
                     // Farming reward information
                     RewardInfo[] rewardInfos,
                     // Oracle pubkey
                     PublicKey oracle,
                     // Packed initialized bin array state
                     long[] binArrayBitmap,
                     // Last time the pool fee parameter was updated
                     long lastUpdatedAt,
                     // _padding_2, previous whitelisted_wallet, BE CAREFUL FOR TOMBSTONE WHEN REUSE !!
                     byte[] padding2,
                     // Address allowed to swap when the current point is greater than or equal to the pre-activation point. The pre-activation point is calculated as `activation_point - pre_activation_duration`.
                     PublicKey preActivationSwapAddress,
                     // Base keypair. Only required for permission pair
                     PublicKey baseKey,
                     // Time point to enable the pair. Only applicable for permission pair.
                     long activationPoint,
                     // Duration before activation activation_point. Used to calculate pre-activation time point for pre_activation_swap_address
                     long preActivationDuration,
                     // _padding 3 is reclaimed free space from swap_cap_deactivate_point and swap_cap_amount before, BE CAREFUL FOR TOMBSTONE WHEN REUSE !!
                     byte[] padding3,
                     // _padding_4, previous lock_duration, BE CAREFUL FOR TOMBSTONE WHEN REUSE !!
                     long padding4,
                     // Pool creator
                     PublicKey creator,
                     // token_mint_x_program_flag
                     int tokenMintXProgramFlag,
                     // token_mint_y_program_flag
                     int tokenMintYProgramFlag,
                     // Reserved space for future use
                     byte[] reserved) implements Borsh {

  public static final int BYTES = 904;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int PARAMETERS_OFFSET = 8;
  public static final int V_PARAMETERS_OFFSET = 40;
  public static final int BUMP_SEED_OFFSET = 72;
  public static final int BIN_STEP_SEED_OFFSET = 73;
  public static final int PAIR_TYPE_OFFSET = 75;
  public static final int ACTIVE_ID_OFFSET = 76;
  public static final int BIN_STEP_OFFSET = 80;
  public static final int STATUS_OFFSET = 82;
  public static final int REQUIRE_BASE_FACTOR_SEED_OFFSET = 83;
  public static final int BASE_FACTOR_SEED_OFFSET = 84;
  public static final int ACTIVATION_TYPE_OFFSET = 86;
  public static final int CREATOR_POOL_ON_OFF_CONTROL_OFFSET = 87;
  public static final int TOKEN_X_MINT_OFFSET = 88;
  public static final int TOKEN_Y_MINT_OFFSET = 120;
  public static final int RESERVE_X_OFFSET = 152;
  public static final int RESERVE_Y_OFFSET = 184;
  public static final int PROTOCOL_FEE_OFFSET = 216;
  public static final int PADDING1_OFFSET = 232;
  public static final int REWARD_INFOS_OFFSET = 264;
  public static final int ORACLE_OFFSET = 552;
  public static final int BIN_ARRAY_BITMAP_OFFSET = 584;
  public static final int LAST_UPDATED_AT_OFFSET = 712;
  public static final int PADDING2_OFFSET = 720;
  public static final int PRE_ACTIVATION_SWAP_ADDRESS_OFFSET = 752;
  public static final int BASE_KEY_OFFSET = 784;
  public static final int ACTIVATION_POINT_OFFSET = 816;
  public static final int PRE_ACTIVATION_DURATION_OFFSET = 824;
  public static final int PADDING3_OFFSET = 832;
  public static final int PADDING4_OFFSET = 840;
  public static final int CREATOR_OFFSET = 848;
  public static final int TOKEN_MINT_X_PROGRAM_FLAG_OFFSET = 880;
  public static final int TOKEN_MINT_Y_PROGRAM_FLAG_OFFSET = 881;
  public static final int RESERVED_OFFSET = 882;

  public static Filter createParametersFilter(final StaticParameters parameters) {
    return Filter.createMemCompFilter(PARAMETERS_OFFSET, parameters.write());
  }

  public static Filter createVParametersFilter(final VariableParameters vParameters) {
    return Filter.createMemCompFilter(V_PARAMETERS_OFFSET, vParameters.write());
  }

  public static Filter createPairTypeFilter(final int pairType) {
    return Filter.createMemCompFilter(PAIR_TYPE_OFFSET, new byte[]{(byte) pairType});
  }

  public static Filter createActiveIdFilter(final int activeId) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, activeId);
    return Filter.createMemCompFilter(ACTIVE_ID_OFFSET, _data);
  }

  public static Filter createBinStepFilter(final int binStep) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, binStep);
    return Filter.createMemCompFilter(BIN_STEP_OFFSET, _data);
  }

  public static Filter createStatusFilter(final int status) {
    return Filter.createMemCompFilter(STATUS_OFFSET, new byte[]{(byte) status});
  }

  public static Filter createRequireBaseFactorSeedFilter(final int requireBaseFactorSeed) {
    return Filter.createMemCompFilter(REQUIRE_BASE_FACTOR_SEED_OFFSET, new byte[]{(byte) requireBaseFactorSeed});
  }

  public static Filter createActivationTypeFilter(final int activationType) {
    return Filter.createMemCompFilter(ACTIVATION_TYPE_OFFSET, new byte[]{(byte) activationType});
  }

  public static Filter createCreatorPoolOnOffControlFilter(final int creatorPoolOnOffControl) {
    return Filter.createMemCompFilter(CREATOR_POOL_ON_OFF_CONTROL_OFFSET, new byte[]{(byte) creatorPoolOnOffControl});
  }

  public static Filter createTokenXMintFilter(final PublicKey tokenXMint) {
    return Filter.createMemCompFilter(TOKEN_X_MINT_OFFSET, tokenXMint);
  }

  public static Filter createTokenYMintFilter(final PublicKey tokenYMint) {
    return Filter.createMemCompFilter(TOKEN_Y_MINT_OFFSET, tokenYMint);
  }

  public static Filter createReserveXFilter(final PublicKey reserveX) {
    return Filter.createMemCompFilter(RESERVE_X_OFFSET, reserveX);
  }

  public static Filter createReserveYFilter(final PublicKey reserveY) {
    return Filter.createMemCompFilter(RESERVE_Y_OFFSET, reserveY);
  }

  public static Filter createProtocolFeeFilter(final ProtocolFee protocolFee) {
    return Filter.createMemCompFilter(PROTOCOL_FEE_OFFSET, protocolFee.write());
  }

  public static Filter createOracleFilter(final PublicKey oracle) {
    return Filter.createMemCompFilter(ORACLE_OFFSET, oracle);
  }

  public static Filter createLastUpdatedAtFilter(final long lastUpdatedAt) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastUpdatedAt);
    return Filter.createMemCompFilter(LAST_UPDATED_AT_OFFSET, _data);
  }

  public static Filter createPreActivationSwapAddressFilter(final PublicKey preActivationSwapAddress) {
    return Filter.createMemCompFilter(PRE_ACTIVATION_SWAP_ADDRESS_OFFSET, preActivationSwapAddress);
  }

  public static Filter createBaseKeyFilter(final PublicKey baseKey) {
    return Filter.createMemCompFilter(BASE_KEY_OFFSET, baseKey);
  }

  public static Filter createActivationPointFilter(final long activationPoint) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, activationPoint);
    return Filter.createMemCompFilter(ACTIVATION_POINT_OFFSET, _data);
  }

  public static Filter createPreActivationDurationFilter(final long preActivationDuration) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, preActivationDuration);
    return Filter.createMemCompFilter(PRE_ACTIVATION_DURATION_OFFSET, _data);
  }

  public static Filter createPadding4Filter(final long padding4) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, padding4);
    return Filter.createMemCompFilter(PADDING4_OFFSET, _data);
  }

  public static Filter createCreatorFilter(final PublicKey creator) {
    return Filter.createMemCompFilter(CREATOR_OFFSET, creator);
  }

  public static Filter createTokenMintXProgramFlagFilter(final int tokenMintXProgramFlag) {
    return Filter.createMemCompFilter(TOKEN_MINT_X_PROGRAM_FLAG_OFFSET, new byte[]{(byte) tokenMintXProgramFlag});
  }

  public static Filter createTokenMintYProgramFlagFilter(final int tokenMintYProgramFlag) {
    return Filter.createMemCompFilter(TOKEN_MINT_Y_PROGRAM_FLAG_OFFSET, new byte[]{(byte) tokenMintYProgramFlag});
  }

  public static LbPair read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static LbPair read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static LbPair read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], LbPair> FACTORY = LbPair::read;

  public static LbPair read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var parameters = StaticParameters.read(_data, i);
    i += Borsh.len(parameters);
    final var vParameters = VariableParameters.read(_data, i);
    i += Borsh.len(vParameters);
    final var bumpSeed = new byte[1];
    i += Borsh.readArray(bumpSeed, _data, i);
    final var binStepSeed = new byte[2];
    i += Borsh.readArray(binStepSeed, _data, i);
    final var pairType = _data[i] & 0xFF;
    ++i;
    final var activeId = getInt32LE(_data, i);
    i += 4;
    final var binStep = getInt16LE(_data, i);
    i += 2;
    final var status = _data[i] & 0xFF;
    ++i;
    final var requireBaseFactorSeed = _data[i] & 0xFF;
    ++i;
    final var baseFactorSeed = new byte[2];
    i += Borsh.readArray(baseFactorSeed, _data, i);
    final var activationType = _data[i] & 0xFF;
    ++i;
    final var creatorPoolOnOffControl = _data[i] & 0xFF;
    ++i;
    final var tokenXMint = readPubKey(_data, i);
    i += 32;
    final var tokenYMint = readPubKey(_data, i);
    i += 32;
    final var reserveX = readPubKey(_data, i);
    i += 32;
    final var reserveY = readPubKey(_data, i);
    i += 32;
    final var protocolFee = ProtocolFee.read(_data, i);
    i += Borsh.len(protocolFee);
    final var padding1 = new byte[32];
    i += Borsh.readArray(padding1, _data, i);
    final var rewardInfos = new RewardInfo[2];
    i += Borsh.readArray(rewardInfos, RewardInfo::read, _data, i);
    final var oracle = readPubKey(_data, i);
    i += 32;
    final var binArrayBitmap = new long[16];
    i += Borsh.readArray(binArrayBitmap, _data, i);
    final var lastUpdatedAt = getInt64LE(_data, i);
    i += 8;
    final var padding2 = new byte[32];
    i += Borsh.readArray(padding2, _data, i);
    final var preActivationSwapAddress = readPubKey(_data, i);
    i += 32;
    final var baseKey = readPubKey(_data, i);
    i += 32;
    final var activationPoint = getInt64LE(_data, i);
    i += 8;
    final var preActivationDuration = getInt64LE(_data, i);
    i += 8;
    final var padding3 = new byte[8];
    i += Borsh.readArray(padding3, _data, i);
    final var padding4 = getInt64LE(_data, i);
    i += 8;
    final var creator = readPubKey(_data, i);
    i += 32;
    final var tokenMintXProgramFlag = _data[i] & 0xFF;
    ++i;
    final var tokenMintYProgramFlag = _data[i] & 0xFF;
    ++i;
    final var reserved = new byte[22];
    Borsh.readArray(reserved, _data, i);
    return new LbPair(_address,
                      discriminator,
                      parameters,
                      vParameters,
                      bumpSeed,
                      binStepSeed,
                      pairType,
                      activeId,
                      binStep,
                      status,
                      requireBaseFactorSeed,
                      baseFactorSeed,
                      activationType,
                      creatorPoolOnOffControl,
                      tokenXMint,
                      tokenYMint,
                      reserveX,
                      reserveY,
                      protocolFee,
                      padding1,
                      rewardInfos,
                      oracle,
                      binArrayBitmap,
                      lastUpdatedAt,
                      padding2,
                      preActivationSwapAddress,
                      baseKey,
                      activationPoint,
                      preActivationDuration,
                      padding3,
                      padding4,
                      creator,
                      tokenMintXProgramFlag,
                      tokenMintYProgramFlag,
                      reserved);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    i += Borsh.write(parameters, _data, i);
    i += Borsh.write(vParameters, _data, i);
    i += Borsh.writeArray(bumpSeed, _data, i);
    i += Borsh.writeArray(binStepSeed, _data, i);
    _data[i] = (byte) pairType;
    ++i;
    putInt32LE(_data, i, activeId);
    i += 4;
    putInt16LE(_data, i, binStep);
    i += 2;
    _data[i] = (byte) status;
    ++i;
    _data[i] = (byte) requireBaseFactorSeed;
    ++i;
    i += Borsh.writeArray(baseFactorSeed, _data, i);
    _data[i] = (byte) activationType;
    ++i;
    _data[i] = (byte) creatorPoolOnOffControl;
    ++i;
    tokenXMint.write(_data, i);
    i += 32;
    tokenYMint.write(_data, i);
    i += 32;
    reserveX.write(_data, i);
    i += 32;
    reserveY.write(_data, i);
    i += 32;
    i += Borsh.write(protocolFee, _data, i);
    i += Borsh.writeArray(padding1, _data, i);
    i += Borsh.writeArray(rewardInfos, _data, i);
    oracle.write(_data, i);
    i += 32;
    i += Borsh.writeArray(binArrayBitmap, _data, i);
    putInt64LE(_data, i, lastUpdatedAt);
    i += 8;
    i += Borsh.writeArray(padding2, _data, i);
    preActivationSwapAddress.write(_data, i);
    i += 32;
    baseKey.write(_data, i);
    i += 32;
    putInt64LE(_data, i, activationPoint);
    i += 8;
    putInt64LE(_data, i, preActivationDuration);
    i += 8;
    i += Borsh.writeArray(padding3, _data, i);
    putInt64LE(_data, i, padding4);
    i += 8;
    creator.write(_data, i);
    i += 32;
    _data[i] = (byte) tokenMintXProgramFlag;
    ++i;
    _data[i] = (byte) tokenMintYProgramFlag;
    ++i;
    i += Borsh.writeArray(reserved, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
