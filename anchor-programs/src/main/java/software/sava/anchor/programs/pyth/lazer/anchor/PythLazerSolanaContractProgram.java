package software.sava.anchor.programs.pyth.lazer.anchor;

import java.util.List;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class PythLazerSolanaContractProgram {

  public static final Discriminator INITIALIZE_DISCRIMINATOR = toDiscriminator(175, 175, 109, 31, 13, 152, 155, 237);

  public static Instruction initialize(final AccountMeta invokedPythLazerSolanaContractProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey payerKey,
                                       final PublicKey storageKey,
                                       final PublicKey topAuthority,
                                       final PublicKey treasury) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createWrite(storageKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[72];
    int i = INITIALIZE_DISCRIMINATOR.write(_data, 0);
    topAuthority.write(_data, i);
    i += 32;
    treasury.write(_data, i);

    return Instruction.createInstruction(invokedPythLazerSolanaContractProgramMeta, keys, _data);
  }

  public record InitializeIxData(Discriminator discriminator, PublicKey topAuthority, PublicKey treasury) implements Borsh {  

    public static InitializeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 72;

    public static InitializeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var topAuthority = readPubKey(_data, i);
      i += 32;
      final var treasury = readPubKey(_data, i);
      return new InitializeIxData(discriminator, topAuthority, treasury);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      topAuthority.write(_data, i);
      i += 32;
      treasury.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_DISCRIMINATOR = toDiscriminator(219, 200, 88, 176, 158, 63, 253, 127);

  public static Instruction update(final AccountMeta invokedPythLazerSolanaContractProgramMeta,
                                   final PublicKey topAuthorityKey,
                                   final PublicKey storageKey,
                                   final PublicKey trustedSigner,
                                   final long expiresAt) {
    final var keys = List.of(
      createReadOnlySigner(topAuthorityKey),
      createWrite(storageKey)
    );

    final byte[] _data = new byte[48];
    int i = UPDATE_DISCRIMINATOR.write(_data, 0);
    trustedSigner.write(_data, i);
    i += 32;
    putInt64LE(_data, i, expiresAt);

    return Instruction.createInstruction(invokedPythLazerSolanaContractProgramMeta, keys, _data);
  }

  public record UpdateIxData(Discriminator discriminator, PublicKey trustedSigner, long expiresAt) implements Borsh {  

    public static UpdateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 48;

    public static UpdateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var trustedSigner = readPubKey(_data, i);
      i += 32;
      final var expiresAt = getInt64LE(_data, i);
      return new UpdateIxData(discriminator, trustedSigner, expiresAt);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      trustedSigner.write(_data, i);
      i += 32;
      putInt64LE(_data, i, expiresAt);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator VERIFY_MESSAGE_DISCRIMINATOR = toDiscriminator(180, 193, 120, 55, 189, 135, 203, 83);

  // Verifies a ed25519 signature on Solana by checking that the transaction contains
  // a correct call to the built-in `ed25519_program`.
  // 
  // - `message_data` is the signed message that is being verified.
  // - `ed25519_instruction_index` is the index of the `ed25519_program` instruction
  // within the transaction. This instruction must precede the current instruction.
  // - `signature_index` is the index of the signature within the inputs to the `ed25519_program`.
  // - `message_offset` is the offset of the signed message within the
  // input data for the current instruction.
  public static Instruction verifyMessage(final AccountMeta invokedPythLazerSolanaContractProgramMeta,
                                          final SolanaAccounts solanaAccounts,
                                          final PublicKey payerKey,
                                          final PublicKey storageKey,
                                          final PublicKey treasuryKey,
                                          // (e.g. in `sysvar::instructions::load_instruction_at_checked`).
                                          // This account is not usable with anchor's `Program` account type because it's not executable.
                                          final PublicKey instructionsSysvarKey,
                                          final byte[] messageData,
                                          final int ed25519InstructionIndex,
                                          final int signatureIndex) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createRead(storageKey),
      createRead(treasuryKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(instructionsSysvarKey)
    );

    final byte[] _data = new byte[11 + Borsh.lenVector(messageData)];
    int i = VERIFY_MESSAGE_DISCRIMINATOR.write(_data, 0);
    i += Borsh.writeVector(messageData, _data, i);
    putInt16LE(_data, i, ed25519InstructionIndex);
    i += 2;
    _data[i] = (byte) signatureIndex;

    return Instruction.createInstruction(invokedPythLazerSolanaContractProgramMeta, keys, _data);
  }

  public record VerifyMessageIxData(Discriminator discriminator,
                                    byte[] messageData,
                                    int ed25519InstructionIndex,
                                    int signatureIndex) implements Borsh {  

    public static VerifyMessageIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static VerifyMessageIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var messageData = Borsh.readbyteVector(_data, i);
      i += Borsh.lenVector(messageData);
      final var ed25519InstructionIndex = getInt16LE(_data, i);
      i += 2;
      final var signatureIndex = _data[i] & 0xFF;
      return new VerifyMessageIxData(discriminator, messageData, ed25519InstructionIndex, signatureIndex);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(messageData, _data, i);
      putInt16LE(_data, i, ed25519InstructionIndex);
      i += 2;
      _data[i] = (byte) signatureIndex;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(messageData) + 2 + 1;
    }
  }

  private PythLazerSolanaContractProgram() {
  }
}
