package software.sava.anchor.programs.jupiter.limit.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record Fee(PublicKey _address,
                  Discriminator discriminator,
                  long makerFee,
                  long makerStableFee,
                  long takerFee,
                  long takerStableFee) implements Borsh {

  public static final int BYTES = 40;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int MAKER_FEE_OFFSET = 8;
  public static final int MAKER_STABLE_FEE_OFFSET = 16;
  public static final int TAKER_FEE_OFFSET = 24;
  public static final int TAKER_STABLE_FEE_OFFSET = 32;

  public static Filter createMakerFeeFilter(final long makerFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, makerFee);
    return Filter.createMemCompFilter(MAKER_FEE_OFFSET, _data);
  }

  public static Filter createMakerStableFeeFilter(final long makerStableFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, makerStableFee);
    return Filter.createMemCompFilter(MAKER_STABLE_FEE_OFFSET, _data);
  }

  public static Filter createTakerFeeFilter(final long takerFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, takerFee);
    return Filter.createMemCompFilter(TAKER_FEE_OFFSET, _data);
  }

  public static Filter createTakerStableFeeFilter(final long takerStableFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, takerStableFee);
    return Filter.createMemCompFilter(TAKER_STABLE_FEE_OFFSET, _data);
  }

  public static Fee read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Fee read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Fee read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Fee> FACTORY = Fee::read;

  public static Fee read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var makerFee = getInt64LE(_data, i);
    i += 8;
    final var makerStableFee = getInt64LE(_data, i);
    i += 8;
    final var takerFee = getInt64LE(_data, i);
    i += 8;
    final var takerStableFee = getInt64LE(_data, i);
    return new Fee(_address,
                   discriminator,
                   makerFee,
                   makerStableFee,
                   takerFee,
                   takerStableFee);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, makerFee);
    i += 8;
    putInt64LE(_data, i, makerStableFee);
    i += 8;
    putInt64LE(_data, i, takerFee);
    i += 8;
    putInt64LE(_data, i, takerStableFee);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
