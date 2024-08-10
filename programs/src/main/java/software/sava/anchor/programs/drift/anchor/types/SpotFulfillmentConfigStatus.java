package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum SpotFulfillmentConfigStatus implements Borsh.Enum {

  Enabled,
  Disabled;

  public static SpotFulfillmentConfigStatus read(final byte[] _data, final int offset) {
    return Borsh.read(SpotFulfillmentConfigStatus.values(), _data, offset);
  }
}