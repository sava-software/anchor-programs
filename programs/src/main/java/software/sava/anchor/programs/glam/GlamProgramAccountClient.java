package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.types.*;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

import static software.sava.anchor.programs.glam.anchor.types.EngineFieldName.DelegateAcls;

public interface GlamProgramAccountClient extends NativeProgramAccountClient {

  static GlamProgramAccountClient createClient(final SolanaAccounts solanaAccounts, final GlamVaultAccounts glamVaultAccounts) {
    return new GlamProgramAccountClientImpl(solanaAccounts, glamVaultAccounts);
  }

  static GlamProgramAccountClient createClient(final SolanaAccounts solanaAccounts,
                                               final GlamAccounts glamAccounts,
                                               final PublicKey signerPublicKey,
                                               final PublicKey glamPublicKey) {
    return createClient(solanaAccounts, GlamVaultAccounts.createAccounts(glamAccounts, signerPublicKey, glamPublicKey));
  }

  static GlamProgramAccountClient createClient(final PublicKey signerPublicKey, final PublicKey glamPublicKey) {
    return createClient(SolanaAccounts.MAIN_NET, GlamAccounts.MAIN_NET, signerPublicKey, glamPublicKey);
  }

  static CompletableFuture<List<AccountInfo<FundAccount>>> fetchGlamAccounts(final SolanaRpcClient rpcClient,
                                                                             final PublicKey programPublicKey) {
    return rpcClient.getProgramAccounts(
        programPublicKey,
        List.of(FundAccount.DISCRIMINATOR_FILTER),
        FundAccount.FACTORY
    );
  }

  static CompletableFuture<List<AccountInfo<FundAccount>>> fetchGlamAccountsByManager(final SolanaRpcClient rpcClient,
                                                                                      final PublicKey managerPublicKey,
                                                                                      final PublicKey programPublicKey) {
    return rpcClient.getProgramAccounts(
        programPublicKey,
        List.of(
            FundAccount.DISCRIMINATOR_FILTER,
            FundAccount.createManagerFilter(managerPublicKey)
        ),
        FundAccount.FACTORY
    );
  }

  static Map<PublicKey, FundAccount> filterGlamAccounts(final List<AccountInfo<FundAccount>> glamFundAccounts,
                                                        final PublicKey feePayerPublicKey) {

    return glamFundAccounts.stream()
        .map(AccountInfo::data)
        .filter(accountInfo -> accountInfo.manager().equals(feePayerPublicKey))
        .collect(Collectors.toMap(FundAccount::_address, Function.identity()));
  }

  static Map<PublicKey, FundAccount> filterGlamAccounts(final List<AccountInfo<FundAccount>> glamFundAccounts,
                                                        final PublicKey feePayerPublicKey,
                                                        final String glamName) {
    return glamFundAccounts.stream()
        .map(AccountInfo::data)
        .filter(accountInfo -> accountInfo.name().equals(glamName)
            && accountInfo.manager().equals(feePayerPublicKey))
        .collect(Collectors.toMap(FundAccount::_address, Function.identity()));
  }

  static boolean isDelegatedWithPermission(final FundAccount glamAccount,
                                           final PublicKey delegate,
                                           final Permission permission) {
    for (final var engineFields : glamAccount.params()) {
      for (final var engineField : engineFields) {
        if (engineField.name() == DelegateAcls && engineField.value() instanceof EngineFieldValue.VecDelegateAcl(
            final var delegateAcls
        )) {
          for (final var delegateAcl : delegateAcls) {
            if (delegate.equals(delegateAcl.pubkey())) {
              for (final var _permission : delegateAcl.permissions()) {
                if (_permission == permission) {
                  return true;
                }
              }
              return false;
            }
          }
          return false;
        }
      }
    }
    return false;
  }

  NativeProgramAccountClient delegatedNativeProgramAccountClient();

  GlamVaultAccounts vaultAccounts();

  Instruction transferLamportsAndSyncNative(final long lamports);

  FundPDA createStakeAccountPDA();

  Instruction splitStakeAccount(final PublicKey existingStakeAccount,
                                final FundPDA newStakeAccountPDA,
                                final long lamports);

  Instruction initializeAndDelegateStake(final FundPDA stakeAccountPDA,
                                         final PublicKey validatorVoteAccount,
                                         final long lamports);

  Instruction initializeFund(final FundModel fundModel);

  Instruction addShareClass(final int shareClassId, final ShareClassModel shareClassModel);

  Instruction updateFund(final FundModel fundModel);

  Instruction closeFund();

  Instruction closeShareClass(final PublicKey shareClassKey, final int shareClassId);

  Instruction subscribe(final PublicKey assetKey,
                        final PublicKey treasuryAssetATAKey,
                        final PublicKey assetATAKey,
                        final int shareClassId,
                        final long amount);

  Instruction redeem(final int shareClassId, final long amount,
                     final boolean inKind);
}
