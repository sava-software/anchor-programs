package software.sava.anchor.programs.jito.steward.anchor.types;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getFloat64LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record UpdateParametersArgs(OptionalInt mevCommissionRange,
                                   OptionalInt epochCreditsRange,
                                   OptionalInt commissionRange,
                                   OptionalDouble scoringDelinquencyThresholdRatio,
                                   OptionalDouble instantUnstakeDelinquencyThresholdRatio,
                                   OptionalInt mevCommissionBpsThreshold,
                                   OptionalInt commissionThreshold,
                                   OptionalInt historicalCommissionThreshold,
                                   OptionalInt numDelegationValidators,
                                   OptionalInt scoringUnstakeCapBps,
                                   OptionalInt instantUnstakeCapBps,
                                   OptionalInt stakeDepositUnstakeCapBps,
                                   OptionalDouble instantUnstakeEpochProgress,
                                   OptionalLong computeScoreSlotRange,
                                   OptionalDouble instantUnstakeInputsEpochProgress,
                                   OptionalLong numEpochsBetweenScoring,
                                   OptionalLong minimumStakeLamports,
                                   OptionalLong minimumVotingEpochs) implements Borsh {

  public static UpdateParametersArgs read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var mevCommissionRange = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
    if (mevCommissionRange.isPresent()) {
      i += 2;
    }
    final var epochCreditsRange = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
    if (epochCreditsRange.isPresent()) {
      i += 2;
    }
    final var commissionRange = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
    if (commissionRange.isPresent()) {
      i += 2;
    }
    final var scoringDelinquencyThresholdRatio = _data[i++] == 0 ? OptionalDouble.empty() : OptionalDouble.of(getFloat64LE(_data, i));
    if (scoringDelinquencyThresholdRatio.isPresent()) {
      i += 8;
    }
    final var instantUnstakeDelinquencyThresholdRatio = _data[i++] == 0 ? OptionalDouble.empty() : OptionalDouble.of(getFloat64LE(_data, i));
    if (instantUnstakeDelinquencyThresholdRatio.isPresent()) {
      i += 8;
    }
    final var mevCommissionBpsThreshold = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
    if (mevCommissionBpsThreshold.isPresent()) {
      i += 2;
    }
    final var commissionThreshold = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    if (commissionThreshold.isPresent()) {
      ++i;
    }
    final var historicalCommissionThreshold = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    if (historicalCommissionThreshold.isPresent()) {
      ++i;
    }
    final var numDelegationValidators = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (numDelegationValidators.isPresent()) {
      i += 4;
    }
    final var scoringUnstakeCapBps = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (scoringUnstakeCapBps.isPresent()) {
      i += 4;
    }
    final var instantUnstakeCapBps = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (instantUnstakeCapBps.isPresent()) {
      i += 4;
    }
    final var stakeDepositUnstakeCapBps = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (stakeDepositUnstakeCapBps.isPresent()) {
      i += 4;
    }
    final var instantUnstakeEpochProgress = _data[i++] == 0 ? OptionalDouble.empty() : OptionalDouble.of(getFloat64LE(_data, i));
    if (instantUnstakeEpochProgress.isPresent()) {
      i += 8;
    }
    final var computeScoreSlotRange = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (computeScoreSlotRange.isPresent()) {
      i += 8;
    }
    final var instantUnstakeInputsEpochProgress = _data[i++] == 0 ? OptionalDouble.empty() : OptionalDouble.of(getFloat64LE(_data, i));
    if (instantUnstakeInputsEpochProgress.isPresent()) {
      i += 8;
    }
    final var numEpochsBetweenScoring = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (numEpochsBetweenScoring.isPresent()) {
      i += 8;
    }
    final var minimumStakeLamports = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (minimumStakeLamports.isPresent()) {
      i += 8;
    }
    final var minimumVotingEpochs = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    return new UpdateParametersArgs(mevCommissionRange,
                                    epochCreditsRange,
                                    commissionRange,
                                    scoringDelinquencyThresholdRatio,
                                    instantUnstakeDelinquencyThresholdRatio,
                                    mevCommissionBpsThreshold,
                                    commissionThreshold,
                                    historicalCommissionThreshold,
                                    numDelegationValidators,
                                    scoringUnstakeCapBps,
                                    instantUnstakeCapBps,
                                    stakeDepositUnstakeCapBps,
                                    instantUnstakeEpochProgress,
                                    computeScoreSlotRange,
                                    instantUnstakeInputsEpochProgress,
                                    numEpochsBetweenScoring,
                                    minimumStakeLamports,
                                    minimumVotingEpochs);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptionalshort(mevCommissionRange, _data, i);
    i += Borsh.writeOptionalshort(epochCreditsRange, _data, i);
    i += Borsh.writeOptionalshort(commissionRange, _data, i);
    i += Borsh.writeOptional(scoringDelinquencyThresholdRatio, _data, i);
    i += Borsh.writeOptional(instantUnstakeDelinquencyThresholdRatio, _data, i);
    i += Borsh.writeOptionalshort(mevCommissionBpsThreshold, _data, i);
    i += Borsh.writeOptionalbyte(commissionThreshold, _data, i);
    i += Borsh.writeOptionalbyte(historicalCommissionThreshold, _data, i);
    i += Borsh.writeOptional(numDelegationValidators, _data, i);
    i += Borsh.writeOptional(scoringUnstakeCapBps, _data, i);
    i += Borsh.writeOptional(instantUnstakeCapBps, _data, i);
    i += Borsh.writeOptional(stakeDepositUnstakeCapBps, _data, i);
    i += Borsh.writeOptional(instantUnstakeEpochProgress, _data, i);
    i += Borsh.writeOptional(computeScoreSlotRange, _data, i);
    i += Borsh.writeOptional(instantUnstakeInputsEpochProgress, _data, i);
    i += Borsh.writeOptional(numEpochsBetweenScoring, _data, i);
    i += Borsh.writeOptional(minimumStakeLamports, _data, i);
    i += Borsh.writeOptional(minimumVotingEpochs, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (mevCommissionRange == null || mevCommissionRange.isEmpty() ? 1 : (1 + 2))
         + (epochCreditsRange == null || epochCreditsRange.isEmpty() ? 1 : (1 + 2))
         + (commissionRange == null || commissionRange.isEmpty() ? 1 : (1 + 2))
         + (scoringDelinquencyThresholdRatio == null || scoringDelinquencyThresholdRatio.isEmpty() ? 1 : (1 + 8))
         + (instantUnstakeDelinquencyThresholdRatio == null || instantUnstakeDelinquencyThresholdRatio.isEmpty() ? 1 : (1 + 8))
         + (mevCommissionBpsThreshold == null || mevCommissionBpsThreshold.isEmpty() ? 1 : (1 + 2))
         + (commissionThreshold == null || commissionThreshold.isEmpty() ? 1 : (1 + 1))
         + (historicalCommissionThreshold == null || historicalCommissionThreshold.isEmpty() ? 1 : (1 + 1))
         + (numDelegationValidators == null || numDelegationValidators.isEmpty() ? 1 : (1 + 4))
         + (scoringUnstakeCapBps == null || scoringUnstakeCapBps.isEmpty() ? 1 : (1 + 4))
         + (instantUnstakeCapBps == null || instantUnstakeCapBps.isEmpty() ? 1 : (1 + 4))
         + (stakeDepositUnstakeCapBps == null || stakeDepositUnstakeCapBps.isEmpty() ? 1 : (1 + 4))
         + (instantUnstakeEpochProgress == null || instantUnstakeEpochProgress.isEmpty() ? 1 : (1 + 8))
         + (computeScoreSlotRange == null || computeScoreSlotRange.isEmpty() ? 1 : (1 + 8))
         + (instantUnstakeInputsEpochProgress == null || instantUnstakeInputsEpochProgress.isEmpty() ? 1 : (1 + 8))
         + (numEpochsBetweenScoring == null || numEpochsBetweenScoring.isEmpty() ? 1 : (1 + 8))
         + (minimumStakeLamports == null || minimumStakeLamports.isEmpty() ? 1 : (1 + 8))
         + (minimumVotingEpochs == null || minimumVotingEpochs.isEmpty() ? 1 : (1 + 8));
  }
}
