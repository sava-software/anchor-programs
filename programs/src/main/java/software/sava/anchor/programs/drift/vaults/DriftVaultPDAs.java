package software.sava.anchor.programs.drift.vaults;

import software.sava.anchor.programs.drift.DriftPDAs;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static software.sava.core.accounts.PublicKey.findProgramAddress;

// https://github.com/drift-labs/drift-vaults/blob/master/ts/sdk/src/addresses.ts
public final class DriftVaultPDAs {

  public static ProgramDerivedAddress getVaultAddress(final PublicKey programId, final byte[] encodedName) {
    return findProgramAddress(
        List.of("vault".getBytes(UTF_8), encodedName),
        programId
    );
  }

  public static ProgramDerivedAddress getVaultDepositorAddress(final PublicKey programId,
                                                               final PublicKey vault,
                                                               final PublicKey authority) {
    return findProgramAddress(
        List.of("vault_depositor".getBytes(UTF_8), vault.toByteArray(), authority.toByteArray()),
        programId
    );
  }

  public static ProgramDerivedAddress getTokenVaultAddress(final PublicKey programId, final PublicKey vault) {
    return findProgramAddress(
        List.of("vault_token_account".getBytes(UTF_8), vault.toByteArray()),
        programId
    );
  }

  public static ProgramDerivedAddress getInsuranceFundTokenVaultAddress(final PublicKey programId,
                                                                        final PublicKey vault,
                                                                        final int marketIndex) {
    return findProgramAddress(
        List.of("vault_token_account".getBytes(UTF_8), vault.toByteArray(), DriftPDAs.lowerTwoLE(marketIndex)),
        programId
    );
  }

  public static ProgramDerivedAddress getVaultProtocolAddress(final PublicKey programId, final PublicKey vault) {
    return findProgramAddress(
        List.of("vault_protocol".getBytes(UTF_8), vault.toByteArray()),
        programId
    );
  }

  public static ProgramDerivedAddress getTokenizedVaultAddress(final PublicKey programId,
                                                               final PublicKey vault,
                                                               final long sharesBase) {
    return findProgramAddress(
        List.of("tokenized_vault_depositor".getBytes(UTF_8), vault.toByteArray(), Long.toString(sharesBase).getBytes(UTF_8)),
        programId
    );
  }

  public static ProgramDerivedAddress getTokenizedVaultMintAddress(final PublicKey programId,
                                                                   final PublicKey vault,
                                                                   final long sharesBase) {
    return findProgramAddress(
        List.of("mint".getBytes(UTF_8), vault.toByteArray(), Long.toString(sharesBase).getBytes(UTF_8)),
        programId
    );
  }

  public static ProgramDerivedAddress getFeeUpdateAddress(final PublicKey programId, final PublicKey vault) {
    return findProgramAddress(
        List.of("fee_update".getBytes(UTF_8), vault.toByteArray()),
        programId
    );
  }

  private DriftVaultPDAs() {
  }
}
