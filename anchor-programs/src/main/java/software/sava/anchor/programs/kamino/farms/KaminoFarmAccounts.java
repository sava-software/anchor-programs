package software.sava.anchor.programs.kamino.farms;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

import java.util.List;

import static java.nio.charset.StandardCharsets.US_ASCII;

public interface KaminoFarmAccounts {

  static ProgramDerivedAddress treasuryVaultPDA(final PublicKey globalConfig,
                                                final PublicKey rewardMint,
                                                final PublicKey programId) {

    return PublicKey.findProgramAddress(
        List.of(
            "tvault".getBytes(US_ASCII),
            globalConfig.toByteArray(),
            rewardMint.toByteArray()
        ),
        programId
    );
  }

  static ProgramDerivedAddress treasuryAuthorityPDA(final PublicKey globalConfig,
                                                    final PublicKey programId) {

    return PublicKey.findProgramAddress(
        List.of(
            "authority".getBytes(US_ASCII),
            globalConfig.toByteArray()
        ),
        programId
    );
  }

  static ProgramDerivedAddress farmVaultPDA(final PublicKey farmState,
                                            final PublicKey tokenMint,
                                            final PublicKey programId) {

    return PublicKey.findProgramAddress(
        List.of(
            "fvault".getBytes(US_ASCII),
            farmState.toByteArray(),
            tokenMint.toByteArray()
        ),
        programId
    );
  }

  static ProgramDerivedAddress getFarmAuthorityPDA(final PublicKey farmState,
                                                   final PublicKey programId) {

    return PublicKey.findProgramAddress(
        List.of(
            "authority".getBytes(US_ASCII),
            farmState.toByteArray()
        ),
        programId
    );
  }

  static ProgramDerivedAddress rewardVaultPDA(final PublicKey farmState,
                                              final PublicKey rewardMint,
                                              final PublicKey programId) {

    return PublicKey.findProgramAddress(
        List.of(
            "rvault".getBytes(US_ASCII),
            farmState.toByteArray(),
            rewardMint.toByteArray()
        ),
        programId
    );
  }

  static ProgramDerivedAddress userStatePDA(final PublicKey farmState,
                                            final PublicKey owner,
                                            final PublicKey programId) {

    return PublicKey.findProgramAddress(
        List.of(
            "user".getBytes(US_ASCII),
            farmState.toByteArray(),
            owner.toByteArray()
        ),
        programId
    );
  }
}
