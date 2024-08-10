package software.sava.anchor.programs.glam;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

public interface GlamAccounts {

  GlamAccounts MAIN_NET = createAccounts(
      "Gco1pcjxCMYjKJjSNJ7mKV7qezeUTE7arXJgy7PAPNRc"
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
