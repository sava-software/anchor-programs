package software.sava.anchor.programs.glam.proxy;

import software.sava.anchor.programs.glam.GlamVaultAccounts;
import systems.glam.ix.proxy.DynamicAccount;
import systems.glam.ix.proxy.DynamicAccountConfig;

import java.util.Map;

final class CachedDynamicGlamAccountFactory implements DynamicGlamAccountFactory {

  private final Map<DynamicAccount<GlamVaultAccounts>, DynamicAccount<GlamVaultAccounts>> cache;

  CachedDynamicGlamAccountFactory(final Map<DynamicAccount<GlamVaultAccounts>, DynamicAccount<GlamVaultAccounts>> cache) {
    this.cache = cache;
  }

  @Override
  public DynamicAccount<GlamVaultAccounts> apply(final DynamicAccountConfig accountConfig) {
    final var name = accountConfig.name();

    final DynamicAccount<GlamVaultAccounts> dynamicAccount;
    if ("cpi_program".equals(name)) {
      dynamicAccount = accountConfig.createReadCpiProgram();
    } else if ("glam_signer".equals(name)) {
      dynamicAccount = accountConfig.createFeePayerAccount();
    } else {
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

    final var cached = cache.putIfAbsent(dynamicAccount, dynamicAccount);
    return cached == null ? dynamicAccount : cached;
  }
}
