package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

public enum MarketStatusBehavior implements Borsh.Enum {

  AllUpdates,
  Open,
  OpenAndPrePost;

  public static MarketStatusBehavior read(final byte[] _data, final int offset) {
    return Borsh.read(MarketStatusBehavior.values(), _data, offset);
  }
}