package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import java.lang.Boolean;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PullFeedInitParams(byte[] feedHash,
                                 long maxVariance,
                                 int minResponses,
                                 byte[] name,
                                 long recentSlot,
                                 byte[] ipfsHash,
                                 int minSampleSize,
                                 int maxStaleness,
                                 Boolean permitWriteByAuthority) implements Borsh {

  public static PullFeedInitParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var feedHash = new byte[32];
    i += Borsh.readArray(feedHash, _data, i);
    final var maxVariance = getInt64LE(_data, i);
    i += 8;
    final var minResponses = getInt32LE(_data, i);
    i += 4;
    final var name = new byte[32];
    i += Borsh.readArray(name, _data, i);
    final var recentSlot = getInt64LE(_data, i);
    i += 8;
    final var ipfsHash = new byte[32];
    i += Borsh.readArray(ipfsHash, _data, i);
    final var minSampleSize = _data[i] & 0xFF;
    ++i;
    final var maxStaleness = getInt32LE(_data, i);
    i += 4;
    final var permitWriteByAuthority = _data[i++] == 0 ? null : _data[i] == 1;
    return new PullFeedInitParams(feedHash,
                                  maxVariance,
                                  minResponses,
                                  name,
                                  recentSlot,
                                  ipfsHash,
                                  minSampleSize,
                                  maxStaleness,
                                  permitWriteByAuthority);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(feedHash, _data, i);
    putInt64LE(_data, i, maxVariance);
    i += 8;
    putInt32LE(_data, i, minResponses);
    i += 4;
    i += Borsh.writeArray(name, _data, i);
    putInt64LE(_data, i, recentSlot);
    i += 8;
    i += Borsh.writeArray(ipfsHash, _data, i);
    _data[i] = (byte) minSampleSize;
    ++i;
    putInt32LE(_data, i, maxStaleness);
    i += 4;
    i += Borsh.writeOptional(permitWriteByAuthority, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenArray(feedHash)
         + 8
         + 4
         + Borsh.lenArray(name)
         + 8
         + Borsh.lenArray(ipfsHash)
         + 1
         + 4
         + (permitWriteByAuthority == null ? 1 : (1 + 1));
  }
}
