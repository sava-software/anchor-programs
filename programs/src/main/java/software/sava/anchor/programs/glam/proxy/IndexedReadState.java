package software.sava.anchor.programs.glam.proxy;

import software.sava.anchor.programs.glam.GlamVaultAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import systems.glam.ix.proxy.DynamicAccount;

record IndexedReadState(int index) implements DynamicAccount<GlamVaultAccounts> {

  @Override
  public void setAccount(final AccountMeta[] mappedAccounts,
                         final AccountMeta cpiProgram,
                         final AccountMeta feePayer,
                         final GlamVaultAccounts runtimeAccounts) {
    mappedAccounts[index] = runtimeAccounts.readGlamState();
  }
}
