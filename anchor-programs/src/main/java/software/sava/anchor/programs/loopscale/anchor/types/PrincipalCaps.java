package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

public record PrincipalCaps(PodU64 max1hr,
                            PodU64 max24hr,
                            // This is the global supply/borrow cap. Always disabled for withdraw caps.
                            PodU64 maxOutstanding) implements Borsh {

  public static final int BYTES = 24;

  public static PrincipalCaps read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var max1hr = PodU64.read(_data, i);
    i += Borsh.len(max1hr);
    final var max24hr = PodU64.read(_data, i);
    i += Borsh.len(max24hr);
    final var maxOutstanding = PodU64.read(_data, i);
    return new PrincipalCaps(max1hr, max24hr, maxOutstanding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(max1hr, _data, i);
    i += Borsh.write(max24hr, _data, i);
    i += Borsh.write(maxOutstanding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
