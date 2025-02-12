package software.sava.anchor.programs.glam.anchor.types;

import java.lang.Boolean;
import java.lang.String;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;

public record MintOpenfundsModel(String isin, byte[] _isin,
                                 String shareClassCurrency, byte[] _shareClassCurrency,
                                 String currencyOfMinimalSubscription, byte[] _currencyOfMinimalSubscription,
                                 String fullShareClassName, byte[] _fullShareClassName,
                                 String investmentStatus, byte[] _investmentStatus,
                                 String minimalInitialSubscriptionCategory, byte[] _minimalInitialSubscriptionCategory,
                                 String minimalInitialSubscriptionInAmount, byte[] _minimalInitialSubscriptionInAmount,
                                 String minimalInitialSubscriptionInShares, byte[] _minimalInitialSubscriptionInShares,
                                 String shareClassDistributionPolicy, byte[] _shareClassDistributionPolicy,
                                 String shareClassExtension, byte[] _shareClassExtension,
                                 String shareClassLaunchDate, byte[] _shareClassLaunchDate,
                                 String shareClassLifecycle, byte[] _shareClassLifecycle,
                                 String launchPrice, byte[] _launchPrice,
                                 String launchPriceCurrency, byte[] _launchPriceCurrency,
                                 String launchPriceDate, byte[] _launchPriceDate,
                                 String currencyOfMinimalOrMaximumRedemption, byte[] _currencyOfMinimalOrMaximumRedemption,
                                 Boolean hasLockUpForRedemption,
                                 Boolean isValidIsin,
                                 String lockUpComment, byte[] _lockUpComment,
                                 String lockUpPeriodInDays, byte[] _lockUpPeriodInDays,
                                 String maximumInitialRedemptionInAmount, byte[] _maximumInitialRedemptionInAmount,
                                 String maximumInitialRedemptionInShares, byte[] _maximumInitialRedemptionInShares,
                                 String minimalInitialRedemptionInAmount, byte[] _minimalInitialRedemptionInAmount,
                                 String minimalInitialRedemptionInShares, byte[] _minimalInitialRedemptionInShares,
                                 String minimalRedemptionCategory, byte[] _minimalRedemptionCategory,
                                 String shareClassDividendType, byte[] _shareClassDividendType,
                                 String cusip, byte[] _cusip,
                                 String valor, byte[] _valor) implements Borsh {

  public static MintOpenfundsModel createRecord(final String isin,
                                                final String shareClassCurrency,
                                                final String currencyOfMinimalSubscription,
                                                final String fullShareClassName,
                                                final String investmentStatus,
                                                final String minimalInitialSubscriptionCategory,
                                                final String minimalInitialSubscriptionInAmount,
                                                final String minimalInitialSubscriptionInShares,
                                                final String shareClassDistributionPolicy,
                                                final String shareClassExtension,
                                                final String shareClassLaunchDate,
                                                final String shareClassLifecycle,
                                                final String launchPrice,
                                                final String launchPriceCurrency,
                                                final String launchPriceDate,
                                                final String currencyOfMinimalOrMaximumRedemption,
                                                final Boolean hasLockUpForRedemption,
                                                final Boolean isValidIsin,
                                                final String lockUpComment,
                                                final String lockUpPeriodInDays,
                                                final String maximumInitialRedemptionInAmount,
                                                final String maximumInitialRedemptionInShares,
                                                final String minimalInitialRedemptionInAmount,
                                                final String minimalInitialRedemptionInShares,
                                                final String minimalRedemptionCategory,
                                                final String shareClassDividendType,
                                                final String cusip,
                                                final String valor) {
    return new MintOpenfundsModel(isin, Borsh.getBytes(isin),
                                  shareClassCurrency, Borsh.getBytes(shareClassCurrency),
                                  currencyOfMinimalSubscription, Borsh.getBytes(currencyOfMinimalSubscription),
                                  fullShareClassName, Borsh.getBytes(fullShareClassName),
                                  investmentStatus, Borsh.getBytes(investmentStatus),
                                  minimalInitialSubscriptionCategory, Borsh.getBytes(minimalInitialSubscriptionCategory),
                                  minimalInitialSubscriptionInAmount, Borsh.getBytes(minimalInitialSubscriptionInAmount),
                                  minimalInitialSubscriptionInShares, Borsh.getBytes(minimalInitialSubscriptionInShares),
                                  shareClassDistributionPolicy, Borsh.getBytes(shareClassDistributionPolicy),
                                  shareClassExtension, Borsh.getBytes(shareClassExtension),
                                  shareClassLaunchDate, Borsh.getBytes(shareClassLaunchDate),
                                  shareClassLifecycle, Borsh.getBytes(shareClassLifecycle),
                                  launchPrice, Borsh.getBytes(launchPrice),
                                  launchPriceCurrency, Borsh.getBytes(launchPriceCurrency),
                                  launchPriceDate, Borsh.getBytes(launchPriceDate),
                                  currencyOfMinimalOrMaximumRedemption, Borsh.getBytes(currencyOfMinimalOrMaximumRedemption),
                                  hasLockUpForRedemption,
                                  isValidIsin,
                                  lockUpComment, Borsh.getBytes(lockUpComment),
                                  lockUpPeriodInDays, Borsh.getBytes(lockUpPeriodInDays),
                                  maximumInitialRedemptionInAmount, Borsh.getBytes(maximumInitialRedemptionInAmount),
                                  maximumInitialRedemptionInShares, Borsh.getBytes(maximumInitialRedemptionInShares),
                                  minimalInitialRedemptionInAmount, Borsh.getBytes(minimalInitialRedemptionInAmount),
                                  minimalInitialRedemptionInShares, Borsh.getBytes(minimalInitialRedemptionInShares),
                                  minimalRedemptionCategory, Borsh.getBytes(minimalRedemptionCategory),
                                  shareClassDividendType, Borsh.getBytes(shareClassDividendType),
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
    final var currencyOfMinimalSubscription = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (currencyOfMinimalSubscription != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var fullShareClassName = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (fullShareClassName != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var investmentStatus = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (investmentStatus != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var minimalInitialSubscriptionCategory = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (minimalInitialSubscriptionCategory != null) {
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
    final var shareClassDistributionPolicy = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (shareClassDistributionPolicy != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var shareClassExtension = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (shareClassExtension != null) {
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
    final var currencyOfMinimalOrMaximumRedemption = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (currencyOfMinimalOrMaximumRedemption != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var hasLockUpForRedemption = _data[i++] == 0 ? null : _data[i] == 1;
    if (hasLockUpForRedemption != null) {
      ++i;
    }
    final var isValidIsin = _data[i++] == 0 ? null : _data[i] == 1;
    if (isValidIsin != null) {
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
    final var maximumInitialRedemptionInAmount = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (maximumInitialRedemptionInAmount != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var maximumInitialRedemptionInShares = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (maximumInitialRedemptionInShares != null) {
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
    final var minimalRedemptionCategory = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (minimalRedemptionCategory != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var shareClassDividendType = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (shareClassDividendType != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var cusip = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (cusip != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var valor = _data[i++] == 0 ? null : Borsh.string(_data, i);
    return new MintOpenfundsModel(isin, Borsh.getBytes(isin),
                                  shareClassCurrency, Borsh.getBytes(shareClassCurrency),
                                  currencyOfMinimalSubscription, Borsh.getBytes(currencyOfMinimalSubscription),
                                  fullShareClassName, Borsh.getBytes(fullShareClassName),
                                  investmentStatus, Borsh.getBytes(investmentStatus),
                                  minimalInitialSubscriptionCategory, Borsh.getBytes(minimalInitialSubscriptionCategory),
                                  minimalInitialSubscriptionInAmount, Borsh.getBytes(minimalInitialSubscriptionInAmount),
                                  minimalInitialSubscriptionInShares, Borsh.getBytes(minimalInitialSubscriptionInShares),
                                  shareClassDistributionPolicy, Borsh.getBytes(shareClassDistributionPolicy),
                                  shareClassExtension, Borsh.getBytes(shareClassExtension),
                                  shareClassLaunchDate, Borsh.getBytes(shareClassLaunchDate),
                                  shareClassLifecycle, Borsh.getBytes(shareClassLifecycle),
                                  launchPrice, Borsh.getBytes(launchPrice),
                                  launchPriceCurrency, Borsh.getBytes(launchPriceCurrency),
                                  launchPriceDate, Borsh.getBytes(launchPriceDate),
                                  currencyOfMinimalOrMaximumRedemption, Borsh.getBytes(currencyOfMinimalOrMaximumRedemption),
                                  hasLockUpForRedemption,
                                  isValidIsin,
                                  lockUpComment, Borsh.getBytes(lockUpComment),
                                  lockUpPeriodInDays, Borsh.getBytes(lockUpPeriodInDays),
                                  maximumInitialRedemptionInAmount, Borsh.getBytes(maximumInitialRedemptionInAmount),
                                  maximumInitialRedemptionInShares, Borsh.getBytes(maximumInitialRedemptionInShares),
                                  minimalInitialRedemptionInAmount, Borsh.getBytes(minimalInitialRedemptionInAmount),
                                  minimalInitialRedemptionInShares, Borsh.getBytes(minimalInitialRedemptionInShares),
                                  minimalRedemptionCategory, Borsh.getBytes(minimalRedemptionCategory),
                                  shareClassDividendType, Borsh.getBytes(shareClassDividendType),
                                  cusip, Borsh.getBytes(cusip),
                                  valor, Borsh.getBytes(valor));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptionalVector(_isin, _data, i);
    i += Borsh.writeOptionalVector(_shareClassCurrency, _data, i);
    i += Borsh.writeOptionalVector(_currencyOfMinimalSubscription, _data, i);
    i += Borsh.writeOptionalVector(_fullShareClassName, _data, i);
    i += Borsh.writeOptionalVector(_investmentStatus, _data, i);
    i += Borsh.writeOptionalVector(_minimalInitialSubscriptionCategory, _data, i);
    i += Borsh.writeOptionalVector(_minimalInitialSubscriptionInAmount, _data, i);
    i += Borsh.writeOptionalVector(_minimalInitialSubscriptionInShares, _data, i);
    i += Borsh.writeOptionalVector(_shareClassDistributionPolicy, _data, i);
    i += Borsh.writeOptionalVector(_shareClassExtension, _data, i);
    i += Borsh.writeOptionalVector(_shareClassLaunchDate, _data, i);
    i += Borsh.writeOptionalVector(_shareClassLifecycle, _data, i);
    i += Borsh.writeOptionalVector(_launchPrice, _data, i);
    i += Borsh.writeOptionalVector(_launchPriceCurrency, _data, i);
    i += Borsh.writeOptionalVector(_launchPriceDate, _data, i);
    i += Borsh.writeOptionalVector(_currencyOfMinimalOrMaximumRedemption, _data, i);
    i += Borsh.writeOptional(hasLockUpForRedemption, _data, i);
    i += Borsh.writeOptional(isValidIsin, _data, i);
    i += Borsh.writeOptionalVector(_lockUpComment, _data, i);
    i += Borsh.writeOptionalVector(_lockUpPeriodInDays, _data, i);
    i += Borsh.writeOptionalVector(_maximumInitialRedemptionInAmount, _data, i);
    i += Borsh.writeOptionalVector(_maximumInitialRedemptionInShares, _data, i);
    i += Borsh.writeOptionalVector(_minimalInitialRedemptionInAmount, _data, i);
    i += Borsh.writeOptionalVector(_minimalInitialRedemptionInShares, _data, i);
    i += Borsh.writeOptionalVector(_minimalRedemptionCategory, _data, i);
    i += Borsh.writeOptionalVector(_shareClassDividendType, _data, i);
    i += Borsh.writeOptionalVector(_cusip, _data, i);
    i += Borsh.writeOptionalVector(_valor, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (_isin == null || _isin.length == 0 ? 1 : (1 + Borsh.lenVector(_isin)))
         + (_shareClassCurrency == null || _shareClassCurrency.length == 0 ? 1 : (1 + Borsh.lenVector(_shareClassCurrency)))
         + (_currencyOfMinimalSubscription == null || _currencyOfMinimalSubscription.length == 0 ? 1 : (1 + Borsh.lenVector(_currencyOfMinimalSubscription)))
         + (_fullShareClassName == null || _fullShareClassName.length == 0 ? 1 : (1 + Borsh.lenVector(_fullShareClassName)))
         + (_investmentStatus == null || _investmentStatus.length == 0 ? 1 : (1 + Borsh.lenVector(_investmentStatus)))
         + (_minimalInitialSubscriptionCategory == null || _minimalInitialSubscriptionCategory.length == 0 ? 1 : (1 + Borsh.lenVector(_minimalInitialSubscriptionCategory)))
         + (_minimalInitialSubscriptionInAmount == null || _minimalInitialSubscriptionInAmount.length == 0 ? 1 : (1 + Borsh.lenVector(_minimalInitialSubscriptionInAmount)))
         + (_minimalInitialSubscriptionInShares == null || _minimalInitialSubscriptionInShares.length == 0 ? 1 : (1 + Borsh.lenVector(_minimalInitialSubscriptionInShares)))
         + (_shareClassDistributionPolicy == null || _shareClassDistributionPolicy.length == 0 ? 1 : (1 + Borsh.lenVector(_shareClassDistributionPolicy)))
         + (_shareClassExtension == null || _shareClassExtension.length == 0 ? 1 : (1 + Borsh.lenVector(_shareClassExtension)))
         + (_shareClassLaunchDate == null || _shareClassLaunchDate.length == 0 ? 1 : (1 + Borsh.lenVector(_shareClassLaunchDate)))
         + (_shareClassLifecycle == null || _shareClassLifecycle.length == 0 ? 1 : (1 + Borsh.lenVector(_shareClassLifecycle)))
         + (_launchPrice == null || _launchPrice.length == 0 ? 1 : (1 + Borsh.lenVector(_launchPrice)))
         + (_launchPriceCurrency == null || _launchPriceCurrency.length == 0 ? 1 : (1 + Borsh.lenVector(_launchPriceCurrency)))
         + (_launchPriceDate == null || _launchPriceDate.length == 0 ? 1 : (1 + Borsh.lenVector(_launchPriceDate)))
         + (_currencyOfMinimalOrMaximumRedemption == null || _currencyOfMinimalOrMaximumRedemption.length == 0 ? 1 : (1 + Borsh.lenVector(_currencyOfMinimalOrMaximumRedemption)))
         + (hasLockUpForRedemption == null ? 1 : (1 + 1))
         + (isValidIsin == null ? 1 : (1 + 1))
         + (_lockUpComment == null || _lockUpComment.length == 0 ? 1 : (1 + Borsh.lenVector(_lockUpComment)))
         + (_lockUpPeriodInDays == null || _lockUpPeriodInDays.length == 0 ? 1 : (1 + Borsh.lenVector(_lockUpPeriodInDays)))
         + (_maximumInitialRedemptionInAmount == null || _maximumInitialRedemptionInAmount.length == 0 ? 1 : (1 + Borsh.lenVector(_maximumInitialRedemptionInAmount)))
         + (_maximumInitialRedemptionInShares == null || _maximumInitialRedemptionInShares.length == 0 ? 1 : (1 + Borsh.lenVector(_maximumInitialRedemptionInShares)))
         + (_minimalInitialRedemptionInAmount == null || _minimalInitialRedemptionInAmount.length == 0 ? 1 : (1 + Borsh.lenVector(_minimalInitialRedemptionInAmount)))
         + (_minimalInitialRedemptionInShares == null || _minimalInitialRedemptionInShares.length == 0 ? 1 : (1 + Borsh.lenVector(_minimalInitialRedemptionInShares)))
         + (_minimalRedemptionCategory == null || _minimalRedemptionCategory.length == 0 ? 1 : (1 + Borsh.lenVector(_minimalRedemptionCategory)))
         + (_shareClassDividendType == null || _shareClassDividendType.length == 0 ? 1 : (1 + Borsh.lenVector(_shareClassDividendType)))
         + (_cusip == null || _cusip.length == 0 ? 1 : (1 + Borsh.lenVector(_cusip)))
         + (_valor == null || _valor.length == 0 ? 1 : (1 + Borsh.lenVector(_valor)));
  }
}
