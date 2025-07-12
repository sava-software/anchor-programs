package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record CrankFeeWhitelist(PublicKey _address, Discriminator discriminator, PublicKey owner, BigInteger[] padding) implements Borsh {

  public static final int BYTES = 120;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(39, 105, 184, 30, 248, 231, 176, 133);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int OWNER_OFFSET = 8;
  public static final int PADDING_OFFSET = 40;

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static CrankFeeWhitelist read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static CrankFeeWhitelist read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static CrankFeeWhitelist read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], CrankFeeWhitelist> FACTORY = CrankFeeWhitelist::read;

  public static CrankFeeWhitelist read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var owner = readPubKey(_data, i);
    i += 32;
    final var padding = new BigInteger[5];
    Borsh.read128Array(padding, _data, i);
    return new CrankFeeWhitelist(_address, discriminator, owner, padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    owner.write(_data, i);
    i += 32;
    i += Borsh.write128Array(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
