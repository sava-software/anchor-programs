package software.sava.anchor.programs.glam.kamino.anchor;

import java.math.BigInteger;

import java.util.List;

import software.sava.anchor.programs.glam.kamino.anchor.types.InitObligationArgs;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static java.util.Objects.requireNonNullElse;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class ExtKaminoProgram {

  public static final Discriminator FARMS_HARVEST_REWARD_DISCRIMINATOR = toDiscriminator(244, 248, 124, 210, 194, 52, 75, 152);

  public static Instruction farmsHarvestReward(final AccountMeta invokedExtKaminoProgramMeta,
                                               final PublicKey glamStateKey,
                                               final PublicKey glamVaultKey,
                                               final PublicKey glamSignerKey,
                                               final PublicKey integrationAuthorityKey,
                                               final PublicKey cpiProgramKey,
                                               final PublicKey glamProtocolProgramKey,
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
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(integrationAuthorityKey),
      createRead(cpiProgramKey),
      createRead(glamProtocolProgramKey),
      createWrite(userStateKey),
      createWrite(farmStateKey),
      createRead(globalConfigKey),
      createRead(rewardMintKey),
      createWrite(userRewardAtaKey),
      createWrite(rewardsVaultKey),
      createWrite(rewardsTreasuryVaultKey),
      createRead(farmVaultsAuthorityKey),
      createRead(requireNonNullElse(scopePricesKey, invokedExtKaminoProgramMeta.publicKey())),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = FARMS_HARVEST_REWARD_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, rewardIndex);

    return Instruction.createInstruction(invokedExtKaminoProgramMeta, keys, _data);
  }

  public record FarmsHarvestRewardIxData(Discriminator discriminator, long rewardIndex) implements Borsh {  

    public static FarmsHarvestRewardIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static FarmsHarvestRewardIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var rewardIndex = getInt64LE(_data, i);
      return new FarmsHarvestRewardIxData(discriminator, rewardIndex);
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

  public static final Discriminator FARMS_STAKE_DISCRIMINATOR = toDiscriminator(224, 105, 208, 179, 98, 200, 213, 238);

  public static Instruction farmsStake(final AccountMeta invokedExtKaminoProgramMeta,
                                       final PublicKey glamStateKey,
                                       final PublicKey glamVaultKey,
                                       final PublicKey glamSignerKey,
                                       final PublicKey integrationAuthorityKey,
                                       final PublicKey cpiProgramKey,
                                       final PublicKey glamProtocolProgramKey,
                                       final PublicKey userStateKey,
                                       final PublicKey farmStateKey,
                                       final PublicKey farmVaultKey,
                                       final PublicKey userAtaKey,
                                       final PublicKey tokenMintKey,
                                       final PublicKey scopePricesKey,
                                       final PublicKey tokenProgramKey,
                                       final long amount) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(integrationAuthorityKey),
      createRead(cpiProgramKey),
      createRead(glamProtocolProgramKey),
      createWrite(userStateKey),
      createWrite(farmStateKey),
      createWrite(farmVaultKey),
      createWrite(userAtaKey),
      createRead(tokenMintKey),
      createRead(requireNonNullElse(scopePricesKey, invokedExtKaminoProgramMeta.publicKey())),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = FARMS_STAKE_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedExtKaminoProgramMeta, keys, _data);
  }

  public record FarmsStakeIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static FarmsStakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static FarmsStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new FarmsStakeIxData(discriminator, amount);
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

  public static final Discriminator FARMS_UNSTAKE_DISCRIMINATOR = toDiscriminator(180, 131, 50, 144, 26, 242, 175, 242);

  public static Instruction farmsUnstake(final AccountMeta invokedExtKaminoProgramMeta,
                                         final PublicKey glamStateKey,
                                         final PublicKey glamVaultKey,
                                         final PublicKey glamSignerKey,
                                         final PublicKey integrationAuthorityKey,
                                         final PublicKey cpiProgramKey,
                                         final PublicKey glamProtocolProgramKey,
                                         final PublicKey userStateKey,
                                         final PublicKey farmStateKey,
                                         final PublicKey scopePricesKey,
                                         final BigInteger amount) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(integrationAuthorityKey),
      createRead(cpiProgramKey),
      createRead(glamProtocolProgramKey),
      createWrite(userStateKey),
      createWrite(farmStateKey),
      createRead(requireNonNullElse(scopePricesKey, invokedExtKaminoProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[24];
    int i = FARMS_UNSTAKE_DISCRIMINATOR.write(_data, 0);
    putInt128LE(_data, i, amount);

    return Instruction.createInstruction(invokedExtKaminoProgramMeta, keys, _data);
  }

  public record FarmsUnstakeIxData(Discriminator discriminator, BigInteger amount) implements Borsh {  

    public static FarmsUnstakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static FarmsUnstakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt128LE(_data, i);
      return new FarmsUnstakeIxData(discriminator, amount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt128LE(_data, i, amount);
      i += 16;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator LENDING_BORROW_OBLIGATION_LIQUIDITY_V_2_DISCRIMINATOR = toDiscriminator(149, 226, 84, 157, 124, 178, 35, 122);

  public static Instruction lendingBorrowObligationLiquidityV2(final AccountMeta invokedExtKaminoProgramMeta,
                                                               final PublicKey glamStateKey,
                                                               final PublicKey glamVaultKey,
                                                               final PublicKey glamSignerKey,
                                                               final PublicKey integrationAuthorityKey,
                                                               final PublicKey cpiProgramKey,
                                                               final PublicKey glamProtocolProgramKey,
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
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(integrationAuthorityKey),
      createRead(cpiProgramKey),
      createRead(glamProtocolProgramKey),
      createWrite(obligationKey),
      createRead(lendingMarketKey),
      createRead(lendingMarketAuthorityKey),
      createWrite(borrowReserveKey),
      createRead(borrowReserveLiquidityMintKey),
      createWrite(reserveSourceLiquidityKey),
      createWrite(borrowReserveLiquidityFeeReceiverKey),
      createWrite(userDestinationLiquidityKey),
      createWrite(requireNonNullElse(referrerTokenStateKey, invokedExtKaminoProgramMeta.publicKey())),
      createRead(tokenProgramKey),
      createRead(instructionSysvarAccountKey),
      createWrite(requireNonNullElse(obligationFarmUserStateKey, invokedExtKaminoProgramMeta.publicKey())),
      createWrite(requireNonNullElse(reserveFarmStateKey, invokedExtKaminoProgramMeta.publicKey())),
      createRead(farmsProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = LENDING_BORROW_OBLIGATION_LIQUIDITY_V_2_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, liquidityAmount);

    return Instruction.createInstruction(invokedExtKaminoProgramMeta, keys, _data);
  }

  public record LendingBorrowObligationLiquidityV2IxData(Discriminator discriminator, long liquidityAmount) implements Borsh {  

    public static LendingBorrowObligationLiquidityV2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static LendingBorrowObligationLiquidityV2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityAmount = getInt64LE(_data, i);
      return new LendingBorrowObligationLiquidityV2IxData(discriminator, liquidityAmount);
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

  public static final Discriminator LENDING_DEPOSIT_RESERVE_LIQUIDITY_AND_OBLIGATION_COLLATERAL_V_2_DISCRIMINATOR = toDiscriminator(33, 146, 50, 121, 127, 94, 92, 192);

  public static Instruction lendingDepositReserveLiquidityAndObligationCollateralV2(final AccountMeta invokedExtKaminoProgramMeta,
                                                                                    final PublicKey glamStateKey,
                                                                                    final PublicKey glamVaultKey,
                                                                                    final PublicKey glamSignerKey,
                                                                                    final PublicKey integrationAuthorityKey,
                                                                                    final PublicKey cpiProgramKey,
                                                                                    final PublicKey glamProtocolProgramKey,
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
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(integrationAuthorityKey),
      createRead(cpiProgramKey),
      createRead(glamProtocolProgramKey),
      createWrite(obligationKey),
      createRead(lendingMarketKey),
      createRead(lendingMarketAuthorityKey),
      createWrite(reserveKey),
      createRead(reserveLiquidityMintKey),
      createWrite(reserveLiquiditySupplyKey),
      createWrite(reserveCollateralMintKey),
      createWrite(reserveDestinationDepositCollateralKey),
      createWrite(userSourceLiquidityKey),
      createRead(requireNonNullElse(placeholderUserDestinationCollateralKey, invokedExtKaminoProgramMeta.publicKey())),
      createRead(collateralTokenProgramKey),
      createRead(liquidityTokenProgramKey),
      createRead(instructionSysvarAccountKey),
      createWrite(requireNonNullElse(obligationFarmUserStateKey, invokedExtKaminoProgramMeta.publicKey())),
      createWrite(requireNonNullElse(reserveFarmStateKey, invokedExtKaminoProgramMeta.publicKey())),
      createRead(farmsProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = LENDING_DEPOSIT_RESERVE_LIQUIDITY_AND_OBLIGATION_COLLATERAL_V_2_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, liquidityAmount);

    return Instruction.createInstruction(invokedExtKaminoProgramMeta, keys, _data);
  }

  public record LendingDepositReserveLiquidityAndObligationCollateralV2IxData(Discriminator discriminator, long liquidityAmount) implements Borsh {  

    public static LendingDepositReserveLiquidityAndObligationCollateralV2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static LendingDepositReserveLiquidityAndObligationCollateralV2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityAmount = getInt64LE(_data, i);
      return new LendingDepositReserveLiquidityAndObligationCollateralV2IxData(discriminator, liquidityAmount);
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

  public static final Discriminator LENDING_INIT_OBLIGATION_DISCRIMINATOR = toDiscriminator(188, 161, 62, 142, 106, 232, 77, 135);

  public static Instruction lendingInitObligation(final AccountMeta invokedExtKaminoProgramMeta,
                                                  final SolanaAccounts solanaAccounts,
                                                  final PublicKey glamStateKey,
                                                  final PublicKey glamVaultKey,
                                                  final PublicKey glamSignerKey,
                                                  final PublicKey integrationAuthorityKey,
                                                  final PublicKey cpiProgramKey,
                                                  final PublicKey glamProtocolProgramKey,
                                                  final PublicKey feePayerKey,
                                                  final PublicKey obligationKey,
                                                  final PublicKey lendingMarketKey,
                                                  final PublicKey seed1AccountKey,
                                                  final PublicKey seed2AccountKey,
                                                  final PublicKey ownerUserMetadataKey,
                                                  final InitObligationArgs args) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(integrationAuthorityKey),
      createRead(cpiProgramKey),
      createRead(glamProtocolProgramKey),
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
    int i = LENDING_INIT_OBLIGATION_DISCRIMINATOR.write(_data, 0);
    Borsh.write(args, _data, i);

    return Instruction.createInstruction(invokedExtKaminoProgramMeta, keys, _data);
  }

  public record LendingInitObligationIxData(Discriminator discriminator, InitObligationArgs args) implements Borsh {  

    public static LendingInitObligationIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static LendingInitObligationIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var args = InitObligationArgs.read(_data, i);
      return new LendingInitObligationIxData(discriminator, args);
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

  public static final Discriminator LENDING_INIT_OBLIGATION_FARMS_FOR_RESERVE_DISCRIMINATOR = toDiscriminator(3, 234, 110, 39, 12, 147, 175, 185);

  public static Instruction lendingInitObligationFarmsForReserve(final AccountMeta invokedExtKaminoProgramMeta,
                                                                 final SolanaAccounts solanaAccounts,
                                                                 final PublicKey glamStateKey,
                                                                 final PublicKey glamVaultKey,
                                                                 final PublicKey glamSignerKey,
                                                                 final PublicKey integrationAuthorityKey,
                                                                 final PublicKey cpiProgramKey,
                                                                 final PublicKey glamProtocolProgramKey,
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
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(integrationAuthorityKey),
      createRead(cpiProgramKey),
      createRead(glamProtocolProgramKey),
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
    int i = LENDING_INIT_OBLIGATION_FARMS_FOR_RESERVE_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) mode;

    return Instruction.createInstruction(invokedExtKaminoProgramMeta, keys, _data);
  }

  public record LendingInitObligationFarmsForReserveIxData(Discriminator discriminator, int mode) implements Borsh {  

    public static LendingInitObligationFarmsForReserveIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static LendingInitObligationFarmsForReserveIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mode = _data[i] & 0xFF;
      return new LendingInitObligationFarmsForReserveIxData(discriminator, mode);
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

  public static final Discriminator LENDING_INIT_USER_METADATA_DISCRIMINATOR = toDiscriminator(68, 236, 138, 146, 124, 228, 247, 241);

  public static Instruction lendingInitUserMetadata(final AccountMeta invokedExtKaminoProgramMeta,
                                                    final SolanaAccounts solanaAccounts,
                                                    final PublicKey glamStateKey,
                                                    final PublicKey glamVaultKey,
                                                    final PublicKey glamSignerKey,
                                                    final PublicKey integrationAuthorityKey,
                                                    final PublicKey cpiProgramKey,
                                                    final PublicKey glamProtocolProgramKey,
                                                    final PublicKey feePayerKey,
                                                    final PublicKey userMetadataKey,
                                                    final PublicKey referrerUserMetadataKey,
                                                    final PublicKey userLookupTable) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(integrationAuthorityKey),
      createRead(cpiProgramKey),
      createRead(glamProtocolProgramKey),
      createWritableSigner(feePayerKey),
      createWrite(userMetadataKey),
      createRead(requireNonNullElse(referrerUserMetadataKey, invokedExtKaminoProgramMeta.publicKey())),
      createRead(solanaAccounts.rentSysVar()),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[40];
    int i = LENDING_INIT_USER_METADATA_DISCRIMINATOR.write(_data, 0);
    userLookupTable.write(_data, i);

    return Instruction.createInstruction(invokedExtKaminoProgramMeta, keys, _data);
  }

  public record LendingInitUserMetadataIxData(Discriminator discriminator, PublicKey userLookupTable) implements Borsh {  

    public static LendingInitUserMetadataIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static LendingInitUserMetadataIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var userLookupTable = readPubKey(_data, i);
      return new LendingInitUserMetadataIxData(discriminator, userLookupTable);
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

  public static final Discriminator LENDING_REPAY_OBLIGATION_LIQUIDITY_V_2_DISCRIMINATOR = toDiscriminator(79, 34, 126, 170, 174, 156, 174, 29);

  public static Instruction lendingRepayObligationLiquidityV2(final AccountMeta invokedExtKaminoProgramMeta,
                                                              final PublicKey glamStateKey,
                                                              final PublicKey glamVaultKey,
                                                              final PublicKey glamSignerKey,
                                                              final PublicKey integrationAuthorityKey,
                                                              final PublicKey cpiProgramKey,
                                                              final PublicKey glamProtocolProgramKey,
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
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(integrationAuthorityKey),
      createRead(cpiProgramKey),
      createRead(glamProtocolProgramKey),
      createWrite(obligationKey),
      createRead(lendingMarketKey),
      createWrite(repayReserveKey),
      createRead(reserveLiquidityMintKey),
      createWrite(reserveDestinationLiquidityKey),
      createWrite(userSourceLiquidityKey),
      createRead(tokenProgramKey),
      createRead(instructionSysvarAccountKey),
      createWrite(requireNonNullElse(obligationFarmUserStateKey, invokedExtKaminoProgramMeta.publicKey())),
      createWrite(requireNonNullElse(reserveFarmStateKey, invokedExtKaminoProgramMeta.publicKey())),
      createRead(lendingMarketAuthorityKey),
      createRead(farmsProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = LENDING_REPAY_OBLIGATION_LIQUIDITY_V_2_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, liquidityAmount);

    return Instruction.createInstruction(invokedExtKaminoProgramMeta, keys, _data);
  }

  public record LendingRepayObligationLiquidityV2IxData(Discriminator discriminator, long liquidityAmount) implements Borsh {  

    public static LendingRepayObligationLiquidityV2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static LendingRepayObligationLiquidityV2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var liquidityAmount = getInt64LE(_data, i);
      return new LendingRepayObligationLiquidityV2IxData(discriminator, liquidityAmount);
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

  public static final Discriminator LENDING_WITHDRAW_OBLIGATION_COLLATERAL_AND_REDEEM_RESERVE_COLLATERAL_V_2_DISCRIMINATOR = toDiscriminator(217, 223, 173, 35, 64, 225, 161, 222);

  public static Instruction lendingWithdrawObligationCollateralAndRedeemReserveCollateralV2(final AccountMeta invokedExtKaminoProgramMeta,
                                                                                            final PublicKey glamStateKey,
                                                                                            final PublicKey glamVaultKey,
                                                                                            final PublicKey glamSignerKey,
                                                                                            final PublicKey integrationAuthorityKey,
                                                                                            final PublicKey cpiProgramKey,
                                                                                            final PublicKey glamProtocolProgramKey,
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
      createRead(integrationAuthorityKey),
      createRead(cpiProgramKey),
      createRead(glamProtocolProgramKey),
      createWrite(obligationKey),
      createRead(lendingMarketKey),
      createRead(lendingMarketAuthorityKey),
      createWrite(withdrawReserveKey),
      createRead(reserveLiquidityMintKey),
      createWrite(reserveSourceCollateralKey),
      createWrite(reserveCollateralMintKey),
      createWrite(reserveLiquiditySupplyKey),
      createWrite(userDestinationLiquidityKey),
      createRead(requireNonNullElse(placeholderUserDestinationCollateralKey, invokedExtKaminoProgramMeta.publicKey())),
      createRead(collateralTokenProgramKey),
      createRead(liquidityTokenProgramKey),
      createRead(instructionSysvarAccountKey),
      createWrite(requireNonNullElse(obligationFarmUserStateKey, invokedExtKaminoProgramMeta.publicKey())),
      createWrite(requireNonNullElse(reserveFarmStateKey, invokedExtKaminoProgramMeta.publicKey())),
      createRead(farmsProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = LENDING_WITHDRAW_OBLIGATION_COLLATERAL_AND_REDEEM_RESERVE_COLLATERAL_V_2_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, collateralAmount);

    return Instruction.createInstruction(invokedExtKaminoProgramMeta, keys, _data);
  }

  public record LendingWithdrawObligationCollateralAndRedeemReserveCollateralV2IxData(Discriminator discriminator, long collateralAmount) implements Borsh {  

    public static LendingWithdrawObligationCollateralAndRedeemReserveCollateralV2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static LendingWithdrawObligationCollateralAndRedeemReserveCollateralV2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var collateralAmount = getInt64LE(_data, i);
      return new LendingWithdrawObligationCollateralAndRedeemReserveCollateralV2IxData(discriminator, collateralAmount);
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

  public static final Discriminator VAULTS_DEPOSIT_DISCRIMINATOR = toDiscriminator(124, 173, 191, 223, 48, 26, 84, 84);

  public static Instruction vaultsDeposit(final AccountMeta invokedExtKaminoProgramMeta,
                                          final PublicKey glamStateKey,
                                          final PublicKey glamVaultKey,
                                          final PublicKey glamSignerKey,
                                          final PublicKey integrationAuthorityKey,
                                          final PublicKey cpiProgramKey,
                                          final PublicKey glamProtocolProgramKey,
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
                                          final long maxAmount) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(integrationAuthorityKey),
      createRead(cpiProgramKey),
      createRead(glamProtocolProgramKey),
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
    int i = VAULTS_DEPOSIT_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, maxAmount);

    return Instruction.createInstruction(invokedExtKaminoProgramMeta, keys, _data);
  }

  public record VaultsDepositIxData(Discriminator discriminator, long maxAmount) implements Borsh {  

    public static VaultsDepositIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static VaultsDepositIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var maxAmount = getInt64LE(_data, i);
      return new VaultsDepositIxData(discriminator, maxAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, maxAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator VAULTS_WITHDRAW_DISCRIMINATOR = toDiscriminator(12, 8, 236, 92, 134, 144, 196, 87);

  public static Instruction vaultsWithdraw(final AccountMeta invokedExtKaminoProgramMeta,
                                           final PublicKey glamStateKey,
                                           final PublicKey glamVaultKey,
                                           final PublicKey glamSignerKey,
                                           final PublicKey integrationAuthorityKey,
                                           final PublicKey cpiProgramKey,
                                           final PublicKey glamProtocolProgramKey,
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
                                           final long sharesAmount) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(integrationAuthorityKey),
      createRead(cpiProgramKey),
      createRead(glamProtocolProgramKey),
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
    int i = VAULTS_WITHDRAW_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, sharesAmount);

    return Instruction.createInstruction(invokedExtKaminoProgramMeta, keys, _data);
  }

  public record VaultsWithdrawIxData(Discriminator discriminator, long sharesAmount) implements Borsh {  

    public static VaultsWithdrawIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static VaultsWithdrawIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var sharesAmount = getInt64LE(_data, i);
      return new VaultsWithdrawIxData(discriminator, sharesAmount);
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

  private ExtKaminoProgram() {
  }
}
