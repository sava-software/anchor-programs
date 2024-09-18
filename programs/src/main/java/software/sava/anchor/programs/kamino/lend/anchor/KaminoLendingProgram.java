package software.sava.anchor.programs.kamino.lend.anchor;

import java.lang.String;

import java.util.List;

import software.sava.anchor.programs.kamino.lend.anchor.types.AssetTier;
import software.sava.anchor.programs.kamino.lend.anchor.types.FeeCalculation;
import software.sava.anchor.programs.kamino.lend.anchor.types.InitObligationArgs;
import software.sava.anchor.programs.kamino.lend.anchor.types.ReserveFarmKind;
import software.sava.anchor.programs.kamino.lend.anchor.types.ReserveStatus;
import software.sava.anchor.programs.kamino.lend.anchor.types.UpdateConfigMode;
import software.sava.anchor.programs.kamino.lend.anchor.types.UpdateLendingMarketConfigValue;
import software.sava.anchor.programs.kamino.lend.anchor.types.UpdateLendingMarketMode;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static java.nio.charset.StandardCharsets.UTF_8;

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

public final class KaminoLendingProgram {

  public static final Discriminator INIT_LENDING_MARKET_DISCRIMINATOR = toDiscriminator(34, 162, 116, 14, 101, 137, 94, 239);

