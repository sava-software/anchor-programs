package software.sava.anchor.programs.drift.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record AmmCache(PublicKey _address,
                       Discriminator discriminator,
                       int bump,
                       byte[] padding,
                       CacheInfo[] cache) implements Borsh {

  public static final int PADDING_LEN = 3;
  public static final int BUMP_OFFSET = 8;
  public static final int PADDING_OFFSET = 9;
  public static final int CACHE_OFFSET = 12;

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static AmmCache read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static AmmCache read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static AmmCache read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], AmmCache> FACTORY = AmmCache::read;

  public static AmmCache read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var bump = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[3];
    i += Borsh.readArray(padding, _data, i);
    final var cache = Borsh.readVector(CacheInfo.class, CacheInfo::read, _data, i);
    return new AmmCache(_address, discriminator, bump, padding, cache);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    _data[i] = (byte) bump;
    ++i;
    i += Borsh.writeArrayChecked(padding, 3, _data, i);
    i += Borsh.writeVector(cache, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 1 + Borsh.lenArray(padding) + Borsh.lenVector(cache);
  }
}
