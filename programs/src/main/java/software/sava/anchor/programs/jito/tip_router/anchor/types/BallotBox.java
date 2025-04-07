package software.sava.anchor.programs.jito.tip_router.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record BallotBox(PublicKey _address,
                        Discriminator discriminator,
                        PublicKey ncn,
                        long epoch,
                        int bump,
                        long slotCreated,
                        long slotConsensusReached,
                        byte[] reserved,
                        long operatorsVoted,
                        long uniqueBallots,
                        Ballot winningBallot,
                        OperatorVote[] operatorVotes,
                        BallotTally[] ballotTallies) implements Borsh {

  public static final int BYTES = 128305;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int NCN_OFFSET = 8;
  public static final int EPOCH_OFFSET = 40;
  public static final int BUMP_OFFSET = 48;
  public static final int SLOT_CREATED_OFFSET = 49;
  public static final int SLOT_CONSENSUS_REACHED_OFFSET = 57;
  public static final int RESERVED_OFFSET = 65;
  public static final int OPERATORS_VOTED_OFFSET = 193;
  public static final int UNIQUE_BALLOTS_OFFSET = 201;
  public static final int WINNING_BALLOT_OFFSET = 209;
  public static final int OPERATOR_VOTES_OFFSET = 305;
  public static final int BALLOT_TALLIES_OFFSET = 64305;

  public static Filter createNcnFilter(final PublicKey ncn) {
    return Filter.createMemCompFilter(NCN_OFFSET, ncn);
  }

  public static Filter createEpochFilter(final long epoch) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, epoch);
    return Filter.createMemCompFilter(EPOCH_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createSlotCreatedFilter(final long slotCreated) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, slotCreated);
    return Filter.createMemCompFilter(SLOT_CREATED_OFFSET, _data);
  }

  public static Filter createSlotConsensusReachedFilter(final long slotConsensusReached) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, slotConsensusReached);
    return Filter.createMemCompFilter(SLOT_CONSENSUS_REACHED_OFFSET, _data);
  }

  public static Filter createOperatorsVotedFilter(final long operatorsVoted) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, operatorsVoted);
    return Filter.createMemCompFilter(OPERATORS_VOTED_OFFSET, _data);
  }

  public static Filter createUniqueBallotsFilter(final long uniqueBallots) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, uniqueBallots);
    return Filter.createMemCompFilter(UNIQUE_BALLOTS_OFFSET, _data);
  }

  public static Filter createWinningBallotFilter(final Ballot winningBallot) {
    return Filter.createMemCompFilter(WINNING_BALLOT_OFFSET, winningBallot.write());
  }

  public static BallotBox read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static BallotBox read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static BallotBox read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], BallotBox> FACTORY = BallotBox::read;

  public static BallotBox read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var ncn = readPubKey(_data, i);
    i += 32;
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var slotCreated = getInt64LE(_data, i);
    i += 8;
    final var slotConsensusReached = getInt64LE(_data, i);
    i += 8;
    final var reserved = new byte[128];
    i += Borsh.readArray(reserved, _data, i);
    final var operatorsVoted = getInt64LE(_data, i);
    i += 8;
    final var uniqueBallots = getInt64LE(_data, i);
    i += 8;
    final var winningBallot = Ballot.read(_data, i);
    i += Borsh.len(winningBallot);
    final var operatorVotes = new OperatorVote[256];
    i += Borsh.readArray(operatorVotes, OperatorVote::read, _data, i);
    final var ballotTallies = new BallotTally[256];
    Borsh.readArray(ballotTallies, BallotTally::read, _data, i);
    return new BallotBox(_address,
                         discriminator,
                         ncn,
                         epoch,
                         bump,
                         slotCreated,
                         slotConsensusReached,
                         reserved,
                         operatorsVoted,
                         uniqueBallots,
                         winningBallot,
                         operatorVotes,
                         ballotTallies);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    ncn.write(_data, i);
    i += 32;
    putInt64LE(_data, i, epoch);
    i += 8;
    _data[i] = (byte) bump;
    ++i;
    putInt64LE(_data, i, slotCreated);
    i += 8;
    putInt64LE(_data, i, slotConsensusReached);
    i += 8;
    i += Borsh.writeArray(reserved, _data, i);
    putInt64LE(_data, i, operatorsVoted);
    i += 8;
    putInt64LE(_data, i, uniqueBallots);
    i += 8;
    i += Borsh.write(winningBallot, _data, i);
    i += Borsh.writeArray(operatorVotes, _data, i);
    i += Borsh.writeArray(ballotTallies, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
