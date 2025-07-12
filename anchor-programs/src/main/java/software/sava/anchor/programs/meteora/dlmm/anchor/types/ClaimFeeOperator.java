package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;

// Parameter that set by the protocol
public record ClaimFeeOperator(PublicKey _address,
                               Discriminator discriminator,
                               // operator
                               PublicKey operator,
                               // Reserve
                               byte[] padding) implements Borsh {

  public static final int BYTES = 168;
  public static final int PADDING_LEN = 128;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int OPERATOR_OFFSET = 8;
  public static final int PADDING_OFFSET = 40;

  public static Filter createOperatorFilter(final PublicKey operator) {
    return Filter.createMemCompFilter(OPERATOR_OFFSET, operator);
  }

  public static ClaimFeeOperator read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static ClaimFeeOperator read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static ClaimFeeOperator read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], ClaimFeeOperator> FACTORY = ClaimFeeOperator::read;

  public static ClaimFeeOperator read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var operator = readPubKey(_data, i);
    i += 32;
    final var padding = new byte[128];
    Borsh.readArray(padding, _data, i);
    return new ClaimFeeOperator(_address, discriminator, operator, padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    operator.write(_data, i);
    i += 32;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
