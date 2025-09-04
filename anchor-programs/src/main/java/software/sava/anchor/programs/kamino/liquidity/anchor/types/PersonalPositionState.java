package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record PersonalPositionState(PublicKey _address,
                                    Discriminator discriminator,
                                    // Bump to identify PDA
                                    int bump,
                                    // Mint address of the tokenized position
                                    PublicKey nftMint,
                                    // The ID of the pool with which this token is connected
                                    PublicKey poolId,
                                    // The lower bound tick of the position
                                    int tickLowerIndex,
                                    // The upper bound tick of the position
                                    int tickUpperIndex,
                                    // The amount of liquidity owned by this position
                                    BigInteger liquidity,
                                    // The token_0 fee growth of the aggregate position as of the last action on the individual position
                                    BigInteger feeGrowthInside0LastX64,
                                    // The token_1 fee growth of the aggregate position as of the last action on the individual position
                                    BigInteger feeGrowthInside1LastX64,
                                    // The fees owed to the position owner in token_0, as of the last computation
                                    long tokenFeesOwed0,
                                    // The fees owed to the position owner in token_1, as of the last computation
                                    long tokenFeesOwed1,
                                    PositionRewardInfo[] rewardInfos,
                                    long[] padding) implements Borsh {

  public static final int BYTES = 281;
  public static final int REWARD_INFOS_LEN = 3;
  public static final int PADDING_LEN = 8;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int BUMP_OFFSET = 8;
  public static final int NFT_MINT_OFFSET = 9;
  public static final int POOL_ID_OFFSET = 41;
  public static final int TICK_LOWER_INDEX_OFFSET = 73;
  public static final int TICK_UPPER_INDEX_OFFSET = 77;
  public static final int LIQUIDITY_OFFSET = 81;
  public static final int FEE_GROWTH_INSIDE_0_LAST_X_66_OFFSET = 97;
  public static final int FEE_GROWTH_INSIDE_1_LAST_X_66_OFFSET = 113;
  public static final int TOKEN_FEES_OWED_0_OFFSET = 129;
  public static final int TOKEN_FEES_OWED_1_OFFSET = 137;
  public static final int REWARD_INFOS_OFFSET = 145;
  public static final int PADDING_OFFSET = 217;

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createNftMintFilter(final PublicKey nftMint) {
    return Filter.createMemCompFilter(NFT_MINT_OFFSET, nftMint);
  }

  public static Filter createPoolIdFilter(final PublicKey poolId) {
    return Filter.createMemCompFilter(POOL_ID_OFFSET, poolId);
  }

  public static Filter createTickLowerIndexFilter(final int tickLowerIndex) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, tickLowerIndex);
    return Filter.createMemCompFilter(TICK_LOWER_INDEX_OFFSET, _data);
  }

  public static Filter createTickUpperIndexFilter(final int tickUpperIndex) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, tickUpperIndex);
    return Filter.createMemCompFilter(TICK_UPPER_INDEX_OFFSET, _data);
  }

  public static Filter createLiquidityFilter(final BigInteger liquidity) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, liquidity);
    return Filter.createMemCompFilter(LIQUIDITY_OFFSET, _data);
  }

  public static Filter createFeeGrowthInside0LastX64Filter(final BigInteger feeGrowthInside0LastX64) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, feeGrowthInside0LastX64);
    return Filter.createMemCompFilter(FEE_GROWTH_INSIDE_0_LAST_X_66_OFFSET, _data);
  }

  public static Filter createFeeGrowthInside1LastX64Filter(final BigInteger feeGrowthInside1LastX64) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, feeGrowthInside1LastX64);
    return Filter.createMemCompFilter(FEE_GROWTH_INSIDE_1_LAST_X_66_OFFSET, _data);
  }

  public static Filter createTokenFeesOwed0Filter(final long tokenFeesOwed0) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, tokenFeesOwed0);
    return Filter.createMemCompFilter(TOKEN_FEES_OWED_0_OFFSET, _data);
  }

  public static Filter createTokenFeesOwed1Filter(final long tokenFeesOwed1) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, tokenFeesOwed1);
    return Filter.createMemCompFilter(TOKEN_FEES_OWED_1_OFFSET, _data);
  }

  public static PersonalPositionState read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static PersonalPositionState read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static PersonalPositionState read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], PersonalPositionState> FACTORY = PersonalPositionState::read;

  public static PersonalPositionState read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var bump = _data[i] & 0xFF;
    ++i;
    final var nftMint = readPubKey(_data, i);
    i += 32;
    final var poolId = readPubKey(_data, i);
    i += 32;
    final var tickLowerIndex = getInt32LE(_data, i);
    i += 4;
    final var tickUpperIndex = getInt32LE(_data, i);
    i += 4;
    final var liquidity = getInt128LE(_data, i);
    i += 16;
    final var feeGrowthInside0LastX64 = getInt128LE(_data, i);
    i += 16;
    final var feeGrowthInside1LastX64 = getInt128LE(_data, i);
    i += 16;
    final var tokenFeesOwed0 = getInt64LE(_data, i);
    i += 8;
    final var tokenFeesOwed1 = getInt64LE(_data, i);
    i += 8;
    final var rewardInfos = new PositionRewardInfo[3];
    i += Borsh.readArray(rewardInfos, PositionRewardInfo::read, _data, i);
    final var padding = new long[8];
    Borsh.readArray(padding, _data, i);
    return new PersonalPositionState(_address,
                                     discriminator,
                                     bump,
                                     nftMint,
                                     poolId,
                                     tickLowerIndex,
                                     tickUpperIndex,
                                     liquidity,
                                     feeGrowthInside0LastX64,
                                     feeGrowthInside1LastX64,
                                     tokenFeesOwed0,
                                     tokenFeesOwed1,
                                     rewardInfos,
                                     padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    _data[i] = (byte) bump;
    ++i;
    nftMint.write(_data, i);
    i += 32;
    poolId.write(_data, i);
    i += 32;
    putInt32LE(_data, i, tickLowerIndex);
    i += 4;
    putInt32LE(_data, i, tickUpperIndex);
    i += 4;
    putInt128LE(_data, i, liquidity);
    i += 16;
    putInt128LE(_data, i, feeGrowthInside0LastX64);
    i += 16;
    putInt128LE(_data, i, feeGrowthInside1LastX64);
    i += 16;
    putInt64LE(_data, i, tokenFeesOwed0);
    i += 8;
    putInt64LE(_data, i, tokenFeesOwed1);
    i += 8;
    i += Borsh.writeArray(rewardInfos, _data, i);
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
