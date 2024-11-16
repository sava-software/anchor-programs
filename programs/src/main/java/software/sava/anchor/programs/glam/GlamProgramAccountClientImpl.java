package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamPDAs;
import software.sava.anchor.programs.glam.anchor.GlamProgram;
import software.sava.anchor.programs.glam.anchor.types.FundModel;
import software.sava.anchor.programs.glam.anchor.types.ShareClassModel;
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

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static software.sava.core.accounts.meta.AccountMeta.createFeePayer;

final class GlamProgramAccountClientImpl implements GlamProgramAccountClient {

  private final SolanaAccounts solanaAccounts;
  private final NativeProgramClient nativeProgramClient;
  private final NativeProgramAccountClient nativeProgramAccountClient;
  private final GlamFundAccounts glamFundAccounts;
  private final AccountMeta invokedProgram;
  private final AccountMeta manager;

  GlamProgramAccountClientImpl(final SolanaAccounts solanaAccounts, final GlamFundAccounts glamFundAccounts) {
    this.solanaAccounts = solanaAccounts;
    this.nativeProgramClient = NativeProgramClient.createClient(solanaAccounts);
    this.glamFundAccounts = glamFundAccounts;
    this.invokedProgram = glamFundAccounts.glamAccounts().invokedProgram();
    this.manager = createFeePayer(glamFundAccounts.signerPublicKey());
    this.nativeProgramAccountClient = nativeProgramClient.createAccountClient(glamFundAccounts.treasuryPublicKey(), manager);
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
  public GlamFundAccounts fundAccounts() {
    return glamFundAccounts;
  }

  @Override
  public PublicKey ownerPublicKey() {
    return nativeProgramAccountClient.ownerPublicKey();
  }

  @Override
  public AccountMeta feePayer() {
    return manager;
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
  public CompletableFuture<List<AccountInfo<TokenAccount>>> fetchTokenAccounts(final SolanaRpcClient rpcClient, final PublicKey tokenMintAddress) {
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
    throw new UnsupportedOperationException("TODO: createLookupTable");
  }

  @Override
  public Instruction freezeLookupTable(final PublicKey tableAccount) {
    throw new UnsupportedOperationException("TODO: freezeLookupTable");
  }

  @Override
  public Instruction extendLookupTable(final PublicKey tableAccount, final List<PublicKey> newAddresses) {
    throw new UnsupportedOperationException("TODO: extendLookupTable");
  }

  @Override
  public Instruction deactivateLookupTable(final PublicKey tableAccount) {
    throw new UnsupportedOperationException("TODO: deactivateLookupTable");
  }

  @Override
  public Instruction closeLookupTable(final PublicKey tableAccount) {
    throw new UnsupportedOperationException("TODO: closeLookupTable");
  }

  @Override
  public Instruction transferLamportsAndSyncNative(final long lamports) {
    return GlamProgram.wsolWrap(
        invokedProgram,
        solanaAccounts,
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        wrappedSolPDA().publicKey(),
        manager.publicKey(),
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
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        wrappedSolPDA().publicKey(),
        manager.publicKey()
    );
  }

  @Override
  public Instruction createAccount(final PublicKey newAccountPublicKey, final long lamports, final long space, final PublicKey programOwner) {
    throw new UnsupportedOperationException("TODO: createAccount");
  }

  @Override
  public Instruction createAccountWithSeed(final AccountWithSeed accountWithSeed, final long lamports, final long space, final PublicKey programOwner) {
    throw new UnsupportedOperationException("TODO: createAccountWithSeed");
  }

  @Override
  public Instruction transferSolLamports(final PublicKey toPublicKey, final long lamports) {
    throw new UnsupportedOperationException("TODO: transferSolLamports");
  }

  @Override
  public Instruction allocateAccountSpaceWithSeed(final AccountWithSeed accountWithSeed, final long space, final PublicKey programOwner) {
    throw new UnsupportedOperationException("TODO: allocateAccountSpaceWithSeed");
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
  public FundPDA createStakeAccountPDA() {
    return FundPDA.createPDA("stake_account", glamFundAccounts.fundPublicKey(), invokedProgram.publicKey());
  }

  @Override
  public Instruction initializeAndDelegateStake(final FundPDA stakeAccountPDA,
                                                final PublicKey validatorVoteAccount,
                                                final long lamports) {
    final var pda = stakeAccountPDA.pda();
    return GlamProgram.initializeAndDelegateStake(
        invokedProgram,
        solanaAccounts,
        manager.publicKey(),
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
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
                                       final FundPDA newStakeAccountPDA,
                                       final long lamports) {
    return GlamProgram.splitStakeAccount(
        invokedProgram,
        solanaAccounts,
        manager.publicKey(),
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
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
        manager.publicKey(),
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey()
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

  @Override
  public Instruction initializeFund(final FundModel fundModel) {
    return GlamProgram.initializeFund(
        invokedProgram,
        solanaAccounts,
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.openFundsPDA().publicKey(),
        glamFundAccounts.treasuryPublicKey(),
        manager.publicKey(),
        fundModel
    );
  }

  @Override
  public Instruction addShareClass(final int shareClassId, final ShareClassModel shareClassModel) {
    return GlamProgram.addShareClass(
        invokedProgram,
        solanaAccounts,
        glamFundAccounts.shareClassPDA(shareClassId).publicKey(),
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.openFundsPDA().publicKey(),
        glamFundAccounts.treasuryPublicKey(),
        manager.publicKey(),
        shareClassModel
    );
  }

  @Override
  public Instruction updateFund(final FundModel fundModel) {
    return GlamProgram.updateFund(
        invokedProgram,
        glamFundAccounts.fundPublicKey(),
        manager.publicKey(),
        fundModel
    );
  }

  @Override
  public Instruction closeFund() {
    return GlamProgram.closeFund(
        invokedProgram,
        solanaAccounts,
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.openFundsPDA().publicKey(),
        glamFundAccounts.treasuryPublicKey(),
        manager.publicKey()
    );
  }

  @Override
  public Instruction closeShareClass(final PublicKey shareClassMintKey, final int shareClassId) {
    final var shareClassPDA = glamFundAccounts.shareClassPDA(shareClassId).publicKey();
    final var extraAccountMetaListPDA = GlamPDAs.extraAccountMetaListPDA(invokedProgram.publicKey(), shareClassPDA);
    final var openFundsPDA = GlamPDAs.openfundsPDA(invokedProgram.publicKey(), glamFundAccounts.fundPublicKey());
    return GlamProgram.closeShareClass(
        invokedProgram,
        solanaAccounts,
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        shareClassMintKey,
        extraAccountMetaListPDA.publicKey(),
        openFundsPDA.publicKey(),
        manager.publicKey(),
        shareClassId
    );
  }


  @Override
  public Instruction subscribe(final PublicKey assetKey,
                               final PublicKey treasuryAssetATAKey,
                               final PublicKey assetATAKey,
                               final int shareClassId,
                               final long amount) {
    final var shareClassPDA = glamFundAccounts.shareClassPDA(shareClassId).publicKey();
    final var shareClassATA = AssociatedTokenProgram.findATA2022(solanaAccounts, manager.publicKey(), shareClassPDA).publicKey();
    final var policyPDA = GlamPDAs.signerPolicyPDA(invokedProgram.publicKey(), shareClassATA);
    return GlamProgram.subscribe(
        invokedProgram,
        solanaAccounts,
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        shareClassPDA,
        shareClassATA,
        assetKey,
        treasuryAssetATAKey,
        assetATAKey,
        policyPDA.publicKey(),
        manager.publicKey(),
        amount,
        true
    );
  }

  @Override
  public Instruction redeem(final int shareClassId,
                            final long amount,
                            final boolean inKind) {
    final var shareClassPDA = glamFundAccounts.shareClassPDA(shareClassId).publicKey();
    final var shareClassATA = AssociatedTokenProgram.findATA2022(solanaAccounts, manager.publicKey(), shareClassPDA).publicKey();
    final var policyPDA = GlamPDAs.signerPolicyPDA(invokedProgram.publicKey(), shareClassATA);
    return GlamProgram.redeem(
        invokedProgram,
        solanaAccounts,
        glamFundAccounts.fundPublicKey(),
        shareClassPDA,
        shareClassATA,
        manager.publicKey(),
        glamFundAccounts.treasuryPublicKey(),
        policyPDA.publicKey(),
        amount,
        inKind,
        true
    );
  }
}
