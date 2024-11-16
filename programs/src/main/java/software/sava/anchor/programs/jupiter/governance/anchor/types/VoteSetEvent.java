package software.sava.anchor.programs.jupiter.governance.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record VoteSetEvent(PublicKey governor,
                           PublicKey proposal,
                           PublicKey voter,
                           PublicKey vote,
                           int side,
                           long votingPower) implements Borsh {

  public static final int BYTES = 137;

  public static VoteSetEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var governor = readPubKey(_data, i);
    i += 32;
    final var proposal = readPubKey(_data, i);
    i += 32;
    final var voter = readPubKey(_data, i);
    i += 32;
    final var vote = readPubKey(_data, i);
    i += 32;
    final var side = _data[i] & 0xFF;
    ++i;
    final var votingPower = getInt64LE(_data, i);
    return new VoteSetEvent(governor,
                            proposal,
                            voter,
                            vote,
                            side,
                            votingPower);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    governor.write(_data, i);
    i += 32;
    proposal.write(_data, i);
    i += 32;
    voter.write(_data, i);
    i += 32;
    vote.write(_data, i);
    i += 32;
    _data[i] = (byte) side;
    ++i;
    putInt64LE(_data, i, votingPower);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
