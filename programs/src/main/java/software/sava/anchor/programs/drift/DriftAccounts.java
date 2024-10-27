package software.sava.anchor.programs.drift;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

import static software.sava.anchor.programs.drift.DriftAsset.USDC;
import static software.sava.anchor.programs.drift.DriftPDAs.deriveSignerAccount;
import static software.sava.core.accounts.PublicKey.fromBase58Encoded;

public interface DriftAccounts {

  DriftAccounts MAIN_NET = createAddressConstants(
      "dRiftyHA39MWEi3m9aunc5MzRF1JYuBsbn6VPcn33UH",
      "D9cnvzswDikQDf53k4HpQ3KJ9y1Fv3HGGDFYMXnK5T6c",
      "GPZkp76cJtNL2mphCvT6FXkJCVPpouidnacckR6rzKDN",
      SpotMarketConfigs.MAIN_NET,
      PerpMarketConfigs.MAIN_NET
  );

  DriftAccounts DEV_NET = createAddressConstants(
      "dRiftyHA39MWEi3m9aunc5MzRF1JYuBsbn6VPcn33UH",
      "FaMS3U4uBojvGn5FSDEPimddcXsCfwkKsFgMVVnDdxGb",
      null,
      SpotMarketConfigs.DEV_NET,
      PerpMarketConfigs.DEV_NET
  );

  AccountMeta invokedDriftProgram();

  default PublicKey driftProgram() {
    return invokedDriftProgram().publicKey();
  }

  PublicKey driftSignerPDA();

  PublicKey stateKey();

  PublicKey marketLookupTable();

  PublicKey serumLookupTable();

  SpotMarketConfig defaultQuoteMarket();

  SpotMarkets spotMarkets();

  SpotMarketConfig spotMarketConfig(final int index);

  SpotMarketConfig spotMarketConfig(final DriftAsset symbol);

  PerpMarkets perpMarkets();

  PerpMarketConfig perpMarketConfig(final int index);

  PerpMarketConfig perpMarketConfig(final DriftProduct product);

  static DriftAccounts createAddressConstants(final PublicKey driftProgram,
                                              final PublicKey marketLookupTable,
                                              final PublicKey serumLookupTable,
                                              final SpotMarkets spotMarkets,
                                              final PerpMarkets perpMarkets) {
    final var driftSigner = deriveSignerAccount(driftProgram).publicKey();
    return new DriftAccountsRecord(
        AccountMeta.createInvoked(driftProgram),
        driftSigner,
        marketLookupTable,
        serumLookupTable,
        DriftPDAs.deriveStateAccount(driftProgram).publicKey(),
        spotMarkets.forAsset(USDC),
        spotMarkets,
        perpMarkets
    );
  }

  static DriftAccounts createAddressConstants(final String driftProgram,
                                              final String marketLookupTable,
                                              final String serumLookupTable,
                                              final SpotMarkets spotMarkets,
                                              final PerpMarkets perpMarkets) {
    return createAddressConstants(
        fromBase58Encoded(driftProgram),
        fromBase58Encoded(marketLookupTable),
        serumLookupTable == null ? null : fromBase58Encoded(driftProgram),
        spotMarkets,
        perpMarkets
    );
  }
}
