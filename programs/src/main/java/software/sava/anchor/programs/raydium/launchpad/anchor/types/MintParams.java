package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.encoding.ByteUtil.getInt32LE;

// Represents the parameters for initializing a new token mint
// # Fields
// * `decimals` - Number of decimal places for the token
// * `name` - Name of the token
// * `symbol` - Symbol/ticker of the token
// * `uri` - URI pointing to token metadata
public record MintParams(int decimals,
                         String name, byte[] _name,
                         String symbol, byte[] _symbol,
                         String uri, byte[] _uri) implements Borsh {

  public static MintParams createRecord(final int decimals,
                                        final String name,
                                        final String symbol,
                                        final String uri) {
    return new MintParams(decimals,
                          name, name.getBytes(UTF_8),
                          symbol, symbol.getBytes(UTF_8),
                          uri, uri.getBytes(UTF_8));
  }

  public static MintParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var decimals = _data[i] & 0xFF;
    ++i;
    final var name = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var symbol = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var uri = Borsh.string(_data, i);
    return new MintParams(decimals,
                          name, name.getBytes(UTF_8),
                          symbol, symbol.getBytes(UTF_8),
                          uri, uri.getBytes(UTF_8));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) decimals;
    ++i;
    i += Borsh.writeVector(_name, _data, i);
    i += Borsh.writeVector(_symbol, _data, i);
    i += Borsh.writeVector(_uri, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 1 + Borsh.lenVector(_name) + Borsh.lenVector(_symbol) + Borsh.lenVector(_uri);
  }
}
