package software.sava.anchor.programs.jupiter.merkle.distributor.anchor;

import java.util.List;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

import static java.nio.charset.StandardCharsets.US_ASCII;

public final class MerkleDistributorPDAs {

  public static ProgramDerivedAddress claimStatusPDA(final PublicKey program,
                                                     final PublicKey claimantAccount,
                                                     final PublicKey distributorAccount) {
    return PublicKey.findProgramAddress(List.of(
      "ClaimStatus".getBytes(US_ASCII),
      claimantAccount.toByteArray(),
      distributorAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress distributorPDA(final PublicKey program,
                                                     final PublicKey baseAccount,
                                                     final PublicKey mintAccount,
                                                     final byte[] version) {
    return PublicKey.findProgramAddress(List.of(
      "MerkleDistributor".getBytes(US_ASCII),
      baseAccount.toByteArray(),
      mintAccount.toByteArray(),
      version
    ), program);
  }

  private MerkleDistributorPDAs() {
  }
}
