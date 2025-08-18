package software.sava.anchor.programs.chainlink.store.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LinkAvailableForPayment(PublicKey _address, Discriminator discriminator, long availableBalance) implements Borsh {

  public static final int BYTES = 16;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int AVAILABLE_BALANCE_OFFSET = 8;

  public static Filter createAvailableBalanceFilter(final long availableBalance) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, availableBalance);
    return Filter.createMemCompFilter(AVAILABLE_BALANCE_OFFSET, _data);
  }

  public static LinkAvailableForPayment read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static LinkAvailableForPayment read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static LinkAvailableForPayment read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], LinkAvailableForPayment> FACTORY = LinkAvailableForPayment::read;

  public static LinkAvailableForPayment read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var availableBalance = getInt64LE(_data, i);
    return new LinkAvailableForPayment(_address, discriminator, availableBalance);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, availableBalance);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
