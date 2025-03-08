package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamPDAs;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import systems.comodal.jsoniter.JsonIterator;
import systems.glam.ix.proxy.DynamicAccount;
import systems.glam.ix.proxy.DynamicAccountConfig;
import systems.glam.ix.proxy.ProgramMapConfig;
import systems.glam.ix.proxy.TransactionMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

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

  DynamicAccount<GlamVaultAccounts> DEFAULT_DYNAMIC_READ_STATE = (mappedAccounts, _, _, vaultAccounts) ->
      mappedAccounts[0] = vaultAccounts.readGlamState();
  DynamicAccount<GlamVaultAccounts> DEFAULT_DYNAMIC_WRITE_VAULT = (mappedAccounts, _, _, vaultAccounts) ->
      mappedAccounts[1] = vaultAccounts.writeVault();
  DynamicAccount<GlamVaultAccounts> DEFAULT_DYNAMIC_FEE_PAYER = (mappedAccounts, _, feePayer, _) ->
      mappedAccounts[2] = feePayer;
  DynamicAccount<GlamVaultAccounts> DEFAULT_DYNAMIC_CPI_PROGRAM = (mappedAccounts, cpiProgram, _, _) ->
      mappedAccounts[3] = cpiProgram;

  Function<DynamicAccountConfig, DynamicAccount<GlamVaultAccounts>> DYNAMIC_ACCOUNT_FACTORY = accountConfig -> {
    final int index = accountConfig.index();
    final boolean w = accountConfig.writable();
    return switch (accountConfig.name()) {
      case "glam_state" -> (mappedAccounts, _, _, vaultAccounts) -> mappedAccounts[index] = w
          ? vaultAccounts.writeGlamState() : vaultAccounts.readGlamState();
      case "glam_vault" -> (mappedAccounts, _, _, vaultAccounts) -> mappedAccounts[index] = w
          ? vaultAccounts.writeVault() : vaultAccounts.readVault();
      case "glam_signer" -> accountConfig.createFeePayerAccount();
      case "cpi_program" -> accountConfig.createReadCpiProgram();
      default -> throw new IllegalStateException("Unknown dynamic account type: " + accountConfig.name());
    };
  };

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
                                                           final List<ProgramMapConfig> mappingConfigs) {
    return TransactionMapper.createMapper(
        invokedGlamProgram,
        GlamVaultAccounts.DYNAMIC_ACCOUNT_FACTORY,
        mappingConfigs
    );
  }

  static TransactionMapper<GlamVaultAccounts> createMapper(final AccountMeta invokedGlamProgram) {
    return createMapper(invokedGlamProgram, loadMappingConfigs());
  }

  default TransactionMapper<GlamVaultAccounts> createMapper() {
    return createMapper(glamAccounts().invokedProgram());
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
