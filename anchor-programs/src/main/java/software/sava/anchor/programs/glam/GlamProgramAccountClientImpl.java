package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.types.PriceDenom;
import software.sava.anchor.programs.glam.mint.anchor.GlamMintPDAs;
import software.sava.anchor.programs.glam.mint.anchor.GlamMintProgram;
import software.sava.anchor.programs.glam.protocol.anchor.GlamProtocolProgram;
import software.sava.anchor.programs.glam.protocol.anchor.types.StateModel;
import software.sava.anchor.programs.glam.spl.anchor.ExtSplProgram;
import software.sava.core.accounts.AccountWithSeed;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.lookup.AddressLookupTable;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.accounts.meta.LookupTableAccountMeta;
import software.sava.core.accounts.token.TokenAccount;
import software.sava.core.tx.Instruction;
import software.sava.core.tx.Transaction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramAccountClient;
import software.sava.solana.programs.clients.NativeProgramClient;
import software.sava.solana.programs.stake.StakeAccount;
import software.sava.solana.programs.stake.StakeAuthorize;
import software.sava.solana.programs.stake.StakeState;
import software.sava.solana.programs.token.AssociatedTokenProgram;

import java.util.List;
import java.util.OptionalInt;
import java.util.SequencedCollection;
import java.util.concurrent.CompletableFuture;

import static software.sava.core.accounts.meta.AccountMeta.createFeePayer;

final class GlamProgramAccountClientImpl implements GlamProgramAccountClient {

  private final SolanaAccounts solanaAccounts;
  private final GlamNativeProgramClient nativeProgramClient;
  private final NativeProgramAccountClient nativeProgramAccountClient;
  private final GlamVaultAccounts glamVaultAccounts;
  private final GlamAccounts glamAccounts;
  private final AccountMeta invokedProtocolProgram;
  private final AccountMeta feePayer;
  private final PublicKey globalConfigKey;

  GlamProgramAccountClientImpl(final SolanaAccounts solanaAccounts, final GlamVaultAccounts glamVaultAccounts) {
    this.solanaAccounts = solanaAccounts;
    this.nativeProgramClient = GlamNativeProgramClient.createClient(solanaAccounts, glamVaultAccounts);
    this.glamVaultAccounts = glamVaultAccounts;
    this.glamAccounts = glamVaultAccounts.glamAccounts();
    this.invokedProtocolProgram = glamAccounts.invokedProtocolProgram();
    this.feePayer = createFeePayer(glamVaultAccounts.feePayer());
    this.nativeProgramAccountClient = NativeProgramAccountClient.createClient(solanaAccounts, glamVaultAccounts.vaultPublicKey(), feePayer);
    this.globalConfigKey = glamVaultAccounts.glamAccounts().globalConfigPDA().publicKey();
  }

  @Override
  public NativeProgramAccountClient delegatedNativeProgramAccountClient() {
    return nativeProgramAccountClient;
  }

  @Override
  public SolanaAccounts solanaAccounts() {
    return solanaAccounts;
  }

  @Override
  public GlamVaultAccounts vaultAccounts() {
    return glamVaultAccounts;
  }

  @Override
  public PublicKey ownerPublicKey() {
    return nativeProgramAccountClient.ownerPublicKey();
  }

  @Override
  public AccountMeta feePayer() {
    return feePayer;
  }

  @Override
  public ProgramDerivedAddress wrappedSolPDA() {
    return nativeProgramAccountClient.wrappedSolPDA();
  }

  @Override
  public NativeProgramClient nativeProgramClient() {
    return nativeProgramClient;
  }

  @Override
  public Transaction createTransaction(final PublicKey feePayer,
                                       final int computeUnitLimit,
                                       final long microLamportComputeUnitPrice,
                                       final Instruction instruction) {
    return nativeProgramAccountClient.createTransaction(feePayer, computeUnitLimit, microLamportComputeUnitPrice, instruction);
  }

