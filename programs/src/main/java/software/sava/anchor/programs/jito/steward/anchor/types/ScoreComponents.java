package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getFloat64LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putFloat64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

// Deprecated: This struct is no longer emitted but is kept to allow parsing of old events.
// Because the event discriminator is based on struct name, it's important to rename the struct if
// fields are changed.
public record ScoreComponents(double score,
                              double yieldScore,
                              double mevCommissionScore,
                              double blacklistedScore,
                              double superminorityScore,
                              double delinquencyScore,
                              double runningJitoScore,
                              double commissionScore,
                              double historicalCommissionScore,
                              double voteCreditsRatio,
                              PublicKey voteAccount,
                              int epoch) implements Borsh {

  public static final int BYTES = 114;

  public static ScoreComponents read(final byte[] _data, final int offset) {
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
    return new ScoreComponents(score,
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
                               epoch);
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
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
