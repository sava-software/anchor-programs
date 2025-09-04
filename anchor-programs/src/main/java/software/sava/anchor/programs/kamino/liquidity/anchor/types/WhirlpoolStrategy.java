package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import java.math.BigInteger;

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

public record WhirlpoolStrategy(PublicKey _address,
                                Discriminator discriminator,
                                PublicKey adminAuthority,
                                PublicKey globalConfig,
                                PublicKey baseVaultAuthority,
                                long baseVaultAuthorityBump,
                                PublicKey pool,
                                PublicKey poolTokenVaultA,
                                PublicKey poolTokenVaultB,
                                PublicKey tickArrayLower,
                                PublicKey tickArrayUpper,
                                PublicKey position,
                                PublicKey positionMint,
                                PublicKey positionMetadata,
                                PublicKey positionTokenAccount,
                                PublicKey tokenAVault,
                                PublicKey tokenBVault,
                                PublicKey[] deprecated0,
                                long[] deprecated1,
                                PublicKey tokenAMint,
                                PublicKey tokenBMint,
                                long tokenAMintDecimals,
                                long tokenBMintDecimals,
                                long tokenAAmounts,
                                long tokenBAmounts,
                                long tokenACollateralId,
                                long tokenBCollateralId,
                                PublicKey scopePrices,
                                PublicKey deprecated2,
                                PublicKey sharesMint,
                                long sharesMintDecimals,
                                PublicKey sharesMintAuthority,
                                long sharesMintAuthorityBump,
                                long sharesIssued,
                                long status,
                                long reward0Amount,
                                PublicKey reward0Vault,
                                long reward0CollateralId,
                                long reward0Decimals,
                                long reward1Amount,
                                PublicKey reward1Vault,
                                long reward1CollateralId,
                                long reward1Decimals,
                                long reward2Amount,
                                PublicKey reward2Vault,
                                long reward2CollateralId,
                                long reward2Decimals,
                                long depositCapUsd,
                                long feesACumulative,
                                long feesBCumulative,
                                long reward0AmountCumulative,
                                long reward1AmountCumulative,
                                long reward2AmountCumulative,
                                long depositCapUsdPerIxn,
                                WithdrawalCaps withdrawalCapA,
                                WithdrawalCaps withdrawalCapB,
                                long maxPriceDeviationBps,
                                int swapVaultMaxSlippageBps,
                                int swapVaultMaxSlippageFromReferenceBps,
                                long strategyType,
                                long padding0,
                                long withdrawFee,
                                long feesFee,
                                long reward0Fee,
                                long reward1Fee,
                                long reward2Fee,
                                long positionTimestamp,
                                KaminoRewardInfo[] kaminoRewards,
                                long strategyDex,
                                PublicKey raydiumProtocolPositionOrBaseVaultAuthority,
                                long allowDepositWithoutInvest,
                                PublicKey raydiumPoolConfigOrBaseVaultAuthority,
                                int depositBlocked,
                                int creationStatus,
                                int investBlocked,
                                // share_calculation_method can be either DOLAR_BASED=0 or PROPORTION_BASED=1
                                int shareCalculationMethod,
                                int withdrawBlocked,
                                int reservedFlag2,
                                int localAdminBlocked,
                                int flashVaultSwapAllowed,
                                Price referenceSwapPriceA,
                                Price referenceSwapPriceB,
                                int isCommunity,
                                int rebalanceType,
                                byte[] padding1,
                                RebalanceRaw rebalanceRaw,
                                byte[] padding2,
                                long tokenAFeesFromRewardsCumulative,
                                long tokenBFeesFromRewardsCumulative,
                                PublicKey strategyLookupTable,
                                long lastSwapUnevenStepTimestamp,
                                PublicKey farm,
                                WithdrawalCaps rebalancesCap,
                                PublicKey swapUnevenAuthority,
                                PublicKey tokenATokenProgram,
                                PublicKey tokenBTokenProgram,
                                PublicKey pendingAdmin,
                                long padding3,
                                BigInteger[] padding4,
                                BigInteger[] padding5,
                                BigInteger[] padding6,
                                BigInteger[] padding7) implements Borsh {

  public static final int BYTES = 4064;
  public static final int DEPRECATED_0_LEN = 2;
  public static final int DEPRECATED_1_LEN = 2;
  public static final int KAMINO_REWARDS_LEN = 3;
  public static final int PADDING_1_LEN = 6;
  public static final int PADDING_2_LEN = 7;
  public static final int PADDING_4_LEN = 13;
  public static final int PADDING_5_LEN = 32;
  public static final int PADDING_6_LEN = 32;
  public static final int PADDING_7_LEN = 32;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int ADMIN_AUTHORITY_OFFSET = 8;
  public static final int GLOBAL_CONFIG_OFFSET = 40;
  public static final int BASE_VAULT_AUTHORITY_OFFSET = 72;
  public static final int BASE_VAULT_AUTHORITY_BUMP_OFFSET = 104;
  public static final int POOL_OFFSET = 112;
  public static final int POOL_TOKEN_VAULT_A_OFFSET = 144;
  public static final int POOL_TOKEN_VAULT_B_OFFSET = 176;
  public static final int TICK_ARRAY_LOWER_OFFSET = 208;
  public static final int TICK_ARRAY_UPPER_OFFSET = 240;
  public static final int POSITION_OFFSET = 272;
  public static final int POSITION_MINT_OFFSET = 304;
  public static final int POSITION_METADATA_OFFSET = 336;
  public static final int POSITION_TOKEN_ACCOUNT_OFFSET = 368;
  public static final int TOKEN_A_VAULT_OFFSET = 400;
  public static final int TOKEN_B_VAULT_OFFSET = 432;
  public static final int DEPRECATED_0_OFFSET = 464;
  public static final int DEPRECATED_1_OFFSET = 528;
  public static final int TOKEN_A_MINT_OFFSET = 544;
  public static final int TOKEN_B_MINT_OFFSET = 576;
  public static final int TOKEN_A_MINT_DECIMALS_OFFSET = 608;
  public static final int TOKEN_B_MINT_DECIMALS_OFFSET = 616;
  public static final int TOKEN_A_AMOUNTS_OFFSET = 624;
  public static final int TOKEN_B_AMOUNTS_OFFSET = 632;
  public static final int TOKEN_A_COLLATERAL_ID_OFFSET = 640;
  public static final int TOKEN_B_COLLATERAL_ID_OFFSET = 648;
  public static final int SCOPE_PRICES_OFFSET = 656;
  public static final int DEPRECATED_2_OFFSET = 688;
  public static final int SHARES_MINT_OFFSET = 720;
  public static final int SHARES_MINT_DECIMALS_OFFSET = 752;
  public static final int SHARES_MINT_AUTHORITY_OFFSET = 760;
  public static final int SHARES_MINT_AUTHORITY_BUMP_OFFSET = 792;
  public static final int SHARES_ISSUED_OFFSET = 800;
  public static final int STATUS_OFFSET = 808;
  public static final int REWARD_0_AMOUNT_OFFSET = 816;
  public static final int REWARD_0_VAULT_OFFSET = 824;
  public static final int REWARD_0_COLLATERAL_ID_OFFSET = 856;
  public static final int REWARD_0_DECIMALS_OFFSET = 864;
  public static final int REWARD_1_AMOUNT_OFFSET = 872;
  public static final int REWARD_1_VAULT_OFFSET = 880;
  public static final int REWARD_1_COLLATERAL_ID_OFFSET = 912;
  public static final int REWARD_1_DECIMALS_OFFSET = 920;
  public static final int REWARD_2_AMOUNT_OFFSET = 928;
  public static final int REWARD_2_VAULT_OFFSET = 936;
  public static final int REWARD_2_COLLATERAL_ID_OFFSET = 968;
  public static final int REWARD_2_DECIMALS_OFFSET = 976;
  public static final int DEPOSIT_CAP_USD_OFFSET = 984;
  public static final int FEES_A_CUMULATIVE_OFFSET = 992;
  public static final int FEES_B_CUMULATIVE_OFFSET = 1000;
  public static final int REWARD_0_AMOUNT_CUMULATIVE_OFFSET = 1008;
  public static final int REWARD_1_AMOUNT_CUMULATIVE_OFFSET = 1016;
  public static final int REWARD_2_AMOUNT_CUMULATIVE_OFFSET = 1024;
  public static final int DEPOSIT_CAP_USD_PER_IXN_OFFSET = 1032;
  public static final int WITHDRAWAL_CAP_A_OFFSET = 1040;
  public static final int WITHDRAWAL_CAP_B_OFFSET = 1072;
  public static final int MAX_PRICE_DEVIATION_BPS_OFFSET = 1104;
  public static final int SWAP_VAULT_MAX_SLIPPAGE_BPS_OFFSET = 1112;
  public static final int SWAP_VAULT_MAX_SLIPPAGE_FROM_REFERENCE_BPS_OFFSET = 1116;
  public static final int STRATEGY_TYPE_OFFSET = 1120;
  public static final int PADDING_0_OFFSET = 1128;
  public static final int WITHDRAW_FEE_OFFSET = 1136;
  public static final int FEES_FEE_OFFSET = 1144;
  public static final int REWARD_0_FEE_OFFSET = 1152;
  public static final int REWARD_1_FEE_OFFSET = 1160;
  public static final int REWARD_2_FEE_OFFSET = 1168;
  public static final int POSITION_TIMESTAMP_OFFSET = 1176;
  public static final int KAMINO_REWARDS_OFFSET = 1184;
  public static final int STRATEGY_DEX_OFFSET = 1544;
  public static final int RAYDIUM_PROTOCOL_POSITION_OR_BASE_VAULT_AUTHORITY_OFFSET = 1552;
  public static final int ALLOW_DEPOSIT_WITHOUT_INVEST_OFFSET = 1584;
  public static final int RAYDIUM_POOL_CONFIG_OR_BASE_VAULT_AUTHORITY_OFFSET = 1592;
  public static final int DEPOSIT_BLOCKED_OFFSET = 1624;
  public static final int CREATION_STATUS_OFFSET = 1625;
  public static final int INVEST_BLOCKED_OFFSET = 1626;
  public static final int SHARE_CALCULATION_METHOD_OFFSET = 1627;
  public static final int WITHDRAW_BLOCKED_OFFSET = 1628;
  public static final int RESERVED_FLAG_2_OFFSET = 1629;
  public static final int LOCAL_ADMIN_BLOCKED_OFFSET = 1630;
  public static final int FLASH_VAULT_SWAP_ALLOWED_OFFSET = 1631;
  public static final int REFERENCE_SWAP_PRICE_A_OFFSET = 1632;
  public static final int REFERENCE_SWAP_PRICE_B_OFFSET = 1648;
  public static final int IS_COMMUNITY_OFFSET = 1664;
  public static final int REBALANCE_TYPE_OFFSET = 1665;
  public static final int PADDING_1_OFFSET = 1666;
  public static final int REBALANCE_RAW_OFFSET = 1672;
  public static final int PADDING_2_OFFSET = 2057;
  public static final int TOKEN_A_FEES_FROM_REWARDS_CUMULATIVE_OFFSET = 2064;
  public static final int TOKEN_B_FEES_FROM_REWARDS_CUMULATIVE_OFFSET = 2072;
  public static final int STRATEGY_LOOKUP_TABLE_OFFSET = 2080;
  public static final int LAST_SWAP_UNEVEN_STEP_TIMESTAMP_OFFSET = 2112;
  public static final int FARM_OFFSET = 2120;
  public static final int REBALANCES_CAP_OFFSET = 2152;
  public static final int SWAP_UNEVEN_AUTHORITY_OFFSET = 2184;
  public static final int TOKEN_A_TOKEN_PROGRAM_OFFSET = 2216;
  public static final int TOKEN_B_TOKEN_PROGRAM_OFFSET = 2248;
  public static final int PENDING_ADMIN_OFFSET = 2280;
  public static final int PADDING_3_OFFSET = 2312;
  public static final int PADDING_4_OFFSET = 2320;
  public static final int PADDING_5_OFFSET = 2528;
  public static final int PADDING_6_OFFSET = 3040;
  public static final int PADDING_7_OFFSET = 3552;

  public static Filter createAdminAuthorityFilter(final PublicKey adminAuthority) {
    return Filter.createMemCompFilter(ADMIN_AUTHORITY_OFFSET, adminAuthority);
  }

  public static Filter createGlobalConfigFilter(final PublicKey globalConfig) {
    return Filter.createMemCompFilter(GLOBAL_CONFIG_OFFSET, globalConfig);
  }

  public static Filter createBaseVaultAuthorityFilter(final PublicKey baseVaultAuthority) {
    return Filter.createMemCompFilter(BASE_VAULT_AUTHORITY_OFFSET, baseVaultAuthority);
  }

  public static Filter createBaseVaultAuthorityBumpFilter(final long baseVaultAuthorityBump) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, baseVaultAuthorityBump);
    return Filter.createMemCompFilter(BASE_VAULT_AUTHORITY_BUMP_OFFSET, _data);
  }

  public static Filter createPoolFilter(final PublicKey pool) {
    return Filter.createMemCompFilter(POOL_OFFSET, pool);
  }

  public static Filter createPoolTokenVaultAFilter(final PublicKey poolTokenVaultA) {
    return Filter.createMemCompFilter(POOL_TOKEN_VAULT_A_OFFSET, poolTokenVaultA);
  }

  public static Filter createPoolTokenVaultBFilter(final PublicKey poolTokenVaultB) {
    return Filter.createMemCompFilter(POOL_TOKEN_VAULT_B_OFFSET, poolTokenVaultB);
  }

  public static Filter createTickArrayLowerFilter(final PublicKey tickArrayLower) {
    return Filter.createMemCompFilter(TICK_ARRAY_LOWER_OFFSET, tickArrayLower);
  }

  public static Filter createTickArrayUpperFilter(final PublicKey tickArrayUpper) {
    return Filter.createMemCompFilter(TICK_ARRAY_UPPER_OFFSET, tickArrayUpper);
  }

  public static Filter createPositionFilter(final PublicKey position) {
    return Filter.createMemCompFilter(POSITION_OFFSET, position);
  }

  public static Filter createPositionMintFilter(final PublicKey positionMint) {
    return Filter.createMemCompFilter(POSITION_MINT_OFFSET, positionMint);
  }

  public static Filter createPositionMetadataFilter(final PublicKey positionMetadata) {
    return Filter.createMemCompFilter(POSITION_METADATA_OFFSET, positionMetadata);
  }

  public static Filter createPositionTokenAccountFilter(final PublicKey positionTokenAccount) {
    return Filter.createMemCompFilter(POSITION_TOKEN_ACCOUNT_OFFSET, positionTokenAccount);
  }

  public static Filter createTokenAVaultFilter(final PublicKey tokenAVault) {
    return Filter.createMemCompFilter(TOKEN_A_VAULT_OFFSET, tokenAVault);
  }

  public static Filter createTokenBVaultFilter(final PublicKey tokenBVault) {
    return Filter.createMemCompFilter(TOKEN_B_VAULT_OFFSET, tokenBVault);
  }

  public static Filter createTokenAMintFilter(final PublicKey tokenAMint) {
    return Filter.createMemCompFilter(TOKEN_A_MINT_OFFSET, tokenAMint);
  }

  public static Filter createTokenBMintFilter(final PublicKey tokenBMint) {
    return Filter.createMemCompFilter(TOKEN_B_MINT_OFFSET, tokenBMint);
  }

  public static Filter createTokenAMintDecimalsFilter(final long tokenAMintDecimals) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, tokenAMintDecimals);
    return Filter.createMemCompFilter(TOKEN_A_MINT_DECIMALS_OFFSET, _data);
  }

  public static Filter createTokenBMintDecimalsFilter(final long tokenBMintDecimals) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, tokenBMintDecimals);
    return Filter.createMemCompFilter(TOKEN_B_MINT_DECIMALS_OFFSET, _data);
  }

  public static Filter createTokenAAmountsFilter(final long tokenAAmounts) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, tokenAAmounts);
    return Filter.createMemCompFilter(TOKEN_A_AMOUNTS_OFFSET, _data);
  }

  public static Filter createTokenBAmountsFilter(final long tokenBAmounts) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, tokenBAmounts);
    return Filter.createMemCompFilter(TOKEN_B_AMOUNTS_OFFSET, _data);
  }

  public static Filter createTokenACollateralIdFilter(final long tokenACollateralId) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, tokenACollateralId);
    return Filter.createMemCompFilter(TOKEN_A_COLLATERAL_ID_OFFSET, _data);
  }

  public static Filter createTokenBCollateralIdFilter(final long tokenBCollateralId) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, tokenBCollateralId);
    return Filter.createMemCompFilter(TOKEN_B_COLLATERAL_ID_OFFSET, _data);
  }

  public static Filter createScopePricesFilter(final PublicKey scopePrices) {
    return Filter.createMemCompFilter(SCOPE_PRICES_OFFSET, scopePrices);
  }

  public static Filter createDeprecated2Filter(final PublicKey deprecated2) {
    return Filter.createMemCompFilter(DEPRECATED_2_OFFSET, deprecated2);
  }

  public static Filter createSharesMintFilter(final PublicKey sharesMint) {
    return Filter.createMemCompFilter(SHARES_MINT_OFFSET, sharesMint);
  }

  public static Filter createSharesMintDecimalsFilter(final long sharesMintDecimals) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, sharesMintDecimals);
    return Filter.createMemCompFilter(SHARES_MINT_DECIMALS_OFFSET, _data);
  }

  public static Filter createSharesMintAuthorityFilter(final PublicKey sharesMintAuthority) {
    return Filter.createMemCompFilter(SHARES_MINT_AUTHORITY_OFFSET, sharesMintAuthority);
  }

  public static Filter createSharesMintAuthorityBumpFilter(final long sharesMintAuthorityBump) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, sharesMintAuthorityBump);
    return Filter.createMemCompFilter(SHARES_MINT_AUTHORITY_BUMP_OFFSET, _data);
  }

  public static Filter createSharesIssuedFilter(final long sharesIssued) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, sharesIssued);
    return Filter.createMemCompFilter(SHARES_ISSUED_OFFSET, _data);
  }

  public static Filter createStatusFilter(final long status) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, status);
    return Filter.createMemCompFilter(STATUS_OFFSET, _data);
  }

  public static Filter createReward0AmountFilter(final long reward0Amount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, reward0Amount);
    return Filter.createMemCompFilter(REWARD_0_AMOUNT_OFFSET, _data);
  }

  public static Filter createReward0VaultFilter(final PublicKey reward0Vault) {
    return Filter.createMemCompFilter(REWARD_0_VAULT_OFFSET, reward0Vault);
  }

  public static Filter createReward0CollateralIdFilter(final long reward0CollateralId) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, reward0CollateralId);
    return Filter.createMemCompFilter(REWARD_0_COLLATERAL_ID_OFFSET, _data);
  }

  public static Filter createReward0DecimalsFilter(final long reward0Decimals) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, reward0Decimals);
    return Filter.createMemCompFilter(REWARD_0_DECIMALS_OFFSET, _data);
  }

  public static Filter createReward1AmountFilter(final long reward1Amount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, reward1Amount);
    return Filter.createMemCompFilter(REWARD_1_AMOUNT_OFFSET, _data);
  }

  public static Filter createReward1VaultFilter(final PublicKey reward1Vault) {
    return Filter.createMemCompFilter(REWARD_1_VAULT_OFFSET, reward1Vault);
  }

  public static Filter createReward1CollateralIdFilter(final long reward1CollateralId) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, reward1CollateralId);
    return Filter.createMemCompFilter(REWARD_1_COLLATERAL_ID_OFFSET, _data);
  }

  public static Filter createReward1DecimalsFilter(final long reward1Decimals) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, reward1Decimals);
    return Filter.createMemCompFilter(REWARD_1_DECIMALS_OFFSET, _data);
  }

  public static Filter createReward2AmountFilter(final long reward2Amount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, reward2Amount);
    return Filter.createMemCompFilter(REWARD_2_AMOUNT_OFFSET, _data);
  }

  public static Filter createReward2VaultFilter(final PublicKey reward2Vault) {
    return Filter.createMemCompFilter(REWARD_2_VAULT_OFFSET, reward2Vault);
  }

  public static Filter createReward2CollateralIdFilter(final long reward2CollateralId) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, reward2CollateralId);
    return Filter.createMemCompFilter(REWARD_2_COLLATERAL_ID_OFFSET, _data);
  }

  public static Filter createReward2DecimalsFilter(final long reward2Decimals) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, reward2Decimals);
    return Filter.createMemCompFilter(REWARD_2_DECIMALS_OFFSET, _data);
  }

  public static Filter createDepositCapUsdFilter(final long depositCapUsd) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, depositCapUsd);
    return Filter.createMemCompFilter(DEPOSIT_CAP_USD_OFFSET, _data);
  }

  public static Filter createFeesACumulativeFilter(final long feesACumulative) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, feesACumulative);
    return Filter.createMemCompFilter(FEES_A_CUMULATIVE_OFFSET, _data);
  }

  public static Filter createFeesBCumulativeFilter(final long feesBCumulative) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, feesBCumulative);
    return Filter.createMemCompFilter(FEES_B_CUMULATIVE_OFFSET, _data);
  }

  public static Filter createReward0AmountCumulativeFilter(final long reward0AmountCumulative) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, reward0AmountCumulative);
    return Filter.createMemCompFilter(REWARD_0_AMOUNT_CUMULATIVE_OFFSET, _data);
  }

  public static Filter createReward1AmountCumulativeFilter(final long reward1AmountCumulative) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, reward1AmountCumulative);
    return Filter.createMemCompFilter(REWARD_1_AMOUNT_CUMULATIVE_OFFSET, _data);
  }

  public static Filter createReward2AmountCumulativeFilter(final long reward2AmountCumulative) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, reward2AmountCumulative);
    return Filter.createMemCompFilter(REWARD_2_AMOUNT_CUMULATIVE_OFFSET, _data);
  }

  public static Filter createDepositCapUsdPerIxnFilter(final long depositCapUsdPerIxn) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, depositCapUsdPerIxn);
    return Filter.createMemCompFilter(DEPOSIT_CAP_USD_PER_IXN_OFFSET, _data);
  }

  public static Filter createWithdrawalCapAFilter(final WithdrawalCaps withdrawalCapA) {
    return Filter.createMemCompFilter(WITHDRAWAL_CAP_A_OFFSET, withdrawalCapA.write());
  }

  public static Filter createWithdrawalCapBFilter(final WithdrawalCaps withdrawalCapB) {
    return Filter.createMemCompFilter(WITHDRAWAL_CAP_B_OFFSET, withdrawalCapB.write());
  }

  public static Filter createMaxPriceDeviationBpsFilter(final long maxPriceDeviationBps) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxPriceDeviationBps);
    return Filter.createMemCompFilter(MAX_PRICE_DEVIATION_BPS_OFFSET, _data);
  }

  public static Filter createSwapVaultMaxSlippageBpsFilter(final int swapVaultMaxSlippageBps) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, swapVaultMaxSlippageBps);
    return Filter.createMemCompFilter(SWAP_VAULT_MAX_SLIPPAGE_BPS_OFFSET, _data);
  }

  public static Filter createSwapVaultMaxSlippageFromReferenceBpsFilter(final int swapVaultMaxSlippageFromReferenceBps) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, swapVaultMaxSlippageFromReferenceBps);
    return Filter.createMemCompFilter(SWAP_VAULT_MAX_SLIPPAGE_FROM_REFERENCE_BPS_OFFSET, _data);
  }

  public static Filter createStrategyTypeFilter(final long strategyType) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, strategyType);
    return Filter.createMemCompFilter(STRATEGY_TYPE_OFFSET, _data);
  }

  public static Filter createPadding0Filter(final long padding0) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, padding0);
    return Filter.createMemCompFilter(PADDING_0_OFFSET, _data);
  }

  public static Filter createWithdrawFeeFilter(final long withdrawFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, withdrawFee);
    return Filter.createMemCompFilter(WITHDRAW_FEE_OFFSET, _data);
  }

  public static Filter createFeesFeeFilter(final long feesFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, feesFee);
    return Filter.createMemCompFilter(FEES_FEE_OFFSET, _data);
  }

  public static Filter createReward0FeeFilter(final long reward0Fee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, reward0Fee);
    return Filter.createMemCompFilter(REWARD_0_FEE_OFFSET, _data);
  }

  public static Filter createReward1FeeFilter(final long reward1Fee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, reward1Fee);
    return Filter.createMemCompFilter(REWARD_1_FEE_OFFSET, _data);
  }

  public static Filter createReward2FeeFilter(final long reward2Fee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, reward2Fee);
    return Filter.createMemCompFilter(REWARD_2_FEE_OFFSET, _data);
  }

  public static Filter createPositionTimestampFilter(final long positionTimestamp) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, positionTimestamp);
    return Filter.createMemCompFilter(POSITION_TIMESTAMP_OFFSET, _data);
  }

  public static Filter createStrategyDexFilter(final long strategyDex) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, strategyDex);
    return Filter.createMemCompFilter(STRATEGY_DEX_OFFSET, _data);
  }

  public static Filter createRaydiumProtocolPositionOrBaseVaultAuthorityFilter(final PublicKey raydiumProtocolPositionOrBaseVaultAuthority) {
    return Filter.createMemCompFilter(RAYDIUM_PROTOCOL_POSITION_OR_BASE_VAULT_AUTHORITY_OFFSET, raydiumProtocolPositionOrBaseVaultAuthority);
  }

  public static Filter createAllowDepositWithoutInvestFilter(final long allowDepositWithoutInvest) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, allowDepositWithoutInvest);
    return Filter.createMemCompFilter(ALLOW_DEPOSIT_WITHOUT_INVEST_OFFSET, _data);
  }

  public static Filter createRaydiumPoolConfigOrBaseVaultAuthorityFilter(final PublicKey raydiumPoolConfigOrBaseVaultAuthority) {
    return Filter.createMemCompFilter(RAYDIUM_POOL_CONFIG_OR_BASE_VAULT_AUTHORITY_OFFSET, raydiumPoolConfigOrBaseVaultAuthority);
  }

  public static Filter createDepositBlockedFilter(final int depositBlocked) {
    return Filter.createMemCompFilter(DEPOSIT_BLOCKED_OFFSET, new byte[]{(byte) depositBlocked});
  }

  public static Filter createCreationStatusFilter(final int creationStatus) {
    return Filter.createMemCompFilter(CREATION_STATUS_OFFSET, new byte[]{(byte) creationStatus});
  }

  public static Filter createInvestBlockedFilter(final int investBlocked) {
    return Filter.createMemCompFilter(INVEST_BLOCKED_OFFSET, new byte[]{(byte) investBlocked});
  }

  public static Filter createShareCalculationMethodFilter(final int shareCalculationMethod) {
    return Filter.createMemCompFilter(SHARE_CALCULATION_METHOD_OFFSET, new byte[]{(byte) shareCalculationMethod});
  }

  public static Filter createWithdrawBlockedFilter(final int withdrawBlocked) {
    return Filter.createMemCompFilter(WITHDRAW_BLOCKED_OFFSET, new byte[]{(byte) withdrawBlocked});
  }

  public static Filter createReservedFlag2Filter(final int reservedFlag2) {
    return Filter.createMemCompFilter(RESERVED_FLAG_2_OFFSET, new byte[]{(byte) reservedFlag2});
  }

  public static Filter createLocalAdminBlockedFilter(final int localAdminBlocked) {
    return Filter.createMemCompFilter(LOCAL_ADMIN_BLOCKED_OFFSET, new byte[]{(byte) localAdminBlocked});
  }

  public static Filter createFlashVaultSwapAllowedFilter(final int flashVaultSwapAllowed) {
    return Filter.createMemCompFilter(FLASH_VAULT_SWAP_ALLOWED_OFFSET, new byte[]{(byte) flashVaultSwapAllowed});
  }

  public static Filter createReferenceSwapPriceAFilter(final Price referenceSwapPriceA) {
    return Filter.createMemCompFilter(REFERENCE_SWAP_PRICE_A_OFFSET, referenceSwapPriceA.write());
  }

  public static Filter createReferenceSwapPriceBFilter(final Price referenceSwapPriceB) {
    return Filter.createMemCompFilter(REFERENCE_SWAP_PRICE_B_OFFSET, referenceSwapPriceB.write());
  }

  public static Filter createIsCommunityFilter(final int isCommunity) {
    return Filter.createMemCompFilter(IS_COMMUNITY_OFFSET, new byte[]{(byte) isCommunity});
  }

  public static Filter createRebalanceTypeFilter(final int rebalanceType) {
    return Filter.createMemCompFilter(REBALANCE_TYPE_OFFSET, new byte[]{(byte) rebalanceType});
  }

  public static Filter createTokenAFeesFromRewardsCumulativeFilter(final long tokenAFeesFromRewardsCumulative) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, tokenAFeesFromRewardsCumulative);
    return Filter.createMemCompFilter(TOKEN_A_FEES_FROM_REWARDS_CUMULATIVE_OFFSET, _data);
  }

  public static Filter createTokenBFeesFromRewardsCumulativeFilter(final long tokenBFeesFromRewardsCumulative) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, tokenBFeesFromRewardsCumulative);
    return Filter.createMemCompFilter(TOKEN_B_FEES_FROM_REWARDS_CUMULATIVE_OFFSET, _data);
  }

  public static Filter createStrategyLookupTableFilter(final PublicKey strategyLookupTable) {
    return Filter.createMemCompFilter(STRATEGY_LOOKUP_TABLE_OFFSET, strategyLookupTable);
  }

  public static Filter createLastSwapUnevenStepTimestampFilter(final long lastSwapUnevenStepTimestamp) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastSwapUnevenStepTimestamp);
    return Filter.createMemCompFilter(LAST_SWAP_UNEVEN_STEP_TIMESTAMP_OFFSET, _data);
  }

  public static Filter createFarmFilter(final PublicKey farm) {
    return Filter.createMemCompFilter(FARM_OFFSET, farm);
  }

  public static Filter createRebalancesCapFilter(final WithdrawalCaps rebalancesCap) {
    return Filter.createMemCompFilter(REBALANCES_CAP_OFFSET, rebalancesCap.write());
  }

  public static Filter createSwapUnevenAuthorityFilter(final PublicKey swapUnevenAuthority) {
    return Filter.createMemCompFilter(SWAP_UNEVEN_AUTHORITY_OFFSET, swapUnevenAuthority);
  }

  public static Filter createTokenATokenProgramFilter(final PublicKey tokenATokenProgram) {
    return Filter.createMemCompFilter(TOKEN_A_TOKEN_PROGRAM_OFFSET, tokenATokenProgram);
  }

  public static Filter createTokenBTokenProgramFilter(final PublicKey tokenBTokenProgram) {
    return Filter.createMemCompFilter(TOKEN_B_TOKEN_PROGRAM_OFFSET, tokenBTokenProgram);
  }

  public static Filter createPendingAdminFilter(final PublicKey pendingAdmin) {
    return Filter.createMemCompFilter(PENDING_ADMIN_OFFSET, pendingAdmin);
  }

  public static Filter createPadding3Filter(final long padding3) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, padding3);
    return Filter.createMemCompFilter(PADDING_3_OFFSET, _data);
  }

  public static WhirlpoolStrategy read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static WhirlpoolStrategy read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static WhirlpoolStrategy read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], WhirlpoolStrategy> FACTORY = WhirlpoolStrategy::read;

  public static WhirlpoolStrategy read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var adminAuthority = readPubKey(_data, i);
    i += 32;
    final var globalConfig = readPubKey(_data, i);
    i += 32;
    final var baseVaultAuthority = readPubKey(_data, i);
    i += 32;
    final var baseVaultAuthorityBump = getInt64LE(_data, i);
    i += 8;
    final var pool = readPubKey(_data, i);
    i += 32;
    final var poolTokenVaultA = readPubKey(_data, i);
    i += 32;
    final var poolTokenVaultB = readPubKey(_data, i);
    i += 32;
    final var tickArrayLower = readPubKey(_data, i);
    i += 32;
    final var tickArrayUpper = readPubKey(_data, i);
    i += 32;
    final var position = readPubKey(_data, i);
    i += 32;
    final var positionMint = readPubKey(_data, i);
    i += 32;
    final var positionMetadata = readPubKey(_data, i);
    i += 32;
    final var positionTokenAccount = readPubKey(_data, i);
    i += 32;
    final var tokenAVault = readPubKey(_data, i);
    i += 32;
    final var tokenBVault = readPubKey(_data, i);
    i += 32;
    final var deprecated0 = new PublicKey[2];
    i += Borsh.readArray(deprecated0, _data, i);
    final var deprecated1 = new long[2];
    i += Borsh.readArray(deprecated1, _data, i);
    final var tokenAMint = readPubKey(_data, i);
    i += 32;
    final var tokenBMint = readPubKey(_data, i);
    i += 32;
    final var tokenAMintDecimals = getInt64LE(_data, i);
    i += 8;
    final var tokenBMintDecimals = getInt64LE(_data, i);
    i += 8;
    final var tokenAAmounts = getInt64LE(_data, i);
    i += 8;
    final var tokenBAmounts = getInt64LE(_data, i);
    i += 8;
    final var tokenACollateralId = getInt64LE(_data, i);
    i += 8;
    final var tokenBCollateralId = getInt64LE(_data, i);
    i += 8;
    final var scopePrices = readPubKey(_data, i);
    i += 32;
    final var deprecated2 = readPubKey(_data, i);
    i += 32;
    final var sharesMint = readPubKey(_data, i);
    i += 32;
    final var sharesMintDecimals = getInt64LE(_data, i);
    i += 8;
    final var sharesMintAuthority = readPubKey(_data, i);
    i += 32;
    final var sharesMintAuthorityBump = getInt64LE(_data, i);
    i += 8;
    final var sharesIssued = getInt64LE(_data, i);
    i += 8;
    final var status = getInt64LE(_data, i);
    i += 8;
    final var reward0Amount = getInt64LE(_data, i);
    i += 8;
    final var reward0Vault = readPubKey(_data, i);
    i += 32;
    final var reward0CollateralId = getInt64LE(_data, i);
    i += 8;
    final var reward0Decimals = getInt64LE(_data, i);
    i += 8;
    final var reward1Amount = getInt64LE(_data, i);
    i += 8;
    final var reward1Vault = readPubKey(_data, i);
    i += 32;
    final var reward1CollateralId = getInt64LE(_data, i);
    i += 8;
    final var reward1Decimals = getInt64LE(_data, i);
    i += 8;
    final var reward2Amount = getInt64LE(_data, i);
    i += 8;
    final var reward2Vault = readPubKey(_data, i);
    i += 32;
    final var reward2CollateralId = getInt64LE(_data, i);
    i += 8;
    final var reward2Decimals = getInt64LE(_data, i);
    i += 8;
    final var depositCapUsd = getInt64LE(_data, i);
    i += 8;
    final var feesACumulative = getInt64LE(_data, i);
    i += 8;
    final var feesBCumulative = getInt64LE(_data, i);
    i += 8;
    final var reward0AmountCumulative = getInt64LE(_data, i);
    i += 8;
    final var reward1AmountCumulative = getInt64LE(_data, i);
    i += 8;
    final var reward2AmountCumulative = getInt64LE(_data, i);
    i += 8;
    final var depositCapUsdPerIxn = getInt64LE(_data, i);
    i += 8;
    final var withdrawalCapA = WithdrawalCaps.read(_data, i);
    i += Borsh.len(withdrawalCapA);
    final var withdrawalCapB = WithdrawalCaps.read(_data, i);
    i += Borsh.len(withdrawalCapB);
    final var maxPriceDeviationBps = getInt64LE(_data, i);
    i += 8;
    final var swapVaultMaxSlippageBps = getInt32LE(_data, i);
    i += 4;
    final var swapVaultMaxSlippageFromReferenceBps = getInt32LE(_data, i);
    i += 4;
    final var strategyType = getInt64LE(_data, i);
    i += 8;
    final var padding0 = getInt64LE(_data, i);
    i += 8;
    final var withdrawFee = getInt64LE(_data, i);
    i += 8;
    final var feesFee = getInt64LE(_data, i);
    i += 8;
    final var reward0Fee = getInt64LE(_data, i);
    i += 8;
    final var reward1Fee = getInt64LE(_data, i);
    i += 8;
    final var reward2Fee = getInt64LE(_data, i);
    i += 8;
    final var positionTimestamp = getInt64LE(_data, i);
    i += 8;
    final var kaminoRewards = new KaminoRewardInfo[3];
    i += Borsh.readArray(kaminoRewards, KaminoRewardInfo::read, _data, i);
    final var strategyDex = getInt64LE(_data, i);
    i += 8;
    final var raydiumProtocolPositionOrBaseVaultAuthority = readPubKey(_data, i);
    i += 32;
    final var allowDepositWithoutInvest = getInt64LE(_data, i);
    i += 8;
    final var raydiumPoolConfigOrBaseVaultAuthority = readPubKey(_data, i);
    i += 32;
    final var depositBlocked = _data[i] & 0xFF;
    ++i;
    final var creationStatus = _data[i] & 0xFF;
    ++i;
    final var investBlocked = _data[i] & 0xFF;
    ++i;
    final var shareCalculationMethod = _data[i] & 0xFF;
    ++i;
    final var withdrawBlocked = _data[i] & 0xFF;
    ++i;
    final var reservedFlag2 = _data[i] & 0xFF;
    ++i;
    final var localAdminBlocked = _data[i] & 0xFF;
    ++i;
    final var flashVaultSwapAllowed = _data[i] & 0xFF;
    ++i;
    final var referenceSwapPriceA = Price.read(_data, i);
    i += Borsh.len(referenceSwapPriceA);
    final var referenceSwapPriceB = Price.read(_data, i);
    i += Borsh.len(referenceSwapPriceB);
    final var isCommunity = _data[i] & 0xFF;
    ++i;
    final var rebalanceType = _data[i] & 0xFF;
    ++i;
    final var padding1 = new byte[6];
    i += Borsh.readArray(padding1, _data, i);
    final var rebalanceRaw = RebalanceRaw.read(_data, i);
    i += Borsh.len(rebalanceRaw);
    final var padding2 = new byte[7];
    i += Borsh.readArray(padding2, _data, i);
    final var tokenAFeesFromRewardsCumulative = getInt64LE(_data, i);
    i += 8;
    final var tokenBFeesFromRewardsCumulative = getInt64LE(_data, i);
    i += 8;
    final var strategyLookupTable = readPubKey(_data, i);
    i += 32;
    final var lastSwapUnevenStepTimestamp = getInt64LE(_data, i);
    i += 8;
    final var farm = readPubKey(_data, i);
    i += 32;
    final var rebalancesCap = WithdrawalCaps.read(_data, i);
    i += Borsh.len(rebalancesCap);
    final var swapUnevenAuthority = readPubKey(_data, i);
    i += 32;
    final var tokenATokenProgram = readPubKey(_data, i);
    i += 32;
    final var tokenBTokenProgram = readPubKey(_data, i);
    i += 32;
    final var pendingAdmin = readPubKey(_data, i);
    i += 32;
    final var padding3 = getInt64LE(_data, i);
    i += 8;
    final var padding4 = new BigInteger[13];
    i += Borsh.read128Array(padding4, _data, i);
    final var padding5 = new BigInteger[32];
    i += Borsh.read128Array(padding5, _data, i);
    final var padding6 = new BigInteger[32];
    i += Borsh.read128Array(padding6, _data, i);
    final var padding7 = new BigInteger[32];
    Borsh.read128Array(padding7, _data, i);
    return new WhirlpoolStrategy(_address,
                                 discriminator,
                                 adminAuthority,
                                 globalConfig,
                                 baseVaultAuthority,
                                 baseVaultAuthorityBump,
                                 pool,
                                 poolTokenVaultA,
                                 poolTokenVaultB,
                                 tickArrayLower,
                                 tickArrayUpper,
                                 position,
                                 positionMint,
                                 positionMetadata,
                                 positionTokenAccount,
                                 tokenAVault,
                                 tokenBVault,
                                 deprecated0,
                                 deprecated1,
                                 tokenAMint,
                                 tokenBMint,
                                 tokenAMintDecimals,
                                 tokenBMintDecimals,
                                 tokenAAmounts,
                                 tokenBAmounts,
                                 tokenACollateralId,
                                 tokenBCollateralId,
                                 scopePrices,
                                 deprecated2,
                                 sharesMint,
                                 sharesMintDecimals,
                                 sharesMintAuthority,
                                 sharesMintAuthorityBump,
                                 sharesIssued,
                                 status,
                                 reward0Amount,
                                 reward0Vault,
                                 reward0CollateralId,
                                 reward0Decimals,
                                 reward1Amount,
                                 reward1Vault,
                                 reward1CollateralId,
                                 reward1Decimals,
                                 reward2Amount,
                                 reward2Vault,
                                 reward2CollateralId,
                                 reward2Decimals,
                                 depositCapUsd,
                                 feesACumulative,
                                 feesBCumulative,
                                 reward0AmountCumulative,
                                 reward1AmountCumulative,
                                 reward2AmountCumulative,
                                 depositCapUsdPerIxn,
                                 withdrawalCapA,
                                 withdrawalCapB,
                                 maxPriceDeviationBps,
                                 swapVaultMaxSlippageBps,
                                 swapVaultMaxSlippageFromReferenceBps,
                                 strategyType,
                                 padding0,
                                 withdrawFee,
                                 feesFee,
                                 reward0Fee,
                                 reward1Fee,
                                 reward2Fee,
                                 positionTimestamp,
                                 kaminoRewards,
                                 strategyDex,
                                 raydiumProtocolPositionOrBaseVaultAuthority,
                                 allowDepositWithoutInvest,
                                 raydiumPoolConfigOrBaseVaultAuthority,
                                 depositBlocked,
                                 creationStatus,
                                 investBlocked,
                                 shareCalculationMethod,
                                 withdrawBlocked,
                                 reservedFlag2,
                                 localAdminBlocked,
                                 flashVaultSwapAllowed,
                                 referenceSwapPriceA,
                                 referenceSwapPriceB,
                                 isCommunity,
                                 rebalanceType,
                                 padding1,
                                 rebalanceRaw,
                                 padding2,
                                 tokenAFeesFromRewardsCumulative,
                                 tokenBFeesFromRewardsCumulative,
                                 strategyLookupTable,
                                 lastSwapUnevenStepTimestamp,
                                 farm,
                                 rebalancesCap,
                                 swapUnevenAuthority,
                                 tokenATokenProgram,
                                 tokenBTokenProgram,
                                 pendingAdmin,
                                 padding3,
                                 padding4,
                                 padding5,
                                 padding6,
                                 padding7);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    adminAuthority.write(_data, i);
    i += 32;
    globalConfig.write(_data, i);
    i += 32;
    baseVaultAuthority.write(_data, i);
    i += 32;
    putInt64LE(_data, i, baseVaultAuthorityBump);
    i += 8;
    pool.write(_data, i);
    i += 32;
    poolTokenVaultA.write(_data, i);
    i += 32;
    poolTokenVaultB.write(_data, i);
    i += 32;
    tickArrayLower.write(_data, i);
    i += 32;
    tickArrayUpper.write(_data, i);
    i += 32;
    position.write(_data, i);
    i += 32;
    positionMint.write(_data, i);
    i += 32;
    positionMetadata.write(_data, i);
    i += 32;
    positionTokenAccount.write(_data, i);
    i += 32;
    tokenAVault.write(_data, i);
    i += 32;
    tokenBVault.write(_data, i);
    i += 32;
    i += Borsh.writeArray(deprecated0, _data, i);
    i += Borsh.writeArray(deprecated1, _data, i);
    tokenAMint.write(_data, i);
    i += 32;
    tokenBMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, tokenAMintDecimals);
    i += 8;
    putInt64LE(_data, i, tokenBMintDecimals);
    i += 8;
    putInt64LE(_data, i, tokenAAmounts);
    i += 8;
    putInt64LE(_data, i, tokenBAmounts);
    i += 8;
    putInt64LE(_data, i, tokenACollateralId);
    i += 8;
    putInt64LE(_data, i, tokenBCollateralId);
    i += 8;
    scopePrices.write(_data, i);
    i += 32;
    deprecated2.write(_data, i);
    i += 32;
    sharesMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, sharesMintDecimals);
    i += 8;
    sharesMintAuthority.write(_data, i);
    i += 32;
    putInt64LE(_data, i, sharesMintAuthorityBump);
    i += 8;
    putInt64LE(_data, i, sharesIssued);
    i += 8;
    putInt64LE(_data, i, status);
    i += 8;
    putInt64LE(_data, i, reward0Amount);
    i += 8;
    reward0Vault.write(_data, i);
    i += 32;
    putInt64LE(_data, i, reward0CollateralId);
    i += 8;
    putInt64LE(_data, i, reward0Decimals);
    i += 8;
    putInt64LE(_data, i, reward1Amount);
    i += 8;
    reward1Vault.write(_data, i);
    i += 32;
    putInt64LE(_data, i, reward1CollateralId);
    i += 8;
    putInt64LE(_data, i, reward1Decimals);
    i += 8;
    putInt64LE(_data, i, reward2Amount);
    i += 8;
    reward2Vault.write(_data, i);
    i += 32;
    putInt64LE(_data, i, reward2CollateralId);
    i += 8;
    putInt64LE(_data, i, reward2Decimals);
    i += 8;
    putInt64LE(_data, i, depositCapUsd);
    i += 8;
    putInt64LE(_data, i, feesACumulative);
    i += 8;
    putInt64LE(_data, i, feesBCumulative);
    i += 8;
    putInt64LE(_data, i, reward0AmountCumulative);
    i += 8;
    putInt64LE(_data, i, reward1AmountCumulative);
    i += 8;
    putInt64LE(_data, i, reward2AmountCumulative);
    i += 8;
    putInt64LE(_data, i, depositCapUsdPerIxn);
    i += 8;
    i += Borsh.write(withdrawalCapA, _data, i);
    i += Borsh.write(withdrawalCapB, _data, i);
    putInt64LE(_data, i, maxPriceDeviationBps);
    i += 8;
    putInt32LE(_data, i, swapVaultMaxSlippageBps);
    i += 4;
    putInt32LE(_data, i, swapVaultMaxSlippageFromReferenceBps);
    i += 4;
    putInt64LE(_data, i, strategyType);
    i += 8;
    putInt64LE(_data, i, padding0);
    i += 8;
    putInt64LE(_data, i, withdrawFee);
    i += 8;
    putInt64LE(_data, i, feesFee);
    i += 8;
    putInt64LE(_data, i, reward0Fee);
    i += 8;
    putInt64LE(_data, i, reward1Fee);
    i += 8;
    putInt64LE(_data, i, reward2Fee);
    i += 8;
    putInt64LE(_data, i, positionTimestamp);
    i += 8;
    i += Borsh.writeArray(kaminoRewards, _data, i);
    putInt64LE(_data, i, strategyDex);
    i += 8;
    raydiumProtocolPositionOrBaseVaultAuthority.write(_data, i);
    i += 32;
    putInt64LE(_data, i, allowDepositWithoutInvest);
    i += 8;
    raydiumPoolConfigOrBaseVaultAuthority.write(_data, i);
    i += 32;
    _data[i] = (byte) depositBlocked;
    ++i;
    _data[i] = (byte) creationStatus;
    ++i;
    _data[i] = (byte) investBlocked;
    ++i;
    _data[i] = (byte) shareCalculationMethod;
    ++i;
    _data[i] = (byte) withdrawBlocked;
    ++i;
    _data[i] = (byte) reservedFlag2;
    ++i;
    _data[i] = (byte) localAdminBlocked;
    ++i;
    _data[i] = (byte) flashVaultSwapAllowed;
    ++i;
    i += Borsh.write(referenceSwapPriceA, _data, i);
    i += Borsh.write(referenceSwapPriceB, _data, i);
    _data[i] = (byte) isCommunity;
    ++i;
    _data[i] = (byte) rebalanceType;
    ++i;
    i += Borsh.writeArray(padding1, _data, i);
    i += Borsh.write(rebalanceRaw, _data, i);
    i += Borsh.writeArray(padding2, _data, i);
    putInt64LE(_data, i, tokenAFeesFromRewardsCumulative);
    i += 8;
    putInt64LE(_data, i, tokenBFeesFromRewardsCumulative);
    i += 8;
    strategyLookupTable.write(_data, i);
    i += 32;
    putInt64LE(_data, i, lastSwapUnevenStepTimestamp);
    i += 8;
    farm.write(_data, i);
    i += 32;
    i += Borsh.write(rebalancesCap, _data, i);
    swapUnevenAuthority.write(_data, i);
    i += 32;
    tokenATokenProgram.write(_data, i);
    i += 32;
    tokenBTokenProgram.write(_data, i);
    i += 32;
    pendingAdmin.write(_data, i);
    i += 32;
    putInt64LE(_data, i, padding3);
    i += 8;
    i += Borsh.write128Array(padding4, _data, i);
    i += Borsh.write128Array(padding5, _data, i);
    i += Borsh.write128Array(padding6, _data, i);
    i += Borsh.write128Array(padding7, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
