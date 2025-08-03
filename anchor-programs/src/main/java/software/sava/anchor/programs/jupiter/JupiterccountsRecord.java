package software.sava.anchor.programs.jupiter;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

record JupiterccountsRecord(PublicKey swapProgram, AccountMeta invokedSwapProgram,
                            PublicKey aggregatorEventAuthority,
                            PublicKey limitOrderProgram, AccountMeta invokedLimitOrderProgram,
                            PublicKey dcaProgram, AccountMeta invokedDCAProgram,
                            PublicKey voteProgram, AccountMeta invokedVoteProgram,
                            PublicKey govProgram, AccountMeta invokedGovProgram,
                            PublicKey jupTokenMint,
                            PublicKey jupBaseKey,
                            PublicKey lockerKey,
                            PublicKey governorKey,
                            AccountMeta invokedMerkleDistributorProgram) implements JupiterAccounts {
}
