package software.sava.anchor.programs.jito.tip_router.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record EpochMarker(PublicKey _address,
                          Discriminator discriminator,
                          PublicKey ncn,
                          long epoch,
                          long slotClosed) implements Borsh {

  public static final int BYTES = 56;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int NCN_OFFSET = 8;
  public static final int EPOCH_OFFSET = 40;
  public static final int SLOT_CLOSED_OFFSET = 48;

  public static Filter createNcnFilter(final PublicKey ncn) {
    return Filter.createMemCompFilter(NCN_OFFSET, ncn);
  }

  public static Filter createEpochFilter(final long epoch) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, epoch);
    return Filter.createMemCompFilter(EPOCH_OFFSET, _data);
  }

  public static Filter createSlotClosedFilter(final long slotClosed) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, slotClosed);
    return Filter.createMemCompFilter(SLOT_CLOSED_OFFSET, _data);
  }

  public static EpochMarker read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static EpochMarker read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static EpochMarker read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], EpochMarker> FACTORY = EpochMarker::read;

  public static EpochMarker read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var ncn = readPubKey(_data, i);
    i += 32;
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var slotClosed = getInt64LE(_data, i);
    return new EpochMarker(_address, discriminator, ncn, epoch, slotClosed);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    ncn.write(_data, i);
    i += 32;
    putInt64LE(_data, i, epoch);
    i += 8;
    putInt64LE(_data, i, slotClosed);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
