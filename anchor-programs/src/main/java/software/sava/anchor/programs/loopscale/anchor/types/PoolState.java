package software.sava.anchor.programs.loopscale.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

// The pool state
// 
// PDA of `[POOL_SEED, config, token_mint_0, token_mint_1]`
// 
public record PoolState(PublicKey _address,
                        Discriminator discriminator,
                        // Bump to identify PDA
                        byte[] bump,
                        PublicKey ammConfig,
                        PublicKey owner,
                        // Token pair of the pool, where token_mint_0 address < token_mint_1 address
                        PublicKey tokenMint0,
                        PublicKey tokenMint1,
                        // Token pair vault
                        PublicKey tokenVault0,
                        PublicKey tokenVault1,
                        // observation account key
                        PublicKey observationKey,
                        // mint0 and mint1 decimals
                        int mintDecimals0,
                        int mintDecimals1,
                        // The minimum number of ticks between initialized ticks
                        int tickSpacing,
                        // The currently in range liquidity available to the pool.
                        BigInteger liquidity,
                        // The current price of the pool as a sqrt(token_1/token_0) Q64.64 value
                        BigInteger sqrtPriceX64,
                        // The current tick of the pool, i.e. according to the last tick transition that was run.
                        int tickCurrent,
                        int padding3,
                        int padding4,
                        // The fee growth as a Q64.64 number, i.e. fees of token_0 and token_1 collected per
                        // unit of liquidity for the entire life of the pool.
                        BigInteger feeGrowthGlobal0X64,
                        BigInteger feeGrowthGlobal1X64,
                        // The amounts of token_0 and token_1 that are owed to the protocol.
                        long protocolFeesToken0,
                        long protocolFeesToken1,
                        // The amounts in and out of swap token_0 and token_1
                        BigInteger swapInAmountToken0,
                        BigInteger swapOutAmountToken1,
                        BigInteger swapInAmountToken1,
                        BigInteger swapOutAmountToken0,
                        // Bitwise representation of the state of the pool
                        // bit0, 1: disable open position and increase liquidity, 0: normal
                        // bit1, 1: disable decrease liquidity, 0: normal
                        // bit2, 1: disable collect fee, 0: normal
                        // bit3, 1: disable collect reward, 0: normal
                        // bit4, 1: disable swap, 0: normal
                        int status,
                        // Leave blank for future use
                        byte[] padding,
                        RewardInfo[] rewardInfos,
                        // Packed initialized tick array state
                        long[] tickArrayBitmap,
                        // except protocol_fee and fund_fee
                        long totalFeesToken0,
                        // except protocol_fee and fund_fee
                        long totalFeesClaimedToken0,
                        long totalFeesToken1,
                        long totalFeesClaimedToken1,
                        long fundFeesToken0,
                        long fundFeesToken1,
                        long openTime,
                        long recentEpoch,
                        long[] padding1,
                        long[] padding2) implements Borsh {

  public static final int BYTES = 1544;
  public static final int BUMP_LEN = 1;
  public static final int PADDING_LEN = 7;
  public static final int REWARD_INFOS_LEN = 3;
  public static final int TICK_ARRAY_BITMAP_LEN = 16;
  public static final int PADDING_1_LEN = 24;
  public static final int PADDING_2_LEN = 32;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(247, 237, 227, 245, 215, 195, 222, 70);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int BUMP_OFFSET = 8;
  public static final int AMM_CONFIG_OFFSET = 9;
  public static final int OWNER_OFFSET = 41;
  public static final int TOKEN_MINT_0_OFFSET = 73;
  public static final int TOKEN_MINT_1_OFFSET = 105;
  public static final int TOKEN_VAULT_0_OFFSET = 137;
  public static final int TOKEN_VAULT_1_OFFSET = 169;
  public static final int OBSERVATION_KEY_OFFSET = 201;
  public static final int MINT_DECIMALS_0_OFFSET = 233;
  public static final int MINT_DECIMALS_1_OFFSET = 234;
  public static final int TICK_SPACING_OFFSET = 235;
  public static final int LIQUIDITY_OFFSET = 237;
  public static final int SQRT_PRICE_X_66_OFFSET = 253;
  public static final int TICK_CURRENT_OFFSET = 269;
  public static final int PADDING_3_OFFSET = 273;
  public static final int PADDING_4_OFFSET = 275;
  public static final int FEE_GROWTH_GLOBAL_0_X_66_OFFSET = 277;
  public static final int FEE_GROWTH_GLOBAL_1_X_66_OFFSET = 293;
  public static final int PROTOCOL_FEES_TOKEN_0_OFFSET = 309;
  public static final int PROTOCOL_FEES_TOKEN_1_OFFSET = 317;
  public static final int SWAP_IN_AMOUNT_TOKEN_0_OFFSET = 325;
  public static final int SWAP_OUT_AMOUNT_TOKEN_1_OFFSET = 341;
  public static final int SWAP_IN_AMOUNT_TOKEN_1_OFFSET = 357;
  public static final int SWAP_OUT_AMOUNT_TOKEN_0_OFFSET = 373;
  public static final int STATUS_OFFSET = 389;
  public static final int PADDING_OFFSET = 390;
  public static final int REWARD_INFOS_OFFSET = 397;
  public static final int TICK_ARRAY_BITMAP_OFFSET = 904;
  public static final int TOTAL_FEES_TOKEN_0_OFFSET = 1032;
  public static final int TOTAL_FEES_CLAIMED_TOKEN_0_OFFSET = 1040;
  public static final int TOTAL_FEES_TOKEN_1_OFFSET = 1048;
  public static final int TOTAL_FEES_CLAIMED_TOKEN_1_OFFSET = 1056;
  public static final int FUND_FEES_TOKEN_0_OFFSET = 1064;
  public static final int FUND_FEES_TOKEN_1_OFFSET = 1072;
  public static final int OPEN_TIME_OFFSET = 1080;
  public static final int RECENT_EPOCH_OFFSET = 1088;
  public static final int PADDING_1_OFFSET = 1096;
  public static final int PADDING_2_OFFSET = 1288;

  public static Filter createAmmConfigFilter(final PublicKey ammConfig) {
    return Filter.createMemCompFilter(AMM_CONFIG_OFFSET, ammConfig);
  }

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static Filter createTokenMint0Filter(final PublicKey tokenMint0) {
    return Filter.createMemCompFilter(TOKEN_MINT_0_OFFSET, tokenMint0);
  }

  public static Filter createTokenMint1Filter(final PublicKey tokenMint1) {
    return Filter.createMemCompFilter(TOKEN_MINT_1_OFFSET, tokenMint1);
  }

  public static Filter createTokenVault0Filter(final PublicKey tokenVault0) {
    return Filter.createMemCompFilter(TOKEN_VAULT_0_OFFSET, tokenVault0);
  }

  public static Filter createTokenVault1Filter(final PublicKey tokenVault1) {
    return Filter.createMemCompFilter(TOKEN_VAULT_1_OFFSET, tokenVault1);
  }

  public static Filter createObservationKeyFilter(final PublicKey observationKey) {
    return Filter.createMemCompFilter(OBSERVATION_KEY_OFFSET, observationKey);
  }

  public static Filter createMintDecimals0Filter(final int mintDecimals0) {
    return Filter.createMemCompFilter(MINT_DECIMALS_0_OFFSET, new byte[]{(byte) mintDecimals0});
  }

  public static Filter createMintDecimals1Filter(final int mintDecimals1) {
    return Filter.createMemCompFilter(MINT_DECIMALS_1_OFFSET, new byte[]{(byte) mintDecimals1});
  }

  public static Filter createTickSpacingFilter(final int tickSpacing) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, tickSpacing);
    return Filter.createMemCompFilter(TICK_SPACING_OFFSET, _data);
  }

  public static Filter createLiquidityFilter(final BigInteger liquidity) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, liquidity);
    return Filter.createMemCompFilter(LIQUIDITY_OFFSET, _data);
  }

  public static Filter createSqrtPriceX64Filter(final BigInteger sqrtPriceX64) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, sqrtPriceX64);
    return Filter.createMemCompFilter(SQRT_PRICE_X_66_OFFSET, _data);
  }

  public static Filter createTickCurrentFilter(final int tickCurrent) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, tickCurrent);
    return Filter.createMemCompFilter(TICK_CURRENT_OFFSET, _data);
  }

  public static Filter createPadding3Filter(final int padding3) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, padding3);
    return Filter.createMemCompFilter(PADDING_3_OFFSET, _data);
  }

  public static Filter createPadding4Filter(final int padding4) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, padding4);
    return Filter.createMemCompFilter(PADDING_4_OFFSET, _data);
  }

  public static Filter createFeeGrowthGlobal0X64Filter(final BigInteger feeGrowthGlobal0X64) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, feeGrowthGlobal0X64);
    return Filter.createMemCompFilter(FEE_GROWTH_GLOBAL_0_X_66_OFFSET, _data);
  }

  public static Filter createFeeGrowthGlobal1X64Filter(final BigInteger feeGrowthGlobal1X64) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, feeGrowthGlobal1X64);
    return Filter.createMemCompFilter(FEE_GROWTH_GLOBAL_1_X_66_OFFSET, _data);
  }

  public static Filter createProtocolFeesToken0Filter(final long protocolFeesToken0) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, protocolFeesToken0);
    return Filter.createMemCompFilter(PROTOCOL_FEES_TOKEN_0_OFFSET, _data);
  }

  public static Filter createProtocolFeesToken1Filter(final long protocolFeesToken1) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, protocolFeesToken1);
    return Filter.createMemCompFilter(PROTOCOL_FEES_TOKEN_1_OFFSET, _data);
  }

  public static Filter createSwapInAmountToken0Filter(final BigInteger swapInAmountToken0) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, swapInAmountToken0);
    return Filter.createMemCompFilter(SWAP_IN_AMOUNT_TOKEN_0_OFFSET, _data);
  }

  public static Filter createSwapOutAmountToken1Filter(final BigInteger swapOutAmountToken1) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, swapOutAmountToken1);
    return Filter.createMemCompFilter(SWAP_OUT_AMOUNT_TOKEN_1_OFFSET, _data);
  }

  public static Filter createSwapInAmountToken1Filter(final BigInteger swapInAmountToken1) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, swapInAmountToken1);
    return Filter.createMemCompFilter(SWAP_IN_AMOUNT_TOKEN_1_OFFSET, _data);
  }

  public static Filter createSwapOutAmountToken0Filter(final BigInteger swapOutAmountToken0) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, swapOutAmountToken0);
    return Filter.createMemCompFilter(SWAP_OUT_AMOUNT_TOKEN_0_OFFSET, _data);
  }

  public static Filter createStatusFilter(final int status) {
    return Filter.createMemCompFilter(STATUS_OFFSET, new byte[]{(byte) status});
  }

  public static Filter createTotalFeesToken0Filter(final long totalFeesToken0) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalFeesToken0);
    return Filter.createMemCompFilter(TOTAL_FEES_TOKEN_0_OFFSET, _data);
  }

  public static Filter createTotalFeesClaimedToken0Filter(final long totalFeesClaimedToken0) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalFeesClaimedToken0);
    return Filter.createMemCompFilter(TOTAL_FEES_CLAIMED_TOKEN_0_OFFSET, _data);
  }

  public static Filter createTotalFeesToken1Filter(final long totalFeesToken1) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalFeesToken1);
    return Filter.createMemCompFilter(TOTAL_FEES_TOKEN_1_OFFSET, _data);
  }

  public static Filter createTotalFeesClaimedToken1Filter(final long totalFeesClaimedToken1) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalFeesClaimedToken1);
    return Filter.createMemCompFilter(TOTAL_FEES_CLAIMED_TOKEN_1_OFFSET, _data);
  }

  public static Filter createFundFeesToken0Filter(final long fundFeesToken0) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, fundFeesToken0);
    return Filter.createMemCompFilter(FUND_FEES_TOKEN_0_OFFSET, _data);
  }

  public static Filter createFundFeesToken1Filter(final long fundFeesToken1) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, fundFeesToken1);
    return Filter.createMemCompFilter(FUND_FEES_TOKEN_1_OFFSET, _data);
  }

  public static Filter createOpenTimeFilter(final long openTime) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, openTime);
    return Filter.createMemCompFilter(OPEN_TIME_OFFSET, _data);
  }

  public static Filter createRecentEpochFilter(final long recentEpoch) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, recentEpoch);
    return Filter.createMemCompFilter(RECENT_EPOCH_OFFSET, _data);
  }

  public static PoolState read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static PoolState read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static PoolState read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], PoolState> FACTORY = PoolState::read;

  public static PoolState read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var bump = new byte[1];
    i += Borsh.readArray(bump, _data, i);
    final var ammConfig = readPubKey(_data, i);
    i += 32;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var tokenMint0 = readPubKey(_data, i);
    i += 32;
    final var tokenMint1 = readPubKey(_data, i);
    i += 32;
    final var tokenVault0 = readPubKey(_data, i);
    i += 32;
    final var tokenVault1 = readPubKey(_data, i);
    i += 32;
    final var observationKey = readPubKey(_data, i);
    i += 32;
    final var mintDecimals0 = _data[i] & 0xFF;
    ++i;
    final var mintDecimals1 = _data[i] & 0xFF;
    ++i;
    final var tickSpacing = getInt16LE(_data, i);
    i += 2;
    final var liquidity = getInt128LE(_data, i);
    i += 16;
    final var sqrtPriceX64 = getInt128LE(_data, i);
    i += 16;
    final var tickCurrent = getInt32LE(_data, i);
    i += 4;
    final var padding3 = getInt16LE(_data, i);
    i += 2;
    final var padding4 = getInt16LE(_data, i);
    i += 2;
    final var feeGrowthGlobal0X64 = getInt128LE(_data, i);
    i += 16;
    final var feeGrowthGlobal1X64 = getInt128LE(_data, i);
    i += 16;
    final var protocolFeesToken0 = getInt64LE(_data, i);
    i += 8;
    final var protocolFeesToken1 = getInt64LE(_data, i);
    i += 8;
    final var swapInAmountToken0 = getInt128LE(_data, i);
    i += 16;
    final var swapOutAmountToken1 = getInt128LE(_data, i);
    i += 16;
    final var swapInAmountToken1 = getInt128LE(_data, i);
    i += 16;
    final var swapOutAmountToken0 = getInt128LE(_data, i);
    i += 16;
    final var status = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[7];
    i += Borsh.readArray(padding, _data, i);
    final var rewardInfos = new RewardInfo[3];
    i += Borsh.readArray(rewardInfos, RewardInfo::read, _data, i);
    final var tickArrayBitmap = new long[16];
    i += Borsh.readArray(tickArrayBitmap, _data, i);
    final var totalFeesToken0 = getInt64LE(_data, i);
    i += 8;
    final var totalFeesClaimedToken0 = getInt64LE(_data, i);
    i += 8;
    final var totalFeesToken1 = getInt64LE(_data, i);
    i += 8;
    final var totalFeesClaimedToken1 = getInt64LE(_data, i);
    i += 8;
    final var fundFeesToken0 = getInt64LE(_data, i);
    i += 8;
    final var fundFeesToken1 = getInt64LE(_data, i);
    i += 8;
    final var openTime = getInt64LE(_data, i);
    i += 8;
    final var recentEpoch = getInt64LE(_data, i);
    i += 8;
    final var padding1 = new long[24];
    i += Borsh.readArray(padding1, _data, i);
    final var padding2 = new long[32];
    Borsh.readArray(padding2, _data, i);
    return new PoolState(_address,
                         discriminator,
                         bump,
                         ammConfig,
                         owner,
                         tokenMint0,
                         tokenMint1,
                         tokenVault0,
                         tokenVault1,
                         observationKey,
                         mintDecimals0,
                         mintDecimals1,
                         tickSpacing,
                         liquidity,
                         sqrtPriceX64,
                         tickCurrent,
                         padding3,
                         padding4,
                         feeGrowthGlobal0X64,
                         feeGrowthGlobal1X64,
                         protocolFeesToken0,
                         protocolFeesToken1,
                         swapInAmountToken0,
                         swapOutAmountToken1,
                         swapInAmountToken1,
                         swapOutAmountToken0,
                         status,
                         padding,
                         rewardInfos,
                         tickArrayBitmap,
                         totalFeesToken0,
                         totalFeesClaimedToken0,
                         totalFeesToken1,
                         totalFeesClaimedToken1,
                         fundFeesToken0,
                         fundFeesToken1,
                         openTime,
                         recentEpoch,
                         padding1,
                         padding2);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    i += Borsh.writeArrayChecked(bump, 1, _data, i);
    ammConfig.write(_data, i);
    i += 32;
    owner.write(_data, i);
    i += 32;
    tokenMint0.write(_data, i);
    i += 32;
    tokenMint1.write(_data, i);
    i += 32;
    tokenVault0.write(_data, i);
    i += 32;
    tokenVault1.write(_data, i);
    i += 32;
    observationKey.write(_data, i);
    i += 32;
    _data[i] = (byte) mintDecimals0;
    ++i;
    _data[i] = (byte) mintDecimals1;
    ++i;
    putInt16LE(_data, i, tickSpacing);
    i += 2;
    putInt128LE(_data, i, liquidity);
    i += 16;
    putInt128LE(_data, i, sqrtPriceX64);
    i += 16;
    putInt32LE(_data, i, tickCurrent);
    i += 4;
    putInt16LE(_data, i, padding3);
    i += 2;
    putInt16LE(_data, i, padding4);
    i += 2;
    putInt128LE(_data, i, feeGrowthGlobal0X64);
    i += 16;
    putInt128LE(_data, i, feeGrowthGlobal1X64);
    i += 16;
    putInt64LE(_data, i, protocolFeesToken0);
    i += 8;
    putInt64LE(_data, i, protocolFeesToken1);
    i += 8;
    putInt128LE(_data, i, swapInAmountToken0);
    i += 16;
    putInt128LE(_data, i, swapOutAmountToken1);
    i += 16;
    putInt128LE(_data, i, swapInAmountToken1);
    i += 16;
    putInt128LE(_data, i, swapOutAmountToken0);
    i += 16;
    _data[i] = (byte) status;
    ++i;
    i += Borsh.writeArrayChecked(padding, 7, _data, i);
    i += Borsh.writeArrayChecked(rewardInfos, 3, _data, i);
    i += Borsh.writeArrayChecked(tickArrayBitmap, 16, _data, i);
    putInt64LE(_data, i, totalFeesToken0);
    i += 8;
    putInt64LE(_data, i, totalFeesClaimedToken0);
    i += 8;
    putInt64LE(_data, i, totalFeesToken1);
    i += 8;
    putInt64LE(_data, i, totalFeesClaimedToken1);
    i += 8;
    putInt64LE(_data, i, fundFeesToken0);
    i += 8;
    putInt64LE(_data, i, fundFeesToken1);
    i += 8;
    putInt64LE(_data, i, openTime);
    i += 8;
    putInt64LE(_data, i, recentEpoch);
    i += 8;
    i += Borsh.writeArrayChecked(padding1, 24, _data, i);
    i += Borsh.writeArrayChecked(padding2, 32, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
