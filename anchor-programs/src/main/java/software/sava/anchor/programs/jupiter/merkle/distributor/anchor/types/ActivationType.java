package software.sava.anchor.programs.jupiter.merkle.distributor.anchor.types;

import software.sava.core.borsh.Borsh;

// Type of the activation
public enum ActivationType implements Borsh.Enum {

  Slot,
  Timestamp;

  public static ActivationType read(final byte[] _data, final int offset) {
    return Borsh.read(ActivationType.values(), _data, offset);
  }
}