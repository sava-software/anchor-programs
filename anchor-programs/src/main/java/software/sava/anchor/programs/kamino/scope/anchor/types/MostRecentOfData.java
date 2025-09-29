package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record MostRecentOfData(short[] sourceEntries,
                               int maxDivergenceBps,
                               long sourcesMaxAgeS) implements Borsh {

  public static final int BYTES = 18;
  public static final int SOURCE_ENTRIES_LEN = 4;

  public static MostRecentOfData read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var sourceEntries = new short[4];
    i += Borsh.readArray(sourceEntries, _data, i);
    final var maxDivergenceBps = getInt16LE(_data, i);
    i += 2;
    final var sourcesMaxAgeS = getInt64LE(_data, i);
    return new MostRecentOfData(sourceEntries, maxDivergenceBps, sourcesMaxAgeS);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArrayChecked(sourceEntries, 4, _data, i);
    putInt16LE(_data, i, maxDivergenceBps);
    i += 2;
    putInt64LE(_data, i, sourcesMaxAgeS);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
