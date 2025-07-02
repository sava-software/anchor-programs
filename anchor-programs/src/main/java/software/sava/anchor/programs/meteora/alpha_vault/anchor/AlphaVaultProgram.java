package software.sava.anchor.programs.meteora.alpha_vault.anchor;

import java.lang.String;

import java.util.List;

import software.sava.anchor.programs.meteora.alpha_vault.anchor.types.CreateMerkleRootConfigParams;
import software.sava.anchor.programs.meteora.alpha_vault.anchor.types.FcfsConfigParameters;
import software.sava.anchor.programs.meteora.alpha_vault.anchor.types.InitializeFcfsVaultParams;
import software.sava.anchor.programs.meteora.alpha_vault.anchor.types.InitializeProrataVaultParams;
import software.sava.anchor.programs.meteora.alpha_vault.anchor.types.InitializeVaultWithConfigParams;
import software.sava.anchor.programs.meteora.alpha_vault.anchor.types.ProrataConfigParameters;
import software.sava.anchor.programs.meteora.alpha_vault.anchor.types.RemainingAccountsInfo;
import software.sava.anchor.programs.meteora.alpha_vault.anchor.types.UpdateFcfsVaultParams;
import software.sava.anchor.programs.meteora.alpha_vault.anchor.types.UpdateProrataVaultParams;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static java.nio.charset.StandardCharsets.UTF_8;

