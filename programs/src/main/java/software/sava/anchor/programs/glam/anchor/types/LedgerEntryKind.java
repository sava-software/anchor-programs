package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

public enum LedgerEntryKind implements Borsh.Enum {

  Subscription,
  Redemption;

  public static LedgerEntryKind read(final byte[] _data, final int offset) {
    return Borsh.read(LedgerEntryKind.values(), _data, offset);
  }
}