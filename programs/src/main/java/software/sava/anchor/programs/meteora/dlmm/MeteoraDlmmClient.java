package software.sava.anchor.programs.meteora.dlmm;

import software.sava.anchor.programs.meteora.MeteoraAccounts;
import software.sava.anchor.programs.meteora.MeteoraPDAs;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.*;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import static software.sava.anchor.programs.meteora.dlmm.DlmmUtils.binIdToArrayIndex;
import static software.sava.anchor.programs.meteora.dlmm.DlmmUtils.binIdToArrayUpperIndex;

public interface MeteoraDlmmClient {

  static MeteoraDlmmClient createClient(final SolanaAccounts solanaAccounts,
                                        final MeteoraAccounts meteoraAccounts,
                                        final PublicKey owner,
                                        final AccountMeta feePayer) {
    return new MeteoraDlmmClientImpl(solanaAccounts, meteoraAccounts, owner, feePayer);
  }

  static MeteoraDlmmClient createClient(final PublicKey owner, final AccountMeta feePayer) {
    return createClient(
        SolanaAccounts.MAIN_NET,
        MeteoraAccounts.MAIN_NET,
        owner,
        feePayer
    );
  }

  static MeteoraDlmmClient createClient(final NativeProgramAccountClient nativeProgramAccountClient,
                                        final MeteoraAccounts meteoraAccounts) {
    return createClient(
        nativeProgramAccountClient.solanaAccounts(),
        meteoraAccounts,
        nativeProgramAccountClient.ownerPublicKey(),
        nativeProgramAccountClient.feePayer()
    );
  }

  static MeteoraDlmmClient createClient(final NativeProgramAccountClient nativeProgramAccountClient) {
    return createClient(nativeProgramAccountClient, MeteoraAccounts.MAIN_NET);
  }

  SolanaAccounts solanaAccounts();

  MeteoraAccounts meteoraAccounts();

  PublicKey owner();

  AccountMeta feePayer();

  Instruction initializePosition(final PublicKey positionKey,
                                 final PublicKey lbPairKey,
                                 final int lowerBinId,
                                 final int width);

  Instruction addLiquidityByStrategy(final PublicKey positionKey,
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
                                     final LiquidityParameterByStrategy liquidityParameter);

