package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record OverrideAmmCacheParams(OptionalLong quoteOwedFromLpPool,
                                     OptionalLong lastSettleSlot,
                                     BigInteger lastFeePoolTokenAmount,
                                     BigInteger lastNetPnlPoolTokenAmount,
                                     OptionalInt ammPositionScalar,
                                     OptionalLong ammInventoryLimit) implements Borsh {

  public static OverrideAmmCacheParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var quoteOwedFromLpPool = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (quoteOwedFromLpPool.isPresent()) {
      i += 8;
    }
    final var lastSettleSlot = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (lastSettleSlot.isPresent()) {
      i += 8;
    }
    final var lastFeePoolTokenAmount = _data[i++] == 0 ? null : getInt128LE(_data, i);
    if (lastFeePoolTokenAmount != null) {
      i += 16;
    }
    final var lastNetPnlPoolTokenAmount = _data[i++] == 0 ? null : getInt128LE(_data, i);
    if (lastNetPnlPoolTokenAmount != null) {
      i += 16;
    }
    final var ammPositionScalar = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    if (ammPositionScalar.isPresent()) {
      ++i;
    }
    final var ammInventoryLimit = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    return new OverrideAmmCacheParams(quoteOwedFromLpPool,
                                      lastSettleSlot,
                                      lastFeePoolTokenAmount,
                                      lastNetPnlPoolTokenAmount,
                                      ammPositionScalar,
                                      ammInventoryLimit);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(quoteOwedFromLpPool, _data, i);
    i += Borsh.writeOptional(lastSettleSlot, _data, i);
    i += Borsh.write128Optional(lastFeePoolTokenAmount, _data, i);
    i += Borsh.write128Optional(lastNetPnlPoolTokenAmount, _data, i);
    i += Borsh.writeOptionalbyte(ammPositionScalar, _data, i);
    i += Borsh.writeOptional(ammInventoryLimit, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (quoteOwedFromLpPool == null || quoteOwedFromLpPool.isEmpty() ? 1 : (1 + 8))
         + (lastSettleSlot == null || lastSettleSlot.isEmpty() ? 1 : (1 + 8))
         + (lastFeePoolTokenAmount == null ? 1 : (1 + 16))
         + (lastNetPnlPoolTokenAmount == null ? 1 : (1 + 16))
         + (ammPositionScalar == null || ammPositionScalar.isEmpty() ? 1 : (1 + 1))
         + (ammInventoryLimit == null || ammInventoryLimit.isEmpty() ? 1 : (1 + 8));
  }
}
