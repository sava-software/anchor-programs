package software.sava.anchor.programs.drift.vaults.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record FeeUpdate(PublicKey _address,
                        Discriminator discriminator,
                        long incomingUpdateTs,
                        long incomingManagementFee,
                        int incomingProfitShare,
                        int incomingHurdleRate,
                        BigInteger[] padding,
                        byte[] padding2) implements Borsh {

  public static final int BYTES = 200;
  public static final int PADDING_LEN = 10;
  public static final int PADDING_2_LEN = 8;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int INCOMING_UPDATE_TS_OFFSET = 8;
  public static final int INCOMING_MANAGEMENT_FEE_OFFSET = 16;
  public static final int INCOMING_PROFIT_SHARE_OFFSET = 24;
  public static final int INCOMING_HURDLE_RATE_OFFSET = 28;
  public static final int PADDING_OFFSET = 32;
  public static final int PADDING_2_OFFSET = 192;

  public static Filter createIncomingUpdateTsFilter(final long incomingUpdateTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, incomingUpdateTs);
    return Filter.createMemCompFilter(INCOMING_UPDATE_TS_OFFSET, _data);
  }

  public static Filter createIncomingManagementFeeFilter(final long incomingManagementFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, incomingManagementFee);
    return Filter.createMemCompFilter(INCOMING_MANAGEMENT_FEE_OFFSET, _data);
  }

  public static Filter createIncomingProfitShareFilter(final int incomingProfitShare) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, incomingProfitShare);
    return Filter.createMemCompFilter(INCOMING_PROFIT_SHARE_OFFSET, _data);
  }

  public static Filter createIncomingHurdleRateFilter(final int incomingHurdleRate) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, incomingHurdleRate);
    return Filter.createMemCompFilter(INCOMING_HURDLE_RATE_OFFSET, _data);
  }

  public static FeeUpdate read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static FeeUpdate read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static FeeUpdate read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], FeeUpdate> FACTORY = FeeUpdate::read;

  public static FeeUpdate read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var incomingUpdateTs = getInt64LE(_data, i);
    i += 8;
    final var incomingManagementFee = getInt64LE(_data, i);
    i += 8;
    final var incomingProfitShare = getInt32LE(_data, i);
    i += 4;
    final var incomingHurdleRate = getInt32LE(_data, i);
    i += 4;
    final var padding = new BigInteger[10];
    i += Borsh.read128Array(padding, _data, i);
    final var padding2 = new byte[8];
    Borsh.readArray(padding2, _data, i);
    return new FeeUpdate(_address,
                         discriminator,
                         incomingUpdateTs,
                         incomingManagementFee,
                         incomingProfitShare,
                         incomingHurdleRate,
                         padding,
                         padding2);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, incomingUpdateTs);
    i += 8;
    putInt64LE(_data, i, incomingManagementFee);
    i += 8;
    putInt32LE(_data, i, incomingProfitShare);
    i += 4;
    putInt32LE(_data, i, incomingHurdleRate);
    i += 4;
    i += Borsh.write128ArrayChecked(padding, 10, _data, i);
    i += Borsh.writeArrayChecked(padding2, 8, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
