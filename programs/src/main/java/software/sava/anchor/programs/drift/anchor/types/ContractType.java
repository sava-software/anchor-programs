package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ContractType implements Borsh.Enum {

  Perpetual,
  Future,
  Prediction;

  public static ContractType read(final byte[] _data, final int offset) {
    return Borsh.read(ContractType.values(), _data, offset);
  }
}