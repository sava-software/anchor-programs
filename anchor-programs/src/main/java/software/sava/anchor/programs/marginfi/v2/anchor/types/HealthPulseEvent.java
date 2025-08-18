package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record HealthPulseEvent(PublicKey account, HealthCache healthCache) implements Borsh {

  public static final int BYTES = 336;

  public static HealthPulseEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var account = readPubKey(_data, i);
    i += 32;
    final var healthCache = HealthCache.read(_data, i);
    return new HealthPulseEvent(account, healthCache);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    account.write(_data, i);
    i += 32;
    i += Borsh.write(healthCache, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
