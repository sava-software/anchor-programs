package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamPDAs;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import systems.glam.ix.proxy.DynamicAccount;
import systems.glam.ix.proxy.DynamicAccountConfig;

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
