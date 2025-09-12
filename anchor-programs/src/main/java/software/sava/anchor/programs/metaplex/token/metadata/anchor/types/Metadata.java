package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import java.util.OptionalInt;
import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record Metadata(PublicKey _address,
                       Key key,
                       PublicKey updateAuthority,
                       PublicKey mint,
                       Data data,
                       boolean primarySaleHappened,
                       boolean isMutable,
                       OptionalInt editionNonce,
                       TokenStandard tokenStandard,
                       Collection collection,
                       Uses uses,
                       CollectionDetails collectionDetails,
                       ProgrammableConfig programmableConfig) implements Borsh {

  public static final int KEY_OFFSET = 0;
  public static final int UPDATE_AUTHORITY_OFFSET = 1;
  public static final int MINT_OFFSET = 33;
  public static final int DATA_OFFSET = 65;

  public static Filter createKeyFilter(final Key key) {
    return Filter.createMemCompFilter(KEY_OFFSET, key.write());
  }

  public static Filter createUpdateAuthorityFilter(final PublicKey updateAuthority) {
    return Filter.createMemCompFilter(UPDATE_AUTHORITY_OFFSET, updateAuthority);
  }

  public static Filter createMintFilter(final PublicKey mint) {
    return Filter.createMemCompFilter(MINT_OFFSET, mint);
  }

  public static Metadata read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Metadata read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Metadata read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Metadata> FACTORY = Metadata::read;

  public static Metadata read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var key = Key.read(_data, i);
    i += Borsh.len(key);
    final var updateAuthority = readPubKey(_data, i);
    i += 32;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var data = Data.read(_data, i);
    i += Borsh.len(data);
    final var primarySaleHappened = _data[i] == 1;
    ++i;
    final var isMutable = _data[i] == 1;
    ++i;
    final var editionNonce = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    if (editionNonce.isPresent()) {
      ++i;
    }
    final var tokenStandard = _data[i++] == 0 ? null : TokenStandard.read(_data, i);
    if (tokenStandard != null) {
      i += Borsh.len(tokenStandard);
    }
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
    final var programmableConfig = _data[i++] == 0 ? null : ProgrammableConfig.read(_data, i);
    return new Metadata(_address,
                        key,
                        updateAuthority,
                        mint,
                        data,
                        primarySaleHappened,
                        isMutable,
                        editionNonce,
                        tokenStandard,
                        collection,
                        uses,
                        collectionDetails,
                        programmableConfig);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(key, _data, i);
    updateAuthority.write(_data, i);
    i += 32;
    mint.write(_data, i);
    i += 32;
    i += Borsh.write(data, _data, i);
    _data[i] = (byte) (primarySaleHappened ? 1 : 0);
    ++i;
    _data[i] = (byte) (isMutable ? 1 : 0);
    ++i;
    i += Borsh.writeOptionalbyte(editionNonce, _data, i);
    i += Borsh.writeOptional(tokenStandard, _data, i);
    i += Borsh.writeOptional(collection, _data, i);
    i += Borsh.writeOptional(uses, _data, i);
    i += Borsh.writeOptional(collectionDetails, _data, i);
    i += Borsh.writeOptional(programmableConfig, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(key)
         + 32
         + 32
         + Borsh.len(data)
         + 1
         + 1
         + (editionNonce == null || editionNonce.isEmpty() ? 1 : (1 + 1))
         + (tokenStandard == null ? 1 : (1 + Borsh.len(tokenStandard)))
         + (collection == null ? 1 : (1 + Borsh.len(collection)))
         + (uses == null ? 1 : (1 + Borsh.len(uses)))
         + (collectionDetails == null ? 1 : (1 + Borsh.len(collectionDetails)))
         + (programmableConfig == null ? 1 : (1 + Borsh.len(programmableConfig)));
  }
}
