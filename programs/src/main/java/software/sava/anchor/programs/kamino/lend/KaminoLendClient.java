package software.sava.anchor.programs.kamino.lend;

import software.sava.anchor.programs.kamino.KaminoAccounts;
import software.sava.anchor.programs.kamino.farms.anchor.types.FarmState;
import software.sava.anchor.programs.kamino.lend.anchor.types.InitObligationArgs;
import software.sava.anchor.programs.kamino.lend.anchor.types.LendingMarket;
import software.sava.anchor.programs.kamino.lend.anchor.types.Reserve;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface KaminoLendClient {

  static KaminoLendClient createClient(final NativeProgramAccountClient nativeProgramAccountClient,
                                       final KaminoAccounts kaminoAccounts) {
    return new KaminoLendClientImpl(nativeProgramAccountClient, kaminoAccounts);
  }

  static KaminoLendClient createClient(final NativeProgramAccountClient nativeProgramAccountClient) {
    return createClient(nativeProgramAccountClient, KaminoAccounts.MAIN_NET);
  }

  static CompletableFuture<List<AccountInfo<FarmState>>> fetchFarmStateAccounts(final SolanaRpcClient rpcClient,
                                                                                final PublicKey kLendProgram) {
    return rpcClient.getProgramAccounts(
        kLendProgram,
        List.of(FarmState.SIZE_FILTER),
        FarmState.FACTORY
    );
  }

  static CompletableFuture<List<AccountInfo<LendingMarket>>> fetchLendingMarkets(final SolanaRpcClient rpcClient,
                                                                                 final PublicKey kLendProgram) {
    return rpcClient.getProgramAccounts(
        kLendProgram,
        List.of(LendingMarket.SIZE_FILTER),
        LendingMarket.FACTORY
    );
  }

  static CompletableFuture<List<AccountInfo<Reserve>>> fetchReserveAccounts(final SolanaRpcClient rpcClient,
                                                                            final PublicKey kLendProgram) {
    return rpcClient.getProgramAccounts(
        kLendProgram,
        List.of(Reserve.SIZE_FILTER),
        Reserve.FACTORY
    );
  }

  static CompletableFuture<List<AccountInfo<Reserve>>> fetchReserveAccounts(final SolanaRpcClient rpcClient,
                                                                            final PublicKey kLendProgram,
                                                                            final PublicKey lendingMarket) {
    return rpcClient.getProgramAccounts(
        kLendProgram,
        List.of(Reserve.SIZE_FILTER, Reserve.createLendingMarketFilter(lendingMarket)),
        Reserve.FACTORY
    );
  }

  SolanaAccounts solanaAccounts();

  KaminoAccounts kaminoAccounts();

  PublicKey user();

  Instruction initReferrerTokenState(final PublicKey lendingMarket,
                                     final PublicKey reserveKey,
                                     final PublicKey referrerKey,
                                     final PublicKey referrerTokenStateKey);

  Instruction withdrawReferrerFees(final PublicKey reserveKey,
                                   final ReservePDAs reservePDAs,
                                   final PublicKey referrerKey,
                                   final PublicKey referrerTokenStateKey,
                                   final PublicKey referrerTokenAccountKey);

  Instruction initReferrerStateAndShortUrl(final PublicKey referrerKey,
                                           final PublicKey referrerStateKey,
                                           final PublicKey referrerShortUrlKey,
                                           final PublicKey referrerUserMetadataKey,
                                           final String shortUrl);

  Instruction deleteReferrerStateAndShortUrl(final PublicKey referrerKey,
                                             final PublicKey referrerStateKey,
                                             final PublicKey shortUrlKey);

  Instruction initUserMetadata(final PublicKey referrerUserMetadataKey, final PublicKey userLookupTable);

  Instruction initObligation(final PublicKey lendingMarket,
                             final PublicKey obligationKey,
                             final InitObligationArgs initObligationArgs);

  Instruction initObligationFarmsForReserve(final Reserve reserve,
                                            final ReservePDAs reservePDAs,
                                            final PublicKey obligationKey,
                                            final PublicKey obligationFarmKey,
                                            final int mode);

  Instruction refreshReserve(final PublicKey lendingMarket, final PublicKey reserveKey);

  Instruction refreshObligation(final PublicKey lendingMarket, final PublicKey obligationKey);

  Instruction depositReserveLiquidityAndObligationCollateral(final PublicKey obligationKey,
                                                             final PublicKey reserveKey,
                                                             final PublicKey reserveDestinationDepositCollateralKey,
                                                             final ReservePDAs reservePDAs,
                                                             final PublicKey sourceTokenAccount,
                                                             final long liquidityAmount);
}
