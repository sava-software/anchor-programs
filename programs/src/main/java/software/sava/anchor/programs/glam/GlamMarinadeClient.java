package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.marinade.MarinadeAccounts;
import software.sava.anchor.programs.marinade.MarinadeProgramClient;

public interface GlamMarinadeClient extends MarinadeProgramClient {

  static GlamMarinadeClient createClient(final GlamProgramAccountClient glamClient,
                                         final MarinadeAccounts marinadeAccounts) {
    return new GlamMarinadeClientImpl(glamClient, marinadeAccounts);
  }

  static GlamMarinadeClient createClient(final GlamProgramAccountClient glamClient) {
    return createClient(glamClient, MarinadeAccounts.MAIN_NET);
  }
}
