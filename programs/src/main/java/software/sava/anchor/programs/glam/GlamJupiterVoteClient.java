package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.jupiter.JupiterAccounts;
import software.sava.anchor.programs.jupiter.voter.JupiterVoteClient;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;

public interface GlamJupiterVoteClient extends JupiterVoteClient {

  static GlamJupiterVoteClient createClient(final SolanaAccounts solanaAccounts,
                                            final JupiterAccounts jupiterAccounts,
                                            final GlamAccounts glamAccounts,
                                            final PublicKey glamKey,
                                            final PublicKey treasuryKey) {
    return new GlamJupiterVoteClientImpl(
        solanaAccounts,
        jupiterAccounts,
        glamAccounts,
        glamKey,
        treasuryKey
    );
  }

  static GlamJupiterVoteClient createClient(final PublicKey glamKey, final PublicKey treasuryKey) {
    return createClient(
        SolanaAccounts.MAIN_NET,
        JupiterAccounts.MAIN_NET,
        GlamAccounts.MAIN_NET,
        glamKey,
        treasuryKey
    );
  }

  PublicKey glamKey();
}