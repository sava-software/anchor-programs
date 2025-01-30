package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;

public record TermsSignature(PublicKey _address, Discriminator discriminator, byte[] signature) implements Borsh {

  public static final int BYTES = 72;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int SIGNATURE_OFFSET = 8;

  public static TermsSignature read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static TermsSignature read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static TermsSignature read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], TermsSignature> FACTORY = TermsSignature::read;

  public static TermsSignature read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var signature = new byte[64];
    Borsh.readArray(signature, _data, i);
    return new TermsSignature(_address, discriminator, signature);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    i += Borsh.writeArray(signature, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
