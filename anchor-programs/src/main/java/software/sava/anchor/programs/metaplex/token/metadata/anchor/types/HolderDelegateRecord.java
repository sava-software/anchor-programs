package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record HolderDelegateRecord(PublicKey _address,
                                   Key key,
                                   int bump,
                                   PublicKey mint,
                                   PublicKey delegate,
                                   PublicKey updateAuthority) implements Borsh {

  public static final int BYTES = 98;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int KEY_OFFSET = 0;
  public static final int BUMP_OFFSET = 1;
  public static final int MINT_OFFSET = 2;
  public static final int DELEGATE_OFFSET = 34;
  public static final int UPDATE_AUTHORITY_OFFSET = 66;

  public static Filter createKeyFilter(final Key key) {
    return Filter.createMemCompFilter(KEY_OFFSET, key.write());
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createMintFilter(final PublicKey mint) {
    return Filter.createMemCompFilter(MINT_OFFSET, mint);
  }

  public static Filter createDelegateFilter(final PublicKey delegate) {
    return Filter.createMemCompFilter(DELEGATE_OFFSET, delegate);
  }

  public static Filter createUpdateAuthorityFilter(final PublicKey updateAuthority) {
    return Filter.createMemCompFilter(UPDATE_AUTHORITY_OFFSET, updateAuthority);
  }

  public static HolderDelegateRecord read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static HolderDelegateRecord read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static HolderDelegateRecord read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], HolderDelegateRecord> FACTORY = HolderDelegateRecord::read;

  public static HolderDelegateRecord read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var key = Key.read(_data, i);
    i += Borsh.len(key);
    final var bump = _data[i] & 0xFF;
    ++i;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var delegate = readPubKey(_data, i);
    i += 32;
    final var updateAuthority = readPubKey(_data, i);
    return new HolderDelegateRecord(_address,
                                    key,
                                    bump,
                                    mint,
                                    delegate,
                                    updateAuthority);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(key, _data, i);
    _data[i] = (byte) bump;
    ++i;
    mint.write(_data, i);
    i += 32;
    delegate.write(_data, i);
    i += 32;
    updateAuthority.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
