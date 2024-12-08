package software.sava.anchor.programs.jupiter;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.encoding.ByteUtil;
import software.sava.core.rpc.Filter;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static software.sava.core.accounts.PublicKey.findProgramAddress;
import static software.sava.core.accounts.PublicKey.fromBase58Encoded;
import static software.sava.core.accounts.meta.AccountMeta.createInvoked;

public interface JupiterAccounts {

  int LOCKER_ACCOUNT_LENGTH = 665;
  Filter LOCKER_ACCOUNT_LENGTH_FILTER = Filter.createDataSizeFilter(LOCKER_ACCOUNT_LENGTH);

  JupiterAccounts MAIN_NET = createAccounts(
      "JUP6LkbZbjS1jKKwapdHNy74zcZ3tLUZoi5QNyVTaV4",
      "jupoNjAxXgZ4rjzxzPMP4oxduvQsQtZzyknqvzYNrNu",
      "DCA265Vj8a9CEuX1eb1LWRnDT7uK6q1xMipnNyatn23M",
      "voTpe3tHQ7AjQHMapgSue2HJFAh2cGsdokqN3XqmVSj",
      "GovaE4iu227srtG2s3tZzB4RmWBzw8sTwrCLZz7kN7rY",
      "JUPyiwrYJFskUPiHa7hkeR8VUtAeFoSYbKedZNsDvCN",
      "bJ1TRoFo2P6UHVwqdiipp6Qhp2HaaHpLowZ5LHet8Gm"
  );

  static JupiterAccounts createAccounts(final PublicKey swapProgram,
                                        final PublicKey limitOrderProgram,
                                        final PublicKey dcaProgram,
                                        final PublicKey voteProgram,
                                        final PublicKey govProgram,
                                        final PublicKey jupTokenMint,
                                        final PublicKey jupBaseKey) {
    return new JupiterccountsRecord(
        swapProgram, createInvoked(swapProgram),
        limitOrderProgram, createInvoked(limitOrderProgram),
        dcaProgram, createInvoked(dcaProgram),
        voteProgram, createInvoked(voteProgram),
        govProgram, createInvoked(govProgram),
        jupTokenMint,
        jupBaseKey,
        deriveLocker(voteProgram, jupBaseKey).publicKey(),
        deriveGovernor(govProgram, jupBaseKey).publicKey()
    );
  }

  static JupiterAccounts createAccounts(final String swapProgram,
                                        final String limitOrderProgram,
                                        final String dcaProgram,
                                        final String voteProgram,
                                        final String govProgram,
                                        final String jupTokenMint,
                                        final String jupBaseKey) {
    return createAccounts(
        fromBase58Encoded(swapProgram),
        fromBase58Encoded(limitOrderProgram),
        fromBase58Encoded(dcaProgram),
        fromBase58Encoded(voteProgram),
        fromBase58Encoded(govProgram),
        fromBase58Encoded(jupTokenMint),
        fromBase58Encoded(jupBaseKey)
    );
  }

  static ProgramDerivedAddress deriveLocker(final PublicKey voteProgram, final PublicKey base) {
    return findProgramAddress(
        List.of(
            "Locker".getBytes(UTF_8),
            base.toByteArray()
        ),
        voteProgram
    );
  }

  static ProgramDerivedAddress deriveLocker(final JupiterAccounts jupiterAccounts, final PublicKey base) {
    return deriveLocker(jupiterAccounts.voteProgram(), base);
  }

  default ProgramDerivedAddress deriveJupLocker() {
    return deriveLocker(this, this.jupBaseKey());
  }

  static ProgramDerivedAddress deriveEscrow(final JupiterAccounts jupiterAccounts,
                                            final PublicKey locker,
                                            final PublicKey escrowOwner) {
    return findProgramAddress(
        List.of(
            "Escrow".getBytes(UTF_8),
            locker.toByteArray(),
            escrowOwner.toByteArray()
        ),
        jupiterAccounts.voteProgram()
    );
  }

  default ProgramDerivedAddress deriveEscrow(final PublicKey locker, final PublicKey escrowOwner) {
    return deriveEscrow(this, locker, escrowOwner);
  }

