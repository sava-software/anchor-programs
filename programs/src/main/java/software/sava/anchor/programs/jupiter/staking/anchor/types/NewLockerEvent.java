package software.sava.anchor.programs.jupiter.staking.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record NewLockerEvent(PublicKey governor,
                             PublicKey locker,
                             PublicKey tokenMint,
                             LockerParams params) implements Borsh {

  public static final int BYTES = 121;

  public static NewLockerEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var governor = readPubKey(_data, i);
    i += 32;
    final var locker = readPubKey(_data, i);
    i += 32;
    final var tokenMint = readPubKey(_data, i);
    i += 32;
    final var params = LockerParams.read(_data, i);
    return new NewLockerEvent(governor,
                              locker,
                              tokenMint,
                              params);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    governor.write(_data, i);
    i += 32;
    locker.write(_data, i);
    i += 32;
    tokenMint.write(_data, i);
    i += 32;
    i += Borsh.write(params, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
