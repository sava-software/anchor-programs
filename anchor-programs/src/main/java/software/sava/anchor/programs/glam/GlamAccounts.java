package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamProtocolPDAs;
import software.sava.anchor.programs.glam.proxy.DynamicGlamAccountFactory;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import systems.glam.ix.proxy.TransactionMapper;

import java.nio.file.Path;

public interface GlamAccounts {

  GlamAccounts MAIN_NET = createAccounts(
      "GLAMbTqav9N9witRjswJ8enwp9vv5G8bsSJ2kPJ4rcyc",
      "gConFzxKL9USmwTdJoeQJvfKmqhJ2CyUaXTyQ8v9TGX",
      "GM1NtvvnSXUptTrMCqbogAdZJydZSNv98DoU5AZVLmGh"
  );

  static GlamAccounts createAccounts(final PublicKey program,
                                     final PublicKey configProgram,
                                     final PublicKey mintProgram) {
    final var glamConfigKey = GlamProtocolPDAs.glamConfigPDA(configProgram).publicKey();
    return new GlamAccountsRecord(
        program,
        AccountMeta.createInvoked(program),
        configProgram,
        glamConfigKey,
        mintProgram
    );
  }

  static GlamAccounts createAccounts(final String program,
                                     final String configProgram,
                                     final String mintProgram) {
    return createAccounts(
        PublicKey.fromBase58Encoded(program),
        PublicKey.fromBase58Encoded(configProgram),
        PublicKey.fromBase58Encoded(mintProgram)
    );
  }

  PublicKey program();

  AccountMeta invokedProgram();

  PublicKey configProgram();

  PublicKey glamConfigKey();

  ProgramDerivedAddress mintPDA(final PublicKey glamPublicKey, final int shareClassId);

  default TransactionMapper<GlamVaultAccounts> createMapper(final Path mappingsDirectory,
                                                            final DynamicGlamAccountFactory dynamicGlamAccountFactory) {
    return GlamVaultAccounts.createMapper(invokedProgram(), mappingsDirectory, dynamicGlamAccountFactory);
  }
}
