package software.sava.anchor.programs.drift.merkle.distributor.anchor;

import java.util.List;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.encoding.ByteUtil;

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
                                                     final PublicKey mintAccount,
                                                     final long version) {
    final byte[] versionBytes = new byte[Long.BYTES];
    ByteUtil.putInt64LE(versionBytes, 0, version);
    return PublicKey.findProgramAddress(List.of(
      "MerkleDistributor".getBytes(US_ASCII),
      mintAccount.toByteArray(),
      versionBytes
    ), program);
  }

  private MerkleDistributorPDAs() {
  }
}
