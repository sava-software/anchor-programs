package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record Data(String name, byte[] _name,
                   String symbol, byte[] _symbol,
                   String uri, byte[] _uri,
                   int sellerFeeBasisPoints,
                   Creator[] creators) implements Borsh {

  public static Data createRecord(final String name,
                                  final String symbol,
                                  final String uri,
                                  final int sellerFeeBasisPoints,
                                  final Creator[] creators) {
    return new Data(name, name.getBytes(UTF_8),
                    symbol, symbol.getBytes(UTF_8),
                    uri, uri.getBytes(UTF_8),
                    sellerFeeBasisPoints,
                    creators);
  }

  public static Data read(final byte[] _data, final int offset) {
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
    final var sellerFeeBasisPoints = getInt16LE(_data, i);
    i += 2;
    final var creators = _data[i++] == 0 ? null : Borsh.readVector(Creator.class, Creator::read, _data, i);
    return new Data(name, name.getBytes(UTF_8),
                    symbol, symbol.getBytes(UTF_8),
                    uri, uri.getBytes(UTF_8),
                    sellerFeeBasisPoints,
                    creators);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_name, _data, i);
    i += Borsh.writeVector(_symbol, _data, i);
    i += Borsh.writeVector(_uri, _data, i);
    putInt16LE(_data, i, sellerFeeBasisPoints);
    i += 2;
    if (creators == null || creators.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(creators, _data, i);
    }
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_name)
         + Borsh.lenVector(_symbol)
         + Borsh.lenVector(_uri)
         + 2
         + (creators == null || creators.length == 0 ? 1 : (1 + Borsh.lenVector(creators)));
  }
}
