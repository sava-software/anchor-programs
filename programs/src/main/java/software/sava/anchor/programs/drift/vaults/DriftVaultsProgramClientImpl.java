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
  private final PublicKey feePayer;

  DriftVaultsProgramClientImpl(final SolanaAccounts solanaAccounts,
                               final DriftAccounts driftAccounts,
                               final PublicKey authority,
                               final PublicKey feePayer) {
    this.solanaAccounts = solanaAccounts;
    this.driftAccounts = driftAccounts;
    this.authority = authority;
    this.feePayer = feePayer;
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
  public PublicKey feePayer() {
    return feePayer;
  }

  @Override
  public PublicKey vaultDepositor(final PublicKey vaultKey) {
    return DriftVaultPDAs.vaultDepositorAddress(
        driftAccounts.driftVaultsProgram(),
        vaultKey,
        authority
    ).publicKey();
  }

  @Override
  public Instruction initializeVaultDepositor(final PublicKey vaultKey,
                                              final PublicKey vaultDepositorKey,
                                              final PublicKey authorityKey,
                                              final PublicKey payerKey) {
    return DriftVaultsProgram.initializeVaultDepositor(
        driftAccounts.invokedDriftVaultsProgram(),
        vaultKey,
        vaultDepositorKey,
        authorityKey,
        payerKey,
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram()
    );
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
