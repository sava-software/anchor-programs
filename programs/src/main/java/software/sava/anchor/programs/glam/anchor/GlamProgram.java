package software.sava.anchor.programs.glam.anchor;

import java.lang.String;

import java.util.List;
import java.util.OptionalInt;

import software.sava.anchor.programs.glam.anchor.types.InitObligationArgs;
import software.sava.anchor.programs.glam.anchor.types.LiquidityParameterByStrategy;
import software.sava.anchor.programs.glam.anchor.types.MarketType;
import software.sava.anchor.programs.glam.anchor.types.MintModel;
import software.sava.anchor.programs.glam.anchor.types.ModifyOrderParams;
import software.sava.anchor.programs.glam.anchor.types.OrderParams;
import software.sava.anchor.programs.glam.anchor.types.PositionDirection;
import software.sava.anchor.programs.glam.anchor.types.StateModel;
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
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class GlamProgram {

  public static final Discriminator ADD_MINT_DISCRIMINATOR = toDiscriminator(171, 222, 111, 37, 60, 166, 208, 108);

  public static Instruction addMint(final AccountMeta invokedGlamProgramMeta,
                                    final SolanaAccounts solanaAccounts,
                                    final PublicKey glamStateKey,
                                    final PublicKey glamSignerKey,
                                    final PublicKey newMintKey,
                                    final PublicKey extraAccountMetaListKey,
                                    final PublicKey openfundsMetadataKey,
                                    final MintModel mintModel) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(newMintKey),
      createWrite(extraAccountMetaListKey),
      createWrite(requireNonNullElse(openfundsMetadataKey, invokedGlamProgramMeta.publicKey())),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.token2022Program())
    );

    final byte[] _data = new byte[8 + Borsh.len(mintModel)];
    int i = writeDiscriminator(ADD_MINT_DISCRIMINATOR, _data, 0);
    Borsh.write(mintModel, _data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record AddMintIxData(Discriminator discriminator, MintModel mintModel) implements Borsh {  

    public static AddMintIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static AddMintIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintModel = MintModel.read(_data, i);
      return new AddMintIxData(discriminator, mintModel);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(mintModel, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(mintModel);
    }
  }

  public static final Discriminator BURN_TOKENS_DISCRIMINATOR = toDiscriminator(76, 15, 51, 254, 229, 215, 121, 66);

  public static Instruction burnTokens(final AccountMeta invokedGlamProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey glamStateKey,
                                       final PublicKey glamSignerKey,
                                       final PublicKey glamMintKey,
                                       final PublicKey fromAtaKey,
                                       final PublicKey fromKey,
                                       final int mintId,
                                       final long amount) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createWrite(fromAtaKey),
      createRead(fromKey),
      createRead(solanaAccounts.token2022Program())
    );

    final byte[] _data = new byte[17];
    int i = writeDiscriminator(BURN_TOKENS_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mintId;
    ++i;
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record BurnTokensIxData(Discriminator discriminator, int mintId, long amount) implements Borsh {  

    public static BurnTokensIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static BurnTokensIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintId = _data[i] & 0xFF;
      ++i;
      final var amount = getInt64LE(_data, i);
      return new BurnTokensIxData(discriminator, mintId, amount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) mintId;
      ++i;
      putInt64LE(_data, i, amount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CLOSE_MINT_DISCRIMINATOR = toDiscriminator(149, 251, 157, 212, 65, 181, 235, 129);

  public static Instruction closeMint(final AccountMeta invokedGlamProgramMeta,
                                      final SolanaAccounts solanaAccounts,
                                      final PublicKey glamStateKey,
                                      final PublicKey glamVaultKey,
                                      final PublicKey glamSignerKey,
                                      final PublicKey glamMintKey,
                                      final PublicKey extraAccountMetaListKey,
                                      final PublicKey metadataKey,
                                      final int mintId) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createWrite(extraAccountMetaListKey),
      createWrite(metadataKey),
      createRead(solanaAccounts.token2022Program())
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(CLOSE_MINT_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mintId;

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record CloseMintIxData(Discriminator discriminator, int mintId) implements Borsh {  

    public static CloseMintIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static CloseMintIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintId = _data[i] & 0xFF;
      return new CloseMintIxData(discriminator, mintId);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) mintId;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CLOSE_STATE_DISCRIMINATOR = toDiscriminator(25, 1, 184, 101, 200, 245, 210, 246);

  public static Instruction closeState(final AccountMeta invokedGlamProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey glamStateKey,
                                       final PublicKey glamVaultKey,
                                       final PublicKey glamSignerKey,
                                       final PublicKey metadataKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createWrite(metadataKey),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, CLOSE_STATE_DISCRIMINATOR);
  }

  public static final Discriminator CLOSE_TOKEN_ACCOUNTS_DISCRIMINATOR = toDiscriminator(199, 170, 37, 55, 63, 183, 235, 143);

  public static Instruction closeTokenAccounts(final AccountMeta invokedGlamProgramMeta,
                                               final SolanaAccounts solanaAccounts,
                                               final PublicKey glamStateKey,
                                               final PublicKey glamVaultKey,
                                               final PublicKey glamSignerKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.token2022Program())
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, CLOSE_TOKEN_ACCOUNTS_DISCRIMINATOR);
  }

  public static final Discriminator DEACTIVATE_STAKE_ACCOUNTS_DISCRIMINATOR = toDiscriminator(58, 18, 6, 22, 226, 216, 161, 193);

  public static Instruction deactivateStakeAccounts(final AccountMeta invokedGlamProgramMeta,
                                                    final SolanaAccounts solanaAccounts,
                                                    final PublicKey glamStateKey,
                                                    final PublicKey glamVaultKey,
                                                    final PublicKey glamSignerKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeProgram())
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, DEACTIVATE_STAKE_ACCOUNTS_DISCRIMINATOR);
  }

  public static final Discriminator DRIFT_BALANCE_VALUE_USD_DISCRIMINATOR = toDiscriminator(152, 248, 238, 80, 92, 122, 40, 131);

  public static Instruction driftBalanceValueUsd(final AccountMeta invokedGlamProgramMeta,
                                                 final PublicKey glamStateKey,
                                                 final PublicKey glamVaultKey,
                                                 final PublicKey signerKey,
                                                 final PublicKey stateKey,
                                                 final PublicKey userKey,
                                                 final PublicKey userStatsKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(signerKey),
      createRead(stateKey),
      createRead(userKey),
      createRead(userStatsKey)
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, DRIFT_BALANCE_VALUE_USD_DISCRIMINATOR);
  }

  public static final Discriminator DRIFT_CANCEL_ORDERS_DISCRIMINATOR = toDiscriminator(98, 107, 48, 79, 97, 60, 99, 58);

  public static Instruction driftCancelOrders(final AccountMeta invokedGlamProgramMeta,
                                              final PublicKey glamStateKey,
                                              final PublicKey glamVaultKey,
                                              final PublicKey glamSignerKey,
                                              final PublicKey cpiProgramKey,
                                              final PublicKey stateKey,
                                              final PublicKey userKey,
                                              final MarketType marketType,
                                              final OptionalInt marketIndex,
                                              final PositionDirection direction) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createRead(stateKey),
      createWrite(userKey)
    );

    final byte[] _data = new byte[
        8
        + (marketType == null ? 1 : (1 + Borsh.len(marketType)))
        + (marketIndex == null || marketIndex.isEmpty() ? 1 : 3)
        + (direction == null ? 1 : (1 + Borsh.len(direction)))
    ];
    int i = writeDiscriminator(DRIFT_CANCEL_ORDERS_DISCRIMINATOR, _data, 0);
    i += Borsh.writeOptional(marketType, _data, i);
    i += Borsh.writeOptionalshort(marketIndex, _data, i);
    Borsh.writeOptional(direction, _data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record DriftCancelOrdersIxData(Discriminator discriminator,
                                        MarketType marketType,
                                        OptionalInt marketIndex,
                                        PositionDirection direction) implements Borsh {  

    public static DriftCancelOrdersIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static DriftCancelOrdersIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var marketType = _data[i++] == 0 ? null : MarketType.read(_data, i);
      if (marketType != null) {
        i += Borsh.len(marketType);
      }
      final var marketIndex = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
      if (marketIndex.isPresent()) {
        i += 2;
      }
      final var direction = _data[i++] == 0 ? null : PositionDirection.read(_data, i);
      return new DriftCancelOrdersIxData(discriminator, marketType, marketIndex, direction);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeOptional(marketType, _data, i);
      i += Borsh.writeOptionalshort(marketIndex, _data, i);
      i += Borsh.writeOptional(direction, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + (marketType == null ? 1 : (1 + Borsh.len(marketType))) + (marketIndex == null || marketIndex.isEmpty() ? 1 : (1 + 2)) + (direction == null ? 1 : (1 + Borsh.len(direction)));
    }
  }

  public static final Discriminator DRIFT_CANCEL_ORDERS_BY_IDS_DISCRIMINATOR = toDiscriminator(172, 99, 108, 14, 81, 89, 228, 183);

  public static Instruction driftCancelOrdersByIds(final AccountMeta invokedGlamProgramMeta,
                                                   final PublicKey glamStateKey,
                                                   final PublicKey glamVaultKey,
                                                   final PublicKey glamSignerKey,
                                                   final PublicKey cpiProgramKey,
                                                   final PublicKey stateKey,
                                                   final PublicKey userKey,
                                                   final int[] orderIds) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createRead(stateKey),
      createWrite(userKey)
    );

    final byte[] _data = new byte[8 + Borsh.lenVector(orderIds)];
    int i = writeDiscriminator(DRIFT_CANCEL_ORDERS_BY_IDS_DISCRIMINATOR, _data, 0);
    Borsh.writeVector(orderIds, _data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record DriftCancelOrdersByIdsIxData(Discriminator discriminator, int[] orderIds) implements Borsh {  

    public static DriftCancelOrdersByIdsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static DriftCancelOrdersByIdsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var orderIds = Borsh.readintVector(_data, i);
      return new DriftCancelOrdersByIdsIxData(discriminator, orderIds);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(orderIds, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(orderIds);
    }
  }

  public static final Discriminator DRIFT_DELETE_USER_DISCRIMINATOR = toDiscriminator(179, 118, 20, 212, 145, 146, 49, 130);

  public static Instruction driftDeleteUser(final AccountMeta invokedGlamProgramMeta,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamVaultKey,
                                            final PublicKey glamSignerKey,
                                            final PublicKey cpiProgramKey,
                                            final PublicKey userKey,
                                            final PublicKey userStatsKey,
                                            final PublicKey stateKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(userKey),
      createWrite(userStatsKey),
      createWrite(stateKey)
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, DRIFT_DELETE_USER_DISCRIMINATOR);
  }

  public static final Discriminator DRIFT_DEPOSIT_DISCRIMINATOR = toDiscriminator(252, 63, 250, 201, 98, 55, 130, 12);

  public static Instruction driftDeposit(final AccountMeta invokedGlamProgramMeta,
                                         final PublicKey glamStateKey,
                                         final PublicKey glamVaultKey,
                                         final PublicKey glamSignerKey,
                                         final PublicKey cpiProgramKey,
                                         final PublicKey stateKey,
                                         final PublicKey userKey,
                                         final PublicKey userStatsKey,
                                         final PublicKey spotMarketVaultKey,
                                         final PublicKey userTokenAccountKey,
                                         final PublicKey tokenProgramKey,
                                         final int marketIndex,
                                         final long amount,
                                         final boolean reduceOnly) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createRead(stateKey),
      createWrite(userKey),
      createWrite(userStatsKey),
      createWrite(spotMarketVaultKey),
      createWrite(userTokenAccountKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[19];
    int i = writeDiscriminator(DRIFT_DEPOSIT_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) (reduceOnly ? 1 : 0);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record DriftDepositIxData(Discriminator discriminator,
                                   int marketIndex,
                                   long amount,
                                   boolean reduceOnly) implements Borsh {  

    public static DriftDepositIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 19;

    public static DriftDepositIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var marketIndex = getInt16LE(_data, i);
      i += 2;
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var reduceOnly = _data[i] == 1;
      return new DriftDepositIxData(discriminator, marketIndex, amount, reduceOnly);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, marketIndex);
      i += 2;
      putInt64LE(_data, i, amount);
      i += 8;
      _data[i] = (byte) (reduceOnly ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DRIFT_INITIALIZE_USER_DISCRIMINATOR = toDiscriminator(107, 244, 158, 15, 225, 239, 98, 245);

  public static Instruction driftInitializeUser(final AccountMeta invokedGlamProgramMeta,
                                                final SolanaAccounts solanaAccounts,
                                                final PublicKey glamStateKey,
                                                final PublicKey glamVaultKey,
                                                final PublicKey glamSignerKey,
                                                final PublicKey cpiProgramKey,
                                                final PublicKey userKey,
                                                final PublicKey userStatsKey,
                                                final PublicKey stateKey,
                                                final PublicKey payerKey,
                                                final int subAccountId,
                                                final byte[] name) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(userKey),
      createWrite(userStatsKey),
      createWrite(stateKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.rentSysVar()),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[10 + Borsh.lenArray(name)];
    int i = writeDiscriminator(DRIFT_INITIALIZE_USER_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, subAccountId);
    i += 2;
    Borsh.writeArray(name, _data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record DriftInitializeUserIxData(Discriminator discriminator, int subAccountId, byte[] name) implements Borsh {  

    public static DriftInitializeUserIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 42;

    public static DriftInitializeUserIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var subAccountId = getInt16LE(_data, i);
      i += 2;
      final var name = new byte[32];
      Borsh.readArray(name, _data, i);
      return new DriftInitializeUserIxData(discriminator, subAccountId, name);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, subAccountId);
      i += 2;
      i += Borsh.writeArray(name, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DRIFT_INITIALIZE_USER_STATS_DISCRIMINATOR = toDiscriminator(133, 185, 103, 162, 90, 161, 78, 143);

  public static Instruction driftInitializeUserStats(final AccountMeta invokedGlamProgramMeta,
                                                     final SolanaAccounts solanaAccounts,
                                                     final PublicKey glamStateKey,
                                                     final PublicKey glamVaultKey,
                                                     final PublicKey glamSignerKey,
                                                     final PublicKey cpiProgramKey,
                                                     final PublicKey userStatsKey,
                                                     final PublicKey stateKey,
                                                     final PublicKey payerKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(userStatsKey),
      createWrite(stateKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.rentSysVar()),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, DRIFT_INITIALIZE_USER_STATS_DISCRIMINATOR);
  }

  public static final Discriminator DRIFT_MODIFY_ORDER_DISCRIMINATOR = toDiscriminator(235, 245, 222, 58, 245, 128, 19, 202);

  public static Instruction driftModifyOrder(final AccountMeta invokedGlamProgramMeta,
                                             final PublicKey glamStateKey,
                                             final PublicKey glamVaultKey,
                                             final PublicKey glamSignerKey,
                                             final PublicKey cpiProgramKey,
                                             final PublicKey stateKey,
                                             final PublicKey userKey,
                                             final OptionalInt orderId,
                                             final ModifyOrderParams modifyOrderParams) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createRead(stateKey),
      createWrite(userKey)
    );

    final byte[] _data = new byte[
        8
        + (orderId == null || orderId.isEmpty() ? 1 : 5) + Borsh.len(modifyOrderParams)
    ];
    int i = writeDiscriminator(DRIFT_MODIFY_ORDER_DISCRIMINATOR, _data, 0);
    i += Borsh.writeOptional(orderId, _data, i);
    Borsh.write(modifyOrderParams, _data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record DriftModifyOrderIxData(Discriminator discriminator, OptionalInt orderId, ModifyOrderParams modifyOrderParams) implements Borsh {  

    public static DriftModifyOrderIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static DriftModifyOrderIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var orderId = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
      if (orderId.isPresent()) {
        i += 4;
      }
      final var modifyOrderParams = ModifyOrderParams.read(_data, i);
      return new DriftModifyOrderIxData(discriminator, orderId, modifyOrderParams);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeOptional(orderId, _data, i);
      i += Borsh.write(modifyOrderParams, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + (orderId == null || orderId.isEmpty() ? 1 : (1 + 4)) + Borsh.len(modifyOrderParams);
    }
  }

  public static final Discriminator DRIFT_PLACE_ORDERS_DISCRIMINATOR = toDiscriminator(117, 18, 210, 6, 238, 174, 135, 167);

  public static Instruction driftPlaceOrders(final AccountMeta invokedGlamProgramMeta,
                                             final PublicKey glamStateKey,
                                             final PublicKey glamVaultKey,
                                             final PublicKey glamSignerKey,
                                             final PublicKey cpiProgramKey,
                                             final PublicKey stateKey,
                                             final PublicKey userKey,
                                             final OrderParams[] params) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createRead(stateKey),
      createWrite(userKey)
    );

    final byte[] _data = new byte[8 + Borsh.lenVector(params)];
    int i = writeDiscriminator(DRIFT_PLACE_ORDERS_DISCRIMINATOR, _data, 0);
    Borsh.writeVector(params, _data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record DriftPlaceOrdersIxData(Discriminator discriminator, OrderParams[] params) implements Borsh {  

    public static DriftPlaceOrdersIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static DriftPlaceOrdersIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = Borsh.readVector(OrderParams.class, OrderParams::read, _data, i);
      return new DriftPlaceOrdersIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(params);
    }
  }

  public static final Discriminator DRIFT_UPDATE_USER_CUSTOM_MARGIN_RATIO_DISCRIMINATOR = toDiscriminator(4, 47, 193, 177, 128, 62, 228, 14);

  public static Instruction driftUpdateUserCustomMarginRatio(final AccountMeta invokedGlamProgramMeta,
                                                             final PublicKey glamStateKey,
                                                             final PublicKey glamVaultKey,
                                                             final PublicKey glamSignerKey,
                                                             final PublicKey cpiProgramKey,
                                                             final PublicKey userKey,
                                                             final int subAccountId,
                                                             final int marginRatio) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(userKey)
    );

    final byte[] _data = new byte[14];
    int i = writeDiscriminator(DRIFT_UPDATE_USER_CUSTOM_MARGIN_RATIO_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, subAccountId);
    i += 2;
    putInt32LE(_data, i, marginRatio);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record DriftUpdateUserCustomMarginRatioIxData(Discriminator discriminator, int subAccountId, int marginRatio) implements Borsh {  

    public static DriftUpdateUserCustomMarginRatioIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 14;

    public static DriftUpdateUserCustomMarginRatioIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var subAccountId = getInt16LE(_data, i);
      i += 2;
      final var marginRatio = getInt32LE(_data, i);
      return new DriftUpdateUserCustomMarginRatioIxData(discriminator, subAccountId, marginRatio);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, subAccountId);
      i += 2;
      putInt32LE(_data, i, marginRatio);
      i += 4;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DRIFT_UPDATE_USER_DELEGATE_DISCRIMINATOR = toDiscriminator(36, 181, 34, 31, 22, 77, 36, 154);

  public static Instruction driftUpdateUserDelegate(final AccountMeta invokedGlamProgramMeta,
                                                    final PublicKey glamStateKey,
                                                    final PublicKey glamVaultKey,
                                                    final PublicKey glamSignerKey,
                                                    final PublicKey cpiProgramKey,
                                                    final PublicKey userKey,
                                                    final int subAccountId,
                                                    final PublicKey delegate) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(userKey)
    );

    final byte[] _data = new byte[42];
    int i = writeDiscriminator(DRIFT_UPDATE_USER_DELEGATE_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, subAccountId);
    i += 2;
    delegate.write(_data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record DriftUpdateUserDelegateIxData(Discriminator discriminator, int subAccountId, PublicKey delegate) implements Borsh {  

    public static DriftUpdateUserDelegateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 42;

    public static DriftUpdateUserDelegateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var subAccountId = getInt16LE(_data, i);
      i += 2;
      final var delegate = readPubKey(_data, i);
      return new DriftUpdateUserDelegateIxData(discriminator, subAccountId, delegate);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, subAccountId);
      i += 2;
      delegate.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DRIFT_UPDATE_USER_MARGIN_TRADING_ENABLED_DISCRIMINATOR = toDiscriminator(157, 175, 12, 19, 202, 114, 17, 36);

  public static Instruction driftUpdateUserMarginTradingEnabled(final AccountMeta invokedGlamProgramMeta,
                                                                final PublicKey glamStateKey,
                                                                final PublicKey glamVaultKey,
                                                                final PublicKey glamSignerKey,
                                                                final PublicKey cpiProgramKey,
                                                                final PublicKey userKey,
                                                                final int subAccountId,
                                                                final boolean marginTradingEnabled) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(userKey)
    );

    final byte[] _data = new byte[11];
    int i = writeDiscriminator(DRIFT_UPDATE_USER_MARGIN_TRADING_ENABLED_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, subAccountId);
    i += 2;
    _data[i] = (byte) (marginTradingEnabled ? 1 : 0);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record DriftUpdateUserMarginTradingEnabledIxData(Discriminator discriminator, int subAccountId, boolean marginTradingEnabled) implements Borsh {  

    public static DriftUpdateUserMarginTradingEnabledIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 11;

    public static DriftUpdateUserMarginTradingEnabledIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var subAccountId = getInt16LE(_data, i);
      i += 2;
      final var marginTradingEnabled = _data[i] == 1;
      return new DriftUpdateUserMarginTradingEnabledIxData(discriminator, subAccountId, marginTradingEnabled);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, subAccountId);
      i += 2;
      _data[i] = (byte) (marginTradingEnabled ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DRIFT_WITHDRAW_DISCRIMINATOR = toDiscriminator(86, 59, 186, 123, 183, 181, 234, 137);

  public static Instruction driftWithdraw(final AccountMeta invokedGlamProgramMeta,
                                          final PublicKey glamStateKey,
                                          final PublicKey glamVaultKey,
                                          final PublicKey glamSignerKey,
                                          final PublicKey cpiProgramKey,
                                          final PublicKey stateKey,
                                          final PublicKey userKey,
                                          final PublicKey userStatsKey,
                                          final PublicKey spotMarketVaultKey,
                                          final PublicKey driftSignerKey,
                                          final PublicKey userTokenAccountKey,
                                          final PublicKey tokenProgramKey,
                                          final int marketIndex,
                                          final long amount,
                                          final boolean reduceOnly) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createRead(stateKey),
      createWrite(userKey),
      createWrite(userStatsKey),
      createWrite(spotMarketVaultKey),
      createRead(driftSignerKey),
      createWrite(userTokenAccountKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[19];
    int i = writeDiscriminator(DRIFT_WITHDRAW_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) (reduceOnly ? 1 : 0);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record DriftWithdrawIxData(Discriminator discriminator,
                                    int marketIndex,
                                    long amount,
                                    boolean reduceOnly) implements Borsh {  

    public static DriftWithdrawIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 19;

    public static DriftWithdrawIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var marketIndex = getInt16LE(_data, i);
      i += 2;
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var reduceOnly = _data[i] == 1;
      return new DriftWithdrawIxData(discriminator, marketIndex, amount, reduceOnly);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, marketIndex);
      i += 2;
      putInt64LE(_data, i, amount);
      i += 8;
      _data[i] = (byte) (reduceOnly ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator FORCE_TRANSFER_TOKENS_DISCRIMINATOR = toDiscriminator(185, 34, 78, 211, 192, 13, 160, 37);

  public static Instruction forceTransferTokens(final AccountMeta invokedGlamProgramMeta,
                                                final SolanaAccounts solanaAccounts,
                                                final PublicKey glamStateKey,
                                                final PublicKey glamSignerKey,
                                                final PublicKey glamMintKey,
                                                final PublicKey fromAtaKey,
                                                final PublicKey toAtaKey,
                                                final PublicKey fromKey,
                                                final PublicKey toKey,
                                                final int mintId,
                                                final long amount) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createWrite(fromAtaKey),
      createWrite(toAtaKey),
      createRead(fromKey),
      createRead(toKey),
      createRead(solanaAccounts.token2022Program())
    );

    final byte[] _data = new byte[17];
    int i = writeDiscriminator(FORCE_TRANSFER_TOKENS_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mintId;
    ++i;
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record ForceTransferTokensIxData(Discriminator discriminator, int mintId, long amount) implements Borsh {  

    public static ForceTransferTokensIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static ForceTransferTokensIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintId = _data[i] & 0xFF;
      ++i;
      final var amount = getInt64LE(_data, i);
      return new ForceTransferTokensIxData(discriminator, mintId, amount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) mintId;
      ++i;
      putInt64LE(_data, i, amount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_AND_DELEGATE_STAKE_DISCRIMINATOR = toDiscriminator(71, 101, 230, 157, 50, 23, 47, 1);

  public static Instruction initializeAndDelegateStake(final AccountMeta invokedGlamProgramMeta,
                                                       final SolanaAccounts solanaAccounts,
                                                       final PublicKey glamStateKey,
                                                       final PublicKey glamVaultKey,
                                                       final PublicKey glamSignerKey,
                                                       final PublicKey vaultStakeAccountKey,
                                                       final PublicKey voteKey,
                                                       final PublicKey stakeConfigKey,
                                                       final long lamports) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createWrite(vaultStakeAccountKey),
      createRead(voteKey),
      createRead(stakeConfigKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.rentSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar()),
      createRead(solanaAccounts.stakeProgram()),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(INITIALIZE_AND_DELEGATE_STAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record InitializeAndDelegateStakeIxData(Discriminator discriminator, long lamports) implements Borsh {  

    public static InitializeAndDelegateStakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static InitializeAndDelegateStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      return new InitializeAndDelegateStakeIxData(discriminator, lamports);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lamports);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_STATE_DISCRIMINATOR = toDiscriminator(190, 171, 224, 219, 217, 72, 199, 176);

  public static Instruction initializeState(final AccountMeta invokedGlamProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamVaultKey,
                                            final PublicKey glamSignerKey,
                                            final PublicKey openfundsMetadataKey,
                                            final StateModel state) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createWrite(requireNonNullElse(openfundsMetadataKey, invokedGlamProgramMeta.publicKey())),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(state)];
    int i = writeDiscriminator(INITIALIZE_STATE_DISCRIMINATOR, _data, 0);
    Borsh.write(state, _data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record InitializeStateIxData(Discriminator discriminator, StateModel state) implements Borsh {  

    public static InitializeStateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static InitializeStateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var state = StateModel.read(_data, i);
      return new InitializeStateIxData(discriminator, state);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(state, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(state);
    }
  }

  public static final Discriminator JUPITER_GOV_NEW_VOTE_DISCRIMINATOR = toDiscriminator(235, 179, 170, 64, 64, 57, 17, 69);

  public static Instruction jupiterGovNewVote(final AccountMeta invokedGlamProgramMeta,
                                              final SolanaAccounts solanaAccounts,
                                              final PublicKey glamStateKey,
                                              final PublicKey glamVaultKey,
                                              final PublicKey glamSignerKey,
                                              final PublicKey cpiProgramKey,
                                              final PublicKey proposalKey,
                                              final PublicKey voteKey,
                                              final PublicKey payerKey,
                                              final PublicKey voter) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createRead(proposalKey),
      createWrite(voteKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(JUPITER_GOV_NEW_VOTE_DISCRIMINATOR, _data, 0);
    voter.write(_data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record JupiterGovNewVoteIxData(Discriminator discriminator, PublicKey voter) implements Borsh {  

    public static JupiterGovNewVoteIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static JupiterGovNewVoteIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var voter = readPubKey(_data, i);
      return new JupiterGovNewVoteIxData(discriminator, voter);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      voter.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator JUPITER_SET_MAX_SWAP_SLIPPAGE_DISCRIMINATOR = toDiscriminator(110, 79, 13, 71, 208, 111, 56, 66);

  public static Instruction jupiterSetMaxSwapSlippage(final AccountMeta invokedGlamProgramMeta,
                                                      final PublicKey glamStateKey,
                                                      final PublicKey glamSignerKey,
                                                      final long slippage) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(JUPITER_SET_MAX_SWAP_SLIPPAGE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, slippage);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record JupiterSetMaxSwapSlippageIxData(Discriminator discriminator, long slippage) implements Borsh {  

    public static JupiterSetMaxSwapSlippageIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static JupiterSetMaxSwapSlippageIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var slippage = getInt64LE(_data, i);
      return new JupiterSetMaxSwapSlippageIxData(discriminator, slippage);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, slippage);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator JUPITER_SWAP_DISCRIMINATOR = toDiscriminator(116, 207, 0, 196, 252, 120, 243, 18);

  public static Instruction jupiterSwap(final AccountMeta invokedGlamProgramMeta,
                                        final SolanaAccounts solanaAccounts,
                                        final PublicKey glamStateKey,
                                        final PublicKey glamVaultKey,
                                        final PublicKey glamSignerKey,
                                        final PublicKey cpiProgramKey,
                                        final PublicKey inputVaultAtaKey,
                                        final PublicKey outputVaultAtaKey,
                                        final PublicKey inputMintKey,
                                        final PublicKey outputMintKey,
                                        final PublicKey inputStakePoolKey,
                                        final PublicKey outputStakePoolKey,
                                        final PublicKey inputTokenProgramKey,
                                        final PublicKey outputTokenProgramKey,
                                        final byte[] data) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(inputVaultAtaKey),
      createWrite(outputVaultAtaKey),
      createRead(inputMintKey),
      createRead(outputMintKey),
      createRead(requireNonNullElse(inputStakePoolKey, invokedGlamProgramMeta.publicKey())),
      createRead(requireNonNullElse(outputStakePoolKey, invokedGlamProgramMeta.publicKey())),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(inputTokenProgramKey),
      createRead(outputTokenProgramKey)
    );

    final byte[] _data = new byte[12 + Borsh.lenVector(data)];
    int i = writeDiscriminator(JUPITER_SWAP_DISCRIMINATOR, _data, 0);
    Borsh.writeVector(data, _data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record JupiterSwapIxData(Discriminator discriminator, byte[] data) implements Borsh {  

    public static JupiterSwapIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static JupiterSwapIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final byte[] data = Borsh.readbyteVector(_data, i);
      return new JupiterSwapIxData(discriminator, data);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(data, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(data);
    }
  }

  public static final Discriminator JUPITER_VOTE_CAST_VOTE_DISCRIMINATOR = toDiscriminator(11, 197, 234, 57, 164, 74, 181, 239);

  public static Instruction jupiterVoteCastVote(final AccountMeta invokedGlamProgramMeta,
                                                final PublicKey glamStateKey,
                                                final PublicKey glamVaultKey,
                                                final PublicKey glamSignerKey,
                                                final PublicKey cpiProgramKey,
                                                final PublicKey lockerKey,
                                                final PublicKey escrowKey,
                                                final PublicKey proposalKey,
                                                final PublicKey voteKey,
                                                final PublicKey governorKey,
                                                final PublicKey governProgramKey,
                                                final int side) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createRead(lockerKey),
      createRead(escrowKey),
      createWrite(proposalKey),
      createWrite(voteKey),
      createRead(governorKey),
      createRead(governProgramKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(JUPITER_VOTE_CAST_VOTE_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) side;

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record JupiterVoteCastVoteIxData(Discriminator discriminator, int side) implements Borsh {  

    public static JupiterVoteCastVoteIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static JupiterVoteCastVoteIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var side = _data[i] & 0xFF;
      return new JupiterVoteCastVoteIxData(discriminator, side);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) side;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator JUPITER_VOTE_CAST_VOTE_CHECKED_DISCRIMINATOR = toDiscriminator(247, 3, 146, 233, 35, 189, 192, 187);

  public static Instruction jupiterVoteCastVoteChecked(final AccountMeta invokedGlamProgramMeta,
                                                       final PublicKey glamStateKey,
                                                       final PublicKey glamVaultKey,
                                                       final PublicKey glamSignerKey,
                                                       final PublicKey cpiProgramKey,
                                                       final PublicKey lockerKey,
                                                       final PublicKey escrowKey,
                                                       final PublicKey proposalKey,
                                                       final PublicKey voteKey,
                                                       final PublicKey governorKey,
                                                       final PublicKey governProgramKey,
                                                       final int side,
                                                       final int expectedSide) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createRead(lockerKey),
      createRead(escrowKey),
      createWrite(proposalKey),
      createWrite(voteKey),
      createRead(governorKey),
      createRead(governProgramKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(JUPITER_VOTE_CAST_VOTE_CHECKED_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) side;
    ++i;
    _data[i] = (byte) expectedSide;

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record JupiterVoteCastVoteCheckedIxData(Discriminator discriminator, int side, int expectedSide) implements Borsh {  

    public static JupiterVoteCastVoteCheckedIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static JupiterVoteCastVoteCheckedIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var side = _data[i] & 0xFF;
      ++i;
      final var expectedSide = _data[i] & 0xFF;
      return new JupiterVoteCastVoteCheckedIxData(discriminator, side, expectedSide);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) side;
      ++i;
      _data[i] = (byte) expectedSide;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator JUPITER_VOTE_INCREASE_LOCKED_AMOUNT_DISCRIMINATOR = toDiscriminator(225, 38, 201, 123, 148, 23, 47, 128);

  public static Instruction jupiterVoteIncreaseLockedAmount(final AccountMeta invokedGlamProgramMeta,
                                                            final PublicKey glamStateKey,
                                                            final PublicKey glamVaultKey,
                                                            final PublicKey glamSignerKey,
                                                            final PublicKey cpiProgramKey,
                                                            final PublicKey lockerKey,
                                                            final PublicKey escrowKey,
                                                            final PublicKey escrowTokensKey,
                                                            final PublicKey sourceTokensKey,
                                                            final PublicKey tokenProgramKey,
                                                            final long amount) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(lockerKey),
      createWrite(escrowKey),
      createWrite(escrowTokensKey),
      createWrite(sourceTokensKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(JUPITER_VOTE_INCREASE_LOCKED_AMOUNT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record JupiterVoteIncreaseLockedAmountIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static JupiterVoteIncreaseLockedAmountIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static JupiterVoteIncreaseLockedAmountIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new JupiterVoteIncreaseLockedAmountIxData(discriminator, amount);
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

  public static final Discriminator JUPITER_VOTE_MERGE_PARTIAL_UNSTAKING_DISCRIMINATOR = toDiscriminator(93, 226, 122, 120, 130, 35, 189, 208);

  public static Instruction jupiterVoteMergePartialUnstaking(final AccountMeta invokedGlamProgramMeta,
                                                             final PublicKey glamStateKey,
                                                             final PublicKey glamVaultKey,
                                                             final PublicKey glamSignerKey,
                                                             final PublicKey cpiProgramKey,
                                                             final PublicKey lockerKey,
                                                             final PublicKey escrowKey,
                                                             final PublicKey partialUnstakeKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(lockerKey),
      createWrite(escrowKey),
      createWrite(partialUnstakeKey)
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, JUPITER_VOTE_MERGE_PARTIAL_UNSTAKING_DISCRIMINATOR);
  }

  public static final Discriminator JUPITER_VOTE_NEW_ESCROW_DISCRIMINATOR = toDiscriminator(255, 87, 157, 219, 61, 178, 144, 159);

  public static Instruction jupiterVoteNewEscrow(final AccountMeta invokedGlamProgramMeta,
                                                 final SolanaAccounts solanaAccounts,
                                                 final PublicKey glamStateKey,
                                                 final PublicKey glamVaultKey,
                                                 final PublicKey glamSignerKey,
                                                 final PublicKey cpiProgramKey,
                                                 final PublicKey lockerKey,
                                                 final PublicKey escrowKey,
                                                 final PublicKey payerKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(lockerKey),
      createWrite(escrowKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, JUPITER_VOTE_NEW_ESCROW_DISCRIMINATOR);
  }

  public static final Discriminator JUPITER_VOTE_OPEN_PARTIAL_UNSTAKING_DISCRIMINATOR = toDiscriminator(84, 7, 113, 220, 212, 63, 237, 218);

  public static Instruction jupiterVoteOpenPartialUnstaking(final AccountMeta invokedGlamProgramMeta,
                                                            final SolanaAccounts solanaAccounts,
                                                            final PublicKey glamStateKey,
                                                            final PublicKey glamVaultKey,
                                                            final PublicKey glamSignerKey,
                                                            final PublicKey cpiProgramKey,
                                                            final PublicKey lockerKey,
                                                            final PublicKey escrowKey,
                                                            final PublicKey partialUnstakeKey,
                                                            final long amount,
                                                            final String memo) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(lockerKey),
      createWrite(escrowKey),
      createWritableSigner(partialUnstakeKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _memo = memo.getBytes(UTF_8);
    final byte[] _data = new byte[20 + Borsh.lenVector(_memo)];
    int i = writeDiscriminator(JUPITER_VOTE_OPEN_PARTIAL_UNSTAKING_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    Borsh.writeVector(_memo, _data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record JupiterVoteOpenPartialUnstakingIxData(Discriminator discriminator, long amount, String memo, byte[] _memo) implements Borsh {  

    public static JupiterVoteOpenPartialUnstakingIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static JupiterVoteOpenPartialUnstakingIxData createRecord(final Discriminator discriminator, final long amount, final String memo) {
      return new JupiterVoteOpenPartialUnstakingIxData(discriminator, amount, memo, memo.getBytes(UTF_8));
    }

    public static JupiterVoteOpenPartialUnstakingIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var memo = Borsh.string(_data, i);
      return new JupiterVoteOpenPartialUnstakingIxData(discriminator, amount, memo, memo.getBytes(UTF_8));
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      i += Borsh.writeVector(_memo, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + Borsh.lenVector(_memo);
    }
  }

  public static final Discriminator JUPITER_VOTE_TOGGLE_MAX_LOCK_DISCRIMINATOR = toDiscriminator(204, 158, 192, 21, 219, 25, 154, 87);

  public static Instruction jupiterVoteToggleMaxLock(final AccountMeta invokedGlamProgramMeta,
                                                     final PublicKey glamStateKey,
                                                     final PublicKey glamVaultKey,
                                                     final PublicKey glamSignerKey,
                                                     final PublicKey cpiProgramKey,
                                                     final PublicKey lockerKey,
                                                     final PublicKey escrowKey,
                                                     final boolean isMaxLock) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createRead(lockerKey),
      createWrite(escrowKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(JUPITER_VOTE_TOGGLE_MAX_LOCK_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) (isMaxLock ? 1 : 0);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record JupiterVoteToggleMaxLockIxData(Discriminator discriminator, boolean isMaxLock) implements Borsh {  

    public static JupiterVoteToggleMaxLockIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static JupiterVoteToggleMaxLockIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var isMaxLock = _data[i] == 1;
      return new JupiterVoteToggleMaxLockIxData(discriminator, isMaxLock);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) (isMaxLock ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator JUPITER_VOTE_WITHDRAW_DISCRIMINATOR = toDiscriminator(195, 172, 184, 195, 23, 178, 145, 191);

  public static Instruction jupiterVoteWithdraw(final AccountMeta invokedGlamProgramMeta,
                                                final PublicKey glamStateKey,
                                                final PublicKey glamVaultKey,
                                                final PublicKey glamSignerKey,
                                                final PublicKey cpiProgramKey,
                                                final PublicKey lockerKey,
                                                final PublicKey escrowKey,
                                                final PublicKey escrowTokensKey,
                                                final PublicKey destinationTokensKey,
                                                final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(lockerKey),
      createWrite(escrowKey),
      createWrite(escrowTokensKey),
      createWrite(destinationTokensKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, JUPITER_VOTE_WITHDRAW_DISCRIMINATOR);
  }

  public static final Discriminator JUPITER_VOTE_WITHDRAW_PARTIAL_UNSTAKING_DISCRIMINATOR = toDiscriminator(109, 98, 65, 252, 184, 0, 216, 240);

  public static Instruction jupiterVoteWithdrawPartialUnstaking(final AccountMeta invokedGlamProgramMeta,
                                                                final PublicKey glamStateKey,
                                                                final PublicKey glamVaultKey,
                                                                final PublicKey glamSignerKey,
                                                                final PublicKey cpiProgramKey,
                                                                final PublicKey lockerKey,
                                                                final PublicKey escrowKey,
                                                                final PublicKey partialUnstakeKey,
                                                                final PublicKey escrowTokensKey,
                                                                final PublicKey destinationTokensKey,
                                                                final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(lockerKey),
      createWrite(escrowKey),
      createWrite(partialUnstakeKey),
      createWrite(escrowTokensKey),
      createWrite(destinationTokensKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, JUPITER_VOTE_WITHDRAW_PARTIAL_UNSTAKING_DISCRIMINATOR);
  }

  public static final Discriminator KAMINO_LENDING_DEPOSIT_RESERVE_LIQUIDITY_AND_OBLIGATION_COLLATERAL_DISCRIMINATOR = toDiscriminator(31, 162, 4, 146, 60, 225, 15, 0);

  public static Instruction kaminoLendingDepositReserveLiquidityAndObligationCollateral(final AccountMeta invokedGlamProgramMeta,
                                                                                        final PublicKey glamStateKey,
                                                                                        final PublicKey glamVaultKey,
                                                                                        final PublicKey glamSignerKey,
                                                                                        final PublicKey cpiProgramKey,
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
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(ownerKey),
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
    int i = writeDiscriminator(KAMINO_LENDING_DEPOSIT_RESERVE_LIQUIDITY_AND_OBLIGATION_COLLATERAL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, liquidityAmount);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record KaminoLendingDepositReserveLiquidityAndObligationCollateralIxData(Discriminator discriminator, long liquidityAmount) implements Borsh {  

    public static KaminoLendingDepositReserveLiquidityAndObligationCollateralIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static KaminoLendingDepositReserveLiquidityAndObligationCollateralIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityAmount = getInt64LE(_data, i);
      return new KaminoLendingDepositReserveLiquidityAndObligationCollateralIxData(discriminator, liquidityAmount);
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

  public static final Discriminator KAMINO_LENDING_INIT_OBLIGATION_DISCRIMINATOR = toDiscriminator(219, 210, 134, 64, 155, 49, 137, 174);

  public static Instruction kaminoLendingInitObligation(final AccountMeta invokedGlamProgramMeta,
                                                        final SolanaAccounts solanaAccounts,
                                                        final PublicKey glamStateKey,
                                                        final PublicKey glamVaultKey,
                                                        final PublicKey glamSignerKey,
                                                        final PublicKey cpiProgramKey,
                                                        final PublicKey obligationOwnerKey,
                                                        final PublicKey feePayerKey,
                                                        final PublicKey obligationKey,
                                                        final PublicKey lendingMarketKey,
                                                        final PublicKey seed1AccountKey,
                                                        final PublicKey seed2AccountKey,
                                                        final PublicKey ownerUserMetadataKey,
                                                        final InitObligationArgs args) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createRead(obligationOwnerKey),
      createWritableSigner(feePayerKey),
      createWrite(obligationKey),
      createRead(lendingMarketKey),
      createRead(seed1AccountKey),
      createRead(seed2AccountKey),
      createRead(ownerUserMetadataKey),
      createRead(solanaAccounts.rentSysVar()),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(args)];
    int i = writeDiscriminator(KAMINO_LENDING_INIT_OBLIGATION_DISCRIMINATOR, _data, 0);
    Borsh.write(args, _data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record KaminoLendingInitObligationIxData(Discriminator discriminator, InitObligationArgs args) implements Borsh {  

    public static KaminoLendingInitObligationIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static KaminoLendingInitObligationIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var args = InitObligationArgs.read(_data, i);
      return new KaminoLendingInitObligationIxData(discriminator, args);
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

  public static final Discriminator KAMINO_LENDING_INIT_OBLIGATION_FARMS_FOR_RESERVE_DISCRIMINATOR = toDiscriminator(227, 61, 130, 2, 117, 226, 78, 1);

  public static Instruction kaminoLendingInitObligationFarmsForReserve(final AccountMeta invokedGlamProgramMeta,
                                                                       final SolanaAccounts solanaAccounts,
                                                                       final PublicKey glamStateKey,
                                                                       final PublicKey glamVaultKey,
                                                                       final PublicKey glamSignerKey,
                                                                       final PublicKey cpiProgramKey,
                                                                       final PublicKey payerKey,
                                                                       final PublicKey ownerKey,
                                                                       final PublicKey obligationKey,
                                                                       final PublicKey lendingMarketAuthorityKey,
                                                                       final PublicKey reserveKey,
                                                                       final PublicKey reserveFarmStateKey,
                                                                       final PublicKey obligationFarmKey,
                                                                       final PublicKey lendingMarketKey,
                                                                       final PublicKey farmsProgramKey,
                                                                       final int mode) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWritableSigner(payerKey),
      createRead(ownerKey),
      createWrite(obligationKey),
      createWrite(lendingMarketAuthorityKey),
      createWrite(reserveKey),
      createWrite(reserveFarmStateKey),
      createWrite(obligationFarmKey),
      createRead(lendingMarketKey),
      createRead(farmsProgramKey),
      createRead(solanaAccounts.rentSysVar()),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(KAMINO_LENDING_INIT_OBLIGATION_FARMS_FOR_RESERVE_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mode;

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record KaminoLendingInitObligationFarmsForReserveIxData(Discriminator discriminator, int mode) implements Borsh {  

    public static KaminoLendingInitObligationFarmsForReserveIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static KaminoLendingInitObligationFarmsForReserveIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mode = _data[i] & 0xFF;
      return new KaminoLendingInitObligationFarmsForReserveIxData(discriminator, mode);
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

  public static final Discriminator KAMINO_LENDING_INIT_USER_METADATA_DISCRIMINATOR = toDiscriminator(200, 95, 140, 132, 190, 65, 17, 161);

  public static Instruction kaminoLendingInitUserMetadata(final AccountMeta invokedGlamProgramMeta,
                                                          final SolanaAccounts solanaAccounts,
                                                          final PublicKey glamStateKey,
                                                          final PublicKey glamVaultKey,
                                                          final PublicKey glamSignerKey,
                                                          final PublicKey cpiProgramKey,
                                                          final PublicKey ownerKey,
                                                          final PublicKey feePayerKey,
                                                          final PublicKey userMetadataKey,
                                                          final PublicKey referrerUserMetadataKey,
                                                          final PublicKey userLookupTable) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createRead(ownerKey),
      createWritableSigner(feePayerKey),
      createWrite(userMetadataKey),
      createRead(referrerUserMetadataKey),
      createRead(solanaAccounts.rentSysVar()),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(KAMINO_LENDING_INIT_USER_METADATA_DISCRIMINATOR, _data, 0);
    userLookupTable.write(_data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record KaminoLendingInitUserMetadataIxData(Discriminator discriminator, PublicKey userLookupTable) implements Borsh {  

    public static KaminoLendingInitUserMetadataIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static KaminoLendingInitUserMetadataIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var userLookupTable = readPubKey(_data, i);
      return new KaminoLendingInitUserMetadataIxData(discriminator, userLookupTable);
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

  public static final Discriminator MARINADE_CLAIM_DISCRIMINATOR = toDiscriminator(54, 44, 48, 204, 218, 141, 36, 5);

  public static Instruction marinadeClaim(final AccountMeta invokedGlamProgramMeta,
                                          final SolanaAccounts solanaAccounts,
                                          final PublicKey glamStateKey,
                                          final PublicKey glamVaultKey,
                                          final PublicKey glamSignerKey,
                                          final PublicKey cpiProgramKey,
                                          final PublicKey stateKey,
                                          final PublicKey reservePdaKey,
                                          final PublicKey ticketAccountKey,
                                          final PublicKey clockKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(stateKey),
      createWrite(reservePdaKey),
      createWrite(ticketAccountKey),
      createRead(clockKey),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, MARINADE_CLAIM_DISCRIMINATOR);
  }

  public static final Discriminator MARINADE_DEPOSIT_DISCRIMINATOR = toDiscriminator(62, 236, 248, 28, 222, 232, 182, 73);

  public static Instruction marinadeDeposit(final AccountMeta invokedGlamProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamVaultKey,
                                            final PublicKey glamSignerKey,
                                            final PublicKey cpiProgramKey,
                                            final PublicKey stateKey,
                                            final PublicKey msolMintKey,
                                            final PublicKey liqPoolSolLegPdaKey,
                                            final PublicKey liqPoolMsolLegKey,
                                            final PublicKey liqPoolMsolLegAuthorityKey,
                                            final PublicKey reservePdaKey,
                                            final PublicKey mintToKey,
                                            final PublicKey msolMintAuthorityKey,
                                            final PublicKey tokenProgramKey,
                                            final long lamports) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(stateKey),
      createWrite(msolMintKey),
      createWrite(liqPoolSolLegPdaKey),
      createWrite(liqPoolMsolLegKey),
      createRead(liqPoolMsolLegAuthorityKey),
      createWrite(reservePdaKey),
      createWrite(mintToKey),
      createRead(msolMintAuthorityKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(MARINADE_DEPOSIT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record MarinadeDepositIxData(Discriminator discriminator, long lamports) implements Borsh {  

    public static MarinadeDepositIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static MarinadeDepositIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      return new MarinadeDepositIxData(discriminator, lamports);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lamports);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator MARINADE_DEPOSIT_STAKE_ACCOUNT_DISCRIMINATOR = toDiscriminator(141, 230, 58, 103, 56, 205, 159, 138);

  public static Instruction marinadeDepositStakeAccount(final AccountMeta invokedGlamProgramMeta,
                                                        final SolanaAccounts solanaAccounts,
                                                        final PublicKey glamStateKey,
                                                        final PublicKey glamVaultKey,
                                                        final PublicKey glamSignerKey,
                                                        final PublicKey cpiProgramKey,
                                                        final PublicKey stateKey,
                                                        final PublicKey validatorListKey,
                                                        final PublicKey stakeListKey,
                                                        final PublicKey stakeAccountKey,
                                                        final PublicKey duplicationFlagKey,
                                                        final PublicKey msolMintKey,
                                                        final PublicKey mintToKey,
                                                        final PublicKey msolMintAuthorityKey,
                                                        final PublicKey clockKey,
                                                        final PublicKey tokenProgramKey,
                                                        final PublicKey stakeProgramKey,
                                                        final int validatorIdx) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(stateKey),
      createWrite(validatorListKey),
      createWrite(stakeListKey),
      createWrite(stakeAccountKey),
      createWrite(duplicationFlagKey),
      createWrite(msolMintKey),
      createWrite(mintToKey),
      createRead(msolMintAuthorityKey),
      createRead(clockKey),
      createRead(solanaAccounts.rentSysVar()),
      createRead(solanaAccounts.systemProgram()),
      createRead(tokenProgramKey),
      createRead(stakeProgramKey)
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(MARINADE_DEPOSIT_STAKE_ACCOUNT_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, validatorIdx);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record MarinadeDepositStakeAccountIxData(Discriminator discriminator, int validatorIdx) implements Borsh {  

    public static MarinadeDepositStakeAccountIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 12;

    public static MarinadeDepositStakeAccountIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var validatorIdx = getInt32LE(_data, i);
      return new MarinadeDepositStakeAccountIxData(discriminator, validatorIdx);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt32LE(_data, i, validatorIdx);
      i += 4;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator MARINADE_LIQUID_UNSTAKE_DISCRIMINATOR = toDiscriminator(29, 146, 34, 21, 26, 68, 141, 161);

  public static Instruction marinadeLiquidUnstake(final AccountMeta invokedGlamProgramMeta,
                                                  final SolanaAccounts solanaAccounts,
                                                  final PublicKey glamStateKey,
                                                  final PublicKey glamVaultKey,
                                                  final PublicKey glamSignerKey,
                                                  final PublicKey cpiProgramKey,
                                                  final PublicKey stateKey,
                                                  final PublicKey msolMintKey,
                                                  final PublicKey liqPoolSolLegPdaKey,
                                                  final PublicKey liqPoolMsolLegKey,
                                                  final PublicKey treasuryMsolAccountKey,
                                                  final PublicKey getMsolFromKey,
                                                  final PublicKey tokenProgramKey,
                                                  final long msolAmount) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(stateKey),
      createWrite(msolMintKey),
      createWrite(liqPoolSolLegPdaKey),
      createWrite(liqPoolMsolLegKey),
      createWrite(treasuryMsolAccountKey),
      createWrite(getMsolFromKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(MARINADE_LIQUID_UNSTAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, msolAmount);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record MarinadeLiquidUnstakeIxData(Discriminator discriminator, long msolAmount) implements Borsh {  

    public static MarinadeLiquidUnstakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static MarinadeLiquidUnstakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var msolAmount = getInt64LE(_data, i);
      return new MarinadeLiquidUnstakeIxData(discriminator, msolAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, msolAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator MARINADE_ORDER_UNSTAKE_DISCRIMINATOR = toDiscriminator(202, 3, 33, 27, 183, 156, 57, 231);

  public static Instruction marinadeOrderUnstake(final AccountMeta invokedGlamProgramMeta,
                                                 final SolanaAccounts solanaAccounts,
                                                 final PublicKey glamStateKey,
                                                 final PublicKey glamVaultKey,
                                                 final PublicKey glamSignerKey,
                                                 final PublicKey cpiProgramKey,
                                                 final PublicKey stateKey,
                                                 final PublicKey msolMintKey,
                                                 final PublicKey burnMsolFromKey,
                                                 final PublicKey newTicketAccountKey,
                                                 final PublicKey clockKey,
                                                 final PublicKey tokenProgramKey,
                                                 final long msolAmount) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(stateKey),
      createWrite(msolMintKey),
      createWrite(burnMsolFromKey),
      createWrite(newTicketAccountKey),
      createRead(clockKey),
      createRead(solanaAccounts.rentSysVar()),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(MARINADE_ORDER_UNSTAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, msolAmount);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record MarinadeOrderUnstakeIxData(Discriminator discriminator, long msolAmount) implements Borsh {  

    public static MarinadeOrderUnstakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static MarinadeOrderUnstakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var msolAmount = getInt64LE(_data, i);
      return new MarinadeOrderUnstakeIxData(discriminator, msolAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, msolAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator MERGE_STAKE_ACCOUNTS_DISCRIMINATOR = toDiscriminator(173, 206, 10, 246, 109, 50, 244, 110);

  public static Instruction mergeStakeAccounts(final AccountMeta invokedGlamProgramMeta,
                                               final SolanaAccounts solanaAccounts,
                                               final PublicKey glamStateKey,
                                               final PublicKey glamVaultKey,
                                               final PublicKey glamSignerKey,
                                               final PublicKey toStakeKey,
                                               final PublicKey fromStakeKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createWrite(toStakeKey),
      createWrite(fromStakeKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar()),
      createRead(solanaAccounts.stakeProgram()),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, MERGE_STAKE_ACCOUNTS_DISCRIMINATOR);
  }

  public static final Discriminator METEORA_DLMM_ADD_LIQUIDITY_BY_STRATEGY_DISCRIMINATOR = toDiscriminator(81, 139, 59, 146, 176, 196, 240, 216);

  public static Instruction meteoraDlmmAddLiquidityByStrategy(final AccountMeta invokedGlamProgramMeta,
                                                              final PublicKey glamStateKey,
                                                              final PublicKey glamVaultKey,
                                                              final PublicKey glamSignerKey,
                                                              final PublicKey cpiProgramKey,
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
                                                              final PublicKey tokenXProgramKey,
                                                              final PublicKey tokenYProgramKey,
                                                              final PublicKey eventAuthorityKey,
                                                              final PublicKey programKey,
                                                              final LiquidityParameterByStrategy params) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
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
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(METEORA_DLMM_ADD_LIQUIDITY_BY_STRATEGY_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record MeteoraDlmmAddLiquidityByStrategyIxData(Discriminator discriminator, LiquidityParameterByStrategy params) implements Borsh {  

    public static MeteoraDlmmAddLiquidityByStrategyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 105;

    public static MeteoraDlmmAddLiquidityByStrategyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = LiquidityParameterByStrategy.read(_data, i);
      return new MeteoraDlmmAddLiquidityByStrategyIxData(discriminator, params);
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

  public static final Discriminator METEORA_DLMM_CLAIM_FEE_DISCRIMINATOR = toDiscriminator(78, 116, 98, 78, 50, 82, 72, 37);

  public static Instruction meteoraDlmmClaimFee(final AccountMeta invokedGlamProgramMeta,
                                                final PublicKey glamStateKey,
                                                final PublicKey glamVaultKey,
                                                final PublicKey glamSignerKey,
                                                final PublicKey cpiProgramKey,
                                                final PublicKey lbPairKey,
                                                final PublicKey positionKey,
                                                final PublicKey binArrayLowerKey,
                                                final PublicKey binArrayUpperKey,
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
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(lbPairKey),
      createWrite(positionKey),
      createWrite(binArrayLowerKey),
      createWrite(binArrayUpperKey),
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

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, METEORA_DLMM_CLAIM_FEE_DISCRIMINATOR);
  }

  public static final Discriminator METEORA_DLMM_CLOSE_POSITION_DISCRIMINATOR = toDiscriminator(186, 117, 42, 24, 221, 194, 34, 143);

  public static Instruction meteoraDlmmClosePosition(final AccountMeta invokedGlamProgramMeta,
                                                     final PublicKey glamStateKey,
                                                     final PublicKey glamVaultKey,
                                                     final PublicKey glamSignerKey,
                                                     final PublicKey cpiProgramKey,
                                                     final PublicKey positionKey,
                                                     final PublicKey lbPairKey,
                                                     final PublicKey binArrayLowerKey,
                                                     final PublicKey binArrayUpperKey,
                                                     final PublicKey eventAuthorityKey,
                                                     final PublicKey programKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(binArrayLowerKey),
      createWrite(binArrayUpperKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, METEORA_DLMM_CLOSE_POSITION_DISCRIMINATOR);
  }

  public static final Discriminator METEORA_DLMM_INITIALIZE_POSITION_DISCRIMINATOR = toDiscriminator(223, 94, 215, 96, 175, 181, 195, 204);

  public static Instruction meteoraDlmmInitializePosition(final AccountMeta invokedGlamProgramMeta,
                                                          final SolanaAccounts solanaAccounts,
                                                          final PublicKey glamStateKey,
                                                          final PublicKey glamVaultKey,
                                                          final PublicKey glamSignerKey,
                                                          final PublicKey cpiProgramKey,
                                                          final PublicKey payerKey,
                                                          final PublicKey positionKey,
                                                          final PublicKey lbPairKey,
                                                          final PublicKey eventAuthorityKey,
                                                          final PublicKey programKey,
                                                          final int lowerBinId,
                                                          final int width) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWritableSigner(payerKey),
      createWritableSigner(positionKey),
      createRead(lbPairKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.rentSysVar()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(METEORA_DLMM_INITIALIZE_POSITION_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, lowerBinId);
    i += 4;
    putInt32LE(_data, i, width);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record MeteoraDlmmInitializePositionIxData(Discriminator discriminator, int lowerBinId, int width) implements Borsh {  

    public static MeteoraDlmmInitializePositionIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static MeteoraDlmmInitializePositionIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lowerBinId = getInt32LE(_data, i);
      i += 4;
      final var width = getInt32LE(_data, i);
      return new MeteoraDlmmInitializePositionIxData(discriminator, lowerBinId, width);
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

  public static final Discriminator METEORA_DLMM_REMOVE_LIQUIDITY_BY_RANGE_DISCRIMINATOR = toDiscriminator(223, 12, 177, 181, 96, 109, 60, 124);

  public static Instruction meteoraDlmmRemoveLiquidityByRange(final AccountMeta invokedGlamProgramMeta,
                                                              final PublicKey glamStateKey,
                                                              final PublicKey glamVaultKey,
                                                              final PublicKey glamSignerKey,
                                                              final PublicKey cpiProgramKey,
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
                                                              final PublicKey tokenXProgramKey,
                                                              final PublicKey tokenYProgramKey,
                                                              final PublicKey eventAuthorityKey,
                                                              final PublicKey programKey,
                                                              final int fromBinId,
                                                              final int toBinId,
                                                              final int bpsToRemove) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
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
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[18];
    int i = writeDiscriminator(METEORA_DLMM_REMOVE_LIQUIDITY_BY_RANGE_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, fromBinId);
    i += 4;
    putInt32LE(_data, i, toBinId);
    i += 4;
    putInt16LE(_data, i, bpsToRemove);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record MeteoraDlmmRemoveLiquidityByRangeIxData(Discriminator discriminator,
                                                        int fromBinId,
                                                        int toBinId,
                                                        int bpsToRemove) implements Borsh {  

    public static MeteoraDlmmRemoveLiquidityByRangeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 18;

    public static MeteoraDlmmRemoveLiquidityByRangeIxData read(final byte[] _data, final int offset) {
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
      return new MeteoraDlmmRemoveLiquidityByRangeIxData(discriminator, fromBinId, toBinId, bpsToRemove);
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

  public static final Discriminator METEORA_DLMM_SWAP_DISCRIMINATOR = toDiscriminator(127, 64, 37, 138, 173, 243, 207, 84);

  public static Instruction meteoraDlmmSwap(final AccountMeta invokedGlamProgramMeta,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamVaultKey,
                                            final PublicKey glamSignerKey,
                                            final PublicKey cpiProgramKey,
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
                                            final PublicKey tokenXProgramKey,
                                            final PublicKey tokenYProgramKey,
                                            final PublicKey eventAuthorityKey,
                                            final PublicKey programKey,
                                            final long amountIn,
                                            final long minAmountOut) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
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
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(METEORA_DLMM_SWAP_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountIn);
    i += 8;
    putInt64LE(_data, i, minAmountOut);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record MeteoraDlmmSwapIxData(Discriminator discriminator, long amountIn, long minAmountOut) implements Borsh {  

    public static MeteoraDlmmSwapIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static MeteoraDlmmSwapIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amountIn = getInt64LE(_data, i);
      i += 8;
      final var minAmountOut = getInt64LE(_data, i);
      return new MeteoraDlmmSwapIxData(discriminator, amountIn, minAmountOut);
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

  public static final Discriminator MINT_TOKENS_DISCRIMINATOR = toDiscriminator(59, 132, 24, 246, 122, 39, 8, 243);

  public static Instruction mintTokens(final AccountMeta invokedGlamProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey glamStateKey,
                                       final PublicKey glamSignerKey,
                                       final PublicKey glamMintKey,
                                       final PublicKey mintToKey,
                                       final PublicKey recipientKey,
                                       final int mintId,
                                       final long amount) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createWrite(mintToKey),
      createRead(recipientKey),
      createRead(solanaAccounts.token2022Program())
    );

    final byte[] _data = new byte[17];
    int i = writeDiscriminator(MINT_TOKENS_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mintId;
    ++i;
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record MintTokensIxData(Discriminator discriminator, int mintId, long amount) implements Borsh {  

    public static MintTokensIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static MintTokensIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintId = _data[i] & 0xFF;
      ++i;
      final var amount = getInt64LE(_data, i);
      return new MintTokensIxData(discriminator, mintId, amount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) mintId;
      ++i;
      putInt64LE(_data, i, amount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REDEEM_DISCRIMINATOR = toDiscriminator(184, 12, 86, 149, 70, 196, 97, 225);

  public static Instruction redeem(final AccountMeta invokedGlamProgramMeta,
                                   final SolanaAccounts solanaAccounts,
                                   final PublicKey glamStateKey,
                                   final PublicKey glamVaultKey,
                                   final PublicKey glamMintKey,
                                   final PublicKey signerKey,
                                   final PublicKey signerShareAtaKey,
                                   final PublicKey signerPolicyKey,
                                   final long amount,
                                   final boolean inKind,
                                   final boolean skipState) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWrite(glamMintKey),
      createWritableSigner(signerKey),
      createWrite(signerShareAtaKey),
      createWrite(requireNonNullElse(signerPolicyKey, invokedGlamProgramMeta.publicKey())),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.token2022Program())
    );

    final byte[] _data = new byte[18];
    int i = writeDiscriminator(REDEEM_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) (inKind ? 1 : 0);
    ++i;
    _data[i] = (byte) (skipState ? 1 : 0);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record RedeemIxData(Discriminator discriminator,
                             long amount,
                             boolean inKind,
                             boolean skipState) implements Borsh {  

    public static RedeemIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 18;

    public static RedeemIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var inKind = _data[i] == 1;
      ++i;
      final var skipState = _data[i] == 1;
      return new RedeemIxData(discriminator, amount, inKind, skipState);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      _data[i] = (byte) (inKind ? 1 : 0);
      ++i;
      _data[i] = (byte) (skipState ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REDELEGATE_STAKE_DISCRIMINATOR = toDiscriminator(240, 90, 140, 104, 96, 8, 134, 31);

  public static Instruction redelegateStake(final AccountMeta invokedGlamProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamVaultKey,
                                            final PublicKey glamSignerKey,
                                            final PublicKey existingStakeKey,
                                            final PublicKey newStakeKey,
                                            final PublicKey voteKey,
                                            final PublicKey stakeConfigKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createWrite(existingStakeKey),
      createWrite(newStakeKey),
      createRead(voteKey),
      createRead(stakeConfigKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeProgram()),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, REDELEGATE_STAKE_DISCRIMINATOR);
  }

  public static final Discriminator SET_SUBSCRIBE_REDEEM_ENABLED_DISCRIMINATOR = toDiscriminator(189, 56, 205, 172, 201, 185, 34, 92);

  public static Instruction setSubscribeRedeemEnabled(final AccountMeta invokedGlamProgramMeta,
                                                      final PublicKey glamStateKey,
                                                      final PublicKey glamSignerKey,
                                                      final boolean enabled) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(SET_SUBSCRIBE_REDEEM_ENABLED_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) (enabled ? 1 : 0);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record SetSubscribeRedeemEnabledIxData(Discriminator discriminator, boolean enabled) implements Borsh {  

    public static SetSubscribeRedeemEnabledIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static SetSubscribeRedeemEnabledIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var enabled = _data[i] == 1;
      return new SetSubscribeRedeemEnabledIxData(discriminator, enabled);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) (enabled ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_TOKEN_ACCOUNTS_STATES_DISCRIMINATOR = toDiscriminator(50, 133, 45, 86, 117, 66, 115, 195);

  public static Instruction setTokenAccountsStates(final AccountMeta invokedGlamProgramMeta,
                                                   final SolanaAccounts solanaAccounts,
                                                   final PublicKey glamStateKey,
                                                   final PublicKey glamSignerKey,
                                                   final PublicKey glamMintKey,
                                                   final int mintId,
                                                   final boolean frozen) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createRead(solanaAccounts.token2022Program())
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(SET_TOKEN_ACCOUNTS_STATES_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mintId;
    ++i;
    _data[i] = (byte) (frozen ? 1 : 0);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record SetTokenAccountsStatesIxData(Discriminator discriminator, int mintId, boolean frozen) implements Borsh {  

    public static SetTokenAccountsStatesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static SetTokenAccountsStatesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintId = _data[i] & 0xFF;
      ++i;
      final var frozen = _data[i] == 1;
      return new SetTokenAccountsStatesIxData(discriminator, mintId, frozen);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) mintId;
      ++i;
      _data[i] = (byte) (frozen ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SPLIT_STAKE_ACCOUNT_DISCRIMINATOR = toDiscriminator(130, 42, 33, 89, 117, 77, 105, 194);

  public static Instruction splitStakeAccount(final AccountMeta invokedGlamProgramMeta,
                                              final SolanaAccounts solanaAccounts,
                                              final PublicKey glamStateKey,
                                              final PublicKey glamVaultKey,
                                              final PublicKey glamSignerKey,
                                              final PublicKey existingStakeKey,
                                              final PublicKey newStakeKey,
                                              final long lamports) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createWrite(existingStakeKey),
      createWrite(newStakeKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeProgram()),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(SPLIT_STAKE_ACCOUNT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record SplitStakeAccountIxData(Discriminator discriminator, long lamports) implements Borsh {  

    public static SplitStakeAccountIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SplitStakeAccountIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      return new SplitStakeAccountIxData(discriminator, lamports);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lamports);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator STAKE_POOL_DEPOSIT_SOL_DISCRIMINATOR = toDiscriminator(147, 187, 91, 151, 158, 187, 247, 79);

  public static Instruction stakePoolDepositSol(final AccountMeta invokedGlamProgramMeta,
                                                final SolanaAccounts solanaAccounts,
                                                final PublicKey glamStateKey,
                                                final PublicKey glamVaultKey,
                                                final PublicKey glamSignerKey,
                                                final PublicKey stakePoolKey,
                                                final PublicKey withdrawAuthorityKey,
                                                final PublicKey reserveStakeKey,
                                                final PublicKey poolMintKey,
                                                final PublicKey feeAccountKey,
                                                final PublicKey mintToKey,
                                                final PublicKey stakePoolProgramKey,
                                                final PublicKey tokenProgramKey,
                                                final long lamports) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createWrite(stakePoolKey),
      createRead(withdrawAuthorityKey),
      createWrite(reserveStakeKey),
      createWrite(poolMintKey),
      createWrite(feeAccountKey),
      createWrite(mintToKey),
      createRead(stakePoolProgramKey),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(solanaAccounts.systemProgram()),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(STAKE_POOL_DEPOSIT_SOL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record StakePoolDepositSolIxData(Discriminator discriminator, long lamports) implements Borsh {  

    public static StakePoolDepositSolIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static StakePoolDepositSolIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      return new StakePoolDepositSolIxData(discriminator, lamports);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lamports);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator STAKE_POOL_DEPOSIT_STAKE_DISCRIMINATOR = toDiscriminator(212, 158, 195, 174, 179, 105, 9, 97);

  public static Instruction stakePoolDepositStake(final AccountMeta invokedGlamProgramMeta,
                                                  final SolanaAccounts solanaAccounts,
                                                  final PublicKey glamStateKey,
                                                  final PublicKey glamVaultKey,
                                                  final PublicKey glamSignerKey,
                                                  final PublicKey vaultStakeAccountKey,
                                                  final PublicKey mintToKey,
                                                  final PublicKey poolMintKey,
                                                  final PublicKey feeAccountKey,
                                                  final PublicKey stakePoolKey,
                                                  final PublicKey depositAuthorityKey,
                                                  final PublicKey withdrawAuthorityKey,
                                                  final PublicKey validatorListKey,
                                                  final PublicKey validatorStakeAccountKey,
                                                  final PublicKey reserveStakeAccountKey,
                                                  final PublicKey stakePoolProgramKey,
                                                  final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createWrite(vaultStakeAccountKey),
      createWrite(mintToKey),
      createWrite(poolMintKey),
      createWrite(feeAccountKey),
      createWrite(stakePoolKey),
      createRead(depositAuthorityKey),
      createRead(withdrawAuthorityKey),
      createWrite(validatorListKey),
      createWrite(validatorStakeAccountKey),
      createWrite(reserveStakeAccountKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar()),
      createRead(stakePoolProgramKey),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(solanaAccounts.systemProgram()),
      createRead(tokenProgramKey),
      createRead(solanaAccounts.stakeProgram())
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, STAKE_POOL_DEPOSIT_STAKE_DISCRIMINATOR);
  }

  public static final Discriminator STAKE_POOL_WITHDRAW_SOL_DISCRIMINATOR = toDiscriminator(179, 100, 204, 0, 192, 46, 233, 181);

  public static Instruction stakePoolWithdrawSol(final AccountMeta invokedGlamProgramMeta,
                                                 final SolanaAccounts solanaAccounts,
                                                 final PublicKey glamStateKey,
                                                 final PublicKey glamVaultKey,
                                                 final PublicKey glamSignerKey,
                                                 final PublicKey stakePoolKey,
                                                 final PublicKey withdrawAuthorityKey,
                                                 final PublicKey reserveStakeKey,
                                                 final PublicKey poolMintKey,
                                                 final PublicKey feeAccountKey,
                                                 final PublicKey poolTokenAtaKey,
                                                 final PublicKey stakePoolProgramKey,
                                                 final PublicKey tokenProgramKey,
                                                 final long poolTokenAmount) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createWrite(stakePoolKey),
      createRead(withdrawAuthorityKey),
      createWrite(reserveStakeKey),
      createWrite(poolMintKey),
      createWrite(feeAccountKey),
      createWrite(poolTokenAtaKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar()),
      createRead(stakePoolProgramKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(tokenProgramKey),
      createRead(solanaAccounts.stakeProgram())
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(STAKE_POOL_WITHDRAW_SOL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, poolTokenAmount);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record StakePoolWithdrawSolIxData(Discriminator discriminator, long poolTokenAmount) implements Borsh {  

    public static StakePoolWithdrawSolIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static StakePoolWithdrawSolIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var poolTokenAmount = getInt64LE(_data, i);
      return new StakePoolWithdrawSolIxData(discriminator, poolTokenAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, poolTokenAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator STAKE_POOL_WITHDRAW_STAKE_DISCRIMINATOR = toDiscriminator(7, 70, 250, 22, 49, 1, 143, 1);

  public static Instruction stakePoolWithdrawStake(final AccountMeta invokedGlamProgramMeta,
                                                   final SolanaAccounts solanaAccounts,
                                                   final PublicKey glamStateKey,
                                                   final PublicKey glamVaultKey,
                                                   final PublicKey glamSignerKey,
                                                   final PublicKey vaultStakeAccountKey,
                                                   final PublicKey poolMintKey,
                                                   final PublicKey feeAccountKey,
                                                   final PublicKey stakePoolKey,
                                                   final PublicKey withdrawAuthorityKey,
                                                   final PublicKey validatorListKey,
                                                   final PublicKey validatorStakeAccountKey,
                                                   final PublicKey poolTokenAtaKey,
                                                   final PublicKey stakePoolProgramKey,
                                                   final PublicKey tokenProgramKey,
                                                   final long poolTokenAmount) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createWrite(vaultStakeAccountKey),
      createWrite(poolMintKey),
      createWrite(feeAccountKey),
      createWrite(stakePoolKey),
      createRead(withdrawAuthorityKey),
      createWrite(validatorListKey),
      createWrite(validatorStakeAccountKey),
      createWrite(poolTokenAtaKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(stakePoolProgramKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(tokenProgramKey),
      createRead(solanaAccounts.stakeProgram())
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(STAKE_POOL_WITHDRAW_STAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, poolTokenAmount);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record StakePoolWithdrawStakeIxData(Discriminator discriminator, long poolTokenAmount) implements Borsh {  

    public static StakePoolWithdrawStakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static StakePoolWithdrawStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var poolTokenAmount = getInt64LE(_data, i);
      return new StakePoolWithdrawStakeIxData(discriminator, poolTokenAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, poolTokenAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SUBSCRIBE_DISCRIMINATOR = toDiscriminator(254, 28, 191, 138, 156, 179, 183, 53);

  public static Instruction subscribe(final AccountMeta invokedGlamProgramMeta,
                                      final SolanaAccounts solanaAccounts,
                                      final PublicKey glamStateKey,
                                      final PublicKey glamVaultKey,
                                      final PublicKey glamMintKey,
                                      final PublicKey signerShareAtaKey,
                                      final PublicKey assetKey,
                                      final PublicKey vaultAtaKey,
                                      final PublicKey signerAssetAtaKey,
                                      final PublicKey signerPolicyKey,
                                      final PublicKey signerKey,
                                      final int mintId,
                                      final long amount,
                                      final boolean skipState) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWrite(glamMintKey),
      createWrite(signerShareAtaKey),
      createRead(assetKey),
      createWrite(vaultAtaKey),
      createWrite(signerAssetAtaKey),
      createWrite(requireNonNullElse(signerPolicyKey, invokedGlamProgramMeta.publicKey())),
      createWritableSigner(signerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.token2022Program())
    );

    final byte[] _data = new byte[18];
    int i = writeDiscriminator(SUBSCRIBE_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mintId;
    ++i;
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) (skipState ? 1 : 0);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record SubscribeIxData(Discriminator discriminator,
                                int mintId,
                                long amount,
                                boolean skipState) implements Borsh {  

    public static SubscribeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 18;

    public static SubscribeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintId = _data[i] & 0xFF;
      ++i;
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var skipState = _data[i] == 1;
      return new SubscribeIxData(discriminator, mintId, amount, skipState);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) mintId;
      ++i;
      putInt64LE(_data, i, amount);
      i += 8;
      _data[i] = (byte) (skipState ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SYSTEM_TRANSFER_DISCRIMINATOR = toDiscriminator(167, 164, 195, 155, 219, 152, 191, 230);

  public static Instruction systemTransfer(final AccountMeta invokedGlamProgramMeta,
                                           final SolanaAccounts solanaAccounts,
                                           final PublicKey glamStateKey,
                                           final PublicKey glamVaultKey,
                                           final PublicKey glamSignerKey,
                                           final PublicKey toKey,
                                           final long lamports) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(toKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.associatedTokenAccountProgram())
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(SYSTEM_TRANSFER_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record SystemTransferIxData(Discriminator discriminator, long lamports) implements Borsh {  

    public static SystemTransferIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SystemTransferIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      return new SystemTransferIxData(discriminator, lamports);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lamports);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator TRANSFER_HOOK_DISCRIMINATOR = toDiscriminator(105, 37, 101, 197, 75, 251, 102, 26);

  public static Instruction transferHook(final AccountMeta invokedGlamProgramMeta,
                                         final PublicKey srcAccountKey,
                                         final PublicKey mintKey,
                                         final PublicKey dstAccountKey,
                                         final PublicKey ownerKey,
                                         final PublicKey extraAccountMetaListKey,
                                         final PublicKey stateKey,
                                         final PublicKey srcAccountPolicyKey,
                                         final PublicKey dstAccountPolicyKey,
                                         final long amount) {
    final var keys = List.of(
      createRead(srcAccountKey),
      createRead(mintKey),
      createRead(dstAccountKey),
      createRead(ownerKey),
      createRead(extraAccountMetaListKey),
      createRead(stateKey),
      createRead(srcAccountPolicyKey),
      createRead(dstAccountPolicyKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(TRANSFER_HOOK_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record TransferHookIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static TransferHookIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static TransferHookIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new TransferHookIxData(discriminator, amount);
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

  public static final Discriminator UPDATE_MINT_DISCRIMINATOR = toDiscriminator(212, 203, 57, 78, 75, 245, 222, 5);

  public static Instruction updateMint(final AccountMeta invokedGlamProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey glamStateKey,
                                       final PublicKey glamSignerKey,
                                       final PublicKey glamMintKey,
                                       final int mintId,
                                       final MintModel mintModel) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createRead(solanaAccounts.token2022Program())
    );

    final byte[] _data = new byte[9 + Borsh.len(mintModel)];
    int i = writeDiscriminator(UPDATE_MINT_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mintId;
    ++i;
    Borsh.write(mintModel, _data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record UpdateMintIxData(Discriminator discriminator, int mintId, MintModel mintModel) implements Borsh {  

    public static UpdateMintIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateMintIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintId = _data[i] & 0xFF;
      ++i;
      final var mintModel = MintModel.read(_data, i);
      return new UpdateMintIxData(discriminator, mintId, mintModel);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) mintId;
      ++i;
      i += Borsh.write(mintModel, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 1 + Borsh.len(mintModel);
    }
  }

  public static final Discriminator UPDATE_STATE_DISCRIMINATOR = toDiscriminator(135, 112, 215, 75, 247, 185, 53, 176);

  public static Instruction updateState(final AccountMeta invokedGlamProgramMeta,
                                        final PublicKey glamStateKey,
                                        final PublicKey glamSignerKey,
                                        final StateModel state) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(state)];
    int i = writeDiscriminator(UPDATE_STATE_DISCRIMINATOR, _data, 0);
    Borsh.write(state, _data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record UpdateStateIxData(Discriminator discriminator, StateModel state) implements Borsh {  

    public static UpdateStateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateStateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var state = StateModel.read(_data, i);
      return new UpdateStateIxData(discriminator, state);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(state, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(state);
    }
  }

  public static final Discriminator WITHDRAW_DISCRIMINATOR = toDiscriminator(183, 18, 70, 156, 148, 109, 161, 34);

  public static Instruction withdraw(final AccountMeta invokedGlamProgramMeta,
                                     final PublicKey glamStateKey,
                                     final PublicKey glamVaultKey,
                                     final PublicKey glamSignerKey,
                                     final PublicKey assetKey,
                                     final PublicKey vaultAtaKey,
                                     final PublicKey signerAtaKey,
                                     final PublicKey tokenProgramKey,
                                     final long amount) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(assetKey),
      createWrite(vaultAtaKey),
      createWrite(signerAtaKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(WITHDRAW_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
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

  public static final Discriminator WITHDRAW_FROM_STAKE_ACCOUNTS_DISCRIMINATOR = toDiscriminator(93, 209, 100, 231, 169, 160, 192, 197);

  public static Instruction withdrawFromStakeAccounts(final AccountMeta invokedGlamProgramMeta,
                                                      final SolanaAccounts solanaAccounts,
                                                      final PublicKey glamStateKey,
                                                      final PublicKey glamVaultKey,
                                                      final PublicKey glamSignerKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar()),
      createRead(solanaAccounts.stakeProgram())
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, WITHDRAW_FROM_STAKE_ACCOUNTS_DISCRIMINATOR);
  }

  public static final Discriminator WSOL_UNWRAP_DISCRIMINATOR = toDiscriminator(123, 189, 16, 96, 233, 186, 54, 215);

  public static Instruction wsolUnwrap(final AccountMeta invokedGlamProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey glamStateKey,
                                       final PublicKey glamVaultKey,
                                       final PublicKey glamSignerKey,
                                       final PublicKey vaultWsolAtaKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createWrite(vaultWsolAtaKey),
      createRead(solanaAccounts.tokenProgram())
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, WSOL_UNWRAP_DISCRIMINATOR);
  }

  public static final Discriminator WSOL_WRAP_DISCRIMINATOR = toDiscriminator(26, 2, 139, 159, 239, 195, 193, 9);

  public static Instruction wsolWrap(final AccountMeta invokedGlamProgramMeta,
                                     final SolanaAccounts solanaAccounts,
                                     final PublicKey glamStateKey,
                                     final PublicKey glamVaultKey,
                                     final PublicKey glamSignerKey,
                                     final PublicKey vaultWsolAtaKey,
                                     final PublicKey wsolMintKey,
                                     final long lamports) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createWrite(vaultWsolAtaKey),
      createRead(wsolMintKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.associatedTokenAccountProgram())
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(WSOL_WRAP_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record WsolWrapIxData(Discriminator discriminator, long lamports) implements Borsh {  

    public static WsolWrapIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static WsolWrapIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      return new WsolWrapIxData(discriminator, lamports);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lamports);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  private GlamProgram() {
  }
}