  public static Instruction initLendingMarket(final AccountMeta invokedKaminoLendingProgramMeta,
                                              final PublicKey lendingMarketOwnerKey,
                                              final PublicKey lendingMarketKey,
                                              final PublicKey lendingMarketAuthorityKey,
                                              final PublicKey systemProgramKey,
                                              final PublicKey rentKey,
                                              final byte[] quoteCurrency) {
    final var keys = List.of(
      createWritableSigner(lendingMarketOwnerKey),
      createWrite(lendingMarketKey),
      createRead(lendingMarketAuthorityKey),
      createRead(systemProgramKey),
      createRead(rentKey)
    );

    final byte[] _data = new byte[8 + Borsh.lenArray(quoteCurrency)];
    int i = writeDiscriminator(INIT_LENDING_MARKET_DISCRIMINATOR, _data, 0);
    Borsh.writeArray(quoteCurrency, _data, i);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record InitLendingMarketIxData(Discriminator discriminator, byte[] quoteCurrency) implements Borsh {  

    public static InitLendingMarketIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static InitLendingMarketIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var quoteCurrency = new byte[32];
      Borsh.readArray(quoteCurrency, _data, i);
      return new InitLendingMarketIxData(discriminator, quoteCurrency);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeArray(quoteCurrency, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_LENDING_MARKET_DISCRIMINATOR = toDiscriminator(209, 157, 53, 210, 97, 180, 31, 45);

  public static Instruction updateLendingMarket(final AccountMeta invokedKaminoLendingProgramMeta,
                                                final PublicKey lendingMarketOwnerKey,
                                                final PublicKey lendingMarketKey,
                                                final long mode,
                                                final byte[] value) {
    final var keys = List.of(
      createReadOnlySigner(lendingMarketOwnerKey),
      createWrite(lendingMarketKey)
    );

    final byte[] _data = new byte[16 + Borsh.lenArray(value)];
    int i = writeDiscriminator(UPDATE_LENDING_MARKET_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, mode);
    i += 8;
    Borsh.writeArray(value, _data, i);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record UpdateLendingMarketIxData(Discriminator discriminator, long mode, byte[] value) implements Borsh {  

    public static UpdateLendingMarketIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 88;

    public static UpdateLendingMarketIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mode = getInt64LE(_data, i);
      i += 8;
      final var value = new byte[72];
      Borsh.readArray(value, _data, i);
      return new UpdateLendingMarketIxData(discriminator, mode, value);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
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

  public static final Discriminator UPDATE_LENDING_MARKET_OWNER_DISCRIMINATOR = toDiscriminator(118, 224, 10, 62, 196, 230, 184, 89);

  public static Instruction updateLendingMarketOwner(final AccountMeta invokedKaminoLendingProgramMeta, final PublicKey lendingMarketOwnerCachedKey, final PublicKey lendingMarketKey) {
    final var keys = List.of(
      createReadOnlySigner(lendingMarketOwnerCachedKey),
      createWrite(lendingMarketKey)
    );

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, UPDATE_LENDING_MARKET_OWNER_DISCRIMINATOR);
  }

  public static final Discriminator INIT_RESERVE_DISCRIMINATOR = toDiscriminator(138, 245, 71, 225, 153, 4, 3, 43);

  public static Instruction initReserve(final AccountMeta invokedKaminoLendingProgramMeta,
                                        final PublicKey lendingMarketOwnerKey,
                                        final PublicKey lendingMarketKey,
                                        final PublicKey lendingMarketAuthorityKey,
                                        final PublicKey reserveKey,
                                        final PublicKey reserveLiquidityMintKey,
                                        final PublicKey reserveLiquiditySupplyKey,
                                        final PublicKey feeReceiverKey,
                                        final PublicKey reserveCollateralMintKey,
                                        final PublicKey reserveCollateralSupplyKey,
                                        final PublicKey rentKey,
                                        final PublicKey liquidityTokenProgramKey,
                                        final PublicKey collateralTokenProgramKey,
                                        final PublicKey systemProgramKey) {
    final var keys = List.of(
      createWritableSigner(lendingMarketOwnerKey),
      createRead(lendingMarketKey),
      createRead(lendingMarketAuthorityKey),
      createWrite(reserveKey),
      createRead(reserveLiquidityMintKey),
      createWrite(reserveLiquiditySupplyKey),
      createWrite(feeReceiverKey),
      createWrite(reserveCollateralMintKey),
      createWrite(reserveCollateralSupplyKey),
      createRead(rentKey),
      createRead(liquidityTokenProgramKey),
      createRead(collateralTokenProgramKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, INIT_RESERVE_DISCRIMINATOR);
  }

  public static final Discriminator INIT_FARMS_FOR_RESERVE_DISCRIMINATOR = toDiscriminator(218, 6, 62, 233, 1, 33, 232, 82);

  public static Instruction initFarmsForReserve(final AccountMeta invokedKaminoLendingProgramMeta,
                                                final PublicKey lendingMarketOwnerKey,
                                                final PublicKey lendingMarketKey,
                                                final PublicKey lendingMarketAuthorityKey,
                                                final PublicKey reserveKey,
                                                final PublicKey farmsProgramKey,
                                                final PublicKey farmsGlobalConfigKey,
                                                final PublicKey farmStateKey,
                                                final PublicKey farmsVaultAuthorityKey,
                                                final PublicKey rentKey,
                                                final PublicKey systemProgramKey,
                                                final int mode) {
    final var keys = List.of(
      createWritableSigner(lendingMarketOwnerKey),
      createRead(lendingMarketKey),
      createWrite(lendingMarketAuthorityKey),
      createWrite(reserveKey),
      createRead(farmsProgramKey),
      createRead(farmsGlobalConfigKey),
      createWrite(farmStateKey),
      createRead(farmsVaultAuthorityKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(INIT_FARMS_FOR_RESERVE_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mode;

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record InitFarmsForReserveIxData(Discriminator discriminator, int mode) implements Borsh {  

    public static InitFarmsForReserveIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static InitFarmsForReserveIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mode = _data[i] & 0xFF;
      return new InitFarmsForReserveIxData(discriminator, mode);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) mode;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_RESERVE_CONFIG_DISCRIMINATOR = toDiscriminator(61, 148, 100, 70, 143, 107, 17, 13);

  public static Instruction updateReserveConfig(final AccountMeta invokedKaminoLendingProgramMeta,
                                                final PublicKey lendingMarketOwnerKey,
                                                final PublicKey lendingMarketKey,
                                                final PublicKey reserveKey,
                                                final long mode,
                                                final byte[] value,
                                                final boolean skipValidation) {
    final var keys = List.of(
      createReadOnlySigner(lendingMarketOwnerKey),
      createRead(lendingMarketKey),
      createWrite(reserveKey)
    );

    final byte[] _data = new byte[21 + Borsh.lenVector(value)];
    int i = writeDiscriminator(UPDATE_RESERVE_CONFIG_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, mode);
    i += 8;
    i += Borsh.writeVector(value, _data, i);
    _data[i] = (byte) (skipValidation ? 1 : 0);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record UpdateReserveConfigIxData(Discriminator discriminator,
                                          long mode,
                                          byte[] value,
                                          boolean skipValidation) implements Borsh {  

    public static UpdateReserveConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateReserveConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mode = getInt64LE(_data, i);
      i += 8;
      final byte[] value = Borsh.readbyteVector(_data, i);
      i += Borsh.lenVector(value);
      final var skipValidation = _data[i] == 1;
      return new UpdateReserveConfigIxData(discriminator, mode, value, skipValidation);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, mode);
      i += 8;
      i += Borsh.writeVector(value, _data, i);
      _data[i] = (byte) (skipValidation ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + Borsh.lenVector(value) + 1;
    }
  }

  public static final Discriminator REDEEM_FEES_DISCRIMINATOR = toDiscriminator(215, 39, 180, 41, 173, 46, 248, 220);

  public static Instruction redeemFees(final AccountMeta invokedKaminoLendingProgramMeta,
                                       final PublicKey reserveKey,
                                       final PublicKey reserveLiquidityMintKey,
                                       final PublicKey reserveLiquidityFeeReceiverKey,
                                       final PublicKey reserveSupplyLiquidityKey,
                                       final PublicKey lendingMarketKey,
                                       final PublicKey lendingMarketAuthorityKey,
                                       final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWrite(reserveKey),
      createWrite(reserveLiquidityMintKey),
      createWrite(reserveLiquidityFeeReceiverKey),
      createWrite(reserveSupplyLiquidityKey),
      createRead(lendingMarketKey),
      createRead(lendingMarketAuthorityKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, REDEEM_FEES_DISCRIMINATOR);
  }

  public static final Discriminator SOCIALIZE_LOSS_DISCRIMINATOR = toDiscriminator(245, 75, 91, 0, 236, 97, 19, 3);

  public static Instruction socializeLoss(final AccountMeta invokedKaminoLendingProgramMeta,
                                          final PublicKey riskCouncilKey,
                                          final PublicKey obligationKey,
                                          final PublicKey lendingMarketKey,
                                          final PublicKey reserveKey,
                                          final PublicKey instructionSysvarAccountKey,
                                          final long liquidityAmount) {
    final var keys = List.of(
      createReadOnlySigner(riskCouncilKey),
      createWrite(obligationKey),
      createRead(lendingMarketKey),
      createWrite(reserveKey),
      createRead(instructionSysvarAccountKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(SOCIALIZE_LOSS_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, liquidityAmount);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record SocializeLossIxData(Discriminator discriminator, long liquidityAmount) implements Borsh {  

    public static SocializeLossIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SocializeLossIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityAmount = getInt64LE(_data, i);
      return new SocializeLossIxData(discriminator, liquidityAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, liquidityAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator WITHDRAW_PROTOCOL_FEE_DISCRIMINATOR = toDiscriminator(158, 201, 158, 189, 33, 93, 162, 103);

  public static Instruction withdrawProtocolFee(final AccountMeta invokedKaminoLendingProgramMeta,
                                                final PublicKey lendingMarketOwnerKey,
                                                final PublicKey lendingMarketKey,
                                                final PublicKey reserveKey,
                                                final PublicKey reserveLiquidityMintKey,
                                                final PublicKey lendingMarketAuthorityKey,
                                                final PublicKey feeVaultKey,
                                                final PublicKey lendingMarketOwnerAtaKey,
                                                final PublicKey tokenProgramKey,
                                                final long amount) {
    final var keys = List.of(
      createReadOnlySigner(lendingMarketOwnerKey),
      createRead(lendingMarketKey),
      createRead(reserveKey),
      createWrite(reserveLiquidityMintKey),
      createRead(lendingMarketAuthorityKey),
      createWrite(feeVaultKey),
      createWrite(lendingMarketOwnerAtaKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(WITHDRAW_PROTOCOL_FEE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record WithdrawProtocolFeeIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static WithdrawProtocolFeeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static WithdrawProtocolFeeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new WithdrawProtocolFeeIxData(discriminator, amount);
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

  public static final Discriminator REFRESH_RESERVE_DISCRIMINATOR = toDiscriminator(2, 218, 138, 235, 79, 201, 25, 102);

  public static Instruction refreshReserve(final AccountMeta invokedKaminoLendingProgramMeta,
                                           final PublicKey reserveKey,
                                           final PublicKey lendingMarketKey,
                                           final PublicKey pythOracleKey,
                                           final PublicKey switchboardPriceOracleKey,
                                           final PublicKey switchboardTwapOracleKey,
                                           final PublicKey scopePricesKey) {
    final var keys = List.of(
      createWrite(reserveKey),
      createRead(lendingMarketKey),
      createRead(pythOracleKey),
      createRead(switchboardPriceOracleKey),
      createRead(switchboardTwapOracleKey),
      createRead(scopePricesKey)
    );

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, REFRESH_RESERVE_DISCRIMINATOR);
  }

  public static final Discriminator REFRESH_RESERVES_BATCH_DISCRIMINATOR = toDiscriminator(144, 110, 26, 103, 162, 204, 252, 147);

  public static Instruction refreshReservesBatch(final AccountMeta invokedKaminoLendingProgramMeta, final boolean skipPriceUpdates) {
    final var keys = AccountMeta.NO_KEYS;

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(REFRESH_RESERVES_BATCH_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) (skipPriceUpdates ? 1 : 0);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record RefreshReservesBatchIxData(Discriminator discriminator, boolean skipPriceUpdates) implements Borsh {  

    public static RefreshReservesBatchIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static RefreshReservesBatchIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var skipPriceUpdates = _data[i] == 1;
      return new RefreshReservesBatchIxData(discriminator, skipPriceUpdates);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) (skipPriceUpdates ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DEPOSIT_RESERVE_LIQUIDITY_DISCRIMINATOR = toDiscriminator(169, 201, 30, 126, 6, 205, 102, 68);

  public static Instruction depositReserveLiquidity(final AccountMeta invokedKaminoLendingProgramMeta,
                                                    final PublicKey ownerKey,
                                                    final PublicKey reserveKey,
                                                    final PublicKey lendingMarketKey,
                                                    final PublicKey lendingMarketAuthorityKey,
                                                    final PublicKey reserveLiquidityMintKey,
                                                    final PublicKey reserveLiquiditySupplyKey,
                                                    final PublicKey reserveCollateralMintKey,
                                                    final PublicKey userSourceLiquidityKey,
                                                    final PublicKey userDestinationCollateralKey,
                                                    final PublicKey collateralTokenProgramKey,
                                                    final PublicKey liquidityTokenProgramKey,
                                                    final PublicKey instructionSysvarAccountKey,
                                                    final long liquidityAmount) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWrite(reserveKey),
      createRead(lendingMarketKey),
      createRead(lendingMarketAuthorityKey),
      createWrite(reserveLiquidityMintKey),
      createWrite(reserveLiquiditySupplyKey),
      createWrite(reserveCollateralMintKey),
      createWrite(userSourceLiquidityKey),
      createWrite(userDestinationCollateralKey),
      createRead(collateralTokenProgramKey),
      createRead(liquidityTokenProgramKey),
      createRead(instructionSysvarAccountKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(DEPOSIT_RESERVE_LIQUIDITY_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, liquidityAmount);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record DepositReserveLiquidityIxData(Discriminator discriminator, long liquidityAmount) implements Borsh {  

    public static DepositReserveLiquidityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static DepositReserveLiquidityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityAmount = getInt64LE(_data, i);
      return new DepositReserveLiquidityIxData(discriminator, liquidityAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, liquidityAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REDEEM_RESERVE_COLLATERAL_DISCRIMINATOR = toDiscriminator(234, 117, 181, 125, 185, 142, 220, 29);

  public static Instruction redeemReserveCollateral(final AccountMeta invokedKaminoLendingProgramMeta,
                                                    final PublicKey ownerKey,
                                                    final PublicKey lendingMarketKey,
                                                    final PublicKey reserveKey,
                                                    final PublicKey lendingMarketAuthorityKey,
                                                    final PublicKey reserveLiquidityMintKey,
                                                    final PublicKey reserveCollateralMintKey,
                                                    final PublicKey reserveLiquiditySupplyKey,
                                                    final PublicKey userSourceCollateralKey,
                                                    final PublicKey userDestinationLiquidityKey,
                                                    final PublicKey collateralTokenProgramKey,
                                                    final PublicKey liquidityTokenProgramKey,
                                                    final PublicKey instructionSysvarAccountKey,
                                                    final long collateralAmount) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createRead(lendingMarketKey),
      createWrite(reserveKey),
      createRead(lendingMarketAuthorityKey),
      createWrite(reserveLiquidityMintKey),
      createWrite(reserveCollateralMintKey),
      createWrite(reserveLiquiditySupplyKey),
      createWrite(userSourceCollateralKey),
      createWrite(userDestinationLiquidityKey),
      createRead(collateralTokenProgramKey),
      createRead(liquidityTokenProgramKey),
      createRead(instructionSysvarAccountKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(REDEEM_RESERVE_COLLATERAL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, collateralAmount);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record RedeemReserveCollateralIxData(Discriminator discriminator, long collateralAmount) implements Borsh {  

    public static RedeemReserveCollateralIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static RedeemReserveCollateralIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var collateralAmount = getInt64LE(_data, i);
      return new RedeemReserveCollateralIxData(discriminator, collateralAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, collateralAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INIT_OBLIGATION_DISCRIMINATOR = toDiscriminator(251, 10, 231, 76, 27, 11, 159, 96);

  public static Instruction initObligation(final AccountMeta invokedKaminoLendingProgramMeta,
                                           final PublicKey obligationOwnerKey,
                                           final PublicKey feePayerKey,
                                           final PublicKey obligationKey,
                                           final PublicKey lendingMarketKey,
                                           final PublicKey seed1AccountKey,
                                           final PublicKey seed2AccountKey,
                                           final PublicKey ownerUserMetadataKey,
                                           final PublicKey rentKey,
                                           final PublicKey systemProgramKey,
                                           final InitObligationArgs args) {
    final var keys = List.of(
      createReadOnlySigner(obligationOwnerKey),
      createWritableSigner(feePayerKey),
      createWrite(obligationKey),
      createRead(lendingMarketKey),
      createRead(seed1AccountKey),
      createRead(seed2AccountKey),
      createRead(ownerUserMetadataKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(args)];
    int i = writeDiscriminator(INIT_OBLIGATION_DISCRIMINATOR, _data, 0);
    Borsh.write(args, _data, i);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record InitObligationIxData(Discriminator discriminator, InitObligationArgs args) implements Borsh {  

    public static InitObligationIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static InitObligationIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var args = InitObligationArgs.read(_data, i);
      return new InitObligationIxData(discriminator, args);
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

  public static final Discriminator INIT_OBLIGATION_FARMS_FOR_RESERVE_DISCRIMINATOR = toDiscriminator(136, 63, 15, 186, 211, 152, 168, 164);

  public static Instruction initObligationFarmsForReserve(final AccountMeta invokedKaminoLendingProgramMeta,
                                                          final PublicKey payerKey,
                                                          final PublicKey ownerKey,
                                                          final PublicKey obligationKey,
                                                          final PublicKey lendingMarketAuthorityKey,
                                                          final PublicKey reserveKey,
                                                          final PublicKey reserveFarmStateKey,
                                                          final PublicKey obligationFarmKey,
                                                          final PublicKey lendingMarketKey,
                                                          final PublicKey farmsProgramKey,
                                                          final PublicKey rentKey,
                                                          final PublicKey systemProgramKey,
                                                          final int mode) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createRead(ownerKey),
      createWrite(obligationKey),
      createWrite(lendingMarketAuthorityKey),
      createWrite(reserveKey),
      createWrite(reserveFarmStateKey),
      createWrite(obligationFarmKey),
      createRead(lendingMarketKey),
      createRead(farmsProgramKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(INIT_OBLIGATION_FARMS_FOR_RESERVE_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mode;

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record InitObligationFarmsForReserveIxData(Discriminator discriminator, int mode) implements Borsh {  

    public static InitObligationFarmsForReserveIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static InitObligationFarmsForReserveIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mode = _data[i] & 0xFF;
      return new InitObligationFarmsForReserveIxData(discriminator, mode);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) mode;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REFRESH_OBLIGATION_FARMS_FOR_RESERVE_DISCRIMINATOR = toDiscriminator(140, 144, 253, 21, 10, 74, 248, 3);

  public static Instruction refreshObligationFarmsForReserve(final AccountMeta invokedKaminoLendingProgramMeta,
                                                             final PublicKey crankKey,
                                                             final PublicKey obligationKey,
                                                             final PublicKey lendingMarketAuthorityKey,
                                                             final PublicKey reserveKey,
                                                             final PublicKey reserveFarmStateKey,
                                                             final PublicKey obligationFarmUserStateKey,
                                                             final PublicKey lendingMarketKey,
                                                             final PublicKey farmsProgramKey,
                                                             final PublicKey rentKey,
                                                             final PublicKey systemProgramKey,
                                                             final int mode) {
    final var keys = List.of(
      createWritableSigner(crankKey),
      createRead(obligationKey),
      createWrite(lendingMarketAuthorityKey),
      createRead(reserveKey),
      createWrite(reserveFarmStateKey),
      createWrite(obligationFarmUserStateKey),
      createRead(lendingMarketKey),
      createRead(farmsProgramKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(REFRESH_OBLIGATION_FARMS_FOR_RESERVE_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mode;

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record RefreshObligationFarmsForReserveIxData(Discriminator discriminator, int mode) implements Borsh {  

    public static RefreshObligationFarmsForReserveIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static RefreshObligationFarmsForReserveIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mode = _data[i] & 0xFF;
      return new RefreshObligationFarmsForReserveIxData(discriminator, mode);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) mode;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REFRESH_OBLIGATION_DISCRIMINATOR = toDiscriminator(33, 132, 147, 228, 151, 192, 72, 89);

  public static Instruction refreshObligation(final AccountMeta invokedKaminoLendingProgramMeta, final PublicKey lendingMarketKey, final PublicKey obligationKey) {
    final var keys = List.of(
      createRead(lendingMarketKey),
      createWrite(obligationKey)
    );

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, REFRESH_OBLIGATION_DISCRIMINATOR);
  }

  public static final Discriminator DEPOSIT_OBLIGATION_COLLATERAL_DISCRIMINATOR = toDiscriminator(108, 209, 4, 72, 21, 22, 118, 133);

  public static Instruction depositObligationCollateral(final AccountMeta invokedKaminoLendingProgramMeta,
                                                        final PublicKey ownerKey,
                                                        final PublicKey obligationKey,
                                                        final PublicKey lendingMarketKey,
                                                        final PublicKey depositReserveKey,
                                                        final PublicKey reserveDestinationCollateralKey,
                                                        final PublicKey userSourceCollateralKey,
                                                        final PublicKey tokenProgramKey,
                                                        final PublicKey instructionSysvarAccountKey,
                                                        final long collateralAmount) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWrite(obligationKey),
      createRead(lendingMarketKey),
      createWrite(depositReserveKey),
      createWrite(reserveDestinationCollateralKey),
      createWrite(userSourceCollateralKey),
      createRead(tokenProgramKey),
      createRead(instructionSysvarAccountKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(DEPOSIT_OBLIGATION_COLLATERAL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, collateralAmount);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record DepositObligationCollateralIxData(Discriminator discriminator, long collateralAmount) implements Borsh {  

    public static DepositObligationCollateralIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static DepositObligationCollateralIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var collateralAmount = getInt64LE(_data, i);
      return new DepositObligationCollateralIxData(discriminator, collateralAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, collateralAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator WITHDRAW_OBLIGATION_COLLATERAL_DISCRIMINATOR = toDiscriminator(37, 116, 205, 103, 243, 192, 92, 198);

  public static Instruction withdrawObligationCollateral(final AccountMeta invokedKaminoLendingProgramMeta,
                                                         final PublicKey ownerKey,
                                                         final PublicKey obligationKey,
                                                         final PublicKey lendingMarketKey,
                                                         final PublicKey lendingMarketAuthorityKey,
                                                         final PublicKey withdrawReserveKey,
                                                         final PublicKey reserveSourceCollateralKey,
                                                         final PublicKey userDestinationCollateralKey,
                                                         final PublicKey tokenProgramKey,
                                                         final PublicKey instructionSysvarAccountKey,
                                                         final long collateralAmount) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWrite(obligationKey),
      createRead(lendingMarketKey),
      createRead(lendingMarketAuthorityKey),
      createWrite(withdrawReserveKey),
      createWrite(reserveSourceCollateralKey),
      createWrite(userDestinationCollateralKey),
      createRead(tokenProgramKey),
      createRead(instructionSysvarAccountKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(WITHDRAW_OBLIGATION_COLLATERAL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, collateralAmount);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record WithdrawObligationCollateralIxData(Discriminator discriminator, long collateralAmount) implements Borsh {  

    public static WithdrawObligationCollateralIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static WithdrawObligationCollateralIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var collateralAmount = getInt64LE(_data, i);
      return new WithdrawObligationCollateralIxData(discriminator, collateralAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, collateralAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator BORROW_OBLIGATION_LIQUIDITY_DISCRIMINATOR = toDiscriminator(121, 127, 18, 204, 73, 245, 225, 65);

  public static Instruction borrowObligationLiquidity(final AccountMeta invokedKaminoLendingProgramMeta,
                                                      final PublicKey ownerKey,
                                                      final PublicKey obligationKey,
                                                      final PublicKey lendingMarketKey,
                                                      final PublicKey lendingMarketAuthorityKey,
                                                      final PublicKey borrowReserveKey,
                                                      final PublicKey borrowReserveLiquidityMintKey,
                                                      final PublicKey reserveSourceLiquidityKey,
                                                      final PublicKey borrowReserveLiquidityFeeReceiverKey,
                                                      final PublicKey userDestinationLiquidityKey,
                                                      final PublicKey referrerTokenStateKey,
                                                      final PublicKey tokenProgramKey,
                                                      final PublicKey instructionSysvarAccountKey,
                                                      final long liquidityAmount) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWrite(obligationKey),
      createRead(lendingMarketKey),
      createRead(lendingMarketAuthorityKey),
      createWrite(borrowReserveKey),
      createWrite(borrowReserveLiquidityMintKey),
      createWrite(reserveSourceLiquidityKey),
      createWrite(borrowReserveLiquidityFeeReceiverKey),
      createWrite(userDestinationLiquidityKey),
      createWrite(referrerTokenStateKey),
      createRead(tokenProgramKey),
      createRead(instructionSysvarAccountKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(BORROW_OBLIGATION_LIQUIDITY_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, liquidityAmount);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record BorrowObligationLiquidityIxData(Discriminator discriminator, long liquidityAmount) implements Borsh {  

    public static BorrowObligationLiquidityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static BorrowObligationLiquidityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityAmount = getInt64LE(_data, i);
      return new BorrowObligationLiquidityIxData(discriminator, liquidityAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, liquidityAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REPAY_OBLIGATION_LIQUIDITY_DISCRIMINATOR = toDiscriminator(145, 178, 13, 225, 76, 240, 147, 72);

  public static Instruction repayObligationLiquidity(final AccountMeta invokedKaminoLendingProgramMeta,
                                                     final PublicKey ownerKey,
                                                     final PublicKey obligationKey,
                                                     final PublicKey lendingMarketKey,
                                                     final PublicKey repayReserveKey,
                                                     final PublicKey reserveLiquidityMintKey,
                                                     final PublicKey reserveDestinationLiquidityKey,
                                                     final PublicKey userSourceLiquidityKey,
                                                     final PublicKey tokenProgramKey,
                                                     final PublicKey instructionSysvarAccountKey,
                                                     final long liquidityAmount) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWrite(obligationKey),
      createRead(lendingMarketKey),
      createWrite(repayReserveKey),
      createWrite(reserveLiquidityMintKey),
      createWrite(reserveDestinationLiquidityKey),
      createWrite(userSourceLiquidityKey),
      createRead(tokenProgramKey),
      createRead(instructionSysvarAccountKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(REPAY_OBLIGATION_LIQUIDITY_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, liquidityAmount);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record RepayObligationLiquidityIxData(Discriminator discriminator, long liquidityAmount) implements Borsh {  

    public static RepayObligationLiquidityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static RepayObligationLiquidityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityAmount = getInt64LE(_data, i);
      return new RepayObligationLiquidityIxData(discriminator, liquidityAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, liquidityAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DEPOSIT_RESERVE_LIQUIDITY_AND_OBLIGATION_COLLATERAL_DISCRIMINATOR = toDiscriminator(129, 199, 4, 2, 222, 39, 26, 46);

  public static Instruction depositReserveLiquidityAndObligationCollateral(final AccountMeta invokedKaminoLendingProgramMeta,
                                                                           final PublicKey ownerKey,
                                                                           final PublicKey obligationKey,
                                                                           final PublicKey lendingMarketKey,
                                                                           final PublicKey lendingMarketAuthorityKey,
                                                                           final PublicKey reserveKey,
                                                                           final PublicKey reserveLiquidityMintKey,
                                                                           final PublicKey reserveLiquiditySupplyKey,
                                                                           final PublicKey reserveCollateralMintKey,
                                                                           final PublicKey reserveDestinationDepositCollateralKey,
                                                                           final PublicKey userSourceLiquidityKey,
                                                                           final PublicKey placeholderUserDestinationCollateralKey,
                                                                           final PublicKey collateralTokenProgramKey,
                                                                           final PublicKey liquidityTokenProgramKey,
                                                                           final PublicKey instructionSysvarAccountKey,
                                                                           final long liquidityAmount) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWrite(obligationKey),
      createRead(lendingMarketKey),
      createRead(lendingMarketAuthorityKey),
      createWrite(reserveKey),
      createWrite(reserveLiquidityMintKey),
      createWrite(reserveLiquiditySupplyKey),
      createWrite(reserveCollateralMintKey),
      createWrite(reserveDestinationDepositCollateralKey),
      createWrite(userSourceLiquidityKey),
      createRead(placeholderUserDestinationCollateralKey),
      createRead(collateralTokenProgramKey),
      createRead(liquidityTokenProgramKey),
      createRead(instructionSysvarAccountKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(DEPOSIT_RESERVE_LIQUIDITY_AND_OBLIGATION_COLLATERAL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, liquidityAmount);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record DepositReserveLiquidityAndObligationCollateralIxData(Discriminator discriminator, long liquidityAmount) implements Borsh {  

    public static DepositReserveLiquidityAndObligationCollateralIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static DepositReserveLiquidityAndObligationCollateralIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityAmount = getInt64LE(_data, i);
      return new DepositReserveLiquidityAndObligationCollateralIxData(discriminator, liquidityAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, liquidityAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator WITHDRAW_OBLIGATION_COLLATERAL_AND_REDEEM_RESERVE_COLLATERAL_DISCRIMINATOR = toDiscriminator(75, 93, 93, 220, 34, 150, 218, 196);

  public static Instruction withdrawObligationCollateralAndRedeemReserveCollateral(final AccountMeta invokedKaminoLendingProgramMeta,
                                                                                   final PublicKey ownerKey,
                                                                                   final PublicKey obligationKey,
                                                                                   final PublicKey lendingMarketKey,
                                                                                   final PublicKey lendingMarketAuthorityKey,
                                                                                   final PublicKey withdrawReserveKey,
                                                                                   final PublicKey reserveLiquidityMintKey,
                                                                                   final PublicKey reserveSourceCollateralKey,
                                                                                   final PublicKey reserveCollateralMintKey,
                                                                                   final PublicKey reserveLiquiditySupplyKey,
                                                                                   final PublicKey userDestinationLiquidityKey,
                                                                                   final PublicKey placeholderUserDestinationCollateralKey,
                                                                                   final PublicKey collateralTokenProgramKey,
                                                                                   final PublicKey liquidityTokenProgramKey,
                                                                                   final PublicKey instructionSysvarAccountKey,
                                                                                   final long collateralAmount) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWrite(obligationKey),
      createRead(lendingMarketKey),
      createRead(lendingMarketAuthorityKey),
      createWrite(withdrawReserveKey),
      createWrite(reserveLiquidityMintKey),
      createWrite(reserveSourceCollateralKey),
      createWrite(reserveCollateralMintKey),
      createWrite(reserveLiquiditySupplyKey),
      createWrite(userDestinationLiquidityKey),
      createRead(placeholderUserDestinationCollateralKey),
      createRead(collateralTokenProgramKey),
      createRead(liquidityTokenProgramKey),
      createRead(instructionSysvarAccountKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(WITHDRAW_OBLIGATION_COLLATERAL_AND_REDEEM_RESERVE_COLLATERAL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, collateralAmount);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record WithdrawObligationCollateralAndRedeemReserveCollateralIxData(Discriminator discriminator, long collateralAmount) implements Borsh {  

    public static WithdrawObligationCollateralAndRedeemReserveCollateralIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static WithdrawObligationCollateralAndRedeemReserveCollateralIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var collateralAmount = getInt64LE(_data, i);
      return new WithdrawObligationCollateralAndRedeemReserveCollateralIxData(discriminator, collateralAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, collateralAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator LIQUIDATE_OBLIGATION_AND_REDEEM_RESERVE_COLLATERAL_DISCRIMINATOR = toDiscriminator(177, 71, 154, 188, 226, 133, 74, 55);

  public static Instruction liquidateObligationAndRedeemReserveCollateral(final AccountMeta invokedKaminoLendingProgramMeta,
                                                                          final PublicKey liquidatorKey,
                                                                          final PublicKey obligationKey,
                                                                          final PublicKey lendingMarketKey,
                                                                          final PublicKey lendingMarketAuthorityKey,
                                                                          final PublicKey repayReserveKey,
                                                                          final PublicKey repayReserveLiquidityMintKey,
                                                                          final PublicKey repayReserveLiquiditySupplyKey,
                                                                          final PublicKey withdrawReserveKey,
                                                                          final PublicKey withdrawReserveLiquidityMintKey,
                                                                          final PublicKey withdrawReserveCollateralMintKey,
                                                                          final PublicKey withdrawReserveCollateralSupplyKey,
                                                                          final PublicKey withdrawReserveLiquiditySupplyKey,
                                                                          final PublicKey withdrawReserveLiquidityFeeReceiverKey,
                                                                          final PublicKey userSourceLiquidityKey,
                                                                          final PublicKey userDestinationCollateralKey,
                                                                          final PublicKey userDestinationLiquidityKey,
                                                                          final PublicKey collateralTokenProgramKey,
                                                                          final PublicKey repayLiquidityTokenProgramKey,
                                                                          final PublicKey withdrawLiquidityTokenProgramKey,
                                                                          final PublicKey instructionSysvarAccountKey,
                                                                          final long liquidityAmount,
                                                                          final long minAcceptableReceivedLiquidityAmount,
                                                                          final long maxAllowedLtvOverridePercent) {
    final var keys = List.of(
      createReadOnlySigner(liquidatorKey),
      createWrite(obligationKey),
      createRead(lendingMarketKey),
      createRead(lendingMarketAuthorityKey),
      createWrite(repayReserveKey),
      createWrite(repayReserveLiquidityMintKey),
      createWrite(repayReserveLiquiditySupplyKey),
      createWrite(withdrawReserveKey),
      createWrite(withdrawReserveLiquidityMintKey),
      createWrite(withdrawReserveCollateralMintKey),
      createWrite(withdrawReserveCollateralSupplyKey),
      createWrite(withdrawReserveLiquiditySupplyKey),
      createWrite(withdrawReserveLiquidityFeeReceiverKey),
      createWrite(userSourceLiquidityKey),
      createWrite(userDestinationCollateralKey),
      createWrite(userDestinationLiquidityKey),
      createRead(collateralTokenProgramKey),
      createRead(repayLiquidityTokenProgramKey),
      createRead(withdrawLiquidityTokenProgramKey),
      createRead(instructionSysvarAccountKey)
    );

    final byte[] _data = new byte[32];
    int i = writeDiscriminator(LIQUIDATE_OBLIGATION_AND_REDEEM_RESERVE_COLLATERAL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, liquidityAmount);
    i += 8;
    putInt64LE(_data, i, minAcceptableReceivedLiquidityAmount);
    i += 8;
    putInt64LE(_data, i, maxAllowedLtvOverridePercent);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record LiquidateObligationAndRedeemReserveCollateralIxData(Discriminator discriminator,
                                                                    long liquidityAmount,
                                                                    long minAcceptableReceivedLiquidityAmount,
                                                                    long maxAllowedLtvOverridePercent) implements Borsh {  

    public static LiquidateObligationAndRedeemReserveCollateralIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 32;

    public static LiquidateObligationAndRedeemReserveCollateralIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityAmount = getInt64LE(_data, i);
      i += 8;
      final var minAcceptableReceivedLiquidityAmount = getInt64LE(_data, i);
      i += 8;
      final var maxAllowedLtvOverridePercent = getInt64LE(_data, i);
      return new LiquidateObligationAndRedeemReserveCollateralIxData(discriminator, liquidityAmount, minAcceptableReceivedLiquidityAmount, maxAllowedLtvOverridePercent);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, liquidityAmount);
      i += 8;
      putInt64LE(_data, i, minAcceptableReceivedLiquidityAmount);
      i += 8;
      putInt64LE(_data, i, maxAllowedLtvOverridePercent);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator FLASH_REPAY_RESERVE_LIQUIDITY_DISCRIMINATOR = toDiscriminator(185, 117, 0, 203, 96, 245, 180, 186);

  public static Instruction flashRepayReserveLiquidity(final AccountMeta invokedKaminoLendingProgramMeta,
                                                       final PublicKey userTransferAuthorityKey,
                                                       final PublicKey lendingMarketAuthorityKey,
                                                       final PublicKey lendingMarketKey,
                                                       final PublicKey reserveKey,
                                                       final PublicKey reserveLiquidityMintKey,
                                                       final PublicKey reserveDestinationLiquidityKey,
                                                       final PublicKey userSourceLiquidityKey,
                                                       final PublicKey reserveLiquidityFeeReceiverKey,
                                                       final PublicKey referrerTokenStateKey,
                                                       final PublicKey referrerAccountKey,
                                                       final PublicKey sysvarInfoKey,
                                                       final PublicKey tokenProgramKey,
                                                       final long liquidityAmount,
                                                       final int borrowInstructionIndex) {
    final var keys = List.of(
      createReadOnlySigner(userTransferAuthorityKey),
      createRead(lendingMarketAuthorityKey),
      createRead(lendingMarketKey),
      createWrite(reserveKey),
      createRead(reserveLiquidityMintKey),
      createWrite(reserveDestinationLiquidityKey),
      createWrite(userSourceLiquidityKey),
      createWrite(reserveLiquidityFeeReceiverKey),
      createWrite(referrerTokenStateKey),
      createWrite(referrerAccountKey),
      createRead(sysvarInfoKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[17];
    int i = writeDiscriminator(FLASH_REPAY_RESERVE_LIQUIDITY_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, liquidityAmount);
    i += 8;
    _data[i] = (byte) borrowInstructionIndex;

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record FlashRepayReserveLiquidityIxData(Discriminator discriminator, long liquidityAmount, int borrowInstructionIndex) implements Borsh {  

    public static FlashRepayReserveLiquidityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static FlashRepayReserveLiquidityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityAmount = getInt64LE(_data, i);
      i += 8;
      final var borrowInstructionIndex = _data[i] & 0xFF;
      return new FlashRepayReserveLiquidityIxData(discriminator, liquidityAmount, borrowInstructionIndex);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, liquidityAmount);
      i += 8;
      _data[i] = (byte) borrowInstructionIndex;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator FLASH_BORROW_RESERVE_LIQUIDITY_DISCRIMINATOR = toDiscriminator(135, 231, 52, 167, 7, 52, 212, 193);

  public static Instruction flashBorrowReserveLiquidity(final AccountMeta invokedKaminoLendingProgramMeta,
                                                        final PublicKey userTransferAuthorityKey,
                                                        final PublicKey lendingMarketAuthorityKey,
                                                        final PublicKey lendingMarketKey,
                                                        final PublicKey reserveKey,
                                                        final PublicKey reserveLiquidityMintKey,
                                                        final PublicKey reserveSourceLiquidityKey,
                                                        final PublicKey userDestinationLiquidityKey,
                                                        final PublicKey reserveLiquidityFeeReceiverKey,
                                                        final PublicKey referrerTokenStateKey,
                                                        final PublicKey referrerAccountKey,
                                                        final PublicKey sysvarInfoKey,
                                                        final PublicKey tokenProgramKey,
                                                        final long liquidityAmount) {
    final var keys = List.of(
      createReadOnlySigner(userTransferAuthorityKey),
      createRead(lendingMarketAuthorityKey),
      createRead(lendingMarketKey),
      createWrite(reserveKey),
      createRead(reserveLiquidityMintKey),
      createWrite(reserveSourceLiquidityKey),
      createWrite(userDestinationLiquidityKey),
      createWrite(reserveLiquidityFeeReceiverKey),
      createWrite(referrerTokenStateKey),
      createWrite(referrerAccountKey),
      createRead(sysvarInfoKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(FLASH_BORROW_RESERVE_LIQUIDITY_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, liquidityAmount);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record FlashBorrowReserveLiquidityIxData(Discriminator discriminator, long liquidityAmount) implements Borsh {  

    public static FlashBorrowReserveLiquidityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static FlashBorrowReserveLiquidityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityAmount = getInt64LE(_data, i);
      return new FlashBorrowReserveLiquidityIxData(discriminator, liquidityAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, liquidityAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REQUEST_ELEVATION_GROUP_DISCRIMINATOR = toDiscriminator(36, 119, 251, 129, 34, 240, 7, 147);

  public static Instruction requestElevationGroup(final AccountMeta invokedKaminoLendingProgramMeta,
                                                  final PublicKey ownerKey,
                                                  final PublicKey obligationKey,
                                                  final PublicKey lendingMarketKey,
                                                  final int elevationGroup) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWrite(obligationKey),
      createRead(lendingMarketKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(REQUEST_ELEVATION_GROUP_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) elevationGroup;

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record RequestElevationGroupIxData(Discriminator discriminator, int elevationGroup) implements Borsh {  

    public static RequestElevationGroupIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static RequestElevationGroupIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var elevationGroup = _data[i] & 0xFF;
      return new RequestElevationGroupIxData(discriminator, elevationGroup);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) elevationGroup;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INIT_REFERRER_TOKEN_STATE_DISCRIMINATOR = toDiscriminator(116, 45, 66, 148, 58, 13, 218, 115);

  public static Instruction initReferrerTokenState(final AccountMeta invokedKaminoLendingProgramMeta,
                                                   final PublicKey payerKey,
                                                   final PublicKey lendingMarketKey,
                                                   final PublicKey reserveKey,
                                                   final PublicKey referrerTokenStateKey,
                                                   final PublicKey rentKey,
                                                   final PublicKey systemProgramKey,
                                                   final PublicKey referrer) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createRead(lendingMarketKey),
      createRead(reserveKey),
      createWrite(referrerTokenStateKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(INIT_REFERRER_TOKEN_STATE_DISCRIMINATOR, _data, 0);
    referrer.write(_data, i);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record InitReferrerTokenStateIxData(Discriminator discriminator, PublicKey referrer) implements Borsh {  

    public static InitReferrerTokenStateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static InitReferrerTokenStateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var referrer = readPubKey(_data, i);
      return new InitReferrerTokenStateIxData(discriminator, referrer);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      referrer.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INIT_USER_METADATA_DISCRIMINATOR = toDiscriminator(117, 169, 176, 69, 197, 23, 15, 162);

  public static Instruction initUserMetadata(final AccountMeta invokedKaminoLendingProgramMeta,
                                             final PublicKey ownerKey,
                                             final PublicKey feePayerKey,
                                             final PublicKey userMetadataKey,
                                             final PublicKey referrerUserMetadataKey,
                                             final PublicKey rentKey,
                                             final PublicKey systemProgramKey,
                                             final PublicKey userLookupTable) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWritableSigner(feePayerKey),
      createWrite(userMetadataKey),
      createRead(referrerUserMetadataKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(INIT_USER_METADATA_DISCRIMINATOR, _data, 0);
    userLookupTable.write(_data, i);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record InitUserMetadataIxData(Discriminator discriminator, PublicKey userLookupTable) implements Borsh {  

    public static InitUserMetadataIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static InitUserMetadataIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var userLookupTable = readPubKey(_data, i);
      return new InitUserMetadataIxData(discriminator, userLookupTable);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      userLookupTable.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator WITHDRAW_REFERRER_FEES_DISCRIMINATOR = toDiscriminator(171, 118, 121, 201, 233, 140, 23, 228);

  public static Instruction withdrawReferrerFees(final AccountMeta invokedKaminoLendingProgramMeta,
                                                 final PublicKey referrerKey,
                                                 final PublicKey referrerTokenStateKey,
                                                 final PublicKey reserveKey,
                                                 final PublicKey reserveLiquidityMintKey,
                                                 final PublicKey reserveSupplyLiquidityKey,
                                                 final PublicKey referrerTokenAccountKey,
                                                 final PublicKey lendingMarketKey,
                                                 final PublicKey lendingMarketAuthorityKey,
                                                 final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWritableSigner(referrerKey),
      createWrite(referrerTokenStateKey),
      createWrite(reserveKey),
      createWrite(reserveLiquidityMintKey),
      createWrite(reserveSupplyLiquidityKey),
      createWrite(referrerTokenAccountKey),
      createRead(lendingMarketKey),
      createRead(lendingMarketAuthorityKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, WITHDRAW_REFERRER_FEES_DISCRIMINATOR);
  }

  public static final Discriminator INIT_REFERRER_STATE_AND_SHORT_URL_DISCRIMINATOR = toDiscriminator(165, 19, 25, 127, 100, 55, 31, 90);

  public static Instruction initReferrerStateAndShortUrl(final AccountMeta invokedKaminoLendingProgramMeta,
                                                         final PublicKey referrerKey,
                                                         final PublicKey referrerStateKey,
                                                         final PublicKey referrerShortUrlKey,
                                                         final PublicKey referrerUserMetadataKey,
                                                         final PublicKey rentKey,
                                                         final PublicKey systemProgramKey,
                                                         final String shortUrl) {
    final var keys = List.of(
      createWritableSigner(referrerKey),
      createWrite(referrerStateKey),
      createWrite(referrerShortUrlKey),
      createRead(referrerUserMetadataKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    final byte[] _shortUrl = shortUrl.getBytes(UTF_8);
    final byte[] _data = new byte[12 + Borsh.lenVector(_shortUrl)];
    int i = writeDiscriminator(INIT_REFERRER_STATE_AND_SHORT_URL_DISCRIMINATOR, _data, 0);
    Borsh.writeVector(_shortUrl, _data, i);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record InitReferrerStateAndShortUrlIxData(Discriminator discriminator, String shortUrl, byte[] _shortUrl) implements Borsh {  

    public static InitReferrerStateAndShortUrlIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static InitReferrerStateAndShortUrlIxData createRecord(final Discriminator discriminator, final String shortUrl) {
      return new InitReferrerStateAndShortUrlIxData(discriminator, shortUrl, shortUrl.getBytes(UTF_8));
    }

    public static InitReferrerStateAndShortUrlIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var shortUrl = Borsh.string(_data, i);
      return new InitReferrerStateAndShortUrlIxData(discriminator, shortUrl, shortUrl.getBytes(UTF_8));
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(_shortUrl, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(_shortUrl);
    }
  }

  public static final Discriminator DELETE_REFERRER_STATE_AND_SHORT_URL_DISCRIMINATOR = toDiscriminator(153, 185, 99, 28, 228, 179, 187, 150);

  public static Instruction deleteReferrerStateAndShortUrl(final AccountMeta invokedKaminoLendingProgramMeta,
                                                           final PublicKey referrerKey,
                                                           final PublicKey referrerStateKey,
                                                           final PublicKey shortUrlKey,
                                                           final PublicKey rentKey,
                                                           final PublicKey systemProgramKey) {
    final var keys = List.of(
      createWritableSigner(referrerKey),
      createWrite(referrerStateKey),
      createWrite(shortUrlKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, DELETE_REFERRER_STATE_AND_SHORT_URL_DISCRIMINATOR);
  }

  public static final Discriminator IDL_MISSING_TYPES_DISCRIMINATOR = toDiscriminator(130, 80, 38, 153, 80, 212, 182, 253);

  public static Instruction idlMissingTypes(final AccountMeta invokedKaminoLendingProgramMeta,
                                            final PublicKey lendingMarketOwnerKey,
                                            final PublicKey lendingMarketKey,
                                            final PublicKey reserveKey,
                                            final ReserveFarmKind reserveFarmKind,
                                            final AssetTier assetTier,
                                            final FeeCalculation feeCalculation,
                                            final ReserveStatus reserveStatus,
                                            final UpdateConfigMode updateConfigMode,
                                            final UpdateLendingMarketConfigValue updateLendingMarketConfigValue,
                                            final UpdateLendingMarketMode updateLendingMarketConfigMode) {
    final var keys = List.of(
      createReadOnlySigner(lendingMarketOwnerKey),
      createRead(lendingMarketKey),
      createWrite(reserveKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(reserveFarmKind) + Borsh.len(assetTier) + Borsh.len(feeCalculation) + Borsh.len(reserveStatus) + Borsh.len(updateConfigMode) + Borsh.len(updateLendingMarketConfigValue) + Borsh.len(updateLendingMarketConfigMode)];
    int i = writeDiscriminator(IDL_MISSING_TYPES_DISCRIMINATOR, _data, 0);
    i += Borsh.write(reserveFarmKind, _data, i);
    i += Borsh.write(assetTier, _data, i);
    i += Borsh.write(feeCalculation, _data, i);
    i += Borsh.write(reserveStatus, _data, i);
    i += Borsh.write(updateConfigMode, _data, i);
    i += Borsh.write(updateLendingMarketConfigValue, _data, i);
    Borsh.write(updateLendingMarketConfigMode, _data, i);

    return Instruction.createInstruction(invokedKaminoLendingProgramMeta, keys, _data);
  }

  public record IdlMissingTypesIxData(Discriminator discriminator,
                                      ReserveFarmKind reserveFarmKind,
                                      AssetTier assetTier,
                                      FeeCalculation feeCalculation,
                                      ReserveStatus reserveStatus,
                                      UpdateConfigMode updateConfigMode,
                                      UpdateLendingMarketConfigValue updateLendingMarketConfigValue,
                                      UpdateLendingMarketMode updateLendingMarketConfigMode) implements Borsh {  

    public static IdlMissingTypesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static IdlMissingTypesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var reserveFarmKind = ReserveFarmKind.read(_data, i);
      i += Borsh.len(reserveFarmKind);
      final var assetTier = AssetTier.read(_data, i);
      i += Borsh.len(assetTier);
      final var feeCalculation = FeeCalculation.read(_data, i);
      i += Borsh.len(feeCalculation);
      final var reserveStatus = ReserveStatus.read(_data, i);
      i += Borsh.len(reserveStatus);
      final var updateConfigMode = UpdateConfigMode.read(_data, i);
      i += Borsh.len(updateConfigMode);
      final var updateLendingMarketConfigValue = UpdateLendingMarketConfigValue.read(_data, i);
      i += Borsh.len(updateLendingMarketConfigValue);
      final var updateLendingMarketConfigMode = UpdateLendingMarketMode.read(_data, i);
      return new IdlMissingTypesIxData(discriminator,
                                       reserveFarmKind,
                                       assetTier,
                                       feeCalculation,
                                       reserveStatus,
                                       updateConfigMode,
                                       updateLendingMarketConfigValue,
                                       updateLendingMarketConfigMode);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(reserveFarmKind, _data, i);
      i += Borsh.write(assetTier, _data, i);
      i += Borsh.write(feeCalculation, _data, i);
      i += Borsh.write(reserveStatus, _data, i);
      i += Borsh.write(updateConfigMode, _data, i);
      i += Borsh.write(updateLendingMarketConfigValue, _data, i);
      i += Borsh.write(updateLendingMarketConfigMode, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(reserveFarmKind)
           + Borsh.len(assetTier)
           + Borsh.len(feeCalculation)
           + Borsh.len(reserveStatus)
           + Borsh.len(updateConfigMode)
           + Borsh.len(updateLendingMarketConfigValue)
           + Borsh.len(updateLendingMarketConfigMode);
    }
  }

  private KaminoLendingProgram() {
  }
}
