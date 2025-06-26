package software.sava.anchor.programs.pump.anchor;

import java.util.List;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

import static java.nio.charset.StandardCharsets.US_ASCII;

public final class PumpPDAs {

  public static ProgramDerivedAddress bondingCurvePDA(final PublicKey program,
                                                      final PublicKey mintAccount) {
    return PublicKey.findProgramAddress(List.of(
      "bonding-curve".getBytes(US_ASCII),
      mintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress globalPDA(final PublicKey program) {
    return PublicKey.findProgramAddress(List.of(
      "global".getBytes(US_ASCII)
    ), program);
  }

  public static ProgramDerivedAddress mintAuthorityPDA(final PublicKey program) {
    return PublicKey.findProgramAddress(List.of(
      "mint-authority".getBytes(US_ASCII)
    ), program);
  }

  private PumpPDAs() {
  }
}
