package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;

public record BinArrayBitmapExtension(PublicKey _address,
                                      Discriminator discriminator,
                                      PublicKey lbPair,
                                      // Packed initialized bin array state for start_bin_index is positive
                                      long[][] positiveBinArrayBitmap,
                                      // Packed initialized bin array state for start_bin_index is negative
                                      long[][] negativeBinArrayBitmap) implements Borsh {

  public static final int BYTES = 1576;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int LB_PAIR_OFFSET = 8;
  public static final int POSITIVE_BIN_ARRAY_BITMAP_OFFSET = 40;
  public static final int NEGATIVE_BIN_ARRAY_BITMAP_OFFSET = 808;

  public static Filter createLbPairFilter(final PublicKey lbPair) {
    return Filter.createMemCompFilter(LB_PAIR_OFFSET, lbPair);
  }

  public static BinArrayBitmapExtension read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static BinArrayBitmapExtension read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static BinArrayBitmapExtension read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], BinArrayBitmapExtension> FACTORY = BinArrayBitmapExtension::read;

  public static BinArrayBitmapExtension read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var lbPair = readPubKey(_data, i);
    i += 32;
    final var positiveBinArrayBitmap = new long[12][8];
    i += Borsh.readArray(positiveBinArrayBitmap, _data, i);
    final var negativeBinArrayBitmap = new long[12][8];
    Borsh.readArray(negativeBinArrayBitmap, _data, i);
    return new BinArrayBitmapExtension(_address, discriminator, lbPair, positiveBinArrayBitmap, negativeBinArrayBitmap);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    lbPair.write(_data, i);
    i += 32;
    i += Borsh.writeArray(positiveBinArrayBitmap, _data, i);
    i += Borsh.writeArray(negativeBinArrayBitmap, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
