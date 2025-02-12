package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamProgram;
import software.sava.anchor.programs.marinade.MarinadeAccounts;
import software.sava.anchor.programs.marinade.anchor.types.State;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.util.Collection;
import java.util.List;

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
    return GlamProgram.marinadeDepositSol(
        invokedProgram,
        solanaAccounts,
        feePayer.publicKey(),
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        marinadeAccounts.stateProgram(),
        marinadeAccounts.treasuryReserveSolPDA(),
        marinadeAccounts.mSolTokenMint(),
        marinadeAccounts.mSolTokenMintAuthorityPDA(),
        marinadeAccounts.liquidityPoolMSolLegAccount(),
        marinadeAccounts.liquidityPoolMSolLegAuthority(),
        marinadeAccounts.liquidityPoolSolLegAccount(),
        mSolTokenAccount,
        marinadeAccounts.marinadeProgram(),
        lamports
    );
  }

  @Override
  public Instruction depositStakeAccount(final State marinadeProgramState,
                                         final PublicKey stakeAccount,
                                         final PublicKey mSolTokenAccount,
                                         final PublicKey validatorPublicKey,
                                         final int validatorIndex) {
    return GlamProgram.marinadeDepositStake(
        invokedProgram,
        solanaAccounts,
        feePayer.publicKey(),
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        marinadeAccounts.stateProgram(),
        marinadeProgramState.validatorSystem().validatorList().account(),
        marinadeProgramState.stakeSystem().stakeList().account(),
        stakeAccount,
        findDuplicationKey(validatorPublicKey).publicKey(),
        marinadeAccounts.mSolTokenMint(),
        marinadeAccounts.mSolTokenMintAuthorityPDA(),
        mSolTokenAccount,
        marinadeAccounts.marinadeProgram(),
        validatorIndex
    );
  }

  @Override
  public Instruction orderUnstake(final PublicKey mSolTokenAccount,
                                  final PublicKey ticketAccount,
                                  final long lamports) {
    return GlamProgram.marinadeDelayedUnstake(
        invokedProgram,
        solanaAccounts,
        feePayer.publicKey(),
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        ticketAccount,
        marinadeAccounts.mSolTokenMint(),
        mSolTokenAccount,
        marinadeAccounts.stateProgram(),
        marinadeAccounts.treasuryReserveSolPDA(),
        marinadeAccounts.marinadeProgram(),
        lamports
    );
  }

  private Instruction claimTickets() {
    return GlamProgram.marinadeClaimTickets(
        glamVaultAccounts.glamAccounts().invokedProgram(),
        solanaAccounts,
        feePayer.publicKey(),
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        marinadeAccounts.stateProgram(),
        marinadeAccounts.treasuryReserveSolPDA(),
        marinadeAccounts.marinadeProgram()
    );
  }

  @Override
  public Instruction claimTicket(final PublicKey ticketAccount) {
    return claimTickets().extraAccount(AccountMeta.createWrite(ticketAccount));
  }

  @Override
  public List<Instruction> claimTickets(final Collection<PublicKey> ticketAccounts) {
    return List.of(claimTickets().extraAccounts(ticketAccounts, AccountMeta.CREATE_WRITE));
  }
}
