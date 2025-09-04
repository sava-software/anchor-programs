package software.sava.anchor.programs.kamino.liquidity.anchor;

import java.lang.String;

import java.math.BigInteger;

import java.util.List;

import software.sava.anchor.programs.kamino.liquidity.anchor.types.CollateralInfoParams;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static java.nio.charset.StandardCharsets.UTF_8;

import static java.util.Objects.requireNonNullElse;

import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class YvaultsProgram {

  public static final Discriminator INITIALIZE_STRATEGY_DISCRIMINATOR = toDiscriminator(208, 119, 144, 145, 178, 57, 105, 252);

  public static Instruction initializeStrategy(final AccountMeta invokedYvaultsProgramMeta,
                                               final PublicKey adminAuthorityKey,
                                               final PublicKey globalConfigKey,
                                               // Program owner also checked.
                                               final PublicKey poolKey,
                                               final PublicKey tokenAMintKey,
                                               final PublicKey tokenBMintKey,
                                               final PublicKey tokenAVaultKey,
                                               final PublicKey tokenBVaultKey,
                                               final PublicKey baseVaultAuthorityKey,
                                               final PublicKey sharesMintKey,
                                               final PublicKey sharesMintAuthorityKey,
                                               final PublicKey tokenInfosKey,
                                               final PublicKey systemProgramKey,
                                               final PublicKey rentKey,
                                               final PublicKey tokenProgramKey,
                                               final PublicKey tokenATokenProgramKey,
                                               final PublicKey tokenBTokenProgramKey,
                                               final PublicKey strategyKey,
                                               final long strategyType,
                                               final long tokenACollateralId,
                                               final long tokenBCollateralId) {
    final var keys = List.of(
      createWritableSigner(adminAuthorityKey),
      createRead(globalConfigKey),
      createRead(poolKey),
      createRead(tokenAMintKey),
      createRead(tokenBMintKey),
      createWrite(tokenAVaultKey),
      createWrite(tokenBVaultKey),
      createWrite(baseVaultAuthorityKey),
      createWrite(sharesMintKey),
      createWrite(sharesMintAuthorityKey),
      createRead(tokenInfosKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(tokenProgramKey),
      createRead(tokenATokenProgramKey),
      createRead(tokenBTokenProgramKey),
      createWrite(strategyKey)
    );

    final byte[] _data = new byte[32];
    int i = INITIALIZE_STRATEGY_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, strategyType);
    i += 8;
    putInt64LE(_data, i, tokenACollateralId);
    i += 8;
    putInt64LE(_data, i, tokenBCollateralId);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record InitializeStrategyIxData(Discriminator discriminator,
                                         long strategyType,
                                         long tokenACollateralId,
                                         long tokenBCollateralId) implements Borsh {  

    public static InitializeStrategyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 32;

    public static InitializeStrategyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var strategyType = getInt64LE(_data, i);
      i += 8;
      final var tokenACollateralId = getInt64LE(_data, i);
      i += 8;
      final var tokenBCollateralId = getInt64LE(_data, i);
      return new InitializeStrategyIxData(discriminator, strategyType, tokenACollateralId, tokenBCollateralId);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, strategyType);
      i += 8;
      putInt64LE(_data, i, tokenACollateralId);
      i += 8;
      putInt64LE(_data, i, tokenBCollateralId);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_KAMINO_REWARD_DISCRIMINATOR = toDiscriminator(203, 212, 8, 90, 91, 118, 111, 50);

  public static Instruction initializeKaminoReward(final AccountMeta invokedYvaultsProgramMeta,
                                                   final PublicKey adminAuthorityKey,
                                                   final PublicKey strategyKey,
                                                   final PublicKey globalConfigKey,
                                                   final PublicKey rewardMintKey,
                                                   final PublicKey rewardVaultKey,
                                                   final PublicKey tokenInfosKey,
                                                   final PublicKey baseVaultAuthorityKey,
                                                   final PublicKey systemProgramKey,
                                                   final PublicKey rentKey,
                                                   final PublicKey tokenProgramKey,
                                                   final long kaminoRewardIndex,
                                                   final long collateralToken) {
    final var keys = List.of(
      createWritableSigner(adminAuthorityKey),
      createWrite(strategyKey),
      createRead(globalConfigKey),
      createRead(rewardMintKey),
      createWritableSigner(rewardVaultKey),
      createRead(tokenInfosKey),
      createWrite(baseVaultAuthorityKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[24];
    int i = INITIALIZE_KAMINO_REWARD_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, kaminoRewardIndex);
    i += 8;
    putInt64LE(_data, i, collateralToken);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record InitializeKaminoRewardIxData(Discriminator discriminator, long kaminoRewardIndex, long collateralToken) implements Borsh {  

    public static InitializeKaminoRewardIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static InitializeKaminoRewardIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var kaminoRewardIndex = getInt64LE(_data, i);
      i += 8;
      final var collateralToken = getInt64LE(_data, i);
      return new InitializeKaminoRewardIxData(discriminator, kaminoRewardIndex, collateralToken);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, kaminoRewardIndex);
      i += 8;
      putInt64LE(_data, i, collateralToken);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ADD_KAMINO_REWARDS_DISCRIMINATOR = toDiscriminator(174, 174, 142, 193, 47, 77, 235, 65);

  public static Instruction addKaminoRewards(final AccountMeta invokedYvaultsProgramMeta,
                                             final PublicKey adminAuthorityKey,
                                             final PublicKey strategyKey,
                                             final PublicKey rewardMintKey,
                                             final PublicKey rewardVaultKey,
                                             final PublicKey baseVaultAuthorityKey,
                                             final PublicKey rewardAtaKey,
                                             final PublicKey tokenProgramKey,
                                             final long kaminoRewardIndex,
                                             final long amount) {
    final var keys = List.of(
      createWritableSigner(adminAuthorityKey),
      createWrite(strategyKey),
      createRead(rewardMintKey),
      createWrite(rewardVaultKey),
      createWrite(baseVaultAuthorityKey),
      createWrite(rewardAtaKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[24];
    int i = ADD_KAMINO_REWARDS_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, kaminoRewardIndex);
    i += 8;
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record AddKaminoRewardsIxData(Discriminator discriminator, long kaminoRewardIndex, long amount) implements Borsh {  

    public static AddKaminoRewardsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static AddKaminoRewardsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var kaminoRewardIndex = getInt64LE(_data, i);
      i += 8;
      final var amount = getInt64LE(_data, i);
      return new AddKaminoRewardsIxData(discriminator, kaminoRewardIndex, amount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, kaminoRewardIndex);
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

  public static final Discriminator INITIALIZE_GLOBAL_CONFIG_DISCRIMINATOR = toDiscriminator(113, 216, 122, 131, 225, 209, 22, 55);

  public static Instruction initializeGlobalConfig(final AccountMeta invokedYvaultsProgramMeta,
                                                   final PublicKey adminAuthorityKey,
                                                   final PublicKey globalConfigKey,
                                                   final PublicKey systemProgramKey) {
    final var keys = List.of(
      createWritableSigner(adminAuthorityKey),
      createWrite(globalConfigKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, INITIALIZE_GLOBAL_CONFIG_DISCRIMINATOR);
  }

  public static final Discriminator INITIALIZE_COLLATERAL_INFO_DISCRIMINATOR = toDiscriminator(74, 61, 216, 76, 244, 91, 18, 119);

  public static Instruction initializeCollateralInfo(final AccountMeta invokedYvaultsProgramMeta,
                                                     final PublicKey adminAuthorityKey,
                                                     final PublicKey globalConfigKey,
                                                     final PublicKey collInfoKey,
                                                     final PublicKey systemProgramKey) {
    final var keys = List.of(
      createWritableSigner(adminAuthorityKey),
      createWrite(globalConfigKey),
      createWrite(collInfoKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, INITIALIZE_COLLATERAL_INFO_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_COLLATERAL_INFO_DISCRIMINATOR = toDiscriminator(76, 94, 131, 44, 137, 61, 161, 110);

  public static Instruction updateCollateralInfo(final AccountMeta invokedYvaultsProgramMeta,
                                                 final PublicKey adminAuthorityKey,
                                                 final PublicKey globalConfigKey,
                                                 final PublicKey tokenInfosKey,
                                                 final long index,
                                                 final long mode,
                                                 final byte[] value) {
    final var keys = List.of(
      createWritableSigner(adminAuthorityKey),
      createRead(globalConfigKey),
      createWrite(tokenInfosKey)
    );

    final byte[] _data = new byte[24 + Borsh.lenArray(value)];
    int i = UPDATE_COLLATERAL_INFO_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, index);
    i += 8;
    putInt64LE(_data, i, mode);
    i += 8;
    Borsh.writeArray(value, _data, i);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record UpdateCollateralInfoIxData(Discriminator discriminator,
                                           long index,
                                           long mode,
                                           byte[] value) implements Borsh {  

    public static UpdateCollateralInfoIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 56;
    public static final int VALUE_LEN = 32;

    public static UpdateCollateralInfoIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var index = getInt64LE(_data, i);
      i += 8;
      final var mode = getInt64LE(_data, i);
      i += 8;
      final var value = new byte[32];
      Borsh.readArray(value, _data, i);
      return new UpdateCollateralInfoIxData(discriminator, index, mode, value);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, index);
      i += 8;
      putInt64LE(_data, i, mode);
      i += 8;
      i += Borsh.writeArray(value, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INSERT_COLLATERAL_INFO_DISCRIMINATOR = toDiscriminator(22, 97, 4, 78, 166, 188, 51, 190);

  public static Instruction insertCollateralInfo(final AccountMeta invokedYvaultsProgramMeta,
                                                 final PublicKey adminAuthorityKey,
                                                 final PublicKey globalConfigKey,
                                                 final PublicKey tokenInfosKey,
                                                 final long index,
                                                 final CollateralInfoParams params) {
    final var keys = List.of(
      createWritableSigner(adminAuthorityKey),
      createRead(globalConfigKey),
      createWrite(tokenInfosKey)
    );

    final byte[] _data = new byte[16 + Borsh.len(params)];
    int i = INSERT_COLLATERAL_INFO_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, index);
    i += 8;
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record InsertCollateralInfoIxData(Discriminator discriminator, long index, CollateralInfoParams params) implements Borsh {  

    public static InsertCollateralInfoIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 161;

    public static InsertCollateralInfoIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var index = getInt64LE(_data, i);
      i += 8;
      final var params = CollateralInfoParams.read(_data, i);
      return new InsertCollateralInfoIxData(discriminator, index, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, index);
      i += 8;
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_SHARES_METADATA_DISCRIMINATOR = toDiscriminator(3, 15, 172, 114, 200, 0, 131, 32);

  public static Instruction initializeSharesMetadata(final AccountMeta invokedYvaultsProgramMeta,
                                                     final PublicKey adminAuthorityKey,
                                                     final PublicKey strategyKey,
                                                     final PublicKey globalConfigKey,
                                                     final PublicKey sharesMintKey,
                                                     final PublicKey sharesMetadataKey,
                                                     final PublicKey sharesMintAuthorityKey,
                                                     final PublicKey systemProgramKey,
                                                     final PublicKey rentKey,
                                                     final PublicKey metadataProgramKey,
                                                     final String name,
                                                     final String symbol,
                                                     final String uri) {
    final var keys = List.of(
      createWritableSigner(adminAuthorityKey),
      createRead(strategyKey),
      createRead(globalConfigKey),
      createRead(sharesMintKey),
      createWrite(sharesMetadataKey),
      createRead(sharesMintAuthorityKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(metadataProgramKey)
    );

    final byte[] _name = name.getBytes(UTF_8);
    final byte[] _symbol = symbol.getBytes(UTF_8);
    final byte[] _uri = uri.getBytes(UTF_8);
    final byte[] _data = new byte[20 + Borsh.lenVector(_name) + Borsh.lenVector(_symbol) + Borsh.lenVector(_uri)];
    int i = INITIALIZE_SHARES_METADATA_DISCRIMINATOR.write(_data, 0);
    i += Borsh.writeVector(_name, _data, i);
    i += Borsh.writeVector(_symbol, _data, i);
    Borsh.writeVector(_uri, _data, i);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record InitializeSharesMetadataIxData(Discriminator discriminator,
                                               String name, byte[] _name,
                                               String symbol, byte[] _symbol,
                                               String uri, byte[] _uri) implements Borsh {  

    public static InitializeSharesMetadataIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static InitializeSharesMetadataIxData createRecord(final Discriminator discriminator,
                                                              final String name,
                                                              final String symbol,
                                                              final String uri) {
      return new InitializeSharesMetadataIxData(discriminator, name, name.getBytes(UTF_8), symbol, symbol.getBytes(UTF_8), uri, uri.getBytes(UTF_8));
    }

    public static InitializeSharesMetadataIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var name = Borsh.string(_data, i);
      i += (Integer.BYTES + getInt32LE(_data, i));
      final var symbol = Borsh.string(_data, i);
      i += (Integer.BYTES + getInt32LE(_data, i));
      final var uri = Borsh.string(_data, i);
      return new InitializeSharesMetadataIxData(discriminator, name, name.getBytes(UTF_8), symbol, symbol.getBytes(UTF_8), uri, uri.getBytes(UTF_8));
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(_name, _data, i);
      i += Borsh.writeVector(_symbol, _data, i);
      i += Borsh.writeVector(_uri, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(_name) + Borsh.lenVector(_symbol) + Borsh.lenVector(_uri);
    }
  }

  public static final Discriminator UPDATE_SHARES_METADATA_DISCRIMINATOR = toDiscriminator(155, 34, 122, 165, 245, 137, 147, 107);

  public static Instruction updateSharesMetadata(final AccountMeta invokedYvaultsProgramMeta,
                                                 final PublicKey adminAuthorityKey,
                                                 final PublicKey strategyKey,
                                                 final PublicKey globalConfigKey,
                                                 final PublicKey sharesMintKey,
                                                 final PublicKey sharesMetadataKey,
                                                 final PublicKey sharesMintAuthorityKey,
                                                 final PublicKey metadataProgramKey,
                                                 final String name,
                                                 final String symbol,
                                                 final String uri) {
    final var keys = List.of(
      createWritableSigner(adminAuthorityKey),
      createRead(strategyKey),
      createRead(globalConfigKey),
      createRead(sharesMintKey),
      createWrite(sharesMetadataKey),
      createRead(sharesMintAuthorityKey),
      createRead(metadataProgramKey)
    );

    final byte[] _name = name.getBytes(UTF_8);
    final byte[] _symbol = symbol.getBytes(UTF_8);
    final byte[] _uri = uri.getBytes(UTF_8);
    final byte[] _data = new byte[20 + Borsh.lenVector(_name) + Borsh.lenVector(_symbol) + Borsh.lenVector(_uri)];
    int i = UPDATE_SHARES_METADATA_DISCRIMINATOR.write(_data, 0);
    i += Borsh.writeVector(_name, _data, i);
    i += Borsh.writeVector(_symbol, _data, i);
    Borsh.writeVector(_uri, _data, i);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record UpdateSharesMetadataIxData(Discriminator discriminator,
                                           String name, byte[] _name,
                                           String symbol, byte[] _symbol,
                                           String uri, byte[] _uri) implements Borsh {  

    public static UpdateSharesMetadataIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateSharesMetadataIxData createRecord(final Discriminator discriminator,
                                                          final String name,
                                                          final String symbol,
                                                          final String uri) {
      return new UpdateSharesMetadataIxData(discriminator, name, name.getBytes(UTF_8), symbol, symbol.getBytes(UTF_8), uri, uri.getBytes(UTF_8));
    }

    public static UpdateSharesMetadataIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var name = Borsh.string(_data, i);
      i += (Integer.BYTES + getInt32LE(_data, i));
      final var symbol = Borsh.string(_data, i);
      i += (Integer.BYTES + getInt32LE(_data, i));
      final var uri = Borsh.string(_data, i);
      return new UpdateSharesMetadataIxData(discriminator, name, name.getBytes(UTF_8), symbol, symbol.getBytes(UTF_8), uri, uri.getBytes(UTF_8));
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(_name, _data, i);
      i += Borsh.writeVector(_symbol, _data, i);
      i += Borsh.writeVector(_uri, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(_name) + Borsh.lenVector(_symbol) + Borsh.lenVector(_uri);
    }
  }

  public static final Discriminator UPDATE_GLOBAL_CONFIG_DISCRIMINATOR = toDiscriminator(164, 84, 130, 189, 111, 58, 250, 200);

  public static Instruction updateGlobalConfig(final AccountMeta invokedYvaultsProgramMeta,
                                               final PublicKey adminAuthorityKey,
                                               final PublicKey globalConfigKey,
                                               final PublicKey systemProgramKey,
                                               final int key,
                                               final int index,
                                               final byte[] value) {
    final var keys = List.of(
      createReadOnlySigner(adminAuthorityKey),
      createWrite(globalConfigKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[12 + Borsh.lenArray(value)];
    int i = UPDATE_GLOBAL_CONFIG_DISCRIMINATOR.write(_data, 0);
    putInt16LE(_data, i, key);
    i += 2;
    putInt16LE(_data, i, index);
    i += 2;
    Borsh.writeArray(value, _data, i);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record UpdateGlobalConfigIxData(Discriminator discriminator,
                                         int key,
                                         int index,
                                         byte[] value) implements Borsh {  

    public static UpdateGlobalConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 44;
    public static final int VALUE_LEN = 32;

    public static UpdateGlobalConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var key = getInt16LE(_data, i);
      i += 2;
      final var index = getInt16LE(_data, i);
      i += 2;
      final var value = new byte[32];
      Borsh.readArray(value, _data, i);
      return new UpdateGlobalConfigIxData(discriminator, key, index, value);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, key);
      i += 2;
      putInt16LE(_data, i, index);
      i += 2;
      i += Borsh.writeArray(value, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_TREASURY_FEE_VAULT_DISCRIMINATOR = toDiscriminator(9, 241, 94, 91, 173, 74, 166, 119);

  public static Instruction updateTreasuryFeeVault(final AccountMeta invokedYvaultsProgramMeta,
                                                   final PublicKey signerKey,
                                                   final PublicKey globalConfigKey,
                                                   final PublicKey feeMintKey,
                                                   final PublicKey treasuryFeeVaultKey,
                                                   final PublicKey treasuryFeeVaultAuthorityKey,
                                                   final PublicKey tokenInfosKey,
                                                   final PublicKey systemProgramKey,
                                                   final PublicKey rentKey,
                                                   final PublicKey tokenProgramKey,
                                                   final int collateralId) {
    final var keys = List.of(
      createWritableSigner(signerKey),
      createWrite(globalConfigKey),
      createRead(feeMintKey),
      createWrite(treasuryFeeVaultKey),
      createRead(treasuryFeeVaultAuthorityKey),
      createRead(tokenInfosKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[10];
    int i = UPDATE_TREASURY_FEE_VAULT_DISCRIMINATOR.write(_data, 0);
    putInt16LE(_data, i, collateralId);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record UpdateTreasuryFeeVaultIxData(Discriminator discriminator, int collateralId) implements Borsh {  

    public static UpdateTreasuryFeeVaultIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static UpdateTreasuryFeeVaultIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var collateralId = getInt16LE(_data, i);
      return new UpdateTreasuryFeeVaultIxData(discriminator, collateralId);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, collateralId);
      i += 2;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_STRATEGY_CONFIG_DISCRIMINATOR = toDiscriminator(81, 217, 177, 65, 40, 227, 8, 165);

  public static Instruction updateStrategyConfig(final AccountMeta invokedYvaultsProgramMeta,
                                                 final PublicKey adminAuthorityKey,
                                                 final PublicKey newAccountKey,
                                                 final PublicKey strategyKey,
                                                 final PublicKey globalConfigKey,
                                                 final PublicKey systemProgramKey,
                                                 final int mode,
                                                 final byte[] value) {
    final var keys = List.of(
      createReadOnlySigner(adminAuthorityKey),
      createRead(newAccountKey),
      createWrite(strategyKey),
      createRead(globalConfigKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[10 + Borsh.lenArray(value)];
    int i = UPDATE_STRATEGY_CONFIG_DISCRIMINATOR.write(_data, 0);
    putInt16LE(_data, i, mode);
    i += 2;
    Borsh.writeArray(value, _data, i);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record UpdateStrategyConfigIxData(Discriminator discriminator, int mode, byte[] value) implements Borsh {  

    public static UpdateStrategyConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 138;
    public static final int VALUE_LEN = 128;

    public static UpdateStrategyConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mode = getInt16LE(_data, i);
      i += 2;
      final var value = new byte[128];
      Borsh.readArray(value, _data, i);
      return new UpdateStrategyConfigIxData(discriminator, mode, value);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, mode);
      i += 2;
      i += Borsh.writeArray(value, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_REWARD_MAPPING_DISCRIMINATOR = toDiscriminator(203, 37, 37, 96, 23, 85, 233, 42);

  public static Instruction updateRewardMapping(final AccountMeta invokedYvaultsProgramMeta,
                                                final PublicKey payerKey,
                                                final PublicKey strategyKey,
                                                final PublicKey globalConfigKey,
                                                final PublicKey poolKey,
                                                final PublicKey rewardMintKey,
                                                final PublicKey rewardVaultKey,
                                                final PublicKey baseVaultAuthorityKey,
                                                final PublicKey tokenInfosKey,
                                                final PublicKey systemProgramKey,
                                                final PublicKey rentKey,
                                                final PublicKey tokenProgramKey,
                                                final int rewardIndex,
                                                final int collateralToken) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createWrite(strategyKey),
      createRead(globalConfigKey),
      createRead(poolKey),
      createRead(rewardMintKey),
      createWritableSigner(rewardVaultKey),
      createWrite(baseVaultAuthorityKey),
      createRead(tokenInfosKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[10];
    int i = UPDATE_REWARD_MAPPING_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) rewardIndex;
    ++i;
    _data[i] = (byte) collateralToken;

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record UpdateRewardMappingIxData(Discriminator discriminator, int rewardIndex, int collateralToken) implements Borsh {  

    public static UpdateRewardMappingIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static UpdateRewardMappingIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var rewardIndex = _data[i] & 0xFF;
      ++i;
      final var collateralToken = _data[i] & 0xFF;
      return new UpdateRewardMappingIxData(discriminator, rewardIndex, collateralToken);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) rewardIndex;
      ++i;
      _data[i] = (byte) collateralToken;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator OPEN_LIQUIDITY_POSITION_DISCRIMINATOR = toDiscriminator(204, 234, 204, 219, 6, 91, 96, 241);

  public static Instruction openLiquidityPosition(final AccountMeta invokedYvaultsProgramMeta,
                                                  final PublicKey adminAuthorityKey,
                                                  final PublicKey strategyKey,
                                                  final PublicKey globalConfigKey,
                                                  final PublicKey poolKey,
                                                  final PublicKey tickArrayLowerKey,
                                                  final PublicKey tickArrayUpperKey,
                                                  final PublicKey baseVaultAuthorityKey,
                                                  final PublicKey positionKey,
                                                  final PublicKey positionMintKey,
                                                  final PublicKey positionMetadataAccountKey,
                                                  final PublicKey positionTokenAccountKey,
                                                  final PublicKey rentKey,
                                                  final PublicKey systemKey,
                                                  final PublicKey tokenProgramKey,
                                                  final PublicKey tokenProgram2022Key,
                                                  final PublicKey tokenATokenProgramKey,
                                                  final PublicKey tokenBTokenProgramKey,
                                                  final PublicKey memoProgramKey,
                                                  final PublicKey associatedTokenProgramKey,
                                                  final PublicKey poolProgramKey,
                                                  final PublicKey oldTickArrayLowerOrBaseVaultAuthorityKey,
                                                  final PublicKey oldTickArrayUpperOrBaseVaultAuthorityKey,
                                                  final PublicKey oldPositionOrBaseVaultAuthorityKey,
                                                  final PublicKey oldPositionMintOrBaseVaultAuthorityKey,
                                                  final PublicKey oldPositionTokenAccountOrBaseVaultAuthorityKey,
                                                  final PublicKey tokenAVaultKey,
                                                  final PublicKey tokenBVaultKey,
                                                  final PublicKey tokenAMintKey,
                                                  final PublicKey tokenBMintKey,
                                                  final PublicKey poolTokenVaultAKey,
                                                  final PublicKey poolTokenVaultBKey,
                                                  final PublicKey scopePricesKey,
                                                  final PublicKey tokenInfosKey,
                                                  final PublicKey eventAuthorityKey,
                                                  final PublicKey consensusAccountKey,
                                                  final long tickLowerIndex,
                                                  final long tickUpperIndex,
                                                  final int bump) {
    final var keys = List.of(
      createWritableSigner(adminAuthorityKey),
      createWrite(strategyKey),
      createRead(globalConfigKey),
      createWrite(poolKey),
      createWrite(tickArrayLowerKey),
      createWrite(tickArrayUpperKey),
      createWrite(baseVaultAuthorityKey),
      createWrite(positionKey),
      createWrite(positionMintKey),
      createWrite(positionMetadataAccountKey),
      createWrite(positionTokenAccountKey),
      createRead(rentKey),
      createRead(systemKey),
      createRead(tokenProgramKey),
      createRead(tokenProgram2022Key),
      createRead(tokenATokenProgramKey),
      createRead(tokenBTokenProgramKey),
      createRead(memoProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(poolProgramKey),
      createWrite(oldTickArrayLowerOrBaseVaultAuthorityKey),
      createWrite(oldTickArrayUpperOrBaseVaultAuthorityKey),
      createWrite(oldPositionOrBaseVaultAuthorityKey),
      createWrite(oldPositionMintOrBaseVaultAuthorityKey),
      createWrite(oldPositionTokenAccountOrBaseVaultAuthorityKey),
      createWrite(tokenAVaultKey),
      createWrite(tokenBVaultKey),
      createRead(tokenAMintKey),
      createRead(tokenBMintKey),
      createWrite(poolTokenVaultAKey),
      createWrite(poolTokenVaultBKey),
      createRead(scopePricesKey),
      createRead(tokenInfosKey),
      createRead(requireNonNullElse(eventAuthorityKey, invokedYvaultsProgramMeta.publicKey())),
      createRead(consensusAccountKey)
    );

    final byte[] _data = new byte[25];
    int i = OPEN_LIQUIDITY_POSITION_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, tickLowerIndex);
    i += 8;
    putInt64LE(_data, i, tickUpperIndex);
    i += 8;
    _data[i] = (byte) bump;

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record OpenLiquidityPositionIxData(Discriminator discriminator,
                                            long tickLowerIndex,
                                            long tickUpperIndex,
                                            int bump) implements Borsh {  

    public static OpenLiquidityPositionIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 25;

    public static OpenLiquidityPositionIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var tickLowerIndex = getInt64LE(_data, i);
      i += 8;
      final var tickUpperIndex = getInt64LE(_data, i);
      i += 8;
      final var bump = _data[i] & 0xFF;
      return new OpenLiquidityPositionIxData(discriminator, tickLowerIndex, tickUpperIndex, bump);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, tickLowerIndex);
      i += 8;
      putInt64LE(_data, i, tickUpperIndex);
      i += 8;
      _data[i] = (byte) bump;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CLOSE_STRATEGY_DISCRIMINATOR = toDiscriminator(56, 247, 170, 246, 89, 221, 134, 200);

  public static Instruction closeStrategy(final AccountMeta invokedYvaultsProgramMeta,
                                          final PublicKey adminAuthorityKey,
                                          final PublicKey strategyKey,
                                          final PublicKey oldPositionOrBaseVaultAuthorityKey,
                                          final PublicKey oldPositionMintOrBaseVaultAuthorityKey,
                                          final PublicKey oldPositionTokenAccountOrBaseVaultAuthorityKey,
                                          final PublicKey oldTickArrayLowerOrBaseVaultAuthorityKey,
                                          final PublicKey oldTickArrayUpperOrBaseVaultAuthorityKey,
                                          final PublicKey poolKey,
                                          final PublicKey tokenAVaultKey,
                                          final PublicKey tokenBVaultKey,
                                          final PublicKey userTokenAAtaKey,
                                          final PublicKey userTokenBAtaKey,
                                          final PublicKey tokenAMintKey,
                                          final PublicKey tokenBMintKey,
                                          // If rewards are uninitialized, pass this as strategy.
                                          final PublicKey reward0VaultKey,
                                          // If rewards are uninitialized, pass this as strategy.
                                          final PublicKey reward1VaultKey,
                                          // If rewards are uninitialized, pass this as strategy.
                                          final PublicKey reward2VaultKey,
                                          // If rewards are uninitialized, pass this as strategy.
                                          final PublicKey kaminoReward0VaultKey,
                                          // If rewards are uninitialized, pass this as strategy.
                                          final PublicKey kaminoReward1VaultKey,
                                          // If rewards are uninitialized, pass this as strategy.
                                          final PublicKey kaminoReward2VaultKey,
                                          // If rewards are uninitialized, pass this as strategy.
                                          final PublicKey userReward0AtaKey,
                                          // If rewards are uninitialized, pass this as strategy.
                                          final PublicKey userReward1AtaKey,
                                          // If rewards are uninitialized, pass this as strategy.
                                          final PublicKey userReward2AtaKey,
                                          // If rewards are uninitialized, pass this as strategy.
                                          final PublicKey userKaminoReward0AtaKey,
                                          // If rewards are uninitialized, pass this as strategy.
                                          final PublicKey userKaminoReward1AtaKey,
                                          // If rewards are uninitialized, pass this as strategy.
                                          final PublicKey userKaminoReward2AtaKey,
                                          final PublicKey baseVaultAuthorityKey,
                                          final PublicKey poolProgramKey,
                                          final PublicKey tokenProgramKey,
                                          final PublicKey tokenATokenProgramKey,
                                          final PublicKey tokenBTokenProgramKey,
                                          final PublicKey systemKey,
                                          final PublicKey eventAuthorityKey) {
    final var keys = List.of(
      createWritableSigner(adminAuthorityKey),
      createWrite(strategyKey),
      createWrite(oldPositionOrBaseVaultAuthorityKey),
      createWrite(oldPositionMintOrBaseVaultAuthorityKey),
      createWrite(oldPositionTokenAccountOrBaseVaultAuthorityKey),
      createWrite(oldTickArrayLowerOrBaseVaultAuthorityKey),
      createWrite(oldTickArrayUpperOrBaseVaultAuthorityKey),
      createRead(poolKey),
      createWrite(tokenAVaultKey),
      createWrite(tokenBVaultKey),
      createWrite(userTokenAAtaKey),
      createWrite(userTokenBAtaKey),
      createWrite(tokenAMintKey),
      createWrite(tokenBMintKey),
      createWrite(reward0VaultKey),
      createWrite(reward1VaultKey),
      createWrite(reward2VaultKey),
      createWrite(kaminoReward0VaultKey),
      createWrite(kaminoReward1VaultKey),
      createWrite(kaminoReward2VaultKey),
      createWrite(userReward0AtaKey),
      createWrite(userReward1AtaKey),
      createWrite(userReward2AtaKey),
      createWrite(userKaminoReward0AtaKey),
      createWrite(userKaminoReward1AtaKey),
      createWrite(userKaminoReward2AtaKey),
      createWrite(baseVaultAuthorityKey),
      createRead(poolProgramKey),
      createRead(tokenProgramKey),
      createRead(tokenATokenProgramKey),
      createRead(tokenBTokenProgramKey),
      createRead(systemKey),
      createRead(requireNonNullElse(eventAuthorityKey, invokedYvaultsProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, CLOSE_STRATEGY_DISCRIMINATOR);
  }

  public static final Discriminator DEPOSIT_DISCRIMINATOR = toDiscriminator(242, 35, 198, 137, 82, 225, 242, 182);

  public static Instruction deposit(final AccountMeta invokedYvaultsProgramMeta,
                                    final PublicKey userKey,
                                    final PublicKey strategyKey,
                                    final PublicKey globalConfigKey,
                                    final PublicKey poolKey,
                                    final PublicKey positionKey,
                                    final PublicKey tickArrayLowerKey,
                                    final PublicKey tickArrayUpperKey,
                                    final PublicKey tokenAVaultKey,
                                    final PublicKey tokenBVaultKey,
                                    final PublicKey baseVaultAuthorityKey,
                                    final PublicKey tokenAAtaKey,
                                    final PublicKey tokenBAtaKey,
                                    final PublicKey tokenAMintKey,
                                    final PublicKey tokenBMintKey,
                                    final PublicKey userSharesAtaKey,
                                    final PublicKey sharesMintKey,
                                    final PublicKey sharesMintAuthorityKey,
                                    final PublicKey scopePricesKey,
                                    final PublicKey tokenInfosKey,
                                    final PublicKey tokenProgramKey,
                                    final PublicKey tokenATokenProgramKey,
                                    final PublicKey tokenBTokenProgramKey,
                                    final PublicKey instructionSysvarAccountKey,
                                    final long tokenMaxA,
                                    final long tokenMaxB) {
    final var keys = List.of(
      createWritableSigner(userKey),
      createWrite(strategyKey),
      createRead(globalConfigKey),
      createRead(poolKey),
      createRead(positionKey),
      createRead(tickArrayLowerKey),
      createRead(tickArrayUpperKey),
      createWrite(tokenAVaultKey),
      createWrite(tokenBVaultKey),
      createRead(baseVaultAuthorityKey),
      createWrite(tokenAAtaKey),
      createWrite(tokenBAtaKey),
      createRead(tokenAMintKey),
      createRead(tokenBMintKey),
      createWrite(userSharesAtaKey),
      createWrite(sharesMintKey),
      createRead(sharesMintAuthorityKey),
      createRead(scopePricesKey),
      createRead(tokenInfosKey),
      createRead(tokenProgramKey),
      createRead(tokenATokenProgramKey),
      createRead(tokenBTokenProgramKey),
      createRead(instructionSysvarAccountKey)
    );

    final byte[] _data = new byte[24];
    int i = DEPOSIT_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, tokenMaxA);
    i += 8;
    putInt64LE(_data, i, tokenMaxB);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record DepositIxData(Discriminator discriminator, long tokenMaxA, long tokenMaxB) implements Borsh {  

    public static DepositIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static DepositIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var tokenMaxA = getInt64LE(_data, i);
      i += 8;
      final var tokenMaxB = getInt64LE(_data, i);
      return new DepositIxData(discriminator, tokenMaxA, tokenMaxB);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, tokenMaxA);
      i += 8;
      putInt64LE(_data, i, tokenMaxB);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INVEST_DISCRIMINATOR = toDiscriminator(13, 245, 180, 103, 254, 182, 121, 4);

  public static Instruction invest(final AccountMeta invokedYvaultsProgramMeta,
                                   final PublicKey payerKey,
                                   final PublicKey strategyKey,
                                   final PublicKey globalConfigKey,
                                   final PublicKey tokenAVaultKey,
                                   final PublicKey tokenBVaultKey,
                                   final PublicKey tokenAMintKey,
                                   final PublicKey tokenBMintKey,
                                   final PublicKey baseVaultAuthorityKey,
                                   final PublicKey poolKey,
                                   final PublicKey tokenATokenProgramKey,
                                   final PublicKey tokenBTokenProgramKey,
                                   final PublicKey memoProgramKey,
                                   final PublicKey tokenProgramKey,
                                   final PublicKey tokenProgram2022Key,
                                   final PublicKey positionKey,
                                   final PublicKey raydiumProtocolPositionOrBaseVaultAuthorityKey,
                                   final PublicKey positionTokenAccountKey,
                                   final PublicKey poolTokenVaultAKey,
                                   final PublicKey poolTokenVaultBKey,
                                   final PublicKey tickArrayLowerKey,
                                   final PublicKey tickArrayUpperKey,
                                   final PublicKey scopePricesKey,
                                   final PublicKey tokenInfosKey,
                                   final PublicKey poolProgramKey,
                                   final PublicKey instructionSysvarAccountKey,
                                   final PublicKey eventAuthorityKey) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createWrite(strategyKey),
      createRead(globalConfigKey),
      createWrite(tokenAVaultKey),
      createWrite(tokenBVaultKey),
      createRead(tokenAMintKey),
      createRead(tokenBMintKey),
      createWrite(baseVaultAuthorityKey),
      createWrite(poolKey),
      createRead(tokenATokenProgramKey),
      createRead(tokenBTokenProgramKey),
      createRead(memoProgramKey),
      createRead(tokenProgramKey),
      createRead(tokenProgram2022Key),
      createWrite(positionKey),
      createWrite(raydiumProtocolPositionOrBaseVaultAuthorityKey),
      createWrite(positionTokenAccountKey),
      createWrite(poolTokenVaultAKey),
      createWrite(poolTokenVaultBKey),
      createWrite(tickArrayLowerKey),
      createWrite(tickArrayUpperKey),
      createRead(scopePricesKey),
      createRead(tokenInfosKey),
      createRead(poolProgramKey),
      createRead(instructionSysvarAccountKey),
      createRead(requireNonNullElse(eventAuthorityKey, invokedYvaultsProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, INVEST_DISCRIMINATOR);
  }

  public static final Discriminator DEPOSIT_AND_INVEST_DISCRIMINATOR = toDiscriminator(22, 157, 173, 6, 187, 25, 86, 109);

  public static Instruction depositAndInvest(final AccountMeta invokedYvaultsProgramMeta,
                                             final PublicKey userKey,
                                             final PublicKey strategyKey,
                                             final PublicKey globalConfigKey,
                                             // check that the pool is owned either by orca or by raydium
                                             final PublicKey poolKey,
                                             final PublicKey positionKey,
                                             final PublicKey raydiumProtocolPositionOrBaseVaultAuthorityKey,
                                             final PublicKey positionTokenAccountKey,
                                             final PublicKey tokenAVaultKey,
                                             final PublicKey tokenBVaultKey,
                                             final PublicKey poolTokenVaultAKey,
                                             final PublicKey poolTokenVaultBKey,
                                             final PublicKey tickArrayLowerKey,
                                             final PublicKey tickArrayUpperKey,
                                             final PublicKey baseVaultAuthorityKey,
                                             final PublicKey tokenAAtaKey,
                                             final PublicKey tokenBAtaKey,
                                             final PublicKey tokenAMintKey,
                                             final PublicKey tokenBMintKey,
                                             final PublicKey userSharesAtaKey,
                                             final PublicKey sharesMintKey,
                                             final PublicKey sharesMintAuthorityKey,
                                             final PublicKey scopePricesKey,
                                             final PublicKey tokenInfosKey,
                                             final PublicKey tokenProgramKey,
                                             final PublicKey tokenProgram2022Key,
                                             final PublicKey tokenATokenProgramKey,
                                             final PublicKey tokenBTokenProgramKey,
                                             final PublicKey memoProgramKey,
                                             final PublicKey poolProgramKey,
                                             final PublicKey instructionSysvarAccountKey,
                                             final PublicKey eventAuthorityKey,
                                             final long tokenMaxA,
                                             final long tokenMaxB) {
    final var keys = List.of(
      createWritableSigner(userKey),
      createWrite(strategyKey),
      createRead(globalConfigKey),
      createWrite(poolKey),
      createWrite(positionKey),
      createWrite(raydiumProtocolPositionOrBaseVaultAuthorityKey),
      createWrite(positionTokenAccountKey),
      createWrite(tokenAVaultKey),
      createWrite(tokenBVaultKey),
      createWrite(poolTokenVaultAKey),
      createWrite(poolTokenVaultBKey),
      createWrite(tickArrayLowerKey),
      createWrite(tickArrayUpperKey),
      createWrite(baseVaultAuthorityKey),
      createWrite(tokenAAtaKey),
      createWrite(tokenBAtaKey),
      createRead(tokenAMintKey),
      createRead(tokenBMintKey),
      createWrite(userSharesAtaKey),
      createWrite(sharesMintKey),
      createRead(sharesMintAuthorityKey),
      createRead(scopePricesKey),
      createRead(tokenInfosKey),
      createRead(tokenProgramKey),
      createRead(tokenProgram2022Key),
      createRead(tokenATokenProgramKey),
      createRead(tokenBTokenProgramKey),
      createRead(memoProgramKey),
      createRead(poolProgramKey),
      createRead(instructionSysvarAccountKey),
      createRead(requireNonNullElse(eventAuthorityKey, invokedYvaultsProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[24];
    int i = DEPOSIT_AND_INVEST_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, tokenMaxA);
    i += 8;
    putInt64LE(_data, i, tokenMaxB);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record DepositAndInvestIxData(Discriminator discriminator, long tokenMaxA, long tokenMaxB) implements Borsh {  

    public static DepositAndInvestIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static DepositAndInvestIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var tokenMaxA = getInt64LE(_data, i);
      i += 8;
      final var tokenMaxB = getInt64LE(_data, i);
      return new DepositAndInvestIxData(discriminator, tokenMaxA, tokenMaxB);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, tokenMaxA);
      i += 8;
      putInt64LE(_data, i, tokenMaxB);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator WITHDRAW_DISCRIMINATOR = toDiscriminator(183, 18, 70, 156, 148, 109, 161, 34);

  public static Instruction withdraw(final AccountMeta invokedYvaultsProgramMeta,
                                     final PublicKey userKey,
                                     final PublicKey strategyKey,
                                     final PublicKey globalConfigKey,
                                     final PublicKey poolKey,
                                     final PublicKey positionKey,
                                     final PublicKey tickArrayLowerKey,
                                     final PublicKey tickArrayUpperKey,
                                     final PublicKey tokenAVaultKey,
                                     final PublicKey tokenBVaultKey,
                                     final PublicKey baseVaultAuthorityKey,
                                     final PublicKey poolTokenVaultAKey,
                                     final PublicKey poolTokenVaultBKey,
                                     final PublicKey tokenAAtaKey,
                                     final PublicKey tokenBAtaKey,
                                     final PublicKey tokenAMintKey,
                                     final PublicKey tokenBMintKey,
                                     final PublicKey userSharesAtaKey,
                                     final PublicKey sharesMintKey,
                                     final PublicKey treasuryFeeTokenAVaultKey,
                                     final PublicKey treasuryFeeTokenBVaultKey,
                                     final PublicKey tokenProgramKey,
                                     final PublicKey tokenProgram2022Key,
                                     final PublicKey tokenATokenProgramKey,
                                     final PublicKey tokenBTokenProgramKey,
                                     final PublicKey memoProgramKey,
                                     final PublicKey positionTokenAccountKey,
                                     final PublicKey poolProgramKey,
                                     final PublicKey instructionSysvarAccountKey,
                                     final PublicKey eventAuthorityKey,
                                     final long sharesAmount) {
    final var keys = List.of(
      createWritableSigner(userKey),
      createWrite(strategyKey),
      createRead(globalConfigKey),
      createWrite(poolKey),
      createWrite(positionKey),
      createWrite(tickArrayLowerKey),
      createWrite(tickArrayUpperKey),
      createWrite(tokenAVaultKey),
      createWrite(tokenBVaultKey),
      createRead(baseVaultAuthorityKey),
      createWrite(poolTokenVaultAKey),
      createWrite(poolTokenVaultBKey),
      createWrite(tokenAAtaKey),
      createWrite(tokenBAtaKey),
      createRead(tokenAMintKey),
      createRead(tokenBMintKey),
      createWrite(userSharesAtaKey),
      createWrite(sharesMintKey),
      createWrite(treasuryFeeTokenAVaultKey),
      createWrite(treasuryFeeTokenBVaultKey),
      createRead(tokenProgramKey),
      createRead(tokenProgram2022Key),
      createRead(tokenATokenProgramKey),
      createRead(tokenBTokenProgramKey),
      createRead(memoProgramKey),
      createWrite(positionTokenAccountKey),
      createRead(poolProgramKey),
      createRead(instructionSysvarAccountKey),
      createRead(requireNonNullElse(eventAuthorityKey, invokedYvaultsProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[16];
    int i = WITHDRAW_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, sharesAmount);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record WithdrawIxData(Discriminator discriminator, long sharesAmount) implements Borsh {  

    public static WithdrawIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static WithdrawIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var sharesAmount = getInt64LE(_data, i);
      return new WithdrawIxData(discriminator, sharesAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, sharesAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator EXECUTIVE_WITHDRAW_DISCRIMINATOR = toDiscriminator(159, 39, 110, 137, 100, 234, 204, 141);

  public static Instruction executiveWithdraw(final AccountMeta invokedYvaultsProgramMeta,
                                              final PublicKey adminAuthorityKey,
                                              final PublicKey strategyKey,
                                              final PublicKey globalConfigKey,
                                              final PublicKey poolKey,
                                              final PublicKey positionKey,
                                              final PublicKey raydiumProtocolPositionOrBaseVaultAuthorityKey,
                                              final PublicKey positionTokenAccountKey,
                                              final PublicKey tickArrayLowerKey,
                                              final PublicKey tickArrayUpperKey,
                                              final PublicKey tokenAVaultKey,
                                              final PublicKey tokenBVaultKey,
                                              final PublicKey baseVaultAuthorityKey,
                                              final PublicKey poolTokenVaultAKey,
                                              final PublicKey poolTokenVaultBKey,
                                              final PublicKey tokenAMintKey,
                                              final PublicKey tokenBMintKey,
                                              final PublicKey scopePricesKey,
                                              final PublicKey tokenInfosKey,
                                              final PublicKey tokenATokenProgramKey,
                                              final PublicKey tokenBTokenProgramKey,
                                              final PublicKey memoProgramKey,
                                              final PublicKey tokenProgramKey,
                                              final PublicKey tokenProgram2022Key,
                                              final PublicKey poolProgramKey,
                                              final PublicKey eventAuthorityKey,
                                              final int action) {
    final var keys = List.of(
      createWritableSigner(adminAuthorityKey),
      createWrite(strategyKey),
      createRead(globalConfigKey),
      createWrite(poolKey),
      createWrite(positionKey),
      createWrite(raydiumProtocolPositionOrBaseVaultAuthorityKey),
      createRead(positionTokenAccountKey),
      createWrite(tickArrayLowerKey),
      createWrite(tickArrayUpperKey),
      createWrite(tokenAVaultKey),
      createWrite(tokenBVaultKey),
      createRead(baseVaultAuthorityKey),
      createWrite(poolTokenVaultAKey),
      createWrite(poolTokenVaultBKey),
      createRead(tokenAMintKey),
      createRead(tokenBMintKey),
      createRead(scopePricesKey),
      createRead(tokenInfosKey),
      createRead(tokenATokenProgramKey),
      createRead(tokenBTokenProgramKey),
      createRead(memoProgramKey),
      createRead(tokenProgramKey),
      createRead(tokenProgram2022Key),
      createRead(poolProgramKey),
      createRead(requireNonNullElse(eventAuthorityKey, invokedYvaultsProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[9];
    int i = EXECUTIVE_WITHDRAW_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) action;

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record ExecutiveWithdrawIxData(Discriminator discriminator, int action) implements Borsh {  

    public static ExecutiveWithdrawIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static ExecutiveWithdrawIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var action = _data[i] & 0xFF;
      return new ExecutiveWithdrawIxData(discriminator, action);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) action;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator COLLECT_FEES_AND_REWARDS_DISCRIMINATOR = toDiscriminator(113, 18, 75, 8, 182, 31, 105, 186);

  public static Instruction collectFeesAndRewards(final AccountMeta invokedYvaultsProgramMeta,
                                                  final PublicKey userKey,
                                                  final PublicKey strategyKey,
                                                  final PublicKey globalConfigKey,
                                                  final PublicKey baseVaultAuthorityKey,
                                                  final PublicKey poolKey,
                                                  final PublicKey tickArrayLowerKey,
                                                  final PublicKey tickArrayUpperKey,
                                                  final PublicKey positionKey,
                                                  final PublicKey raydiumProtocolPositionOrBaseVaultAuthorityKey,
                                                  final PublicKey positionTokenAccountKey,
                                                  final PublicKey tokenAVaultKey,
                                                  final PublicKey poolTokenVaultAKey,
                                                  final PublicKey tokenBVaultKey,
                                                  final PublicKey poolTokenVaultBKey,
                                                  final PublicKey treasuryFeeTokenAVaultKey,
                                                  final PublicKey treasuryFeeTokenBVaultKey,
                                                  final PublicKey treasuryFeeVaultAuthorityKey,
                                                  // If rewards are uninitialized, pass this as strategy.
                                                  final PublicKey reward0VaultKey,
                                                  // If rewards are uninitialized, pass this as strategy.
                                                  final PublicKey reward1VaultKey,
                                                  // If rewards are uninitialized, pass this as strategy.
                                                  final PublicKey reward2VaultKey,
                                                  // If rewards are uninitialized, pass this as strategy.
                                                  final PublicKey poolRewardVault0Key,
                                                  // If rewards are uninitialized, pass this as strategy.
                                                  final PublicKey poolRewardVault1Key,
                                                  // If rewards are uninitialized, pass this as strategy.
                                                  final PublicKey poolRewardVault2Key,
                                                  final PublicKey tokenAMintKey,
                                                  final PublicKey tokenBMintKey,
                                                  final PublicKey tokenATokenProgramKey,
                                                  final PublicKey tokenBTokenProgramKey,
                                                  final PublicKey memoProgramKey,
                                                  final PublicKey tokenProgramKey,
                                                  final PublicKey tokenProgram2022Key,
                                                  final PublicKey poolProgramKey,
                                                  final PublicKey instructionSysvarAccountKey,
                                                  final PublicKey eventAuthorityKey) {
    final var keys = List.of(
      createWritableSigner(userKey),
      createWrite(strategyKey),
      createRead(globalConfigKey),
      createWrite(baseVaultAuthorityKey),
      createWrite(poolKey),
      createWrite(tickArrayLowerKey),
      createWrite(tickArrayUpperKey),
      createWrite(positionKey),
      createWrite(raydiumProtocolPositionOrBaseVaultAuthorityKey),
      createRead(positionTokenAccountKey),
      createWrite(tokenAVaultKey),
      createWrite(poolTokenVaultAKey),
      createWrite(tokenBVaultKey),
      createWrite(poolTokenVaultBKey),
      createWrite(treasuryFeeTokenAVaultKey),
      createWrite(treasuryFeeTokenBVaultKey),
      createRead(treasuryFeeVaultAuthorityKey),
      createWrite(reward0VaultKey),
      createWrite(reward1VaultKey),
      createWrite(reward2VaultKey),
      createWrite(poolRewardVault0Key),
      createWrite(poolRewardVault1Key),
      createWrite(poolRewardVault2Key),
      createRead(tokenAMintKey),
      createRead(tokenBMintKey),
      createRead(tokenATokenProgramKey),
      createRead(tokenBTokenProgramKey),
      createRead(memoProgramKey),
      createRead(tokenProgramKey),
      createRead(tokenProgram2022Key),
      createRead(poolProgramKey),
      createRead(instructionSysvarAccountKey),
      createRead(requireNonNullElse(eventAuthorityKey, invokedYvaultsProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, COLLECT_FEES_AND_REWARDS_DISCRIMINATOR);
  }

  public static final Discriminator SWAP_REWARDS_DISCRIMINATOR = toDiscriminator(92, 41, 172, 30, 190, 65, 174, 90);

  public static Instruction swapRewards(final AccountMeta invokedYvaultsProgramMeta,
                                        final PublicKey userKey,
                                        final PublicKey strategyKey,
                                        final PublicKey globalConfigKey,
                                        final PublicKey poolKey,
                                        final PublicKey tokenAVaultKey,
                                        final PublicKey tokenBVaultKey,
                                        final PublicKey rewardVaultKey,
                                        final PublicKey baseVaultAuthorityKey,
                                        final PublicKey treasuryFeeTokenAVaultKey,
                                        final PublicKey treasuryFeeTokenBVaultKey,
                                        final PublicKey treasuryFeeVaultAuthorityKey,
                                        final PublicKey tokenAMintKey,
                                        final PublicKey tokenBMintKey,
                                        final PublicKey rewardMintKey,
                                        final PublicKey userTokenAAtaKey,
                                        final PublicKey userTokenBAtaKey,
                                        final PublicKey userRewardTokenAccountKey,
                                        final PublicKey scopePricesKey,
                                        final PublicKey tokenInfosKey,
                                        final PublicKey systemProgramKey,
                                        final PublicKey tokenATokenProgramKey,
                                        final PublicKey tokenBTokenProgramKey,
                                        final PublicKey rewardTokenProgramKey,
                                        final PublicKey instructionSysvarAccountKey,
                                        final long tokenAIn,
                                        final long tokenBIn,
                                        final long rewardIndex,
                                        final long rewardCollateralId,
                                        final long minCollateralTokenOut) {
    final var keys = List.of(
      createWritableSigner(userKey),
      createWrite(strategyKey),
      createRead(globalConfigKey),
      createRead(poolKey),
      createWrite(tokenAVaultKey),
      createWrite(tokenBVaultKey),
      createWrite(rewardVaultKey),
      createWrite(baseVaultAuthorityKey),
      createWrite(treasuryFeeTokenAVaultKey),
      createWrite(treasuryFeeTokenBVaultKey),
      createRead(treasuryFeeVaultAuthorityKey),
      createRead(tokenAMintKey),
      createRead(tokenBMintKey),
      createRead(rewardMintKey),
      createWrite(userTokenAAtaKey),
      createWrite(userTokenBAtaKey),
      createWrite(userRewardTokenAccountKey),
      createRead(scopePricesKey),
      createRead(tokenInfosKey),
      createRead(systemProgramKey),
      createRead(tokenATokenProgramKey),
      createRead(tokenBTokenProgramKey),
      createRead(rewardTokenProgramKey),
      createRead(instructionSysvarAccountKey)
    );

    final byte[] _data = new byte[48];
    int i = SWAP_REWARDS_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, tokenAIn);
    i += 8;
    putInt64LE(_data, i, tokenBIn);
    i += 8;
    putInt64LE(_data, i, rewardIndex);
    i += 8;
    putInt64LE(_data, i, rewardCollateralId);
    i += 8;
    putInt64LE(_data, i, minCollateralTokenOut);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record SwapRewardsIxData(Discriminator discriminator,
                                  long tokenAIn,
                                  long tokenBIn,
                                  long rewardIndex,
                                  long rewardCollateralId,
                                  long minCollateralTokenOut) implements Borsh {  

    public static SwapRewardsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 48;

    public static SwapRewardsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var tokenAIn = getInt64LE(_data, i);
      i += 8;
      final var tokenBIn = getInt64LE(_data, i);
      i += 8;
      final var rewardIndex = getInt64LE(_data, i);
      i += 8;
      final var rewardCollateralId = getInt64LE(_data, i);
      i += 8;
      final var minCollateralTokenOut = getInt64LE(_data, i);
      return new SwapRewardsIxData(discriminator,
                                   tokenAIn,
                                   tokenBIn,
                                   rewardIndex,
                                   rewardCollateralId,
                                   minCollateralTokenOut);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, tokenAIn);
      i += 8;
      putInt64LE(_data, i, tokenBIn);
      i += 8;
      putInt64LE(_data, i, rewardIndex);
      i += 8;
      putInt64LE(_data, i, rewardCollateralId);
      i += 8;
      putInt64LE(_data, i, minCollateralTokenOut);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CHECK_EXPECTED_VAULTS_BALANCES_DISCRIMINATOR = toDiscriminator(75, 151, 187, 125, 50, 4, 11, 71);

  public static Instruction checkExpectedVaultsBalances(final AccountMeta invokedYvaultsProgramMeta,
                                                        final PublicKey userKey,
                                                        final PublicKey tokenAAtaKey,
                                                        final PublicKey tokenBAtaKey,
                                                        final long tokenAAtaBalance,
                                                        final long tokenBAtaBalance) {
    final var keys = List.of(
      createWritableSigner(userKey),
      createRead(tokenAAtaKey),
      createRead(tokenBAtaKey)
    );

    final byte[] _data = new byte[24];
    int i = CHECK_EXPECTED_VAULTS_BALANCES_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, tokenAAtaBalance);
    i += 8;
    putInt64LE(_data, i, tokenBAtaBalance);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record CheckExpectedVaultsBalancesIxData(Discriminator discriminator, long tokenAAtaBalance, long tokenBAtaBalance) implements Borsh {  

    public static CheckExpectedVaultsBalancesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static CheckExpectedVaultsBalancesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var tokenAAtaBalance = getInt64LE(_data, i);
      i += 8;
      final var tokenBAtaBalance = getInt64LE(_data, i);
      return new CheckExpectedVaultsBalancesIxData(discriminator, tokenAAtaBalance, tokenBAtaBalance);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, tokenAAtaBalance);
      i += 8;
      putInt64LE(_data, i, tokenBAtaBalance);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SINGLE_TOKEN_DEPOSIT_AND_INVEST_WITH_MIN_DISCRIMINATOR = toDiscriminator(118, 134, 143, 192, 188, 21, 131, 17);

  public static Instruction singleTokenDepositAndInvestWithMin(final AccountMeta invokedYvaultsProgramMeta,
                                                               final PublicKey userKey,
                                                               final PublicKey strategyKey,
                                                               final PublicKey globalConfigKey,
                                                               // check that the pool is owned either by orca or by raydium
                                                               final PublicKey poolKey,
                                                               final PublicKey positionKey,
                                                               final PublicKey raydiumProtocolPositionOrBaseVaultAuthorityKey,
                                                               final PublicKey positionTokenAccountKey,
                                                               final PublicKey tokenAVaultKey,
                                                               final PublicKey tokenBVaultKey,
                                                               final PublicKey poolTokenVaultAKey,
                                                               final PublicKey poolTokenVaultBKey,
                                                               final PublicKey tickArrayLowerKey,
                                                               final PublicKey tickArrayUpperKey,
                                                               final PublicKey baseVaultAuthorityKey,
                                                               final PublicKey tokenAAtaKey,
                                                               final PublicKey tokenBAtaKey,
                                                               final PublicKey tokenAMintKey,
                                                               final PublicKey tokenBMintKey,
                                                               final PublicKey userSharesAtaKey,
                                                               final PublicKey sharesMintKey,
                                                               final PublicKey sharesMintAuthorityKey,
                                                               final PublicKey scopePricesKey,
                                                               final PublicKey tokenInfosKey,
                                                               final PublicKey tokenProgramKey,
                                                               final PublicKey tokenProgram2022Key,
                                                               final PublicKey tokenATokenProgramKey,
                                                               final PublicKey tokenBTokenProgramKey,
                                                               final PublicKey memoProgramKey,
                                                               final PublicKey poolProgramKey,
                                                               final PublicKey instructionSysvarAccountKey,
                                                               final PublicKey eventAuthorityKey,
                                                               final long tokenAMinPostDepositBalance,
                                                               final long tokenBMinPostDepositBalance) {
    final var keys = List.of(
      createWritableSigner(userKey),
      createWrite(strategyKey),
      createRead(globalConfigKey),
      createWrite(poolKey),
      createWrite(positionKey),
      createWrite(raydiumProtocolPositionOrBaseVaultAuthorityKey),
      createWrite(positionTokenAccountKey),
      createWrite(tokenAVaultKey),
      createWrite(tokenBVaultKey),
      createWrite(poolTokenVaultAKey),
      createWrite(poolTokenVaultBKey),
      createWrite(tickArrayLowerKey),
      createWrite(tickArrayUpperKey),
      createWrite(baseVaultAuthorityKey),
      createWrite(tokenAAtaKey),
      createWrite(tokenBAtaKey),
      createRead(tokenAMintKey),
      createRead(tokenBMintKey),
      createWrite(userSharesAtaKey),
      createWrite(sharesMintKey),
      createRead(sharesMintAuthorityKey),
      createRead(scopePricesKey),
      createRead(tokenInfosKey),
      createRead(tokenProgramKey),
      createRead(tokenProgram2022Key),
      createRead(tokenATokenProgramKey),
      createRead(tokenBTokenProgramKey),
      createRead(memoProgramKey),
      createRead(poolProgramKey),
      createRead(instructionSysvarAccountKey),
      createRead(requireNonNullElse(eventAuthorityKey, invokedYvaultsProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[24];
    int i = SINGLE_TOKEN_DEPOSIT_AND_INVEST_WITH_MIN_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, tokenAMinPostDepositBalance);
    i += 8;
    putInt64LE(_data, i, tokenBMinPostDepositBalance);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record SingleTokenDepositAndInvestWithMinIxData(Discriminator discriminator, long tokenAMinPostDepositBalance, long tokenBMinPostDepositBalance) implements Borsh {  

    public static SingleTokenDepositAndInvestWithMinIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static SingleTokenDepositAndInvestWithMinIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var tokenAMinPostDepositBalance = getInt64LE(_data, i);
      i += 8;
      final var tokenBMinPostDepositBalance = getInt64LE(_data, i);
      return new SingleTokenDepositAndInvestWithMinIxData(discriminator, tokenAMinPostDepositBalance, tokenBMinPostDepositBalance);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, tokenAMinPostDepositBalance);
      i += 8;
      putInt64LE(_data, i, tokenBMinPostDepositBalance);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SINGLE_TOKEN_DEPOSIT_WITH_MIN_DISCRIMINATOR = toDiscriminator(250, 142, 102, 160, 72, 12, 83, 139);

  public static Instruction singleTokenDepositWithMin(final AccountMeta invokedYvaultsProgramMeta,
                                                      final PublicKey userKey,
                                                      final PublicKey strategyKey,
                                                      final PublicKey globalConfigKey,
                                                      final PublicKey poolKey,
                                                      final PublicKey positionKey,
                                                      final PublicKey tickArrayLowerKey,
                                                      final PublicKey tickArrayUpperKey,
                                                      final PublicKey tokenAVaultKey,
                                                      final PublicKey tokenBVaultKey,
                                                      final PublicKey baseVaultAuthorityKey,
                                                      final PublicKey tokenAAtaKey,
                                                      final PublicKey tokenBAtaKey,
                                                      final PublicKey tokenAMintKey,
                                                      final PublicKey tokenBMintKey,
                                                      final PublicKey userSharesAtaKey,
                                                      final PublicKey sharesMintKey,
                                                      final PublicKey sharesMintAuthorityKey,
                                                      final PublicKey scopePricesKey,
                                                      final PublicKey tokenInfosKey,
                                                      final PublicKey tokenProgramKey,
                                                      final PublicKey tokenATokenProgramKey,
                                                      final PublicKey tokenBTokenProgramKey,
                                                      final PublicKey instructionSysvarAccountKey,
                                                      final long tokenAMinPostDepositBalance,
                                                      final long tokenBMinPostDepositBalance) {
    final var keys = List.of(
      createWritableSigner(userKey),
      createWrite(strategyKey),
      createRead(globalConfigKey),
      createRead(poolKey),
      createRead(positionKey),
      createRead(tickArrayLowerKey),
      createRead(tickArrayUpperKey),
      createWrite(tokenAVaultKey),
      createWrite(tokenBVaultKey),
      createRead(baseVaultAuthorityKey),
      createWrite(tokenAAtaKey),
      createWrite(tokenBAtaKey),
      createRead(tokenAMintKey),
      createRead(tokenBMintKey),
      createWrite(userSharesAtaKey),
      createWrite(sharesMintKey),
      createRead(sharesMintAuthorityKey),
      createRead(scopePricesKey),
      createRead(tokenInfosKey),
      createRead(tokenProgramKey),
      createRead(tokenATokenProgramKey),
      createRead(tokenBTokenProgramKey),
      createRead(instructionSysvarAccountKey)
    );

    final byte[] _data = new byte[24];
    int i = SINGLE_TOKEN_DEPOSIT_WITH_MIN_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, tokenAMinPostDepositBalance);
    i += 8;
    putInt64LE(_data, i, tokenBMinPostDepositBalance);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record SingleTokenDepositWithMinIxData(Discriminator discriminator, long tokenAMinPostDepositBalance, long tokenBMinPostDepositBalance) implements Borsh {  

    public static SingleTokenDepositWithMinIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static SingleTokenDepositWithMinIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var tokenAMinPostDepositBalance = getInt64LE(_data, i);
      i += 8;
      final var tokenBMinPostDepositBalance = getInt64LE(_data, i);
      return new SingleTokenDepositWithMinIxData(discriminator, tokenAMinPostDepositBalance, tokenBMinPostDepositBalance);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, tokenAMinPostDepositBalance);
      i += 8;
      putInt64LE(_data, i, tokenBMinPostDepositBalance);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator FLASH_SWAP_UNEVEN_VAULTS_START_DISCRIMINATOR = toDiscriminator(129, 111, 174, 12, 10, 60, 149, 193);

  // Start of a Flash swap uneven vaults.
  // 
  // This needs to be the first instruction of the transaction or preceded only by a
  // ComputeBudget.
  // 
  // This ix has to be paired with a `flash_swap_uneven_vaults_end` (`FlashSwapUnevenVaultsEnd`)
  // as the last instruction of the transaction. No other instruction targeted the program is
  // allowed.
  // The instructions between the start and end instructions are expected to perform the swap.
  public static Instruction flashSwapUnevenVaultsStart(final AccountMeta invokedYvaultsProgramMeta,
                                                       final PublicKey swapperKey,
                                                       final PublicKey strategyKey,
                                                       final PublicKey globalConfigKey,
                                                       final PublicKey tokenAVaultKey,
                                                       final PublicKey tokenBVaultKey,
                                                       final PublicKey tokenAAtaKey,
                                                       final PublicKey tokenBAtaKey,
                                                       final PublicKey baseVaultAuthorityKey,
                                                       final PublicKey poolKey,
                                                       final PublicKey positionKey,
                                                       final PublicKey scopePricesKey,
                                                       final PublicKey tokenInfosKey,
                                                       final PublicKey tickArrayLowerKey,
                                                       final PublicKey tickArrayUpperKey,
                                                       final PublicKey tokenAMintKey,
                                                       final PublicKey tokenBMintKey,
                                                       final PublicKey tokenATokenProgramKey,
                                                       final PublicKey tokenBTokenProgramKey,
                                                       final PublicKey instructionSysvarAccountKey,
                                                       final PublicKey consensusAccountKey,
                                                       final long amount,
                                                       final boolean aToB) {
    final var keys = List.of(
      createWritableSigner(swapperKey),
      createWrite(strategyKey),
      createRead(globalConfigKey),
      createWrite(tokenAVaultKey),
      createWrite(tokenBVaultKey),
      createWrite(tokenAAtaKey),
      createWrite(tokenBAtaKey),
      createWrite(baseVaultAuthorityKey),
      createWrite(poolKey),
      createWrite(positionKey),
      createRead(scopePricesKey),
      createRead(tokenInfosKey),
      createRead(tickArrayLowerKey),
      createRead(tickArrayUpperKey),
      createRead(tokenAMintKey),
      createRead(tokenBMintKey),
      createRead(tokenATokenProgramKey),
      createRead(tokenBTokenProgramKey),
      createRead(instructionSysvarAccountKey),
      createRead(consensusAccountKey)
    );

    final byte[] _data = new byte[17];
    int i = FLASH_SWAP_UNEVEN_VAULTS_START_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) (aToB ? 1 : 0);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record FlashSwapUnevenVaultsStartIxData(Discriminator discriminator, long amount, boolean aToB) implements Borsh {  

    public static FlashSwapUnevenVaultsStartIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static FlashSwapUnevenVaultsStartIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var aToB = _data[i] == 1;
      return new FlashSwapUnevenVaultsStartIxData(discriminator, amount, aToB);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      _data[i] = (byte) (aToB ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator FLASH_SWAP_UNEVEN_VAULTS_END_DISCRIMINATOR = toDiscriminator(226, 2, 190, 101, 202, 132, 156, 20);

  // End of Flash swap uneven vaults.
  // 
  // See [`flash_swap_uneven_vaults_start`] for details.
  // 
  // Warning: This instruction is allowed to be used independently from
  // `FlashSwapUnevenVaultsStart` and shall not perform any operation
  // that can be exploited when used alone.
  public static Instruction flashSwapUnevenVaultsEnd(final AccountMeta invokedYvaultsProgramMeta,
                                                     final PublicKey swapperKey,
                                                     final PublicKey strategyKey,
                                                     final PublicKey globalConfigKey,
                                                     final PublicKey tokenAVaultKey,
                                                     final PublicKey tokenBVaultKey,
                                                     final PublicKey tokenAAtaKey,
                                                     final PublicKey tokenBAtaKey,
                                                     final PublicKey baseVaultAuthorityKey,
                                                     final PublicKey poolKey,
                                                     final PublicKey positionKey,
                                                     final PublicKey scopePricesKey,
                                                     final PublicKey tokenInfosKey,
                                                     final PublicKey tickArrayLowerKey,
                                                     final PublicKey tickArrayUpperKey,
                                                     final PublicKey tokenAMintKey,
                                                     final PublicKey tokenBMintKey,
                                                     final PublicKey tokenATokenProgramKey,
                                                     final PublicKey tokenBTokenProgramKey,
                                                     final PublicKey instructionSysvarAccountKey,
                                                     final PublicKey consensusAccountKey,
                                                     final long minRepayAmount,
                                                     final long amountToLeaveToUser,
                                                     final boolean aToB) {
    final var keys = List.of(
      createWritableSigner(swapperKey),
      createWrite(strategyKey),
      createRead(globalConfigKey),
      createWrite(tokenAVaultKey),
      createWrite(tokenBVaultKey),
      createWrite(tokenAAtaKey),
      createWrite(tokenBAtaKey),
      createWrite(baseVaultAuthorityKey),
      createWrite(poolKey),
      createWrite(positionKey),
      createRead(scopePricesKey),
      createRead(tokenInfosKey),
      createRead(tickArrayLowerKey),
      createRead(tickArrayUpperKey),
      createRead(tokenAMintKey),
      createRead(tokenBMintKey),
      createRead(tokenATokenProgramKey),
      createRead(tokenBTokenProgramKey),
      createRead(instructionSysvarAccountKey),
      createRead(consensusAccountKey)
    );

    final byte[] _data = new byte[25];
    int i = FLASH_SWAP_UNEVEN_VAULTS_END_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, minRepayAmount);
    i += 8;
    putInt64LE(_data, i, amountToLeaveToUser);
    i += 8;
    _data[i] = (byte) (aToB ? 1 : 0);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record FlashSwapUnevenVaultsEndIxData(Discriminator discriminator,
                                               long minRepayAmount,
                                               long amountToLeaveToUser,
                                               boolean aToB) implements Borsh {  

    public static FlashSwapUnevenVaultsEndIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 25;

    public static FlashSwapUnevenVaultsEndIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var minRepayAmount = getInt64LE(_data, i);
      i += 8;
      final var amountToLeaveToUser = getInt64LE(_data, i);
      i += 8;
      final var aToB = _data[i] == 1;
      return new FlashSwapUnevenVaultsEndIxData(discriminator, minRepayAmount, amountToLeaveToUser, aToB);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, minRepayAmount);
      i += 8;
      putInt64LE(_data, i, amountToLeaveToUser);
      i += 8;
      _data[i] = (byte) (aToB ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator EMERGENCY_SWAP_DISCRIMINATOR = toDiscriminator(73, 226, 248, 215, 5, 197, 211, 229);

  public static Instruction emergencySwap(final AccountMeta invokedYvaultsProgramMeta,
                                          final PublicKey adminAuthorityKey,
                                          final PublicKey strategyKey,
                                          final PublicKey globalConfigKey,
                                          final PublicKey tokenAMintKey,
                                          final PublicKey tokenBMintKey,
                                          final PublicKey tokenAVaultKey,
                                          final PublicKey tokenBVaultKey,
                                          final PublicKey baseVaultAuthorityKey,
                                          final PublicKey poolKey,
                                          final PublicKey positionKey,
                                          final PublicKey poolTokenVaultAKey,
                                          final PublicKey poolTokenVaultBKey,
                                          // Payer must send this correctly.
                                          final PublicKey tickArray0Key,
                                          // Payer must send this correctly.
                                          final PublicKey tickArray1Key,
                                          // Payer must send this correctly.
                                          final PublicKey tickArray2Key,
                                          final PublicKey oracleKey,
                                          final PublicKey poolProgramKey,
                                          final PublicKey scopePricesKey,
                                          final PublicKey tokenInfosKey,
                                          final PublicKey tokenATokenProgramKey,
                                          final PublicKey tokenBTokenProgramKey,
                                          final PublicKey memoProgramKey,
                                          final boolean aToB,
                                          final long targetLimitBps) {
    final var keys = List.of(
      createWritableSigner(adminAuthorityKey),
      createWrite(strategyKey),
      createRead(globalConfigKey),
      createRead(tokenAMintKey),
      createRead(tokenBMintKey),
      createWrite(tokenAVaultKey),
      createWrite(tokenBVaultKey),
      createWrite(baseVaultAuthorityKey),
      createWrite(poolKey),
      createWrite(positionKey),
      createWrite(poolTokenVaultAKey),
      createWrite(poolTokenVaultBKey),
      createWrite(tickArray0Key),
      createWrite(tickArray1Key),
      createWrite(tickArray2Key),
      createWrite(oracleKey),
      createRead(poolProgramKey),
      createRead(scopePricesKey),
      createRead(tokenInfosKey),
      createRead(tokenATokenProgramKey),
      createRead(tokenBTokenProgramKey),
      createRead(memoProgramKey)
    );

    final byte[] _data = new byte[17];
    int i = EMERGENCY_SWAP_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) (aToB ? 1 : 0);
    ++i;
    putInt64LE(_data, i, targetLimitBps);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record EmergencySwapIxData(Discriminator discriminator, boolean aToB, long targetLimitBps) implements Borsh {  

    public static EmergencySwapIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static EmergencySwapIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var aToB = _data[i] == 1;
      ++i;
      final var targetLimitBps = getInt64LE(_data, i);
      return new EmergencySwapIxData(discriminator, aToB, targetLimitBps);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) (aToB ? 1 : 0);
      ++i;
      putInt64LE(_data, i, targetLimitBps);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator WITHDRAW_FROM_TREASURY_DISCRIMINATOR = toDiscriminator(0, 164, 86, 76, 56, 72, 12, 170);

  public static Instruction withdrawFromTreasury(final AccountMeta invokedYvaultsProgramMeta,
                                                 final PublicKey adminAuthorityKey,
                                                 final PublicKey globalConfigKey,
                                                 final PublicKey mintKey,
                                                 final PublicKey treasuryFeeVaultKey,
                                                 final PublicKey treasuryFeeVaultAuthorityKey,
                                                 final PublicKey tokenAccountKey,
                                                 final PublicKey systemProgramKey,
                                                 final PublicKey rentKey,
                                                 final PublicKey tokenProgramKey,
                                                 final long amount) {
    final var keys = List.of(
      createWritableSigner(adminAuthorityKey),
      createRead(globalConfigKey),
      createRead(mintKey),
      createWrite(treasuryFeeVaultKey),
      createWrite(treasuryFeeVaultAuthorityKey),
      createWrite(tokenAccountKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = WITHDRAW_FROM_TREASURY_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record WithdrawFromTreasuryIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static WithdrawFromTreasuryIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static WithdrawFromTreasuryIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new WithdrawFromTreasuryIxData(discriminator, amount);
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

  public static final Discriminator PERMISIONLESS_WITHDRAW_FROM_TREASURY_DISCRIMINATOR = toDiscriminator(167, 36, 32, 79, 97, 170, 183, 108);

  public static Instruction permisionlessWithdrawFromTreasury(final AccountMeta invokedYvaultsProgramMeta,
                                                              final PublicKey signerKey,
                                                              final PublicKey globalConfigKey,
                                                              final PublicKey mintKey,
                                                              final PublicKey treasuryFeeVaultKey,
                                                              final PublicKey treasuryFeeVaultAuthorityKey,
                                                              final PublicKey tokenAccountKey,
                                                              final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWritableSigner(signerKey),
      createRead(globalConfigKey),
      createRead(mintKey),
      createWrite(treasuryFeeVaultKey),
      createWrite(treasuryFeeVaultAuthorityKey),
      createWrite(tokenAccountKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, PERMISIONLESS_WITHDRAW_FROM_TREASURY_DISCRIMINATOR);
  }

  public static final Discriminator WITHDRAW_FROM_TOPUP_DISCRIMINATOR = toDiscriminator(95, 227, 138, 220, 240, 95, 150, 113);

  public static Instruction withdrawFromTopup(final AccountMeta invokedYvaultsProgramMeta,
                                              final PublicKey adminAuthorityKey,
                                              final PublicKey topupVaultKey,
                                              final PublicKey systemKey,
                                              final long amount) {
    final var keys = List.of(
      createWritableSigner(adminAuthorityKey),
      createWrite(topupVaultKey),
      createRead(systemKey)
    );

    final byte[] _data = new byte[16];
    int i = WITHDRAW_FROM_TOPUP_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record WithdrawFromTopupIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static WithdrawFromTopupIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static WithdrawFromTopupIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new WithdrawFromTopupIxData(discriminator, amount);
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

  public static final Discriminator CHANGE_POOL_DISCRIMINATOR = toDiscriminator(141, 221, 123, 235, 35, 9, 145, 201);

  public static Instruction changePool(final AccountMeta invokedYvaultsProgramMeta,
                                       final PublicKey adminAuthorityKey,
                                       final PublicKey strategyKey,
                                       final PublicKey oldPositionKey,
                                       final PublicKey baseVaultAuthorityKey,
                                       final PublicKey newPoolKey,
                                       final PublicKey strategyRewardVault0OrBaseVaultAuthorityKey,
                                       final PublicKey strategyRewardVault1OrBaseVaultAuthorityKey,
                                       final PublicKey strategyRewardVault2OrBaseVaultAuthorityKey) {
    final var keys = List.of(
      createWritableSigner(adminAuthorityKey),
      createWrite(strategyKey),
      createRead(oldPositionKey),
      createRead(baseVaultAuthorityKey),
      createRead(newPoolKey),
      createRead(strategyRewardVault0OrBaseVaultAuthorityKey),
      createRead(strategyRewardVault1OrBaseVaultAuthorityKey),
      createRead(strategyRewardVault2OrBaseVaultAuthorityKey)
    );

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, CHANGE_POOL_DISCRIMINATOR);
  }

  public static final Discriminator CLOSE_PROGRAM_ACCOUNT_DISCRIMINATOR = toDiscriminator(245, 14, 192, 211, 99, 42, 170, 187);

  public static Instruction closeProgramAccount(final AccountMeta invokedYvaultsProgramMeta,
                                                final PublicKey adminAuthorityKey,
                                                final PublicKey programKey,
                                                final PublicKey programDataKey,
                                                final PublicKey closingAccountKey,
                                                final PublicKey systemProgramKey) {
    final var keys = List.of(
      createWritableSigner(adminAuthorityKey),
      createRead(programKey),
      createRead(programDataKey),
      createWrite(closingAccountKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, CLOSE_PROGRAM_ACCOUNT_DISCRIMINATOR);
  }

  public static final Discriminator ORCA_SWAP_DISCRIMINATOR = toDiscriminator(33, 94, 249, 97, 250, 254, 198, 93);

  public static Instruction orcaSwap(final AccountMeta invokedYvaultsProgramMeta,
                                     final PublicKey funderKey,
                                     final PublicKey tokenATokenProgramKey,
                                     final PublicKey tokenBTokenProgramKey,
                                     final PublicKey memoProgramKey,
                                     final PublicKey tokenAuthorityKey,
                                     final PublicKey whirlpoolKey,
                                     final PublicKey tokenOwnerAccountAKey,
                                     final PublicKey tokenVaultAKey,
                                     final PublicKey tokenOwnerAccountBKey,
                                     final PublicKey tokenVaultBKey,
                                     final PublicKey tokenMintAKey,
                                     final PublicKey tokenMintBKey,
                                     final PublicKey tickArray0Key,
                                     final PublicKey tickArray1Key,
                                     final PublicKey tickArray2Key,
                                     final PublicKey oracleKey,
                                     final PublicKey whirlpoolProgramKey,
                                     final long amount,
                                     final long otherAmountThreshold,
                                     final BigInteger sqrtPriceLimit,
                                     final boolean amountSpecifiedIsInput,
                                     final boolean aToB) {
    final var keys = List.of(
      createWritableSigner(funderKey),
      createRead(tokenATokenProgramKey),
      createRead(tokenBTokenProgramKey),
      createRead(memoProgramKey),
      createRead(tokenAuthorityKey),
      createRead(whirlpoolKey),
      createRead(tokenOwnerAccountAKey),
      createRead(tokenVaultAKey),
      createRead(tokenOwnerAccountBKey),
      createRead(tokenVaultBKey),
      createRead(tokenMintAKey),
      createRead(tokenMintBKey),
      createRead(tickArray0Key),
      createRead(tickArray1Key),
      createRead(tickArray2Key),
      createRead(oracleKey),
      createRead(whirlpoolProgramKey)
    );

    final byte[] _data = new byte[42];
    int i = ORCA_SWAP_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    putInt64LE(_data, i, otherAmountThreshold);
    i += 8;
    putInt128LE(_data, i, sqrtPriceLimit);
    i += 16;
    _data[i] = (byte) (amountSpecifiedIsInput ? 1 : 0);
    ++i;
    _data[i] = (byte) (aToB ? 1 : 0);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record OrcaSwapIxData(Discriminator discriminator,
                               long amount,
                               long otherAmountThreshold,
                               BigInteger sqrtPriceLimit,
                               boolean amountSpecifiedIsInput,
                               boolean aToB) implements Borsh {  

    public static OrcaSwapIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 42;

    public static OrcaSwapIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var otherAmountThreshold = getInt64LE(_data, i);
      i += 8;
      final var sqrtPriceLimit = getInt128LE(_data, i);
      i += 16;
      final var amountSpecifiedIsInput = _data[i] == 1;
      ++i;
      final var aToB = _data[i] == 1;
      return new OrcaSwapIxData(discriminator,
                                amount,
                                otherAmountThreshold,
                                sqrtPriceLimit,
                                amountSpecifiedIsInput,
                                aToB);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      putInt64LE(_data, i, otherAmountThreshold);
      i += 8;
      putInt128LE(_data, i, sqrtPriceLimit);
      i += 16;
      _data[i] = (byte) (amountSpecifiedIsInput ? 1 : 0);
      ++i;
      _data[i] = (byte) (aToB ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SIGN_TERMS_DISCRIMINATOR = toDiscriminator(226, 42, 174, 143, 144, 159, 139, 1);

  public static Instruction signTerms(final AccountMeta invokedYvaultsProgramMeta,
                                      final PublicKey ownerKey,
                                      final PublicKey ownerSignatureStateKey,
                                      final PublicKey systemProgramKey,
                                      final PublicKey rentKey,
                                      final byte[] signature) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWrite(ownerSignatureStateKey),
      createRead(systemProgramKey),
      createRead(rentKey)
    );

    final byte[] _data = new byte[8 + Borsh.lenArray(signature)];
    int i = SIGN_TERMS_DISCRIMINATOR.write(_data, 0);
    Borsh.writeArray(signature, _data, i);

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, _data);
  }

  public record SignTermsIxData(Discriminator discriminator, byte[] signature) implements Borsh {  

    public static SignTermsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 72;
    public static final int SIGNATURE_LEN = 64;

    public static SignTermsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var signature = new byte[64];
      Borsh.readArray(signature, _data, i);
      return new SignTermsIxData(discriminator, signature);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeArray(signature, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_STRATEGY_ADMIN_DISCRIMINATOR = toDiscriminator(13, 227, 164, 236, 32, 39, 6, 255);

  public static Instruction updateStrategyAdmin(final AccountMeta invokedYvaultsProgramMeta, final PublicKey pendingAdminKey, final PublicKey strategyKey) {
    final var keys = List.of(
      createWritableSigner(pendingAdminKey),
      createWrite(strategyKey)
    );

    return Instruction.createInstruction(invokedYvaultsProgramMeta, keys, UPDATE_STRATEGY_ADMIN_DISCRIMINATOR);
  }

  private YvaultsProgram() {
  }
}
