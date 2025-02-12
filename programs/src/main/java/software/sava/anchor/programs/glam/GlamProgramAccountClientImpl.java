package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamProgram;
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

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static software.sava.core.accounts.meta.AccountMeta.createFeePayer;

final class GlamProgramAccountClientImpl implements GlamProgramAccountClient {

  private final SolanaAccounts solanaAccounts;
  private final NativeProgramClient nativeProgramClient;
  private final NativeProgramAccountClient nativeProgramAccountClient;
  private final GlamVaultAccounts glamVaultAccounts;
  private final AccountMeta invokedProgram;
  private final AccountMeta feePayer;

  GlamProgramAccountClientImpl(final SolanaAccounts solanaAccounts, final GlamVaultAccounts glamVaultAccounts) {
    this.solanaAccounts = solanaAccounts;
    this.nativeProgramClient = GlamNativeProgramClient.createClient(solanaAccounts, glamVaultAccounts);
    this.glamVaultAccounts = glamVaultAccounts;
    this.invokedProgram = glamVaultAccounts.glamAccounts().invokedProgram();
    this.feePayer = createFeePayer(glamVaultAccounts.feePayer());
    this.nativeProgramAccountClient = NativeProgramAccountClient.createClient(solanaAccounts, glamVaultAccounts.vaultPublicKey(), feePayer);
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
  public Instruction transferLamportsAndSyncNative(final long lamports) {
    return GlamProgram.wsolWrap(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        wrappedSolPDA().publicKey(),
        feePayer.publicKey(),
        lamports
    );
  }

  @Override
  public List<Instruction> wrapSOL(final long lamports) {
    return List.of(transferLamportsAndSyncNative(lamports));
  }

  @Override
  public Instruction unwrapSOL() {
    return GlamProgram.wsolUnwrap(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        wrappedSolPDA().publicKey(),
        feePayer.publicKey()
    );
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
  public Instruction transferSolLamports(final PublicKey toPublicKey, final long lamports) {
    throw new UnsupportedOperationException("TODO: transferSolLamports");
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
  public Instruction transferToken(final PublicKey fromTokenAccount,
                                   final PublicKey toTokenAccount,
                                   final long lamports) {
    throw new UnsupportedOperationException("TODO: transferToken");
  }

  @Override
  public Instruction transferTokenChecked(final PublicKey fromTokenAccount,
                                          final PublicKey toTokenAccount,
                                          final long lamports,
                                          final int decimals,
                                          final PublicKey tokenMint) {
    throw new UnsupportedOperationException("TODO: transferTokenChecked");
  }

  @Override
  public Instruction closeTokenAccount(final PublicKey tokenAccount) {
    throw new UnsupportedOperationException("TODO: closeTokenAccount");
  }

  @Override
  public VaultPDA createStakeAccountPDA() {
    return VaultPDA.createPDA("stake_account", glamVaultAccounts.glamPublicKey(), invokedProgram.publicKey());
  }

  @Override
  public Instruction initializeAndDelegateStake(final VaultPDA stakeAccountPDA,
                                                final PublicKey validatorVoteAccount,
                                                final long lamports) {
    final var pda = stakeAccountPDA.pda();
    return GlamProgram.initializeAndDelegateStake(
        invokedProgram,
        solanaAccounts,
        feePayer.publicKey(),
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        pda.publicKey(),
        validatorVoteAccount,
        solanaAccounts.stakeConfig(),
        lamports,
        stakeAccountPDA.id(),
        pda.nonce()
    );
  }

  @Override
  public Instruction splitStakeAccount(final PublicKey existingStakeAccount,
                                       final VaultPDA newStakeAccountPDA,
                                       final long lamports) {
    return GlamProgram.splitStakeAccount(
        invokedProgram,
        solanaAccounts,
        feePayer.publicKey(),
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        existingStakeAccount,
        newStakeAccountPDA.pda().publicKey(),
        lamports,
        newStakeAccountPDA.id(),
        newStakeAccountPDA.pda().nonce()
    );
  }

  @Override
  public Instruction deactivateStakeAccount(final PublicKey stakeAccount) {
    return nativeProgramClient.deactivateStakeAccount(stakeAccount, null);
  }


  private Instruction closeStakeAccounts() {
    return GlamProgram.withdrawFromStakeAccounts(
        invokedProgram,
        solanaAccounts,
        feePayer.publicKey(),
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey()
    );
  }

  @Override
  public Instruction closeStakeAccount(final AccountInfo<StakeAccount> stakeAccountInfo) {
    return closeStakeAccounts().extraAccount(stakeAccountInfo.pubKey(), AccountMeta.CREATE_WRITE);
  }

  @Override
  public List<Instruction> closeStakeAccounts(final Collection<AccountInfo<StakeAccount>> stakeAccounts) {
    final var extraAccounts = stakeAccounts.stream().map(AccountInfo::pubKey).toList();
    return List.of(closeStakeAccounts().extraAccounts(extraAccounts, AccountMeta.CREATE_WRITE));
  }

  @Override
  public Instruction withdrawStakeAccount(final StakeAccount stakeAccount, final long lamports) {
    throw new UnsupportedOperationException("TODO: withdrawStakeAccount with specific amount of lamports");
  }
}