  @Override
  public Transaction createTransaction(final AccountMeta feePayer,
                                       final int computeUnitLimit,
                                       final long microLamportComputeUnitPrice,
                                       final Instruction instruction) {
    return nativeProgramAccountClient.createTransaction(feePayer, computeUnitLimit, microLamportComputeUnitPrice, instruction);
  }

  @Override
  public Transaction createTransaction(final Instruction instruction) {
    return nativeProgramAccountClient.createTransaction(instruction);
  }

  @Override
  public Transaction createTransaction(final List<Instruction> instructions) {
    return nativeProgramAccountClient.createTransaction(instructions);
  }

  @Override
  public Transaction createTransaction(final int computeUnitLimit,
                                       final long microLamportComputeUnitPrice,
                                       final Instruction instruction) {
    return nativeProgramAccountClient.createTransaction(computeUnitLimit, microLamportComputeUnitPrice, instruction);
  }

  @Override
  public Transaction createTransaction(final int computeUnitLimit,
                                       final long microLamportComputeUnitPrice,
                                       final List<Instruction> instructions) {
    return nativeProgramAccountClient.createTransaction(computeUnitLimit, microLamportComputeUnitPrice, instructions);
  }

  @Override
  public Transaction createTransaction(final int computeUnitLimit,
                                       final long microLamportComputeUnitPrice,
                                       final Transaction instruction) {
    return nativeProgramAccountClient.createTransaction(computeUnitLimit, microLamportComputeUnitPrice, instruction);
  }

  @Override
  public Transaction createTransaction(final PublicKey feePayer,
                                       final int computeUnitLimit,
                                       final long microLamportComputeUnitPrice,
                                       final Instruction instruction,
                                       final AddressLookupTable lookupTable) {
    return nativeProgramAccountClient.createTransaction(feePayer, computeUnitLimit, microLamportComputeUnitPrice, instruction, lookupTable);
  }

  @Override
  public Transaction createTransaction(final AccountMeta feePayer,
                                       final int computeUnitLimit,
                                       final long microLamportComputeUnitPrice,
                                       final Instruction instruction,
                                       final AddressLookupTable lookupTable) {
    return nativeProgramAccountClient.createTransaction(feePayer, computeUnitLimit, microLamportComputeUnitPrice, instruction, lookupTable);
  }

  @Override
  public Transaction createTransaction(final Instruction instruction, final AddressLookupTable lookupTable) {
    return nativeProgramAccountClient.createTransaction(instruction, lookupTable);
  }

  @Override
  public Transaction createTransaction(final List<Instruction> instructions, final AddressLookupTable lookupTable) {
    return nativeProgramAccountClient.createTransaction(instructions, lookupTable);
  }

  @Override
  public Transaction createTransaction(final int computeUnitLimit,
                                       final long microLamportComputeUnitPrice,
                                       final Instruction instruction,
                                       final AddressLookupTable lookupTable) {
    return nativeProgramAccountClient.createTransaction(computeUnitLimit, microLamportComputeUnitPrice, instruction, lookupTable);
  }

  @Override
  public Transaction createTransaction(final int computeUnitLimit,
                                       final long microLamportComputeUnitPrice,
                                       final List<Instruction> instructions,
                                       final AddressLookupTable lookupTable) {
    return nativeProgramAccountClient.createTransaction(computeUnitLimit, microLamportComputeUnitPrice, instructions, lookupTable);
  }

  @Override
  public Transaction createTransaction(final PublicKey feePayer,
                                       final int computeUnitLimit,
                                       final long microLamportComputeUnitPrice,
                                       final Instruction instruction,
                                       final LookupTableAccountMeta[] tableAccountMetas) {
    return nativeProgramAccountClient.createTransaction(feePayer, computeUnitLimit, microLamportComputeUnitPrice, instruction, tableAccountMetas);
  }

