package software.sava.anchor.programs.kamino.lend;

import software.sava.anchor.programs.kamino.KaminoAccounts;
import software.sava.anchor.programs.kamino.lend.anchor.KaminoLendingProgram;
import software.sava.anchor.programs.kamino.lend.anchor.types.InitObligationArgs;
import software.sava.anchor.programs.kamino.lend.anchor.types.LendingMarket;
import software.sava.anchor.programs.kamino.lend.anchor.types.Obligation;
import software.sava.anchor.programs.kamino.lend.anchor.types.Reserve;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.Signer;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.PrivateKeyEncoding;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.solana.programs.clients.NativeProgramAccountClient;
import software.sava.solana.programs.clients.NativeProgramClient;
import systems.comodal.jsoniter.JsonIterator;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;

import static software.sava.rpc.json.http.request.Commitment.FINALIZED;

final class KaminoLendClientImpl implements KaminoLendClient {

  private final NativeProgramAccountClient nativeProgramAccountClient;
  private final SolanaAccounts solanaAccounts;
  private final KaminoAccounts kaminoAccounts;
  private final PublicKey owner;
  private final PublicKey ownerMetadata;
  private final PublicKey feePayer;

  KaminoLendClientImpl(final NativeProgramAccountClient nativeProgramAccountClient,
                       final KaminoAccounts kaminoAccounts) {
    this.nativeProgramAccountClient = nativeProgramAccountClient;
    this.solanaAccounts = nativeProgramAccountClient.solanaAccounts();
    this.kaminoAccounts = kaminoAccounts;
    this.owner = nativeProgramAccountClient.ownerPublicKey();
    this.ownerMetadata = KaminoAccounts.userMetadataPda(owner, kaminoAccounts.kLendProgram()).publicKey();
    this.feePayer = nativeProgramAccountClient.feePayer().publicKey();
  }

  @Override
  public SolanaAccounts solanaAccounts() {
    return solanaAccounts;
  }

  @Override
  public KaminoAccounts kaminoAccounts() {
    return kaminoAccounts;
  }

  @Override
  public PublicKey user() {
    return owner;
  }

  @Override
  public Instruction initReferrerTokenState(final PublicKey lendingMarket,
                                            final PublicKey reserveKey,
                                            final PublicKey referrerKey,
                                            final PublicKey referrerTokenStateKey) {
    return KaminoLendingProgram.initReferrerTokenState(
        kaminoAccounts.invokedKLendProgram(),
        feePayer,
        lendingMarket,
        reserveKey,
        referrerKey,
        referrerTokenStateKey,
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram()
    );
  }

  @Override
  public Instruction withdrawReferrerFees(final PublicKey reserveKey,
                                          final ReservePDAs reservePDAs,
                                          final PublicKey referrerKey,
                                          final PublicKey referrerTokenStateKey,
                                          final PublicKey referrerTokenAccountKey) {
    return KaminoLendingProgram.withdrawReferrerFees(
        kaminoAccounts.invokedKLendProgram(),
        referrerKey,
        referrerTokenStateKey,
        reserveKey,
        reservePDAs.collateralMint(),
        reservePDAs.liquiditySupplyVault(),
        referrerTokenAccountKey,
        reservePDAs.market(),
        reservePDAs.marketAuthority(),
        solanaAccounts.tokenProgram()
    );
  }

  @Override
  public Instruction initReferrerStateAndShortUrl(final PublicKey referrerKey,
                                                  final PublicKey referrerStateKey,
                                                  final PublicKey referrerShortUrlKey,
                                                  final PublicKey referrerUserMetadataKey,
                                                  final String shortUrl) {
    return KaminoLendingProgram.initReferrerStateAndShortUrl(
        kaminoAccounts.invokedKLendProgram(),
        referrerKey,
        referrerStateKey,
        referrerShortUrlKey,
        referrerUserMetadataKey,
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram(),
        shortUrl
    );
  }

  @Override
  public Instruction deleteReferrerStateAndShortUrl(final PublicKey referrerKey,
                                                    final PublicKey referrerStateKey,
                                                    final PublicKey shortUrlKey) {
    return KaminoLendingProgram.deleteReferrerStateAndShortUrl(
        kaminoAccounts.invokedKLendProgram(),
        referrerKey,
        referrerStateKey,
        shortUrlKey,
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram()
    );
  }

