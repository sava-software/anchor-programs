package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum InsuranceFundOperation implements Borsh.Enum {

  Init,
  Add,
  RequestRemove,
  Remove;

  public static InsuranceFundOperation read(final byte[] _data, final int offset) {
    return Borsh.read(InsuranceFundOperation.values(), _data, offset);
  }
}