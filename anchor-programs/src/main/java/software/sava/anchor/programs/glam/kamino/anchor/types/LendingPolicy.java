package software.sava.anchor.programs.glam.kamino.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

public record LendingPolicy(PublicKey[] marketsAllowlist, PublicKey[] borrowAllowlist) implements Borsh {

  public static LendingPolicy read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var marketsAllowlist = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(marketsAllowlist);
    final var borrowAllowlist = Borsh.readPublicKeyVector(_data, i);
    return new LendingPolicy(marketsAllowlist, borrowAllowlist);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(marketsAllowlist, _data, i);
    i += Borsh.writeVector(borrowAllowlist, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(marketsAllowlist) + Borsh.lenVector(borrowAllowlist);
  }
}
