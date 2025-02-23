package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.jupiter.JupiterAccounts;
import software.sava.anchor.programs.jupiter.voter.JupiterVoteClient;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;

public interface GlamJupiterVoteClient extends JupiterVoteClient {

  static GlamJupiterVoteClient createClient(final SolanaAccounts solanaAccounts,
                                            final JupiterAccounts jupiterAccounts,
                                            final GlamAccounts glamAccounts,
                                            final PublicKey glamKey,
                                            final PublicKey glamVaultKey,
                                            final PublicKey feePayer) {
    return new GlamJupiterVoteClientImpl(
        solanaAccounts,
        jupiterAccounts,
        glamAccounts,
        glamKey,
        glamVaultKey,
        feePayer
    );
  }

  static GlamJupiterVoteClient createClient(final PublicKey glamKey,
                                            final PublicKey glamVaultKey,
                                            final PublicKey feePayer) {
    return createClient(
        SolanaAccounts.MAIN_NET,
        JupiterAccounts.MAIN_NET,
        GlamAccounts.MAIN_NET,
        glamKey,
        glamVaultKey,
        feePayer
    );
  }

  GlamAccounts glamAccounts();

  PublicKey glamKey();

  Instruction castVote(PublicKey escrowKey,
                       PublicKey voteDelegate,
                       PublicKey proposal,
                       PublicKey voteKey,
                       int side,
                       int expectedSide);
}
