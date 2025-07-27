package software.sava.anchor.programs.metadao.launchpad.anchor;

import java.util.List;

import software.sava.anchor.programs.metadao.launchpad.anchor.types.InitializeLaunchArgs;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.anchor.AnchorUtil.writeDiscriminator;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class LaunchpadProgram {

  public static final Discriminator INITIALIZE_LAUNCH_DISCRIMINATOR = toDiscriminator(90, 201, 220, 142, 112, 253, 100, 13);

  public static Instruction initializeLaunch(final AccountMeta invokedLaunchpadProgramMeta,
                                             final PublicKey launchKey,
                                             final PublicKey baseMintKey,
                                             final PublicKey tokenMetadataKey,
                                             final PublicKey launchSignerKey,
                                             final PublicKey quoteVaultKey,
                                             final PublicKey baseVaultKey,
                                             final PublicKey payerKey,
                                             final PublicKey launchAuthorityKey,
                                             final PublicKey quoteMintKey,
                                             final PublicKey rentKey,
                                             final PublicKey tokenProgramKey,
                                             final PublicKey associatedTokenProgramKey,
                                             final PublicKey systemProgramKey,
                                             final PublicKey tokenMetadataProgramKey,
                                             final PublicKey eventAuthorityKey,
                                             final PublicKey programKey,
                                             final InitializeLaunchArgs args) {
    final var keys = List.of(
      createWrite(launchKey),
      createWrite(baseMintKey),
      createWrite(tokenMetadataKey),
      createRead(launchSignerKey),
      createWrite(quoteVaultKey),
      createWrite(baseVaultKey),
      createWritableSigner(payerKey),
      createRead(launchAuthorityKey),
      createRead(quoteMintKey),
      createRead(rentKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(tokenMetadataProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(args)];
    int i = writeDiscriminator(INITIALIZE_LAUNCH_DISCRIMINATOR, _data, 0);
    Borsh.write(args, _data, i);

    return Instruction.createInstruction(invokedLaunchpadProgramMeta, keys, _data);
  }

  public record InitializeLaunchIxData(Discriminator discriminator, InitializeLaunchArgs args) implements Borsh {  

    public static InitializeLaunchIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static InitializeLaunchIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var args = InitializeLaunchArgs.read(_data, i);
      return new InitializeLaunchIxData(discriminator, args);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(args, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(args);
    }
  }

  public static final Discriminator START_LAUNCH_DISCRIMINATOR = toDiscriminator(205, 178, 36, 232, 95, 202, 241, 68);

  public static Instruction startLaunch(final AccountMeta invokedLaunchpadProgramMeta,
                                        final PublicKey launchKey,
                                        final PublicKey launchAuthorityKey,
                                        final PublicKey eventAuthorityKey,
                                        final PublicKey programKey) {
    final var keys = List.of(
      createWrite(launchKey),
      createReadOnlySigner(launchAuthorityKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedLaunchpadProgramMeta, keys, START_LAUNCH_DISCRIMINATOR);
  }

  public static final Discriminator FUND_DISCRIMINATOR = toDiscriminator(218, 188, 111, 221, 152, 113, 174, 7);

  public static Instruction fund(final AccountMeta invokedLaunchpadProgramMeta,
                                 final PublicKey launchKey,
                                 final PublicKey fundingRecordKey,
                                 final PublicKey launchSignerKey,
                                 final PublicKey launchQuoteVaultKey,
                                 final PublicKey funderKey,
                                 final PublicKey payerKey,
                                 final PublicKey funderQuoteAccountKey,
                                 final PublicKey tokenProgramKey,
                                 final PublicKey systemProgramKey,
                                 final PublicKey eventAuthorityKey,
                                 final PublicKey programKey,
                                 final long amount) {
    final var keys = List.of(
      createWrite(launchKey),
      createWrite(fundingRecordKey),
      createRead(launchSignerKey),
      createWrite(launchQuoteVaultKey),
      createReadOnlySigner(funderKey),
      createWritableSigner(payerKey),
      createWrite(funderQuoteAccountKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(FUND_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedLaunchpadProgramMeta, keys, _data);
  }

  public record FundIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static FundIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static FundIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new FundIxData(discriminator, amount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator COMPLETE_LAUNCH_DISCRIMINATOR = toDiscriminator(118, 207, 250, 130, 44, 148, 251, 237);

  public static Instruction completeLaunch(final AccountMeta invokedLaunchpadProgramMeta,
                                           final PublicKey launchKey,
                                           final PublicKey tokenMetadataKey,
                                           final PublicKey payerKey,
                                           final PublicKey launchSignerKey,
                                           final PublicKey launchQuoteVaultKey,
                                           final PublicKey launchBaseVaultKey,
                                           final PublicKey treasuryQuoteAccountKey,
                                           final PublicKey treasuryLpAccountKey,
                                           final PublicKey poolStateKey,
                                           final PublicKey baseMintKey,
                                           final PublicKey quoteMintKey,
                                           final PublicKey lpMintKey,
                                           final PublicKey lpVaultKey,
                                           final PublicKey poolTokenVaultKey,
                                           final PublicKey poolUsdcVaultKey,
                                           final PublicKey observationStateKey,
                                           final PublicKey daoKey,
                                           final PublicKey squadsMultisigKey,
                                           final PublicKey squadsMultisigVaultKey,
                                           final PublicKey spendingLimitKey,
                                           final PublicKey systemProgramKey,
                                           final PublicKey tokenProgramKey,
                                           final PublicKey associatedTokenProgramKey,
                                           final PublicKey staticAccountsAuthorityKey,
                                           // Use the lowest fee pool, can see fees at https://api-v3.raydium.io/main/cpmm-config
                                           final PublicKey staticAccountsAmmConfigKey,
                                           // create pool fee account
                                           final PublicKey staticAccountsCreatePoolFeeKey,
                                           final PublicKey staticAccountsCpSwapProgramKey,
                                           final PublicKey staticAccountsAutocratProgramKey,
                                           final PublicKey staticAccountsTokenMetadataProgramKey,
                                           final PublicKey staticAccountsAutocratEventAuthorityKey,
                                           final PublicKey staticAccountsRentKey,
                                           final PublicKey staticAccountsSquadsProgramKey,
                                           final PublicKey staticAccountsSquadsProgramConfigKey,
                                           final PublicKey staticAccountsSquadsProgramConfigTreasuryKey,
                                           final PublicKey eventAuthorityKey,
                                           final PublicKey programKey) {
    final var keys = List.of(
      createWrite(launchKey),
      createWrite(tokenMetadataKey),
      createWritableSigner(payerKey),
      createWrite(launchSignerKey),
      createWrite(launchQuoteVaultKey),
      createWrite(launchBaseVaultKey),
      createWrite(treasuryQuoteAccountKey),
      createWrite(treasuryLpAccountKey),
      createWrite(poolStateKey),
      createWrite(baseMintKey),
      createRead(quoteMintKey),
      createWrite(lpMintKey),
      createWrite(lpVaultKey),
      createWrite(poolTokenVaultKey),
      createWrite(poolUsdcVaultKey),
      createWrite(observationStateKey),
      createWrite(daoKey),
      createWrite(squadsMultisigKey),
      createRead(squadsMultisigVaultKey),
      createWrite(spendingLimitKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(staticAccountsAuthorityKey),
      createWrite(staticAccountsAmmConfigKey),
      createWrite(staticAccountsCreatePoolFeeKey),
      createRead(staticAccountsCpSwapProgramKey),
      createRead(staticAccountsAutocratProgramKey),
      createRead(staticAccountsTokenMetadataProgramKey),
      createRead(staticAccountsAutocratEventAuthorityKey),
      createRead(staticAccountsRentKey),
      createRead(staticAccountsSquadsProgramKey),
      createRead(staticAccountsSquadsProgramConfigKey),
      createWrite(staticAccountsSquadsProgramConfigTreasuryKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedLaunchpadProgramMeta, keys, COMPLETE_LAUNCH_DISCRIMINATOR);
  }

  public static final Discriminator REFUND_DISCRIMINATOR = toDiscriminator(2, 96, 183, 251, 63, 208, 46, 46);

  public static Instruction refund(final AccountMeta invokedLaunchpadProgramMeta,
                                   final PublicKey launchKey,
                                   final PublicKey fundingRecordKey,
                                   final PublicKey launchQuoteVaultKey,
                                   final PublicKey launchSignerKey,
                                   final PublicKey funderKey,
                                   final PublicKey funderQuoteAccountKey,
                                   final PublicKey tokenProgramKey,
                                   final PublicKey systemProgramKey,
                                   final PublicKey eventAuthorityKey,
                                   final PublicKey programKey) {
    final var keys = List.of(
      createWrite(launchKey),
      createWrite(fundingRecordKey),
      createWrite(launchQuoteVaultKey),
      createRead(launchSignerKey),
      createWritableSigner(funderKey),
      createWrite(funderQuoteAccountKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedLaunchpadProgramMeta, keys, REFUND_DISCRIMINATOR);
  }

  public static final Discriminator CLAIM_DISCRIMINATOR = toDiscriminator(62, 198, 214, 193, 213, 159, 108, 210);

  public static Instruction claim(final AccountMeta invokedLaunchpadProgramMeta,
                                  final PublicKey launchKey,
                                  final PublicKey fundingRecordKey,
                                  final PublicKey launchSignerKey,
                                  final PublicKey baseMintKey,
                                  final PublicKey launchBaseVaultKey,
                                  final PublicKey funderKey,
                                  final PublicKey payerKey,
                                  final PublicKey funderTokenAccountKey,
                                  final PublicKey tokenProgramKey,
                                  final PublicKey systemProgramKey,
                                  final PublicKey eventAuthorityKey,
                                  final PublicKey programKey) {
    final var keys = List.of(
      createWrite(launchKey),
      createWrite(fundingRecordKey),
      createRead(launchSignerKey),
      createWrite(baseMintKey),
      createWrite(launchBaseVaultKey),
      createRead(funderKey),
      createWritableSigner(payerKey),
      createWrite(funderTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedLaunchpadProgramMeta, keys, CLAIM_DISCRIMINATOR);
  }

  private LaunchpadProgram() {
  }
}
