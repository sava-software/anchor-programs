package software.sava.anchor.programs.glam;

import software.sava.core.accounts.meta.AccountMeta;

public record GlamAccountMeta(DynamicGlamAccount dynamicGlamAccount, int index, boolean write) {

  public void setAccount(final AccountMeta[] accounts, final AccountMeta accountMeta) {
    accounts[index] = accountMeta;
  }

  public void setAccount(final AccountMeta[] accounts, final GlamVaultAccounts vaultAccounts) {
    if (index < 0) {
      return;
    }
    final var accountMeta = vaultAccounts.accountMeta(dynamicGlamAccount, write);
    setAccount(accounts, accountMeta);
  }
}
