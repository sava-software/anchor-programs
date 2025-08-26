package software.sava.anchor.programs.glam.config.anchor;

import java.util.List;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

import static java.nio.charset.StandardCharsets.US_ASCII;

public final class GlamConfigPDAs {

  public static ProgramDerivedAddress globalConfigPDA(final PublicKey program) {
    return PublicKey.findProgramAddress(List.of(
      "global-config".getBytes(US_ASCII)
    ), program);
  }

  private GlamConfigPDAs() {
  }
}
