package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record AssetData(String name, byte[] _name,
                        String symbol, byte[] _symbol,
                        String uri, byte[] _uri,
                        int sellerFeeBasisPoints,
                        Creator[] creators,
                        boolean primarySaleHappened,
                        boolean isMutable,
                        TokenStandard tokenStandard,
                        Collection collection,
                        Uses uses,
                        CollectionDetails collectionDetails,
                        PublicKey ruleSet) implements Borsh {

  public static AssetData createRecord(final String name,
                                       final String symbol,
                                       final String uri,
                                       final int sellerFeeBasisPoints,
                                       final Creator[] creators,
                                       final boolean primarySaleHappened,
                                       final boolean isMutable,
                                       final TokenStandard tokenStandard,
                                       final Collection collection,
                                       final Uses uses,
                                       final CollectionDetails collectionDetails,
                                       final PublicKey ruleSet) {
    return new AssetData(name, name.getBytes(UTF_8),
                         symbol, symbol.getBytes(UTF_8),
                         uri, uri.getBytes(UTF_8),
                         sellerFeeBasisPoints,
                         creators,
                         primarySaleHappened,
                         isMutable,
                         tokenStandard,
                         collection,
                         uses,
                         collectionDetails,
                         ruleSet);
  }

  public static AssetData read(final byte[] _data, final int offset) {
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
    final var primarySaleHappened = _data[i] == 1;
    ++i;
    final var isMutable = _data[i] == 1;
    ++i;
    final var tokenStandard = TokenStandard.read(_data, i);
    i += Borsh.len(tokenStandard);
    final var collection = _data[i++] == 0 ? null : Collection.read(_data, i);
    if (collection != null) {
      i += Borsh.len(collection);
    }
    final var uses = _data[i++] == 0 ? null : Uses.read(_data, i);
    if (uses != null) {
      i += Borsh.len(uses);
    }
    final var collectionDetails = _data[i++] == 0 ? null : CollectionDetails.read(_data, i);
    if (collectionDetails != null) {
      i += Borsh.len(collectionDetails);
    }
    final var ruleSet = _data[i++] == 0 ? null : readPubKey(_data, i);
    return new AssetData(name, name.getBytes(UTF_8),
                         symbol, symbol.getBytes(UTF_8),
                         uri, uri.getBytes(UTF_8),
                         sellerFeeBasisPoints,
                         creators,
                         primarySaleHappened,
                         isMutable,
                         tokenStandard,
                         collection,
                         uses,
                         collectionDetails,
                         ruleSet);
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
    _data[i] = (byte) (primarySaleHappened ? 1 : 0);
    ++i;
    _data[i] = (byte) (isMutable ? 1 : 0);
    ++i;
    i += Borsh.write(tokenStandard, _data, i);
    i += Borsh.writeOptional(collection, _data, i);
    i += Borsh.writeOptional(uses, _data, i);
    i += Borsh.writeOptional(collectionDetails, _data, i);
    i += Borsh.writeOptional(ruleSet, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_name)
         + Borsh.lenVector(_symbol)
         + Borsh.lenVector(_uri)
         + 2
         + (creators == null || creators.length == 0 ? 1 : (1 + Borsh.lenVector(creators)))
         + 1
         + 1
         + Borsh.len(tokenStandard)
         + (collection == null ? 1 : (1 + Borsh.len(collection)))
         + (uses == null ? 1 : (1 + Borsh.len(uses)))
         + (collectionDetails == null ? 1 : (1 + Borsh.len(collectionDetails)))
         + (ruleSet == null ? 1 : (1 + 32));
  }
}
