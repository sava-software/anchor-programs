package software.sava.anchor.programs.metaplex.token.metadata;

import software.sava.core.accounts.PublicKey;

record MetaplexAccountsRecord(PublicKey tokenMetadataProgram) implements MetaplexAccounts {
}
