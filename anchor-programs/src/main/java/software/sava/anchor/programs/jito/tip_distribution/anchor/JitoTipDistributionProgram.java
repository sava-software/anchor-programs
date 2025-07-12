package software.sava.anchor.programs.jito.tip_distribution.anchor;

import java.util.List;

import software.sava.anchor.programs.jito.tip_distribution.anchor.types.Config;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.anchor.AnchorUtil.writeDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class JitoTipDistributionProgram {

  public static final Discriminator CLAIM_DISCRIMINATOR = toDiscriminator(62, 198, 214, 193, 213, 159, 108, 210);

  public static Instruction claim(final AccountMeta invokedJitoTipDistributionProgramMeta,
                                  final PublicKey configKey,
                                  final PublicKey tipDistributionAccountKey,
                                  final PublicKey merkleRootUploadAuthorityKey,
                                  // Status of the claim. Used to prevent the same party from claiming multiple times.
                                  final PublicKey claimStatusKey,
                                  // Receiver of the funds.
                                  final PublicKey claimantKey,
                                  // Who is paying for the claim.
                                  final PublicKey payerKey,
                                  final PublicKey systemProgramKey,
                                  final int bump,
                                  final long amount,
                                  final byte[][] proof) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(tipDistributionAccountKey),
      createReadOnlySigner(merkleRootUploadAuthorityKey),
      createWrite(claimStatusKey),
      createWrite(claimantKey),
      createWritableSigner(payerKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[17 + Borsh.lenVectorArray(proof)];
    int i = writeDiscriminator(CLAIM_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) bump;
    ++i;
    putInt64LE(_data, i, amount);
    i += 8;
    Borsh.writeVectorArray(proof, _data, i);

    return Instruction.createInstruction(invokedJitoTipDistributionProgramMeta, keys, _data);
  }

  public record ClaimIxData(Discriminator discriminator,
                            int bump,
                            long amount,
                            byte[][] proof) implements Borsh {  

    public static ClaimIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ClaimIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var bump = _data[i] & 0xFF;
      ++i;
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var proof = Borsh.readMultiDimensionbyteVectorArray(32, _data, i);
      return new ClaimIxData(discriminator, bump, amount, proof);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) bump;
      ++i;
      putInt64LE(_data, i, amount);
      i += 8;
      i += Borsh.writeVectorArray(proof, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 1 + 8 + Borsh.lenVectorArray(proof);
    }
  }

  public static final Discriminator CLOSE_CLAIM_STATUS_DISCRIMINATOR = toDiscriminator(163, 214, 191, 165, 245, 188, 17, 185);

  public static Instruction closeClaimStatus(final AccountMeta invokedJitoTipDistributionProgramMeta,
                                             final PublicKey configKey,
                                             final PublicKey claimStatusKey,
                                             // Receiver of the funds.
                                             final PublicKey claimStatusPayerKey) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(claimStatusKey),
      createWrite(claimStatusPayerKey)
    );

    return Instruction.createInstruction(invokedJitoTipDistributionProgramMeta, keys, CLOSE_CLAIM_STATUS_DISCRIMINATOR);
  }

  public static final Discriminator CLOSE_TIP_DISTRIBUTION_ACCOUNT_DISCRIMINATOR = toDiscriminator(47, 136, 208, 190, 125, 243, 74, 227);

  public static Instruction closeTipDistributionAccount(final AccountMeta invokedJitoTipDistributionProgramMeta,
                                                        final PublicKey configKey,
                                                        final PublicKey expiredFundsAccountKey,
                                                        final PublicKey tipDistributionAccountKey,
                                                        final PublicKey validatorVoteAccountKey,
                                                        // Anyone can crank this instruction.
                                                        final PublicKey signerKey,
                                                        final long epoch) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(expiredFundsAccountKey),
      createWrite(tipDistributionAccountKey),
      createWrite(validatorVoteAccountKey),
      createWritableSigner(signerKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(CLOSE_TIP_DISTRIBUTION_ACCOUNT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipDistributionProgramMeta, keys, _data);
  }

  public record CloseTipDistributionAccountIxData(Discriminator discriminator, long epoch) implements Borsh {  

    public static CloseTipDistributionAccountIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static CloseTipDistributionAccountIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var epoch = getInt64LE(_data, i);
      return new CloseTipDistributionAccountIxData(discriminator, epoch);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, epoch);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_DISCRIMINATOR = toDiscriminator(175, 175, 109, 31, 13, 152, 155, 237);

  public static Instruction initialize(final AccountMeta invokedJitoTipDistributionProgramMeta,
                                       final PublicKey configKey,
                                       final PublicKey systemProgramKey,
                                       final PublicKey initializerKey,
                                       final PublicKey authority,
                                       final PublicKey expiredFundsAccount,
                                       final long numEpochsValid,
                                       final int maxValidatorCommissionBps,
                                       final int bump) {
    final var keys = List.of(
      createWrite(configKey),
      createRead(systemProgramKey),
      createWritableSigner(initializerKey)
    );

    final byte[] _data = new byte[83];
    int i = writeDiscriminator(INITIALIZE_DISCRIMINATOR, _data, 0);
    authority.write(_data, i);
    i += 32;
    expiredFundsAccount.write(_data, i);
    i += 32;
    putInt64LE(_data, i, numEpochsValid);
    i += 8;
    putInt16LE(_data, i, maxValidatorCommissionBps);
    i += 2;
    _data[i] = (byte) bump;

    return Instruction.createInstruction(invokedJitoTipDistributionProgramMeta, keys, _data);
  }

  public record InitializeIxData(Discriminator discriminator,
                                 PublicKey authority,
                                 PublicKey expiredFundsAccount,
                                 long numEpochsValid,
                                 int maxValidatorCommissionBps,
                                 int bump) implements Borsh {  

    public static InitializeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 83;

    public static InitializeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var authority = readPubKey(_data, i);
      i += 32;
      final var expiredFundsAccount = readPubKey(_data, i);
      i += 32;
      final var numEpochsValid = getInt64LE(_data, i);
      i += 8;
      final var maxValidatorCommissionBps = getInt16LE(_data, i);
      i += 2;
      final var bump = _data[i] & 0xFF;
      return new InitializeIxData(discriminator,
                                  authority,
                                  expiredFundsAccount,
                                  numEpochsValid,
                                  maxValidatorCommissionBps,
                                  bump);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      authority.write(_data, i);
      i += 32;
      expiredFundsAccount.write(_data, i);
      i += 32;
      putInt64LE(_data, i, numEpochsValid);
      i += 8;
      putInt16LE(_data, i, maxValidatorCommissionBps);
      i += 2;
      _data[i] = (byte) bump;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_MERKLE_ROOT_UPLOAD_CONFIG_DISCRIMINATOR = toDiscriminator(232, 87, 72, 14, 89, 40, 40, 27);

  public static Instruction initializeMerkleRootUploadConfig(final AccountMeta invokedJitoTipDistributionProgramMeta,
                                                             final PublicKey configKey,
                                                             final PublicKey merkleRootUploadConfigKey,
                                                             final PublicKey authorityKey,
                                                             final PublicKey payerKey,
                                                             final PublicKey systemProgramKey,
                                                             final PublicKey authority,
                                                             final PublicKey originalAuthority) {
    final var keys = List.of(
      createWrite(configKey),
      createWrite(merkleRootUploadConfigKey),
      createReadOnlySigner(authorityKey),
      createWritableSigner(payerKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[72];
    int i = writeDiscriminator(INITIALIZE_MERKLE_ROOT_UPLOAD_CONFIG_DISCRIMINATOR, _data, 0);
    authority.write(_data, i);
    i += 32;
    originalAuthority.write(_data, i);

    return Instruction.createInstruction(invokedJitoTipDistributionProgramMeta, keys, _data);
  }

  public record InitializeMerkleRootUploadConfigIxData(Discriminator discriminator, PublicKey authority, PublicKey originalAuthority) implements Borsh {  

    public static InitializeMerkleRootUploadConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 72;

    public static InitializeMerkleRootUploadConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var authority = readPubKey(_data, i);
      i += 32;
      final var originalAuthority = readPubKey(_data, i);
      return new InitializeMerkleRootUploadConfigIxData(discriminator, authority, originalAuthority);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      authority.write(_data, i);
      i += 32;
      originalAuthority.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_TIP_DISTRIBUTION_ACCOUNT_DISCRIMINATOR = toDiscriminator(120, 191, 25, 182, 111, 49, 179, 55);

  public static Instruction initializeTipDistributionAccount(final AccountMeta invokedJitoTipDistributionProgramMeta,
                                                             final PublicKey configKey,
                                                             final PublicKey tipDistributionAccountKey,
                                                             // The validator's vote account is used to check this transaction's signer is also the authorized withdrawer.
                                                             final PublicKey validatorVoteAccountKey,
                                                             // Must be equal to the supplied validator vote account's authorized withdrawer.
                                                             final PublicKey signerKey,
                                                             final PublicKey systemProgramKey,
                                                             final PublicKey merkleRootUploadAuthority,
                                                             final int validatorCommissionBps,
                                                             final int bump) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(tipDistributionAccountKey),
      createRead(validatorVoteAccountKey),
      createWritableSigner(signerKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[43];
    int i = writeDiscriminator(INITIALIZE_TIP_DISTRIBUTION_ACCOUNT_DISCRIMINATOR, _data, 0);
    merkleRootUploadAuthority.write(_data, i);
    i += 32;
    putInt16LE(_data, i, validatorCommissionBps);
    i += 2;
    _data[i] = (byte) bump;

    return Instruction.createInstruction(invokedJitoTipDistributionProgramMeta, keys, _data);
  }

  public record InitializeTipDistributionAccountIxData(Discriminator discriminator,
                                                       PublicKey merkleRootUploadAuthority,
                                                       int validatorCommissionBps,
                                                       int bump) implements Borsh {  

    public static InitializeTipDistributionAccountIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 43;

    public static InitializeTipDistributionAccountIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var merkleRootUploadAuthority = readPubKey(_data, i);
      i += 32;
      final var validatorCommissionBps = getInt16LE(_data, i);
      i += 2;
      final var bump = _data[i] & 0xFF;
      return new InitializeTipDistributionAccountIxData(discriminator, merkleRootUploadAuthority, validatorCommissionBps, bump);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      merkleRootUploadAuthority.write(_data, i);
      i += 32;
      putInt16LE(_data, i, validatorCommissionBps);
      i += 2;
      _data[i] = (byte) bump;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator MIGRATE_TDA_MERKLE_ROOT_UPLOAD_AUTHORITY_DISCRIMINATOR = toDiscriminator(13, 226, 163, 144, 56, 202, 214, 23);

  public static Instruction migrateTdaMerkleRootUploadAuthority(final AccountMeta invokedJitoTipDistributionProgramMeta, final PublicKey tipDistributionAccountKey, final PublicKey merkleRootUploadConfigKey) {
    final var keys = List.of(
      createWrite(tipDistributionAccountKey),
      createRead(merkleRootUploadConfigKey)
    );

    return Instruction.createInstruction(invokedJitoTipDistributionProgramMeta, keys, MIGRATE_TDA_MERKLE_ROOT_UPLOAD_AUTHORITY_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_CONFIG_DISCRIMINATOR = toDiscriminator(29, 158, 252, 191, 10, 83, 219, 99);

  public static Instruction updateConfig(final AccountMeta invokedJitoTipDistributionProgramMeta,
                                         final PublicKey configKey,
                                         final PublicKey authorityKey,
                                         final Config newConfig) {
    final var keys = List.of(
      createWrite(configKey),
      createWritableSigner(authorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(newConfig)];
    int i = writeDiscriminator(UPDATE_CONFIG_DISCRIMINATOR, _data, 0);
    Borsh.write(newConfig, _data, i);

    return Instruction.createInstruction(invokedJitoTipDistributionProgramMeta, keys, _data);
  }

  public record UpdateConfigIxData(Discriminator discriminator, Config newConfig) implements Borsh {  

    public static UpdateConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 91;

    public static UpdateConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var newConfig = Config.read(_data, i);
      return new UpdateConfigIxData(discriminator, newConfig);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(newConfig, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_MERKLE_ROOT_UPLOAD_CONFIG_DISCRIMINATOR = toDiscriminator(128, 227, 159, 139, 176, 128, 118, 2);

  public static Instruction updateMerkleRootUploadConfig(final AccountMeta invokedJitoTipDistributionProgramMeta,
                                                         final PublicKey configKey,
                                                         final PublicKey merkleRootUploadConfigKey,
                                                         final PublicKey authorityKey,
                                                         final PublicKey systemProgramKey,
                                                         final PublicKey authority,
                                                         final PublicKey originalAuthority) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(merkleRootUploadConfigKey),
      createReadOnlySigner(authorityKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[72];
    int i = writeDiscriminator(UPDATE_MERKLE_ROOT_UPLOAD_CONFIG_DISCRIMINATOR, _data, 0);
    authority.write(_data, i);
    i += 32;
    originalAuthority.write(_data, i);

    return Instruction.createInstruction(invokedJitoTipDistributionProgramMeta, keys, _data);
  }

  public record UpdateMerkleRootUploadConfigIxData(Discriminator discriminator, PublicKey authority, PublicKey originalAuthority) implements Borsh {  

    public static UpdateMerkleRootUploadConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 72;

    public static UpdateMerkleRootUploadConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var authority = readPubKey(_data, i);
      i += 32;
      final var originalAuthority = readPubKey(_data, i);
      return new UpdateMerkleRootUploadConfigIxData(discriminator, authority, originalAuthority);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      authority.write(_data, i);
      i += 32;
      originalAuthority.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPLOAD_MERKLE_ROOT_DISCRIMINATOR = toDiscriminator(70, 3, 110, 29, 199, 190, 205, 176);

  public static Instruction uploadMerkleRoot(final AccountMeta invokedJitoTipDistributionProgramMeta,
                                             final PublicKey configKey,
                                             final PublicKey tipDistributionAccountKey,
                                             final PublicKey merkleRootUploadAuthorityKey,
                                             final byte[] root,
                                             final long maxTotalClaim,
                                             final long maxNumNodes) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(tipDistributionAccountKey),
      createWritableSigner(merkleRootUploadAuthorityKey)
    );

    final byte[] _data = new byte[24 + Borsh.lenArray(root)];
    int i = writeDiscriminator(UPLOAD_MERKLE_ROOT_DISCRIMINATOR, _data, 0);
    i += Borsh.writeArray(root, _data, i);
    putInt64LE(_data, i, maxTotalClaim);
    i += 8;
    putInt64LE(_data, i, maxNumNodes);

    return Instruction.createInstruction(invokedJitoTipDistributionProgramMeta, keys, _data);
  }

  public record UploadMerkleRootIxData(Discriminator discriminator,
                                       byte[] root,
                                       long maxTotalClaim,
                                       long maxNumNodes) implements Borsh {  

    public static UploadMerkleRootIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 56;
    public static final int ROOT_LEN = 32;

    public static UploadMerkleRootIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var root = new byte[32];
      i += Borsh.readArray(root, _data, i);
      final var maxTotalClaim = getInt64LE(_data, i);
      i += 8;
      final var maxNumNodes = getInt64LE(_data, i);
      return new UploadMerkleRootIxData(discriminator, root, maxTotalClaim, maxNumNodes);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeArray(root, _data, i);
      putInt64LE(_data, i, maxTotalClaim);
      i += 8;
      putInt64LE(_data, i, maxNumNodes);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  private JitoTipDistributionProgram() {
  }
}
