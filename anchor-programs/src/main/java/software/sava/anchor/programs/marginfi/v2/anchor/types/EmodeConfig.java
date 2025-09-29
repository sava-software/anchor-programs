package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.borsh.Borsh;

// An emode configuration. Each bank has one such configuration, but this may also be the
// intersection of many configurations (see `reconcile_emode_configs`). For example, the risk
// engine creates such an intersection from all the emode config of all banks the user is borrowing
// from.
public record EmodeConfig(EmodeEntry[] entries) implements Borsh {

  public static final int BYTES = 400;
  public static final int ENTRIES_LEN = 10;

  public static EmodeConfig read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var entries = new EmodeEntry[10];
    Borsh.readArray(entries, EmodeEntry::read, _data, offset);
    return new EmodeConfig(entries);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArrayChecked(entries, 10, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
