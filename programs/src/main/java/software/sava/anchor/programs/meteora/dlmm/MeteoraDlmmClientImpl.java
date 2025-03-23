package software.sava.anchor.programs.meteora.dlmm;

import software.sava.anchor.programs.meteora.MeteoraAccounts;
import software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmProgram;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.*;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;

import java.net.URI;
import java.net.http.HttpClient;

final class MeteoraDlmmClientImpl implements MeteoraDlmmClient {

  private final SolanaAccounts solanaAccounts;
  private final MeteoraAccounts meteoraAccounts;
  private final PublicKey owner;
  private final AccountMeta feePayer;

  MeteoraDlmmClientImpl(final SolanaAccounts solanaAccounts,
                        final MeteoraAccounts meteoraAccounts,
                        final PublicKey owner,
                        final AccountMeta feePayer) {
    this.solanaAccounts = solanaAccounts;
    this.meteoraAccounts = meteoraAccounts;
    this.owner = owner;
    this.feePayer = feePayer;
  }

  @Override
  public SolanaAccounts solanaAccounts() {
    return solanaAccounts;
  }

  @Override
  public MeteoraAccounts meteoraAccounts() {
    return meteoraAccounts;
  }

  @Override
  public PublicKey owner() {
    return owner;
  }

  @Override
  public AccountMeta feePayer() {
    return feePayer;
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

  public static void main(String[] args) {
    var accounts = MeteoraAccounts.MAIN_NET;
    var solAccounts = SolanaAccounts.MAIN_NET;

    final var rpcEndpoint = URI.create("https://mainnet.helius-rpc.com/?api-key=");

    try (final var httpClient = HttpClient.newHttpClient()) {
      final var rpcClient = SolanaRpcClient.createClient(
          rpcEndpoint,
          httpClient,
          response -> {
            System.out.println(new String(response.body()));
            return true;
          }
      );

      final var solUSDC = rpcClient.getAccountInfo(
          PublicKey.fromBase58Encoded("5rCf1DM8LjKTw4YqhnoLcngyZYeNnQqztScTogYHAS6"),
          LbPair.FACTORY
      ).join();
      System.out.println(solUSDC.data());

//      final var lbPairs = rpcClient.getProgramAccounts(
//          accounts.dlmmProgram(),
//          List.of(
//              LbPair.SIZE_FILTER,
//              Filter.createMemCompFilter(0, accounts.lbPairDiscriminator().data()),
//              LbPair.createPairTypeFilter(PairType.Permissionless.ordinal()),
//              LbPair.createTokenXMintFilter(solAccounts.wrappedSolTokenMint())
////              ,
////              LbPair.createTokenYMintFilter(MarinadeAccounts.MAIN_NET.mSolTokenMint())
//          ),
//          LbPair.FACTORY
//      ).join();
//      for (final var accountInfo : lbPairs) {
//        System.out.println(accountInfo.data());
//      }
//      System.out.println(lbPairs.size());
    }
  }
}
