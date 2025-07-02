package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record CrankFeeWhitelistCreated(PublicKey cranker) implements Borsh {

  public static final int BYTES = 32;

  public static CrankFeeWhitelistCreated read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var cranker = readPubKey(_data, offset);
    return new CrankFeeWhitelistCreated(cranker);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    cranker.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
