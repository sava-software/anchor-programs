package software.sava.anchor.programs.meteora.dlmm;

import software.sava.anchor.programs.meteora.MeteoraAccounts;
import software.sava.anchor.programs.meteora.MeteoraPDAs;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.*;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import static software.sava.anchor.programs.meteora.dlmm.DlmmUtils.binIdToArrayIndex;

public interface MeteoraDlmmClient {

  static MeteoraDlmmClient createClient(final SolanaAccounts solanaAccounts,
                                        final MeteoraAccounts meteoraAccounts,
                                        final PublicKey owner,
                                        final AccountMeta feePayer) {
    return new MeteoraDlmmClientImpl(
        solanaAccounts,
        meteoraAccounts,
        owner,
        feePayer,
        solanaAccounts.readMemoProgramV2().publicKey(),
        meteoraAccounts.eventAuthority().publicKey()
    );
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

  static List<AccountMeta> deriveBinAccounts(final PublicKey programId,
                                             final PublicKey lbPairKey,
                                             final int lowerBinId,
                                             final int upperBinId) {
    final int lowerBinIndex = binIdToArrayIndex(lowerBinId);
    final int upperBinIndex = binIdToArrayIndex(upperBinId);
    if (lowerBinIndex == upperBinIndex) {
      return List.of(AccountMeta.createWrite(MeteoraPDAs.binArrayPdA(lbPairKey, lowerBinIndex, programId).publicKey()));
    } else {
      return IntStream.rangeClosed(lowerBinIndex, upperBinIndex)
          .mapToObj(binIndex -> MeteoraPDAs.binArrayPdA(lbPairKey, binIndex, programId).publicKey())
          .map(AccountMeta.CREATE_WRITE)
          .toList();
    }
  }

  static List<AccountMeta> deriveBinAccounts(final PublicKey programId,
                                             final PositionV2 positionV2) {
    return deriveBinAccounts(programId, positionV2.lbPair(), positionV2.lowerBinId(), positionV2.upperBinId());
  }

  static Instruction appendBinAccounts(final PublicKey programId,
                                       final PublicKey lbPairKey,
                                       final int lowerBinId,
                                       final int upperBinId,
                                       final Instruction instruction) {
    return instruction.extraAccounts(deriveBinAccounts(programId, lbPairKey, lowerBinId, upperBinId));
  }

  default Instruction appendBinAccounts(final PublicKey lbPairKey,
                                        final int lowerBinId,
                                        final int upperBinId,
                                        final Instruction instruction) {
    final var programId = meteoraAccounts().dlmmProgram();
    return appendBinAccounts(programId, lbPairKey, lowerBinId, upperBinId, instruction);
  }

  SolanaAccounts solanaAccounts();

  MeteoraAccounts meteoraAccounts();

  PublicKey owner();

  AccountMeta feePayer();

  default List<AccountMeta> deriveBinAccounts(final PublicKey lbPairKey,
                                              final int lowerBinId,
                                              final int upperBinId) {
    return deriveBinAccounts(meteoraAccounts().dlmmProgram(), lbPairKey, lowerBinId, upperBinId);
  }


  default List<AccountMeta> deriveBinAccounts(final PositionV2 positionV2) {
    return deriveBinAccounts(positionV2.lbPair(), positionV2.lowerBinId(), positionV2.upperBinId());
  }

  CompletableFuture<List<AccountInfo<PositionV2>>> fetchPositions(final SolanaRpcClient rpcClient);

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
                                     final PublicKey tokenXProgramKey,
                                     final PublicKey tokenYProgramKey,
                                     final LiquidityParameterByStrategy liquidityParameter,
                                     final RemainingAccountsInfo remainingAccountsInfo);

