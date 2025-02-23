package software.sava.anchor.programs.kamino.lend;

import software.sava.anchor.programs.kamino.KaminoAccounts;
import software.sava.anchor.programs.kamino.lend.anchor.KaminoLendingProgram;
import software.sava.anchor.programs.kamino.lend.anchor.types.InitObligationArgs;
import software.sava.anchor.programs.kamino.lend.anchor.types.Obligation;
import software.sava.anchor.programs.kamino.lend.anchor.types.Reserve;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.PrivateKeyEncoding;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.solana.programs.clients.NativeProgramAccountClient;
import software.sava.solana.programs.clients.NativeProgramClient;
import software.sava.solana.programs.system.SystemProgram;
import systems.comodal.jsoniter.JsonIterator;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static java.time.ZoneOffset.UTC;

final class KaminoLendClientImpl implements KaminoLendClient {

  private final NativeProgramAccountClient nativeProgramAccountClient;
  private final SolanaAccounts solanaAccounts;
  private final KaminoAccounts kaminoAccounts;
  private final PublicKey owner;
  private final PublicKey ownerMetadata;
  private final PublicKey feePayer;
  private final PublicKey referrer;

  KaminoLendClientImpl(final NativeProgramAccountClient nativeProgramAccountClient,
                       final KaminoAccounts kaminoAccounts,
                       final PublicKey referrer) {
    this.nativeProgramAccountClient = nativeProgramAccountClient;
    this.solanaAccounts = nativeProgramAccountClient.solanaAccounts();
    this.kaminoAccounts = kaminoAccounts;
    this.owner = nativeProgramAccountClient.ownerPublicKey();
    this.ownerMetadata = KaminoAccounts.userMetadataPda(owner, kaminoAccounts.kLendProgram()).publicKey();
    this.feePayer = nativeProgramAccountClient.feePayer().publicKey();
    this.referrer = referrer;
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


  static void main(final String[] args) throws IOException {
    var jsonConfig = Files.readAllBytes(Path.of(""));
    var ji = JsonIterator.parse(jsonConfig).skipUntil("privateKey");
    var signer = PrivateKeyEncoding.fromJsonPrivateKey(ji);
    var feePayer = signer.publicKey();
    final var endpoint = "";

    final var solanaAccounts = SolanaAccounts.MAIN_NET;
    final var nativeClient = NativeProgramClient.createClient(solanaAccounts);
    final var nativeAccountClient = nativeClient.createAccountClient(AccountMeta.createFeePayer(feePayer));
    final var kaminoClient = KaminoLendClient.createClient(nativeAccountClient, null);

    try (final var httpClient = HttpClient.newHttpClient()) {
      final var rpcClient = SolanaRpcClient.createClient(URI.create(endpoint), httpClient);

      final var minRentFuture = rpcClient.getMinimumBalanceForRentExemption(Obligation.BYTES);

      final var programId = kaminoClient.kaminoAccounts().kLendProgram();
      final var reserves = KaminoLendClient.fetchReserveAccounts(rpcClient, programId).join();
      final var reserveMap = HashMap.<PublicKey, Map<PublicKey, Reserve>>newHashMap(reserves.size());
      for (final var accountInfo : reserves) {
        final var reserve = accountInfo.data();
        reserveMap.put(reserve.lendingMarket(), Map.of(reserve.liquidity().mintPubkey(), reserve));
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

      final var seed = ZonedDateTime.now(UTC).toString();
      final var accountWithSeed = PublicKey.createOffCurveAccountWithAsciiSeed(
          feePayer,
          seed,
          kaminoClient.kaminoAccounts().kLendProgram()
      );
      final long minRent = minRentFuture.join();

      final var createObligationIx = SystemProgram.createAccountWithSeed(
          solanaAccounts.invokedSystemProgram(),
          feePayer,
          accountWithSeed,
          minRent,
          Obligation.BYTES,
          kaminoClient.kaminoAccounts().kLendProgram()
      );
    }
  }
}
