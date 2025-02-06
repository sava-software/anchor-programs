package software.sava.anchor.programs.glam;

import software.sava.core.accounts.meta.AccountMeta;

public record GlamDynamicAccountMeta(DynamicGlamAccount dynamicGlamAccount, int index, boolean write) {

  public void setAccount(final AccountMeta[] accounts, final AccountMeta accountMeta) {
    accounts[index] = accountMeta;
  }

  public void setAccount(final AccountMeta[] accounts, final GlamVaultAccounts vaultAccounts) {
    final var accountMeta = vaultAccounts.accountMeta(dynamicGlamAccount, write);
    setAccount(accounts, accountMeta);
  }
}
