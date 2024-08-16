package software.sava.anchor.programs.drift.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record UserStats(PublicKey _address,
                        Discriminator discriminator,
                        // The authority for all of a users sub accounts
                        PublicKey authority,
                        // The address that referred this user
                        PublicKey referrer,
                        // Stats on the fees paid by the user
                        UserFees fees,
                        // The timestamp of the next epoch
                        // Epoch is used to limit referrer rewards earned in single epoch
                        long nextEpochTs,
                        // Rolling 30day maker volume for user
                        // precision: QUOTE_PRECISION
                        long makerVolume30d,
                        // Rolling 30day taker volume for user
                        // precision: QUOTE_PRECISION
                        long takerVolume30d,
                        // Rolling 30day filler volume for user
                        // precision: QUOTE_PRECISION
                        long fillerVolume30d,
                        // last time the maker volume was updated
                        long lastMakerVolume30dTs,
                        // last time the taker volume was updated
                        long lastTakerVolume30dTs,
                        // last time the filler volume was updated
                        long lastFillerVolume30dTs,
                        // The amount of tokens staked in the quote spot markets if
                        long ifStakedQuoteAssetAmount,
                        // The current number of sub accounts
                        int numberOfSubAccounts,
                        // The number of sub accounts created. Can be greater than the number of sub accounts if user
                        // has deleted sub accounts
                        int numberOfSubAccountsCreated,
                        // Whether the user is a referrer. Sub account 0 can not be deleted if user is a referrer
                        boolean isReferrer,
                        boolean disableUpdatePerpBidAskTwap,
                        int[] padding) implements Borsh {

  public static final int AUTHORITY_OFFSET = 8;
  public static final int REFERRER_OFFSET = 40;
  public static final int FEES_OFFSET = 72;

  public static Filter createAuthorityFilter(final PublicKey authority) {
    return Filter.createMemCompFilter(AUTHORITY_OFFSET, authority);
  }

  public static Filter createReferrerFilter(final PublicKey referrer) {
    return Filter.createMemCompFilter(REFERRER_OFFSET, referrer);
  }

  public static Filter createFeesFilter(final UserFees fees) {
    return Filter.createMemCompFilter(FEES_OFFSET, fees.write());
  }

  public static UserStats read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static UserStats read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], UserStats> FACTORY = UserStats::read;

  public static UserStats read(final PublicKey _address, final byte[] _data, final int offset) {
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var authority = readPubKey(_data, i);
    i += 32;
    final var referrer = readPubKey(_data, i);
    i += 32;
    final var fees = UserFees.read(_data, i);
    i += Borsh.len(fees);
    final var nextEpochTs = getInt64LE(_data, i);
    i += 8;
    final var makerVolume30d = getInt64LE(_data, i);
    i += 8;
    final var takerVolume30d = getInt64LE(_data, i);
    i += 8;
    final var fillerVolume30d = getInt64LE(_data, i);
    i += 8;
    final var lastMakerVolume30dTs = getInt64LE(_data, i);
    i += 8;
    final var lastTakerVolume30dTs = getInt64LE(_data, i);
    i += 8;
    final var lastFillerVolume30dTs = getInt64LE(_data, i);
    i += 8;
    final var ifStakedQuoteAssetAmount = getInt64LE(_data, i);
    i += 8;
    final var numberOfSubAccounts = getInt16LE(_data, i);
    i += 2;
    final var numberOfSubAccountsCreated = getInt16LE(_data, i);
    i += 2;
    final var isReferrer = _data[i] == 1;
    ++i;
    final var disableUpdatePerpBidAskTwap = _data[i] == 1;
    ++i;
    final var padding = Borsh.readArray(new int[50], _data, i);
    return new UserStats(_address,
                         discriminator,
                         authority,
                         referrer,
                         fees,
                         nextEpochTs,
                         makerVolume30d,
                         takerVolume30d,
                         fillerVolume30d,
                         lastMakerVolume30dTs,
                         lastTakerVolume30dTs,
                         lastFillerVolume30dTs,
                         ifStakedQuoteAssetAmount,
                         numberOfSubAccounts,
                         numberOfSubAccountsCreated,
                         isReferrer,
                         disableUpdatePerpBidAskTwap,
                         padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    authority.write(_data, i);
    i += 32;
    referrer.write(_data, i);
    i += 32;
    i += Borsh.write(fees, _data, i);
    putInt64LE(_data, i, nextEpochTs);
    i += 8;
    putInt64LE(_data, i, makerVolume30d);
    i += 8;
    putInt64LE(_data, i, takerVolume30d);
    i += 8;
    putInt64LE(_data, i, fillerVolume30d);
    i += 8;
    putInt64LE(_data, i, lastMakerVolume30dTs);
    i += 8;
    putInt64LE(_data, i, lastTakerVolume30dTs);
    i += 8;
    putInt64LE(_data, i, lastFillerVolume30dTs);
    i += 8;
    putInt64LE(_data, i, ifStakedQuoteAssetAmount);
    i += 8;
    putInt16LE(_data, i, numberOfSubAccounts);
    i += 2;
    putInt16LE(_data, i, numberOfSubAccountsCreated);
    i += 2;
    _data[i] = (byte) (isReferrer ? 1 : 0);
    ++i;
    _data[i] = (byte) (disableUpdatePerpBidAskTwap ? 1 : 0);
    ++i;
    i += Borsh.fixedWrite(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32
         + 32
         + Borsh.len(fees)
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 2
         + 2
         + 1
         + 1
         + Borsh.fixedLen(padding);
  }
}
