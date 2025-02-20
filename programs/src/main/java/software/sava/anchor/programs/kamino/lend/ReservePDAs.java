package software.sava.anchor.programs.kamino.lend;

import software.sava.anchor.programs.kamino.KaminoAccounts;
import software.sava.core.accounts.PublicKey;

public interface ReservePDAs {

  static ReservePDAs createPDAs(final PublicKey programId,
                                final MarketPDAs marketPDAs,
                                final PublicKey mint,
                                final PublicKey tokenProgram) {
    final var market = marketPDAs.market();
    return new ReservePDAsRecord(
        marketPDAs,
        mint,
        tokenProgram,
        KaminoAccounts.reserveLiqSupplyPda(market, mint, programId).publicKey(),
        KaminoAccounts.reserveCollateralMintPda(market, mint, programId).publicKey(),
        KaminoAccounts.reserveCollateralSupplyPda(market, mint, programId).publicKey(),
        KaminoAccounts.reserveFeeVaultPda(market, mint, programId).publicKey()
    );
  }

  MarketPDAs marketPDAs();

  default PublicKey market() {
    return marketPDAs().market();
  }

  default PublicKey marketAuthority() {
    return marketPDAs().authority();
  }

  PublicKey mint();

  PublicKey tokenProgram();

  PublicKey liquiditySupplyVault();

  PublicKey collateralMint();

  PublicKey collateralSupplyVault();

  PublicKey feeVault();
}
