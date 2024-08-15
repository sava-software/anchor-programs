package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

public enum Action implements Borsh.Enum {

  Subscribe,
  Redeem;

  public static Action read(final byte[] _data, final int offset) {
    return Borsh.read(Action.values(), _data, offset);
  }
}