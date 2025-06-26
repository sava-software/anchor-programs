package software.sava.anchor.programs.kamino.lend.anchor.types;

import software.sava.core.borsh.Borsh;

public enum UpdateGlobalConfigMode implements Borsh.Enum {

  PendingAdmin,
  FeeCollector;

  public static UpdateGlobalConfigMode read(final byte[] _data, final int offset) {
    return Borsh.read(UpdateGlobalConfigMode.values(), _data, offset);
  }
}