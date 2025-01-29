package software.sava.anchor.programs.glam;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

import java.time.ZonedDateTime;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.ZoneOffset.UTC;

public record VaultPDA(ProgramDerivedAddress pda, String id) {

  public static VaultPDA createPDA(final String baseId,
                                   final String id,
                                   final PublicKey owner,
                                   final PublicKey programId) {
    final var pda = PublicKey.findProgramAddress(
        List.of(
            baseId.getBytes(UTF_8),
            id.getBytes(UTF_8),
            owner.toByteArray()
        ), programId
    );
    return new VaultPDA(pda, id);
  }

  public static VaultPDA createPDA(final String baseId,
                                   final PublicKey owner,
                                   final PublicKey programId) {
    return createPDA(baseId, ZonedDateTime.now(UTC).toString(), owner, programId);
  }
}
