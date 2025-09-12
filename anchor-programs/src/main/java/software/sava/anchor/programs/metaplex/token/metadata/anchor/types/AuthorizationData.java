package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.Borsh;

public record AuthorizationData(Payload payload) implements Borsh {

  public static AuthorizationData read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var payload = Payload.read(_data, offset);
    return new AuthorizationData(payload);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(payload, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(payload);
  }
}
