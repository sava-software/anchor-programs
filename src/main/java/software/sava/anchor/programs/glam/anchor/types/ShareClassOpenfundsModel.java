package software.sava.anchor.programs.glam.anchor.types;

import java.lang.Boolean;
import java.lang.String;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;

public record ShareClassOpenfundsModel(String isin, byte[] _isin,
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


  public static ShareClassOpenfundsModel createRecord(final String isin,
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
    return new ShareClassOpenfundsModel(isin, Borsh.getBytes(isin),
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

  public static ShareClassOpenfundsModel read(final byte[] _data, final int offset) {
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
    return new ShareClassOpenfundsModel(isin, Borsh.getBytes(isin),
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
    i += Borsh.writeOptional(_isin, _data, i);
    i += Borsh.writeOptional(_shareClassCurrency, _data, i);
    i += Borsh.writeOptional(_currencyOfMinimalSubscription, _data, i);
    i += Borsh.writeOptional(_fullShareClassName, _data, i);
    i += Borsh.writeOptional(_investmentStatus, _data, i);
    i += Borsh.writeOptional(_minimalInitialSubscriptionCategory, _data, i);
    i += Borsh.writeOptional(_minimalInitialSubscriptionInAmount, _data, i);
    i += Borsh.writeOptional(_minimalInitialSubscriptionInShares, _data, i);
    i += Borsh.writeOptional(_shareClassDistributionPolicy, _data, i);
    i += Borsh.writeOptional(_shareClassExtension, _data, i);
    i += Borsh.writeOptional(_shareClassLaunchDate, _data, i);
    i += Borsh.writeOptional(_shareClassLifecycle, _data, i);
    i += Borsh.writeOptional(_launchPrice, _data, i);
    i += Borsh.writeOptional(_launchPriceCurrency, _data, i);
    i += Borsh.writeOptional(_launchPriceDate, _data, i);
    i += Borsh.writeOptional(_currencyOfMinimalOrMaximumRedemption, _data, i);
    i += Borsh.writeOptional(hasLockUpForRedemption, _data, i);
    i += Borsh.writeOptional(isValidIsin, _data, i);
    i += Borsh.writeOptional(_lockUpComment, _data, i);
    i += Borsh.writeOptional(_lockUpPeriodInDays, _data, i);
    i += Borsh.writeOptional(_maximumInitialRedemptionInAmount, _data, i);
    i += Borsh.writeOptional(_maximumInitialRedemptionInShares, _data, i);
    i += Borsh.writeOptional(_minimalInitialRedemptionInAmount, _data, i);
    i += Borsh.writeOptional(_minimalInitialRedemptionInShares, _data, i);
    i += Borsh.writeOptional(_minimalRedemptionCategory, _data, i);
    i += Borsh.writeOptional(_shareClassDividendType, _data, i);
    i += Borsh.writeOptional(_cusip, _data, i);
    i += Borsh.writeOptional(_valor, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenOptional(_isin)
         + Borsh.lenOptional(_shareClassCurrency)
         + Borsh.lenOptional(_currencyOfMinimalSubscription)
         + Borsh.lenOptional(_fullShareClassName)
         + Borsh.lenOptional(_investmentStatus)
         + Borsh.lenOptional(_minimalInitialSubscriptionCategory)
         + Borsh.lenOptional(_minimalInitialSubscriptionInAmount)
         + Borsh.lenOptional(_minimalInitialSubscriptionInShares)
         + Borsh.lenOptional(_shareClassDistributionPolicy)
         + Borsh.lenOptional(_shareClassExtension)
         + Borsh.lenOptional(_shareClassLaunchDate)
         + Borsh.lenOptional(_shareClassLifecycle)
         + Borsh.lenOptional(_launchPrice)
         + Borsh.lenOptional(_launchPriceCurrency)
         + Borsh.lenOptional(_launchPriceDate)
         + Borsh.lenOptional(_currencyOfMinimalOrMaximumRedemption)
         + 2
         + 2
         + Borsh.lenOptional(_lockUpComment)
         + Borsh.lenOptional(_lockUpPeriodInDays)
         + Borsh.lenOptional(_maximumInitialRedemptionInAmount)
         + Borsh.lenOptional(_maximumInitialRedemptionInShares)
         + Borsh.lenOptional(_minimalInitialRedemptionInAmount)
         + Borsh.lenOptional(_minimalInitialRedemptionInShares)
         + Borsh.lenOptional(_minimalRedemptionCategory)
         + Borsh.lenOptional(_shareClassDividendType)
         + Borsh.lenOptional(_cusip)
         + Borsh.lenOptional(_valor);
  }
}