package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record FcfsVaultConfig(PublicKey _address,
                              Discriminator discriminator,
                              long maxDepositingCap,
                              long startVestingDuration,
                              long endVestingDuration,
                              long depositingSlotDurationUntilLastJoinSlot,
                              long individualDepositingCap,
                              long escrowFee,
                              byte[] padding) implements Borsh {

  public static final int BYTES = 232;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int MAX_DEPOSITING_CAP_OFFSET = 8;
  public static final int START_VESTING_DURATION_OFFSET = 16;
  public static final int END_VESTING_DURATION_OFFSET = 24;
  public static final int DEPOSITING_SLOT_DURATION_UNTIL_LAST_JOIN_SLOT_OFFSET = 32;
  public static final int INDIVIDUAL_DEPOSITING_CAP_OFFSET = 40;
  public static final int ESCROW_FEE_OFFSET = 48;
  public static final int PADDING_OFFSET = 56;

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

  public static Filter createDepositingSlotDurationUntilLastJoinSlotFilter(final long depositingSlotDurationUntilLastJoinSlot) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, depositingSlotDurationUntilLastJoinSlot);
    return Filter.createMemCompFilter(DEPOSITING_SLOT_DURATION_UNTIL_LAST_JOIN_SLOT_OFFSET, _data);
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

  public static FcfsVaultConfig read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static FcfsVaultConfig read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], FcfsVaultConfig> FACTORY = FcfsVaultConfig::read;

  public static FcfsVaultConfig read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var maxDepositingCap = getInt64LE(_data, i);
    i += 8;
    final var startVestingDuration = getInt64LE(_data, i);
    i += 8;
    final var endVestingDuration = getInt64LE(_data, i);
    i += 8;
    final var depositingSlotDurationUntilLastJoinSlot = getInt64LE(_data, i);
    i += 8;
    final var individualDepositingCap = getInt64LE(_data, i);
    i += 8;
    final var escrowFee = getInt64LE(_data, i);
    i += 8;
    final var padding = new byte[176];
    Borsh.readArray(padding, _data, i);
    return new FcfsVaultConfig(_address,
                               discriminator,
                               maxDepositingCap,
                               startVestingDuration,
                               endVestingDuration,
                               depositingSlotDurationUntilLastJoinSlot,
                               individualDepositingCap,
                               escrowFee,
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
    putInt64LE(_data, i, depositingSlotDurationUntilLastJoinSlot);
    i += 8;
    putInt64LE(_data, i, individualDepositingCap);
    i += 8;
    putInt64LE(_data, i, escrowFee);
    i += 8;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
