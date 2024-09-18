package software.sava.anchor.programs.meteora.dlmm.anchor;

import java.util.List;
import java.util.OptionalInt;

import software.sava.anchor.programs.meteora.dlmm.anchor.types.AddLiquiditySingleSidePreciseParameter;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.BinLiquidityReduction;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.FeeParameter;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.InitPermissionPairIx;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.InitPresetParametersIx;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.LiquidityOneSideParameter;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.LiquidityParameter;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.LiquidityParameterByStrategy;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.LiquidityParameterByStrategyOneSide;
import software.sava.anchor.programs.meteora.dlmm.anchor.types.LiquidityParameterByWeight;
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
      createWrite(binArrayBitmapExtensionKey),
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
                                                       final PublicKey tokenProgramKey,
                                                       final PublicKey systemProgramKey,
                                                       final PublicKey rentKey,
                                                       final PublicKey eventAuthorityKey,
                                                       final PublicKey programKey,
                                                       final InitPermissionPairIx ixData) {
    final var keys = List.of(
      createReadOnlySigner(baseKey),
      createWrite(lbPairKey),
      createWrite(binArrayBitmapExtensionKey),
      createRead(tokenMintXKey),
      createRead(tokenMintYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(oracleKey),
      createWritableSigner(adminKey),
      createRead(tokenProgramKey),
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

    public static final int BYTES = 33;

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
      createWrite(binArrayBitmapExtensionKey),
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
      createWrite(binArrayBitmapExtensionKey),
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
      createWrite(binArrayBitmapExtensionKey),
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
      createWrite(binArrayBitmapExtensionKey),
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
      createWrite(binArrayBitmapExtensionKey),
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
      createWrite(binArrayBitmapExtensionKey),
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
                                                         // operator
                                                         final PublicKey operatorKey,
                                                         final PublicKey systemProgramKey,
                                                         final PublicKey rentKey,
                                                         final PublicKey eventAuthorityKey,
                                                         final PublicKey programKey,
                                                         final int lowerBinId,
                                                         final int width,
                                                         final PublicKey owner,
                                                         final PublicKey feeOwner) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createReadOnlySigner(baseKey),
      createWrite(positionKey),
      createRead(lbPairKey),
      createReadOnlySigner(operatorKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[80];
    int i = writeDiscriminator(INITIALIZE_POSITION_BY_OPERATOR_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, lowerBinId);
    i += 4;
    putInt32LE(_data, i, width);
    i += 4;
    owner.write(_data, i);
    i += 32;
    feeOwner.write(_data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record InitializePositionByOperatorIxData(Discriminator discriminator,
                                                   int lowerBinId,
                                                   int width,
                                                   PublicKey owner,
                                                   PublicKey feeOwner) implements Borsh {  

    public static InitializePositionByOperatorIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 80;

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
      final var owner = readPubKey(_data, i);
      i += 32;
      final var feeOwner = readPubKey(_data, i);
      return new InitializePositionByOperatorIxData(discriminator,
                                                    lowerBinId,
                                                    width,
                                                    owner,
                                                    feeOwner);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt32LE(_data, i, lowerBinId);
      i += 4;
      putInt32LE(_data, i, width);
      i += 4;
      owner.write(_data, i);
      i += 32;
      feeOwner.write(_data, i);
      i += 32;
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
      createRead(binArrayBitmapExtensionKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(userTokenInKey),
      createWrite(userTokenOutKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(oracleKey),
      createWrite(hostFeeInKey),
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
      createRead(binArrayBitmapExtensionKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(userTokenInKey),
      createWrite(userTokenOutKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(oracleKey),
      createWrite(hostFeeInKey),
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
      createRead(binArrayBitmapExtensionKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(userTokenInKey),
      createWrite(userTokenOutKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(oracleKey),
      createWrite(hostFeeInKey),
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
                                                final PublicKey tokenXProgramKey,
                                                final PublicKey tokenYProgramKey,
                                                final long amountX,
                                                final long amountY) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(receiverTokenXKey),
      createWrite(receiverTokenYKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(WITHDRAW_PROTOCOL_FEE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountX);
    i += 8;
    putInt64LE(_data, i, amountY);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record WithdrawProtocolFeeIxData(Discriminator discriminator, long amountX, long amountY) implements Borsh {  

    public static WithdrawProtocolFeeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static WithdrawProtocolFeeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amountX = getInt64LE(_data, i);
      i += 8;
      final var amountY = getInt64LE(_data, i);
      return new WithdrawProtocolFeeIxData(discriminator, amountX, amountY);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amountX);
      i += 8;
      putInt64LE(_data, i, amountY);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_REWARD_DISCRIMINATOR = toDiscriminator(95, 135, 192, 196, 242, 129, 230, 68);

  public static Instruction initializeReward(final AccountMeta invokedLbClmmProgramMeta,
                                             final PublicKey lbPairKey,
                                             final PublicKey rewardVaultKey,
                                             final PublicKey rewardMintKey,
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
                                       final boolean carryForward) {
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

    final byte[] _data = new byte[25];
    int i = writeDiscriminator(FUND_REWARD_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, rewardIndex);
    i += 8;
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) (carryForward ? 1 : 0);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record FundRewardIxData(Discriminator discriminator,
                                 long rewardIndex,
                                 long amount,
                                 boolean carryForward) implements Borsh {  

    public static FundRewardIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 25;

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
      return new FundRewardIxData(discriminator, rewardIndex, amount, carryForward);
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
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
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

  public static final Discriminator UPDATE_FEE_PARAMETERS_DISCRIMINATOR = toDiscriminator(128, 128, 208, 91, 246, 53, 31, 176);

  public static Instruction updateFeeParameters(final AccountMeta invokedLbClmmProgramMeta,
                                                final PublicKey lbPairKey,
                                                final PublicKey adminKey,
                                                final PublicKey eventAuthorityKey,
                                                final PublicKey programKey,
                                                final FeeParameter feeParameter) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createReadOnlySigner(adminKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(feeParameter)];
    int i = writeDiscriminator(UPDATE_FEE_PARAMETERS_DISCRIMINATOR, _data, 0);
    Borsh.write(feeParameter, _data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record UpdateFeeParametersIxData(Discriminator discriminator, FeeParameter feeParameter) implements Borsh {  

    public static UpdateFeeParametersIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 12;

    public static UpdateFeeParametersIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var feeParameter = FeeParameter.read(_data, i);
      return new UpdateFeeParametersIxData(discriminator, feeParameter);
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

    public static final int BYTES = 36;

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
      createWrite(binArrayBitmapExtensionKey),
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

  public static final Discriminator REMOVE_LIQUIDITY_SINGLE_SIDE_DISCRIMINATOR = toDiscriminator(84, 84, 177, 66, 254, 185, 10, 251);

  public static Instruction removeLiquiditySingleSide(final AccountMeta invokedLbClmmProgramMeta,
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
                                                      final PublicKey programKey) {
    final var keys = List.of(
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(binArrayBitmapExtensionKey),
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

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, REMOVE_LIQUIDITY_SINGLE_SIDE_DISCRIMINATOR);
  }

  public static final Discriminator TOGGLE_PAIR_STATUS_DISCRIMINATOR = toDiscriminator(61, 115, 52, 23, 46, 13, 31, 144);

  public static Instruction togglePairStatus(final AccountMeta invokedLbClmmProgramMeta, final PublicKey lbPairKey, final PublicKey adminKey) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createReadOnlySigner(adminKey)
    );

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, TOGGLE_PAIR_STATUS_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_WHITELISTED_WALLET_DISCRIMINATOR = toDiscriminator(4, 105, 92, 167, 132, 28, 9, 90);

  public static Instruction updateWhitelistedWallet(final AccountMeta invokedLbClmmProgramMeta,
                                                    final PublicKey lbPairKey,
                                                    final PublicKey creatorKey,
                                                    final PublicKey wallet) {
    final var keys = List.of(
      createWrite(lbPairKey),
      createReadOnlySigner(creatorKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(UPDATE_WHITELISTED_WALLET_DISCRIMINATOR, _data, 0);
    wallet.write(_data, i);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record UpdateWhitelistedWalletIxData(Discriminator discriminator, PublicKey wallet) implements Borsh {  

    public static UpdateWhitelistedWalletIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static UpdateWhitelistedWalletIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var wallet = readPubKey(_data, i);
      return new UpdateWhitelistedWalletIxData(discriminator, wallet);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      wallet.write(_data, i);
      i += 32;
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
                                                     final PublicKey eventAuthorityKey,
                                                     final PublicKey programKey,
                                                     final long rewardIndex) {
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

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(WITHDRAW_INELIGIBLE_REWARD_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, rewardIndex);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record WithdrawIneligibleRewardIxData(Discriminator discriminator, long rewardIndex) implements Borsh {  

    public static WithdrawIneligibleRewardIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static WithdrawIneligibleRewardIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var rewardIndex = getInt64LE(_data, i);
      return new WithdrawIneligibleRewardIxData(discriminator, rewardIndex);
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

  public static final Discriminator SET_LOCK_RELEASE_POINT_DISCRIMINATOR = toDiscriminator(148, 71, 56, 20, 55, 218, 152, 133);

  public static Instruction setLockReleasePoint(final AccountMeta invokedLbClmmProgramMeta,
                                                final PublicKey positionKey,
                                                final PublicKey lbPairKey,
                                                final PublicKey senderKey,
                                                final PublicKey eventAuthorityKey,
                                                final PublicKey programKey,
                                                final long newLockReleasePoint) {
    final var keys = List.of(
      createWrite(positionKey),
      createRead(lbPairKey),
      createReadOnlySigner(senderKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(SET_LOCK_RELEASE_POINT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, newLockReleasePoint);

    return Instruction.createInstruction(invokedLbClmmProgramMeta, keys, _data);
  }

  public record SetLockReleasePointIxData(Discriminator discriminator, long newLockReleasePoint) implements Borsh {  

    public static SetLockReleasePointIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SetLockReleasePointIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var newLockReleasePoint = getInt64LE(_data, i);
      return new SetLockReleasePointIxData(discriminator, newLockReleasePoint);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, newLockReleasePoint);
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
      createWrite(binArrayBitmapExtensionKey),
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
      createWrite(binArrayBitmapExtensionKey),
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
      createRead(binArrayBitmapExtensionKey),
      createRead(fromBinArrayKey),
      createRead(toBinArrayKey),
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

  private LbClmmProgram() {
  }
}
