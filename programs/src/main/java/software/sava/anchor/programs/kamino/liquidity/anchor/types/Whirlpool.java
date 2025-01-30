package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// External types
public record Whirlpool(PublicKey _address,
                        Discriminator discriminator,
                        PublicKey whirlpoolsConfig,
                        byte[] whirlpoolBump,
                        int tickSpacing,
                        byte[] tickSpacingSeed,
                        int feeRate,
                        int protocolFeeRate,
                        BigInteger liquidity,
                        BigInteger sqrtPrice,
                        int tickCurrentIndex,
                        long protocolFeeOwedA,
                        long protocolFeeOwedB,
                        PublicKey tokenMintA,
                        PublicKey tokenVaultA,
                        BigInteger feeGrowthGlobalA,
                        PublicKey tokenMintB,
                        PublicKey tokenVaultB,
                        BigInteger feeGrowthGlobalB,
                        long rewardLastUpdatedTimestamp,
                        WhirlpoolRewardInfo[] rewardInfos) implements Borsh {

  public static final int BYTES = 653;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int WHIRLPOOLS_CONFIG_OFFSET = 8;
  public static final int WHIRLPOOL_BUMP_OFFSET = 40;
  public static final int TICK_SPACING_OFFSET = 41;
  public static final int TICK_SPACING_SEED_OFFSET = 43;
  public static final int FEE_RATE_OFFSET = 45;
  public static final int PROTOCOL_FEE_RATE_OFFSET = 47;
  public static final int LIQUIDITY_OFFSET = 49;
  public static final int SQRT_PRICE_OFFSET = 65;
  public static final int TICK_CURRENT_INDEX_OFFSET = 81;
  public static final int PROTOCOL_FEE_OWED_A_OFFSET = 85;
  public static final int PROTOCOL_FEE_OWED_B_OFFSET = 93;
  public static final int TOKEN_MINT_A_OFFSET = 101;
  public static final int TOKEN_VAULT_A_OFFSET = 133;
  public static final int FEE_GROWTH_GLOBAL_A_OFFSET = 165;
  public static final int TOKEN_MINT_B_OFFSET = 181;
  public static final int TOKEN_VAULT_B_OFFSET = 213;
  public static final int FEE_GROWTH_GLOBAL_B_OFFSET = 245;
  public static final int REWARD_LAST_UPDATED_TIMESTAMP_OFFSET = 261;
  public static final int REWARD_INFOS_OFFSET = 269;

  public static Filter createWhirlpoolsConfigFilter(final PublicKey whirlpoolsConfig) {
    return Filter.createMemCompFilter(WHIRLPOOLS_CONFIG_OFFSET, whirlpoolsConfig);
  }

  public static Filter createTickSpacingFilter(final int tickSpacing) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, tickSpacing);
    return Filter.createMemCompFilter(TICK_SPACING_OFFSET, _data);
  }

  public static Filter createFeeRateFilter(final int feeRate) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, feeRate);
    return Filter.createMemCompFilter(FEE_RATE_OFFSET, _data);
  }

  public static Filter createProtocolFeeRateFilter(final int protocolFeeRate) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, protocolFeeRate);
    return Filter.createMemCompFilter(PROTOCOL_FEE_RATE_OFFSET, _data);
  }

  public static Filter createLiquidityFilter(final BigInteger liquidity) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, liquidity);
    return Filter.createMemCompFilter(LIQUIDITY_OFFSET, _data);
  }

  public static Filter createSqrtPriceFilter(final BigInteger sqrtPrice) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, sqrtPrice);
    return Filter.createMemCompFilter(SQRT_PRICE_OFFSET, _data);
  }

  public static Filter createTickCurrentIndexFilter(final int tickCurrentIndex) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, tickCurrentIndex);
    return Filter.createMemCompFilter(TICK_CURRENT_INDEX_OFFSET, _data);
  }

  public static Filter createProtocolFeeOwedAFilter(final long protocolFeeOwedA) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, protocolFeeOwedA);
    return Filter.createMemCompFilter(PROTOCOL_FEE_OWED_A_OFFSET, _data);
  }

  public static Filter createProtocolFeeOwedBFilter(final long protocolFeeOwedB) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, protocolFeeOwedB);
    return Filter.createMemCompFilter(PROTOCOL_FEE_OWED_B_OFFSET, _data);
  }

  public static Filter createTokenMintAFilter(final PublicKey tokenMintA) {
    return Filter.createMemCompFilter(TOKEN_MINT_A_OFFSET, tokenMintA);
  }

  public static Filter createTokenVaultAFilter(final PublicKey tokenVaultA) {
    return Filter.createMemCompFilter(TOKEN_VAULT_A_OFFSET, tokenVaultA);
  }

  public static Filter createFeeGrowthGlobalAFilter(final BigInteger feeGrowthGlobalA) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, feeGrowthGlobalA);
    return Filter.createMemCompFilter(FEE_GROWTH_GLOBAL_A_OFFSET, _data);
  }

  public static Filter createTokenMintBFilter(final PublicKey tokenMintB) {
    return Filter.createMemCompFilter(TOKEN_MINT_B_OFFSET, tokenMintB);
  }

  public static Filter createTokenVaultBFilter(final PublicKey tokenVaultB) {
    return Filter.createMemCompFilter(TOKEN_VAULT_B_OFFSET, tokenVaultB);
  }

  public static Filter createFeeGrowthGlobalBFilter(final BigInteger feeGrowthGlobalB) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, feeGrowthGlobalB);
    return Filter.createMemCompFilter(FEE_GROWTH_GLOBAL_B_OFFSET, _data);
  }

  public static Filter createRewardLastUpdatedTimestampFilter(final long rewardLastUpdatedTimestamp) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, rewardLastUpdatedTimestamp);
    return Filter.createMemCompFilter(REWARD_LAST_UPDATED_TIMESTAMP_OFFSET, _data);
  }

  public static Whirlpool read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Whirlpool read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Whirlpool read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Whirlpool> FACTORY = Whirlpool::read;

  public static Whirlpool read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var whirlpoolsConfig = readPubKey(_data, i);
    i += 32;
    final var whirlpoolBump = new byte[1];
    i += Borsh.readArray(whirlpoolBump, _data, i);
    final var tickSpacing = getInt16LE(_data, i);
    i += 2;
    final var tickSpacingSeed = new byte[2];
    i += Borsh.readArray(tickSpacingSeed, _data, i);
    final var feeRate = getInt16LE(_data, i);
    i += 2;
    final var protocolFeeRate = getInt16LE(_data, i);
    i += 2;
    final var liquidity = getInt128LE(_data, i);
    i += 16;
    final var sqrtPrice = getInt128LE(_data, i);
    i += 16;
    final var tickCurrentIndex = getInt32LE(_data, i);
    i += 4;
    final var protocolFeeOwedA = getInt64LE(_data, i);
    i += 8;
    final var protocolFeeOwedB = getInt64LE(_data, i);
    i += 8;
    final var tokenMintA = readPubKey(_data, i);
    i += 32;
    final var tokenVaultA = readPubKey(_data, i);
    i += 32;
    final var feeGrowthGlobalA = getInt128LE(_data, i);
    i += 16;
    final var tokenMintB = readPubKey(_data, i);
    i += 32;
    final var tokenVaultB = readPubKey(_data, i);
    i += 32;
    final var feeGrowthGlobalB = getInt128LE(_data, i);
    i += 16;
    final var rewardLastUpdatedTimestamp = getInt64LE(_data, i);
    i += 8;
    final var rewardInfos = new WhirlpoolRewardInfo[3];
    Borsh.readArray(rewardInfos, WhirlpoolRewardInfo::read, _data, i);
    return new Whirlpool(_address,
                         discriminator,
                         whirlpoolsConfig,
                         whirlpoolBump,
                         tickSpacing,
                         tickSpacingSeed,
                         feeRate,
                         protocolFeeRate,
                         liquidity,
                         sqrtPrice,
                         tickCurrentIndex,
                         protocolFeeOwedA,
                         protocolFeeOwedB,
                         tokenMintA,
                         tokenVaultA,
                         feeGrowthGlobalA,
                         tokenMintB,
                         tokenVaultB,
                         feeGrowthGlobalB,
                         rewardLastUpdatedTimestamp,
                         rewardInfos);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    whirlpoolsConfig.write(_data, i);
    i += 32;
    i += Borsh.writeArray(whirlpoolBump, _data, i);
    putInt16LE(_data, i, tickSpacing);
    i += 2;
    i += Borsh.writeArray(tickSpacingSeed, _data, i);
    putInt16LE(_data, i, feeRate);
    i += 2;
    putInt16LE(_data, i, protocolFeeRate);
    i += 2;
    putInt128LE(_data, i, liquidity);
    i += 16;
    putInt128LE(_data, i, sqrtPrice);
    i += 16;
    putInt32LE(_data, i, tickCurrentIndex);
    i += 4;
    putInt64LE(_data, i, protocolFeeOwedA);
    i += 8;
    putInt64LE(_data, i, protocolFeeOwedB);
    i += 8;
    tokenMintA.write(_data, i);
    i += 32;
    tokenVaultA.write(_data, i);
    i += 32;
    putInt128LE(_data, i, feeGrowthGlobalA);
    i += 16;
    tokenMintB.write(_data, i);
    i += 32;
    tokenVaultB.write(_data, i);
    i += 32;
    putInt128LE(_data, i, feeGrowthGlobalB);
    i += 16;
    putInt64LE(_data, i, rewardLastUpdatedTimestamp);
    i += 8;
    i += Borsh.writeArray(rewardInfos, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
