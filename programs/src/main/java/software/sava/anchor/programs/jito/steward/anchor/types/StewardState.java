package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Tracks state of the stake pool.
// Follow state transitions here:
// https://github.com/jito-foundation/stakenet/blob/master/programs/steward/state-machine-diagram.png
public record StewardState(// Current state of the Steward
                           StewardStateEnum stateTag,
                           // Internal lamport balance of each validator, used to track stake deposits that need to be unstaked,
                           // so not always equal to the stake account balance.
                           long[] validatorLamportBalances,
                           // Overall score of validator, used to determine delegates and order for delegation.
                           int[] scores,
                           // Indices of validators, sorted by score descending
                           short[] sortedScoreIndices,
                           // Yield component of the score. Used as secondary priority, to determine order for unstaking.
                           int[] yieldScores,
                           // Indices of validators, sorted by yield score descending
                           short[] sortedYieldScoreIndices,
                           // Target share of pool represented as a proportion, indexed by spl_stake_pool::ValidatorList index
                           Delegation[] delegations,
                           // Each bit represents a validator, true if validator should be unstaked
                           BitMask instantUnstake,
                           // Tracks progress of states that require one instruction per validator
                           BitMask progress,
                           // Marks a validator for immediate removal after `remove_validator_from_pool` has been called on the stake pool
                           // This happens when a validator is able to be removed within the same epoch as it was marked
                           BitMask validatorsForImmediateRemoval,
                           // Marks a validator for removal after `remove_validator_from_pool` has been called on the stake pool
                           // This is cleaned up in the next epoch
                           BitMask validatorsToRemove,
                           // Slot of the first ComputeScores instruction in the current cycle
                           long startComputingScoresSlot,
                           // Internal current epoch, for tracking when epoch has changed
                           long currentEpoch,
                           // Next cycle start
                           long nextCycleEpoch,
                           // Number of validators in the stake pool, used to determine the number of validators to be scored.
                           // Updated at the start of each cycle and when validators are removed.
                           long numPoolValidators,
                           // Total lamports that have been due to scoring this cycle
                           long scoringUnstakeTotal,
                           // Total lamports that have been due to instant unstaking this cycle
                           long instantUnstakeTotal,
                           // Total lamports that have been due to stake deposits this cycle
                           long stakeDepositUnstakeTotal,
                           // Flags to track state transitions and operations
                           int statusFlags,
                           // Number of validators added to the pool in the current cycle
                           int validatorsAdded,
                           // Future state and #[repr(C)] alignment
                           byte[] padding0) implements Borsh {

  public static final int BYTES = 182593;

  public static StewardState read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var stateTag = StewardStateEnum.read(_data, i);
    i += Borsh.len(stateTag);
    final var validatorLamportBalances = new long[5000];
    i += Borsh.readArray(validatorLamportBalances, _data, i);
    final var scores = new int[5000];
    i += Borsh.readArray(scores, _data, i);
    final var sortedScoreIndices = new short[5000];
    i += Borsh.readArray(sortedScoreIndices, _data, i);
    final var yieldScores = new int[5000];
    i += Borsh.readArray(yieldScores, _data, i);
    final var sortedYieldScoreIndices = new short[5000];
    i += Borsh.readArray(sortedYieldScoreIndices, _data, i);
    final var delegations = new Delegation[5000];
    i += Borsh.readArray(delegations, Delegation::read, _data, i);
    final var instantUnstake = BitMask.read(_data, i);
    i += Borsh.len(instantUnstake);
    final var progress = BitMask.read(_data, i);
    i += Borsh.len(progress);
    final var validatorsForImmediateRemoval = BitMask.read(_data, i);
    i += Borsh.len(validatorsForImmediateRemoval);
    final var validatorsToRemove = BitMask.read(_data, i);
    i += Borsh.len(validatorsToRemove);
    final var startComputingScoresSlot = getInt64LE(_data, i);
    i += 8;
    final var currentEpoch = getInt64LE(_data, i);
    i += 8;
    final var nextCycleEpoch = getInt64LE(_data, i);
    i += 8;
    final var numPoolValidators = getInt64LE(_data, i);
    i += 8;
    final var scoringUnstakeTotal = getInt64LE(_data, i);
    i += 8;
    final var instantUnstakeTotal = getInt64LE(_data, i);
    i += 8;
    final var stakeDepositUnstakeTotal = getInt64LE(_data, i);
    i += 8;
    final var statusFlags = getInt32LE(_data, i);
    i += 4;
    final var validatorsAdded = getInt16LE(_data, i);
    i += 2;
    final var padding0 = new byte[40002];
    Borsh.readArray(padding0, _data, i);
    return new StewardState(stateTag,
                            validatorLamportBalances,
                            scores,
                            sortedScoreIndices,
                            yieldScores,
                            sortedYieldScoreIndices,
                            delegations,
                            instantUnstake,
                            progress,
                            validatorsForImmediateRemoval,
                            validatorsToRemove,
                            startComputingScoresSlot,
                            currentEpoch,
                            nextCycleEpoch,
                            numPoolValidators,
                            scoringUnstakeTotal,
                            instantUnstakeTotal,
                            stakeDepositUnstakeTotal,
                            statusFlags,
                            validatorsAdded,
                            padding0);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(stateTag, _data, i);
    i += Borsh.writeArray(validatorLamportBalances, _data, i);
    i += Borsh.writeArray(scores, _data, i);
    i += Borsh.writeArray(sortedScoreIndices, _data, i);
    i += Borsh.writeArray(yieldScores, _data, i);
    i += Borsh.writeArray(sortedYieldScoreIndices, _data, i);
    i += Borsh.writeArray(delegations, _data, i);
    i += Borsh.write(instantUnstake, _data, i);
    i += Borsh.write(progress, _data, i);
    i += Borsh.write(validatorsForImmediateRemoval, _data, i);
    i += Borsh.write(validatorsToRemove, _data, i);
    putInt64LE(_data, i, startComputingScoresSlot);
    i += 8;
    putInt64LE(_data, i, currentEpoch);
    i += 8;
    putInt64LE(_data, i, nextCycleEpoch);
    i += 8;
    putInt64LE(_data, i, numPoolValidators);
    i += 8;
    putInt64LE(_data, i, scoringUnstakeTotal);
    i += 8;
    putInt64LE(_data, i, instantUnstakeTotal);
    i += 8;
    putInt64LE(_data, i, stakeDepositUnstakeTotal);
    i += 8;
    putInt32LE(_data, i, statusFlags);
    i += 4;
    putInt16LE(_data, i, validatorsAdded);
    i += 2;
    i += Borsh.writeArray(padding0, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