  @Override
  public Instruction initUserMetadata(final PublicKey referrerUserMetadataKey, final PublicKey userLookupTable) {
    return KaminoLendingProgram.initUserMetadata(
        kaminoAccounts.invokedKLendProgram(),
        owner,
        feePayer,
        ownerMetadata,
        referrerUserMetadataKey,
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram(),
        userLookupTable
    );
  }

  @Override
  public Instruction initObligation(final PublicKey lendingMarket,
                                    final PublicKey obligationKey,
                                    final InitObligationArgs initObligationArgs) {
    return KaminoLendingProgram.initObligation(
        kaminoAccounts.invokedKLendProgram(),
        owner,
        feePayer,
        obligationKey,
        lendingMarket,
        solanaAccounts.systemProgram(),
        solanaAccounts.systemProgram(),
        ownerMetadata,
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram(),
        initObligationArgs
    );
  }

  @Override
  public Instruction initObligationFarmsForReserve(final Reserve reserve,
                                                   final ReservePDAs reservePDAs,
                                                   final PublicKey obligationKey,
                                                   final PublicKey obligationFarmKey,
                                                   final int mode) {
    return initObligationFarmsForReserve(
        reserve._address(),
        reserve.farmCollateral(),
        reservePDAs,
        obligationKey,
        obligationFarmKey,
        mode
    );
  }

  public Instruction initObligationFarmsForReserve(final PublicKey reserveKey,
                                                   final PublicKey reserveFarmStateKey,
                                                   final ReservePDAs reservePDAs,
                                                   final PublicKey obligationKey,
                                                   final PublicKey obligationFarmKey,
                                                   final int mode) {
    return KaminoLendingProgram.initObligationFarmsForReserve(
        kaminoAccounts.invokedKLendProgram(),
        feePayer,
        owner,
        obligationKey,
        reservePDAs.marketAuthority(),
        reserveKey,
        reserveFarmStateKey,
        obligationFarmKey,
        reservePDAs.market(),
        kaminoAccounts.farmProgram(),
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram(),
        mode
    );
  }

  @Override
  public Instruction refreshReserve(final PublicKey lendingMarket, final PublicKey reserveKey) {
    return KaminoLendingProgram.refreshReserve(
        kaminoAccounts.invokedKLendProgram(),
        reserveKey,
        lendingMarket,
        kaminoAccounts.kLendProgram(),
        kaminoAccounts.kLendProgram(),
        kaminoAccounts.kLendProgram(),
        kaminoAccounts.scopePrices()
    );
  }

  @Override
  public Instruction refreshObligation(final PublicKey lendingMarket,
                                       final PublicKey obligationKey) {
    return KaminoLendingProgram.refreshObligation(
        kaminoAccounts.invokedKLendProgram(),
        lendingMarket,
        obligationKey
    );
  }

  @Override
  public Instruction refreshObligationFarmsForReserve(final PublicKey baseAccountsKey, final int mode) {
    return KaminoLendingProgram.refreshObligationFarmsForReserve(
        kaminoAccounts.invokedKLendProgram(),
        feePayer,
        baseAccountsKey,
        kaminoAccounts.farmProgram(),
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram(),
        mode
    );
  }

  @Override
  public Instruction depositReserveLiquidityAndObligationCollateral(final PublicKey obligationKey,
                                                                    final PublicKey reserveKey,
                                                                    final PublicKey reserveDestinationDepositCollateralKey,
                                                                    final ReservePDAs reservePDAs,
                                                                    final PublicKey sourceTokenAccount,
                                                                    final long liquidityAmount) {
    return KaminoLendingProgram.depositReserveLiquidityAndObligationCollateral(
        kaminoAccounts.invokedKLendProgram(),
        owner,
        obligationKey,
        reservePDAs.market(),
        reservePDAs.marketAuthority(),
        reserveKey,
        reservePDAs.mint(),
        reservePDAs.liquiditySupplyVault(),
        reservePDAs.collateralMint(),
        reservePDAs.collateralSupplyVault(),
        sourceTokenAccount,
        kaminoAccounts.kLendProgram(),
        reservePDAs.tokenProgram(),
        reservePDAs.tokenProgram(),
        solanaAccounts.instructionsSysVar(),
        liquidityAmount
    );
  }


