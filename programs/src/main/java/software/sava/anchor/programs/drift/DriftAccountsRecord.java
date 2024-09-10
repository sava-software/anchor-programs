package software.sava.anchor.programs.drift;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

record DriftAccountsRecord(AccountMeta invokedDriftProgram,
                           PublicKey stateKey) implements DriftAccounts {
}
