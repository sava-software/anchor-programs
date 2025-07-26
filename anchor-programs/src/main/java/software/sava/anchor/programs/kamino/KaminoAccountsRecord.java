package software.sava.anchor.programs.kamino;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

public record KaminoAccountsRecord(AccountMeta invokedKLendProgram,
                                   PublicKey scopePricesProgram,
                                   PublicKey scopeOraclePrices,
                                   PublicKey farmProgram,
                                   AccountMeta invokedKVaultsProgram,
                                   PublicKey kVaultsEventAuthority) implements KaminoAccounts {


}
