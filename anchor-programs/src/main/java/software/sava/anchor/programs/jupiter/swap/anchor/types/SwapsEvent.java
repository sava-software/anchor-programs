package software.sava.anchor.programs.jupiter.swap.anchor.types;

import software.sava.core.borsh.Borsh;

public record SwapsEvent(SwapEventV2[] swapEvents) implements Borsh {

  public static SwapsEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var swapEvents = Borsh.readVector(SwapEventV2.class, SwapEventV2::read, _data, offset);
    return new SwapsEvent(swapEvents);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(swapEvents, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(swapEvents);
  }
}
