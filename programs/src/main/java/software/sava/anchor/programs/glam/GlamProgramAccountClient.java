package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.types.*;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static software.sava.anchor.programs.glam.anchor.types.EngineFieldName.DelegateAcls;
import static software.sava.anchor.programs.glam.anchor.types.EngineFieldName.IntegrationAcls;

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

  static CompletableFuture<List<AccountInfo<FundAccount>>> fetchGlamAccountsByOwner(final SolanaRpcClient rpcClient,
                                                                                    final PublicKey ownerPublicKey,
                                                                                    final PublicKey programPublicKey) {
    return rpcClient.getProgramAccounts(
        programPublicKey,
        List.of(
            FundAccount.DISCRIMINATOR_FILTER,
            FundAccount.createOwnerFilter(ownerPublicKey)
        ),
        FundAccount.FACTORY
    );
  }

  static boolean isDelegatedWithPermission(final FundAccount glamAccount, final PublicKey delegate) {
    for (final var engineFields : glamAccount.params()) {
      for (final var engineField : engineFields) {
        if (engineField.name() == DelegateAcls && engineField.value()
            instanceof EngineFieldValue.VecDelegateAcl(final var delegateAcls)) {
          for (final var delegateAcl : delegateAcls) {
            if (delegate.equals(delegateAcl.pubkey())) {
              return true;
            }
          }
          return false;
        }
      }
    }
    return false;
  }

  static boolean isDelegatedWithPermission(final FundAccount glamAccount,
                                           final PublicKey delegate,
                                           final Permission permission) {
    for (final var engineFields : glamAccount.params()) {
      for (final var engineField : engineFields) {
        if (engineField.name() == DelegateAcls && engineField.value()
            instanceof EngineFieldValue.VecDelegateAcl(final var delegateAcls)) {
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

  static void removePresentPermissions(final FundAccount glamAccount,
                                       final PublicKey delegateKey,
                                       final Set<Permission> requiredPermissions,
                                       final Set<IntegrationName> requiredIntegrations) {
    for (final var engineFields : glamAccount.params()) {
      for (final var engineField : engineFields) {
        final var engineFieldName = engineField.name();
        if (engineFieldName == DelegateAcls) {
          if (engineField.value() instanceof EngineFieldValue.VecDelegateAcl(final var delegateAcls)) {
            for (final var delegateAcl : delegateAcls) {
              if (delegateKey.equals(delegateAcl.pubkey())) {
                for (final var permission : delegateAcl.permissions()) {
                  requiredPermissions.remove(permission);
                }
                if (requiredIntegrations.isEmpty()) {
                  break;
                }
              }
            }
          }
        } else if (engineFieldName == IntegrationAcls) {
          if (engineField.value() instanceof EngineFieldValue.VecIntegrationAcl(final var integrationAcls)) {
            for (final var integrationAcl : integrationAcls) {
              requiredIntegrations.remove(integrationAcl.name());
            }
            if (requiredPermissions.isEmpty()) {
              break;
            }
          }
        }
      }
    }
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

  Instruction initializeFund(final StateModel fundModel);

  Instruction addShareClass(final int shareClassId, final ShareClassModel shareClassModel);

  Instruction updateFund(final StateModel fundModel);

  Instruction closeFund();

  Instruction closeShareClass(final PublicKey shareClassKey, final int shareClassId);

  Instruction subscribe(final PublicKey assetKey,
                        final PublicKey vaultAssetATAKey,
                        final PublicKey assetATAKey,
                        final int shareClassId,
                        final long amount);

  Instruction redeem(final int shareClassId, final long amount,
                     final boolean inKind);
}
