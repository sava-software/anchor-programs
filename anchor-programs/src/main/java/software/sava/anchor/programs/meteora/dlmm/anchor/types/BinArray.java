package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

// An account to contain a range of bin. For example: Bin 100 <-> 200.
// For example:
// BinArray index: 0 contains bin 0 <-> 599
// index: 2 contains bin 600 <-> 1199, ...
public record BinArray(PublicKey _address,
                       Discriminator discriminator,
                       long index,
                       // Version of binArray
                       int version,
                       byte[] padding,
                       PublicKey lbPair,
                       Bin[] bins) implements Borsh {

  public static final int BYTES = 10136;
  public static final int PADDING_LEN = 7;
  public static final int BINS_LEN = 70;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(92, 142, 92, 220, 5, 148, 70, 181);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int INDEX_OFFSET = 8;
  public static final int VERSION_OFFSET = 16;
  public static final int PADDING_OFFSET = 17;
  public static final int LB_PAIR_OFFSET = 24;
  public static final int BINS_OFFSET = 56;

  public static Filter createIndexFilter(final long index) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, index);
    return Filter.createMemCompFilter(INDEX_OFFSET, _data);
  }

  public static Filter createVersionFilter(final int version) {
    return Filter.createMemCompFilter(VERSION_OFFSET, new byte[]{(byte) version});
  }

  public static Filter createLbPairFilter(final PublicKey lbPair) {
    return Filter.createMemCompFilter(LB_PAIR_OFFSET, lbPair);
  }

  public static BinArray read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static BinArray read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static BinArray read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], BinArray> FACTORY = BinArray::read;

  public static BinArray read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var index = getInt64LE(_data, i);
    i += 8;
    final var version = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[7];
    i += Borsh.readArray(padding, _data, i);
    final var lbPair = readPubKey(_data, i);
    i += 32;
    final var bins = new Bin[70];
    Borsh.readArray(bins, Bin::read, _data, i);
    return new BinArray(_address,
                        discriminator,
                        index,
                        version,
                        padding,
                        lbPair,
                        bins);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, index);
    i += 8;
    _data[i] = (byte) version;
    ++i;
    i += Borsh.writeArray(padding, _data, i);
    lbPair.write(_data, i);
    i += 32;
    i += Borsh.writeArray(bins, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
