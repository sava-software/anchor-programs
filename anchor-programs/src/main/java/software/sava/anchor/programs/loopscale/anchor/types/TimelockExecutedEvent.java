package software.sava.anchor.programs.loopscale.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

public record TimelockExecutedEvent(String timelock, byte[] _timelock) implements Borsh {

  public static TimelockExecutedEvent createRecord(final String timelock) {
    return new TimelockExecutedEvent(timelock, timelock.getBytes(UTF_8));
  }

  public static TimelockExecutedEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var timelock = Borsh.string(_data, offset);
    return new TimelockExecutedEvent(timelock, timelock.getBytes(UTF_8));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_timelock, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_timelock);
  }
}
