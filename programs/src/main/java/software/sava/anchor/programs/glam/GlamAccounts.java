package software.sava.anchor.programs.glam;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

public interface GlamAccounts {

  GlamAccounts MAIN_NET = createAccounts(
      "GLAMpLuXu78TA4ao3DPZvT1zQ7woxoQ8ahdYbhnqY9mP"
  );

  static GlamAccounts createAccounts(final PublicKey program) {
    return new GlamAccountsRecord(
        program,
        AccountMeta.createInvoked(program)
    );
  }

  static GlamAccounts createAccounts(final String program) {
    return createAccounts(
        PublicKey.fromBase58Encoded(program)
    );
  }

  PublicKey program();

  AccountMeta invokedProgram();
}
