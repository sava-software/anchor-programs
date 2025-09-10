package software.sava.anchor.programs.glam.protocol.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ActionType implements Borsh.Enum {

  AddExternalAccount,
  DeleteExternalAccount,
  DeleteExternalAccountIfZeroLamports,
  DeleteExternalAccountIfZeroBalance,
  AddAsset,
  DeleteAsset,
  Refund;

  public static ActionType read(final byte[] _data, final int offset) {
    return Borsh.read(ActionType.values(), _data, offset);
  }
}