package software.sava.anchor.programs.glam.mint.anchor.types;

import software.sava.core.borsh.Borsh;

public enum RequestType implements Borsh.Enum {

  Subscription,
  Redemption;

  public static RequestType read(final byte[] _data, final int offset) {
    return Borsh.read(RequestType.values(), _data, offset);
  }
}