package software.sava.anchor.programs.chainlink.ocr2.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record OracleObservationCount(PublicKey _address, Discriminator discriminator, int count) implements Borsh {

  public static final int BYTES = 12;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int COUNT_OFFSET = 8;

  public static Filter createCountFilter(final int count) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, count);
    return Filter.createMemCompFilter(COUNT_OFFSET, _data);
  }

  public static OracleObservationCount read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static OracleObservationCount read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static OracleObservationCount read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], OracleObservationCount> FACTORY = OracleObservationCount::read;

  public static OracleObservationCount read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var count = getInt32LE(_data, i);
    return new OracleObservationCount(_address, discriminator, count);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt32LE(_data, i, count);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
