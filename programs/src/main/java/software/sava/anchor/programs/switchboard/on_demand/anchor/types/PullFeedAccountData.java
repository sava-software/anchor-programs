package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

// A representation of the data in a pull feed account.
public record PullFeedAccountData(PublicKey _address,
                                  Discriminator discriminator,
                                  // The oracle submissions for this feed.
                                  OracleSubmission[] submissions,
                                  // The public key of the authority that can update the feed hash that
                                  // this account will use for registering updates.
                                  PublicKey authority,
                                  // The public key of the queue which oracles must be bound to in order to
                                  // submit data to this feed.
                                  PublicKey queue,
                                  // SHA-256 hash of the job schema oracles will execute to produce data
                                  // for this feed.
                                  byte[] feedHash,
                                  // The slot at which this account was initialized.
                                  long initializedAt,
                                  long permissions,
                                  long maxVariance,
                                  int minResponses,
                                  byte[] name,
                                  byte[] padding1,
                                  int permitWriteByAuthority,
                                  int historicalResultIdx,
                                  int minSampleSize,
                                  long lastUpdateTimestamp,
                                  long lutSlot,
                                  byte[] reserved1,
                                  CurrentResult result,
                                  int maxStaleness,
                                  byte[] padding2,
                                  CompactResult[] historicalResults,
                                  byte[] ebuf4,
                                  byte[] ebuf3,
                                  byte[] ebuf2) implements Borsh {

  public static final int BYTES = 3208;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(196, 27, 108, 196, 10, 215, 219, 40);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int SUBMISSIONS_OFFSET = 8;
  public static final int AUTHORITY_OFFSET = 2056;
  public static final int QUEUE_OFFSET = 2088;
  public static final int FEED_HASH_OFFSET = 2120;
  public static final int INITIALIZED_AT_OFFSET = 2152;
  public static final int PERMISSIONS_OFFSET = 2160;
  public static final int MAX_VARIANCE_OFFSET = 2168;
  public static final int MIN_RESPONSES_OFFSET = 2176;
  public static final int NAME_OFFSET = 2180;
  public static final int PADDING1_OFFSET = 2212;
  public static final int PERMIT_WRITE_BY_AUTHORITY_OFFSET = 2213;
  public static final int HISTORICAL_RESULT_IDX_OFFSET = 2214;
  public static final int MIN_SAMPLE_SIZE_OFFSET = 2215;
  public static final int LAST_UPDATE_TIMESTAMP_OFFSET = 2216;
  public static final int LUT_SLOT_OFFSET = 2224;
  public static final int RESERVED1_OFFSET = 2232;
  public static final int RESULT_OFFSET = 2264;
  public static final int MAX_STALENESS_OFFSET = 2392;
  public static final int PADDING2_OFFSET = 2396;
  public static final int HISTORICAL_RESULTS_OFFSET = 2408;
  public static final int EBUF4_OFFSET = 2920;
  public static final int EBUF3_OFFSET = 2928;
  public static final int EBUF2_OFFSET = 2952;

  public static Filter createAuthorityFilter(final PublicKey authority) {
    return Filter.createMemCompFilter(AUTHORITY_OFFSET, authority);
  }

  public static Filter createQueueFilter(final PublicKey queue) {
    return Filter.createMemCompFilter(QUEUE_OFFSET, queue);
  }

  public static Filter createInitializedAtFilter(final long initializedAt) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, initializedAt);
    return Filter.createMemCompFilter(INITIALIZED_AT_OFFSET, _data);
  }

  public static Filter createPermissionsFilter(final long permissions) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, permissions);
    return Filter.createMemCompFilter(PERMISSIONS_OFFSET, _data);
  }

  public static Filter createMaxVarianceFilter(final long maxVariance) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxVariance);
    return Filter.createMemCompFilter(MAX_VARIANCE_OFFSET, _data);
  }

  public static Filter createMinResponsesFilter(final int minResponses) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, minResponses);
    return Filter.createMemCompFilter(MIN_RESPONSES_OFFSET, _data);
  }

  public static Filter createPermitWriteByAuthorityFilter(final int permitWriteByAuthority) {
    return Filter.createMemCompFilter(PERMIT_WRITE_BY_AUTHORITY_OFFSET, new byte[]{(byte) permitWriteByAuthority});
  }

  public static Filter createHistoricalResultIdxFilter(final int historicalResultIdx) {
    return Filter.createMemCompFilter(HISTORICAL_RESULT_IDX_OFFSET, new byte[]{(byte) historicalResultIdx});
  }

  public static Filter createMinSampleSizeFilter(final int minSampleSize) {
    return Filter.createMemCompFilter(MIN_SAMPLE_SIZE_OFFSET, new byte[]{(byte) minSampleSize});
  }

  public static Filter createLastUpdateTimestampFilter(final long lastUpdateTimestamp) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastUpdateTimestamp);
    return Filter.createMemCompFilter(LAST_UPDATE_TIMESTAMP_OFFSET, _data);
  }

  public static Filter createLutSlotFilter(final long lutSlot) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lutSlot);
    return Filter.createMemCompFilter(LUT_SLOT_OFFSET, _data);
  }

  public static Filter createResultFilter(final CurrentResult result) {
    return Filter.createMemCompFilter(RESULT_OFFSET, result.write());
  }

  public static Filter createMaxStalenessFilter(final int maxStaleness) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, maxStaleness);
    return Filter.createMemCompFilter(MAX_STALENESS_OFFSET, _data);
  }

  public static PullFeedAccountData read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static PullFeedAccountData read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static PullFeedAccountData read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], PullFeedAccountData> FACTORY = PullFeedAccountData::read;

  public static PullFeedAccountData read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var submissions = new OracleSubmission[32];
    i += Borsh.readArray(submissions, OracleSubmission::read, _data, i);
    final var authority = readPubKey(_data, i);
    i += 32;
    final var queue = readPubKey(_data, i);
    i += 32;
    final var feedHash = new byte[32];
    i += Borsh.readArray(feedHash, _data, i);
    final var initializedAt = getInt64LE(_data, i);
    i += 8;
    final var permissions = getInt64LE(_data, i);
    i += 8;
    final var maxVariance = getInt64LE(_data, i);
    i += 8;
    final var minResponses = getInt32LE(_data, i);
    i += 4;
    final var name = new byte[32];
    i += Borsh.readArray(name, _data, i);
    final var padding1 = new byte[1];
    i += Borsh.readArray(padding1, _data, i);
    final var permitWriteByAuthority = _data[i] & 0xFF;
    ++i;
    final var historicalResultIdx = _data[i] & 0xFF;
    ++i;
    final var minSampleSize = _data[i] & 0xFF;
    ++i;
    final var lastUpdateTimestamp = getInt64LE(_data, i);
    i += 8;
    final var lutSlot = getInt64LE(_data, i);
    i += 8;
    final var reserved1 = new byte[32];
    i += Borsh.readArray(reserved1, _data, i);
    final var result = CurrentResult.read(_data, i);
    i += Borsh.len(result);
    final var maxStaleness = getInt32LE(_data, i);
    i += 4;
    final var padding2 = new byte[12];
    i += Borsh.readArray(padding2, _data, i);
    final var historicalResults = new CompactResult[32];
    i += Borsh.readArray(historicalResults, CompactResult::read, _data, i);
    final var ebuf4 = new byte[8];
    i += Borsh.readArray(ebuf4, _data, i);
    final var ebuf3 = new byte[24];
    i += Borsh.readArray(ebuf3, _data, i);
    final var ebuf2 = new byte[256];
    Borsh.readArray(ebuf2, _data, i);
    return new PullFeedAccountData(_address,
                                   discriminator,
                                   submissions,
                                   authority,
                                   queue,
                                   feedHash,
                                   initializedAt,
                                   permissions,
                                   maxVariance,
                                   minResponses,
                                   name,
                                   padding1,
                                   permitWriteByAuthority,
                                   historicalResultIdx,
                                   minSampleSize,
                                   lastUpdateTimestamp,
                                   lutSlot,
                                   reserved1,
                                   result,
                                   maxStaleness,
                                   padding2,
                                   historicalResults,
                                   ebuf4,
                                   ebuf3,
                                   ebuf2);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    i += Borsh.writeArray(submissions, _data, i);
    authority.write(_data, i);
    i += 32;
    queue.write(_data, i);
    i += 32;
    i += Borsh.writeArray(feedHash, _data, i);
    putInt64LE(_data, i, initializedAt);
    i += 8;
    putInt64LE(_data, i, permissions);
    i += 8;
    putInt64LE(_data, i, maxVariance);
    i += 8;
    putInt32LE(_data, i, minResponses);
    i += 4;
    i += Borsh.writeArray(name, _data, i);
    i += Borsh.writeArray(padding1, _data, i);
    _data[i] = (byte) permitWriteByAuthority;
    ++i;
    _data[i] = (byte) historicalResultIdx;
    ++i;
    _data[i] = (byte) minSampleSize;
    ++i;
    putInt64LE(_data, i, lastUpdateTimestamp);
    i += 8;
    putInt64LE(_data, i, lutSlot);
    i += 8;
    i += Borsh.writeArray(reserved1, _data, i);
    i += Borsh.write(result, _data, i);
    putInt32LE(_data, i, maxStaleness);
    i += 4;
    i += Borsh.writeArray(padding2, _data, i);
    i += Borsh.writeArray(historicalResults, _data, i);
    i += Borsh.writeArray(ebuf4, _data, i);
    i += Borsh.writeArray(ebuf3, _data, i);
    i += Borsh.writeArray(ebuf2, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
