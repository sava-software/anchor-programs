package software.sava.anchor.programs.metadao.amm.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record TwapOracle(long lastUpdatedSlot,
                         // A price is the number of quote units per base unit multiplied by 1e12.
                         // You cannot simply divide by 1e12 to get a price you can display in the UI
                         // because the base and quote decimals may be different. Instead, do:
                         // ui_price = (price * (10**(base_decimals - quote_decimals))) / 1e12
                         BigInteger lastPrice,
                         // If we did a raw TWAP over prices, someone could push the TWAP heavily with
                         // a few extremely large outliers. So we use observations, which can only move
                         // by `max_observation_change_per_update` per update.
                         BigInteger lastObservation,
                         // Running sum of slots_per_last_update * last_observation.
                         // 
                         // Assuming latest observations are as big as possible (u64::MAX * 1e12),
                         // we can store 18 million slots worth of observations, which turns out to
                         // be ~85 days worth of slots.
                         // 
                         // Assuming that latest observations are 100x smaller than they could theoretically
                         // be, we can store 8500 days (23 years) worth of them. Even this is a very
                         // very conservative assumption - META/USDC prices should be between 1e9 and
                         // 1e15, which would overflow after 1e15 years worth of slots.
                         // 
                         // So in the case of an overflow, the aggregator rolls back to 0. It's the
                         // client's responsibility to sanity check the assets or to handle an
                         // aggregator at T2 being smaller than an aggregator at T1.
                         BigInteger aggregator,
                         // The most that an observation can change per update.
                         BigInteger maxObservationChangePerUpdate,
                         // What the initial `latest_observation` is set to.
                         BigInteger initialObservation,
                         // Number of slots after amm.created_at_slot to start recording TWAP
                         long startDelaySlots) implements Borsh {

  public static final int BYTES = 96;

  public static TwapOracle read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lastUpdatedSlot = getInt64LE(_data, i);
    i += 8;
    final var lastPrice = getInt128LE(_data, i);
    i += 16;
    final var lastObservation = getInt128LE(_data, i);
    i += 16;
    final var aggregator = getInt128LE(_data, i);
    i += 16;
    final var maxObservationChangePerUpdate = getInt128LE(_data, i);
    i += 16;
    final var initialObservation = getInt128LE(_data, i);
    i += 16;
    final var startDelaySlots = getInt64LE(_data, i);
    return new TwapOracle(lastUpdatedSlot,
                          lastPrice,
                          lastObservation,
                          aggregator,
                          maxObservationChangePerUpdate,
                          initialObservation,
                          startDelaySlots);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, lastUpdatedSlot);
    i += 8;
    putInt128LE(_data, i, lastPrice);
    i += 16;
    putInt128LE(_data, i, lastObservation);
    i += 16;
    putInt128LE(_data, i, aggregator);
    i += 16;
    putInt128LE(_data, i, maxObservationChangePerUpdate);
    i += 16;
    putInt128LE(_data, i, initialObservation);
    i += 16;
    putInt64LE(_data, i, startDelaySlots);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
