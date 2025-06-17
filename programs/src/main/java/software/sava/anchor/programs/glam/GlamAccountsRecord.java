package software.sava.anchor.programs.glam;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public record GlamAccountsRecord(PublicKey program,
                                 AccountMeta invokedProgram,
                                 PublicKey glamConfigKey) implements GlamAccounts {

  @Override
  public ProgramDerivedAddress mintPDA(final PublicKey glamPublicKey, final int shareClassId) {
    return PublicKey.findProgramAddress(
        List.of(
            "mint".getBytes(UTF_8),
            new byte[]{(byte) (shareClassId % 256)},
            glamPublicKey.toByteArray()
        ), program()
    );
  }
}
