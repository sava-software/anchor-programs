package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record StakeSystem(List stakeList,
                          long delayedUnstakeCoolingDown,
                          int stakeDepositBumpSeed,
                          int stakeWithdrawBumpSeed,
                          // set by admin, how much slots before the end of the epoch, stake-delta can start
                          long slotsForStakeDelta,
                          // Marks the start of stake-delta operations, meaning that if somebody starts a delayed-unstake ticket
                          // after this var is set with epoch_num the ticket will have epoch_created = current_epoch+1
                          // (the user must wait one more epoch, because their unstake-delta will be execute in this epoch)
                          long lastStakeDeltaEpoch,
                          long minStake,
                          // can be set by validator-manager-auth to allow a second run of stake-delta to stake late stakers in the last minute of the epoch
                          // so we maximize user's rewards
                          int extraStakeDeltaRuns) implements Borsh {

  public static final int BYTES = 114;

  public static StakeSystem read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var stakeList = List.read(_data, i);
    i += Borsh.len(stakeList);
    final var delayedUnstakeCoolingDown = getInt64LE(_data, i);
    i += 8;
    final var stakeDepositBumpSeed = _data[i] & 0xFF;
    ++i;
    final var stakeWithdrawBumpSeed = _data[i] & 0xFF;
    ++i;
    final var slotsForStakeDelta = getInt64LE(_data, i);
    i += 8;
    final var lastStakeDeltaEpoch = getInt64LE(_data, i);
    i += 8;
    final var minStake = getInt64LE(_data, i);
    i += 8;
    final var extraStakeDeltaRuns = getInt32LE(_data, i);
    return new StakeSystem(stakeList,
                           delayedUnstakeCoolingDown,
                           stakeDepositBumpSeed,
                           stakeWithdrawBumpSeed,
                           slotsForStakeDelta,
                           lastStakeDeltaEpoch,
                           minStake,
                           extraStakeDeltaRuns);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(stakeList, _data, i);
    putInt64LE(_data, i, delayedUnstakeCoolingDown);
    i += 8;
    _data[i] = (byte) stakeDepositBumpSeed;
    ++i;
    _data[i] = (byte) stakeWithdrawBumpSeed;
    ++i;
    putInt64LE(_data, i, slotsForStakeDelta);
    i += 8;
    putInt64LE(_data, i, lastStakeDeltaEpoch);
    i += 8;
    putInt64LE(_data, i, minStake);
    i += 8;
    putInt32LE(_data, i, extraStakeDeltaRuns);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
