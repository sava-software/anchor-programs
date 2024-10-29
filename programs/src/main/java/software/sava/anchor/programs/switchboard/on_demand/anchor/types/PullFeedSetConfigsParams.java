package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record PullFeedSetConfigsParams(byte[] feedHash,
                                       PublicKey authority,
                                       OptionalLong maxVariance,
                                       OptionalInt minResponses,
                                       byte[] name,
                                       byte[] ipfsHash,
                                       OptionalInt minSampleSize,
                                       OptionalInt maxStaleness) implements Borsh {

  public static PullFeedSetConfigsParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var feedHash = _data[i++] == 0 ? null : new byte[32];
    if (feedHash != null) {
      i += Borsh.readArray(feedHash, _data, i);
    }
    final var authority = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (authority != null) {
      i += 32;
    }
    final var maxVariance = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (maxVariance.isPresent()) {
      i += 8;
    }
    final var minResponses = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (minResponses.isPresent()) {
      i += 4;
    }
    final var name = _data[i++] == 0 ? null : new byte[32];
    if (name != null) {
      i += Borsh.readArray(name, _data, i);
    }
    final var ipfsHash = _data[i++] == 0 ? null : new byte[32];
    if (ipfsHash != null) {
      i += Borsh.readArray(ipfsHash, _data, i);
    }
    final var minSampleSize = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    if (minSampleSize.isPresent()) {
      ++i;
    }
    final var maxStaleness = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    return new PullFeedSetConfigsParams(feedHash,
                                        authority,
                                        maxVariance,
                                        minResponses,
                                        name,
                                        ipfsHash,
                                        minSampleSize,
                                        maxStaleness);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    if (feedHash == null || feedHash.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeArray(feedHash, _data, i);
    }
    i += Borsh.writeOptional(authority, _data, i);
    i += Borsh.writeOptional(maxVariance, _data, i);
    i += Borsh.writeOptional(minResponses, _data, i);
    if (name == null || name.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeArray(name, _data, i);
    }
    if (ipfsHash == null || ipfsHash.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeArray(ipfsHash, _data, i);
    }
    i += Borsh.writeOptionalbyte(minSampleSize, _data, i);
    i += Borsh.writeOptional(maxStaleness, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (feedHash == null || feedHash.length == 0 ? 1 : (1 + Borsh.lenArray(feedHash)))
         + (authority == null ? 1 : (1 + 32))
         + (maxVariance == null || maxVariance.isEmpty() ? 1 : (1 + 8))
         + (minResponses == null || minResponses.isEmpty() ? 1 : (1 + 4))
         + (name == null || name.length == 0 ? 1 : (1 + Borsh.lenArray(name)))
         + (ipfsHash == null || ipfsHash.length == 0 ? 1 : (1 + Borsh.lenArray(ipfsHash)))
         + (minSampleSize == null || minSampleSize.isEmpty() ? 1 : (1 + 1))
         + (maxStaleness == null || maxStaleness.isEmpty() ? 1 : (1 + 4));
  }
}
