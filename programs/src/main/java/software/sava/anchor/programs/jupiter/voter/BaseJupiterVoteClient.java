package software.sava.anchor.programs.jupiter.voter;

import software.sava.anchor.programs.jupiter.JupiterAccounts;
import software.sava.anchor.programs.jupiter.voter.anchor.types.Escrow;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;

import static software.sava.solana.programs.token.AssociatedTokenProgram.findATA;

public abstract class BaseJupiterVoteClient implements JupiterVoteClient {

  protected final SolanaAccounts solanaAccounts;
  protected final JupiterAccounts jupiterAccounts;
  protected final PublicKey escrowOwnerKey;
  protected final PublicKey ownerJupATA;
  protected final PublicKey escrowKey;
  protected final PublicKey escrowJupATA;

  protected BaseJupiterVoteClient(final SolanaAccounts solanaAccounts,
                                  final JupiterAccounts jupiterAccounts,
                                  final PublicKey escrowOwnerKey) {
    this.solanaAccounts = solanaAccounts;
    this.jupiterAccounts = jupiterAccounts;
    this.escrowOwnerKey = escrowOwnerKey;
    final var jupTokenMint = jupiterAccounts.jupTokenMint();
    this.ownerJupATA = findATA(solanaAccounts, escrowOwnerKey, jupTokenMint).publicKey();
    this.escrowKey = jupiterAccounts.deriveEscrow(escrowOwnerKey).publicKey();
    this.escrowJupATA = findATA(solanaAccounts, escrowKey, jupTokenMint).publicKey();
  }

  @Override
  public final SolanaAccounts solanaAccounts() {
    return solanaAccounts;
  }

  @Override
  public final JupiterAccounts jupiterAccounts() {
    return jupiterAccounts;
  }

  @Override
  public final PublicKey escrowOwnerKey() {
    return escrowOwnerKey;
  }

  @Override
  public final PublicKey escrowOwnerKeyATA() {
    return ownerJupATA;
  }

  @Override
  public final PublicKey escrowKey() {
    return escrowKey;
  }

  @Override
  public final PublicKey escrowATA() {
    return escrowJupATA;
  }

  @Override
  public final PublicKey deriveVoteKey(final PublicKey proposal) {
    return jupiterAccounts.deriveVote(proposal, escrowOwnerKey).publicKey();
  }

  @Override
  public final Instruction newEscrow(final PublicKey payer) {
    return newEscrow(escrowOwnerKey, escrowKey, payer);
  }

  @Override
  public final Instruction newVote(final PublicKey proposal,
                                   final PublicKey voteKey,
                                   final PublicKey payer) {
    return newVote(proposal, voteKey, payer, escrowOwnerKey);
  }

  @Override
  public final Instruction newVote(final PublicKey proposal, final PublicKey payer) {
    final var voteKey = deriveVoteKey(proposal);
    return newVote(
        proposal,
        voteKey,
        payer,
        escrowKey
    );
  }

  @Override
  public final Instruction castVote(final PublicKey proposal,
                                    final PublicKey voteKey,
                                    final int side) {
    return castVote(
        escrowKey,
        escrowOwnerKey,
        proposal,
        voteKey,
        side
    );
  }

  @Override
  public final Instruction setVoteDelegate(final PublicKey newDelegate) {
    return setVoteDelegate(escrowOwnerKey, escrowKey, newDelegate);
  }

  @Override
  public final Instruction increaseLockedAmount(final PublicKey escrowTokensKey,
                                                final PublicKey payerKey,
                                                final PublicKey sourceTokensKey,
                                                final long amount) {
    return increaseLockedAmount(
        escrowKey,
        escrowTokensKey,
        payerKey,
        sourceTokensKey,
        amount
    );
  }

  @Override
  public final Instruction increaseLockedAmount(final PublicKey payerKey, final long amount) {
    return increaseLockedAmount(
        escrowJupATA,
        payerKey,
        ownerJupATA,
        amount
    );
  }


