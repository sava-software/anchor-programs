package software.sava.anchor.programs.glam;


import software.sava.anchor.programs.glam.anchor.types.PriceDenom;
import software.sava.anchor.programs.glam.protocol.anchor.types.StateAccount;
import software.sava.anchor.programs.glam.protocol.anchor.types.StateModel;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.util.List;
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

  NativeProgramAccountClient delegatedNativeProgramAccountClient();

  GlamVaultAccounts vaultAccounts();

  Instruction fulfill(final int mintId, final PublicKey baseAssetMint, final PublicKey baseAssetTokenProgram);

  default Instruction fulfill(final PublicKey baseAssetMint, final PublicKey baseAssetTokenProgram) {
    return fulfill(0, baseAssetMint, baseAssetTokenProgram);
  }

  Instruction priceVaultTokens(final PublicKey solUsdOracleKey,
                               final PublicKey baseAssetUsdOracleKey,
                               final short[][] aggIndexes);

  Instruction priceStakes(final PublicKey solUsdOracleKey, final PublicKey baseAssetUsdOracleKey);

  Instruction priceDriftUsers(final PublicKey solUSDOracleKey, final PublicKey baseAssetUsdOracleKey, final int numUsers);

  Instruction priceDriftVaultDepositors(final PublicKey solOracleKey,
                                        final PublicKey baseAssetUsdOracleKey,
                                        final int numVaultDepositors,
                                        final int numSpotMarkets,
                                        final int numPerpMarkets);

  Instruction priceKaminoObligations(final PublicKey kaminoLendingProgramKey,
                                     final PublicKey solUSDOracleKey,
                                     final PublicKey baseAssetUsdOracleKey,
                                     final PublicKey pythOracleKey,
                                     final PublicKey switchboardPriceOracleKey,
                                     final PublicKey switchboardTwapOracleKey,
                                     final PublicKey scopePricesKey,
                                     final int numObligations,
                                     final int numMarkets,
                                     final int numReserves);

  Instruction priceKaminoVaultShares(final PublicKey solUSDOracleKey,
                                     final PublicKey baseAssetUsdOracleKey,
                                     final int numVaults);

  Instruction priceMeteoraPositions(final PublicKey solOracleKey, final PriceDenom priceDenom);

  Instruction updateState(final StateModel state);
}
