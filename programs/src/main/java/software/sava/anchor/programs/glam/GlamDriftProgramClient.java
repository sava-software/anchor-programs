package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.drift.DriftAccounts;
import software.sava.anchor.programs.drift.DriftProgramClient;

public interface GlamDriftProgramClient extends DriftProgramClient {

  static GlamDriftProgramClient createClient(final GlamProgramAccountClient glamClient,
                                             final DriftAccounts marinadeAccounts) {
    return new GlamDriftProgramClientImpl(glamClient, marinadeAccounts);
  }

  static GlamDriftProgramClient createClient(final GlamProgramAccountClient glamClient) {
    return createClient(glamClient, DriftAccounts.MAIN_NET);
  }
}