  @Override
  public Transaction createTransaction(final AccountMeta feePayer,
                                       final int computeUnitLimit,
                                       final long microLamportComputeUnitPrice,
                                       final Instruction instruction,
                                       final LookupTableAccountMeta[] tableAccountMetas) {
    return nativeProgramAccountClient.createTransaction(feePayer, computeUnitLimit, microLamportComputeUnitPrice, instruction, tableAccountMetas);
  }

  @Override
  public Transaction createTransaction(final Instruction instruction,
                                       final LookupTableAccountMeta[] tableAccountMetas) {
    return nativeProgramAccountClient.createTransaction(instruction, tableAccountMetas);
  }

  @Override
  public Transaction createTransaction(final List<Instruction> instructions,
                                       final LookupTableAccountMeta[] tableAccountMetas) {
    return nativeProgramAccountClient.createTransaction(instructions, tableAccountMetas);
  }

  @Override
  public Transaction createTransaction(final int computeUnitLimit,
                                       final long microLamportComputeUnitPrice,
                                       final Instruction instruction,
                                       final LookupTableAccountMeta[] tableAccountMetas) {
    return nativeProgramAccountClient.createTransaction(computeUnitLimit, microLamportComputeUnitPrice, instruction, tableAccountMetas);
  }

  @Override
  public Transaction createTransaction(final int computeUnitLimit,
                                       final long microLamportComputeUnitPrice,
                                       final List<Instruction> instructions,
                                       final LookupTableAccountMeta[] tableAccountMetas) {
    return nativeProgramAccountClient.createTransaction(computeUnitLimit, microLamportComputeUnitPrice, instructions, tableAccountMetas);
  }

  @Override
  public ProgramDerivedAddress findATA(final PublicKey mint) {
    return nativeProgramAccountClient.findATA(mint);
  }

  @Override
  public ProgramDerivedAddress findATA(final PublicKey tokenProgram, final PublicKey mint) {
    return nativeProgramAccountClient.findATA(tokenProgram, mint);
  }

  @Override
  public ProgramDerivedAddress findATAForFeePayer(final PublicKey mint) {
    return nativeProgramAccountClient.findATAForFeePayer(mint);
  }

  @Override
  public ProgramDerivedAddress findATAForFeePayer(final PublicKey tokenProgram, final PublicKey mint) {
    return nativeProgramAccountClient.findATAForFeePayer(tokenProgram, mint);
  }

  @Override
  public CompletableFuture<List<AccountInfo<TokenAccount>>> fetchTokenAccounts(final SolanaRpcClient rpcClient,
                                                                               final PublicKey tokenMintAddress) {
    return nativeProgramAccountClient.fetchTokenAccounts(rpcClient, tokenMintAddress);
  }

  @Override
  public CompletableFuture<List<AccountInfo<TokenAccount>>> fetchTokenAccounts(final SolanaRpcClient rpcClient) {
    return nativeProgramAccountClient.fetchTokenAccounts(rpcClient);
  }

  @Override
  public CompletableFuture<List<AccountInfo<TokenAccount>>> fetchToken2022Accounts(final SolanaRpcClient rpcClient) {
    return nativeProgramAccountClient.fetchToken2022Accounts(rpcClient);
  }

  @Override
  public Instruction syncNative() {
    return nativeProgramAccountClient.syncNative();
  }

