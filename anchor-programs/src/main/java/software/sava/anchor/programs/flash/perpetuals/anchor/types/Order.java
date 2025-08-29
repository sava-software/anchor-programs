package software.sava.anchor.programs.flash.perpetuals.anchor.types;

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

public record Order(PublicKey _address,
                    Discriminator discriminator,
                    PublicKey owner,
                    PublicKey market,
                    LimitOrder[] limitOrders,
                    TriggerOrder[] takeProfitOrders,
                    TriggerOrder[] stopLossOrders,
                    boolean isInitialised,
                    int openOrders,
                    int openSl,
                    int openTp,
                    int inactiveSl,
                    int inactiveTp,
                    int activeOrders,
                    int bump,
                    long referenceTimestamp,
                    long executionCount,
                    long[] padding) implements Borsh {

  public static final int BYTES = 624;
  public static final int LIMIT_ORDERS_LEN = 5;
  public static final int TAKE_PROFIT_ORDERS_LEN = 5;
  public static final int STOP_LOSS_ORDERS_LEN = 5;
  public static final int PADDING_LEN = 6;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int OWNER_OFFSET = 8;
  public static final int MARKET_OFFSET = 40;
  public static final int LIMIT_ORDERS_OFFSET = 72;
  public static final int TAKE_PROFIT_ORDERS_OFFSET = 342;
  public static final int STOP_LOSS_ORDERS_OFFSET = 447;
  public static final int IS_INITIALISED_OFFSET = 552;
  public static final int OPEN_ORDERS_OFFSET = 553;
  public static final int OPEN_SL_OFFSET = 554;
  public static final int OPEN_TP_OFFSET = 555;
  public static final int INACTIVE_SL_OFFSET = 556;
  public static final int INACTIVE_TP_OFFSET = 557;
  public static final int ACTIVE_ORDERS_OFFSET = 558;
  public static final int BUMP_OFFSET = 559;
  public static final int REFERENCE_TIMESTAMP_OFFSET = 560;
  public static final int EXECUTION_COUNT_OFFSET = 568;
  public static final int PADDING_OFFSET = 576;

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static Filter createMarketFilter(final PublicKey market) {
    return Filter.createMemCompFilter(MARKET_OFFSET, market);
  }

  public static Filter createIsInitialisedFilter(final boolean isInitialised) {
    return Filter.createMemCompFilter(IS_INITIALISED_OFFSET, new byte[]{(byte) (isInitialised ? 1 : 0)});
  }

  public static Filter createOpenOrdersFilter(final int openOrders) {
    return Filter.createMemCompFilter(OPEN_ORDERS_OFFSET, new byte[]{(byte) openOrders});
  }

  public static Filter createOpenSlFilter(final int openSl) {
    return Filter.createMemCompFilter(OPEN_SL_OFFSET, new byte[]{(byte) openSl});
  }

  public static Filter createOpenTpFilter(final int openTp) {
    return Filter.createMemCompFilter(OPEN_TP_OFFSET, new byte[]{(byte) openTp});
  }

  public static Filter createInactiveSlFilter(final int inactiveSl) {
    return Filter.createMemCompFilter(INACTIVE_SL_OFFSET, new byte[]{(byte) inactiveSl});
  }

  public static Filter createInactiveTpFilter(final int inactiveTp) {
    return Filter.createMemCompFilter(INACTIVE_TP_OFFSET, new byte[]{(byte) inactiveTp});
  }

  public static Filter createActiveOrdersFilter(final int activeOrders) {
    return Filter.createMemCompFilter(ACTIVE_ORDERS_OFFSET, new byte[]{(byte) activeOrders});
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createReferenceTimestampFilter(final long referenceTimestamp) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, referenceTimestamp);
    return Filter.createMemCompFilter(REFERENCE_TIMESTAMP_OFFSET, _data);
  }

  public static Filter createExecutionCountFilter(final long executionCount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, executionCount);
    return Filter.createMemCompFilter(EXECUTION_COUNT_OFFSET, _data);
  }

  public static Order read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Order read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Order read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Order> FACTORY = Order::read;

  public static Order read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var owner = readPubKey(_data, i);
    i += 32;
    final var market = readPubKey(_data, i);
    i += 32;
    final var limitOrders = new LimitOrder[5];
    i += Borsh.readArray(limitOrders, LimitOrder::read, _data, i);
    final var takeProfitOrders = new TriggerOrder[5];
    i += Borsh.readArray(takeProfitOrders, TriggerOrder::read, _data, i);
    final var stopLossOrders = new TriggerOrder[5];
    i += Borsh.readArray(stopLossOrders, TriggerOrder::read, _data, i);
    final var isInitialised = _data[i] == 1;
    ++i;
    final var openOrders = _data[i] & 0xFF;
    ++i;
    final var openSl = _data[i] & 0xFF;
    ++i;
    final var openTp = _data[i] & 0xFF;
    ++i;
    final var inactiveSl = _data[i] & 0xFF;
    ++i;
    final var inactiveTp = _data[i] & 0xFF;
    ++i;
    final var activeOrders = _data[i] & 0xFF;
    ++i;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var referenceTimestamp = getInt64LE(_data, i);
    i += 8;
    final var executionCount = getInt64LE(_data, i);
    i += 8;
    final var padding = new long[6];
    Borsh.readArray(padding, _data, i);
    return new Order(_address,
                     discriminator,
                     owner,
                     market,
                     limitOrders,
                     takeProfitOrders,
                     stopLossOrders,
                     isInitialised,
                     openOrders,
                     openSl,
                     openTp,
                     inactiveSl,
                     inactiveTp,
                     activeOrders,
                     bump,
                     referenceTimestamp,
                     executionCount,
                     padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    owner.write(_data, i);
    i += 32;
    market.write(_data, i);
    i += 32;
    i += Borsh.writeArray(limitOrders, _data, i);
    i += Borsh.writeArray(takeProfitOrders, _data, i);
    i += Borsh.writeArray(stopLossOrders, _data, i);
    _data[i] = (byte) (isInitialised ? 1 : 0);
    ++i;
    _data[i] = (byte) openOrders;
    ++i;
    _data[i] = (byte) openSl;
    ++i;
    _data[i] = (byte) openTp;
    ++i;
    _data[i] = (byte) inactiveSl;
    ++i;
    _data[i] = (byte) inactiveTp;
    ++i;
    _data[i] = (byte) activeOrders;
    ++i;
    _data[i] = (byte) bump;
    ++i;
    putInt64LE(_data, i, referenceTimestamp);
    i += 8;
    putInt64LE(_data, i, executionCount);
    i += 8;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
