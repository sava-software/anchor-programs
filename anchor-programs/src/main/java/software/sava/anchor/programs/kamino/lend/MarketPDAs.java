package software.sava.anchor.programs.kamino.lend;

import software.sava.anchor.programs.kamino.KaminoAccounts;
import software.sava.core.accounts.PublicKey;

public interface MarketPDAs {

  static MarketPDAs createPDAs(final PublicKey programId, final PublicKey market) {
    return new MarketPDAsRecord(
        market,
        KaminoAccounts.lendingMarketAuthPda(market, programId).publicKey()
    );
  }

  PublicKey market();

  PublicKey authority();
}
