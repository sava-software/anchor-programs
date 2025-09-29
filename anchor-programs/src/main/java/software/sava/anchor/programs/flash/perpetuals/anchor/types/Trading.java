package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record Trading(PublicKey _address,
                      Discriminator discriminator,
                      PublicKey nftMint,
                      PublicKey owner,
                      PublicKey delegate,
                      boolean isInitialized,
                      int level,
                      int bump,
                      long voltagePoints,
                      VoltageStats stats,
                      VoltageStats snapshot,
                      long timestamp,
                      long counter,
                      PublicKey tokenStakeAccount,
                      boolean burnt,
                      byte[] padding) implements Borsh {

  public static final int BYTES = 275;
  public static final int PADDING_LEN = 15;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int NFT_MINT_OFFSET = 8;
  public static final int OWNER_OFFSET = 40;
  public static final int DELEGATE_OFFSET = 72;
  public static final int IS_INITIALIZED_OFFSET = 104;
  public static final int LEVEL_OFFSET = 105;
  public static final int BUMP_OFFSET = 106;
  public static final int VOLTAGE_POINTS_OFFSET = 107;
  public static final int STATS_OFFSET = 115;
  public static final int SNAPSHOT_OFFSET = 163;
  public static final int TIMESTAMP_OFFSET = 211;
  public static final int COUNTER_OFFSET = 219;
  public static final int TOKEN_STAKE_ACCOUNT_OFFSET = 227;
  public static final int BURNT_OFFSET = 259;
  public static final int PADDING_OFFSET = 260;

  public static Filter createNftMintFilter(final PublicKey nftMint) {
    return Filter.createMemCompFilter(NFT_MINT_OFFSET, nftMint);
  }

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static Filter createDelegateFilter(final PublicKey delegate) {
    return Filter.createMemCompFilter(DELEGATE_OFFSET, delegate);
  }

  public static Filter createIsInitializedFilter(final boolean isInitialized) {
    return Filter.createMemCompFilter(IS_INITIALIZED_OFFSET, new byte[]{(byte) (isInitialized ? 1 : 0)});
  }

  public static Filter createLevelFilter(final int level) {
    return Filter.createMemCompFilter(LEVEL_OFFSET, new byte[]{(byte) level});
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createVoltagePointsFilter(final long voltagePoints) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, voltagePoints);
    return Filter.createMemCompFilter(VOLTAGE_POINTS_OFFSET, _data);
  }

  public static Filter createStatsFilter(final VoltageStats stats) {
    return Filter.createMemCompFilter(STATS_OFFSET, stats.write());
  }

  public static Filter createSnapshotFilter(final VoltageStats snapshot) {
    return Filter.createMemCompFilter(SNAPSHOT_OFFSET, snapshot.write());
  }

  public static Filter createTimestampFilter(final long timestamp) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, timestamp);
    return Filter.createMemCompFilter(TIMESTAMP_OFFSET, _data);
  }

  public static Filter createCounterFilter(final long counter) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, counter);
    return Filter.createMemCompFilter(COUNTER_OFFSET, _data);
  }

  public static Filter createTokenStakeAccountFilter(final PublicKey tokenStakeAccount) {
    return Filter.createMemCompFilter(TOKEN_STAKE_ACCOUNT_OFFSET, tokenStakeAccount);
  }

  public static Filter createBurntFilter(final boolean burnt) {
    return Filter.createMemCompFilter(BURNT_OFFSET, new byte[]{(byte) (burnt ? 1 : 0)});
  }

  public static Trading read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Trading read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Trading read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Trading> FACTORY = Trading::read;

  public static Trading read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var nftMint = readPubKey(_data, i);
    i += 32;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var delegate = readPubKey(_data, i);
    i += 32;
    final var isInitialized = _data[i] == 1;
    ++i;
    final var level = _data[i] & 0xFF;
    ++i;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var voltagePoints = getInt64LE(_data, i);
    i += 8;
    final var stats = VoltageStats.read(_data, i);
    i += Borsh.len(stats);
    final var snapshot = VoltageStats.read(_data, i);
    i += Borsh.len(snapshot);
    final var timestamp = getInt64LE(_data, i);
    i += 8;
    final var counter = getInt64LE(_data, i);
    i += 8;
    final var tokenStakeAccount = readPubKey(_data, i);
    i += 32;
    final var burnt = _data[i] == 1;
    ++i;
    final var padding = new byte[15];
    Borsh.readArray(padding, _data, i);
    return new Trading(_address,
                       discriminator,
                       nftMint,
                       owner,
                       delegate,
                       isInitialized,
                       level,
                       bump,
                       voltagePoints,
                       stats,
                       snapshot,
                       timestamp,
                       counter,
                       tokenStakeAccount,
                       burnt,
                       padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    nftMint.write(_data, i);
    i += 32;
    owner.write(_data, i);
    i += 32;
    delegate.write(_data, i);
    i += 32;
    _data[i] = (byte) (isInitialized ? 1 : 0);
    ++i;
    _data[i] = (byte) level;
    ++i;
    _data[i] = (byte) bump;
    ++i;
    putInt64LE(_data, i, voltagePoints);
    i += 8;
    i += Borsh.write(stats, _data, i);
    i += Borsh.write(snapshot, _data, i);
    putInt64LE(_data, i, timestamp);
    i += 8;
    putInt64LE(_data, i, counter);
    i += 8;
    tokenStakeAccount.write(_data, i);
    i += 32;
    _data[i] = (byte) (burnt ? 1 : 0);
    ++i;
    i += Borsh.writeArrayChecked(padding, 15, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
