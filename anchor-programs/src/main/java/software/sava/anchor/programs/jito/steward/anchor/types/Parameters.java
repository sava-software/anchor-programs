package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getFloat64LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putFloat64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Parameters(// Number of epochs to consider for MEV commission
                         int mevCommissionRange,
                         // Number of epochs to consider for epoch credits
                         int epochCreditsRange,
                         // Number of epochs to consider for commission
                         int commissionRange,
                         // Highest MEV commission rate allowed in bps
                         int mevCommissionBpsThreshold,
                         // Proportion of delinquent slots to total slots to trigger delinquency measurement in scoring
                         double scoringDelinquencyThresholdRatio,
                         // Proportion of delinquent slots to total slots to trigger instant unstake
                         double instantUnstakeDelinquencyThresholdRatio,
                         // Highest commission rate allowed in commission_range epochs, in percent
                         int commissionThreshold,
                         // Highest commission rate allowed in tracked history
                         int historicalCommissionThreshold,
                         // Required so that the struct is 8-byte aligned
                         // https://doc.rust-lang.org/reference/type-layout.html#reprc-structs
                         byte[] padding0,
                         // Number of validators to delegate to
                         int numDelegationValidators,
                         // Maximum amount of the pool to be unstaked in a cycle for scoring (in basis points)
                         int scoringUnstakeCapBps,
                         int instantUnstakeCapBps,
                         // Maximum amount of the pool to be unstaked in a cycle from stake deposits (in basis points)
                         int stakeDepositUnstakeCapBps,
                         // Number of slots that scoring must be completed in
                         long computeScoreSlotRange,
                         // Progress in epoch before instant unstake is allowed
                         double instantUnstakeEpochProgress,
                         // Validator history copy_vote_account and Cluster History must be updated past this epoch progress before calculating instant unstake
                         double instantUnstakeInputsEpochProgress,
                         // Number of epochs a given validator set will be delegated to before recomputing scores
                         long numEpochsBetweenScoring,
                         // Minimum stake required to be added to pool ValidatorList and eligible for delegation
                         long minimumStakeLamports,
                         // Minimum epochs voting required to be in the pool ValidatorList and eligible for delegation
                         long minimumVotingEpochs,
                         long[] padding1) implements Borsh {

  public static final int BYTES = 352;

  public static Parameters read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var mevCommissionRange = getInt16LE(_data, i);
    i += 2;
    final var epochCreditsRange = getInt16LE(_data, i);
    i += 2;
    final var commissionRange = getInt16LE(_data, i);
    i += 2;
    final var mevCommissionBpsThreshold = getInt16LE(_data, i);
    i += 2;
    final var scoringDelinquencyThresholdRatio = getFloat64LE(_data, i);
    i += 8;
    final var instantUnstakeDelinquencyThresholdRatio = getFloat64LE(_data, i);
    i += 8;
    final var commissionThreshold = _data[i] & 0xFF;
    ++i;
    final var historicalCommissionThreshold = _data[i] & 0xFF;
    ++i;
    final var padding0 = new byte[6];
    i += Borsh.readArray(padding0, _data, i);
    final var numDelegationValidators = getInt32LE(_data, i);
    i += 4;
    final var scoringUnstakeCapBps = getInt32LE(_data, i);
    i += 4;
    final var instantUnstakeCapBps = getInt32LE(_data, i);
    i += 4;
    final var stakeDepositUnstakeCapBps = getInt32LE(_data, i);
    i += 4;
    final var computeScoreSlotRange = getInt64LE(_data, i);
    i += 8;
    final var instantUnstakeEpochProgress = getFloat64LE(_data, i);
    i += 8;
    final var instantUnstakeInputsEpochProgress = getFloat64LE(_data, i);
    i += 8;
    final var numEpochsBetweenScoring = getInt64LE(_data, i);
    i += 8;
    final var minimumStakeLamports = getInt64LE(_data, i);
    i += 8;
    final var minimumVotingEpochs = getInt64LE(_data, i);
    i += 8;
    final var padding1 = new long[32];
    Borsh.readArray(padding1, _data, i);
    return new Parameters(mevCommissionRange,
                          epochCreditsRange,
                          commissionRange,
                          mevCommissionBpsThreshold,
                          scoringDelinquencyThresholdRatio,
                          instantUnstakeDelinquencyThresholdRatio,
                          commissionThreshold,
                          historicalCommissionThreshold,
                          padding0,
                          numDelegationValidators,
                          scoringUnstakeCapBps,
                          instantUnstakeCapBps,
                          stakeDepositUnstakeCapBps,
                          computeScoreSlotRange,
                          instantUnstakeEpochProgress,
                          instantUnstakeInputsEpochProgress,
                          numEpochsBetweenScoring,
                          minimumStakeLamports,
                          minimumVotingEpochs,
                          padding1);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, mevCommissionRange);
    i += 2;
    putInt16LE(_data, i, epochCreditsRange);
    i += 2;
    putInt16LE(_data, i, commissionRange);
    i += 2;
    putInt16LE(_data, i, mevCommissionBpsThreshold);
    i += 2;
    putFloat64LE(_data, i, scoringDelinquencyThresholdRatio);
    i += 8;
    putFloat64LE(_data, i, instantUnstakeDelinquencyThresholdRatio);
    i += 8;
    _data[i] = (byte) commissionThreshold;
    ++i;
    _data[i] = (byte) historicalCommissionThreshold;
    ++i;
    i += Borsh.writeArray(padding0, _data, i);
    putInt32LE(_data, i, numDelegationValidators);
    i += 4;
    putInt32LE(_data, i, scoringUnstakeCapBps);
    i += 4;
    putInt32LE(_data, i, instantUnstakeCapBps);
    i += 4;
    putInt32LE(_data, i, stakeDepositUnstakeCapBps);
    i += 4;
    putInt64LE(_data, i, computeScoreSlotRange);
    i += 8;
    putFloat64LE(_data, i, instantUnstakeEpochProgress);
    i += 8;
    putFloat64LE(_data, i, instantUnstakeInputsEpochProgress);
    i += 8;
    putInt64LE(_data, i, numEpochsBetweenScoring);
    i += 8;
    putInt64LE(_data, i, minimumStakeLamports);
    i += 8;
    putInt64LE(_data, i, minimumVotingEpochs);
    i += 8;
    i += Borsh.writeArray(padding1, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
