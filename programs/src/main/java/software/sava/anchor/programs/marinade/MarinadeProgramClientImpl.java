package software.sava.anchor.programs.marinade;

import software.sava.anchor.programs.marinade.anchor.MarinadeFinanceProgram;
import software.sava.anchor.programs.marinade.anchor.types.State;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

final class MarinadeProgramClientImpl implements MarinadeProgramClient {

  private final NativeProgramAccountClient nativeProgramAccountClient;
  private final SolanaAccounts solanaAccounts;
  private final MarinadeAccounts marinadeAccounts;
  private final PublicKey owner;
  private final PublicKey feePayer;

  MarinadeProgramClientImpl(final NativeProgramAccountClient nativeProgramAccountClient,
                            final MarinadeAccounts marinadeAccounts) {
    this.nativeProgramAccountClient = nativeProgramAccountClient;
    this.solanaAccounts = nativeProgramAccountClient.solanaAccounts();
    this.marinadeAccounts = marinadeAccounts;
    this.owner = nativeProgramAccountClient.ownerPublicKey();
    this.feePayer = nativeProgramAccountClient.feePayer().publicKey();
  }

  @Override
  public SolanaAccounts solanaAccounts() {
    return solanaAccounts;
  }

  @Override
  public MarinadeAccounts marinadeAccounts() {
    return marinadeAccounts;
  }

  @Override
  public NativeProgramAccountClient nativeProgramAccountClient() {
    return nativeProgramAccountClient;
  }

  @Override
  public PublicKey owner() {
    return owner;
  }

  @Override
  public PublicKey feePayer() {
    return feePayer;
  }

  @Override
  public Instruction marinadeDeposit(final PublicKey mSolTokenAccount, final long lamports) {
    return MarinadeFinanceProgram.deposit(
        marinadeAccounts.invokedMarinadeProgram(),
        owner,
        marinadeAccounts.stateProgram(),
        marinadeAccounts.mSolTokenMint(),
        marinadeAccounts.liquidityPoolSolLegAccount(),
        marinadeAccounts.liquidityPoolMSolLegAccount(),
        marinadeAccounts.liquidityPoolMSolLegAuthority(),
        marinadeAccounts.treasuryReserveSolPDA(),
        mSolTokenAccount,
        marinadeAccounts.mSolTokenMintAuthorityPDA(),
        solanaAccounts.systemProgram(),
        solanaAccounts.tokenProgram(),
        lamports
    );
  }

  @Override
  public Instruction depositStakeAccount(final State marinadeProgramState,
                                         final PublicKey stakeAccount,
                                         final PublicKey mSolTokenAccount,
                                         final PublicKey validatorPublicKey,
                                         final int validatorIndex) {
    return MarinadeFinanceProgram.depositStakeAccount(
        marinadeAccounts.invokedMarinadeProgram(),
        owner,
        owner,
        marinadeAccounts.stateProgram(),
        marinadeProgramState.validatorSystem().validatorList().account(),
        marinadeProgramState.stakeSystem().stakeList().account(),
        stakeAccount,
        findDuplicationKey(validatorPublicKey).publicKey(),
        marinadeAccounts.mSolTokenMint(),
        mSolTokenAccount,
        marinadeAccounts.mSolTokenMintAuthorityPDA(),
        solanaAccounts.clockSysVar(),
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram(),
        solanaAccounts.tokenProgram(),
        solanaAccounts.stakeProgram(),
        validatorIndex
    );
  }

  @Override
  public Instruction orderUnstake(final PublicKey mSolTokenAccount,
                                  final PublicKey ticketAccount,
                                  final long lamports) {
    return MarinadeFinanceProgram.orderUnstake(
        marinadeAccounts.invokedMarinadeProgram(),
        marinadeAccounts.stateProgram(),
        marinadeAccounts.mSolTokenMint(),
        mSolTokenAccount,
        owner,
        ticketAccount,
        solanaAccounts.clockSysVar(),
        solanaAccounts.rentSysVar(),
        solanaAccounts.tokenProgram(),
        lamports
    );
  }

  @Override
  public Instruction claimTicket(final PublicKey ticketAccount) {
    return MarinadeFinanceProgram.claim(
        marinadeAccounts.invokedMarinadeProgram(),
        marinadeAccounts.stateProgram(),
        marinadeAccounts.treasuryReserveSolPDA(),
        ticketAccount,
        owner,
        solanaAccounts.clockSysVar(),
        solanaAccounts.systemProgram()
    );
  }
}
