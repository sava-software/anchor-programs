package software.sava.anchor.programs.drift;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static software.sava.core.accounts.PublicKey.findProgramAddress;

public final class DriftPDAs {

  private static byte[] lowerTwoLE(final int id) {
    return new byte[]{(byte) (id & 0xFF), (byte) (id & 0xFF00)};
  }

  public static ProgramDerivedAddress deriveStateAccount(final PublicKey programId) {
    return findProgramAddress(List.of("drift_state".getBytes(UTF_8)), programId);
  }

  public static ProgramDerivedAddress deriveSignerAccount(final PublicKey programId) {
    return findProgramAddress(List.of("drift_signer".getBytes(UTF_8)), programId);
  }


  public static ProgramDerivedAddress deriveMainUserAccount(final DriftAccounts driftAccounts,
                                                            final PublicKey authority) {
    return deriveUserAccount(driftAccounts, authority, 0);
  }

  public static ProgramDerivedAddress deriveUserAccount(final DriftAccounts driftAccounts,
                                                        final PublicKey authority,
                                                        final int subAccountId) {
    return findProgramAddress(
        List.of(
            "user".getBytes(UTF_8),
            authority.toByteArray(),
            lowerTwoLE(subAccountId)
        ),
        driftAccounts.driftProgram()
    );
  }

  public static ProgramDerivedAddress deriveUserStatsAccount(final DriftAccounts driftAccounts,
                                                             final PublicKey authority) {
    return findProgramAddress(
        List.of(
            "user_stats".getBytes(UTF_8),
            authority.toByteArray()
        ),
        driftAccounts.driftProgram()
    );
  }

  private static ProgramDerivedAddress getIndexedAccount(final DriftAccounts driftAccounts,
                                                         final String namespace,
                                                         final int index) {
    return findProgramAddress(
        List.of(
            namespace.getBytes(UTF_8), lowerTwoLE(index)
        ),
        driftAccounts.driftProgram()
    );
  }

  public static ProgramDerivedAddress derivePerpMarketAccount(final DriftAccounts driftAccounts,
                                                              final int marketIndex) {
    return getIndexedAccount(driftAccounts, "perp_market", marketIndex);
  }

  public static ProgramDerivedAddress deriveSpotMarketAccount(final DriftAccounts driftAccounts,
                                                              final int marketIndex) {
    return getIndexedAccount(driftAccounts, "spot_market", marketIndex);
  }

  public static ProgramDerivedAddress deriveInsuranceFundStakeAccount(final DriftAccounts driftAccounts,
                                                                      final int marketIndex) {
    return getIndexedAccount(driftAccounts, "insurance_fund_stake", marketIndex);
  }

  public static ProgramDerivedAddress derivePreLaunchOracleAccount(final DriftAccounts driftAccounts,
                                                                   final int marketIndex) {
    return getIndexedAccount(driftAccounts, "prelaunch_oracle", marketIndex);
  }

  public static ProgramDerivedAddress derivePythPullOracleAccount(final PublicKey programId, final byte[] feedId) {
    return findProgramAddress(List.of("pyth_pull".getBytes(UTF_8), feedId), programId);
  }

  private DriftPDAs() {
  }
}
