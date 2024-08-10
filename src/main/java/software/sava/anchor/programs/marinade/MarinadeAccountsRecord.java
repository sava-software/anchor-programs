package software.sava.anchor.programs.marinade;

import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.accounts.PublicKey;

record MarinadeAccountsRecord(PublicKey mSolTokenMint,
                              PublicKey mSolTokenMintAuthorityPDA,
                              AccountMeta writeMSolTokenMint,
                              PublicKey marinadeProgram,
                              AccountMeta invokedMarinadeProgram,
                              PublicKey stateProgram,
                              AccountMeta writeStateProgram,
                              // Treasury
                              PublicKey treasuryReserveSolPDA,
                              AccountMeta writeReserveSolPDA,
                              PublicKey treasuryMSolAccount,
                              // Liquidity Pool
                              PublicKey liquidityPoolMSolSolMint,
                              PublicKey liquidityPoolAuthPDA,
                              PublicKey liquidityPoolMSolLegAccount,
                              PublicKey liquidityPoolMSolLegAuthority,
                              PublicKey liquidityPoolSolLegAccount) implements MarinadeAccounts {
}
