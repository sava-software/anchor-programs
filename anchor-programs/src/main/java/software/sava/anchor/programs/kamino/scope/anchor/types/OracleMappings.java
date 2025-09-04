package software.sava.anchor.programs.kamino.scope.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record OracleMappings(PublicKey _address,
                             Discriminator discriminator,
                             PublicKey[] priceInfoAccounts,
                             byte[] priceTypes,
                             short[] twapSource,
                             byte[] twapEnabled,
                             short[] refPrice,
                             byte[][] generic) implements Borsh {

  public static final int BYTES = 29704;
  public static final int PRICE_INFO_ACCOUNTS_LEN = 512;
  public static final int PRICE_TYPES_LEN = 512;
  public static final int TWAP_SOURCE_LEN = 512;
  public static final int TWAP_ENABLED_LEN = 512;
  public static final int REF_PRICE_LEN = 512;
  public static final int GENERIC_LEN = 512;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int PRICE_INFO_ACCOUNTS_OFFSET = 8;
  public static final int PRICE_TYPES_OFFSET = 16392;
  public static final int TWAP_SOURCE_OFFSET = 16904;
  public static final int TWAP_ENABLED_OFFSET = 17928;
  public static final int REF_PRICE_OFFSET = 18440;
  public static final int GENERIC_OFFSET = 19464;

  public static OracleMappings read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static OracleMappings read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static OracleMappings read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], OracleMappings> FACTORY = OracleMappings::read;

  public static OracleMappings read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var priceInfoAccounts = new PublicKey[512];
    i += Borsh.readArray(priceInfoAccounts, _data, i);
    final var priceTypes = new byte[512];
    i += Borsh.readArray(priceTypes, _data, i);
    final var twapSource = new short[512];
    i += Borsh.readArray(twapSource, _data, i);
    final var twapEnabled = new byte[512];
    i += Borsh.readArray(twapEnabled, _data, i);
    final var refPrice = new short[512];
    i += Borsh.readArray(refPrice, _data, i);
    final var generic = new byte[512][20];
    Borsh.readArray(generic, _data, i);
    return new OracleMappings(_address,
                              discriminator,
                              priceInfoAccounts,
                              priceTypes,
                              twapSource,
                              twapEnabled,
                              refPrice,
                              generic);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    i += Borsh.writeArray(priceInfoAccounts, _data, i);
    i += Borsh.writeArray(priceTypes, _data, i);
    i += Borsh.writeArray(twapSource, _data, i);
    i += Borsh.writeArray(twapEnabled, _data, i);
    i += Borsh.writeArray(refPrice, _data, i);
    i += Borsh.writeArray(generic, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
