package software.sava.anchor.programs.jito.steward.anchor;

import java.util.List;
import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.anchor.programs.jito.steward.anchor.types.AuthorityType;
import software.sava.anchor.programs.jito.steward.anchor.types.PreferredValidatorType;
import software.sava.anchor.programs.jito.steward.anchor.types.UpdateParametersArgs;
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
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class StewardProgram {

  public static final Discriminator ADD_VALIDATOR_TO_POOL_DISCRIMINATOR = toDiscriminator(181, 6, 29, 25, 192, 211, 190, 187);

  public static Instruction addValidatorToPool(final AccountMeta invokedStewardProgramMeta,
                                               final PublicKey configKey,
                                               final PublicKey stateAccountKey,
                                               final PublicKey stakePoolProgramKey,
                                               final PublicKey stakePoolKey,
                                               final PublicKey reserveStakeKey,
                                               final PublicKey withdrawAuthorityKey,
                                               final PublicKey validatorListKey,
                                               final PublicKey stakeAccountKey,
                                               final PublicKey voteAccountKey,
                                               final PublicKey rentKey,
                                               final PublicKey clockKey,
                                               final PublicKey stakeHistoryKey,
                                               final PublicKey stakeConfigKey,
                                               final PublicKey systemProgramKey,
                                               final PublicKey stakeProgramKey,
                                               final PublicKey adminKey,
                                               final OptionalInt validatorSeed) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(stateAccountKey),
      createRead(stakePoolProgramKey),
      createWrite(stakePoolKey),
      createWrite(reserveStakeKey),
      createRead(withdrawAuthorityKey),
      createWrite(validatorListKey),
      createWrite(stakeAccountKey),
      createRead(voteAccountKey),
      createRead(rentKey),
      createRead(clockKey),
      createRead(stakeHistoryKey),
      createRead(stakeConfigKey),
      createRead(systemProgramKey),
      createRead(stakeProgramKey),
      createWritableSigner(adminKey)
    );

    final byte[] _data = new byte[
        8
        + (validatorSeed == null || validatorSeed.isEmpty() ? 1 : 5)
    ];
    int i = writeDiscriminator(ADD_VALIDATOR_TO_POOL_DISCRIMINATOR, _data, 0);
    Borsh.writeOptional(validatorSeed, _data, i);

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, _data);
  }

  public record AddValidatorToPoolIxData(Discriminator discriminator, OptionalInt validatorSeed) implements Borsh {

    public static AddValidatorToPoolIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var validatorSeed = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
      return new AddValidatorToPoolIxData(discriminator, validatorSeed);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeOptional(validatorSeed, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + (validatorSeed == null || validatorSeed.isEmpty() ? 1 : (1 + 4));
    }
  }

  public static final Discriminator ADD_VALIDATORS_TO_BLACKLIST_DISCRIMINATOR = toDiscriminator(204, 81, 61, 86, 100, 92, 226, 86);

  public static Instruction addValidatorsToBlacklist(final AccountMeta invokedStewardProgramMeta,
                                                     final PublicKey configKey,
                                                     final PublicKey authorityKey,
                                                     final int[] validatorHistoryBlacklist) {
    final var keys = List.of(
      createWrite(configKey),
      createWritableSigner(authorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.lenVector(validatorHistoryBlacklist)];
    int i = writeDiscriminator(ADD_VALIDATORS_TO_BLACKLIST_DISCRIMINATOR, _data, 0);
    Borsh.writeVector(validatorHistoryBlacklist, _data, i);

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, _data);
  }

  public record AddValidatorsToBlacklistIxData(Discriminator discriminator, int[] validatorHistoryBlacklist) implements Borsh {

    public static AddValidatorsToBlacklistIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var validatorHistoryBlacklist = Borsh.readintVector(_data, i);
      return new AddValidatorsToBlacklistIxData(discriminator, validatorHistoryBlacklist);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(validatorHistoryBlacklist, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(validatorHistoryBlacklist);
    }
  }

  public static final Discriminator ADMIN_MARK_FOR_REMOVAL_DISCRIMINATOR = toDiscriminator(213, 225, 98, 245, 9, 15, 154, 63);

  public static Instruction adminMarkForRemoval(final AccountMeta invokedStewardProgramMeta,
                                                final PublicKey configKey,
                                                final PublicKey stateAccountKey,
                                                final PublicKey authorityKey,
                                                final long validatorListIndex,
                                                final int markForRemoval,
                                                final int immediate) {
    final var keys = List.of(
      createWrite(configKey),
      createWrite(stateAccountKey),
      createWritableSigner(authorityKey)
    );

    final byte[] _data = new byte[18];
    int i = writeDiscriminator(ADMIN_MARK_FOR_REMOVAL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, validatorListIndex);
    i += 8;
    _data[i] = (byte) markForRemoval;
    ++i;
    _data[i] = (byte) immediate;

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, _data);
  }

  public record AdminMarkForRemovalIxData(Discriminator discriminator,
                                          long validatorListIndex,
                                          int markForRemoval,
                                          int immediate) implements Borsh {

    public static final int BYTES = 18;

    public static AdminMarkForRemovalIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var validatorListIndex = getInt64LE(_data, i);
      i += 8;
      final var markForRemoval = _data[i] & 0xFF;
      ++i;
      final var immediate = _data[i] & 0xFF;
      return new AdminMarkForRemovalIxData(discriminator, validatorListIndex, markForRemoval, immediate);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, validatorListIndex);
      i += 8;
      _data[i] = (byte) markForRemoval;
      ++i;
      _data[i] = (byte) immediate;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator AUTO_ADD_VALIDATOR_TO_POOL_DISCRIMINATOR = toDiscriminator(166, 226, 7, 8, 169, 239, 220, 69);

  public static Instruction autoAddValidatorToPool(final AccountMeta invokedStewardProgramMeta,
                                                   final PublicKey configKey,
                                                   final PublicKey stewardStateKey,
                                                   final PublicKey validatorHistoryAccountKey,
                                                   final PublicKey stakePoolKey,
                                                   final PublicKey reserveStakeKey,
                                                   final PublicKey withdrawAuthorityKey,
                                                   final PublicKey validatorListKey,
                                                   final PublicKey stakeAccountKey,
                                                   final PublicKey voteAccountKey,
                                                   final PublicKey stakeHistoryKey,
                                                   final PublicKey stakeConfigKey,
                                                   final PublicKey stakeProgramKey,
                                                   final PublicKey stakePoolProgramKey,
                                                   final PublicKey systemProgramKey,
                                                   final PublicKey rentKey,
                                                   final PublicKey clockKey) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(stewardStateKey),
      createRead(validatorHistoryAccountKey),
      createWrite(stakePoolKey),
      createWrite(reserveStakeKey),
      createRead(withdrawAuthorityKey),
      createWrite(validatorListKey),
      createWrite(stakeAccountKey),
      createRead(voteAccountKey),
      createRead(stakeHistoryKey),
      createRead(stakeConfigKey),
      createRead(stakeProgramKey),
      createRead(stakePoolProgramKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(clockKey)
    );

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, AUTO_ADD_VALIDATOR_TO_POOL_DISCRIMINATOR);
  }

  public static final Discriminator AUTO_REMOVE_VALIDATOR_FROM_POOL_DISCRIMINATOR = toDiscriminator(65, 39, 73, 213, 52, 34, 181, 94);

  public static Instruction autoRemoveValidatorFromPool(final AccountMeta invokedStewardProgramMeta,
                                                        final PublicKey configKey,
                                                        final PublicKey validatorHistoryAccountKey,
                                                        final PublicKey stateAccountKey,
                                                        final PublicKey stakePoolKey,
                                                        final PublicKey reserveStakeKey,
                                                        final PublicKey withdrawAuthorityKey,
                                                        final PublicKey validatorListKey,
                                                        final PublicKey stakeAccountKey,
                                                        // Account may not exist yet so no owner check done
                                                        final PublicKey transientStakeAccountKey,
                                                        final PublicKey voteAccountKey,
                                                        final PublicKey stakeHistoryKey,
                                                        final PublicKey stakeConfigKey,
                                                        final PublicKey stakeProgramKey,
                                                        final PublicKey stakePoolProgramKey,
                                                        final PublicKey systemProgramKey,
                                                        final PublicKey rentKey,
                                                        final PublicKey clockKey,
                                                        final long validatorListIndex) {
    final var keys = List.of(
      createRead(configKey),
      createRead(validatorHistoryAccountKey),
      createWrite(stateAccountKey),
      createWrite(stakePoolKey),
      createWrite(reserveStakeKey),
      createRead(withdrawAuthorityKey),
      createWrite(validatorListKey),
      createWrite(stakeAccountKey),
      createWrite(transientStakeAccountKey),
      createRead(voteAccountKey),
      createRead(stakeHistoryKey),
      createRead(stakeConfigKey),
      createRead(stakeProgramKey),
      createRead(stakePoolProgramKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(clockKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(AUTO_REMOVE_VALIDATOR_FROM_POOL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, validatorListIndex);

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, _data);
  }

  public record AutoRemoveValidatorFromPoolIxData(Discriminator discriminator, long validatorListIndex) implements Borsh {

    public static final int BYTES = 16;

    public static AutoRemoveValidatorFromPoolIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var validatorListIndex = getInt64LE(_data, i);
      return new AutoRemoveValidatorFromPoolIxData(discriminator, validatorListIndex);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, validatorListIndex);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CLOSE_STEWARD_ACCOUNTS_DISCRIMINATOR = toDiscriminator(172, 171, 212, 186, 90, 10, 181, 24);

  public static Instruction closeStewardAccounts(final AccountMeta invokedStewardProgramMeta,
                                                 final PublicKey configKey,
                                                 final PublicKey stateAccountKey,
                                                 final PublicKey authorityKey) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(stateAccountKey),
      createWritableSigner(authorityKey)
    );

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, CLOSE_STEWARD_ACCOUNTS_DISCRIMINATOR);
  }

  public static final Discriminator COMPUTE_DELEGATIONS_DISCRIMINATOR = toDiscriminator(249, 138, 49, 247, 69, 32, 11, 175);

  public static Instruction computeDelegations(final AccountMeta invokedStewardProgramMeta,
                                               final PublicKey configKey,
                                               final PublicKey stateAccountKey,
                                               final PublicKey validatorListKey) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(stateAccountKey),
      createRead(validatorListKey)
    );

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, COMPUTE_DELEGATIONS_DISCRIMINATOR);
  }

  public static final Discriminator COMPUTE_INSTANT_UNSTAKE_DISCRIMINATOR = toDiscriminator(172, 220, 51, 183, 2, 94, 253, 251);

  public static Instruction computeInstantUnstake(final AccountMeta invokedStewardProgramMeta,
                                                  final PublicKey configKey,
                                                  final PublicKey stateAccountKey,
                                                  final PublicKey validatorHistoryKey,
                                                  final PublicKey validatorListKey,
                                                  final PublicKey clusterHistoryKey,
                                                  final long validatorListIndex) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(stateAccountKey),
      createRead(validatorHistoryKey),
      createRead(validatorListKey),
      createRead(clusterHistoryKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(COMPUTE_INSTANT_UNSTAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, validatorListIndex);

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, _data);
  }

  public record ComputeInstantUnstakeIxData(Discriminator discriminator, long validatorListIndex) implements Borsh {

    public static final int BYTES = 16;

    public static ComputeInstantUnstakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var validatorListIndex = getInt64LE(_data, i);
      return new ComputeInstantUnstakeIxData(discriminator, validatorListIndex);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, validatorListIndex);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator COMPUTE_SCORE_DISCRIMINATOR = toDiscriminator(161, 101, 4, 93, 120, 62, 41, 20);

  public static Instruction computeScore(final AccountMeta invokedStewardProgramMeta,
                                         final PublicKey configKey,
                                         final PublicKey stateAccountKey,
                                         final PublicKey validatorHistoryKey,
                                         final PublicKey validatorListKey,
                                         final PublicKey clusterHistoryKey,
                                         final long validatorListIndex) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(stateAccountKey),
      createRead(validatorHistoryKey),
      createRead(validatorListKey),
      createRead(clusterHistoryKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(COMPUTE_SCORE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, validatorListIndex);

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, _data);
  }

  public record ComputeScoreIxData(Discriminator discriminator, long validatorListIndex) implements Borsh {

    public static final int BYTES = 16;

    public static ComputeScoreIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var validatorListIndex = getInt64LE(_data, i);
      return new ComputeScoreIxData(discriminator, validatorListIndex);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, validatorListIndex);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DECREASE_ADDITIONAL_VALIDATOR_STAKE_DISCRIMINATOR = toDiscriminator(90, 22, 113, 73, 21, 229, 33, 83);

  public static Instruction decreaseAdditionalValidatorStake(final AccountMeta invokedStewardProgramMeta,
                                                             final PublicKey configKey,
                                                             final PublicKey stateAccountKey,
                                                             final PublicKey validatorHistoryKey,
                                                             final PublicKey voteAccountKey,
                                                             final PublicKey stakePoolProgramKey,
                                                             final PublicKey stakePoolKey,
                                                             final PublicKey withdrawAuthorityKey,
                                                             final PublicKey validatorListKey,
                                                             final PublicKey reserveStakeKey,
                                                             final PublicKey stakeAccountKey,
                                                             final PublicKey ephemeralStakeAccountKey,
                                                             final PublicKey transientStakeAccountKey,
                                                             final PublicKey clockKey,
                                                             final PublicKey stakeHistoryKey,
                                                             final PublicKey systemProgramKey,
                                                             final PublicKey stakeProgramKey,
                                                             final PublicKey adminKey,
                                                             final long lamports,
                                                             final long transientSeed,
                                                             final long ephemeralSeed) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(stateAccountKey),
      createWrite(validatorHistoryKey),
      createRead(voteAccountKey),
      createRead(stakePoolProgramKey),
      createRead(stakePoolKey),
      createRead(withdrawAuthorityKey),
      createWrite(validatorListKey),
      createWrite(reserveStakeKey),
      createWrite(stakeAccountKey),
      createWrite(ephemeralStakeAccountKey),
      createWrite(transientStakeAccountKey),
      createRead(clockKey),
      createRead(stakeHistoryKey),
      createRead(systemProgramKey),
      createRead(stakeProgramKey),
      createWritableSigner(adminKey)
    );

    final byte[] _data = new byte[32];
    int i = writeDiscriminator(DECREASE_ADDITIONAL_VALIDATOR_STAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);
    i += 8;
    putInt64LE(_data, i, transientSeed);
    i += 8;
    putInt64LE(_data, i, ephemeralSeed);

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, _data);
  }

  public record DecreaseAdditionalValidatorStakeIxData(Discriminator discriminator,
                                                       long lamports,
                                                       long transientSeed,
                                                       long ephemeralSeed) implements Borsh {

    public static final int BYTES = 32;

    public static DecreaseAdditionalValidatorStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      i += 8;
      final var transientSeed = getInt64LE(_data, i);
      i += 8;
      final var ephemeralSeed = getInt64LE(_data, i);
      return new DecreaseAdditionalValidatorStakeIxData(discriminator, lamports, transientSeed, ephemeralSeed);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lamports);
      i += 8;
      putInt64LE(_data, i, transientSeed);
      i += 8;
      putInt64LE(_data, i, ephemeralSeed);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DECREASE_VALIDATOR_STAKE_DISCRIMINATOR = toDiscriminator(145, 203, 107, 123, 71, 63, 35, 225);

  public static Instruction decreaseValidatorStake(final AccountMeta invokedStewardProgramMeta,
                                                   final PublicKey configKey,
                                                   final PublicKey stateAccountKey,
                                                   final PublicKey validatorHistoryKey,
                                                   final PublicKey stakePoolProgramKey,
                                                   final PublicKey stakePoolKey,
                                                   final PublicKey withdrawAuthorityKey,
                                                   final PublicKey validatorListKey,
                                                   final PublicKey reserveStakeKey,
                                                   final PublicKey stakeAccountKey,
                                                   final PublicKey transientStakeAccountKey,
                                                   final PublicKey voteAccountKey,
                                                   final PublicKey clockKey,
                                                   final PublicKey rentKey,
                                                   final PublicKey stakeHistoryKey,
                                                   final PublicKey systemProgramKey,
                                                   final PublicKey stakeProgramKey,
                                                   final PublicKey adminKey,
                                                   final long lamports,
                                                   final long transientSeed) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(stateAccountKey),
      createWrite(validatorHistoryKey),
      createRead(stakePoolProgramKey),
      createWrite(stakePoolKey),
      createRead(withdrawAuthorityKey),
      createWrite(validatorListKey),
      createWrite(reserveStakeKey),
      createWrite(stakeAccountKey),
      createWrite(transientStakeAccountKey),
      createRead(voteAccountKey),
      createRead(clockKey),
      createRead(rentKey),
      createRead(stakeHistoryKey),
      createRead(systemProgramKey),
      createRead(stakeProgramKey),
      createWritableSigner(adminKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(DECREASE_VALIDATOR_STAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);
    i += 8;
    putInt64LE(_data, i, transientSeed);

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, _data);
  }

  public record DecreaseValidatorStakeIxData(Discriminator discriminator, long lamports, long transientSeed) implements Borsh {

    public static final int BYTES = 24;

    public static DecreaseValidatorStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      i += 8;
      final var transientSeed = getInt64LE(_data, i);
      return new DecreaseValidatorStakeIxData(discriminator, lamports, transientSeed);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lamports);
      i += 8;
      putInt64LE(_data, i, transientSeed);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator EPOCH_MAINTENANCE_DISCRIMINATOR = toDiscriminator(208, 225, 211, 82, 219, 242, 58, 200);

  public static Instruction epochMaintenance(final AccountMeta invokedStewardProgramMeta,
                                             final PublicKey configKey,
                                             final PublicKey stateAccountKey,
                                             final PublicKey validatorListKey,
                                             final PublicKey stakePoolKey,
                                             final OptionalLong validatorIndexToRemove) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(stateAccountKey),
      createRead(validatorListKey),
      createRead(stakePoolKey)
    );

    final byte[] _data = new byte[
        8
        + (validatorIndexToRemove == null || validatorIndexToRemove.isEmpty() ? 1 : 9)
    ];
    int i = writeDiscriminator(EPOCH_MAINTENANCE_DISCRIMINATOR, _data, 0);
    Borsh.writeOptional(validatorIndexToRemove, _data, i);

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, _data);
  }

  public record EpochMaintenanceIxData(Discriminator discriminator, OptionalLong validatorIndexToRemove) implements Borsh {

    public static EpochMaintenanceIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var validatorIndexToRemove = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
      return new EpochMaintenanceIxData(discriminator, validatorIndexToRemove);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeOptional(validatorIndexToRemove, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + (validatorIndexToRemove == null || validatorIndexToRemove.isEmpty() ? 1 : (1 + 8));
    }
  }

  public static final Discriminator IDLE_DISCRIMINATOR = toDiscriminator(200, 79, 16, 41, 251, 91, 239, 83);

  public static Instruction idle(final AccountMeta invokedStewardProgramMeta,
                                 final PublicKey configKey,
                                 final PublicKey stateAccountKey,
                                 final PublicKey validatorListKey) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(stateAccountKey),
      createRead(validatorListKey)
    );

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, IDLE_DISCRIMINATOR);
  }

  public static final Discriminator INCREASE_ADDITIONAL_VALIDATOR_STAKE_DISCRIMINATOR = toDiscriminator(93, 136, 94, 230, 32, 54, 167, 242);

  public static Instruction increaseAdditionalValidatorStake(final AccountMeta invokedStewardProgramMeta,
                                                             final PublicKey configKey,
                                                             final PublicKey stateAccountKey,
                                                             final PublicKey validatorHistoryKey,
                                                             final PublicKey stakePoolProgramKey,
                                                             final PublicKey stakePoolKey,
                                                             final PublicKey withdrawAuthorityKey,
                                                             final PublicKey validatorListKey,
                                                             final PublicKey reserveStakeKey,
                                                             final PublicKey ephemeralStakeAccountKey,
                                                             final PublicKey transientStakeAccountKey,
                                                             final PublicKey stakeAccountKey,
                                                             final PublicKey voteAccountKey,
                                                             final PublicKey clockKey,
                                                             final PublicKey stakeHistoryKey,
                                                             final PublicKey stakeConfigKey,
                                                             final PublicKey systemProgramKey,
                                                             final PublicKey stakeProgramKey,
                                                             final PublicKey adminKey,
                                                             final long lamports,
                                                             final long transientSeed,
                                                             final long ephemeralSeed) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(stateAccountKey),
      createWrite(validatorHistoryKey),
      createRead(stakePoolProgramKey),
      createRead(stakePoolKey),
      createRead(withdrawAuthorityKey),
      createWrite(validatorListKey),
      createWrite(reserveStakeKey),
      createWrite(ephemeralStakeAccountKey),
      createWrite(transientStakeAccountKey),
      createRead(stakeAccountKey),
      createRead(voteAccountKey),
      createRead(clockKey),
      createRead(stakeHistoryKey),
      createRead(stakeConfigKey),
      createRead(systemProgramKey),
      createRead(stakeProgramKey),
      createWritableSigner(adminKey)
    );

    final byte[] _data = new byte[32];
    int i = writeDiscriminator(INCREASE_ADDITIONAL_VALIDATOR_STAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);
    i += 8;
    putInt64LE(_data, i, transientSeed);
    i += 8;
    putInt64LE(_data, i, ephemeralSeed);

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, _data);
  }

  public record IncreaseAdditionalValidatorStakeIxData(Discriminator discriminator,
                                                       long lamports,
                                                       long transientSeed,
                                                       long ephemeralSeed) implements Borsh {

    public static final int BYTES = 32;

    public static IncreaseAdditionalValidatorStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      i += 8;
      final var transientSeed = getInt64LE(_data, i);
      i += 8;
      final var ephemeralSeed = getInt64LE(_data, i);
      return new IncreaseAdditionalValidatorStakeIxData(discriminator, lamports, transientSeed, ephemeralSeed);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lamports);
      i += 8;
      putInt64LE(_data, i, transientSeed);
      i += 8;
      putInt64LE(_data, i, ephemeralSeed);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INCREASE_VALIDATOR_STAKE_DISCRIMINATOR = toDiscriminator(5, 121, 50, 243, 14, 159, 97, 6);

  public static Instruction increaseValidatorStake(final AccountMeta invokedStewardProgramMeta,
                                                   final PublicKey configKey,
                                                   final PublicKey stateAccountKey,
                                                   final PublicKey validatorHistoryKey,
                                                   final PublicKey stakePoolProgramKey,
                                                   final PublicKey stakePoolKey,
                                                   final PublicKey withdrawAuthorityKey,
                                                   final PublicKey validatorListKey,
                                                   final PublicKey reserveStakeKey,
                                                   final PublicKey transientStakeAccountKey,
                                                   final PublicKey stakeAccountKey,
                                                   final PublicKey voteAccountKey,
                                                   final PublicKey clockKey,
                                                   final PublicKey rentKey,
                                                   final PublicKey stakeHistoryKey,
                                                   final PublicKey stakeConfigKey,
                                                   final PublicKey systemProgramKey,
                                                   final PublicKey stakeProgramKey,
                                                   final PublicKey adminKey,
                                                   final long lamports,
                                                   final long transientSeed) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(stateAccountKey),
      createWrite(validatorHistoryKey),
      createRead(stakePoolProgramKey),
      createWrite(stakePoolKey),
      createRead(withdrawAuthorityKey),
      createWrite(validatorListKey),
      createWrite(reserveStakeKey),
      createWrite(transientStakeAccountKey),
      createWrite(stakeAccountKey),
      createRead(voteAccountKey),
      createRead(clockKey),
      createRead(rentKey),
      createRead(stakeHistoryKey),
      createRead(stakeConfigKey),
      createRead(systemProgramKey),
      createRead(stakeProgramKey),
      createWritableSigner(adminKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(INCREASE_VALIDATOR_STAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);
    i += 8;
    putInt64LE(_data, i, transientSeed);

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, _data);
  }

  public record IncreaseValidatorStakeIxData(Discriminator discriminator, long lamports, long transientSeed) implements Borsh {

    public static final int BYTES = 24;

    public static IncreaseValidatorStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      i += 8;
      final var transientSeed = getInt64LE(_data, i);
      return new IncreaseValidatorStakeIxData(discriminator, lamports, transientSeed);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lamports);
      i += 8;
      putInt64LE(_data, i, transientSeed);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_STEWARD_DISCRIMINATOR = toDiscriminator(195, 182, 16, 84, 217, 58, 220, 175);

  public static Instruction initializeSteward(final AccountMeta invokedStewardProgramMeta,
                                              final PublicKey configKey,
                                              final PublicKey stateAccountKey,
                                              final PublicKey stakePoolKey,
                                              final PublicKey stakePoolProgramKey,
                                              final PublicKey systemProgramKey,
                                              final PublicKey currentStakerKey,
                                              final UpdateParametersArgs updateParametersArgs) {
    final var keys = List.of(
      createWritableSigner(configKey),
      createWrite(stateAccountKey),
      createWrite(stakePoolKey),
      createRead(stakePoolProgramKey),
      createRead(systemProgramKey),
      createWritableSigner(currentStakerKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(updateParametersArgs)];
    int i = writeDiscriminator(INITIALIZE_STEWARD_DISCRIMINATOR, _data, 0);
    Borsh.write(updateParametersArgs, _data, i);

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, _data);
  }

  public record InitializeStewardIxData(Discriminator discriminator, UpdateParametersArgs updateParametersArgs) implements Borsh {

    public static InitializeStewardIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var updateParametersArgs = UpdateParametersArgs.read(_data, i);
      return new InitializeStewardIxData(discriminator, updateParametersArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(updateParametersArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(updateParametersArgs);
    }
  }

  public static final Discriminator INSTANT_REMOVE_VALIDATOR_DISCRIMINATOR = toDiscriminator(119, 127, 216, 135, 24, 63, 229, 242);

  public static Instruction instantRemoveValidator(final AccountMeta invokedStewardProgramMeta,
                                                   final PublicKey configKey,
                                                   final PublicKey stateAccountKey,
                                                   final PublicKey validatorListKey,
                                                   final PublicKey stakePoolKey,
                                                   final long validatorIndexToRemove) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(stateAccountKey),
      createRead(validatorListKey),
      createRead(stakePoolKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(INSTANT_REMOVE_VALIDATOR_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, validatorIndexToRemove);

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, _data);
  }

  public record InstantRemoveValidatorIxData(Discriminator discriminator, long validatorIndexToRemove) implements Borsh {

    public static final int BYTES = 16;

    public static InstantRemoveValidatorIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var validatorIndexToRemove = getInt64LE(_data, i);
      return new InstantRemoveValidatorIxData(discriminator, validatorIndexToRemove);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, validatorIndexToRemove);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator PAUSE_STEWARD_DISCRIMINATOR = toDiscriminator(214, 85, 52, 67, 192, 238, 178, 102);

  public static Instruction pauseSteward(final AccountMeta invokedStewardProgramMeta, final PublicKey configKey, final PublicKey authorityKey) {
    final var keys = List.of(
      createWrite(configKey),
      createWritableSigner(authorityKey)
    );

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, PAUSE_STEWARD_DISCRIMINATOR);
  }

  public static final Discriminator REALLOC_STATE_DISCRIMINATOR = toDiscriminator(67, 181, 233, 214, 215, 148, 245, 126);

  public static Instruction reallocState(final AccountMeta invokedStewardProgramMeta,
                                         final PublicKey stateAccountKey,
                                         final PublicKey configKey,
                                         final PublicKey validatorListKey,
                                         final PublicKey systemProgramKey,
                                         final PublicKey signerKey) {
    final var keys = List.of(
      createWrite(stateAccountKey),
      createRead(configKey),
      createRead(validatorListKey),
      createRead(systemProgramKey),
      createWritableSigner(signerKey)
    );

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, REALLOC_STATE_DISCRIMINATOR);
  }

  public static final Discriminator REBALANCE_DISCRIMINATOR = toDiscriminator(108, 158, 77, 9, 210, 52, 88, 62);

  public static Instruction rebalance(final AccountMeta invokedStewardProgramMeta,
                                      final PublicKey configKey,
                                      final PublicKey stateAccountKey,
                                      final PublicKey validatorHistoryKey,
                                      final PublicKey stakePoolProgramKey,
                                      final PublicKey stakePoolKey,
                                      final PublicKey withdrawAuthorityKey,
                                      final PublicKey validatorListKey,
                                      final PublicKey reserveStakeKey,
                                      final PublicKey stakeAccountKey,
                                      // Account may not exist yet so no owner check done
                                      final PublicKey transientStakeAccountKey,
                                      final PublicKey voteAccountKey,
                                      final PublicKey clockKey,
                                      final PublicKey rentKey,
                                      final PublicKey stakeHistoryKey,
                                      final PublicKey stakeConfigKey,
                                      final PublicKey systemProgramKey,
                                      final PublicKey stakeProgramKey,
                                      final long validatorListIndex) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(stateAccountKey),
      createRead(validatorHistoryKey),
      createRead(stakePoolProgramKey),
      createRead(stakePoolKey),
      createRead(withdrawAuthorityKey),
      createWrite(validatorListKey),
      createWrite(reserveStakeKey),
      createWrite(stakeAccountKey),
      createWrite(transientStakeAccountKey),
      createRead(voteAccountKey),
      createRead(clockKey),
      createRead(rentKey),
      createRead(stakeHistoryKey),
      createRead(stakeConfigKey),
      createRead(systemProgramKey),
      createRead(stakeProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(REBALANCE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, validatorListIndex);

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, _data);
  }

  public record RebalanceIxData(Discriminator discriminator, long validatorListIndex) implements Borsh {

    public static final int BYTES = 16;

    public static RebalanceIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var validatorListIndex = getInt64LE(_data, i);
      return new RebalanceIxData(discriminator, validatorListIndex);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, validatorListIndex);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REMOVE_VALIDATOR_FROM_POOL_DISCRIMINATOR = toDiscriminator(161, 32, 213, 239, 221, 15, 181, 114);

  public static Instruction removeValidatorFromPool(final AccountMeta invokedStewardProgramMeta,
                                                    final PublicKey configKey,
                                                    final PublicKey stateAccountKey,
                                                    final PublicKey stakePoolProgramKey,
                                                    final PublicKey stakePoolKey,
                                                    final PublicKey withdrawAuthorityKey,
                                                    final PublicKey validatorListKey,
                                                    final PublicKey stakeAccountKey,
                                                    final PublicKey transientStakeAccountKey,
                                                    final PublicKey clockKey,
                                                    final PublicKey systemProgramKey,
                                                    final PublicKey stakeProgramKey,
                                                    final PublicKey adminKey,
                                                    final long validatorListIndex) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(stateAccountKey),
      createRead(stakePoolProgramKey),
      createWrite(stakePoolKey),
      createRead(withdrawAuthorityKey),
      createWrite(validatorListKey),
      createWrite(stakeAccountKey),
      createWrite(transientStakeAccountKey),
      createRead(clockKey),
      createRead(systemProgramKey),
      createRead(stakeProgramKey),
      createWritableSigner(adminKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(REMOVE_VALIDATOR_FROM_POOL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, validatorListIndex);

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, _data);
  }

  public record RemoveValidatorFromPoolIxData(Discriminator discriminator, long validatorListIndex) implements Borsh {

    public static final int BYTES = 16;

    public static RemoveValidatorFromPoolIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var validatorListIndex = getInt64LE(_data, i);
      return new RemoveValidatorFromPoolIxData(discriminator, validatorListIndex);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, validatorListIndex);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REMOVE_VALIDATORS_FROM_BLACKLIST_DISCRIMINATOR = toDiscriminator(233, 114, 77, 164, 159, 209, 137, 137);

  public static Instruction removeValidatorsFromBlacklist(final AccountMeta invokedStewardProgramMeta,
                                                          final PublicKey configKey,
                                                          final PublicKey authorityKey,
                                                          final int[] validatorHistoryBlacklist) {
    final var keys = List.of(
      createWrite(configKey),
      createWritableSigner(authorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.lenVector(validatorHistoryBlacklist)];
    int i = writeDiscriminator(REMOVE_VALIDATORS_FROM_BLACKLIST_DISCRIMINATOR, _data, 0);
    Borsh.writeVector(validatorHistoryBlacklist, _data, i);

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, _data);
  }

  public record RemoveValidatorsFromBlacklistIxData(Discriminator discriminator, int[] validatorHistoryBlacklist) implements Borsh {

    public static RemoveValidatorsFromBlacklistIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var validatorHistoryBlacklist = Borsh.readintVector(_data, i);
      return new RemoveValidatorsFromBlacklistIxData(discriminator, validatorHistoryBlacklist);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(validatorHistoryBlacklist, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(validatorHistoryBlacklist);
    }
  }

  public static final Discriminator RESET_STEWARD_STATE_DISCRIMINATOR = toDiscriminator(84, 248, 158, 46, 200, 205, 234, 86);

  public static Instruction resetStewardState(final AccountMeta invokedStewardProgramMeta,
                                              final PublicKey stateAccountKey,
                                              final PublicKey configKey,
                                              final PublicKey stakePoolKey,
                                              final PublicKey validatorListKey,
                                              final PublicKey authorityKey) {
    final var keys = List.of(
      createWrite(stateAccountKey),
      createRead(configKey),
      createRead(stakePoolKey),
      createRead(validatorListKey),
      createWritableSigner(authorityKey)
    );

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, RESET_STEWARD_STATE_DISCRIMINATOR);
  }

  public static final Discriminator RESET_VALIDATOR_LAMPORT_BALANCES_DISCRIMINATOR = toDiscriminator(69, 111, 124, 69, 69, 29, 96, 181);

  public static Instruction resetValidatorLamportBalances(final AccountMeta invokedStewardProgramMeta,
                                                          final PublicKey stewardStateKey,
                                                          final PublicKey configKey,
                                                          final PublicKey authorityKey) {
    final var keys = List.of(
      createWrite(stewardStateKey),
      createRead(configKey),
      createReadOnlySigner(authorityKey)
    );

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, RESET_VALIDATOR_LAMPORT_BALANCES_DISCRIMINATOR);
  }

  public static final Discriminator RESUME_STEWARD_DISCRIMINATOR = toDiscriminator(25, 71, 153, 183, 197, 197, 187, 3);

  public static Instruction resumeSteward(final AccountMeta invokedStewardProgramMeta, final PublicKey configKey, final PublicKey authorityKey) {
    final var keys = List.of(
      createWrite(configKey),
      createWritableSigner(authorityKey)
    );

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, RESUME_STEWARD_DISCRIMINATOR);
  }

  public static final Discriminator SET_NEW_AUTHORITY_DISCRIMINATOR = toDiscriminator(94, 40, 220, 124, 122, 142, 142, 98);

  public static Instruction setNewAuthority(final AccountMeta invokedStewardProgramMeta,
                                            final PublicKey configKey,
                                            final PublicKey newAuthorityKey,
                                            final PublicKey adminKey,
                                            final AuthorityType authorityType) {
    final var keys = List.of(
      createWrite(configKey),
      createRead(newAuthorityKey),
      createWritableSigner(adminKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(authorityType)];
    int i = writeDiscriminator(SET_NEW_AUTHORITY_DISCRIMINATOR, _data, 0);
    Borsh.write(authorityType, _data, i);

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, _data);
  }

  public record SetNewAuthorityIxData(Discriminator discriminator, AuthorityType authorityType) implements Borsh {

    public static SetNewAuthorityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var authorityType = AuthorityType.read(_data, i);
      return new SetNewAuthorityIxData(discriminator, authorityType);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(authorityType, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(authorityType);
    }
  }

  public static final Discriminator SET_PREFERRED_VALIDATOR_DISCRIMINATOR = toDiscriminator(114, 42, 19, 98, 212, 97, 109, 13);

  public static Instruction setPreferredValidator(final AccountMeta invokedStewardProgramMeta,
                                                  final PublicKey configKey,
                                                  final PublicKey stateAccountKey,
                                                  final PublicKey stakePoolProgramKey,
                                                  final PublicKey stakePoolKey,
                                                  final PublicKey validatorListKey,
                                                  final PublicKey adminKey,
                                                  final PreferredValidatorType validatorType,
                                                  final PublicKey validator) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(stateAccountKey),
      createRead(stakePoolProgramKey),
      createWrite(stakePoolKey),
      createRead(validatorListKey),
      createWritableSigner(adminKey)
    );

    final byte[] _data = new byte[
        8 + Borsh.len(validatorType)
        + (validator == null ? 1 : 33)
    ];
    int i = writeDiscriminator(SET_PREFERRED_VALIDATOR_DISCRIMINATOR, _data, 0);
    i += Borsh.write(validatorType, _data, i);
    Borsh.writeOptional(validator, _data, i);

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, _data);
  }

  public record SetPreferredValidatorIxData(Discriminator discriminator, PreferredValidatorType validatorType, PublicKey validator) implements Borsh {

    public static SetPreferredValidatorIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var validatorType = PreferredValidatorType.read(_data, i);
      i += Borsh.len(validatorType);
      final var validator = _data[i++] == 0 ? null : readPubKey(_data, i);
      return new SetPreferredValidatorIxData(discriminator, validatorType, validator);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(validatorType, _data, i);
      i += Borsh.writeOptional(validator, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(validatorType) + (validator == null ? 1 : (1 + 32));
    }
  }

  public static final Discriminator SET_STAKER_DISCRIMINATOR = toDiscriminator(149, 203, 114, 28, 80, 138, 17, 131);

  public static Instruction setStaker(final AccountMeta invokedStewardProgramMeta,
                                      final PublicKey configKey,
                                      final PublicKey stateAccountKey,
                                      final PublicKey stakePoolProgramKey,
                                      final PublicKey stakePoolKey,
                                      final PublicKey newStakerKey,
                                      final PublicKey adminKey) {
    final var keys = List.of(
      createRead(configKey),
      createWrite(stateAccountKey),
      createRead(stakePoolProgramKey),
      createWrite(stakePoolKey),
      createRead(newStakerKey),
      createWritableSigner(adminKey)
    );

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, SET_STAKER_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_PARAMETERS_DISCRIMINATOR = toDiscriminator(116, 107, 24, 207, 101, 49, 213, 77);

  public static Instruction updateParameters(final AccountMeta invokedStewardProgramMeta,
                                             final PublicKey configKey,
                                             final PublicKey authorityKey,
                                             final UpdateParametersArgs updateParametersArgs) {
    final var keys = List.of(
      createWrite(configKey),
      createWritableSigner(authorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(updateParametersArgs)];
    int i = writeDiscriminator(UPDATE_PARAMETERS_DISCRIMINATOR, _data, 0);
    Borsh.write(updateParametersArgs, _data, i);

    return Instruction.createInstruction(invokedStewardProgramMeta, keys, _data);
  }

  public record UpdateParametersIxData(Discriminator discriminator, UpdateParametersArgs updateParametersArgs) implements Borsh {

    public static UpdateParametersIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var updateParametersArgs = UpdateParametersArgs.read(_data, i);
      return new UpdateParametersIxData(discriminator, updateParametersArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(updateParametersArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(updateParametersArgs);
    }
  }

  private StewardProgram() {
  }
}
