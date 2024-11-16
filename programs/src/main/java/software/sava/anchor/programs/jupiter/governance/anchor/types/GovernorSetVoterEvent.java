package software.sava.anchor.programs.jupiter.governance.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record GovernorSetVoterEvent(PublicKey governor,
                                    PublicKey prevLocker,
                                    PublicKey newLocker) implements Borsh {

  public static final int BYTES = 96;

  public static GovernorSetVoterEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var governor = readPubKey(_data, i);
    i += 32;
    final var prevLocker = readPubKey(_data, i);
    i += 32;
    final var newLocker = readPubKey(_data, i);
    return new GovernorSetVoterEvent(governor, prevLocker, newLocker);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    governor.write(_data, i);
    i += 32;
    prevLocker.write(_data, i);
    i += 32;
    newLocker.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
