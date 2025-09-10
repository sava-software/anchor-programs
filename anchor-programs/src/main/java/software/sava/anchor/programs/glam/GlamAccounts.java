package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.config.anchor.GlamConfigPDAs;
import software.sava.anchor.programs.glam.proxy.DynamicGlamAccountFactory;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import systems.glam.ix.proxy.TransactionMapper;

import java.nio.file.Path;

public interface GlamAccounts {

  GlamAccounts MAIN_NET = createAccounts(
      "GLAMbTqav9N9witRjswJ8enwp9vv5G8bsSJ2kPJ4rcyc",
      "GLAMpaME8wdTEzxtiYEAa5yD8fZbxZiz2hNtV58RZiEz",
      "gConFzxKL9USmwTdJoeQJvfKmqhJ2CyUaXTyQ8v9TGX",
      "GM1NtvvnSXUptTrMCqbogAdZJydZSNv98DoU5AZVLmGh",
      "po1iCYakK3gHCLbuju4wGzFowTMpAJxkqK1iwUqMonY",
      "G1NTsQ36mjPe89HtPYqxKsjY5HmYsDR6CbD2gd2U2pta",
      "G1NTdrBmBpW43msRQmsf7qXSw3MFBNaqJcAkGiRmRq2F",
      "G1NTkDEUR3pkEqGCKZtmtmVzCUEdYa86pezHkwYbLyde"
  );

  static GlamAccounts createAccounts(final PublicKey program,
                                     final PublicKey protocolProgram,
                                     final PublicKey configProgram,
                                     final PublicKey mintProgram,
                                     final PublicKey policyProgram,
                                     final PublicKey tokenExtensionProgram,
                                     final PublicKey driftExtensionProgram,
                                     final PublicKey kaminoExtensionProgram) {
    return new GlamAccountsRecord(
        program,
        AccountMeta.createInvoked(program),
        AccountMeta.createInvoked(protocolProgram),
        configProgram,
        mintProgram,
        policyProgram,
        tokenExtensionProgram,
        driftExtensionProgram,
        kaminoExtensionProgram
    );
  }

  static GlamAccounts createAccounts(final String program,
                                     final String protocolProgram,
                                     final String configProgram,
                                     final String mintProgram,
                                     final String policyProgram,
                                     final String tokenExtensionProgram,
                                     final String driftExtensionProgram,
                                     final String kaminoExtensionProgram) {
    return createAccounts(
        PublicKey.fromBase58Encoded(program),
        PublicKey.fromBase58Encoded(protocolProgram),
        PublicKey.fromBase58Encoded(configProgram),
        PublicKey.fromBase58Encoded(mintProgram),
        PublicKey.fromBase58Encoded(policyProgram),
        PublicKey.fromBase58Encoded(tokenExtensionProgram),
        PublicKey.fromBase58Encoded(driftExtensionProgram),
        PublicKey.fromBase58Encoded(kaminoExtensionProgram)
    );
  }

  PublicKey program();

  AccountMeta invokedProgram();

  AccountMeta invokedProtocolProgram();

  default PublicKey protocolProgram() {
    return invokedProtocolProgram().publicKey();
  }

  PublicKey configProgram();

  PublicKey mintProgram();

  PublicKey policyProgram();

  default ProgramDerivedAddress globalConfigPDA() {
    return GlamConfigPDAs.globalConfigPDA(configProgram());
  }

  ProgramDerivedAddress mintPDA(final PublicKey glamPublicKey, final int shareClassId);

  default TransactionMapper<GlamVaultAccounts> createMapper(final Path mappingsDirectory,
                                                            final DynamicGlamAccountFactory dynamicGlamAccountFactory) {
    return GlamVaultAccounts.createMapper(invokedProgram(), mappingsDirectory, dynamicGlamAccountFactory);
  }

  PublicKey tokenExtensionProgram();

  PublicKey driftExtensionProgram();

  PublicKey kaminoExtensionProgram();
}
