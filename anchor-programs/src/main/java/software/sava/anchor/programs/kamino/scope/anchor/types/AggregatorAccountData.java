package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record AggregatorAccountData(byte[] name,
                                    byte[] metadata,
                                    PublicKey authorWallet,
                                    PublicKey queuePubkey,
                                    int oracleRequestBatchSize,
                                    int minOracleResults,
                                    int minJobResults,
                                    int minUpdateDelaySeconds,
                                    long startAfter,
                                    SwitchboardDecimal varianceThreshold,
                                    long forceReportPeriod,
                                    long expiration,
                                    long consecutiveFailureCount,
                                    long nextAllowedUpdateTime,
                                    boolean isLocked,
                                    byte[] schedule,
                                    AggregatorRound latestConfirmedRound,
                                    AggregatorRound currentRound,
                                    PublicKey[] jobPubkeysData,
                                    Hash[] jobHashes,
                                    int jobPubkeysSize,
                                    byte[] jobsChecksum,
                                    PublicKey authority,
                                    byte[] ebuf) implements Borsh {

  public static final int BYTES = 3843;
  public static final int NAME_LEN = 32;
  public static final int METADATA_LEN = 128;
  public static final int SCHEDULE_LEN = 32;
  public static final int JOB_PUBKEYS_DATA_LEN = 16;
  public static final int JOB_HASHES_LEN = 16;
  public static final int JOBS_CHECKSUM_LEN = 32;
  public static final int EBUF_LEN = 224;

  public static AggregatorAccountData read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var name = new byte[32];
    i += Borsh.readArray(name, _data, i);
    final var metadata = new byte[128];
    i += Borsh.readArray(metadata, _data, i);
    final var authorWallet = readPubKey(_data, i);
    i += 32;
    final var queuePubkey = readPubKey(_data, i);
    i += 32;
    final var oracleRequestBatchSize = getInt32LE(_data, i);
    i += 4;
    final var minOracleResults = getInt32LE(_data, i);
    i += 4;
    final var minJobResults = getInt32LE(_data, i);
    i += 4;
    final var minUpdateDelaySeconds = getInt32LE(_data, i);
    i += 4;
    final var startAfter = getInt64LE(_data, i);
    i += 8;
    final var varianceThreshold = SwitchboardDecimal.read(_data, i);
    i += Borsh.len(varianceThreshold);
    final var forceReportPeriod = getInt64LE(_data, i);
    i += 8;
    final var expiration = getInt64LE(_data, i);
    i += 8;
    final var consecutiveFailureCount = getInt64LE(_data, i);
    i += 8;
    final var nextAllowedUpdateTime = getInt64LE(_data, i);
    i += 8;
    final var isLocked = _data[i] == 1;
    ++i;
    final var schedule = new byte[32];
    i += Borsh.readArray(schedule, _data, i);
    final var latestConfirmedRound = AggregatorRound.read(_data, i);
    i += Borsh.len(latestConfirmedRound);
    final var currentRound = AggregatorRound.read(_data, i);
    i += Borsh.len(currentRound);
    final var jobPubkeysData = new PublicKey[16];
    i += Borsh.readArray(jobPubkeysData, _data, i);
    final var jobHashes = new Hash[16];
    i += Borsh.readArray(jobHashes, Hash::read, _data, i);
    final var jobPubkeysSize = getInt32LE(_data, i);
    i += 4;
    final var jobsChecksum = new byte[32];
    i += Borsh.readArray(jobsChecksum, _data, i);
    final var authority = readPubKey(_data, i);
    i += 32;
    final var ebuf = new byte[224];
    Borsh.readArray(ebuf, _data, i);
    return new AggregatorAccountData(name,
                                     metadata,
                                     authorWallet,
                                     queuePubkey,
                                     oracleRequestBatchSize,
                                     minOracleResults,
                                     minJobResults,
                                     minUpdateDelaySeconds,
                                     startAfter,
                                     varianceThreshold,
                                     forceReportPeriod,
                                     expiration,
                                     consecutiveFailureCount,
                                     nextAllowedUpdateTime,
                                     isLocked,
                                     schedule,
                                     latestConfirmedRound,
                                     currentRound,
                                     jobPubkeysData,
                                     jobHashes,
                                     jobPubkeysSize,
                                     jobsChecksum,
                                     authority,
                                     ebuf);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(name, _data, i);
    i += Borsh.writeArray(metadata, _data, i);
    authorWallet.write(_data, i);
    i += 32;
    queuePubkey.write(_data, i);
    i += 32;
    putInt32LE(_data, i, oracleRequestBatchSize);
    i += 4;
    putInt32LE(_data, i, minOracleResults);
    i += 4;
    putInt32LE(_data, i, minJobResults);
    i += 4;
    putInt32LE(_data, i, minUpdateDelaySeconds);
    i += 4;
    putInt64LE(_data, i, startAfter);
    i += 8;
    i += Borsh.write(varianceThreshold, _data, i);
    putInt64LE(_data, i, forceReportPeriod);
    i += 8;
    putInt64LE(_data, i, expiration);
    i += 8;
    putInt64LE(_data, i, consecutiveFailureCount);
    i += 8;
    putInt64LE(_data, i, nextAllowedUpdateTime);
    i += 8;
    _data[i] = (byte) (isLocked ? 1 : 0);
    ++i;
    i += Borsh.writeArray(schedule, _data, i);
    i += Borsh.write(latestConfirmedRound, _data, i);
    i += Borsh.write(currentRound, _data, i);
    i += Borsh.writeArray(jobPubkeysData, _data, i);
    i += Borsh.writeArray(jobHashes, _data, i);
    putInt32LE(_data, i, jobPubkeysSize);
    i += 4;
    i += Borsh.writeArray(jobsChecksum, _data, i);
    authority.write(_data, i);
    i += 32;
    i += Borsh.writeArray(ebuf, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
