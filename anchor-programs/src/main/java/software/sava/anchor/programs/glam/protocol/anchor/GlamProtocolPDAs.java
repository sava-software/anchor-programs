package software.sava.anchor.programs.glam.protocol.anchor;

import java.util.List;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

import static java.nio.charset.StandardCharsets.US_ASCII;

public final class GlamProtocolPDAs {

  public static ProgramDerivedAddress glamStatePDA(final PublicKey program,
                                                   final PublicKey glamSignerAccount,
                                                   final byte[] stateModelCreated) {
    return PublicKey.findProgramAddress(List.of(
      "state".getBytes(US_ASCII),
      glamSignerAccount.toByteArray(),
      stateModelCreated
    ), program);
  }

  public static ProgramDerivedAddress glamVaultPDA(final PublicKey program,
                                                   final PublicKey glamStateAccount) {
    return PublicKey.findProgramAddress(List.of(
      "vault".getBytes(US_ASCII),
      glamStateAccount.toByteArray()
    ), program);
  }

  private GlamProtocolPDAs() {
  }
}
