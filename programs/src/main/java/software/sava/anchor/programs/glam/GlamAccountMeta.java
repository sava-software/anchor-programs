package software.sava.anchor.programs.glam;

import software.sava.core.accounts.meta.AccountMeta;

public record GlamAccountMeta(AccountMeta accountMeta, int index) {

  public void setAccount(final AccountMeta[] accounts) {
    accounts[index] = accountMeta;
  }
}
