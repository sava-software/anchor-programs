package software.sava.anchor.programs.glam;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

record GlamFundAccountsRecord(GlamAccounts glamAccounts,
                              PublicKey signerPublicKey,
                              PublicKey fundPublicKey,
                              ProgramDerivedAddress treasuryPDA,
                              ProgramDerivedAddress openFundsPDA) implements GlamFundAccounts {

  public static ProgramDerivedAddress deriveAddress(final String name, final PublicKey parent, final PublicKey program) {
    return PublicKey.findProgramAddress(
        List.of(
            name.getBytes(UTF_8),
            parent.toByteArray()
        ), program);
  }

  @Override
  public ProgramDerivedAddress shareClassPDA(final long shareId) {
    return PublicKey.findProgramAddress(
        List.of(
            "share".getBytes(UTF_8),
            new byte[]{(byte) (shareId % 256)},
            fundPublicKey.toByteArray()
        ), glamAccounts.program());
  }
}
