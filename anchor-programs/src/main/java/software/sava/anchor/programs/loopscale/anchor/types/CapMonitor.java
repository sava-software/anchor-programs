package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

public record CapMonitor(PodU64 startTime1hr,
                         PodU64 startTime24hr,
                         PodU64 principal1hr,
                         PodU64 principal24hr) implements Borsh {

  public static final int BYTES = 32;

  public static CapMonitor read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var startTime1hr = PodU64.read(_data, i);
    i += Borsh.len(startTime1hr);
    final var startTime24hr = PodU64.read(_data, i);
    i += Borsh.len(startTime24hr);
    final var principal1hr = PodU64.read(_data, i);
    i += Borsh.len(principal1hr);
    final var principal24hr = PodU64.read(_data, i);
    return new CapMonitor(startTime1hr,
                          startTime24hr,
                          principal1hr,
                          principal24hr);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(startTime1hr, _data, i);
    i += Borsh.write(startTime24hr, _data, i);
    i += Borsh.write(principal1hr, _data, i);
    i += Borsh.write(principal24hr, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
