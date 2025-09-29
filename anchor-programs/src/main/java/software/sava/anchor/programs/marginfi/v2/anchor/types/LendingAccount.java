package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.borsh.Borsh;

public record LendingAccount(Balance[] balances, long[] padding) implements Borsh {

  public static final int BYTES = 1728;
  public static final int BALANCES_LEN = 16;
  public static final int PADDING_LEN = 8;

  public static LendingAccount read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var balances = new Balance[16];
    i += Borsh.readArray(balances, Balance::read, _data, i);
    final var padding = new long[8];
    Borsh.readArray(padding, _data, i);
    return new LendingAccount(balances, padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArrayChecked(balances, 16, _data, i);
    i += Borsh.writeArrayChecked(padding, 8, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
