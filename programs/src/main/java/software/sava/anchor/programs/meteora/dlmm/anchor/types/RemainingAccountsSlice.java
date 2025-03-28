package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

public record RemainingAccountsSlice(AccountsType accountsType, int length) implements Borsh {

  public static final int BYTES = 2;

  public static RemainingAccountsSlice read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var accountsType = AccountsType.read(_data, i);
    i += Borsh.len(accountsType);
    final var length = _data[i] & 0xFF;
    return new RemainingAccountsSlice(accountsType, length);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(accountsType, _data, i);
    _data[i] = (byte) length;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
