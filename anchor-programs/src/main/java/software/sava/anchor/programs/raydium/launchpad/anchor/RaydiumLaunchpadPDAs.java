package software.sava.anchor.programs.raydium.launchpad.anchor;

import java.util.List;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

import static java.nio.charset.StandardCharsets.US_ASCII;

public final class RaydiumLaunchpadPDAs {

  public static ProgramDerivedAddress ammAuthorityPDA(final PublicKey ammProgram) {
    return PublicKey.findProgramAddress(List.of(
      "amm authority".getBytes(US_ASCII)
    ), ammProgram);
  }

  public static ProgramDerivedAddress ammBaseVaultPDA(final PublicKey ammProgram,
                                                      final PublicKey ammProgramAccount,
                                                      final PublicKey marketAccount) {
    return PublicKey.findProgramAddress(List.of(
      ammProgramAccount.toByteArray(),
      marketAccount.toByteArray(),
      "coin_vault_associated_seed".getBytes(US_ASCII)
    ), ammProgram);
  }

  public static ProgramDerivedAddress ammConfigPDA(final PublicKey ammProgram) {
    return PublicKey.findProgramAddress(List.of(
      "amm_config_account_seed".getBytes(US_ASCII)
    ), ammProgram);
  }

  public static ProgramDerivedAddress ammLpMintPDA(final PublicKey ammProgram,
                                                   final PublicKey ammProgramAccount,
                                                   final PublicKey marketAccount) {
    return PublicKey.findProgramAddress(List.of(
      ammProgramAccount.toByteArray(),
      marketAccount.toByteArray(),
      "lp_mint_associated_seed".getBytes(US_ASCII)
    ), ammProgram);
  }

  public static ProgramDerivedAddress ammOpenOrdersPDA(final PublicKey ammProgram,
                                                       final PublicKey ammProgramAccount,
                                                       final PublicKey marketAccount) {
    return PublicKey.findProgramAddress(List.of(
      ammProgramAccount.toByteArray(),
      marketAccount.toByteArray(),
      "open_order_associated_seed".getBytes(US_ASCII)
    ), ammProgram);
  }

  public static ProgramDerivedAddress ammPoolPDA(final PublicKey ammProgram,
                                                 final PublicKey ammProgramAccount,
                                                 final PublicKey marketAccount) {
    return PublicKey.findProgramAddress(List.of(
      ammProgramAccount.toByteArray(),
      marketAccount.toByteArray(),
      "amm_associated_seed".getBytes(US_ASCII)
    ), ammProgram);
  }

  public static ProgramDerivedAddress ammQuoteVaultPDA(final PublicKey ammProgram,
                                                       final PublicKey ammProgramAccount,
                                                       final PublicKey marketAccount) {
    return PublicKey.findProgramAddress(List.of(
      ammProgramAccount.toByteArray(),
      marketAccount.toByteArray(),
      "pc_vault_associated_seed".getBytes(US_ASCII)
    ), ammProgram);
  }

  public static ProgramDerivedAddress ammTargetOrdersPDA(final PublicKey ammProgram,
                                                         final PublicKey ammProgramAccount,
                                                         final PublicKey marketAccount) {
    return PublicKey.findProgramAddress(List.of(
      ammProgramAccount.toByteArray(),
      marketAccount.toByteArray(),
      "target_associated_seed".getBytes(US_ASCII)
    ), ammProgram);
  }

  public static ProgramDerivedAddress authorityPDA(final PublicKey program) {
    return PublicKey.findProgramAddress(List.of(
      "vault_auth_seed".getBytes(US_ASCII)
    ), program);
  }

