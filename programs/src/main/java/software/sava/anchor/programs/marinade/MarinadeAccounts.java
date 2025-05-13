package software.sava.anchor.programs.marinade;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

import java.util.List;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static software.sava.core.accounts.PublicKey.fromBase58Encoded;
import static software.sava.core.accounts.meta.AccountMeta.createInvoked;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;

public interface MarinadeAccounts {

  MarinadeAccounts MAIN_NET = createAddressConstants(
      "mSoLzYCxHdYgdzU16g5QSh3i5K3z3KZK7ytfqcJm7So",
      "3JLPCS1qM2zRw3Dp6V4hZnYHd4toMNPkNesXdX9tg6KM",
      "MarBmsSgKXdrN1egZf5sqe1TMai9K1rChYNDJgjq7aD",
      "8szGkuLTAux9XMgZ2vtY39jVSowEcpBfFfD8hXSEqdGC",
      "Du3Ysj1wKbxPKkuPPnvzQLQh8oMSVifs3jGZjJWXFmHN",
      "B1aLzaNMeFVAyQ6f3XbbUyKcH2YPHu2fqiEagmiF23VR",
      "LPmSozJJ8Jh69ut2WP3XmVohTjL4ipR18yiCzxrUmVj",
      "HZsepB79dnpvH6qfVgvMpS738EndHw3qSHo4Gv5WX1KA",
      "7GgPYjS5Dza89wV6FpZ23kUJRG5vbQ1GM25ezspYFSoE",
      "EyaSjUtSgo9aRD1f8LWXwdvkpDTmXAW54yoSHZRF14WL",
      "UefNb6z6yvArqe4cJHTXCqStRsKmWhGxnZzuHbikP5Q",
      "DwFYJNnhLmw19FBTrVaLWZ8SZJpxdPoSYVSJaio9tjbY"
  );

  PublicKey mSolTokenMint();

  PublicKey mSolTokenMintAuthorityPDA();

  AccountMeta writeMSolTokenMint();

  PublicKey marinadeProgram();

  AccountMeta invokedMarinadeProgram();

  PublicKey stateProgram();

  AccountMeta writeStateProgram();

  // Treasury

  PublicKey treasuryReserveSolPDA();

  AccountMeta writeReserveSolPDA();

  PublicKey treasuryMSolAccount();

  // Liquidity Pool

  PublicKey liquidityPoolMSolSolMint();

  PublicKey liquidityPoolAuthPDA();

  PublicKey liquidityPoolMSolLegAccount();

  PublicKey liquidityPoolMSolLegAuthority();

  PublicKey liquidityPoolSolLegAccount();

  PublicKey validatorListAccount();

  static MarinadeAccounts createAddressConstants(
      final PublicKey mSolTokenMint,
      final PublicKey mSolTokenMintAuthorityPDA,
      final PublicKey stakePoolProgram,
      final PublicKey stateProgram,
      final PublicKey reserveSolProgram,
      final PublicKey treasuryMSolAccount,
      final PublicKey mSolSolLPMint,
      final PublicKey lpAuthPDA,
      final PublicKey mSolLegAccount,
      final PublicKey mSolLegAuthority,
      final PublicKey solLegAccount,
      final PublicKey validatorListAccount) {
    return new MarinadeAccountsRecord(
        mSolTokenMint,
        mSolTokenMintAuthorityPDA,
        createWrite(mSolTokenMint),
        stakePoolProgram,
        createInvoked(stakePoolProgram),
        stateProgram,
        createWrite(stateProgram),
        reserveSolProgram,
        createWrite(reserveSolProgram),
        treasuryMSolAccount,
        mSolSolLPMint,
        lpAuthPDA,
        mSolLegAccount,
        mSolLegAuthority,
        solLegAccount,
        validatorListAccount
    );
  }

  static MarinadeAccounts createAddressConstants(
      final String mSolTokenMint,
      final String mSolTokenMintAuthorityPDA,
      final String stakePoolProgram,
      final String stateProgram,
      final String reserveSolProgram,
      final String treasuryMSolAccount,
      final String mSolSolLPMint,
      final String lpAuthPDA,
      final String mSolLegAccount,
      final String mSolLegAuthority,
      final String solLegAccount,
      final String validatorListAccount) {
    return createAddressConstants(
        fromBase58Encoded(mSolTokenMint),
        fromBase58Encoded(mSolTokenMintAuthorityPDA),
        fromBase58Encoded(stakePoolProgram),
        fromBase58Encoded(stateProgram),
        fromBase58Encoded(reserveSolProgram),
        fromBase58Encoded(treasuryMSolAccount),
        fromBase58Encoded(mSolSolLPMint),
        fromBase58Encoded(lpAuthPDA),
        fromBase58Encoded(mSolLegAccount),
        fromBase58Encoded(mSolLegAuthority),
        fromBase58Encoded(solLegAccount),
        fromBase58Encoded(validatorListAccount)
    );
  }

  static ProgramDerivedAddress deriveReserveAuthority(final PublicKey program, final PublicKey state) {
    return PublicKey.findProgramAddress(List.of(
            state.toByteArray(),
            "reserve".getBytes(US_ASCII)
        ), program
    );
  }

  default ProgramDerivedAddress deriveReserveAuthority() {
    return deriveReserveAuthority(marinadeProgram(), stateProgram());
  }

  static ProgramDerivedAddress deriveStakeMintAuthority(final PublicKey program, final PublicKey state) {
    return PublicKey.findProgramAddress(List.of(
            state.toByteArray(),
            "st_mint".getBytes(US_ASCII)
        ), program
    );
  }

  default ProgramDerivedAddress deriveStakeMintAuthority() {
    return deriveStakeMintAuthority(marinadeProgram(), stateProgram());
  }

  static ProgramDerivedAddress deriveStakeWithdrawAuthority(final PublicKey program, final PublicKey state) {
    return PublicKey.findProgramAddress(List.of(
            state.toByteArray(),
            "withdraw".getBytes(US_ASCII)
        ), program
    );
  }

  default ProgramDerivedAddress deriveStakeWithdrawAuthority() {
    return deriveStakeWithdrawAuthority(marinadeProgram(), stateProgram());
  }

  static ProgramDerivedAddress deriveStakeDepositAuthority(final PublicKey program, final PublicKey state) {
    return PublicKey.findProgramAddress(List.of(
            state.toByteArray(),
            "deposit".getBytes(US_ASCII)
        ), program
    );
  }

  default ProgramDerivedAddress deriveStakeDepositAuthority() {
    return deriveStakeDepositAuthority(marinadeProgram(), stateProgram());
  }
}
