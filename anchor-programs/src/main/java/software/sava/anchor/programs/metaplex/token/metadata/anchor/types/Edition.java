package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Edition(PublicKey _address,
                      Key key,
                      PublicKey parent,
                      long edition) implements Borsh {

  public static final int BYTES = 41;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int KEY_OFFSET = 0;
  public static final int PARENT_OFFSET = 1;
  public static final int EDITION_OFFSET = 33;

  public static Filter createKeyFilter(final Key key) {
    return Filter.createMemCompFilter(KEY_OFFSET, key.write());
  }

  public static Filter createParentFilter(final PublicKey parent) {
    return Filter.createMemCompFilter(PARENT_OFFSET, parent);
  }

  public static Filter createEditionFilter(final long edition) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, edition);
    return Filter.createMemCompFilter(EDITION_OFFSET, _data);
  }

  public static Edition read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Edition read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Edition read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Edition> FACTORY = Edition::read;

  public static Edition read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var key = Key.read(_data, i);
    i += Borsh.len(key);
    final var parent = readPubKey(_data, i);
    i += 32;
    final var edition = getInt64LE(_data, i);
    return new Edition(_address, key, parent, edition);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(key, _data, i);
    parent.write(_data, i);
    i += 32;
    putInt64LE(_data, i, edition);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