  public static ProgramDerivedAddress baseVaultPDA(final PublicKey program,
                                                   final PublicKey poolStateAccount,
                                                   final PublicKey baseMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      "pool_vault".getBytes(US_ASCII),
      poolStateAccount.toByteArray(),
      baseMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress cpswapAuthorityPDA(final PublicKey cpswapProgram) {
    return PublicKey.findProgramAddress(List.of(
      "vault_and_lp_mint_auth_seed".getBytes(US_ASCII)
    ), cpswapProgram);
  }

  public static ProgramDerivedAddress cpswapBaseVaultPDA(final PublicKey cpswapProgram,
                                                         final PublicKey cpswapPoolAccount,
                                                         final PublicKey baseMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      "pool_vault".getBytes(US_ASCII),
      cpswapPoolAccount.toByteArray(),
      baseMintAccount.toByteArray()
    ), cpswapProgram);
  }

  public static ProgramDerivedAddress cpswapLpMintPDA(final PublicKey cpswapProgram,
                                                      final PublicKey cpswapPoolAccount) {
    return PublicKey.findProgramAddress(List.of(
      "pool_lp_mint".getBytes(US_ASCII),
      cpswapPoolAccount.toByteArray()
    ), cpswapProgram);
  }

  public static ProgramDerivedAddress cpswapObservationPDA(final PublicKey cpswapProgram,
                                                           final PublicKey cpswapPoolAccount) {
    return PublicKey.findProgramAddress(List.of(
      "observation".getBytes(US_ASCII),
      cpswapPoolAccount.toByteArray()
    ), cpswapProgram);
  }

  public static ProgramDerivedAddress cpswapQuoteVaultPDA(final PublicKey cpswapProgram,
                                                          final PublicKey cpswapPoolAccount,
                                                          final PublicKey quoteMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      "pool_vault".getBytes(US_ASCII),
      cpswapPoolAccount.toByteArray(),
      quoteMintAccount.toByteArray()
    ), cpswapProgram);
  }

  public static ProgramDerivedAddress creatorFeeVaultPDA(final PublicKey program,
                                                         final PublicKey creatorAccount,
                                                         final PublicKey quoteMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      creatorAccount.toByteArray(),
      quoteMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress eventAuthorityPDA(final PublicKey program) {
    return PublicKey.findProgramAddress(List.of(
      "__event_authority".getBytes(US_ASCII)
    ), program);
  }

  public static ProgramDerivedAddress feeVaultAuthorityPDA(final PublicKey program) {
    return PublicKey.findProgramAddress(List.of(
      "creator_fee_vault_auth_seed".getBytes(US_ASCII)
    ), program);
  }

  public static ProgramDerivedAddress feeVaultAuthority1PDA(final PublicKey program) {
    return PublicKey.findProgramAddress(List.of(
      "platform_fee_vault_auth_seed".getBytes(US_ASCII)
    ), program);
  }

  public static ProgramDerivedAddress globalConfigPDA(final PublicKey program,
                                                      final PublicKey quoteTokenMintAccount,
                                                      final byte[] curveType,
                                                      final byte[] index) {
    return PublicKey.findProgramAddress(List.of(
      "global_config".getBytes(US_ASCII),
      quoteTokenMintAccount.toByteArray(),
      curveType,
      index
    ), program);
  }

  public static ProgramDerivedAddress lockAuthorityPDA(final PublicKey lockProgram) {
    return PublicKey.findProgramAddress(List.of(
      "lock_cp_authority_seed".getBytes(US_ASCII)
    ), lockProgram);
  }

  public static ProgramDerivedAddress platformConfigPDA(final PublicKey program,
                                                        final PublicKey platformAdminAccount) {
    return PublicKey.findProgramAddress(List.of(
      "platform_config".getBytes(US_ASCII),
      platformAdminAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress platformFeeVaultPDA(final PublicKey program,
                                                          final PublicKey platformConfigAccount,
                                                          final PublicKey quoteMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      platformConfigAccount.toByteArray(),
      quoteMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress platformVestingRecordPDA(final PublicKey program,
                                                               final PublicKey poolStateAccount,
                                                               final PublicKey beneficiaryAccount) {
    return PublicKey.findProgramAddress(List.of(
      "pool_vesting".getBytes(US_ASCII),
      poolStateAccount.toByteArray(),
      beneficiaryAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress poolStatePDA(final PublicKey program,
                                                   final PublicKey baseMintAccount,
                                                   final PublicKey quoteMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      "pool".getBytes(US_ASCII),
      baseMintAccount.toByteArray(),
      quoteMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress quoteVaultPDA(final PublicKey program,
                                                    final PublicKey poolStateAccount,
                                                    final PublicKey quoteMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      "pool_vault".getBytes(US_ASCII),
      poolStateAccount.toByteArray(),
      quoteMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress recipientTokenAccountPDA(final PublicKey program,
                                                               final PublicKey creatorAccount,
                                                               final PublicKey tokenProgramAccount,
                                                               final PublicKey quoteMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      creatorAccount.toByteArray(),
      tokenProgramAccount.toByteArray(),
      quoteMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress recipientTokenAccount1PDA(final PublicKey program,
                                                                final PublicKey platformFeeWalletAccount,
                                                                final PublicKey tokenProgramAccount,
                                                                final PublicKey quoteMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      platformFeeWalletAccount.toByteArray(),
      tokenProgramAccount.toByteArray(),
      quoteMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress userBaseTokenPDA(final PublicKey program,
                                                       final PublicKey beneficiaryAccount,
                                                       final PublicKey baseTokenProgramAccount,
                                                       final PublicKey baseTokenMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      beneficiaryAccount.toByteArray(),
      baseTokenProgramAccount.toByteArray(),
      baseTokenMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress vestingRecordPDA(final PublicKey program,
                                                       final PublicKey poolStateAccount,
                                                       final PublicKey beneficiaryAccount) {
    return PublicKey.findProgramAddress(List.of(
      "pool_vesting".getBytes(US_ASCII),
      poolStateAccount.toByteArray(),
      beneficiaryAccount.toByteArray()
    ), program);
  }

  private RaydiumLaunchpadPDAs() {
  }
}
