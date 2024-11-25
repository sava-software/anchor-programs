package software.sava.anchor.programs.moon.anchor.types;


import static java.nio.charset.StandardCharsets.UTF_8;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

import software.sava.core.borsh.Borsh;

public record TokenMintParams(String name, byte[] _name,
                              String symbol, byte[] _symbol,
                              String uri, byte[] _uri,
                              int decimals,
                              int collateralCurrency,
                              long amount,
                              int curveType,
                              int migrationTarget) implements Borsh {

  public static TokenMintParams createRecord(final String name,
                                             final String symbol,
                                             final String uri,
                                             final int decimals,
                                             final int collateralCurrency,
                                             final long amount,
                                             final int curveType,
                                             final int migrationTarget) {
    return new TokenMintParams(name, name.getBytes(UTF_8),
                               symbol, symbol.getBytes(UTF_8),
                               uri, uri.getBytes(UTF_8),
                               decimals,
                               collateralCurrency,
                               amount,
                               curveType,
                               migrationTarget);
  }

  public static TokenMintParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var name = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var symbol = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var uri = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var decimals = _data[i] & 0xFF;
    ++i;
    final var collateralCurrency = _data[i] & 0xFF;
    ++i;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var curveType = _data[i] & 0xFF;
    ++i;
    final var migrationTarget = _data[i] & 0xFF;
    return new TokenMintParams(name, name.getBytes(UTF_8),
                               symbol, symbol.getBytes(UTF_8),
                               uri, uri.getBytes(UTF_8),
                               decimals,
                               collateralCurrency,
                               amount,
                               curveType,
                               migrationTarget);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_name, _data, i);
    i += Borsh.writeVector(_symbol, _data, i);
    i += Borsh.writeVector(_uri, _data, i);
    _data[i] = (byte) decimals;
    ++i;
    _data[i] = (byte) collateralCurrency;
    ++i;
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) curveType;
    ++i;
    _data[i] = (byte) migrationTarget;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_name)
         + Borsh.lenVector(_symbol)
         + Borsh.lenVector(_uri)
         + 1
         + 1
         + 8
         + 1
         + 1;
  }
}
