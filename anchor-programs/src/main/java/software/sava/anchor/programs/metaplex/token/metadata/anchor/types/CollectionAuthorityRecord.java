package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record CollectionAuthorityRecord(PublicKey _address,
                                        Key key,
                                        int bump,
                                        PublicKey updateAuthority) implements Borsh {

  public static final int KEY_OFFSET = 0;
  public static final int BUMP_OFFSET = 1;
  public static final int UPDATE_AUTHORITY_OFFSET = 2;

  public static Filter createKeyFilter(final Key key) {
    return Filter.createMemCompFilter(KEY_OFFSET, key.write());
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createUpdateAuthorityFilter(final PublicKey updateAuthority) {
    final byte[] _data = new byte[33];
    _data[0] = 1;
    updateAuthority.write(_data, 1);
    return Filter.createMemCompFilter(UPDATE_AUTHORITY_OFFSET, _data);
  }

  public static CollectionAuthorityRecord read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static CollectionAuthorityRecord read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static CollectionAuthorityRecord read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], CollectionAuthorityRecord> FACTORY = CollectionAuthorityRecord::read;

  public static CollectionAuthorityRecord read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var key = Key.read(_data, i);
    i += Borsh.len(key);
    final var bump = _data[i] & 0xFF;
    ++i;
    final var updateAuthority = _data[i++] == 0 ? null : readPubKey(_data, i);
    return new CollectionAuthorityRecord(_address, key, bump, updateAuthority);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(key, _data, i);
    _data[i] = (byte) bump;
    ++i;
    i += Borsh.writeOptional(updateAuthority, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(key) + 1 + (updateAuthority == null ? 1 : (1 + 32));
  }
}
