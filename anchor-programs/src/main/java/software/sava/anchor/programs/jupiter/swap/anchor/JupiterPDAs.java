package software.sava.anchor.programs.jupiter.swap.anchor;

import java.util.List;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

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

  private JupiterPDAs() {
  }
}
