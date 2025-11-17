package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CappedMostRecentOfData(short[] sourceEntries,
                                     int maxDivergenceBps,
                                     long sourcesMaxAgeS,
                                     int capEntry) implements Borsh {

  public static final int BYTES = 20;
  public static final int SOURCE_ENTRIES_LEN = 4;

  public static CappedMostRecentOfData read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var sourceEntries = new short[4];
    i += Borsh.readArray(sourceEntries, _data, i);
    final var maxDivergenceBps = getInt16LE(_data, i);
    i += 2;
    final var sourcesMaxAgeS = getInt64LE(_data, i);
    i += 8;
    final var capEntry = getInt16LE(_data, i);
    return new CappedMostRecentOfData(sourceEntries,
                                      maxDivergenceBps,
                                      sourcesMaxAgeS,
                                      capEntry);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArrayChecked(sourceEntries, 4, _data, i);
    putInt16LE(_data, i, maxDivergenceBps);
    i += 2;
    putInt64LE(_data, i, sourcesMaxAgeS);
    i += 8;
    putInt16LE(_data, i, capEntry);
    i += 2;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
