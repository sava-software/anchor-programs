package software.sava.anchor.programs.glam_v0;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.solana.programs.clients.NativeProgramClient;

public interface GlamNativeProgramClient extends NativeProgramClient {

  static GlamNativeProgramClient createClient(final SolanaAccounts solanaAccounts, final GlamVaultAccounts glamVaultAccounts) {
    return new GlamNativeProgramClientImpl(solanaAccounts, glamVaultAccounts);
  }

  static GlamNativeProgramClient createClient(final SolanaAccounts solanaAccounts,
                                              final GlamAccounts glamAccounts,
                                              final PublicKey signerPublicKey,
                                              final PublicKey glamPublicKey) {
    return createClient(solanaAccounts, GlamVaultAccounts.createAccounts(glamAccounts, signerPublicKey, glamPublicKey));
  }

  static GlamNativeProgramClient createClient(final PublicKey signerPublicKey, final PublicKey glamPublicKey) {
    return createClient(SolanaAccounts.MAIN_NET, GlamAccounts.MAIN_NET, signerPublicKey, glamPublicKey);
  }
}
