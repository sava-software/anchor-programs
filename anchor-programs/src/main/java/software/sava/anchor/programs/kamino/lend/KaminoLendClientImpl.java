package software.sava.anchor.programs.kamino.lend;

import software.sava.anchor.programs.kamino.KaminoAccounts;
import software.sava.anchor.programs.kamino.lend.anchor.KaminoLendingProgram;
import software.sava.anchor.programs.kamino.lend.anchor.types.InitObligationArgs;
import software.sava.anchor.programs.kamino.lend.anchor.types.Reserve;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

final class KaminoLendClientImpl implements KaminoLendClient {

  private final NativeProgramAccountClient nativeProgramAccountClient;
  private final SolanaAccounts solanaAccounts;
  private final KaminoAccounts kaminoAccounts;
  private final PublicKey owner;
  private final PublicKey ownerMetadata;
  private final PublicKey feePayer;

  KaminoLendClientImpl(final NativeProgramAccountClient nativeProgramAccountClient,
                       final KaminoAccounts kaminoAccounts) {
    this.nativeProgramAccountClient = nativeProgramAccountClient;
    this.solanaAccounts = nativeProgramAccountClient.solanaAccounts();
    this.kaminoAccounts = kaminoAccounts;
    this.owner = nativeProgramAccountClient.ownerPublicKey();
    this.ownerMetadata = KaminoAccounts.userMetadataPda(owner, kaminoAccounts.kLendProgram()).publicKey();
    this.feePayer = nativeProgramAccountClient.feePayer().publicKey();
  }

  @Override
  public SolanaAccounts solanaAccounts() {
    return solanaAccounts;
  }

  @Override
  public KaminoAccounts kaminoAccounts() {
    return kaminoAccounts;
  }

  @Override
  public PublicKey user() {
    return owner;
  }

  @Override
  public Instruction initReferrerTokenState(final PublicKey lendingMarket,
                                            final PublicKey reserveKey,
                                            final PublicKey referrerKey,
                                            final PublicKey referrerTokenStateKey) {
    return KaminoLendingProgram.initReferrerTokenState(
        kaminoAccounts.invokedKLendProgram(),
        feePayer,
        lendingMarket,
        reserveKey,
        referrerKey,
        referrerTokenStateKey,
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram()
    );
  }

  @Override
  public Instruction withdrawReferrerFees(final PublicKey reserveKey,
                                          final ReservePDAs reservePDAs,
                                          final PublicKey referrerKey,
                                          final PublicKey referrerTokenStateKey,
                                          final PublicKey referrerTokenAccountKey) {
    return KaminoLendingProgram.withdrawReferrerFees(
        kaminoAccounts.invokedKLendProgram(),
        referrerKey,
        referrerTokenStateKey,
        reserveKey,
        reservePDAs.collateralMint(),
        reservePDAs.liquiditySupplyVault(),
        referrerTokenAccountKey,
        reservePDAs.market(),
        reservePDAs.marketAuthority(),
        solanaAccounts.tokenProgram()
    );
  }

  @Override
  public Instruction initReferrerStateAndShortUrl(final PublicKey referrerKey,
                                                  final PublicKey referrerStateKey,
                                                  final PublicKey referrerShortUrlKey,
                                                  final PublicKey referrerUserMetadataKey,
                                                  final String shortUrl) {
    return KaminoLendingProgram.initReferrerStateAndShortUrl(
        kaminoAccounts.invokedKLendProgram(),
        referrerKey,
        referrerStateKey,
        referrerShortUrlKey,
        referrerUserMetadataKey,
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram(),
        shortUrl
    );
  }

  @Override
  public Instruction deleteReferrerStateAndShortUrl(final PublicKey referrerKey,
                                                    final PublicKey referrerStateKey,
                                                    final PublicKey shortUrlKey) {
    return KaminoLendingProgram.deleteReferrerStateAndShortUrl(
        kaminoAccounts.invokedKLendProgram(),
        referrerKey,
        referrerStateKey,
        shortUrlKey,
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram()
    );
  }

