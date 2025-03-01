package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamProgram;
import software.sava.core.accounts.AccountWithSeed;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.lookup.AddressLookupTable;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.accounts.sysvar.Clock;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramAccountClient;
import software.sava.solana.programs.clients.NativeProgramClient;
import software.sava.solana.programs.stake.LockUp;
import software.sava.solana.programs.stake.StakeAccount;
import software.sava.solana.programs.stake.StakeAuthorize;
import software.sava.solana.programs.stake.StakeState;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.OptionalLong;
import java.util.concurrent.CompletableFuture;

import static software.sava.core.accounts.meta.AccountMeta.createFeePayer;

final class GlamNativeProgramClientImpl implements GlamNativeProgramClient {

  private final SolanaAccounts solanaAccounts;
  private final NativeProgramClient nativeProgramClient;
  private final GlamVaultAccounts glamVaultAccounts;
  private final AccountMeta invokedProgram;
  private final AccountMeta feePayer;

  GlamNativeProgramClientImpl(final SolanaAccounts solanaAccounts, final GlamVaultAccounts glamVaultAccounts) {
    this.solanaAccounts = solanaAccounts;
    this.nativeProgramClient = NativeProgramClient.createClient(solanaAccounts);
    this.glamVaultAccounts = glamVaultAccounts;
    this.invokedProgram = glamVaultAccounts.glamAccounts().invokedProgram();
    this.feePayer = createFeePayer(glamVaultAccounts.feePayer());
  }

  @Override
  public SolanaAccounts accounts() {
    return solanaAccounts;
  }

  @Override
  public NativeProgramAccountClient createAccountClient(final AccountMeta ownerAndFeePayer) {
    return GlamProgramAccountClient.createClient(
        solanaAccounts,
        GlamVaultAccounts.createAccounts(
            glamVaultAccounts.glamAccounts(),
            ownerAndFeePayer.publicKey(),
            glamVaultAccounts.glamPublicKey()
        )
    );
  }

  @Override
  public NativeProgramAccountClient createAccountClient(final PublicKey owner, final AccountMeta feePayer) {
    return GlamProgramAccountClient.createClient(
        solanaAccounts,
        GlamVaultAccounts.createAccounts(
            glamVaultAccounts.glamAccounts(),
            feePayer.publicKey(),
            glamVaultAccounts.glamPublicKey()
        )
    );
  }

  @Override
  public CompletableFuture<AccountInfo<Clock>> fetchClockSysVar(final SolanaRpcClient rpcClient) {
    return nativeProgramClient.fetchClockSysVar(rpcClient);
  }

  @Override
  public Instruction computeUnitLimit(final int computeUnitLimit) {
    return nativeProgramClient.computeUnitLimit(computeUnitLimit);
  }

  @Override
  public Instruction computeUnitPrice(final long computeUnitPrice) {
    return nativeProgramClient.computeUnitPrice(computeUnitPrice);
  }

  @Override
  public Instruction allocateAccountSpace(final PublicKey newAccountPublicKey, final long space) {
    return nativeProgramClient.allocateAccountSpace(newAccountPublicKey, space);
  }

  @Override
  public Instruction syncNative(final PublicKey tokenAccount) {
    return nativeProgramClient.syncNative(tokenAccount);
  }

  @Override
  public CompletableFuture<List<AccountInfo<StakeAccount>>> fetchStakeAccountsByStakeAuthority(final SolanaRpcClient rpcClient,
                                                                                               final StakeState stakeState,
                                                                                               final PublicKey staker) {
    return nativeProgramClient.fetchStakeAccountsByStakeAuthority(rpcClient, stakeState, staker);
  }

  @Override
  public CompletableFuture<List<AccountInfo<StakeAccount>>> fetchStakeAccountsByWithdrawAuthority(final SolanaRpcClient rpcClient,
                                                                                                  final StakeState stakeState,
                                                                                                  final PublicKey withdrawer) {
    return nativeProgramClient.fetchStakeAccountsByWithdrawAuthority(rpcClient, stakeState, withdrawer);
  }

