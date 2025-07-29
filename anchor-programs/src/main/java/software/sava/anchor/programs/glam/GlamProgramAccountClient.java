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

public interface GlamProgramAccountClient extends NativeProgramAccountClient {

  static GlamProgramAccountClient createClient(final SolanaAccounts solanaAccounts,
                                               final GlamVaultAccounts glamVaultAccounts) {
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

  static CompletableFuture<List<AccountInfo<StateAccount>>> fetchGlamAccounts(final SolanaRpcClient rpcClient,
                                                                              final PublicKey programPublicKey) {
    return rpcClient.getProgramAccounts(
        programPublicKey,
        List.of(StateAccount.DISCRIMINATOR_FILTER),
        StateAccount.FACTORY
    );
  }

  static CompletableFuture<List<AccountInfo<StateAccount>>> fetchGlamAccountsByOwner(final SolanaRpcClient rpcClient,
                                                                                     final PublicKey ownerPublicKey,
                                                                                     final PublicKey programPublicKey) {
    return rpcClient.getProgramAccounts(
        programPublicKey,
        List.of(
            StateAccount.DISCRIMINATOR_FILTER,
            StateAccount.createOwnerFilter(ownerPublicKey)
        ),
        StateAccount.FACTORY
    );
  }

  static boolean isDelegated(final StateAccount glamAccount, final PublicKey delegate) {
    for (final var delegateAcl : glamAccount.delegateAcls()) {
      if (delegate.equals(delegateAcl.pubkey())) {
        return true;
      }
    }
    return false;
  }

  static boolean isDelegatedWithPermission(final StateAccount glamAccount,
                                           final PublicKey delegate,
                                           final Permission permission) {
    for (final var delegateAcl : glamAccount.delegateAcls()) {
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

  static void removePresentPermissions(final StateAccount glamAccount,
                                       final PublicKey delegateKey,
                                       final Set<Permission> requiredPermissions,
                                       final Set<Integration> requiredIntegrations) {
    for (final var delegateAcl : glamAccount.delegateAcls()) {
      if (delegateKey.equals(delegateAcl.pubkey())) {
        for (final var permission : delegateAcl.permissions()) {
          requiredPermissions.remove(permission);
        }
        break;
      }
    }
    for (final var integration : glamAccount.integrations()) {
      requiredIntegrations.remove(integration);
    }
  }

  static boolean hasIntegration(final StateAccount glamAccount, final Integration integration) {
    for (final var _integration : glamAccount.integrations()) {
      if (_integration == integration) {
        return true;
      }
    }
    return false;
  }

  NativeProgramAccountClient delegatedNativeProgramAccountClient();

  GlamVaultAccounts vaultAccounts();

  Instruction fulfill(final int mintId, final PublicKey baseAssetMint, final PublicKey baseAssetTokenProgram);

  default Instruction fulfill(final PublicKey baseAssetMint, final PublicKey baseAssetTokenProgram) {
    return fulfill(0, baseAssetMint, baseAssetTokenProgram);
  }

  Instruction priceVault(final PublicKey solOracleKey, final PriceDenom priceDenom);

  Instruction priceStakes(final PublicKey solOracleKey, final PriceDenom priceDenom);

  Instruction priceDriftUsers(final PublicKey solOracleKey, final PriceDenom priceDenom, final int numUsers);

  Instruction priceDriftVaultDepositors(final PublicKey solOracleKey,
                                        final PriceDenom priceDenom,
                                        final int numVaultDepositors,
                                        final int numSpotMarkets,
                                        final int numPerpMarkets);

  Instruction priceKaminoObligations(final PublicKey kaminoLendingProgramKey,
                                     final PublicKey solOracleKey,
                                     final PublicKey pythOracleKey,
                                     final PublicKey switchboardPriceOracleKey,
                                     final PublicKey switchboardTwapOracleKey,
                                     final PublicKey scopePricesKey,
                                     final PriceDenom priceDenom);

  Instruction priceKaminoVaultShares(final PublicKey solOracleKey,
                                     final PriceDenom priceDenom,
                                     final int numVaults);

  Instruction priceMeteoraPositions(final PublicKey solOracleKey, final PriceDenom priceDenom);

  Instruction updateState(final StateModel state);
}
