package software.sava.anchor.programs.jupiter.governance.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ClaimRewardEvent(PublicKey governor,
                               PublicKey voter,
                               PublicKey proposal,
                               long votingReward) implements Borsh {

  public static final int BYTES = 104;

  public static ClaimRewardEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var governor = readPubKey(_data, i);
    i += 32;
    final var voter = readPubKey(_data, i);
    i += 32;
    final var proposal = readPubKey(_data, i);
    i += 32;
    final var votingReward = getInt64LE(_data, i);
    return new ClaimRewardEvent(governor,
                                voter,
                                proposal,
                                votingReward);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    governor.write(_data, i);
    i += 32;
    voter.write(_data, i);
    i += 32;
    proposal.write(_data, i);
    i += 32;
    putInt64LE(_data, i, votingReward);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
