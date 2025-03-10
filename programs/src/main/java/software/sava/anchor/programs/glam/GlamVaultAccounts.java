package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamPDAs;
import software.sava.anchor.programs.glam.proxy.DynamicGlamAccountFactory;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import systems.comodal.jsoniter.JsonIterator;
import systems.glam.ix.proxy.ProgramMapConfig;
import systems.glam.ix.proxy.TransactionMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

  static GlamVaultAccounts createV0Accounts(final GlamAccounts glamAccounts,
                                            final PublicKey feePayer,
                                            final PublicKey glamPublicKey) {
    final var program = glamAccounts.program();
    final var vaultPDA = software.sava.anchor.programs.glam_v0.anchor.GlamPDAs.vaultPDA(program, glamPublicKey);
    final var metadataPDA = software.sava.anchor.programs.glam_v0.anchor.GlamPDAs.metadataPDA(program, glamPublicKey);
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

  static GlamVaultAccounts createV0Accounts(final PublicKey feePayer, final PublicKey glamPublicKey) {
    return GlamVaultAccounts.createV0Accounts(GlamAccounts.MAIN_NET_VO, feePayer, glamPublicKey);
  }

  static List<ProgramMapConfig> loadMappingConfigs() {
    final var packagePath = GlamVaultAccounts.class.getPackage().getName().replace('.', '/');
    final var classLoader = GlamVaultAccounts.class.getClassLoader();
    final var basePackage = classLoader.getResource(packagePath + "/drift.json");
    if (basePackage == null) {
      throw new IllegalStateException("Failed to find mappings resource directory at " + packagePath);
    }
    try {
      final var basePath = Paths.get(basePackage.toURI()).getParent();
      try (final var paths = Files.walk(basePath, 1)) {
        return paths.parallel()
            .filter(Files::isRegularFile)
            .filter(Files::isReadable)
            .filter(f -> f.getFileName().toString().endsWith(".json"))
            .map(filePath -> {
              try {
                final var ji = JsonIterator.parse(Files.readAllBytes(filePath));
                return ProgramMapConfig.parseConfig(ji);
              } catch (final IOException e) {
                throw new UncheckedIOException(e);
              }
            })
            .toList();
      }
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    } catch (final URISyntaxException e) {
      throw new RuntimeException(e);
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
                                                           final DynamicGlamAccountFactory dynamicGlamAccountFactory) {
    return createMapper(invokedGlamProgram, loadMappingConfigs(), dynamicGlamAccountFactory);
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
