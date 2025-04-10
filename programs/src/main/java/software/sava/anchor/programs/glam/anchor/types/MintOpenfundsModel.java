package software.sava.anchor.programs.glam.anchor.types;

import java.lang.Boolean;
import java.lang.String;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;

public record MintOpenfundsModel(String isin, byte[] _isin,
                                 String shareClassCurrency, byte[] _shareClassCurrency,
                                 String fullShareClassName, byte[] _fullShareClassName,
                                 Boolean hasPerformanceFee,
                                 String investmentStatus, byte[] _investmentStatus,
                                 String minimalInitialSubscriptionInAmount, byte[] _minimalInitialSubscriptionInAmount,
                                 String minimalInitialSubscriptionInShares, byte[] _minimalInitialSubscriptionInShares,
                                 String minimalSubsequentSubscriptionInAmount, byte[] _minimalSubsequentSubscriptionInAmount,
                                 String minimalSubsequentSubscriptionInShares, byte[] _minimalSubsequentSubscriptionInShares,
                                 String shareClassLaunchDate, byte[] _shareClassLaunchDate,
                                 String shareClassLifecycle, byte[] _shareClassLifecycle,
                                 String launchPrice, byte[] _launchPrice,
                                 String launchPriceCurrency, byte[] _launchPriceCurrency,
                                 String launchPriceDate, byte[] _launchPriceDate,
                                 Boolean hasLockUpForRedemption,
                                 String lockUpComment, byte[] _lockUpComment,
                                 String lockUpPeriodInDays, byte[] _lockUpPeriodInDays,
                                 String minimalInitialRedemptionInAmount, byte[] _minimalInitialRedemptionInAmount,
                                 String minimalInitialRedemptionInShares, byte[] _minimalInitialRedemptionInShares,
                                 String minimalSubsequentRedemptionInAmount, byte[] _minimalSubsequentRedemptionInAmount,
                                 String minimalSubsequentRedemptionInShares, byte[] _minimalSubsequentRedemptionInShares,
                                 String roundingMethodForPrices, byte[] _roundingMethodForPrices,
                                 String cusip, byte[] _cusip,
                                 String valor, byte[] _valor) implements Borsh {

  public static MintOpenfundsModel createRecord(final String isin,
                                                final String shareClassCurrency,
                                                final String fullShareClassName,
                                                final Boolean hasPerformanceFee,
                                                final String investmentStatus,
                                                final String minimalInitialSubscriptionInAmount,
                                                final String minimalInitialSubscriptionInShares,
                                                final String minimalSubsequentSubscriptionInAmount,
                                                final String minimalSubsequentSubscriptionInShares,
                                                final String shareClassLaunchDate,
                                                final String shareClassLifecycle,
                                                final String launchPrice,
                                                final String launchPriceCurrency,
                                                final String launchPriceDate,
                                                final Boolean hasLockUpForRedemption,
                                                final String lockUpComment,
                                                final String lockUpPeriodInDays,
                                                final String minimalInitialRedemptionInAmount,
                                                final String minimalInitialRedemptionInShares,
                                                final String minimalSubsequentRedemptionInAmount,
                                                final String minimalSubsequentRedemptionInShares,
                                                final String roundingMethodForPrices,
                                                final String cusip,
                                                final String valor) {
    return new MintOpenfundsModel(isin, Borsh.getBytes(isin),
                                  shareClassCurrency, Borsh.getBytes(shareClassCurrency),
                                  fullShareClassName, Borsh.getBytes(fullShareClassName),
                                  hasPerformanceFee,
                                  investmentStatus, Borsh.getBytes(investmentStatus),
                                  minimalInitialSubscriptionInAmount, Borsh.getBytes(minimalInitialSubscriptionInAmount),
                                  minimalInitialSubscriptionInShares, Borsh.getBytes(minimalInitialSubscriptionInShares),
                                  minimalSubsequentSubscriptionInAmount, Borsh.getBytes(minimalSubsequentSubscriptionInAmount),
                                  minimalSubsequentSubscriptionInShares, Borsh.getBytes(minimalSubsequentSubscriptionInShares),
                                  shareClassLaunchDate, Borsh.getBytes(shareClassLaunchDate),
                                  shareClassLifecycle, Borsh.getBytes(shareClassLifecycle),
                                  launchPrice, Borsh.getBytes(launchPrice),
                                  launchPriceCurrency, Borsh.getBytes(launchPriceCurrency),
                                  launchPriceDate, Borsh.getBytes(launchPriceDate),
                                  hasLockUpForRedemption,
                                  lockUpComment, Borsh.getBytes(lockUpComment),
                                  lockUpPeriodInDays, Borsh.getBytes(lockUpPeriodInDays),
                                  minimalInitialRedemptionInAmount, Borsh.getBytes(minimalInitialRedemptionInAmount),
                                  minimalInitialRedemptionInShares, Borsh.getBytes(minimalInitialRedemptionInShares),
                                  minimalSubsequentRedemptionInAmount, Borsh.getBytes(minimalSubsequentRedemptionInAmount),
                                  minimalSubsequentRedemptionInShares, Borsh.getBytes(minimalSubsequentRedemptionInShares),
                                  roundingMethodForPrices, Borsh.getBytes(roundingMethodForPrices),
                                  cusip, Borsh.getBytes(cusip),
                                  valor, Borsh.getBytes(valor));
  }

  public static MintOpenfundsModel read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var isin = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (isin != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var shareClassCurrency = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (shareClassCurrency != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var fullShareClassName = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (fullShareClassName != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var hasPerformanceFee = _data[i++] == 0 ? null : _data[i] == 1;
    if (hasPerformanceFee != null) {
      ++i;
    }
    final var investmentStatus = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (investmentStatus != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var minimalInitialSubscriptionInAmount = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (minimalInitialSubscriptionInAmount != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var minimalInitialSubscriptionInShares = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (minimalInitialSubscriptionInShares != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var minimalSubsequentSubscriptionInAmount = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (minimalSubsequentSubscriptionInAmount != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var minimalSubsequentSubscriptionInShares = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (minimalSubsequentSubscriptionInShares != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var shareClassLaunchDate = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (shareClassLaunchDate != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var shareClassLifecycle = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (shareClassLifecycle != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var launchPrice = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (launchPrice != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var launchPriceCurrency = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (launchPriceCurrency != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var launchPriceDate = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (launchPriceDate != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var hasLockUpForRedemption = _data[i++] == 0 ? null : _data[i] == 1;
    if (hasLockUpForRedemption != null) {
      ++i;
    }
    final var lockUpComment = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (lockUpComment != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var lockUpPeriodInDays = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (lockUpPeriodInDays != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var minimalInitialRedemptionInAmount = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (minimalInitialRedemptionInAmount != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var minimalInitialRedemptionInShares = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (minimalInitialRedemptionInShares != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var minimalSubsequentRedemptionInAmount = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (minimalSubsequentRedemptionInAmount != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var minimalSubsequentRedemptionInShares = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (minimalSubsequentRedemptionInShares != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var roundingMethodForPrices = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (roundingMethodForPrices != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var cusip = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (cusip != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var valor = _data[i++] == 0 ? null : Borsh.string(_data, i);
    return new MintOpenfundsModel(isin, Borsh.getBytes(isin),
                                  shareClassCurrency, Borsh.getBytes(shareClassCurrency),
                                  fullShareClassName, Borsh.getBytes(fullShareClassName),
                                  hasPerformanceFee,
                                  investmentStatus, Borsh.getBytes(investmentStatus),
                                  minimalInitialSubscriptionInAmount, Borsh.getBytes(minimalInitialSubscriptionInAmount),
                                  minimalInitialSubscriptionInShares, Borsh.getBytes(minimalInitialSubscriptionInShares),
                                  minimalSubsequentSubscriptionInAmount, Borsh.getBytes(minimalSubsequentSubscriptionInAmount),
                                  minimalSubsequentSubscriptionInShares, Borsh.getBytes(minimalSubsequentSubscriptionInShares),
                                  shareClassLaunchDate, Borsh.getBytes(shareClassLaunchDate),
                                  shareClassLifecycle, Borsh.getBytes(shareClassLifecycle),
                                  launchPrice, Borsh.getBytes(launchPrice),
                                  launchPriceCurrency, Borsh.getBytes(launchPriceCurrency),
                                  launchPriceDate, Borsh.getBytes(launchPriceDate),
                                  hasLockUpForRedemption,
                                  lockUpComment, Borsh.getBytes(lockUpComment),
                                  lockUpPeriodInDays, Borsh.getBytes(lockUpPeriodInDays),
                                  minimalInitialRedemptionInAmount, Borsh.getBytes(minimalInitialRedemptionInAmount),
                                  minimalInitialRedemptionInShares, Borsh.getBytes(minimalInitialRedemptionInShares),
                                  minimalSubsequentRedemptionInAmount, Borsh.getBytes(minimalSubsequentRedemptionInAmount),
                                  minimalSubsequentRedemptionInShares, Borsh.getBytes(minimalSubsequentRedemptionInShares),
                                  roundingMethodForPrices, Borsh.getBytes(roundingMethodForPrices),
                                  cusip, Borsh.getBytes(cusip),
                                  valor, Borsh.getBytes(valor));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptionalVector(_isin, _data, i);
    i += Borsh.writeOptionalVector(_shareClassCurrency, _data, i);
    i += Borsh.writeOptionalVector(_fullShareClassName, _data, i);
    i += Borsh.writeOptional(hasPerformanceFee, _data, i);
    i += Borsh.writeOptionalVector(_investmentStatus, _data, i);
    i += Borsh.writeOptionalVector(_minimalInitialSubscriptionInAmount, _data, i);
    i += Borsh.writeOptionalVector(_minimalInitialSubscriptionInShares, _data, i);
    i += Borsh.writeOptionalVector(_minimalSubsequentSubscriptionInAmount, _data, i);
    i += Borsh.writeOptionalVector(_minimalSubsequentSubscriptionInShares, _data, i);
    i += Borsh.writeOptionalVector(_shareClassLaunchDate, _data, i);
    i += Borsh.writeOptionalVector(_shareClassLifecycle, _data, i);
    i += Borsh.writeOptionalVector(_launchPrice, _data, i);
    i += Borsh.writeOptionalVector(_launchPriceCurrency, _data, i);
    i += Borsh.writeOptionalVector(_launchPriceDate, _data, i);
    i += Borsh.writeOptional(hasLockUpForRedemption, _data, i);
    i += Borsh.writeOptionalVector(_lockUpComment, _data, i);
    i += Borsh.writeOptionalVector(_lockUpPeriodInDays, _data, i);
    i += Borsh.writeOptionalVector(_minimalInitialRedemptionInAmount, _data, i);
    i += Borsh.writeOptionalVector(_minimalInitialRedemptionInShares, _data, i);
    i += Borsh.writeOptionalVector(_minimalSubsequentRedemptionInAmount, _data, i);
    i += Borsh.writeOptionalVector(_minimalSubsequentRedemptionInShares, _data, i);
    i += Borsh.writeOptionalVector(_roundingMethodForPrices, _data, i);
    i += Borsh.writeOptionalVector(_cusip, _data, i);
    i += Borsh.writeOptionalVector(_valor, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (_isin == null || _isin.length == 0 ? 1 : (1 + Borsh.lenVector(_isin)))
         + (_shareClassCurrency == null || _shareClassCurrency.length == 0 ? 1 : (1 + Borsh.lenVector(_shareClassCurrency)))
         + (_fullShareClassName == null || _fullShareClassName.length == 0 ? 1 : (1 + Borsh.lenVector(_fullShareClassName)))
         + (hasPerformanceFee == null ? 1 : (1 + 1))
         + (_investmentStatus == null || _investmentStatus.length == 0 ? 1 : (1 + Borsh.lenVector(_investmentStatus)))
         + (_minimalInitialSubscriptionInAmount == null || _minimalInitialSubscriptionInAmount.length == 0 ? 1 : (1 + Borsh.lenVector(_minimalInitialSubscriptionInAmount)))
         + (_minimalInitialSubscriptionInShares == null || _minimalInitialSubscriptionInShares.length == 0 ? 1 : (1 + Borsh.lenVector(_minimalInitialSubscriptionInShares)))
         + (_minimalSubsequentSubscriptionInAmount == null || _minimalSubsequentSubscriptionInAmount.length == 0 ? 1 : (1 + Borsh.lenVector(_minimalSubsequentSubscriptionInAmount)))
         + (_minimalSubsequentSubscriptionInShares == null || _minimalSubsequentSubscriptionInShares.length == 0 ? 1 : (1 + Borsh.lenVector(_minimalSubsequentSubscriptionInShares)))
         + (_shareClassLaunchDate == null || _shareClassLaunchDate.length == 0 ? 1 : (1 + Borsh.lenVector(_shareClassLaunchDate)))
         + (_shareClassLifecycle == null || _shareClassLifecycle.length == 0 ? 1 : (1 + Borsh.lenVector(_shareClassLifecycle)))
         + (_launchPrice == null || _launchPrice.length == 0 ? 1 : (1 + Borsh.lenVector(_launchPrice)))
         + (_launchPriceCurrency == null || _launchPriceCurrency.length == 0 ? 1 : (1 + Borsh.lenVector(_launchPriceCurrency)))
         + (_launchPriceDate == null || _launchPriceDate.length == 0 ? 1 : (1 + Borsh.lenVector(_launchPriceDate)))
         + (hasLockUpForRedemption == null ? 1 : (1 + 1))
         + (_lockUpComment == null || _lockUpComment.length == 0 ? 1 : (1 + Borsh.lenVector(_lockUpComment)))
         + (_lockUpPeriodInDays == null || _lockUpPeriodInDays.length == 0 ? 1 : (1 + Borsh.lenVector(_lockUpPeriodInDays)))
         + (_minimalInitialRedemptionInAmount == null || _minimalInitialRedemptionInAmount.length == 0 ? 1 : (1 + Borsh.lenVector(_minimalInitialRedemptionInAmount)))
         + (_minimalInitialRedemptionInShares == null || _minimalInitialRedemptionInShares.length == 0 ? 1 : (1 + Borsh.lenVector(_minimalInitialRedemptionInShares)))
         + (_minimalSubsequentRedemptionInAmount == null || _minimalSubsequentRedemptionInAmount.length == 0 ? 1 : (1 + Borsh.lenVector(_minimalSubsequentRedemptionInAmount)))
         + (_minimalSubsequentRedemptionInShares == null || _minimalSubsequentRedemptionInShares.length == 0 ? 1 : (1 + Borsh.lenVector(_minimalSubsequentRedemptionInShares)))
         + (_roundingMethodForPrices == null || _roundingMethodForPrices.length == 0 ? 1 : (1 + Borsh.lenVector(_roundingMethodForPrices)))
         + (_cusip == null || _cusip.length == 0 ? 1 : (1 + Borsh.lenVector(_cusip)))
         + (_valor == null || _valor.length == 0 ? 1 : (1 + Borsh.lenVector(_valor)));
  }
}
