package software.sava.anchor.programs.drift.anchor.types;

import java.lang.Boolean;

import java.util.OptionalLong;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record UpdatePerpMarketSummaryStatsParams(OptionalLong quoteAssetAmountWithUnsettledLp,
                                                 OptionalLong netUnsettledFundingPnl,
                                                 Boolean updateAmmSummaryStats,
                                                 Boolean excludeTotalLiqFee) implements Borsh {

  public static UpdatePerpMarketSummaryStatsParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var quoteAssetAmountWithUnsettledLp = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (quoteAssetAmountWithUnsettledLp.isPresent()) {
      i += 8;
    }
    final var netUnsettledFundingPnl = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (netUnsettledFundingPnl.isPresent()) {
      i += 8;
    }
    final var updateAmmSummaryStats = _data[i++] == 0 ? null : _data[i] == 1;
    if (updateAmmSummaryStats != null) {
      ++i;
    }
    final var excludeTotalLiqFee = _data[i++] == 0 ? null : _data[i] == 1;
    return new UpdatePerpMarketSummaryStatsParams(quoteAssetAmountWithUnsettledLp,
                                                  netUnsettledFundingPnl,
                                                  updateAmmSummaryStats,
                                                  excludeTotalLiqFee);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(quoteAssetAmountWithUnsettledLp, _data, i);
    i += Borsh.writeOptional(netUnsettledFundingPnl, _data, i);
    i += Borsh.writeOptional(updateAmmSummaryStats, _data, i);
    i += Borsh.writeOptional(excludeTotalLiqFee, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (quoteAssetAmountWithUnsettledLp == null || quoteAssetAmountWithUnsettledLp.isEmpty() ? 1 : (1 + 8)) + (netUnsettledFundingPnl == null || netUnsettledFundingPnl.isEmpty() ? 1 : (1 + 8)) + (updateAmmSummaryStats == null ? 1 : (1 + 1)) + (excludeTotalLiqFee == null ? 1 : (1 + 1));
  }
}