  default Instruction addLiquidityByStrategy(final PublicKey positionKey,
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
                                             final LiquidityParameterByStrategy liquidityParameter) {
    final var strategyParameters = liquidityParameter.strategyParameters();
    final var programId = meteoraAccounts().dlmmProgram();
    final var binArrayLowerKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayIndex(strategyParameters.minBinId()), programId);
    final var binArrayUpperKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayUpperIndex(strategyParameters.maxBinId()), programId);

    return addLiquidityByStrategy(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey, reserveYKey,
        tokenXMintKey, tokenYMintKey,
        binArrayLowerKey.publicKey(), binArrayUpperKey.publicKey(),
        tokenXProgramKey, tokenYProgramKey,
        liquidityParameter
    );
  }

  default Instruction addLiquidityByStrategy(final PublicKey positionKey,
                                             final LbPair lbPair,
                                             final PublicKey binArrayBitmapExtensionKey,
                                             final PublicKey userTokenXKey,
                                             final PublicKey userTokenYKey,
                                             final PublicKey tokenXProgramKey,
                                             final PublicKey tokenYProgramKey,
                                             final LiquidityParameterByStrategy liquidityParameter) {
    final var lbPairKey = lbPair._address();
    return addLiquidityByStrategy(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        lbPair.reserveX(), lbPair.reserveY(),
        lbPair.tokenXMint(), lbPair.tokenYMint(),
        tokenXProgramKey, tokenYProgramKey,
        liquidityParameter
    );
  }

  default Instruction addLiquidityByStrategy(final PublicKey positionKey,
                                             final PublicKey lbPairKey,
                                             final PublicKey binArrayBitmapExtensionKey,
                                             final PublicKey userTokenXKey,
                                             final PublicKey userTokenYKey,
                                             final PublicKey tokenXMintKey,
                                             final PublicKey tokenYMintKey,
                                             final PublicKey tokenXProgramKey,
                                             final PublicKey tokenYProgramKey,
                                             final LiquidityParameterByStrategy liquidityParameter) {
    final var programId = meteoraAccounts().dlmmProgram();

    final var reserveXKey = MeteoraPDAs.reservePDA(lbPairKey, tokenXMintKey, programId);
    final var reserveYKey = MeteoraPDAs.reservePDA(lbPairKey, tokenYMintKey, programId);

    return addLiquidityByStrategy(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey.publicKey(), reserveYKey.publicKey(),
        tokenXMintKey, tokenYMintKey,
        tokenXProgramKey, tokenYProgramKey,
        liquidityParameter
    );
  }

  Instruction addLiquidityByStrategyOneSide(final PublicKey positionKey,
                                            final PublicKey lbPairKey,
                                            final PublicKey binArrayBitmapExtensionKey,
                                            final PublicKey userTokenKey,
                                            final PublicKey reserveKey,
                                            final PublicKey tokenMintKey,
                                            final PublicKey binArrayLowerKey,
                                            final PublicKey binArrayUpperKey,
                                            final PublicKey tokenProgramKey,
                                            final LiquidityParameterByStrategyOneSide liquidityParameter);

  default Instruction addLiquidityByStrategyOneSide(final PublicKey positionKey,
                                                    final PublicKey lbPairKey,
                                                    final PublicKey binArrayBitmapExtensionKey,
                                                    final PublicKey userTokenKey,
                                                    final PublicKey reserveKey,
                                                    final PublicKey tokenMintKey,
                                                    final PublicKey tokenProgramKey,
                                                    final LiquidityParameterByStrategyOneSide liquidityParameter) {
    final var programId = meteoraAccounts().dlmmProgram();

    final var strategyParameters = liquidityParameter.strategyParameters();
    final var binArrayLowerKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayIndex(strategyParameters.minBinId()), programId);
    final var binArrayUpperKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayUpperIndex(strategyParameters.maxBinId()), programId);

    return addLiquidityByStrategyOneSide(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenKey,
        reserveKey,
        tokenMintKey,
        binArrayLowerKey.publicKey(), binArrayUpperKey.publicKey(),
        tokenProgramKey,
        liquidityParameter
    );
  }

  default Instruction addLiquidityByStrategyOneSide(final PublicKey positionKey,
                                                    final LbPair lbPair,
                                                    final PublicKey binArrayBitmapExtensionKey,
                                                    final PublicKey userTokenKey,
                                                    final PublicKey reserveKey,
                                                    final PublicKey tokenMintKey,
                                                    final PublicKey tokenProgramKey,
                                                    final LiquidityParameterByStrategyOneSide liquidityParameter) {
    return addLiquidityByStrategyOneSide(
        positionKey,
        lbPair._address(),
        binArrayBitmapExtensionKey,
        userTokenKey,
        reserveKey,
        tokenMintKey,
        tokenProgramKey,
        liquidityParameter
    );
  }

  Instruction addLiquidity(final PublicKey positionKey,
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
                           final LiquidityParameter liquidityParameter);

  default Instruction addLiquidity(final PublicKey positionKey,
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
                                   final LiquidityParameter liquidityParameter) {
    final var binLiquidityDist = liquidityParameter.binLiquidityDist();
    int minBidId = binLiquidityDist[0].binId();
    int maxBidId = minBidId;
    int binId;
    for (int i = 1; i < binLiquidityDist.length; ++i) {
      binId = binLiquidityDist[i].binId();
      if (binId < minBidId) {
        minBidId = binId;
      } else if (binId > maxBidId) {
        maxBidId = binId;
      }
    }

    final var programId = meteoraAccounts().dlmmProgram();
    final var binArrayLowerKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayIndex(minBidId), programId);
    final var binArrayUpperKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayUpperIndex(maxBidId), programId);

    return addLiquidity(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey, reserveYKey,
        tokenXMintKey, tokenYMintKey,
        binArrayLowerKey.publicKey(), binArrayUpperKey.publicKey(),
        tokenXProgramKey, tokenYProgramKey,
        liquidityParameter
    );
  }

  default Instruction addLiquidity(final PublicKey positionKey,
                                   final LbPair lbPair,
                                   final PublicKey binArrayBitmapExtensionKey,
                                   final PublicKey userTokenXKey,
                                   final PublicKey userTokenYKey,
                                   final PublicKey tokenXProgramKey,
                                   final PublicKey tokenYProgramKey,
                                   final LiquidityParameter liquidityParameter) {
    return addLiquidity(
        positionKey,
        lbPair._address(),
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        lbPair.reserveX(), lbPair.reserveY(),
        lbPair.tokenXMint(), lbPair.tokenYMint(),
        tokenXProgramKey, tokenYProgramKey,
        liquidityParameter
    );
  }

  default Instruction addLiquidity(final PublicKey positionKey,
                                   final PublicKey lbPairKey,
                                   final PublicKey binArrayBitmapExtensionKey,
                                   final PublicKey userTokenXKey,
                                   final PublicKey userTokenYKey,
                                   final PublicKey tokenXMintKey,
                                   final PublicKey tokenYMintKey,
                                   final PublicKey tokenXProgramKey,
                                   final PublicKey tokenYProgramKey,
                                   final LiquidityParameter liquidityParameter) {
    final var programId = meteoraAccounts().dlmmProgram();
    final var reserveXKey = MeteoraPDAs.reservePDA(lbPairKey, tokenXMintKey, programId);
    final var reserveYKey = MeteoraPDAs.reservePDA(lbPairKey, tokenYMintKey, programId);

    return addLiquidity(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey.publicKey(), reserveYKey.publicKey(),
        tokenXMintKey, tokenYMintKey,
        tokenXProgramKey, tokenYProgramKey,
        liquidityParameter
    );
  }

  Instruction addLiquidityOneSide(final PublicKey positionKey,
                                  final PublicKey lbPairKey,
                                  final PublicKey binArrayBitmapExtensionKey,
                                  final PublicKey userTokenKey,
                                  final PublicKey reserveKey,
                                  final PublicKey tokenMintKey,
                                  final PublicKey binArrayLowerKey,
                                  final PublicKey binArrayUpperKey,
                                  final PublicKey tokenProgramKey,
                                  final LiquidityOneSideParameter liquidityParameter);

  default Instruction addLiquidityOneSide(final PublicKey positionKey,
                                          final PublicKey lbPairKey,
                                          final PublicKey binArrayBitmapExtensionKey,
                                          final PublicKey userTokenKey,
                                          final PublicKey tokenMintKey,
                                          final PublicKey tokenProgramKey,
                                          final LiquidityOneSideParameter liquidityParameter) {
    final var programId = meteoraAccounts().dlmmProgram();

    final var reserveKey = MeteoraPDAs.reservePDA(lbPairKey, tokenMintKey, programId).publicKey();

    final var binLiquidityDist = liquidityParameter.binLiquidityDist();
    int minBidId = binLiquidityDist[0].binId();
    int maxBidId = minBidId;
    int binId;
    for (int i = 1; i < binLiquidityDist.length; ++i) {
      binId = binLiquidityDist[i].binId();
      if (binId < minBidId) {
        minBidId = binId;
      } else if (binId > maxBidId) {
        maxBidId = binId;
      }
    }

    final var binArrayLowerKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayIndex(minBidId), programId);
    final var binArrayUpperKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayUpperIndex(maxBidId), programId);

    return addLiquidityOneSide(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenKey,
        reserveKey,
        tokenMintKey,
        binArrayLowerKey.publicKey(), binArrayUpperKey.publicKey(),
        tokenProgramKey,
        liquidityParameter
    );
  }

  default Instruction addLiquidityOneSide(final PublicKey positionKey,
                                          final LbPair lbPair,
                                          final PublicKey binArrayBitmapExtensionKey,
                                          final PublicKey userTokenKey,
                                          final PublicKey tokenMintKey,
                                          final PublicKey tokenProgramKey,
                                          final LiquidityOneSideParameter liquidityParameter) {
    return addLiquidityOneSide(
        positionKey,
        lbPair._address(),
        binArrayBitmapExtensionKey,
        userTokenKey,
        tokenMintKey,
        tokenProgramKey,
        liquidityParameter
    );
  }

  Instruction addLiquidityOneSidePrecise(final PublicKey positionKey,
                                         final PublicKey lbPairKey,
                                         final PublicKey binArrayBitmapExtensionKey,
                                         final PublicKey userTokenKey,
                                         final PublicKey reserveKey,
                                         final PublicKey tokenMintKey,
                                         final PublicKey binArrayLowerKey,
                                         final PublicKey binArrayUpperKey,
                                         final PublicKey tokenProgramKey,
                                         final AddLiquiditySingleSidePreciseParameter liquidityParameter);

  default Instruction addLiquidityOneSidePrecise(final PublicKey positionKey,
                                                 final PublicKey lbPairKey,
                                                 final PublicKey binArrayBitmapExtensionKey,
                                                 final PublicKey userTokenKey,
                                                 final PublicKey tokenMintKey,
                                                 final PublicKey tokenProgramKey,
                                                 final AddLiquiditySingleSidePreciseParameter liquidityParameter) {
    final var programId = meteoraAccounts().dlmmProgram();

    final var reserveKey = MeteoraPDAs.reservePDA(lbPairKey, tokenMintKey, programId).publicKey();

    final var bins = liquidityParameter.bins();
    int minBidId = bins[0].binId();
    int maxBidId = minBidId;
    int binId;
    for (int i = 1; i < bins.length; ++i) {
      binId = bins[i].binId();
      if (binId < minBidId) {
        minBidId = binId;
      } else if (binId > maxBidId) {
        maxBidId = binId;
      }
    }

    final var binArrayLowerKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayIndex(minBidId), programId);
    final var binArrayUpperKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayUpperIndex(maxBidId), programId);

    return addLiquidityOneSidePrecise(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenKey,
        reserveKey,
        tokenMintKey,
        binArrayLowerKey.publicKey(), binArrayUpperKey.publicKey(),
        tokenProgramKey,
        liquidityParameter
    );
  }

  default Instruction addLiquidityOneSidePrecise(final PublicKey positionKey,
                                                 final LbPair lbPair,
                                                 final PublicKey binArrayBitmapExtensionKey,
                                                 final PublicKey userTokenKey,
                                                 final PublicKey tokenMintKey,
                                                 final PublicKey tokenProgramKey,
                                                 final AddLiquiditySingleSidePreciseParameter liquidityParameter) {
    return addLiquidityOneSidePrecise(
        positionKey,
        lbPair._address(),
        binArrayBitmapExtensionKey,
        userTokenKey,
        tokenMintKey,
        tokenProgramKey,
        liquidityParameter
    );
  }

  Instruction addLiquidityByWeight(final PublicKey positionKey,
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
                                   final LiquidityParameterByWeight liquidityParameter);

  default Instruction addLiquidityByWeight(final PublicKey positionKey,
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
                                           final LiquidityParameterByWeight liquidityParameter) {
    final var programId = meteoraAccounts().dlmmProgram();

    final var binLiquidityDist = liquidityParameter.binLiquidityDist();
    int minBidId = binLiquidityDist[0].binId();
    int maxBidId = minBidId;
    int binId;
    for (int i = 1; i < binLiquidityDist.length; ++i) {
      binId = binLiquidityDist[i].binId();
      if (binId < minBidId) {
        minBidId = binId;
      } else if (binId > maxBidId) {
        maxBidId = binId;
      }
    }
    final var binArrayLowerKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayIndex(minBidId), programId);
    final var binArrayUpperKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayUpperIndex(maxBidId), programId);

    return addLiquidityByWeight(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey, reserveYKey,
        tokenXMintKey, tokenYMintKey,
        binArrayLowerKey.publicKey(), binArrayUpperKey.publicKey(),
        tokenXProgramKey, tokenYProgramKey,
        liquidityParameter
    );
  }

  default Instruction addLiquidityByWeight(final PublicKey positionKey,
                                           final LbPair lbPair,
                                           final PublicKey binArrayBitmapExtensionKey,
                                           final PublicKey userTokenXKey,
                                           final PublicKey userTokenYKey,
                                           final PublicKey tokenXProgramKey,
                                           final PublicKey tokenYProgramKey,
                                           final LiquidityParameterByWeight liquidityParameter) {
    return addLiquidityByWeight(
        positionKey,
        lbPair._address(),
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        lbPair.reserveX(), lbPair.reserveY(),
        lbPair.tokenXMint(), lbPair.tokenYMint(),
        tokenXProgramKey, tokenYProgramKey,
        liquidityParameter
    );
  }

  default Instruction addLiquidityByWeight(final PublicKey positionKey,
                                           final PublicKey lbPairKey,
                                           final PublicKey binArrayBitmapExtensionKey,
                                           final PublicKey userTokenXKey,
                                           final PublicKey userTokenYKey,
                                           final PublicKey tokenXMintKey,
                                           final PublicKey tokenYMintKey,
                                           final PublicKey tokenXProgramKey,
                                           final PublicKey tokenYProgramKey,
                                           final LiquidityParameterByWeight liquidityParameter) {
    final var programId = meteoraAccounts().dlmmProgram();

    final var reserveXKey = MeteoraPDAs.reservePDA(lbPairKey, tokenXMintKey, programId);
    final var reserveYKey = MeteoraPDAs.reservePDA(lbPairKey, tokenYMintKey, programId);

    return addLiquidityByWeight(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey.publicKey(), reserveYKey.publicKey(),
        tokenXMintKey, tokenYMintKey,
        tokenXProgramKey, tokenYProgramKey,
        liquidityParameter
    );
  }

  Instruction removeLiquidityByRange(final PublicKey positionKey,
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
                                     final int bpsToRemove);

  default Instruction removeLiquidityByRange(final PublicKey positionKey,
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
                                             final int bpsToRemove) {
    final var programId = meteoraAccounts().dlmmProgram();
    final var binArrayLowerKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayIndex(fromBinId), programId);
    final var binArrayUpperKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayUpperIndex(toBinId), programId);

    return removeLiquidityByRange(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey, reserveYKey,
        tokenXMintKey, tokenYMintKey,
        binArrayLowerKey.publicKey(), binArrayUpperKey.publicKey(),
        tokenXProgramKey, tokenYProgramKey,
        fromBinId, toBinId, bpsToRemove
    );
  }

  default Instruction removeLiquidityByRange(final PublicKey positionKey,
                                             final LbPair lbPair,
                                             final PublicKey binArrayBitmapExtensionKey,
                                             final PublicKey userTokenXKey,
                                             final PublicKey userTokenYKey,
                                             final PublicKey tokenXProgramKey,
                                             final PublicKey tokenYProgramKey,
                                             final int fromBinId,
                                             final int toBinId,
                                             final int bpsToRemove) {
    final var lbPairKey = lbPair._address();
    return removeLiquidityByRange(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        lbPair.reserveX(), lbPair.reserveY(),
        lbPair.tokenXMint(), lbPair.tokenYMint(),
        tokenXProgramKey, tokenYProgramKey,
        fromBinId, toBinId, bpsToRemove
    );
  }

  default Instruction removeLiquidityByRange(final PublicKey positionKey,
                                             final PublicKey lbPairKey,
                                             final PublicKey binArrayBitmapExtensionKey,
                                             final PublicKey userTokenXKey,
                                             final PublicKey userTokenYKey,
                                             final PublicKey tokenXMintKey,
                                             final PublicKey tokenYMintKey,
                                             final PublicKey tokenXProgramKey,
                                             final PublicKey tokenYProgramKey,
                                             final int fromBinId,
                                             final int toBinId,
                                             final int bpsToRemove) {
    final var programId = meteoraAccounts().dlmmProgram();

    final var reserveXKey = MeteoraPDAs.reservePDA(lbPairKey, tokenXMintKey, programId);
    final var reserveYKey = MeteoraPDAs.reservePDA(lbPairKey, tokenYMintKey, programId);

    return removeLiquidityByRange(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey.publicKey(), reserveYKey.publicKey(),
        tokenXMintKey, tokenYMintKey,
        tokenXProgramKey, tokenYProgramKey,
        fromBinId, toBinId, bpsToRemove
    );
  }

  default Instruction removeLiquidityByRange(final PositionV2 positionV2,
                                             final PublicKey binArrayBitmapExtensionKey,
                                             final PublicKey userTokenXKey,
                                             final PublicKey userTokenYKey,
                                             final PublicKey tokenXMintKey,
                                             final PublicKey tokenYMintKey,
                                             final PublicKey tokenXProgramKey,
                                             final PublicKey tokenYProgramKey,
                                             final int bpsToRemove) {
    final var programId = meteoraAccounts().dlmmProgram();

    final var lbPairKey = positionV2.lbPair();
    final var reserveXKey = MeteoraPDAs.reservePDA(lbPairKey, tokenXMintKey, programId);
    final var reserveYKey = MeteoraPDAs.reservePDA(lbPairKey, tokenYMintKey, programId);

    return removeLiquidityByRange(
        positionV2._address(),
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey.publicKey(), reserveYKey.publicKey(),
        tokenXMintKey, tokenYMintKey,
        tokenXProgramKey, tokenYProgramKey,
        positionV2.lowerBinId(), positionV2.upperBinId(), bpsToRemove
    );
  }

  default Instruction removeLiquidityByRange(final PositionV2 positionV2,
                                             final LbPair lbPair,
                                             final PublicKey binArrayBitmapExtensionKey,
                                             final PublicKey userTokenXKey,
                                             final PublicKey userTokenYKey,
                                             final PublicKey tokenXProgramKey,
                                             final PublicKey tokenYProgramKey,
                                             final int bpsToRemove) {
    return removeLiquidityByRange(
        positionV2._address(),
        positionV2.lbPair(),
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        lbPair.reserveX(), lbPair.reserveY(),
        lbPair.tokenXMint(), lbPair.tokenYMint(),
        tokenXProgramKey, tokenYProgramKey,
        positionV2.lowerBinId(), positionV2.upperBinId(), bpsToRemove
    );
  }

  Instruction removeLiquidity(final PublicKey positionKey,
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
                              final BinLiquidityReduction[] binLiquidityRemoval);

  default Instruction removeLiquidity(final PublicKey positionKey,
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
                                      final BinLiquidityReduction[] binLiquidityRemoval) {
    int minBidId = binLiquidityRemoval[0].binId();
    int maxBidId = minBidId;
    int binId;
    for (int i = 1; i < binLiquidityRemoval.length; ++i) {
      binId = binLiquidityRemoval[i].binId();
      if (binId < minBidId) {
        minBidId = binId;
      } else if (binId > maxBidId) {
        maxBidId = binId;
      }
    }

    final var programId = meteoraAccounts().dlmmProgram();
    final var binArrayLowerKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayIndex(minBidId), programId);
    final var binArrayUpperKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayUpperIndex(maxBidId), programId);

    return removeLiquidity(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey, reserveYKey,
        tokenXMintKey, tokenYMintKey,
        binArrayLowerKey.publicKey(), binArrayUpperKey.publicKey(),
        tokenXProgramKey, tokenYProgramKey,
        binLiquidityRemoval
    );
  }

  default Instruction removeLiquidity(final PublicKey positionKey,
                                      final LbPair lbPair,
                                      final PublicKey binArrayBitmapExtensionKey,
                                      final PublicKey userTokenXKey,
                                      final PublicKey userTokenYKey,
                                      final PublicKey tokenXProgramKey,
                                      final PublicKey tokenYProgramKey,
                                      final BinLiquidityReduction[] binLiquidityRemoval) {
    return removeLiquidity(
        positionKey,
        lbPair._address(),
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        lbPair.reserveX(), lbPair.reserveY(),
        lbPair.tokenXMint(), lbPair.tokenYMint(),
        tokenXProgramKey, tokenYProgramKey,
        binLiquidityRemoval
    );
  }

  default Instruction removeLiquidity(final PublicKey positionKey,
                                      final PublicKey lbPairKey,
                                      final PublicKey binArrayBitmapExtensionKey,
                                      final PublicKey userTokenXKey,
                                      final PublicKey userTokenYKey,
                                      final PublicKey tokenXMintKey,
                                      final PublicKey tokenYMintKey,
                                      final PublicKey tokenXProgramKey,
                                      final PublicKey tokenYProgramKey,
                                      final BinLiquidityReduction[] binLiquidityRemoval) {
    final var programId = meteoraAccounts().dlmmProgram();
    final var reserveXKey = MeteoraPDAs.reservePDA(lbPairKey, tokenXMintKey, programId);
    final var reserveYKey = MeteoraPDAs.reservePDA(lbPairKey, tokenYMintKey, programId);

    return removeLiquidity(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey.publicKey(), reserveYKey.publicKey(),
        tokenXMintKey, tokenYMintKey,
        tokenXProgramKey, tokenYProgramKey,
        binLiquidityRemoval
    );
  }

  Instruction removeAllLiquidity(final PublicKey positionKey,
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
                                 final PublicKey tokenYProgramKey);

  default Instruction removeAllLiquidity(final PublicKey positionKey,
                                         final PublicKey lbPairKey,
                                         final PublicKey binArrayBitmapExtensionKey,
                                         final PublicKey userTokenXKey,
                                         final PublicKey userTokenYKey,
                                         final PublicKey reserveXKey,
                                         final PublicKey reserveYKey,
                                         final PublicKey tokenXMintKey,
                                         final PublicKey tokenYMintKey,
                                         final int minBidId,
                                         final int maxBidId,
                                         final PublicKey tokenXProgramKey,
                                         final PublicKey tokenYProgramKey) {
    final var programId = meteoraAccounts().dlmmProgram();
    final var binArrayLowerKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayIndex(minBidId), programId);
    final var binArrayUpperKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayUpperIndex(maxBidId), programId);

    return removeAllLiquidity(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey, reserveYKey,
        tokenXMintKey, tokenYMintKey,
        binArrayLowerKey.publicKey(), binArrayUpperKey.publicKey(),
        tokenXProgramKey, tokenYProgramKey
    );
  }

  default Instruction removeAllLiquidity(final PublicKey positionKey,
                                         final PublicKey lbPairKey,
                                         final PublicKey binArrayBitmapExtensionKey,
                                         final PublicKey userTokenXKey,
                                         final PublicKey userTokenYKey,
                                         final PublicKey tokenXMintKey,
                                         final PublicKey tokenYMintKey,
                                         final int minBidId,
                                         final int maxBidId,
                                         final PublicKey tokenXProgramKey,
                                         final PublicKey tokenYProgramKey) {
    final var programId = meteoraAccounts().dlmmProgram();
    final var reserveXKey = MeteoraPDAs.reservePDA(lbPairKey, tokenXMintKey, programId);
    final var reserveYKey = MeteoraPDAs.reservePDA(lbPairKey, tokenYMintKey, programId);

    return removeAllLiquidity(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey.publicKey(), reserveYKey.publicKey(),
        tokenXMintKey, tokenYMintKey,
        minBidId, maxBidId,
        tokenXProgramKey, tokenYProgramKey
    );
  }

  default Instruction removeAllLiquidity(final PositionV2 position,
                                         final PublicKey binArrayBitmapExtensionKey,
                                         final PublicKey userTokenXKey,
                                         final PublicKey userTokenYKey,
                                         final PublicKey tokenXMintKey,
                                         final PublicKey tokenYMintKey,
                                         final PublicKey tokenXProgramKey,
                                         final PublicKey tokenYProgramKey) {
    final var programId = meteoraAccounts().dlmmProgram();
    final var lbPairKey = position.lbPair();
    final var reserveXKey = MeteoraPDAs.reservePDA(lbPairKey, tokenXMintKey, programId);
    final var reserveYKey = MeteoraPDAs.reservePDA(lbPairKey, tokenYMintKey, programId);

    return removeAllLiquidity(
        position._address(),
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        reserveXKey.publicKey(), reserveYKey.publicKey(),
        tokenXMintKey, tokenYMintKey,
        position.lowerBinId(), position.upperBinId(),
        tokenXProgramKey, tokenYProgramKey
    );
  }

  Instruction claimFee(final PublicKey lbPairKey,
                       final PublicKey positionKey,
                       final PublicKey binArrayLowerKey,
                       final PublicKey binArrayUpperKey,
                       final PublicKey reserveXKey,
                       final PublicKey reserveYKey,
                       final PublicKey userTokenXKey,
                       final PublicKey userTokenYKey,
                       final PublicKey tokenXMintKey,
                       final PublicKey tokenYMintKey,
                       final PublicKey tokenProgramKey);

  default Instruction claimFee(final PublicKey lbPairKey,
                               final PublicKey positionKey,
                               final int minBidId,
                               final int maxBidId,
                               final PublicKey reserveXKey,
                               final PublicKey reserveYKey,
                               final PublicKey userTokenXKey,
                               final PublicKey userTokenYKey,
                               final PublicKey tokenXMintKey,
                               final PublicKey tokenYMintKey,
                               final PublicKey tokenProgramKey) {
    final var programId = meteoraAccounts().dlmmProgram();
    final var binArrayLowerKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayIndex(minBidId), programId);
    final var binArrayUpperKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayUpperIndex(maxBidId), programId);

    return claimFee(
        lbPairKey,
        positionKey,
        binArrayLowerKey.publicKey(), binArrayUpperKey.publicKey(),
        reserveXKey, reserveYKey,
        userTokenXKey, userTokenYKey,
        tokenXMintKey, tokenYMintKey,
        tokenProgramKey
    );
  }

  default Instruction claimFee(final PublicKey lbPairKey,
                               final PublicKey positionKey,
                               final int minBidId,
                               final int maxBidId,
                               final PublicKey userTokenXKey,
                               final PublicKey userTokenYKey,
                               final PublicKey tokenXMintKey,
                               final PublicKey tokenYMintKey,
                               final PublicKey tokenProgramKey) {
    final var programId = meteoraAccounts().dlmmProgram();
    final var reserveXKey = MeteoraPDAs.reservePDA(lbPairKey, tokenXMintKey, programId);
    final var reserveYKey = MeteoraPDAs.reservePDA(lbPairKey, tokenYMintKey, programId);

    return claimFee(
        lbPairKey,
        positionKey,
        minBidId, maxBidId,
        reserveXKey.publicKey(), reserveYKey.publicKey(),
        userTokenXKey, userTokenYKey,
        tokenXMintKey, tokenYMintKey,
        tokenProgramKey
    );
  }

  default Instruction claimFee(final PositionV2 position,
                               final PublicKey userTokenXKey,
                               final PublicKey userTokenYKey,
                               final PublicKey tokenXMintKey,
                               final PublicKey tokenYMintKey,
                               final PublicKey tokenProgramKey) {
    final var programId = meteoraAccounts().dlmmProgram();
    final var lbPairKey = position.lbPair();
    final var reserveXKey = MeteoraPDAs.reservePDA(lbPairKey, tokenXMintKey, programId);
    final var reserveYKey = MeteoraPDAs.reservePDA(lbPairKey, tokenYMintKey, programId);

    return claimFee(
        lbPairKey,
        position._address(),
        position.lowerBinId(), position.upperBinId(),
        reserveXKey.publicKey(), reserveYKey.publicKey(),
        userTokenXKey, userTokenYKey,
        tokenXMintKey, tokenYMintKey,
        tokenProgramKey
    );
  }

  default Instruction claimFee(final PositionV2 position,
                               final LbPair lbPair,
                               final PublicKey userTokenXKey,
                               final PublicKey userTokenYKey,
                               final PublicKey tokenProgramKey) {
    return claimFee(
        position.lbPair(),
        position._address(),
        position.lowerBinId(), position.upperBinId(),
        lbPair.reserveX(), lbPair.reserveY(),
        userTokenXKey, userTokenYKey,
        lbPair.tokenXMint(), lbPair.tokenYMint(),
        tokenProgramKey
    );
  }

  Instruction claimReward(final PublicKey lbPairKey,
                          final PublicKey positionKey,
                          final PublicKey binArrayLowerKey,
                          final PublicKey binArrayUpperKey,
                          final PublicKey rewardVaultKey,
                          final PublicKey rewardMintKey,
                          final PublicKey userTokenAccountKey,
                          final PublicKey tokenProgramKey,
                          final int rewardIndex);

  default Instruction claimReward(final PublicKey lbPairKey,
                                  final PublicKey positionKey,
                                  final int minBidId,
                                  final int maxBidId,
                                  final PublicKey rewardMintKey,
                                  final PublicKey userTokenAccountKey,
                                  final PublicKey tokenProgramKey,
                                  final int rewardIndex) {
    final var programId = meteoraAccounts().dlmmProgram();
    final var binArrayLowerKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayIndex(minBidId), programId);
    final var binArrayUpperKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayUpperIndex(maxBidId), programId);
    final var rewardVaultKey = MeteoraPDAs.rewardVaultPDA(lbPairKey, rewardIndex, programId).publicKey();

    return claimReward(
        lbPairKey,
        positionKey,
        binArrayLowerKey.publicKey(), binArrayUpperKey.publicKey(),
        rewardVaultKey, rewardMintKey,
        userTokenAccountKey,
        tokenProgramKey,
        rewardIndex
    );
  }

  default Instruction claimReward(final PositionV2 position,
                                  final PublicKey rewardMintKey,
                                  final PublicKey userTokenAccountKey,
                                  final PublicKey tokenProgramKey,
                                  final int rewardIndex) {
    return claimReward(
        position.lbPair(),
        position._address(),
        position.lowerBinId(), position.upperBinId(),
        rewardMintKey,
        userTokenAccountKey,
        tokenProgramKey,
        rewardIndex
    );
  }

  Instruction closePosition(final PublicKey positionKey,
                            final PublicKey lbPairKey,
                            final PublicKey binArrayLowerKey,
                            final PublicKey binArrayUpperKey,
                            final PublicKey rentReceiverKey);

  default Instruction closePosition(final PublicKey positionKey,
                                    final PublicKey lbPairKey,
                                    final PublicKey binArrayLowerKey,
                                    final PublicKey binArrayUpperKey) {
    return closePosition(
        positionKey,
        lbPairKey,
        binArrayLowerKey, binArrayUpperKey,
        feePayer().publicKey()
    );
  }

  default Instruction closePosition(final PublicKey positionKey,
                                    final PublicKey lbPairKey,
                                    final int lowerBinId,
                                    final int upperBinId,
                                    final PublicKey rentReceiverKey) {
    final var programId = meteoraAccounts().dlmmProgram();
    final var binArrayLowerKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayIndex(lowerBinId), programId);
    final var binArrayUpperKey = MeteoraPDAs.binArrayPdA(lbPairKey, binIdToArrayUpperIndex(upperBinId), programId);
    return closePosition(
        positionKey,
        lbPairKey,
        binArrayLowerKey.publicKey(), binArrayUpperKey.publicKey(),
        rentReceiverKey
    );
  }

  default Instruction closePosition(final PublicKey positionKey,
                                    final PublicKey lbPairKey,
                                    final int lowerBinId,
                                    final int upperBinId) {
    return closePosition(
        positionKey,
        lbPairKey,
        lowerBinId, upperBinId,
        feePayer().publicKey()
    );
  }

  default Instruction closePosition(final PositionV2 position, final PublicKey rentReceiverKey) {
    return closePosition(
        position._address(),
        position.lbPair(),
        position.lowerBinId(), position.upperBinId(),
        rentReceiverKey
    );
  }

  default Instruction closePosition(final PositionV2 position) {
    return closePosition(position, feePayer().publicKey());
  }
}
