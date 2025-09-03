package software.sava.anchor.programs.kamino.scope;

import software.sava.anchor.programs.kamino.KaminoAccounts;
import software.sava.anchor.programs.kamino.scope.anchor.types.Configuration;
import software.sava.anchor.programs.kamino.scope.anchor.types.OracleMappings;
import software.sava.anchor.programs.kamino.scope.anchor.types.OracleType;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.util.Arrays;
import java.util.List;

public interface ScopeProgramClient {

  static ScopeProgramClient createClient(final NativeProgramAccountClient nativeProgramAccountClient,
                                         final KaminoAccounts kaminoAccounts) {
    return new ScopeProgramClientImpl(nativeProgramAccountClient, kaminoAccounts);
  }

  static ScopeProgramClient createClient(final NativeProgramAccountClient nativeProgramAccountClient) {
    return createClient(nativeProgramAccountClient, KaminoAccounts.MAIN_NET);
  }

  SolanaAccounts solanaAccounts();

  KaminoAccounts kaminoAccounts();

  PublicKey authority();

  PublicKey feePayer();

  Instruction initialize(final PublicKey adminKey,
                         final PublicKey configurationKey,
                         final PublicKey tokenMetadatasKey,
                         final PublicKey oracleTwapsKey,
                         final PublicKey oraclePricesKey,
                         final PublicKey oracleMappingsKey,
                         final String feedName);

  default Instruction initialize(final Configuration configuration,
                                 final String feedName) {
    return initialize(
        configuration.admin(),
        configuration._address(),
        configuration.tokensMetadata(),
        configuration.oracleTwaps(),
        configuration.oracleMappings(),
        configuration.oraclePrices(),
        feedName
    );
  }

  Instruction refreshPriceList(final PublicKey oraclePricesKey,
                               final PublicKey oracleMappingsKey,
                               final PublicKey oracleTwapsKey,
                               final short[] tokens);

  default Instruction refreshPriceList(final Configuration configuration,
                                       final short[] tokens) {
    return refreshPriceList(
        configuration.oraclePrices(),
        configuration.oracleMappings(),
        configuration.oracleTwaps(),
        tokens
    );
  }

  static List<AccountMeta> refreshPriceListExtraAccounts(final OracleMappings oracleMappings, final short[] tokens) {
    final var priceInfoAccounts = oracleMappings.priceInfoAccounts();
    final var oracleTypes = oracleMappings.priceTypes();
    final var oracleTypeEnums = OracleType.values();
    final var accountMetas = new AccountMeta[tokens.length];
    for (int i = 0; i < tokens.length; i++) {
      final int token = tokens[i];
      final var oracleType = oracleTypeEnums[oracleTypes[token]];
      switch (oracleType) {
        case KToken, KTokenToTokenA, KTokenToTokenB,
             JupiterLpCompute, JupiterLpFetch, JupiterLpScope,
             MeteoraDlmmAtoB, MeteoraDlmmBtoA,
             OrcaWhirlpoolAtoB, OrcaWhirlpoolBtoA,
             Securitize -> throw new IllegalStateException(oracleType + "Requires asset mints as well.");
      }
      accountMetas[i] = AccountMeta.createRead(priceInfoAccounts[token]);
    }
    return Arrays.asList(accountMetas);
  }

  default Instruction refreshPriceList(final Configuration configuration,
                                       final OracleMappings oracleMappings,
                                       final short[] tokens) {
    return refreshPriceList(
        configuration.oraclePrices(),
        configuration.oracleMappings(),
        configuration.oracleTwaps(),
        tokens
    ).extraAccounts(refreshPriceListExtraAccounts(oracleMappings, tokens));
  }

  Instruction refreshChainlinkPrice(final PublicKey userKey,
                                    final PublicKey oraclePricesKey,
                                    final PublicKey oracleMappingsKey,
                                    final PublicKey oracleTwapsKey,
                                    final PublicKey verifierAccountKey,
                                    final PublicKey accessControllerKey,
                                    final PublicKey configAccountKey,
                                    final PublicKey verifierProgramIdKey,
                                    final int token,
                                    final byte[] serializedChainlinkReport);

  default Instruction refreshChainlinkPrice(final PublicKey userKey,
                                            final Configuration configuration,
                                            final PublicKey verifierAccountKey,
                                            final PublicKey accessControllerKey,
                                            final PublicKey configAccountKey,
                                            final PublicKey verifierProgramIdKey,
                                            final int token,
                                            final byte[] serializedChainlinkReport) {
    return refreshChainlinkPrice(
        userKey,
        configuration.oraclePrices(),
        configuration.oracleMappings(),
        configuration.oracleTwaps(),
        verifierAccountKey,
        accessControllerKey,
        configAccountKey,
        verifierProgramIdKey,
        token,
        serializedChainlinkReport
    );
  }

