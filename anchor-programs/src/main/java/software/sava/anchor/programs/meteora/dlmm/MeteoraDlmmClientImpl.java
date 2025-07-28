package software.sava.anchor.programs.meteora.dlmm;

import software.sava.anchor.programs.meteora.MeteoraAccounts;
import software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmProgram;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.*;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.rpc.Filter;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
  public CompletableFuture<List<AccountInfo<LbPair>>> fetchPairs(final SolanaRpcClient rpcClient) {
    return rpcClient.getProgramAccounts(
        meteoraAccounts.dlmmProgram(),
        List.of(
            LbPair.SIZE_FILTER,
            Filter.createMemCompFilter(0, meteoraAccounts.lbPairDiscriminator().data())
        ),
        LbPair.FACTORY
    );
  }

  @Override
  public CompletableFuture<List<AccountInfo<LbPair>>> fetchPairs(final SolanaRpcClient rpcClient,
                                                                 final PublicKey xMint,
                                                                 final PublicKey yMint) {
    return rpcClient.getProgramAccounts(
        meteoraAccounts.dlmmProgram(),
        List.of(
            LbPair.SIZE_FILTER,
            Filter.createMemCompFilter(0, meteoraAccounts.lbPairDiscriminator().data()),
            LbPair.createTokenXMintFilter(xMint),
            LbPair.createTokenYMintFilter(yMint)
        ),
        LbPair.FACTORY
    );
  }

  @Override
  public Instruction initializePosition(final PublicKey positionKey,
                                        final PublicKey lbPairKey,
                                        final int lowerBinId,
                                        final int width) {
    return LbClmmProgram.initializePosition(
        meteoraAccounts.invokedDlmmProgram(),
        solanaAccounts,
        feePayer.publicKey(),
        positionKey,
        lbPairKey,
        owner,
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
        solanaAccounts,
        feePayer.publicKey(),
        baseKey,
        positionKey,
        lbPairKey,
        owner,
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
}