  @Override
  public Instruction initializeStakeAccount(final PublicKey unInitializedStakeAccount, final PublicKey staker) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Instruction initializeStakeAccount(final PublicKey unInitializedStakeAccount) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Instruction initializeStakeAccountChecked(final PublicKey unInitializedStakeAccount, final PublicKey staker) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Instruction initializeStakeAccountChecked(final PublicKey unInitializedStakeAccount) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Instruction authorizeStakeAccount(final PublicKey stakeAccount,
                                           final PublicKey stakeOrWithdrawAuthority,
                                           final PublicKey lockupAuthority,
                                           final StakeAuthorize stakeAuthorize) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Instruction authorizeStakeAccount(final PublicKey stakeAccount,
                                           final PublicKey stakeOrWithdrawAuthority,
                                           final StakeAuthorize stakeAuthorize) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Instruction authorizeStakeAccountChecked(final PublicKey stakeAccount,
                                                  final PublicKey stakeOrWithdrawAuthority,
                                                  final PublicKey newStakeOrWithdrawAuthority,
                                                  final StakeAuthorize stakeAuthorize) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Instruction authorizeStakeAccountChecked(final PublicKey stakeAccount,
                                                  final PublicKey stakeOrWithdrawAuthority,
                                                  final StakeAuthorize stakeAuthorize) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Instruction delegateStakeAccount(final PublicKey initializedStakeAccount,
                                          final PublicKey validatorVoteAccount) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ProgramDerivedAddress findLookupTableAddress(final long recentSlot) {
    return nativeProgramAccountClient.findLookupTableAddress(recentSlot);
  }

  @Override
  public Instruction createLookupTable(final ProgramDerivedAddress uninitializedTableAccount, final long recentSlot) {
    return nativeProgramAccountClient.createLookupTable(uninitializedTableAccount, recentSlot);
  }

  @Override
  public Instruction freezeLookupTable(final PublicKey tableAccount) {
    return nativeProgramAccountClient.freezeLookupTable(tableAccount);
  }

  @Override
  public Instruction extendLookupTable(final PublicKey tableAccount,
                                       final SequencedCollection<PublicKey> newAddresses) {
    return nativeProgramAccountClient.extendLookupTable(tableAccount, newAddresses);
  }

  @Override
  public Instruction extendLookupTable(final PublicKey tableAccount, final List<PublicKey> newAddresses) {
    return nativeProgramAccountClient.extendLookupTable(tableAccount, newAddresses);
  }

  @Override
  public Instruction deactivateLookupTable(final PublicKey tableAccount) {
    return nativeProgramAccountClient.deactivateLookupTable(tableAccount);
  }

  @Override
  public Instruction closeLookupTable(final PublicKey tableAccount) {
    return nativeProgramAccountClient.closeLookupTable(tableAccount);
  }

  @Override
  public Instruction transferSolLamports(final PublicKey toPublicKey, final long lamports) {
    return GlamProtocolProgram.systemTransfer(
        invokedProtocolProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        toPublicKey,
        lamports
    );
  }

  @Override
  public List<Instruction> wrapSOL(final long lamports) {
    final var wrappedSolPDA = wrappedSolPDA().publicKey();
    final var transferIx = transferSolLamports(wrappedSolPDA, lamports);
    final var syncNativeIx = nativeProgramClient.syncNative(wrappedSolPDA);
    return List.of(transferIx, syncNativeIx);
  }

  @Override
  public Instruction unwrapSOL() {
    return closeTokenAccount(wrappedSolPDA().publicKey());
  }

  @Override
  public Instruction createAccount(final PublicKey newAccountPublicKey,
                                   final long lamports,
                                   final long space,
                                   final PublicKey programOwner) {
    return nativeProgramAccountClient.createAccount(newAccountPublicKey, lamports, space, programOwner);
  }

  @Override
  public Instruction createAccountWithSeed(final AccountWithSeed accountWithSeed,
                                           final long lamports,
                                           final long space,
                                           final PublicKey programOwner) {
    return nativeProgramAccountClient.createAccountWithSeed(accountWithSeed, lamports, space, programOwner);
  }

  @Override
  public Instruction allocateAccountSpaceWithSeed(final AccountWithSeed accountWithSeed,
                                                  final long space,
                                                  final PublicKey programOwner) {
    return nativeProgramAccountClient.allocateAccountSpaceWithSeed(accountWithSeed, space, programOwner);
  }

