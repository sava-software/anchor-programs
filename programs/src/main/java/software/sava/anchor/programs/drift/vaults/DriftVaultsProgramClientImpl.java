package software.sava.anchor.programs.drift.vaults;

import software.sava.anchor.programs.drift.DriftAccounts;
import software.sava.anchor.programs.drift.vaults.anchor.DriftVaultsProgram;
import software.sava.anchor.programs.drift.vaults.anchor.types.WithdrawUnit;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;

final class DriftVaultsProgramClientImpl implements DriftVaultsProgramClient {

  private final SolanaAccounts solanaAccounts;
  private final DriftAccounts driftAccounts;
  private final PublicKey authority;

  DriftVaultsProgramClientImpl(final SolanaAccounts solanaAccounts,
                               final DriftAccounts driftAccounts,
                               final PublicKey authority) {
    this.solanaAccounts = solanaAccounts;
    this.driftAccounts = driftAccounts;
    this.authority = authority;
  }

  @Override
  public SolanaAccounts solanaAccounts() {
    return solanaAccounts;
  }

  @Override
  public DriftAccounts driftAccounts() {
    return driftAccounts;
  }

  @Override
  public PublicKey authority() {
    return authority;
  }

  @Override
  public Instruction deposit(final PublicKey vaultKey,
                             final PublicKey vaultDepositorKey,
                             final PublicKey authorityKey,
                             final PublicKey vaultTokenAccountKey,
                             final PublicKey driftUserStatsKey,
                             final PublicKey driftUserKey,
                             final PublicKey driftSpotMarketVaultKey,
                             final PublicKey userTokenAccountKey,
                             final PublicKey tokenProgramKey,
                             final long amount) {
    return DriftVaultsProgram.deposit(
        driftAccounts.invokedDriftVaultsProgram(),
        vaultKey,
        vaultDepositorKey,
        authorityKey,
        vaultTokenAccountKey,
        driftUserStatsKey,
        driftUserKey,
        driftAccounts.stateKey(),
        driftSpotMarketVaultKey,
        userTokenAccountKey,
        driftAccounts.driftProgram(),
        tokenProgramKey,
        amount
    );
  }

  @Override
  public Instruction requestWithdraw(final PublicKey vaultKey,
                                     final PublicKey vaultDepositorKey,
                                     final PublicKey authorityKey,
                                     final PublicKey driftUserStatsKey,
                                     final PublicKey driftUserKey,
                                     final long withdrawAmount,
                                     final WithdrawUnit withdrawUnit) {
    return DriftVaultsProgram.requestWithdraw(
        driftAccounts.invokedDriftVaultsProgram(),
        vaultKey,
        vaultDepositorKey,
        authorityKey,
        driftUserStatsKey,
        driftUserKey,
        driftAccounts.stateKey(),
        withdrawAmount,
        withdrawUnit
    );
  }

  @Override
  public Instruction cancelRequestWithdraw(final PublicKey vaultKey,
                                           final PublicKey vaultDepositorKey,
                                           final PublicKey authorityKey,
                                           final PublicKey driftUserStatsKey,
                                           final PublicKey driftUserKey) {
    return DriftVaultsProgram.cancelRequestWithdraw(
        driftAccounts.invokedDriftVaultsProgram(),
        vaultKey,
        vaultDepositorKey,
        authorityKey,
        driftUserStatsKey,
        driftUserKey,
        driftAccounts.stateKey()
    );
  }

  @Override
  public Instruction withdraw(final PublicKey vaultKey,
                              final PublicKey vaultDepositorKey,
                              final PublicKey authorityKey,
                              final PublicKey vaultTokenAccountKey,
                              final PublicKey driftUserStatsKey,
                              final PublicKey driftUserKey,
                              final PublicKey driftSpotMarketVaultKey,
                              final PublicKey userTokenAccountKey,
                              final PublicKey tokenProgramKey) {
    return DriftVaultsProgram.withdraw(
        driftAccounts.invokedDriftVaultsProgram(),
        vaultKey,
        vaultDepositorKey,
        authorityKey,
        vaultTokenAccountKey,
        driftUserStatsKey,
        driftUserKey,
        driftAccounts.stateKey(),
        driftSpotMarketVaultKey,
        driftAccounts.driftSignerPDA(),
        userTokenAccountKey,
        driftAccounts.driftProgram(),
        tokenProgramKey
    );
  }
}
