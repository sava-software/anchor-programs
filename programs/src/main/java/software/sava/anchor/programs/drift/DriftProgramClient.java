package software.sava.anchor.programs.drift;

import software.sava.core.accounts.SolanaAccounts;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

public interface DriftProgramClient {

  static DriftProgramClient createClient(final NativeProgramAccountClient nativeProgramAccountClient,
                                         final DriftAccounts accounts) {
    return new DriftProgramClientImpl(nativeProgramAccountClient, accounts);
  }

  static DriftProgramClient createClient(final NativeProgramAccountClient nativeProgramAccountClient) {
    return createClient(nativeProgramAccountClient, DriftAccounts.MAIN_NET);
  }

  SolanaAccounts solanaAccounts();

  DriftAccounts accounts();
}