  Instruction refreshPythLazerPrice(final PublicKey userKey,
                                    final PublicKey oraclePricesKey,
                                    final PublicKey oracleMappingsKey,
                                    final PublicKey oracleTwapsKey,
                                    final PublicKey pythProgramKey,
                                    final PublicKey pythStorageKey,
                                    final PublicKey pythTreasuryKey,
                                    final short[] tokens,
                                    final byte[] serializedPythMessage,
                                    final int ed25519InstructionIndex);

  default Instruction refreshPythLazerPrice(final PublicKey userKey,
                                            final Configuration configuration,
                                            final PublicKey pythProgramKey,
                                            final PublicKey pythStorageKey,
                                            final PublicKey pythTreasuryKey,
                                            final short[] tokens,
                                            final byte[] serializedPythMessage,
                                            final int ed25519InstructionIndex) {
    return refreshPythLazerPrice(
        userKey,
        configuration.oraclePrices(),
        configuration.oracleMappings(),
        configuration.oracleTwaps(),
        pythProgramKey,
        pythStorageKey,
        pythTreasuryKey,
        tokens,
        serializedPythMessage,
        ed25519InstructionIndex
    );
  }

  Instruction updateMapping(final PublicKey adminKey,
                            final PublicKey configurationKey,
                            final PublicKey oracleMappingsKey,
                            final PublicKey priceInfoKey,
                            final int token,
                            final int priceType,
                            final boolean twapEnabled,
                            final int twapSource,
                            final int refPriceIndex,
                            final String feedName,
                            final byte[] genericData);


  default Instruction updateMapping(final Configuration configuration,
                                    final PublicKey priceInfoKey,
                                    final int token,
                                    final int priceType,
                                    final boolean twapEnabled,
                                    final int twapSource,
                                    final int refPriceIndex,
                                    final String feedName,
                                    final byte[] genericData) {
    return updateMapping(
        configuration.admin(),
        configuration._address(),
        configuration.oracleMappings(),
        priceInfoKey,
        token,
        priceType,
        twapEnabled,
        twapSource,
        refPriceIndex,
        feedName,
        genericData
    );
  }

  Instruction resetTwap(final PublicKey adminKey,
                        final PublicKey configurationKey,
                        final PublicKey oracleTwapsKey,
                        final long token,
                        final String feedName);

  default Instruction resetTwap(final Configuration configuration,
                                final long token,
                                final String feedName) {
    return resetTwap(
        configuration.admin(),
        configuration._address(),
        configuration.oracleTwaps(),
        token,
        feedName
    );
  }

  Instruction updateTokenMetadata(final PublicKey adminKey,
                                  final PublicKey configurationKey,
                                  final PublicKey tokensMetadataKey,
                                  final long index,
                                  final long mode,
                                  final String feedName,
                                  final byte[] value);

  default Instruction updateTokenMetadata(final Configuration configuration,
                                          final long index,
                                          final long mode,
                                          final String feedName,
                                          final byte[] value) {
    return updateTokenMetadata(
        configuration.admin(),
        configuration._address(),
        configuration.tokensMetadata(),
        index,
        mode,
        feedName,
        value
    );
  }

  Instruction setAdminCached(final PublicKey adminKey,
                             final PublicKey configurationKey,
                             final PublicKey newAdmin,
                             final String feedName);

  default Instruction setAdminCached(final Configuration configuration,
                                     final PublicKey newAdmin,
                                     final String feedName) {
    return setAdminCached(
        configuration.admin(),
        configuration._address(),
        newAdmin,
        feedName
    );
  }

  Instruction approveAdminCached(final PublicKey adminCachedKey,
                                 final PublicKey configurationKey,
                                 final String feedName);

  default Instruction approveAdminCached(final Configuration configuration,
                                         final String feedName) {
    return approveAdminCached(configuration.adminCached(), configuration._address(), feedName);
  }

  Instruction createMintMap(final PublicKey adminKey,
                            final PublicKey configurationKey,
                            final PublicKey mappingsKey,
                            final PublicKey seedPk,
                            final long seedId,
                            final int bump,
                            final short[][] scopeChains);

  default Instruction createMintMap(final Configuration configuration,
                                    final PublicKey seedPk,
                                    final long seedId,
                                    final int bump,
                                    final short[][] scopeChains) {
    return createMintMap(
        configuration.admin(),
        configuration._address(),
        configuration.oracleMappings(),
        seedPk,
        seedId,
        bump,
        scopeChains
    );
  }

  Instruction closeMintMap(final PublicKey adminKey,
                           final PublicKey configurationKey,
                           final PublicKey mappingsKey);

  default Instruction closeMintMap(final Configuration configuration) {
    return closeMintMap(
        configuration.admin(),
        configuration._address(),
        configuration.oracleMappings()
    );
  }
}
