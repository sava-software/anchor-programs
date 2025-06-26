package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum AMMLiquiditySplit implements Borsh.Enum {

  ProtocolOwned,
  LPOwned,
  Shared;

  public static AMMLiquiditySplit read(final byte[] _data, final int offset) {
    return Borsh.read(AMMLiquiditySplit.values(), _data, offset);
  }
}