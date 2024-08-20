package software.sava.anchor.programs.jupiter;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

import static software.sava.core.accounts.PublicKey.fromBase58Encoded;
import static software.sava.core.accounts.meta.AccountMeta.createInvoked;

public interface JupiterAccounts {

  JupiterAccounts MAIN_NET = createAccounts(
      "JUP6LkbZbjS1jKKwapdHNy74zcZ3tLUZoi5QNyVTaV4",
      "jupoNjAxXgZ4rjzxzPMP4oxduvQsQtZzyknqvzYNrNu",
      "DCA265Vj8a9CEuX1eb1LWRnDT7uK6q1xMipnNyatn23M"
  );

  static JupiterAccounts createAccounts(final PublicKey swapProgram,
                                        final PublicKey limitOrderProgram,
                                        final PublicKey dcaProgram) {
    return new JupiterccountsRecord(
        swapProgram, createInvoked(swapProgram),
        limitOrderProgram, createInvoked(limitOrderProgram),
        dcaProgram, createInvoked(dcaProgram)
    );
  }

  static JupiterAccounts createAccounts(final String swapProgram,
                                        final String limitOrderProgram,
                                        final String dcaProgram) {
    return createAccounts(
        fromBase58Encoded(swapProgram),
        fromBase58Encoded(limitOrderProgram),
        fromBase58Encoded(dcaProgram)
    );
  }

  PublicKey swapProgram();

  AccountMeta invokedSwapProgram();

  PublicKey limitOrderProgram();

  AccountMeta invokedLimitOrderProgram();

  PublicKey dcaProgram();

  AccountMeta invokedDCAProgram();
}
