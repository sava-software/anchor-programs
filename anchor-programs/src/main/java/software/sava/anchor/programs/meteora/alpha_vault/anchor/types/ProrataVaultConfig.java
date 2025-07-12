package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record ProrataVaultConfig(PublicKey _address,
                                 Discriminator discriminator,
                                 long maxBuyingCap,
                                 long startVestingDuration,
                                 long endVestingDuration,
                                 long escrowFee,
                                 int activationType,
                                 byte[] padding) implements Borsh {

  public static final int BYTES = 232;
  public static final int PADDING_LEN = 191;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(93, 214, 205, 104, 119, 9, 51, 152);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int MAX_BUYING_CAP_OFFSET = 8;
  public static final int START_VESTING_DURATION_OFFSET = 16;
  public static final int END_VESTING_DURATION_OFFSET = 24;
  public static final int ESCROW_FEE_OFFSET = 32;
  public static final int ACTIVATION_TYPE_OFFSET = 40;
  public static final int PADDING_OFFSET = 41;

  public static Filter createMaxBuyingCapFilter(final long maxBuyingCap) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxBuyingCap);
    return Filter.createMemCompFilter(MAX_BUYING_CAP_OFFSET, _data);
  }

  public static Filter createStartVestingDurationFilter(final long startVestingDuration) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, startVestingDuration);
    return Filter.createMemCompFilter(START_VESTING_DURATION_OFFSET, _data);
  }

  public static Filter createEndVestingDurationFilter(final long endVestingDuration) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, endVestingDuration);
    return Filter.createMemCompFilter(END_VESTING_DURATION_OFFSET, _data);
  }

  public static Filter createEscrowFeeFilter(final long escrowFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, escrowFee);
    return Filter.createMemCompFilter(ESCROW_FEE_OFFSET, _data);
  }

  public static Filter createActivationTypeFilter(final int activationType) {
    return Filter.createMemCompFilter(ACTIVATION_TYPE_OFFSET, new byte[]{(byte) activationType});
  }

  public static ProrataVaultConfig read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static ProrataVaultConfig read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static ProrataVaultConfig read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], ProrataVaultConfig> FACTORY = ProrataVaultConfig::read;

  public static ProrataVaultConfig read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var maxBuyingCap = getInt64LE(_data, i);
    i += 8;
    final var startVestingDuration = getInt64LE(_data, i);
    i += 8;
    final var endVestingDuration = getInt64LE(_data, i);
    i += 8;
    final var escrowFee = getInt64LE(_data, i);
    i += 8;
    final var activationType = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[191];
    Borsh.readArray(padding, _data, i);
    return new ProrataVaultConfig(_address,
                                  discriminator,
                                  maxBuyingCap,
                                  startVestingDuration,
                                  endVestingDuration,
                                  escrowFee,
                                  activationType,
                                  padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, maxBuyingCap);
    i += 8;
    putInt64LE(_data, i, startVestingDuration);
    i += 8;
    putInt64LE(_data, i, endVestingDuration);
    i += 8;
    putInt64LE(_data, i, escrowFee);
    i += 8;
    _data[i] = (byte) activationType;
    ++i;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
