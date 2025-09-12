package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import java.util.OptionalLong;
import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record MasterEditionV2(PublicKey _address,
                              Key key,
                              long supply,
                              OptionalLong maxSupply) implements Borsh {

  public static final int KEY_OFFSET = 0;
  public static final int SUPPLY_OFFSET = 1;
  public static final int MAX_SUPPLY_OFFSET = 9;

  public static Filter createKeyFilter(final Key key) {
    return Filter.createMemCompFilter(KEY_OFFSET, key.write());
  }

  public static Filter createSupplyFilter(final long supply) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, supply);
    return Filter.createMemCompFilter(SUPPLY_OFFSET, _data);
  }

  public static Filter createMaxSupplyFilter(final long maxSupply) {
    final byte[] _data = new byte[9];
    _data[0] = 1;
    putInt64LE(_data, 1, maxSupply);
    return Filter.createMemCompFilter(MAX_SUPPLY_OFFSET, _data);
  }

  public static MasterEditionV2 read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static MasterEditionV2 read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static MasterEditionV2 read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], MasterEditionV2> FACTORY = MasterEditionV2::read;

  public static MasterEditionV2 read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var key = Key.read(_data, i);
    i += Borsh.len(key);
    final var supply = getInt64LE(_data, i);
    i += 8;
    final var maxSupply = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    return new MasterEditionV2(_address, key, supply, maxSupply);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(key, _data, i);
    putInt64LE(_data, i, supply);
    i += 8;
    i += Borsh.writeOptional(maxSupply, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(key) + 8 + (maxSupply == null || maxSupply.isEmpty() ? 1 : (1 + 8));
  }
}
