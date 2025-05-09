package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamProtocolProgram;
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
    if (stakeAuthorize != StakeAuthorize.Staker) {
      throw new IllegalStateException("Only the Staker authority may be changed.");
    }
    return GlamProtocolProgram.stakeAuthorize(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        stakeAccount,
        stakeOrWithdrawAuthority,
        0
    );
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
    return GlamProtocolProgram.stakeInitialize(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        unInitializedStakeAccount
    );
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
    return GlamProtocolProgram.stakeDelegateStake(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        initializedStakeAccount,
        validatorVoteAccount,
        solanaAccounts.stakeConfig()
    );
  }

  @Override
  public Instruction reDelegateStakeAccount(final StakeAccount delegatedStakeAccount,
                                            final PublicKey uninitializedStakeAccount,
                                            final PublicKey validatorVoteAccount) {
    return GlamProtocolProgram.stakeRedelegate(
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
    return GlamProtocolProgram.stakeSplit(
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
    return GlamProtocolProgram.stakeMerge(
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
    return GlamProtocolProgram.stakeWithdraw(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        stakeAccount.address(),
        lamports
    );
  }

  private Instruction deactivateStakeAccount(final PublicKey delegatedStakeAccount) {
    return GlamProtocolProgram.stakeDeactivate(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        delegatedStakeAccount
    );
  }

  @Override
  public Instruction deactivateStakeAccount(final PublicKey delegatedStakeAccount, final PublicKey stakeAuthority) {
    return deactivateStakeAccount(delegatedStakeAccount);
  }

  @Override
  public Instruction deactivateStakeAccount(final StakeAccount delegatedStakeAccount) {
    return deactivateStakeAccount(delegatedStakeAccount.address());
  }

  @Override
  public Instruction moveStake(final StakeAccount sourceStakeAccount,
                               final PublicKey destinationStakeAccount,
                               final long lamports) {
    return GlamProtocolProgram.stakeMoveStake(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        sourceStakeAccount.address(),
        destinationStakeAccount,
        lamports
    );
  }

  @Override
  public Instruction moveLamports(final StakeAccount sourceStakeAccount,
                                  final PublicKey destinationStakeAccount,
                                  final long lamports) {
    return GlamProtocolProgram.stakeMoveLamports(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        sourceStakeAccount.address(),
        destinationStakeAccount,
        lamports
    );
  }
}
