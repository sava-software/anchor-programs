package software.sava.anchor.programs.kamino.vaults;

import software.sava.anchor.programs.kamino.KaminoAccounts;
import software.sava.anchor.programs.kamino.lend.anchor.types.Reserve;
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
                       final PublicKey withdrawFromReserveAccountsReserveKey,
                       final PublicKey withdrawFromReserveAccountsCtokenVaultKey,
                       final PublicKey withdrawFromReserveAccountsLendingMarketKey,
                       final PublicKey withdrawFromReserveAccountsLendingMarketAuthorityKey,
                       final PublicKey withdrawFromReserveAccountsReserveLiquiditySupplyKey,
                       final PublicKey withdrawFromReserveAccountsReserveCollateralMintKey,
                       final PublicKey withdrawFromReserveAccountsReserveCollateralTokenProgramKey,
                       final long sharesAmount);

  default Instruction withdraw(final VaultState vaultState,
                               final PublicKey withdrawFromAvailableUserTokenAtaKey,
                               final PublicKey withdrawFromAvailableUserSharesAtaKey,
                               final PublicKey withdrawFromAvailableSharesTokenProgramKey,
                               final PublicKey withdrawFromReserveAccountsReserveKey,
                               final PublicKey withdrawFromReserveAccountsCtokenVaultKey,
                               final PublicKey withdrawFromReserveAccountsLendingMarketKey,
                               final PublicKey withdrawFromReserveAccountsLendingMarketAuthorityKey,
                               final PublicKey withdrawFromReserveAccountsReserveLiquiditySupplyKey,
                               final PublicKey withdrawFromReserveAccountsReserveCollateralMintKey,
                               final PublicKey withdrawFromReserveAccountsReserveCollateralTokenProgramKey,
                               final long maxAmount) {

    return withdraw(
        vaultState._address(),
        vaultState.tokenVault(),
        vaultState.baseVaultAuthority(),
        withdrawFromAvailableUserTokenAtaKey,
        vaultState.tokenMint(),
        withdrawFromAvailableUserSharesAtaKey,
        vaultState.sharesMint(),
        vaultState.tokenProgram(),
        withdrawFromAvailableSharesTokenProgramKey,
        withdrawFromReserveAccountsReserveKey,
        withdrawFromReserveAccountsCtokenVaultKey,
        withdrawFromReserveAccountsLendingMarketKey,
        withdrawFromReserveAccountsLendingMarketAuthorityKey,
        withdrawFromReserveAccountsReserveLiquiditySupplyKey,
        withdrawFromReserveAccountsReserveCollateralMintKey,
        withdrawFromReserveAccountsReserveCollateralTokenProgramKey,
        maxAmount
    );
  }

  default Instruction withdraw(final VaultState vaultState,
                               final PublicKey withdrawFromAvailableUserTokenAtaKey,
                               final PublicKey withdrawFromAvailableUserSharesAtaKey,
                               final PublicKey withdrawFromAvailableSharesTokenProgramKey,
                               final PublicKey withdrawFromReserveAccountsReserveCollateralTokenProgramKey,
                               final Reserve reserve,
                               final long maxAmount) {
    final var kaminoAccounts = kaminoAccounts();
    final var lendingMarket = reserve.lendingMarket();
    final var reserveCollateral = reserve.collateral();
    final var vaultStateKey = vaultState._address();
    final var cTokenVault = kaminoAccounts.cTokenVault(vaultStateKey, reserve._address()).publicKey();
    return withdraw(
        vaultStateKey,
        vaultState.tokenVault(),
        vaultState.baseVaultAuthority(),
        withdrawFromAvailableUserTokenAtaKey,
        vaultState.tokenMint(),
        withdrawFromAvailableUserSharesAtaKey,
        vaultState.sharesMint(),
        vaultState.tokenProgram(),
        withdrawFromAvailableSharesTokenProgramKey,
        reserve._address(),
        cTokenVault,
        lendingMarket,
        kaminoAccounts.lendingMarketAuthPda(lendingMarket).publicKey(),
        reserve.liquidity().supplyVault(),
        reserveCollateral.mintPubkey(),
        withdrawFromReserveAccountsReserveCollateralTokenProgramKey,
        maxAmount
    );
  }

  default Instruction withdraw(final VaultState vaultState,
                               final PublicKey withdrawFromAvailableSharesTokenProgramKey,
                               final PublicKey withdrawFromReserveAccountsReserveCollateralTokenProgramKey,
                               final Reserve reserve,
                               final long maxAmount) {
    final var tokenMint = vaultState.tokenMint();
    final var tokenProgram = vaultState.tokenProgram();
    final var nativeClient = nativeProgramAccountClient();
    final var userTokenAtaKey = nativeClient.findATA(tokenProgram, tokenMint).publicKey();
    final var sharesMint = vaultState.sharesMint();
    final var userSharesAtaKey = nativeClient.findATA(withdrawFromAvailableSharesTokenProgramKey, sharesMint).publicKey();
    return withdraw(
        vaultState,
        userTokenAtaKey,
        userSharesAtaKey,
        withdrawFromAvailableSharesTokenProgramKey,
        withdrawFromReserveAccountsReserveCollateralTokenProgramKey,
        reserve,
        maxAmount
    );
  }
}
