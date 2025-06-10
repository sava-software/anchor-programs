package software.sava.anchor.programs.drift.vaults;

import software.sava.anchor.programs.drift.DriftAccounts;
import software.sava.anchor.programs.drift.vaults.anchor.types.Vault;
import software.sava.anchor.programs.drift.vaults.anchor.types.WithdrawUnit;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

public interface DriftVaultsProgramClient {

  static DriftVaultsProgramClient createClient(final NativeProgramAccountClient nativeProgramAccountClient,
                                               final DriftAccounts driftAccounts) {
    return new DriftVaultsProgramClientImpl(
        nativeProgramAccountClient.solanaAccounts(),
        driftAccounts,
        nativeProgramAccountClient.ownerPublicKey()
    );
  }

  static DriftVaultsProgramClient createClient(final NativeProgramAccountClient nativeProgramAccountClient) {
    return createClient(nativeProgramAccountClient, DriftAccounts.MAIN_NET);
  }

  SolanaAccounts solanaAccounts();

  DriftAccounts driftAccounts();

  PublicKey authority();

  Instruction deposit(final PublicKey vaultKey,
                      final PublicKey vaultDepositorKey,
                      final PublicKey authorityKey,
                      final PublicKey vaultTokenAccountKey,
                      final PublicKey driftUserStatsKey,
                      final PublicKey driftUserKey,
                      final PublicKey driftSpotMarketVaultKey,
                      final PublicKey userTokenAccountKey,
                      final PublicKey tokenProgramKey,
                      final long amount);

  default Instruction deposit(final Vault vault,
                              final PublicKey driftSpotMarketVaultKey,
                              final PublicKey userTokenAccountKey,
                              final PublicKey tokenProgramKey,
                              final long amount) {
    final var vaultKey = vault._address();
    final var driftAccounts = driftAccounts();
    final var authority = authority();
    final var vaultDepositor = DriftVaultPDAs.getVaultDepositorAddress(
        driftAccounts.driftVaultsProgram(),
        vaultKey,
        authority
    ).publicKey();

    return deposit(
        vaultKey,
        vaultDepositor,
        authority,
        vault.tokenAccount(),
        vault.userStats(),
        vault.user(),
        driftSpotMarketVaultKey,
        userTokenAccountKey,
        tokenProgramKey,
        amount
    );
  }

  Instruction requestWithdraw(final PublicKey vaultKey,
                              final PublicKey vaultDepositorKey,
                              final PublicKey authorityKey,
                              final PublicKey driftUserStatsKey,
                              final PublicKey driftUserKey,
                              final long withdrawAmount,
                              final WithdrawUnit withdrawUnit);

  default Instruction requestWithdraw(final Vault vault, final long withdrawAmount, final WithdrawUnit withdrawUnit) {
    final var vaultKey = vault._address();
    final var driftAccounts = driftAccounts();
    final var authority = authority();
    final var vaultDepositor = DriftVaultPDAs.getVaultDepositorAddress(
        driftAccounts.driftVaultsProgram(),
        vaultKey,
        authority
    ).publicKey();

    return requestWithdraw(
        vaultKey,
        vaultDepositor,
        authority,
        vault.userStats(),
        vault.user(),
        withdrawAmount,
        withdrawUnit
    );
  }

  Instruction cancelRequestWithdraw(final PublicKey vaultKey,
                                    final PublicKey vaultDepositorKey,
                                    final PublicKey authorityKey,
                                    final PublicKey driftUserStatsKey,
                                    final PublicKey driftUserKey);

  default Instruction cancelRequestWithdraw(final Vault vault) {
    final var vaultKey = vault._address();
    final var driftAccounts = driftAccounts();
    final var authority = authority();
    final var vaultDepositor = DriftVaultPDAs.getVaultDepositorAddress(
        driftAccounts.driftVaultsProgram(),
        vaultKey,
        authority
    ).publicKey();

    return cancelRequestWithdraw(
        vaultKey,
        vaultDepositor,
        authority,
        vault.userStats(),
        vault.user()
    );
  }

  Instruction withdraw(final PublicKey vaultKey,
                       final PublicKey vaultDepositorKey,
                       final PublicKey authorityKey,
                       final PublicKey vaultTokenAccountKey,
                       final PublicKey driftUserStatsKey,
                       final PublicKey driftUserKey,
                       final PublicKey driftSpotMarketVaultKey,
                       final PublicKey userTokenAccountKey,
                       final PublicKey tokenProgramKey);

  default Instruction withdraw(final Vault vault,
                               final PublicKey driftSpotMarketVaultKey,
                               final PublicKey userTokenAccountKey,
                               final PublicKey tokenProgramKey) {
    final var vaultKey = vault._address();
    final var driftAccounts = driftAccounts();
    final var authority = authority();
    final var vaultDepositor = DriftVaultPDAs.getVaultDepositorAddress(
        driftAccounts.driftVaultsProgram(),
        vaultKey,
        authority
    ).publicKey();

    return withdraw(
        vaultKey,
        vaultDepositor,
        authority,
        vault.tokenAccount(),
        vault.userStats(),
        vault.user(),
        driftSpotMarketVaultKey,
        userTokenAccountKey,
        tokenProgramKey
    );
  }
}
