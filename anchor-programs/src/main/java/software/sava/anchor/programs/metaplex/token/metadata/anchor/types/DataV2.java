package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record DataV2(String name, byte[] _name,
                     String symbol, byte[] _symbol,
                     String uri, byte[] _uri,
                     int sellerFeeBasisPoints,
                     Creator[] creators,
                     Collection collection,
                     Uses uses) implements Borsh {

  public static DataV2 createRecord(final String name,
                                    final String symbol,
                                    final String uri,
                                    final int sellerFeeBasisPoints,
                                    final Creator[] creators,
                                    final Collection collection,
                                    final Uses uses) {
    return new DataV2(name, name.getBytes(UTF_8),
                      symbol, symbol.getBytes(UTF_8),
                      uri, uri.getBytes(UTF_8),
                      sellerFeeBasisPoints,
                      creators,
                      collection,
                      uses);
  }

  public static DataV2 read(final byte[] _data, final int offset) {
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
    if (creators != null) {
      i += Borsh.lenVector(creators);
    }
    final var collection = _data[i++] == 0 ? null : Collection.read(_data, i);
    if (collection != null) {
      i += Borsh.len(collection);
    }
    final var uses = _data[i++] == 0 ? null : Uses.read(_data, i);
    return new DataV2(name, name.getBytes(UTF_8),
                      symbol, symbol.getBytes(UTF_8),
                      uri, uri.getBytes(UTF_8),
                      sellerFeeBasisPoints,
                      creators,
                      collection,
                      uses);
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
    i += Borsh.writeOptional(collection, _data, i);
    i += Borsh.writeOptional(uses, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_name)
         + Borsh.lenVector(_symbol)
         + Borsh.lenVector(_uri)
         + 2
         + (creators == null || creators.length == 0 ? 1 : (1 + Borsh.lenVector(creators)))
         + (collection == null ? 1 : (1 + Borsh.len(collection)))
         + (uses == null ? 1 : (1 + Borsh.len(uses)));
  }
}
