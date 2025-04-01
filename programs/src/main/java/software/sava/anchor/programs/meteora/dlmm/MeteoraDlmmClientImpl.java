package software.sava.anchor.programs.meteora.dlmm;

import software.sava.anchor.programs.meteora.MeteoraAccounts;
import software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmConstants;
import software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmProgram;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.*;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.Signer;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.accounts.token.TokenAccount;
import software.sava.core.rpc.Filter;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.PrivateKeyEncoding;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.rpc.json.http.response.InnerInstructions;
import software.sava.rpc.json.http.response.TxSimulation;
import software.sava.solana.programs.clients.NativeProgramAccountClient;
import software.sava.solana.programs.compute_budget.ComputeBudgetProgram;
import software.sava.solana.web2.helius.client.http.HeliusClient;
import systems.comodal.jsoniter.JsonIterator;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URI;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNullElse;
import static software.sava.anchor.programs.meteora.dlmm.DlmmUtils.NO_REMAINING_ACCOUNTS;

record MeteoraDlmmClientImpl(SolanaAccounts solanaAccounts,
                             MeteoraAccounts meteoraAccounts,
                             PublicKey owner,
                             AccountMeta feePayer,
                             PublicKey memoProgram,
                             PublicKey eventAuthority) implements MeteoraDlmmClient {

  @Override
  public CompletableFuture<List<AccountInfo<PositionV2>>> fetchPositions(final SolanaRpcClient rpcClient) {
    return rpcClient.getProgramAccounts(
        meteoraAccounts.dlmmProgram(),
        List.of(
            PositionV2.SIZE_FILTER,
            Filter.createMemCompFilter(0, meteoraAccounts.positionV2Discriminator().data()),
            PositionV2.createOwnerFilter(owner)
        ),
        PositionV2.FACTORY
    );
  }

  @Override
  public Instruction initializePosition(final PublicKey positionKey,
                                        final PublicKey lbPairKey,
                                        final int lowerBinId,
                                        final int width) {
    return LbClmmProgram.initializePosition(
        meteoraAccounts.invokedDlmmProgram(),
        feePayer.publicKey(),
        positionKey,
        lbPairKey,
        owner,
        solanaAccounts.systemProgram(),
        solanaAccounts.rentSysVar(),
        eventAuthority,
        meteoraAccounts.dlmmProgram(),
        lowerBinId,
        width
    );
  }

  @Override
  public Instruction initializePositionWithSeeds(final PublicKey positionKey,
                                                 final PublicKey baseKey,
                                                 final PublicKey lbPairKey,
                                                 final int lowerBinId,
                                                 final int width) {
    return LbClmmProgram.initializePositionPda(
        meteoraAccounts.invokedDlmmProgram(),
        feePayer.publicKey(),
        baseKey,
        positionKey,
        lbPairKey,
        owner,
        solanaAccounts.systemProgram(),
        solanaAccounts.rentSysVar(),
        eventAuthority,
        meteoraAccounts.dlmmProgram(),
        lowerBinId,
        width
    );
  }

  @Override
  public Instruction addLiquidityByStrategy(final PublicKey positionKey,
                                            final PublicKey lbPairKey,
                                            final PublicKey binArrayBitmapExtensionKey,
                                            final PublicKey userTokenXKey,
                                            final PublicKey userTokenYKey,
                                            final PublicKey reserveXKey,
                                            final PublicKey reserveYKey,
                                            final PublicKey tokenXMintKey,
                                            final PublicKey tokenYMintKey,
                                            final PublicKey tokenXProgramKey,
                                            final PublicKey tokenYProgramKey,
                                            final LiquidityParameterByStrategy liquidityParameter,
                                            final RemainingAccountsInfo remainingAccountsInfo) {
    return LbClmmProgram.addLiquidityByStrategy2(
        meteoraAccounts.invokedDlmmProgram(),
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey, reserveYKey,
        tokenXMintKey, tokenYMintKey,
        owner,
        tokenXProgramKey, tokenYProgramKey,
        eventAuthority,
        meteoraAccounts.dlmmProgram(),
        liquidityParameter,
        requireNonNullElse(remainingAccountsInfo, NO_REMAINING_ACCOUNTS)
    );
  }

  @Override
  public Instruction addLiquidity(final PublicKey positionKey,
                                  final PublicKey lbPairKey,
                                  final PublicKey binArrayBitmapExtensionKey,
                                  final PublicKey userTokenXKey,
                                  final PublicKey userTokenYKey,
                                  final PublicKey reserveXKey,
                                  final PublicKey reserveYKey,
                                  final PublicKey tokenXMintKey,
                                  final PublicKey tokenYMintKey,
                                  final PublicKey tokenXProgramKey,
                                  final PublicKey tokenYProgramKey,
                                  final LiquidityParameter liquidityParameter,
                                  final RemainingAccountsInfo remainingAccountsInfo) {
    return LbClmmProgram.addLiquidity2(
        meteoraAccounts.invokedDlmmProgram(),
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey, reserveYKey,
        tokenXMintKey, tokenYMintKey,
        owner,
        tokenXProgramKey, tokenYProgramKey,
        eventAuthority,
        meteoraAccounts.dlmmProgram(),
        liquidityParameter,
        requireNonNullElse(remainingAccountsInfo, NO_REMAINING_ACCOUNTS)
    );
  }

  @Override
  public Instruction addLiquidityOneSidePrecise(final PublicKey positionKey,
                                                final PublicKey lbPairKey,
                                                final PublicKey binArrayBitmapExtensionKey,
                                                final PublicKey userTokenKey,
                                                final PublicKey reserveKey,
                                                final PublicKey tokenMintKey,
                                                final PublicKey tokenProgramKey,
                                                final AddLiquiditySingleSidePreciseParameter2 liquidityParameter,
                                                final RemainingAccountsInfo remainingAccountsInfo) {
    return LbClmmProgram.addLiquidityOneSidePrecise2(
        meteoraAccounts.invokedDlmmProgram(),
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenKey,
        reserveKey,
        tokenMintKey,
        owner,
        tokenProgramKey,
        eventAuthority,
        meteoraAccounts.dlmmProgram(),
        liquidityParameter,
        requireNonNullElse(remainingAccountsInfo, NO_REMAINING_ACCOUNTS)
    );
  }

  @Override
  public Instruction removeLiquidityByRange(final PublicKey positionKey,
                                            final PublicKey lbPairKey,
                                            final PublicKey binArrayBitmapExtensionKey,
                                            final PublicKey userTokenXKey,
                                            final PublicKey userTokenYKey,
                                            final PublicKey reserveXKey,
                                            final PublicKey reserveYKey,
                                            final PublicKey tokenXMintKey,
                                            final PublicKey tokenYMintKey,
                                            final PublicKey tokenXProgramKey,
                                            final PublicKey tokenYProgramKey,
                                            final int fromBinId,
                                            final int toBinId,
                                            final int bpsToRemove,
                                            final RemainingAccountsInfo remainingAccountsInfo) {
    return LbClmmProgram.removeLiquidityByRange2(
        meteoraAccounts.invokedDlmmProgram(),
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey, reserveYKey,
        tokenXMintKey, tokenYMintKey,
        owner,
        tokenXProgramKey, tokenYProgramKey,
        memoProgram,
        eventAuthority,
        meteoraAccounts.dlmmProgram(),
        fromBinId, toBinId, bpsToRemove,
        requireNonNullElse(remainingAccountsInfo, NO_REMAINING_ACCOUNTS)
    );
  }

  @Override
  public Instruction removeLiquidity(final PublicKey positionKey,
                                     final PublicKey lbPairKey,
                                     final PublicKey binArrayBitmapExtensionKey,
                                     final PublicKey userTokenXKey,
                                     final PublicKey userTokenYKey,
                                     final PublicKey reserveXKey,
                                     final PublicKey reserveYKey,
                                     final PublicKey tokenXMintKey,
                                     final PublicKey tokenYMintKey,
                                     final PublicKey tokenXProgramKey,
                                     final PublicKey tokenYProgramKey,
                                     final BinLiquidityReduction[] binLiquidityRemoval,
                                     final RemainingAccountsInfo remainingAccountsInfo) {
    return LbClmmProgram.removeLiquidity2(
        meteoraAccounts.invokedDlmmProgram(),
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey, reserveYKey,
        tokenXMintKey, tokenYMintKey,
        owner,
        tokenXProgramKey, tokenYProgramKey,
        memoProgram,
        eventAuthority,
        meteoraAccounts.dlmmProgram(),
        binLiquidityRemoval,
        requireNonNullElse(remainingAccountsInfo, NO_REMAINING_ACCOUNTS)
    );
  }

  @Override
  public Instruction claimFee(final PublicKey lbPairKey,
                              final PublicKey positionKey,
                              final PublicKey reserveXKey,
                              final PublicKey reserveYKey,
                              final PublicKey userTokenXKey,
                              final PublicKey userTokenYKey,
                              final PublicKey tokenXMintKey,
                              final PublicKey tokenYMintKey,
                              final PublicKey tokenXProgramKey,
                              final PublicKey tokenYProgramKey,
                              final int minBinId,
                              final int maxBinId,
                              final RemainingAccountsInfo remainingAccountsInfo) {
    return LbClmmProgram.claimFee2(
        meteoraAccounts.invokedDlmmProgram(),
        lbPairKey,
        positionKey,
        owner,
        reserveXKey, reserveYKey,
        userTokenXKey, userTokenYKey,
        tokenXMintKey, tokenYMintKey,
        tokenXProgramKey, tokenYProgramKey,
        memoProgram,
        eventAuthority,
        meteoraAccounts.dlmmProgram(),
        minBinId, maxBinId,
        requireNonNullElse(remainingAccountsInfo, NO_REMAINING_ACCOUNTS)
    );
  }

  @Override
  public Instruction claimReward(final PublicKey lbPairKey,
                                 final PublicKey positionKey,
                                 final PublicKey rewardVaultKey,
                                 final PublicKey rewardMintKey,
                                 final PublicKey userTokenAccountKey,
                                 final PublicKey tokenProgramKey,
                                 final int rewardIndex,
                                 final int minBidId,
                                 final int maxBidId,
                                 final RemainingAccountsInfo remainingAccountsInfo) {
    return LbClmmProgram.claimReward2(
        meteoraAccounts.invokedDlmmProgram(),
        lbPairKey,
        positionKey,
        owner,
        rewardVaultKey,
        rewardMintKey,
        userTokenAccountKey,
        tokenProgramKey,
        memoProgram,
        eventAuthority,
        meteoraAccounts.dlmmProgram(),
        rewardIndex,
        minBidId, maxBidId,
        requireNonNullElse(remainingAccountsInfo, NO_REMAINING_ACCOUNTS)
    );
  }

  @Override
  public Instruction closePosition(final PublicKey positionKey, final PublicKey rentReceiverKey) {
    return LbClmmProgram.closePosition2(
        meteoraAccounts.invokedDlmmProgram(),
        positionKey,
        owner,
        rentReceiverKey,
        eventAuthority,
        meteoraAccounts.dlmmProgram()
    );
  }

  public static void main(String[] args) throws IOException {
    var accounts = MeteoraAccounts.MAIN_NET;
    var solAccounts = SolanaAccounts.MAIN_NET;

    final var secretFilePath = "";
    final var apiKey = "";

    final var signer = PrivateKeyEncoding.fromJsonPrivateKey(JsonIterator.parse(Files.readAllBytes(Path.of(secretFilePath))).skipUntil("privateKey"));
    final var nativeAccountClient = NativeProgramAccountClient.createClient(signer);
    final var dlmmClient = MeteoraDlmmClient.createClient(nativeAccountClient, accounts);

    final var rpcEndpoint = URI.create("https://mainnet.helius-rpc.com/?api-key=" + apiKey);
    final var stakedEndpoint = URI.create("https://staked.helius-rpc.com/?api-key=" + apiKey);

    try (final var httpClient = HttpClient.newHttpClient()) {
      final var heliusClient = HeliusClient.createHttpClient(
          URI.create("https://mainnet.helius-rpc.com/?api-key=" + apiKey),
          httpClient
      );

      final var rpcClient = SolanaRpcClient.createClient(
          rpcEndpoint,
          httpClient,
          response -> {
            System.out.println(new String(response.body()));
            return true;
          }
      );

      final var lbPairKey = PublicKey.fromBase58Encoded("2dBPJGLgNDZnzA32452zV2u6vensbo28dveBvecDg6X1");

      final var lbPairAccountInfo = rpcClient.getAccountInfo(lbPairKey, LbPair.FACTORY).join();
      final var lbPair = lbPairAccountInfo.data();

      final var binStepBase = DlmmUtils.binStepBase(lbPair.binStep());
      final var activePrice = DlmmUtils.binPrice(binStepBase, lbPair.activeId(), 0, MathContext.DECIMAL64);
      System.out.println(activePrice.toPlainString());

      final var tokenXAccount = nativeAccountClient.findATA(lbPair.tokenXMint()).publicKey();
      System.out.println("User X Account: " + tokenXAccount);
      final var tokenYAccount = nativeAccountClient.findATA(lbPair.tokenYMint()).publicKey();
      System.out.println("User Y Account: " + tokenYAccount);

      final var tokenXAccountInfo = rpcClient.getAccountInfo(tokenXAccount, TokenAccount.FACTORY).join();
      System.out.println("User X Account Info: " + tokenXAccountInfo.data());

//      final var tokenYAccountInfo = rpcClient.getAccountInfo(tokenYAccount, TokenAccount.FACTORY).join();
//      System.out.println("User Y Account Info: " + tokenYAccountInfo.data());

      final int scaleDifference = 0;

//      final var positionKeyPair = Signer.generatePrivateKeyPairBytes();
//      final var positionSigner = Signer.createFromKeyPair(positionKeyPair);

//      final var instructions = addLiquidityByStrategy(
//          nativeAccountClient,
//          dlmmClient,
//          lbPair,
////          positionSigner.publicKey(),
//          0,
//          tokenXAccount,
//          420_000_000,
//          tokenYAccount,
//          activePrice.multiply(new BigDecimal("0.998")).doubleValue(),
//          scaleDifference,
//          (int) LbClmmConstants.MAX_BIN_PER_POSITION >> 1
//      );

//      final var instructions = bidLiquidityOneSidePrecise(
//          nativeAccountClient,
//          dlmmClient,
//          lbPair,
//          tokenYAccount,
//          420_000_000,
//          activePrice.multiply(new BigDecimal("0.996")).doubleValue(),
//          scaleDifference,
//          (int) LbClmmConstants.MAX_BIN_PER_POSITION >> 2
//      );

      final var instructions = askLiquidityOneSidePrecise(
          nativeAccountClient,
          dlmmClient,
          lbPair,
          tokenYAccount,
          tokenXAccountInfo.data().amount(),
          activePrice.multiply(new BigDecimal("0.996")).doubleValue(),
          scaleDifference,
          (int) LbClmmConstants.MAX_BIN_PER_POSITION >> 2
      );

//      final var instructions = removeLiquidityByRange(
//          rpcClient,
//          nativeAccountClient,
//          dlmmClient,
//          lbPair,
//          tokenXAccount, tokenYAccount
//      );

      final var sendClient = SolanaRpcClient.createClient(
          stakedEndpoint,
          httpClient,
          response -> {
            System.out.println(new String(response.body()));
            return true;
          }
      );

      simulateAndSend(true, List.of(signer), nativeAccountClient, rpcClient, sendClient, heliusClient, solAccounts, instructions);
    }
  }

  private static void wrapSol(final List<Instruction> instructions,
                              final NativeProgramAccountClient nativeClient,
                              final LbPair lbPair,
                              final long xAmount,
                              final long yAmount) {
    final var solAccounts = nativeClient.solanaAccounts();
    final var wSolMint = solAccounts.wrappedSolTokenMint();
    if (xAmount > 0 && lbPair.tokenXMint().equals(wSolMint)) {
      instructions.add(nativeClient.createATAForOwnerFundedByFeePayer(
          true, nativeClient.wrappedSolPDA().publicKey(), wSolMint
      ));
      instructions.addAll(nativeClient.wrapSOL(xAmount));
    } else if (yAmount > 0 && lbPair.tokenYMint().equals(wSolMint)) {
      instructions.add(nativeClient.createATAForOwnerFundedByFeePayer(
          true, nativeClient.wrappedSolPDA().publicKey(), wSolMint
      ));
      instructions.addAll(nativeClient.wrapSOL(yAmount));
    }
  }

  private static List<Instruction> addLiquidityByStrategy(final NativeProgramAccountClient nativeClient,
                                                          final MeteoraDlmmClient dlmmClient,
                                                          final LbPair lbPair,
                                                          final long xAmount,
                                                          final PublicKey tokenXAccount,
                                                          final long yAmount,
                                                          final PublicKey tokenYAccount,
                                                          final double targetPrice,
                                                          final int scaleDifference,
                                                          final int width) {
    final var solAccounts = dlmmClient.solanaAccounts();
    final var instructions = new ArrayList<Instruction>();
    wrapSol(instructions, nativeClient, lbPair, xAmount, yAmount);

    final double inverseLogBinStepBase = DlmmUtils.inverseLogBinStepBase(lbPair.binStep());
    final double priceScaleFactor = DlmmUtils.priceScaleFactor(scaleDifference);
    final double binId = DlmmUtils.binIdFromInverseLogBinStepBase(targetPrice, priceScaleFactor, inverseLogBinStepBase);
    final int upperBinId = (int) binId;
    final int lowerBinId = upperBinId - (width - 1);

    System.out.println(DlmmUtils.binPrice(lbPair.binStep(), lowerBinId, scaleDifference, MathContext.DECIMAL64).toPlainString());
    System.out.println(DlmmUtils.binPrice(lbPair.binStep(), upperBinId, scaleDifference, MathContext.DECIMAL64).toPlainString());

    final var lbPairKey = lbPair._address();
    final var positionKey = dlmmClient.derivePositionAccount(lbPairKey, lowerBinId, width).publicKey();
    final var initializePositionIx = dlmmClient.initializePositionWithSeeds(positionKey, lbPairKey, lowerBinId, width);
    instructions.add(initializePositionIx);

    final var binAccountMetas = dlmmClient.deriveBinAccounts(lbPairKey, lowerBinId, upperBinId);

    final byte[] parameters = new byte[64];
//    parameters[0] = 1;
    final var params = new LiquidityParameterByStrategy(
        xAmount, yAmount,
        lbPair.activeId(),
        10,
        new StrategyParameters(lowerBinId, upperBinId, StrategyType.BidAskImBalanced, parameters)
    );

    final var tokenProgram = solAccounts.tokenProgram();
    final var addLiquidityIx = dlmmClient.addLiquidityByStrategy(
        positionKey,
        lbPair,
        null,
        tokenXAccount, tokenYAccount,
        tokenProgram, tokenProgram,
        params,
        null
    ).extraAccounts(binAccountMetas);
    instructions.add(addLiquidityIx);

    return instructions;
  }

  private static List<Instruction> askLiquidityOneSidePrecise(final NativeProgramAccountClient nativeClient,
                                                              final MeteoraDlmmClient dlmmClient,
                                                              final LbPair lbPair,
                                                              final PublicKey tokenAccount,
                                                              final long scaledAmount,
                                                              final double targetPrice,
                                                              final int scaleDifference,
                                                              final int width) {
    final var instructions = new ArrayList<Instruction>();
    wrapSol(instructions, nativeClient, lbPair, scaledAmount, 0);

    final double inverseLogBinStepBase = DlmmUtils.inverseLogBinStepBase(lbPair.binStep());
    final double priceScaleFactor = DlmmUtils.priceScaleFactor(scaleDifference);
    final double binId = DlmmUtils.binIdFromInverseLogBinStepBase(targetPrice, priceScaleFactor, inverseLogBinStepBase);
    final int lowerBinId = (int) binId;
    final int upperBinId = lowerBinId + (width - 1);

    System.out.println(DlmmUtils.binPrice(lbPair.binStep(), lowerBinId, scaleDifference, MathContext.DECIMAL64).toPlainString());
    for (int b = lowerBinId; b <= upperBinId; ++b) {
      final double binPrice = DlmmUtils.binPrice(lbPair.binStep(), b, priceScaleFactor);
      System.out.println(binPrice);
    }
    System.out.println(DlmmUtils.binPrice(lbPair.binStep(), upperBinId, scaleDifference, MathContext.DECIMAL64).toPlainString());

    final var lbPairKey = lbPair._address();
    final var positionKey = dlmmClient.derivePositionAccount(lbPairKey, lowerBinId, width).publicKey();
    final var initializePositionIx = dlmmClient.initializePositionWithSeeds(positionKey, lbPairKey, lowerBinId, width);
    instructions.add(initializePositionIx);

    final var binAccountMetas = dlmmClient.deriveBinAccounts(lbPairKey, lowerBinId, upperBinId);

    final double binStepBase = DlmmUtils.binStepBase(lbPair.binStep()).doubleValue();
    double binPrice = DlmmUtils.binPrice(binStepBase, lowerBinId, priceScaleFactor);

    final int amountPerBin = (int) (scaledAmount / width);
    final var bins = new CompressedBinDepositAmount[width];
    bins[0] = new CompressedBinDepositAmount(lowerBinId, (scaledAmount % width) != 0 ? amountPerBin + 1 : amountPerBin);
    for (int i = 1, b = lowerBinId + 1; i < width; ++i, ++b) {
      bins[i] = new CompressedBinDepositAmount(b, amountPerBin);
    }

    final var params = new AddLiquiditySingleSidePreciseParameter2(
        bins,
        1,
//        (long) StrictMath.pow(10, tokenScale),
        Long.MAX_VALUE
//        DlmmUtils.U64_MAX
    );

    final var addLiquidityIx = dlmmClient.askLiquidityPrecise(
        positionKey,
        lbPair,
        null,
        tokenAccount,
        dlmmClient.solanaAccounts().tokenProgram(),
        params,
        null
    ).extraAccounts(binAccountMetas);
    instructions.add(addLiquidityIx);

    return instructions;
  }

  private static List<Instruction> bidLiquidityOneSidePrecise(final NativeProgramAccountClient nativeClient,
                                                              final MeteoraDlmmClient dlmmClient,
                                                              final LbPair lbPair,
                                                              final PublicKey tokenAccount,
                                                              final long scaledAmount,
                                                              final double targetPrice,
                                                              final int scaleDifference,
                                                              final int width) {
    final var instructions = new ArrayList<Instruction>();
    wrapSol(instructions, nativeClient, lbPair, 0, scaledAmount);

    final double inverseLogBinStepBase = DlmmUtils.inverseLogBinStepBase(lbPair.binStep());
    final double priceScaleFactor = DlmmUtils.priceScaleFactor(scaleDifference);
    final double binId = DlmmUtils.binIdFromInverseLogBinStepBase(targetPrice, priceScaleFactor, inverseLogBinStepBase);
    final int upperBinId = (int) binId;
    final int lowerBinId = upperBinId - (width - 1);

    System.out.println(DlmmUtils.binPrice(lbPair.binStep(), lowerBinId, scaleDifference, MathContext.DECIMAL64).toPlainString());
    System.out.println(DlmmUtils.binPrice(lbPair.binStep(), upperBinId, scaleDifference, MathContext.DECIMAL64).toPlainString());

    final var lbPairKey = lbPair._address();
    final var positionKey = dlmmClient.derivePositionAccount(lbPairKey, lowerBinId, width).publicKey();
    final var initializePositionIx = dlmmClient.initializePositionWithSeeds(positionKey, lbPairKey, lowerBinId, width);
    instructions.add(initializePositionIx);

    final var binAccountMetas = dlmmClient.deriveBinAccounts(lbPairKey, lowerBinId, upperBinId);

    final int amountPerBin = (int) (scaledAmount / width);
    final var bins = new CompressedBinDepositAmount[width];
    bins[0] = new CompressedBinDepositAmount(upperBinId, (scaledAmount % width) != 0 ? amountPerBin + 1 : amountPerBin);
    for (int i = 1, b = upperBinId - 1; i < width; i++, --b) {
      bins[i] = new CompressedBinDepositAmount(b, amountPerBin);
    }

    final var params = new AddLiquiditySingleSidePreciseParameter2(
        bins,
        1,
//        (long) StrictMath.pow(10, tokenScale),
        Long.MAX_VALUE
//        DlmmUtils.U64_MAX
    );

    final var addLiquidityIx = dlmmClient.bidLiquidityPrecise(
        positionKey,
        lbPair,
        null,
        tokenAccount,
        dlmmClient.solanaAccounts().tokenProgram(),
        params,
        null
    ).extraAccounts(binAccountMetas);
    instructions.add(addLiquidityIx);

    return instructions;
  }

  private static List<Instruction> removeLiquidityByRange(final SolanaRpcClient rpcClient,
                                                          final NativeProgramAccountClient nativeClient,
                                                          final MeteoraDlmmClient dlmmClient,
                                                          final LbPair lbPair,
                                                          final PublicKey userTokenX, final PublicKey userTokenY) {
    final var positionAccounts = dlmmClient.fetchPositions(rpcClient).join();

    final var position = positionAccounts.getFirst().data();
    final var binAccountMetas = dlmmClient.deriveBinAccounts(position);

    final var solAccounts = dlmmClient.solanaAccounts();
    final var createXAccount = nativeClient.createATAForOwnerFundedByFeePayer(
        true, userTokenX, lbPair.tokenXMint()
    );
    final var createYAccount = nativeClient.createATAForOwnerFundedByFeePayer(
        true, userTokenY, lbPair.tokenYMint()
    );

    final var tokenProgram = solAccounts.tokenProgram();
//    final var removeLiquidityIx = dlmmClient.removeLiquidityByRange(
//        position,
//        lbPair,
//        null,
//        userTokenX, userTokenY,
//        tokenProgram, tokenProgram,
//        DlmmUtils.BASIS_POINT_MAX,
//        null
//    ).extraAccounts(binAccountMetas);


    final var binReduction = new BinLiquidityReduction[position.upperBinId() - position.lowerBinId() + 1];
    for (int i = 0, b = position.lowerBinId(); i < binReduction.length; ++i, ++b) {
      binReduction[i] = new BinLiquidityReduction(b, DlmmUtils.BASIS_POINT_MAX);
    }
    final var removeLiquidityIx = dlmmClient.removeLiquidity(
        position._address(),
        lbPair,
        null,
        userTokenX, userTokenY,
        tokenProgram, tokenProgram,
        binReduction,
        null
    ).extraAccounts(binAccountMetas);

    final var claimFeeIx = dlmmClient.claimFee(
        lbPair, position,
        userTokenX, userTokenY,
        tokenProgram, tokenProgram,
        null
    ).extraAccounts(binAccountMetas);

    final var closePositionIx = dlmmClient.closePosition(position).extraAccounts(binAccountMetas);

    final var wSolMint = solAccounts.wrappedSolTokenMint();

    return wSolMint.equals(lbPair.tokenXMint()) || wSolMint.equals(lbPair.tokenYMint())
        ? List.of(
        createXAccount,
        createYAccount,
        removeLiquidityIx,
        claimFeeIx,
        closePositionIx,
        nativeClient.unwrapSOL()
    )
        : List.of(
        createXAccount,
        createYAccount,
        removeLiquidityIx,
        claimFeeIx,
        closePositionIx
    );
  }

  private static void simulateAndSend(final boolean simulateOnly,
                                      final List<Signer> signers,
                                      final NativeProgramAccountClient nativeAccountClient,
                                      final SolanaRpcClient rpcClient,
                                      final SolanaRpcClient sendClient,
                                      final HeliusClient heliusClient,
                                      final SolanaAccounts solAccounts,
                                      final List<Instruction> simulationInstructions) {
    final var simulationTransaction = nativeAccountClient.createTransaction(ComputeBudgetProgram.MAX_COMPUTE_BUDGET, 0, simulationInstructions);
    final var encodedSimulationTx = simulationTransaction.base64EncodeToString();

    final var simulationFuture = rpcClient.simulateTransaction(encodedSimulationTx);

    final var feeFuture = simulateOnly ? null : heliusClient.getRecommendedTransactionPriorityFeeEstimate(encodedSimulationTx);

    System.out.println(encodedSimulationTx);

    for (final var ix : simulationTransaction.instructions()) {
      System.out.println("\n\nprogram: " + ix.programId());
      for (final var account : ix.accounts()) {
        System.out.println(account);
      }
    }

    final var simulationResult = simulationFuture.join();
    logSimulationResult(simulationResult);

    if (simulateOnly || simulationResult.error() != null) {
      return;
    }

    final var computeBudget = ComputeBudgetProgram.setComputeUnitLimit(
        solAccounts.invokedComputeBudgetProgram(),
        simulationResult.unitsConsumed().getAsInt()
    );

    final var instructions = new ArrayList<Instruction>();
    instructions.add(computeBudget);
    final var recommendedFee = feeFuture.join();
    System.out.println(recommendedFee.toPlainString());
    final var computePrice = ComputeBudgetProgram.setComputeUnitPrice(
        solAccounts.invokedComputeBudgetProgram(),
        recommendedFee.longValue()
    );
    instructions.add(computePrice);
    instructions.addAll(simulationInstructions);

    final var transaction = nativeAccountClient.createTransaction(instructions);
    transaction.setRecentBlockHash(simulationResult.replacementBlockHash().blockhash());
    transaction.sign(signers);
    final var encodedTx = transaction.base64EncodeToString();
    System.out.println(encodedTx);
    final var sendFuture = sendClient.sendTransaction(encodedTx);
    final var sendResult = sendFuture.join();
    System.out.println(sendResult);
  }

  private static void logSimulationResult(final TxSimulation simulationResult) {
    System.out.format("""
            
            Simulation Result:
              program: %s
              CU consumed: %d
              error: %s
              blockhash: %s
              inner instructions:
              %s
              logs:
              %s
            
            """,
        simulationResult.programId(),
        simulationResult.unitsConsumed().orElse(-1),
        simulationResult.error(),
        simulationResult.replacementBlockHash(),
        simulationResult.innerInstructions().stream().map(InnerInstructions::toString)
            .collect(Collectors.joining("\n    * ", "  * ", "")),
        simulationResult.logs().stream().collect(Collectors.joining("\n    * ", "  * ", ""))
    );
  }
}
