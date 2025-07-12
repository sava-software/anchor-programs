package software.sava.anchor.programs.drift.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record HighLeverageModeConfig(PublicKey _address,
                                     Discriminator discriminator,
                                     int maxUsers,
                                     int currentUsers,
                                     int reduceOnly,
                                     byte[] padding) implements Borsh {

  public static final int BYTES = 48;
  public static final int PADDING_LEN = 31;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int MAX_USERS_OFFSET = 8;
  public static final int CURRENT_USERS_OFFSET = 12;
  public static final int REDUCE_ONLY_OFFSET = 16;
  public static final int PADDING_OFFSET = 17;

  public static Filter createMaxUsersFilter(final int maxUsers) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, maxUsers);
    return Filter.createMemCompFilter(MAX_USERS_OFFSET, _data);
  }

  public static Filter createCurrentUsersFilter(final int currentUsers) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, currentUsers);
    return Filter.createMemCompFilter(CURRENT_USERS_OFFSET, _data);
  }

  public static Filter createReduceOnlyFilter(final int reduceOnly) {
    return Filter.createMemCompFilter(REDUCE_ONLY_OFFSET, new byte[]{(byte) reduceOnly});
  }

  public static HighLeverageModeConfig read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static HighLeverageModeConfig read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static HighLeverageModeConfig read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], HighLeverageModeConfig> FACTORY = HighLeverageModeConfig::read;

  public static HighLeverageModeConfig read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var maxUsers = getInt32LE(_data, i);
    i += 4;
    final var currentUsers = getInt32LE(_data, i);
    i += 4;
    final var reduceOnly = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[31];
    Borsh.readArray(padding, _data, i);
    return new HighLeverageModeConfig(_address,
                                      discriminator,
                                      maxUsers,
                                      currentUsers,
                                      reduceOnly,
                                      padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt32LE(_data, i, maxUsers);
    i += 4;
    putInt32LE(_data, i, currentUsers);
    i += 4;
    _data[i] = (byte) reduceOnly;
    ++i;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
