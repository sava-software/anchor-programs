package software.sava.anchor.programs.kamino.farms.anchor;

import java.math.BigInteger;

import java.util.List;

import software.sava.anchor.programs.kamino.farms.anchor.types.FarmConfigOption;
import software.sava.anchor.programs.kamino.farms.anchor.types.GlobalConfigOption;
import software.sava.anchor.programs.kamino.farms.anchor.types.LockingMode;
import software.sava.anchor.programs.kamino.farms.anchor.types.RewardType;
import software.sava.anchor.programs.kamino.farms.anchor.types.TimeUnit;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static java.util.Objects.requireNonNullElse;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.anchor.AnchorUtil.writeDiscriminator;
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
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class FarmsProgram {

  public static final Discriminator INITIALIZE_GLOBAL_CONFIG_DISCRIMINATOR = toDiscriminator(113, 216, 122, 131, 225, 209, 22, 55);

  public static Instruction initializeGlobalConfig(final AccountMeta invokedFarmsProgramMeta,
                                                   final PublicKey globalAdminKey,
                                                   final PublicKey globalConfigKey,
                                                   final PublicKey treasuryVaultsAuthorityKey,
                                                   final PublicKey systemProgramKey) {
    final var keys = List.of(
      createWritableSigner(globalAdminKey),
      createWrite(globalConfigKey),
      createRead(treasuryVaultsAuthorityKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, INITIALIZE_GLOBAL_CONFIG_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_GLOBAL_CONFIG_DISCRIMINATOR = toDiscriminator(164, 84, 130, 189, 111, 58, 250, 200);

  public static Instruction updateGlobalConfig(final AccountMeta invokedFarmsProgramMeta,
                                               final PublicKey globalAdminKey,
                                               final PublicKey globalConfigKey,
                                               final int mode,
                                               final byte[] value) {
    final var keys = List.of(
      createReadOnlySigner(globalAdminKey),
      createWrite(globalConfigKey)
    );

    final byte[] _data = new byte[9 + Borsh.lenArray(value)];
    int i = writeDiscriminator(UPDATE_GLOBAL_CONFIG_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mode;
    ++i;
    Borsh.writeArray(value, _data, i);

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, _data);
  }

  public record UpdateGlobalConfigIxData(Discriminator discriminator, int mode, byte[] value) implements Borsh {  

    public static UpdateGlobalConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 41;

    public static UpdateGlobalConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mode = _data[i] & 0xFF;
      ++i;
      final var value = new byte[32];
      Borsh.readArray(value, _data, i);
      return new UpdateGlobalConfigIxData(discriminator, mode, value);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) mode;
      ++i;
      i += Borsh.writeArray(value, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_FARM_DISCRIMINATOR = toDiscriminator(252, 28, 185, 172, 244, 74, 117, 165);

  public static Instruction initializeFarm(final AccountMeta invokedFarmsProgramMeta,
                                           final PublicKey farmAdminKey,
                                           final PublicKey farmStateKey,
                                           final PublicKey globalConfigKey,
                                           final PublicKey farmVaultKey,
                                           final PublicKey farmVaultsAuthorityKey,
                                           final PublicKey tokenMintKey,
                                           final PublicKey tokenProgramKey,
                                           final PublicKey systemProgramKey,
                                           final PublicKey rentKey) {
    final var keys = List.of(
      createWritableSigner(farmAdminKey),
      createWrite(farmStateKey),
      createRead(globalConfigKey),
      createWrite(farmVaultKey),
      createRead(farmVaultsAuthorityKey),
      createRead(tokenMintKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(rentKey)
    );

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, INITIALIZE_FARM_DISCRIMINATOR);
  }

  public static final Discriminator INITIALIZE_FARM_DELEGATED_DISCRIMINATOR = toDiscriminator(250, 84, 101, 25, 51, 77, 204, 91);

  public static Instruction initializeFarmDelegated(final AccountMeta invokedFarmsProgramMeta,
                                                    final PublicKey farmAdminKey,
                                                    final PublicKey farmDelegateKey,
                                                    final PublicKey farmStateKey,
                                                    final PublicKey globalConfigKey,
                                                    final PublicKey farmVaultsAuthorityKey,
                                                    final PublicKey systemProgramKey,
                                                    final PublicKey rentKey) {
    final var keys = List.of(
      createWritableSigner(farmAdminKey),
      createReadOnlySigner(farmDelegateKey),
      createWrite(farmStateKey),
      createRead(globalConfigKey),
      createRead(farmVaultsAuthorityKey),
      createRead(systemProgramKey),
      createRead(rentKey)
    );

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, INITIALIZE_FARM_DELEGATED_DISCRIMINATOR);
  }

  public static final Discriminator INITIALIZE_REWARD_DISCRIMINATOR = toDiscriminator(95, 135, 192, 196, 242, 129, 230, 68);

  public static Instruction initializeReward(final AccountMeta invokedFarmsProgramMeta,
                                             final PublicKey farmAdminKey,
                                             final PublicKey farmStateKey,
                                             final PublicKey globalConfigKey,
                                             final PublicKey rewardMintKey,
                                             final PublicKey rewardVaultKey,
                                             final PublicKey rewardTreasuryVaultKey,
                                             final PublicKey farmVaultsAuthorityKey,
                                             final PublicKey treasuryVaultsAuthorityKey,
                                             final PublicKey tokenProgramKey,
                                             final PublicKey systemProgramKey,
                                             final PublicKey rentKey) {
    final var keys = List.of(
      createWritableSigner(farmAdminKey),
      createWrite(farmStateKey),
      createRead(globalConfigKey),
      createRead(rewardMintKey),
      createWrite(rewardVaultKey),
      createWrite(rewardTreasuryVaultKey),
      createRead(farmVaultsAuthorityKey),
      createRead(treasuryVaultsAuthorityKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(rentKey)
    );

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, INITIALIZE_REWARD_DISCRIMINATOR);
  }

  public static final Discriminator ADD_REWARDS_DISCRIMINATOR = toDiscriminator(88, 186, 25, 227, 38, 137, 81, 23);

  public static Instruction addRewards(final AccountMeta invokedFarmsProgramMeta,
                                       final PublicKey payerKey,
                                       final PublicKey farmStateKey,
                                       final PublicKey rewardMintKey,
                                       final PublicKey rewardVaultKey,
                                       final PublicKey farmVaultsAuthorityKey,
                                       final PublicKey payerRewardTokenAtaKey,
                                       final PublicKey scopePricesKey,
                                       final PublicKey tokenProgramKey,
                                       final long amount,
                                       final long rewardIndex) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createWrite(farmStateKey),
      createRead(rewardMintKey),
      createWrite(rewardVaultKey),
      createRead(farmVaultsAuthorityKey),
      createWrite(payerRewardTokenAtaKey),
      createRead(requireNonNullElse(scopePricesKey, invokedFarmsProgramMeta.publicKey())),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(ADD_REWARDS_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    putInt64LE(_data, i, rewardIndex);

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, _data);
  }

  public record AddRewardsIxData(Discriminator discriminator, long amount, long rewardIndex) implements Borsh {  

    public static AddRewardsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static AddRewardsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var rewardIndex = getInt64LE(_data, i);
      return new AddRewardsIxData(discriminator, amount, rewardIndex);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      putInt64LE(_data, i, rewardIndex);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_FARM_CONFIG_DISCRIMINATOR = toDiscriminator(214, 176, 188, 244, 203, 59, 230, 207);

  public static Instruction updateFarmConfig(final AccountMeta invokedFarmsProgramMeta,
                                             final PublicKey signerKey,
                                             final PublicKey farmStateKey,
                                             final PublicKey scopePricesKey,
                                             final int mode,
                                             final byte[] data) {
    final var keys = List.of(
      createWritableSigner(signerKey),
      createWrite(farmStateKey),
      createRead(requireNonNullElse(scopePricesKey, invokedFarmsProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[14 + Borsh.lenVector(data)];
    int i = writeDiscriminator(UPDATE_FARM_CONFIG_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, mode);
    i += 2;
    Borsh.writeVector(data, _data, i);

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, _data);
  }

  public record UpdateFarmConfigIxData(Discriminator discriminator, int mode, byte[] data) implements Borsh {  

    public static UpdateFarmConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateFarmConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mode = getInt16LE(_data, i);
      i += 2;
      final byte[] data = Borsh.readbyteVector(_data, i);
      return new UpdateFarmConfigIxData(discriminator, mode, data);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, mode);
      i += 2;
      i += Borsh.writeVector(data, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 2 + Borsh.lenVector(data);
    }
  }

  public static final Discriminator INITIALIZE_USER_DISCRIMINATOR = toDiscriminator(111, 17, 185, 250, 60, 122, 38, 254);

  public static Instruction initializeUser(final AccountMeta invokedFarmsProgramMeta,
                                           final PublicKey authorityKey,
                                           final PublicKey payerKey,
                                           final PublicKey ownerKey,
                                           final PublicKey delegateeKey,
                                           final PublicKey userStateKey,
                                           final PublicKey farmStateKey,
                                           final PublicKey systemProgramKey,
                                           final PublicKey rentKey) {
    final var keys = List.of(
      createReadOnlySigner(authorityKey),
      createWritableSigner(payerKey),
      createRead(ownerKey),
      createRead(delegateeKey),
      createWrite(userStateKey),
      createWrite(farmStateKey),
      createRead(systemProgramKey),
      createRead(rentKey)
    );

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, INITIALIZE_USER_DISCRIMINATOR);
  }

  public static final Discriminator TRANSFER_OWNERSHIP_DISCRIMINATOR = toDiscriminator(65, 177, 215, 73, 53, 45, 99, 47);

  public static Instruction transferOwnership(final AccountMeta invokedFarmsProgramMeta,
                                              final PublicKey ownerKey,
                                              final PublicKey userStateKey,
                                              final PublicKey newOwner) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWrite(userStateKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(TRANSFER_OWNERSHIP_DISCRIMINATOR, _data, 0);
    newOwner.write(_data, i);

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, _data);
  }

  public record TransferOwnershipIxData(Discriminator discriminator, PublicKey newOwner) implements Borsh {  

    public static TransferOwnershipIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static TransferOwnershipIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var newOwner = readPubKey(_data, i);
      return new TransferOwnershipIxData(discriminator, newOwner);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      newOwner.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REWARD_USER_ONCE_DISCRIMINATOR = toDiscriminator(219, 137, 57, 22, 94, 186, 96, 114);

  public static Instruction rewardUserOnce(final AccountMeta invokedFarmsProgramMeta,
                                           final PublicKey farmAdminKey,
                                           final PublicKey farmStateKey,
                                           final PublicKey userStateKey,
                                           final long rewardIndex,
                                           final long amount) {
    final var keys = List.of(
      createWritableSigner(farmAdminKey),
      createWrite(farmStateKey),
      createWrite(userStateKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(REWARD_USER_ONCE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, rewardIndex);
    i += 8;
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, _data);
  }

  public record RewardUserOnceIxData(Discriminator discriminator, long rewardIndex, long amount) implements Borsh {  

    public static RewardUserOnceIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static RewardUserOnceIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var rewardIndex = getInt64LE(_data, i);
      i += 8;
      final var amount = getInt64LE(_data, i);
      return new RewardUserOnceIxData(discriminator, rewardIndex, amount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, rewardIndex);
      i += 8;
      putInt64LE(_data, i, amount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REFRESH_FARM_DISCRIMINATOR = toDiscriminator(214, 131, 138, 183, 144, 194, 172, 42);

  public static Instruction refreshFarm(final AccountMeta invokedFarmsProgramMeta, final PublicKey farmStateKey, final PublicKey scopePricesKey) {
    final var keys = List.of(
      createWrite(farmStateKey),
      createRead(requireNonNullElse(scopePricesKey, invokedFarmsProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, REFRESH_FARM_DISCRIMINATOR);
  }

  public static final Discriminator STAKE_DISCRIMINATOR = toDiscriminator(206, 176, 202, 18, 200, 209, 179, 108);

  public static Instruction stake(final AccountMeta invokedFarmsProgramMeta,
                                  final PublicKey ownerKey,
                                  final PublicKey userStateKey,
                                  final PublicKey farmStateKey,
                                  final PublicKey farmVaultKey,
                                  final PublicKey userAtaKey,
                                  final PublicKey tokenMintKey,
                                  final PublicKey scopePricesKey,
                                  final PublicKey tokenProgramKey,
                                  final long amount) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWrite(userStateKey),
      createWrite(farmStateKey),
      createWrite(farmVaultKey),
      createWrite(userAtaKey),
      createRead(tokenMintKey),
      createRead(requireNonNullElse(scopePricesKey, invokedFarmsProgramMeta.publicKey())),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(STAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, _data);
  }

  public record StakeIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static StakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static StakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new StakeIxData(discriminator, amount);
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

  public static final Discriminator SET_STAKE_DELEGATED_DISCRIMINATOR = toDiscriminator(73, 171, 184, 75, 30, 56, 198, 223);

  public static Instruction setStakeDelegated(final AccountMeta invokedFarmsProgramMeta,
                                              final PublicKey delegateAuthorityKey,
                                              final PublicKey userStateKey,
                                              final PublicKey farmStateKey,
                                              final long newAmount) {
    final var keys = List.of(
      createReadOnlySigner(delegateAuthorityKey),
      createWrite(userStateKey),
      createWrite(farmStateKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(SET_STAKE_DELEGATED_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, newAmount);

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, _data);
  }

  public record SetStakeDelegatedIxData(Discriminator discriminator, long newAmount) implements Borsh {  

    public static SetStakeDelegatedIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SetStakeDelegatedIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var newAmount = getInt64LE(_data, i);
      return new SetStakeDelegatedIxData(discriminator, newAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, newAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator HARVEST_REWARD_DISCRIMINATOR = toDiscriminator(68, 200, 228, 233, 184, 32, 226, 188);

  public static Instruction harvestReward(final AccountMeta invokedFarmsProgramMeta,
                                          final PublicKey ownerKey,
                                          final PublicKey userStateKey,
                                          final PublicKey farmStateKey,
                                          final PublicKey globalConfigKey,
                                          final PublicKey rewardMintKey,
                                          final PublicKey userRewardAtaKey,
                                          final PublicKey rewardsVaultKey,
                                          final PublicKey rewardsTreasuryVaultKey,
                                          final PublicKey farmVaultsAuthorityKey,
                                          final PublicKey scopePricesKey,
                                          final PublicKey tokenProgramKey,
                                          final long rewardIndex) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWrite(userStateKey),
      createWrite(farmStateKey),
      createRead(globalConfigKey),
      createRead(rewardMintKey),
      createWrite(userRewardAtaKey),
      createWrite(rewardsVaultKey),
      createWrite(rewardsTreasuryVaultKey),
      createRead(farmVaultsAuthorityKey),
      createRead(requireNonNullElse(scopePricesKey, invokedFarmsProgramMeta.publicKey())),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(HARVEST_REWARD_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, rewardIndex);

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, _data);
  }

  public record HarvestRewardIxData(Discriminator discriminator, long rewardIndex) implements Borsh {  

    public static HarvestRewardIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static HarvestRewardIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var rewardIndex = getInt64LE(_data, i);
      return new HarvestRewardIxData(discriminator, rewardIndex);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, rewardIndex);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UNSTAKE_DISCRIMINATOR = toDiscriminator(90, 95, 107, 42, 205, 124, 50, 225);

  public static Instruction unstake(final AccountMeta invokedFarmsProgramMeta,
                                    final PublicKey ownerKey,
                                    final PublicKey userStateKey,
                                    final PublicKey farmStateKey,
                                    final PublicKey scopePricesKey,
                                    final BigInteger stakeSharesScaled) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWrite(userStateKey),
      createWrite(farmStateKey),
      createRead(requireNonNullElse(scopePricesKey, invokedFarmsProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(UNSTAKE_DISCRIMINATOR, _data, 0);
    putInt128LE(_data, i, stakeSharesScaled);

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, _data);
  }

  public record UnstakeIxData(Discriminator discriminator, BigInteger stakeSharesScaled) implements Borsh {  

    public static UnstakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static UnstakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var stakeSharesScaled = getInt128LE(_data, i);
      return new UnstakeIxData(discriminator, stakeSharesScaled);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt128LE(_data, i, stakeSharesScaled);
      i += 16;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REFRESH_USER_STATE_DISCRIMINATOR = toDiscriminator(1, 135, 12, 62, 243, 140, 77, 108);

  public static Instruction refreshUserState(final AccountMeta invokedFarmsProgramMeta,
                                             final PublicKey userStateKey,
                                             final PublicKey farmStateKey,
                                             final PublicKey scopePricesKey) {
    final var keys = List.of(
      createWrite(userStateKey),
      createWrite(farmStateKey),
      createRead(requireNonNullElse(scopePricesKey, invokedFarmsProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, REFRESH_USER_STATE_DISCRIMINATOR);
  }

  public static final Discriminator WITHDRAW_UNSTAKED_DEPOSITS_DISCRIMINATOR = toDiscriminator(36, 102, 187, 49, 220, 36, 132, 67);

  public static Instruction withdrawUnstakedDeposits(final AccountMeta invokedFarmsProgramMeta,
                                                     final PublicKey ownerKey,
                                                     final PublicKey userStateKey,
                                                     final PublicKey farmStateKey,
                                                     final PublicKey userAtaKey,
                                                     final PublicKey farmVaultKey,
                                                     final PublicKey farmVaultsAuthorityKey,
                                                     final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWrite(userStateKey),
      createWrite(farmStateKey),
      createWrite(userAtaKey),
      createWrite(farmVaultKey),
      createRead(farmVaultsAuthorityKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, WITHDRAW_UNSTAKED_DEPOSITS_DISCRIMINATOR);
  }

  public static final Discriminator WITHDRAW_TREASURY_DISCRIMINATOR = toDiscriminator(40, 63, 122, 158, 144, 216, 83, 96);

  public static Instruction withdrawTreasury(final AccountMeta invokedFarmsProgramMeta,
                                             final PublicKey globalAdminKey,
                                             final PublicKey globalConfigKey,
                                             final PublicKey rewardMintKey,
                                             final PublicKey rewardTreasuryVaultKey,
                                             final PublicKey treasuryVaultAuthorityKey,
                                             final PublicKey withdrawDestinationTokenAccountKey,
                                             final PublicKey tokenProgramKey,
                                             final long amount) {
    final var keys = List.of(
      createWritableSigner(globalAdminKey),
      createRead(globalConfigKey),
      createRead(rewardMintKey),
      createWrite(rewardTreasuryVaultKey),
      createRead(treasuryVaultAuthorityKey),
      createWrite(withdrawDestinationTokenAccountKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(WITHDRAW_TREASURY_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, _data);
  }

  public record WithdrawTreasuryIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static WithdrawTreasuryIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static WithdrawTreasuryIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new WithdrawTreasuryIxData(discriminator, amount);
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

  public static final Discriminator DEPOSIT_TO_FARM_VAULT_DISCRIMINATOR = toDiscriminator(131, 166, 64, 94, 108, 213, 114, 183);

  public static Instruction depositToFarmVault(final AccountMeta invokedFarmsProgramMeta,
                                               final PublicKey depositorKey,
                                               final PublicKey farmStateKey,
                                               final PublicKey farmVaultKey,
                                               final PublicKey depositorAtaKey,
                                               final PublicKey tokenProgramKey,
                                               final long amount) {
    final var keys = List.of(
      createReadOnlySigner(depositorKey),
      createWrite(farmStateKey),
      createWrite(farmVaultKey),
      createWrite(depositorAtaKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(DEPOSIT_TO_FARM_VAULT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, _data);
  }

  public record DepositToFarmVaultIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static DepositToFarmVaultIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static DepositToFarmVaultIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new DepositToFarmVaultIxData(discriminator, amount);
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

  public static final Discriminator WITHDRAW_FROM_FARM_VAULT_DISCRIMINATOR = toDiscriminator(22, 82, 128, 250, 86, 79, 124, 78);

  public static Instruction withdrawFromFarmVault(final AccountMeta invokedFarmsProgramMeta,
                                                  final PublicKey withdrawAuthorityKey,
                                                  final PublicKey farmStateKey,
                                                  final PublicKey withdrawerTokenAccountKey,
                                                  final PublicKey farmVaultKey,
                                                  final PublicKey farmVaultsAuthorityKey,
                                                  final PublicKey tokenProgramKey,
                                                  final long amount) {
    final var keys = List.of(
      createWritableSigner(withdrawAuthorityKey),
      createWrite(farmStateKey),
      createWrite(withdrawerTokenAccountKey),
      createWrite(farmVaultKey),
      createRead(farmVaultsAuthorityKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(WITHDRAW_FROM_FARM_VAULT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, _data);
  }

  public record WithdrawFromFarmVaultIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static WithdrawFromFarmVaultIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static WithdrawFromFarmVaultIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new WithdrawFromFarmVaultIxData(discriminator, amount);
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

  public static final Discriminator WITHDRAW_SLASHED_AMOUNT_DISCRIMINATOR = toDiscriminator(202, 217, 67, 74, 172, 22, 140, 216);

  public static Instruction withdrawSlashedAmount(final AccountMeta invokedFarmsProgramMeta,
                                                  final PublicKey crankKey,
                                                  final PublicKey farmStateKey,
                                                  final PublicKey slashedAmountSpillAddressKey,
                                                  final PublicKey farmVaultKey,
                                                  final PublicKey farmVaultsAuthorityKey,
                                                  final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWritableSigner(crankKey),
      createWrite(farmStateKey),
      createWrite(slashedAmountSpillAddressKey),
      createWrite(farmVaultKey),
      createRead(farmVaultsAuthorityKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, WITHDRAW_SLASHED_AMOUNT_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_FARM_ADMIN_DISCRIMINATOR = toDiscriminator(20, 37, 136, 19, 122, 239, 36, 130);

  public static Instruction updateFarmAdmin(final AccountMeta invokedFarmsProgramMeta, final PublicKey pendingFarmAdminKey, final PublicKey farmStateKey) {
    final var keys = List.of(
      createWritableSigner(pendingFarmAdminKey),
      createWrite(farmStateKey)
    );

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, UPDATE_FARM_ADMIN_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_GLOBAL_CONFIG_ADMIN_DISCRIMINATOR = toDiscriminator(184, 87, 23, 193, 156, 238, 175, 119);

  public static Instruction updateGlobalConfigAdmin(final AccountMeta invokedFarmsProgramMeta, final PublicKey pendingGlobalAdminKey, final PublicKey globalConfigKey) {
    final var keys = List.of(
      createReadOnlySigner(pendingGlobalAdminKey),
      createWrite(globalConfigKey)
    );

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, UPDATE_GLOBAL_CONFIG_ADMIN_DISCRIMINATOR);
  }

  public static final Discriminator WITHDRAW_REWARD_DISCRIMINATOR = toDiscriminator(191, 187, 176, 137, 9, 25, 187, 244);

  public static Instruction withdrawReward(final AccountMeta invokedFarmsProgramMeta,
                                           final PublicKey farmAdminKey,
                                           final PublicKey farmStateKey,
                                           final PublicKey rewardMintKey,
                                           final PublicKey rewardVaultKey,
                                           final PublicKey farmVaultsAuthorityKey,
                                           final PublicKey adminRewardTokenAtaKey,
                                           final PublicKey scopePricesKey,
                                           final PublicKey tokenProgramKey,
                                           final long amount,
                                           final long rewardIndex) {
    final var keys = List.of(
      createWritableSigner(farmAdminKey),
      createWrite(farmStateKey),
      createRead(rewardMintKey),
      createWrite(rewardVaultKey),
      createRead(farmVaultsAuthorityKey),
      createWrite(adminRewardTokenAtaKey),
      createRead(requireNonNullElse(scopePricesKey, invokedFarmsProgramMeta.publicKey())),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(WITHDRAW_REWARD_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    putInt64LE(_data, i, rewardIndex);

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, _data);
  }

  public record WithdrawRewardIxData(Discriminator discriminator, long amount, long rewardIndex) implements Borsh {  

    public static WithdrawRewardIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static WithdrawRewardIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var rewardIndex = getInt64LE(_data, i);
      return new WithdrawRewardIxData(discriminator, amount, rewardIndex);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      putInt64LE(_data, i, rewardIndex);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator IDL_MISSING_TYPES_DISCRIMINATOR = toDiscriminator(130, 80, 38, 153, 80, 212, 182, 253);

  public static Instruction idlMissingTypes(final AccountMeta invokedFarmsProgramMeta,
                                            final PublicKey globalAdminKey,
                                            final PublicKey globalConfigKey,
                                            final GlobalConfigOption globalConfigOptionKind,
                                            final FarmConfigOption farmConfigOptionKind,
                                            final TimeUnit timeUnit,
                                            final LockingMode lockingMode,
                                            final RewardType rewardType) {
    final var keys = List.of(
      createReadOnlySigner(globalAdminKey),
      createWrite(globalConfigKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(globalConfigOptionKind) + Borsh.len(farmConfigOptionKind) + Borsh.len(timeUnit) + Borsh.len(lockingMode) + Borsh.len(rewardType)];
    int i = writeDiscriminator(IDL_MISSING_TYPES_DISCRIMINATOR, _data, 0);
    i += Borsh.write(globalConfigOptionKind, _data, i);
    i += Borsh.write(farmConfigOptionKind, _data, i);
    i += Borsh.write(timeUnit, _data, i);
    i += Borsh.write(lockingMode, _data, i);
    Borsh.write(rewardType, _data, i);

    return Instruction.createInstruction(invokedFarmsProgramMeta, keys, _data);
  }

  public record IdlMissingTypesIxData(Discriminator discriminator,
                                      GlobalConfigOption globalConfigOptionKind,
                                      FarmConfigOption farmConfigOptionKind,
                                      TimeUnit timeUnit,
                                      LockingMode lockingMode,
                                      RewardType rewardType) implements Borsh {  

    public static IdlMissingTypesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 13;

    public static IdlMissingTypesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var globalConfigOptionKind = GlobalConfigOption.read(_data, i);
      i += Borsh.len(globalConfigOptionKind);
      final var farmConfigOptionKind = FarmConfigOption.read(_data, i);
      i += Borsh.len(farmConfigOptionKind);
      final var timeUnit = TimeUnit.read(_data, i);
      i += Borsh.len(timeUnit);
      final var lockingMode = LockingMode.read(_data, i);
      i += Borsh.len(lockingMode);
      final var rewardType = RewardType.read(_data, i);
      return new IdlMissingTypesIxData(discriminator,
                                       globalConfigOptionKind,
                                       farmConfigOptionKind,
                                       timeUnit,
                                       lockingMode,
                                       rewardType);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(globalConfigOptionKind, _data, i);
      i += Borsh.write(farmConfigOptionKind, _data, i);
      i += Borsh.write(timeUnit, _data, i);
      i += Borsh.write(lockingMode, _data, i);
      i += Borsh.write(rewardType, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  private FarmsProgram() {
  }
}
