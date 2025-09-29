package software.sava.anchor.programs.jupiter.governance.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

// A Yes/No Proposal is a pending transaction that may or may not be executed by the DAO.
public record Proposal(PublicKey _address,
                       Discriminator discriminator,
                       // The public key of the governor.
                       PublicKey governor,
                       // The unique ID of the proposal, auto-incremented.
                       long index,
                       // Bump seed
                       int bump,
                       // The public key of the proposer.
                       PublicKey proposer,
                       // The number of votes in support of a proposal required in order for a quorum to be reached and for a vote to succeed
                       long quorumVotes,
                       // maximum options of the proposal
                       int maxOption,
                       // Vote for each option
                       long[] optionVotes,
                       // The timestamp when the proposal was canceled.
                       long canceledAt,
                       // The timestamp when the proposal was created.
                       long createdAt,
                       // The timestamp in which the proposal was activated.
                       // This is when voting begins.
                       long activatedAt,
                       // The timestamp when voting ends.
                       // This only applies to active proposals.
                       long votingEndsAt,
                       // The timestamp in which the proposal was queued, i.e.
                       // approved for execution on the Smart Wallet.
                       long queuedAt,
                       // If the transaction was queued, this is the associated Smart Wallet transaction.
                       PublicKey queuedTransaction,
                       // optional reward
                       VotingReward votingReward,
                       // total claimed reward
                       long totalClaimedReward,
                       int proposalType,
                       // buffers for future use
                       BigInteger[] buffers,
                       // The instructions associated with the proposal.
                       ProposalInstruction[] instructions) implements Borsh {

  public static final int BUFFERS_LEN = 10;
  public static final int GOVERNOR_OFFSET = 8;
  public static final int INDEX_OFFSET = 40;
  public static final int BUMP_OFFSET = 48;
  public static final int PROPOSER_OFFSET = 49;
  public static final int QUORUM_VOTES_OFFSET = 81;
  public static final int MAX_OPTION_OFFSET = 89;
  public static final int OPTION_VOTES_OFFSET = 90;

  public static Filter createGovernorFilter(final PublicKey governor) {
    return Filter.createMemCompFilter(GOVERNOR_OFFSET, governor);
  }

  public static Filter createIndexFilter(final long index) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, index);
    return Filter.createMemCompFilter(INDEX_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createProposerFilter(final PublicKey proposer) {
    return Filter.createMemCompFilter(PROPOSER_OFFSET, proposer);
  }

  public static Filter createQuorumVotesFilter(final long quorumVotes) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, quorumVotes);
    return Filter.createMemCompFilter(QUORUM_VOTES_OFFSET, _data);
  }

  public static Filter createMaxOptionFilter(final int maxOption) {
    return Filter.createMemCompFilter(MAX_OPTION_OFFSET, new byte[]{(byte) maxOption});
  }

  public static Proposal read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Proposal read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Proposal read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Proposal> FACTORY = Proposal::read;

  public static Proposal read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var governor = readPubKey(_data, i);
    i += 32;
    final var index = getInt64LE(_data, i);
    i += 8;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var proposer = readPubKey(_data, i);
    i += 32;
    final var quorumVotes = getInt64LE(_data, i);
    i += 8;
    final var maxOption = _data[i] & 0xFF;
    ++i;
    final var optionVotes = Borsh.readlongVector(_data, i);
    i += Borsh.lenVector(optionVotes);
    final var canceledAt = getInt64LE(_data, i);
    i += 8;
    final var createdAt = getInt64LE(_data, i);
    i += 8;
    final var activatedAt = getInt64LE(_data, i);
    i += 8;
    final var votingEndsAt = getInt64LE(_data, i);
    i += 8;
    final var queuedAt = getInt64LE(_data, i);
    i += 8;
    final var queuedTransaction = readPubKey(_data, i);
    i += 32;
    final var votingReward = VotingReward.read(_data, i);
    i += Borsh.len(votingReward);
    final var totalClaimedReward = getInt64LE(_data, i);
    i += 8;
    final var proposalType = _data[i] & 0xFF;
    ++i;
    final var buffers = new BigInteger[10];
    i += Borsh.read128Array(buffers, _data, i);
    final var instructions = Borsh.readVector(ProposalInstruction.class, ProposalInstruction::read, _data, i);
    return new Proposal(_address,
                        discriminator,
                        governor,
                        index,
                        bump,
                        proposer,
                        quorumVotes,
                        maxOption,
                        optionVotes,
                        canceledAt,
                        createdAt,
                        activatedAt,
                        votingEndsAt,
                        queuedAt,
                        queuedTransaction,
                        votingReward,
                        totalClaimedReward,
                        proposalType,
                        buffers,
                        instructions);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    governor.write(_data, i);
    i += 32;
    putInt64LE(_data, i, index);
    i += 8;
    _data[i] = (byte) bump;
    ++i;
    proposer.write(_data, i);
    i += 32;
    putInt64LE(_data, i, quorumVotes);
    i += 8;
    _data[i] = (byte) maxOption;
    ++i;
    i += Borsh.writeVector(optionVotes, _data, i);
    putInt64LE(_data, i, canceledAt);
    i += 8;
    putInt64LE(_data, i, createdAt);
    i += 8;
    putInt64LE(_data, i, activatedAt);
    i += 8;
    putInt64LE(_data, i, votingEndsAt);
    i += 8;
    putInt64LE(_data, i, queuedAt);
    i += 8;
    queuedTransaction.write(_data, i);
    i += 32;
    i += Borsh.write(votingReward, _data, i);
    putInt64LE(_data, i, totalClaimedReward);
    i += 8;
    _data[i] = (byte) proposalType;
    ++i;
    i += Borsh.write128ArrayChecked(buffers, 10, _data, i);
    i += Borsh.writeVector(instructions, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32
         + 8
         + 1
         + 32
         + 8
         + 1
         + Borsh.lenVector(optionVotes)
         + 8
         + 8
         + 8
         + 8
         + 8
         + 32
         + Borsh.len(votingReward)
         + 8
         + 1
         + Borsh.len128Array(buffers)
         + Borsh.lenVector(instructions);
  }
}
