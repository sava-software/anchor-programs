package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.borsh.Borsh;

public record MarginfiGroupCreateEvent(GroupEventHeader header) implements Borsh {

  public static MarginfiGroupCreateEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var header = GroupEventHeader.read(_data, offset);
    return new MarginfiGroupCreateEvent(header);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(header, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(header);
  }
}
