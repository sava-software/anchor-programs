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

public record Constituent(PublicKey _address,
                          Discriminator discriminator,
                          // address of the constituent
                          PublicKey pubkey,
                          PublicKey mint,
                          PublicKey lpPool,
                          PublicKey vault,
                          // total fees received by the constituent. Positive = fees received, Negative = fees paid
                          BigInteger totalSwapFees,
                          // spot borrow-lend balance for constituent
                          ConstituentSpotBalance spotBalance,
                          long lastSpotBalanceTokenAmount,
                          long cumulativeSpotInterestAccruedTokenAmount,
                          // max deviation from target_weight allowed for the constituent
                          // precision: PERCENTAGE_PRECISION
                          long maxWeightDeviation,
                          // min fee charged on swaps to/from this constituent
                          // precision: PERCENTAGE_PRECISION
                          long swapFeeMin,
                          // max fee charged on swaps to/from this constituent
                          // precision: PERCENTAGE_PRECISION
                          long swapFeeMax,
                          // Max Borrow amount:
                          // precision: token precision
                          long maxBorrowTokenAmount,
                          // ata token balance in token precision
                          long vaultTokenBalance,
                          long lastOraclePrice,
                          long lastOracleSlot,
                          // Delay allowed for valid AUM calculation
                          long oracleStalenessThreshold,
                          long flashLoanInitialTokenAmount,
                          // Every swap to/from this constituent has a monotonically increasing id. This is the next id to use
                          long nextSwapId,
                          // percentable of derivatve weight to go to this specific derivative PERCENTAGE_PRECISION. Zero if no derivative weight
                          long derivativeWeight,
                          long volatility,
                          long constituentDerivativeDepegThreshold,
                          // The `constituent_index` of the parent constituent. -1 if it is a parent index
                          // Example: if in a pool with SOL (parent) and dSOL (derivative),
                          // SOL.constituent_index = 1, SOL.constituent_derivative_index = -1,
                          // dSOL.constituent_index = 2, dSOL.constituent_derivative_index = 1
                          int constituentDerivativeIndex,
                          int spotMarketIndex,
                          int constituentIndex,
                          int decimals,
                          int bump,
                          int vaultBump,
                          int gammaInventory,
                          int gammaExecution,
                          int xi,
                          int status,
                          int pausedOperations,
                          byte[] padding) implements Borsh {

  public static final int BYTES = 480;
  public static final int PADDING_LEN = 162;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int PUBKEY_OFFSET = 8;
  public static final int MINT_OFFSET = 40;
  public static final int LP_POOL_OFFSET = 72;
  public static final int VAULT_OFFSET = 104;
  public static final int TOTAL_SWAP_FEES_OFFSET = 136;
  public static final int SPOT_BALANCE_OFFSET = 152;
  public static final int LAST_SPOT_BALANCE_TOKEN_AMOUNT_OFFSET = 184;
  public static final int CUMULATIVE_SPOT_INTEREST_ACCRUED_TOKEN_AMOUNT_OFFSET = 192;
  public static final int MAX_WEIGHT_DEVIATION_OFFSET = 200;
  public static final int SWAP_FEE_MIN_OFFSET = 208;
  public static final int SWAP_FEE_MAX_OFFSET = 216;
  public static final int MAX_BORROW_TOKEN_AMOUNT_OFFSET = 224;
  public static final int VAULT_TOKEN_BALANCE_OFFSET = 232;
  public static final int LAST_ORACLE_PRICE_OFFSET = 240;
  public static final int LAST_ORACLE_SLOT_OFFSET = 248;
  public static final int ORACLE_STALENESS_THRESHOLD_OFFSET = 256;
  public static final int FLASH_LOAN_INITIAL_TOKEN_AMOUNT_OFFSET = 264;
  public static final int NEXT_SWAP_ID_OFFSET = 272;
  public static final int DERIVATIVE_WEIGHT_OFFSET = 280;
  public static final int VOLATILITY_OFFSET = 288;
  public static final int CONSTITUENT_DERIVATIVE_DEPEG_THRESHOLD_OFFSET = 296;
  public static final int CONSTITUENT_DERIVATIVE_INDEX_OFFSET = 304;
  public static final int SPOT_MARKET_INDEX_OFFSET = 306;
  public static final int CONSTITUENT_INDEX_OFFSET = 308;
  public static final int DECIMALS_OFFSET = 310;
  public static final int BUMP_OFFSET = 311;
  public static final int VAULT_BUMP_OFFSET = 312;
  public static final int GAMMA_INVENTORY_OFFSET = 313;
  public static final int GAMMA_EXECUTION_OFFSET = 314;
  public static final int XI_OFFSET = 315;
  public static final int STATUS_OFFSET = 316;
  public static final int PAUSED_OPERATIONS_OFFSET = 317;
  public static final int PADDING_OFFSET = 318;

  public static Filter createPubkeyFilter(final PublicKey pubkey) {
    return Filter.createMemCompFilter(PUBKEY_OFFSET, pubkey);
  }

  public static Filter createMintFilter(final PublicKey mint) {
    return Filter.createMemCompFilter(MINT_OFFSET, mint);
  }

  public static Filter createLpPoolFilter(final PublicKey lpPool) {
    return Filter.createMemCompFilter(LP_POOL_OFFSET, lpPool);
  }

  public static Filter createVaultFilter(final PublicKey vault) {
    return Filter.createMemCompFilter(VAULT_OFFSET, vault);
  }

  public static Filter createTotalSwapFeesFilter(final BigInteger totalSwapFees) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, totalSwapFees);
    return Filter.createMemCompFilter(TOTAL_SWAP_FEES_OFFSET, _data);
  }

  public static Filter createSpotBalanceFilter(final ConstituentSpotBalance spotBalance) {
    return Filter.createMemCompFilter(SPOT_BALANCE_OFFSET, spotBalance.write());
  }

  public static Filter createLastSpotBalanceTokenAmountFilter(final long lastSpotBalanceTokenAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastSpotBalanceTokenAmount);
    return Filter.createMemCompFilter(LAST_SPOT_BALANCE_TOKEN_AMOUNT_OFFSET, _data);
  }

  public static Filter createCumulativeSpotInterestAccruedTokenAmountFilter(final long cumulativeSpotInterestAccruedTokenAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, cumulativeSpotInterestAccruedTokenAmount);
    return Filter.createMemCompFilter(CUMULATIVE_SPOT_INTEREST_ACCRUED_TOKEN_AMOUNT_OFFSET, _data);
  }

  public static Filter createMaxWeightDeviationFilter(final long maxWeightDeviation) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxWeightDeviation);
    return Filter.createMemCompFilter(MAX_WEIGHT_DEVIATION_OFFSET, _data);
  }

  public static Filter createSwapFeeMinFilter(final long swapFeeMin) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, swapFeeMin);
    return Filter.createMemCompFilter(SWAP_FEE_MIN_OFFSET, _data);
  }

  public static Filter createSwapFeeMaxFilter(final long swapFeeMax) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, swapFeeMax);
    return Filter.createMemCompFilter(SWAP_FEE_MAX_OFFSET, _data);
  }

  public static Filter createMaxBorrowTokenAmountFilter(final long maxBorrowTokenAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxBorrowTokenAmount);
    return Filter.createMemCompFilter(MAX_BORROW_TOKEN_AMOUNT_OFFSET, _data);
  }

  public static Filter createVaultTokenBalanceFilter(final long vaultTokenBalance) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, vaultTokenBalance);
    return Filter.createMemCompFilter(VAULT_TOKEN_BALANCE_OFFSET, _data);
  }

  public static Filter createLastOraclePriceFilter(final long lastOraclePrice) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastOraclePrice);
    return Filter.createMemCompFilter(LAST_ORACLE_PRICE_OFFSET, _data);
  }

  public static Filter createLastOracleSlotFilter(final long lastOracleSlot) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastOracleSlot);
    return Filter.createMemCompFilter(LAST_ORACLE_SLOT_OFFSET, _data);
  }

  public static Filter createOracleStalenessThresholdFilter(final long oracleStalenessThreshold) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, oracleStalenessThreshold);
    return Filter.createMemCompFilter(ORACLE_STALENESS_THRESHOLD_OFFSET, _data);
  }

  public static Filter createFlashLoanInitialTokenAmountFilter(final long flashLoanInitialTokenAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, flashLoanInitialTokenAmount);
    return Filter.createMemCompFilter(FLASH_LOAN_INITIAL_TOKEN_AMOUNT_OFFSET, _data);
  }

  public static Filter createNextSwapIdFilter(final long nextSwapId) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, nextSwapId);
    return Filter.createMemCompFilter(NEXT_SWAP_ID_OFFSET, _data);
  }

  public static Filter createDerivativeWeightFilter(final long derivativeWeight) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, derivativeWeight);
    return Filter.createMemCompFilter(DERIVATIVE_WEIGHT_OFFSET, _data);
  }

  public static Filter createVolatilityFilter(final long volatility) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, volatility);
    return Filter.createMemCompFilter(VOLATILITY_OFFSET, _data);
  }

  public static Filter createConstituentDerivativeDepegThresholdFilter(final long constituentDerivativeDepegThreshold) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, constituentDerivativeDepegThreshold);
    return Filter.createMemCompFilter(CONSTITUENT_DERIVATIVE_DEPEG_THRESHOLD_OFFSET, _data);
  }

  public static Filter createConstituentDerivativeIndexFilter(final int constituentDerivativeIndex) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, constituentDerivativeIndex);
    return Filter.createMemCompFilter(CONSTITUENT_DERIVATIVE_INDEX_OFFSET, _data);
  }

  public static Filter createSpotMarketIndexFilter(final int spotMarketIndex) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, spotMarketIndex);
    return Filter.createMemCompFilter(SPOT_MARKET_INDEX_OFFSET, _data);
  }

  public static Filter createConstituentIndexFilter(final int constituentIndex) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, constituentIndex);
    return Filter.createMemCompFilter(CONSTITUENT_INDEX_OFFSET, _data);
  }

  public static Filter createDecimalsFilter(final int decimals) {
    return Filter.createMemCompFilter(DECIMALS_OFFSET, new byte[]{(byte) decimals});
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createVaultBumpFilter(final int vaultBump) {
    return Filter.createMemCompFilter(VAULT_BUMP_OFFSET, new byte[]{(byte) vaultBump});
  }

  public static Filter createGammaInventoryFilter(final int gammaInventory) {
    return Filter.createMemCompFilter(GAMMA_INVENTORY_OFFSET, new byte[]{(byte) gammaInventory});
  }

  public static Filter createGammaExecutionFilter(final int gammaExecution) {
    return Filter.createMemCompFilter(GAMMA_EXECUTION_OFFSET, new byte[]{(byte) gammaExecution});
  }

  public static Filter createXiFilter(final int xi) {
    return Filter.createMemCompFilter(XI_OFFSET, new byte[]{(byte) xi});
  }

  public static Filter createStatusFilter(final int status) {
    return Filter.createMemCompFilter(STATUS_OFFSET, new byte[]{(byte) status});
  }

  public static Filter createPausedOperationsFilter(final int pausedOperations) {
    return Filter.createMemCompFilter(PAUSED_OPERATIONS_OFFSET, new byte[]{(byte) pausedOperations});
  }

  public static Constituent read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Constituent read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Constituent read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Constituent> FACTORY = Constituent::read;

  public static Constituent read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var pubkey = readPubKey(_data, i);
    i += 32;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var lpPool = readPubKey(_data, i);
    i += 32;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var totalSwapFees = getInt128LE(_data, i);
    i += 16;
    final var spotBalance = ConstituentSpotBalance.read(_data, i);
    i += Borsh.len(spotBalance);
    final var lastSpotBalanceTokenAmount = getInt64LE(_data, i);
    i += 8;
    final var cumulativeSpotInterestAccruedTokenAmount = getInt64LE(_data, i);
    i += 8;
    final var maxWeightDeviation = getInt64LE(_data, i);
    i += 8;
    final var swapFeeMin = getInt64LE(_data, i);
    i += 8;
    final var swapFeeMax = getInt64LE(_data, i);
    i += 8;
    final var maxBorrowTokenAmount = getInt64LE(_data, i);
    i += 8;
    final var vaultTokenBalance = getInt64LE(_data, i);
    i += 8;
    final var lastOraclePrice = getInt64LE(_data, i);
    i += 8;
    final var lastOracleSlot = getInt64LE(_data, i);
    i += 8;
    final var oracleStalenessThreshold = getInt64LE(_data, i);
    i += 8;
    final var flashLoanInitialTokenAmount = getInt64LE(_data, i);
    i += 8;
    final var nextSwapId = getInt64LE(_data, i);
    i += 8;
    final var derivativeWeight = getInt64LE(_data, i);
    i += 8;
    final var volatility = getInt64LE(_data, i);
    i += 8;
    final var constituentDerivativeDepegThreshold = getInt64LE(_data, i);
    i += 8;
    final var constituentDerivativeIndex = getInt16LE(_data, i);
    i += 2;
    final var spotMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var constituentIndex = getInt16LE(_data, i);
    i += 2;
    final var decimals = _data[i] & 0xFF;
    ++i;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var vaultBump = _data[i] & 0xFF;
    ++i;
    final var gammaInventory = _data[i] & 0xFF;
    ++i;
    final var gammaExecution = _data[i] & 0xFF;
    ++i;
    final var xi = _data[i] & 0xFF;
    ++i;
    final var status = _data[i] & 0xFF;
    ++i;
    final var pausedOperations = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[162];
    Borsh.readArray(padding, _data, i);
    return new Constituent(_address,
                           discriminator,
                           pubkey,
                           mint,
                           lpPool,
                           vault,
                           totalSwapFees,
                           spotBalance,
                           lastSpotBalanceTokenAmount,
                           cumulativeSpotInterestAccruedTokenAmount,
                           maxWeightDeviation,
                           swapFeeMin,
                           swapFeeMax,
                           maxBorrowTokenAmount,
                           vaultTokenBalance,
                           lastOraclePrice,
                           lastOracleSlot,
                           oracleStalenessThreshold,
                           flashLoanInitialTokenAmount,
                           nextSwapId,
                           derivativeWeight,
                           volatility,
                           constituentDerivativeDepegThreshold,
                           constituentDerivativeIndex,
                           spotMarketIndex,
                           constituentIndex,
                           decimals,
                           bump,
                           vaultBump,
                           gammaInventory,
                           gammaExecution,
                           xi,
                           status,
                           pausedOperations,
                           padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    pubkey.write(_data, i);
    i += 32;
    mint.write(_data, i);
    i += 32;
    lpPool.write(_data, i);
    i += 32;
    vault.write(_data, i);
    i += 32;
    putInt128LE(_data, i, totalSwapFees);
    i += 16;
    i += Borsh.write(spotBalance, _data, i);
    putInt64LE(_data, i, lastSpotBalanceTokenAmount);
    i += 8;
    putInt64LE(_data, i, cumulativeSpotInterestAccruedTokenAmount);
    i += 8;
    putInt64LE(_data, i, maxWeightDeviation);
    i += 8;
    putInt64LE(_data, i, swapFeeMin);
    i += 8;
    putInt64LE(_data, i, swapFeeMax);
    i += 8;
    putInt64LE(_data, i, maxBorrowTokenAmount);
    i += 8;
    putInt64LE(_data, i, vaultTokenBalance);
    i += 8;
    putInt64LE(_data, i, lastOraclePrice);
    i += 8;
    putInt64LE(_data, i, lastOracleSlot);
    i += 8;
    putInt64LE(_data, i, oracleStalenessThreshold);
    i += 8;
    putInt64LE(_data, i, flashLoanInitialTokenAmount);
    i += 8;
    putInt64LE(_data, i, nextSwapId);
    i += 8;
    putInt64LE(_data, i, derivativeWeight);
    i += 8;
    putInt64LE(_data, i, volatility);
    i += 8;
    putInt64LE(_data, i, constituentDerivativeDepegThreshold);
    i += 8;
    putInt16LE(_data, i, constituentDerivativeIndex);
    i += 2;
    putInt16LE(_data, i, spotMarketIndex);
    i += 2;
    putInt16LE(_data, i, constituentIndex);
    i += 2;
    _data[i] = (byte) decimals;
    ++i;
    _data[i] = (byte) bump;
    ++i;
    _data[i] = (byte) vaultBump;
    ++i;
    _data[i] = (byte) gammaInventory;
    ++i;
    _data[i] = (byte) gammaExecution;
    ++i;
    _data[i] = (byte) xi;
    ++i;
    _data[i] = (byte) status;
    ++i;
    _data[i] = (byte) pausedOperations;
    ++i;
    i += Borsh.writeArrayChecked(padding, 162, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
