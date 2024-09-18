package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ValidatorHistoryEntry(long activatedStakeLamports,
                                    int epoch,
                                    int mevCommission,
                                    int epochCredits,
                                    int commission,
                                    int clientType,
                                    ClientVersion version,
                                    byte[] ip,
                                    int padding0,
                                    int isSuperminority,
                                    int rank,
                                    long voteAccountLastUpdateSlot,
                                    int mevEarned,
                                    byte[] padding1) implements Borsh {

  public static final int BYTES = 128;

  public static ValidatorHistoryEntry read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var activatedStakeLamports = getInt64LE(_data, i);
    i += 8;
    final var epoch = getInt16LE(_data, i);
    i += 2;
    final var mevCommission = getInt16LE(_data, i);
    i += 2;
    final var epochCredits = getInt32LE(_data, i);
    i += 4;
    final var commission = _data[i] & 0xFF;
    ++i;
    final var clientType = _data[i] & 0xFF;
    ++i;
    final var version = ClientVersion.read(_data, i);
    i += Borsh.len(version);
    final var ip = new byte[4];
    i += Borsh.readArray(ip, _data, i);
    final var padding0 = _data[i] & 0xFF;
    ++i;
    final var isSuperminority = _data[i] & 0xFF;
    ++i;
    final var rank = getInt32LE(_data, i);
    i += 4;
    final var voteAccountLastUpdateSlot = getInt64LE(_data, i);
    i += 8;
    final var mevEarned = getInt32LE(_data, i);
    i += 4;
    final var padding1 = new byte[84];
    Borsh.readArray(padding1, _data, i);
    return new ValidatorHistoryEntry(activatedStakeLamports,
                                     epoch,
                                     mevCommission,
                                     epochCredits,
                                     commission,
                                     clientType,
                                     version,
                                     ip,
                                     padding0,
                                     isSuperminority,
                                     rank,
                                     voteAccountLastUpdateSlot,
                                     mevEarned,
                                     padding1);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, activatedStakeLamports);
    i += 8;
    putInt16LE(_data, i, epoch);
    i += 2;
    putInt16LE(_data, i, mevCommission);
    i += 2;
    putInt32LE(_data, i, epochCredits);
    i += 4;
    _data[i] = (byte) commission;
    ++i;
    _data[i] = (byte) clientType;
    ++i;
    i += Borsh.write(version, _data, i);
    i += Borsh.writeArray(ip, _data, i);
    _data[i] = (byte) padding0;
    ++i;
    _data[i] = (byte) isSuperminority;
    ++i;
    putInt32LE(_data, i, rank);
    i += 4;
    putInt64LE(_data, i, voteAccountLastUpdateSlot);
    i += 8;
    putInt32LE(_data, i, mevEarned);
    i += 4;
    i += Borsh.writeArray(padding1, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
