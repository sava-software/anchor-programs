package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.config.anchor.GlamConfigPDAs;
import software.sava.anchor.programs.glam.drift.anchor.ExtDriftPDAs;
import software.sava.anchor.programs.glam.kamino.anchor.ExtKaminoPDAs;
import software.sava.anchor.programs.glam.protocol.anchor.types.StateAccount;
import software.sava.anchor.programs.glam.proxy.DynamicGlamAccountFactory;
import software.sava.anchor.programs.glam.spl.anchor.ExtSplPDAs;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import systems.glam.ix.proxy.TransactionMapper;

import java.nio.file.Path;
import java.util.Map;

import static software.sava.core.accounts.meta.AccountMeta.createRead;

public interface GlamAccounts {

  // https://github.com/glamsystems/glam-sdk/tree/main/idl
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
                                     final PublicKey splExtensionProgram,
                                     final PublicKey driftExtensionProgram,
                                     final PublicKey kaminoExtensionProgram) {
    final var splExtensionAuthority = createRead(ExtSplPDAs.integrationAuthorityPDA(splExtensionProgram).publicKey());
    final var driftExtensionAuthority = createRead(ExtDriftPDAs.integrationAuthorityPDA(driftExtensionProgram).publicKey());
    final var kaminoExtensionAuthority = createRead(ExtKaminoPDAs.integrationAuthorityPDA(kaminoExtensionProgram).publicKey());
    final var extensionAuthorities = Map.of(
        splExtensionProgram, splExtensionAuthority,
        driftExtensionProgram, driftExtensionAuthority,
        kaminoExtensionProgram, kaminoExtensionAuthority
    );
    return new GlamAccountsRecord(
        program,
        AccountMeta.createInvoked(program),
        AccountMeta.createInvoked(protocolProgram),
        configProgram,
        mintProgram,
        policyProgram,
        splExtensionProgram,
        splExtensionAuthority,
        driftExtensionProgram,
        driftExtensionAuthority,
        kaminoExtensionProgram,
        kaminoExtensionAuthority,
        extensionAuthorities
    );
  }

  static GlamAccounts createAccounts(final String program,
                                     final String protocolProgram,
                                     final String configProgram,
                                     final String mintProgram,
                                     final String policyProgram,
                                     final String splExtensionProgram,
                                     final String driftExtensionProgram,
                                     final String kaminoExtensionProgram) {
    return createAccounts(
        PublicKey.fromBase58Encoded(program),
        PublicKey.fromBase58Encoded(protocolProgram),
        PublicKey.fromBase58Encoded(configProgram),
        PublicKey.fromBase58Encoded(mintProgram),
        PublicKey.fromBase58Encoded(policyProgram),
        PublicKey.fromBase58Encoded(splExtensionProgram),
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

  PublicKey splExtensionProgram();

  AccountMeta readSplExtensionAuthority();

  PublicKey driftExtensionProgram();

  AccountMeta readDriftExtensionAuthority();

  PublicKey kaminoExtensionProgram();

  AccountMeta readKaminoExtensionAuthority();

  Map<PublicKey, AccountMeta> extensionAuthorities();

  static boolean usesIntegration(final StateAccount stateAccount,
                                 final PublicKey program,
                                 final int bitFlag) {
    for (final var acl : stateAccount.integrationAcls()) {
      if (acl.integrationProgram().equals(program)) {
        final int mask = acl.protocolsBitmask();
        return (mask & bitFlag) == bitFlag;
      }
    }
    return false;
  }
}
