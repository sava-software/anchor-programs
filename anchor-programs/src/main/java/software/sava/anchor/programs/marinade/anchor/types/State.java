package software.sava.anchor.programs.marinade.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record State(PublicKey _address,
                    Discriminator discriminator,
                    PublicKey msolMint,
                    PublicKey adminAuthority,
                    PublicKey operationalSolAccount,
                    PublicKey treasuryMsolAccount,
                    int reserveBumpSeed,
                    int msolMintAuthorityBumpSeed,
                    long rentExemptForTokenAcc,
                    Fee rewardFee,
                    StakeSystem stakeSystem,
                    ValidatorSystem validatorSystem,
                    LiqPool liqPool,
                    long availableReserveBalance,
                    long msolSupply,
                    long msolPrice,
                    // count tickets for delayed-unstake
                    long circulatingTicketCount,
                    // total lamports amount of generated and not claimed yet tickets
                    long circulatingTicketBalance,
                    long lentFromReserve,
                    long minDeposit,
                    long minWithdraw,
                    long stakingSolCap,
                    long emergencyCoolingDown,
                    // emergency pause
                    PublicKey pauseAuthority,
                    boolean paused,
                    FeeCents delayedUnstakeFee,
                    FeeCents withdrawStakeAccountFee,
                    boolean withdrawStakeAccountEnabled,
                    long lastStakeMoveEpoch,
                    long stakeMoved,
                    Fee maxStakeMovedPerEpoch) implements Borsh {

  public static final int BYTES = 638;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int MSOL_MINT_OFFSET = 8;
  public static final int ADMIN_AUTHORITY_OFFSET = 40;
  public static final int OPERATIONAL_SOL_ACCOUNT_OFFSET = 72;
  public static final int TREASURY_MSOL_ACCOUNT_OFFSET = 104;
  public static final int RESERVE_BUMP_SEED_OFFSET = 136;
  public static final int MSOL_MINT_AUTHORITY_BUMP_SEED_OFFSET = 137;
  public static final int RENT_EXEMPT_FOR_TOKEN_ACC_OFFSET = 138;
  public static final int REWARD_FEE_OFFSET = 146;
  public static final int STAKE_SYSTEM_OFFSET = 150;
  public static final int VALIDATOR_SYSTEM_OFFSET = 264;
  public static final int LIQ_POOL_OFFSET = 385;
  public static final int AVAILABLE_RESERVE_BALANCE_OFFSET = 496;
  public static final int MSOL_SUPPLY_OFFSET = 504;
  public static final int MSOL_PRICE_OFFSET = 512;
  public static final int CIRCULATING_TICKET_COUNT_OFFSET = 520;
  public static final int CIRCULATING_TICKET_BALANCE_OFFSET = 528;
  public static final int LENT_FROM_RESERVE_OFFSET = 536;
  public static final int MIN_DEPOSIT_OFFSET = 544;
  public static final int MIN_WITHDRAW_OFFSET = 552;
  public static final int STAKING_SOL_CAP_OFFSET = 560;
  public static final int EMERGENCY_COOLING_DOWN_OFFSET = 568;
  public static final int PAUSE_AUTHORITY_OFFSET = 576;
  public static final int PAUSED_OFFSET = 608;
  public static final int DELAYED_UNSTAKE_FEE_OFFSET = 609;
  public static final int WITHDRAW_STAKE_ACCOUNT_FEE_OFFSET = 613;
  public static final int WITHDRAW_STAKE_ACCOUNT_ENABLED_OFFSET = 617;
  public static final int LAST_STAKE_MOVE_EPOCH_OFFSET = 618;
  public static final int STAKE_MOVED_OFFSET = 626;
  public static final int MAX_STAKE_MOVED_PER_EPOCH_OFFSET = 634;

  public static Filter createMsolMintFilter(final PublicKey msolMint) {
    return Filter.createMemCompFilter(MSOL_MINT_OFFSET, msolMint);
  }

  public static Filter createAdminAuthorityFilter(final PublicKey adminAuthority) {
    return Filter.createMemCompFilter(ADMIN_AUTHORITY_OFFSET, adminAuthority);
  }

  public static Filter createOperationalSolAccountFilter(final PublicKey operationalSolAccount) {
    return Filter.createMemCompFilter(OPERATIONAL_SOL_ACCOUNT_OFFSET, operationalSolAccount);
  }

  public static Filter createTreasuryMsolAccountFilter(final PublicKey treasuryMsolAccount) {
    return Filter.createMemCompFilter(TREASURY_MSOL_ACCOUNT_OFFSET, treasuryMsolAccount);
  }

  public static Filter createReserveBumpSeedFilter(final int reserveBumpSeed) {
    return Filter.createMemCompFilter(RESERVE_BUMP_SEED_OFFSET, new byte[]{(byte) reserveBumpSeed});
  }

  public static Filter createMsolMintAuthorityBumpSeedFilter(final int msolMintAuthorityBumpSeed) {
    return Filter.createMemCompFilter(MSOL_MINT_AUTHORITY_BUMP_SEED_OFFSET, new byte[]{(byte) msolMintAuthorityBumpSeed});
  }

  public static Filter createRentExemptForTokenAccFilter(final long rentExemptForTokenAcc) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, rentExemptForTokenAcc);
    return Filter.createMemCompFilter(RENT_EXEMPT_FOR_TOKEN_ACC_OFFSET, _data);
  }

  public static Filter createRewardFeeFilter(final Fee rewardFee) {
    return Filter.createMemCompFilter(REWARD_FEE_OFFSET, rewardFee.write());
  }

  public static Filter createStakeSystemFilter(final StakeSystem stakeSystem) {
    return Filter.createMemCompFilter(STAKE_SYSTEM_OFFSET, stakeSystem.write());
  }

  public static Filter createValidatorSystemFilter(final ValidatorSystem validatorSystem) {
    return Filter.createMemCompFilter(VALIDATOR_SYSTEM_OFFSET, validatorSystem.write());
  }

  public static Filter createLiqPoolFilter(final LiqPool liqPool) {
    return Filter.createMemCompFilter(LIQ_POOL_OFFSET, liqPool.write());
  }

  public static Filter createAvailableReserveBalanceFilter(final long availableReserveBalance) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, availableReserveBalance);
    return Filter.createMemCompFilter(AVAILABLE_RESERVE_BALANCE_OFFSET, _data);
  }

  public static Filter createMsolSupplyFilter(final long msolSupply) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, msolSupply);
    return Filter.createMemCompFilter(MSOL_SUPPLY_OFFSET, _data);
  }

  public static Filter createMsolPriceFilter(final long msolPrice) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, msolPrice);
    return Filter.createMemCompFilter(MSOL_PRICE_OFFSET, _data);
  }

  public static Filter createCirculatingTicketCountFilter(final long circulatingTicketCount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, circulatingTicketCount);
    return Filter.createMemCompFilter(CIRCULATING_TICKET_COUNT_OFFSET, _data);
  }

  public static Filter createCirculatingTicketBalanceFilter(final long circulatingTicketBalance) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, circulatingTicketBalance);
    return Filter.createMemCompFilter(CIRCULATING_TICKET_BALANCE_OFFSET, _data);
  }

  public static Filter createLentFromReserveFilter(final long lentFromReserve) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lentFromReserve);
    return Filter.createMemCompFilter(LENT_FROM_RESERVE_OFFSET, _data);
  }

  public static Filter createMinDepositFilter(final long minDeposit) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minDeposit);
    return Filter.createMemCompFilter(MIN_DEPOSIT_OFFSET, _data);
  }

  public static Filter createMinWithdrawFilter(final long minWithdraw) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minWithdraw);
    return Filter.createMemCompFilter(MIN_WITHDRAW_OFFSET, _data);
  }

  public static Filter createStakingSolCapFilter(final long stakingSolCap) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, stakingSolCap);
    return Filter.createMemCompFilter(STAKING_SOL_CAP_OFFSET, _data);
  }

  public static Filter createEmergencyCoolingDownFilter(final long emergencyCoolingDown) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, emergencyCoolingDown);
    return Filter.createMemCompFilter(EMERGENCY_COOLING_DOWN_OFFSET, _data);
  }

  public static Filter createPauseAuthorityFilter(final PublicKey pauseAuthority) {
    return Filter.createMemCompFilter(PAUSE_AUTHORITY_OFFSET, pauseAuthority);
  }

  public static Filter createPausedFilter(final boolean paused) {
    return Filter.createMemCompFilter(PAUSED_OFFSET, new byte[]{(byte) (paused ? 1 : 0)});
  }

  public static Filter createDelayedUnstakeFeeFilter(final FeeCents delayedUnstakeFee) {
    return Filter.createMemCompFilter(DELAYED_UNSTAKE_FEE_OFFSET, delayedUnstakeFee.write());
  }

  public static Filter createWithdrawStakeAccountFeeFilter(final FeeCents withdrawStakeAccountFee) {
    return Filter.createMemCompFilter(WITHDRAW_STAKE_ACCOUNT_FEE_OFFSET, withdrawStakeAccountFee.write());
  }

  public static Filter createWithdrawStakeAccountEnabledFilter(final boolean withdrawStakeAccountEnabled) {
    return Filter.createMemCompFilter(WITHDRAW_STAKE_ACCOUNT_ENABLED_OFFSET, new byte[]{(byte) (withdrawStakeAccountEnabled ? 1 : 0)});
  }

  public static Filter createLastStakeMoveEpochFilter(final long lastStakeMoveEpoch) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastStakeMoveEpoch);
    return Filter.createMemCompFilter(LAST_STAKE_MOVE_EPOCH_OFFSET, _data);
  }

  public static Filter createStakeMovedFilter(final long stakeMoved) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, stakeMoved);
    return Filter.createMemCompFilter(STAKE_MOVED_OFFSET, _data);
  }

  public static Filter createMaxStakeMovedPerEpochFilter(final Fee maxStakeMovedPerEpoch) {
    return Filter.createMemCompFilter(MAX_STAKE_MOVED_PER_EPOCH_OFFSET, maxStakeMovedPerEpoch.write());
  }

  public static State read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static State read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static State read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], State> FACTORY = State::read;

  public static State read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var msolMint = readPubKey(_data, i);
    i += 32;
    final var adminAuthority = readPubKey(_data, i);
    i += 32;
    final var operationalSolAccount = readPubKey(_data, i);
    i += 32;
    final var treasuryMsolAccount = readPubKey(_data, i);
    i += 32;
    final var reserveBumpSeed = _data[i] & 0xFF;
    ++i;
    final var msolMintAuthorityBumpSeed = _data[i] & 0xFF;
    ++i;
    final var rentExemptForTokenAcc = getInt64LE(_data, i);
    i += 8;
    final var rewardFee = Fee.read(_data, i);
    i += Borsh.len(rewardFee);
    final var stakeSystem = StakeSystem.read(_data, i);
    i += Borsh.len(stakeSystem);
    final var validatorSystem = ValidatorSystem.read(_data, i);
    i += Borsh.len(validatorSystem);
    final var liqPool = LiqPool.read(_data, i);
    i += Borsh.len(liqPool);
    final var availableReserveBalance = getInt64LE(_data, i);
    i += 8;
    final var msolSupply = getInt64LE(_data, i);
    i += 8;
    final var msolPrice = getInt64LE(_data, i);
    i += 8;
    final var circulatingTicketCount = getInt64LE(_data, i);
    i += 8;
    final var circulatingTicketBalance = getInt64LE(_data, i);
    i += 8;
    final var lentFromReserve = getInt64LE(_data, i);
    i += 8;
    final var minDeposit = getInt64LE(_data, i);
    i += 8;
    final var minWithdraw = getInt64LE(_data, i);
    i += 8;
    final var stakingSolCap = getInt64LE(_data, i);
    i += 8;
    final var emergencyCoolingDown = getInt64LE(_data, i);
    i += 8;
    final var pauseAuthority = readPubKey(_data, i);
    i += 32;
    final var paused = _data[i] == 1;
    ++i;
    final var delayedUnstakeFee = FeeCents.read(_data, i);
    i += Borsh.len(delayedUnstakeFee);
    final var withdrawStakeAccountFee = FeeCents.read(_data, i);
    i += Borsh.len(withdrawStakeAccountFee);
    final var withdrawStakeAccountEnabled = _data[i] == 1;
    ++i;
    final var lastStakeMoveEpoch = getInt64LE(_data, i);
    i += 8;
    final var stakeMoved = getInt64LE(_data, i);
    i += 8;
    final var maxStakeMovedPerEpoch = Fee.read(_data, i);
    return new State(_address,
                     discriminator,
                     msolMint,
                     adminAuthority,
                     operationalSolAccount,
                     treasuryMsolAccount,
                     reserveBumpSeed,
                     msolMintAuthorityBumpSeed,
                     rentExemptForTokenAcc,
                     rewardFee,
                     stakeSystem,
                     validatorSystem,
                     liqPool,
                     availableReserveBalance,
                     msolSupply,
                     msolPrice,
                     circulatingTicketCount,
                     circulatingTicketBalance,
                     lentFromReserve,
                     minDeposit,
                     minWithdraw,
                     stakingSolCap,
                     emergencyCoolingDown,
                     pauseAuthority,
                     paused,
                     delayedUnstakeFee,
                     withdrawStakeAccountFee,
                     withdrawStakeAccountEnabled,
                     lastStakeMoveEpoch,
                     stakeMoved,
                     maxStakeMovedPerEpoch);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    msolMint.write(_data, i);
    i += 32;
    adminAuthority.write(_data, i);
    i += 32;
    operationalSolAccount.write(_data, i);
    i += 32;
    treasuryMsolAccount.write(_data, i);
    i += 32;
    _data[i] = (byte) reserveBumpSeed;
    ++i;
    _data[i] = (byte) msolMintAuthorityBumpSeed;
    ++i;
    putInt64LE(_data, i, rentExemptForTokenAcc);
    i += 8;
    i += Borsh.write(rewardFee, _data, i);
    i += Borsh.write(stakeSystem, _data, i);
    i += Borsh.write(validatorSystem, _data, i);
    i += Borsh.write(liqPool, _data, i);
    putInt64LE(_data, i, availableReserveBalance);
    i += 8;
    putInt64LE(_data, i, msolSupply);
    i += 8;
    putInt64LE(_data, i, msolPrice);
    i += 8;
    putInt64LE(_data, i, circulatingTicketCount);
    i += 8;
    putInt64LE(_data, i, circulatingTicketBalance);
    i += 8;
    putInt64LE(_data, i, lentFromReserve);
    i += 8;
    putInt64LE(_data, i, minDeposit);
    i += 8;
    putInt64LE(_data, i, minWithdraw);
    i += 8;
    putInt64LE(_data, i, stakingSolCap);
    i += 8;
    putInt64LE(_data, i, emergencyCoolingDown);
    i += 8;
    pauseAuthority.write(_data, i);
    i += 32;
    _data[i] = (byte) (paused ? 1 : 0);
    ++i;
    i += Borsh.write(delayedUnstakeFee, _data, i);
    i += Borsh.write(withdrawStakeAccountFee, _data, i);
    _data[i] = (byte) (withdrawStakeAccountEnabled ? 1 : 0);
    ++i;
    putInt64LE(_data, i, lastStakeMoveEpoch);
    i += 8;
    putInt64LE(_data, i, stakeMoved);
    i += 8;
    i += Borsh.write(maxStakeMovedPerEpoch, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
