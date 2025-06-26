package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public record RebalanceAutodriftState(RebalanceAutodriftWindow lastWindow,
                                      RebalanceAutodriftWindow currentWindow,
                                      RebalanceAutodriftStep step) implements Borsh {

  public static RebalanceAutodriftState read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lastWindow = RebalanceAutodriftWindow.read(_data, i);
    i += Borsh.len(lastWindow);
    final var currentWindow = RebalanceAutodriftWindow.read(_data, i);
    i += Borsh.len(currentWindow);
    final var step = RebalanceAutodriftStep.read(_data, i);
    return new RebalanceAutodriftState(lastWindow, currentWindow, step);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(lastWindow, _data, i);
    i += Borsh.write(currentWindow, _data, i);
    i += Borsh.write(step, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(lastWindow) + Borsh.len(currentWindow) + Borsh.len(step);
  }
}
