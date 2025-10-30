package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ConstituentStatus implements Borsh.Enum {

  ReduceOnly,
  Decommissioned;

  public static ConstituentStatus read(final byte[] _data, final int offset) {
    return Borsh.read(ConstituentStatus.values(), _data, offset);
  }
}