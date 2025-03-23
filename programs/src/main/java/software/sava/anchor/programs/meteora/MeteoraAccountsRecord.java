package software.sava.anchor.programs.meteora;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

public record MeteoraAccountsRecord(PublicKey dlmmProgram,
                                    AccountMeta invokedDlmmProgram,
                                    ProgramDerivedAddress eventAuthority,
                                    AccountMeta invokedDynamicAmmPoolsProgram,
                                    AccountMeta invokedM3m3StakeForFeeProgram,
                                    AccountMeta invokedVaultProgram,
                                    AccountMeta invokedFarmProgram,
                                    AccountMeta invokedDlmmVaultProgram,
                                    AccountMeta invokedAffiliateProgram,
                                    AccountMeta invokedMercurialStableSwapProgram) implements MeteoraAccounts {
}
