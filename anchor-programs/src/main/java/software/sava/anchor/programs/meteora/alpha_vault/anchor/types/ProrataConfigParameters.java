package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ProrataConfigParameters(long maxBuyingCap,
                                      long startVestingDuration,
                                      long endVestingDuration,
                                      long escrowFee,
                                      int activationType,
                                      long index) implements Borsh {

  public static final int BYTES = 41;

  public static ProrataConfigParameters read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
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
    final var index = getInt64LE(_data, i);
    return new ProrataConfigParameters(maxBuyingCap,
                                       startVestingDuration,
                                       endVestingDuration,
                                       escrowFee,
                                       activationType,
                                       index);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
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
    putInt64LE(_data, i, index);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
