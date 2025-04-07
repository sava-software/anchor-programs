package software.sava.anchor.programs.jito.tip_router.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record BallotTally(int index,
                          Ballot ballot,
                          StakeWeights stakeWeights,
                          long tally) implements Borsh {

  public static final int BYTES = 250;

  public static BallotTally read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var index = getInt16LE(_data, i);
    i += 2;
    final var ballot = Ballot.read(_data, i);
    i += Borsh.len(ballot);
    final var stakeWeights = StakeWeights.read(_data, i);
    i += Borsh.len(stakeWeights);
    final var tally = getInt64LE(_data, i);
    return new BallotTally(index,
                           ballot,
                           stakeWeights,
                           tally);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, index);
    i += 2;
    i += Borsh.write(ballot, _data, i);
    i += Borsh.write(stakeWeights, _data, i);
    putInt64LE(_data, i, tally);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
