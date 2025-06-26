package software.sava.anchor.programs.pyth.lazer.anchor;

import java.util.List;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

import static java.nio.charset.StandardCharsets.US_ASCII;

public final class PythLazerSolanaContractPDAs {

  public static ProgramDerivedAddress storagePDA(final PublicKey program) {
    return PublicKey.findProgramAddress(List.of(
      "storage".getBytes(US_ASCII)
    ), program);
  }

  private PythLazerSolanaContractPDAs() {
  }
}
