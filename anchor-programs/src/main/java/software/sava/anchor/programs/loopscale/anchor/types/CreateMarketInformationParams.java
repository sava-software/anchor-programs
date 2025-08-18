package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record CreateMarketInformationParams(PublicKey principalMint, PublicKey authority) implements Borsh {

  public static final int BYTES = 64;

  public static CreateMarketInformationParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var principalMint = readPubKey(_data, i);
    i += 32;
    final var authority = readPubKey(_data, i);
    return new CreateMarketInformationParams(principalMint, authority);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    principalMint.write(_data, i);
    i += 32;
    authority.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
