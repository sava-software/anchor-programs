package software.sava.anchor.programs.drift.vaults.anchor.types;

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

public record Vault(PublicKey _address,
                    Discriminator discriminator,
                    // The name of the vault. Vault pubkey is derived from this name.
                    byte[] name,
                    // The vault's pubkey. It is a pda of name and also used as the authority for drift user
                    PublicKey pubkey,
                    // The manager of the vault who has ability to update vault params
                    PublicKey manager,
                    // The vaults token account. Used to receive tokens between deposits and withdrawals
                    PublicKey tokenAccount,
                    // The drift user stats account for the vault
                    PublicKey userStats,
                    // The drift user account for the vault
                    PublicKey user,
                    // The vaults designated delegate for drift user account
                    // can differ from actual user delegate if vault is in liquidation
                    PublicKey delegate,
                    // The delegate handling liquidation for depositor
                    PublicKey liquidationDelegate,
                    // The sum of all shares held by the users (vault depositors)
                    BigInteger userShares,
                    // The sum of all shares: deposits from users, manager deposits, manager profit/fee, and protocol profit/fee.
                    // The manager deposits are total_shares - user_shares - protocol_profit_and_fee_shares.
                    BigInteger totalShares,
                    // Last fee update unix timestamp
                    long lastFeeUpdateTs,
                    // When the liquidation starts
                    long liquidationStartTs,
                    // The period (in seconds) that a vault depositor must wait after requesting a withdrawal to finalize withdrawal.
                    // Currently, the maximum is 90 days.
                    long redeemPeriod,
                    // The sum of all outstanding withdraw requests
                    long totalWithdrawRequested,
                    // Max token capacity, once hit/passed vault will reject new deposits (updatable)
                    long maxTokens,
                    // The annual fee charged on deposits by the manager.
                    // Traditional funds typically charge 2% per year on assets under management.
                    long managementFee,
                    // Timestamp vault initialized
                    long initTs,
                    // The net deposits for the vault
                    long netDeposits,
                    // The net deposits for the manager
                    long managerNetDeposits,
                    // Total deposits
                    long totalDeposits,
                    // Total withdraws
                    long totalWithdraws,
                    // Total deposits for the manager
                    long managerTotalDeposits,
                    // Total withdraws for the manager
                    long managerTotalWithdraws,
                    // Total management fee accrued by the manager
                    long managerTotalFee,
                    // Total profit share accrued by the manager
                    long managerTotalProfitShare,
                    // The minimum deposit amount
                    long minDepositAmount,
                    WithdrawRequest lastManagerWithdrawRequest,
                    // The base 10 exponent of the shares (given massive share inflation can occur at near zero vault equity)
                    int sharesBase,
                    // Percentage the manager charges on all profits realized by depositors: PERCENTAGE_PRECISION
                    int profitShare,
                    // Vault manager only collect incentive fees during periods when returns are higher than this amount: PERCENTAGE_PRECISION
                    int hurdleRate,
                    // The spot market index the vault deposits into/withdraws from
                    int spotMarketIndex,
                    // The bump for the vault pda
                    int bump,
                    // Whether anybody can be a depositor
                    boolean permissioned,
                    // The optional [`VaultProtocol`] account.
                    boolean vaultProtocol,
                    // How fuel distribution should be treated [`FuelDistributionMode`]. Default is `UsersOnly`
                    int fuelDistributionMode,
                    // Whether the vault has a FeeUpdate account [`FeeUpdateStatus`]. Default is `FeeUpdateStatus::None`
                    // After a `FeeUpdate` account is created and the manager has staged a fee update, the status is set to `PendingFeeUpdate`.
                    // And instructsions that may finalize the fee update must include the `FeeUpdate` account with `remaining_accounts`.
                    int feeUpdateStatus,
                    // The class of the vault [`VaultClass`]. Default is `VaultClass::Normal`
                    int vaultClass,
                    // The timestamp cumulative_fuel_per_share was last updated
                    int lastCumulativeFuelPerShareTs,
                    // The cumulative fuel per share (scaled up by 1e6 to avoid losing precision)
                    BigInteger cumulativeFuelPerShare,
                    // The total fuel accumulated
                    BigInteger cumulativeFuel,
                    // The total value (in deposit asset) of borrows the manager has outstanding.
                    // Purely for informational purposes for assets that have left the vault that the manager
                    // is expected to return.
                    long managerBorrowedValue,
                    long[] padding) implements Borsh {

  public static final int BYTES = 536;
  public static final int NAME_LEN = 32;
  public static final int PADDING_LEN = 2;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int NAME_OFFSET = 8;
  public static final int PUBKEY_OFFSET = 40;
  public static final int MANAGER_OFFSET = 72;
  public static final int TOKEN_ACCOUNT_OFFSET = 104;
  public static final int USER_STATS_OFFSET = 136;
  public static final int USER_OFFSET = 168;
  public static final int DELEGATE_OFFSET = 200;
  public static final int LIQUIDATION_DELEGATE_OFFSET = 232;
  public static final int USER_SHARES_OFFSET = 264;
  public static final int TOTAL_SHARES_OFFSET = 280;
  public static final int LAST_FEE_UPDATE_TS_OFFSET = 296;
  public static final int LIQUIDATION_START_TS_OFFSET = 304;
  public static final int REDEEM_PERIOD_OFFSET = 312;
  public static final int TOTAL_WITHDRAW_REQUESTED_OFFSET = 320;
  public static final int MAX_TOKENS_OFFSET = 328;
  public static final int MANAGEMENT_FEE_OFFSET = 336;
  public static final int INIT_TS_OFFSET = 344;
  public static final int NET_DEPOSITS_OFFSET = 352;
  public static final int MANAGER_NET_DEPOSITS_OFFSET = 360;
  public static final int TOTAL_DEPOSITS_OFFSET = 368;
  public static final int TOTAL_WITHDRAWS_OFFSET = 376;
  public static final int MANAGER_TOTAL_DEPOSITS_OFFSET = 384;
  public static final int MANAGER_TOTAL_WITHDRAWS_OFFSET = 392;
  public static final int MANAGER_TOTAL_FEE_OFFSET = 400;
  public static final int MANAGER_TOTAL_PROFIT_SHARE_OFFSET = 408;
  public static final int MIN_DEPOSIT_AMOUNT_OFFSET = 416;
  public static final int LAST_MANAGER_WITHDRAW_REQUEST_OFFSET = 424;
  public static final int SHARES_BASE_OFFSET = 456;
  public static final int PROFIT_SHARE_OFFSET = 460;
  public static final int HURDLE_RATE_OFFSET = 464;
  public static final int SPOT_MARKET_INDEX_OFFSET = 468;
  public static final int BUMP_OFFSET = 470;
  public static final int PERMISSIONED_OFFSET = 471;
  public static final int VAULT_PROTOCOL_OFFSET = 472;
  public static final int FUEL_DISTRIBUTION_MODE_OFFSET = 473;
  public static final int FEE_UPDATE_STATUS_OFFSET = 474;
  public static final int VAULT_CLASS_OFFSET = 475;
  public static final int LAST_CUMULATIVE_FUEL_PER_SHARE_TS_OFFSET = 476;
  public static final int CUMULATIVE_FUEL_PER_SHARE_OFFSET = 480;
  public static final int CUMULATIVE_FUEL_OFFSET = 496;
  public static final int MANAGER_BORROWED_VALUE_OFFSET = 512;
  public static final int PADDING_OFFSET = 520;

  public static Filter createPubkeyFilter(final PublicKey pubkey) {
    return Filter.createMemCompFilter(PUBKEY_OFFSET, pubkey);
  }

  public static Filter createManagerFilter(final PublicKey manager) {
    return Filter.createMemCompFilter(MANAGER_OFFSET, manager);
  }

  public static Filter createTokenAccountFilter(final PublicKey tokenAccount) {
    return Filter.createMemCompFilter(TOKEN_ACCOUNT_OFFSET, tokenAccount);
  }

  public static Filter createUserStatsFilter(final PublicKey userStats) {
    return Filter.createMemCompFilter(USER_STATS_OFFSET, userStats);
  }

  public static Filter createUserFilter(final PublicKey user) {
    return Filter.createMemCompFilter(USER_OFFSET, user);
  }

  public static Filter createDelegateFilter(final PublicKey delegate) {
    return Filter.createMemCompFilter(DELEGATE_OFFSET, delegate);
  }

  public static Filter createLiquidationDelegateFilter(final PublicKey liquidationDelegate) {
    return Filter.createMemCompFilter(LIQUIDATION_DELEGATE_OFFSET, liquidationDelegate);
  }

  public static Filter createUserSharesFilter(final BigInteger userShares) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, userShares);
    return Filter.createMemCompFilter(USER_SHARES_OFFSET, _data);
  }

  public static Filter createTotalSharesFilter(final BigInteger totalShares) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, totalShares);
    return Filter.createMemCompFilter(TOTAL_SHARES_OFFSET, _data);
  }

  public static Filter createLastFeeUpdateTsFilter(final long lastFeeUpdateTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastFeeUpdateTs);
    return Filter.createMemCompFilter(LAST_FEE_UPDATE_TS_OFFSET, _data);
  }

  public static Filter createLiquidationStartTsFilter(final long liquidationStartTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, liquidationStartTs);
    return Filter.createMemCompFilter(LIQUIDATION_START_TS_OFFSET, _data);
  }

  public static Filter createRedeemPeriodFilter(final long redeemPeriod) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, redeemPeriod);
    return Filter.createMemCompFilter(REDEEM_PERIOD_OFFSET, _data);
  }

  public static Filter createTotalWithdrawRequestedFilter(final long totalWithdrawRequested) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalWithdrawRequested);
    return Filter.createMemCompFilter(TOTAL_WITHDRAW_REQUESTED_OFFSET, _data);
  }

  public static Filter createMaxTokensFilter(final long maxTokens) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxTokens);
    return Filter.createMemCompFilter(MAX_TOKENS_OFFSET, _data);
  }

  public static Filter createManagementFeeFilter(final long managementFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, managementFee);
    return Filter.createMemCompFilter(MANAGEMENT_FEE_OFFSET, _data);
  }

  public static Filter createInitTsFilter(final long initTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, initTs);
    return Filter.createMemCompFilter(INIT_TS_OFFSET, _data);
  }

  public static Filter createNetDepositsFilter(final long netDeposits) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, netDeposits);
    return Filter.createMemCompFilter(NET_DEPOSITS_OFFSET, _data);
  }

  public static Filter createManagerNetDepositsFilter(final long managerNetDeposits) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, managerNetDeposits);
    return Filter.createMemCompFilter(MANAGER_NET_DEPOSITS_OFFSET, _data);
  }

  public static Filter createTotalDepositsFilter(final long totalDeposits) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalDeposits);
    return Filter.createMemCompFilter(TOTAL_DEPOSITS_OFFSET, _data);
  }

  public static Filter createTotalWithdrawsFilter(final long totalWithdraws) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalWithdraws);
    return Filter.createMemCompFilter(TOTAL_WITHDRAWS_OFFSET, _data);
  }

  public static Filter createManagerTotalDepositsFilter(final long managerTotalDeposits) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, managerTotalDeposits);
    return Filter.createMemCompFilter(MANAGER_TOTAL_DEPOSITS_OFFSET, _data);
  }

  public static Filter createManagerTotalWithdrawsFilter(final long managerTotalWithdraws) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, managerTotalWithdraws);
    return Filter.createMemCompFilter(MANAGER_TOTAL_WITHDRAWS_OFFSET, _data);
  }

  public static Filter createManagerTotalFeeFilter(final long managerTotalFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, managerTotalFee);
    return Filter.createMemCompFilter(MANAGER_TOTAL_FEE_OFFSET, _data);
  }

  public static Filter createManagerTotalProfitShareFilter(final long managerTotalProfitShare) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, managerTotalProfitShare);
    return Filter.createMemCompFilter(MANAGER_TOTAL_PROFIT_SHARE_OFFSET, _data);
  }

  public static Filter createMinDepositAmountFilter(final long minDepositAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minDepositAmount);
    return Filter.createMemCompFilter(MIN_DEPOSIT_AMOUNT_OFFSET, _data);
  }

  public static Filter createLastManagerWithdrawRequestFilter(final WithdrawRequest lastManagerWithdrawRequest) {
    return Filter.createMemCompFilter(LAST_MANAGER_WITHDRAW_REQUEST_OFFSET, lastManagerWithdrawRequest.write());
  }

  public static Filter createSharesBaseFilter(final int sharesBase) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, sharesBase);
    return Filter.createMemCompFilter(SHARES_BASE_OFFSET, _data);
  }

  public static Filter createProfitShareFilter(final int profitShare) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, profitShare);
    return Filter.createMemCompFilter(PROFIT_SHARE_OFFSET, _data);
  }

  public static Filter createHurdleRateFilter(final int hurdleRate) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, hurdleRate);
    return Filter.createMemCompFilter(HURDLE_RATE_OFFSET, _data);
  }

  public static Filter createSpotMarketIndexFilter(final int spotMarketIndex) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, spotMarketIndex);
    return Filter.createMemCompFilter(SPOT_MARKET_INDEX_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createPermissionedFilter(final boolean permissioned) {
    return Filter.createMemCompFilter(PERMISSIONED_OFFSET, new byte[]{(byte) (permissioned ? 1 : 0)});
  }

  public static Filter createVaultProtocolFilter(final boolean vaultProtocol) {
    return Filter.createMemCompFilter(VAULT_PROTOCOL_OFFSET, new byte[]{(byte) (vaultProtocol ? 1 : 0)});
  }

  public static Filter createFuelDistributionModeFilter(final int fuelDistributionMode) {
    return Filter.createMemCompFilter(FUEL_DISTRIBUTION_MODE_OFFSET, new byte[]{(byte) fuelDistributionMode});
  }

  public static Filter createFeeUpdateStatusFilter(final int feeUpdateStatus) {
    return Filter.createMemCompFilter(FEE_UPDATE_STATUS_OFFSET, new byte[]{(byte) feeUpdateStatus});
  }

  public static Filter createVaultClassFilter(final int vaultClass) {
    return Filter.createMemCompFilter(VAULT_CLASS_OFFSET, new byte[]{(byte) vaultClass});
  }

  public static Filter createLastCumulativeFuelPerShareTsFilter(final int lastCumulativeFuelPerShareTs) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, lastCumulativeFuelPerShareTs);
    return Filter.createMemCompFilter(LAST_CUMULATIVE_FUEL_PER_SHARE_TS_OFFSET, _data);
  }

  public static Filter createCumulativeFuelPerShareFilter(final BigInteger cumulativeFuelPerShare) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, cumulativeFuelPerShare);
    return Filter.createMemCompFilter(CUMULATIVE_FUEL_PER_SHARE_OFFSET, _data);
  }

  public static Filter createCumulativeFuelFilter(final BigInteger cumulativeFuel) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, cumulativeFuel);
    return Filter.createMemCompFilter(CUMULATIVE_FUEL_OFFSET, _data);
  }

  public static Filter createManagerBorrowedValueFilter(final long managerBorrowedValue) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, managerBorrowedValue);
    return Filter.createMemCompFilter(MANAGER_BORROWED_VALUE_OFFSET, _data);
  }

  public static Vault read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Vault read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Vault read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Vault> FACTORY = Vault::read;

  public static Vault read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var name = new byte[32];
    i += Borsh.readArray(name, _data, i);
    final var pubkey = readPubKey(_data, i);
    i += 32;
    final var manager = readPubKey(_data, i);
    i += 32;
    final var tokenAccount = readPubKey(_data, i);
    i += 32;
    final var userStats = readPubKey(_data, i);
    i += 32;
    final var user = readPubKey(_data, i);
    i += 32;
    final var delegate = readPubKey(_data, i);
    i += 32;
    final var liquidationDelegate = readPubKey(_data, i);
    i += 32;
    final var userShares = getInt128LE(_data, i);
    i += 16;
    final var totalShares = getInt128LE(_data, i);
    i += 16;
    final var lastFeeUpdateTs = getInt64LE(_data, i);
    i += 8;
    final var liquidationStartTs = getInt64LE(_data, i);
    i += 8;
    final var redeemPeriod = getInt64LE(_data, i);
    i += 8;
    final var totalWithdrawRequested = getInt64LE(_data, i);
    i += 8;
    final var maxTokens = getInt64LE(_data, i);
    i += 8;
    final var managementFee = getInt64LE(_data, i);
    i += 8;
    final var initTs = getInt64LE(_data, i);
    i += 8;
    final var netDeposits = getInt64LE(_data, i);
    i += 8;
    final var managerNetDeposits = getInt64LE(_data, i);
    i += 8;
    final var totalDeposits = getInt64LE(_data, i);
    i += 8;
    final var totalWithdraws = getInt64LE(_data, i);
    i += 8;
    final var managerTotalDeposits = getInt64LE(_data, i);
    i += 8;
    final var managerTotalWithdraws = getInt64LE(_data, i);
    i += 8;
    final var managerTotalFee = getInt64LE(_data, i);
    i += 8;
    final var managerTotalProfitShare = getInt64LE(_data, i);
    i += 8;
    final var minDepositAmount = getInt64LE(_data, i);
    i += 8;
    final var lastManagerWithdrawRequest = WithdrawRequest.read(_data, i);
    i += Borsh.len(lastManagerWithdrawRequest);
    final var sharesBase = getInt32LE(_data, i);
    i += 4;
    final var profitShare = getInt32LE(_data, i);
    i += 4;
    final var hurdleRate = getInt32LE(_data, i);
    i += 4;
    final var spotMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var permissioned = _data[i] == 1;
    ++i;
    final var vaultProtocol = _data[i] == 1;
    ++i;
    final var fuelDistributionMode = _data[i] & 0xFF;
    ++i;
    final var feeUpdateStatus = _data[i] & 0xFF;
    ++i;
    final var vaultClass = _data[i] & 0xFF;
    ++i;
    final var lastCumulativeFuelPerShareTs = getInt32LE(_data, i);
    i += 4;
    final var cumulativeFuelPerShare = getInt128LE(_data, i);
    i += 16;
    final var cumulativeFuel = getInt128LE(_data, i);
    i += 16;
    final var managerBorrowedValue = getInt64LE(_data, i);
    i += 8;
    final var padding = new long[2];
    Borsh.readArray(padding, _data, i);
    return new Vault(_address,
                     discriminator,
                     name,
                     pubkey,
                     manager,
                     tokenAccount,
                     userStats,
                     user,
                     delegate,
                     liquidationDelegate,
                     userShares,
                     totalShares,
                     lastFeeUpdateTs,
                     liquidationStartTs,
                     redeemPeriod,
                     totalWithdrawRequested,
                     maxTokens,
                     managementFee,
                     initTs,
                     netDeposits,
                     managerNetDeposits,
                     totalDeposits,
                     totalWithdraws,
                     managerTotalDeposits,
                     managerTotalWithdraws,
                     managerTotalFee,
                     managerTotalProfitShare,
                     minDepositAmount,
                     lastManagerWithdrawRequest,
                     sharesBase,
                     profitShare,
                     hurdleRate,
                     spotMarketIndex,
                     bump,
                     permissioned,
                     vaultProtocol,
                     fuelDistributionMode,
                     feeUpdateStatus,
                     vaultClass,
                     lastCumulativeFuelPerShareTs,
                     cumulativeFuelPerShare,
                     cumulativeFuel,
                     managerBorrowedValue,
                     padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    i += Borsh.writeArray(name, _data, i);
    pubkey.write(_data, i);
    i += 32;
    manager.write(_data, i);
    i += 32;
    tokenAccount.write(_data, i);
    i += 32;
    userStats.write(_data, i);
    i += 32;
    user.write(_data, i);
    i += 32;
    delegate.write(_data, i);
    i += 32;
    liquidationDelegate.write(_data, i);
    i += 32;
    putInt128LE(_data, i, userShares);
    i += 16;
    putInt128LE(_data, i, totalShares);
    i += 16;
    putInt64LE(_data, i, lastFeeUpdateTs);
    i += 8;
    putInt64LE(_data, i, liquidationStartTs);
    i += 8;
    putInt64LE(_data, i, redeemPeriod);
    i += 8;
    putInt64LE(_data, i, totalWithdrawRequested);
    i += 8;
    putInt64LE(_data, i, maxTokens);
    i += 8;
    putInt64LE(_data, i, managementFee);
    i += 8;
    putInt64LE(_data, i, initTs);
    i += 8;
    putInt64LE(_data, i, netDeposits);
    i += 8;
    putInt64LE(_data, i, managerNetDeposits);
    i += 8;
    putInt64LE(_data, i, totalDeposits);
    i += 8;
    putInt64LE(_data, i, totalWithdraws);
    i += 8;
    putInt64LE(_data, i, managerTotalDeposits);
    i += 8;
    putInt64LE(_data, i, managerTotalWithdraws);
    i += 8;
    putInt64LE(_data, i, managerTotalFee);
    i += 8;
    putInt64LE(_data, i, managerTotalProfitShare);
    i += 8;
    putInt64LE(_data, i, minDepositAmount);
    i += 8;
    i += Borsh.write(lastManagerWithdrawRequest, _data, i);
    putInt32LE(_data, i, sharesBase);
    i += 4;
    putInt32LE(_data, i, profitShare);
    i += 4;
    putInt32LE(_data, i, hurdleRate);
    i += 4;
    putInt16LE(_data, i, spotMarketIndex);
    i += 2;
    _data[i] = (byte) bump;
    ++i;
    _data[i] = (byte) (permissioned ? 1 : 0);
    ++i;
    _data[i] = (byte) (vaultProtocol ? 1 : 0);
    ++i;
    _data[i] = (byte) fuelDistributionMode;
    ++i;
    _data[i] = (byte) feeUpdateStatus;
    ++i;
    _data[i] = (byte) vaultClass;
    ++i;
    putInt32LE(_data, i, lastCumulativeFuelPerShareTs);
    i += 4;
    putInt128LE(_data, i, cumulativeFuelPerShare);
    i += 16;
    putInt128LE(_data, i, cumulativeFuel);
    i += 16;
    putInt64LE(_data, i, managerBorrowedValue);
    i += 8;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
