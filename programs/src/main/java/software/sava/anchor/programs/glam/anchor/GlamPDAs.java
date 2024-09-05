package software.sava.anchor.programs.glam.anchor;

import java.util.Base64;
import java.util.List;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

import static java.nio.charset.StandardCharsets.UTF_8;

public final class GlamPDAs {

  public static ProgramDerivedAddress fundPDA(final PublicKey program,
                                              final PublicKey managerAccount,
                                              final byte[] fundModelCreated) {
    return PublicKey.findProgramAddress(List.of(
      "fund".getBytes(UTF_8),
      managerAccount.toByteArray(),
      fundModelCreated
    ), program);
  }

  public static ProgramDerivedAddress inputSignerAtaPDA(final PublicKey program,
                                                        final PublicKey signerAccount,
                                                        final PublicKey inputMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      signerAccount.toByteArray(),
      Base64.getDecoder().decode("Bt324ddloZPZy+FGzut5rBy0he1fWzeROoz1hX7/AKk="),
      inputMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress mintToPDA(final PublicKey program,
                                                final PublicKey treasuryAccount,
                                                final PublicKey msolMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      treasuryAccount.toByteArray(),
      Base64.getDecoder().decode("Bt324ddloZPZy+FGzut5rBy0he1fWzeROoz1hX7/AKk="),
      msolMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress mintTo1PDA(final PublicKey program,
                                                 final PublicKey treasuryAccount,
                                                 final PublicKey poolMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      treasuryAccount.toByteArray(),
      Base64.getDecoder().decode("Bt324ddloZPZy+FGzut5rBy0he1fWzeROoz1hX7/AKk="),
      poolMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress openfundsPDA(final PublicKey program,
                                                   final PublicKey fundAccount) {
    return PublicKey.findProgramAddress(List.of(
      "openfunds".getBytes(UTF_8),
      fundAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress outputTreasuryAtaPDA(final PublicKey program,
                                                           final PublicKey treasuryAccount,
                                                           final PublicKey outputMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      treasuryAccount.toByteArray(),
      Base64.getDecoder().decode("Bt324ddloZPZy+FGzut5rBy0he1fWzeROoz1hX7/AKk="),
      outputMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress signerShareAtaPDA(final PublicKey program,
                                                        final PublicKey signerAccount,
                                                        final PublicKey token2022ProgramAccount,
                                                        final PublicKey shareClassAccount) {
    return PublicKey.findProgramAddress(List.of(
      signerAccount.toByteArray(),
      token2022ProgramAccount.toByteArray(),
      shareClassAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress treasuryPDA(final PublicKey program,
                                                  final PublicKey fundAccount) {
    return PublicKey.findProgramAddress(List.of(
      "treasury".getBytes(UTF_8),
      fundAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress treasuryWsolAtaPDA(final PublicKey program,
                                                         final PublicKey treasuryAccount,
                                                         final PublicKey wsolMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      treasuryAccount.toByteArray(),
      Base64.getDecoder().decode("Bt324ddloZPZy+FGzut5rBy0he1fWzeROoz1hX7/AKk="),
      wsolMintAccount.toByteArray()
    ), program);
  }

  private GlamPDAs() {
  }
}