package software.sava.anchor.programs.jupiter.swap.anchor;

import java.util.List;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

import static java.nio.charset.StandardCharsets.US_ASCII;

public final class JupiterPDAs {

  public static ProgramDerivedAddress destinationTokenAccountPDA(final PublicKey program,
                                                                 final PublicKey walletAccount,
                                                                 final PublicKey tokenProgramAccount,
                                                                 final PublicKey mintAccount) {
    return PublicKey.findProgramAddress(List.of(
      walletAccount.toByteArray(),
      tokenProgramAccount.toByteArray(),
      mintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress openOrdersPDA(final PublicKey program,
                                                    final PublicKey marketAccount,
                                                    final PublicKey payerAccount) {
    return PublicKey.findProgramAddress(List.of(
      "open_orders".getBytes(US_ASCII),
      marketAccount.toByteArray(),
      payerAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress openOrders1PDA(final PublicKey program,
                                                     final PublicKey marketAccount,
                                                     final PublicKey programAuthorityAccount) {
    return PublicKey.findProgramAddress(List.of(
      "open_orders".getBytes(US_ASCII),
      marketAccount.toByteArray(),
      programAuthorityAccount.toByteArray()
    ), program);
  }

  private JupiterPDAs() {
  }
}
