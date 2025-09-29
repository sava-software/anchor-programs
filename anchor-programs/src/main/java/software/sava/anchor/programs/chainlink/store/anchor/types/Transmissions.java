package software.sava.anchor.programs.chainlink.store.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record Transmissions(PublicKey _address,
                            Discriminator discriminator,
                            int version,
                            int state,
                            PublicKey owner,
                            PublicKey proposedOwner,
                            PublicKey writer,
                            byte[] description,
                            int decimals,
                            int flaggingThreshold,
                            int latestRoundId,
                            int granularity,
                            int liveLength,
                            int liveCursor,
                            int historicalCursor) implements Borsh {

  public static final int BYTES = 160;
  public static final int DESCRIPTION_LEN = 32;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int VERSION_OFFSET = 8;
  public static final int STATE_OFFSET = 9;
  public static final int OWNER_OFFSET = 10;
  public static final int PROPOSED_OWNER_OFFSET = 42;
  public static final int WRITER_OFFSET = 74;
  public static final int DESCRIPTION_OFFSET = 106;
  public static final int DECIMALS_OFFSET = 138;
  public static final int FLAGGING_THRESHOLD_OFFSET = 139;
  public static final int LATEST_ROUND_ID_OFFSET = 143;
  public static final int GRANULARITY_OFFSET = 147;
  public static final int LIVE_LENGTH_OFFSET = 148;
  public static final int LIVE_CURSOR_OFFSET = 152;
  public static final int HISTORICAL_CURSOR_OFFSET = 156;

  public static Filter createVersionFilter(final int version) {
    return Filter.createMemCompFilter(VERSION_OFFSET, new byte[]{(byte) version});
  }

  public static Filter createStateFilter(final int state) {
    return Filter.createMemCompFilter(STATE_OFFSET, new byte[]{(byte) state});
  }

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static Filter createProposedOwnerFilter(final PublicKey proposedOwner) {
    return Filter.createMemCompFilter(PROPOSED_OWNER_OFFSET, proposedOwner);
  }

  public static Filter createWriterFilter(final PublicKey writer) {
    return Filter.createMemCompFilter(WRITER_OFFSET, writer);
  }

  public static Filter createDecimalsFilter(final int decimals) {
    return Filter.createMemCompFilter(DECIMALS_OFFSET, new byte[]{(byte) decimals});
  }

  public static Filter createFlaggingThresholdFilter(final int flaggingThreshold) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, flaggingThreshold);
    return Filter.createMemCompFilter(FLAGGING_THRESHOLD_OFFSET, _data);
  }

  public static Filter createLatestRoundIdFilter(final int latestRoundId) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, latestRoundId);
    return Filter.createMemCompFilter(LATEST_ROUND_ID_OFFSET, _data);
  }

  public static Filter createGranularityFilter(final int granularity) {
    return Filter.createMemCompFilter(GRANULARITY_OFFSET, new byte[]{(byte) granularity});
  }

  public static Filter createLiveLengthFilter(final int liveLength) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, liveLength);
    return Filter.createMemCompFilter(LIVE_LENGTH_OFFSET, _data);
  }

  public static Filter createLiveCursorFilter(final int liveCursor) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, liveCursor);
    return Filter.createMemCompFilter(LIVE_CURSOR_OFFSET, _data);
  }

  public static Filter createHistoricalCursorFilter(final int historicalCursor) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, historicalCursor);
    return Filter.createMemCompFilter(HISTORICAL_CURSOR_OFFSET, _data);
  }

  public static Transmissions read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Transmissions read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Transmissions read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Transmissions> FACTORY = Transmissions::read;

  public static Transmissions read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var version = _data[i] & 0xFF;
    ++i;
    final var state = _data[i] & 0xFF;
    ++i;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var proposedOwner = readPubKey(_data, i);
    i += 32;
    final var writer = readPubKey(_data, i);
    i += 32;
    final var description = new byte[32];
    i += Borsh.readArray(description, _data, i);
    final var decimals = _data[i] & 0xFF;
    ++i;
    final var flaggingThreshold = getInt32LE(_data, i);
    i += 4;
    final var latestRoundId = getInt32LE(_data, i);
    i += 4;
    final var granularity = _data[i] & 0xFF;
    ++i;
    final var liveLength = getInt32LE(_data, i);
    i += 4;
    final var liveCursor = getInt32LE(_data, i);
    i += 4;
    final var historicalCursor = getInt32LE(_data, i);
    return new Transmissions(_address,
                             discriminator,
                             version,
                             state,
                             owner,
                             proposedOwner,
                             writer,
                             description,
                             decimals,
                             flaggingThreshold,
                             latestRoundId,
                             granularity,
                             liveLength,
                             liveCursor,
                             historicalCursor);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    _data[i] = (byte) version;
    ++i;
    _data[i] = (byte) state;
    ++i;
    owner.write(_data, i);
    i += 32;
    proposedOwner.write(_data, i);
    i += 32;
    writer.write(_data, i);
    i += 32;
    i += Borsh.writeArrayChecked(description, 32, _data, i);
    _data[i] = (byte) decimals;
    ++i;
    putInt32LE(_data, i, flaggingThreshold);
    i += 4;
    putInt32LE(_data, i, latestRoundId);
    i += 4;
    _data[i] = (byte) granularity;
    ++i;
    putInt32LE(_data, i, liveLength);
    i += 4;
    putInt32LE(_data, i, liveCursor);
    i += 4;
    putInt32LE(_data, i, historicalCursor);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