  @Override
  public AccountWithSeed createOffCurveAccountWithSeed(final String asciiSeed, final PublicKey programId) {
    return nativeProgramAccountClient.createOffCurveAccountWithSeed(asciiSeed, programId);
  }

  @Override
  public AccountWithSeed createOffCurveStakeAccountWithSeed(final String asciiSeed) {
    return nativeProgramAccountClient.createOffCurveStakeAccountWithSeed(asciiSeed);
  }

  @Override
  public CompletableFuture<List<AccountInfo<StakeAccount>>> fetchStakeAccountsByStakeAuthority(final SolanaRpcClient rpcClient,
                                                                                               final StakeState stakeState) {
    return nativeProgramAccountClient.fetchStakeAccountsByStakeAuthority(rpcClient, stakeState);
  }

  @Override
  public CompletableFuture<List<AccountInfo<StakeAccount>>> fetchStakeAccountsByWithdrawAuthority(final SolanaRpcClient rpcClient,
                                                                                                  final StakeState stakeState) {
    return nativeProgramAccountClient.fetchStakeAccountsByWithdrawAuthority(rpcClient, stakeState);
  }

  @Override
  public CompletableFuture<List<AccountInfo<AddressLookupTable>>> fetchLookupTableAccountsByAuthority(final SolanaRpcClient rpcClient) {
    return nativeProgramAccountClient.fetchLookupTableAccountsByAuthority(rpcClient);
  }

  @Override
  public Instruction createStakeAccount(final PublicKey newAccountPublicKey, final long lamports) {
    return nativeProgramAccountClient.createStakeAccount(newAccountPublicKey, lamports);
  }

  @Override
  public Instruction createStakeAccountWithSeed(final AccountWithSeed accountWithSeed, final long lamports) {
    return nativeProgramAccountClient.createStakeAccountWithSeed(accountWithSeed, lamports);
  }

  @Override
  public Instruction allocateStakeAccountWithSeed(final AccountWithSeed accountWithSeed) {
    return nativeProgramAccountClient.allocateStakeAccountWithSeed(accountWithSeed);
  }

  @Override
  public Instruction transferSolLamportsWithSeed(final AccountWithSeed accountWithSeed,
                                                 final PublicKey recipientAccount,
                                                 final long lamports,
                                                 final PublicKey programOwner) {
    throw new UnsupportedOperationException("TODO: transferSolLamportsWithSeed");
  }

  @Override
  public Instruction transferToken(final AccountMeta invokedTokenProgram,
                                   final PublicKey fromTokenAccount,
                                   final PublicKey toTokenAccount,
                                   final long scaledAmount) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Instruction transferTokenChecked(final AccountMeta invokedTokenProgram,
                                          final PublicKey fromTokenAccount,
                                          final PublicKey toTokenAccount,
                                          final long scaledAmount,
                                          final int decimals,
                                          final PublicKey tokenMint) {
    return ExtSplProgram.tokenTransferChecked(
        glamAccounts.invokedSplExtensionProgram(),
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        glamAccounts.readSplExtensionAuthority().publicKey(),
        invokedProtocolProgram.publicKey(),
        invokedTokenProgram.publicKey(),
        fromTokenAccount,
        tokenMint,
        toTokenAccount,
        scaledAmount,
        decimals
    );
  }

  @Override
  public Instruction closeTokenAccount(final AccountMeta invokedTokenProgram, final PublicKey tokenAccount) {
    return ExtSplProgram.tokenCloseAccount(
        glamAccounts.invokedSplExtensionProgram(),
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        glamAccounts.readSplExtensionAuthority().publicKey(),
        invokedTokenProgram.publicKey(),
        invokedProtocolProgram.publicKey(),
        tokenAccount
    );
  }

  @Override
  public Instruction deactivateStakeAccount(final PublicKey stakeAccount) {
    return nativeProgramClient.deactivateStakeAccount(stakeAccount, null);
  }