  @Override
  public Instruction initUserMetadata(final PublicKey referrerUserMetadataKey, final PublicKey userLookupTable) {
    return KaminoLendingProgram.initUserMetadata(
        kaminoAccounts.invokedKLendProgram(),
        owner,
        feePayer,
        ownerMetadata,
        referrerUserMetadataKey,
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram(),
        userLookupTable
    );
  }

  @Override
  public Instruction initObligation(final PublicKey lendingMarket,
                                    final PublicKey obligationKey,
                                    final InitObligationArgs initObligationArgs) {
    return KaminoLendingProgram.initObligation(
        kaminoAccounts.invokedKLendProgram(),
        owner,
        feePayer,
        obligationKey,
        lendingMarket,
        solanaAccounts.systemProgram(),
        solanaAccounts.systemProgram(),
        ownerMetadata,
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram(),
        initObligationArgs
    );
  }

  @Override
  public Instruction initObligationFarmsForReserve(final Reserve reserve,
                                                   final ReservePDAs reservePDAs,
                                                   final PublicKey obligationKey,
                                                   final PublicKey obligationFarmKey,
                                                   final int mode) {
    return initObligationFarmsForReserve(
        reserve._address(),
        reserve.farmCollateral(),
        reservePDAs,
        obligationKey,
        obligationFarmKey,
        mode
    );
  }

  public Instruction initObligationFarmsForReserve(final PublicKey reserveKey,
                                                   final PublicKey reserveFarmStateKey,
                                                   final ReservePDAs reservePDAs,
                                                   final PublicKey obligationKey,
                                                   final PublicKey obligationFarmKey,
                                                   final int mode) {
    return KaminoLendingProgram.initObligationFarmsForReserve(
        kaminoAccounts.invokedKLendProgram(),
        feePayer,
        owner,
        obligationKey,
        reservePDAs.marketAuthority(),
        reserveKey,
        reserveFarmStateKey,
        obligationFarmKey,
        reservePDAs.market(),
        kaminoAccounts.farmProgram(),
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram(),
        mode
    );
  }

  @Override
  public Instruction refreshReserve(final PublicKey lendingMarket,
                                    final PublicKey reserveKey,
                                    final PublicKey pythOracleKey,
                                    final PublicKey switchboardPriceOracleKey,
                                    final PublicKey switchboardTwapOracleKey,
                                    final PublicKey scopePricesKey) {
    return KaminoLendingProgram.refreshReserve(
        kaminoAccounts.invokedKLendProgram(),
        reserveKey,
        lendingMarket,
        pythOracleKey,
        switchboardPriceOracleKey,
        switchboardTwapOracleKey,
        scopePricesKey
    );
  }

  @Override
  public Instruction refreshObligation(final PublicKey lendingMarket,
                                       final PublicKey obligationKey) {
    return KaminoLendingProgram.refreshObligation(
        kaminoAccounts.invokedKLendProgram(),
        lendingMarket,
        obligationKey
    );
  }

  @Override
  public Instruction depositReserveLiquidityAndObligationCollateral(final PublicKey obligationKey,
                                                                    final PublicKey reserveKey,
                                                                    final PublicKey reserveDestinationDepositCollateralKey,
                                                                    final ReservePDAs reservePDAs,
                                                                    final PublicKey sourceTokenAccount,
                                                                    final long liquidityAmount) {
    return KaminoLendingProgram.depositReserveLiquidityAndObligationCollateral(
        kaminoAccounts.invokedKLendProgram(),
        owner,
        obligationKey,
        reservePDAs.market(),
        reservePDAs.marketAuthority(),
        reserveKey,
        reservePDAs.mint(),
        reservePDAs.liquiditySupplyVault(),
        reservePDAs.collateralMint(),
        reservePDAs.collateralSupplyVault(),
        sourceTokenAccount,
        kaminoAccounts.kLendProgram(),
        reservePDAs.tokenProgram(),
        reservePDAs.tokenProgram(),
        solanaAccounts.instructionsSysVar(),
        liquidityAmount
    );
  }
}
