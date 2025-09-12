package software.sava.anchor.programs.metaplex.token.metadata;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

import java.nio.charset.StandardCharsets;
import java.util.List;

public interface MetaplexAccounts {

  MetaplexAccounts MAIN_NET = createAccounts(
      "metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"
  );

  static MetaplexAccounts createAccounts(final PublicKey tokenMetadataProgram) {
    return new MetaplexAccountsRecord(
        tokenMetadataProgram
    );
  }

  static MetaplexAccounts createAccounts(final String tokenMetadataProgram) {
    return createAccounts(
        PublicKey.fromBase58Encoded(tokenMetadataProgram)
    );
  }

  static ProgramDerivedAddress metadataPDA(final PublicKey tokenMetadataProgram, final PublicKey mintKey) {
    return PublicKey.findProgramAddress(List.of(
            "metadata".getBytes(StandardCharsets.US_ASCII),
            tokenMetadataProgram.toByteArray(),
            mintKey.toByteArray()
        ),
        tokenMetadataProgram
    );
  }

  PublicKey tokenMetadataProgram();

  default ProgramDerivedAddress metadataPDA(final PublicKey mintKey) {
    return metadataPDA(tokenMetadataProgram(), mintKey);
  }
}
