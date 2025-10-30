package software.sava.anchor.programs.drift.anchor.types;

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
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record LPPool(PublicKey _address,
                     Discriminator discriminator,
                     // address of the vault.
                     PublicKey pubkey,
                     PublicKey mint,
                     PublicKey whitelistMint,
                     PublicKey constituentTargetBase,
                     PublicKey constituentCorrelations,
                     // The current number of VaultConstituents in the vault, each constituent is pda(LPPool.address, constituent_index)
                     // which constituent is the quote, receives revenue pool distributions. (maybe this should just be implied idx 0)
                     // pub quote_constituent_index: u16,
                     // QUOTE_PRECISION: Max AUM, Prohibit minting new DLP beyond this
                     BigInteger maxAum,
                     // QUOTE_PRECISION: AUM of the vault in USD, updated lazily
                     BigInteger lastAum,
                     // QUOTE PRECISION: Cumulative quotes from settles
                     BigInteger cumulativeQuoteSentToPerpMarkets,
                     BigInteger cumulativeQuoteReceivedFromPerpMarkets,
                     // QUOTE_PRECISION: Total fees paid for minting and redeeming LP tokens
                     BigInteger totalMintRedeemFeesPaid,
                     // timestamp of last AUM slot
                     long lastAumSlot,
                     long maxSettleQuoteAmount,
                     // timestamp of last vAMM revenue rebalance
                     long padding,
                     // Every mint/redeem has a monotonically increasing id. This is the next id to use
                     long mintRedeemId,
                     long settleId,
                     // PERCENTAGE_PRECISION
                     long minMintFee,
                     long tokenSupply,
                     long volatility,
                     int constituents,
                     int quoteConsituentIndex,
                     int bump,
                     int gammaExecution,
                     int xi,
                     int targetOracleDelayFeeBpsPer10Slots,
                     int targetPositionDelayFeeBpsPer10Slots,
                     int lpPoolId,
                     byte[] padding2) implements Borsh {

  public static final int BYTES = 496;
  public static final int PADDING_2_LEN = 174;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int PUBKEY_OFFSET = 8;
  public static final int MINT_OFFSET = 40;
  public static final int WHITELIST_MINT_OFFSET = 72;
  public static final int CONSTITUENT_TARGET_BASE_OFFSET = 104;
  public static final int CONSTITUENT_CORRELATIONS_OFFSET = 136;
  public static final int MAX_AUM_OFFSET = 168;
  public static final int LAST_AUM_OFFSET = 184;
  public static final int CUMULATIVE_QUOTE_SENT_TO_PERP_MARKETS_OFFSET = 200;
  public static final int CUMULATIVE_QUOTE_RECEIVED_FROM_PERP_MARKETS_OFFSET = 216;
  public static final int TOTAL_MINT_REDEEM_FEES_PAID_OFFSET = 232;
  public static final int LAST_AUM_SLOT_OFFSET = 248;
  public static final int MAX_SETTLE_QUOTE_AMOUNT_OFFSET = 256;
  public static final int PADDING_OFFSET = 264;
  public static final int MINT_REDEEM_ID_OFFSET = 272;
  public static final int SETTLE_ID_OFFSET = 280;
  public static final int MIN_MINT_FEE_OFFSET = 288;
  public static final int TOKEN_SUPPLY_OFFSET = 296;
  public static final int VOLATILITY_OFFSET = 304;
  public static final int CONSTITUENTS_OFFSET = 312;
  public static final int QUOTE_CONSITUENT_INDEX_OFFSET = 314;
  public static final int BUMP_OFFSET = 316;
  public static final int GAMMA_EXECUTION_OFFSET = 317;
  public static final int XI_OFFSET = 318;
  public static final int TARGET_ORACLE_DELAY_FEE_BPS_PER_11_SLOTS_OFFSET = 319;
  public static final int TARGET_POSITION_DELAY_FEE_BPS_PER_11_SLOTS_OFFSET = 320;
  public static final int LP_POOL_ID_OFFSET = 321;
  public static final int PADDING_2_OFFSET = 322;

  public static Filter createPubkeyFilter(final PublicKey pubkey) {
    return Filter.createMemCompFilter(PUBKEY_OFFSET, pubkey);
  }

  public static Filter createMintFilter(final PublicKey mint) {
    return Filter.createMemCompFilter(MINT_OFFSET, mint);
  }

  public static Filter createWhitelistMintFilter(final PublicKey whitelistMint) {
    return Filter.createMemCompFilter(WHITELIST_MINT_OFFSET, whitelistMint);
  }

  public static Filter createConstituentTargetBaseFilter(final PublicKey constituentTargetBase) {
    return Filter.createMemCompFilter(CONSTITUENT_TARGET_BASE_OFFSET, constituentTargetBase);
  }

  public static Filter createConstituentCorrelationsFilter(final PublicKey constituentCorrelations) {
    return Filter.createMemCompFilter(CONSTITUENT_CORRELATIONS_OFFSET, constituentCorrelations);
  }

  public static Filter createMaxAumFilter(final BigInteger maxAum) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, maxAum);
    return Filter.createMemCompFilter(MAX_AUM_OFFSET, _data);
  }

  public static Filter createLastAumFilter(final BigInteger lastAum) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, lastAum);
    return Filter.createMemCompFilter(LAST_AUM_OFFSET, _data);
  }

  public static Filter createCumulativeQuoteSentToPerpMarketsFilter(final BigInteger cumulativeQuoteSentToPerpMarkets) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, cumulativeQuoteSentToPerpMarkets);
    return Filter.createMemCompFilter(CUMULATIVE_QUOTE_SENT_TO_PERP_MARKETS_OFFSET, _data);
  }

  public static Filter createCumulativeQuoteReceivedFromPerpMarketsFilter(final BigInteger cumulativeQuoteReceivedFromPerpMarkets) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, cumulativeQuoteReceivedFromPerpMarkets);
    return Filter.createMemCompFilter(CUMULATIVE_QUOTE_RECEIVED_FROM_PERP_MARKETS_OFFSET, _data);
  }

  public static Filter createTotalMintRedeemFeesPaidFilter(final BigInteger totalMintRedeemFeesPaid) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, totalMintRedeemFeesPaid);
    return Filter.createMemCompFilter(TOTAL_MINT_REDEEM_FEES_PAID_OFFSET, _data);
  }

  public static Filter createLastAumSlotFilter(final long lastAumSlot) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastAumSlot);
    return Filter.createMemCompFilter(LAST_AUM_SLOT_OFFSET, _data);
  }

  public static Filter createMaxSettleQuoteAmountFilter(final long maxSettleQuoteAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxSettleQuoteAmount);
    return Filter.createMemCompFilter(MAX_SETTLE_QUOTE_AMOUNT_OFFSET, _data);
  }

  public static Filter createPaddingFilter(final long padding) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, padding);
    return Filter.createMemCompFilter(PADDING_OFFSET, _data);
  }

  public static Filter createMintRedeemIdFilter(final long mintRedeemId) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, mintRedeemId);
    return Filter.createMemCompFilter(MINT_REDEEM_ID_OFFSET, _data);
  }

  public static Filter createSettleIdFilter(final long settleId) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, settleId);
    return Filter.createMemCompFilter(SETTLE_ID_OFFSET, _data);
  }

  public static Filter createMinMintFeeFilter(final long minMintFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minMintFee);
    return Filter.createMemCompFilter(MIN_MINT_FEE_OFFSET, _data);
  }

  public static Filter createTokenSupplyFilter(final long tokenSupply) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, tokenSupply);
    return Filter.createMemCompFilter(TOKEN_SUPPLY_OFFSET, _data);
  }

  public static Filter createVolatilityFilter(final long volatility) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, volatility);
    return Filter.createMemCompFilter(VOLATILITY_OFFSET, _data);
  }

  public static Filter createConstituentsFilter(final int constituents) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, constituents);
    return Filter.createMemCompFilter(CONSTITUENTS_OFFSET, _data);
  }

  public static Filter createQuoteConsituentIndexFilter(final int quoteConsituentIndex) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, quoteConsituentIndex);
    return Filter.createMemCompFilter(QUOTE_CONSITUENT_INDEX_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createGammaExecutionFilter(final int gammaExecution) {
    return Filter.createMemCompFilter(GAMMA_EXECUTION_OFFSET, new byte[]{(byte) gammaExecution});
  }

  public static Filter createXiFilter(final int xi) {
    return Filter.createMemCompFilter(XI_OFFSET, new byte[]{(byte) xi});
  }

  public static Filter createTargetOracleDelayFeeBpsPer10SlotsFilter(final int targetOracleDelayFeeBpsPer10Slots) {
    return Filter.createMemCompFilter(TARGET_ORACLE_DELAY_FEE_BPS_PER_11_SLOTS_OFFSET, new byte[]{(byte) targetOracleDelayFeeBpsPer10Slots});
  }

  public static Filter createTargetPositionDelayFeeBpsPer10SlotsFilter(final int targetPositionDelayFeeBpsPer10Slots) {
    return Filter.createMemCompFilter(TARGET_POSITION_DELAY_FEE_BPS_PER_11_SLOTS_OFFSET, new byte[]{(byte) targetPositionDelayFeeBpsPer10Slots});
  }

  public static Filter createLpPoolIdFilter(final int lpPoolId) {
    return Filter.createMemCompFilter(LP_POOL_ID_OFFSET, new byte[]{(byte) lpPoolId});
  }

  public static LPPool read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static LPPool read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static LPPool read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], LPPool> FACTORY = LPPool::read;

  public static LPPool read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var pubkey = readPubKey(_data, i);
    i += 32;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var whitelistMint = readPubKey(_data, i);
    i += 32;
    final var constituentTargetBase = readPubKey(_data, i);
    i += 32;
    final var constituentCorrelations = readPubKey(_data, i);
    i += 32;
    final var maxAum = getInt128LE(_data, i);
    i += 16;
    final var lastAum = getInt128LE(_data, i);
    i += 16;
    final var cumulativeQuoteSentToPerpMarkets = getInt128LE(_data, i);
    i += 16;
    final var cumulativeQuoteReceivedFromPerpMarkets = getInt128LE(_data, i);
    i += 16;
    final var totalMintRedeemFeesPaid = getInt128LE(_data, i);
    i += 16;
    final var lastAumSlot = getInt64LE(_data, i);
    i += 8;
    final var maxSettleQuoteAmount = getInt64LE(_data, i);
    i += 8;
    final var padding = getInt64LE(_data, i);
    i += 8;
    final var mintRedeemId = getInt64LE(_data, i);
    i += 8;
    final var settleId = getInt64LE(_data, i);
    i += 8;
    final var minMintFee = getInt64LE(_data, i);
    i += 8;
    final var tokenSupply = getInt64LE(_data, i);
    i += 8;
    final var volatility = getInt64LE(_data, i);
    i += 8;
    final var constituents = getInt16LE(_data, i);
    i += 2;
    final var quoteConsituentIndex = getInt16LE(_data, i);
    i += 2;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var gammaExecution = _data[i] & 0xFF;
    ++i;
    final var xi = _data[i] & 0xFF;
    ++i;
    final var targetOracleDelayFeeBpsPer10Slots = _data[i] & 0xFF;
    ++i;
    final var targetPositionDelayFeeBpsPer10Slots = _data[i] & 0xFF;
    ++i;
    final var lpPoolId = _data[i] & 0xFF;
    ++i;
    final var padding2 = new byte[174];
    Borsh.readArray(padding2, _data, i);
    return new LPPool(_address,
                      discriminator,
                      pubkey,
                      mint,
                      whitelistMint,
                      constituentTargetBase,
                      constituentCorrelations,
                      maxAum,
                      lastAum,
                      cumulativeQuoteSentToPerpMarkets,
                      cumulativeQuoteReceivedFromPerpMarkets,
                      totalMintRedeemFeesPaid,
                      lastAumSlot,
                      maxSettleQuoteAmount,
                      padding,
                      mintRedeemId,
                      settleId,
                      minMintFee,
                      tokenSupply,
                      volatility,
                      constituents,
                      quoteConsituentIndex,
                      bump,
                      gammaExecution,
                      xi,
                      targetOracleDelayFeeBpsPer10Slots,
                      targetPositionDelayFeeBpsPer10Slots,
                      lpPoolId,
                      padding2);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    pubkey.write(_data, i);
    i += 32;
    mint.write(_data, i);
    i += 32;
    whitelistMint.write(_data, i);
    i += 32;
    constituentTargetBase.write(_data, i);
    i += 32;
    constituentCorrelations.write(_data, i);
    i += 32;
    putInt128LE(_data, i, maxAum);
    i += 16;
    putInt128LE(_data, i, lastAum);
    i += 16;
    putInt128LE(_data, i, cumulativeQuoteSentToPerpMarkets);
    i += 16;
    putInt128LE(_data, i, cumulativeQuoteReceivedFromPerpMarkets);
    i += 16;
    putInt128LE(_data, i, totalMintRedeemFeesPaid);
    i += 16;
    putInt64LE(_data, i, lastAumSlot);
    i += 8;
    putInt64LE(_data, i, maxSettleQuoteAmount);
    i += 8;
    putInt64LE(_data, i, padding);
    i += 8;
    putInt64LE(_data, i, mintRedeemId);
    i += 8;
    putInt64LE(_data, i, settleId);
    i += 8;
    putInt64LE(_data, i, minMintFee);
    i += 8;
    putInt64LE(_data, i, tokenSupply);
    i += 8;
    putInt64LE(_data, i, volatility);
    i += 8;
    putInt16LE(_data, i, constituents);
    i += 2;
    putInt16LE(_data, i, quoteConsituentIndex);
    i += 2;
    _data[i] = (byte) bump;
    ++i;
    _data[i] = (byte) gammaExecution;
    ++i;
    _data[i] = (byte) xi;
    ++i;
    _data[i] = (byte) targetOracleDelayFeeBpsPer10Slots;
    ++i;
    _data[i] = (byte) targetPositionDelayFeeBpsPer10Slots;
    ++i;
    _data[i] = (byte) lpPoolId;
    ++i;
    i += Borsh.writeArrayChecked(padding2, 174, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
