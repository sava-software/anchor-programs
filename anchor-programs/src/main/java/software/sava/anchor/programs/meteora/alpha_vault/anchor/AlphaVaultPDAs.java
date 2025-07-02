package software.sava.anchor.programs.meteora.alpha_vault.anchor;

import java.util.List;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

import static java.nio.charset.StandardCharsets.US_ASCII;

public final class AlphaVaultPDAs {

  public static ProgramDerivedAddress configPDA(final PublicKey program,
                                                final byte[] configParametersIndex) {
    return PublicKey.findProgramAddress(List.of(
      "fcfs_config".getBytes(US_ASCII),
      configParametersIndex
    ), program);
  }

  public static ProgramDerivedAddress config1PDA(final PublicKey program,
                                                 final byte[] configParametersIndex) {
    return PublicKey.findProgramAddress(List.of(
      "prorata_config".getBytes(US_ASCII),
      configParametersIndex
    ), program);
  }

  public static ProgramDerivedAddress crankFeeWhitelistPDA(final PublicKey program,
                                                           final PublicKey crankerAccount) {
    return PublicKey.findProgramAddress(List.of(
      "crank_fee_whitelist".getBytes(US_ASCII),
      crankerAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress escrowPDA(final PublicKey program,
                                                final PublicKey vaultAccount,
                                                final PublicKey ownerAccount) {
    return PublicKey.findProgramAddress(List.of(
      "escrow".getBytes(US_ASCII),
      vaultAccount.toByteArray(),
      ownerAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress eventAuthorityPDA(final PublicKey program) {
    return PublicKey.findProgramAddress(List.of(
      "__event_authority".getBytes(US_ASCII)
    ), program);
  }

  public static ProgramDerivedAddress merkleProofMetadataPDA(final PublicKey program,
                                                             final PublicKey vaultAccount) {
    return PublicKey.findProgramAddress(List.of(
      "merkle_proof_metadata".getBytes(US_ASCII),
      vaultAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress merkleRootConfigPDA(final PublicKey program,
                                                          final PublicKey vaultAccount,
                                                          final byte[] paramsVersion) {
    return PublicKey.findProgramAddress(List.of(
      "merkle_root".getBytes(US_ASCII),
      vaultAccount.toByteArray(),
      paramsVersion
    ), program);
  }

  public static ProgramDerivedAddress vaultPDA(final PublicKey program,
                                               final PublicKey baseAccount,
                                               final PublicKey poolAccount) {
    return PublicKey.findProgramAddress(List.of(
      "vault".getBytes(US_ASCII),
      baseAccount.toByteArray(),
      poolAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress vault1PDA(final PublicKey program,
                                                final PublicKey configAccount,
                                                final PublicKey poolAccount) {
    return PublicKey.findProgramAddress(List.of(
      "vault".getBytes(US_ASCII),
      configAccount.toByteArray(),
      poolAccount.toByteArray()
    ), program);
  }

  private AlphaVaultPDAs() {
  }
}
