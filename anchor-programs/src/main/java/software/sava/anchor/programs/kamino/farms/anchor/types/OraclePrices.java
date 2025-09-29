package software.sava.anchor.programs.kamino.farms.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record OraclePrices(PublicKey _address, Discriminator discriminator, PublicKey oracleMappings, DatedPrice[] prices) implements Borsh {

  public static final int BYTES = 28712;
  public static final int PRICES_LEN = 512;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int ORACLE_MAPPINGS_OFFSET = 8;
  public static final int PRICES_OFFSET = 40;

  public static Filter createOracleMappingsFilter(final PublicKey oracleMappings) {
    return Filter.createMemCompFilter(ORACLE_MAPPINGS_OFFSET, oracleMappings);
  }

  public static OraclePrices read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static OraclePrices read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static OraclePrices read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], OraclePrices> FACTORY = OraclePrices::read;

  public static OraclePrices read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var oracleMappings = readPubKey(_data, i);
    i += 32;
    final var prices = new DatedPrice[512];
    Borsh.readArray(prices, DatedPrice::read, _data, i);
    return new OraclePrices(_address, discriminator, oracleMappings, prices);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    oracleMappings.write(_data, i);
    i += 32;
    i += Borsh.writeArrayChecked(prices, 512, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
