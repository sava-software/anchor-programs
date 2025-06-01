package software.sava.anchor.programs.kamino.vaults.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record PythConfiguration(// Pubkey of the base price feed (disabled if `null` or `default`)
                                PublicKey price) implements Borsh {

  public static final int BYTES = 32;

  public static PythConfiguration read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var price = readPubKey(_data, offset);
    return new PythConfiguration(price);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    price.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
