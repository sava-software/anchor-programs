package software.sava.anchor.programs.glam.spl.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

public record TransferPolicy(PublicKey[] allowlist) implements Borsh {

  public static TransferPolicy read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var allowlist = Borsh.readPublicKeyVector(_data, offset);
    return new TransferPolicy(allowlist);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(allowlist, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(allowlist);
  }
}
