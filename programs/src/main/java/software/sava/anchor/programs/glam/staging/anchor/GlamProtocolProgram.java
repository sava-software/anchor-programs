package software.sava.anchor.programs.glam.staging.anchor;

import java.lang.String;

import java.util.List;
import java.util.OptionalInt;

import software.sava.anchor.programs.glam.staging.anchor.types.AddLiquiditySingleSidePreciseParameter;
import software.sava.anchor.programs.glam.staging.anchor.types.BinLiquidityReduction;
import software.sava.anchor.programs.glam.staging.anchor.types.InitObligationArgs;
import software.sava.anchor.programs.glam.staging.anchor.types.LiquidityParameter;
import software.sava.anchor.programs.glam.staging.anchor.types.LiquidityParameterByStrategy;
import software.sava.anchor.programs.glam.staging.anchor.types.MarketType;
import software.sava.anchor.programs.glam.staging.anchor.types.MintModel;
import software.sava.anchor.programs.glam.staging.anchor.types.ModifyOrderParams;
import software.sava.anchor.programs.glam.staging.anchor.types.OrderParams;
import software.sava.anchor.programs.glam.staging.anchor.types.PositionDirection;
import software.sava.anchor.programs.glam.staging.anchor.types.StateModel;
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

public final class GlamProtocolProgram {

  public static final Discriminator ADD_MINT_DISCRIMINATOR = toDiscriminator(171, 222, 111, 37, 60, 166, 208, 108);

