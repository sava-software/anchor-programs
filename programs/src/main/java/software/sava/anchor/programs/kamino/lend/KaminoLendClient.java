package software.sava.anchor.programs.kamino.lend;

import software.sava.anchor.programs.kamino.KaminoAccounts;
import software.sava.anchor.programs.kamino.lend.anchor.types.InitObligationArgs;
import software.sava.anchor.programs.kamino.lend.anchor.types.Reserve;
import software.sava.core.accounts.PublicKey;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface KaminoLendClient {

  static KaminoLendClient createClient(final NativeProgramAccountClient nativeProgramAccountClient,
                                       final KaminoAccounts kaminoAccounts,
                                       final PublicKey referrer) {
    return new KaminoLendClientImpl(nativeProgramAccountClient, kaminoAccounts, referrer);
  }

  static KaminoLendClient createClient(final NativeProgramAccountClient nativeProgramAccountClient,
                                       final PublicKey referrer) {
    return createClient(nativeProgramAccountClient, KaminoAccounts.MAIN_NET, referrer);
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

  static void main(final String[] args) {
    final var endpoint = URI.create("https://.solana-mainnet.quiknode.pro/");
    try (final var httpClient = HttpClient.newHttpClient()) {
      final var rpcClient = SolanaRpcClient.createClient(endpoint, httpClient);

      final var programId = KaminoAccounts.MAIN_NET.kLendProgram();
      final var reserves = fetchReserveAccounts(rpcClient, programId).join();
      for (final var accountInfo : reserves) {
        final var reserve = accountInfo.data();
        System.out.println(reserve);
        final var marketPDAs = MarketPDAs.createPDAs(programId, reserve.lendingMarket());
        final var liquidity = reserve.liquidity();
        final var reservePDAs = ReservePDAs.createPDAs(
            programId,
            marketPDAs,
            liquidity.mintPubkey(),
            liquidity.tokenProgram()
        );
        System.out.println(marketPDAs);
        System.out.println(reservePDAs);
      }
    }
  }

  Instruction initObligation(final PublicKey lendingMarket,
                             final PublicKey obligationKey,
                             final InitObligationArgs initObligationArgs);

  Instruction refreshReserve(final PublicKey lendingMarket, final PublicKey reserveKey);

  Instruction refreshObligation(final PublicKey lendingMarket,
                                final PublicKey obligationKey);

  Instruction refreshObligationFarmsForReserve(final PublicKey baseAccountsKey, final int mode);

  Instruction depositReserveLiquidityAndObligationCollateral(final PublicKey obligationKey,
                                                             final PublicKey reserveKey,
                                                             final PublicKey reserveDestinationDepositCollateralKey,
                                                             final ReservePDAs reservePDAs,
                                                             final PublicKey sourceTokenAccount,
                                                             final long liquidityAmount);
}