  @Override
  public final Instruction extendLockDuration(final Escrow escrow, final long duration) {
    return extendLockDuration(
        escrow.locker(),
        escrow._address(),
        escrow.owner(),
        duration
    );
  }

  @Override
  public final Instruction extendLockDuration(final long duration) {
    return extendLockDuration(
        jupiterAccounts.lockerKey(),
        escrowKey,
        escrowOwnerKey,
        duration
    );
  }

  @Override
  public final Instruction toggleMaxLock(final Escrow escrow, final boolean maxLock) {
    return toggleMaxLock(
        escrow.locker(),
        escrow._address(),
        escrow.owner(),
        maxLock
    );
  }

  @Override
  public final Instruction toggleMaxLock(final PublicKey escrowOwner,
                                         final PublicKey escrowKey,
                                         final boolean maxLock) {
    return toggleMaxLock(
        jupiterAccounts.lockerKey(),
        escrowKey,
        escrowOwner,
        maxLock
    );
  }

  @Override
  public final Instruction toggleMaxLock(final boolean maxLock) {
    return toggleMaxLock(
        jupiterAccounts.lockerKey(),
        escrowKey,
        escrowOwnerKey,
        maxLock
    );
  }

  @Override
  public final Instruction withdraw(final Escrow escrow,
                                    final PublicKey payerKey,
                                    final PublicKey destinationTokensKey) {
    return withdraw(
        escrow.locker(),
        escrow._address(),
        escrow.owner(),
        escrow.tokens(),
        payerKey,
        destinationTokensKey
    );
  }

  @Override
  public final Instruction withdraw(final PublicKey payerKey) {
    return withdraw(
        jupiterAccounts.lockerKey(),
        escrowKey,
        escrowOwnerKey,
        escrowJupATA,
        payerKey,
        ownerJupATA
    );
  }

  @Override
  public final Instruction withdraw() {
    return withdraw(escrowOwnerKey);
  }

  @Override
  public final Instruction withdraw(final Escrow escrow) {
    return withdraw(escrow, escrow.owner(), ownerJupATA);
  }

  @Override
  public final Instruction openPartialUnstaking(final Escrow escrow,
                                                final PublicKey partialUnstakeKey,
                                                final long amount,
                                                final String memo) {
    return openPartialUnstaking(
        escrow.locker(),
        escrow._address(),
        escrow.owner(),
        partialUnstakeKey,
        amount,
        memo
    );
  }

  @Override
  public final Instruction openPartialUnstaking(final PublicKey partialUnstakeKey,
                                                final long amount,
                                                final String memo) {
    return openPartialUnstaking(
        jupiterAccounts.lockerKey(),
        escrowKey,
        escrowOwnerKey,
        partialUnstakeKey,
        amount,
        memo
    );
  }

  @Override
  public final Instruction mergePartialUnstaking(final Escrow escrow, final PublicKey partialUnstakeKey) {
    return mergePartialUnstaking(
        escrow.locker(),
        escrow._address(),
        escrow.owner(),
        partialUnstakeKey
    );
  }

  @Override
  public final Instruction mergePartialUnstaking(final PublicKey partialUnstakeKey) {
    return mergePartialUnstaking(
        jupiterAccounts.lockerKey(),
        escrowKey,
        escrowOwnerKey,
        partialUnstakeKey
    );
  }

  @Override
  public final Instruction withdrawPartialUnstaking(final Escrow escrow,
                                                    final PublicKey partialUnstakeKey,
                                                    final PublicKey payerKey,
                                                    final PublicKey destinationTokensKey) {
    return withdrawPartialUnstaking(
        escrow.locker(),
        escrow._address(),
        escrow.owner(),
        escrow.tokens(),
        partialUnstakeKey,
        payerKey,
        destinationTokensKey
    );
  }

  @Override
  public final Instruction withdrawPartialUnstaking(final PublicKey partialUnstakeKey, final PublicKey payerKey) {
    return withdrawPartialUnstaking(
        jupiterAccounts.lockerKey(),
        escrowKey,
        escrowOwnerKey,
        escrowJupATA,
        partialUnstakeKey,
        payerKey,
        ownerJupATA
    );
  }
}
