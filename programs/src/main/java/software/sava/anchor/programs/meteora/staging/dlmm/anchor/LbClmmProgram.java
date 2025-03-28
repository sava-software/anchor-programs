package software.sava.anchor.programs.meteora.staging.dlmm.anchor;

import java.util.List;
import java.util.OptionalInt;

import software.sava.anchor.programs.meteora.staging.dlmm.anchor.types.AddLiquiditySingleSidePreciseParameter;
import software.sava.anchor.programs.meteora.staging.dlmm.anchor.types.AddLiquiditySingleSidePreciseParameter2;
import software.sava.anchor.programs.meteora.staging.dlmm.anchor.types.BaseFeeParameter;
import software.sava.anchor.programs.meteora.staging.dlmm.anchor.types.BinLiquidityReduction;
import software.sava.anchor.programs.meteora.staging.dlmm.anchor.types.CustomizableParams;
import software.sava.anchor.programs.meteora.staging.dlmm.anchor.types.DynamicFeeParameter;
import software.sava.anchor.programs.meteora.staging.dlmm.anchor.types.InitPermissionPairIx;
import software.sava.anchor.programs.meteora.staging.dlmm.anchor.types.InitPresetParameters2Ix;
import software.sava.anchor.programs.meteora.staging.dlmm.anchor.types.InitPresetParametersIx;
import software.sava.anchor.programs.meteora.staging.dlmm.anchor.types.InitializeLbPair2Params;
import software.sava.anchor.programs.meteora.staging.dlmm.anchor.types.LiquidityOneSideParameter;
import software.sava.anchor.programs.meteora.staging.dlmm.anchor.types.LiquidityParameter;
import software.sava.anchor.programs.meteora.staging.dlmm.anchor.types.LiquidityParameterByStrategy;
import software.sava.anchor.programs.meteora.staging.dlmm.anchor.types.LiquidityParameterByStrategyOneSide;
import software.sava.anchor.programs.meteora.staging.dlmm.anchor.types.LiquidityParameterByWeight;
import software.sava.anchor.programs.meteora.staging.dlmm.anchor.types.RemainingAccountsInfo;
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
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class LbClmmProgram {

  public static final Discriminator INITIALIZE_LB_PAIR_DISCRIMINATOR = toDiscriminator(45, 154, 237, 210, 221, 15, 166, 92);

  public static Instruction initializeLbPair(final AccountMeta invokedLbClmmProgramMeta,
                                             final PublicKey lbPairKey,
                                             final PublicKey binArrayBitmapExtensionKey,
                                             final PublicKey tokenMintXKey,
                                             final PublicKey tokenMintYKey,
                                             final PublicKey reserveXKey,
                                             final PublicKey reserveYKey,
                                             final PublicKey oracleKey,
                                             final PublicKey presetParameterKey,
                                             final PublicKey funderKey,
                                             final PublicKey tokenProgramKey,
                                             final PublicKey systemProgramKey,
                                             final PublicKey rentKey,
                                             final PublicKey eventAuthorityKey,
                                             final PublicKey programKey,
                                             final int activeId,
                                             final int binStep) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createRead(tokenMintXKey),
      createRead(tokenMintYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(oracleKey),
      createRead(presetParameterKey),
      createWritableSigner(funderKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[14];
    int i = writeDiscriminator(INITIALIZE_LB_PAIR_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, activeId);
    i += 4;
    putInt16LE(_data, i, binStep);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record InitializeLbPairIxData(Discriminator discriminator, int activeId, int binStep) implements Borsh {  

    public static InitializeLbPairIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 14;

    public static InitializeLbPairIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var activeId = getInt32LE(_data, i);
      i += 4;
      final var binStep = getInt16LE(_data, i);
      return new InitializeLbPairIxData(discriminator, activeId, binStep);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt32LE(_data, i, activeId);
      i += 4;
      putInt16LE(_data, i, binStep);
      i += 2;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_PERMISSION_LB_PAIR_DISCRIMINATOR = toDiscriminator(108, 102, 213, 85, 251, 3, 53, 21);

  public static Instruction initializePermissionLbPair(final AccountMeta invokedLbClmmProgramMeta,
                                                       final PublicKey baseKey,
                                                       final PublicKey lbPairKey,
                                                       final PublicKey binArrayBitmapExtensionKey,
                                                       final PublicKey tokenMintXKey,
                                                       final PublicKey tokenMintYKey,
                                                       final PublicKey reserveXKey,
                                                       final PublicKey reserveYKey,
                                                       final PublicKey oracleKey,
                                                       final PublicKey adminKey,
                                                       final PublicKey tokenBadgeXKey,
                                                       final PublicKey tokenBadgeYKey,
                                                       final PublicKey tokenProgramXKey,
                                                       final PublicKey tokenProgramYKey,
                                                       final PublicKey systemProgramKey,
                                                       final PublicKey rentKey,
                                                       final PublicKey eventAuthorityKey,
                                                       final PublicKey programKey,
                                                       final InitPermissionPairIx ixData) {
    final var keys = List.of(
      createReadOnlySigner(baseKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createRead(tokenMintXKey),
      createRead(tokenMintYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(oracleKey),
      createWritableSigner(adminKey),
      createRead(requireNonNullElse(tokenBadgeXKey, invokedLbClmmProgramMeta.publicKey())),
      createRead(requireNonNullElse(tokenBadgeYKey, invokedLbClmmProgramMeta.publicKey())),
      createRead(tokenProgramXKey),
      createRead(tokenProgramYKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(ixData)];
    int i = writeDiscriminator(INITIALIZE_PERMISSION_LB_PAIR_DISCRIMINATOR, _data, 0);
    Borsh.write(ixData, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record InitializePermissionLbPairIxData(Discriminator discriminator, InitPermissionPairIx ixData) implements Borsh {  

    public static InitializePermissionLbPairIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 20;

    public static InitializePermissionLbPairIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var ixData = InitPermissionPairIx.read(_data, i);
      return new InitializePermissionLbPairIxData(discriminator, ixData);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(ixData, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_CUSTOMIZABLE_PERMISSIONLESS_LB_PAIR_DISCRIMINATOR = toDiscriminator(46, 39, 41, 135, 111, 183, 200, 64);

  public static Instruction initializeCustomizablePermissionlessLbPair(final AccountMeta invokedLbClmmProgramMeta,
                                                                       final PublicKey lbPairKey,
                                                                       final PublicKey binArrayBitmapExtensionKey,
                                                                       final PublicKey tokenMintXKey,
                                                                       final PublicKey tokenMintYKey,
                                                                       final PublicKey reserveXKey,
                                                                       final PublicKey reserveYKey,
                                                                       final PublicKey oracleKey,
                                                                       final PublicKey userTokenXKey,
                                                                       final PublicKey funderKey,
                                                                       final PublicKey tokenProgramKey,
                                                                       final PublicKey systemProgramKey,
                                                                       final PublicKey userTokenYKey,
                                                                       final PublicKey eventAuthorityKey,
                                                                       final PublicKey programKey,
                                                                       final CustomizableParams params) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createRead(tokenMintXKey),
      createRead(tokenMintYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(oracleKey),
      createRead(userTokenXKey),
      createWritableSigner(funderKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(userTokenYKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(INITIALIZE_CUSTOMIZABLE_PERMISSIONLESS_LB_PAIR_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record InitializeCustomizablePermissionlessLbPairIxData(Discriminator discriminator, CustomizableParams params) implements Borsh {  

    public static InitializeCustomizablePermissionlessLbPairIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static InitializeCustomizablePermissionlessLbPairIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = CustomizableParams.read(_data, i);
      return new InitializeCustomizablePermissionlessLbPairIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(params);
    }
  }

  public static final Discriminator INITIALIZE_BIN_ARRAY_BITMAP_EXTENSION_DISCRIMINATOR = toDiscriminator(47, 157, 226, 180, 12, 240, 33, 71);

  public static Instruction initializeBinArrayBitmapExtension(final AccountMeta invokedLbClmmProgramMeta,
                                                              final PublicKey lbPairKey,
                                                              // Initialize an account to store if a bin array is initialized.
                                                              final PublicKey binArrayBitmapExtensionKey,
                                                              final PublicKey funderKey,
                                                              final PublicKey systemProgramKey,
                                                              final PublicKey rentKey) {
    final var keys = List.of(
      createRead(lbPairKey),
      createWrite(binArrayBitmapExtensionKey),
      createWritableSigner(funderKey),
      createRead(systemProgramKey),
      createRead(rentKey)
    );

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, INITIALIZE_BIN_ARRAY_BITMAP_EXTENSION_DISCRIMINATOR);
  }

  public static final Discriminator INITIALIZE_BIN_ARRAY_DISCRIMINATOR = toDiscriminator(35, 86, 19, 185, 78, 212, 75, 211);

  public static Instruction initializeBinArray(final AccountMeta invokedLbClmmProgramMeta,
                                               final PublicKey lbPairKey,
                                               final PublicKey binArrayKey,
                                               final PublicKey funderKey,
                                               final PublicKey systemProgramKey,
                                               final long index) {
    final var keys = List.of(
      createRead(lbPairKey),
      createWrite(binArrayKey),
      createWritableSigner(funderKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(INITIALIZE_BIN_ARRAY_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, index);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record InitializeBinArrayIxData(Discriminator discriminator, long index) implements Borsh {  

    public static InitializeBinArrayIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static InitializeBinArrayIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var index = getInt64LE(_data, i);
      return new InitializeBinArrayIxData(discriminator, index);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, index);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ADD_LIQUIDITY_DISCRIMINATOR = toDiscriminator(181, 157, 89, 67, 143, 182, 52, 72);

  public static Instruction addLiquidity(final AccountMeta invokedLbClmmProgramMeta,
                                         final PublicKey positionKey,
                                         final PublicKey lbPairKey,
                                         final PublicKey binArrayBitmapExtensionKey,
                                         final PublicKey userTokenXKey,
                                         final PublicKey userTokenYKey,
                                         final PublicKey reserveXKey,
                                         final PublicKey reserveYKey,
                                         final PublicKey tokenXMintKey,
                                         final PublicKey tokenYMintKey,
                                         final PublicKey binArrayLowerKey,
                                         final PublicKey binArrayUpperKey,
                                         final PublicKey senderKey,
                                         final PublicKey tokenXProgramKey,
                                         final PublicKey tokenYProgramKey,
                                         final PublicKey eventAuthorityKey,
                                         final PublicKey programKey,
                                         final LiquidityParameter liquidityParameter) {
    final var keys = List.of(
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(userTokenXKey),
      createWrite(userTokenYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(binArrayLowerKey),
      createWrite(binArrayUpperKey),
      createReadOnlySigner(senderKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(liquidityParameter)];
    int i = writeDiscriminator(ADD_LIQUIDITY_DISCRIMINATOR, _data, 0);
    Borsh.write(liquidityParameter, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record AddLiquidityIxData(Discriminator discriminator, LiquidityParameter liquidityParameter) implements Borsh {  

    public static AddLiquidityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static AddLiquidityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityParameter = LiquidityParameter.read(_data, i);
      return new AddLiquidityIxData(discriminator, liquidityParameter);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(liquidityParameter, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(liquidityParameter);
    }
  }

  public static final Discriminator ADD_LIQUIDITY_BY_WEIGHT_DISCRIMINATOR = toDiscriminator(28, 140, 238, 99, 231, 162, 21, 149);

  public static Instruction addLiquidityByWeight(final AccountMeta invokedLbClmmProgramMeta,
                                                 final PublicKey positionKey,
                                                 final PublicKey lbPairKey,
                                                 final PublicKey binArrayBitmapExtensionKey,
                                                 final PublicKey userTokenXKey,
                                                 final PublicKey userTokenYKey,
                                                 final PublicKey reserveXKey,
                                                 final PublicKey reserveYKey,
                                                 final PublicKey tokenXMintKey,
                                                 final PublicKey tokenYMintKey,
                                                 final PublicKey binArrayLowerKey,
                                                 final PublicKey binArrayUpperKey,
                                                 final PublicKey senderKey,
                                                 final PublicKey tokenXProgramKey,
                                                 final PublicKey tokenYProgramKey,
                                                 final PublicKey eventAuthorityKey,
                                                 final PublicKey programKey,
                                                 final LiquidityParameterByWeight liquidityParameter) {
    final var keys = List.of(
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(userTokenXKey),
      createWrite(userTokenYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(binArrayLowerKey),
      createWrite(binArrayUpperKey),
      createReadOnlySigner(senderKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(liquidityParameter)];
    int i = writeDiscriminator(ADD_LIQUIDITY_BY_WEIGHT_DISCRIMINATOR, _data, 0);
    Borsh.write(liquidityParameter, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record AddLiquidityByWeightIxData(Discriminator discriminator, LiquidityParameterByWeight liquidityParameter) implements Borsh {  

    public static AddLiquidityByWeightIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static AddLiquidityByWeightIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityParameter = LiquidityParameterByWeight.read(_data, i);
      return new AddLiquidityByWeightIxData(discriminator, liquidityParameter);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(liquidityParameter, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(liquidityParameter);
    }
  }

  public static final Discriminator ADD_LIQUIDITY_BY_STRATEGY_DISCRIMINATOR = toDiscriminator(7, 3, 150, 127, 148, 40, 61, 200);

  public static Instruction addLiquidityByStrategy(final AccountMeta invokedLbClmmProgramMeta,
                                                   final PublicKey positionKey,
                                                   final PublicKey lbPairKey,
                                                   final PublicKey binArrayBitmapExtensionKey,
                                                   final PublicKey userTokenXKey,
                                                   final PublicKey userTokenYKey,
                                                   final PublicKey reserveXKey,
                                                   final PublicKey reserveYKey,
                                                   final PublicKey tokenXMintKey,
                                                   final PublicKey tokenYMintKey,
                                                   final PublicKey binArrayLowerKey,
                                                   final PublicKey binArrayUpperKey,
                                                   final PublicKey senderKey,
                                                   final PublicKey tokenXProgramKey,
                                                   final PublicKey tokenYProgramKey,
                                                   final PublicKey eventAuthorityKey,
                                                   final PublicKey programKey,
                                                   final LiquidityParameterByStrategy liquidityParameter) {
    final var keys = List.of(
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(userTokenXKey),
      createWrite(userTokenYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(binArrayLowerKey),
      createWrite(binArrayUpperKey),
      createReadOnlySigner(senderKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(liquidityParameter)];
    int i = writeDiscriminator(ADD_LIQUIDITY_BY_STRATEGY_DISCRIMINATOR, _data, 0);
    Borsh.write(liquidityParameter, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record AddLiquidityByStrategyIxData(Discriminator discriminator, LiquidityParameterByStrategy liquidityParameter) implements Borsh {  

    public static AddLiquidityByStrategyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 105;

    public static AddLiquidityByStrategyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityParameter = LiquidityParameterByStrategy.read(_data, i);
      return new AddLiquidityByStrategyIxData(discriminator, liquidityParameter);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(liquidityParameter, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ADD_LIQUIDITY_BY_STRATEGY_ONE_SIDE_DISCRIMINATOR = toDiscriminator(41, 5, 238, 175, 100, 225, 6, 205);

  public static Instruction addLiquidityByStrategyOneSide(final AccountMeta invokedLbClmmProgramMeta,
                                                          final PublicKey positionKey,
                                                          final PublicKey lbPairKey,
                                                          final PublicKey binArrayBitmapExtensionKey,
                                                          final PublicKey userTokenKey,
                                                          final PublicKey reserveKey,
                                                          final PublicKey tokenMintKey,
                                                          final PublicKey binArrayLowerKey,
                                                          final PublicKey binArrayUpperKey,
                                                          final PublicKey senderKey,
                                                          final PublicKey tokenProgramKey,
                                                          final PublicKey eventAuthorityKey,
                                                          final PublicKey programKey,
                                                          final LiquidityParameterByStrategyOneSide liquidityParameter) {
    final var keys = List.of(
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(userTokenKey),
      createWrite(reserveKey),
      createRead(tokenMintKey),
      createWrite(binArrayLowerKey),
      createWrite(binArrayUpperKey),
      createReadOnlySigner(senderKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(liquidityParameter)];
    int i = writeDiscriminator(ADD_LIQUIDITY_BY_STRATEGY_ONE_SIDE_DISCRIMINATOR, _data, 0);
    Borsh.write(liquidityParameter, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record AddLiquidityByStrategyOneSideIxData(Discriminator discriminator, LiquidityParameterByStrategyOneSide liquidityParameter) implements Borsh {  

    public static AddLiquidityByStrategyOneSideIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 97;

    public static AddLiquidityByStrategyOneSideIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityParameter = LiquidityParameterByStrategyOneSide.read(_data, i);
      return new AddLiquidityByStrategyOneSideIxData(discriminator, liquidityParameter);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(liquidityParameter, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ADD_LIQUIDITY_ONE_SIDE_DISCRIMINATOR = toDiscriminator(94, 155, 103, 151, 70, 95, 220, 165);

  public static Instruction addLiquidityOneSide(final AccountMeta invokedLbClmmProgramMeta,
                                                final PublicKey positionKey,
                                                final PublicKey lbPairKey,
                                                final PublicKey binArrayBitmapExtensionKey,
                                                final PublicKey userTokenKey,
                                                final PublicKey reserveKey,
                                                final PublicKey tokenMintKey,
                                                final PublicKey binArrayLowerKey,
                                                final PublicKey binArrayUpperKey,
                                                final PublicKey senderKey,
                                                final PublicKey tokenProgramKey,
                                                final PublicKey eventAuthorityKey,
                                                final PublicKey programKey,
                                                final LiquidityOneSideParameter liquidityParameter) {
    final var keys = List.of(
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(userTokenKey),
      createWrite(reserveKey),
      createRead(tokenMintKey),
      createWrite(binArrayLowerKey),
      createWrite(binArrayUpperKey),
      createReadOnlySigner(senderKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(liquidityParameter)];
    int i = writeDiscriminator(ADD_LIQUIDITY_ONE_SIDE_DISCRIMINATOR, _data, 0);
    Borsh.write(liquidityParameter, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record AddLiquidityOneSideIxData(Discriminator discriminator, LiquidityOneSideParameter liquidityParameter) implements Borsh {  

    public static AddLiquidityOneSideIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static AddLiquidityOneSideIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityParameter = LiquidityOneSideParameter.read(_data, i);
      return new AddLiquidityOneSideIxData(discriminator, liquidityParameter);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(liquidityParameter, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(liquidityParameter);
    }
  }

  public static final Discriminator REMOVE_LIQUIDITY_DISCRIMINATOR = toDiscriminator(80, 85, 209, 72, 24, 206, 177, 108);

  public static Instruction removeLiquidity(final AccountMeta invokedLbClmmProgramMeta,
                                            final PublicKey positionKey,
                                            final PublicKey lbPairKey,
                                            final PublicKey binArrayBitmapExtensionKey,
                                            final PublicKey userTokenXKey,
                                            final PublicKey userTokenYKey,
                                            final PublicKey reserveXKey,
                                            final PublicKey reserveYKey,
                                            final PublicKey tokenXMintKey,
                                            final PublicKey tokenYMintKey,
                                            final PublicKey binArrayLowerKey,
                                            final PublicKey binArrayUpperKey,
                                            final PublicKey senderKey,
                                            final PublicKey tokenXProgramKey,
                                            final PublicKey tokenYProgramKey,
                                            final PublicKey eventAuthorityKey,
                                            final PublicKey programKey,
                                            final BinLiquidityReduction[] binLiquidityRemoval) {
    final var keys = List.of(
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(userTokenXKey),
      createWrite(userTokenYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(binArrayLowerKey),
      createWrite(binArrayUpperKey),
      createReadOnlySigner(senderKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.lenVector(binLiquidityRemoval)];
    int i = writeDiscriminator(REMOVE_LIQUIDITY_DISCRIMINATOR, _data, 0);
    Borsh.writeVector(binLiquidityRemoval, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record RemoveLiquidityIxData(Discriminator discriminator, BinLiquidityReduction[] binLiquidityRemoval) implements Borsh {  

    public static RemoveLiquidityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static RemoveLiquidityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var binLiquidityRemoval = Borsh.readVector(BinLiquidityReduction.class, BinLiquidityReduction::read, _data, i);
      return new RemoveLiquidityIxData(discriminator, binLiquidityRemoval);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(binLiquidityRemoval, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(binLiquidityRemoval);
    }
  }

  public static final Discriminator INITIALIZE_POSITION_DISCRIMINATOR = toDiscriminator(219, 192, 234, 71, 190, 191, 102, 80);

  public static Instruction initializePosition(final AccountMeta invokedLbClmmProgramMeta,
                                               final PublicKey payerKey,
                                               final PublicKey positionKey,
                                               final PublicKey lbPairKey,
                                               final PublicKey ownerKey,
                                               final PublicKey systemProgramKey,
                                               final PublicKey rentKey,
                                               final PublicKey eventAuthorityKey,
                                               final PublicKey programKey,
                                               final int lowerBinId,
                                               final int width) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createWritableSigner(positionKey),
      createRead(lbPairKey),
      createReadOnlySigner(ownerKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(INITIALIZE_POSITION_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, lowerBinId);
    i += 4;
    putInt32LE(_data, i, width);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record InitializePositionIxData(Discriminator discriminator, int lowerBinId, int width) implements Borsh {  

    public static InitializePositionIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static InitializePositionIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lowerBinId = getInt32LE(_data, i);
      i += 4;
      final var width = getInt32LE(_data, i);
      return new InitializePositionIxData(discriminator, lowerBinId, width);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt32LE(_data, i, lowerBinId);
      i += 4;
      putInt32LE(_data, i, width);
      i += 4;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_POSITION_PDA_DISCRIMINATOR = toDiscriminator(46, 82, 125, 146, 85, 141, 228, 153);

  public static Instruction initializePositionPda(final AccountMeta invokedLbClmmProgramMeta,
                                                  final PublicKey payerKey,
                                                  final PublicKey baseKey,
                                                  final PublicKey positionKey,
                                                  final PublicKey lbPairKey,
                                                  // owner
                                                  final PublicKey ownerKey,
                                                  final PublicKey systemProgramKey,
                                                  final PublicKey rentKey,
                                                  final PublicKey eventAuthorityKey,
                                                  final PublicKey programKey,
                                                  final int lowerBinId,
                                                  final int width) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createReadOnlySigner(baseKey),
      createWrite(positionKey),
      createRead(lbPairKey),
      createReadOnlySigner(ownerKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(INITIALIZE_POSITION_PDA_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, lowerBinId);
    i += 4;
    putInt32LE(_data, i, width);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record InitializePositionPdaIxData(Discriminator discriminator, int lowerBinId, int width) implements Borsh {  

    public static InitializePositionPdaIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static InitializePositionPdaIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lowerBinId = getInt32LE(_data, i);
      i += 4;
      final var width = getInt32LE(_data, i);
      return new InitializePositionPdaIxData(discriminator, lowerBinId, width);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt32LE(_data, i, lowerBinId);
      i += 4;
      putInt32LE(_data, i, width);
      i += 4;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_POSITION_BY_OPERATOR_DISCRIMINATOR = toDiscriminator(251, 189, 190, 244, 117, 254, 35, 148);

  public static Instruction initializePositionByOperator(final AccountMeta invokedLbClmmProgramMeta,
                                                         final PublicKey payerKey,
                                                         final PublicKey baseKey,
                                                         final PublicKey positionKey,
                                                         final PublicKey lbPairKey,
                                                         final PublicKey ownerKey,
                                                         // operator
                                                         final PublicKey operatorKey,
                                                         final PublicKey operatorTokenXKey,
                                                         final PublicKey ownerTokenXKey,
                                                         final PublicKey systemProgramKey,
                                                         final PublicKey eventAuthorityKey,
                                                         final PublicKey programKey,
                                                         final int lowerBinId,
                                                         final int width,
                                                         final PublicKey feeOwner,
                                                         final long lockReleasePoint) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createReadOnlySigner(baseKey),
      createWrite(positionKey),
      createRead(lbPairKey),
      createRead(ownerKey),
      createReadOnlySigner(operatorKey),
      createRead(operatorTokenXKey),
      createRead(ownerTokenXKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[56];
    int i = writeDiscriminator(INITIALIZE_POSITION_BY_OPERATOR_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, lowerBinId);
    i += 4;
    putInt32LE(_data, i, width);
    i += 4;
    feeOwner.write(_data, i);
    i += 32;
    putInt64LE(_data, i, lockReleasePoint);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record InitializePositionByOperatorIxData(Discriminator discriminator,
                                                   int lowerBinId,
                                                   int width,
                                                   PublicKey feeOwner,
                                                   long lockReleasePoint) implements Borsh {  

    public static InitializePositionByOperatorIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 56;

    public static InitializePositionByOperatorIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lowerBinId = getInt32LE(_data, i);
      i += 4;
      final var width = getInt32LE(_data, i);
      i += 4;
      final var feeOwner = readPubKey(_data, i);
      i += 32;
      final var lockReleasePoint = getInt64LE(_data, i);
      return new InitializePositionByOperatorIxData(discriminator,
                                                    lowerBinId,
                                                    width,
                                                    feeOwner,
                                                    lockReleasePoint);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt32LE(_data, i, lowerBinId);
      i += 4;
      putInt32LE(_data, i, width);
      i += 4;
      feeOwner.write(_data, i);
      i += 32;
      putInt64LE(_data, i, lockReleasePoint);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_POSITION_OPERATOR_DISCRIMINATOR = toDiscriminator(202, 184, 103, 143, 180, 191, 116, 217);

  public static Instruction updatePositionOperator(final AccountMeta invokedLbClmmProgramMeta,
                                                   final PublicKey positionKey,
                                                   final PublicKey ownerKey,
                                                   final PublicKey eventAuthorityKey,
                                                   final PublicKey programKey,
                                                   final PublicKey operator) {
    final var keys = List.of(
      createWrite(positionKey),
      createReadOnlySigner(ownerKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(UPDATE_POSITION_OPERATOR_DISCRIMINATOR, _data, 0);
    operator.write(_data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record UpdatePositionOperatorIxData(Discriminator discriminator, PublicKey operator) implements Borsh {  

    public static UpdatePositionOperatorIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static UpdatePositionOperatorIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var operator = readPubKey(_data, i);
      return new UpdatePositionOperatorIxData(discriminator, operator);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      operator.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SWAP_DISCRIMINATOR = toDiscriminator(248, 198, 158, 145, 225, 117, 135, 200);

  public static Instruction swap(final AccountMeta invokedLbClmmProgramMeta,
                                 final PublicKey lbPairKey,
                                 final PublicKey binArrayBitmapExtensionKey,
                                 final PublicKey reserveXKey,
                                 final PublicKey reserveYKey,
                                 final PublicKey userTokenInKey,
                                 final PublicKey userTokenOutKey,
                                 final PublicKey tokenXMintKey,
                                 final PublicKey tokenYMintKey,
                                 final PublicKey oracleKey,
                                 final PublicKey hostFeeInKey,
                                 final PublicKey userKey,
                                 final PublicKey tokenXProgramKey,
                                 final PublicKey tokenYProgramKey,
                                 final PublicKey eventAuthorityKey,
                                 final PublicKey programKey,
                                 final long amountIn,
                                 final long minAmountOut) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createRead(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(userTokenInKey),
      createWrite(userTokenOutKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(oracleKey),
      createWrite(requireNonNullElse(hostFeeInKey, invokedLbClmmProgramMeta.publicKey())),
      createReadOnlySigner(userKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(SWAP_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountIn);
    i += 8;
    putInt64LE(_data, i, minAmountOut);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record SwapIxData(Discriminator discriminator, long amountIn, long minAmountOut) implements Borsh {  

    public static SwapIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static SwapIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amountIn = getInt64LE(_data, i);
      i += 8;
      final var minAmountOut = getInt64LE(_data, i);
      return new SwapIxData(discriminator, amountIn, minAmountOut);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amountIn);
      i += 8;
      putInt64LE(_data, i, minAmountOut);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SWAP_EXACT_OUT_DISCRIMINATOR = toDiscriminator(250, 73, 101, 33, 38, 207, 75, 184);

  public static Instruction swapExactOut(final AccountMeta invokedLbClmmProgramMeta,
                                         final PublicKey lbPairKey,
                                         final PublicKey binArrayBitmapExtensionKey,
                                         final PublicKey reserveXKey,
                                         final PublicKey reserveYKey,
                                         final PublicKey userTokenInKey,
                                         final PublicKey userTokenOutKey,
                                         final PublicKey tokenXMintKey,
                                         final PublicKey tokenYMintKey,
                                         final PublicKey oracleKey,
                                         final PublicKey hostFeeInKey,
                                         final PublicKey userKey,
                                         final PublicKey tokenXProgramKey,
                                         final PublicKey tokenYProgramKey,
                                         final PublicKey eventAuthorityKey,
                                         final PublicKey programKey,
                                         final long maxInAmount,
                                         final long outAmount) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createRead(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(userTokenInKey),
      createWrite(userTokenOutKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(oracleKey),
      createWrite(requireNonNullElse(hostFeeInKey, invokedLbClmmProgramMeta.publicKey())),
      createReadOnlySigner(userKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(SWAP_EXACT_OUT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, maxInAmount);
    i += 8;
    putInt64LE(_data, i, outAmount);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record SwapExactOutIxData(Discriminator discriminator, long maxInAmount, long outAmount) implements Borsh {  

    public static SwapExactOutIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static SwapExactOutIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var maxInAmount = getInt64LE(_data, i);
      i += 8;
      final var outAmount = getInt64LE(_data, i);
      return new SwapExactOutIxData(discriminator, maxInAmount, outAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, maxInAmount);
      i += 8;
      putInt64LE(_data, i, outAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SWAP_WITH_PRICE_IMPACT_DISCRIMINATOR = toDiscriminator(56, 173, 230, 208, 173, 228, 156, 205);

  public static Instruction swapWithPriceImpact(final AccountMeta invokedLbClmmProgramMeta,
                                                final PublicKey lbPairKey,
                                                final PublicKey binArrayBitmapExtensionKey,
                                                final PublicKey reserveXKey,
                                                final PublicKey reserveYKey,
                                                final PublicKey userTokenInKey,
                                                final PublicKey userTokenOutKey,
                                                final PublicKey tokenXMintKey,
                                                final PublicKey tokenYMintKey,
                                                final PublicKey oracleKey,
                                                final PublicKey hostFeeInKey,
                                                final PublicKey userKey,
                                                final PublicKey tokenXProgramKey,
                                                final PublicKey tokenYProgramKey,
                                                final PublicKey eventAuthorityKey,
                                                final PublicKey programKey,
                                                final long amountIn,
                                                final OptionalInt activeId,
                                                final int maxPriceImpactBps) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createRead(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(userTokenInKey),
      createWrite(userTokenOutKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(oracleKey),
      createWrite(requireNonNullElse(hostFeeInKey, invokedLbClmmProgramMeta.publicKey())),
      createReadOnlySigner(userKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[
        18
        + (activeId == null || activeId.isEmpty() ? 1 : 5)
    ];
    int i = writeDiscriminator(SWAP_WITH_PRICE_IMPACT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountIn);
    i += 8;
    i += Borsh.writeOptional(activeId, _data, i);
    putInt16LE(_data, i, maxPriceImpactBps);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record SwapWithPriceImpactIxData(Discriminator discriminator,
                                          long amountIn,
                                          OptionalInt activeId,
                                          int maxPriceImpactBps) implements Borsh {  

    public static SwapWithPriceImpactIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static SwapWithPriceImpactIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amountIn = getInt64LE(_data, i);
      i += 8;
      final var activeId = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
      if (activeId.isPresent()) {
        i += 4;
      }
      final var maxPriceImpactBps = getInt16LE(_data, i);
      return new SwapWithPriceImpactIxData(discriminator, amountIn, activeId, maxPriceImpactBps);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amountIn);
      i += 8;
      i += Borsh.writeOptional(activeId, _data, i);
      putInt16LE(_data, i, maxPriceImpactBps);
      i += 2;
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + (activeId == null || activeId.isEmpty() ? 1 : (1 + 4)) + 2;
    }
  }

  public static final Discriminator WITHDRAW_PROTOCOL_FEE_DISCRIMINATOR = toDiscriminator(158, 201, 158, 189, 33, 93, 162, 103);

  public static Instruction withdrawProtocolFee(final AccountMeta invokedLbClmmProgramMeta,
                                                final PublicKey lbPairKey,
                                                final PublicKey reserveXKey,
                                                final PublicKey reserveYKey,
                                                final PublicKey tokenXMintKey,
                                                final PublicKey tokenYMintKey,
                                                final PublicKey receiverTokenXKey,
                                                final PublicKey receiverTokenYKey,
                                                final PublicKey claimFeeOperatorKey,
                                                // operator
                                                final PublicKey operatorKey,
                                                final PublicKey tokenXProgramKey,
                                                final PublicKey tokenYProgramKey,
                                                final PublicKey memoProgramKey,
                                                final long amountX,
                                                final long amountY,
                                                final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(receiverTokenXKey),
      createWrite(receiverTokenYKey),
      createRead(claimFeeOperatorKey),
      createReadOnlySigner(operatorKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(memoProgramKey)
    );

    final byte[] _data = new byte[24 + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(WITHDRAW_PROTOCOL_FEE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountX);
    i += 8;
    putInt64LE(_data, i, amountY);
    i += 8;
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record WithdrawProtocolFeeIxData(Discriminator discriminator,
                                          long amountX,
                                          long amountY,
                                          RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static WithdrawProtocolFeeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static WithdrawProtocolFeeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amountX = getInt64LE(_data, i);
      i += 8;
      final var amountY = getInt64LE(_data, i);
      i += 8;
      final var remainingAccountsInfo = RemainingAccountsInfo.read(_data, i);
      return new WithdrawProtocolFeeIxData(discriminator, amountX, amountY, remainingAccountsInfo);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amountX);
      i += 8;
      putInt64LE(_data, i, amountY);
      i += 8;
      i += Borsh.write(remainingAccountsInfo, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + 8 + Borsh.len(remainingAccountsInfo);
    }
  }

  public static final Discriminator INITIALIZE_REWARD_DISCRIMINATOR = toDiscriminator(95, 135, 192, 196, 242, 129, 230, 68);

  public static Instruction initializeReward(final AccountMeta invokedLbClmmProgramMeta,
                                             final PublicKey lbPairKey,
                                             final PublicKey rewardVaultKey,
                                             final PublicKey rewardMintKey,
                                             final PublicKey tokenBadgeKey,
                                             final PublicKey adminKey,
                                             final PublicKey tokenProgramKey,
                                             final PublicKey systemProgramKey,
                                             final PublicKey rentKey,
                                             final PublicKey eventAuthorityKey,
                                             final PublicKey programKey,
                                             final long rewardIndex,
                                             final long rewardDuration,
                                             final PublicKey funder) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createWrite(rewardVaultKey),
      createRead(rewardMintKey),
      createRead(requireNonNullElse(tokenBadgeKey, invokedLbClmmProgramMeta.publicKey())),
      createWritableSigner(adminKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[56];
    int i = writeDiscriminator(INITIALIZE_REWARD_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, rewardIndex);
    i += 8;
    putInt64LE(_data, i, rewardDuration);
    i += 8;
    funder.write(_data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record InitializeRewardIxData(Discriminator discriminator,
                                       long rewardIndex,
                                       long rewardDuration,
                                       PublicKey funder) implements Borsh {  

    public static InitializeRewardIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 56;

    public static InitializeRewardIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var rewardIndex = getInt64LE(_data, i);
      i += 8;
      final var rewardDuration = getInt64LE(_data, i);
      i += 8;
      final var funder = readPubKey(_data, i);
      return new InitializeRewardIxData(discriminator, rewardIndex, rewardDuration, funder);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, rewardIndex);
      i += 8;
      putInt64LE(_data, i, rewardDuration);
      i += 8;
      funder.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator FUND_REWARD_DISCRIMINATOR = toDiscriminator(188, 50, 249, 165, 93, 151, 38, 63);

  public static Instruction fundReward(final AccountMeta invokedLbClmmProgramMeta,
                                       final PublicKey lbPairKey,
                                       final PublicKey rewardVaultKey,
                                       final PublicKey rewardMintKey,
                                       final PublicKey funderTokenAccountKey,
                                       final PublicKey funderKey,
                                       final PublicKey binArrayKey,
                                       final PublicKey tokenProgramKey,
                                       final PublicKey eventAuthorityKey,
                                       final PublicKey programKey,
                                       final long rewardIndex,
                                       final long amount,
                                       final boolean carryForward,
                                       final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createWrite(rewardVaultKey),
      createRead(rewardMintKey),
      createWrite(funderTokenAccountKey),
      createReadOnlySigner(funderKey),
      createWrite(binArrayKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[25 + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(FUND_REWARD_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, rewardIndex);
    i += 8;
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) (carryForward ? 1 : 0);
    ++i;
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record FundRewardIxData(Discriminator discriminator,
                                 long rewardIndex,
                                 long amount,
                                 boolean carryForward,
                                 RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static FundRewardIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static FundRewardIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var rewardIndex = getInt64LE(_data, i);
      i += 8;
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var carryForward = _data[i] == 1;
      ++i;
      final var remainingAccountsInfo = RemainingAccountsInfo.read(_data, i);
      return new FundRewardIxData(discriminator,
                                  rewardIndex,
                                  amount,
                                  carryForward,
                                  remainingAccountsInfo);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, rewardIndex);
      i += 8;
      putInt64LE(_data, i, amount);
      i += 8;
      _data[i] = (byte) (carryForward ? 1 : 0);
      ++i;
      i += Borsh.write(remainingAccountsInfo, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + 8 + 1 + Borsh.len(remainingAccountsInfo);
    }
  }

  public static final Discriminator UPDATE_REWARD_FUNDER_DISCRIMINATOR = toDiscriminator(211, 28, 48, 32, 215, 160, 35, 23);

  public static Instruction updateRewardFunder(final AccountMeta invokedLbClmmProgramMeta,
                                               final PublicKey lbPairKey,
                                               final PublicKey adminKey,
                                               final PublicKey eventAuthorityKey,
                                               final PublicKey programKey,
                                               final long rewardIndex,
                                               final PublicKey newFunder) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createReadOnlySigner(adminKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[48];
    int i = writeDiscriminator(UPDATE_REWARD_FUNDER_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, rewardIndex);
    i += 8;
    newFunder.write(_data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record UpdateRewardFunderIxData(Discriminator discriminator, long rewardIndex, PublicKey newFunder) implements Borsh {  

    public static UpdateRewardFunderIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 48;

    public static UpdateRewardFunderIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var rewardIndex = getInt64LE(_data, i);
      i += 8;
      final var newFunder = readPubKey(_data, i);
      return new UpdateRewardFunderIxData(discriminator, rewardIndex, newFunder);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, rewardIndex);
      i += 8;
      newFunder.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_REWARD_DURATION_DISCRIMINATOR = toDiscriminator(138, 174, 196, 169, 213, 235, 254, 107);

  public static Instruction updateRewardDuration(final AccountMeta invokedLbClmmProgramMeta,
                                                 final PublicKey lbPairKey,
                                                 final PublicKey adminKey,
                                                 final PublicKey binArrayKey,
                                                 final PublicKey eventAuthorityKey,
                                                 final PublicKey programKey,
                                                 final long rewardIndex,
                                                 final long newDuration) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createReadOnlySigner(adminKey),
      createWrite(binArrayKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(UPDATE_REWARD_DURATION_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, rewardIndex);
    i += 8;
    putInt64LE(_data, i, newDuration);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record UpdateRewardDurationIxData(Discriminator discriminator, long rewardIndex, long newDuration) implements Borsh {  

    public static UpdateRewardDurationIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static UpdateRewardDurationIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var rewardIndex = getInt64LE(_data, i);
      i += 8;
      final var newDuration = getInt64LE(_data, i);
      return new UpdateRewardDurationIxData(discriminator, rewardIndex, newDuration);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, rewardIndex);
      i += 8;
      putInt64LE(_data, i, newDuration);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CLAIM_REWARD_DISCRIMINATOR = toDiscriminator(149, 95, 181, 242, 94, 90, 158, 162);

  public static Instruction claimReward(final AccountMeta invokedLbClmmProgramMeta,
                                        final PublicKey lbPairKey,
                                        final PublicKey positionKey,
                                        final PublicKey binArrayLowerKey,
                                        final PublicKey binArrayUpperKey,
                                        final PublicKey senderKey,
                                        final PublicKey rewardVaultKey,
                                        final PublicKey rewardMintKey,
                                        final PublicKey userTokenAccountKey,
                                        final PublicKey tokenProgramKey,
                                        final PublicKey eventAuthorityKey,
                                        final PublicKey programKey,
                                        final long rewardIndex) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createWrite(positionKey),
      createWrite(binArrayLowerKey),
      createWrite(binArrayUpperKey),
      createReadOnlySigner(senderKey),
      createWrite(rewardVaultKey),
      createRead(rewardMintKey),
      createWrite(userTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(CLAIM_REWARD_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, rewardIndex);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record ClaimRewardIxData(Discriminator discriminator, long rewardIndex) implements Borsh {  

    public static ClaimRewardIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static ClaimRewardIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var rewardIndex = getInt64LE(_data, i);
      return new ClaimRewardIxData(discriminator, rewardIndex);
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

  public static final Discriminator CLAIM_FEE_DISCRIMINATOR = toDiscriminator(169, 32, 79, 137, 136, 232, 70, 137);

  public static Instruction claimFee(final AccountMeta invokedLbClmmProgramMeta,
                                     final PublicKey lbPairKey,
                                     final PublicKey positionKey,
                                     final PublicKey binArrayLowerKey,
                                     final PublicKey binArrayUpperKey,
                                     final PublicKey senderKey,
                                     final PublicKey reserveXKey,
                                     final PublicKey reserveYKey,
                                     final PublicKey userTokenXKey,
                                     final PublicKey userTokenYKey,
                                     final PublicKey tokenXMintKey,
                                     final PublicKey tokenYMintKey,
                                     final PublicKey tokenProgramKey,
                                     final PublicKey eventAuthorityKey,
                                     final PublicKey programKey) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createWrite(positionKey),
      createWrite(binArrayLowerKey),
      createWrite(binArrayUpperKey),
      createReadOnlySigner(senderKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(userTokenXKey),
      createWrite(userTokenYKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, CLAIM_FEE_DISCRIMINATOR);
  }

  public static final Discriminator CLOSE_POSITION_DISCRIMINATOR = toDiscriminator(123, 134, 81, 0, 49, 68, 98, 98);

  public static Instruction closePosition(final AccountMeta invokedLbClmmProgramMeta,
                                          final PublicKey positionKey,
                                          final PublicKey lbPairKey,
                                          final PublicKey binArrayLowerKey,
                                          final PublicKey binArrayUpperKey,
                                          final PublicKey senderKey,
                                          final PublicKey rentReceiverKey,
                                          final PublicKey eventAuthorityKey,
                                          final PublicKey programKey) {
    final var keys = List.of(
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(binArrayLowerKey),
      createWrite(binArrayUpperKey),
      createReadOnlySigner(senderKey),
      createWrite(rentReceiverKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, CLOSE_POSITION_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_BASE_FEE_PARAMETERS_DISCRIMINATOR = toDiscriminator(75, 168, 223, 161, 16, 195, 3, 47);

  public static Instruction updateBaseFeeParameters(final AccountMeta invokedLbClmmProgramMeta,
                                                    final PublicKey lbPairKey,
                                                    final PublicKey adminKey,
                                                    final PublicKey eventAuthorityKey,
                                                    final PublicKey programKey,
                                                    final BaseFeeParameter feeParameter) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createReadOnlySigner(adminKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(feeParameter)];
    int i = writeDiscriminator(UPDATE_BASE_FEE_PARAMETERS_DISCRIMINATOR, _data, 0);
    Borsh.write(feeParameter, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record UpdateBaseFeeParametersIxData(Discriminator discriminator, BaseFeeParameter feeParameter) implements Borsh {  

    public static UpdateBaseFeeParametersIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 13;

    public static UpdateBaseFeeParametersIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var feeParameter = BaseFeeParameter.read(_data, i);
      return new UpdateBaseFeeParametersIxData(discriminator, feeParameter);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(feeParameter, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_DYNAMIC_FEE_PARAMETERS_DISCRIMINATOR = toDiscriminator(92, 161, 46, 246, 255, 189, 22, 22);

  public static Instruction updateDynamicFeeParameters(final AccountMeta invokedLbClmmProgramMeta,
                                                       final PublicKey lbPairKey,
                                                       final PublicKey adminKey,
                                                       final PublicKey eventAuthorityKey,
                                                       final PublicKey programKey,
                                                       final DynamicFeeParameter feeParameter) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createReadOnlySigner(adminKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(feeParameter)];
    int i = writeDiscriminator(UPDATE_DYNAMIC_FEE_PARAMETERS_DISCRIMINATOR, _data, 0);
    Borsh.write(feeParameter, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record UpdateDynamicFeeParametersIxData(Discriminator discriminator, DynamicFeeParameter feeParameter) implements Borsh {  

    public static UpdateDynamicFeeParametersIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 22;

    public static UpdateDynamicFeeParametersIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var feeParameter = DynamicFeeParameter.read(_data, i);
      return new UpdateDynamicFeeParametersIxData(discriminator, feeParameter);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(feeParameter, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INCREASE_ORACLE_LENGTH_DISCRIMINATOR = toDiscriminator(190, 61, 125, 87, 103, 79, 158, 173);

  public static Instruction increaseOracleLength(final AccountMeta invokedLbClmmProgramMeta,
                                                 final PublicKey oracleKey,
                                                 final PublicKey funderKey,
                                                 final PublicKey systemProgramKey,
                                                 final PublicKey eventAuthorityKey,
                                                 final PublicKey programKey,
                                                 final long lengthToAdd) {
    final var keys = List.of(
      createWrite(oracleKey),
      createWritableSigner(funderKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(INCREASE_ORACLE_LENGTH_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lengthToAdd);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record IncreaseOracleLengthIxData(Discriminator discriminator, long lengthToAdd) implements Borsh {  

    public static IncreaseOracleLengthIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static IncreaseOracleLengthIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lengthToAdd = getInt64LE(_data, i);
      return new IncreaseOracleLengthIxData(discriminator, lengthToAdd);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lengthToAdd);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_PRESET_PARAMETER_DISCRIMINATOR = toDiscriminator(66, 188, 71, 211, 98, 109, 14, 186);

  public static Instruction initializePresetParameter(final AccountMeta invokedLbClmmProgramMeta,
                                                      final PublicKey presetParameterKey,
                                                      final PublicKey adminKey,
                                                      final PublicKey systemProgramKey,
                                                      final PublicKey rentKey,
                                                      final InitPresetParametersIx ix) {
    final var keys = List.of(
      createWrite(presetParameterKey),
      createWritableSigner(adminKey),
      createRead(systemProgramKey),
      createRead(rentKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(ix)];
    int i = writeDiscriminator(INITIALIZE_PRESET_PARAMETER_DISCRIMINATOR, _data, 0);
    Borsh.write(ix, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record InitializePresetParameterIxData(Discriminator discriminator, InitPresetParametersIx ix) implements Borsh {  

    public static InitializePresetParameterIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 28;

    public static InitializePresetParameterIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var ix = InitPresetParametersIx.read(_data, i);
      return new InitializePresetParameterIxData(discriminator, ix);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(ix, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CLOSE_PRESET_PARAMETER_DISCRIMINATOR = toDiscriminator(4, 148, 145, 100, 134, 26, 181, 61);

  public static Instruction closePresetParameter(final AccountMeta invokedLbClmmProgramMeta,
                                                 final PublicKey presetParameterKey,
                                                 final PublicKey adminKey,
                                                 final PublicKey rentReceiverKey) {
    final var keys = List.of(
      createWrite(presetParameterKey),
      createWritableSigner(adminKey),
      createWrite(rentReceiverKey)
    );

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, CLOSE_PRESET_PARAMETER_DISCRIMINATOR);
  }

  public static final Discriminator CLOSE_PRESET_PARAMETER2_DISCRIMINATOR = toDiscriminator(39, 25, 95, 107, 116, 17, 115, 28);

  public static Instruction closePresetParameter2(final AccountMeta invokedLbClmmProgramMeta,
                                                  final PublicKey presetParameterKey,
                                                  final PublicKey adminKey,
                                                  final PublicKey rentReceiverKey) {
    final var keys = List.of(
      createWrite(presetParameterKey),
      createWritableSigner(adminKey),
      createWrite(rentReceiverKey)
    );

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, CLOSE_PRESET_PARAMETER2_DISCRIMINATOR);
  }

  public static final Discriminator REMOVE_ALL_LIQUIDITY_DISCRIMINATOR = toDiscriminator(10, 51, 61, 35, 112, 105, 24, 85);

  public static Instruction removeAllLiquidity(final AccountMeta invokedLbClmmProgramMeta,
                                               final PublicKey positionKey,
                                               final PublicKey lbPairKey,
                                               final PublicKey binArrayBitmapExtensionKey,
                                               final PublicKey userTokenXKey,
                                               final PublicKey userTokenYKey,
                                               final PublicKey reserveXKey,
                                               final PublicKey reserveYKey,
                                               final PublicKey tokenXMintKey,
                                               final PublicKey tokenYMintKey,
                                               final PublicKey binArrayLowerKey,
                                               final PublicKey binArrayUpperKey,
                                               final PublicKey senderKey,
                                               final PublicKey tokenXProgramKey,
                                               final PublicKey tokenYProgramKey,
                                               final PublicKey eventAuthorityKey,
                                               final PublicKey programKey) {
    final var keys = List.of(
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(userTokenXKey),
      createWrite(userTokenYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(binArrayLowerKey),
      createWrite(binArrayUpperKey),
      createReadOnlySigner(senderKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, REMOVE_ALL_LIQUIDITY_DISCRIMINATOR);
  }

  public static final Discriminator SET_PAIR_STATUS_DISCRIMINATOR = toDiscriminator(67, 248, 231, 137, 154, 149, 217, 174);

  public static Instruction setPairStatus(final AccountMeta invokedLbClmmProgramMeta,
                                          final PublicKey lbPairKey,
                                          final PublicKey adminKey,
                                          final int status) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createReadOnlySigner(adminKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(SET_PAIR_STATUS_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) status;

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record SetPairStatusIxData(Discriminator discriminator, int status) implements Borsh {  

    public static SetPairStatusIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static SetPairStatusIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var status = _data[i] & 0xFF;
      return new SetPairStatusIxData(discriminator, status);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) status;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator MIGRATE_POSITION_DISCRIMINATOR = toDiscriminator(15, 132, 59, 50, 199, 6, 251, 46);

  public static Instruction migratePosition(final AccountMeta invokedLbClmmProgramMeta,
                                            final PublicKey positionV2Key,
                                            final PublicKey positionV1Key,
                                            final PublicKey lbPairKey,
                                            final PublicKey binArrayLowerKey,
                                            final PublicKey binArrayUpperKey,
                                            final PublicKey ownerKey,
                                            final PublicKey systemProgramKey,
                                            final PublicKey rentReceiverKey,
                                            final PublicKey eventAuthorityKey,
                                            final PublicKey programKey) {
    final var keys = List.of(
      createWritableSigner(positionV2Key),
      createWrite(positionV1Key),
      createRead(lbPairKey),
      createWrite(binArrayLowerKey),
      createWrite(binArrayUpperKey),
      createWritableSigner(ownerKey),
      createRead(systemProgramKey),
      createWrite(rentReceiverKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, MIGRATE_POSITION_DISCRIMINATOR);
  }

  public static final Discriminator MIGRATE_BIN_ARRAY_DISCRIMINATOR = toDiscriminator(17, 23, 159, 211, 101, 184, 41, 241);

  public static Instruction migrateBinArray(final AccountMeta invokedLbClmmProgramMeta, final PublicKey lbPairKey) {
    final var keys = List.of(
      createRead(lbPairKey)
    );

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, MIGRATE_BIN_ARRAY_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_FEES_AND_REWARDS_DISCRIMINATOR = toDiscriminator(154, 230, 250, 13, 236, 209, 75, 223);

  public static Instruction updateFeesAndRewards(final AccountMeta invokedLbClmmProgramMeta,
                                                 final PublicKey positionKey,
                                                 final PublicKey lbPairKey,
                                                 final PublicKey binArrayLowerKey,
                                                 final PublicKey binArrayUpperKey,
                                                 final PublicKey ownerKey) {
    final var keys = List.of(
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(binArrayLowerKey),
      createWrite(binArrayUpperKey),
      createReadOnlySigner(ownerKey)
    );

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, UPDATE_FEES_AND_REWARDS_DISCRIMINATOR);
  }

  public static final Discriminator WITHDRAW_INELIGIBLE_REWARD_DISCRIMINATOR = toDiscriminator(148, 206, 42, 195, 247, 49, 103, 8);

  public static Instruction withdrawIneligibleReward(final AccountMeta invokedLbClmmProgramMeta,
                                                     final PublicKey lbPairKey,
                                                     final PublicKey rewardVaultKey,
                                                     final PublicKey rewardMintKey,
                                                     final PublicKey funderTokenAccountKey,
                                                     final PublicKey funderKey,
                                                     final PublicKey binArrayKey,
                                                     final PublicKey tokenProgramKey,
                                                     final PublicKey memoProgramKey,
                                                     final PublicKey eventAuthorityKey,
                                                     final PublicKey programKey,
                                                     final long rewardIndex,
                                                     final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createWrite(rewardVaultKey),
      createRead(rewardMintKey),
      createWrite(funderTokenAccountKey),
      createReadOnlySigner(funderKey),
      createWrite(binArrayKey),
      createRead(tokenProgramKey),
      createRead(memoProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16 + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(WITHDRAW_INELIGIBLE_REWARD_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, rewardIndex);
    i += 8;
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record WithdrawIneligibleRewardIxData(Discriminator discriminator, long rewardIndex, RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static WithdrawIneligibleRewardIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static WithdrawIneligibleRewardIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var rewardIndex = getInt64LE(_data, i);
      i += 8;
      final var remainingAccountsInfo = RemainingAccountsInfo.read(_data, i);
      return new WithdrawIneligibleRewardIxData(discriminator, rewardIndex, remainingAccountsInfo);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, rewardIndex);
      i += 8;
      i += Borsh.write(remainingAccountsInfo, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + Borsh.len(remainingAccountsInfo);
    }
  }

  public static final Discriminator SET_ACTIVATION_POINT_DISCRIMINATOR = toDiscriminator(91, 249, 15, 165, 26, 129, 254, 125);

  public static Instruction setActivationPoint(final AccountMeta invokedLbClmmProgramMeta,
                                               final PublicKey lbPairKey,
                                               final PublicKey adminKey,
                                               final long activationPoint) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createWritableSigner(adminKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(SET_ACTIVATION_POINT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, activationPoint);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record SetActivationPointIxData(Discriminator discriminator, long activationPoint) implements Borsh {  

    public static SetActivationPointIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SetActivationPointIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var activationPoint = getInt64LE(_data, i);
      return new SetActivationPointIxData(discriminator, activationPoint);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, activationPoint);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REMOVE_LIQUIDITY_BY_RANGE_DISCRIMINATOR = toDiscriminator(26, 82, 102, 152, 240, 74, 105, 26);

  public static Instruction removeLiquidityByRange(final AccountMeta invokedLbClmmProgramMeta,
                                                   final PublicKey positionKey,
                                                   final PublicKey lbPairKey,
                                                   final PublicKey binArrayBitmapExtensionKey,
                                                   final PublicKey userTokenXKey,
                                                   final PublicKey userTokenYKey,
                                                   final PublicKey reserveXKey,
                                                   final PublicKey reserveYKey,
                                                   final PublicKey tokenXMintKey,
                                                   final PublicKey tokenYMintKey,
                                                   final PublicKey binArrayLowerKey,
                                                   final PublicKey binArrayUpperKey,
                                                   final PublicKey senderKey,
                                                   final PublicKey tokenXProgramKey,
                                                   final PublicKey tokenYProgramKey,
                                                   final PublicKey eventAuthorityKey,
                                                   final PublicKey programKey,
                                                   final int fromBinId,
                                                   final int toBinId,
                                                   final int bpsToRemove) {
    final var keys = List.of(
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(userTokenXKey),
      createWrite(userTokenYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(binArrayLowerKey),
      createWrite(binArrayUpperKey),
      createReadOnlySigner(senderKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[18];
    int i = writeDiscriminator(REMOVE_LIQUIDITY_BY_RANGE_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, fromBinId);
    i += 4;
    putInt32LE(_data, i, toBinId);
    i += 4;
    putInt16LE(_data, i, bpsToRemove);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record RemoveLiquidityByRangeIxData(Discriminator discriminator,
                                             int fromBinId,
                                             int toBinId,
                                             int bpsToRemove) implements Borsh {  

    public static RemoveLiquidityByRangeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 18;

    public static RemoveLiquidityByRangeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var fromBinId = getInt32LE(_data, i);
      i += 4;
      final var toBinId = getInt32LE(_data, i);
      i += 4;
      final var bpsToRemove = getInt16LE(_data, i);
      return new RemoveLiquidityByRangeIxData(discriminator, fromBinId, toBinId, bpsToRemove);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt32LE(_data, i, fromBinId);
      i += 4;
      putInt32LE(_data, i, toBinId);
      i += 4;
      putInt16LE(_data, i, bpsToRemove);
      i += 2;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ADD_LIQUIDITY_ONE_SIDE_PRECISE_DISCRIMINATOR = toDiscriminator(161, 194, 103, 84, 171, 71, 250, 154);

  public static Instruction addLiquidityOneSidePrecise(final AccountMeta invokedLbClmmProgramMeta,
                                                       final PublicKey positionKey,
                                                       final PublicKey lbPairKey,
                                                       final PublicKey binArrayBitmapExtensionKey,
                                                       final PublicKey userTokenKey,
                                                       final PublicKey reserveKey,
                                                       final PublicKey tokenMintKey,
                                                       final PublicKey binArrayLowerKey,
                                                       final PublicKey binArrayUpperKey,
                                                       final PublicKey senderKey,
                                                       final PublicKey tokenProgramKey,
                                                       final PublicKey eventAuthorityKey,
                                                       final PublicKey programKey,
                                                       final AddLiquiditySingleSidePreciseParameter parameter) {
    final var keys = List.of(
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(userTokenKey),
      createWrite(reserveKey),
      createRead(tokenMintKey),
      createWrite(binArrayLowerKey),
      createWrite(binArrayUpperKey),
      createReadOnlySigner(senderKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(parameter)];
    int i = writeDiscriminator(ADD_LIQUIDITY_ONE_SIDE_PRECISE_DISCRIMINATOR, _data, 0);
    Borsh.write(parameter, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record AddLiquidityOneSidePreciseIxData(Discriminator discriminator, AddLiquiditySingleSidePreciseParameter parameter) implements Borsh {  

    public static AddLiquidityOneSidePreciseIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static AddLiquidityOneSidePreciseIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var parameter = AddLiquiditySingleSidePreciseParameter.read(_data, i);
      return new AddLiquidityOneSidePreciseIxData(discriminator, parameter);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(parameter, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(parameter);
    }
  }

  public static final Discriminator GO_TO_A_BIN_DISCRIMINATOR = toDiscriminator(146, 72, 174, 224, 40, 253, 84, 174);

  public static Instruction goToABin(final AccountMeta invokedLbClmmProgramMeta,
                                     final PublicKey lbPairKey,
                                     final PublicKey binArrayBitmapExtensionKey,
                                     final PublicKey fromBinArrayKey,
                                     final PublicKey toBinArrayKey,
                                     final PublicKey eventAuthorityKey,
                                     final PublicKey programKey,
                                     final int binId) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createRead(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createRead(requireNonNullElse(fromBinArrayKey, invokedLbClmmProgramMeta.publicKey())),
      createRead(requireNonNullElse(toBinArrayKey, invokedLbClmmProgramMeta.publicKey())),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(GO_TO_A_BIN_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, binId);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record GoToABinIxData(Discriminator discriminator, int binId) implements Borsh {  

    public static GoToABinIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 12;

    public static GoToABinIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var binId = getInt32LE(_data, i);
      return new GoToABinIxData(discriminator, binId);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt32LE(_data, i, binId);
      i += 4;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_PRE_ACTIVATION_DURATION_DISCRIMINATOR = toDiscriminator(165, 61, 201, 244, 130, 159, 22, 100);

  public static Instruction setPreActivationDuration(final AccountMeta invokedLbClmmProgramMeta,
                                                     final PublicKey lbPairKey,
                                                     final PublicKey creatorKey,
                                                     final long preActivationDuration) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createReadOnlySigner(creatorKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(SET_PRE_ACTIVATION_DURATION_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, preActivationDuration);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record SetPreActivationDurationIxData(Discriminator discriminator, long preActivationDuration) implements Borsh {  

    public static SetPreActivationDurationIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SetPreActivationDurationIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var preActivationDuration = getInt64LE(_data, i);
      return new SetPreActivationDurationIxData(discriminator, preActivationDuration);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, preActivationDuration);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_PRE_ACTIVATION_SWAP_ADDRESS_DISCRIMINATOR = toDiscriminator(57, 139, 47, 123, 216, 80, 223, 10);

  public static Instruction setPreActivationSwapAddress(final AccountMeta invokedLbClmmProgramMeta,
                                                        final PublicKey lbPairKey,
                                                        final PublicKey creatorKey,
                                                        final PublicKey preActivationSwapAddress) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createReadOnlySigner(creatorKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(SET_PRE_ACTIVATION_SWAP_ADDRESS_DISCRIMINATOR, _data, 0);
    preActivationSwapAddress.write(_data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record SetPreActivationSwapAddressIxData(Discriminator discriminator, PublicKey preActivationSwapAddress) implements Borsh {  

    public static SetPreActivationSwapAddressIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static SetPreActivationSwapAddressIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var preActivationSwapAddress = readPubKey(_data, i);
      return new SetPreActivationSwapAddressIxData(discriminator, preActivationSwapAddress);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      preActivationSwapAddress.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_TOKEN_BADGE_DISCRIMINATOR = toDiscriminator(253, 77, 205, 95, 27, 224, 89, 223);

  public static Instruction initializeTokenBadge(final AccountMeta invokedLbClmmProgramMeta,
                                                 final PublicKey tokenMintKey,
                                                 final PublicKey tokenBadgeKey,
                                                 final PublicKey adminKey,
                                                 final PublicKey systemProgramKey) {
    final var keys = List.of(
      createRead(tokenMintKey),
      createWrite(tokenBadgeKey),
      createWritableSigner(adminKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, INITIALIZE_TOKEN_BADGE_DISCRIMINATOR);
  }

  public static final Discriminator CREATE_CLAIM_PROTOCOL_FEE_OPERATOR_DISCRIMINATOR = toDiscriminator(51, 19, 150, 252, 105, 157, 48, 91);

  public static Instruction createClaimProtocolFeeOperator(final AccountMeta invokedLbClmmProgramMeta,
                                                           final PublicKey claimFeeOperatorKey,
                                                           final PublicKey operatorKey,
                                                           final PublicKey adminKey,
                                                           final PublicKey systemProgramKey) {
    final var keys = List.of(
      createWrite(claimFeeOperatorKey),
      createRead(operatorKey),
      createWritableSigner(adminKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, CREATE_CLAIM_PROTOCOL_FEE_OPERATOR_DISCRIMINATOR);
  }

  public static final Discriminator CLOSE_CLAIM_PROTOCOL_FEE_OPERATOR_DISCRIMINATOR = toDiscriminator(8, 41, 87, 35, 80, 48, 121, 26);

  public static Instruction closeClaimProtocolFeeOperator(final AccountMeta invokedLbClmmProgramMeta,
                                                          final PublicKey claimFeeOperatorKey,
                                                          final PublicKey rentReceiverKey,
                                                          final PublicKey adminKey) {
    final var keys = List.of(
      createWrite(claimFeeOperatorKey),
      createWrite(rentReceiverKey),
      createReadOnlySigner(adminKey)
    );

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, CLOSE_CLAIM_PROTOCOL_FEE_OPERATOR_DISCRIMINATOR);
  }

  public static final Discriminator INITIALIZE_PRESET_PARAMETER2_DISCRIMINATOR = toDiscriminator(184, 7, 240, 171, 103, 47, 183, 121);

  public static Instruction initializePresetParameter2(final AccountMeta invokedLbClmmProgramMeta,
                                                       final PublicKey presetParameterKey,
                                                       final PublicKey adminKey,
                                                       final PublicKey systemProgramKey,
                                                       final InitPresetParameters2Ix ix) {
    final var keys = List.of(
      createWrite(presetParameterKey),
      createWritableSigner(adminKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(ix)];
    int i = writeDiscriminator(INITIALIZE_PRESET_PARAMETER2_DISCRIMINATOR, _data, 0);
    Borsh.write(ix, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record InitializePresetParameter2IxData(Discriminator discriminator, InitPresetParameters2Ix ix) implements Borsh {  

    public static InitializePresetParameter2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 31;

    public static InitializePresetParameter2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var ix = InitPresetParameters2Ix.read(_data, i);
      return new InitializePresetParameter2IxData(discriminator, ix);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(ix, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_LB_PAIR2_DISCRIMINATOR = toDiscriminator(73, 59, 36, 120, 237, 83, 108, 198);

  public static Instruction initializeLbPair2(final AccountMeta invokedLbClmmProgramMeta,
                                              final PublicKey lbPairKey,
                                              final PublicKey binArrayBitmapExtensionKey,
                                              final PublicKey tokenMintXKey,
                                              final PublicKey tokenMintYKey,
                                              final PublicKey reserveXKey,
                                              final PublicKey reserveYKey,
                                              final PublicKey oracleKey,
                                              final PublicKey presetParameterKey,
                                              final PublicKey funderKey,
                                              final PublicKey tokenBadgeXKey,
                                              final PublicKey tokenBadgeYKey,
                                              final PublicKey tokenProgramXKey,
                                              final PublicKey tokenProgramYKey,
                                              final PublicKey systemProgramKey,
                                              final PublicKey eventAuthorityKey,
                                              final PublicKey programKey,
                                              final InitializeLbPair2Params params) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createRead(tokenMintXKey),
      createRead(tokenMintYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(oracleKey),
      createRead(presetParameterKey),
      createWritableSigner(funderKey),
      createRead(requireNonNullElse(tokenBadgeXKey, invokedLbClmmProgramMeta.publicKey())),
      createRead(requireNonNullElse(tokenBadgeYKey, invokedLbClmmProgramMeta.publicKey())),
      createRead(tokenProgramXKey),
      createRead(tokenProgramYKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(INITIALIZE_LB_PAIR2_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record InitializeLbPair2IxData(Discriminator discriminator, InitializeLbPair2Params params) implements Borsh {  

    public static InitializeLbPair2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 108;

    public static InitializeLbPair2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = InitializeLbPair2Params.read(_data, i);
      return new InitializeLbPair2IxData(discriminator, params);
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

  public static final Discriminator INITIALIZE_CUSTOMIZABLE_PERMISSIONLESS_LB_PAIR2_DISCRIMINATOR = toDiscriminator(243, 73, 129, 126, 51, 19, 241, 107);

  public static Instruction initializeCustomizablePermissionlessLbPair2(final AccountMeta invokedLbClmmProgramMeta,
                                                                        final PublicKey lbPairKey,
                                                                        final PublicKey binArrayBitmapExtensionKey,
                                                                        final PublicKey tokenMintXKey,
                                                                        final PublicKey tokenMintYKey,
                                                                        final PublicKey reserveXKey,
                                                                        final PublicKey reserveYKey,
                                                                        final PublicKey oracleKey,
                                                                        final PublicKey userTokenXKey,
                                                                        final PublicKey funderKey,
                                                                        final PublicKey tokenBadgeXKey,
                                                                        final PublicKey tokenBadgeYKey,
                                                                        final PublicKey tokenProgramXKey,
                                                                        final PublicKey tokenProgramYKey,
                                                                        final PublicKey systemProgramKey,
                                                                        final PublicKey userTokenYKey,
                                                                        final PublicKey eventAuthorityKey,
                                                                        final PublicKey programKey,
                                                                        final CustomizableParams params) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createRead(tokenMintXKey),
      createRead(tokenMintYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(oracleKey),
      createRead(userTokenXKey),
      createWritableSigner(funderKey),
      createRead(requireNonNullElse(tokenBadgeXKey, invokedLbClmmProgramMeta.publicKey())),
      createRead(requireNonNullElse(tokenBadgeYKey, invokedLbClmmProgramMeta.publicKey())),
      createRead(tokenProgramXKey),
      createRead(tokenProgramYKey),
      createRead(systemProgramKey),
      createRead(userTokenYKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(INITIALIZE_CUSTOMIZABLE_PERMISSIONLESS_LB_PAIR2_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record InitializeCustomizablePermissionlessLbPair2IxData(Discriminator discriminator, CustomizableParams params) implements Borsh {  

    public static InitializeCustomizablePermissionlessLbPair2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static InitializeCustomizablePermissionlessLbPair2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = CustomizableParams.read(_data, i);
      return new InitializeCustomizablePermissionlessLbPair2IxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(params);
    }
  }

  public static final Discriminator CLAIM_FEE2_DISCRIMINATOR = toDiscriminator(112, 191, 101, 171, 28, 144, 127, 187);

  public static Instruction claimFee2(final AccountMeta invokedLbClmmProgramMeta,
                                      final PublicKey lbPairKey,
                                      final PublicKey positionKey,
                                      final PublicKey senderKey,
                                      final PublicKey reserveXKey,
                                      final PublicKey reserveYKey,
                                      final PublicKey userTokenXKey,
                                      final PublicKey userTokenYKey,
                                      final PublicKey tokenXMintKey,
                                      final PublicKey tokenYMintKey,
                                      final PublicKey tokenProgramXKey,
                                      final PublicKey tokenProgramYKey,
                                      final PublicKey memoProgramKey,
                                      final PublicKey eventAuthorityKey,
                                      final PublicKey programKey,
                                      final int minBinId,
                                      final int maxBinId,
                                      final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createWrite(positionKey),
      createReadOnlySigner(senderKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(userTokenXKey),
      createWrite(userTokenYKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createRead(tokenProgramXKey),
      createRead(tokenProgramYKey),
      createRead(memoProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16 + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(CLAIM_FEE2_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, minBinId);
    i += 4;
    putInt32LE(_data, i, maxBinId);
    i += 4;
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record ClaimFee2IxData(Discriminator discriminator,
                                int minBinId,
                                int maxBinId,
                                RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static ClaimFee2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ClaimFee2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var minBinId = getInt32LE(_data, i);
      i += 4;
      final var maxBinId = getInt32LE(_data, i);
      i += 4;
      final var remainingAccountsInfo = RemainingAccountsInfo.read(_data, i);
      return new ClaimFee2IxData(discriminator, minBinId, maxBinId, remainingAccountsInfo);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt32LE(_data, i, minBinId);
      i += 4;
      putInt32LE(_data, i, maxBinId);
      i += 4;
      i += Borsh.write(remainingAccountsInfo, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 4 + 4 + Borsh.len(remainingAccountsInfo);
    }
  }

  public static final Discriminator CLAIM_REWARD2_DISCRIMINATOR = toDiscriminator(190, 3, 127, 119, 178, 87, 157, 183);

  public static Instruction claimReward2(final AccountMeta invokedLbClmmProgramMeta,
                                         final PublicKey lbPairKey,
                                         final PublicKey positionKey,
                                         final PublicKey senderKey,
                                         final PublicKey rewardVaultKey,
                                         final PublicKey rewardMintKey,
                                         final PublicKey userTokenAccountKey,
                                         final PublicKey tokenProgramKey,
                                         final PublicKey memoProgramKey,
                                         final PublicKey eventAuthorityKey,
                                         final PublicKey programKey,
                                         final long rewardIndex,
                                         final int minBinId,
                                         final int maxBinId,
                                         final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createWrite(positionKey),
      createReadOnlySigner(senderKey),
      createWrite(rewardVaultKey),
      createRead(rewardMintKey),
      createWrite(userTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(memoProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[24 + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(CLAIM_REWARD2_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, rewardIndex);
    i += 8;
    putInt32LE(_data, i, minBinId);
    i += 4;
    putInt32LE(_data, i, maxBinId);
    i += 4;
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record ClaimReward2IxData(Discriminator discriminator,
                                   long rewardIndex,
                                   int minBinId,
                                   int maxBinId,
                                   RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static ClaimReward2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ClaimReward2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var rewardIndex = getInt64LE(_data, i);
      i += 8;
      final var minBinId = getInt32LE(_data, i);
      i += 4;
      final var maxBinId = getInt32LE(_data, i);
      i += 4;
      final var remainingAccountsInfo = RemainingAccountsInfo.read(_data, i);
      return new ClaimReward2IxData(discriminator,
                                    rewardIndex,
                                    minBinId,
                                    maxBinId,
                                    remainingAccountsInfo);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, rewardIndex);
      i += 8;
      putInt32LE(_data, i, minBinId);
      i += 4;
      putInt32LE(_data, i, maxBinId);
      i += 4;
      i += Borsh.write(remainingAccountsInfo, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + 4 + 4 + Borsh.len(remainingAccountsInfo);
    }
  }

  public static final Discriminator ADD_LIQUIDITY2_DISCRIMINATOR = toDiscriminator(228, 162, 78, 28, 70, 219, 116, 115);

  public static Instruction addLiquidity2(final AccountMeta invokedLbClmmProgramMeta,
                                          final PublicKey positionKey,
                                          final PublicKey lbPairKey,
                                          final PublicKey binArrayBitmapExtensionKey,
                                          final PublicKey userTokenXKey,
                                          final PublicKey userTokenYKey,
                                          final PublicKey reserveXKey,
                                          final PublicKey reserveYKey,
                                          final PublicKey tokenXMintKey,
                                          final PublicKey tokenYMintKey,
                                          final PublicKey senderKey,
                                          final PublicKey tokenXProgramKey,
                                          final PublicKey tokenYProgramKey,
                                          final PublicKey eventAuthorityKey,
                                          final PublicKey programKey,
                                          final LiquidityParameter liquidityParameter,
                                          final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(userTokenXKey),
      createWrite(userTokenYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createReadOnlySigner(senderKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(liquidityParameter) + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(ADD_LIQUIDITY2_DISCRIMINATOR, _data, 0);
    i += Borsh.write(liquidityParameter, _data, i);
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record AddLiquidity2IxData(Discriminator discriminator, LiquidityParameter liquidityParameter, RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static AddLiquidity2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static AddLiquidity2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityParameter = LiquidityParameter.read(_data, i);
      i += Borsh.len(liquidityParameter);
      final var remainingAccountsInfo = RemainingAccountsInfo.read(_data, i);
      return new AddLiquidity2IxData(discriminator, liquidityParameter, remainingAccountsInfo);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(liquidityParameter, _data, i);
      i += Borsh.write(remainingAccountsInfo, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(liquidityParameter) + Borsh.len(remainingAccountsInfo);
    }
  }

  public static final Discriminator ADD_LIQUIDITY_BY_STRATEGY2_DISCRIMINATOR = toDiscriminator(3, 221, 149, 218, 111, 141, 118, 213);

  public static Instruction addLiquidityByStrategy2(final AccountMeta invokedLbClmmProgramMeta,
                                                    final PublicKey positionKey,
                                                    final PublicKey lbPairKey,
                                                    final PublicKey binArrayBitmapExtensionKey,
                                                    final PublicKey userTokenXKey,
                                                    final PublicKey userTokenYKey,
                                                    final PublicKey reserveXKey,
                                                    final PublicKey reserveYKey,
                                                    final PublicKey tokenXMintKey,
                                                    final PublicKey tokenYMintKey,
                                                    final PublicKey senderKey,
                                                    final PublicKey tokenXProgramKey,
                                                    final PublicKey tokenYProgramKey,
                                                    final PublicKey eventAuthorityKey,
                                                    final PublicKey programKey,
                                                    final LiquidityParameterByStrategy liquidityParameter,
                                                    final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(userTokenXKey),
      createWrite(userTokenYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createReadOnlySigner(senderKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(liquidityParameter) + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(ADD_LIQUIDITY_BY_STRATEGY2_DISCRIMINATOR, _data, 0);
    i += Borsh.write(liquidityParameter, _data, i);
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record AddLiquidityByStrategy2IxData(Discriminator discriminator, LiquidityParameterByStrategy liquidityParameter, RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static AddLiquidityByStrategy2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static AddLiquidityByStrategy2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityParameter = LiquidityParameterByStrategy.read(_data, i);
      i += Borsh.len(liquidityParameter);
      final var remainingAccountsInfo = RemainingAccountsInfo.read(_data, i);
      return new AddLiquidityByStrategy2IxData(discriminator, liquidityParameter, remainingAccountsInfo);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(liquidityParameter, _data, i);
      i += Borsh.write(remainingAccountsInfo, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(liquidityParameter) + Borsh.len(remainingAccountsInfo);
    }
  }

  public static final Discriminator ADD_LIQUIDITY_ONE_SIDE_PRECISE2_DISCRIMINATOR = toDiscriminator(33, 51, 163, 201, 117, 98, 125, 231);

  public static Instruction addLiquidityOneSidePrecise2(final AccountMeta invokedLbClmmProgramMeta,
                                                        final PublicKey positionKey,
                                                        final PublicKey lbPairKey,
                                                        final PublicKey binArrayBitmapExtensionKey,
                                                        final PublicKey userTokenKey,
                                                        final PublicKey reserveKey,
                                                        final PublicKey tokenMintKey,
                                                        final PublicKey senderKey,
                                                        final PublicKey tokenProgramKey,
                                                        final PublicKey eventAuthorityKey,
                                                        final PublicKey programKey,
                                                        final AddLiquiditySingleSidePreciseParameter2 liquidityParameter,
                                                        final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(userTokenKey),
      createWrite(reserveKey),
      createRead(tokenMintKey),
      createReadOnlySigner(senderKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(liquidityParameter) + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(ADD_LIQUIDITY_ONE_SIDE_PRECISE2_DISCRIMINATOR, _data, 0);
    i += Borsh.write(liquidityParameter, _data, i);
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record AddLiquidityOneSidePrecise2IxData(Discriminator discriminator, AddLiquiditySingleSidePreciseParameter2 liquidityParameter, RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static AddLiquidityOneSidePrecise2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static AddLiquidityOneSidePrecise2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityParameter = AddLiquiditySingleSidePreciseParameter2.read(_data, i);
      i += Borsh.len(liquidityParameter);
      final var remainingAccountsInfo = RemainingAccountsInfo.read(_data, i);
      return new AddLiquidityOneSidePrecise2IxData(discriminator, liquidityParameter, remainingAccountsInfo);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(liquidityParameter, _data, i);
      i += Borsh.write(remainingAccountsInfo, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(liquidityParameter) + Borsh.len(remainingAccountsInfo);
    }
  }

  public static final Discriminator REMOVE_LIQUIDITY2_DISCRIMINATOR = toDiscriminator(230, 215, 82, 127, 241, 101, 227, 146);

  public static Instruction removeLiquidity2(final AccountMeta invokedLbClmmProgramMeta,
                                             final PublicKey positionKey,
                                             final PublicKey lbPairKey,
                                             final PublicKey binArrayBitmapExtensionKey,
                                             final PublicKey userTokenXKey,
                                             final PublicKey userTokenYKey,
                                             final PublicKey reserveXKey,
                                             final PublicKey reserveYKey,
                                             final PublicKey tokenXMintKey,
                                             final PublicKey tokenYMintKey,
                                             final PublicKey senderKey,
                                             final PublicKey tokenXProgramKey,
                                             final PublicKey tokenYProgramKey,
                                             final PublicKey memoProgramKey,
                                             final PublicKey eventAuthorityKey,
                                             final PublicKey programKey,
                                             final BinLiquidityReduction[] binLiquidityRemoval,
                                             final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(userTokenXKey),
      createWrite(userTokenYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createReadOnlySigner(senderKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(memoProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.lenVector(binLiquidityRemoval) + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(REMOVE_LIQUIDITY2_DISCRIMINATOR, _data, 0);
    i += Borsh.writeVector(binLiquidityRemoval, _data, i);
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record RemoveLiquidity2IxData(Discriminator discriminator, BinLiquidityReduction[] binLiquidityRemoval, RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static RemoveLiquidity2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static RemoveLiquidity2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var binLiquidityRemoval = Borsh.readVector(BinLiquidityReduction.class, BinLiquidityReduction::read, _data, i);
      i += Borsh.lenVector(binLiquidityRemoval);
      final var remainingAccountsInfo = RemainingAccountsInfo.read(_data, i);
      return new RemoveLiquidity2IxData(discriminator, binLiquidityRemoval, remainingAccountsInfo);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(binLiquidityRemoval, _data, i);
      i += Borsh.write(remainingAccountsInfo, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(binLiquidityRemoval) + Borsh.len(remainingAccountsInfo);
    }
  }

  public static final Discriminator REMOVE_LIQUIDITY_BY_RANGE2_DISCRIMINATOR = toDiscriminator(204, 2, 195, 145, 53, 145, 145, 205);

  public static Instruction removeLiquidityByRange2(final AccountMeta invokedLbClmmProgramMeta,
                                                    final PublicKey positionKey,
                                                    final PublicKey lbPairKey,
                                                    final PublicKey binArrayBitmapExtensionKey,
                                                    final PublicKey userTokenXKey,
                                                    final PublicKey userTokenYKey,
                                                    final PublicKey reserveXKey,
                                                    final PublicKey reserveYKey,
                                                    final PublicKey tokenXMintKey,
                                                    final PublicKey tokenYMintKey,
                                                    final PublicKey senderKey,
                                                    final PublicKey tokenXProgramKey,
                                                    final PublicKey tokenYProgramKey,
                                                    final PublicKey memoProgramKey,
                                                    final PublicKey eventAuthorityKey,
                                                    final PublicKey programKey,
                                                    final int fromBinId,
                                                    final int toBinId,
                                                    final int bpsToRemove,
                                                    final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(userTokenXKey),
      createWrite(userTokenYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createReadOnlySigner(senderKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(memoProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[18 + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(REMOVE_LIQUIDITY_BY_RANGE2_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, fromBinId);
    i += 4;
    putInt32LE(_data, i, toBinId);
    i += 4;
    putInt16LE(_data, i, bpsToRemove);
    i += 2;
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record RemoveLiquidityByRange2IxData(Discriminator discriminator,
                                              int fromBinId,
                                              int toBinId,
                                              int bpsToRemove,
                                              RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static RemoveLiquidityByRange2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static RemoveLiquidityByRange2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var fromBinId = getInt32LE(_data, i);
      i += 4;
      final var toBinId = getInt32LE(_data, i);
      i += 4;
      final var bpsToRemove = getInt16LE(_data, i);
      i += 2;
      final var remainingAccountsInfo = RemainingAccountsInfo.read(_data, i);
      return new RemoveLiquidityByRange2IxData(discriminator,
                                               fromBinId,
                                               toBinId,
                                               bpsToRemove,
                                               remainingAccountsInfo);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt32LE(_data, i, fromBinId);
      i += 4;
      putInt32LE(_data, i, toBinId);
      i += 4;
      putInt16LE(_data, i, bpsToRemove);
      i += 2;
      i += Borsh.write(remainingAccountsInfo, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 4 + 4 + 2 + Borsh.len(remainingAccountsInfo);
    }
  }

  public static final Discriminator SWAP2_DISCRIMINATOR = toDiscriminator(65, 75, 63, 76, 235, 91, 91, 136);

  public static Instruction swap2(final AccountMeta invokedLbClmmProgramMeta,
                                  final PublicKey lbPairKey,
                                  final PublicKey binArrayBitmapExtensionKey,
                                  final PublicKey reserveXKey,
                                  final PublicKey reserveYKey,
                                  final PublicKey userTokenInKey,
                                  final PublicKey userTokenOutKey,
                                  final PublicKey tokenXMintKey,
                                  final PublicKey tokenYMintKey,
                                  final PublicKey oracleKey,
                                  final PublicKey hostFeeInKey,
                                  final PublicKey userKey,
                                  final PublicKey tokenXProgramKey,
                                  final PublicKey tokenYProgramKey,
                                  final PublicKey memoProgramKey,
                                  final PublicKey eventAuthorityKey,
                                  final PublicKey programKey,
                                  final long amountIn,
                                  final long minAmountOut,
                                  final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createRead(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(userTokenInKey),
      createWrite(userTokenOutKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(oracleKey),
      createWrite(requireNonNullElse(hostFeeInKey, invokedLbClmmProgramMeta.publicKey())),
      createReadOnlySigner(userKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(memoProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[24 + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(SWAP2_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountIn);
    i += 8;
    putInt64LE(_data, i, minAmountOut);
    i += 8;
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record Swap2IxData(Discriminator discriminator,
                            long amountIn,
                            long minAmountOut,
                            RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static Swap2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static Swap2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amountIn = getInt64LE(_data, i);
      i += 8;
      final var minAmountOut = getInt64LE(_data, i);
      i += 8;
      final var remainingAccountsInfo = RemainingAccountsInfo.read(_data, i);
      return new Swap2IxData(discriminator, amountIn, minAmountOut, remainingAccountsInfo);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amountIn);
      i += 8;
      putInt64LE(_data, i, minAmountOut);
      i += 8;
      i += Borsh.write(remainingAccountsInfo, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + 8 + Borsh.len(remainingAccountsInfo);
    }
  }

  public static final Discriminator SWAP_EXACT_OUT2_DISCRIMINATOR = toDiscriminator(43, 215, 247, 132, 137, 60, 243, 81);

  public static Instruction swapExactOut2(final AccountMeta invokedLbClmmProgramMeta,
                                          final PublicKey lbPairKey,
                                          final PublicKey binArrayBitmapExtensionKey,
                                          final PublicKey reserveXKey,
                                          final PublicKey reserveYKey,
                                          final PublicKey userTokenInKey,
                                          final PublicKey userTokenOutKey,
                                          final PublicKey tokenXMintKey,
                                          final PublicKey tokenYMintKey,
                                          final PublicKey oracleKey,
                                          final PublicKey hostFeeInKey,
                                          final PublicKey userKey,
                                          final PublicKey tokenXProgramKey,
                                          final PublicKey tokenYProgramKey,
                                          final PublicKey memoProgramKey,
                                          final PublicKey eventAuthorityKey,
                                          final PublicKey programKey,
                                          final long maxInAmount,
                                          final long outAmount,
                                          final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createRead(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(userTokenInKey),
      createWrite(userTokenOutKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(oracleKey),
      createWrite(requireNonNullElse(hostFeeInKey, invokedLbClmmProgramMeta.publicKey())),
      createReadOnlySigner(userKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(memoProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[24 + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(SWAP_EXACT_OUT2_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, maxInAmount);
    i += 8;
    putInt64LE(_data, i, outAmount);
    i += 8;
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record SwapExactOut2IxData(Discriminator discriminator,
                                    long maxInAmount,
                                    long outAmount,
                                    RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static SwapExactOut2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static SwapExactOut2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var maxInAmount = getInt64LE(_data, i);
      i += 8;
      final var outAmount = getInt64LE(_data, i);
      i += 8;
      final var remainingAccountsInfo = RemainingAccountsInfo.read(_data, i);
      return new SwapExactOut2IxData(discriminator, maxInAmount, outAmount, remainingAccountsInfo);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, maxInAmount);
      i += 8;
      putInt64LE(_data, i, outAmount);
      i += 8;
      i += Borsh.write(remainingAccountsInfo, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + 8 + Borsh.len(remainingAccountsInfo);
    }
  }

  public static final Discriminator SWAP_WITH_PRICE_IMPACT2_DISCRIMINATOR = toDiscriminator(74, 98, 192, 214, 177, 51, 75, 51);

  public static Instruction swapWithPriceImpact2(final AccountMeta invokedLbClmmProgramMeta,
                                                 final PublicKey lbPairKey,
                                                 final PublicKey binArrayBitmapExtensionKey,
                                                 final PublicKey reserveXKey,
                                                 final PublicKey reserveYKey,
                                                 final PublicKey userTokenInKey,
                                                 final PublicKey userTokenOutKey,
                                                 final PublicKey tokenXMintKey,
                                                 final PublicKey tokenYMintKey,
                                                 final PublicKey oracleKey,
                                                 final PublicKey hostFeeInKey,
                                                 final PublicKey userKey,
                                                 final PublicKey tokenXProgramKey,
                                                 final PublicKey tokenYProgramKey,
                                                 final PublicKey memoProgramKey,
                                                 final PublicKey eventAuthorityKey,
                                                 final PublicKey programKey,
                                                 final long amountIn,
                                                 final OptionalInt activeId,
                                                 final int maxPriceImpactBps,
                                                 final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createRead(requireNonNullElse(binArrayBitmapExtensionKey, invokedLbClmmProgramMeta.publicKey())),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(userTokenInKey),
      createWrite(userTokenOutKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(oracleKey),
      createWrite(requireNonNullElse(hostFeeInKey, invokedLbClmmProgramMeta.publicKey())),
      createReadOnlySigner(userKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(memoProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[
        18
        + (activeId == null || activeId.isEmpty() ? 1 : 5) + Borsh.len(remainingAccountsInfo)
    ];
    int i = writeDiscriminator(SWAP_WITH_PRICE_IMPACT2_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountIn);
    i += 8;
    i += Borsh.writeOptional(activeId, _data, i);
    putInt16LE(_data, i, maxPriceImpactBps);
    i += 2;
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record SwapWithPriceImpact2IxData(Discriminator discriminator,
                                           long amountIn,
                                           OptionalInt activeId,
                                           int maxPriceImpactBps,
                                           RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static SwapWithPriceImpact2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static SwapWithPriceImpact2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amountIn = getInt64LE(_data, i);
      i += 8;
      final var activeId = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
      if (activeId.isPresent()) {
        i += 4;
      }
      final var maxPriceImpactBps = getInt16LE(_data, i);
      i += 2;
      final var remainingAccountsInfo = RemainingAccountsInfo.read(_data, i);
      return new SwapWithPriceImpact2IxData(discriminator,
                                            amountIn,
                                            activeId,
                                            maxPriceImpactBps,
                                            remainingAccountsInfo);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amountIn);
      i += 8;
      i += Borsh.writeOptional(activeId, _data, i);
      putInt16LE(_data, i, maxPriceImpactBps);
      i += 2;
      i += Borsh.write(remainingAccountsInfo, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + (activeId == null || activeId.isEmpty() ? 1 : (1 + 4)) + 2 + Borsh.len(remainingAccountsInfo);
    }
  }

  public static final Discriminator CLOSE_POSITION2_DISCRIMINATOR = toDiscriminator(174, 90, 35, 115, 186, 40, 147, 226);

  public static Instruction closePosition2(final AccountMeta invokedLbClmmProgramMeta,
                                           final PublicKey positionKey,
                                           final PublicKey senderKey,
                                           final PublicKey rentReceiverKey,
                                           final PublicKey eventAuthorityKey,
                                           final PublicKey programKey) {
    final var keys = List.of(
      createWrite(positionKey),
      createReadOnlySigner(senderKey),
      createWrite(rentReceiverKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, CLOSE_POSITION2_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_FEES_AND_REWARD2_DISCRIMINATOR = toDiscriminator(32, 142, 184, 154, 103, 65, 184, 88);

  public static Instruction updateFeesAndReward2(final AccountMeta invokedLbClmmProgramMeta,
                                                 final PublicKey positionKey,
                                                 final PublicKey lbPairKey,
                                                 final PublicKey ownerKey,
                                                 final int minBinId,
                                                 final int maxBinId) {
    final var keys = List.of(
      createWrite(positionKey),
      createWrite(lbPairKey),
      createReadOnlySigner(ownerKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(UPDATE_FEES_AND_REWARD2_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, minBinId);
    i += 4;
    putInt32LE(_data, i, maxBinId);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record UpdateFeesAndReward2IxData(Discriminator discriminator, int minBinId, int maxBinId) implements Borsh {  

    public static UpdateFeesAndReward2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static UpdateFeesAndReward2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var minBinId = getInt32LE(_data, i);
      i += 4;
      final var maxBinId = getInt32LE(_data, i);
      return new UpdateFeesAndReward2IxData(discriminator, minBinId, maxBinId);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt32LE(_data, i, minBinId);
      i += 4;
      putInt32LE(_data, i, maxBinId);
      i += 4;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CLOSE_POSITION_IF_EMPTY_DISCRIMINATOR = toDiscriminator(59, 124, 212, 118, 91, 152, 110, 157);

  public static Instruction closePositionIfEmpty(final AccountMeta invokedLbClmmProgramMeta,
                                                 final PublicKey positionKey,
                                                 final PublicKey senderKey,
                                                 final PublicKey rentReceiverKey,
                                                 final PublicKey eventAuthorityKey,
                                                 final PublicKey programKey) {
    final var keys = List.of(
      createWrite(positionKey),
      createReadOnlySigner(senderKey),
      createWrite(rentReceiverKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, CLOSE_POSITION_IF_EMPTY_DISCRIMINATOR);
  }

  private LbClmmProgram() {
  }
}
