package software.sava.anchor.programs.jupiter;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

record JupiterccountsRecord(PublicKey swapProgram, AccountMeta invokedSwapProgram,
                            PublicKey limitOrderProgram, AccountMeta invokedLimitOrderProgram,
                            PublicKey dcaProgram, AccountMeta invokedDCAProgram) implements JupiterAccounts {
}
