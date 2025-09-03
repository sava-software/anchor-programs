package software.sava.anchor.programs.kamino;

import software.sava.anchor.programs.kamino.lend.MarketPDAs;
import software.sava.anchor.programs.kamino.lend.ReservePDAs;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.encoding.ByteUtil;

import java.util.List;

import static java.nio.charset.StandardCharsets.US_ASCII;

public interface KaminoAccounts {

  // https://github.com/Kamino-Finance/klend-sdk/blob/master/src/utils/seeds.ts
  // https://github.com/Kamino-Finance/klend/blob/master/programs/klend/src/utils/seeds.rs

  KaminoAccounts MAIN_NET = createAccounts(
      "KLend2g3cP87fffoy8q1mQqGKjrxjC8boSyAYavgmjD",
      "HFn8GnPADiny6XqUoWE8uRPPxb29ikn4yTuPa9MF2fWJ",
      "FarmsPZpWu9i7Kky8tPN37rs2TpmMrAZrC7S7vJa91Hr",
      "KvauGMspG5k6rtzrqqn7WNn3oZdyKqLKwK2XWQ8FLjd"
  );

  static KaminoAccounts createAccounts(final PublicKey kLendProgram,
                                       final PublicKey scopePricesProgram,
                                       final PublicKey farmProgram,
                                       final PublicKey kVaultsProgram) {
    final var kVaultsEventAuthority = PublicKey.findProgramAddress(
        List.of("__event_authority".getBytes(US_ASCII)),
        kVaultsProgram
    ).publicKey();
    return new KaminoAccountsRecord(
        AccountMeta.createInvoked(kLendProgram),
        scopePricesProgram,
        ScopeFeedAccounts.SCOPE_MAINNET_HUBBLE_FEED,
        ScopeFeedAccounts.SCOPE_MAINNET_KLEND_FEED,
        farmProgram,
        AccountMeta.createInvoked(kVaultsProgram),
        kVaultsEventAuthority
    );
  }

  static KaminoAccounts createAccounts(final String kLendProgram,
                                       final String scopePricesProgram,
                                       final String farmProgram,
                                       final String kVaultsProgram) {
    return createAccounts(
        PublicKey.fromBase58Encoded(kLendProgram),
        PublicKey.fromBase58Encoded(scopePricesProgram),
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

  static ProgramDerivedAddress cTokenVault(final PublicKey vaultKey,
                                           final PublicKey reserveKey,
                                           final PublicKey programId) {

    return PublicKey.findProgramAddress(
        List.of(
            "ctoken_vault".getBytes(US_ASCII),
            vaultKey.toByteArray(),
            reserveKey.toByteArray()
        ),
        programId
    );
  }

  AccountMeta invokedKLendProgram();

  default PublicKey kLendProgram() {
    return invokedKLendProgram().publicKey();
  }

  PublicKey farmProgram();

  AccountMeta invokedKVaultsProgram();

  default PublicKey kVaultsProgram() {
    return invokedKVaultsProgram().publicKey();
  }

  PublicKey kVaultsEventAuthority();

  default ProgramDerivedAddress cTokenVault(final PublicKey vaultKey, final PublicKey reserveKey) {
    return cTokenVault(vaultKey, reserveKey, kVaultsProgram());
  }

  PublicKey scopePricesProgram();

  default AccountMeta invokedScopePricesProgram() {
    return AccountMeta.createInvoked(scopePricesProgram());
  }

  ScopeFeedAccounts scopeMainnetHubbleFeed();

  ScopeFeedAccounts scopeMainnetKLendFeed();

  default PublicKey scopeEventAuthority() {
    return PublicKey.findProgramAddress(
        List.of("__event_authority".getBytes(US_ASCII)),
        scopePricesProgram()
    ).publicKey();
  }

  static ProgramDerivedAddress mintsToScopeChain(final PublicKey scopeOraclePrices,
                                                 final PublicKey seedKey,
                                                 final long seedId,
                                                 final PublicKey programId) {
    final byte[] seed = new byte[Long.BYTES];
    ByteUtil.putInt64LE(seed, 0, seedId);
    return PublicKey.findProgramAddress(
        List.of(
            "mints_to_scope_chains".getBytes(US_ASCII),
            scopeOraclePrices.toByteArray(),
            seedKey.toByteArray(),
            seed
        ),
        programId
    );
  }

  default ProgramDerivedAddress mintsToScopeChain(final PublicKey scopeOraclePrices,
                                                  final PublicKey seedKey,
                                                  final long seedId) {
    return mintsToScopeChain(scopeOraclePrices, seedKey, seedId, scopePricesProgram());
  }

  static ProgramDerivedAddress scopeFeedConfiguration(final String feedName, final PublicKey programId) {
    return PublicKey.findProgramAddress(
        List.of(
            "conf".getBytes(US_ASCII),
            feedName.getBytes(US_ASCII)
        ),
        programId
    );
  }

  default ProgramDerivedAddress scopeFeedConfiguration(final String feedName) {
    return scopeFeedConfiguration(feedName, scopePricesProgram());
  }
}
