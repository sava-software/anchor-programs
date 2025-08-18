package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.borsh.Borsh;

public record MarginfiAccountCreateEvent(AccountEventHeader header) implements Borsh {

  public static MarginfiAccountCreateEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var header = AccountEventHeader.read(_data, offset);
    return new MarginfiAccountCreateEvent(header);
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
