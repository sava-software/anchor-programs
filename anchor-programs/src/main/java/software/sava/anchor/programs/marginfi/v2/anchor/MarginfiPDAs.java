package software.sava.anchor.programs.marginfi.v2.anchor;

import java.util.List;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

import static java.nio.charset.StandardCharsets.US_ASCII;

public final class MarginfiPDAs {

  public static ProgramDerivedAddress bankPDA(final PublicKey program,
                                              final PublicKey marginfiGroupAccount,
                                              final PublicKey bankMintAccount,
                                              final byte[] bankSeed) {
    return PublicKey.findProgramAddress(List.of(
      marginfiGroupAccount.toByteArray(),
      bankMintAccount.toByteArray(),
      bankSeed
    ), program);
  }

  public static ProgramDerivedAddress bankInsuranceVaultPDA(final PublicKey program,
                                                            final PublicKey liabBankAccount) {
    return PublicKey.findProgramAddress(List.of(
      "insurance_vault".getBytes(US_ASCII),
      liabBankAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress bankLiquidityVaultPDA(final PublicKey program,
                                                            final PublicKey liabBankAccount) {
    return PublicKey.findProgramAddress(List.of(
      "liquidity_vault".getBytes(US_ASCII),
      liabBankAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress bankLiquidityVaultAuthorityPDA(final PublicKey program,
                                                                     final PublicKey bankAccount) {
    return PublicKey.findProgramAddress(List.of(
      "liquidity_vault_auth".getBytes(US_ASCII),
      bankAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress bankLiquidityVaultAuthority1PDA(final PublicKey program,
                                                                      final PublicKey liabBankAccount) {
    return PublicKey.findProgramAddress(List.of(
      "liquidity_vault_auth".getBytes(US_ASCII),
      liabBankAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress emissionsAuthPDA(final PublicKey program,
                                                       final PublicKey bankAccount,
                                                       final PublicKey emissionsMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      "emissions_auth_seed".getBytes(US_ASCII),
      bankAccount.toByteArray(),
      emissionsMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress emissionsTokenAccountPDA(final PublicKey program,
                                                               final PublicKey bankAccount,
                                                               final PublicKey emissionsMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      "emissions_token_account_seed".getBytes(US_ASCII),
      bankAccount.toByteArray(),
      emissionsMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress emissionsVaultPDA(final PublicKey program,
                                                        final PublicKey bankAccount,
                                                        final PublicKey emissionsMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      "emissions_token_account_seed".getBytes(US_ASCII),
      bankAccount.toByteArray(),
      emissionsMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress feeStatePDA(final PublicKey program) {
    return PublicKey.findProgramAddress(List.of(
      "feestate".getBytes(US_ASCII)
    ), program);
  }

  public static ProgramDerivedAddress feeVaultPDA(final PublicKey program,
                                                  final PublicKey bankAccount) {
    return PublicKey.findProgramAddress(List.of(
      "fee_vault".getBytes(US_ASCII),
      bankAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress feeVaultAuthorityPDA(final PublicKey program,
                                                           final PublicKey bankAccount) {
    return PublicKey.findProgramAddress(List.of(
      "fee_vault_auth".getBytes(US_ASCII),
      bankAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress insuranceVaultPDA(final PublicKey program,
                                                        final PublicKey bankAccount) {
    return PublicKey.findProgramAddress(List.of(
      "insurance_vault".getBytes(US_ASCII),
      bankAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress insuranceVaultAuthorityPDA(final PublicKey program,
                                                                 final PublicKey bankAccount) {
    return PublicKey.findProgramAddress(List.of(
      "insurance_vault_auth".getBytes(US_ASCII),
      bankAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress liquidityVaultPDA(final PublicKey program,
                                                        final PublicKey bankAccount) {
    return PublicKey.findProgramAddress(List.of(
      "liquidity_vault".getBytes(US_ASCII),
      bankAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress liquidityVaultAuthorityPDA(final PublicKey program,
                                                                 final PublicKey bankAccount) {
    return PublicKey.findProgramAddress(List.of(
      "liquidity_vault_auth".getBytes(US_ASCII),
      bankAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress stakedSettingsPDA(final PublicKey program,
                                                        final PublicKey marginfiGroupAccount) {
    return PublicKey.findProgramAddress(List.of(
      "staked_settings".getBytes(US_ASCII),
      marginfiGroupAccount.toByteArray()
    ), program);
  }

  private MarginfiPDAs() {
  }
}
