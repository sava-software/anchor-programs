package software.sava.anchor.programs.kamino.lend.anchor.types;

import java.lang.String;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;

public record ShortUrl(PublicKey _address, Discriminator discriminator, PublicKey referrer, String shortUrl, byte[] _shortUrl) implements Borsh {

  public static final int REFERRER_OFFSET = 8;
  public static final int SHORT_URL_OFFSET = 40;

  public static Filter createReferrerFilter(final PublicKey referrer) {
    return Filter.createMemCompFilter(REFERRER_OFFSET, referrer);
  }

  public static ShortUrl createRecord(final PublicKey _address, final Discriminator discriminator, final PublicKey referrer, final String shortUrl) {
    return new ShortUrl(_address, discriminator, referrer, shortUrl, shortUrl.getBytes(UTF_8));
  }

  public static ShortUrl read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static ShortUrl read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], ShortUrl> FACTORY = ShortUrl::read;

  public static ShortUrl read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var referrer = readPubKey(_data, i);
    i += 32;
    final var shortUrl = Borsh.string(_data, i);
    return new ShortUrl(_address, discriminator, referrer, shortUrl, shortUrl.getBytes(UTF_8));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    referrer.write(_data, i);
    i += 32;
    i += Borsh.writeVector(_shortUrl, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32 + Borsh.lenVector(_shortUrl);
  }
}
