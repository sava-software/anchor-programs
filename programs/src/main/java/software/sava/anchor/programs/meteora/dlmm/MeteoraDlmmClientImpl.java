package software.sava.anchor.programs.meteora.dlmm;

import software.sava.anchor.programs.meteora.MeteoraAccounts;
import software.sava.anchor.programs.meteora.MeteoraPDAs;
import software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmProgram;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.LiquidityParameterByStrategy;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;

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


  public static void main(String[] args) {
    var accounts = MeteoraAccounts.MAIN_NET;
    var solAccounts = SolanaAccounts.MAIN_NET;

    var lbPair = PublicKey.fromBase58Encoded("5rCf1DM8LjKTw4YqhnoLcngyZYeNnQqztScTogYHAS6");
    final var pda = MeteoraPDAs.reservePDA(
        lbPair,
        PublicKey.fromBase58Encoded("EPjFWdd5AufqSSqeM2qN1xzybapC8G4wEGGkZwyTDt1v"),
        accounts.dlmmProgram()
    );

    System.out.println(pda);
  }
}
