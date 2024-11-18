package software.sava.anchor.programs.jupiter.governance;

import software.sava.anchor.programs.jupiter.JupiterAccounts;
import software.sava.anchor.programs.jupiter.staking.anchor.LockedVoterProgram;
import software.sava.anchor.programs.jupiter.staking.anchor.types.Escrow;
import software.sava.anchor.programs.jupiter.staking.anchor.types.Locker;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;

final class JupiterVoteClientImpl implements JupiterVoteClient {

  private final SolanaAccounts solanaAccounts;
  private final JupiterAccounts accounts;

  JupiterVoteClientImpl(final SolanaAccounts solanaAccounts,
                        final JupiterAccounts accounts) {
    this.solanaAccounts = solanaAccounts;
    this.accounts = accounts;
  }

  @Override
  public SolanaAccounts solanaAccounts() {
    return solanaAccounts;
  }

  @Override
  public JupiterAccounts jupiterAccounts() {
    return accounts;
  }

  @Override
  public Instruction castVote(final Locker locker,
                              final Escrow escrow,
                              final PublicKey voteDelegate,
                              final PublicKey proposal,
                              final PublicKey vote,
                              final int side) {
    return LockedVoterProgram.castVote(
        accounts.invokedVoteProgram(),
        locker._address(),
        escrow._address(),
        voteDelegate,
        proposal,
        vote,
        locker.governor(),
        accounts.govProgram(),
        side
    );
  }
}
