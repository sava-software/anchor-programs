package software.sava.anchor.programs.kamino.scope;

import software.sava.anchor.programs.kamino.KaminoAccounts;
import software.sava.anchor.programs.kamino.scope.anchor.ScopeProgram;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

final class ScopeProgramClientImpl implements ScopeProgramClient {

  private final NativeProgramAccountClient nativeProgramAccountClient;
  private final SolanaAccounts solanaAccounts;
  private final KaminoAccounts kaminoAccounts;
  private final AccountMeta invokedScopeProgram;
  private final PublicKey authority;
  private final PublicKey feePayer;

  ScopeProgramClientImpl(final NativeProgramAccountClient nativeProgramAccountClient,
                         final KaminoAccounts kaminoAccounts) {
    this.nativeProgramAccountClient = nativeProgramAccountClient;
    this.solanaAccounts = nativeProgramAccountClient.solanaAccounts();
    this.kaminoAccounts = kaminoAccounts;
    this.invokedScopeProgram = kaminoAccounts.invokedScopePricesProgram();
    this.authority = nativeProgramAccountClient.ownerPublicKey();
    this.feePayer = nativeProgramAccountClient.feePayer().publicKey();
  }

  @Override
  public SolanaAccounts solanaAccounts() {
    return solanaAccounts;
  }

  @Override
  public KaminoAccounts kaminoAccounts() {
    return kaminoAccounts;
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
  public Instruction initialize(final PublicKey adminKey,
                                final PublicKey configurationKey,
                                final PublicKey tokenMetadatasKey,
                                final PublicKey oracleTwapsKey,
                                final PublicKey oraclePricesKey,
                                final PublicKey oracleMappingsKey,
                                final String feedName) {
    return ScopeProgram.initialize(
        invokedScopeProgram,
        adminKey,
        solanaAccounts.systemProgram(),
        configurationKey,
        tokenMetadatasKey,
        oracleTwapsKey,
        oraclePricesKey,
        oracleMappingsKey,
        feedName
    );
  }

  @Override
  public Instruction refreshPriceList(final PublicKey oraclePricesKey,
                                      final PublicKey oracleMappingsKey,
                                      final PublicKey oracleTwapsKey,
                                      final short[] tokens) {
    return ScopeProgram.refreshPriceList(
        invokedScopeProgram,
        oraclePricesKey,
        oracleMappingsKey,
        oracleTwapsKey,
        solanaAccounts.instructionsSysVar(),
        tokens
    );
  }

  @Override
  public Instruction refreshChainlinkPrice(final PublicKey userKey,
                                           final PublicKey oraclePricesKey,
                                           final PublicKey oracleMappingsKey,
                                           final PublicKey oracleTwapsKey,
                                           final PublicKey verifierAccountKey,
                                           final PublicKey accessControllerKey,
                                           final PublicKey configAccountKey,
                                           final PublicKey verifierProgramIdKey,
                                           final int token,
                                           final byte[] serializedChainlinkReport) {
    return ScopeProgram.refreshChainlinkPrice(
        invokedScopeProgram,
        userKey,
        oraclePricesKey,
        oracleMappingsKey,
        oracleTwapsKey,
        verifierAccountKey,
        accessControllerKey,
        configAccountKey,
        verifierProgramIdKey,
        token,
        serializedChainlinkReport
    );
  }

  @Override
  public Instruction refreshPythLazerPrice(final PublicKey userKey,
                                           final PublicKey oraclePricesKey,
                                           final PublicKey oracleMappingsKey,
                                           final PublicKey oracleTwapsKey,
                                           final PublicKey pythProgramKey,
                                           final PublicKey pythStorageKey,
                                           final PublicKey pythTreasuryKey,
                                           final short[] tokens,
                                           final byte[] serializedPythMessage,
                                           final int ed25519InstructionIndex) {
    return ScopeProgram.refreshPythLazerPrice(
        invokedScopeProgram,
        userKey,
        oraclePricesKey,
        oracleMappingsKey,
        oracleTwapsKey,
        pythProgramKey,
        pythStorageKey,
        pythTreasuryKey,
        solanaAccounts.systemProgram(),
        solanaAccounts.instructionsSysVar(),
        tokens,
        serializedPythMessage,
        ed25519InstructionIndex
    );
  }

  @Override
  public Instruction updateMapping(final PublicKey adminKey,
                                   final PublicKey configurationKey,
                                   final PublicKey oracleMappingsKey,
                                   final PublicKey priceInfoKey,
                                   final int token,
                                   final int priceType,
                                   final boolean twapEnabled,
                                   final int twapSource,
                                   final int refPriceIndex,
                                   final String feedName,
                                   final byte[] genericData) {
    return ScopeProgram.updateMapping(
        invokedScopeProgram,
        adminKey,
        configurationKey,
        oracleMappingsKey,
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

  @Override
  public Instruction resetTwap(final PublicKey adminKey,
                               final PublicKey configurationKey,
                               final PublicKey oracleTwapsKey,
                               final long token,
                               final String feedName) {
    return ScopeProgram.resetTwap(
        invokedScopeProgram,
        adminKey,
        configurationKey,
        oracleTwapsKey,
        solanaAccounts.instructionsSysVar(),
        token,
        feedName
    );
  }

  @Override
  public Instruction updateTokenMetadata(final PublicKey adminKey,
                                         final PublicKey configurationKey,
                                         final PublicKey tokensMetadataKey,
                                         final long index,
                                         final long mode,
                                         final String feedName,
                                         final byte[] value) {
    return ScopeProgram.updateTokenMetadata(
        invokedScopeProgram,
        adminKey,
        configurationKey,
        tokensMetadataKey,
        index,
        mode,
        feedName,
        value
    );
  }

  @Override
  public Instruction setAdminCached(final PublicKey adminKey,
                                    final PublicKey configurationKey,
                                    final PublicKey newAdmin,
                                    final String feedName) {
    return ScopeProgram.setAdminCached(
        invokedScopeProgram,
        adminKey,
        configurationKey,
        newAdmin,
        feedName
    );
  }

  @Override
  public Instruction approveAdminCached(final PublicKey adminCachedKey,
                                        final PublicKey configurationKey,
                                        final String feedName) {
    return ScopeProgram.approveAdminCached(
        invokedScopeProgram,
        adminCachedKey,
        configurationKey,
        feedName
    );
  }

  @Override
  public Instruction createMintMap(final PublicKey adminKey,
                                   final PublicKey configurationKey,
                                   final PublicKey mappingsKey,
                                   final PublicKey seedPk,
                                   final long seedId,
                                   final int bump,
                                   final short[][] scopeChains) {
    return ScopeProgram.createMintMap(
        invokedScopeProgram,
        adminKey,
        configurationKey,
        mappingsKey,
        solanaAccounts.systemProgram(),
        seedPk,
        seedId,
        bump,
        scopeChains
    );
  }

  @Override
  public Instruction closeMintMap(final PublicKey adminKey,
                                  final PublicKey configurationKey,
                                  final PublicKey mappingsKey) {
    return ScopeProgram.closeMintMap(
        invokedScopeProgram,
        adminKey,
        configurationKey,
        mappingsKey,
        solanaAccounts.systemProgram()
    );
  }
}
