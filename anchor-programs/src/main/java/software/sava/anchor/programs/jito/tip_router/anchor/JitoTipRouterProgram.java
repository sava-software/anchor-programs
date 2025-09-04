package software.sava.anchor.programs.jito.tip_router.anchor;

import java.math.BigInteger;

import java.util.List;
import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.anchor.programs.jito.tip_router.anchor.types.ConfigAdminRole;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static java.util.Objects.requireNonNullElse;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class JitoTipRouterProgram {

  public static final Discriminator INITIALIZE_CONFIG_DISCRIMINATOR = toDiscriminator(208, 127, 21, 1, 194, 190, 196, 70);

  public static Instruction initializeConfig(final AccountMeta invokedJitoTipRouterProgramMeta,
                                             final PublicKey configKey,
                                             final PublicKey ncnKey,
                                             final PublicKey feeWalletKey,
                                             final PublicKey ncnAdminKey,
                                             final PublicKey tieBreakerAdminKey,
                                             final PublicKey accountPayerKey,
                                             final PublicKey systemProgramKey,
                                             final int blockEngineFeeBps,
                                             final int daoFeeBps,
                                             final int defaultNcnFeeBps,
                                             final long epochsBeforeStall,
                                             final long epochsAfterConsensusBeforeClose,
                                             final long validSlotsAfterConsensus) {
    final var keys = List.of(
      createWrite(configKey),
      createRead(ncnKey),
      createRead(feeWalletKey),
      createReadOnlySigner(ncnAdminKey),
      createRead(tieBreakerAdminKey),
      createWrite(accountPayerKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[38];
    int i = INITIALIZE_CONFIG_DISCRIMINATOR.write(_data, 0);
    putInt16LE(_data, i, blockEngineFeeBps);
    i += 2;
    putInt16LE(_data, i, daoFeeBps);
    i += 2;
    putInt16LE(_data, i, defaultNcnFeeBps);
    i += 2;
    putInt64LE(_data, i, epochsBeforeStall);
    i += 8;
    putInt64LE(_data, i, epochsAfterConsensusBeforeClose);
    i += 8;
    putInt64LE(_data, i, validSlotsAfterConsensus);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record InitializeConfigIxData(Discriminator discriminator,
                                       int blockEngineFeeBps,
                                       int daoFeeBps,
                                       int defaultNcnFeeBps,
                                       long epochsBeforeStall,
                                       long epochsAfterConsensusBeforeClose,
                                       long validSlotsAfterConsensus) implements Borsh {  

    public static InitializeConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 38;

    public static InitializeConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var blockEngineFeeBps = getInt16LE(_data, i);
      i += 2;
      final var daoFeeBps = getInt16LE(_data, i);
      i += 2;
      final var defaultNcnFeeBps = getInt16LE(_data, i);
      i += 2;
      final var epochsBeforeStall = getInt64LE(_data, i);
      i += 8;
      final var epochsAfterConsensusBeforeClose = getInt64LE(_data, i);
      i += 8;
      final var validSlotsAfterConsensus = getInt64LE(_data, i);
      return new InitializeConfigIxData(discriminator,
                                        blockEngineFeeBps,
                                        daoFeeBps,
                                        defaultNcnFeeBps,
                                        epochsBeforeStall,
                                        epochsAfterConsensusBeforeClose,
                                        validSlotsAfterConsensus);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, blockEngineFeeBps);
      i += 2;
      putInt16LE(_data, i, daoFeeBps);
      i += 2;
      putInt16LE(_data, i, defaultNcnFeeBps);
      i += 2;
      putInt64LE(_data, i, epochsBeforeStall);
      i += 8;
      putInt64LE(_data, i, epochsAfterConsensusBeforeClose);
      i += 8;
      putInt64LE(_data, i, validSlotsAfterConsensus);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_VAULT_REGISTRY_DISCRIMINATOR = toDiscriminator(233, 197, 179, 247, 34, 136, 39, 184);

  public static Instruction initializeVaultRegistry(final AccountMeta invokedJitoTipRouterProgramMeta,
                                                    final PublicKey configKey,
                                                    final PublicKey vaultRegistryKey,
                                                    final PublicKey ncnKey,
                                                    final PublicKey accountPayerKey,
                                                    final PublicKey systemProgramKey) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(vaultRegistryKey),
      createRead(ncnKey),
      createWrite(accountPayerKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, INITIALIZE_VAULT_REGISTRY_DISCRIMINATOR);
  }

  public static final Discriminator REALLOC_VAULT_REGISTRY_DISCRIMINATOR = toDiscriminator(204, 130, 253, 219, 228, 118, 244, 82);

  public static Instruction reallocVaultRegistry(final AccountMeta invokedJitoTipRouterProgramMeta,
                                                 final PublicKey configKey,
                                                 final PublicKey vaultRegistryKey,
                                                 final PublicKey ncnKey,
                                                 final PublicKey accountPayerKey,
                                                 final PublicKey systemProgramKey) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(vaultRegistryKey),
      createRead(ncnKey),
      createWrite(accountPayerKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, REALLOC_VAULT_REGISTRY_DISCRIMINATOR);
  }

  public static final Discriminator REGISTER_VAULT_DISCRIMINATOR = toDiscriminator(121, 62, 4, 122, 93, 231, 119, 49);

  public static Instruction registerVault(final AccountMeta invokedJitoTipRouterProgramMeta,
                                          final PublicKey configKey,
                                          final PublicKey vaultRegistryKey,
                                          final PublicKey ncnKey,
                                          final PublicKey vaultKey,
                                          final PublicKey ncnVaultTicketKey) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(vaultRegistryKey),
      createRead(ncnKey),
      createRead(vaultKey),
      createRead(ncnVaultTicketKey)
    );

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, REGISTER_VAULT_DISCRIMINATOR);
  }

  public static final Discriminator INITIALIZE_EPOCH_STATE_DISCRIMINATOR = toDiscriminator(139, 122, 53, 254, 85, 205, 138, 245);

  public static Instruction initializeEpochState(final AccountMeta invokedJitoTipRouterProgramMeta,
                                                 final PublicKey epochMarkerKey,
                                                 final PublicKey epochStateKey,
                                                 final PublicKey configKey,
                                                 final PublicKey ncnKey,
                                                 final PublicKey accountPayerKey,
                                                 final PublicKey systemProgramKey,
                                                 final long epoch) {
    final var keys = List.of(
      createRead(epochMarkerKey),
      createWrite(epochStateKey),
      createRead(configKey),
      createRead(ncnKey),
      createWrite(accountPayerKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = INITIALIZE_EPOCH_STATE_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record InitializeEpochStateIxData(Discriminator discriminator, long epoch) implements Borsh {  

    public static InitializeEpochStateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static InitializeEpochStateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var epoch = getInt64LE(_data, i);
      return new InitializeEpochStateIxData(discriminator, epoch);
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

  public static final Discriminator REALLOC_EPOCH_STATE_DISCRIMINATOR = toDiscriminator(3, 222, 123, 59, 161, 168, 26, 203);

  public static Instruction reallocEpochState(final AccountMeta invokedJitoTipRouterProgramMeta,
                                              final PublicKey epochStateKey,
                                              final PublicKey configKey,
                                              final PublicKey ncnKey,
                                              final PublicKey accountPayerKey,
                                              final PublicKey systemProgramKey,
                                              final long epoch) {
    final var keys = List.of(
      createWrite(epochStateKey),
      createRead(configKey),
      createRead(ncnKey),
      createWrite(accountPayerKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = REALLOC_EPOCH_STATE_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record ReallocEpochStateIxData(Discriminator discriminator, long epoch) implements Borsh {  

    public static ReallocEpochStateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static ReallocEpochStateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var epoch = getInt64LE(_data, i);
      return new ReallocEpochStateIxData(discriminator, epoch);
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

  public static final Discriminator INITIALIZE_WEIGHT_TABLE_DISCRIMINATOR = toDiscriminator(2, 144, 127, 144, 142, 12, 199, 187);

  public static Instruction initializeWeightTable(final AccountMeta invokedJitoTipRouterProgramMeta,
                                                  final PublicKey epochMarkerKey,
                                                  final PublicKey epochStateKey,
                                                  final PublicKey vaultRegistryKey,
                                                  final PublicKey ncnKey,
                                                  final PublicKey weightTableKey,
                                                  final PublicKey accountPayerKey,
                                                  final PublicKey systemProgramKey,
                                                  final long epoch) {
    final var keys = List.of(
      createRead(epochMarkerKey),
      createRead(epochStateKey),
      createRead(vaultRegistryKey),
      createRead(ncnKey),
      createWrite(weightTableKey),
      createWrite(accountPayerKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = INITIALIZE_WEIGHT_TABLE_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record InitializeWeightTableIxData(Discriminator discriminator, long epoch) implements Borsh {  

    public static InitializeWeightTableIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static InitializeWeightTableIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var epoch = getInt64LE(_data, i);
      return new InitializeWeightTableIxData(discriminator, epoch);
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

  public static final Discriminator REALLOC_WEIGHT_TABLE_DISCRIMINATOR = toDiscriminator(164, 39, 172, 89, 25, 57, 1, 121);

  public static Instruction reallocWeightTable(final AccountMeta invokedJitoTipRouterProgramMeta,
                                               final PublicKey epochStateKey,
                                               final PublicKey configKey,
                                               final PublicKey weightTableKey,
                                               final PublicKey ncnKey,
                                               final PublicKey vaultRegistryKey,
                                               final PublicKey accountPayerKey,
                                               final PublicKey systemProgramKey,
                                               final long epoch) {
    final var keys = List.of(
      createWrite(epochStateKey),
      createRead(configKey),
      createWrite(weightTableKey),
      createRead(ncnKey),
      createRead(vaultRegistryKey),
      createWrite(accountPayerKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = REALLOC_WEIGHT_TABLE_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record ReallocWeightTableIxData(Discriminator discriminator, long epoch) implements Borsh {  

    public static ReallocWeightTableIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static ReallocWeightTableIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var epoch = getInt64LE(_data, i);
      return new ReallocWeightTableIxData(discriminator, epoch);
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

  public static final Discriminator SWITCHBOARD_SET_WEIGHT_DISCRIMINATOR = toDiscriminator(145, 62, 228, 4, 230, 96, 185, 96);

  public static Instruction switchboardSetWeight(final AccountMeta invokedJitoTipRouterProgramMeta,
                                                 final PublicKey epochStateKey,
                                                 final PublicKey ncnKey,
                                                 final PublicKey weightTableKey,
                                                 final PublicKey switchboardFeedKey,
                                                 final PublicKey stMint,
                                                 final long epoch) {
    final var keys = List.of(
      createWrite(epochStateKey),
      createRead(ncnKey),
      createWrite(weightTableKey),
      createRead(switchboardFeedKey)
    );

    final byte[] _data = new byte[48];
    int i = SWITCHBOARD_SET_WEIGHT_DISCRIMINATOR.write(_data, 0);
    stMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record SwitchboardSetWeightIxData(Discriminator discriminator, PublicKey stMint, long epoch) implements Borsh {  

    public static SwitchboardSetWeightIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 48;

    public static SwitchboardSetWeightIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var stMint = readPubKey(_data, i);
      i += 32;
      final var epoch = getInt64LE(_data, i);
      return new SwitchboardSetWeightIxData(discriminator, stMint, epoch);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      stMint.write(_data, i);
      i += 32;
      putInt64LE(_data, i, epoch);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_EPOCH_SNAPSHOT_DISCRIMINATOR = toDiscriminator(209, 224, 11, 193, 202, 16, 18, 248);

  public static Instruction initializeEpochSnapshot(final AccountMeta invokedJitoTipRouterProgramMeta,
                                                    final PublicKey epochMarkerKey,
                                                    final PublicKey epochStateKey,
                                                    final PublicKey configKey,
                                                    final PublicKey ncnKey,
                                                    final PublicKey weightTableKey,
                                                    final PublicKey epochSnapshotKey,
                                                    final PublicKey accountPayerKey,
                                                    final PublicKey systemProgramKey,
                                                    final long epoch) {
    final var keys = List.of(
      createRead(epochMarkerKey),
      createWrite(epochStateKey),
      createRead(configKey),
      createRead(ncnKey),
      createRead(weightTableKey),
      createWrite(epochSnapshotKey),
      createWrite(accountPayerKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = INITIALIZE_EPOCH_SNAPSHOT_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record InitializeEpochSnapshotIxData(Discriminator discriminator, long epoch) implements Borsh {  

    public static InitializeEpochSnapshotIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static InitializeEpochSnapshotIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var epoch = getInt64LE(_data, i);
      return new InitializeEpochSnapshotIxData(discriminator, epoch);
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

  public static final Discriminator INITIALIZE_OPERATOR_SNAPSHOT_DISCRIMINATOR = toDiscriminator(118, 229, 222, 64, 157, 171, 158, 5);

  public static Instruction initializeOperatorSnapshot(final AccountMeta invokedJitoTipRouterProgramMeta,
                                                       final PublicKey epochMarkerKey,
                                                       final PublicKey epochStateKey,
                                                       final PublicKey configKey,
                                                       final PublicKey ncnKey,
                                                       final PublicKey operatorKey,
                                                       final PublicKey ncnOperatorStateKey,
                                                       final PublicKey epochSnapshotKey,
                                                       final PublicKey operatorSnapshotKey,
                                                       final PublicKey accountPayerKey,
                                                       final PublicKey systemProgramKey,
                                                       final long epoch) {
    final var keys = List.of(
      createRead(epochMarkerKey),
      createRead(epochStateKey),
      createRead(configKey),
      createRead(ncnKey),
      createRead(operatorKey),
      createRead(ncnOperatorStateKey),
      createRead(epochSnapshotKey),
      createWrite(operatorSnapshotKey),
      createWrite(accountPayerKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = INITIALIZE_OPERATOR_SNAPSHOT_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record InitializeOperatorSnapshotIxData(Discriminator discriminator, long epoch) implements Borsh {  

    public static InitializeOperatorSnapshotIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static InitializeOperatorSnapshotIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var epoch = getInt64LE(_data, i);
      return new InitializeOperatorSnapshotIxData(discriminator, epoch);
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

  public static final Discriminator REALLOC_OPERATOR_SNAPSHOT_DISCRIMINATOR = toDiscriminator(10, 103, 250, 237, 16, 45, 193, 249);

  public static Instruction reallocOperatorSnapshot(final AccountMeta invokedJitoTipRouterProgramMeta,
                                                    final PublicKey epochStateKey,
                                                    final PublicKey configKey,
                                                    final PublicKey restakingConfigKey,
                                                    final PublicKey ncnKey,
                                                    final PublicKey operatorKey,
                                                    final PublicKey ncnOperatorStateKey,
                                                    final PublicKey epochSnapshotKey,
                                                    final PublicKey operatorSnapshotKey,
                                                    final PublicKey accountPayerKey,
                                                    final PublicKey systemProgramKey,
                                                    final long epoch) {
    final var keys = List.of(
      createWrite(epochStateKey),
      createRead(configKey),
      createRead(restakingConfigKey),
      createRead(ncnKey),
      createRead(operatorKey),
      createRead(ncnOperatorStateKey),
      createWrite(epochSnapshotKey),
      createWrite(operatorSnapshotKey),
      createWrite(accountPayerKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = REALLOC_OPERATOR_SNAPSHOT_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record ReallocOperatorSnapshotIxData(Discriminator discriminator, long epoch) implements Borsh {  

    public static ReallocOperatorSnapshotIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static ReallocOperatorSnapshotIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var epoch = getInt64LE(_data, i);
      return new ReallocOperatorSnapshotIxData(discriminator, epoch);
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

  public static final Discriminator SNAPSHOT_VAULT_OPERATOR_DELEGATION_DISCRIMINATOR = toDiscriminator(236, 142, 91, 17, 20, 171, 97, 185);

  public static Instruction snapshotVaultOperatorDelegation(final AccountMeta invokedJitoTipRouterProgramMeta,
                                                            final PublicKey epochStateKey,
                                                            final PublicKey configKey,
                                                            final PublicKey restakingConfigKey,
                                                            final PublicKey ncnKey,
                                                            final PublicKey operatorKey,
                                                            final PublicKey vaultKey,
                                                            final PublicKey vaultNcnTicketKey,
                                                            final PublicKey ncnVaultTicketKey,
                                                            final PublicKey vaultOperatorDelegationKey,
                                                            final PublicKey weightTableKey,
                                                            final PublicKey epochSnapshotKey,
                                                            final PublicKey operatorSnapshotKey,
                                                            final long epoch) {
    final var keys = List.of(
      createWrite(epochStateKey),
      createRead(configKey),
      createRead(restakingConfigKey),
      createRead(ncnKey),
      createRead(operatorKey),
      createRead(vaultKey),
      createRead(vaultNcnTicketKey),
      createRead(ncnVaultTicketKey),
      createRead(vaultOperatorDelegationKey),
      createRead(weightTableKey),
      createWrite(epochSnapshotKey),
      createWrite(operatorSnapshotKey)
    );

    final byte[] _data = new byte[16];
    int i = SNAPSHOT_VAULT_OPERATOR_DELEGATION_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record SnapshotVaultOperatorDelegationIxData(Discriminator discriminator, long epoch) implements Borsh {  

    public static SnapshotVaultOperatorDelegationIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SnapshotVaultOperatorDelegationIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var epoch = getInt64LE(_data, i);
      return new SnapshotVaultOperatorDelegationIxData(discriminator, epoch);
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

  public static final Discriminator INITIALIZE_BALLOT_BOX_DISCRIMINATOR = toDiscriminator(78, 30, 55, 143, 169, 230, 225, 218);

  public static Instruction initializeBallotBox(final AccountMeta invokedJitoTipRouterProgramMeta,
                                                final PublicKey epochMarkerKey,
                                                final PublicKey epochStateKey,
                                                final PublicKey configKey,
                                                final PublicKey ballotBoxKey,
                                                final PublicKey ncnKey,
                                                final PublicKey accountPayerKey,
                                                final PublicKey systemProgramKey,
                                                final long epoch) {
    final var keys = List.of(
      createRead(epochMarkerKey),
      createRead(epochStateKey),
      createRead(configKey),
      createWrite(ballotBoxKey),
      createRead(ncnKey),
      createWrite(accountPayerKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = INITIALIZE_BALLOT_BOX_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record InitializeBallotBoxIxData(Discriminator discriminator, long epoch) implements Borsh {  

    public static InitializeBallotBoxIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static InitializeBallotBoxIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var epoch = getInt64LE(_data, i);
      return new InitializeBallotBoxIxData(discriminator, epoch);
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

  public static final Discriminator REALLOC_BALLOT_BOX_DISCRIMINATOR = toDiscriminator(244, 255, 62, 68, 101, 168, 176, 237);

  public static Instruction reallocBallotBox(final AccountMeta invokedJitoTipRouterProgramMeta,
                                             final PublicKey epochStateKey,
                                             final PublicKey configKey,
                                             final PublicKey ballotBoxKey,
                                             final PublicKey ncnKey,
                                             final PublicKey accountPayerKey,
                                             final PublicKey systemProgramKey,
                                             final long epoch) {
    final var keys = List.of(
      createWrite(epochStateKey),
      createRead(configKey),
      createWrite(ballotBoxKey),
      createRead(ncnKey),
      createWrite(accountPayerKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = REALLOC_BALLOT_BOX_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record ReallocBallotBoxIxData(Discriminator discriminator, long epoch) implements Borsh {  

    public static ReallocBallotBoxIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static ReallocBallotBoxIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var epoch = getInt64LE(_data, i);
      return new ReallocBallotBoxIxData(discriminator, epoch);
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

  public static final Discriminator CAST_VOTE_DISCRIMINATOR = toDiscriminator(20, 212, 15, 189, 69, 180, 69, 151);

  public static Instruction castVote(final AccountMeta invokedJitoTipRouterProgramMeta,
                                     final PublicKey epochStateKey,
                                     final PublicKey configKey,
                                     final PublicKey ballotBoxKey,
                                     final PublicKey ncnKey,
                                     final PublicKey epochSnapshotKey,
                                     final PublicKey operatorSnapshotKey,
                                     final PublicKey operatorKey,
                                     final PublicKey operatorVoterKey,
                                     final byte[] metaMerkleRoot,
                                     final long epoch) {
    final var keys = List.of(
      createWrite(epochStateKey),
      createRead(configKey),
      createWrite(ballotBoxKey),
      createRead(ncnKey),
      createRead(epochSnapshotKey),
      createRead(operatorSnapshotKey),
      createRead(operatorKey),
      createReadOnlySigner(operatorVoterKey)
    );

    final byte[] _data = new byte[16 + Borsh.lenArray(metaMerkleRoot)];
    int i = CAST_VOTE_DISCRIMINATOR.write(_data, 0);
    i += Borsh.writeArray(metaMerkleRoot, _data, i);
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record CastVoteIxData(Discriminator discriminator, byte[] metaMerkleRoot, long epoch) implements Borsh {  

    public static CastVoteIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 48;
    public static final int META_MERKLE_ROOT_LEN = 32;

    public static CastVoteIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var metaMerkleRoot = new byte[32];
      i += Borsh.readArray(metaMerkleRoot, _data, i);
      final var epoch = getInt64LE(_data, i);
      return new CastVoteIxData(discriminator, metaMerkleRoot, epoch);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeArray(metaMerkleRoot, _data, i);
      putInt64LE(_data, i, epoch);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_MERKLE_ROOT_DISCRIMINATOR = toDiscriminator(43, 24, 91, 60, 240, 137, 28, 102);

  public static Instruction setMerkleRoot(final AccountMeta invokedJitoTipRouterProgramMeta,
                                          final PublicKey epochStateKey,
                                          final PublicKey configKey,
                                          final PublicKey ncnKey,
                                          final PublicKey ballotBoxKey,
                                          final PublicKey voteAccountKey,
                                          final PublicKey tipDistributionAccountKey,
                                          final PublicKey tipDistributionConfigKey,
                                          final PublicKey tipDistributionProgramKey,
                                          final byte[][] proof,
                                          final byte[] merkleRoot,
                                          final long maxTotalClaim,
                                          final long maxNumNodes,
                                          final long epoch) {
    final var keys = List.of(
      createWrite(epochStateKey),
      createWrite(configKey),
      createRead(ncnKey),
      createRead(ballotBoxKey),
      createRead(voteAccountKey),
      createWrite(tipDistributionAccountKey),
      createRead(tipDistributionConfigKey),
      createRead(tipDistributionProgramKey)
    );

    final byte[] _data = new byte[32 + Borsh.lenVectorArray(proof) + Borsh.lenArray(merkleRoot)];
    int i = SET_MERKLE_ROOT_DISCRIMINATOR.write(_data, 0);
    i += Borsh.writeVectorArray(proof, _data, i);
    i += Borsh.writeArray(merkleRoot, _data, i);
    putInt64LE(_data, i, maxTotalClaim);
    i += 8;
    putInt64LE(_data, i, maxNumNodes);
    i += 8;
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record SetMerkleRootIxData(Discriminator discriminator,
                                    byte[][] proof,
                                    byte[] merkleRoot,
                                    long maxTotalClaim,
                                    long maxNumNodes,
                                    long epoch) implements Borsh {  

    public static SetMerkleRootIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int MERKLE_ROOT_LEN = 32;
    public static SetMerkleRootIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var proof = Borsh.readMultiDimensionbyteVectorArray(32, _data, i);
      i += Borsh.lenVectorArray(proof);
      final var merkleRoot = new byte[32];
      i += Borsh.readArray(merkleRoot, _data, i);
      final var maxTotalClaim = getInt64LE(_data, i);
      i += 8;
      final var maxNumNodes = getInt64LE(_data, i);
      i += 8;
      final var epoch = getInt64LE(_data, i);
      return new SetMerkleRootIxData(discriminator,
                                     proof,
                                     merkleRoot,
                                     maxTotalClaim,
                                     maxNumNodes,
                                     epoch);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVectorArray(proof, _data, i);
      i += Borsh.writeArray(merkleRoot, _data, i);
      putInt64LE(_data, i, maxTotalClaim);
      i += 8;
      putInt64LE(_data, i, maxNumNodes);
      i += 8;
      putInt64LE(_data, i, epoch);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVectorArray(proof)
           + Borsh.lenArray(merkleRoot)
           + 8
           + 8
           + 8;
    }
  }

  public static final Discriminator INITIALIZE_BASE_REWARD_ROUTER_DISCRIMINATOR = toDiscriminator(34, 137, 180, 147, 32, 48, 48, 207);

  public static Instruction initializeBaseRewardRouter(final AccountMeta invokedJitoTipRouterProgramMeta,
                                                       final PublicKey epochMarkerKey,
                                                       final PublicKey epochStateKey,
                                                       final PublicKey ncnKey,
                                                       final PublicKey baseRewardRouterKey,
                                                       final PublicKey baseRewardReceiverKey,
                                                       final PublicKey accountPayerKey,
                                                       final PublicKey systemProgramKey,
                                                       final long epoch) {
    final var keys = List.of(
      createRead(epochMarkerKey),
      createRead(epochStateKey),
      createRead(ncnKey),
      createWrite(baseRewardRouterKey),
      createWrite(baseRewardReceiverKey),
      createWrite(accountPayerKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = INITIALIZE_BASE_REWARD_ROUTER_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record InitializeBaseRewardRouterIxData(Discriminator discriminator, long epoch) implements Borsh {  

    public static InitializeBaseRewardRouterIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static InitializeBaseRewardRouterIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var epoch = getInt64LE(_data, i);
      return new InitializeBaseRewardRouterIxData(discriminator, epoch);
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

  public static final Discriminator REALLOC_BASE_REWARD_ROUTER_DISCRIMINATOR = toDiscriminator(244, 189, 12, 81, 193, 5, 120, 142);

  public static Instruction reallocBaseRewardRouter(final AccountMeta invokedJitoTipRouterProgramMeta,
                                                    final PublicKey epochStateKey,
                                                    final PublicKey configKey,
                                                    final PublicKey baseRewardRouterKey,
                                                    final PublicKey ncnKey,
                                                    final PublicKey accountPayerKey,
                                                    final PublicKey systemProgramKey,
                                                    final long epoch) {
    final var keys = List.of(
      createWrite(epochStateKey),
      createRead(configKey),
      createWrite(baseRewardRouterKey),
      createRead(ncnKey),
      createWrite(accountPayerKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = REALLOC_BASE_REWARD_ROUTER_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record ReallocBaseRewardRouterIxData(Discriminator discriminator, long epoch) implements Borsh {  

    public static ReallocBaseRewardRouterIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static ReallocBaseRewardRouterIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var epoch = getInt64LE(_data, i);
      return new ReallocBaseRewardRouterIxData(discriminator, epoch);
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

  public static final Discriminator INITIALIZE_NCN_REWARD_ROUTER_DISCRIMINATOR = toDiscriminator(119, 239, 24, 199, 250, 175, 253, 121);

  public static Instruction initializeNcnRewardRouter(final AccountMeta invokedJitoTipRouterProgramMeta,
                                                      final PublicKey epochMarkerKey,
                                                      final PublicKey epochStateKey,
                                                      final PublicKey ncnKey,
                                                      final PublicKey operatorKey,
                                                      final PublicKey operatorSnapshotKey,
                                                      final PublicKey ncnRewardRouterKey,
                                                      final PublicKey ncnRewardReceiverKey,
                                                      final PublicKey accountPayerKey,
                                                      final PublicKey systemProgramKey,
                                                      final int ncnFeeGroup,
                                                      final long epoch) {
    final var keys = List.of(
      createRead(epochMarkerKey),
      createWrite(epochStateKey),
      createRead(ncnKey),
      createRead(operatorKey),
      createRead(operatorSnapshotKey),
      createWrite(ncnRewardRouterKey),
      createWrite(ncnRewardReceiverKey),
      createWrite(accountPayerKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[17];
    int i = INITIALIZE_NCN_REWARD_ROUTER_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) ncnFeeGroup;
    ++i;
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record InitializeNcnRewardRouterIxData(Discriminator discriminator, int ncnFeeGroup, long epoch) implements Borsh {  

    public static InitializeNcnRewardRouterIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static InitializeNcnRewardRouterIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var ncnFeeGroup = _data[i] & 0xFF;
      ++i;
      final var epoch = getInt64LE(_data, i);
      return new InitializeNcnRewardRouterIxData(discriminator, ncnFeeGroup, epoch);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) ncnFeeGroup;
      ++i;
      putInt64LE(_data, i, epoch);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ROUTE_BASE_REWARDS_DISCRIMINATOR = toDiscriminator(199, 208, 14, 171, 131, 157, 206, 156);

  public static Instruction routeBaseRewards(final AccountMeta invokedJitoTipRouterProgramMeta,
                                             final PublicKey epochStateKey,
                                             final PublicKey configKey,
                                             final PublicKey ncnKey,
                                             final PublicKey epochSnapshotKey,
                                             final PublicKey ballotBoxKey,
                                             final PublicKey baseRewardRouterKey,
                                             final PublicKey baseRewardReceiverKey,
                                             final int maxIterations,
                                             final long epoch) {
    final var keys = List.of(
      createWrite(epochStateKey),
      createRead(configKey),
      createRead(ncnKey),
      createRead(epochSnapshotKey),
      createRead(ballotBoxKey),
      createWrite(baseRewardRouterKey),
      createWrite(baseRewardReceiverKey)
    );

    final byte[] _data = new byte[18];
    int i = ROUTE_BASE_REWARDS_DISCRIMINATOR.write(_data, 0);
    putInt16LE(_data, i, maxIterations);
    i += 2;
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record RouteBaseRewardsIxData(Discriminator discriminator, int maxIterations, long epoch) implements Borsh {  

    public static RouteBaseRewardsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 18;

    public static RouteBaseRewardsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var maxIterations = getInt16LE(_data, i);
      i += 2;
      final var epoch = getInt64LE(_data, i);
      return new RouteBaseRewardsIxData(discriminator, maxIterations, epoch);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, maxIterations);
      i += 2;
      putInt64LE(_data, i, epoch);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ROUTE_NCN_REWARDS_DISCRIMINATOR = toDiscriminator(193, 58, 111, 20, 1, 120, 97, 211);

  public static Instruction routeNcnRewards(final AccountMeta invokedJitoTipRouterProgramMeta,
                                            final PublicKey epochStateKey,
                                            final PublicKey ncnKey,
                                            final PublicKey operatorKey,
                                            final PublicKey operatorSnapshotKey,
                                            final PublicKey ncnRewardRouterKey,
                                            final PublicKey ncnRewardReceiverKey,
                                            final int ncnFeeGroup,
                                            final int maxIterations,
                                            final long epoch) {
    final var keys = List.of(
      createWrite(epochStateKey),
      createRead(ncnKey),
      createRead(operatorKey),
      createRead(operatorSnapshotKey),
      createWrite(ncnRewardRouterKey),
      createWrite(ncnRewardReceiverKey)
    );

    final byte[] _data = new byte[19];
    int i = ROUTE_NCN_REWARDS_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) ncnFeeGroup;
    ++i;
    putInt16LE(_data, i, maxIterations);
    i += 2;
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record RouteNcnRewardsIxData(Discriminator discriminator,
                                      int ncnFeeGroup,
                                      int maxIterations,
                                      long epoch) implements Borsh {  

    public static RouteNcnRewardsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 19;

    public static RouteNcnRewardsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var ncnFeeGroup = _data[i] & 0xFF;
      ++i;
      final var maxIterations = getInt16LE(_data, i);
      i += 2;
      final var epoch = getInt64LE(_data, i);
      return new RouteNcnRewardsIxData(discriminator, ncnFeeGroup, maxIterations, epoch);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) ncnFeeGroup;
      ++i;
      putInt16LE(_data, i, maxIterations);
      i += 2;
      putInt64LE(_data, i, epoch);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DISTRIBUTE_BASE_REWARDS_DISCRIMINATOR = toDiscriminator(4, 195, 158, 109, 242, 93, 102, 90);

  public static Instruction distributeBaseRewards(final AccountMeta invokedJitoTipRouterProgramMeta,
                                                  final PublicKey epochStateKey,
                                                  final PublicKey configKey,
                                                  final PublicKey ncnKey,
                                                  final PublicKey baseRewardRouterKey,
                                                  final PublicKey baseRewardReceiverKey,
                                                  final PublicKey baseFeeWalletKey,
                                                  final PublicKey baseFeeWalletAtaKey,
                                                  final PublicKey stakePoolProgramKey,
                                                  final PublicKey stakePoolKey,
                                                  final PublicKey stakePoolWithdrawAuthorityKey,
                                                  final PublicKey reserveStakeKey,
                                                  final PublicKey managerFeeAccountKey,
                                                  final PublicKey referrerPoolTokensAccountKey,
                                                  final PublicKey poolMintKey,
                                                  final PublicKey tokenProgramKey,
                                                  final PublicKey systemProgramKey,
                                                  final int baseFeeGroup,
                                                  final long epoch) {
    final var keys = List.of(
      createWrite(epochStateKey),
      createRead(configKey),
      createRead(ncnKey),
      createWrite(baseRewardRouterKey),
      createWrite(baseRewardReceiverKey),
      createRead(baseFeeWalletKey),
      createWrite(baseFeeWalletAtaKey),
      createRead(stakePoolProgramKey),
      createWrite(stakePoolKey),
      createRead(stakePoolWithdrawAuthorityKey),
      createWrite(reserveStakeKey),
      createWrite(managerFeeAccountKey),
      createWrite(referrerPoolTokensAccountKey),
      createWrite(poolMintKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[17];
    int i = DISTRIBUTE_BASE_REWARDS_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) baseFeeGroup;
    ++i;
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record DistributeBaseRewardsIxData(Discriminator discriminator, int baseFeeGroup, long epoch) implements Borsh {  

    public static DistributeBaseRewardsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static DistributeBaseRewardsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var baseFeeGroup = _data[i] & 0xFF;
      ++i;
      final var epoch = getInt64LE(_data, i);
      return new DistributeBaseRewardsIxData(discriminator, baseFeeGroup, epoch);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) baseFeeGroup;
      ++i;
      putInt64LE(_data, i, epoch);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DISTRIBUTE_BASE_NCN_REWARD_ROUTE_DISCRIMINATOR = toDiscriminator(239, 25, 84, 146, 249, 147, 251, 177);

  public static Instruction distributeBaseNcnRewardRoute(final AccountMeta invokedJitoTipRouterProgramMeta,
                                                         final PublicKey epochStateKey,
                                                         final PublicKey configKey,
                                                         final PublicKey ncnKey,
                                                         final PublicKey operatorKey,
                                                         final PublicKey baseRewardRouterKey,
                                                         final PublicKey baseRewardReceiverKey,
                                                         final PublicKey ncnRewardRouterKey,
                                                         final PublicKey ncnRewardReceiverKey,
                                                         final PublicKey systemProgramKey,
                                                         final int ncnFeeGroup,
                                                         final long epoch) {
    final var keys = List.of(
      createWrite(epochStateKey),
      createRead(configKey),
      createRead(ncnKey),
      createRead(operatorKey),
      createWrite(baseRewardRouterKey),
      createWrite(baseRewardReceiverKey),
      createRead(ncnRewardRouterKey),
      createWrite(ncnRewardReceiverKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[17];
    int i = DISTRIBUTE_BASE_NCN_REWARD_ROUTE_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) ncnFeeGroup;
    ++i;
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record DistributeBaseNcnRewardRouteIxData(Discriminator discriminator, int ncnFeeGroup, long epoch) implements Borsh {  

    public static DistributeBaseNcnRewardRouteIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static DistributeBaseNcnRewardRouteIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var ncnFeeGroup = _data[i] & 0xFF;
      ++i;
      final var epoch = getInt64LE(_data, i);
      return new DistributeBaseNcnRewardRouteIxData(discriminator, ncnFeeGroup, epoch);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) ncnFeeGroup;
      ++i;
      putInt64LE(_data, i, epoch);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DISTRIBUTE_NCN_OPERATOR_REWARDS_DISCRIMINATOR = toDiscriminator(238, 43, 174, 146, 134, 148, 161, 253);

  public static Instruction distributeNcnOperatorRewards(final AccountMeta invokedJitoTipRouterProgramMeta,
                                                         final PublicKey epochStateKey,
                                                         final PublicKey configKey,
                                                         final PublicKey ncnKey,
                                                         final PublicKey operatorKey,
                                                         final PublicKey operatorAtaKey,
                                                         final PublicKey operatorSnapshotKey,
                                                         final PublicKey ncnRewardRouterKey,
                                                         final PublicKey ncnRewardReceiverKey,
                                                         final PublicKey stakePoolProgramKey,
                                                         final PublicKey stakePoolKey,
                                                         final PublicKey stakePoolWithdrawAuthorityKey,
                                                         final PublicKey reserveStakeKey,
                                                         final PublicKey managerFeeAccountKey,
                                                         final PublicKey referrerPoolTokensAccountKey,
                                                         final PublicKey poolMintKey,
                                                         final PublicKey tokenProgramKey,
                                                         final PublicKey systemProgramKey,
                                                         final int ncnFeeGroup,
                                                         final long epoch) {
    final var keys = List.of(
      createWrite(epochStateKey),
      createRead(configKey),
      createRead(ncnKey),
      createWrite(operatorKey),
      createWrite(operatorAtaKey),
      createWrite(operatorSnapshotKey),
      createWrite(ncnRewardRouterKey),
      createWrite(ncnRewardReceiverKey),
      createRead(stakePoolProgramKey),
      createWrite(stakePoolKey),
      createRead(stakePoolWithdrawAuthorityKey),
      createWrite(reserveStakeKey),
      createWrite(managerFeeAccountKey),
      createWrite(referrerPoolTokensAccountKey),
      createWrite(poolMintKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[17];
    int i = DISTRIBUTE_NCN_OPERATOR_REWARDS_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) ncnFeeGroup;
    ++i;
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record DistributeNcnOperatorRewardsIxData(Discriminator discriminator, int ncnFeeGroup, long epoch) implements Borsh {  

    public static DistributeNcnOperatorRewardsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static DistributeNcnOperatorRewardsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var ncnFeeGroup = _data[i] & 0xFF;
      ++i;
      final var epoch = getInt64LE(_data, i);
      return new DistributeNcnOperatorRewardsIxData(discriminator, ncnFeeGroup, epoch);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) ncnFeeGroup;
      ++i;
      putInt64LE(_data, i, epoch);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DISTRIBUTE_NCN_VAULT_REWARDS_DISCRIMINATOR = toDiscriminator(24, 91, 193, 197, 18, 123, 181, 223);

  public static Instruction distributeNcnVaultRewards(final AccountMeta invokedJitoTipRouterProgramMeta,
                                                      final PublicKey epochStateKey,
                                                      final PublicKey configKey,
                                                      final PublicKey ncnKey,
                                                      final PublicKey operatorKey,
                                                      final PublicKey vaultKey,
                                                      final PublicKey vaultAtaKey,
                                                      final PublicKey operatorSnapshotKey,
                                                      final PublicKey ncnRewardRouterKey,
                                                      final PublicKey ncnRewardReceiverKey,
                                                      final PublicKey stakePoolProgramKey,
                                                      final PublicKey stakePoolKey,
                                                      final PublicKey stakePoolWithdrawAuthorityKey,
                                                      final PublicKey reserveStakeKey,
                                                      final PublicKey managerFeeAccountKey,
                                                      final PublicKey referrerPoolTokensAccountKey,
                                                      final PublicKey poolMintKey,
                                                      final PublicKey tokenProgramKey,
                                                      final PublicKey systemProgramKey,
                                                      final int ncnFeeGroup,
                                                      final long epoch) {
    final var keys = List.of(
      createWrite(epochStateKey),
      createRead(configKey),
      createRead(ncnKey),
      createRead(operatorKey),
      createRead(vaultKey),
      createWrite(vaultAtaKey),
      createWrite(operatorSnapshotKey),
      createWrite(ncnRewardRouterKey),
      createWrite(ncnRewardReceiverKey),
      createRead(stakePoolProgramKey),
      createWrite(stakePoolKey),
      createRead(stakePoolWithdrawAuthorityKey),
      createWrite(reserveStakeKey),
      createWrite(managerFeeAccountKey),
      createWrite(referrerPoolTokensAccountKey),
      createWrite(poolMintKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[17];
    int i = DISTRIBUTE_NCN_VAULT_REWARDS_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) ncnFeeGroup;
    ++i;
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record DistributeNcnVaultRewardsIxData(Discriminator discriminator, int ncnFeeGroup, long epoch) implements Borsh {  

    public static DistributeNcnVaultRewardsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static DistributeNcnVaultRewardsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var ncnFeeGroup = _data[i] & 0xFF;
      ++i;
      final var epoch = getInt64LE(_data, i);
      return new DistributeNcnVaultRewardsIxData(discriminator, ncnFeeGroup, epoch);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) ncnFeeGroup;
      ++i;
      putInt64LE(_data, i, epoch);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CLAIM_WITH_PAYER_DISCRIMINATOR = toDiscriminator(4, 194, 148, 110, 140, 72, 37, 242);

  public static Instruction claimWithPayer(final AccountMeta invokedJitoTipRouterProgramMeta,
                                           final PublicKey accountPayerKey,
                                           final PublicKey configKey,
                                           final PublicKey ncnKey,
                                           final PublicKey tipDistributionConfigKey,
                                           final PublicKey tipDistributionAccountKey,
                                           final PublicKey claimStatusKey,
                                           final PublicKey claimantKey,
                                           final PublicKey tipDistributionProgramKey,
                                           final PublicKey systemProgramKey,
                                           final byte[][] proof,
                                           final long amount,
                                           final int bump) {
    final var keys = List.of(
      createWrite(accountPayerKey),
      createRead(configKey),
      createRead(ncnKey),
      createRead(tipDistributionConfigKey),
      createWrite(tipDistributionAccountKey),
      createWrite(claimStatusKey),
      createWrite(claimantKey),
      createRead(tipDistributionProgramKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[17 + Borsh.lenVectorArray(proof)];
    int i = CLAIM_WITH_PAYER_DISCRIMINATOR.write(_data, 0);
    i += Borsh.writeVectorArray(proof, _data, i);
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) bump;

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record ClaimWithPayerIxData(Discriminator discriminator,
                                     byte[][] proof,
                                     long amount,
                                     int bump) implements Borsh {  

    public static ClaimWithPayerIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ClaimWithPayerIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var proof = Borsh.readMultiDimensionbyteVectorArray(32, _data, i);
      i += Borsh.lenVectorArray(proof);
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var bump = _data[i] & 0xFF;
      return new ClaimWithPayerIxData(discriminator, proof, amount, bump);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVectorArray(proof, _data, i);
      putInt64LE(_data, i, amount);
      i += 8;
      _data[i] = (byte) bump;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVectorArray(proof) + 8 + 1;
    }
  }

  public static final Discriminator CLOSE_EPOCH_ACCOUNT_DISCRIMINATOR = toDiscriminator(142, 189, 100, 11, 115, 13, 139, 178);

  public static Instruction closeEpochAccount(final AccountMeta invokedJitoTipRouterProgramMeta,
                                              final PublicKey epochMarkerKey,
                                              final PublicKey epochStateKey,
                                              final PublicKey configKey,
                                              final PublicKey ncnKey,
                                              final PublicKey accountToCloseKey,
                                              final PublicKey accountPayerKey,
                                              final PublicKey daoWalletKey,
                                              final PublicKey systemProgramKey,
                                              final PublicKey receiverToCloseKey,
                                              final long epoch) {
    final var keys = List.of(
      createWrite(epochMarkerKey),
      createWrite(epochStateKey),
      createRead(configKey),
      createRead(ncnKey),
      createWrite(accountToCloseKey),
      createWrite(accountPayerKey),
      createWrite(daoWalletKey),
      createRead(systemProgramKey),
      createWrite(requireNonNullElse(receiverToCloseKey, invokedJitoTipRouterProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[16];
    int i = CLOSE_EPOCH_ACCOUNT_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record CloseEpochAccountIxData(Discriminator discriminator, long epoch) implements Borsh {  

    public static CloseEpochAccountIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static CloseEpochAccountIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var epoch = getInt64LE(_data, i);
      return new CloseEpochAccountIxData(discriminator, epoch);
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

  public static final Discriminator ADMIN_SET_PARAMETERS_DISCRIMINATOR = toDiscriminator(221, 186, 58, 203, 129, 199, 74, 188);

  public static Instruction adminSetParameters(final AccountMeta invokedJitoTipRouterProgramMeta,
                                               final PublicKey configKey,
                                               final PublicKey ncnKey,
                                               final PublicKey ncnAdminKey,
                                               final OptionalLong startingValidEpoch,
                                               final OptionalLong epochsBeforeStall,
                                               final OptionalLong epochsAfterConsensusBeforeClose,
                                               final OptionalLong validSlotsAfterConsensus) {
    final var keys = List.of(
      createWrite(configKey),
      createRead(ncnKey),
      createReadOnlySigner(ncnAdminKey)
    );

    final byte[] _data = new byte[
        8
        + (startingValidEpoch == null || startingValidEpoch.isEmpty() ? 1 : 9)
        + (epochsBeforeStall == null || epochsBeforeStall.isEmpty() ? 1 : 9)
        + (epochsAfterConsensusBeforeClose == null || epochsAfterConsensusBeforeClose.isEmpty() ? 1 : 9)
        + (validSlotsAfterConsensus == null || validSlotsAfterConsensus.isEmpty() ? 1 : 9)
    ];
    int i = ADMIN_SET_PARAMETERS_DISCRIMINATOR.write(_data, 0);
    i += Borsh.writeOptional(startingValidEpoch, _data, i);
    i += Borsh.writeOptional(epochsBeforeStall, _data, i);
    i += Borsh.writeOptional(epochsAfterConsensusBeforeClose, _data, i);
    Borsh.writeOptional(validSlotsAfterConsensus, _data, i);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record AdminSetParametersIxData(Discriminator discriminator,
                                         OptionalLong startingValidEpoch,
                                         OptionalLong epochsBeforeStall,
                                         OptionalLong epochsAfterConsensusBeforeClose,
                                         OptionalLong validSlotsAfterConsensus) implements Borsh {  

    public static AdminSetParametersIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static AdminSetParametersIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var startingValidEpoch = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
      if (startingValidEpoch.isPresent()) {
        i += 8;
      }
      final var epochsBeforeStall = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
      if (epochsBeforeStall.isPresent()) {
        i += 8;
      }
      final var epochsAfterConsensusBeforeClose = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
      if (epochsAfterConsensusBeforeClose.isPresent()) {
        i += 8;
      }
      final var validSlotsAfterConsensus = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
      return new AdminSetParametersIxData(discriminator,
                                          startingValidEpoch,
                                          epochsBeforeStall,
                                          epochsAfterConsensusBeforeClose,
                                          validSlotsAfterConsensus);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeOptional(startingValidEpoch, _data, i);
      i += Borsh.writeOptional(epochsBeforeStall, _data, i);
      i += Borsh.writeOptional(epochsAfterConsensusBeforeClose, _data, i);
      i += Borsh.writeOptional(validSlotsAfterConsensus, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + (startingValidEpoch == null || startingValidEpoch.isEmpty() ? 1 : (1 + 8)) + (epochsBeforeStall == null || epochsBeforeStall.isEmpty() ? 1 : (1 + 8)) + (epochsAfterConsensusBeforeClose == null || epochsAfterConsensusBeforeClose.isEmpty() ? 1 : (1 + 8)) + (validSlotsAfterConsensus == null || validSlotsAfterConsensus.isEmpty() ? 1 : (1 + 8));
    }
  }

  public static final Discriminator ADMIN_SET_CONFIG_FEES_DISCRIMINATOR = toDiscriminator(182, 62, 14, 164, 183, 202, 55, 143);

  public static Instruction adminSetConfigFees(final AccountMeta invokedJitoTipRouterProgramMeta,
                                               final PublicKey configKey,
                                               final PublicKey ncnKey,
                                               final PublicKey ncnAdminKey,
                                               final OptionalInt newBlockEngineFeeBps,
                                               final OptionalInt baseFeeGroup,
                                               final PublicKey newBaseFeeWallet,
                                               final OptionalInt newBaseFeeBps,
                                               final OptionalInt ncnFeeGroup,
                                               final OptionalInt newNcnFeeBps,
                                               final OptionalInt newPriorityFeeDistributionFeeBps) {
    final var keys = List.of(
      createWrite(configKey),
      createRead(ncnKey),
      createReadOnlySigner(ncnAdminKey)
    );

    final byte[] _data = new byte[
        8
        + (newBlockEngineFeeBps == null || newBlockEngineFeeBps.isEmpty() ? 1 : 3)
        + (baseFeeGroup == null || baseFeeGroup.isEmpty() ? 1 : 2)
        + (newBaseFeeWallet == null ? 1 : 33)
        + (newBaseFeeBps == null || newBaseFeeBps.isEmpty() ? 1 : 3)
        + (ncnFeeGroup == null || ncnFeeGroup.isEmpty() ? 1 : 2)
        + (newNcnFeeBps == null || newNcnFeeBps.isEmpty() ? 1 : 3)
        + (newPriorityFeeDistributionFeeBps == null || newPriorityFeeDistributionFeeBps.isEmpty() ? 1 : 3)
    ];
    int i = ADMIN_SET_CONFIG_FEES_DISCRIMINATOR.write(_data, 0);
    i += Borsh.writeOptionalshort(newBlockEngineFeeBps, _data, i);
    i += Borsh.writeOptionalbyte(baseFeeGroup, _data, i);
    i += Borsh.writeOptional(newBaseFeeWallet, _data, i);
    i += Borsh.writeOptionalshort(newBaseFeeBps, _data, i);
    i += Borsh.writeOptionalbyte(ncnFeeGroup, _data, i);
    i += Borsh.writeOptionalshort(newNcnFeeBps, _data, i);
    Borsh.writeOptionalshort(newPriorityFeeDistributionFeeBps, _data, i);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record AdminSetConfigFeesIxData(Discriminator discriminator,
                                         OptionalInt newBlockEngineFeeBps,
                                         OptionalInt baseFeeGroup,
                                         PublicKey newBaseFeeWallet,
                                         OptionalInt newBaseFeeBps,
                                         OptionalInt ncnFeeGroup,
                                         OptionalInt newNcnFeeBps,
                                         OptionalInt newPriorityFeeDistributionFeeBps) implements Borsh {  

    public static AdminSetConfigFeesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static AdminSetConfigFeesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var newBlockEngineFeeBps = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
      if (newBlockEngineFeeBps.isPresent()) {
        i += 2;
      }
      final var baseFeeGroup = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
      if (baseFeeGroup.isPresent()) {
        ++i;
      }
      final var newBaseFeeWallet = _data[i++] == 0 ? null : readPubKey(_data, i);
      if (newBaseFeeWallet != null) {
        i += 32;
      }
      final var newBaseFeeBps = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
      if (newBaseFeeBps.isPresent()) {
        i += 2;
      }
      final var ncnFeeGroup = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
      if (ncnFeeGroup.isPresent()) {
        ++i;
      }
      final var newNcnFeeBps = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
      if (newNcnFeeBps.isPresent()) {
        i += 2;
      }
      final var newPriorityFeeDistributionFeeBps = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
      return new AdminSetConfigFeesIxData(discriminator,
                                          newBlockEngineFeeBps,
                                          baseFeeGroup,
                                          newBaseFeeWallet,
                                          newBaseFeeBps,
                                          ncnFeeGroup,
                                          newNcnFeeBps,
                                          newPriorityFeeDistributionFeeBps);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeOptionalshort(newBlockEngineFeeBps, _data, i);
      i += Borsh.writeOptionalbyte(baseFeeGroup, _data, i);
      i += Borsh.writeOptional(newBaseFeeWallet, _data, i);
      i += Borsh.writeOptionalshort(newBaseFeeBps, _data, i);
      i += Borsh.writeOptionalbyte(ncnFeeGroup, _data, i);
      i += Borsh.writeOptionalshort(newNcnFeeBps, _data, i);
      i += Borsh.writeOptionalshort(newPriorityFeeDistributionFeeBps, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + (newBlockEngineFeeBps == null || newBlockEngineFeeBps.isEmpty() ? 1 : (1 + 2))
           + (baseFeeGroup == null || baseFeeGroup.isEmpty() ? 1 : (1 + 1))
           + (newBaseFeeWallet == null ? 1 : (1 + 32))
           + (newBaseFeeBps == null || newBaseFeeBps.isEmpty() ? 1 : (1 + 2))
           + (ncnFeeGroup == null || ncnFeeGroup.isEmpty() ? 1 : (1 + 1))
           + (newNcnFeeBps == null || newNcnFeeBps.isEmpty() ? 1 : (1 + 2))
           + (newPriorityFeeDistributionFeeBps == null || newPriorityFeeDistributionFeeBps.isEmpty() ? 1 : (1 + 2));
    }
  }

  public static final Discriminator ADMIN_SET_NEW_ADMIN_DISCRIMINATOR = toDiscriminator(218, 112, 199, 167, 48, 21, 49, 147);

  public static Instruction adminSetNewAdmin(final AccountMeta invokedJitoTipRouterProgramMeta,
                                             final PublicKey configKey,
                                             final PublicKey ncnKey,
                                             final PublicKey ncnAdminKey,
                                             final PublicKey newAdminKey,
                                             final ConfigAdminRole role) {
    final var keys = List.of(
      createWrite(configKey),
      createRead(ncnKey),
      createReadOnlySigner(ncnAdminKey),
      createRead(newAdminKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(role)];
    int i = ADMIN_SET_NEW_ADMIN_DISCRIMINATOR.write(_data, 0);
    Borsh.write(role, _data, i);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record AdminSetNewAdminIxData(Discriminator discriminator, ConfigAdminRole role) implements Borsh {  

    public static AdminSetNewAdminIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static AdminSetNewAdminIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var role = ConfigAdminRole.read(_data, i);
      return new AdminSetNewAdminIxData(discriminator, role);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(role, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ADMIN_SET_TIE_BREAKER_DISCRIMINATOR = toDiscriminator(78, 119, 82, 120, 43, 53, 27, 26);

  public static Instruction adminSetTieBreaker(final AccountMeta invokedJitoTipRouterProgramMeta,
                                               final PublicKey epochStateKey,
                                               final PublicKey configKey,
                                               final PublicKey ballotBoxKey,
                                               final PublicKey ncnKey,
                                               final PublicKey tieBreakerAdminKey,
                                               final byte[] metaMerkleRoot,
                                               final long epoch) {
    final var keys = List.of(
      createWrite(epochStateKey),
      createRead(configKey),
      createWrite(ballotBoxKey),
      createRead(ncnKey),
      createReadOnlySigner(tieBreakerAdminKey)
    );

    final byte[] _data = new byte[16 + Borsh.lenArray(metaMerkleRoot)];
    int i = ADMIN_SET_TIE_BREAKER_DISCRIMINATOR.write(_data, 0);
    i += Borsh.writeArray(metaMerkleRoot, _data, i);
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record AdminSetTieBreakerIxData(Discriminator discriminator, byte[] metaMerkleRoot, long epoch) implements Borsh {  

    public static AdminSetTieBreakerIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 48;
    public static final int META_MERKLE_ROOT_LEN = 32;

    public static AdminSetTieBreakerIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var metaMerkleRoot = new byte[32];
      i += Borsh.readArray(metaMerkleRoot, _data, i);
      final var epoch = getInt64LE(_data, i);
      return new AdminSetTieBreakerIxData(discriminator, metaMerkleRoot, epoch);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeArray(metaMerkleRoot, _data, i);
      putInt64LE(_data, i, epoch);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ADMIN_SET_WEIGHT_DISCRIMINATOR = toDiscriminator(38, 226, 136, 223, 236, 62, 12, 112);

  public static Instruction adminSetWeight(final AccountMeta invokedJitoTipRouterProgramMeta,
                                           final PublicKey epochStateKey,
                                           final PublicKey ncnKey,
                                           final PublicKey weightTableKey,
                                           final PublicKey weightTableAdminKey,
                                           final PublicKey stMint,
                                           final BigInteger weight,
                                           final long epoch) {
    final var keys = List.of(
      createWrite(epochStateKey),
      createRead(ncnKey),
      createWrite(weightTableKey),
      createReadOnlySigner(weightTableAdminKey)
    );

    final byte[] _data = new byte[64];
    int i = ADMIN_SET_WEIGHT_DISCRIMINATOR.write(_data, 0);
    stMint.write(_data, i);
    i += 32;
    putInt128LE(_data, i, weight);
    i += 16;
    putInt64LE(_data, i, epoch);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record AdminSetWeightIxData(Discriminator discriminator,
                                     PublicKey stMint,
                                     BigInteger weight,
                                     long epoch) implements Borsh {  

    public static AdminSetWeightIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 64;

    public static AdminSetWeightIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var stMint = readPubKey(_data, i);
      i += 32;
      final var weight = getInt128LE(_data, i);
      i += 16;
      final var epoch = getInt64LE(_data, i);
      return new AdminSetWeightIxData(discriminator, stMint, weight, epoch);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      stMint.write(_data, i);
      i += 32;
      putInt128LE(_data, i, weight);
      i += 16;
      putInt64LE(_data, i, epoch);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ADMIN_REGISTER_ST_MINT_DISCRIMINATOR = toDiscriminator(196, 31, 85, 50, 146, 63, 254, 114);

  public static Instruction adminRegisterStMint(final AccountMeta invokedJitoTipRouterProgramMeta,
                                                final PublicKey configKey,
                                                final PublicKey ncnKey,
                                                final PublicKey stMintKey,
                                                final PublicKey vaultRegistryKey,
                                                final PublicKey adminKey,
                                                final int ncnFeeGroup,
                                                final long rewardMultiplierBps,
                                                final PublicKey switchboardFeed,
                                                final BigInteger noFeedWeight) {
    final var keys = List.of(
      createRead(configKey),
      createRead(ncnKey),
      createRead(stMintKey),
      createWrite(vaultRegistryKey),
      createWritableSigner(adminKey)
    );

    final byte[] _data = new byte[
        17
        + (switchboardFeed == null ? 1 : 33)
        + (noFeedWeight == null ? 1 : 17)
    ];
    int i = ADMIN_REGISTER_ST_MINT_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) ncnFeeGroup;
    ++i;
    putInt64LE(_data, i, rewardMultiplierBps);
    i += 8;
    i += Borsh.writeOptional(switchboardFeed, _data, i);
    Borsh.write128Optional(noFeedWeight, _data, i);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record AdminRegisterStMintIxData(Discriminator discriminator,
                                          int ncnFeeGroup,
                                          long rewardMultiplierBps,
                                          PublicKey switchboardFeed,
                                          BigInteger noFeedWeight) implements Borsh {  

    public static AdminRegisterStMintIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static AdminRegisterStMintIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var ncnFeeGroup = _data[i] & 0xFF;
      ++i;
      final var rewardMultiplierBps = getInt64LE(_data, i);
      i += 8;
      final var switchboardFeed = _data[i++] == 0 ? null : readPubKey(_data, i);
      if (switchboardFeed != null) {
        i += 32;
      }
      final var noFeedWeight = _data[i++] == 0 ? null : getInt128LE(_data, i);
      return new AdminRegisterStMintIxData(discriminator,
                                           ncnFeeGroup,
                                           rewardMultiplierBps,
                                           switchboardFeed,
                                           noFeedWeight);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) ncnFeeGroup;
      ++i;
      putInt64LE(_data, i, rewardMultiplierBps);
      i += 8;
      i += Borsh.writeOptional(switchboardFeed, _data, i);
      i += Borsh.write128Optional(noFeedWeight, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 1 + 8 + (switchboardFeed == null ? 1 : (1 + 32)) + (noFeedWeight == null ? 1 : (1 + 16));
    }
  }

  public static final Discriminator ADMIN_SET_ST_MINT_DISCRIMINATOR = toDiscriminator(138, 116, 242, 67, 88, 160, 234, 212);

  public static Instruction adminSetStMint(final AccountMeta invokedJitoTipRouterProgramMeta,
                                           final PublicKey configKey,
                                           final PublicKey ncnKey,
                                           final PublicKey vaultRegistryKey,
                                           final PublicKey adminKey,
                                           final PublicKey stMint,
                                           final OptionalInt ncnFeeGroup,
                                           final OptionalLong rewardMultiplierBps,
                                           final PublicKey switchboardFeed,
                                           final BigInteger noFeedWeight) {
    final var keys = List.of(
      createRead(configKey),
      createRead(ncnKey),
      createWrite(vaultRegistryKey),
      createWritableSigner(adminKey)
    );

    final byte[] _data = new byte[
        40
        + (ncnFeeGroup == null || ncnFeeGroup.isEmpty() ? 1 : 2)
        + (rewardMultiplierBps == null || rewardMultiplierBps.isEmpty() ? 1 : 9)
        + (switchboardFeed == null ? 1 : 33)
        + (noFeedWeight == null ? 1 : 17)
    ];
    int i = ADMIN_SET_ST_MINT_DISCRIMINATOR.write(_data, 0);
    stMint.write(_data, i);
    i += 32;
    i += Borsh.writeOptionalbyte(ncnFeeGroup, _data, i);
    i += Borsh.writeOptional(rewardMultiplierBps, _data, i);
    i += Borsh.writeOptional(switchboardFeed, _data, i);
    Borsh.write128Optional(noFeedWeight, _data, i);

    return Instruction.createInstruction(invokedJitoTipRouterProgramMeta, keys, _data);
  }

  public record AdminSetStMintIxData(Discriminator discriminator,
                                     PublicKey stMint,
                                     OptionalInt ncnFeeGroup,
                                     OptionalLong rewardMultiplierBps,
                                     PublicKey switchboardFeed,
                                     BigInteger noFeedWeight) implements Borsh {  

    public static AdminSetStMintIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static AdminSetStMintIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var stMint = readPubKey(_data, i);
      i += 32;
      final var ncnFeeGroup = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
      if (ncnFeeGroup.isPresent()) {
        ++i;
      }
      final var rewardMultiplierBps = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
      if (rewardMultiplierBps.isPresent()) {
        i += 8;
      }
      final var switchboardFeed = _data[i++] == 0 ? null : readPubKey(_data, i);
      if (switchboardFeed != null) {
        i += 32;
      }
      final var noFeedWeight = _data[i++] == 0 ? null : getInt128LE(_data, i);
      return new AdminSetStMintIxData(discriminator,
                                      stMint,
                                      ncnFeeGroup,
                                      rewardMultiplierBps,
                                      switchboardFeed,
                                      noFeedWeight);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      stMint.write(_data, i);
      i += 32;
      i += Borsh.writeOptionalbyte(ncnFeeGroup, _data, i);
      i += Borsh.writeOptional(rewardMultiplierBps, _data, i);
      i += Borsh.writeOptional(switchboardFeed, _data, i);
      i += Borsh.write128Optional(noFeedWeight, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 32
           + (ncnFeeGroup == null || ncnFeeGroup.isEmpty() ? 1 : (1 + 1))
           + (rewardMultiplierBps == null || rewardMultiplierBps.isEmpty() ? 1 : (1 + 8))
           + (switchboardFeed == null ? 1 : (1 + 32))
           + (noFeedWeight == null ? 1 : (1 + 16));
    }
  }

  private JitoTipRouterProgram() {
  }
}
