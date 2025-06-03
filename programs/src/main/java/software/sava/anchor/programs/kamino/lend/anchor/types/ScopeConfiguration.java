package software.sava.anchor.programs.kamino.lend.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record ScopeConfiguration(// Pubkey of the scope price feed (disabled if `null` or `default`)
                                 PublicKey priceFeed,
                                 // This is the scope_id price chain that results in a price for the token
                                 short[] priceChain,
                                 // This is the scope_id price chain for the twap
                                 short[] twapChain) implements Borsh {

  public static final int BYTES = 48;

  public static ScopeConfiguration read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var priceFeed = readPubKey(_data, i);
    i += 32;
    final var priceChain = new short[4];
    i += Borsh.readArray(priceChain, _data, i);
    final var twapChain = new short[4];
    Borsh.readArray(twapChain, _data, i);
    return new ScopeConfiguration(priceFeed, priceChain, twapChain);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    priceFeed.write(_data, i);
    i += 32;
    i += Borsh.writeArray(priceChain, _data, i);
    i += Borsh.writeArray(twapChain, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
