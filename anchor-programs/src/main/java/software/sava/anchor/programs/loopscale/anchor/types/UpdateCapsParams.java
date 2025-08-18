package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

public record UpdateCapsParams(ParsedPrincipalCaps borrowCaps,
                               ParsedPrincipalCaps withdrawCaps,
                               ParsedPrincipalCaps supplyCaps) implements Borsh {

  public static UpdateCapsParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var borrowCaps = _data[i++] == 0 ? null : ParsedPrincipalCaps.read(_data, i);
    if (borrowCaps != null) {
      i += Borsh.len(borrowCaps);
    }
    final var withdrawCaps = _data[i++] == 0 ? null : ParsedPrincipalCaps.read(_data, i);
    if (withdrawCaps != null) {
      i += Borsh.len(withdrawCaps);
    }
    final var supplyCaps = _data[i++] == 0 ? null : ParsedPrincipalCaps.read(_data, i);
    return new UpdateCapsParams(borrowCaps, withdrawCaps, supplyCaps);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(borrowCaps, _data, i);
    i += Borsh.writeOptional(withdrawCaps, _data, i);
    i += Borsh.writeOptional(supplyCaps, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (borrowCaps == null ? 1 : (1 + Borsh.len(borrowCaps))) + (withdrawCaps == null ? 1 : (1 + Borsh.len(withdrawCaps))) + (supplyCaps == null ? 1 : (1 + Borsh.len(supplyCaps)));
  }
}