  public static void main(final String[] args) throws IOException {
    var jsonConfig = Files.readAllBytes(Path.of(""));
    var ji = JsonIterator.parse(jsonConfig).skipUntil("privateKey");
    var signer = PrivateKeyEncoding.fromJsonPrivateKey(ji);
    var feePayer = signer.publicKey();
    final var endpoint = "";

    final var solanaAccounts = SolanaAccounts.MAIN_NET;
    final var nativeClient = NativeProgramClient.createClient(solanaAccounts);
    final var nativeAccountClient = nativeClient.createAccountClient(AccountMeta.createFeePayer(feePayer));
    final var kaminoClient = KaminoLendClient.createClient(nativeAccountClient);

    final var referrer = feePayer; // PublicKey.fromBase58Encoded("");
    final var referrerUserMetadataKey = KaminoAccounts
        .userMetadataPda(referrer, kaminoClient.kaminoAccounts().kLendProgram())
        .publicKey();

    final var solMint = SolanaAccounts.MAIN_NET.wrappedSolTokenMint();
    try (final var httpClient = HttpClient.newHttpClient()) {
      final var rpcClient = SolanaRpcClient.createClient(URI.create(endpoint), httpClient);

      final var kaminoAccounts = kaminoClient.kaminoAccounts();
      final var kLendProgramId = kaminoAccounts.kLendProgram();


      final var lendingMarketsFuture = KaminoLendClient.fetchLendingMarkets(rpcClient, kLendProgramId);
      final var lendingMarkets = lendingMarketsFuture.join();
      LendingMarket mainLendingMarket = null;
      for (final var lendingMarketAccountInfo : lendingMarkets) {
        final var lendingMarket = lendingMarketAccountInfo.data();
        System.out.format("""
                address: %s,
                name: %s,
                quote: %s,
                refFeeBps: %d,
                minDeposit: %d
                
                """,
            lendingMarket._address(),
            new String(lendingMarket.name()),
            new String(lendingMarket.quoteCurrency()),
            lendingMarket.referralFeeBps(),
            lendingMarket.minInitialDepositAmount()
        );
      }


      final var reserveAccountsFuture = KaminoLendClient.fetchReserveAccounts(rpcClient, kLendProgramId);
      final var reserveAccounts = reserveAccountsFuture.join();
      for (final var accountInfo : reserveAccounts) {
        final var reserve = accountInfo.data();
        final var lendingMarket = reserve.lendingMarket();
        if (mainLendingMarket.equals(lendingMarket)) {
          final var tokenMint = reserve.liquidity().mintPubkey();
          if (tokenMint.equals(solMint)) {
            System.out.println(reserve);
            final var marketPDAs = MarketPDAs.createPDAs(kLendProgramId, reserve.lendingMarket());
            final var liquidity = reserve.liquidity();
            final var reservePDAs = kaminoAccounts.createReservePDAs(
                marketPDAs,
                liquidity.mintPubkey(),
                liquidity.tokenProgram()
            );
            System.out.println(marketPDAs);
            System.out.println(reservePDAs);
          }
        }
      }

      final var recentSlotFuture = rpcClient.getSlot(FINALIZED);
      final var obligationMinRentFuture = rpcClient.getMinimumBalanceForRentExemption(Obligation.BYTES);

      final var recentSlot = recentSlotFuture.join();
      final var lookupTablePDA = nativeAccountClient.findLookupTableAddress(recentSlot);
      final var createTableIx = nativeAccountClient.createLookupTable(lookupTablePDA, recentSlot);
      final var initUserMetadataIx = kaminoClient.initUserMetadata(referrerUserMetadataKey, lookupTablePDA.publicKey());

      final var obligationKey = PublicKey.readPubKey(Signer.generatePrivateKeyPairBytes());
      final long obligationMinRent = obligationMinRentFuture.join();
      final var initObligationIx = kaminoClient.initObligation(
          null,
          obligationKey,
          new InitObligationArgs(0, 0)
      );
    }
  }
}
