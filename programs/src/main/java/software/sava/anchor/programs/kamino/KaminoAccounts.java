package software.sava.anchor.programs.kamino;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

import java.util.List;

import static java.nio.charset.StandardCharsets.US_ASCII;

public interface KaminoAccounts {

  // https://github.com/Kamino-Finance/klend-sdk/blob/master/src/utils/seeds.ts
  // https://github.com/Kamino-Finance/klend/blob/master/programs/klend/src/utils/seeds.rs

  static final KaminoAccounts MAIN_NET = createAccounts(
      "KLend2g3cP87fffoy8q1mQqGKjrxjC8boSyAYavgmjD",
      "3NJYftD5sjVfxSnUdZ1wVML8f3aC6mp1CXCL6L7TnU8C",
      "FarmsPZpWu9i7Kky8tPN37rs2TpmMrAZrC7S7vJa91Hr"
  );

  static KaminoAccounts createAccounts(final PublicKey program,
                                       final PublicKey scopePrices,
                                       final PublicKey farmProgram) {
    return new KaminoAccountsRecord(
        AccountMeta.createInvoked(program),
        scopePrices,
        farmProgram
    );
  }

  static KaminoAccounts createAccounts(final String program,
                                       final String scopePrices,
                                       final String farmProgram) {
    return createAccounts(
        PublicKey.fromBase58Encoded(program),
        PublicKey.fromBase58Encoded(scopePrices),
        PublicKey.fromBase58Encoded(farmProgram)
    );
  }

  static ProgramDerivedAddress lendingMarketAuthPda(final PublicKey lendingMarket,
                                                    final PublicKey programId) {

    return PublicKey.findProgramAddress(
        List.of(
            "lma".getBytes(US_ASCII),
            lendingMarket.toByteArray()
        ),
        programId
    );
  }

  static ProgramDerivedAddress reserveLiqSupplyPda(final PublicKey lendingMarket,
                                                   final PublicKey collateralMint,
                                                   final PublicKey programId) {

    return PublicKey.findProgramAddress(
        List.of(
            "reserve_liq_supply".getBytes(US_ASCII),
            lendingMarket.toByteArray(),
            collateralMint.toByteArray()
        ),
        programId
    );
  }

  static ProgramDerivedAddress reserveFeeVaultPda(final PublicKey lendingMarket,
                                                  final PublicKey collateralMint,
                                                  final PublicKey programId) {

    return PublicKey.findProgramAddress(
        List.of(
            "fee_receiver".getBytes(US_ASCII),
            lendingMarket.toByteArray(),
            collateralMint.toByteArray()
        ),
        programId
    );
  }

  static ProgramDerivedAddress reserveCollateralMintPda(final PublicKey lendingMarket,
                                                        final PublicKey collateralMint,
                                                        final PublicKey programId) {

    return PublicKey.findProgramAddress(
        List.of(
            "reserve_coll_mint".getBytes(US_ASCII),
            lendingMarket.toByteArray(),
            collateralMint.toByteArray()
        ),
        programId
    );
  }

  static ProgramDerivedAddress reserveCollateralSupplyPda(final PublicKey lendingMarket,
                                                          final PublicKey collateralMint,
                                                          final PublicKey programId) {

    return PublicKey.findProgramAddress(
        List.of(
            "reserve_coll_supply".getBytes(US_ASCII),
            lendingMarket.toByteArray(),
            collateralMint.toByteArray()
        ),
        programId
    );
  }

  static ProgramDerivedAddress userMetadataPda(final PublicKey user,
                                               final PublicKey programId) {

    return PublicKey.findProgramAddress(
        List.of(
            "user_meta".getBytes(US_ASCII),
            user.toByteArray()
        ),
        programId
    );
  }

  static ProgramDerivedAddress referrerTokenStatePda(final PublicKey referrer,
                                                     final PublicKey reserve,
                                                     final PublicKey programId) {
    if (referrer.equals(PublicKey.NONE)) {
      return null;
    }
    return PublicKey.findProgramAddress(
        List.of(
            "referrer_acc".getBytes(US_ASCII),
            referrer.toByteArray(),
            reserve.toByteArray()
        ),
        programId
    );
  }

  static ProgramDerivedAddress referrerStatePda(final PublicKey referrer,
                                                final PublicKey programId) {

    return PublicKey.findProgramAddress(
        List.of(
            "ref_state".getBytes(US_ASCII),
            referrer.toByteArray()
        ),
        programId
    );
  }

  static ProgramDerivedAddress shortUrlPda(final PublicKey shortUrl,
                                           final PublicKey programId) {

    return PublicKey.findProgramAddress(
        List.of(
            "short_url".getBytes(US_ASCII),
            shortUrl.toByteArray()
        ),
        programId
    );
  }

  AccountMeta invokedProgram();

  default PublicKey program() {
    return invokedProgram().publicKey();
  }

  PublicKey scopePrices();

  PublicKey farmProgram();
}