  public static Instruction addMint(final AccountMeta invokedGlamProtocolProgramMeta,
                                    final SolanaAccounts solanaAccounts,
                                    final PublicKey glamStateKey,
                                    final PublicKey glamSignerKey,
                                    final PublicKey newMintKey,
                                    final PublicKey extraMetasAccountKey,
                                    final PublicKey openfundsMetadataKey,
                                    final PublicKey policiesProgramKey,
                                    final MintModel mintModel) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(newMintKey),
      createWrite(requireNonNullElse(extraMetasAccountKey, invokedGlamProtocolProgramMeta.publicKey())),
      createWrite(requireNonNullElse(openfundsMetadataKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.token2022Program()),
      createRead(policiesProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(mintModel)];
    int i = writeDiscriminator(ADD_MINT_DISCRIMINATOR, _data, 0);
    Borsh.write(mintModel, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction burnTokens(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static final Discriminator CLAIM_DISCRIMINATOR = toDiscriminator(62, 198, 214, 193, 213, 159, 108, 210);

  public static Instruction claim(final AccountMeta invokedGlamProtocolProgramMeta,
                                  final SolanaAccounts solanaAccounts,
                                  final PublicKey glamStateKey,
                                  final PublicKey glamEscrowKey,
                                  final PublicKey signerKey,
                                  final PublicKey assetKey,
                                  final PublicKey signerAssetAtaKey,
                                  final PublicKey escrowAssetAtaKey,
                                  final PublicKey tokenProgramKey,
                                  final int mintId) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamEscrowKey),
      createWritableSigner(signerKey),
      createRead(assetKey),
      createWrite(signerAssetAtaKey),
      createWrite(escrowAssetAtaKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(CLAIM_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mintId;

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record ClaimIxData(Discriminator discriminator, int mintId) implements Borsh {  

    public static ClaimIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static ClaimIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintId = _data[i] & 0xFF;
      return new ClaimIxData(discriminator, mintId);
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

  public static final Discriminator CLOSE_MINT_DISCRIMINATOR = toDiscriminator(149, 251, 157, 212, 65, 181, 235, 129);

  public static Instruction closeMint(final AccountMeta invokedGlamProtocolProgramMeta,
                                      final SolanaAccounts solanaAccounts,
                                      final PublicKey glamStateKey,
                                      final PublicKey glamVaultKey,
                                      final PublicKey glamSignerKey,
                                      final PublicKey glamMintKey,
                                      final PublicKey extraMetasAccountKey,
                                      final PublicKey metadataKey,
                                      final PublicKey policiesProgramKey,
                                      final int mintId) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createWrite(requireNonNullElse(extraMetasAccountKey, invokedGlamProtocolProgramMeta.publicKey())),
      createWrite(metadataKey),
      createRead(policiesProgramKey),
      createRead(solanaAccounts.token2022Program()),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(CLOSE_MINT_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mintId;

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction closeState(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, CLOSE_STATE_DISCRIMINATOR);
  }

  public static final Discriminator CRYSTALLIZE_FEES_DISCRIMINATOR = toDiscriminator(78, 0, 111, 26, 7, 12, 41, 249);

  public static Instruction crystallizeFees(final AccountMeta invokedGlamProtocolProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamEscrowKey,
                                            final PublicKey glamMintKey,
                                            final PublicKey escrowMintAtaKey,
                                            final PublicKey signerKey,
                                            final int mintId) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamEscrowKey),
      createWrite(glamMintKey),
      createWrite(escrowMintAtaKey),
      createWritableSigner(signerKey),
      createRead(solanaAccounts.token2022Program())
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(CRYSTALLIZE_FEES_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mintId;

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record CrystallizeFeesIxData(Discriminator discriminator, int mintId) implements Borsh {  

    public static CrystallizeFeesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static CrystallizeFeesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintId = _data[i] & 0xFF;
      return new CrystallizeFeesIxData(discriminator, mintId);
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

  public static final Discriminator DRIFT_BALANCE_VALUE_USD_DISCRIMINATOR = toDiscriminator(152, 248, 238, 80, 92, 122, 40, 131);

  public static Instruction driftBalanceValueUsd(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, DRIFT_BALANCE_VALUE_USD_DISCRIMINATOR);
  }

  public static final Discriminator DRIFT_CANCEL_ORDERS_DISCRIMINATOR = toDiscriminator(98, 107, 48, 79, 97, 60, 99, 58);

  public static Instruction driftCancelOrders(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction driftCancelOrdersByIds(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction driftDeleteUser(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, DRIFT_DELETE_USER_DISCRIMINATOR);
  }

  public static final Discriminator DRIFT_DEPOSIT_DISCRIMINATOR = toDiscriminator(252, 63, 250, 201, 98, 55, 130, 12);

  public static Instruction driftDeposit(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction driftInitializeUser(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction driftInitializeUserStats(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, DRIFT_INITIALIZE_USER_STATS_DISCRIMINATOR);
  }

  public static final Discriminator DRIFT_MODIFY_ORDER_DISCRIMINATOR = toDiscriminator(235, 245, 222, 58, 245, 128, 19, 202);

  public static Instruction driftModifyOrder(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction driftPlaceOrders(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction driftUpdateUserCustomMarginRatio(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction driftUpdateUserDelegate(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction driftUpdateUserMarginTradingEnabled(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction driftWithdraw(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction forceTransferTokens(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static final Discriminator FULFILL_DISCRIMINATOR = toDiscriminator(143, 2, 52, 206, 174, 164, 247, 72);

  public static Instruction fulfill(final AccountMeta invokedGlamProtocolProgramMeta,
                                    final SolanaAccounts solanaAccounts,
                                    final PublicKey glamStateKey,
                                    final PublicKey glamVaultKey,
                                    final PublicKey glamEscrowKey,
                                    final PublicKey glamMintKey,
                                    final PublicKey signerKey,
                                    final PublicKey escrowMintAtaKey,
                                    final PublicKey assetKey,
                                    final PublicKey vaultAssetAtaKey,
                                    final PublicKey escrowAssetAtaKey,
                                    final int mintId) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createRead(glamEscrowKey),
      createWrite(glamMintKey),
      createWritableSigner(signerKey),
      createWrite(escrowMintAtaKey),
      createRead(assetKey),
      createWrite(vaultAssetAtaKey),
      createWrite(escrowAssetAtaKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.token2022Program()),
      createRead(solanaAccounts.associatedTokenAccountProgram())
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(FULFILL_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mintId;

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record FulfillIxData(Discriminator discriminator, int mintId) implements Borsh {  

    public static FulfillIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static FulfillIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintId = _data[i] & 0xFF;
      return new FulfillIxData(discriminator, mintId);
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

  public static final Discriminator INITIALIZE_STATE_DISCRIMINATOR = toDiscriminator(190, 171, 224, 219, 217, 72, 199, 176);

  public static Instruction initializeState(final AccountMeta invokedGlamProtocolProgramMeta,
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
      createWrite(requireNonNullElse(openfundsMetadataKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(state)];
    int i = writeDiscriminator(INITIALIZE_STATE_DISCRIMINATOR, _data, 0);
    Borsh.write(state, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction jupiterGovNewVote(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction jupiterSetMaxSwapSlippage(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction jupiterSwap(final AccountMeta invokedGlamProtocolProgramMeta,
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
      createRead(requireNonNullElse(inputStakePoolKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(requireNonNullElse(outputStakePoolKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(inputTokenProgramKey),
      createRead(outputTokenProgramKey)
    );

    final byte[] _data = new byte[12 + Borsh.lenVector(data)];
    int i = writeDiscriminator(JUPITER_SWAP_DISCRIMINATOR, _data, 0);
    Borsh.writeVector(data, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction jupiterVoteCastVote(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction jupiterVoteCastVoteChecked(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction jupiterVoteIncreaseLockedAmount(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction jupiterVoteMergePartialUnstaking(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, JUPITER_VOTE_MERGE_PARTIAL_UNSTAKING_DISCRIMINATOR);
  }

  public static final Discriminator JUPITER_VOTE_NEW_ESCROW_DISCRIMINATOR = toDiscriminator(255, 87, 157, 219, 61, 178, 144, 159);

  public static Instruction jupiterVoteNewEscrow(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, JUPITER_VOTE_NEW_ESCROW_DISCRIMINATOR);
  }

  public static final Discriminator JUPITER_VOTE_OPEN_PARTIAL_UNSTAKING_DISCRIMINATOR = toDiscriminator(84, 7, 113, 220, 212, 63, 237, 218);

  public static Instruction jupiterVoteOpenPartialUnstaking(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction jupiterVoteToggleMaxLock(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction jupiterVoteWithdraw(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, JUPITER_VOTE_WITHDRAW_DISCRIMINATOR);
  }

  public static final Discriminator JUPITER_VOTE_WITHDRAW_PARTIAL_UNSTAKING_DISCRIMINATOR = toDiscriminator(109, 98, 65, 252, 184, 0, 216, 240);

  public static Instruction jupiterVoteWithdrawPartialUnstaking(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, JUPITER_VOTE_WITHDRAW_PARTIAL_UNSTAKING_DISCRIMINATOR);
  }

  public static final Discriminator KAMINO_LENDING_BORROW_OBLIGATION_LIQUIDITY_V2_DISCRIMINATOR = toDiscriminator(175, 198, 39, 162, 103, 76, 51, 121);

  public static Instruction kaminoLendingBorrowObligationLiquidityV2(final AccountMeta invokedGlamProtocolProgramMeta,
                                                                     final PublicKey glamStateKey,
                                                                     final PublicKey glamVaultKey,
                                                                     final PublicKey glamSignerKey,
                                                                     final PublicKey cpiProgramKey,
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
                                                                     final PublicKey obligationFarmUserStateKey,
                                                                     final PublicKey reserveFarmStateKey,
                                                                     final PublicKey farmsProgramKey,
                                                                     final long liquidityAmount) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(obligationKey),
      createRead(lendingMarketKey),
      createRead(lendingMarketAuthorityKey),
      createWrite(borrowReserveKey),
      createRead(borrowReserveLiquidityMintKey),
      createWrite(reserveSourceLiquidityKey),
      createWrite(borrowReserveLiquidityFeeReceiverKey),
      createWrite(userDestinationLiquidityKey),
      createWrite(referrerTokenStateKey),
      createRead(tokenProgramKey),
      createRead(instructionSysvarAccountKey),
      createWrite(obligationFarmUserStateKey),
      createWrite(reserveFarmStateKey),
      createRead(farmsProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(KAMINO_LENDING_BORROW_OBLIGATION_LIQUIDITY_V2_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, liquidityAmount);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record KaminoLendingBorrowObligationLiquidityV2IxData(Discriminator discriminator, long liquidityAmount) implements Borsh {  

    public static KaminoLendingBorrowObligationLiquidityV2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static KaminoLendingBorrowObligationLiquidityV2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityAmount = getInt64LE(_data, i);
      return new KaminoLendingBorrowObligationLiquidityV2IxData(discriminator, liquidityAmount);
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

  public static final Discriminator KAMINO_LENDING_DEPOSIT_RESERVE_LIQUIDITY_AND_OBLIGATION_COLLATERAL_V2_DISCRIMINATOR = toDiscriminator(93, 120, 106, 112, 40, 45, 84, 32);

  public static Instruction kaminoLendingDepositReserveLiquidityAndObligationCollateralV2(final AccountMeta invokedGlamProtocolProgramMeta,
                                                                                          final PublicKey glamStateKey,
                                                                                          final PublicKey glamVaultKey,
                                                                                          final PublicKey glamSignerKey,
                                                                                          final PublicKey cpiProgramKey,
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
                                                                                          final PublicKey obligationFarmUserStateKey,
                                                                                          final PublicKey reserveFarmStateKey,
                                                                                          final PublicKey farmsProgramKey,
                                                                                          final long liquidityAmount) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(obligationKey),
      createRead(lendingMarketKey),
      createRead(lendingMarketAuthorityKey),
      createWrite(reserveKey),
      createRead(reserveLiquidityMintKey),
      createWrite(reserveLiquiditySupplyKey),
      createWrite(reserveCollateralMintKey),
      createWrite(reserveDestinationDepositCollateralKey),
      createWrite(userSourceLiquidityKey),
      createRead(placeholderUserDestinationCollateralKey),
      createRead(collateralTokenProgramKey),
      createRead(liquidityTokenProgramKey),
      createRead(instructionSysvarAccountKey),
      createWrite(obligationFarmUserStateKey),
      createWrite(reserveFarmStateKey),
      createRead(farmsProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(KAMINO_LENDING_DEPOSIT_RESERVE_LIQUIDITY_AND_OBLIGATION_COLLATERAL_V2_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, liquidityAmount);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record KaminoLendingDepositReserveLiquidityAndObligationCollateralV2IxData(Discriminator discriminator, long liquidityAmount) implements Borsh {  

    public static KaminoLendingDepositReserveLiquidityAndObligationCollateralV2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static KaminoLendingDepositReserveLiquidityAndObligationCollateralV2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityAmount = getInt64LE(_data, i);
      return new KaminoLendingDepositReserveLiquidityAndObligationCollateralV2IxData(discriminator, liquidityAmount);
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

  public static Instruction kaminoLendingInitObligation(final AccountMeta invokedGlamProtocolProgramMeta,
                                                        final SolanaAccounts solanaAccounts,
                                                        final PublicKey glamStateKey,
                                                        final PublicKey glamVaultKey,
                                                        final PublicKey glamSignerKey,
                                                        final PublicKey cpiProgramKey,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction kaminoLendingInitObligationFarmsForReserve(final AccountMeta invokedGlamProtocolProgramMeta,
                                                                       final SolanaAccounts solanaAccounts,
                                                                       final PublicKey glamStateKey,
                                                                       final PublicKey glamVaultKey,
                                                                       final PublicKey glamSignerKey,
                                                                       final PublicKey cpiProgramKey,
                                                                       final PublicKey payerKey,
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
      createWrite(obligationKey),
      createRead(lendingMarketAuthorityKey),
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction kaminoLendingInitUserMetadata(final AccountMeta invokedGlamProtocolProgramMeta,
                                                          final SolanaAccounts solanaAccounts,
                                                          final PublicKey glamStateKey,
                                                          final PublicKey glamVaultKey,
                                                          final PublicKey glamSignerKey,
                                                          final PublicKey cpiProgramKey,
                                                          final PublicKey feePayerKey,
                                                          final PublicKey userMetadataKey,
                                                          final PublicKey referrerUserMetadataKey,
                                                          final PublicKey userLookupTable) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWritableSigner(feePayerKey),
      createWrite(userMetadataKey),
      createRead(referrerUserMetadataKey),
      createRead(solanaAccounts.rentSysVar()),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(KAMINO_LENDING_INIT_USER_METADATA_DISCRIMINATOR, _data, 0);
    userLookupTable.write(_data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static final Discriminator KAMINO_LENDING_REPAY_OBLIGATION_LIQUIDITY_V2_DISCRIMINATOR = toDiscriminator(135, 57, 236, 69, 153, 77, 15, 88);

  public static Instruction kaminoLendingRepayObligationLiquidityV2(final AccountMeta invokedGlamProtocolProgramMeta,
                                                                    final PublicKey glamStateKey,
                                                                    final PublicKey glamVaultKey,
                                                                    final PublicKey glamSignerKey,
                                                                    final PublicKey cpiProgramKey,
                                                                    final PublicKey obligationKey,
                                                                    final PublicKey lendingMarketKey,
                                                                    final PublicKey repayReserveKey,
                                                                    final PublicKey reserveLiquidityMintKey,
                                                                    final PublicKey reserveDestinationLiquidityKey,
                                                                    final PublicKey userSourceLiquidityKey,
                                                                    final PublicKey tokenProgramKey,
                                                                    final PublicKey instructionSysvarAccountKey,
                                                                    final PublicKey obligationFarmUserStateKey,
                                                                    final PublicKey reserveFarmStateKey,
                                                                    final PublicKey lendingMarketAuthorityKey,
                                                                    final PublicKey farmsProgramKey,
                                                                    final long liquidityAmount) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(obligationKey),
      createRead(lendingMarketKey),
      createWrite(repayReserveKey),
      createRead(reserveLiquidityMintKey),
      createWrite(reserveDestinationLiquidityKey),
      createWrite(userSourceLiquidityKey),
      createRead(tokenProgramKey),
      createRead(instructionSysvarAccountKey),
      createWrite(obligationFarmUserStateKey),
      createWrite(reserveFarmStateKey),
      createRead(lendingMarketAuthorityKey),
      createRead(farmsProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(KAMINO_LENDING_REPAY_OBLIGATION_LIQUIDITY_V2_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, liquidityAmount);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record KaminoLendingRepayObligationLiquidityV2IxData(Discriminator discriminator, long liquidityAmount) implements Borsh {  

    public static KaminoLendingRepayObligationLiquidityV2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static KaminoLendingRepayObligationLiquidityV2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityAmount = getInt64LE(_data, i);
      return new KaminoLendingRepayObligationLiquidityV2IxData(discriminator, liquidityAmount);
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

  public static final Discriminator KAMINO_LENDING_WITHDRAW_OBLIGATION_COLLATERAL_AND_REDEEM_RESERVE_COLLATERAL_V2_DISCRIMINATOR = toDiscriminator(249, 60, 252, 239, 136, 53, 181, 3);

  public static Instruction kaminoLendingWithdrawObligationCollateralAndRedeemReserveCollateralV2(final AccountMeta invokedGlamProtocolProgramMeta,
                                                                                                  final PublicKey glamStateKey,
                                                                                                  final PublicKey glamVaultKey,
                                                                                                  final PublicKey glamSignerKey,
                                                                                                  final PublicKey cpiProgramKey,
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
                                                                                                  final PublicKey obligationFarmUserStateKey,
                                                                                                  final PublicKey reserveFarmStateKey,
                                                                                                  final PublicKey farmsProgramKey,
                                                                                                  final long collateralAmount) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(obligationKey),
      createRead(lendingMarketKey),
      createRead(lendingMarketAuthorityKey),
      createWrite(withdrawReserveKey),
      createRead(reserveLiquidityMintKey),
      createWrite(reserveSourceCollateralKey),
      createWrite(reserveCollateralMintKey),
      createWrite(reserveLiquiditySupplyKey),
      createWrite(userDestinationLiquidityKey),
      createRead(placeholderUserDestinationCollateralKey),
      createRead(collateralTokenProgramKey),
      createRead(liquidityTokenProgramKey),
      createRead(instructionSysvarAccountKey),
      createWrite(obligationFarmUserStateKey),
      createWrite(reserveFarmStateKey),
      createRead(farmsProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(KAMINO_LENDING_WITHDRAW_OBLIGATION_COLLATERAL_AND_REDEEM_RESERVE_COLLATERAL_V2_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, collateralAmount);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record KaminoLendingWithdrawObligationCollateralAndRedeemReserveCollateralV2IxData(Discriminator discriminator, long collateralAmount) implements Borsh {  

    public static KaminoLendingWithdrawObligationCollateralAndRedeemReserveCollateralV2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static KaminoLendingWithdrawObligationCollateralAndRedeemReserveCollateralV2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var collateralAmount = getInt64LE(_data, i);
      return new KaminoLendingWithdrawObligationCollateralAndRedeemReserveCollateralV2IxData(discriminator, collateralAmount);
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

  public static final Discriminator MARINADE_CLAIM_DISCRIMINATOR = toDiscriminator(54, 44, 48, 204, 218, 141, 36, 5);

  public static Instruction marinadeClaim(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, MARINADE_CLAIM_DISCRIMINATOR);
  }

  public static final Discriminator MARINADE_DEPOSIT_DISCRIMINATOR = toDiscriminator(62, 236, 248, 28, 222, 232, 182, 73);

  public static Instruction marinadeDeposit(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction marinadeDepositStakeAccount(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction marinadeLiquidUnstake(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction marinadeOrderUnstake(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static final Discriminator METEORA_DLMM_ADD_LIQUIDITY_DISCRIMINATOR = toDiscriminator(214, 108, 176, 68, 92, 135, 32, 35);

  public static Instruction meteoraDlmmAddLiquidity(final AccountMeta invokedGlamProtocolProgramMeta,
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
                                                    final LiquidityParameter liquidityParameter) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
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

    final byte[] _data = new byte[8 + Borsh.len(liquidityParameter)];
    int i = writeDiscriminator(METEORA_DLMM_ADD_LIQUIDITY_DISCRIMINATOR, _data, 0);
    Borsh.write(liquidityParameter, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record MeteoraDlmmAddLiquidityIxData(Discriminator discriminator, LiquidityParameter liquidityParameter) implements Borsh {  

    public static MeteoraDlmmAddLiquidityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static MeteoraDlmmAddLiquidityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityParameter = LiquidityParameter.read(_data, i);
      return new MeteoraDlmmAddLiquidityIxData(discriminator, liquidityParameter);
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

  public static final Discriminator METEORA_DLMM_ADD_LIQUIDITY_BY_STRATEGY_DISCRIMINATOR = toDiscriminator(81, 139, 59, 146, 176, 196, 240, 216);

  public static Instruction meteoraDlmmAddLiquidityByStrategy(final AccountMeta invokedGlamProtocolProgramMeta,
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
                                                              final LiquidityParameterByStrategy liquidityParameter) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
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

    final byte[] _data = new byte[8 + Borsh.len(liquidityParameter)];
    int i = writeDiscriminator(METEORA_DLMM_ADD_LIQUIDITY_BY_STRATEGY_DISCRIMINATOR, _data, 0);
    Borsh.write(liquidityParameter, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record MeteoraDlmmAddLiquidityByStrategyIxData(Discriminator discriminator, LiquidityParameterByStrategy liquidityParameter) implements Borsh {  

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
      final var liquidityParameter = LiquidityParameterByStrategy.read(_data, i);
      return new MeteoraDlmmAddLiquidityByStrategyIxData(discriminator, liquidityParameter);
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

  public static final Discriminator METEORA_DLMM_ADD_LIQUIDITY_ONE_SIDE_PRECISE_DISCRIMINATOR = toDiscriminator(244, 187, 200, 82, 30, 179, 154, 224);

  public static Instruction meteoraDlmmAddLiquidityOneSidePrecise(final AccountMeta invokedGlamProtocolProgramMeta,
                                                                  final PublicKey glamStateKey,
                                                                  final PublicKey glamVaultKey,
                                                                  final PublicKey glamSignerKey,
                                                                  final PublicKey cpiProgramKey,
                                                                  final PublicKey positionKey,
                                                                  final PublicKey lbPairKey,
                                                                  final PublicKey binArrayBitmapExtensionKey,
                                                                  final PublicKey userTokenKey,
                                                                  final PublicKey reserveKey,
                                                                  final PublicKey tokenMintKey,
                                                                  final PublicKey binArrayLowerKey,
                                                                  final PublicKey binArrayUpperKey,
                                                                  final PublicKey tokenProgramKey,
                                                                  final PublicKey eventAuthorityKey,
                                                                  final PublicKey programKey,
                                                                  final AddLiquiditySingleSidePreciseParameter parameter) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(binArrayBitmapExtensionKey),
      createWrite(userTokenKey),
      createWrite(reserveKey),
      createRead(tokenMintKey),
      createWrite(binArrayLowerKey),
      createWrite(binArrayUpperKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(parameter)];
    int i = writeDiscriminator(METEORA_DLMM_ADD_LIQUIDITY_ONE_SIDE_PRECISE_DISCRIMINATOR, _data, 0);
    Borsh.write(parameter, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record MeteoraDlmmAddLiquidityOneSidePreciseIxData(Discriminator discriminator, AddLiquiditySingleSidePreciseParameter parameter) implements Borsh {  

    public static MeteoraDlmmAddLiquidityOneSidePreciseIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static MeteoraDlmmAddLiquidityOneSidePreciseIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var parameter = AddLiquiditySingleSidePreciseParameter.read(_data, i);
      return new MeteoraDlmmAddLiquidityOneSidePreciseIxData(discriminator, parameter);
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

  public static final Discriminator METEORA_DLMM_CLAIM_FEE_DISCRIMINATOR = toDiscriminator(78, 116, 98, 78, 50, 82, 72, 37);

  public static Instruction meteoraDlmmClaimFee(final AccountMeta invokedGlamProtocolProgramMeta,
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
      createRead(glamVaultKey),
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, METEORA_DLMM_CLAIM_FEE_DISCRIMINATOR);
  }

  public static final Discriminator METEORA_DLMM_CLAIM_REWARD_DISCRIMINATOR = toDiscriminator(107, 160, 137, 17, 162, 0, 24, 234);

  public static Instruction meteoraDlmmClaimReward(final AccountMeta invokedGlamProtocolProgramMeta,
                                                   final PublicKey glamStateKey,
                                                   final PublicKey glamVaultKey,
                                                   final PublicKey glamSignerKey,
                                                   final PublicKey cpiProgramKey,
                                                   final PublicKey lbPairKey,
                                                   final PublicKey positionKey,
                                                   final PublicKey binArrayLowerKey,
                                                   final PublicKey binArrayUpperKey,
                                                   final PublicKey rewardVaultKey,
                                                   final PublicKey rewardMintKey,
                                                   final PublicKey userTokenAccountKey,
                                                   final PublicKey tokenProgramKey,
                                                   final PublicKey eventAuthorityKey,
                                                   final PublicKey programKey,
                                                   final long rewardIndex) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(lbPairKey),
      createWrite(positionKey),
      createWrite(binArrayLowerKey),
      createWrite(binArrayUpperKey),
      createWrite(rewardVaultKey),
      createRead(rewardMintKey),
      createWrite(userTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(METEORA_DLMM_CLAIM_REWARD_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, rewardIndex);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record MeteoraDlmmClaimRewardIxData(Discriminator discriminator, long rewardIndex) implements Borsh {  

    public static MeteoraDlmmClaimRewardIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static MeteoraDlmmClaimRewardIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var rewardIndex = getInt64LE(_data, i);
      return new MeteoraDlmmClaimRewardIxData(discriminator, rewardIndex);
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

  public static final Discriminator METEORA_DLMM_CLOSE_POSITION_DISCRIMINATOR = toDiscriminator(186, 117, 42, 24, 221, 194, 34, 143);

  public static Instruction meteoraDlmmClosePosition(final AccountMeta invokedGlamProtocolProgramMeta,
                                                     final PublicKey glamStateKey,
                                                     final PublicKey glamVaultKey,
                                                     final PublicKey glamSignerKey,
                                                     final PublicKey cpiProgramKey,
                                                     final PublicKey positionKey,
                                                     final PublicKey lbPairKey,
                                                     final PublicKey binArrayLowerKey,
                                                     final PublicKey binArrayUpperKey,
                                                     final PublicKey rentReceiverKey,
                                                     final PublicKey eventAuthorityKey,
                                                     final PublicKey programKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(binArrayLowerKey),
      createWrite(binArrayUpperKey),
      createWrite(rentReceiverKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, METEORA_DLMM_CLOSE_POSITION_DISCRIMINATOR);
  }

  public static final Discriminator METEORA_DLMM_INITIALIZE_POSITION_DISCRIMINATOR = toDiscriminator(223, 94, 215, 96, 175, 181, 195, 204);

  public static Instruction meteoraDlmmInitializePosition(final AccountMeta invokedGlamProtocolProgramMeta,
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
      createRead(glamVaultKey),
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static final Discriminator METEORA_DLMM_REMOVE_LIQUIDITY_DISCRIMINATOR = toDiscriminator(185, 228, 248, 124, 57, 133, 19, 192);

  public static Instruction meteoraDlmmRemoveLiquidity(final AccountMeta invokedGlamProtocolProgramMeta,
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
                                                       final BinLiquidityReduction[] binLiquidityRemoval) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
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

    final byte[] _data = new byte[8 + Borsh.lenVector(binLiquidityRemoval)];
    int i = writeDiscriminator(METEORA_DLMM_REMOVE_LIQUIDITY_DISCRIMINATOR, _data, 0);
    Borsh.writeVector(binLiquidityRemoval, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record MeteoraDlmmRemoveLiquidityIxData(Discriminator discriminator, BinLiquidityReduction[] binLiquidityRemoval) implements Borsh {  

    public static MeteoraDlmmRemoveLiquidityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static MeteoraDlmmRemoveLiquidityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var binLiquidityRemoval = Borsh.readVector(BinLiquidityReduction.class, BinLiquidityReduction::read, _data, i);
      return new MeteoraDlmmRemoveLiquidityIxData(discriminator, binLiquidityRemoval);
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

  public static final Discriminator METEORA_DLMM_REMOVE_LIQUIDITY_BY_RANGE_DISCRIMINATOR = toDiscriminator(223, 12, 177, 181, 96, 109, 60, 124);

  public static Instruction meteoraDlmmRemoveLiquidityByRange(final AccountMeta invokedGlamProtocolProgramMeta,
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
      createRead(glamVaultKey),
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction meteoraDlmmSwap(final AccountMeta invokedGlamProtocolProgramMeta,
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
      createRead(glamVaultKey),
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction mintTokens(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static final Discriminator PRICE_STAKES_DISCRIMINATOR = toDiscriminator(0, 60, 60, 103, 201, 94, 72, 223);

  public static Instruction priceStakes(final AccountMeta invokedGlamProtocolProgramMeta,
                                        final PublicKey glamStateKey,
                                        final PublicKey glamVaultKey,
                                        final PublicKey glamSignerKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey)
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, PRICE_STAKES_DISCRIMINATOR);
  }

  public static final Discriminator PRICE_TICKETS_DISCRIMINATOR = toDiscriminator(253, 18, 224, 98, 226, 43, 65, 76);

  public static Instruction priceTickets(final AccountMeta invokedGlamProtocolProgramMeta,
                                         final PublicKey glamStateKey,
                                         final PublicKey glamVaultKey,
                                         final PublicKey glamSignerKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey)
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, PRICE_TICKETS_DISCRIMINATOR);
  }

  public static final Discriminator PRICE_VAULT_DISCRIMINATOR = toDiscriminator(47, 213, 36, 17, 183, 5, 141, 45);

  public static Instruction priceVault(final AccountMeta invokedGlamProtocolProgramMeta,
                                       final PublicKey glamStateKey,
                                       final PublicKey glamVaultKey,
                                       final PublicKey glamSignerKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey)
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, PRICE_VAULT_DISCRIMINATOR);
  }

  public static final Discriminator REDEEM_QUEUED_DISCRIMINATOR = toDiscriminator(3, 43, 239, 213, 40, 225, 179, 28);

  public static Instruction redeemQueued(final AccountMeta invokedGlamProtocolProgramMeta,
                                         final SolanaAccounts solanaAccounts,
                                         final PublicKey glamStateKey,
                                         final PublicKey glamEscrowKey,
                                         final PublicKey glamMintKey,
                                         final PublicKey signerKey,
                                         final PublicKey signerMintAtaKey,
                                         final PublicKey escrowMintAtaKey,
                                         final int mintId,
                                         final long sharesIn) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamEscrowKey),
      createWrite(glamMintKey),
      createWritableSigner(signerKey),
      createWrite(signerMintAtaKey),
      createWrite(escrowMintAtaKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.token2022Program()),
      createRead(solanaAccounts.associatedTokenAccountProgram())
    );

    final byte[] _data = new byte[17];
    int i = writeDiscriminator(REDEEM_QUEUED_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mintId;
    ++i;
    putInt64LE(_data, i, sharesIn);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record RedeemQueuedIxData(Discriminator discriminator, int mintId, long sharesIn) implements Borsh {  

    public static RedeemQueuedIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static RedeemQueuedIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintId = _data[i] & 0xFF;
      ++i;
      final var sharesIn = getInt64LE(_data, i);
      return new RedeemQueuedIxData(discriminator, mintId, sharesIn);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) mintId;
      ++i;
      putInt64LE(_data, i, sharesIn);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_SUBSCRIBE_REDEEM_ENABLED_DISCRIMINATOR = toDiscriminator(189, 56, 205, 172, 201, 185, 34, 92);

  public static Instruction setSubscribeRedeemEnabled(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction setTokenAccountsStates(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static final Discriminator STAKE_AUTHORIZE_DISCRIMINATOR = toDiscriminator(127, 247, 88, 164, 201, 0, 79, 7);

  public static Instruction stakeAuthorize(final AccountMeta invokedGlamProtocolProgramMeta,
                                           final SolanaAccounts solanaAccounts,
                                           final PublicKey glamStateKey,
                                           final PublicKey glamVaultKey,
                                           final PublicKey glamSignerKey,
                                           final PublicKey stakeKey,
                                           final PublicKey newAuthority,
                                           final int stakerWithWithdrawer) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.stakeProgram()),
      createWrite(stakeKey),
      createRead(solanaAccounts.clockSysVar())
    );

    final byte[] _data = new byte[44];
    int i = writeDiscriminator(STAKE_AUTHORIZE_DISCRIMINATOR, _data, 0);
    newAuthority.write(_data, i);
    i += 32;
    putInt32LE(_data, i, stakerWithWithdrawer);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record StakeAuthorizeIxData(Discriminator discriminator, PublicKey newAuthority, int stakerWithWithdrawer) implements Borsh {  

    public static StakeAuthorizeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 44;

    public static StakeAuthorizeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var newAuthority = readPubKey(_data, i);
      i += 32;
      final var stakerWithWithdrawer = getInt32LE(_data, i);
      return new StakeAuthorizeIxData(discriminator, newAuthority, stakerWithWithdrawer);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      newAuthority.write(_data, i);
      i += 32;
      putInt32LE(_data, i, stakerWithWithdrawer);
      i += 4;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator STAKE_DEACTIVATE_DISCRIMINATOR = toDiscriminator(224, 10, 93, 175, 175, 145, 237, 169);

  public static Instruction stakeDeactivate(final AccountMeta invokedGlamProtocolProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamVaultKey,
                                            final PublicKey glamSignerKey,
                                            final PublicKey stakeKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.stakeProgram()),
      createWrite(stakeKey),
      createRead(solanaAccounts.clockSysVar())
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, STAKE_DEACTIVATE_DISCRIMINATOR);
  }

  public static final Discriminator STAKE_DELEGATE_STAKE_DISCRIMINATOR = toDiscriminator(202, 40, 152, 239, 175, 251, 66, 228);

  public static Instruction stakeDelegateStake(final AccountMeta invokedGlamProtocolProgramMeta,
                                               final SolanaAccounts solanaAccounts,
                                               final PublicKey glamStateKey,
                                               final PublicKey glamVaultKey,
                                               final PublicKey glamSignerKey,
                                               final PublicKey stakeKey,
                                               final PublicKey voteKey,
                                               final PublicKey stakeConfigKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.stakeProgram()),
      createWrite(stakeKey),
      createRead(voteKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar()),
      createRead(stakeConfigKey)
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, STAKE_DELEGATE_STAKE_DISCRIMINATOR);
  }

  public static final Discriminator STAKE_INITIALIZE_DISCRIMINATOR = toDiscriminator(68, 66, 118, 79, 15, 144, 190, 190);

  public static Instruction stakeInitialize(final AccountMeta invokedGlamProtocolProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamVaultKey,
                                            final PublicKey glamSignerKey,
                                            final PublicKey stakeKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.stakeProgram()),
      createWrite(stakeKey),
      createRead(solanaAccounts.rentSysVar()),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, STAKE_INITIALIZE_DISCRIMINATOR);
  }

  public static final Discriminator STAKE_MERGE_DISCRIMINATOR = toDiscriminator(46, 181, 125, 12, 51, 179, 134, 176);

  public static Instruction stakeMerge(final AccountMeta invokedGlamProtocolProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey glamStateKey,
                                       final PublicKey glamVaultKey,
                                       final PublicKey glamSignerKey,
                                       final PublicKey destinationStakeKey,
                                       final PublicKey sourceStakeKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.stakeProgram()),
      createWrite(destinationStakeKey),
      createWrite(sourceStakeKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar())
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, STAKE_MERGE_DISCRIMINATOR);
  }

  public static final Discriminator STAKE_MOVE_LAMPORTS_DISCRIMINATOR = toDiscriminator(21, 85, 218, 122, 182, 189, 82, 200);

  public static Instruction stakeMoveLamports(final AccountMeta invokedGlamProtocolProgramMeta,
                                              final SolanaAccounts solanaAccounts,
                                              final PublicKey glamStateKey,
                                              final PublicKey glamVaultKey,
                                              final PublicKey glamSignerKey,
                                              final PublicKey sourceStakeKey,
                                              final PublicKey destinationStakeKey,
                                              final long lamports) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.stakeProgram()),
      createWrite(sourceStakeKey),
      createWrite(destinationStakeKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(STAKE_MOVE_LAMPORTS_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record StakeMoveLamportsIxData(Discriminator discriminator, long lamports) implements Borsh {  

    public static StakeMoveLamportsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static StakeMoveLamportsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      return new StakeMoveLamportsIxData(discriminator, lamports);
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

  public static final Discriminator STAKE_MOVE_STAKE_DISCRIMINATOR = toDiscriminator(9, 190, 67, 62, 46, 251, 144, 186);

  public static Instruction stakeMoveStake(final AccountMeta invokedGlamProtocolProgramMeta,
                                           final SolanaAccounts solanaAccounts,
                                           final PublicKey glamStateKey,
                                           final PublicKey glamVaultKey,
                                           final PublicKey glamSignerKey,
                                           final PublicKey sourceStakeKey,
                                           final PublicKey destinationStakeKey,
                                           final long lamports) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.stakeProgram()),
      createWrite(sourceStakeKey),
      createWrite(destinationStakeKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(STAKE_MOVE_STAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record StakeMoveStakeIxData(Discriminator discriminator, long lamports) implements Borsh {  

    public static StakeMoveStakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static StakeMoveStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      return new StakeMoveStakeIxData(discriminator, lamports);
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

  public static Instruction stakePoolDepositSol(final AccountMeta invokedGlamProtocolProgramMeta,
                                                final SolanaAccounts solanaAccounts,
                                                final PublicKey glamStateKey,
                                                final PublicKey glamVaultKey,
                                                final PublicKey glamSignerKey,
                                                final PublicKey cpiProgramKey,
                                                final PublicKey stakePoolKey,
                                                final PublicKey stakePoolWithdrawAuthorityKey,
                                                final PublicKey reserveStakeKey,
                                                final PublicKey poolTokensToKey,
                                                final PublicKey feeAccountKey,
                                                final PublicKey referrerPoolTokensAccountKey,
                                                final PublicKey poolMintKey,
                                                final PublicKey tokenProgramKey,
                                                final long lamportsIn) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(stakePoolKey),
      createRead(stakePoolWithdrawAuthorityKey),
      createWrite(reserveStakeKey),
      createWrite(poolTokensToKey),
      createWrite(feeAccountKey),
      createWrite(referrerPoolTokensAccountKey),
      createWrite(poolMintKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(STAKE_POOL_DEPOSIT_SOL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamportsIn);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record StakePoolDepositSolIxData(Discriminator discriminator, long lamportsIn) implements Borsh {  

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
      final var lamportsIn = getInt64LE(_data, i);
      return new StakePoolDepositSolIxData(discriminator, lamportsIn);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lamportsIn);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator STAKE_POOL_DEPOSIT_SOL_WITH_SLIPPAGE_DISCRIMINATOR = toDiscriminator(57, 21, 43, 19, 86, 36, 25, 172);

  public static Instruction stakePoolDepositSolWithSlippage(final AccountMeta invokedGlamProtocolProgramMeta,
                                                            final SolanaAccounts solanaAccounts,
                                                            final PublicKey glamStateKey,
                                                            final PublicKey glamVaultKey,
                                                            final PublicKey glamSignerKey,
                                                            final PublicKey cpiProgramKey,
                                                            final PublicKey stakePoolKey,
                                                            final PublicKey stakePoolWithdrawAuthorityKey,
                                                            final PublicKey reserveStakeKey,
                                                            final PublicKey poolTokensToKey,
                                                            final PublicKey feeAccountKey,
                                                            final PublicKey referrerPoolTokensAccountKey,
                                                            final PublicKey poolMintKey,
                                                            final PublicKey tokenProgramKey,
                                                            final long lamportsIn,
                                                            final long minimumPoolTokensOut) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(stakePoolKey),
      createRead(stakePoolWithdrawAuthorityKey),
      createWrite(reserveStakeKey),
      createWrite(poolTokensToKey),
      createWrite(feeAccountKey),
      createWrite(referrerPoolTokensAccountKey),
      createWrite(poolMintKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(STAKE_POOL_DEPOSIT_SOL_WITH_SLIPPAGE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamportsIn);
    i += 8;
    putInt64LE(_data, i, minimumPoolTokensOut);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record StakePoolDepositSolWithSlippageIxData(Discriminator discriminator, long lamportsIn, long minimumPoolTokensOut) implements Borsh {  

    public static StakePoolDepositSolWithSlippageIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static StakePoolDepositSolWithSlippageIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamportsIn = getInt64LE(_data, i);
      i += 8;
      final var minimumPoolTokensOut = getInt64LE(_data, i);
      return new StakePoolDepositSolWithSlippageIxData(discriminator, lamportsIn, minimumPoolTokensOut);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lamportsIn);
      i += 8;
      putInt64LE(_data, i, minimumPoolTokensOut);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator STAKE_POOL_DEPOSIT_STAKE_DISCRIMINATOR = toDiscriminator(212, 158, 195, 174, 179, 105, 9, 97);

  public static Instruction stakePoolDepositStake(final AccountMeta invokedGlamProtocolProgramMeta,
                                                  final SolanaAccounts solanaAccounts,
                                                  final PublicKey glamStateKey,
                                                  final PublicKey glamVaultKey,
                                                  final PublicKey glamSignerKey,
                                                  final PublicKey cpiProgramKey,
                                                  final PublicKey stakePoolKey,
                                                  final PublicKey validatorListKey,
                                                  final PublicKey stakePoolWithdrawAuthorityKey,
                                                  final PublicKey depositStakeKey,
                                                  final PublicKey validatorStakeAccountKey,
                                                  final PublicKey reserveStakeAccountKey,
                                                  final PublicKey poolTokensToKey,
                                                  final PublicKey feeAccountKey,
                                                  final PublicKey referrerPoolTokensAccountKey,
                                                  final PublicKey poolMintKey,
                                                  final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(stakePoolKey),
      createWrite(validatorListKey),
      createRead(stakePoolWithdrawAuthorityKey),
      createWrite(depositStakeKey),
      createWrite(validatorStakeAccountKey),
      createWrite(reserveStakeAccountKey),
      createWrite(poolTokensToKey),
      createWrite(feeAccountKey),
      createWrite(referrerPoolTokensAccountKey),
      createWrite(poolMintKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar()),
      createRead(tokenProgramKey),
      createRead(solanaAccounts.stakeProgram())
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, STAKE_POOL_DEPOSIT_STAKE_DISCRIMINATOR);
  }

  public static final Discriminator STAKE_POOL_DEPOSIT_STAKE_WITH_SLIPPAGE_DISCRIMINATOR = toDiscriminator(185, 104, 64, 97, 108, 243, 239, 165);

  public static Instruction stakePoolDepositStakeWithSlippage(final AccountMeta invokedGlamProtocolProgramMeta,
                                                              final SolanaAccounts solanaAccounts,
                                                              final PublicKey glamStateKey,
                                                              final PublicKey glamVaultKey,
                                                              final PublicKey glamSignerKey,
                                                              final PublicKey cpiProgramKey,
                                                              final PublicKey stakePoolKey,
                                                              final PublicKey validatorListKey,
                                                              final PublicKey stakePoolWithdrawAuthorityKey,
                                                              final PublicKey depositStakeKey,
                                                              final PublicKey validatorStakeAccountKey,
                                                              final PublicKey reserveStakeAccountKey,
                                                              final PublicKey poolTokensToKey,
                                                              final PublicKey feeAccountKey,
                                                              final PublicKey referrerPoolTokensAccountKey,
                                                              final PublicKey poolMintKey,
                                                              final PublicKey tokenProgramKey,
                                                              final long minimumPoolTokensOut) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(stakePoolKey),
      createWrite(validatorListKey),
      createRead(stakePoolWithdrawAuthorityKey),
      createWrite(depositStakeKey),
      createWrite(validatorStakeAccountKey),
      createWrite(reserveStakeAccountKey),
      createWrite(poolTokensToKey),
      createWrite(feeAccountKey),
      createWrite(referrerPoolTokensAccountKey),
      createWrite(poolMintKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar()),
      createRead(tokenProgramKey),
      createRead(solanaAccounts.stakeProgram())
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(STAKE_POOL_DEPOSIT_STAKE_WITH_SLIPPAGE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, minimumPoolTokensOut);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record StakePoolDepositStakeWithSlippageIxData(Discriminator discriminator, long minimumPoolTokensOut) implements Borsh {  

    public static StakePoolDepositStakeWithSlippageIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static StakePoolDepositStakeWithSlippageIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var minimumPoolTokensOut = getInt64LE(_data, i);
      return new StakePoolDepositStakeWithSlippageIxData(discriminator, minimumPoolTokensOut);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, minimumPoolTokensOut);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator STAKE_POOL_WITHDRAW_SOL_DISCRIMINATOR = toDiscriminator(179, 100, 204, 0, 192, 46, 233, 181);

  public static Instruction stakePoolWithdrawSol(final AccountMeta invokedGlamProtocolProgramMeta,
                                                 final SolanaAccounts solanaAccounts,
                                                 final PublicKey glamStateKey,
                                                 final PublicKey glamVaultKey,
                                                 final PublicKey glamSignerKey,
                                                 final PublicKey cpiProgramKey,
                                                 final PublicKey stakePoolKey,
                                                 final PublicKey stakePoolWithdrawAuthorityKey,
                                                 final PublicKey poolTokensFromKey,
                                                 final PublicKey reserveStakeKey,
                                                 final PublicKey feeAccountKey,
                                                 final PublicKey poolMintKey,
                                                 final PublicKey tokenProgramKey,
                                                 final long poolTokensIn) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(stakePoolKey),
      createRead(stakePoolWithdrawAuthorityKey),
      createWrite(poolTokensFromKey),
      createWrite(reserveStakeKey),
      createWrite(feeAccountKey),
      createWrite(poolMintKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar()),
      createRead(solanaAccounts.stakeProgram()),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(STAKE_POOL_WITHDRAW_SOL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, poolTokensIn);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record StakePoolWithdrawSolIxData(Discriminator discriminator, long poolTokensIn) implements Borsh {  

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
      final var poolTokensIn = getInt64LE(_data, i);
      return new StakePoolWithdrawSolIxData(discriminator, poolTokensIn);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, poolTokensIn);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator STAKE_POOL_WITHDRAW_SOL_WITH_SLIPPAGE_DISCRIMINATOR = toDiscriminator(210, 92, 86, 93, 123, 17, 117, 89);

  public static Instruction stakePoolWithdrawSolWithSlippage(final AccountMeta invokedGlamProtocolProgramMeta,
                                                             final SolanaAccounts solanaAccounts,
                                                             final PublicKey glamStateKey,
                                                             final PublicKey glamVaultKey,
                                                             final PublicKey glamSignerKey,
                                                             final PublicKey cpiProgramKey,
                                                             final PublicKey stakePoolKey,
                                                             final PublicKey stakePoolWithdrawAuthorityKey,
                                                             final PublicKey poolTokensFromKey,
                                                             final PublicKey reserveStakeKey,
                                                             final PublicKey feeAccountKey,
                                                             final PublicKey poolMintKey,
                                                             final PublicKey tokenProgramKey,
                                                             final long poolTokensIn,
                                                             final long minimumLamportsOut) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(stakePoolKey),
      createRead(stakePoolWithdrawAuthorityKey),
      createWrite(poolTokensFromKey),
      createWrite(reserveStakeKey),
      createWrite(feeAccountKey),
      createWrite(poolMintKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar()),
      createRead(solanaAccounts.stakeProgram()),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(STAKE_POOL_WITHDRAW_SOL_WITH_SLIPPAGE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, poolTokensIn);
    i += 8;
    putInt64LE(_data, i, minimumLamportsOut);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record StakePoolWithdrawSolWithSlippageIxData(Discriminator discriminator, long poolTokensIn, long minimumLamportsOut) implements Borsh {  

    public static StakePoolWithdrawSolWithSlippageIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static StakePoolWithdrawSolWithSlippageIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var poolTokensIn = getInt64LE(_data, i);
      i += 8;
      final var minimumLamportsOut = getInt64LE(_data, i);
      return new StakePoolWithdrawSolWithSlippageIxData(discriminator, poolTokensIn, minimumLamportsOut);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, poolTokensIn);
      i += 8;
      putInt64LE(_data, i, minimumLamportsOut);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator STAKE_POOL_WITHDRAW_STAKE_DISCRIMINATOR = toDiscriminator(7, 70, 250, 22, 49, 1, 143, 1);

  public static Instruction stakePoolWithdrawStake(final AccountMeta invokedGlamProtocolProgramMeta,
                                                   final SolanaAccounts solanaAccounts,
                                                   final PublicKey glamStateKey,
                                                   final PublicKey glamVaultKey,
                                                   final PublicKey glamSignerKey,
                                                   final PublicKey cpiProgramKey,
                                                   final PublicKey stakePoolKey,
                                                   final PublicKey validatorListKey,
                                                   final PublicKey stakePoolWithdrawAuthorityKey,
                                                   final PublicKey validatorStakeAccountKey,
                                                   final PublicKey stakeKey,
                                                   final PublicKey poolTokensFromKey,
                                                   final PublicKey feeAccountKey,
                                                   final PublicKey poolMintKey,
                                                   final PublicKey tokenProgramKey,
                                                   final long poolTokensIn) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(stakePoolKey),
      createWrite(validatorListKey),
      createRead(stakePoolWithdrawAuthorityKey),
      createWrite(validatorStakeAccountKey),
      createWrite(stakeKey),
      createWrite(poolTokensFromKey),
      createWrite(feeAccountKey),
      createWrite(poolMintKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(tokenProgramKey),
      createRead(solanaAccounts.stakeProgram())
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(STAKE_POOL_WITHDRAW_STAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, poolTokensIn);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record StakePoolWithdrawStakeIxData(Discriminator discriminator, long poolTokensIn) implements Borsh {  

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
      final var poolTokensIn = getInt64LE(_data, i);
      return new StakePoolWithdrawStakeIxData(discriminator, poolTokensIn);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, poolTokensIn);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator STAKE_POOL_WITHDRAW_STAKE_WITH_SLIPPAGE_DISCRIMINATOR = toDiscriminator(74, 83, 151, 22, 32, 149, 154, 141);

  public static Instruction stakePoolWithdrawStakeWithSlippage(final AccountMeta invokedGlamProtocolProgramMeta,
                                                               final SolanaAccounts solanaAccounts,
                                                               final PublicKey glamStateKey,
                                                               final PublicKey glamVaultKey,
                                                               final PublicKey glamSignerKey,
                                                               final PublicKey cpiProgramKey,
                                                               final PublicKey stakePoolKey,
                                                               final PublicKey validatorListKey,
                                                               final PublicKey stakePoolWithdrawAuthorityKey,
                                                               final PublicKey validatorStakeAccountKey,
                                                               final PublicKey stakeKey,
                                                               final PublicKey poolTokensFromKey,
                                                               final PublicKey feeAccountKey,
                                                               final PublicKey poolMintKey,
                                                               final PublicKey tokenProgramKey,
                                                               final long poolTokensIn,
                                                               final long minimumLamportsOut) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(stakePoolKey),
      createWrite(validatorListKey),
      createRead(stakePoolWithdrawAuthorityKey),
      createWrite(validatorStakeAccountKey),
      createWrite(stakeKey),
      createWrite(poolTokensFromKey),
      createWrite(feeAccountKey),
      createWrite(poolMintKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(tokenProgramKey),
      createRead(solanaAccounts.stakeProgram())
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(STAKE_POOL_WITHDRAW_STAKE_WITH_SLIPPAGE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, poolTokensIn);
    i += 8;
    putInt64LE(_data, i, minimumLamportsOut);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record StakePoolWithdrawStakeWithSlippageIxData(Discriminator discriminator, long poolTokensIn, long minimumLamportsOut) implements Borsh {  

    public static StakePoolWithdrawStakeWithSlippageIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static StakePoolWithdrawStakeWithSlippageIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var poolTokensIn = getInt64LE(_data, i);
      i += 8;
      final var minimumLamportsOut = getInt64LE(_data, i);
      return new StakePoolWithdrawStakeWithSlippageIxData(discriminator, poolTokensIn, minimumLamportsOut);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, poolTokensIn);
      i += 8;
      putInt64LE(_data, i, minimumLamportsOut);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator STAKE_REDELEGATE_DISCRIMINATOR = toDiscriminator(134, 227, 164, 247, 120, 0, 225, 174);

  public static Instruction stakeRedelegate(final AccountMeta invokedGlamProtocolProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamVaultKey,
                                            final PublicKey glamSignerKey,
                                            final PublicKey stakeKey,
                                            final PublicKey newStakeKey,
                                            final PublicKey voteKey,
                                            final PublicKey stakeConfigKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.stakeProgram()),
      createWrite(stakeKey),
      createWrite(newStakeKey),
      createRead(voteKey),
      createRead(stakeConfigKey)
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, STAKE_REDELEGATE_DISCRIMINATOR);
  }

  public static final Discriminator STAKE_SPLIT_DISCRIMINATOR = toDiscriminator(63, 128, 169, 206, 158, 60, 135, 48);

  public static Instruction stakeSplit(final AccountMeta invokedGlamProtocolProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey glamStateKey,
                                       final PublicKey glamVaultKey,
                                       final PublicKey glamSignerKey,
                                       final PublicKey stakeKey,
                                       final PublicKey splitStakeKey,
                                       final long lamports) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.stakeProgram()),
      createWrite(stakeKey),
      createWrite(splitStakeKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(STAKE_SPLIT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record StakeSplitIxData(Discriminator discriminator, long lamports) implements Borsh {  

    public static StakeSplitIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static StakeSplitIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      return new StakeSplitIxData(discriminator, lamports);
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

  public static final Discriminator STAKE_WITHDRAW_DISCRIMINATOR = toDiscriminator(199, 13, 168, 20, 92, 151, 29, 56);

  public static Instruction stakeWithdraw(final AccountMeta invokedGlamProtocolProgramMeta,
                                          final SolanaAccounts solanaAccounts,
                                          final PublicKey glamStateKey,
                                          final PublicKey glamVaultKey,
                                          final PublicKey glamSignerKey,
                                          final PublicKey stakeKey,
                                          final long lamports) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.stakeProgram()),
      createWrite(stakeKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar())
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(STAKE_WITHDRAW_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record StakeWithdrawIxData(Discriminator discriminator, long lamports) implements Borsh {  

    public static StakeWithdrawIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static StakeWithdrawIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      return new StakeWithdrawIxData(discriminator, lamports);
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

  public static final Discriminator SUBSCRIBE_INSTANT_DISCRIMINATOR = toDiscriminator(191, 239, 146, 220, 75, 86, 193, 152);

  public static Instruction subscribeInstant(final AccountMeta invokedGlamProtocolProgramMeta,
                                             final SolanaAccounts solanaAccounts,
                                             final PublicKey glamStateKey,
                                             final PublicKey glamEscrowKey,
                                             final PublicKey glamVaultKey,
                                             final PublicKey glamMintKey,
                                             final PublicKey signerKey,
                                             final PublicKey signerMintAtaKey,
                                             final PublicKey escrowMintAtaKey,
                                             final PublicKey depositAssetKey,
                                             final PublicKey vaultDepositAtaKey,
                                             final PublicKey signerDepositAtaKey,
                                             final PublicKey signerPolicyKey,
                                             final PublicKey policiesProgramKey,
                                             final int mintId,
                                             final long amountIn) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamEscrowKey),
      createRead(glamVaultKey),
      createWrite(glamMintKey),
      createWritableSigner(signerKey),
      createWrite(signerMintAtaKey),
      createWrite(escrowMintAtaKey),
      createRead(depositAssetKey),
      createWrite(vaultDepositAtaKey),
      createWrite(signerDepositAtaKey),
      createWrite(requireNonNullElse(signerPolicyKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.token2022Program()),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(policiesProgramKey)
    );

    final byte[] _data = new byte[17];
    int i = writeDiscriminator(SUBSCRIBE_INSTANT_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mintId;
    ++i;
    putInt64LE(_data, i, amountIn);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record SubscribeInstantIxData(Discriminator discriminator, int mintId, long amountIn) implements Borsh {  

    public static SubscribeInstantIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static SubscribeInstantIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintId = _data[i] & 0xFF;
      ++i;
      final var amountIn = getInt64LE(_data, i);
      return new SubscribeInstantIxData(discriminator, mintId, amountIn);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) mintId;
      ++i;
      putInt64LE(_data, i, amountIn);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SYSTEM_TRANSFER_DISCRIMINATOR = toDiscriminator(167, 164, 195, 155, 219, 152, 191, 230);

  public static Instruction systemTransfer(final AccountMeta invokedGlamProtocolProgramMeta,
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
      createWrite(toKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(SYSTEM_TRANSFER_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static final Discriminator TOKEN_CLOSE_ACCOUNT_DISCRIMINATOR = toDiscriminator(240, 32, 179, 154, 96, 110, 43, 79);

  public static Instruction tokenCloseAccount(final AccountMeta invokedGlamProtocolProgramMeta,
                                              final SolanaAccounts solanaAccounts,
                                              final PublicKey glamStateKey,
                                              final PublicKey glamVaultKey,
                                              final PublicKey glamSignerKey,
                                              final PublicKey cpiProgramKey,
                                              final PublicKey tokenAccountKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createRead(solanaAccounts.systemProgram()),
      createWrite(tokenAccountKey)
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, TOKEN_CLOSE_ACCOUNT_DISCRIMINATOR);
  }

  public static final Discriminator TOKEN_TRANSFER_DISCRIMINATOR = toDiscriminator(210, 16, 52, 5, 247, 164, 59, 18);

  public static Instruction tokenTransfer(final AccountMeta invokedGlamProtocolProgramMeta,
                                          final PublicKey glamStateKey,
                                          final PublicKey glamVaultKey,
                                          final PublicKey glamSignerKey,
                                          final PublicKey cpiProgramKey,
                                          final PublicKey fromKey,
                                          final PublicKey toKey,
                                          final long lamports) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(fromKey),
      createWrite(toKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(TOKEN_TRANSFER_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record TokenTransferIxData(Discriminator discriminator, long lamports) implements Borsh {  

    public static TokenTransferIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static TokenTransferIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      return new TokenTransferIxData(discriminator, lamports);
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

  public static final Discriminator TOKEN_TRANSFER_CHECKED_DISCRIMINATOR = toDiscriminator(169, 178, 117, 156, 169, 191, 199, 116);

  public static Instruction tokenTransferChecked(final AccountMeta invokedGlamProtocolProgramMeta,
                                                 final PublicKey glamStateKey,
                                                 final PublicKey glamVaultKey,
                                                 final PublicKey glamSignerKey,
                                                 final PublicKey cpiProgramKey,
                                                 final PublicKey fromKey,
                                                 final PublicKey mintKey,
                                                 final PublicKey toKey,
                                                 final long lamports,
                                                 final int decimals) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(fromKey),
      createRead(mintKey),
      createWrite(toKey)
    );

    final byte[] _data = new byte[17];
    int i = writeDiscriminator(TOKEN_TRANSFER_CHECKED_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);
    i += 8;
    _data[i] = (byte) decimals;

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record TokenTransferCheckedIxData(Discriminator discriminator, long lamports, int decimals) implements Borsh {  

    public static TokenTransferCheckedIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static TokenTransferCheckedIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      i += 8;
      final var decimals = _data[i] & 0xFF;
      return new TokenTransferCheckedIxData(discriminator, lamports, decimals);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lamports);
      i += 8;
      _data[i] = (byte) decimals;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_MINT_DISCRIMINATOR = toDiscriminator(212, 203, 57, 78, 75, 245, 222, 5);

  public static Instruction updateMint(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction updateState(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  public static Instruction withdraw(final AccountMeta invokedGlamProtocolProgramMeta,
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

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
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

  private GlamProtocolProgram() {
  }
}
