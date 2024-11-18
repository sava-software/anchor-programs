package software.sava.anchor.programs.jupiter;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.rpc.Filter;

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
      "CVMdMd79no569tjc5Sq7kzz8isbfCcFyBS5TLGsrZ5dN"
      );

  static JupiterAccounts createAccounts(final PublicKey swapProgram,
                                        final PublicKey limitOrderProgram,
                                        final PublicKey dcaProgram,
                                        final PublicKey voteProgram,
                                        final PublicKey govProgram,
                                        final PublicKey jupTokenMint,
                                        final PublicKey jupLockerAccount) {
    return new JupiterccountsRecord(
        swapProgram, createInvoked(swapProgram),
        limitOrderProgram, createInvoked(limitOrderProgram),
        dcaProgram, createInvoked(dcaProgram),
        voteProgram, createInvoked(voteProgram),
        govProgram, createInvoked(govProgram),
        jupTokenMint,
        jupLockerAccount
    );
  }

  static JupiterAccounts createAccounts(final String swapProgram,
                                        final String limitOrderProgram,
                                        final String dcaProgram,
                                        final String voteProgram,
                                        final String govProgram,
                                        final String jupTokenMint,
                                        final String jupLockerAccount) {
    return createAccounts(
        fromBase58Encoded(swapProgram),
        fromBase58Encoded(limitOrderProgram),
        fromBase58Encoded(dcaProgram),
        fromBase58Encoded(voteProgram),
        fromBase58Encoded(govProgram),
        fromBase58Encoded(jupTokenMint),
        fromBase58Encoded(jupLockerAccount)
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

  PublicKey jupLockerAccount();
}
