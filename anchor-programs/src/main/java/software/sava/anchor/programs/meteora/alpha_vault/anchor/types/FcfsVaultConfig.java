package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record FcfsVaultConfig(PublicKey _address,
                              Discriminator discriminator,
                              long maxDepositingCap,
                              long startVestingDuration,
                              long endVestingDuration,
                              long depositingDurationUntilLastJoinPoint,
                              long individualDepositingCap,
                              long escrowFee,
                              int activationType,
                              byte[] padding) implements Borsh {

  public static final int BYTES = 232;
  public static final int PADDING_LEN = 175;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(99, 243, 252, 122, 160, 175, 130, 52);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int MAX_DEPOSITING_CAP_OFFSET = 8;
  public static final int START_VESTING_DURATION_OFFSET = 16;
  public static final int END_VESTING_DURATION_OFFSET = 24;
  public static final int DEPOSITING_DURATION_UNTIL_LAST_JOIN_POINT_OFFSET = 32;
  public static final int INDIVIDUAL_DEPOSITING_CAP_OFFSET = 40;
  public static final int ESCROW_FEE_OFFSET = 48;
  public static final int ACTIVATION_TYPE_OFFSET = 56;
  public static final int PADDING_OFFSET = 57;

  public static Filter createMaxDepositingCapFilter(final long maxDepositingCap) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxDepositingCap);
    return Filter.createMemCompFilter(MAX_DEPOSITING_CAP_OFFSET, _data);
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

  public static Filter createDepositingDurationUntilLastJoinPointFilter(final long depositingDurationUntilLastJoinPoint) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, depositingDurationUntilLastJoinPoint);
    return Filter.createMemCompFilter(DEPOSITING_DURATION_UNTIL_LAST_JOIN_POINT_OFFSET, _data);
  }

  public static Filter createIndividualDepositingCapFilter(final long individualDepositingCap) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, individualDepositingCap);
    return Filter.createMemCompFilter(INDIVIDUAL_DEPOSITING_CAP_OFFSET, _data);
  }

  public static Filter createEscrowFeeFilter(final long escrowFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, escrowFee);
    return Filter.createMemCompFilter(ESCROW_FEE_OFFSET, _data);
  }

  public static Filter createActivationTypeFilter(final int activationType) {
    return Filter.createMemCompFilter(ACTIVATION_TYPE_OFFSET, new byte[]{(byte) activationType});
  }

  public static FcfsVaultConfig read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static FcfsVaultConfig read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static FcfsVaultConfig read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], FcfsVaultConfig> FACTORY = FcfsVaultConfig::read;

  public static FcfsVaultConfig read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var maxDepositingCap = getInt64LE(_data, i);
    i += 8;
    final var startVestingDuration = getInt64LE(_data, i);
    i += 8;
    final var endVestingDuration = getInt64LE(_data, i);
    i += 8;
    final var depositingDurationUntilLastJoinPoint = getInt64LE(_data, i);
    i += 8;
    final var individualDepositingCap = getInt64LE(_data, i);
    i += 8;
    final var escrowFee = getInt64LE(_data, i);
    i += 8;
    final var activationType = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[175];
    Borsh.readArray(padding, _data, i);
    return new FcfsVaultConfig(_address,
                               discriminator,
                               maxDepositingCap,
                               startVestingDuration,
                               endVestingDuration,
                               depositingDurationUntilLastJoinPoint,
                               individualDepositingCap,
                               escrowFee,
                               activationType,
                               padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, maxDepositingCap);
    i += 8;
    putInt64LE(_data, i, startVestingDuration);
    i += 8;
    putInt64LE(_data, i, endVestingDuration);
    i += 8;
    putInt64LE(_data, i, depositingDurationUntilLastJoinPoint);
    i += 8;
    putInt64LE(_data, i, individualDepositingCap);
    i += 8;
    putInt64LE(_data, i, escrowFee);
    i += 8;
    _data[i] = (byte) activationType;
    ++i;
    i += Borsh.writeArrayChecked(padding, 175, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
