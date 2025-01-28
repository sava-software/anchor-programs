package software.sava.anchor.programs.glam_v0;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

record GlamAccountsRecord(PublicKey program, AccountMeta invokedProgram) implements GlamAccounts {

}
