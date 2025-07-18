package software.sava.anchor.programs.jupiter.governance.anchor;

import java.lang.String;

import java.util.List;

import software.sava.anchor.programs.jupiter.governance.anchor.types.GovernanceParameters;
import software.sava.anchor.programs.jupiter.governance.anchor.types.ProposalInstruction;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.anchor.AnchorUtil.writeDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class GovernProgram {

  public static final Discriminator CREATE_GOVERNOR_DISCRIMINATOR = toDiscriminator(103, 30, 78, 252, 28, 128, 40, 3);

  // Creates a [Governor].
  public static Instruction createGovernor(final AccountMeta invokedGovernProgramMeta,
                                           // Base of the [Governor] key.
                                           final PublicKey baseKey,
                                           // Governor.
                                           final PublicKey governorKey,
                                           // The Smart Wallet.
                                           final PublicKey smartWalletKey,
                                           // Payer.
                                           final PublicKey payerKey,
                                           // System program.
                                           final PublicKey systemProgramKey,
                                           final PublicKey locker,
                                           final GovernanceParameters params) {
    final var keys = List.of(
      createReadOnlySigner(baseKey),
      createWrite(governorKey),
      createRead(smartWalletKey),
      createWritableSigner(payerKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[40 + Borsh.len(params)];
    int i = writeDiscriminator(CREATE_GOVERNOR_DISCRIMINATOR, _data, 0);
    locker.write(_data, i);
    i += 32;
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedGovernProgramMeta, keys, _data);
  }

  public record CreateGovernorIxData(Discriminator discriminator, PublicKey locker, GovernanceParameters params) implements Borsh {  

    public static CreateGovernorIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 72;

    public static CreateGovernorIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var locker = readPubKey(_data, i);
      i += 32;
      final var params = GovernanceParameters.read(_data, i);
      return new CreateGovernorIxData(discriminator, locker, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      locker.write(_data, i);
      i += 32;
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CREATE_PROPOSAL_DISCRIMINATOR = toDiscriminator(132, 116, 68, 174, 216, 160, 198, 22);

  // Creates a [Proposal].
  // This may be called by anyone, since the [Proposal] does not do anything until
  // it is activated in [activate_proposal].
  public static Instruction createProposal(final AccountMeta invokedGovernProgramMeta,
                                           // The [Governor].
                                           final PublicKey governorKey,
                                           // The [Proposal].
                                           final PublicKey proposalKey,
                                           // smart wallet of governor
                                           final PublicKey smartWalletKey,
                                           // Proposer of the proposal.
                                           // One of the owners. Checked in the handler via [SmartWallet::owner_index].
                                           final PublicKey proposerKey,
                                           // Payer of the proposal.
                                           final PublicKey payerKey,
                                           // System program.
                                           final PublicKey systemProgramKey,
                                           final PublicKey eventAuthorityKey,
                                           final PublicKey programKey,
                                           final int proposalType,
                                           final int maxOption,
                                           final ProposalInstruction[] instructions) {
    final var keys = List.of(
      createWrite(governorKey),
      createWrite(proposalKey),
      createRead(smartWalletKey),
      createReadOnlySigner(proposerKey),
      createWritableSigner(payerKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[10 + Borsh.lenVector(instructions)];
    int i = writeDiscriminator(CREATE_PROPOSAL_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) proposalType;
    ++i;
    _data[i] = (byte) maxOption;
    ++i;
    Borsh.writeVector(instructions, _data, i);

    return Instruction.createInstruction(invokedGovernProgramMeta, keys, _data);
  }

  public record CreateProposalIxData(Discriminator discriminator,
                                     int proposalType,
                                     int maxOption,
                                     ProposalInstruction[] instructions) implements Borsh {  

    public static CreateProposalIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static CreateProposalIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var proposalType = _data[i] & 0xFF;
      ++i;
      final var maxOption = _data[i] & 0xFF;
      ++i;
      final var instructions = Borsh.readVector(ProposalInstruction.class, ProposalInstruction::read, _data, i);
      return new CreateProposalIxData(discriminator, proposalType, maxOption, instructions);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) proposalType;
      ++i;
      _data[i] = (byte) maxOption;
      ++i;
      i += Borsh.writeVector(instructions, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 1 + 1 + Borsh.lenVector(instructions);
    }
  }

  public static final Discriminator ACTIVATE_PROPOSAL_DISCRIMINATOR = toDiscriminator(90, 186, 203, 234, 70, 185, 191, 21);

  // Activates a proposal.
  // Only the [Governor::voter] may call this; that program
  // may ensure that only certain types of users can activate proposals.
  public static Instruction activateProposal(final AccountMeta invokedGovernProgramMeta,
                                             // The [Governor].
                                             final PublicKey governorKey,
                                             // The [Proposal] to activate.
                                             final PublicKey proposalKey,
                                             // The locker of the [Governor] that may activate the proposal.
                                             final PublicKey lockerKey) {
    final var keys = List.of(
      createRead(governorKey),
      createWrite(proposalKey),
      createReadOnlySigner(lockerKey)
    );

    return Instruction.createInstruction(invokedGovernProgramMeta, keys, ACTIVATE_PROPOSAL_DISCRIMINATOR);
  }

  public static final Discriminator CANCEL_PROPOSAL_DISCRIMINATOR = toDiscriminator(106, 74, 128, 146, 19, 65, 39, 23);

  // Cancels a proposal.
  // This is only callable by the creator of the proposal.
  public static Instruction cancelProposal(final AccountMeta invokedGovernProgramMeta,
                                           // The [Governor].
                                           final PublicKey governorKey,
                                           // The [Proposal] to activate.
                                           final PublicKey proposalKey,
                                           // The [Proposal::proposer].
                                           final PublicKey proposerKey,
                                           final PublicKey eventAuthorityKey,
                                           final PublicKey programKey) {
    final var keys = List.of(
      createRead(governorKey),
      createWrite(proposalKey),
      createReadOnlySigner(proposerKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedGovernProgramMeta, keys, CANCEL_PROPOSAL_DISCRIMINATOR);
  }

  public static final Discriminator QUEUE_PROPOSAL_DISCRIMINATOR = toDiscriminator(168, 219, 139, 211, 205, 152, 125, 110);

  // Queues a proposal for execution by the [SmartWallet].
  public static Instruction queueProposal(final AccountMeta invokedGovernProgramMeta,
                                          // The Governor.
                                          final PublicKey governorKey,
                                          // The Proposal to queue.
                                          final PublicKey proposalKey,
                                          // The transaction key of the proposal.
                                          // This account is passed to and validated by the Smart Wallet program to be initialized.
                                          final PublicKey transactionKey,
                                          // The Smart Wallet.
                                          final PublicKey smartWalletKey,
                                          // Payer of the queued transaction.
                                          final PublicKey payerKey,
                                          // The Smart Wallet program.
                                          final PublicKey smartWalletProgramKey,
                                          // The System program.
                                          final PublicKey systemProgramKey,
                                          final PublicKey eventAuthorityKey,
                                          final PublicKey programKey) {
    final var keys = List.of(
      createRead(governorKey),
      createWrite(proposalKey),
      createWrite(transactionKey),
      createWrite(smartWalletKey),
      createWritableSigner(payerKey),
      createRead(smartWalletProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedGovernProgramMeta, keys, QUEUE_PROPOSAL_DISCRIMINATOR);
  }

  public static final Discriminator NEW_VOTE_DISCRIMINATOR = toDiscriminator(163, 108, 157, 189, 140, 80, 13, 143);

  // Creates a new [Vote]. Anyone can call this.
  public static Instruction newVote(final AccountMeta invokedGovernProgramMeta,
                                    // Proposal being voted on.
                                    final PublicKey proposalKey,
                                    // The vote.
                                    final PublicKey voteKey,
                                    // Payer of the [Vote].
                                    final PublicKey payerKey,
                                    // System program.
                                    final PublicKey systemProgramKey,
                                    final PublicKey voter) {
    final var keys = List.of(
      createRead(proposalKey),
      createWrite(voteKey),
      createWritableSigner(payerKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(NEW_VOTE_DISCRIMINATOR, _data, 0);
    voter.write(_data, i);

    return Instruction.createInstruction(invokedGovernProgramMeta, keys, _data);
  }

  public record NewVoteIxData(Discriminator discriminator, PublicKey voter) implements Borsh {  

    public static NewVoteIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static NewVoteIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var voter = readPubKey(_data, i);
      return new NewVoteIxData(discriminator, voter);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      voter.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_VOTE_DISCRIMINATOR = toDiscriminator(171, 33, 83, 172, 148, 215, 239, 97);

  // Sets a [Vote] weight and side.
  // This may only be called by the [Governor::voter].
  public static Instruction setVote(final AccountMeta invokedGovernProgramMeta,
                                    // The [Governor].
                                    final PublicKey governorKey,
                                    // The [Proposal].
                                    final PublicKey proposalKey,
                                    // The [Vote].
                                    final PublicKey voteKey,
                                    // The [Governor::locker].
                                    final PublicKey lockerKey,
                                    final int side,
                                    final long weight) {
    final var keys = List.of(
      createRead(governorKey),
      createWrite(proposalKey),
      createWrite(voteKey),
      createReadOnlySigner(lockerKey)
    );

    final byte[] _data = new byte[17];
    int i = writeDiscriminator(SET_VOTE_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) side;
    ++i;
    putInt64LE(_data, i, weight);

    return Instruction.createInstruction(invokedGovernProgramMeta, keys, _data);
  }

  public record SetVoteIxData(Discriminator discriminator, int side, long weight) implements Borsh {  

    public static SetVoteIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static SetVoteIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var side = _data[i] & 0xFF;
      ++i;
      final var weight = getInt64LE(_data, i);
      return new SetVoteIxData(discriminator, side, weight);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) side;
      ++i;
      putInt64LE(_data, i, weight);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_GOVERNANCE_PARAMS_DISCRIMINATOR = toDiscriminator(175, 187, 3, 73, 8, 251, 67, 178);

  // Sets the [GovernanceParameters].
  // This may only be called by the [Governor::smart_wallet].
  public static Instruction setGovernanceParams(final AccountMeta invokedGovernProgramMeta,
                                                // The [Governor]
                                                final PublicKey governorKey,
                                                // The Smart Wallet.
                                                final PublicKey smartWalletKey,
                                                final GovernanceParameters params) {
    final var keys = List.of(
      createWrite(governorKey),
      createReadOnlySigner(smartWalletKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(SET_GOVERNANCE_PARAMS_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedGovernProgramMeta, keys, _data);
  }

  public record SetGovernanceParamsIxData(Discriminator discriminator, GovernanceParameters params) implements Borsh {  

    public static SetGovernanceParamsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static SetGovernanceParamsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GovernanceParameters.read(_data, i);
      return new SetGovernanceParamsIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_VOTING_REWARD_DISCRIMINATOR = toDiscriminator(227, 241, 48, 137, 30, 26, 104, 70);

  // Sets Voting Reward.
  // This may only be called by the [Governor::smart_wallet].
  public static Instruction setVotingReward(final AccountMeta invokedGovernProgramMeta,
                                            // The [Governor]
                                            final PublicKey governorKey,
                                            // reward mint
                                            final PublicKey rewardMintKey,
                                            // The Smart Wallet.
                                            final PublicKey smartWalletKey,
                                            final long rewardPerProposal) {
    final var keys = List.of(
      createWrite(governorKey),
      createRead(rewardMintKey),
      createReadOnlySigner(smartWalletKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(SET_VOTING_REWARD_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, rewardPerProposal);

    return Instruction.createInstruction(invokedGovernProgramMeta, keys, _data);
  }

  public record SetVotingRewardIxData(Discriminator discriminator, long rewardPerProposal) implements Borsh {  

    public static SetVotingRewardIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SetVotingRewardIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var rewardPerProposal = getInt64LE(_data, i);
      return new SetVotingRewardIxData(discriminator, rewardPerProposal);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, rewardPerProposal);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CLAIM_REWARD_DISCRIMINATOR = toDiscriminator(149, 95, 181, 242, 94, 90, 158, 162);

  // Claim rewards, for voter
  public static Instruction claimReward(final AccountMeta invokedGovernProgramMeta,
                                        // The [Governor]
                                        final PublicKey governorKey,
                                        // reward mint
                                        final PublicKey rewardVaultKey,
                                        // proposal
                                        final PublicKey proposalKey,
                                        // The [Vote].
                                        final PublicKey voteKey,
                                        // Owner of the vault
                                        // TODO: check whether vote delegrate can claim on behalf of owner?
                                        final PublicKey voterKey,
                                        // Voter token account
                                        final PublicKey voterTokenAccountKey,
                                        // Token program.
                                        final PublicKey tokenProgramKey,
                                        final PublicKey eventAuthorityKey,
                                        final PublicKey programKey) {
    final var keys = List.of(
      createWrite(governorKey),
      createWrite(rewardVaultKey),
      createWrite(proposalKey),
      createWrite(voteKey),
      createReadOnlySigner(voterKey),
      createWrite(voterTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedGovernProgramMeta, keys, CLAIM_REWARD_DISCRIMINATOR);
  }

  public static final Discriminator SET_LOCKER_DISCRIMINATOR = toDiscriminator(17, 6, 101, 72, 250, 23, 152, 96);

  // Sets the locker of the [Governor].
  public static Instruction setLocker(final AccountMeta invokedGovernProgramMeta,
                                      // The [Governor]
                                      final PublicKey governorKey,
                                      // The Smart Wallet.
                                      final PublicKey smartWalletKey,
                                      final PublicKey newLocker) {
    final var keys = List.of(
      createWrite(governorKey),
      createReadOnlySigner(smartWalletKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(SET_LOCKER_DISCRIMINATOR, _data, 0);
    newLocker.write(_data, i);

    return Instruction.createInstruction(invokedGovernProgramMeta, keys, _data);
  }

  public record SetLockerIxData(Discriminator discriminator, PublicKey newLocker) implements Borsh {  

    public static SetLockerIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static SetLockerIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var newLocker = readPubKey(_data, i);
      return new SetLockerIxData(discriminator, newLocker);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      newLocker.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CREATE_PROPOSAL_META_DISCRIMINATOR = toDiscriminator(238, 138, 212, 160, 46, 53, 51, 88);

  // Creates a [ProposalMeta].
  public static Instruction createProposalMeta(final AccountMeta invokedGovernProgramMeta,
                                               // The [Proposal].
                                               final PublicKey proposalKey,
                                               // Proposer of the proposal.
                                               final PublicKey proposerKey,
                                               // The [ProposalMeta].
                                               final PublicKey proposalMetaKey,
                                               // Payer of the [ProposalMeta].
                                               final PublicKey payerKey,
                                               // System program.
                                               final PublicKey systemProgramKey,
                                               final PublicKey eventAuthorityKey,
                                               final PublicKey programKey,
                                               final int bump,
                                               final String title,
                                               final String descriptionLink) {
    final var keys = List.of(
      createRead(proposalKey),
      createReadOnlySigner(proposerKey),
      createWrite(proposalMetaKey),
      createWritableSigner(payerKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _title = title.getBytes(UTF_8);
    final byte[] _descriptionLink = descriptionLink.getBytes(UTF_8);
    final byte[] _data = new byte[17 + Borsh.lenVector(_title) + Borsh.lenVector(_descriptionLink)];
    int i = writeDiscriminator(CREATE_PROPOSAL_META_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) bump;
    ++i;
    i += Borsh.writeVector(_title, _data, i);
    Borsh.writeVector(_descriptionLink, _data, i);

    return Instruction.createInstruction(invokedGovernProgramMeta, keys, _data);
  }

  public record CreateProposalMetaIxData(Discriminator discriminator,
                                         int bump,
                                         String title, byte[] _title,
                                         String descriptionLink, byte[] _descriptionLink) implements Borsh {  

    public static CreateProposalMetaIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static CreateProposalMetaIxData createRecord(final Discriminator discriminator,
                                                        final int bump,
                                                        final String title,
                                                        final String descriptionLink) {
      return new CreateProposalMetaIxData(discriminator, bump, title, title.getBytes(UTF_8), descriptionLink, descriptionLink.getBytes(UTF_8));
    }

    public static CreateProposalMetaIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var bump = _data[i] & 0xFF;
      ++i;
      final var title = Borsh.string(_data, i);
      i += (Integer.BYTES + getInt32LE(_data, i));
      final var descriptionLink = Borsh.string(_data, i);
      return new CreateProposalMetaIxData(discriminator, bump, title, title.getBytes(UTF_8), descriptionLink, descriptionLink.getBytes(UTF_8));
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) bump;
      ++i;
      i += Borsh.writeVector(_title, _data, i);
      i += Borsh.writeVector(_descriptionLink, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 1 + Borsh.lenVector(_title) + Borsh.lenVector(_descriptionLink);
    }
  }

  public static final Discriminator CREATE_OPTION_PROPOSAL_META_DISCRIMINATOR = toDiscriminator(152, 144, 104, 228, 245, 234, 164, 224);

  // Creates an [OptionProposalMeta].
  public static Instruction createOptionProposalMeta(final AccountMeta invokedGovernProgramMeta,
                                                     // The [Proposal].
                                                     final PublicKey proposalKey,
                                                     // Proposer of the proposal.
                                                     final PublicKey proposerKey,
                                                     // The [ProposalMeta].
                                                     final PublicKey optionProposalMetaKey,
                                                     // Payer of the [ProposalMeta].
                                                     final PublicKey payerKey,
                                                     // System program.
                                                     final PublicKey systemProgramKey,
                                                     final PublicKey eventAuthorityKey,
                                                     final PublicKey programKey,
                                                     final int bump,
                                                     final String[] optionDescriptions) {
    final var keys = List.of(
      createRead(proposalKey),
      createReadOnlySigner(proposerKey),
      createWrite(optionProposalMetaKey),
      createWritableSigner(payerKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[9 + Borsh.lenVector(optionDescriptions)];
    int i = writeDiscriminator(CREATE_OPTION_PROPOSAL_META_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) bump;
    ++i;
    Borsh.writeVector(optionDescriptions, _data, i);

    return Instruction.createInstruction(invokedGovernProgramMeta, keys, _data);
  }

  public record CreateOptionProposalMetaIxData(Discriminator discriminator, int bump, String[] optionDescriptions, byte[][] _optionDescriptions) implements Borsh {  

    public static CreateOptionProposalMetaIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static CreateOptionProposalMetaIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var bump = _data[i] & 0xFF;
      ++i;
      final var optionDescriptions = Borsh.readStringVector(_data, i);
      return new CreateOptionProposalMetaIxData(discriminator, bump, optionDescriptions, Borsh.getBytes(optionDescriptions));
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) bump;
      ++i;
      i += Borsh.writeVector(optionDescriptions, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 1 + Borsh.lenVector(optionDescriptions);
    }
  }

  private GovernProgram() {
  }
}
