package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

public enum PriceUpdateResult implements Borsh.Enum {

  Updated,
  SuspendExistingPrice;

  public static PriceUpdateResult read(final byte[] _data, final int offset) {
    return Borsh.read(PriceUpdateResult.values(), _data, offset);
  }
}