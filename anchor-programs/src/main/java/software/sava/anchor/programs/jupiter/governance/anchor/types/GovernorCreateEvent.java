package software.sava.anchor.programs.jupiter.governance.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record GovernorCreateEvent(PublicKey governor,
                                  PublicKey locker,
                                  PublicKey smartWallet,
                                  GovernanceParameters parameters) implements Borsh {

  public static final int BYTES = 128;

  public static GovernorCreateEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var governor = readPubKey(_data, i);
    i += 32;
    final var locker = readPubKey(_data, i);
    i += 32;
    final var smartWallet = readPubKey(_data, i);
    i += 32;
    final var parameters = GovernanceParameters.read(_data, i);
    return new GovernorCreateEvent(governor,
                                   locker,
                                   smartWallet,
                                   parameters);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    governor.write(_data, i);
    i += 32;
    locker.write(_data, i);
    i += 32;
    smartWallet.write(_data, i);
    i += 32;
    i += Borsh.write(parameters, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
