package software.sava.anchor.programs.glam.anchor;

import java.util.List;
import java.util.OptionalInt;

import software.sava.anchor.programs.glam.anchor.types.AddLiquiditySingleSidePreciseParameter2;
import software.sava.anchor.programs.glam.anchor.types.BinLiquidityReduction;
import software.sava.anchor.programs.glam.anchor.types.InitObligationArgs;
import software.sava.anchor.programs.glam.anchor.types.LiquidityParameter;
import software.sava.anchor.programs.glam.anchor.types.LiquidityParameterByStrategy;
import software.sava.anchor.programs.glam.anchor.types.MarketType;
import software.sava.anchor.programs.glam.anchor.types.MintModel;
import software.sava.anchor.programs.glam.anchor.types.ModifyOrderParams;
import software.sava.anchor.programs.glam.anchor.types.OrderParams;
import software.sava.anchor.programs.glam.anchor.types.PositionDirection;
import software.sava.anchor.programs.glam.anchor.types.PriceDenom;
import software.sava.anchor.programs.glam.anchor.types.RemainingAccountsInfo;
import software.sava.anchor.programs.glam.anchor.types.SettlePnlMode;
import software.sava.anchor.programs.glam.anchor.types.StateModel;
import software.sava.anchor.programs.glam.anchor.types.VoteAuthorizeEnum;
import software.sava.anchor.programs.glam.anchor.types.WithdrawUnit;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

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
                                  final PublicKey tokenMintKey,
                                  final PublicKey signerAtaKey,
                                  final PublicKey escrowAtaKey,
                                  final PublicKey claimTokenProgramKey,
                                  final int mintId) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamEscrowKey),
      createWritableSigner(signerKey),
      createRead(tokenMintKey),
      createWrite(signerAtaKey),
      createWrite(escrowAtaKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(claimTokenProgramKey),
      createRead(solanaAccounts.associatedTokenAccountProgram())
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
      createWrite(glamStateKey),
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

  public static final Discriminator DRIFT_DISTRIBUTOR_NEW_CLAIM_DISCRIMINATOR = toDiscriminator(204, 159, 250, 46, 124, 193, 250, 10);

  public static Instruction driftDistributorNewClaim(final AccountMeta invokedGlamProtocolProgramMeta,
                                                     final SolanaAccounts solanaAccounts,
                                                     final PublicKey glamStateKey,
                                                     final PublicKey glamVaultKey,
                                                     final PublicKey glamSignerKey,
                                                     final PublicKey cpiProgramKey,
                                                     final PublicKey distributorKey,
                                                     final PublicKey claimStatusKey,
                                                     final PublicKey fromKey,
                                                     final PublicKey toKey,
                                                     final PublicKey tokenProgramKey,
                                                     final long amountUnlocked,
                                                     final long amountLocked,
                                                     final byte[][] proof) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(distributorKey),
      createWrite(claimStatusKey),
      createWrite(fromKey),
      createWrite(toKey),
      createRead(tokenProgramKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[24 + Borsh.lenVectorArray(proof)];
    int i = writeDiscriminator(DRIFT_DISTRIBUTOR_NEW_CLAIM_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountUnlocked);
    i += 8;
    putInt64LE(_data, i, amountLocked);
    i += 8;
    Borsh.writeVectorArray(proof, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record DriftDistributorNewClaimIxData(Discriminator discriminator,
                                               long amountUnlocked,
                                               long amountLocked,
                                               byte[][] proof) implements Borsh {  

    public static DriftDistributorNewClaimIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static DriftDistributorNewClaimIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amountUnlocked = getInt64LE(_data, i);
      i += 8;
      final var amountLocked = getInt64LE(_data, i);
      i += 8;
      final var proof = Borsh.readMultiDimensionbyteVectorArray(32, _data, i);
      return new DriftDistributorNewClaimIxData(discriminator, amountUnlocked, amountLocked, proof);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amountUnlocked);
      i += 8;
      putInt64LE(_data, i, amountLocked);
      i += 8;
      i += Borsh.writeVectorArray(proof, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + 8 + Borsh.lenVectorArray(proof);
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
      createWrite(glamStateKey),
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
    public static final int NAME_LEN = 32;

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

  public static final Discriminator DRIFT_SETTLE_MULTIPLE_PNLS_DISCRIMINATOR = toDiscriminator(100, 72, 3, 45, 69, 37, 10, 144);

  public static Instruction driftSettleMultiplePnls(final AccountMeta invokedGlamProtocolProgramMeta,
                                                    final PublicKey glamStateKey,
                                                    final PublicKey glamVaultKey,
                                                    final PublicKey glamSignerKey,
                                                    final PublicKey cpiProgramKey,
                                                    final PublicKey stateKey,
                                                    final PublicKey userKey,
                                                    final PublicKey spotMarketVaultKey,
                                                    final short[] marketIndexes,
                                                    final SettlePnlMode mode) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createRead(stateKey),
      createWrite(userKey),
      createRead(spotMarketVaultKey)
    );

    final byte[] _data = new byte[8 + Borsh.lenVector(marketIndexes) + Borsh.len(mode)];
    int i = writeDiscriminator(DRIFT_SETTLE_MULTIPLE_PNLS_DISCRIMINATOR, _data, 0);
    i += Borsh.writeVector(marketIndexes, _data, i);
    Borsh.write(mode, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record DriftSettleMultiplePnlsIxData(Discriminator discriminator, short[] marketIndexes, SettlePnlMode mode) implements Borsh {  

    public static DriftSettleMultiplePnlsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static DriftSettleMultiplePnlsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var marketIndexes = Borsh.readshortVector(_data, i);
      i += Borsh.lenVector(marketIndexes);
      final var mode = SettlePnlMode.read(_data, i);
      return new DriftSettleMultiplePnlsIxData(discriminator, marketIndexes, mode);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(marketIndexes, _data, i);
      i += Borsh.write(mode, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(marketIndexes) + Borsh.len(mode);
    }
  }

  public static final Discriminator DRIFT_SETTLE_PNL_DISCRIMINATOR = toDiscriminator(161, 254, 255, 100, 140, 113, 169, 175);

  public static Instruction driftSettlePnl(final AccountMeta invokedGlamProtocolProgramMeta,
                                           final PublicKey glamStateKey,
                                           final PublicKey glamVaultKey,
                                           final PublicKey glamSignerKey,
                                           final PublicKey cpiProgramKey,
                                           final PublicKey stateKey,
                                           final PublicKey userKey,
                                           final PublicKey spotMarketVaultKey,
                                           final int marketIndex) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createRead(stateKey),
      createWrite(userKey),
      createRead(spotMarketVaultKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(DRIFT_SETTLE_PNL_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record DriftSettlePnlIxData(Discriminator discriminator, int marketIndex) implements Borsh {  

    public static DriftSettlePnlIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static DriftSettlePnlIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var marketIndex = getInt16LE(_data, i);
      return new DriftSettlePnlIxData(discriminator, marketIndex);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, marketIndex);
      i += 2;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
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

  public static final Discriminator DRIFT_UPDATE_USER_POOL_ID_DISCRIMINATOR = toDiscriminator(44, 176, 143, 200, 153, 248, 221, 218);

  public static Instruction driftUpdateUserPoolId(final AccountMeta invokedGlamProtocolProgramMeta,
                                                  final PublicKey glamStateKey,
                                                  final PublicKey glamVaultKey,
                                                  final PublicKey glamSignerKey,
                                                  final PublicKey cpiProgramKey,
                                                  final PublicKey userKey,
                                                  final int subAccountId,
                                                  final int poolId) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(userKey)
    );

    final byte[] _data = new byte[11];
    int i = writeDiscriminator(DRIFT_UPDATE_USER_POOL_ID_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, subAccountId);
    i += 2;
    _data[i] = (byte) poolId;

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record DriftUpdateUserPoolIdIxData(Discriminator discriminator, int subAccountId, int poolId) implements Borsh {  

    public static DriftUpdateUserPoolIdIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 11;

    public static DriftUpdateUserPoolIdIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var subAccountId = getInt16LE(_data, i);
      i += 2;
      final var poolId = _data[i] & 0xFF;
      return new DriftUpdateUserPoolIdIxData(discriminator, subAccountId, poolId);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, subAccountId);
      i += 2;
      _data[i] = (byte) poolId;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DRIFT_VAULTS_CANCEL_REQUEST_WITHDRAW_DISCRIMINATOR = toDiscriminator(241, 196, 156, 180, 21, 155, 228, 125);

  public static Instruction driftVaultsCancelRequestWithdraw(final AccountMeta invokedGlamProtocolProgramMeta,
                                                             final PublicKey glamStateKey,
                                                             final PublicKey glamVaultKey,
                                                             final PublicKey glamSignerKey,
                                                             final PublicKey cpiProgramKey,
                                                             final PublicKey vaultKey,
                                                             final PublicKey vaultDepositorKey,
                                                             final PublicKey driftUserStatsKey,
                                                             final PublicKey driftUserKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(vaultKey),
      createWrite(vaultDepositorKey),
      createRead(driftUserStatsKey),
      createRead(driftUserKey)
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, DRIFT_VAULTS_CANCEL_REQUEST_WITHDRAW_DISCRIMINATOR);
  }

  public static final Discriminator DRIFT_VAULTS_DEPOSIT_DISCRIMINATOR = toDiscriminator(95, 223, 42, 76, 37, 21, 176, 73);

  public static Instruction driftVaultsDeposit(final AccountMeta invokedGlamProtocolProgramMeta,
                                               final PublicKey glamStateKey,
                                               final PublicKey glamVaultKey,
                                               final PublicKey glamSignerKey,
                                               final PublicKey cpiProgramKey,
                                               final PublicKey vaultKey,
                                               final PublicKey vaultDepositorKey,
                                               final PublicKey vaultTokenAccountKey,
                                               final PublicKey driftUserStatsKey,
                                               final PublicKey driftUserKey,
                                               final PublicKey driftStateKey,
                                               final PublicKey driftSpotMarketVaultKey,
                                               final PublicKey userTokenAccountKey,
                                               final PublicKey driftProgramKey,
                                               final PublicKey tokenProgramKey,
                                               final long amount) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(vaultKey),
      createWrite(vaultDepositorKey),
      createWrite(vaultTokenAccountKey),
      createWrite(driftUserStatsKey),
      createWrite(driftUserKey),
      createRead(driftStateKey),
      createWrite(driftSpotMarketVaultKey),
      createWrite(userTokenAccountKey),
      createRead(driftProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(DRIFT_VAULTS_DEPOSIT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record DriftVaultsDepositIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static DriftVaultsDepositIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static DriftVaultsDepositIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new DriftVaultsDepositIxData(discriminator, amount);
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

  public static final Discriminator DRIFT_VAULTS_INITIALIZE_VAULT_DEPOSITOR_DISCRIMINATOR = toDiscriminator(109, 183, 50, 62, 60, 195, 192, 51);

  public static Instruction driftVaultsInitializeVaultDepositor(final AccountMeta invokedGlamProtocolProgramMeta,
                                                                final SolanaAccounts solanaAccounts,
                                                                final PublicKey glamStateKey,
                                                                final PublicKey glamVaultKey,
                                                                final PublicKey glamSignerKey,
                                                                final PublicKey cpiProgramKey,
                                                                final PublicKey vaultKey,
                                                                final PublicKey vaultDepositorKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createRead(vaultKey),
      createWrite(vaultDepositorKey),
      createRead(solanaAccounts.rentSysVar()),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, DRIFT_VAULTS_INITIALIZE_VAULT_DEPOSITOR_DISCRIMINATOR);
  }

  public static final Discriminator DRIFT_VAULTS_REQUEST_WITHDRAW_DISCRIMINATOR = toDiscriminator(19, 53, 222, 51, 44, 215, 35, 82);

  public static Instruction driftVaultsRequestWithdraw(final AccountMeta invokedGlamProtocolProgramMeta,
                                                       final PublicKey glamStateKey,
                                                       final PublicKey glamVaultKey,
                                                       final PublicKey glamSignerKey,
                                                       final PublicKey cpiProgramKey,
                                                       final PublicKey vaultKey,
                                                       final PublicKey vaultDepositorKey,
                                                       final PublicKey driftUserStatsKey,
                                                       final PublicKey driftUserKey,
                                                       final long withdrawAmount,
                                                       final WithdrawUnit withdrawUnit) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(vaultKey),
      createWrite(vaultDepositorKey),
      createRead(driftUserStatsKey),
      createRead(driftUserKey)
    );

    final byte[] _data = new byte[16 + Borsh.len(withdrawUnit)];
    int i = writeDiscriminator(DRIFT_VAULTS_REQUEST_WITHDRAW_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, withdrawAmount);
    i += 8;
    Borsh.write(withdrawUnit, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record DriftVaultsRequestWithdrawIxData(Discriminator discriminator, long withdrawAmount, WithdrawUnit withdrawUnit) implements Borsh {  

    public static DriftVaultsRequestWithdrawIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static DriftVaultsRequestWithdrawIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var withdrawAmount = getInt64LE(_data, i);
      i += 8;
      final var withdrawUnit = WithdrawUnit.read(_data, i);
      return new DriftVaultsRequestWithdrawIxData(discriminator, withdrawAmount, withdrawUnit);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, withdrawAmount);
      i += 8;
      i += Borsh.write(withdrawUnit, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DRIFT_VAULTS_WITHDRAW_DISCRIMINATOR = toDiscriminator(58, 127, 150, 177, 66, 45, 5, 30);

  public static Instruction driftVaultsWithdraw(final AccountMeta invokedGlamProtocolProgramMeta,
                                                final PublicKey glamStateKey,
                                                final PublicKey glamVaultKey,
                                                final PublicKey glamSignerKey,
                                                final PublicKey cpiProgramKey,
                                                final PublicKey vaultKey,
                                                final PublicKey vaultDepositorKey,
                                                final PublicKey vaultTokenAccountKey,
                                                final PublicKey driftUserStatsKey,
                                                final PublicKey driftUserKey,
                                                final PublicKey driftStateKey,
                                                final PublicKey driftSpotMarketVaultKey,
                                                final PublicKey driftSignerKey,
                                                final PublicKey userTokenAccountKey,
                                                final PublicKey driftProgramKey,
                                                final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(vaultKey),
      createWrite(vaultDepositorKey),
      createWrite(vaultTokenAccountKey),
      createWrite(driftUserStatsKey),
      createWrite(driftUserKey),
      createRead(driftStateKey),
      createWrite(driftSpotMarketVaultKey),
      createRead(driftSignerKey),
      createWrite(userTokenAccountKey),
      createRead(driftProgramKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, DRIFT_VAULTS_WITHDRAW_DISCRIMINATOR);
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

  public static final Discriminator EMERGENCY_UPDATE_MINT_DISCRIMINATOR = toDiscriminator(141, 210, 26, 160, 120, 140, 28, 239);

  public static Instruction emergencyUpdateMint(final AccountMeta invokedGlamProtocolProgramMeta,
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
    int i = writeDiscriminator(EMERGENCY_UPDATE_MINT_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mintId;
    ++i;
    Borsh.write(mintModel, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record EmergencyUpdateMintIxData(Discriminator discriminator, int mintId, MintModel mintModel) implements Borsh {  

    public static EmergencyUpdateMintIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static EmergencyUpdateMintIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintId = _data[i] & 0xFF;
      ++i;
      final var mintModel = MintModel.read(_data, i);
      return new EmergencyUpdateMintIxData(discriminator, mintId, mintModel);
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

  public static final Discriminator EMERGENCY_UPDATE_STATE_DISCRIMINATOR = toDiscriminator(156, 211, 55, 70, 92, 37, 190, 66);

  public static Instruction emergencyUpdateState(final AccountMeta invokedGlamProtocolProgramMeta,
                                                 final PublicKey glamStateKey,
                                                 final PublicKey glamSignerKey,
                                                 final StateModel state) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(state)];
    int i = writeDiscriminator(EMERGENCY_UPDATE_STATE_DISCRIMINATOR, _data, 0);
    Borsh.write(state, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record EmergencyUpdateStateIxData(Discriminator discriminator, StateModel state) implements Borsh {  

    public static EmergencyUpdateStateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static EmergencyUpdateStateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var state = StateModel.read(_data, i);
      return new EmergencyUpdateStateIxData(discriminator, state);
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

  public static final Discriminator EXTEND_STATE_DISCRIMINATOR = toDiscriminator(34, 147, 151, 206, 134, 128, 82, 228);

  public static Instruction extendState(final AccountMeta invokedGlamProtocolProgramMeta,
                                        final SolanaAccounts solanaAccounts,
                                        final PublicKey glamStateKey,
                                        final PublicKey glamSignerKey,
                                        final int bytes) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(EXTEND_STATE_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, bytes);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record ExtendStateIxData(Discriminator discriminator, int bytes) implements Borsh {  

    public static ExtendStateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 12;

    public static ExtendStateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var bytes = getInt32LE(_data, i);
      return new ExtendStateIxData(discriminator, bytes);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt32LE(_data, i, bytes);
      i += 4;
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
                                                final PublicKey toPolicyAccountKey,
                                                final PublicKey policiesProgramKey,
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
      createWrite(requireNonNullElse(toPolicyAccountKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.token2022Program()),
      createRead(policiesProgramKey)
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
                                    final PublicKey depositTokenProgramKey,
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
      createRead(depositTokenProgramKey),
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

  public static final Discriminator JUPITER_VOTE_NEW_ESCROW_DISCRIMINATOR = toDiscriminator(255, 87, 157, 219, 61, 178, 144, 159);

  public static Instruction jupiterVoteNewEscrow(final AccountMeta invokedGlamProtocolProgramMeta,
                                                 final SolanaAccounts solanaAccounts,
                                                 final PublicKey glamStateKey,
                                                 final PublicKey glamVaultKey,
                                                 final PublicKey glamSignerKey,
                                                 final PublicKey cpiProgramKey,
                                                 final PublicKey lockerKey,
                                                 final PublicKey escrowKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(lockerKey),
      createWrite(escrowKey),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, JUPITER_VOTE_NEW_ESCROW_DISCRIMINATOR);
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

  public static final Discriminator KAMINO_FARM_HARVEST_REWARD_DISCRIMINATOR = toDiscriminator(64, 132, 133, 175, 224, 147, 145, 119);

  public static Instruction kaminoFarmHarvestReward(final AccountMeta invokedGlamProtocolProgramMeta,
                                                    final PublicKey glamStateKey,
                                                    final PublicKey glamVaultKey,
                                                    final PublicKey glamSignerKey,
                                                    final PublicKey cpiProgramKey,
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
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(userStateKey),
      createWrite(farmStateKey),
      createRead(globalConfigKey),
      createRead(rewardMintKey),
      createWrite(userRewardAtaKey),
      createWrite(rewardsVaultKey),
      createWrite(rewardsTreasuryVaultKey),
      createRead(farmVaultsAuthorityKey),
      createRead(requireNonNullElse(scopePricesKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(KAMINO_FARM_HARVEST_REWARD_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, rewardIndex);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record KaminoFarmHarvestRewardIxData(Discriminator discriminator, long rewardIndex) implements Borsh {  

    public static KaminoFarmHarvestRewardIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static KaminoFarmHarvestRewardIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var rewardIndex = getInt64LE(_data, i);
      return new KaminoFarmHarvestRewardIxData(discriminator, rewardIndex);
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

  public static final Discriminator KAMINO_LENDING_BORROW_OBLIGATION_LIQUIDITY_V_2_DISCRIMINATOR = toDiscriminator(175, 198, 39, 162, 103, 76, 51, 121);

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
      createWrite(requireNonNullElse(referrerTokenStateKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(tokenProgramKey),
      createRead(instructionSysvarAccountKey),
      createWrite(requireNonNullElse(obligationFarmUserStateKey, invokedGlamProtocolProgramMeta.publicKey())),
      createWrite(requireNonNullElse(reserveFarmStateKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(farmsProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(KAMINO_LENDING_BORROW_OBLIGATION_LIQUIDITY_V_2_DISCRIMINATOR, _data, 0);
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

  public static final Discriminator KAMINO_LENDING_DEPOSIT_RESERVE_LIQUIDITY_AND_OBLIGATION_COLLATERAL_V_2_DISCRIMINATOR = toDiscriminator(93, 120, 106, 112, 40, 45, 84, 32);

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
      createRead(requireNonNullElse(placeholderUserDestinationCollateralKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(collateralTokenProgramKey),
      createRead(liquidityTokenProgramKey),
      createRead(instructionSysvarAccountKey),
      createWrite(requireNonNullElse(obligationFarmUserStateKey, invokedGlamProtocolProgramMeta.publicKey())),
      createWrite(requireNonNullElse(reserveFarmStateKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(farmsProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(KAMINO_LENDING_DEPOSIT_RESERVE_LIQUIDITY_AND_OBLIGATION_COLLATERAL_V_2_DISCRIMINATOR, _data, 0);
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
      createWrite(glamStateKey),
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
      createRead(requireNonNullElse(referrerUserMetadataKey, invokedGlamProtocolProgramMeta.publicKey())),
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

  public static final Discriminator KAMINO_LENDING_REPAY_OBLIGATION_LIQUIDITY_V_2_DISCRIMINATOR = toDiscriminator(135, 57, 236, 69, 153, 77, 15, 88);

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
      createWrite(requireNonNullElse(obligationFarmUserStateKey, invokedGlamProtocolProgramMeta.publicKey())),
      createWrite(requireNonNullElse(reserveFarmStateKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(lendingMarketAuthorityKey),
      createRead(farmsProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(KAMINO_LENDING_REPAY_OBLIGATION_LIQUIDITY_V_2_DISCRIMINATOR, _data, 0);
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

  public static final Discriminator KAMINO_LENDING_WITHDRAW_OBLIGATION_COLLATERAL_AND_REDEEM_RESERVE_COLLATERAL_V_2_DISCRIMINATOR = toDiscriminator(249, 60, 252, 239, 136, 53, 181, 3);

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
      createWrite(glamStateKey),
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
      createRead(requireNonNullElse(placeholderUserDestinationCollateralKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(collateralTokenProgramKey),
      createRead(liquidityTokenProgramKey),
      createRead(instructionSysvarAccountKey),
      createWrite(requireNonNullElse(obligationFarmUserStateKey, invokedGlamProtocolProgramMeta.publicKey())),
      createWrite(requireNonNullElse(reserveFarmStateKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(farmsProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(KAMINO_LENDING_WITHDRAW_OBLIGATION_COLLATERAL_AND_REDEEM_RESERVE_COLLATERAL_V_2_DISCRIMINATOR, _data, 0);
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

  public static final Discriminator KAMINO_VAULTS_DEPOSIT_DISCRIMINATOR = toDiscriminator(209, 133, 37, 193, 192, 217, 55, 40);

  public static Instruction kaminoVaultsDeposit(final AccountMeta invokedGlamProtocolProgramMeta,
                                                final PublicKey glamStateKey,
                                                final PublicKey glamVaultKey,
                                                final PublicKey glamSignerKey,
                                                final PublicKey cpiProgramKey,
                                                final PublicKey vaultStateKey,
                                                final PublicKey tokenVaultKey,
                                                final PublicKey tokenMintKey,
                                                final PublicKey baseVaultAuthorityKey,
                                                final PublicKey sharesMintKey,
                                                final PublicKey userTokenAtaKey,
                                                final PublicKey userSharesAtaKey,
                                                final PublicKey klendProgramKey,
                                                final PublicKey tokenProgramKey,
                                                final PublicKey sharesTokenProgramKey,
                                                final PublicKey eventAuthorityKey,
                                                final PublicKey programKey,
                                                final long amount) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(vaultStateKey),
      createWrite(tokenVaultKey),
      createRead(tokenMintKey),
      createRead(baseVaultAuthorityKey),
      createWrite(sharesMintKey),
      createWrite(userTokenAtaKey),
      createWrite(userSharesAtaKey),
      createRead(klendProgramKey),
      createRead(tokenProgramKey),
      createRead(sharesTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(KAMINO_VAULTS_DEPOSIT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record KaminoVaultsDepositIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static KaminoVaultsDepositIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static KaminoVaultsDepositIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new KaminoVaultsDepositIxData(discriminator, amount);
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

  public static final Discriminator KAMINO_VAULTS_WITHDRAW_DISCRIMINATOR = toDiscriminator(82, 106, 49, 86, 156, 15, 87, 8);

  public static Instruction kaminoVaultsWithdraw(final AccountMeta invokedGlamProtocolProgramMeta,
                                                 final PublicKey glamStateKey,
                                                 final PublicKey glamVaultKey,
                                                 final PublicKey glamSignerKey,
                                                 final PublicKey cpiProgramKey,
                                                 final PublicKey withdrawFromAvailableVaultStateKey,
                                                 final PublicKey withdrawFromAvailableTokenVaultKey,
                                                 final PublicKey withdrawFromAvailableBaseVaultAuthorityKey,
                                                 final PublicKey withdrawFromAvailableUserTokenAtaKey,
                                                 final PublicKey withdrawFromAvailableTokenMintKey,
                                                 final PublicKey withdrawFromAvailableUserSharesAtaKey,
                                                 final PublicKey withdrawFromAvailableSharesMintKey,
                                                 final PublicKey withdrawFromAvailableTokenProgramKey,
                                                 final PublicKey withdrawFromAvailableSharesTokenProgramKey,
                                                 final PublicKey withdrawFromAvailableKlendProgramKey,
                                                 final PublicKey withdrawFromAvailableEventAuthorityKey,
                                                 final PublicKey withdrawFromAvailableProgramKey,
                                                 final PublicKey withdrawFromReserveVaultStateKey,
                                                 final PublicKey withdrawFromReserveReserveKey,
                                                 final PublicKey withdrawFromReserveCtokenVaultKey,
                                                 final PublicKey withdrawFromReserveLendingMarketKey,
                                                 final PublicKey withdrawFromReserveLendingMarketAuthorityKey,
                                                 final PublicKey withdrawFromReserveReserveLiquiditySupplyKey,
                                                 final PublicKey withdrawFromReserveReserveCollateralMintKey,
                                                 final PublicKey withdrawFromReserveReserveCollateralTokenProgramKey,
                                                 final PublicKey withdrawFromReserveInstructionSysvarAccountKey,
                                                 final PublicKey eventAuthorityKey,
                                                 final PublicKey programKey,
                                                 final long amount) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(withdrawFromAvailableVaultStateKey),
      createWrite(withdrawFromAvailableTokenVaultKey),
      createRead(withdrawFromAvailableBaseVaultAuthorityKey),
      createWrite(withdrawFromAvailableUserTokenAtaKey),
      createWrite(withdrawFromAvailableTokenMintKey),
      createWrite(withdrawFromAvailableUserSharesAtaKey),
      createWrite(withdrawFromAvailableSharesMintKey),
      createRead(withdrawFromAvailableTokenProgramKey),
      createRead(withdrawFromAvailableSharesTokenProgramKey),
      createRead(withdrawFromAvailableKlendProgramKey),
      createRead(withdrawFromAvailableEventAuthorityKey),
      createRead(withdrawFromAvailableProgramKey),
      createWrite(withdrawFromReserveVaultStateKey),
      createWrite(withdrawFromReserveReserveKey),
      createWrite(withdrawFromReserveCtokenVaultKey),
      createRead(withdrawFromReserveLendingMarketKey),
      createRead(withdrawFromReserveLendingMarketAuthorityKey),
      createWrite(withdrawFromReserveReserveLiquiditySupplyKey),
      createWrite(withdrawFromReserveReserveCollateralMintKey),
      createRead(withdrawFromReserveReserveCollateralTokenProgramKey),
      createRead(withdrawFromReserveInstructionSysvarAccountKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(KAMINO_VAULTS_WITHDRAW_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record KaminoVaultsWithdrawIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static KaminoVaultsWithdrawIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static KaminoVaultsWithdrawIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new KaminoVaultsWithdrawIxData(discriminator, amount);
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
      createWrite(glamStateKey),
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

  public static final Discriminator MARINADE_WITHDRAW_STAKE_ACCOUNT_DISCRIMINATOR = toDiscriminator(88, 200, 107, 134, 244, 89, 194, 111);

  public static Instruction marinadeWithdrawStakeAccount(final AccountMeta invokedGlamProtocolProgramMeta,
                                                         final SolanaAccounts solanaAccounts,
                                                         final PublicKey glamStateKey,
                                                         final PublicKey glamVaultKey,
                                                         final PublicKey glamSignerKey,
                                                         final PublicKey cpiProgramKey,
                                                         final PublicKey stateKey,
                                                         final PublicKey msolMintKey,
                                                         final PublicKey burnMsolFromKey,
                                                         final PublicKey treasuryMsolAccountKey,
                                                         final PublicKey validatorListKey,
                                                         final PublicKey stakeListKey,
                                                         final PublicKey stakeWithdrawAuthorityKey,
                                                         final PublicKey stakeDepositAuthorityKey,
                                                         final PublicKey stakeAccountKey,
                                                         final PublicKey splitStakeAccountKey,
                                                         final PublicKey clockKey,
                                                         final PublicKey tokenProgramKey,
                                                         final PublicKey stakeProgramKey,
                                                         final int stakeIndex,
                                                         final int validatorIndex,
                                                         final long msolAmount,
                                                         final PublicKey beneficiary) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(stateKey),
      createWrite(msolMintKey),
      createWrite(burnMsolFromKey),
      createWrite(treasuryMsolAccountKey),
      createWrite(validatorListKey),
      createWrite(stakeListKey),
      createRead(stakeWithdrawAuthorityKey),
      createRead(stakeDepositAuthorityKey),
      createWrite(stakeAccountKey),
      createWritableSigner(splitStakeAccountKey),
      createRead(clockKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(tokenProgramKey),
      createRead(stakeProgramKey)
    );

    final byte[] _data = new byte[56];
    int i = writeDiscriminator(MARINADE_WITHDRAW_STAKE_ACCOUNT_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, stakeIndex);
    i += 4;
    putInt32LE(_data, i, validatorIndex);
    i += 4;
    putInt64LE(_data, i, msolAmount);
    i += 8;
    beneficiary.write(_data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record MarinadeWithdrawStakeAccountIxData(Discriminator discriminator,
                                                   int stakeIndex,
                                                   int validatorIndex,
                                                   long msolAmount,
                                                   PublicKey beneficiary) implements Borsh {  

    public static MarinadeWithdrawStakeAccountIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 56;

    public static MarinadeWithdrawStakeAccountIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var stakeIndex = getInt32LE(_data, i);
      i += 4;
      final var validatorIndex = getInt32LE(_data, i);
      i += 4;
      final var msolAmount = getInt64LE(_data, i);
      i += 8;
      final var beneficiary = readPubKey(_data, i);
      return new MarinadeWithdrawStakeAccountIxData(discriminator,
                                                    stakeIndex,
                                                    validatorIndex,
                                                    msolAmount,
                                                    beneficiary);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt32LE(_data, i, stakeIndex);
      i += 4;
      putInt32LE(_data, i, validatorIndex);
      i += 4;
      putInt64LE(_data, i, msolAmount);
      i += 8;
      beneficiary.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator MERKLE_DISTRIBUTOR_NEW_CLAIM_AND_STAKE_DISCRIMINATOR = toDiscriminator(203, 167, 65, 87, 208, 161, 91, 207);

  public static Instruction merkleDistributorNewClaimAndStake(final AccountMeta invokedGlamProtocolProgramMeta,
                                                              final SolanaAccounts solanaAccounts,
                                                              final PublicKey glamStateKey,
                                                              final PublicKey glamVaultKey,
                                                              final PublicKey glamSignerKey,
                                                              final PublicKey cpiProgramKey,
                                                              final PublicKey distributorKey,
                                                              final PublicKey claimStatusKey,
                                                              final PublicKey fromKey,
                                                              final PublicKey tokenProgramKey,
                                                              final PublicKey voterProgramKey,
                                                              final PublicKey lockerKey,
                                                              final PublicKey escrowKey,
                                                              final PublicKey escrowTokensKey,
                                                              final long amountUnlocked,
                                                              final long amountLocked,
                                                              final byte[][] proof) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(distributorKey),
      createWrite(claimStatusKey),
      createWrite(fromKey),
      createRead(tokenProgramKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(voterProgramKey),
      createWrite(lockerKey),
      createWrite(escrowKey),
      createWrite(escrowTokensKey)
    );

    final byte[] _data = new byte[24 + Borsh.lenVectorArray(proof)];
    int i = writeDiscriminator(MERKLE_DISTRIBUTOR_NEW_CLAIM_AND_STAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountUnlocked);
    i += 8;
    putInt64LE(_data, i, amountLocked);
    i += 8;
    Borsh.writeVectorArray(proof, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record MerkleDistributorNewClaimAndStakeIxData(Discriminator discriminator,
                                                        long amountUnlocked,
                                                        long amountLocked,
                                                        byte[][] proof) implements Borsh {  

    public static MerkleDistributorNewClaimAndStakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static MerkleDistributorNewClaimAndStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amountUnlocked = getInt64LE(_data, i);
      i += 8;
      final var amountLocked = getInt64LE(_data, i);
      i += 8;
      final var proof = Borsh.readMultiDimensionbyteVectorArray(32, _data, i);
      return new MerkleDistributorNewClaimAndStakeIxData(discriminator, amountUnlocked, amountLocked, proof);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amountUnlocked);
      i += 8;
      putInt64LE(_data, i, amountLocked);
      i += 8;
      i += Borsh.writeVectorArray(proof, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + 8 + Borsh.lenVectorArray(proof);
    }
  }

  public static final Discriminator METEORA_DLMM_ADD_LIQUIDITY_2_DISCRIMINATOR = toDiscriminator(248, 123, 81, 94, 137, 10, 79, 81);

  public static Instruction meteoraDlmmAddLiquidity2(final AccountMeta invokedGlamProtocolProgramMeta,
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
                                                     final PublicKey tokenXProgramKey,
                                                     final PublicKey tokenYProgramKey,
                                                     final PublicKey eventAuthorityKey,
                                                     final PublicKey programKey,
                                                     final LiquidityParameter liquidityParameter,
                                                     final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedGlamProtocolProgramMeta.publicKey())),
      createWrite(userTokenXKey),
      createWrite(userTokenYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(liquidityParameter) + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(METEORA_DLMM_ADD_LIQUIDITY_2_DISCRIMINATOR, _data, 0);
    i += Borsh.write(liquidityParameter, _data, i);
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record MeteoraDlmmAddLiquidity2IxData(Discriminator discriminator, LiquidityParameter liquidityParameter, RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static MeteoraDlmmAddLiquidity2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static MeteoraDlmmAddLiquidity2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityParameter = LiquidityParameter.read(_data, i);
      i += Borsh.len(liquidityParameter);
      final var remainingAccountsInfo = RemainingAccountsInfo.read(_data, i);
      return new MeteoraDlmmAddLiquidity2IxData(discriminator, liquidityParameter, remainingAccountsInfo);
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

  public static final Discriminator METEORA_DLMM_ADD_LIQUIDITY_BY_STRATEGY_2_DISCRIMINATOR = toDiscriminator(219, 171, 159, 202, 167, 192, 209, 25);

  public static Instruction meteoraDlmmAddLiquidityByStrategy2(final AccountMeta invokedGlamProtocolProgramMeta,
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
                                                               final PublicKey tokenXProgramKey,
                                                               final PublicKey tokenYProgramKey,
                                                               final PublicKey eventAuthorityKey,
                                                               final PublicKey programKey,
                                                               final LiquidityParameterByStrategy liquidityParameter,
                                                               final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedGlamProtocolProgramMeta.publicKey())),
      createWrite(userTokenXKey),
      createWrite(userTokenYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(liquidityParameter) + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(METEORA_DLMM_ADD_LIQUIDITY_BY_STRATEGY_2_DISCRIMINATOR, _data, 0);
    i += Borsh.write(liquidityParameter, _data, i);
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record MeteoraDlmmAddLiquidityByStrategy2IxData(Discriminator discriminator, LiquidityParameterByStrategy liquidityParameter, RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static MeteoraDlmmAddLiquidityByStrategy2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static MeteoraDlmmAddLiquidityByStrategy2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityParameter = LiquidityParameterByStrategy.read(_data, i);
      i += Borsh.len(liquidityParameter);
      final var remainingAccountsInfo = RemainingAccountsInfo.read(_data, i);
      return new MeteoraDlmmAddLiquidityByStrategy2IxData(discriminator, liquidityParameter, remainingAccountsInfo);
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

  public static final Discriminator METEORA_DLMM_ADD_LIQUIDITY_ONE_SIDE_PRECISE_2_DISCRIMINATOR = toDiscriminator(60, 137, 107, 235, 107, 209, 19, 106);

  public static Instruction meteoraDlmmAddLiquidityOneSidePrecise2(final AccountMeta invokedGlamProtocolProgramMeta,
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
                                                                   final PublicKey tokenProgramKey,
                                                                   final PublicKey eventAuthorityKey,
                                                                   final PublicKey programKey,
                                                                   final AddLiquiditySingleSidePreciseParameter2 parameter,
                                                                   final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedGlamProtocolProgramMeta.publicKey())),
      createWrite(userTokenKey),
      createWrite(reserveKey),
      createRead(tokenMintKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(parameter) + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(METEORA_DLMM_ADD_LIQUIDITY_ONE_SIDE_PRECISE_2_DISCRIMINATOR, _data, 0);
    i += Borsh.write(parameter, _data, i);
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record MeteoraDlmmAddLiquidityOneSidePrecise2IxData(Discriminator discriminator, AddLiquiditySingleSidePreciseParameter2 parameter, RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static MeteoraDlmmAddLiquidityOneSidePrecise2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static MeteoraDlmmAddLiquidityOneSidePrecise2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var parameter = AddLiquiditySingleSidePreciseParameter2.read(_data, i);
      i += Borsh.len(parameter);
      final var remainingAccountsInfo = RemainingAccountsInfo.read(_data, i);
      return new MeteoraDlmmAddLiquidityOneSidePrecise2IxData(discriminator, parameter, remainingAccountsInfo);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(parameter, _data, i);
      i += Borsh.write(remainingAccountsInfo, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(parameter) + Borsh.len(remainingAccountsInfo);
    }
  }

  public static final Discriminator METEORA_DLMM_CLAIM_FEE_2_DISCRIMINATOR = toDiscriminator(152, 214, 221, 166, 191, 34, 17, 189);

  public static Instruction meteoraDlmmClaimFee2(final AccountMeta invokedGlamProtocolProgramMeta,
                                                 final PublicKey glamStateKey,
                                                 final PublicKey glamVaultKey,
                                                 final PublicKey glamSignerKey,
                                                 final PublicKey cpiProgramKey,
                                                 final PublicKey lbPairKey,
                                                 final PublicKey positionKey,
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
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(lbPairKey),
      createWrite(positionKey),
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
    int i = writeDiscriminator(METEORA_DLMM_CLAIM_FEE_2_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, minBinId);
    i += 4;
    putInt32LE(_data, i, maxBinId);
    i += 4;
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record MeteoraDlmmClaimFee2IxData(Discriminator discriminator,
                                           int minBinId,
                                           int maxBinId,
                                           RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static MeteoraDlmmClaimFee2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static MeteoraDlmmClaimFee2IxData read(final byte[] _data, final int offset) {
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
      return new MeteoraDlmmClaimFee2IxData(discriminator, minBinId, maxBinId, remainingAccountsInfo);
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

  public static final Discriminator METEORA_DLMM_CLAIM_REWARD_2_DISCRIMINATOR = toDiscriminator(73, 232, 244, 62, 123, 103, 114, 39);

  public static Instruction meteoraDlmmClaimReward2(final AccountMeta invokedGlamProtocolProgramMeta,
                                                    final PublicKey glamStateKey,
                                                    final PublicKey glamVaultKey,
                                                    final PublicKey glamSignerKey,
                                                    final PublicKey cpiProgramKey,
                                                    final PublicKey lbPairKey,
                                                    final PublicKey positionKey,
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
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(lbPairKey),
      createWrite(positionKey),
      createWrite(rewardVaultKey),
      createRead(rewardMintKey),
      createWrite(userTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(memoProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[24 + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(METEORA_DLMM_CLAIM_REWARD_2_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, rewardIndex);
    i += 8;
    putInt32LE(_data, i, minBinId);
    i += 4;
    putInt32LE(_data, i, maxBinId);
    i += 4;
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record MeteoraDlmmClaimReward2IxData(Discriminator discriminator,
                                              long rewardIndex,
                                              int minBinId,
                                              int maxBinId,
                                              RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static MeteoraDlmmClaimReward2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static MeteoraDlmmClaimReward2IxData read(final byte[] _data, final int offset) {
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
      return new MeteoraDlmmClaimReward2IxData(discriminator,
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

  public static final Discriminator METEORA_DLMM_CLOSE_POSITION_2_DISCRIMINATOR = toDiscriminator(69, 117, 240, 192, 34, 79, 62, 230);

  public static Instruction meteoraDlmmClosePosition2(final AccountMeta invokedGlamProtocolProgramMeta,
                                                      final PublicKey glamStateKey,
                                                      final PublicKey glamVaultKey,
                                                      final PublicKey glamSignerKey,
                                                      final PublicKey cpiProgramKey,
                                                      final PublicKey positionKey,
                                                      final PublicKey rentReceiverKey,
                                                      final PublicKey eventAuthorityKey,
                                                      final PublicKey programKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(positionKey),
      createWrite(rentReceiverKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, METEORA_DLMM_CLOSE_POSITION_2_DISCRIMINATOR);
  }

  public static final Discriminator METEORA_DLMM_INITIALIZE_POSITION_DISCRIMINATOR = toDiscriminator(223, 94, 215, 96, 175, 181, 195, 204);

  public static Instruction meteoraDlmmInitializePosition(final AccountMeta invokedGlamProtocolProgramMeta,
                                                          final SolanaAccounts solanaAccounts,
                                                          final PublicKey glamStateKey,
                                                          final PublicKey glamVaultKey,
                                                          final PublicKey glamSignerKey,
                                                          final PublicKey cpiProgramKey,
                                                          final PublicKey positionKey,
                                                          final PublicKey lbPairKey,
                                                          final PublicKey eventAuthorityKey,
                                                          final PublicKey programKey,
                                                          final int lowerBinId,
                                                          final int width) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
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

  public static final Discriminator METEORA_DLMM_INITIALIZE_POSITION_PDA_DISCRIMINATOR = toDiscriminator(206, 130, 13, 156, 170, 45, 96, 115);

  public static Instruction meteoraDlmmInitializePositionPda(final AccountMeta invokedGlamProtocolProgramMeta,
                                                             final SolanaAccounts solanaAccounts,
                                                             final PublicKey glamStateKey,
                                                             final PublicKey glamVaultKey,
                                                             final PublicKey glamSignerKey,
                                                             final PublicKey cpiProgramKey,
                                                             final PublicKey positionKey,
                                                             final PublicKey lbPairKey,
                                                             final PublicKey eventAuthorityKey,
                                                             final PublicKey programKey,
                                                             final int lowerBinId,
                                                             final int width) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(positionKey),
      createRead(lbPairKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.rentSysVar()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(METEORA_DLMM_INITIALIZE_POSITION_PDA_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, lowerBinId);
    i += 4;
    putInt32LE(_data, i, width);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record MeteoraDlmmInitializePositionPdaIxData(Discriminator discriminator, int lowerBinId, int width) implements Borsh {  

    public static MeteoraDlmmInitializePositionPdaIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static MeteoraDlmmInitializePositionPdaIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lowerBinId = getInt32LE(_data, i);
      i += 4;
      final var width = getInt32LE(_data, i);
      return new MeteoraDlmmInitializePositionPdaIxData(discriminator, lowerBinId, width);
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

  public static final Discriminator METEORA_DLMM_REMOVE_LIQUIDITY_2_DISCRIMINATOR = toDiscriminator(118, 111, 170, 14, 224, 187, 21, 119);

  public static Instruction meteoraDlmmRemoveLiquidity2(final AccountMeta invokedGlamProtocolProgramMeta,
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
                                                        final PublicKey tokenXProgramKey,
                                                        final PublicKey tokenYProgramKey,
                                                        final PublicKey memoProgramKey,
                                                        final PublicKey eventAuthorityKey,
                                                        final PublicKey programKey,
                                                        final BinLiquidityReduction[] binLiquidityRemoval,
                                                        final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedGlamProtocolProgramMeta.publicKey())),
      createWrite(userTokenXKey),
      createWrite(userTokenYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(memoProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.lenVector(binLiquidityRemoval) + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(METEORA_DLMM_REMOVE_LIQUIDITY_2_DISCRIMINATOR, _data, 0);
    i += Borsh.writeVector(binLiquidityRemoval, _data, i);
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record MeteoraDlmmRemoveLiquidity2IxData(Discriminator discriminator, BinLiquidityReduction[] binLiquidityRemoval, RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static MeteoraDlmmRemoveLiquidity2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static MeteoraDlmmRemoveLiquidity2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var binLiquidityRemoval = Borsh.readVector(BinLiquidityReduction.class, BinLiquidityReduction::read, _data, i);
      i += Borsh.lenVector(binLiquidityRemoval);
      final var remainingAccountsInfo = RemainingAccountsInfo.read(_data, i);
      return new MeteoraDlmmRemoveLiquidity2IxData(discriminator, binLiquidityRemoval, remainingAccountsInfo);
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

  public static final Discriminator METEORA_DLMM_REMOVE_LIQUIDITY_BY_RANGE_2_DISCRIMINATOR = toDiscriminator(157, 54, 138, 77, 16, 239, 100, 16);

  public static Instruction meteoraDlmmRemoveLiquidityByRange2(final AccountMeta invokedGlamProtocolProgramMeta,
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
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(positionKey),
      createWrite(lbPairKey),
      createWrite(requireNonNullElse(binArrayBitmapExtensionKey, invokedGlamProtocolProgramMeta.publicKey())),
      createWrite(userTokenXKey),
      createWrite(userTokenYKey),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(memoProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[18 + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(METEORA_DLMM_REMOVE_LIQUIDITY_BY_RANGE_2_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, fromBinId);
    i += 4;
    putInt32LE(_data, i, toBinId);
    i += 4;
    putInt16LE(_data, i, bpsToRemove);
    i += 2;
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record MeteoraDlmmRemoveLiquidityByRange2IxData(Discriminator discriminator,
                                                         int fromBinId,
                                                         int toBinId,
                                                         int bpsToRemove,
                                                         RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static MeteoraDlmmRemoveLiquidityByRange2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static MeteoraDlmmRemoveLiquidityByRange2IxData read(final byte[] _data, final int offset) {
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
      return new MeteoraDlmmRemoveLiquidityByRange2IxData(discriminator,
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

  public static final Discriminator METEORA_DLMM_SWAP_2_DISCRIMINATOR = toDiscriminator(95, 183, 135, 4, 4, 165, 65, 133);

  public static Instruction meteoraDlmmSwap2(final AccountMeta invokedGlamProtocolProgramMeta,
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
                                             final PublicKey memoProgramKey,
                                             final PublicKey eventAuthorityKey,
                                             final PublicKey programKey,
                                             final long amountIn,
                                             final long minAmountOut,
                                             final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(lbPairKey),
      createRead(requireNonNullElse(binArrayBitmapExtensionKey, invokedGlamProtocolProgramMeta.publicKey())),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(userTokenInKey),
      createWrite(userTokenOutKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(oracleKey),
      createWrite(requireNonNullElse(hostFeeInKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(tokenXProgramKey),
      createRead(tokenYProgramKey),
      createRead(memoProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[24 + Borsh.len(remainingAccountsInfo)];
    int i = writeDiscriminator(METEORA_DLMM_SWAP_2_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountIn);
    i += 8;
    putInt64LE(_data, i, minAmountOut);
    i += 8;
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record MeteoraDlmmSwap2IxData(Discriminator discriminator,
                                       long amountIn,
                                       long minAmountOut,
                                       RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static MeteoraDlmmSwap2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static MeteoraDlmmSwap2IxData read(final byte[] _data, final int offset) {
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
      return new MeteoraDlmmSwap2IxData(discriminator, amountIn, minAmountOut, remainingAccountsInfo);
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

  public static final Discriminator METEORA_DLMM_SWAP_WITH_PRICE_IMPACT_2_DISCRIMINATOR = toDiscriminator(219, 33, 50, 237, 37, 101, 146, 101);

  public static Instruction meteoraDlmmSwapWithPriceImpact2(final AccountMeta invokedGlamProtocolProgramMeta,
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
                                                            final PublicKey memoProgramKey,
                                                            final PublicKey eventAuthorityKey,
                                                            final PublicKey programKey,
                                                            final long amountIn,
                                                            final OptionalInt activeId,
                                                            final int maxPriceImpactBps,
                                                            final RemainingAccountsInfo remainingAccountsInfo) {
    final var keys = List.of(
      createRead(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(lbPairKey),
      createRead(requireNonNullElse(binArrayBitmapExtensionKey, invokedGlamProtocolProgramMeta.publicKey())),
      createWrite(reserveXKey),
      createWrite(reserveYKey),
      createWrite(userTokenInKey),
      createWrite(userTokenOutKey),
      createRead(tokenXMintKey),
      createRead(tokenYMintKey),
      createWrite(oracleKey),
      createWrite(requireNonNullElse(hostFeeInKey, invokedGlamProtocolProgramMeta.publicKey())),
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
    int i = writeDiscriminator(METEORA_DLMM_SWAP_WITH_PRICE_IMPACT_2_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountIn);
    i += 8;
    i += Borsh.writeOptional(activeId, _data, i);
    putInt16LE(_data, i, maxPriceImpactBps);
    i += 2;
    Borsh.write(remainingAccountsInfo, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record MeteoraDlmmSwapWithPriceImpact2IxData(Discriminator discriminator,
                                                      long amountIn,
                                                      OptionalInt activeId,
                                                      int maxPriceImpactBps,
                                                      RemainingAccountsInfo remainingAccountsInfo) implements Borsh {  

    public static MeteoraDlmmSwapWithPriceImpact2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static MeteoraDlmmSwapWithPriceImpact2IxData read(final byte[] _data, final int offset) {
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
      return new MeteoraDlmmSwapWithPriceImpact2IxData(discriminator,
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

  public static final Discriminator MINT_TOKENS_DISCRIMINATOR = toDiscriminator(59, 132, 24, 246, 122, 39, 8, 243);

  public static Instruction mintTokens(final AccountMeta invokedGlamProtocolProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey glamStateKey,
                                       final PublicKey glamSignerKey,
                                       final PublicKey glamMintKey,
                                       final PublicKey mintToKey,
                                       final PublicKey recipientKey,
                                       final PublicKey policyAccountKey,
                                       final PublicKey policiesProgramKey,
                                       final int mintId,
                                       final long amount) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createWrite(mintToKey),
      createWrite(recipientKey),
      createWrite(requireNonNullElse(policyAccountKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.token2022Program()),
      createRead(policiesProgramKey)
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

  public static final Discriminator PRICE_DRIFT_USERS_DISCRIMINATOR = toDiscriminator(12, 5, 143, 51, 101, 81, 200, 150);

  public static Instruction priceDriftUsers(final AccountMeta invokedGlamProtocolProgramMeta,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamVaultKey,
                                            final PublicKey signerKey,
                                            final PublicKey solOracleKey,
                                            final PublicKey glamConfigKey,
                                            final PriceDenom denom) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(signerKey),
      createRead(solOracleKey),
      createRead(glamConfigKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(denom)];
    int i = writeDiscriminator(PRICE_DRIFT_USERS_DISCRIMINATOR, _data, 0);
    Borsh.write(denom, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record PriceDriftUsersIxData(Discriminator discriminator, PriceDenom denom) implements Borsh {  

    public static PriceDriftUsersIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static PriceDriftUsersIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var denom = PriceDenom.read(_data, i);
      return new PriceDriftUsersIxData(discriminator, denom);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(denom, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator PRICE_DRIFT_VAULT_DEPOSITORS_DISCRIMINATOR = toDiscriminator(234, 16, 238, 70, 189, 23, 98, 160);

  public static Instruction priceDriftVaultDepositors(final AccountMeta invokedGlamProtocolProgramMeta,
                                                      final PublicKey glamStateKey,
                                                      final PublicKey glamVaultKey,
                                                      final PublicKey signerKey,
                                                      final PublicKey solOracleKey,
                                                      final PublicKey glamConfigKey,
                                                      final PriceDenom denom) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(signerKey),
      createRead(solOracleKey),
      createRead(glamConfigKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(denom)];
    int i = writeDiscriminator(PRICE_DRIFT_VAULT_DEPOSITORS_DISCRIMINATOR, _data, 0);
    Borsh.write(denom, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record PriceDriftVaultDepositorsIxData(Discriminator discriminator, PriceDenom denom) implements Borsh {  

    public static PriceDriftVaultDepositorsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static PriceDriftVaultDepositorsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var denom = PriceDenom.read(_data, i);
      return new PriceDriftVaultDepositorsIxData(discriminator, denom);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(denom, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator PRICE_KAMINO_OBLIGATIONS_DISCRIMINATOR = toDiscriminator(166, 110, 234, 179, 240, 179, 69, 246);

  public static Instruction priceKaminoObligations(final AccountMeta invokedGlamProtocolProgramMeta,
                                                   final PublicKey glamStateKey,
                                                   final PublicKey glamVaultKey,
                                                   final PublicKey signerKey,
                                                   final PublicKey kaminoLendingProgramKey,
                                                   final PublicKey solOracleKey,
                                                   final PublicKey glamConfigKey,
                                                   final PublicKey pythOracleKey,
                                                   final PublicKey switchboardPriceOracleKey,
                                                   final PublicKey switchboardTwapOracleKey,
                                                   final PublicKey scopePricesKey,
                                                   final PriceDenom denom) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(signerKey),
      createRead(kaminoLendingProgramKey),
      createRead(solOracleKey),
      createRead(glamConfigKey),
      createRead(requireNonNullElse(pythOracleKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(requireNonNullElse(switchboardPriceOracleKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(requireNonNullElse(switchboardTwapOracleKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(requireNonNullElse(scopePricesKey, invokedGlamProtocolProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(denom)];
    int i = writeDiscriminator(PRICE_KAMINO_OBLIGATIONS_DISCRIMINATOR, _data, 0);
    Borsh.write(denom, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record PriceKaminoObligationsIxData(Discriminator discriminator, PriceDenom denom) implements Borsh {  

    public static PriceKaminoObligationsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static PriceKaminoObligationsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var denom = PriceDenom.read(_data, i);
      return new PriceKaminoObligationsIxData(discriminator, denom);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(denom, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator PRICE_KAMINO_VAULT_SHARES_DISCRIMINATOR = toDiscriminator(112, 92, 238, 224, 145, 105, 38, 249);

  public static Instruction priceKaminoVaultShares(final AccountMeta invokedGlamProtocolProgramMeta,
                                                   final PublicKey glamStateKey,
                                                   final PublicKey glamVaultKey,
                                                   final PublicKey signerKey,
                                                   final PublicKey kaminoLendingProgramKey,
                                                   final PublicKey solOracleKey,
                                                   final PublicKey glamConfigKey,
                                                   final PublicKey pythOracleKey,
                                                   final PublicKey switchboardPriceOracleKey,
                                                   final PublicKey switchboardTwapOracleKey,
                                                   final PublicKey scopePricesKey,
                                                   final PriceDenom denom) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(signerKey),
      createRead(kaminoLendingProgramKey),
      createRead(solOracleKey),
      createRead(glamConfigKey),
      createRead(requireNonNullElse(pythOracleKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(requireNonNullElse(switchboardPriceOracleKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(requireNonNullElse(switchboardTwapOracleKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(requireNonNullElse(scopePricesKey, invokedGlamProtocolProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(denom)];
    int i = writeDiscriminator(PRICE_KAMINO_VAULT_SHARES_DISCRIMINATOR, _data, 0);
    Borsh.write(denom, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record PriceKaminoVaultSharesIxData(Discriminator discriminator, PriceDenom denom) implements Borsh {  

    public static PriceKaminoVaultSharesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static PriceKaminoVaultSharesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var denom = PriceDenom.read(_data, i);
      return new PriceKaminoVaultSharesIxData(discriminator, denom);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(denom, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator PRICE_METEORA_POSITIONS_DISCRIMINATOR = toDiscriminator(186, 22, 157, 249, 185, 176, 253, 133);

  public static Instruction priceMeteoraPositions(final AccountMeta invokedGlamProtocolProgramMeta,
                                                  final PublicKey glamStateKey,
                                                  final PublicKey glamVaultKey,
                                                  final PublicKey signerKey,
                                                  final PublicKey solOracleKey,
                                                  final PublicKey glamConfigKey,
                                                  final PriceDenom denom) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(signerKey),
      createRead(solOracleKey),
      createRead(glamConfigKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(denom)];
    int i = writeDiscriminator(PRICE_METEORA_POSITIONS_DISCRIMINATOR, _data, 0);
    Borsh.write(denom, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record PriceMeteoraPositionsIxData(Discriminator discriminator, PriceDenom denom) implements Borsh {  

    public static PriceMeteoraPositionsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static PriceMeteoraPositionsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var denom = PriceDenom.read(_data, i);
      return new PriceMeteoraPositionsIxData(discriminator, denom);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(denom, _data, i);
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
                                        final PublicKey signerKey,
                                        final PublicKey solOracleKey,
                                        final PublicKey glamConfigKey,
                                        final PriceDenom denom) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(signerKey),
      createRead(solOracleKey),
      createRead(glamConfigKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(denom)];
    int i = writeDiscriminator(PRICE_STAKES_DISCRIMINATOR, _data, 0);
    Borsh.write(denom, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record PriceStakesIxData(Discriminator discriminator, PriceDenom denom) implements Borsh {  

    public static PriceStakesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static PriceStakesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var denom = PriceDenom.read(_data, i);
      return new PriceStakesIxData(discriminator, denom);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(denom, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator PRICE_VAULT_DISCRIMINATOR = toDiscriminator(47, 213, 36, 17, 183, 5, 141, 45);

  public static Instruction priceVault(final AccountMeta invokedGlamProtocolProgramMeta,
                                       final PublicKey glamStateKey,
                                       final PublicKey glamVaultKey,
                                       final PublicKey signerKey,
                                       final PublicKey solOracleKey,
                                       final PublicKey glamConfigKey,
                                       final PriceDenom denom) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(signerKey),
      createRead(solOracleKey),
      createRead(glamConfigKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(denom)];
    int i = writeDiscriminator(PRICE_VAULT_DISCRIMINATOR, _data, 0);
    Borsh.write(denom, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record PriceVaultIxData(Discriminator discriminator, PriceDenom denom) implements Borsh {  

    public static PriceVaultIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static PriceVaultIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var denom = PriceDenom.read(_data, i);
      return new PriceVaultIxData(discriminator, denom);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(denom, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator QUEUED_REDEEM_DISCRIMINATOR = toDiscriminator(82, 242, 202, 93, 170, 196, 215, 113);

  public static Instruction queuedRedeem(final AccountMeta invokedGlamProtocolProgramMeta,
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
    int i = writeDiscriminator(QUEUED_REDEEM_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mintId;
    ++i;
    putInt64LE(_data, i, sharesIn);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record QueuedRedeemIxData(Discriminator discriminator, int mintId, long sharesIn) implements Borsh {  

    public static QueuedRedeemIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static QueuedRedeemIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintId = _data[i] & 0xFF;
      ++i;
      final var sharesIn = getInt64LE(_data, i);
      return new QueuedRedeemIxData(discriminator, mintId, sharesIn);
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

  public static final Discriminator QUEUED_SUBSCRIBE_DISCRIMINATOR = toDiscriminator(107, 180, 212, 63, 146, 0, 159, 255);

  public static Instruction queuedSubscribe(final AccountMeta invokedGlamProtocolProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamEscrowKey,
                                            final PublicKey signerKey,
                                            final PublicKey depositAssetKey,
                                            final PublicKey escrowDepositAtaKey,
                                            final PublicKey signerDepositAtaKey,
                                            final PublicKey depositTokenProgramKey,
                                            final int mintId,
                                            final long amountIn) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamEscrowKey),
      createWritableSigner(signerKey),
      createRead(depositAssetKey),
      createWrite(escrowDepositAtaKey),
      createWrite(signerDepositAtaKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(depositTokenProgramKey),
      createRead(solanaAccounts.associatedTokenAccountProgram())
    );

    final byte[] _data = new byte[17];
    int i = writeDiscriminator(QUEUED_SUBSCRIBE_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mintId;
    ++i;
    putInt64LE(_data, i, amountIn);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record QueuedSubscribeIxData(Discriminator discriminator, int mintId, long amountIn) implements Borsh {  

    public static QueuedSubscribeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static QueuedSubscribeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintId = _data[i] & 0xFF;
      ++i;
      final var amountIn = getInt64LE(_data, i);
      return new QueuedSubscribeIxData(discriminator, mintId, amountIn);
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

  public static final Discriminator SET_PROTOCOL_FEES_DISCRIMINATOR = toDiscriminator(49, 143, 189, 18, 56, 206, 158, 226);

  public static Instruction setProtocolFees(final AccountMeta invokedGlamProtocolProgramMeta,
                                            final PublicKey glamStateKey,
                                            final PublicKey signerKey,
                                            final PublicKey glamConfigKey,
                                            final int baseFeeBps,
                                            final int flowFeeBps) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(signerKey),
      createRead(glamConfigKey)
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(SET_PROTOCOL_FEES_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, baseFeeBps);
    i += 2;
    putInt16LE(_data, i, flowFeeBps);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record SetProtocolFeesIxData(Discriminator discriminator, int baseFeeBps, int flowFeeBps) implements Borsh {  

    public static SetProtocolFeesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 12;

    public static SetProtocolFeesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var baseFeeBps = getInt16LE(_data, i);
      i += 2;
      final var flowFeeBps = getInt16LE(_data, i);
      return new SetProtocolFeesIxData(discriminator, baseFeeBps, flowFeeBps);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, baseFeeBps);
      i += 2;
      putInt16LE(_data, i, flowFeeBps);
      i += 2;
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
                                           final int stakerOrWithdrawer) {
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
    putInt32LE(_data, i, stakerOrWithdrawer);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record StakeAuthorizeIxData(Discriminator discriminator, PublicKey newAuthority, int stakerOrWithdrawer) implements Borsh {  

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
      final var stakerOrWithdrawer = getInt32LE(_data, i);
      return new StakeAuthorizeIxData(discriminator, newAuthority, stakerOrWithdrawer);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      newAuthority.write(_data, i);
      i += 32;
      putInt32LE(_data, i, stakerOrWithdrawer);
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

  public static final Discriminator SUBSCRIBE_DISCRIMINATOR = toDiscriminator(254, 28, 191, 138, 156, 179, 183, 53);

  public static Instruction subscribe(final AccountMeta invokedGlamProtocolProgramMeta,
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
                                      final PublicKey depositTokenProgramKey,
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
      createRead(depositTokenProgramKey),
      createRead(solanaAccounts.token2022Program()),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(policiesProgramKey)
    );

    final byte[] _data = new byte[17];
    int i = writeDiscriminator(SUBSCRIBE_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mintId;
    ++i;
    putInt64LE(_data, i, amountIn);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record SubscribeIxData(Discriminator discriminator, int mintId, long amountIn) implements Borsh {  

    public static SubscribeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static SubscribeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintId = _data[i] & 0xFF;
      ++i;
      final var amountIn = getInt64LE(_data, i);
      return new SubscribeIxData(discriminator, mintId, amountIn);
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

  public static final Discriminator UPDATE_MINT_APPLY_TIMELOCK_DISCRIMINATOR = toDiscriminator(223, 241, 80, 24, 120, 25, 82, 134);

  public static Instruction updateMintApplyTimelock(final AccountMeta invokedGlamProtocolProgramMeta,
                                                    final SolanaAccounts solanaAccounts,
                                                    final PublicKey glamStateKey,
                                                    final PublicKey glamSignerKey,
                                                    final PublicKey glamMintKey,
                                                    final int mintId) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createRead(solanaAccounts.token2022Program())
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(UPDATE_MINT_APPLY_TIMELOCK_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) mintId;

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record UpdateMintApplyTimelockIxData(Discriminator discriminator, int mintId) implements Borsh {  

    public static UpdateMintApplyTimelockIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static UpdateMintApplyTimelockIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintId = _data[i] & 0xFF;
      return new UpdateMintApplyTimelockIxData(discriminator, mintId);
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

  public static final Discriminator UPDATE_STATE_APPLY_TIMELOCK_DISCRIMINATOR = toDiscriminator(66, 12, 138, 80, 133, 85, 46, 220);

  public static Instruction updateStateApplyTimelock(final AccountMeta invokedGlamProtocolProgramMeta, final PublicKey glamStateKey, final PublicKey glamSignerKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey)
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, UPDATE_STATE_APPLY_TIMELOCK_DISCRIMINATOR);
  }

  public static final Discriminator VOTE_AUTHORIZE_DISCRIMINATOR = toDiscriminator(61, 48, 136, 95, 88, 65, 161, 215);

  public static Instruction voteAuthorize(final AccountMeta invokedGlamProtocolProgramMeta,
                                          final SolanaAccounts solanaAccounts,
                                          final PublicKey glamStateKey,
                                          final PublicKey glamVaultKey,
                                          final PublicKey glamSignerKey,
                                          final PublicKey voteKey,
                                          final PublicKey newAuthority,
                                          final VoteAuthorizeEnum voteAuthorize) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.voteProgram()),
      createWrite(voteKey),
      createRead(solanaAccounts.clockSysVar())
    );

    final byte[] _data = new byte[40 + Borsh.len(voteAuthorize)];
    int i = writeDiscriminator(VOTE_AUTHORIZE_DISCRIMINATOR, _data, 0);
    newAuthority.write(_data, i);
    i += 32;
    Borsh.write(voteAuthorize, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record VoteAuthorizeIxData(Discriminator discriminator, PublicKey newAuthority, VoteAuthorizeEnum voteAuthorize) implements Borsh {  

    public static VoteAuthorizeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 41;

    public static VoteAuthorizeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var newAuthority = readPubKey(_data, i);
      i += 32;
      final var voteAuthorize = VoteAuthorizeEnum.read(_data, i);
      return new VoteAuthorizeIxData(discriminator, newAuthority, voteAuthorize);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      newAuthority.write(_data, i);
      i += 32;
      i += Borsh.write(voteAuthorize, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator VOTE_UPDATE_COMMISSION_DISCRIMINATOR = toDiscriminator(193, 83, 224, 17, 218, 144, 211, 60);

  public static Instruction voteUpdateCommission(final AccountMeta invokedGlamProtocolProgramMeta,
                                                 final SolanaAccounts solanaAccounts,
                                                 final PublicKey glamStateKey,
                                                 final PublicKey glamVaultKey,
                                                 final PublicKey glamSignerKey,
                                                 final PublicKey voteKey,
                                                 final int newCommission) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.voteProgram()),
      createWrite(voteKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(VOTE_UPDATE_COMMISSION_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) newCommission;

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record VoteUpdateCommissionIxData(Discriminator discriminator, int newCommission) implements Borsh {  

    public static VoteUpdateCommissionIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static VoteUpdateCommissionIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var newCommission = _data[i] & 0xFF;
      return new VoteUpdateCommissionIxData(discriminator, newCommission);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) newCommission;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator VOTE_UPDATE_VALIDATOR_IDENTITY_DISCRIMINATOR = toDiscriminator(164, 111, 105, 150, 248, 104, 150, 8);

  public static Instruction voteUpdateValidatorIdentity(final AccountMeta invokedGlamProtocolProgramMeta,
                                                        final SolanaAccounts solanaAccounts,
                                                        final PublicKey glamStateKey,
                                                        final PublicKey glamVaultKey,
                                                        final PublicKey glamSignerKey,
                                                        final PublicKey voteKey,
                                                        final PublicKey identityKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.voteProgram()),
      createWrite(voteKey),
      createWritableSigner(identityKey)
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, VOTE_UPDATE_VALIDATOR_IDENTITY_DISCRIMINATOR);
  }

  public static final Discriminator VOTE_WITHDRAW_DISCRIMINATOR = toDiscriminator(193, 3, 236, 192, 122, 146, 217, 149);

  public static Instruction voteWithdraw(final AccountMeta invokedGlamProtocolProgramMeta,
                                         final SolanaAccounts solanaAccounts,
                                         final PublicKey glamStateKey,
                                         final PublicKey glamVaultKey,
                                         final PublicKey glamSignerKey,
                                         final PublicKey voteKey,
                                         final PublicKey recipientKey,
                                         final long lamports) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.voteProgram()),
      createWrite(voteKey),
      createWrite(recipientKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(VOTE_WITHDRAW_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record VoteWithdrawIxData(Discriminator discriminator, long lamports) implements Borsh {  

    public static VoteWithdrawIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static VoteWithdrawIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      return new VoteWithdrawIxData(discriminator, lamports);
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
