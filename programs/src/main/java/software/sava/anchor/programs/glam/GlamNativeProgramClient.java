package software.sava.anchor.programs.glam;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.solana.programs.clients.NativeProgramClient;

public interface GlamNativeProgramClient extends NativeProgramClient {

  static GlamNativeProgramClient createClient(final SolanaAccounts solanaAccounts, final GlamFundAccounts glamFundAccounts) {
    return new GlamNativeProgramClientImpl(solanaAccounts, glamFundAccounts);
  }

  static GlamNativeProgramClient createClient(final SolanaAccounts solanaAccounts,
                                              final GlamAccounts glamAccounts,
                                              final PublicKey signerPublicKey,
                                              final PublicKey fundPublicKey) {
    return createClient(solanaAccounts, GlamFundAccounts.createAccounts(glamAccounts, signerPublicKey, fundPublicKey));
  }

  static GlamNativeProgramClient createClient(final PublicKey signerPublicKey, final PublicKey fundPublicKey) {
    return createClient(SolanaAccounts.MAIN_NET, GlamAccounts.MAIN_NET, signerPublicKey, fundPublicKey);
  }
}
