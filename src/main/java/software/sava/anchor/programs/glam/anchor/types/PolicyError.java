package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

public enum PolicyError implements Borsh.Enum {

  TransfersDisabled,
  AmountTooBig,
  LockOut;

  public static PolicyError read(final byte[] _data, final int offset) {
    return Borsh.read(PolicyError.values(), _data, offset);
  }
}