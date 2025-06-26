package software.sava.anchor.programs.kamino.vaults.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record SwitchboardConfiguration(// Pubkey of the base price feed (disabled if `null` or `default`)
                                       PublicKey priceAggregator,
                                       PublicKey twapAggregator) implements Borsh {

  public static final int BYTES = 64;

  public static SwitchboardConfiguration read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var priceAggregator = readPubKey(_data, i);
    i += 32;
    final var twapAggregator = readPubKey(_data, i);
    return new SwitchboardConfiguration(priceAggregator, twapAggregator);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    priceAggregator.write(_data, i);
    i += 32;
    twapAggregator.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
