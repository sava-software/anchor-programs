package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.proxy.DynamicGlamAccountFactory;
import software.sava.anchor.programs.glam_v0.GlamV0AccountsRecord;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import systems.glam.ix.proxy.TransactionMapper;

public interface GlamAccounts {

  GlamAccounts MAIN_NET = createAccounts(
      "GLAMbTqav9N9witRjswJ8enwp9vv5G8bsSJ2kPJ4rcyc"
  );

  GlamAccounts MAIN_NET_VO = GlamAccounts.createV0Accounts(
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

  static GlamAccounts createV0Accounts(final PublicKey program) {
    return new GlamV0AccountsRecord(
        program,
        AccountMeta.createInvoked(program)
    );
  }

  static GlamAccounts createV0Accounts(final String program) {
    return createV0Accounts(
        PublicKey.fromBase58Encoded(program)
    );
  }

  PublicKey program();

  AccountMeta invokedProgram();

  ProgramDerivedAddress mintPDA(final PublicKey glamPublicKey, final int shareClassId);

  default TransactionMapper<GlamVaultAccounts> createMapper(final DynamicGlamAccountFactory dynamicGlamAccountFactory) {
    return GlamVaultAccounts.createMapper(invokedProgram(), dynamicGlamAccountFactory);
  }
}
