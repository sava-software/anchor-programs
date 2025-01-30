package software.sava.anchor.programs.kamino.lend.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;

public record ReferrerState(PublicKey _address, Discriminator discriminator, PublicKey shortUrl, PublicKey owner) implements Borsh {

  public static final int BYTES = 72;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int SHORT_URL_OFFSET = 8;
  public static final int OWNER_OFFSET = 40;

  public static Filter createShortUrlFilter(final PublicKey shortUrl) {
    return Filter.createMemCompFilter(SHORT_URL_OFFSET, shortUrl);
  }

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static ReferrerState read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static ReferrerState read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static ReferrerState read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], ReferrerState> FACTORY = ReferrerState::read;

  public static ReferrerState read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var shortUrl = readPubKey(_data, i);
    i += 32;
    final var owner = readPubKey(_data, i);
    return new ReferrerState(_address, discriminator, shortUrl, owner);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    shortUrl.write(_data, i);
    i += 32;
    owner.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
