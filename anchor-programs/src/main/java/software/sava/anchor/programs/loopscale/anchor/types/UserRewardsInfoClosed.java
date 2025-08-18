package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record UserRewardsInfoClosed(PublicKey address) implements Borsh {

  public static final int BYTES = 32;

  public static UserRewardsInfoClosed read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var address = readPubKey(_data, offset);
    return new UserRewardsInfoClosed(address);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    address.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
