package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InstantUnstakeDetails(// Latest epoch credits
                                    long epochCreditsLatest,
                                    // Latest vote account update slot
                                    long voteAccountLastUpdateSlot,
                                    // Latest total blocks
                                    int totalBlocksLatest,
                                    // Cluster history slot index
                                    long clusterHistorySlotIndex,
                                    // Commission value
                                    int commission,
                                    // MEV commission value
                                    int mevCommission) implements Borsh {

  public static final int BYTES = 31;

  public static InstantUnstakeDetails read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var epochCreditsLatest = getInt64LE(_data, i);
    i += 8;
    final var voteAccountLastUpdateSlot = getInt64LE(_data, i);
    i += 8;
    final var totalBlocksLatest = getInt32LE(_data, i);
    i += 4;
    final var clusterHistorySlotIndex = getInt64LE(_data, i);
    i += 8;
    final var commission = _data[i] & 0xFF;
    ++i;
    final var mevCommission = getInt16LE(_data, i);
    return new InstantUnstakeDetails(epochCreditsLatest,
                                     voteAccountLastUpdateSlot,
                                     totalBlocksLatest,
                                     clusterHistorySlotIndex,
                                     commission,
                                     mevCommission);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, epochCreditsLatest);
    i += 8;
    putInt64LE(_data, i, voteAccountLastUpdateSlot);
    i += 8;
    putInt32LE(_data, i, totalBlocksLatest);
    i += 4;
    putInt64LE(_data, i, clusterHistorySlotIndex);
    i += 8;
    _data[i] = (byte) commission;
    ++i;
    putInt16LE(_data, i, mevCommission);
    i += 2;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
