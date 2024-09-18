package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getFloat64LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putFloat64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record ScoreComponentsV2(// Product of all scoring components
                                double score,
                                // vote_credits_ratio * (1 - commission)
                                double yieldScore,
                                // If max mev commission in mev_commission_range epochs is less than threshold, score is 1.0, else 0
                                double mevCommissionScore,
                                // If validator is blacklisted, score is 0.0, else 1.0
                                double blacklistedScore,
                                // If validator is not in the superminority, score is 1.0, else 0.0
                                double superminorityScore,
                                // If delinquency is not > threshold in any epoch, score is 1.0, else 0.0
                                double delinquencyScore,
                                // If validator has a mev commission in the last 10 epochs, score is 1.0, else 0.0
                                double runningJitoScore,
                                // If max commission in commission_range epochs is less than commission_threshold, score is 1.0, else 0.0
                                double commissionScore,
                                // If max commission in all validator history epochs is less than historical_commission_threshold, score is 1.0, else 0.0
                                double historicalCommissionScore,
                                // Average vote credits in last epoch_credits_range epochs / average blocks in last epoch_credits_range epochs
                                // Excluding current epoch
                                double voteCreditsRatio,
                                PublicKey voteAccount,
                                int epoch,
                                // Details about why a given score was calculated
                                ScoreDetails details) implements Borsh {

  public static final int BYTES = 136;

  public static ScoreComponentsV2 read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var score = getFloat64LE(_data, i);
    i += 8;
    final var yieldScore = getFloat64LE(_data, i);
    i += 8;
    final var mevCommissionScore = getFloat64LE(_data, i);
    i += 8;
    final var blacklistedScore = getFloat64LE(_data, i);
    i += 8;
    final var superminorityScore = getFloat64LE(_data, i);
    i += 8;
    final var delinquencyScore = getFloat64LE(_data, i);
    i += 8;
    final var runningJitoScore = getFloat64LE(_data, i);
    i += 8;
    final var commissionScore = getFloat64LE(_data, i);
    i += 8;
    final var historicalCommissionScore = getFloat64LE(_data, i);
    i += 8;
    final var voteCreditsRatio = getFloat64LE(_data, i);
    i += 8;
    final var voteAccount = readPubKey(_data, i);
    i += 32;
    final var epoch = getInt16LE(_data, i);
    i += 2;
    final var details = ScoreDetails.read(_data, i);
    return new ScoreComponentsV2(score,
                                 yieldScore,
                                 mevCommissionScore,
                                 blacklistedScore,
                                 superminorityScore,
                                 delinquencyScore,
                                 runningJitoScore,
                                 commissionScore,
                                 historicalCommissionScore,
                                 voteCreditsRatio,
                                 voteAccount,
                                 epoch,
                                 details);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putFloat64LE(_data, i, score);
    i += 8;
    putFloat64LE(_data, i, yieldScore);
    i += 8;
    putFloat64LE(_data, i, mevCommissionScore);
    i += 8;
    putFloat64LE(_data, i, blacklistedScore);
    i += 8;
    putFloat64LE(_data, i, superminorityScore);
    i += 8;
    putFloat64LE(_data, i, delinquencyScore);
    i += 8;
    putFloat64LE(_data, i, runningJitoScore);
    i += 8;
    putFloat64LE(_data, i, commissionScore);
    i += 8;
    putFloat64LE(_data, i, historicalCommissionScore);
    i += 8;
    putFloat64LE(_data, i, voteCreditsRatio);
    i += 8;
    voteAccount.write(_data, i);
    i += 32;
    putInt16LE(_data, i, epoch);
    i += 2;
    i += Borsh.write(details, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
