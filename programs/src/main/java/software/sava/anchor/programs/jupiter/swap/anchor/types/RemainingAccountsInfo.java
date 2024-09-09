package software.sava.anchor.programs.jupiter.swap.anchor.types;

import software.sava.core.borsh.Borsh;

public record RemainingAccountsInfo(RemainingAccountsSlice[] slices) implements Borsh {

  public static RemainingAccountsInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var slices = Borsh.readVector(RemainingAccountsSlice.class, RemainingAccountsSlice::read, _data, offset);
    return new RemainingAccountsInfo(slices);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(slices, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(slices);
  }
}
