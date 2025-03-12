package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamPDAs;
import software.sava.anchor.programs.glam.proxy.DynamicGlamAccountFactory;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import systems.comodal.jsoniter.JsonIterator;
import systems.glam.ix.proxy.IndexedAccountMeta;
import systems.glam.ix.proxy.ProgramMapConfig;
import systems.glam.ix.proxy.TransactionMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public interface GlamVaultAccounts {

  static GlamVaultAccounts createAccounts(final GlamAccounts glamAccounts,
                                          final PublicKey feePayer,
                                          final PublicKey glamPublicKey) {
    final var program = glamAccounts.program();
    final var vaultPDA = GlamPDAs.glamVaultPDA(program, glamPublicKey);
    final var metadataPDA = GlamPDAs.metadataPDA(program, glamPublicKey);
    return new GlamVaultAccountsRecord(
        glamAccounts,
        AccountMeta.createReadOnlySigner(feePayer),
        AccountMeta.createWritableSigner(feePayer),
        AccountMeta.createRead(glamPublicKey),
        AccountMeta.createWrite(glamPublicKey),
        vaultPDA,
        AccountMeta.createRead(vaultPDA.publicKey()),
        AccountMeta.createWrite(vaultPDA.publicKey()),
        metadataPDA,
        AccountMeta.createRead(metadataPDA.publicKey()),
        AccountMeta.createWrite(metadataPDA.publicKey())
    );
  }

  static GlamVaultAccounts createAccounts(final PublicKey feePayer, final PublicKey glamPublicKey) {
    return GlamVaultAccounts.createAccounts(GlamAccounts.MAIN_NET, feePayer, glamPublicKey);
  }

  static List<ProgramMapConfig> loadMappingConfigs(final Path mappingsDirectory) {
    final var accountMetaCache = new HashMap<AccountMeta, AccountMeta>(256);
    final var indexedAccountMetaCache = new HashMap<IndexedAccountMeta, IndexedAccountMeta>(256);
    try {
      try (final var paths = Files.walk(mappingsDirectory, 1)) {
        return paths.parallel()
            .filter(Files::isRegularFile)
            .filter(Files::isReadable)
            .filter(f -> f.getFileName().toString().endsWith(".json"))
            .map(filePath -> {
              try {
                final var ji = JsonIterator.parse(Files.readAllBytes(filePath));
                return ProgramMapConfig.parseConfig(accountMetaCache, indexedAccountMetaCache, ji);
              } catch (final IOException e) {
                throw new UncheckedIOException(e);
              }
            })
            .toList();
      }
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  static TransactionMapper<GlamVaultAccounts> createMapper(final AccountMeta invokedGlamProgram,
                                                           final List<ProgramMapConfig> mappingConfigs,
                                                           final DynamicGlamAccountFactory dynamicGlamAccountFactory) {
    return TransactionMapper.createMapper(
        invokedGlamProgram,
        dynamicGlamAccountFactory,
        mappingConfigs
    );
  }

  static TransactionMapper<GlamVaultAccounts> createMapper(final AccountMeta invokedGlamProgram,
                                                           final Path mappingsDirectory,
                                                           final DynamicGlamAccountFactory dynamicGlamAccountFactory) {
    return createMapper(invokedGlamProgram, loadMappingConfigs(mappingsDirectory), dynamicGlamAccountFactory);
  }

  GlamAccounts glamAccounts();

  PublicKey feePayer();

  PublicKey glamPublicKey();

  AccountMeta writeGlamState();

  AccountMeta readGlamState();

  ProgramDerivedAddress vaultPDA();

  default PublicKey vaultPublicKey() {
    return vaultPDA().publicKey();
  }

  AccountMeta writeVault();

  AccountMeta readVault();

  ProgramDerivedAddress metadataPDA();

  ProgramDerivedAddress shareClassPDA(final int shareId);
}