  @Override
  public CompletableFuture<List<AccountInfo<StakeAccount>>> fetchStakeAccountsByStakeAndWithdrawAuthority(final SolanaRpcClient rpcClient,
                                                                                                          final StakeState stakeState,
                                                                                                          final PublicKey withdrawer) {
    return nativeProgramClient.fetchStakeAccountsByStakeAndWithdrawAuthority(rpcClient, stakeState, withdrawer);
  }

  @Override
  public CompletableFuture<List<AccountInfo<StakeAccount>>> fetchStakeAccountsWithCustodian(final SolanaRpcClient rpcClient,
                                                                                            final StakeState stakeState,
                                                                                            final PublicKey custodian) {
    return nativeProgramClient.fetchStakeAccountsWithCustodian(rpcClient, stakeState, custodian);
  }

  @Override
  public CompletableFuture<List<AccountInfo<StakeAccount>>> fetchStakeAccountsForValidator(final SolanaRpcClient rpcClient,
                                                                                           final StakeState stakeState,
                                                                                           final PublicKey voteAccount) {
    return nativeProgramClient.fetchStakeAccountsForValidator(rpcClient, stakeState, voteAccount);
  }

  @Override
  public CompletableFuture<List<AccountInfo<StakeAccount>>> fetchStakeAccountsForValidatorAndWithdrawAuthority(final SolanaRpcClient rpcClient,
                                                                                                               final StakeState stakeState,
                                                                                                               final PublicKey voteAccount,
                                                                                                               final PublicKey withdrawAuthority) {
    return nativeProgramClient.fetchStakeAccountsForValidatorAndWithdrawAuthority(rpcClient, stakeState, voteAccount, withdrawAuthority);
  }

  @Override
  public CompletableFuture<List<AccountInfo<StakeAccount>>> fetchStakeAccountsForValidatorAndStakeAndWithdrawAuthority(
      final SolanaRpcClient rpcClient,
      final StakeState stakeState,
      final PublicKey voteAccount,
      final PublicKey withdrawAuthority) {
    return nativeProgramClient.fetchStakeAccountsForValidatorAndStakeAndWithdrawAuthority(rpcClient, stakeState, voteAccount, withdrawAuthority);
  }

  @Override
  public CompletableFuture<List<AccountInfo<AddressLookupTable>>> fetchLookupTableAccountsByAuthority(final SolanaRpcClient rpcClient,
                                                                                                      final PublicKey authority) {
    return nativeProgramClient.fetchLookupTableAccountsByAuthority(rpcClient, authority);
  }

  @Override
  public Instruction deactivateDelinquentStake(final PublicKey delegatedStakeAccount,
                                               final PublicKey delinquentVoteAccount,
                                               final PublicKey referenceVoteAccount) {
    return nativeProgramClient.deactivateDelinquentStake(delegatedStakeAccount, delinquentVoteAccount, referenceVoteAccount);
  }

