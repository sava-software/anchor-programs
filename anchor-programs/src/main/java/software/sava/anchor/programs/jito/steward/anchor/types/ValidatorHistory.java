package software.sava.anchor.programs.jito.steward.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record ValidatorHistory(PublicKey _address,
                               Discriminator discriminator,
                               int structVersion,
                               PublicKey voteAccount,
                               int index,
                               int bump,
                               byte[] padding0,
                               long lastIpTimestamp,
                               long lastVersionTimestamp,
                               byte[] padding1,
                               CircBuf history) implements Borsh {

  public static final int BYTES = 65856;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(205, 25, 8, 221, 253, 131, 2, 146);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int STRUCT_VERSION_OFFSET = 8;
  public static final int VOTE_ACCOUNT_OFFSET = 12;
  public static final int INDEX_OFFSET = 44;
  public static final int BUMP_OFFSET = 48;
  public static final int PADDING0_OFFSET = 49;
  public static final int LAST_IP_TIMESTAMP_OFFSET = 56;
  public static final int LAST_VERSION_TIMESTAMP_OFFSET = 64;
  public static final int PADDING1_OFFSET = 72;
  public static final int HISTORY_OFFSET = 304;

  public static Filter createStructVersionFilter(final int structVersion) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, structVersion);
    return Filter.createMemCompFilter(STRUCT_VERSION_OFFSET, _data);
  }

  public static Filter createVoteAccountFilter(final PublicKey voteAccount) {
    return Filter.createMemCompFilter(VOTE_ACCOUNT_OFFSET, voteAccount);
  }

  public static Filter createIndexFilter(final int index) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, index);
    return Filter.createMemCompFilter(INDEX_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createLastIpTimestampFilter(final long lastIpTimestamp) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastIpTimestamp);
    return Filter.createMemCompFilter(LAST_IP_TIMESTAMP_OFFSET, _data);
  }

  public static Filter createLastVersionTimestampFilter(final long lastVersionTimestamp) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastVersionTimestamp);
    return Filter.createMemCompFilter(LAST_VERSION_TIMESTAMP_OFFSET, _data);
  }

  public static ValidatorHistory read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static ValidatorHistory read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], ValidatorHistory> FACTORY = ValidatorHistory::read;

  public static ValidatorHistory read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var structVersion = getInt32LE(_data, i);
    i += 4;
    final var voteAccount = readPubKey(_data, i);
    i += 32;
    final var index = getInt32LE(_data, i);
    i += 4;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var padding0 = new byte[7];
    i += Borsh.readArray(padding0, _data, i);
    final var lastIpTimestamp = getInt64LE(_data, i);
    i += 8;
    final var lastVersionTimestamp = getInt64LE(_data, i);
    i += 8;
    final var padding1 = new byte[232];
    i += Borsh.readArray(padding1, _data, i);
    final var history = CircBuf.read(_data, i);
    return new ValidatorHistory(_address,
                                discriminator,
                                structVersion,
                                voteAccount,
                                index,
                                bump,
                                padding0,
                                lastIpTimestamp,
                                lastVersionTimestamp,
                                padding1,
                                history);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt32LE(_data, i, structVersion);
    i += 4;
    voteAccount.write(_data, i);
    i += 32;
    putInt32LE(_data, i, index);
    i += 4;
    _data[i] = (byte) bump;
    ++i;
    i += Borsh.writeArray(padding0, _data, i);
    putInt64LE(_data, i, lastIpTimestamp);
    i += 8;
    putInt64LE(_data, i, lastVersionTimestamp);
    i += 8;
    i += Borsh.writeArray(padding1, _data, i);
    i += Borsh.write(history, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
