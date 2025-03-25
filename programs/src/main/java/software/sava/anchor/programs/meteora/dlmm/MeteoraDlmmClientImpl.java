package software.sava.anchor.programs.meteora.dlmm;

import software.sava.anchor.programs.meteora.MeteoraAccounts;
import software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmProgram;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.*;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.solana.web2.jupiter.client.http.JupiterClient;

import java.net.URI;
import java.net.http.HttpClient;

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

  public static void main(String[] args) {
    var accounts = MeteoraAccounts.MAIN_NET;
    var solAccounts = SolanaAccounts.MAIN_NET;

    final var rpcEndpoint = URI.create("https://mainnet.helius-rpc.com/?api-key=");

    try (final var httpClient = HttpClient.newHttpClient()) {
      final var jupiter = JupiterClient.createClient(httpClient);

      final var rpcClient = SolanaRpcClient.createClient(
          rpcEndpoint,
          httpClient,
          response -> {
            System.out.println(new String(response.body()));
            return true;
          }
      );

      final var lbPairAccountInfo = rpcClient.getAccountInfo(
          PublicKey.fromBase58Encoded("7ubS3GccjhQY99AYNKXjNJqnXjaokEdfdV915xnCb96r"),
          LbPair.FACTORY
      ).join();

      final var lbPair = lbPairAccountInfo.data();
    }
  }
}
