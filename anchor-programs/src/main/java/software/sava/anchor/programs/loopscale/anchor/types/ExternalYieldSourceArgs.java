package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record ExternalYieldSourceArgs(int newExternalYieldSource, PublicKey externalYieldVault) implements Borsh {

  public static final int BYTES = 33;

  public static ExternalYieldSourceArgs read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var newExternalYieldSource = _data[i] & 0xFF;
    ++i;
    final var externalYieldVault = readPubKey(_data, i);
    return new ExternalYieldSourceArgs(newExternalYieldSource, externalYieldVault);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) newExternalYieldSource;
    ++i;
    externalYieldVault.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
