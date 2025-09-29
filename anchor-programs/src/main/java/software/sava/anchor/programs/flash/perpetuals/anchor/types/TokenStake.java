package software.sava.anchor.programs.flash.perpetuals.anchor.types;

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

public record TokenStake(PublicKey _address,
                         Discriminator discriminator,
                         PublicKey owner,
                         boolean isInitialized,
                         int bump,
                         int level,
                         int withdrawRequestCount,
                         WithdrawRequest[] withdrawRequest,
                         long activeStakeAmount,
                         long updateTimestamp,
                         long tradeTimestamp,
                         int tradeCounter,
                         int lastRewardEpochCount,
                         long rewardTokens,
                         long unclaimedRevenueAmount,
                         BigInteger revenueSnapshot,
                         long[] padding) implements Borsh {

  public static final int BYTES = 196;
  public static final int WITHDRAW_REQUEST_LEN = 5;
  public static final int PADDING_LEN = 1;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int OWNER_OFFSET = 8;
  public static final int IS_INITIALIZED_OFFSET = 40;
  public static final int BUMP_OFFSET = 41;
  public static final int LEVEL_OFFSET = 42;
  public static final int WITHDRAW_REQUEST_COUNT_OFFSET = 43;
  public static final int WITHDRAW_REQUEST_OFFSET = 44;
  public static final int ACTIVE_STAKE_AMOUNT_OFFSET = 124;
  public static final int UPDATE_TIMESTAMP_OFFSET = 132;
  public static final int TRADE_TIMESTAMP_OFFSET = 140;
  public static final int TRADE_COUNTER_OFFSET = 148;
  public static final int LAST_REWARD_EPOCH_COUNT_OFFSET = 152;
  public static final int REWARD_TOKENS_OFFSET = 156;
  public static final int UNCLAIMED_REVENUE_AMOUNT_OFFSET = 164;
  public static final int REVENUE_SNAPSHOT_OFFSET = 172;
  public static final int PADDING_OFFSET = 188;

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static Filter createIsInitializedFilter(final boolean isInitialized) {
    return Filter.createMemCompFilter(IS_INITIALIZED_OFFSET, new byte[]{(byte) (isInitialized ? 1 : 0)});
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createLevelFilter(final int level) {
    return Filter.createMemCompFilter(LEVEL_OFFSET, new byte[]{(byte) level});
  }

  public static Filter createWithdrawRequestCountFilter(final int withdrawRequestCount) {
    return Filter.createMemCompFilter(WITHDRAW_REQUEST_COUNT_OFFSET, new byte[]{(byte) withdrawRequestCount});
  }

  public static Filter createActiveStakeAmountFilter(final long activeStakeAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, activeStakeAmount);
    return Filter.createMemCompFilter(ACTIVE_STAKE_AMOUNT_OFFSET, _data);
  }

  public static Filter createUpdateTimestampFilter(final long updateTimestamp) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, updateTimestamp);
    return Filter.createMemCompFilter(UPDATE_TIMESTAMP_OFFSET, _data);
  }

  public static Filter createTradeTimestampFilter(final long tradeTimestamp) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, tradeTimestamp);
    return Filter.createMemCompFilter(TRADE_TIMESTAMP_OFFSET, _data);
  }

  public static Filter createTradeCounterFilter(final int tradeCounter) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, tradeCounter);
    return Filter.createMemCompFilter(TRADE_COUNTER_OFFSET, _data);
  }

  public static Filter createLastRewardEpochCountFilter(final int lastRewardEpochCount) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, lastRewardEpochCount);
    return Filter.createMemCompFilter(LAST_REWARD_EPOCH_COUNT_OFFSET, _data);
  }

  public static Filter createRewardTokensFilter(final long rewardTokens) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, rewardTokens);
    return Filter.createMemCompFilter(REWARD_TOKENS_OFFSET, _data);
  }

  public static Filter createUnclaimedRevenueAmountFilter(final long unclaimedRevenueAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, unclaimedRevenueAmount);
    return Filter.createMemCompFilter(UNCLAIMED_REVENUE_AMOUNT_OFFSET, _data);
  }

  public static Filter createRevenueSnapshotFilter(final BigInteger revenueSnapshot) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, revenueSnapshot);
    return Filter.createMemCompFilter(REVENUE_SNAPSHOT_OFFSET, _data);
  }

  public static TokenStake read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static TokenStake read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static TokenStake read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], TokenStake> FACTORY = TokenStake::read;

  public static TokenStake read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var owner = readPubKey(_data, i);
    i += 32;
    final var isInitialized = _data[i] == 1;
    ++i;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var level = _data[i] & 0xFF;
    ++i;
    final var withdrawRequestCount = _data[i] & 0xFF;
    ++i;
    final var withdrawRequest = new WithdrawRequest[5];
    i += Borsh.readArray(withdrawRequest, WithdrawRequest::read, _data, i);
    final var activeStakeAmount = getInt64LE(_data, i);
    i += 8;
    final var updateTimestamp = getInt64LE(_data, i);
    i += 8;
    final var tradeTimestamp = getInt64LE(_data, i);
    i += 8;
    final var tradeCounter = getInt32LE(_data, i);
    i += 4;
    final var lastRewardEpochCount = getInt32LE(_data, i);
    i += 4;
    final var rewardTokens = getInt64LE(_data, i);
    i += 8;
    final var unclaimedRevenueAmount = getInt64LE(_data, i);
    i += 8;
    final var revenueSnapshot = getInt128LE(_data, i);
    i += 16;
    final var padding = new long[1];
    Borsh.readArray(padding, _data, i);
    return new TokenStake(_address,
                          discriminator,
                          owner,
                          isInitialized,
                          bump,
                          level,
                          withdrawRequestCount,
                          withdrawRequest,
                          activeStakeAmount,
                          updateTimestamp,
                          tradeTimestamp,
                          tradeCounter,
                          lastRewardEpochCount,
                          rewardTokens,
                          unclaimedRevenueAmount,
                          revenueSnapshot,
                          padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    owner.write(_data, i);
    i += 32;
    _data[i] = (byte) (isInitialized ? 1 : 0);
    ++i;
    _data[i] = (byte) bump;
    ++i;
    _data[i] = (byte) level;
    ++i;
    _data[i] = (byte) withdrawRequestCount;
    ++i;
    i += Borsh.writeArrayChecked(withdrawRequest, 5, _data, i);
    putInt64LE(_data, i, activeStakeAmount);
    i += 8;
    putInt64LE(_data, i, updateTimestamp);
    i += 8;
    putInt64LE(_data, i, tradeTimestamp);
    i += 8;
    putInt32LE(_data, i, tradeCounter);
    i += 4;
    putInt32LE(_data, i, lastRewardEpochCount);
    i += 4;
    putInt64LE(_data, i, rewardTokens);
    i += 8;
    putInt64LE(_data, i, unclaimedRevenueAmount);
    i += 8;
    putInt128LE(_data, i, revenueSnapshot);
    i += 16;
    i += Borsh.writeArrayChecked(padding, 1, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