  @Override
  public Instruction setStakeAccountLockup(final PublicKey initializedStakeAccount,
                                           final PublicKey lockupOrWithdrawAuthority,
                                           final Instant timestamp,
                                           final OptionalLong epoch,
                                           final PublicKey custodian) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Instruction setStakeAccountLockupChecked(final PublicKey initializedStakeAccount,
                                                  final PublicKey lockupOrWithdrawAuthority,
                                                  final PublicKey newLockupAuthority,
                                                  final Instant timestamp,
                                                  final OptionalLong epoch) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Instruction authorizeStakeAccount(final PublicKey stakeAccount,
                                           final PublicKey stakeOrWithdrawAuthority,
                                           final PublicKey lockupAuthority,
                                           final PublicKey newAuthority,
                                           final StakeAuthorize stakeAuthorize) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Instruction authorizeStakeAccountChecked(final PublicKey stakeAccount,
                                                  final PublicKey stakeOrWithdrawAuthority,
                                                  final PublicKey newStakeOrWithdrawAuthority,
                                                  final PublicKey lockupAuthority,
                                                  final StakeAuthorize stakeAuthorize) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Instruction authorizeStakeAccountWithSeed(final PublicKey stakeAccount,
                                                   final AccountWithSeed baseKeyOrWithdrawAuthority,
                                                   final PublicKey lockupAuthority,
                                                   final PublicKey newAuthorizedPublicKey,
                                                   final StakeAuthorize stakeAuthorize,
                                                   final PublicKey authorityOwner) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Instruction authorizeStakeAccountCheckedWithSeed(final PublicKey stakeAccount,
                                                          final AccountWithSeed baseKeyOrWithdrawAuthority,
                                                          final PublicKey stakeOrWithdrawAuthority,
                                                          final PublicKey lockupAuthority,
                                                          final StakeAuthorize stakeAuthorize,
                                                          final PublicKey authorityOwner) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Instruction initializeStakeAccount(final PublicKey unInitializedStakeAccount,
                                            final PublicKey staker,
                                            final PublicKey withdrawer,
                                            final LockUp lockUp) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Instruction initializeStakeAccountChecked(final PublicKey unInitializedStakeAccount,
                                                   final PublicKey staker,
                                                   final PublicKey withdrawer) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Instruction delegateStakeAccount(final PublicKey initializedStakeAccount,
                                          final PublicKey validatorVoteAccount,
                                          final PublicKey stakeAuthority) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Instruction delegateStakeAccount(final StakeAccount initializedStakeAccount,
                                          final PublicKey validatorVoteAccount) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Instruction reDelegateStakeAccount(final StakeAccount delegatedStakeAccount,
                                            final PublicKey uninitializedStakeAccount,
                                            final PublicKey validatorVoteAccount) {
    return GlamProgram.redelegateStake(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        delegatedStakeAccount.address(),
        uninitializedStakeAccount,
        validatorVoteAccount,
        solanaAccounts.stakeConfig()
    );
  }

  @Override
  public Instruction splitStakeAccount(final StakeAccount splitStakeAccount,
                                       final PublicKey unInitializedStakeAccount,
                                       final long lamports) {
    return GlamProgram.splitStakeAccount(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        splitStakeAccount.address(),
        unInitializedStakeAccount,
        lamports
    );
  }

  @Override
  public Instruction mergeStakeAccounts(final StakeAccount destinationStakeAccount, final PublicKey srcStakeAccount) {
    return GlamProgram.mergeStakeAccounts(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        destinationStakeAccount.address(),
        srcStakeAccount
    );
  }

  @Override
  public Instruction withdrawStakeAccount(final StakeAccount stakeAccount,
                                          final PublicKey recipient,
                                          final long lamports) {
    throw new UnsupportedOperationException();
  }

  private Instruction deactivateStakeAccounts() {
    return GlamProgram.deactivateStakeAccounts(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey()
    );
  }

  @Override
  public Instruction deactivateStakeAccount(final PublicKey delegatedStakeAccount, final PublicKey stakeAuthority) {
    return deactivateStakeAccounts().extraAccount(delegatedStakeAccount, AccountMeta.CREATE_WRITE);
  }

  @Override
  public List<Instruction> deactivateStakeAccountInfos(final Collection<AccountInfo<StakeAccount>> stakeAccounts) {
    final var extraAccounts = stakeAccounts.stream().map(AccountInfo::pubKey).toList();
    return List.of(deactivateStakeAccounts().extraAccounts(extraAccounts, AccountMeta.CREATE_WRITE));
  }

  @Override
  public List<Instruction> deactivateStakeAccounts(final Collection<StakeAccount> stakeAccounts) {
    final var extraAccounts = stakeAccounts.stream().map(StakeAccount::address).toList();
    return List.of(deactivateStakeAccounts().extraAccounts(extraAccounts, AccountMeta.CREATE_WRITE));
  }

  @Override
  public Instruction jupiterSetMaxSwapSlippage(final int slippageBps) {
    return GlamProgram.jupiterSetMaxSwapSlippage(
        invokedProgram,
        glamVaultAccounts.glamPublicKey(),
        feePayer.publicKey(),
        slippageBps
    );
  }
}
