package software.sava.anchor.programs.meteora.dlmm;

import software.sava.anchor.programs.meteora.MeteoraAccounts;
import software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmProgram;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.*;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.Signer;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
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
import static software.sava.solana.programs.compute_budget.ComputeBudgetProgram.MAX_COMPUTE_BUDGET;

record MeteoraDlmmClientImpl(SolanaAccounts solanaAccounts,
                             MeteoraAccounts meteoraAccounts,
                             PublicKey owner,
                             AccountMeta feePayer) implements MeteoraDlmmClient {

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
        meteoraAccounts.eventAuthority().publicKey(),
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
                                            final PublicKey binArrayLowerKey,
                                            final PublicKey binArrayUpperKey,
                                            final LiquidityParameterByStrategy liquidityParameter,
                                            final RemainingAccountsInfo remainingAccountsInfo) {
    return appendAccounts(binArrayLowerKey, binArrayUpperKey, LbClmmProgram.addLiquidityByStrategy2(
            meteoraAccounts.invokedDlmmProgram(),
            positionKey,
            lbPairKey,
            binArrayBitmapExtensionKey,
            userTokenXKey, userTokenYKey,
            reserveXKey, reserveYKey,
            tokenXMintKey, tokenYMintKey,
            owner,
            tokenXProgramKey, tokenYProgramKey,
            meteoraAccounts.eventAuthority().publicKey(),
            meteoraAccounts.dlmmProgram(),
            liquidityParameter,
            requireNonNullElse(remainingAccountsInfo, NO_REMAINING_ACCOUNTS)
        )
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
                                  final PublicKey binArrayLowerKey,
                                  final PublicKey binArrayUpperKey,
                                  final LiquidityParameter liquidityParameter,
                                  final RemainingAccountsInfo remainingAccountsInfo) {
    return appendAccounts(binArrayLowerKey, binArrayUpperKey, LbClmmProgram.addLiquidity2(
            meteoraAccounts.invokedDlmmProgram(),
            positionKey,
            lbPairKey,
            binArrayBitmapExtensionKey,
            userTokenXKey, userTokenYKey,
            reserveXKey, reserveYKey,
            tokenXMintKey, tokenYMintKey,
            owner,
            tokenXProgramKey, tokenYProgramKey,
            meteoraAccounts.eventAuthority().publicKey(),
            meteoraAccounts.dlmmProgram(),
            liquidityParameter,
            requireNonNullElse(remainingAccountsInfo, NO_REMAINING_ACCOUNTS)
        )
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
                                                final PublicKey binArrayLowerKey,
                                                final PublicKey binArrayUpperKey,
                                                final AddLiquiditySingleSidePreciseParameter2 liquidityParameter,
                                                final RemainingAccountsInfo remainingAccountsInfo) {
    return appendAccounts(binArrayLowerKey, binArrayUpperKey, LbClmmProgram.addLiquidityOneSidePrecise2(
            meteoraAccounts.invokedDlmmProgram(),
            positionKey,
            lbPairKey,
            binArrayBitmapExtensionKey,
            userTokenKey,
            reserveKey,
            tokenMintKey,
            owner,
            tokenProgramKey,
            meteoraAccounts.eventAuthority().publicKey(),
            meteoraAccounts.dlmmProgram(),
            liquidityParameter,
            remainingAccountsInfo
        )
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
                                            final PublicKey binArrayLowerKey,
                                            final PublicKey binArrayUpperKey,
                                            final int fromBinId,
                                            final int toBinId,
                                            final int bpsToRemove,
                                            final RemainingAccountsInfo remainingAccountsInfo) {
    return appendAccounts(binArrayLowerKey, binArrayUpperKey, LbClmmProgram.removeLiquidityByRange2(
            meteoraAccounts.invokedDlmmProgram(),
            positionKey,
            lbPairKey,
            binArrayBitmapExtensionKey,
            userTokenXKey, userTokenYKey,
            reserveXKey, reserveYKey,
            tokenXMintKey, tokenYMintKey,
            owner,
            tokenXProgramKey, tokenYProgramKey,
            solanaAccounts.memoProgram(),
            meteoraAccounts.eventAuthority().publicKey(),
            meteoraAccounts.dlmmProgram(),
            fromBinId, toBinId, bpsToRemove,
            requireNonNullElse(remainingAccountsInfo, NO_REMAINING_ACCOUNTS)
        )
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
                                     final PublicKey binArrayLowerKey,
                                     final PublicKey binArrayUpperKey,
                                     final BinLiquidityReduction[] binLiquidityRemoval,
                                     final RemainingAccountsInfo remainingAccountsInfo) {
    return appendAccounts(binArrayLowerKey, binArrayUpperKey, LbClmmProgram.removeLiquidity2(
            meteoraAccounts.invokedDlmmProgram(),
            positionKey,
            lbPairKey,
            binArrayBitmapExtensionKey,
            userTokenXKey, userTokenYKey,
            reserveXKey, reserveYKey,
            tokenXMintKey, tokenYMintKey,
            owner,
            tokenXProgramKey, tokenYProgramKey,
            solanaAccounts.memoProgram(),
            meteoraAccounts.eventAuthority().publicKey(),
            meteoraAccounts.dlmmProgram(),
            binLiquidityRemoval,
            requireNonNullElse(remainingAccountsInfo, NO_REMAINING_ACCOUNTS)
        )
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
                              final PublicKey binArrayLowerKey,
                              final PublicKey binArrayUpperKey,
                              final int minBinId,
                              final int maxBinId,
                              final RemainingAccountsInfo remainingAccountsInfo) {
    return appendAccounts(binArrayLowerKey, binArrayUpperKey, LbClmmProgram.claimFee2(
            meteoraAccounts.invokedDlmmProgram(),
            lbPairKey,
            positionKey,
            owner,
            reserveXKey, reserveYKey,
            userTokenXKey, userTokenYKey,
            tokenXMintKey, tokenYMintKey,
            tokenXProgramKey, tokenYProgramKey,
            solanaAccounts.memoProgram(),
            meteoraAccounts.eventAuthority().publicKey(),
            meteoraAccounts.dlmmProgram(),
            minBinId, maxBinId,
            requireNonNullElse(remainingAccountsInfo, NO_REMAINING_ACCOUNTS)
        )
    );
  }

  @Override
  public Instruction claimReward(final PublicKey lbPairKey,
                                 final PublicKey positionKey,
                                 final PublicKey rewardVaultKey,
                                 final PublicKey rewardMintKey,
                                 final PublicKey userTokenAccountKey,
                                 final PublicKey tokenProgramKey,
                                 final PublicKey binArrayLowerKey,
                                 final PublicKey binArrayUpperKey,
                                 final int rewardIndex,
                                 final int minBidId,
                                 final int maxBidId,
                                 final RemainingAccountsInfo remainingAccountsInfo) {
    return appendAccounts(binArrayLowerKey, binArrayUpperKey, LbClmmProgram.claimReward2(
            meteoraAccounts.invokedDlmmProgram(),
            lbPairKey,
            positionKey,
            owner,
            rewardVaultKey,
            rewardMintKey,
            userTokenAccountKey,
            tokenProgramKey,
            solanaAccounts.memoProgram(),
            meteoraAccounts.eventAuthority().publicKey(),
            meteoraAccounts.dlmmProgram(),
            rewardIndex,
            minBidId, maxBidId,
            requireNonNullElse(remainingAccountsInfo, NO_REMAINING_ACCOUNTS)
        )
    );
  }

  @Override
  public Instruction closePosition(final PublicKey positionKey,
                                   final PublicKey rentReceiverKey,
                                   final PublicKey binArrayLowerKey,
                                   final PublicKey binArrayUpperKey) {
    return appendAccounts(binArrayLowerKey, binArrayUpperKey, LbClmmProgram.closePosition2(
            meteoraAccounts.invokedDlmmProgram(),
            positionKey,
            owner,
            rentReceiverKey,
            meteoraAccounts.eventAuthority().publicKey(),
            meteoraAccounts.dlmmProgram()
        )
    );
  }

  private static Instruction appendAccounts(final PublicKey binArrayLowerKey,
                                            final PublicKey binArrayUpperKey,
                                            final Instruction instruction) {
    return instruction.extraAccounts(List.of(
            AccountMeta.createWrite(binArrayLowerKey),
            AccountMeta.createWrite(binArrayUpperKey)
        )
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

    final var simulationBudget = ComputeBudgetProgram.setComputeUnitLimit(
        solAccounts.invokedComputeBudgetProgram(), MAX_COMPUTE_BUDGET
    );
    final var simulationPrice = ComputeBudgetProgram.setComputeUnitPrice(
        solAccounts.invokedComputeBudgetProgram(), 0
    );
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

      final var sendClient = SolanaRpcClient.createClient(
          stakedEndpoint,
          httpClient,
          response -> {
            System.out.println(new String(response.body()));
            return true;
          }
      );


      final var lbPairKey = PublicKey.fromBase58Encoded("7ubS3GccjhQY99AYNKXjNJqnXjaokEdfdV915xnCb96r");
      final var lbPairAccountInfo = rpcClient.getAccountInfo(lbPairKey, LbPair.FACTORY).join();
      final var lbPair = lbPairAccountInfo.data();

      final var positionKey = PublicKey.createOffCurveAccountWithAsciiSeed(
          signer.publicKey(),
          lbPairKey.toBase58(),
          accounts.dlmmProgram()
      ).publicKey();

      final var tokenXAccount = nativeAccountClient.findATA(lbPair.tokenXMint()).publicKey();
      System.out.println("User X Account: " + tokenXAccount);
      final var tokenYAccount = nativeAccountClient.findATA(lbPair.tokenYMint()).publicKey();
      System.out.println("User Y Account: " + tokenYAccount);

      final int lowerBinId = 0;
      final int width = 69;

      final var initializePositionIx = dlmmClient.initializePosition(positionKey, lbPairKey, lowerBinId, width);

      final byte[] parameters = new byte[64];
      parameters[0] = 1;
      final var bins = new CompressedBinDepositAmount[69];
      for (int i = 0; i < 69; i++) {
        bins[0] = new CompressedBinDepositAmount(i, 1_000 + i);
      }
      final long amount = 69420;
      final var params = new AddLiquiditySingleSidePreciseParameter2(
          bins,
          0,
          10
      );
      final var addLiquidityIx = dlmmClient.addLiquidityOneSidePrecise(
          positionKey, lbPair,
          null,
          tokenXAccount,
          lbPair.tokenXMint(),
          solAccounts.tokenProgram(),
          params,
          null
      );

      final var simulationInstructions = List.of(initializePositionIx, addLiquidityIx);
      simulateAndSend(signer, nativeAccountClient, rpcClient, heliusClient, solAccounts, simulationInstructions);
    }
  }

  private static void simulateAndSend(final Signer signer,
                                      final NativeProgramAccountClient nativeAccountClient,
                                      final SolanaRpcClient rpcClient,
                                      final HeliusClient heliusClient,
                                      final SolanaAccounts solAccounts,
                                      final List<Instruction> simulationInstructions) {
    final var simulationTransaction = nativeAccountClient.createTransaction(ComputeBudgetProgram.MAX_COMPUTE_BUDGET, 0, simulationInstructions);
    final var encodedSimulationTx = simulationTransaction.base64EncodeToString();

    final var simulationFuture = rpcClient.simulateTransaction(encodedSimulationTx);
    final var feeFuture = heliusClient.getRecommendedTransactionPriorityFeeEstimate(encodedSimulationTx);
    System.out.println(encodedSimulationTx);

    for (final var ix : simulationTransaction.instructions()) {
      System.out.println(ix.programId());
      for (final var account : ix.accounts()) {
        System.out.println(account);
      }
    }

    final var simulationResult = simulationFuture.join();
    logSimulationResult(simulationResult);

    final var computeBudget = ComputeBudgetProgram.setComputeUnitLimit(
        solAccounts.invokedComputeBudgetProgram(),
        simulationResult.unitsConsumed().getAsInt()
    );

    final var recommendedFee = feeFuture.join();
    System.out.println(recommendedFee.toPlainString());
    final var computePrice = ComputeBudgetProgram.setComputeUnitPrice(
        solAccounts.invokedComputeBudgetProgram(),
        recommendedFee.longValue()
    );
    final var instructions = new ArrayList<Instruction>();
    instructions.add(computeBudget);
    instructions.add(computePrice);
    instructions.addAll(simulationInstructions);

    final var transaction = nativeAccountClient.createTransaction(instructions);
    transaction.setRecentBlockHash(simulationResult.replacementBlockHash().blockhash());
    transaction.sign(signer);
    final var encodedTx = transaction.base64EncodeToString();
    System.out.println(encodedTx);
//      final var sendFuture = sendClient.sendTransaction(encodedTx);
//      final var sendResult = sendFuture.join();
//      System.out.println(sendResult);
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