  default ProgramDerivedAddress deriveEscrow(final PublicKey escrowOwner) {
    return deriveEscrow(this, lockerKey(), escrowOwner);
  }

  static ProgramDerivedAddress deriveGovernor(final PublicKey govProgram, final PublicKey base) {
    return findProgramAddress(
        List.of(
            "Governor".getBytes(UTF_8),
            base.toByteArray()
        ),
        govProgram
    );
  }

  static ProgramDerivedAddress deriveGovernor(final JupiterAccounts jupiterAccounts, final PublicKey base) {
    return deriveGovernor(jupiterAccounts.govProgram(), base);
  }

  default ProgramDerivedAddress deriveGovernor() {
    return deriveGovernor(this, this.jupBaseKey());
  }

  static ProgramDerivedAddress deriveProposal(final JupiterAccounts jupiterAccounts,
                                              final PublicKey governor,
                                              final long proposalCount) {
    final byte[] proposalBytes = new byte[Long.BYTES];
    ByteUtil.putInt64LE(proposalBytes, 0, proposalCount);
    return findProgramAddress(
        List.of(
            "Proposal".getBytes(UTF_8),
            governor.toByteArray(),
            proposalBytes
        ),
        jupiterAccounts.govProgram()
    );
  }

  default ProgramDerivedAddress deriveProposal(final PublicKey governor, final long proposalCount) {
    return deriveProposal(this, governor, proposalCount);
  }

  default ProgramDerivedAddress deriveProposal(final long proposalCount) {
    return deriveProposal(this, governorKey(), proposalCount);
  }

  static ProgramDerivedAddress deriveProposalMeta(final JupiterAccounts jupiterAccounts, final PublicKey proposal) {
    return findProgramAddress(
        List.of(
            "ProposalMeta".getBytes(UTF_8),
            proposal.toByteArray()
        ),
        jupiterAccounts.govProgram()
    );
  }

  default ProgramDerivedAddress deriveProposalMeta(final PublicKey proposal) {
    return deriveProposalMeta(this, proposal);
  }

  static ProgramDerivedAddress deriveOptionProposalMeta(final JupiterAccounts jupiterAccounts,
                                                        final PublicKey proposal) {
    return findProgramAddress(
        List.of(
            "OptionProposalMeta".getBytes(UTF_8),
            proposal.toByteArray()
        ),
        jupiterAccounts.govProgram()
    );
  }

  default ProgramDerivedAddress deriveOptionProposalMeta(final PublicKey proposal) {
    return deriveOptionProposalMeta(this, proposal);
  }

  static ProgramDerivedAddress deriveVote(final JupiterAccounts jupiterAccounts,
                                          final PublicKey proposal,
                                          final PublicKey voter) {
    return findProgramAddress(
        List.of(
            "Vote".getBytes(UTF_8),
            proposal.toByteArray(),
            voter.toByteArray()
        ),
        jupiterAccounts.govProgram()
    );
  }

  default ProgramDerivedAddress deriveVote(final PublicKey proposal, final PublicKey voter) {
    return deriveVote(this, proposal, voter);
  }

  static ProgramDerivedAddress deriveClaimStatus(final PublicKey programId,
                                                 final long index,
                                                 final PublicKey distributor) {
    final byte[] indexBytes = new byte[Long.BYTES];
    ByteUtil.putInt64LE(indexBytes, 0, index);
    return findProgramAddress(
        List.of(
            "ClaimStatus".getBytes(UTF_8),
            indexBytes,
            distributor.toByteArray()
        ),
        programId
    );
  }

  PublicKey swapProgram();

  AccountMeta invokedSwapProgram();

  PublicKey limitOrderProgram();

  AccountMeta invokedLimitOrderProgram();

  PublicKey dcaProgram();

  AccountMeta invokedDCAProgram();

  PublicKey voteProgram();

  AccountMeta invokedVoteProgram();

  PublicKey govProgram();

  AccountMeta invokedGovProgram();

  PublicKey jupTokenMint();

  PublicKey jupBaseKey();

  PublicKey lockerKey();

  PublicKey governorKey();
}
