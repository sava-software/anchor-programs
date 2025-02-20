package software.sava.anchor.programs.kamino.lend;

import software.sava.core.accounts.PublicKey;

public record MarketPDAsRecord(PublicKey market,
                               PublicKey authority) implements MarketPDAs {
}
