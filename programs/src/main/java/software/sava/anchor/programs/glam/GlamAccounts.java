package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.proxy.DynamicGlamAccountFactory;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import systems.glam.ix.proxy.TransactionMapper;

import java.nio.file.Path;

public interface GlamAccounts {

  GlamAccounts MAIN_NET = createAccounts(
      "GLAMbTqav9N9witRjswJ8enwp9vv5G8bsSJ2kPJ4rcyc"
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

  ProgramDerivedAddress mintPDA(final PublicKey glamPublicKey, final int shareClassId);

  default TransactionMapper<GlamVaultAccounts> createMapper(final Path mappingsDirectory,
                                                            final DynamicGlamAccountFactory dynamicGlamAccountFactory) {
    return GlamVaultAccounts.createMapper(invokedProgram(), mappingsDirectory, dynamicGlamAccountFactory);
  }
}
