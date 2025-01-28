package software.sava.anchor.programs.glam_v0.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ManagerKind implements Borsh.Enum {

  Wallet,
  Squads;

  public static ManagerKind read(final byte[] _data, final int offset) {
    return Borsh.read(ManagerKind.values(), _data, offset);
  }
}
