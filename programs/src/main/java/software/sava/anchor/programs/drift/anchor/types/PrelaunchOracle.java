package software.sava.anchor.programs.drift.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PrelaunchOracle(PublicKey _address,
                              Discriminator discriminator,
                              long price,
                              long maxPrice,
                              long confidence,
                              long lastUpdateSlot,
                              long ammLastUpdateSlot,
                              int perpMarketIndex,
                              byte[] padding) implements Borsh {

  public static final int BYTES = 120;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int PRICE_OFFSET = 8;
  public static final int MAX_PRICE_OFFSET = 16;
  public static final int CONFIDENCE_OFFSET = 24;
  public static final int LAST_UPDATE_SLOT_OFFSET = 32;
  public static final int AMM_LAST_UPDATE_SLOT_OFFSET = 40;
  public static final int PERP_MARKET_INDEX_OFFSET = 48;
  public static final int PADDING_OFFSET = 50;

  public static Filter createPriceFilter(final long price) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, price);
    return Filter.createMemCompFilter(PRICE_OFFSET, _data);
  }

  public static Filter createMaxPriceFilter(final long maxPrice) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxPrice);
    return Filter.createMemCompFilter(MAX_PRICE_OFFSET, _data);
  }

  public static Filter createConfidenceFilter(final long confidence) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, confidence);
    return Filter.createMemCompFilter(CONFIDENCE_OFFSET, _data);
  }

  public static Filter createLastUpdateSlotFilter(final long lastUpdateSlot) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastUpdateSlot);
    return Filter.createMemCompFilter(LAST_UPDATE_SLOT_OFFSET, _data);
  }

  public static Filter createAmmLastUpdateSlotFilter(final long ammLastUpdateSlot) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, ammLastUpdateSlot);
    return Filter.createMemCompFilter(AMM_LAST_UPDATE_SLOT_OFFSET, _data);
  }

  public static Filter createPerpMarketIndexFilter(final int perpMarketIndex) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, perpMarketIndex);
    return Filter.createMemCompFilter(PERP_MARKET_INDEX_OFFSET, _data);
  }

  public static PrelaunchOracle read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static PrelaunchOracle read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], PrelaunchOracle> FACTORY = PrelaunchOracle::read;

  public static PrelaunchOracle read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var price = getInt64LE(_data, i);
    i += 8;
    final var maxPrice = getInt64LE(_data, i);
    i += 8;
    final var confidence = getInt64LE(_data, i);
    i += 8;
    final var lastUpdateSlot = getInt64LE(_data, i);
    i += 8;
    final var ammLastUpdateSlot = getInt64LE(_data, i);
    i += 8;
    final var perpMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var padding = new byte[70];
    Borsh.readArray(padding, _data, i);
    return new PrelaunchOracle(_address,
                               discriminator,
                               price,
                               maxPrice,
                               confidence,
                               lastUpdateSlot,
                               ammLastUpdateSlot,
                               perpMarketIndex,
                               padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, price);
    i += 8;
    putInt64LE(_data, i, maxPrice);
    i += 8;
    putInt64LE(_data, i, confidence);
    i += 8;
    putInt64LE(_data, i, lastUpdateSlot);
    i += 8;
    putInt64LE(_data, i, ammLastUpdateSlot);
    i += 8;
    putInt16LE(_data, i, perpMarketIndex);
    i += 2;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
