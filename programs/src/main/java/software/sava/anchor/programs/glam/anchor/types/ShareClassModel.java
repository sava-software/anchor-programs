package software.sava.anchor.programs.glam.anchor.types;

import java.lang.Boolean;
import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;

public record ShareClassModel(String symbol, byte[] _symbol,
                              String name, byte[] _name,
                              String uri, byte[] _uri,
                              PublicKey fundId,
                              PublicKey asset,
                              String imageUri, byte[] _imageUri,
                              Boolean isRawOpenfunds,
                              ShareClassOpenfundsModel rawOpenfunds,
                              PublicKey[] allowlist,
                              PublicKey[] blocklist) implements Borsh {

  public static ShareClassModel createRecord(final String symbol,
                                             final String name,
                                             final String uri,
                                             final PublicKey fundId,
                                             final PublicKey asset,
                                             final String imageUri,
                                             final Boolean isRawOpenfunds,
                                             final ShareClassOpenfundsModel rawOpenfunds,
                                             final PublicKey[] allowlist,
                                             final PublicKey[] blocklist) {
    return new ShareClassModel(symbol, Borsh.getBytes(symbol),
                               name, Borsh.getBytes(name),
                               uri, Borsh.getBytes(uri),
                               fundId,
                               asset,
                               imageUri, Borsh.getBytes(imageUri),
                               isRawOpenfunds,
                               rawOpenfunds,
                               allowlist,
                               blocklist);
  }

  public static ShareClassModel read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var symbol = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (symbol != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var name = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (name != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var uri = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (uri != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var fundId = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (fundId != null) {
      i += 32;
    }
    final var asset = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (asset != null) {
      i += 32;
    }
    final var imageUri = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (imageUri != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var isRawOpenfunds = _data[i++] == 0 ? null : _data[i] == 1;
    if (isRawOpenfunds != null) {
      ++i;
    }
    final var rawOpenfunds = _data[i++] == 0 ? null : ShareClassOpenfundsModel.read(_data, i);
    if (rawOpenfunds != null) {
      i += Borsh.len(rawOpenfunds);
    }
    final var allowlist = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(allowlist);
    final var blocklist = Borsh.readPublicKeyVector(_data, i);
    return new ShareClassModel(symbol, Borsh.getBytes(symbol),
                               name, Borsh.getBytes(name),
                               uri, Borsh.getBytes(uri),
                               fundId,
                               asset,
                               imageUri, Borsh.getBytes(imageUri),
                               isRawOpenfunds,
                               rawOpenfunds,
                               allowlist,
                               blocklist);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptionalVector(_symbol, _data, i);
    i += Borsh.writeOptionalVector(_name, _data, i);
    i += Borsh.writeOptionalVector(_uri, _data, i);
    i += Borsh.writeOptional(fundId, _data, i);
    i += Borsh.writeOptional(asset, _data, i);
    i += Borsh.writeOptionalVector(_imageUri, _data, i);
    i += Borsh.writeOptional(isRawOpenfunds, _data, i);
    i += Borsh.writeOptional(rawOpenfunds, _data, i);
    i += Borsh.writeVector(allowlist, _data, i);
    i += Borsh.writeVector(blocklist, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (_symbol == null || _symbol.length == 0 ? 1 : (1 + Borsh.lenVector(_symbol)))
         + (_name == null || _name.length == 0 ? 1 : (1 + Borsh.lenVector(_name)))
         + (_uri == null || _uri.length == 0 ? 1 : (1 + Borsh.lenVector(_uri)))
         + (fundId == null ? 1 : (1 + 32))
         + (asset == null ? 1 : (1 + 32))
         + (_imageUri == null || _imageUri.length == 0 ? 1 : (1 + Borsh.lenVector(_imageUri)))
         + (isRawOpenfunds == null ? 1 : (1 + 1))
         + (rawOpenfunds == null ? 1 : (1 + Borsh.len(rawOpenfunds)))
         + Borsh.lenVector(allowlist)
         + Borsh.lenVector(blocklist);
  }
}
