package software.sava.anchor.programs.kamino;

import software.sava.anchor.programs.kamino.lend.MarketPDAs;
import software.sava.anchor.programs.kamino.lend.ReservePDAs;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;

import java.util.List;

import static java.nio.charset.StandardCharsets.US_ASCII;

public interface KaminoAccounts {

  // https://github.com/Kamino-Finance/klend-sdk/blob/master/src/utils/seeds.ts
  // https://github.com/Kamino-Finance/klend/blob/master/programs/klend/src/utils/seeds.rs

  KaminoAccounts MAIN_NET = createAccounts(
      "KLend2g3cP87fffoy8q1mQqGKjrxjC8boSyAYavgmjD",
      "3NJYftD5sjVfxSnUdZ1wVML8f3aC6mp1CXCL6L7TnU8C",
      "FarmsPZpWu9i7Kky8tPN37rs2TpmMrAZrC7S7vJa91Hr",
      "KvauGMspG5k6rtzrqqn7WNn3oZdyKqLKwK2XWQ8FLjd"
  );

  static KaminoAccounts createAccounts(final PublicKey kLendProgram,
                                       final PublicKey scopePrices,
                                       final PublicKey farmProgram,
                                       final PublicKey kVaultsProgram) {
    final var kVaultsEventAuthority = PublicKey.findProgramAddress(
        List.of("__event_authority".getBytes(US_ASCII)),
        kVaultsProgram
    ).publicKey();
    return new KaminoAccountsRecord(
        AccountMeta.createInvoked(kLendProgram),
        scopePrices,
        farmProgram,
        AccountMeta.createInvoked(kVaultsProgram),
        kVaultsEventAuthority
    );
  }

  static KaminoAccounts createAccounts(final String kLendProgram,
                                       final String scopePrices,
                                       final String farmProgram,
                                       final String kVaultsProgram) {
    return createAccounts(
        PublicKey.fromBase58Encoded(kLendProgram),
        PublicKey.fromBase58Encoded(scopePrices),
        PublicKey.fromBase58Encoded(farmProgram),
        PublicKey.fromBase58Encoded(kVaultsProgram)
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

  default ProgramDerivedAddress lendingMarketAuthPda(final PublicKey lendingMarket) {
    return lendingMarketAuthPda(lendingMarket, kLendProgram());
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

  default ProgramDerivedAddress reserveLiqSupplyPda(final PublicKey lendingMarket, final PublicKey collateralMint) {
    return reserveLiqSupplyPda(lendingMarket, collateralMint, kLendProgram());
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

  default ProgramDerivedAddress reserveFeeVaultPda(final PublicKey lendingMarket, final PublicKey collateralMint) {
    return reserveFeeVaultPda(lendingMarket, collateralMint, kLendProgram());
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

  default ProgramDerivedAddress reserveCollateralMintPda(final PublicKey lendingMarket,
                                                         final PublicKey collateralMint) {
    return reserveCollateralMintPda(lendingMarket, collateralMint, kLendProgram());
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

  default ProgramDerivedAddress reserveCollateralSupplyPda(final PublicKey lendingMarket,
                                                           final PublicKey collateralMint) {
    return reserveCollateralSupplyPda(lendingMarket, collateralMint, kLendProgram());
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

  default ProgramDerivedAddress userMetadataPda(final PublicKey user) {
    return userMetadataPda(user, kLendProgram());
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

  default ProgramDerivedAddress referrerTokenStatePda(final PublicKey referrer, final PublicKey reserve) {
    return referrerTokenStatePda(referrer, reserve, kLendProgram());
  }

  static ProgramDerivedAddress referrerStatePda(final PublicKey referrer, final PublicKey programId) {

    return PublicKey.findProgramAddress(
        List.of(
            "ref_state".getBytes(US_ASCII),
            referrer.toByteArray()
        ),
        programId
    );
  }

  default ProgramDerivedAddress referrerStatePda(final PublicKey referrer) {
    return referrerStatePda(referrer, kLendProgram());
  }

  static ProgramDerivedAddress shortUrlPda(final String shortUrl, final PublicKey programId) {

    return PublicKey.findProgramAddress(
        List.of(
            "short_url".getBytes(US_ASCII),
            shortUrl.getBytes(US_ASCII)
        ),
        programId
    );
  }

  default ProgramDerivedAddress shortUrlPda(final String shortUrl) {
    return shortUrlPda(shortUrl, kLendProgram());
  }

  default ReservePDAs createReservePDAs(final MarketPDAs marketPDAs,
                                        final PublicKey mint,
                                        final PublicKey tokenProgram) {
    return ReservePDAs.createPDAs(
        kLendProgram(),
        marketPDAs,
        mint,
        tokenProgram
    );
  }

  AccountMeta invokedKLendProgram();

  default PublicKey kLendProgram() {
    return invokedKLendProgram().publicKey();
  }

  PublicKey scopePrices();

  PublicKey farmProgram();

  AccountMeta invokedKVaultsProgram();

  default PublicKey kVaultsProgram() {
    return invokedKLendProgram().publicKey();
  }

  PublicKey kVaultsEventAuthority();
}
