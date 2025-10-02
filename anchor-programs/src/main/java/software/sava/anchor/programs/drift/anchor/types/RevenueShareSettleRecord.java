package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RevenueShareSettleRecord(long ts,
                                       PublicKey builder,
                                       PublicKey referrer,
                                       long feeSettled,
                                       int marketIndex,
                                       MarketType marketType,
                                       int builderSubAccountId,
                                       long builderTotalReferrerRewards,
                                       long builderTotalBuilderRewards) implements Borsh {

  public static RevenueShareSettleRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var builder = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (builder != null) {
      i += 32;
    }
    final var referrer = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (referrer != null) {
      i += 32;
    }
    final var feeSettled = getInt64LE(_data, i);
    i += 8;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var marketType = MarketType.read(_data, i);
    i += Borsh.len(marketType);
    final var builderSubAccountId = getInt16LE(_data, i);
    i += 2;
    final var builderTotalReferrerRewards = getInt64LE(_data, i);
    i += 8;
    final var builderTotalBuilderRewards = getInt64LE(_data, i);
    return new RevenueShareSettleRecord(ts,
                                        builder,
                                        referrer,
                                        feeSettled,
                                        marketIndex,
                                        marketType,
                                        builderSubAccountId,
                                        builderTotalReferrerRewards,
                                        builderTotalBuilderRewards);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    i += Borsh.writeOptional(builder, _data, i);
    i += Borsh.writeOptional(referrer, _data, i);
    putInt64LE(_data, i, feeSettled);
    i += 8;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    i += Borsh.write(marketType, _data, i);
    putInt16LE(_data, i, builderSubAccountId);
    i += 2;
    putInt64LE(_data, i, builderTotalReferrerRewards);
    i += 8;
    putInt64LE(_data, i, builderTotalBuilderRewards);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return 8
         + (builder == null ? 1 : (1 + 32))
         + (referrer == null ? 1 : (1 + 32))
         + 8
         + 2
         + Borsh.len(marketType)
         + 2
         + 8
         + 8;
  }
}
