package software.sava.anchor.programs.metadao.autocrat.anchor.types;

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

public record Dao(PublicKey _address,
                  Discriminator discriminator,
                  int treasuryPdaBump,
                  PublicKey treasury,
                  PublicKey tokenMint,
                  PublicKey usdcMint,
                  int proposalCount,
                  int passThresholdBps,
                  long slotsPerProposal,
                  // For manipulation-resistance the TWAP is a time-weighted average observation,
                  // where observation tries to approximate price but can only move by
                  // `twap_max_observation_change_per_update` per update. Because it can only move
                  // a little bit per update, you need to check that it has a good initial observation.
                  // Otherwise, an attacker could create a very high initial observation in the pass
                  // market and a very low one in the fail market to force the proposal to pass.
                  // 
                  // We recommend setting an initial observation around the spot price of the token,
                  // and max observation change per update around 2% the spot price of the token.
                  // For example, if the spot price of META is $400, we'd recommend setting an initial
                  // observation of 400 (converted into the AMM prices) and a max observation change per
                  // update of 8 (also converted into the AMM prices). Observations can be updated once
                  // a minute, so 2% allows the proposal market to reach double the spot price or 0
                  // in 50 minutes.
                  BigInteger twapInitialObservation,
                  BigInteger twapMaxObservationChangePerUpdate,
                  // As an anti-spam measure and to help liquidity, you need to lock up some liquidity
                  // in both futarchic markets in order to create a proposal.
                  // 
                  // For example, for META, we can use a `min_quote_futarchic_liquidity` of
                  // 5000 * 1_000_000 (5000 USDC) and a `min_base_futarchic_liquidity` of
                  // 10 * 1_000_000_000 (10 META).
                  long minQuoteFutarchicLiquidity,
                  long minBaseFutarchicLiquidity,
                  long seqNum) implements Borsh {

  public static final int BYTES = 175;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int TREASURY_PDA_BUMP_OFFSET = 8;
  public static final int TREASURY_OFFSET = 9;
  public static final int TOKEN_MINT_OFFSET = 41;
  public static final int USDC_MINT_OFFSET = 73;
  public static final int PROPOSAL_COUNT_OFFSET = 105;
  public static final int PASS_THRESHOLD_BPS_OFFSET = 109;
  public static final int SLOTS_PER_PROPOSAL_OFFSET = 111;
  public static final int TWAP_INITIAL_OBSERVATION_OFFSET = 119;
  public static final int TWAP_MAX_OBSERVATION_CHANGE_PER_UPDATE_OFFSET = 135;
  public static final int MIN_QUOTE_FUTARCHIC_LIQUIDITY_OFFSET = 151;
  public static final int MIN_BASE_FUTARCHIC_LIQUIDITY_OFFSET = 159;
  public static final int SEQ_NUM_OFFSET = 167;

  public static Filter createTreasuryPdaBumpFilter(final int treasuryPdaBump) {
    return Filter.createMemCompFilter(TREASURY_PDA_BUMP_OFFSET, new byte[]{(byte) treasuryPdaBump});
  }

  public static Filter createTreasuryFilter(final PublicKey treasury) {
    return Filter.createMemCompFilter(TREASURY_OFFSET, treasury);
  }

  public static Filter createTokenMintFilter(final PublicKey tokenMint) {
    return Filter.createMemCompFilter(TOKEN_MINT_OFFSET, tokenMint);
  }

  public static Filter createUsdcMintFilter(final PublicKey usdcMint) {
    return Filter.createMemCompFilter(USDC_MINT_OFFSET, usdcMint);
  }

  public static Filter createProposalCountFilter(final int proposalCount) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, proposalCount);
    return Filter.createMemCompFilter(PROPOSAL_COUNT_OFFSET, _data);
  }

  public static Filter createPassThresholdBpsFilter(final int passThresholdBps) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, passThresholdBps);
    return Filter.createMemCompFilter(PASS_THRESHOLD_BPS_OFFSET, _data);
  }

  public static Filter createSlotsPerProposalFilter(final long slotsPerProposal) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, slotsPerProposal);
    return Filter.createMemCompFilter(SLOTS_PER_PROPOSAL_OFFSET, _data);
  }

  public static Filter createTwapInitialObservationFilter(final BigInteger twapInitialObservation) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, twapInitialObservation);
    return Filter.createMemCompFilter(TWAP_INITIAL_OBSERVATION_OFFSET, _data);
  }

  public static Filter createTwapMaxObservationChangePerUpdateFilter(final BigInteger twapMaxObservationChangePerUpdate) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, twapMaxObservationChangePerUpdate);
    return Filter.createMemCompFilter(TWAP_MAX_OBSERVATION_CHANGE_PER_UPDATE_OFFSET, _data);
  }

  public static Filter createMinQuoteFutarchicLiquidityFilter(final long minQuoteFutarchicLiquidity) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minQuoteFutarchicLiquidity);
    return Filter.createMemCompFilter(MIN_QUOTE_FUTARCHIC_LIQUIDITY_OFFSET, _data);
  }

  public static Filter createMinBaseFutarchicLiquidityFilter(final long minBaseFutarchicLiquidity) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minBaseFutarchicLiquidity);
    return Filter.createMemCompFilter(MIN_BASE_FUTARCHIC_LIQUIDITY_OFFSET, _data);
  }

  public static Filter createSeqNumFilter(final long seqNum) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, seqNum);
    return Filter.createMemCompFilter(SEQ_NUM_OFFSET, _data);
  }

  public static Dao read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Dao read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Dao read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Dao> FACTORY = Dao::read;

  public static Dao read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var treasuryPdaBump = _data[i] & 0xFF;
    ++i;
    final var treasury = readPubKey(_data, i);
    i += 32;
    final var tokenMint = readPubKey(_data, i);
    i += 32;
    final var usdcMint = readPubKey(_data, i);
    i += 32;
    final var proposalCount = getInt32LE(_data, i);
    i += 4;
    final var passThresholdBps = getInt16LE(_data, i);
    i += 2;
    final var slotsPerProposal = getInt64LE(_data, i);
    i += 8;
    final var twapInitialObservation = getInt128LE(_data, i);
    i += 16;
    final var twapMaxObservationChangePerUpdate = getInt128LE(_data, i);
    i += 16;
    final var minQuoteFutarchicLiquidity = getInt64LE(_data, i);
    i += 8;
    final var minBaseFutarchicLiquidity = getInt64LE(_data, i);
    i += 8;
    final var seqNum = getInt64LE(_data, i);
    return new Dao(_address,
                   discriminator,
                   treasuryPdaBump,
                   treasury,
                   tokenMint,
                   usdcMint,
                   proposalCount,
                   passThresholdBps,
                   slotsPerProposal,
                   twapInitialObservation,
                   twapMaxObservationChangePerUpdate,
                   minQuoteFutarchicLiquidity,
                   minBaseFutarchicLiquidity,
                   seqNum);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    _data[i] = (byte) treasuryPdaBump;
    ++i;
    treasury.write(_data, i);
    i += 32;
    tokenMint.write(_data, i);
    i += 32;
    usdcMint.write(_data, i);
    i += 32;
    putInt32LE(_data, i, proposalCount);
    i += 4;
    putInt16LE(_data, i, passThresholdBps);
    i += 2;
    putInt64LE(_data, i, slotsPerProposal);
    i += 8;
    putInt128LE(_data, i, twapInitialObservation);
    i += 16;
    putInt128LE(_data, i, twapMaxObservationChangePerUpdate);
    i += 16;
    putInt64LE(_data, i, minQuoteFutarchicLiquidity);
    i += 8;
    putInt64LE(_data, i, minBaseFutarchicLiquidity);
    i += 8;
    putInt64LE(_data, i, seqNum);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