  @Override
  public Instruction withdrawStakeAccount(final StakeAccount stakeAccount, final long lamports) {
    return nativeProgramClient.withdrawStakeAccount(stakeAccount, ownerPublicKey(), lamports);
  }

  @Override
  public Instruction fulfill(final int mintId,
                             final PublicKey baseAssetMint,
                             final PublicKey baseAssetTokenProgram,
                             final OptionalInt limit) {
    final var glamProgram = invokedProtocolProgram.publicKey();

    final var escrow = GlamMintPDAs.glamEscrowPDA(glamProgram, glamVaultAccounts.glamPublicKey()).publicKey();

    final var mint = glamVaultAccounts.mintPDA(mintId).publicKey();
    final var escrowMintTokenAccount = AssociatedTokenProgram.findATA(solanaAccounts, escrow, solanaAccounts.token2022Program(), mint);

    final var vault = glamVaultAccounts.vaultPublicKey();
    final var vaultTokenAccount = AssociatedTokenProgram.findATA(solanaAccounts, vault, baseAssetTokenProgram, baseAssetMint);

    final var escrowTokenAccount = AssociatedTokenProgram.findATA(solanaAccounts, escrow, baseAssetTokenProgram, baseAssetMint);

    final var requestQueueKey = GlamMintPDAs.requestQueuePDA(glamAccounts.mintProgram(), mint).publicKey();
    return GlamMintProgram.fulfill(
        glamAccounts.invokedMintProgram(),
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        vault,
        mint,
        escrow,
        requestQueueKey,
        feePayer.publicKey(),
        escrowMintTokenAccount.publicKey(),
        baseAssetMint,
        vaultTokenAccount.publicKey(),
        escrowTokenAccount.publicKey(),
        baseAssetTokenProgram,
        invokedProtocolProgram.publicKey(),
        limit
    );
  }

//  public Instruction disburseFees(final int mintId,
//                                  final PublicKey baseAssetMint,
//                                  final PublicKey baseAssetTokenProgram) {
//    final var glamProgram = invokedProgram.publicKey();
//
//    final var escrow = GlamProtocolPDAs.glamEscrowPDA(glamProgram, glamVaultAccounts.glamPublicKey()).publicKey();
//
//    final var mint = glamVaultAccounts.mintPDA(mintId).publicKey();
//    final var escrowMintTokenAccount = AssociatedTokenProgram.findATA(solanaAccounts, escrow, solanaAccounts.token2022Program(), mint);
//
//    final var vault = glamVaultAccounts.vaultPublicKey();
//    final var vaultTokenAccount = AssociatedTokenProgram.findATA(solanaAccounts, vault, baseAssetTokenProgram, baseAssetMint);
//
//    final var escrowTokenAccount = AssociatedTokenProgram.findATA(solanaAccounts, escrow, baseAssetTokenProgram, baseAssetMint);
//
//    return GlamProtocolProgram.disburseFees(
//        invokedProgram,
//        solanaAccounts,
//        glamVaultAccounts.glamPublicKey(),
//        vault,
//        escrow,
//        mint,
//        feePayer.publicKey(),
//        escrowMintTokenAccount.publicKey(),
//        baseAssetMint,
//        vaultTokenAccount.publicKey(),
//        escrowTokenAccount.publicKey(),
//        glamAccounts.glamConfigKey(),
//        baseAssetTokenProgram,
//        mintId
//    );
//  }

  @Override
  public Instruction priceVaultTokens(final PublicKey solUsdOracleKey,
                                      final PublicKey baseAssetUsdOracleKey,
                                      final short[][] aggIndexes) {
    return GlamMintProgram.priceVaultTokens(
        glamAccounts.invokedMintExtensionProgram(),
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        solUsdOracleKey,
        baseAssetUsdOracleKey,
        glamAccounts.readMintExtensionAuthority().publicKey(),
        globalConfigKey,
        invokedProtocolProgram.publicKey(),
        null, null,
        aggIndexes
    );
  }

