package software.sava.anchor.programs.kamino.scope.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;

public record OracleTwaps(PublicKey _address,
                          Discriminator discriminator,
                          PublicKey oraclePrices,
                          PublicKey oracleMappings,
                          EmaTwap[] twaps) implements Borsh {

  public static final int BYTES = 344136;
  public static final int TWAPS_LEN = 512;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int ORACLE_PRICES_OFFSET = 8;
  public static final int ORACLE_MAPPINGS_OFFSET = 40;
  public static final int TWAPS_OFFSET = 72;

  public static Filter createOraclePricesFilter(final PublicKey oraclePrices) {
    return Filter.createMemCompFilter(ORACLE_PRICES_OFFSET, oraclePrices);
  }

  public static Filter createOracleMappingsFilter(final PublicKey oracleMappings) {
    return Filter.createMemCompFilter(ORACLE_MAPPINGS_OFFSET, oracleMappings);
  }

  public static OracleTwaps read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static OracleTwaps read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static OracleTwaps read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], OracleTwaps> FACTORY = OracleTwaps::read;

  public static OracleTwaps read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var oraclePrices = readPubKey(_data, i);
    i += 32;
    final var oracleMappings = readPubKey(_data, i);
    i += 32;
    final var twaps = new EmaTwap[512];
    Borsh.readArray(twaps, EmaTwap::read, _data, i);
    return new OracleTwaps(_address, discriminator, oraclePrices, oracleMappings, twaps);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    oraclePrices.write(_data, i);
    i += 32;
    oracleMappings.write(_data, i);
    i += 32;
    i += Borsh.writeArray(twaps, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
