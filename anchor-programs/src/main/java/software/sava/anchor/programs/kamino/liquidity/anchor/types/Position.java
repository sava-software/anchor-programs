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

public record Position(PublicKey _address,
                       Discriminator discriminator,
                       PublicKey whirlpool,
                       PublicKey positionMint,
                       BigInteger liquidity,
                       int tickLowerIndex,
                       int tickUpperIndex,
                       BigInteger feeGrowthCheckpointA,
                       long feeOwedA,
                       BigInteger feeGrowthCheckpointB,
                       long feeOwedB,
                       PositionRewardInfo[] rewardInfos) implements Borsh {

  public static final int BYTES = 216;
  public static final int REWARD_INFOS_LEN = 3;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int WHIRLPOOL_OFFSET = 8;
  public static final int POSITION_MINT_OFFSET = 40;
  public static final int LIQUIDITY_OFFSET = 72;
  public static final int TICK_LOWER_INDEX_OFFSET = 88;
  public static final int TICK_UPPER_INDEX_OFFSET = 92;
  public static final int FEE_GROWTH_CHECKPOINT_A_OFFSET = 96;
  public static final int FEE_OWED_A_OFFSET = 112;
  public static final int FEE_GROWTH_CHECKPOINT_B_OFFSET = 120;
  public static final int FEE_OWED_B_OFFSET = 136;
  public static final int REWARD_INFOS_OFFSET = 144;

  public static Filter createWhirlpoolFilter(final PublicKey whirlpool) {
    return Filter.createMemCompFilter(WHIRLPOOL_OFFSET, whirlpool);
  }

  public static Filter createPositionMintFilter(final PublicKey positionMint) {
    return Filter.createMemCompFilter(POSITION_MINT_OFFSET, positionMint);
  }

  public static Filter createLiquidityFilter(final BigInteger liquidity) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, liquidity);
    return Filter.createMemCompFilter(LIQUIDITY_OFFSET, _data);
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

  public static Filter createFeeGrowthCheckpointAFilter(final BigInteger feeGrowthCheckpointA) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, feeGrowthCheckpointA);
    return Filter.createMemCompFilter(FEE_GROWTH_CHECKPOINT_A_OFFSET, _data);
  }

  public static Filter createFeeOwedAFilter(final long feeOwedA) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, feeOwedA);
    return Filter.createMemCompFilter(FEE_OWED_A_OFFSET, _data);
  }

  public static Filter createFeeGrowthCheckpointBFilter(final BigInteger feeGrowthCheckpointB) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, feeGrowthCheckpointB);
    return Filter.createMemCompFilter(FEE_GROWTH_CHECKPOINT_B_OFFSET, _data);
  }

  public static Filter createFeeOwedBFilter(final long feeOwedB) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, feeOwedB);
    return Filter.createMemCompFilter(FEE_OWED_B_OFFSET, _data);
  }

  public static Position read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Position read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Position read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Position> FACTORY = Position::read;

  public static Position read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var whirlpool = readPubKey(_data, i);
    i += 32;
    final var positionMint = readPubKey(_data, i);
    i += 32;
    final var liquidity = getInt128LE(_data, i);
    i += 16;
    final var tickLowerIndex = getInt32LE(_data, i);
    i += 4;
    final var tickUpperIndex = getInt32LE(_data, i);
    i += 4;
    final var feeGrowthCheckpointA = getInt128LE(_data, i);
    i += 16;
    final var feeOwedA = getInt64LE(_data, i);
    i += 8;
    final var feeGrowthCheckpointB = getInt128LE(_data, i);
    i += 16;
    final var feeOwedB = getInt64LE(_data, i);
    i += 8;
    final var rewardInfos = new PositionRewardInfo[3];
    Borsh.readArray(rewardInfos, PositionRewardInfo::read, _data, i);
    return new Position(_address,
                        discriminator,
                        whirlpool,
                        positionMint,
                        liquidity,
                        tickLowerIndex,
                        tickUpperIndex,
                        feeGrowthCheckpointA,
                        feeOwedA,
                        feeGrowthCheckpointB,
                        feeOwedB,
                        rewardInfos);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    whirlpool.write(_data, i);
    i += 32;
    positionMint.write(_data, i);
    i += 32;
    putInt128LE(_data, i, liquidity);
    i += 16;
    putInt32LE(_data, i, tickLowerIndex);
    i += 4;
    putInt32LE(_data, i, tickUpperIndex);
    i += 4;
    putInt128LE(_data, i, feeGrowthCheckpointA);
    i += 16;
    putInt64LE(_data, i, feeOwedA);
    i += 8;
    putInt128LE(_data, i, feeGrowthCheckpointB);
    i += 16;
    putInt64LE(_data, i, feeOwedB);
    i += 8;
    i += Borsh.writeArray(rewardInfos, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
