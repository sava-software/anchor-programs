package software.sava.anchor.programs.kamino.lend;

import software.sava.anchor.programs.kamino.KaminoAccounts;
import software.sava.anchor.programs.kamino.lend.anchor.KaminoLendingProgram;
import software.sava.anchor.programs.kamino.lend.anchor.types.InitObligationArgs;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;

final class KaminoLendClientImpl implements KaminoLendClient {

  private final SolanaAccounts solanaAccounts;
  private final KaminoAccounts kaminoAccounts;
  private final PublicKey owner;
  private final PublicKey ownerMetadata;
  private final PublicKey feePayer;
  private final PublicKey referrer;

  KaminoLendClientImpl(final SolanaAccounts solanaAccounts,
                       final KaminoAccounts kaminoAccounts,
                       final PublicKey owner,
                       final PublicKey feePayer,
                       final PublicKey referrer) {
    this.solanaAccounts = solanaAccounts;
    this.kaminoAccounts = kaminoAccounts;
    this.owner = owner;
    this.ownerMetadata = KaminoAccounts.userMetadataPda(owner, kaminoAccounts.program()).publicKey();
    this.feePayer = feePayer;
    this.referrer = referrer;
  }

  public Instruction initObligation(final PublicKey lendingMarket,
                                    final PublicKey obligationKey,
                                    final InitObligationArgs initObligationArgs) {
    return KaminoLendingProgram.initObligation(
        kaminoAccounts.invokedProgram(),
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

  public Instruction refreshReserve(final PublicKey lendingMarket) {
    return KaminoLendingProgram.refreshReserve(
        kaminoAccounts.invokedProgram(),
        null,
        lendingMarket,
        kaminoAccounts.program(),
        kaminoAccounts.program(),
        kaminoAccounts.program(),
        kaminoAccounts.scopePrices()
    );
  }

  public Instruction refreshObligation(final PublicKey lendingMarket,
                                       final PublicKey obligationKey) {
    return KaminoLendingProgram.refreshObligation(
        kaminoAccounts.invokedProgram(),
        lendingMarket,
        obligationKey
    );
  }

  public Instruction refreshObligationFarmsForReserve(final PublicKey obligationKey,
                                                      final ReservePDAs reservePDAs,
                                                      final int mode) {
    return KaminoLendingProgram.refreshObligationFarmsForReserve(
        kaminoAccounts.invokedProgram(),
        feePayer,
        obligationKey,
        reservePDAs.marketAuthority(),
        null,
        null,
        null,
        reservePDAs.market(),
        kaminoAccounts.farmProgram(),
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram(),
        mode
    );
  }

  public Instruction depositReserveLiquidityAndObligationCollateral(final PublicKey obligationKey,
                                                                    final ReservePDAs reservePDAs,
                                                                    final PublicKey sourceTokenAccount,
                                                                    final long liquidityAmount) {
    return KaminoLendingProgram.depositReserveLiquidityAndObligationCollateral(
        kaminoAccounts.invokedProgram(),
        owner,
        obligationKey,
        reservePDAs.market(),
        reservePDAs.marketAuthority(),
        null,
        reservePDAs.mint(),
        reservePDAs.liquiditySupplyVault(),
        reservePDAs.collateralMint(),
        null,
        sourceTokenAccount,
        kaminoAccounts.program(),
        reservePDAs.tokenProgram(),
        reservePDAs.tokenProgram(),
        solanaAccounts.instructionsSysVar(),
        liquidityAmount
    );
  }
}
