package software.sava.anchor.programs.glam.proxy;

import software.sava.anchor.programs.glam.GlamVaultAccounts;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import systems.glam.ix.proxy.DynamicAccount;

public record IndexedWriteVault(int index) implements DynamicAccount<GlamVaultAccounts> {

  @Override
  public void setAccount(final AccountMeta[] mappedAccounts,
                         final PublicKey proxyProgram,
                         final AccountMeta cpiProgram,
                         final AccountMeta feePayer,
                         final GlamVaultAccounts runtimeAccounts) {
    mappedAccounts[index] = runtimeAccounts.writeVault();
  }
}
