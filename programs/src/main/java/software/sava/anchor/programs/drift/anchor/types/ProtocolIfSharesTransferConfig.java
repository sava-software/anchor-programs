package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ProtocolIfSharesTransferConfig(PublicKey _address,
                                             Discriminator discriminator,
                                             PublicKey[] whitelistedSigners,
                                             BigInteger maxTransferPerEpoch,
                                             BigInteger currentEpochTransfer,
                                             long nextEpochTs,
                                             BigInteger[] padding) implements Borsh {

  public static final int BYTES = 304;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int WHITELISTED_SIGNERS_OFFSET = 8;
  public static final int MAX_TRANSFER_PER_EPOCH_OFFSET = 136;
  public static final int CURRENT_EPOCH_TRANSFER_OFFSET = 152;
  public static final int NEXT_EPOCH_TS_OFFSET = 168;
  public static final int PADDING_OFFSET = 176;

  public static Filter createMaxTransferPerEpochFilter(final BigInteger maxTransferPerEpoch) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, maxTransferPerEpoch);
    return Filter.createMemCompFilter(MAX_TRANSFER_PER_EPOCH_OFFSET, _data);
  }

  public static Filter createCurrentEpochTransferFilter(final BigInteger currentEpochTransfer) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, currentEpochTransfer);
    return Filter.createMemCompFilter(CURRENT_EPOCH_TRANSFER_OFFSET, _data);
  }

  public static Filter createNextEpochTsFilter(final long nextEpochTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, nextEpochTs);
    return Filter.createMemCompFilter(NEXT_EPOCH_TS_OFFSET, _data);
  }

  public static ProtocolIfSharesTransferConfig read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static ProtocolIfSharesTransferConfig read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static ProtocolIfSharesTransferConfig read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], ProtocolIfSharesTransferConfig> FACTORY = ProtocolIfSharesTransferConfig::read;

  public static ProtocolIfSharesTransferConfig read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var whitelistedSigners = new PublicKey[4];
    i += Borsh.readArray(whitelistedSigners, _data, i);
    final var maxTransferPerEpoch = getInt128LE(_data, i);
    i += 16;
    final var currentEpochTransfer = getInt128LE(_data, i);
    i += 16;
    final var nextEpochTs = getInt64LE(_data, i);
    i += 8;
    final var padding = new BigInteger[8];
    Borsh.readArray(padding, _data, i);
    return new ProtocolIfSharesTransferConfig(_address,
                                              discriminator,
                                              whitelistedSigners,
                                              maxTransferPerEpoch,
                                              currentEpochTransfer,
                                              nextEpochTs,
                                              padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    i += Borsh.writeArray(whitelistedSigners, _data, i);
    putInt128LE(_data, i, maxTransferPerEpoch);
    i += 16;
    putInt128LE(_data, i, currentEpochTransfer);
    i += 16;
    putInt64LE(_data, i, nextEpochTs);
    i += 8;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