  default Instruction addLiquidityByStrategy(final PublicKey positionKey,
                                             final LbPair lbPair,
                                             final PublicKey binArrayBitmapExtensionKey,
                                             final PublicKey userTokenXKey,
                                             final PublicKey userTokenYKey,
                                             final PublicKey tokenXProgramKey,
                                             final PublicKey tokenYProgramKey,
                                             final LiquidityParameterByStrategy liquidityParameter,
                                             final RemainingAccountsInfo remainingAccountsInfo) {
    return addLiquidityByStrategy(
        positionKey,
        lbPair._address(),
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        lbPair.reserveX(), lbPair.reserveY(),
        lbPair.tokenXMint(), lbPair.tokenYMint(),
        tokenXProgramKey, tokenYProgramKey,
        liquidityParameter,
        remainingAccountsInfo
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
                                             final LiquidityParameterByStrategy liquidityParameter,
                                             final RemainingAccountsInfo remainingAccountsInfo) {
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
        liquidityParameter,
        remainingAccountsInfo
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
                           final PublicKey tokenXProgramKey,
                           final PublicKey tokenYProgramKey,
                           final LiquidityParameter liquidityParameter,
                           final RemainingAccountsInfo remainingAccountsInfo);

  default Instruction addLiquidity(final PublicKey positionKey,
                                   final LbPair lbPair,
                                   final PublicKey binArrayBitmapExtensionKey,
                                   final PublicKey userTokenXKey,
                                   final PublicKey userTokenYKey,
                                   final PublicKey tokenXProgramKey,
                                   final PublicKey tokenYProgramKey,
                                   final LiquidityParameter liquidityParameter,
                                   final RemainingAccountsInfo remainingAccountsInfo) {
    return addLiquidity(
        positionKey,
        lbPair._address(),
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        lbPair.reserveX(), lbPair.reserveY(),
        lbPair.tokenXMint(), lbPair.tokenYMint(),
        tokenXProgramKey, tokenYProgramKey,
        liquidityParameter,
        remainingAccountsInfo
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
                                   final LiquidityParameter liquidityParameter,
                                   final RemainingAccountsInfo remainingAccountsInfo) {
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
        liquidityParameter,
        remainingAccountsInfo
    );
  }

  Instruction addLiquidityOneSidePrecise(final PublicKey positionKey,
                                         final PublicKey lbPairKey,
                                         final PublicKey binArrayBitmapExtensionKey,
                                         final PublicKey userTokenKey,
                                         final PublicKey reserveKey,
                                         final PublicKey tokenMintKey,
                                         final PublicKey tokenProgramKey,
                                         final AddLiquiditySingleSidePreciseParameter2 liquidityParameter,
                                         final RemainingAccountsInfo remainingAccountsInfo);

  default Instruction addLiquidityOneSidePrecise(final PublicKey positionKey,
                                                 final PublicKey lbPairKey,
                                                 final PublicKey binArrayBitmapExtensionKey,
                                                 final PublicKey userTokenKey,
                                                 final PublicKey tokenMintKey,
                                                 final PublicKey tokenProgramKey,
                                                 final AddLiquiditySingleSidePreciseParameter2 liquidityParameter,
                                                 final RemainingAccountsInfo remainingAccountsInfo) {
    final var programId = meteoraAccounts().dlmmProgram();
    final var reserveKey = MeteoraPDAs.reservePDA(lbPairKey, tokenMintKey, programId).publicKey();
    return addLiquidityOneSidePrecise(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenKey,
        reserveKey,
        tokenMintKey,
        tokenProgramKey, liquidityParameter,
        remainingAccountsInfo
    );
  }

  default Instruction addLiquidityOneSidePrecise(final PublicKey positionKey,
                                                 final LbPair lbPair,
                                                 final PublicKey binArrayBitmapExtensionKey,
                                                 final PublicKey userTokenKey,
                                                 final PublicKey tokenMintKey,
                                                 final PublicKey tokenProgramKey,
                                                 final AddLiquiditySingleSidePreciseParameter2 liquidityParameter,
                                                 final RemainingAccountsInfo remainingAccountsInfo) {
    return addLiquidityOneSidePrecise(
        positionKey,
        lbPair._address(),
        binArrayBitmapExtensionKey,
        userTokenKey,
        tokenMintKey,
        tokenProgramKey,
        liquidityParameter,
        remainingAccountsInfo
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
                                     final PublicKey tokenXProgramKey,
                                     final PublicKey tokenYProgramKey,
                                     final int fromBinId,
                                     final int toBinId,
                                     final int bpsToRemove,
                                     final RemainingAccountsInfo remainingAccountsInfo);

  default Instruction removeLiquidityByRange(final PublicKey positionKey,
                                             final LbPair lbPair,
                                             final PublicKey binArrayBitmapExtensionKey,
                                             final PublicKey userTokenXKey,
                                             final PublicKey userTokenYKey,
                                             final PublicKey tokenXProgramKey,
                                             final PublicKey tokenYProgramKey,
                                             final int fromBinId,
                                             final int toBinId,
                                             final int bpsToRemove,
                                             final RemainingAccountsInfo remainingAccountsInfo) {
    final var lbPairKey = lbPair._address();
    return removeLiquidityByRange(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        lbPair.reserveX(), lbPair.reserveY(),
        lbPair.tokenXMint(), lbPair.tokenYMint(),
        tokenXProgramKey, tokenYProgramKey,
        fromBinId, toBinId, bpsToRemove,
        remainingAccountsInfo
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
                                             final int bpsToRemove,
                                             final RemainingAccountsInfo remainingAccountsInfo) {
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
        fromBinId, toBinId, bpsToRemove,
        remainingAccountsInfo
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
                                             final int bpsToRemove,
                                             final RemainingAccountsInfo remainingAccountsInfo) {
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
        positionV2.lowerBinId(), positionV2.upperBinId(), bpsToRemove,
        remainingAccountsInfo
    );
  }

  default Instruction removeLiquidityByRange(final PositionV2 positionV2,
                                             final LbPair lbPair,
                                             final PublicKey binArrayBitmapExtensionKey,
                                             final PublicKey userTokenXKey,
                                             final PublicKey userTokenYKey,
                                             final PublicKey tokenXProgramKey,
                                             final PublicKey tokenYProgramKey,
                                             final int bpsToRemove,
                                             final RemainingAccountsInfo remainingAccountsInfo) {
    return removeLiquidityByRange(
        positionV2._address(),
        positionV2.lbPair(),
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        lbPair.reserveX(), lbPair.reserveY(),
        lbPair.tokenXMint(), lbPair.tokenYMint(),
        tokenXProgramKey, tokenYProgramKey,
        positionV2.lowerBinId(), positionV2.upperBinId(), bpsToRemove,
        remainingAccountsInfo
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
                              final PublicKey tokenXProgramKey,
                              final PublicKey tokenYProgramKey,
                              final BinLiquidityReduction[] binLiquidityRemoval,
                              final RemainingAccountsInfo remainingAccountsInfo);

  default Instruction removeLiquidity(final PublicKey positionKey,
                                      final LbPair lbPair,
                                      final PublicKey binArrayBitmapExtensionKey,
                                      final PublicKey userTokenXKey,
                                      final PublicKey userTokenYKey,
                                      final PublicKey tokenXProgramKey,
                                      final PublicKey tokenYProgramKey,
                                      final BinLiquidityReduction[] binLiquidityRemoval,
                                      final RemainingAccountsInfo remainingAccountsInfo) {
    return removeLiquidity(
        positionKey,
        lbPair._address(),
        binArrayBitmapExtensionKey,
        userTokenXKey, userTokenYKey,
        lbPair.reserveX(), lbPair.reserveY(),
        lbPair.tokenXMint(), lbPair.tokenYMint(),
        tokenXProgramKey, tokenYProgramKey,
        binLiquidityRemoval,
        remainingAccountsInfo
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
                                      final BinLiquidityReduction[] binLiquidityRemoval,
                                      final RemainingAccountsInfo remainingAccountsInfo) {
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
        binLiquidityRemoval,
        remainingAccountsInfo
    );
  }

  Instruction claimFee(final PublicKey lbPairKey,
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
                       final RemainingAccountsInfo remainingAccountsInfo);

  default Instruction claimFee(final PublicKey lbPairKey,
                               final PublicKey positionKey,
                               final PublicKey userTokenXKey,
                               final PublicKey userTokenYKey,
                               final PublicKey tokenXMintKey,
                               final PublicKey tokenYMintKey,
                               final PublicKey tokenXProgramKey,
                               final PublicKey tokenYProgramKey,
                               final int minBidId,
                               final int maxBidId,
                               final RemainingAccountsInfo remainingAccountsInfo) {
    final var programId = meteoraAccounts().dlmmProgram();
    final var reserveXKey = MeteoraPDAs.reservePDA(lbPairKey, tokenXMintKey, programId);
    final var reserveYKey = MeteoraPDAs.reservePDA(lbPairKey, tokenYMintKey, programId);

    return claimFee(
        lbPairKey,
        positionKey,
        reserveXKey.publicKey(), reserveYKey.publicKey(),
        userTokenXKey, userTokenYKey,
        tokenXMintKey, tokenYMintKey,
        tokenXProgramKey, tokenYProgramKey,
        minBidId, maxBidId,
        remainingAccountsInfo
    );
  }

  default Instruction claimFee(final PositionV2 position,
                               final PublicKey userTokenXKey,
                               final PublicKey userTokenYKey,
                               final PublicKey tokenXMintKey,
                               final PublicKey tokenYMintKey,
                               final PublicKey tokenXProgramKey,
                               final PublicKey tokenYProgramKey,
                               final RemainingAccountsInfo remainingAccountsInfo) {
    final var programId = meteoraAccounts().dlmmProgram();
    final var lbPairKey = position.lbPair();
    final var reserveXKey = MeteoraPDAs.reservePDA(lbPairKey, tokenXMintKey, programId);
    final var reserveYKey = MeteoraPDAs.reservePDA(lbPairKey, tokenYMintKey, programId);

    return claimFee(
        lbPairKey,
        position._address(),
        reserveXKey.publicKey(), reserveYKey.publicKey(),
        userTokenXKey, userTokenYKey,
        tokenXMintKey, tokenYMintKey,
        tokenXProgramKey, tokenYProgramKey,
        position.lowerBinId(), position.upperBinId(),
        remainingAccountsInfo
    );
  }

  default Instruction claimFee(final LbPair lbPair,
                               final PositionV2 position,
                               final PublicKey userTokenXKey,
                               final PublicKey userTokenYKey,
                               final PublicKey tokenXProgramKey,
                               final PublicKey tokenYProgramKey,
                               final RemainingAccountsInfo remainingAccountsInfo) {
    return claimFee(
        position.lbPair(),
        position._address(),
        lbPair.reserveX(), lbPair.reserveY(),
        userTokenXKey, userTokenYKey,
        lbPair.tokenXMint(), lbPair.tokenYMint(),
        tokenXProgramKey, tokenYProgramKey,
        position.lowerBinId(), position.upperBinId(),
        remainingAccountsInfo
    );
  }

  Instruction claimReward(final PublicKey lbPairKey,
                          final PublicKey positionKey,
                          final PublicKey rewardVaultKey,
                          final PublicKey rewardMintKey,
                          final PublicKey userTokenAccountKey,
                          final PublicKey tokenProgramKey,
                          final int rewardIndex,
                          final int minBidId,
                          final int maxBidId,
                          final RemainingAccountsInfo remainingAccountsInfo);

  default Instruction claimReward(final PublicKey lbPairKey,
                                  final PublicKey positionKey,
                                  final PublicKey rewardMintKey,
                                  final PublicKey userTokenAccountKey,
                                  final PublicKey tokenProgramKey,
                                  final int rewardIndex,
                                  final int minBidId,
                                  final int maxBidId,
                                  final RemainingAccountsInfo remainingAccountsInfo) {
    final var programId = meteoraAccounts().dlmmProgram();
    final var rewardVaultKey = MeteoraPDAs.rewardVaultPDA(lbPairKey, rewardIndex, programId).publicKey();

    return claimReward(
        lbPairKey,
        positionKey,
        rewardVaultKey, rewardMintKey, userTokenAccountKey, tokenProgramKey,
        rewardIndex,
        minBidId, maxBidId,
        remainingAccountsInfo
    );
  }

  default Instruction claimReward(final PositionV2 position,
                                  final PublicKey rewardMintKey,
                                  final PublicKey userTokenAccountKey,
                                  final PublicKey tokenProgramKey,
                                  final int rewardIndex,
                                  final RemainingAccountsInfo remainingAccountsInfo) {
    return claimReward(
        position.lbPair(),
        position._address(),
        rewardMintKey,
        userTokenAccountKey,
        tokenProgramKey,
        rewardIndex,
        position.lowerBinId(), position.upperBinId(),
        remainingAccountsInfo
    );
  }


  Instruction closePosition(final PublicKey positionKey, final PublicKey rentReceiverKey);

  default Instruction closePosition(final PublicKey positionKey) {
    return closePosition(positionKey, feePayer().publicKey());
  }

  default Instruction closePosition(final PositionV2 position, final PublicKey rentReceiverKey) {
    return closePosition(position._address(), rentReceiverKey);
  }

  default Instruction closePosition(final PositionV2 position) {
    return closePosition(position, feePayer().publicKey());
  }
}
