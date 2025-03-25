package software.sava.anchor.programs.meteora.dlmm;

import software.sava.anchor.programs.meteora.MeteoraAccounts;
import software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmProgram;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.*;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.rpc.Filter;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.PrivateKeyEncoding;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.InnerInstructions;
import software.sava.rpc.json.http.response.TxSimulation;
import software.sava.solana.programs.clients.NativeProgramAccountClient;
import software.sava.solana.programs.compute_budget.ComputeBudgetProgram;
import software.sava.solana.web2.helius.client.http.HeliusClient;
import software.sava.solana.web2.jupiter.client.http.JupiterClient;
import systems.comodal.jsoniter.JsonIterator;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static software.sava.solana.programs.compute_budget.ComputeBudgetProgram.MAX_COMPUTE_BUDGET;

record MeteoraDlmmClientImpl(SolanaAccounts solanaAccounts,
                             MeteoraAccounts meteoraAccounts,
                             PublicKey owner,
                             AccountMeta feePayer) implements MeteoraDlmmClient {

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
                                            final PublicKey binArrayLowerKey,
                                            final PublicKey binArrayUpperKey,
                                            final PublicKey tokenXProgramKey,
                                            final PublicKey tokenYProgramKey,
                                            final LiquidityParameterByStrategy liquidityParameter) {
    return LbClmmProgram.addLiquidityByStrategy(
        meteoraAccounts.invokedDlmmProgram(),
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey, reserveYKey,
        tokenXMintKey, tokenYMintKey,
        binArrayLowerKey, binArrayUpperKey,
        owner,
        tokenXProgramKey, tokenYProgramKey,
        meteoraAccounts.eventAuthority().publicKey(),
        meteoraAccounts.dlmmProgram(),
        liquidityParameter
    );
  }

  @Override
  public Instruction addLiquidityByStrategyOneSide(final PublicKey positionKey,
                                                   final PublicKey lbPairKey,
                                                   final PublicKey binArrayBitmapExtensionKey,
                                                   final PublicKey userTokenKey,
                                                   final PublicKey reserveKey,
                                                   final PublicKey tokenMintKey,
                                                   final PublicKey binArrayLowerKey,
                                                   final PublicKey binArrayUpperKey,
                                                   final PublicKey tokenProgramKey,
                                                   final LiquidityParameterByStrategyOneSide liquidityParameter) {
    return LbClmmProgram.addLiquidityByStrategyOneSide(
        meteoraAccounts.invokedDlmmProgram(),
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenKey,
        reserveKey,
        tokenMintKey,
        binArrayLowerKey, binArrayUpperKey,
        owner,
        tokenProgramKey,
        meteoraAccounts.eventAuthority().publicKey(),
        meteoraAccounts.dlmmProgram(),
        liquidityParameter
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
                                  final PublicKey binArrayLowerKey,
                                  final PublicKey binArrayUpperKey,
                                  final PublicKey tokenXProgramKey,
                                  final PublicKey tokenYProgramKey,
                                  final LiquidityParameter liquidityParameter) {
    return LbClmmProgram.addLiquidity(
        meteoraAccounts.invokedDlmmProgram(),
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey, reserveYKey,
        tokenXMintKey, tokenYMintKey,
        binArrayLowerKey, binArrayUpperKey,
        owner,
        tokenXProgramKey, tokenYProgramKey,
        meteoraAccounts.eventAuthority().publicKey(),
        meteoraAccounts.dlmmProgram(),
        liquidityParameter
    );
  }

  @Override
  public Instruction addLiquidityOneSide(final PublicKey positionKey,
                                         final PublicKey lbPairKey,
                                         final PublicKey binArrayBitmapExtensionKey,
                                         final PublicKey userTokenKey,
                                         final PublicKey reserveKey,
                                         final PublicKey tokenMintKey,
                                         final PublicKey binArrayLowerKey,
                                         final PublicKey binArrayUpperKey,
                                         final PublicKey tokenProgramKey,
                                         final LiquidityOneSideParameter liquidityParameter) {
    return LbClmmProgram.addLiquidityOneSide(
        meteoraAccounts.invokedDlmmProgram(),
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenKey,
        reserveKey,
        tokenMintKey,
        binArrayLowerKey, binArrayUpperKey,
        owner,
        tokenProgramKey,
        meteoraAccounts.eventAuthority().publicKey(),
        meteoraAccounts.dlmmProgram(),
        liquidityParameter
    );
  }

  @Override
  public Instruction addLiquidityOneSidePrecise(final PublicKey positionKey,
                                                final PublicKey lbPairKey,
                                                final PublicKey binArrayBitmapExtensionKey,
                                                final PublicKey userTokenKey,
                                                final PublicKey reserveKey,
                                                final PublicKey tokenMintKey,
                                                final PublicKey binArrayLowerKey,
                                                final PublicKey binArrayUpperKey,
                                                final PublicKey tokenProgramKey,
                                                final AddLiquiditySingleSidePreciseParameter liquidityParameter) {
    return LbClmmProgram.addLiquidityOneSidePrecise(
        meteoraAccounts.invokedDlmmProgram(),
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenKey,
        reserveKey,
        tokenMintKey,
        binArrayLowerKey, binArrayUpperKey,
        owner,
        tokenProgramKey,
        meteoraAccounts.eventAuthority().publicKey(),
        meteoraAccounts.dlmmProgram(),
        liquidityParameter
    );
  }

  @Override
  public Instruction addLiquidityByWeight(final PublicKey positionKey,
                                          final PublicKey lbPairKey,
                                          final PublicKey binArrayBitmapExtensionKey,
                                          final PublicKey userTokenXKey,
                                          final PublicKey userTokenYKey,
                                          final PublicKey reserveXKey,
                                          final PublicKey reserveYKey,
                                          final PublicKey tokenXMintKey,
                                          final PublicKey tokenYMintKey,
                                          final PublicKey binArrayLowerKey,
                                          final PublicKey binArrayUpperKey,
                                          final PublicKey tokenXProgramKey,
                                          final PublicKey tokenYProgramKey,
                                          final LiquidityParameterByWeight liquidityParameter) {
    return LbClmmProgram.addLiquidityByWeight(
        meteoraAccounts.invokedDlmmProgram(),
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey, reserveYKey,
        tokenXMintKey, tokenYMintKey,
        binArrayLowerKey, binArrayUpperKey,
        owner,
        tokenXProgramKey, tokenYProgramKey,
        meteoraAccounts.eventAuthority().publicKey(),
        meteoraAccounts.dlmmProgram(),
        liquidityParameter
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
                                            final PublicKey binArrayLowerKey,
                                            final PublicKey binArrayUpperKey,
                                            final PublicKey tokenXProgramKey,
                                            final PublicKey tokenYProgramKey,
                                            final int fromBinId,
                                            final int toBinId,
                                            final int bpsToRemove) {
    return LbClmmProgram.removeLiquidityByRange(
        meteoraAccounts.invokedDlmmProgram(),
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey, reserveYKey,
        tokenXMintKey, tokenYMintKey,
        binArrayLowerKey, binArrayUpperKey,
        owner,
        tokenXProgramKey, tokenYProgramKey,
        meteoraAccounts.eventAuthority().publicKey(),
        meteoraAccounts.dlmmProgram(),
        fromBinId, toBinId, bpsToRemove
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
                                     final PublicKey binArrayLowerKey,
                                     final PublicKey binArrayUpperKey,
                                     final PublicKey tokenXProgramKey,
                                     final PublicKey tokenYProgramKey,
                                     final BinLiquidityReduction[] binLiquidityRemoval) {
    return LbClmmProgram.removeLiquidity(
        meteoraAccounts.invokedDlmmProgram(),
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey, reserveYKey,
        tokenXMintKey, tokenYMintKey,
        binArrayLowerKey, binArrayUpperKey,
        owner,
        tokenXProgramKey, tokenYProgramKey,
        meteoraAccounts.eventAuthority().publicKey(),
        meteoraAccounts.dlmmProgram(),
        binLiquidityRemoval
    );
  }

  @Override
  public Instruction removeAllLiquidity(final PublicKey positionKey,
                                        final PublicKey lbPairKey,
                                        final PublicKey binArrayBitmapExtensionKey,
                                        final PublicKey userTokenXKey,
                                        final PublicKey userTokenYKey,
                                        final PublicKey reserveXKey,
                                        final PublicKey reserveYKey,
                                        final PublicKey tokenXMintKey,
                                        final PublicKey tokenYMintKey,
                                        final PublicKey binArrayLowerKey,
                                        final PublicKey binArrayUpperKey,
                                        final PublicKey tokenXProgramKey,
                                        final PublicKey tokenYProgramKey) {
    return LbClmmProgram.removeAllLiquidity(
        meteoraAccounts.invokedDlmmProgram(),
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey, reserveYKey,
        tokenXMintKey, tokenYMintKey,
        binArrayLowerKey, binArrayUpperKey,
        owner,
        tokenXProgramKey, tokenYProgramKey,
        meteoraAccounts.eventAuthority().publicKey(),
        meteoraAccounts.dlmmProgram()
    );
  }

  @Override
  public Instruction claimFee(final PublicKey lbPairKey,
                              final PublicKey positionKey,
                              final PublicKey binArrayLowerKey,
                              final PublicKey binArrayUpperKey,
                              final PublicKey reserveXKey,
                              final PublicKey reserveYKey,
                              final PublicKey userTokenXKey,
                              final PublicKey userTokenYKey,
                              final PublicKey tokenXMintKey,
                              final PublicKey tokenYMintKey,
                              final PublicKey tokenProgramKey) {
    return LbClmmProgram.claimFee(
        meteoraAccounts.invokedDlmmProgram(),
        lbPairKey,
        positionKey,
        binArrayLowerKey, binArrayUpperKey,
        owner,
        reserveXKey, reserveYKey,
        userTokenXKey, userTokenYKey,
        tokenXMintKey, tokenYMintKey,
        tokenProgramKey,
        meteoraAccounts.eventAuthority().publicKey(),
        meteoraAccounts.dlmmProgram()
    );
  }

  @Override
  public Instruction claimReward(final PublicKey lbPairKey,
                                 final PublicKey positionKey,
                                 final PublicKey binArrayLowerKey,
                                 final PublicKey binArrayUpperKey,
                                 final PublicKey rewardVaultKey,
                                 final PublicKey rewardMintKey,
                                 final PublicKey userTokenAccountKey,
                                 final PublicKey tokenProgramKey,
                                 final int rewardIndex) {
    return LbClmmProgram.claimReward(
        meteoraAccounts.invokedDlmmProgram(),
        lbPairKey,
        positionKey,
        binArrayLowerKey, binArrayUpperKey,
        owner,
        rewardVaultKey,
        rewardMintKey,
        userTokenAccountKey,
        tokenProgramKey,
        meteoraAccounts.eventAuthority().publicKey(),
        meteoraAccounts.dlmmProgram(),
        rewardIndex
    );
  }

  @Override
  public Instruction closePosition(final PublicKey positionKey,
                                   final PublicKey lbPairKey,
                                   final PublicKey binArrayLowerKey,
                                   final PublicKey binArrayUpperKey,
                                   final PublicKey rentReceiverKey) {
    return LbClmmProgram.closePosition(
        meteoraAccounts.invokedDlmmProgram(),
        positionKey,
        lbPairKey,
        binArrayLowerKey, binArrayUpperKey,
        owner,
        rentReceiverKey,
        meteoraAccounts.eventAuthority().publicKey(),
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

    final var simulationBudget = ComputeBudgetProgram.setComputeUnitLimit(
        solAccounts.invokedComputeBudgetProgram(), MAX_COMPUTE_BUDGET
    );
    final var simulationPrice = ComputeBudgetProgram.setComputeUnitPrice(
        solAccounts.invokedComputeBudgetProgram(), 0
    );
    try (final var httpClient = HttpClient.newHttpClient()) {
      final var jupiter = JupiterClient.createClient(httpClient);
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

      final var positionAccountInfo = rpcClient.getProgramAccounts(
          accounts.dlmmProgram(),
          List.of(
              PositionV2.SIZE_FILTER,
              Filter.createMemCompFilter(0, accounts.positionV2Discriminator().data()),
              PositionV2.createOwnerFilter(signer.publicKey())
          ),
          PositionV2.FACTORY
      ).join();
      final var position = positionAccountInfo.getFirst().data();

      final var lbPairKey = position.lbPair();

      final var lbPairAccountInfo = rpcClient.getAccountInfo(lbPairKey, LbPair.FACTORY).join();

      final var lbPair = lbPairAccountInfo.data();

      final var tokenXAccount = nativeAccountClient.findATA(lbPair.tokenXMint()).publicKey();
      System.out.println("User X Account: " + tokenXAccount);
      final var tokenYAccount = nativeAccountClient.findATA(lbPair.tokenYMint()).publicKey();
      System.out.println("User Y Account: " + tokenYAccount);

      final var removeLiquidityIx = dlmmClient.removeLiquidityByRange(
          position,
          lbPair,
          null,
          tokenXAccount, tokenYAccount,
          solAccounts.tokenProgram(), solAccounts.tokenProgram(),
          DlmmUtils.BASIS_POINT_MAX
      );

      final var claimFeeIx = dlmmClient.claimFee(
          position, lbPair,
          tokenXAccount, tokenYAccount,
          solAccounts.tokenProgram()
      );

      final var closePositionIx = dlmmClient.closePosition(position);

      final var simulationInstructions = List.of(
          simulationBudget, simulationPrice,
          removeLiquidityIx,
          claimFeeIx,
          closePositionIx
      );
      final var simulationTransaction = nativeAccountClient.createTransaction(simulationInstructions);
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
      final var instructions = List.of(
          computeBudget, computePrice,
          removeLiquidityIx,
          claimFeeIx,
          closePositionIx
      );
      final var transaction = nativeAccountClient.createTransaction(instructions);
      transaction.setRecentBlockHash(simulationResult.replacementBlockHash().blockhash());
      transaction.sign(signer);
      final var encodedTx = transaction.base64EncodeToString();
      System.out.println(encodedTx);
      final var sendFuture = sendClient.sendTransaction(encodedTx);
      final var sendResult = sendFuture.join();
      System.out.println(sendResult);
    }
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