  @Override
  public Instruction priceDriftUsers(final PublicKey solUSDOracleKey,
                                     final PublicKey baseAssetUsdOracleKey,
                                     final int numUsers) {
    return GlamMintProgram.priceDriftUsers(
        glamAccounts.invokedMintExtensionProgram(),
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        solUSDOracleKey,
        baseAssetUsdOracleKey,
        glamAccounts.readMintExtensionAuthority().publicKey(),
        globalConfigKey,
        invokedProtocolProgram.publicKey(),
        null, null,
        numUsers
    );
  }

  @Override
  public Instruction priceDriftVaultDepositors(final PublicKey solOracleKey,
                                               final PublicKey baseAssetUsdOracleKey,
                                               final int numVaultDepositors,
                                               final int numSpotMarkets,
                                               final int numPerpMarkets) {
    return GlamMintProgram.priceDriftVaultDepositors(
        glamAccounts.invokedMintExtensionProgram(),
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        solOracleKey,
        baseAssetUsdOracleKey,
        glamAccounts.readMintExtensionAuthority().publicKey(),
        globalConfigKey,
        invokedProtocolProgram.publicKey(),
        null, null,
        numVaultDepositors,
        numSpotMarkets,
        numPerpMarkets
    );
  }

  @Override
  public Instruction priceKaminoObligations(final PublicKey kaminoLendingProgramKey,
                                            final PublicKey solUSDOracleKey,
                                            final PublicKey baseAssetUsdOracleKey,
                                            final PublicKey pythOracleKey,
                                            final PublicKey switchboardPriceOracleKey,
                                            final PublicKey switchboardTwapOracleKey,
                                            final PublicKey scopePricesKey,
                                            final int numObligations,
                                            final int numMarkets,
                                            final int numReserves) {
    return GlamMintProgram.priceKaminoObligations(
        glamAccounts.invokedMintExtensionProgram(),
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        kaminoLendingProgramKey,
        solUSDOracleKey,
        baseAssetUsdOracleKey,
        glamAccounts.readMintExtensionAuthority().publicKey(),
        globalConfigKey,
        invokedProtocolProgram.publicKey(),
        null, null,
        pythOracleKey,
        switchboardPriceOracleKey,
        switchboardTwapOracleKey,
        scopePricesKey,
        numObligations,
        numMarkets,
        numReserves
    );
  }

  @Override
  public Instruction priceKaminoVaultShares(final PublicKey solUSDOracleKey,
                                            final PublicKey baseAssetUsdOracleKey,
                                            final int numVaults) {
    return GlamMintProgram.priceKaminoVaultShares(
        glamAccounts.invokedMintExtensionProgram(),
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        solUSDOracleKey,
        baseAssetUsdOracleKey,
        glamAccounts.readMintExtensionAuthority().publicKey(),
        globalConfigKey,
        invokedProtocolProgram.publicKey(),
        null, null,
        numVaults
    );
  }

  @Override
  public Instruction priceMeteoraPositions(final PublicKey solOracleKey, final PriceDenom priceDenom) {
    throw new IllegalStateException("TODO: add meteora extension program");
//    return software.sava.anchor.programs.glam.anchor.GlamProtocolProgram.priceMeteoraPositions(
//        invokedProtocolProgram,
//        glamVaultAccounts.glamPublicKey(),
//        glamVaultAccounts.vaultPublicKey(),
//        feePayer.publicKey(),
//        solOracleKey,
//        glamAccounts.readMintExtensionAuthority().publicKey(),
//        globalConfigKey,
//        invokedProtocolProgram.publicKey()
//    );
  }

  @Override
  public Instruction updateState(final StateModel state) {
    return GlamProtocolProgram.updateState(
        invokedProtocolProgram,
        glamVaultAccounts.glamPublicKey(),
        feePayer.publicKey(),
        state
    );
  }
}
