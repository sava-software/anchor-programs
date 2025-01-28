package software.sava.anchor.programs.glam_v0;

import software.sava.anchor.programs.marinade.MarinadeAccounts;
import software.sava.anchor.programs.marinade.MarinadeProgramClient;
import software.sava.core.accounts.AccountWithSeed;
import software.sava.core.accounts.PublicKey;
import software.sava.core.tx.Instruction;

public interface GlamMarinadeClient extends MarinadeProgramClient {

  static GlamMarinadeClient createClient(final GlamProgramAccountClient glamClient,
                                         final MarinadeAccounts marinadeAccounts) {
    return new GlamMarinadeClientImpl(glamClient, marinadeAccounts);
  }

  static GlamMarinadeClient createClient(final GlamProgramAccountClient glamClient) {
    return createClient(glamClient, MarinadeAccounts.MAIN_NET);
  }

  FundPDA createMarinadeTicket();

  private static RuntimeException throwGlamCreatesPDAs() {
    throw new IllegalStateException("Glam program will create the ticket account using the PDA seed and bump/nonce.");
  }

  @Override
  default Instruction createTicketAccountIx(final PublicKey ticketAccount, final long minRentLamports) {
    throw throwGlamCreatesPDAs();
  }

  @Override
  default AccountWithSeed createOffCurveAccountWithSeed(final String asciiSeed) {
    throw throwGlamCreatesPDAs();
  }

  @Override
  default Instruction createTicketAccountWithSeedIx(final AccountWithSeed accountWithSeed, final long minRentLamports) {
    throw throwGlamCreatesPDAs();
  }

  @Override
  default Instruction orderUnstake(final PublicKey mSolTokenAccount,
                                   final PublicKey ticketAccount,
                                   final long lamports) {
    throw new IllegalStateException("TicketPDA is needed for the program to create the ticket account.");
  }

  Instruction orderUnstake(final PublicKey mSolTokenAccount,
                           final FundPDA ticketAccount,
                           final long lamports);
}
