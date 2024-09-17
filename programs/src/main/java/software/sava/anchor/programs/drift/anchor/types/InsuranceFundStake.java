package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InsuranceFundStake(PublicKey _address,
                                 Discriminator discriminator,
                                 PublicKey authority,
                                 BigInteger ifShares,
                                 BigInteger lastWithdrawRequestShares,
                                 BigInteger ifBase,
                                 long lastValidTs,
                                 long lastWithdrawRequestValue,
                                 long lastWithdrawRequestTs,
                                 long costBasis,
                                 int marketIndex,
                                 byte[] padding) implements Borsh {

  public static final int BYTES = 136;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int AUTHORITY_OFFSET = 8;
  public static final int IF_SHARES_OFFSET = 40;
  public static final int LAST_WITHDRAW_REQUEST_SHARES_OFFSET = 56;
  public static final int IF_BASE_OFFSET = 72;
  public static final int LAST_VALID_TS_OFFSET = 88;
  public static final int LAST_WITHDRAW_REQUEST_VALUE_OFFSET = 96;
  public static final int LAST_WITHDRAW_REQUEST_TS_OFFSET = 104;
  public static final int COST_BASIS_OFFSET = 112;
  public static final int MARKET_INDEX_OFFSET = 120;
  public static final int PADDING_OFFSET = 122;

  public static Filter createAuthorityFilter(final PublicKey authority) {
    return Filter.createMemCompFilter(AUTHORITY_OFFSET, authority);
  }

  public static Filter createIfSharesFilter(final BigInteger ifShares) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, ifShares);
    return Filter.createMemCompFilter(IF_SHARES_OFFSET, _data);
  }

  public static Filter createLastWithdrawRequestSharesFilter(final BigInteger lastWithdrawRequestShares) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, lastWithdrawRequestShares);
    return Filter.createMemCompFilter(LAST_WITHDRAW_REQUEST_SHARES_OFFSET, _data);
  }

  public static Filter createIfBaseFilter(final BigInteger ifBase) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, ifBase);
    return Filter.createMemCompFilter(IF_BASE_OFFSET, _data);
  }

  public static Filter createLastValidTsFilter(final long lastValidTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastValidTs);
    return Filter.createMemCompFilter(LAST_VALID_TS_OFFSET, _data);
  }

  public static Filter createLastWithdrawRequestValueFilter(final long lastWithdrawRequestValue) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastWithdrawRequestValue);
    return Filter.createMemCompFilter(LAST_WITHDRAW_REQUEST_VALUE_OFFSET, _data);
  }

  public static Filter createLastWithdrawRequestTsFilter(final long lastWithdrawRequestTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastWithdrawRequestTs);
    return Filter.createMemCompFilter(LAST_WITHDRAW_REQUEST_TS_OFFSET, _data);
  }

  public static Filter createCostBasisFilter(final long costBasis) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, costBasis);
    return Filter.createMemCompFilter(COST_BASIS_OFFSET, _data);
  }

  public static Filter createMarketIndexFilter(final int marketIndex) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, marketIndex);
    return Filter.createMemCompFilter(MARKET_INDEX_OFFSET, _data);
  }

  public static InsuranceFundStake read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static InsuranceFundStake read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], InsuranceFundStake> FACTORY = InsuranceFundStake::read;

  public static InsuranceFundStake read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var authority = readPubKey(_data, i);
    i += 32;
    final var ifShares = getInt128LE(_data, i);
    i += 16;
    final var lastWithdrawRequestShares = getInt128LE(_data, i);
    i += 16;
    final var ifBase = getInt128LE(_data, i);
    i += 16;
    final var lastValidTs = getInt64LE(_data, i);
    i += 8;
    final var lastWithdrawRequestValue = getInt64LE(_data, i);
    i += 8;
    final var lastWithdrawRequestTs = getInt64LE(_data, i);
    i += 8;
    final var costBasis = getInt64LE(_data, i);
    i += 8;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var padding = new byte[14];
    Borsh.readArray(padding, _data, i);
    return new InsuranceFundStake(_address,
                                  discriminator,
                                  authority,
                                  ifShares,
                                  lastWithdrawRequestShares,
                                  ifBase,
                                  lastValidTs,
                                  lastWithdrawRequestValue,
                                  lastWithdrawRequestTs,
                                  costBasis,
                                  marketIndex,
                                  padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    authority.write(_data, i);
    i += 32;
    putInt128LE(_data, i, ifShares);
    i += 16;
    putInt128LE(_data, i, lastWithdrawRequestShares);
    i += 16;
    putInt128LE(_data, i, ifBase);
    i += 16;
    putInt64LE(_data, i, lastValidTs);
    i += 8;
    putInt64LE(_data, i, lastWithdrawRequestValue);
    i += 8;
    putInt64LE(_data, i, lastWithdrawRequestTs);
    i += 8;
    putInt64LE(_data, i, costBasis);
    i += 8;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
