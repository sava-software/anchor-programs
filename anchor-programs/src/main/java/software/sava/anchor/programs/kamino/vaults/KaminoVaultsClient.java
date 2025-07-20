package software.sava.anchor.programs.kamino.vaults;

import software.sava.anchor.programs.kamino.KaminoAccounts;
import software.sava.anchor.programs.kamino.vaults.anchor.types.VaultState;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

public interface KaminoVaultsClient {

  static KaminoVaultsClientImpl createClient(final NativeProgramAccountClient nativeProgramAccountClient,
                                             final KaminoAccounts kaminoAccounts) {
    return new KaminoVaultsClientImpl(nativeProgramAccountClient, kaminoAccounts);
  }

  static KaminoVaultsClientImpl createClient(final NativeProgramAccountClient nativeProgramAccountClient) {
    return createClient(nativeProgramAccountClient, KaminoAccounts.MAIN_NET);
  }

  NativeProgramAccountClient nativeProgramAccountClient();

  SolanaAccounts solanaAccounts();

  KaminoAccounts kaminoAccounts();

  PublicKey user();

  PublicKey feePayer();

  Instruction deposit(final PublicKey vaultStateKey,
                      final PublicKey tokenVaultKey,
                      final PublicKey tokenMintKey,
                      final PublicKey baseVaultAuthorityKey,
                      final PublicKey sharesMintKey,
                      final PublicKey userTokenAtaKey,
                      final PublicKey userSharesAtaKey,
                      final PublicKey tokenProgramKey,
                      final PublicKey sharesTokenProgramKey,
                      final PublicKey programKey,
                      final long maxAmount);

  default Instruction deposit(final VaultState vaultState,
                              final PublicKey userTokenAtaKey,
                              final PublicKey userSharesAtaKey,
                              final PublicKey sharesTokenProgramKey,
                              final long maxAmount) {

    return deposit(
        vaultState._address(),
        vaultState.tokenVault(),
        vaultState.tokenMint(),
        vaultState.baseVaultAuthority(),
        vaultState.sharesMint(),
        userTokenAtaKey,
        userSharesAtaKey,
        vaultState.tokenProgram(),
        sharesTokenProgramKey,
        kaminoAccounts().kVaultsProgram(),
        maxAmount
    );
  }

  default Instruction deposit(final VaultState vaultState,
                              final PublicKey sharesTokenProgramKey,
                              final long maxAmount) {
    final var tokenMint = vaultState.tokenMint();
    final var tokenProgram = vaultState.tokenProgram();
    final var nativeClient = nativeProgramAccountClient();
    final var userTokenAtaKey = nativeClient.findATA(tokenProgram, tokenMint).publicKey();
    final var sharesMint = vaultState.sharesMint();
    final var userSharesAtaKey = nativeClient.findATA(sharesTokenProgramKey, sharesMint).publicKey();
    return deposit(
        vaultState._address(),
        vaultState.tokenVault(),
        tokenMint,
        vaultState.baseVaultAuthority(),
        sharesMint,
        userTokenAtaKey,
        userSharesAtaKey,
        tokenProgram,
        sharesTokenProgramKey,
        kaminoAccounts().kVaultsProgram(),
        maxAmount
    );
  }

  Instruction withdraw(final PublicKey withdrawFromAvailableVaultStateKey,
                       final PublicKey withdrawFromAvailableTokenVaultKey,
                       final PublicKey withdrawFromAvailableBaseVaultAuthorityKey,
                       final PublicKey withdrawFromAvailableUserTokenAtaKey,
                       final PublicKey withdrawFromAvailableTokenMintKey,
                       final PublicKey withdrawFromAvailableUserSharesAtaKey,
                       final PublicKey withdrawFromAvailableSharesMintKey,
                       final PublicKey withdrawFromAvailableTokenProgramKey,
                       final PublicKey withdrawFromAvailableSharesTokenProgramKey,
                       final PublicKey withdrawFromAvailableKlendProgramKey,
                       final PublicKey withdrawFromAvailableEventAuthorityKey,
                       final PublicKey withdrawFromAvailableProgramKey,
                       final PublicKey withdrawFromReserveAccountsVaultStateKey,
                       final PublicKey withdrawFromReserveAccountsReserveKey,
                       final PublicKey withdrawFromReserveAccountsCtokenVaultKey,
                       final PublicKey withdrawFromReserveAccountsLendingMarketKey,
                       final PublicKey withdrawFromReserveAccountsLendingMarketAuthorityKey,
                       final PublicKey withdrawFromReserveAccountsReserveLiquiditySupplyKey,
                       final PublicKey withdrawFromReserveAccountsReserveCollateralMintKey,
                       final PublicKey withdrawFromReserveAccountsReserveCollateralTokenProgramKey,
                       final PublicKey withdrawFromReserveAccountsInstructionSysvarAccountKey,
                       final PublicKey programKey,
                       final long sharesAmount);
}
