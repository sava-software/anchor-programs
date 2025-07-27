package software.sava.anchor.programs.metadao.launchpad.anchor.types;

import software.sava.core.borsh.Borsh;

public enum LaunchState implements Borsh.Enum {

  Initialized,
  Live,
  Complete,
  Refunding;

  public static LaunchState read(final byte[] _data, final int offset) {
    return Borsh.read(LaunchState.values(), _data, offset);
  }
}