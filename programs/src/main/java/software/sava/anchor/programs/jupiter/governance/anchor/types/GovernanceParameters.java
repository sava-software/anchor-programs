package software.sava.anchor.programs.jupiter.governance.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Governance parameters.
public record GovernanceParameters(// The delay before voting on a proposal may take place, once proposed, in seconds
                                   long votingDelay,
                                   // The duration of voting on a proposal, in seconds
                                   long votingPeriod,
                                   // The number of votes in support of a proposal required in order for a quorum to be reached and for a vote to succeed
                                   long quorumVotes,
                                   // The timelock delay of the DAO's created proposals.
                                   long timelockDelaySeconds) implements Borsh {

  public static final int BYTES = 32;

  public static GovernanceParameters read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var votingDelay = getInt64LE(_data, i);
    i += 8;
    final var votingPeriod = getInt64LE(_data, i);
    i += 8;
    final var quorumVotes = getInt64LE(_data, i);
    i += 8;
    final var timelockDelaySeconds = getInt64LE(_data, i);
    return new GovernanceParameters(votingDelay,
                                    votingPeriod,
                                    quorumVotes,
                                    timelockDelaySeconds);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, votingDelay);
    i += 8;
    putInt64LE(_data, i, votingPeriod);
    i += 8;
    putInt64LE(_data, i, quorumVotes);
    i += 8;
    putInt64LE(_data, i, timelockDelaySeconds);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
