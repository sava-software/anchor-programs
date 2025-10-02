package software.sava.anchor.programs.drift.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record RevenueShareEscrow(PublicKey _address,
                                 Discriminator discriminator,
                                 // the owner of this account, a user
                                 PublicKey authority,
                                 PublicKey referrer,
                                 int referrerBoostExpireTs,
                                 int referrerRewardOffset,
                                 int refereeFeeNumeratorOffset,
                                 int referrerBoostNumerator,
                                 byte[] reservedFixed,
                                 int padding0,
                                 RevenueShareOrder[] orders,
                                 int padding1,
                                 BuilderInfo[] approvedBuilders) implements Borsh {

  public static final int RESERVED_FIXED_LEN = 17;
  public static final int AUTHORITY_OFFSET = 8;
  public static final int REFERRER_OFFSET = 40;
  public static final int REFERRER_BOOST_EXPIRE_TS_OFFSET = 72;
  public static final int REFERRER_REWARD_OFFSET_OFFSET = 76;
  public static final int REFEREE_FEE_NUMERATOR_OFFSET_OFFSET = 77;
  public static final int REFERRER_BOOST_NUMERATOR_OFFSET = 78;
  public static final int RESERVED_FIXED_OFFSET = 79;
  public static final int PADDING_0_OFFSET = 96;
  public static final int ORDERS_OFFSET = 100;

  public static Filter createAuthorityFilter(final PublicKey authority) {
    return Filter.createMemCompFilter(AUTHORITY_OFFSET, authority);
  }

  public static Filter createReferrerFilter(final PublicKey referrer) {
    return Filter.createMemCompFilter(REFERRER_OFFSET, referrer);
  }

  public static Filter createReferrerBoostExpireTsFilter(final int referrerBoostExpireTs) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, referrerBoostExpireTs);
    return Filter.createMemCompFilter(REFERRER_BOOST_EXPIRE_TS_OFFSET, _data);
  }

  public static Filter createReferrerRewardOffsetFilter(final int referrerRewardOffset) {
    return Filter.createMemCompFilter(REFERRER_REWARD_OFFSET_OFFSET, new byte[]{(byte) referrerRewardOffset});
  }

  public static Filter createRefereeFeeNumeratorOffsetFilter(final int refereeFeeNumeratorOffset) {
    return Filter.createMemCompFilter(REFEREE_FEE_NUMERATOR_OFFSET_OFFSET, new byte[]{(byte) refereeFeeNumeratorOffset});
  }

  public static Filter createReferrerBoostNumeratorFilter(final int referrerBoostNumerator) {
    return Filter.createMemCompFilter(REFERRER_BOOST_NUMERATOR_OFFSET, new byte[]{(byte) referrerBoostNumerator});
  }

  public static Filter createPadding0Filter(final int padding0) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, padding0);
    return Filter.createMemCompFilter(PADDING_0_OFFSET, _data);
  }

  public static RevenueShareEscrow read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static RevenueShareEscrow read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static RevenueShareEscrow read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], RevenueShareEscrow> FACTORY = RevenueShareEscrow::read;

  public static RevenueShareEscrow read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var authority = readPubKey(_data, i);
    i += 32;
    final var referrer = readPubKey(_data, i);
    i += 32;
    final var referrerBoostExpireTs = getInt32LE(_data, i);
    i += 4;
    final var referrerRewardOffset = _data[i];
    ++i;
    final var refereeFeeNumeratorOffset = _data[i];
    ++i;
    final var referrerBoostNumerator = _data[i];
    ++i;
    final var reservedFixed = new byte[17];
    i += Borsh.readArray(reservedFixed, _data, i);
    final var padding0 = getInt32LE(_data, i);
    i += 4;
    final var orders = Borsh.readVector(RevenueShareOrder.class, RevenueShareOrder::read, _data, i);
    i += Borsh.lenVector(orders);
    final var padding1 = getInt32LE(_data, i);
    i += 4;
    final var approvedBuilders = Borsh.readVector(BuilderInfo.class, BuilderInfo::read, _data, i);
    return new RevenueShareEscrow(_address,
                                  discriminator,
                                  authority,
                                  referrer,
                                  referrerBoostExpireTs,
                                  referrerRewardOffset,
                                  refereeFeeNumeratorOffset,
                                  referrerBoostNumerator,
                                  reservedFixed,
                                  padding0,
                                  orders,
                                  padding1,
                                  approvedBuilders);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    authority.write(_data, i);
    i += 32;
    referrer.write(_data, i);
    i += 32;
    putInt32LE(_data, i, referrerBoostExpireTs);
    i += 4;
    _data[i] = (byte) referrerRewardOffset;
    ++i;
    _data[i] = (byte) refereeFeeNumeratorOffset;
    ++i;
    _data[i] = (byte) referrerBoostNumerator;
    ++i;
    i += Borsh.writeArrayChecked(reservedFixed, 17, _data, i);
    putInt32LE(_data, i, padding0);
    i += 4;
    i += Borsh.writeVector(orders, _data, i);
    putInt32LE(_data, i, padding1);
    i += 4;
    i += Borsh.writeVector(approvedBuilders, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32
         + 32
         + 4
         + 1
         + 1
         + 1
         + Borsh.lenArray(reservedFixed)
         + 4
         + Borsh.lenVector(orders)
         + 4
         + Borsh.lenVector(approvedBuilders);
  }
}
