package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

public record V8V10(MarketStatusBehavior marketStatusBehavior) implements Borsh {

  public static final int BYTES = 1;

  public static V8V10 read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var marketStatusBehavior = MarketStatusBehavior.read(_data, offset);
    return new V8V10(marketStatusBehavior);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(marketStatusBehavior, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
