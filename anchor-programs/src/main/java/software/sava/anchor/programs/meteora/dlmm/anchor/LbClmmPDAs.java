package software.sava.anchor.programs.meteora.dlmm.anchor;

import java.util.List;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;

import static java.nio.charset.StandardCharsets.US_ASCII;

public final class LbClmmPDAs {

  public static ProgramDerivedAddress binArrayPDA(final PublicKey program,
                                                  final PublicKey lbPairAccount,
                                                  final byte[] index) {
    return PublicKey.findProgramAddress(List.of(
      "bin_array".getBytes(US_ASCII),
      lbPairAccount.toByteArray(),
      index
    ), program);
  }

  public static ProgramDerivedAddress binArrayBitmapExtensionPDA(final PublicKey program,
                                                                 final PublicKey lbPairAccount) {
    return PublicKey.findProgramAddress(List.of(
      "bitmap".getBytes(US_ASCII),
      lbPairAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress claimFeeOperatorPDA(final PublicKey program,
                                                          final PublicKey operatorAccount) {
    return PublicKey.findProgramAddress(List.of(
      "cf_operator".getBytes(US_ASCII),
      operatorAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress eventAuthorityPDA(final PublicKey program) {
    return PublicKey.findProgramAddress(List.of(
      "__event_authority".getBytes(US_ASCII)
    ), program);
  }

  public static ProgramDerivedAddress oraclePDA(final PublicKey program,
                                                final PublicKey lbPairAccount) {
    return PublicKey.findProgramAddress(List.of(
      "oracle".getBytes(US_ASCII),
      lbPairAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress positionPDA(final PublicKey program,
                                                  final PublicKey lbPairAccount,
                                                  final PublicKey baseAccount,
                                                  final byte[] lowerBinId,
                                                  final byte[] width) {
    return PublicKey.findProgramAddress(List.of(
      "position".getBytes(US_ASCII),
      lbPairAccount.toByteArray(),
      baseAccount.toByteArray(),
      lowerBinId,
      width
    ), program);
  }

  public static ProgramDerivedAddress presetParameterPDA(final PublicKey program,
                                                         final byte[] ixBinStep,
                                                         final byte[] ixBaseFactor) {
    return PublicKey.findProgramAddress(List.of(
      "preset_parameter".getBytes(US_ASCII),
      ixBinStep,
      ixBaseFactor
    ), program);
  }

  public static ProgramDerivedAddress presetParameter1PDA(final PublicKey program,
                                                          final byte[] ixIndex) {
    return PublicKey.findProgramAddress(List.of(
      "preset_parameter2".getBytes(US_ASCII),
      ixIndex
    ), program);
  }

  public static ProgramDerivedAddress receiverTokenXPDA(final PublicKey program,
                                                        final PublicKey _4EWqcx3aNZmMetCnxwLYwyNjan6XLGp3Ca2W316vrSjv,
                                                        final PublicKey tokenXProgramAccount,
                                                        final PublicKey tokenXMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      _4EWqcx3aNZmMetCnxwLYwyNjan6XLGp3Ca2W316vrSjv.toByteArray(),
      tokenXProgramAccount.toByteArray(),
      tokenXMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress receiverTokenYPDA(final PublicKey program,
                                                        final PublicKey _4EWqcx3aNZmMetCnxwLYwyNjan6XLGp3Ca2W316vrSjv,
                                                        final PublicKey tokenYProgramAccount,
                                                        final PublicKey tokenYMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      _4EWqcx3aNZmMetCnxwLYwyNjan6XLGp3Ca2W316vrSjv.toByteArray(),
      tokenYProgramAccount.toByteArray(),
      tokenYMintAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress reserveXPDA(final PublicKey program,
                                                  final PublicKey lbPairAccount,
                                                  final PublicKey tokenMintXAccount) {
    return PublicKey.findProgramAddress(List.of(
      lbPairAccount.toByteArray(),
      tokenMintXAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress reserveYPDA(final PublicKey program,
                                                  final PublicKey lbPairAccount,
                                                  final PublicKey tokenMintYAccount) {
    return PublicKey.findProgramAddress(List.of(
      lbPairAccount.toByteArray(),
      tokenMintYAccount.toByteArray()
    ), program);
  }

  public static ProgramDerivedAddress rewardVaultPDA(final PublicKey program,
                                                     final PublicKey lbPairAccount,
                                                     final byte[] rewardIndex) {
    return PublicKey.findProgramAddress(List.of(
      lbPairAccount.toByteArray(),
      rewardIndex
    ), program);
  }

  public static ProgramDerivedAddress tokenBadgePDA(final PublicKey program,
                                                    final PublicKey tokenMintAccount) {
    return PublicKey.findProgramAddress(List.of(
      "token_badge".getBytes(US_ASCII),
      tokenMintAccount.toByteArray()
    ), program);
  }

  private LbClmmPDAs() {
  }
}
