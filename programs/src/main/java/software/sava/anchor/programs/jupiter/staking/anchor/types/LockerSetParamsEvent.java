package software.sava.anchor.programs.jupiter.staking.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record LockerSetParamsEvent(PublicKey locker,
                                   LockerParams prevParams,
                                   LockerParams params) implements Borsh {

  public static final int BYTES = 82;

  public static LockerSetParamsEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var locker = readPubKey(_data, i);
    i += 32;
    final var prevParams = LockerParams.read(_data, i);
    i += Borsh.len(prevParams);
    final var params = LockerParams.read(_data, i);
    return new LockerSetParamsEvent(locker, prevParams, params);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    locker.write(_data, i);
    i += 32;
    i += Borsh.write(prevParams, _data, i);
    i += Borsh.write(params, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
