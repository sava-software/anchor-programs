package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ClusterHistoryEntry(int totalBlocks,
                                  int epoch,
                                  byte[] padding0,
                                  long epochStartTimestamp,
                                  byte[] padding) implements Borsh {

  public static final int BYTES = 256;

  public static ClusterHistoryEntry read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var totalBlocks = getInt32LE(_data, i);
    i += 4;
    final var epoch = getInt16LE(_data, i);
    i += 2;
    final var padding0 = new byte[2];
    i += Borsh.readArray(padding0, _data, i);
    final var epochStartTimestamp = getInt64LE(_data, i);
    i += 8;
    final var padding = new byte[240];
    Borsh.readArray(padding, _data, i);
    return new ClusterHistoryEntry(totalBlocks,
                                   epoch,
                                   padding0,
                                   epochStartTimestamp,
                                   padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, totalBlocks);
    i += 4;
    putInt16LE(_data, i, epoch);
    i += 2;
    i += Borsh.writeArray(padding0, _data, i);
    putInt64LE(_data, i, epochStartTimestamp);
    i += 8;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
