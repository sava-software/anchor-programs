package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record UpdateFcfsVaultParams(long maxDepositingCap,
                                    long depositingSlot,
                                    long individualDepositingCap,
                                    long startVestingSlot,
                                    long endVestingSlot) implements Borsh {

  public static final int BYTES = 40;

  public static UpdateFcfsVaultParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var maxDepositingCap = getInt64LE(_data, i);
    i += 8;
    final var depositingSlot = getInt64LE(_data, i);
    i += 8;
    final var individualDepositingCap = getInt64LE(_data, i);
    i += 8;
    final var startVestingSlot = getInt64LE(_data, i);
    i += 8;
    final var endVestingSlot = getInt64LE(_data, i);
    return new UpdateFcfsVaultParams(maxDepositingCap,
                                     depositingSlot,
                                     individualDepositingCap,
                                     startVestingSlot,
                                     endVestingSlot);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, maxDepositingCap);
    i += 8;
    putInt64LE(_data, i, depositingSlot);
    i += 8;
    putInt64LE(_data, i, individualDepositingCap);
    i += 8;
    putInt64LE(_data, i, startVestingSlot);
    i += 8;
    putInt64LE(_data, i, endVestingSlot);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
