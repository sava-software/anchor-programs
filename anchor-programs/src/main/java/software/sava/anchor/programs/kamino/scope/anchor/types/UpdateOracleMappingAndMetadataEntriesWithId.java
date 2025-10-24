package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record UpdateOracleMappingAndMetadataEntriesWithId(int entryId, UpdateOracleMappingAndMetadataEntry[] updates) implements Borsh {

  public static UpdateOracleMappingAndMetadataEntriesWithId read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var entryId = getInt16LE(_data, i);
    i += 2;
    final var updates = Borsh.readVector(UpdateOracleMappingAndMetadataEntry.class, UpdateOracleMappingAndMetadataEntry::read, _data, i);
    return new UpdateOracleMappingAndMetadataEntriesWithId(entryId, updates);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, entryId);
    i += 2;
    i += Borsh.writeVector(updates, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 2 + Borsh.lenVector(updates);
  }
}
