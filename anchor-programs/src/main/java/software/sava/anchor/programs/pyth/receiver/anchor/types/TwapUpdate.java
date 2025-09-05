package software.sava.anchor.programs.pyth.receiver.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record TwapUpdate(PublicKey _address, Discriminator discriminator, PublicKey writeAuthority, TwapPrice twap) implements Borsh {

  public static final int BYTES = 112;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int WRITE_AUTHORITY_OFFSET = 8;
  public static final int TWAP_OFFSET = 40;

  public static Filter createWriteAuthorityFilter(final PublicKey writeAuthority) {
    return Filter.createMemCompFilter(WRITE_AUTHORITY_OFFSET, writeAuthority);
  }

  public static Filter createTwapFilter(final TwapPrice twap) {
    return Filter.createMemCompFilter(TWAP_OFFSET, twap.write());
  }

  public static TwapUpdate read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static TwapUpdate read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static TwapUpdate read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], TwapUpdate> FACTORY = TwapUpdate::read;

  public static TwapUpdate read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var writeAuthority = readPubKey(_data, i);
    i += 32;
    final var twap = TwapPrice.read(_data, i);
    return new TwapUpdate(_address, discriminator, writeAuthority, twap);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    writeAuthority.write(_data, i);
    i += 32;
    i += Borsh.write(twap, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