import static java.util.Objects.requireNonNullElse;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.anchor.AnchorUtil.writeDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class AlphaVaultProgram {

  public static final Discriminator CLAIM_TOKEN_DISCRIMINATOR = toDiscriminator(116, 206, 27, 191, 166, 19, 0, 73);

  public static Instruction claimToken(final AccountMeta invokedAlphaVaultProgramMeta,
                                       final PublicKey vaultKey,
                                       final PublicKey escrowKey,
                                       final PublicKey tokenOutVaultKey,
                                       final PublicKey destinationTokenKey,
                                       final PublicKey tokenMintKey,
                                       final PublicKey tokenProgramKey,
                                       final PublicKey ownerKey,
                                       final PublicKey eventAuthorityKey,
                                       final PublicKey programKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(escrowKey),
      createWrite(tokenOutVaultKey),
      createWrite(destinationTokenKey),
      createRead(tokenMintKey),
      createRead(tokenProgramKey),
      createReadOnlySigner(ownerKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, CLAIM_TOKEN_DISCRIMINATOR);
  }

  public static final Discriminator CLOSE_CRANK_FEE_WHITELIST_DISCRIMINATOR = toDiscriminator(189, 166, 73, 241, 81, 12, 246, 170);

  public static Instruction closeCrankFeeWhitelist(final AccountMeta invokedAlphaVaultProgramMeta,
                                                   final PublicKey crankFeeWhitelistKey,
                                                   final PublicKey adminKey,
                                                   final PublicKey rentReceiverKey,
                                                   final PublicKey eventAuthorityKey,
                                                   final PublicKey programKey) {
    final var keys = List.of(
      createWrite(crankFeeWhitelistKey),
      createWritableSigner(adminKey),
      createWrite(rentReceiverKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, CLOSE_CRANK_FEE_WHITELIST_DISCRIMINATOR);
  }

  public static final Discriminator CLOSE_ESCROW_DISCRIMINATOR = toDiscriminator(139, 171, 94, 146, 191, 91, 144, 50);

  public static Instruction closeEscrow(final AccountMeta invokedAlphaVaultProgramMeta,
                                        final PublicKey vaultKey,
                                        final PublicKey escrowKey,
                                        final PublicKey ownerKey,
                                        final PublicKey rentReceiverKey,
                                        final PublicKey eventAuthorityKey,
                                        final PublicKey programKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(escrowKey),
      createReadOnlySigner(ownerKey),
      createWrite(rentReceiverKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, CLOSE_ESCROW_DISCRIMINATOR);
  }

  public static final Discriminator CLOSE_FCFS_CONFIG_DISCRIMINATOR = toDiscriminator(48, 178, 212, 101, 23, 138, 233, 90);

  public static Instruction closeFcfsConfig(final AccountMeta invokedAlphaVaultProgramMeta,
                                            final PublicKey configKey,
                                            final PublicKey adminKey,
                                            final PublicKey rentReceiverKey) {
    final var keys = List.of(
      createWrite(configKey),
      createWritableSigner(adminKey),
      createWrite(rentReceiverKey)
    );

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, CLOSE_FCFS_CONFIG_DISCRIMINATOR);
  }

  public static final Discriminator CLOSE_MERKLE_PROOF_METADATA_DISCRIMINATOR = toDiscriminator(23, 52, 170, 30, 252, 47, 100, 129);

  public static Instruction closeMerkleProofMetadata(final AccountMeta invokedAlphaVaultProgramMeta,
                                                     final PublicKey vaultKey,
                                                     final PublicKey merkleProofMetadataKey,
                                                     final PublicKey adminKey,
                                                     final PublicKey rentReceiverKey,
                                                     final PublicKey eventAuthorityKey,
                                                     final PublicKey programKey) {
    final var keys = List.of(
      createRead(vaultKey),
      createWrite(merkleProofMetadataKey),
      createReadOnlySigner(adminKey),
      createWrite(rentReceiverKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, CLOSE_MERKLE_PROOF_METADATA_DISCRIMINATOR);
  }

  public static final Discriminator CLOSE_PRORATA_CONFIG_DISCRIMINATOR = toDiscriminator(84, 140, 103, 57, 178, 155, 57, 26);

  public static Instruction closeProrataConfig(final AccountMeta invokedAlphaVaultProgramMeta,
                                               final PublicKey configKey,
                                               final PublicKey adminKey,
                                               final PublicKey rentReceiverKey) {
    final var keys = List.of(
      createWrite(configKey),
      createWritableSigner(adminKey),
      createWrite(rentReceiverKey)
    );

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, CLOSE_PRORATA_CONFIG_DISCRIMINATOR);
  }

  public static final Discriminator CREATE_CRANK_FEE_WHITELIST_DISCRIMINATOR = toDiscriminator(120, 91, 25, 162, 211, 27, 100, 199);

  public static Instruction createCrankFeeWhitelist(final AccountMeta invokedAlphaVaultProgramMeta,
                                                    final SolanaAccounts solanaAccounts,
                                                    final PublicKey crankFeeWhitelistKey,
                                                    final PublicKey crankerKey,
                                                    final PublicKey adminKey,
                                                    final PublicKey eventAuthorityKey,
                                                    final PublicKey programKey) {
    final var keys = List.of(
      createWrite(crankFeeWhitelistKey),
      createRead(crankerKey),
      createWritableSigner(adminKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, CREATE_CRANK_FEE_WHITELIST_DISCRIMINATOR);
  }

  public static final Discriminator CREATE_FCFS_CONFIG_DISCRIMINATOR = toDiscriminator(7, 255, 242, 242, 1, 99, 179, 12);

  public static Instruction createFcfsConfig(final AccountMeta invokedAlphaVaultProgramMeta,
                                             final SolanaAccounts solanaAccounts,
                                             final PublicKey configKey,
                                             final PublicKey adminKey,
                                             final FcfsConfigParameters configParameters) {
    final var keys = List.of(
      createWrite(configKey),
      createWritableSigner(adminKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(configParameters)];
    int i = writeDiscriminator(CREATE_FCFS_CONFIG_DISCRIMINATOR, _data, 0);
    Borsh.write(configParameters, _data, i);

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, _data);
  }

  public record CreateFcfsConfigIxData(Discriminator discriminator, FcfsConfigParameters configParameters) implements Borsh {  

    public static CreateFcfsConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 65;

    public static CreateFcfsConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var configParameters = FcfsConfigParameters.read(_data, i);
      return new CreateFcfsConfigIxData(discriminator, configParameters);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(configParameters, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CREATE_MERKLE_PROOF_METADATA_DISCRIMINATOR = toDiscriminator(151, 46, 163, 52, 181, 178, 47, 227);

  public static Instruction createMerkleProofMetadata(final AccountMeta invokedAlphaVaultProgramMeta,
                                                      final SolanaAccounts solanaAccounts,
                                                      final PublicKey vaultKey,
                                                      final PublicKey merkleProofMetadataKey,
                                                      final PublicKey adminKey,
                                                      final PublicKey eventAuthorityKey,
                                                      final PublicKey programKey,
                                                      final String proofUrl) {
    final var keys = List.of(
      createRead(vaultKey),
      createWrite(merkleProofMetadataKey),
      createWritableSigner(adminKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _proofUrl = proofUrl.getBytes(UTF_8);
    final byte[] _data = new byte[12 + Borsh.lenVector(_proofUrl)];
    int i = writeDiscriminator(CREATE_MERKLE_PROOF_METADATA_DISCRIMINATOR, _data, 0);
    Borsh.writeVector(_proofUrl, _data, i);

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, _data);
  }

  public record CreateMerkleProofMetadataIxData(Discriminator discriminator, String proofUrl, byte[] _proofUrl) implements Borsh {  

    public static CreateMerkleProofMetadataIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static CreateMerkleProofMetadataIxData createRecord(final Discriminator discriminator, final String proofUrl) {
      return new CreateMerkleProofMetadataIxData(discriminator, proofUrl, proofUrl.getBytes(UTF_8));
    }

    public static CreateMerkleProofMetadataIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var proofUrl = Borsh.string(_data, i);
      return new CreateMerkleProofMetadataIxData(discriminator, proofUrl, proofUrl.getBytes(UTF_8));
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(_proofUrl, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(_proofUrl);
    }
  }

  public static final Discriminator CREATE_MERKLE_ROOT_CONFIG_DISCRIMINATOR = toDiscriminator(55, 243, 253, 240, 78, 186, 232, 166);

  public static Instruction createMerkleRootConfig(final AccountMeta invokedAlphaVaultProgramMeta,
                                                   final SolanaAccounts solanaAccounts,
                                                   final PublicKey vaultKey,
                                                   final PublicKey merkleRootConfigKey,
                                                   final PublicKey adminKey,
                                                   final PublicKey eventAuthorityKey,
                                                   final PublicKey programKey,
                                                   final CreateMerkleRootConfigParams params) {
    final var keys = List.of(
      createRead(vaultKey),
      createWrite(merkleRootConfigKey),
      createWritableSigner(adminKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(CREATE_MERKLE_ROOT_CONFIG_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, _data);
  }

  public record CreateMerkleRootConfigIxData(Discriminator discriminator, CreateMerkleRootConfigParams params) implements Borsh {  

    public static CreateMerkleRootConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 48;

    public static CreateMerkleRootConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = CreateMerkleRootConfigParams.read(_data, i);
      return new CreateMerkleRootConfigIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CREATE_NEW_ESCROW_DISCRIMINATOR = toDiscriminator(60, 154, 170, 202, 252, 109, 83, 199);

  public static Instruction createNewEscrow(final AccountMeta invokedAlphaVaultProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey vaultKey,
                                            final PublicKey poolKey,
                                            final PublicKey escrowKey,
                                            final PublicKey ownerKey,
                                            final PublicKey payerKey,
                                            final PublicKey escrowFeeReceiverKey,
                                            final PublicKey eventAuthorityKey,
                                            final PublicKey programKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createRead(poolKey),
      createWrite(escrowKey),
      createRead(ownerKey),
      createWritableSigner(payerKey),
      createWrite(requireNonNullElse(escrowFeeReceiverKey, invokedAlphaVaultProgramMeta.publicKey())),
      createRead(solanaAccounts.systemProgram()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, CREATE_NEW_ESCROW_DISCRIMINATOR);
  }

  public static final Discriminator CREATE_PERMISSIONED_ESCROW_DISCRIMINATOR = toDiscriminator(60, 166, 36, 85, 96, 137, 132, 184);

  public static Instruction createPermissionedEscrow(final AccountMeta invokedAlphaVaultProgramMeta,
                                                     final SolanaAccounts solanaAccounts,
                                                     final PublicKey vaultKey,
                                                     final PublicKey poolKey,
                                                     final PublicKey escrowKey,
                                                     final PublicKey ownerKey,
                                                     // merkle_root_config
                                                     final PublicKey merkleRootConfigKey,
                                                     final PublicKey payerKey,
                                                     final PublicKey escrowFeeReceiverKey,
                                                     final PublicKey eventAuthorityKey,
                                                     final PublicKey programKey,
                                                     final long maxCap,
                                                     final byte[][] proof) {
    final var keys = List.of(
      createWrite(vaultKey),
      createRead(poolKey),
      createWrite(escrowKey),
      createRead(ownerKey),
      createRead(merkleRootConfigKey),
      createWritableSigner(payerKey),
      createWrite(requireNonNullElse(escrowFeeReceiverKey, invokedAlphaVaultProgramMeta.publicKey())),
      createRead(solanaAccounts.systemProgram()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16 + Borsh.lenVectorArray(proof)];
    int i = writeDiscriminator(CREATE_PERMISSIONED_ESCROW_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, maxCap);
    i += 8;
    Borsh.writeVectorArray(proof, _data, i);

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, _data);
  }

  public record CreatePermissionedEscrowIxData(Discriminator discriminator, long maxCap, byte[][] proof) implements Borsh {  

    public static CreatePermissionedEscrowIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static CreatePermissionedEscrowIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var maxCap = getInt64LE(_data, i);
      i += 8;
      final var proof = Borsh.readMultiDimensionbyteVectorArray(32, _data, i);
      return new CreatePermissionedEscrowIxData(discriminator, maxCap, proof);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, maxCap);
      i += 8;
      i += Borsh.writeVectorArray(proof, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + Borsh.lenVectorArray(proof);
    }
  }

  public static final Discriminator CREATE_PERMISSIONED_ESCROW_WITH_AUTHORITY_DISCRIMINATOR = toDiscriminator(211, 231, 194, 69, 65, 11, 123, 93);

  public static Instruction createPermissionedEscrowWithAuthority(final AccountMeta invokedAlphaVaultProgramMeta,
                                                                  final SolanaAccounts solanaAccounts,
                                                                  final PublicKey vaultKey,
                                                                  final PublicKey poolKey,
                                                                  final PublicKey escrowKey,
                                                                  final PublicKey ownerKey,
                                                                  final PublicKey payerKey,
                                                                  final PublicKey eventAuthorityKey,
                                                                  final PublicKey programKey,
                                                                  final long maxCap) {
    final var keys = List.of(
      createWrite(vaultKey),
      createRead(poolKey),
      createWrite(escrowKey),
      createRead(ownerKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(CREATE_PERMISSIONED_ESCROW_WITH_AUTHORITY_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, maxCap);

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, _data);
  }

  public record CreatePermissionedEscrowWithAuthorityIxData(Discriminator discriminator, long maxCap) implements Borsh {  

    public static CreatePermissionedEscrowWithAuthorityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static CreatePermissionedEscrowWithAuthorityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var maxCap = getInt64LE(_data, i);
      return new CreatePermissionedEscrowWithAuthorityIxData(discriminator, maxCap);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, maxCap);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CREATE_PRORATA_CONFIG_DISCRIMINATOR = toDiscriminator(38, 203, 72, 231, 103, 29, 195, 61);

  public static Instruction createProrataConfig(final AccountMeta invokedAlphaVaultProgramMeta,
                                                final SolanaAccounts solanaAccounts,
                                                final PublicKey configKey,
                                                final PublicKey adminKey,
                                                final ProrataConfigParameters configParameters) {
    final var keys = List.of(
      createWrite(configKey),
      createWritableSigner(adminKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(configParameters)];
    int i = writeDiscriminator(CREATE_PRORATA_CONFIG_DISCRIMINATOR, _data, 0);
    Borsh.write(configParameters, _data, i);

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, _data);
  }

  public record CreateProrataConfigIxData(Discriminator discriminator, ProrataConfigParameters configParameters) implements Borsh {  

    public static CreateProrataConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 49;

    public static CreateProrataConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var configParameters = ProrataConfigParameters.read(_data, i);
      return new CreateProrataConfigIxData(discriminator, configParameters);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(configParameters, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DEPOSIT_DISCRIMINATOR = toDiscriminator(242, 35, 198, 137, 82, 225, 242, 182);

  public static Instruction deposit(final AccountMeta invokedAlphaVaultProgramMeta,
                                    final PublicKey vaultKey,
                                    final PublicKey poolKey,
                                    final PublicKey escrowKey,
                                    final PublicKey sourceTokenKey,
                                    final PublicKey tokenVaultKey,
                                    final PublicKey tokenMintKey,
                                    final PublicKey tokenProgramKey,
                                    final PublicKey ownerKey,
                                    final PublicKey eventAuthorityKey,
                                    final PublicKey programKey,
                                    final long maxAmount) {
    final var keys = List.of(
      createWrite(vaultKey),
      createRead(poolKey),
      createWrite(escrowKey),
      createWrite(sourceTokenKey),
      createWrite(tokenVaultKey),
      createRead(tokenMintKey),
      createRead(tokenProgramKey),
      createReadOnlySigner(ownerKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(DEPOSIT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, maxAmount);

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, _data);
  }

  public record DepositIxData(Discriminator discriminator, long maxAmount) implements Borsh {  

    public static DepositIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static DepositIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var maxAmount = getInt64LE(_data, i);
      return new DepositIxData(discriminator, maxAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, maxAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator FILL_DAMM_V2_DISCRIMINATOR = toDiscriminator(221, 175, 108, 48, 19, 204, 125, 23);

  public static Instruction fillDammV2(final AccountMeta invokedAlphaVaultProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey vaultKey,
                                       final PublicKey tokenVaultKey,
                                       final PublicKey tokenOutVaultKey,
                                       final PublicKey ammProgramKey,
                                       final PublicKey poolAuthorityKey,
                                       final PublicKey poolKey,
                                       final PublicKey tokenAVaultKey,
                                       final PublicKey tokenBVaultKey,
                                       final PublicKey tokenAMintKey,
                                       final PublicKey tokenBMintKey,
                                       final PublicKey tokenAProgramKey,
                                       final PublicKey tokenBProgramKey,
                                       final PublicKey dammEventAuthorityKey,
                                       final PublicKey crankFeeWhitelistKey,
                                       final PublicKey crankFeeReceiverKey,
                                       final PublicKey crankerKey,
                                       final PublicKey eventAuthorityKey,
                                       final PublicKey programKey,
                                       final long maxAmount) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(tokenVaultKey),
      createWrite(tokenOutVaultKey),
      createRead(ammProgramKey),
      createRead(poolAuthorityKey),
      createWrite(poolKey),
      createWrite(tokenAVaultKey),
      createWrite(tokenBVaultKey),
      createRead(tokenAMintKey),
      createRead(tokenBMintKey),
      createRead(tokenAProgramKey),
      createRead(tokenBProgramKey),
      createRead(dammEventAuthorityKey),
      createRead(requireNonNullElse(crankFeeWhitelistKey, invokedAlphaVaultProgramMeta.publicKey())),
      createWrite(requireNonNullElse(crankFeeReceiverKey, invokedAlphaVaultProgramMeta.publicKey())),
      createWritableSigner(crankerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(FILL_DAMM_V2_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, maxAmount);

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, _data);
  }

  public record FillDammV2IxData(Discriminator discriminator, long maxAmount) implements Borsh {  

    public static FillDammV2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static FillDammV2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var maxAmount = getInt64LE(_data, i);
      return new FillDammV2IxData(discriminator, maxAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, maxAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator FILL_DLMM_DISCRIMINATOR = toDiscriminator(1, 108, 141, 11, 4, 126, 251, 222);

  public static Instruction fillDlmm(final AccountMeta invokedAlphaVaultProgramMeta,
                                     final SolanaAccounts solanaAccounts,
                                     final PublicKey vaultKey,
                                     final PublicKey tokenVaultKey,
                                     final PublicKey tokenOutVaultKey,
                                     final PublicKey ammProgramKey,
                                     final PublicKey poolKey,
                                     final PublicKey binArrayBitmapExtensionKey,
                                     final PublicKey reserveXKey,
                                     final PublicKey reserveYKey,
                                     final PublicKey tokenXMintKey,
                                     final PublicKey tokenYMintKey,
                                     final PublicKey oracleKey,
                                     final PublicKey tokenXProgramKey,
                                     final PublicKey tokenYProgramKey,
                                     final PublicKey dlmmEventAuthorityKey,
                                     final PublicKey crankFeeWhitelistKey,
                                     final PublicKey crankFeeReceiverKey,
                                     final PublicKey crankerKey,
                                     final PublicKey memoProgramKey,
                                     final PublicKey eventAuthorityKey,
                                     final PublicKey programKey,
                                     final long maxAmount,
                                     final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(tokenVaultKey),
      createWrite(tokenOutVaultKey),
      createRead(ammProgramKey),
      createWrite(poolKey),
      createRead(binArrayBitmapExtensionKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(oracleKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(dlmmEventAuthorityKey),
      createRead(requireNonNullElse(crankFeeWhitelistKey, invokedAlphaVaultProgramMeta.publicKey())),
      createWrite(requireNonNullElse(crankFeeReceiverKey, invokedAlphaVaultProgramMeta.publicKey())),
      createWritableSigner(crankerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(memoProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16 + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(FILL_DLMM_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, maxAmount);
    i += 8;
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, _data);
  }

  public record FillDlmmIxData(Discriminator discriminator, long maxAmount, RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static FillDlmmIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static FillDlmmIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var maxAmount = getInt64LE(_data, i);
      i += 8;
      final var remainingAccountsInfo = RemainingAccountsInfo.read(_data, i);
      return new FillDlmmIxData(discriminator, maxAmount, remainingAccountsInfo);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, maxAmount);
      i += 8;
      i += Borsh.write(remainingAccountsInfo, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + Borsh.len(remainingAccountsInfo);
    }
  }

  public static final Discriminator FILL_DYNAMIC_AMM_DISCRIMINATOR = toDiscriminator(224, 226, 223, 80, 36, 50, 70, 231);

  public static Instruction fillDynamicAmm(final AccountMeta invokedAlphaVaultProgramMeta,
                                           final SolanaAccounts solanaAccounts,
                                           final PublicKey vaultKey,
                                           final PublicKey tokenVaultKey,
                                           final PublicKey tokenOutVaultKey,
                                           final PublicKey ammProgramKey,
                                           final PublicKey poolKey,
                                           final PublicKey aVaultKey,
                                           final PublicKey bVaultKey,
                                           final PublicKey aTokenVaultKey,
                                           final PublicKey bTokenVaultKey,
                                           final PublicKey aVaultLpMintKey,
                                           final PublicKey bVaultLpMintKey,
                                           final PublicKey aVaultLpKey,
                                           final PublicKey bVaultLpKey,
                                           final PublicKey adminTokenFeeKey,
                                           final PublicKey vaultProgramKey,
                                           final PublicKey tokenProgramKey,
                                           final PublicKey crankFeeWhitelistKey,
                                           final PublicKey crankFeeReceiverKey,
                                           final PublicKey crankerKey,
                                           final PublicKey eventAuthorityKey,
                                           final PublicKey programKey,
                                           final long maxAmount) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(tokenVaultKey),
      createWrite(tokenOutVaultKey),
      createRead(ammProgramKey),
      createWrite(poolKey),
      createWrite(aVaultKey),
      createWrite(bVaultKey),
      createWrite(aTokenVaultKey),
      createWrite(bTokenVaultKey),
      createWrite(aVaultLpMintKey),
      createWrite(bVaultLpMintKey),
      createWrite(aVaultLpKey),
      createWrite(bVaultLpKey),
      createWrite(adminTokenFeeKey),
      createRead(vaultProgramKey),
      createRead(tokenProgramKey),
      createRead(requireNonNullElse(crankFeeWhitelistKey, invokedAlphaVaultProgramMeta.publicKey())),
      createWrite(requireNonNullElse(crankFeeReceiverKey, invokedAlphaVaultProgramMeta.publicKey())),
      createWritableSigner(crankerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(FILL_DYNAMIC_AMM_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, maxAmount);

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, _data);
  }

  public record FillDynamicAmmIxData(Discriminator discriminator, long maxAmount) implements Borsh {  

    public static FillDynamicAmmIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static FillDynamicAmmIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var maxAmount = getInt64LE(_data, i);
      return new FillDynamicAmmIxData(discriminator, maxAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, maxAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_FCFS_VAULT_DISCRIMINATOR = toDiscriminator(163, 205, 69, 145, 235, 71, 47, 21);

  public static Instruction initializeFcfsVault(final AccountMeta invokedAlphaVaultProgramMeta,
                                                final SolanaAccounts solanaAccounts,
                                                final PublicKey vaultKey,
                                                final PublicKey poolKey,
                                                final PublicKey funderKey,
                                                final PublicKey baseKey,
                                                final PublicKey eventAuthorityKey,
                                                final PublicKey programKey,
                                                final InitializeFcfsVaultParams params) {
    final var keys = List.of(
      createWrite(vaultKey),
      createRead(poolKey),
      createWritableSigner(funderKey),
      createReadOnlySigner(baseKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(INITIALIZE_FCFS_VAULT_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, _data);
  }

  public record InitializeFcfsVaultIxData(Discriminator discriminator, InitializeFcfsVaultParams params) implements Borsh {  

    public static InitializeFcfsVaultIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 122;

    public static InitializeFcfsVaultIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = InitializeFcfsVaultParams.read(_data, i);
      return new InitializeFcfsVaultIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_PRORATA_VAULT_DISCRIMINATOR = toDiscriminator(178, 180, 176, 247, 128, 186, 43, 9);

  public static Instruction initializeProrataVault(final AccountMeta invokedAlphaVaultProgramMeta,
                                                   final SolanaAccounts solanaAccounts,
                                                   final PublicKey vaultKey,
                                                   final PublicKey poolKey,
                                                   final PublicKey funderKey,
                                                   final PublicKey baseKey,
                                                   final PublicKey eventAuthorityKey,
                                                   final PublicKey programKey,
                                                   final InitializeProrataVaultParams params) {
    final var keys = List.of(
      createWrite(vaultKey),
      createRead(poolKey),
      createWritableSigner(funderKey),
      createReadOnlySigner(baseKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(INITIALIZE_PRORATA_VAULT_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, _data);
  }

  public record InitializeProrataVaultIxData(Discriminator discriminator, InitializeProrataVaultParams params) implements Borsh {  

    public static InitializeProrataVaultIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 114;

    public static InitializeProrataVaultIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = InitializeProrataVaultParams.read(_data, i);
      return new InitializeProrataVaultIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_VAULT_WITH_FCFS_CONFIG_DISCRIMINATOR = toDiscriminator(189, 251, 92, 104, 235, 21, 81, 182);

  public static Instruction initializeVaultWithFcfsConfig(final AccountMeta invokedAlphaVaultProgramMeta,
                                                          final SolanaAccounts solanaAccounts,
                                                          final PublicKey vaultKey,
                                                          final PublicKey poolKey,
                                                          final PublicKey quoteMintKey,
                                                          final PublicKey funderKey,
                                                          final PublicKey configKey,
                                                          final PublicKey eventAuthorityKey,
                                                          final PublicKey programKey,
                                                          final InitializeVaultWithConfigParams params) {
    final var keys = List.of(
      createWrite(vaultKey),
      createRead(poolKey),
      createRead(quoteMintKey),
      createWritableSigner(funderKey),
      createRead(configKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(INITIALIZE_VAULT_WITH_FCFS_CONFIG_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, _data);
  }

  public record InitializeVaultWithFcfsConfigIxData(Discriminator discriminator, InitializeVaultWithConfigParams params) implements Borsh {  

    public static InitializeVaultWithFcfsConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 74;

    public static InitializeVaultWithFcfsConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = InitializeVaultWithConfigParams.read(_data, i);
      return new InitializeVaultWithFcfsConfigIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_VAULT_WITH_PRORATA_CONFIG_DISCRIMINATOR = toDiscriminator(155, 216, 34, 162, 103, 242, 236, 211);

  public static Instruction initializeVaultWithProrataConfig(final AccountMeta invokedAlphaVaultProgramMeta,
                                                             final SolanaAccounts solanaAccounts,
                                                             final PublicKey vaultKey,
                                                             final PublicKey poolKey,
                                                             final PublicKey quoteMintKey,
                                                             final PublicKey funderKey,
                                                             final PublicKey configKey,
                                                             final PublicKey eventAuthorityKey,
                                                             final PublicKey programKey,
                                                             final InitializeVaultWithConfigParams params) {
    final var keys = List.of(
      createWrite(vaultKey),
      createRead(poolKey),
      createRead(quoteMintKey),
      createWritableSigner(funderKey),
      createRead(configKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(INITIALIZE_VAULT_WITH_PRORATA_CONFIG_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, _data);
  }

  public record InitializeVaultWithProrataConfigIxData(Discriminator discriminator, InitializeVaultWithConfigParams params) implements Borsh {  

    public static InitializeVaultWithProrataConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 74;

    public static InitializeVaultWithProrataConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = InitializeVaultWithConfigParams.read(_data, i);
      return new InitializeVaultWithProrataConfigIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator TRANSFER_VAULT_AUTHORITY_DISCRIMINATOR = toDiscriminator(139, 35, 83, 88, 52, 186, 162, 110);

  public static Instruction transferVaultAuthority(final AccountMeta invokedAlphaVaultProgramMeta,
                                                   final PublicKey vaultKey,
                                                   final PublicKey vaultAuthorityKey,
                                                   final PublicKey newAuthority) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(vaultAuthorityKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(TRANSFER_VAULT_AUTHORITY_DISCRIMINATOR, _data, 0);
    newAuthority.write(_data, i);

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, _data);
  }

  public record TransferVaultAuthorityIxData(Discriminator discriminator, PublicKey newAuthority) implements Borsh {  

    public static TransferVaultAuthorityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static TransferVaultAuthorityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var newAuthority = readPubKey(_data, i);
      return new TransferVaultAuthorityIxData(discriminator, newAuthority);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      newAuthority.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_FCFS_VAULT_PARAMETERS_DISCRIMINATOR = toDiscriminator(172, 23, 13, 143, 18, 133, 104, 174);

  public static Instruction updateFcfsVaultParameters(final AccountMeta invokedAlphaVaultProgramMeta,
                                                      final PublicKey vaultKey,
                                                      final PublicKey poolKey,
                                                      final PublicKey adminKey,
                                                      final PublicKey eventAuthorityKey,
                                                      final PublicKey programKey,
                                                      final UpdateFcfsVaultParams params) {
    final var keys = List.of(
      createWrite(vaultKey),
      createRead(poolKey),
      createReadOnlySigner(adminKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(UPDATE_FCFS_VAULT_PARAMETERS_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, _data);
  }

  public record UpdateFcfsVaultParametersIxData(Discriminator discriminator, UpdateFcfsVaultParams params) implements Borsh {  

    public static UpdateFcfsVaultParametersIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 48;

    public static UpdateFcfsVaultParametersIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = UpdateFcfsVaultParams.read(_data, i);
      return new UpdateFcfsVaultParametersIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_PRORATA_VAULT_PARAMETERS_DISCRIMINATOR = toDiscriminator(177, 39, 151, 50, 253, 249, 5, 74);

  public static Instruction updateProrataVaultParameters(final AccountMeta invokedAlphaVaultProgramMeta,
                                                         final PublicKey vaultKey,
                                                         final PublicKey poolKey,
                                                         final PublicKey adminKey,
                                                         final PublicKey eventAuthorityKey,
                                                         final PublicKey programKey,
                                                         final UpdateProrataVaultParams params) {
    final var keys = List.of(
      createWrite(vaultKey),
      createRead(poolKey),
      createReadOnlySigner(adminKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(UPDATE_PRORATA_VAULT_PARAMETERS_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, _data);
  }

  public record UpdateProrataVaultParametersIxData(Discriminator discriminator, UpdateProrataVaultParams params) implements Borsh {  

    public static UpdateProrataVaultParametersIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 32;

    public static UpdateProrataVaultParametersIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = UpdateProrataVaultParams.read(_data, i);
      return new UpdateProrataVaultParametersIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator WITHDRAW_DISCRIMINATOR = toDiscriminator(183, 18, 70, 156, 148, 109, 161, 34);

  public static Instruction withdraw(final AccountMeta invokedAlphaVaultProgramMeta,
                                     final PublicKey vaultKey,
                                     final PublicKey poolKey,
                                     final PublicKey escrowKey,
                                     final PublicKey destinationTokenKey,
                                     final PublicKey tokenVaultKey,
                                     final PublicKey tokenMintKey,
                                     final PublicKey tokenProgramKey,
                                     final PublicKey ownerKey,
                                     final PublicKey eventAuthorityKey,
                                     final PublicKey programKey,
                                     final long amount) {
    final var keys = List.of(
      createWrite(vaultKey),
      createRead(poolKey),
      createWrite(escrowKey),
      createWrite(destinationTokenKey),
      createWrite(tokenVaultKey),
      createRead(tokenMintKey),
      createRead(tokenProgramKey),
      createReadOnlySigner(ownerKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(WITHDRAW_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, _data);
  }

  public record WithdrawIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static WithdrawIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static WithdrawIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new WithdrawIxData(discriminator, amount);
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

  public static final Discriminator WITHDRAW_REMAINING_QUOTE_DISCRIMINATOR = toDiscriminator(54, 253, 188, 34, 100, 145, 59, 127);

  public static Instruction withdrawRemainingQuote(final AccountMeta invokedAlphaVaultProgramMeta,
                                                   final PublicKey vaultKey,
                                                   final PublicKey poolKey,
                                                   final PublicKey escrowKey,
                                                   final PublicKey tokenVaultKey,
                                                   final PublicKey destinationTokenKey,
                                                   final PublicKey tokenMintKey,
                                                   final PublicKey tokenProgramKey,
                                                   final PublicKey ownerKey,
                                                   final PublicKey eventAuthorityKey,
                                                   final PublicKey programKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createRead(poolKey),
      createWrite(escrowKey),
      createWrite(tokenVaultKey),
      createWrite(destinationTokenKey),
      createRead(tokenMintKey),
      createRead(tokenProgramKey),
      createReadOnlySigner(ownerKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedAlphaVaultProgramMeta, keys, WITHDRAW_REMAINING_QUOTE_DISCRIMINATOR);
  }

  private AlphaVaultProgram() {
  }
}
