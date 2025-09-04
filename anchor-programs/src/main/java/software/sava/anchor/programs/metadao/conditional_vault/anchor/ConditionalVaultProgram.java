package software.sava.anchor.programs.metadao.conditional_vault.anchor;

import java.util.List;

import software.sava.anchor.programs.metadao.conditional_vault.anchor.types.AddMetadataToConditionalTokensArgs;
import software.sava.anchor.programs.metadao.conditional_vault.anchor.types.InitializeQuestionArgs;
import software.sava.anchor.programs.metadao.conditional_vault.anchor.types.ResolveQuestionArgs;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class ConditionalVaultProgram {

  public static final Discriminator INITIALIZE_QUESTION_DISCRIMINATOR = toDiscriminator(245, 151, 106, 188, 88, 44, 65, 212);

  public static Instruction initializeQuestion(final AccountMeta invokedConditionalVaultProgramMeta,
                                               final PublicKey questionKey,
                                               final PublicKey payerKey,
                                               final PublicKey systemProgramKey,
                                               final PublicKey eventAuthorityKey,
                                               final PublicKey programKey,
                                               final InitializeQuestionArgs args) {
    final var keys = List.of(
      createWrite(questionKey),
      createWritableSigner(payerKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(args)];
    int i = INITIALIZE_QUESTION_DISCRIMINATOR.write(_data, 0);
    Borsh.write(args, _data, i);

    return Instruction.createInstruction(invokedConditionalVaultProgramMeta, keys, _data);
  }

  public record InitializeQuestionIxData(Discriminator discriminator, InitializeQuestionArgs args) implements Borsh {  

    public static InitializeQuestionIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 73;

    public static InitializeQuestionIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var args = InitializeQuestionArgs.read(_data, i);
      return new InitializeQuestionIxData(discriminator, args);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(args, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator RESOLVE_QUESTION_DISCRIMINATOR = toDiscriminator(52, 32, 224, 179, 180, 8, 0, 246);

  public static Instruction resolveQuestion(final AccountMeta invokedConditionalVaultProgramMeta,
                                            final PublicKey questionKey,
                                            final PublicKey oracleKey,
                                            final PublicKey eventAuthorityKey,
                                            final PublicKey programKey,
                                            final ResolveQuestionArgs args) {
    final var keys = List.of(
      createWrite(questionKey),
      createReadOnlySigner(oracleKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(args)];
    int i = RESOLVE_QUESTION_DISCRIMINATOR.write(_data, 0);
    Borsh.write(args, _data, i);

    return Instruction.createInstruction(invokedConditionalVaultProgramMeta, keys, _data);
  }

  public record ResolveQuestionIxData(Discriminator discriminator, ResolveQuestionArgs args) implements Borsh {  

    public static ResolveQuestionIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ResolveQuestionIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var args = ResolveQuestionArgs.read(_data, i);
      return new ResolveQuestionIxData(discriminator, args);
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

  public static final Discriminator INITIALIZE_CONDITIONAL_VAULT_DISCRIMINATOR = toDiscriminator(37, 88, 250, 212, 54, 218, 227, 175);

  public static Instruction initializeConditionalVault(final AccountMeta invokedConditionalVaultProgramMeta,
                                                       final PublicKey vaultKey,
                                                       final PublicKey questionKey,
                                                       final PublicKey underlyingTokenMintKey,
                                                       final PublicKey vaultUnderlyingTokenAccountKey,
                                                       final PublicKey payerKey,
                                                       final PublicKey tokenProgramKey,
                                                       final PublicKey associatedTokenProgramKey,
                                                       final PublicKey systemProgramKey,
                                                       final PublicKey eventAuthorityKey,
                                                       final PublicKey programKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createRead(questionKey),
      createRead(underlyingTokenMintKey),
      createRead(vaultUnderlyingTokenAccountKey),
      createWritableSigner(payerKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedConditionalVaultProgramMeta, keys, INITIALIZE_CONDITIONAL_VAULT_DISCRIMINATOR);
  }

  public static final Discriminator SPLIT_TOKENS_DISCRIMINATOR = toDiscriminator(79, 195, 116, 0, 140, 176, 73, 179);

  public static Instruction splitTokens(final AccountMeta invokedConditionalVaultProgramMeta,
                                        final PublicKey questionKey,
                                        final PublicKey vaultKey,
                                        final PublicKey vaultUnderlyingTokenAccountKey,
                                        final PublicKey authorityKey,
                                        final PublicKey userUnderlyingTokenAccountKey,
                                        final PublicKey tokenProgramKey,
                                        final PublicKey eventAuthorityKey,
                                        final PublicKey programKey,
                                        final long amount) {
    final var keys = List.of(
      createRead(questionKey),
      createWrite(vaultKey),
      createWrite(vaultUnderlyingTokenAccountKey),
      createReadOnlySigner(authorityKey),
      createWrite(userUnderlyingTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = SPLIT_TOKENS_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedConditionalVaultProgramMeta, keys, _data);
  }

  public record SplitTokensIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static SplitTokensIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SplitTokensIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new SplitTokensIxData(discriminator, amount);
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

  public static final Discriminator MERGE_TOKENS_DISCRIMINATOR = toDiscriminator(226, 89, 251, 121, 225, 130, 180, 14);

  public static Instruction mergeTokens(final AccountMeta invokedConditionalVaultProgramMeta,
                                        final PublicKey questionKey,
                                        final PublicKey vaultKey,
                                        final PublicKey vaultUnderlyingTokenAccountKey,
                                        final PublicKey authorityKey,
                                        final PublicKey userUnderlyingTokenAccountKey,
                                        final PublicKey tokenProgramKey,
                                        final PublicKey eventAuthorityKey,
                                        final PublicKey programKey,
                                        final long amount) {
    final var keys = List.of(
      createRead(questionKey),
      createWrite(vaultKey),
      createWrite(vaultUnderlyingTokenAccountKey),
      createReadOnlySigner(authorityKey),
      createWrite(userUnderlyingTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = MERGE_TOKENS_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedConditionalVaultProgramMeta, keys, _data);
  }

  public record MergeTokensIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static MergeTokensIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static MergeTokensIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new MergeTokensIxData(discriminator, amount);
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

  public static final Discriminator REDEEM_TOKENS_DISCRIMINATOR = toDiscriminator(246, 98, 134, 41, 152, 33, 120, 69);

  public static Instruction redeemTokens(final AccountMeta invokedConditionalVaultProgramMeta,
                                         final PublicKey questionKey,
                                         final PublicKey vaultKey,
                                         final PublicKey vaultUnderlyingTokenAccountKey,
                                         final PublicKey authorityKey,
                                         final PublicKey userUnderlyingTokenAccountKey,
                                         final PublicKey tokenProgramKey,
                                         final PublicKey eventAuthorityKey,
                                         final PublicKey programKey) {
    final var keys = List.of(
      createRead(questionKey),
      createWrite(vaultKey),
      createWrite(vaultUnderlyingTokenAccountKey),
      createReadOnlySigner(authorityKey),
      createWrite(userUnderlyingTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedConditionalVaultProgramMeta, keys, REDEEM_TOKENS_DISCRIMINATOR);
  }

  public static final Discriminator ADD_METADATA_TO_CONDITIONAL_TOKENS_DISCRIMINATOR = toDiscriminator(133, 20, 169, 231, 114, 112, 45, 1);

  public static Instruction addMetadataToConditionalTokens(final AccountMeta invokedConditionalVaultProgramMeta,
                                                           final PublicKey payerKey,
                                                           final PublicKey vaultKey,
                                                           final PublicKey conditionalTokenMintKey,
                                                           final PublicKey conditionalTokenMetadataKey,
                                                           final PublicKey tokenMetadataProgramKey,
                                                           final PublicKey systemProgramKey,
                                                           final PublicKey rentKey,
                                                           final PublicKey eventAuthorityKey,
                                                           final PublicKey programKey,
                                                           final AddMetadataToConditionalTokensArgs args) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createWrite(vaultKey),
      createWrite(conditionalTokenMintKey),
      createWrite(conditionalTokenMetadataKey),
      createRead(tokenMetadataProgramKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(args)];
    int i = ADD_METADATA_TO_CONDITIONAL_TOKENS_DISCRIMINATOR.write(_data, 0);
    Borsh.write(args, _data, i);

    return Instruction.createInstruction(invokedConditionalVaultProgramMeta, keys, _data);
  }

  public record AddMetadataToConditionalTokensIxData(Discriminator discriminator, AddMetadataToConditionalTokensArgs args) implements Borsh {  

    public static AddMetadataToConditionalTokensIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static AddMetadataToConditionalTokensIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var args = AddMetadataToConditionalTokensArgs.read(_data, i);
      return new AddMetadataToConditionalTokensIxData(discriminator, args);
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

  private ConditionalVaultProgram() {
  }
}
