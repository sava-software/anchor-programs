package software.sava.anchor.programs.meteora.dlmm;

import software.sava.anchor.programs.meteora.MeteoraAccounts;
import software.sava.anchor.programs.meteora.MeteoraPDAs;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.LiquidityParameterByStrategy;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import static software.sava.anchor.programs.meteora.MeteoraPDAs.bidIdToArrayIndex;

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
                                             final PublicKey tokenXMintKey,
                                             final PublicKey tokenYMintKey,
                                             final PublicKey tokenXProgramKey,
                                             final PublicKey tokenYProgramKey,
                                             final LiquidityParameterByStrategy liquidityParameter) {
    final var programId = meteoraAccounts().dlmmProgram();

    final var reserveXKey = MeteoraPDAs.reservePDA(lbPairKey, tokenXMintKey, programId);
    final var reserveYKey = MeteoraPDAs.reservePDA(lbPairKey, tokenYMintKey, programId);

    final var strategyParameters = liquidityParameter.strategyParameters();
    final var binArrayLowerKey = MeteoraPDAs.binArrayPdA(lbPairKey, bidIdToArrayIndex(strategyParameters.minBinId()), programId);
    final var binArrayUpperKey = MeteoraPDAs.binArrayPdA(lbPairKey, bidIdToArrayIndex(strategyParameters.maxBinId()), programId);

    return addLiquidityByStrategy(
        positionKey,
        lbPairKey,
        binArrayBitmapExtensionKey,
        userTokenXKey,
        userTokenYKey, reserveXKey.publicKey(),
        reserveYKey.publicKey(), tokenXMintKey,
        tokenYMintKey, binArrayLowerKey.publicKey(),
        binArrayUpperKey.publicKey(), tokenXProgramKey,
        tokenYProgramKey,
        liquidityParameter
    );
  }
}
