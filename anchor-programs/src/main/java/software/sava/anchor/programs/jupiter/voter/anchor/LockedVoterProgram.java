package software.sava.anchor.programs.jupiter.voter.anchor;

import java.lang.String;

import java.util.List;

import software.sava.anchor.programs.jupiter.voter.anchor.types.LockerParams;
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
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class LockedVoterProgram {

  public static final Discriminator NEW_LOCKER_DISCRIMINATOR = toDiscriminator(177, 133, 32, 90, 229, 216, 131, 47);

  public static Instruction newLocker(final AccountMeta invokedLockedVoterProgramMeta,
                                      // Base.
                                      final PublicKey baseKey,
                                      // [Locker].
                                      final PublicKey lockerKey,
                                      // Mint of the token that can be used to join the [Locker].
                                      final PublicKey tokenMintKey,
                                      // [Governor] associated with the [Locker].
                                      final PublicKey governorKey,
                                      // Payer of the initialization.
                                      final PublicKey payerKey,
                                      // System program.
                                      final PublicKey systemProgramKey,
                                      final LockerParams params) {
    final var keys = List.of(
      createReadOnlySigner(baseKey),
      createWrite(lockerKey),
      createRead(tokenMintKey),
      createRead(governorKey),
      createWritableSigner(payerKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(NEW_LOCKER_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLockedVoterProgramMeta, keys, _data);
  }

  public record NewLockerIxData(Discriminator discriminator, LockerParams params) implements Borsh {  

    public static NewLockerIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 33;

    public static NewLockerIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = LockerParams.read(_data, i);
      return new NewLockerIxData(discriminator, params);
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

  public static final Discriminator NEW_ESCROW_DISCRIMINATOR = toDiscriminator(216, 182, 143, 11, 220, 38, 86, 185);

  public static Instruction newEscrow(final AccountMeta invokedLockedVoterProgramMeta,
                                      // [Locker].
                                      final PublicKey lockerKey,
                                      // [Escrow].
                                      final PublicKey escrowKey,
                                      final PublicKey escrowOwnerKey,
                                      // Payer of the initialization.
                                      final PublicKey payerKey,
                                      // System program.
                                      final PublicKey systemProgramKey) {
    final var keys = List.of(
      createWrite(lockerKey),
      createWrite(escrowKey),
      createRead(escrowOwnerKey),
      createWritableSigner(payerKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedLockedVoterProgramMeta, keys, NEW_ESCROW_DISCRIMINATOR);
  }

  public static final Discriminator INCREASE_LOCKED_AMOUNT_DISCRIMINATOR = toDiscriminator(5, 168, 118, 53, 72, 46, 203, 146);

  public static Instruction increaseLockedAmount(final AccountMeta invokedLockedVoterProgramMeta,
                                                 // [Locker].
                                                 final PublicKey lockerKey,
                                                 // [Escrow].
                                                 final PublicKey escrowKey,
                                                 // Token account held by the [Escrow].
                                                 final PublicKey escrowTokensKey,
                                                 // Authority [Self::source_tokens], Anyone can increase amount for user
                                                 final PublicKey payerKey,
                                                 // The source of deposited tokens.
                                                 final PublicKey sourceTokensKey,
                                                 // Token program.
                                                 final PublicKey tokenProgramKey,
                                                 final long amount) {
    final var keys = List.of(
      createWrite(lockerKey),
      createWrite(escrowKey),
      createWrite(escrowTokensKey),
      createReadOnlySigner(payerKey),
      createWrite(sourceTokensKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(INCREASE_LOCKED_AMOUNT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedLockedVoterProgramMeta, keys, _data);
  }

  public record IncreaseLockedAmountIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static IncreaseLockedAmountIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static IncreaseLockedAmountIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new IncreaseLockedAmountIxData(discriminator, amount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator EXTEND_LOCK_DURATION_DISCRIMINATOR = toDiscriminator(177, 105, 196, 129, 153, 137, 136, 230);

  public static Instruction extendLockDuration(final AccountMeta invokedLockedVoterProgramMeta,
                                               // [Locker].
                                               final PublicKey lockerKey,
                                               // [Escrow].
                                               final PublicKey escrowKey,
                                               // Authority of the [Escrow] and
                                               final PublicKey escrowOwnerKey,
                                               final long duration) {
    final var keys = List.of(
      createRead(lockerKey),
      createWrite(escrowKey),
      createReadOnlySigner(escrowOwnerKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(EXTEND_LOCK_DURATION_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, duration);

    return Instruction.createInstruction(invokedLockedVoterProgramMeta, keys, _data);
  }

  public record ExtendLockDurationIxData(Discriminator discriminator, long duration) implements Borsh {  

    public static ExtendLockDurationIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static ExtendLockDurationIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var duration = getInt64LE(_data, i);
      return new ExtendLockDurationIxData(discriminator, duration);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, duration);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator TOGGLE_MAX_LOCK_DISCRIMINATOR = toDiscriminator(163, 157, 161, 132, 179, 107, 127, 143);

  public static Instruction toggleMaxLock(final AccountMeta invokedLockedVoterProgramMeta,
                                          // [Locker].
                                          final PublicKey lockerKey,
                                          // [Escrow].
                                          final PublicKey escrowKey,
                                          // Authority of the [Escrow] and
                                          final PublicKey escrowOwnerKey,
                                          final boolean isMaxLock) {
    final var keys = List.of(
      createRead(lockerKey),
      createWrite(escrowKey),
      createReadOnlySigner(escrowOwnerKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(TOGGLE_MAX_LOCK_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) (isMaxLock ? 1 : 0);

    return Instruction.createInstruction(invokedLockedVoterProgramMeta, keys, _data);
  }

  public record ToggleMaxLockIxData(Discriminator discriminator, boolean isMaxLock) implements Borsh {  

    public static ToggleMaxLockIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static ToggleMaxLockIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var isMaxLock = _data[i] == 1;
      return new ToggleMaxLockIxData(discriminator, isMaxLock);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) (isMaxLock ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator WITHDRAW_DISCRIMINATOR = toDiscriminator(183, 18, 70, 156, 148, 109, 161, 34);

  public static Instruction withdraw(final AccountMeta invokedLockedVoterProgramMeta,
                                     // The [Locker] being exited from.
                                     final PublicKey lockerKey,
                                     // The [Escrow] that is being closed.
                                     final PublicKey escrowKey,
                                     // Authority of the [Escrow].
                                     final PublicKey escrowOwnerKey,
                                     // Tokens locked up in the [Escrow].
                                     final PublicKey escrowTokensKey,
                                     // Destination for the tokens to unlock.
                                     final PublicKey destinationTokensKey,
                                     // The payer to receive the rent refund.
                                     final PublicKey payerKey,
                                     // Token program.
                                     final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWrite(lockerKey),
      createWrite(escrowKey),
      createReadOnlySigner(escrowOwnerKey),
      createWrite(escrowTokensKey),
      createWrite(destinationTokensKey),
      createWritableSigner(payerKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedLockedVoterProgramMeta, keys, WITHDRAW_DISCRIMINATOR);
  }

  public static final Discriminator ACTIVATE_PROPOSAL_DISCRIMINATOR = toDiscriminator(90, 186, 203, 234, 70, 185, 191, 21);

  public static Instruction activateProposal(final AccountMeta invokedLockedVoterProgramMeta,
                                             // The [Locker].
                                             final PublicKey lockerKey,
                                             // The [Governor].
                                             final PublicKey governorKey,
                                             // The [Proposal].
                                             final PublicKey proposalKey,
                                             // The [govern] program.
                                             final PublicKey governProgramKey,
                                             // The smart wallet on the [Governor].
                                             final PublicKey smartWalletKey) {
    final var keys = List.of(
      createRead(lockerKey),
      createRead(governorKey),
      createWrite(proposalKey),
      createRead(governProgramKey),
      createReadOnlySigner(smartWalletKey)
    );

    return Instruction.createInstruction(invokedLockedVoterProgramMeta, keys, ACTIVATE_PROPOSAL_DISCRIMINATOR);
  }

  public static final Discriminator CAST_VOTE_DISCRIMINATOR = toDiscriminator(20, 212, 15, 189, 69, 180, 69, 151);

  public static Instruction castVote(final AccountMeta invokedLockedVoterProgramMeta,
                                     // The [Locker].
                                     final PublicKey lockerKey,
                                     // The [Escrow] that is voting.
                                     final PublicKey escrowKey,
                                     // Vote delegate of the [Escrow].
                                     final PublicKey voteDelegateKey,
                                     // The [Proposal] being voted on.
                                     final PublicKey proposalKey,
                                     // The [Vote].
                                     final PublicKey voteKey,
                                     // The [Governor].
                                     final PublicKey governorKey,
                                     // The [govern] program.
                                     final PublicKey governProgramKey,
                                     final int side) {
    final var keys = List.of(
      createRead(lockerKey),
      createRead(escrowKey),
      createReadOnlySigner(voteDelegateKey),
      createWrite(proposalKey),
      createWrite(voteKey),
      createRead(governorKey),
      createRead(governProgramKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(CAST_VOTE_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) side;

    return Instruction.createInstruction(invokedLockedVoterProgramMeta, keys, _data);
  }

  public record CastVoteIxData(Discriminator discriminator, int side) implements Borsh {  

    public static CastVoteIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static CastVoteIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var side = _data[i] & 0xFF;
      return new CastVoteIxData(discriminator, side);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) side;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_VOTE_DELEGATE_DISCRIMINATOR = toDiscriminator(46, 236, 241, 243, 251, 108, 156, 12);

  public static Instruction setVoteDelegate(final AccountMeta invokedLockedVoterProgramMeta,
                                            // The [Escrow].
                                            final PublicKey escrowKey,
                                            // The owner of the [Escrow].
                                            final PublicKey escrowOwnerKey,
                                            final PublicKey newDelegate) {
    final var keys = List.of(
      createWrite(escrowKey),
      createReadOnlySigner(escrowOwnerKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(SET_VOTE_DELEGATE_DISCRIMINATOR, _data, 0);
    newDelegate.write(_data, i);

    return Instruction.createInstruction(invokedLockedVoterProgramMeta, keys, _data);
  }

  public record SetVoteDelegateIxData(Discriminator discriminator, PublicKey newDelegate) implements Borsh {  

    public static SetVoteDelegateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static SetVoteDelegateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var newDelegate = readPubKey(_data, i);
      return new SetVoteDelegateIxData(discriminator, newDelegate);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      newDelegate.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_LOCKER_PARAMS_DISCRIMINATOR = toDiscriminator(106, 39, 132, 84, 254, 77, 161, 169);

  public static Instruction setLockerParams(final AccountMeta invokedLockedVoterProgramMeta,
                                            // The [Locker].
                                            final PublicKey lockerKey,
                                            // The [Governor].
                                            final PublicKey governorKey,
                                            // The smart wallet on the [Governor].
                                            final PublicKey smartWalletKey,
                                            final LockerParams params) {
    final var keys = List.of(
      createWrite(lockerKey),
      createRead(governorKey),
      createReadOnlySigner(smartWalletKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(SET_LOCKER_PARAMS_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLockedVoterProgramMeta, keys, _data);
  }

  public record SetLockerParamsIxData(Discriminator discriminator, LockerParams params) implements Borsh {  

    public static SetLockerParamsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 33;

    public static SetLockerParamsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = LockerParams.read(_data, i);
      return new SetLockerParamsIxData(discriminator, params);
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

  public static final Discriminator OPEN_PARTIAL_UNSTAKING_DISCRIMINATOR = toDiscriminator(201, 137, 207, 175, 79, 95, 220, 27);

  public static Instruction openPartialUnstaking(final AccountMeta invokedLockedVoterProgramMeta,
                                                 // [Locker].
                                                 final PublicKey lockerKey,
                                                 // [Escrow].
                                                 final PublicKey escrowKey,
                                                 // [Escrow].
                                                 final PublicKey partialUnstakeKey,
                                                 final PublicKey ownerKey,
                                                 // System program.
                                                 final PublicKey systemProgramKey,
                                                 final long amount,
                                                 final String memo) {
    final var keys = List.of(
      createWrite(lockerKey),
      createWrite(escrowKey),
      createWritableSigner(partialUnstakeKey),
      createWritableSigner(ownerKey),
      createRead(systemProgramKey)
    );

    final byte[] _memo = memo.getBytes(UTF_8);
    final byte[] _data = new byte[20 + Borsh.lenVector(_memo)];
    int i = writeDiscriminator(OPEN_PARTIAL_UNSTAKING_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    Borsh.writeVector(_memo, _data, i);

    return Instruction.createInstruction(invokedLockedVoterProgramMeta, keys, _data);
  }

  public record OpenPartialUnstakingIxData(Discriminator discriminator, long amount, String memo, byte[] _memo) implements Borsh {  

    public static OpenPartialUnstakingIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static OpenPartialUnstakingIxData createRecord(final Discriminator discriminator, final long amount, final String memo) {
      return new OpenPartialUnstakingIxData(discriminator, amount, memo, memo.getBytes(UTF_8));
    }

    public static OpenPartialUnstakingIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var memo = Borsh.string(_data, i);
      return new OpenPartialUnstakingIxData(discriminator, amount, memo, memo.getBytes(UTF_8));
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      i += Borsh.writeVector(_memo, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + Borsh.lenVector(_memo);
    }
  }

  public static final Discriminator MERGE_PARTIAL_UNSTAKING_DISCRIMINATOR = toDiscriminator(190, 154, 163, 153, 168, 115, 40, 173);

  public static Instruction mergePartialUnstaking(final AccountMeta invokedLockedVoterProgramMeta,
                                                  // [Locker].
                                                  final PublicKey lockerKey,
                                                  // [Escrow].
                                                  final PublicKey escrowKey,
                                                  // The [PartialUnstaking] that is being merged.
                                                  final PublicKey partialUnstakeKey,
                                                  final PublicKey ownerKey) {
    final var keys = List.of(
      createWrite(lockerKey),
      createWrite(escrowKey),
      createWrite(partialUnstakeKey),
      createWritableSigner(ownerKey)
    );

    return Instruction.createInstruction(invokedLockedVoterProgramMeta, keys, MERGE_PARTIAL_UNSTAKING_DISCRIMINATOR);
  }

  public static final Discriminator WITHDRAW_PARTIAL_UNSTAKING_DISCRIMINATOR = toDiscriminator(201, 202, 137, 124, 2, 3, 245, 87);

  public static Instruction withdrawPartialUnstaking(final AccountMeta invokedLockedVoterProgramMeta,
                                                     // The [Locker] being exited from.
                                                     final PublicKey lockerKey,
                                                     // The [Escrow] that is being closed.
                                                     final PublicKey escrowKey,
                                                     // The [PartialUnstaking] that is being withdraw.
                                                     final PublicKey partialUnstakeKey,
                                                     // Authority of the [Escrow].
                                                     final PublicKey ownerKey,
                                                     // Tokens locked up in the [Escrow].
                                                     final PublicKey escrowTokensKey,
                                                     // Destination for the tokens to unlock.
                                                     final PublicKey destinationTokensKey,
                                                     // The payer to receive the rent refund.
                                                     final PublicKey payerKey,
                                                     // Token program.
                                                     final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWrite(lockerKey),
      createWrite(escrowKey),
      createWrite(partialUnstakeKey),
      createReadOnlySigner(ownerKey),
      createWrite(escrowTokensKey),
      createWrite(destinationTokensKey),
      createWritableSigner(payerKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedLockedVoterProgramMeta, keys, WITHDRAW_PARTIAL_UNSTAKING_DISCRIMINATOR);
  }

  private LockedVoterProgram() {
  }
}
