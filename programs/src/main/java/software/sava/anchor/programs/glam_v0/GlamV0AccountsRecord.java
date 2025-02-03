package software.sava.anchor.programs.glam_v0;

import software.sava.anchor.programs.glam.GlamAccounts;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public record GlamV0AccountsRecord(PublicKey program, AccountMeta invokedProgram) implements GlamAccounts {


  public ProgramDerivedAddress mintPDA(final PublicKey glamPublicKey, final int shareClassId) {
    return PublicKey.findProgramAddress(
        List.of(
            "share".getBytes(UTF_8),
            new byte[]{(byte) (shareClassId % 256)},
            glamPublicKey.toByteArray()
        ), program()
    );
  }
}
