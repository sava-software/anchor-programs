package software.sava.anchor.programs.kamino.lend;

import software.sava.anchor.programs.kamino.KaminoAccounts;
import software.sava.anchor.programs.kamino.lend.anchor.KaminoLendingProgram;
import software.sava.anchor.programs.kamino.lend.anchor.types.InitObligationArgs;
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
  private final PublicKey referrer;

  KaminoLendClientImpl(final NativeProgramAccountClient nativeProgramAccountClient,
                       final KaminoAccounts kaminoAccounts,
                       final PublicKey referrer) {
    this.nativeProgramAccountClient = nativeProgramAccountClient;
    this.solanaAccounts = nativeProgramAccountClient.solanaAccounts();
    this.kaminoAccounts = kaminoAccounts;
    this.owner = nativeProgramAccountClient.ownerPublicKey();
    this.ownerMetadata = KaminoAccounts.userMetadataPda(owner, kaminoAccounts.kLendProgram()).publicKey();
    this.feePayer = nativeProgramAccountClient.feePayer().publicKey();
    this.referrer = referrer;
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
  public Instruction refreshReserve(final PublicKey lendingMarket, final PublicKey reserveKey) {
    return KaminoLendingProgram.refreshReserve(
        kaminoAccounts.invokedKLendProgram(),
        reserveKey,
        lendingMarket,
        kaminoAccounts.kLendProgram(),
        kaminoAccounts.kLendProgram(),
        kaminoAccounts.kLendProgram(),
        kaminoAccounts.scopePrices()
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
  public Instruction refreshObligationFarmsForReserve(final PublicKey baseAccountsKey, final int mode) {
    return KaminoLendingProgram.refreshObligationFarmsForReserve(
        kaminoAccounts.invokedKLendProgram(),
        feePayer,
        baseAccountsKey,
        kaminoAccounts.farmProgram(),
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram(),
        mode
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
