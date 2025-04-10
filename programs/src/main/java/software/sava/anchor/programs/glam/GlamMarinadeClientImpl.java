package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamProtocolProgram;
import software.sava.anchor.programs.marinade.MarinadeAccounts;
import software.sava.anchor.programs.marinade.anchor.types.State;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

final class GlamMarinadeClientImpl implements GlamMarinadeClient {

  private final SolanaAccounts solanaAccounts;
  private final MarinadeAccounts marinadeAccounts;
  private final GlamVaultAccounts glamVaultAccounts;
  private final AccountMeta invokedProgram;
  private final GlamProgramAccountClient glamClient;
  private final AccountMeta feePayer;

  GlamMarinadeClientImpl(final GlamProgramAccountClient glamClient, final MarinadeAccounts marinadeAccounts) {
    this.solanaAccounts = glamClient.solanaAccounts();
    this.marinadeAccounts = marinadeAccounts;
    this.glamVaultAccounts = glamClient.vaultAccounts();
    this.invokedProgram = glamVaultAccounts.glamAccounts().invokedProgram();
    this.glamClient = glamClient;
    this.feePayer = glamClient.feePayer();
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
    return glamClient;
  }

  @Override
  public PublicKey owner() {
    return glamVaultAccounts.vaultPublicKey();
  }

  @Override
  public PublicKey feePayer() {
    return feePayer.publicKey();
  }

  @Override
  public Instruction marinadeDeposit(final PublicKey mSolTokenAccount, final long lamports) {
    return GlamProtocolProgram.marinadeDeposit(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        marinadeAccounts.marinadeProgram(),
        marinadeAccounts.stateProgram(),
        marinadeAccounts.mSolTokenMint(),
        marinadeAccounts.liquidityPoolSolLegAccount(),
        marinadeAccounts.liquidityPoolMSolLegAccount(),
        marinadeAccounts.liquidityPoolMSolLegAuthority(),
        marinadeAccounts.treasuryReserveSolPDA(),
        mSolTokenAccount,
        marinadeAccounts.mSolTokenMintAuthorityPDA(),
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
    return GlamProtocolProgram.marinadeDepositStakeAccount(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        marinadeAccounts.marinadeProgram(),
        marinadeAccounts.stateProgram(),
        marinadeProgramState.validatorSystem().validatorList().account(),
        marinadeProgramState.stakeSystem().stakeList().account(),
        stakeAccount,
        findDuplicationKey(validatorPublicKey).publicKey(),
        marinadeAccounts.mSolTokenMint(),
        mSolTokenAccount,
        marinadeAccounts.mSolTokenMintAuthorityPDA(),
        solanaAccounts.clockSysVar(),
        solanaAccounts.tokenProgram(),
        solanaAccounts.stakeProgram(),
        validatorIndex
    );
  }

  @Override
  public Instruction orderUnstake(final PublicKey mSolTokenAccount,
                                  final PublicKey ticketAccount,
                                  final long lamports) {
    return GlamProtocolProgram.marinadeOrderUnstake(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        marinadeAccounts.marinadeProgram(),
        marinadeAccounts.stateProgram(),
        marinadeAccounts.mSolTokenMint(),
        mSolTokenAccount,
        ticketAccount,
        solanaAccounts.clockSysVar(),
        solanaAccounts.tokenProgram(),
        lamports
    );
  }

  @Override
  public Instruction claimTicket(final PublicKey ticketAccount) {
    return GlamProtocolProgram.marinadeClaim(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        marinadeAccounts.marinadeProgram(),
        marinadeAccounts.stateProgram(),
        marinadeAccounts.treasuryReserveSolPDA(),
        ticketAccount,
        solanaAccounts.clockSysVar()
    );
  }
}
