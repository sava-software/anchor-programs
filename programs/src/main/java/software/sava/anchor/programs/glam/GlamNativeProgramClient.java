package software.sava.anchor.programs.glam;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;
import software.sava.solana.programs.clients.NativeProgramClient;

public interface GlamNativeProgramClient extends NativeProgramClient {

  static GlamNativeProgramClient createClient(final SolanaAccounts solanaAccounts,
                                              final GlamVaultAccounts glamVaultAccounts) {
    return new GlamNativeProgramClientImpl(solanaAccounts, glamVaultAccounts);
  }

  static GlamNativeProgramClient createClient(final SolanaAccounts solanaAccounts,
                                              final GlamAccounts glamAccounts,
                                              final PublicKey feePayer,
                                              final PublicKey glamPublicKey) {
    return createClient(solanaAccounts, GlamVaultAccounts.createAccounts(glamAccounts, feePayer, glamPublicKey));
  }

  static GlamNativeProgramClient createClient(final PublicKey feePayer, final PublicKey glamPublicKey) {
    return createClient(SolanaAccounts.MAIN_NET, GlamAccounts.MAIN_NET, feePayer, glamPublicKey);
  }

  static GlamNativeProgramClient createV0Client(final SolanaAccounts solanaAccounts,
                                                final GlamVaultAccounts glamVaultAccounts) {
    return new software.sava.anchor.programs.glam_v0.GlamNativeProgramClientImpl(solanaAccounts, glamVaultAccounts);
  }

  static GlamNativeProgramClient createV0Client(final SolanaAccounts solanaAccounts,
                                                final GlamAccounts glamAccounts,
                                                final PublicKey feePayer,
                                                final PublicKey glamPublicKey) {
    return createV0Client(solanaAccounts, GlamVaultAccounts.createV0Accounts(glamAccounts, feePayer, glamPublicKey));
  }

  static GlamNativeProgramClient createV0Client(final PublicKey feePayer, final PublicKey glamPublicKey) {
    return createV0Client(SolanaAccounts.MAIN_NET, GlamAccounts.MAIN_NET_VO, feePayer, glamPublicKey);
  }

  Instruction jupiterSetMaxSwapSlippage(final int slippageBps);
}
