package software.sava.anchor.programs.drift.vaults.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record FeeUpdateRecord(long ts,
                              FeeUpdateAction action,
                              long timelockEndTs,
                              PublicKey vault,
                              long oldManagementFee,
                              int oldProfitShare,
                              int oldHurdleRate,
                              long newManagementFee,
                              int newProfitShare,
                              int newHurdleRate) implements Borsh {

  public static final int BYTES = 81;

  public static FeeUpdateRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var action = FeeUpdateAction.read(_data, i);
    i += Borsh.len(action);
    final var timelockEndTs = getInt64LE(_data, i);
    i += 8;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var oldManagementFee = getInt64LE(_data, i);
    i += 8;
    final var oldProfitShare = getInt32LE(_data, i);
    i += 4;
    final var oldHurdleRate = getInt32LE(_data, i);
    i += 4;
    final var newManagementFee = getInt64LE(_data, i);
    i += 8;
    final var newProfitShare = getInt32LE(_data, i);
    i += 4;
    final var newHurdleRate = getInt32LE(_data, i);
    return new FeeUpdateRecord(ts,
                               action,
                               timelockEndTs,
                               vault,
                               oldManagementFee,
                               oldProfitShare,
                               oldHurdleRate,
                               newManagementFee,
                               newProfitShare,
                               newHurdleRate);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    i += Borsh.write(action, _data, i);
    putInt64LE(_data, i, timelockEndTs);
    i += 8;
    vault.write(_data, i);
    i += 32;
    putInt64LE(_data, i, oldManagementFee);
    i += 8;
    putInt32LE(_data, i, oldProfitShare);
    i += 4;
    putInt32LE(_data, i, oldHurdleRate);
    i += 4;
    putInt64LE(_data, i, newManagementFee);
    i += 8;
    putInt32LE(_data, i, newProfitShare);
    i += 4;
    putInt32LE(_data, i, newHurdleRate);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
