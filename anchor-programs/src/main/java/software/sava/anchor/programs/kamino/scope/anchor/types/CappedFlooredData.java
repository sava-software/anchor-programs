package software.sava.anchor.programs.kamino.scope.anchor.types;

import java.util.OptionalInt;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record CappedFlooredData(int sourceEntry,
                                OptionalInt capEntry,
                                OptionalInt floorEntry) implements Borsh {

  public static CappedFlooredData read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var sourceEntry = getInt16LE(_data, i);
    i += 2;
    final var capEntry = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
    if (capEntry.isPresent()) {
      i += 2;
    }
    final var floorEntry = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
    return new CappedFlooredData(sourceEntry, capEntry, floorEntry);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, sourceEntry);
    i += 2;
    i += Borsh.writeOptionalshort(capEntry, _data, i);
    i += Borsh.writeOptionalshort(floorEntry, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 2 + (capEntry == null || capEntry.isEmpty() ? 1 : (1 + 2)) + (floorEntry == null || floorEntry.isEmpty() ? 1 : (1 + 2));
  }
}
