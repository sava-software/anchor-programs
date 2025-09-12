package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record UseAuthorityRecord(PublicKey _address,
                                 Key key,
                                 long allowedUses,
                                 int bump) implements Borsh {

  public static final int BYTES = 10;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int KEY_OFFSET = 0;
  public static final int ALLOWED_USES_OFFSET = 1;
  public static final int BUMP_OFFSET = 9;

  public static Filter createKeyFilter(final Key key) {
    return Filter.createMemCompFilter(KEY_OFFSET, key.write());
  }

  public static Filter createAllowedUsesFilter(final long allowedUses) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, allowedUses);
    return Filter.createMemCompFilter(ALLOWED_USES_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static UseAuthorityRecord read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static UseAuthorityRecord read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static UseAuthorityRecord read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], UseAuthorityRecord> FACTORY = UseAuthorityRecord::read;

  public static UseAuthorityRecord read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var key = Key.read(_data, i);
    i += Borsh.len(key);
    final var allowedUses = getInt64LE(_data, i);
    i += 8;
    final var bump = _data[i] & 0xFF;
    return new UseAuthorityRecord(_address, key, allowedUses, bump);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(key, _data, i);
    putInt64LE(_data, i, allowedUses);
    i += 8;
    _data[i] = (byte) bump;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
