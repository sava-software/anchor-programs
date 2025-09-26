package software.sava.anchor.programs.glam.proxy;

import software.sava.anchor.programs.glam.GlamVaultAccounts;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import systems.glam.ix.proxy.DynamicAccount;
import systems.glam.ix.proxy.DynamicAccountConfig;

import java.util.Map;

final class CachedDynamicGlamAccountFactory implements DynamicGlamAccountFactory {

  private final Map<PublicKey, AccountMeta> extensionAuthorities;
  private final Map<DynamicAccount<GlamVaultAccounts>, DynamicAccount<GlamVaultAccounts>> cache;

  CachedDynamicGlamAccountFactory(final Map<PublicKey, AccountMeta> extensionAuthorities,
                                  final Map<DynamicAccount<GlamVaultAccounts>, DynamicAccount<GlamVaultAccounts>> cache) {
    this.extensionAuthorities = extensionAuthorities;
    this.cache = cache;
  }

  @Override
  public DynamicAccount<GlamVaultAccounts> apply(final DynamicAccountConfig accountConfig) {
    final var name = accountConfig.name();

    final DynamicAccount<GlamVaultAccounts> dynamicAccount;
    switch (name) {
      case "cpi_program" -> dynamicAccount = accountConfig.createReadCpiProgram();
      case "glam_signer" -> dynamicAccount = accountConfig.createFeePayerAccount();
      case "integration_authority" ->
          dynamicAccount = new IndexedExtAuthority(accountConfig.index(), extensionAuthorities);
      case null, default -> {
        final int index = accountConfig.index();
        final boolean w = accountConfig.writable();
        if ("glam_state".equals(name)) {
          dynamicAccount = w ? new IndexedWriteState(index) : new IndexedReadState(index);
        } else if ("glam_vault".equals(name)) {
          dynamicAccount = w ? new IndexedWriteVault(index) : new IndexedReadVault(index);
        } else {
          throw new IllegalStateException("Unknown dynamic account type: " + accountConfig.name());
        }
      }
    }

    final var cached = cache.putIfAbsent(dynamicAccount, dynamicAccount);
    return cached == null ? dynamicAccount : cached;
  }
}
