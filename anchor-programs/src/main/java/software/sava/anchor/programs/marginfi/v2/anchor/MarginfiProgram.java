package software.sava.anchor.programs.marginfi.v2.anchor;

import java.lang.Boolean;

import java.util.List;
import java.util.OptionalLong;

import software.sava.anchor.programs.marginfi.v2.anchor.types.BankConfigCompact;
import software.sava.anchor.programs.marginfi.v2.anchor.types.BankConfigOpt;
import software.sava.anchor.programs.marginfi.v2.anchor.types.EmodeEntry;
import software.sava.anchor.programs.marginfi.v2.anchor.types.InterestRateConfigOpt;
import software.sava.anchor.programs.marginfi.v2.anchor.types.StakedSettingsConfig;
import software.sava.anchor.programs.marginfi.v2.anchor.types.StakedSettingsEditConfig;
import software.sava.anchor.programs.marginfi.v2.anchor.types.WrappedI80F48;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

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
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class MarginfiProgram {

  public static final Discriminator CONFIG_GROUP_FEE_DISCRIMINATOR = toDiscriminator(231, 205, 66, 242, 220, 87, 145, 38);

  // (global fee admin only) Enable or disable program fees for any group. Does not require the
  // group admin to sign: the global fee state admin can turn program fees on or off for any
  // group
  public static Instruction configGroupFee(final AccountMeta invokedMarginfiProgramMeta,
                                           final PublicKey marginfiGroupKey,
                                           // `global_fee_admin` of the FeeState
                                           final PublicKey globalFeeAdminKey,
                                           final PublicKey feeStateKey,
                                           final boolean enableProgramFee) {
    final var keys = List.of(
      createWrite(marginfiGroupKey),
      createReadOnlySigner(globalFeeAdminKey),
      createRead(feeStateKey)
    );

    final byte[] _data = new byte[9];
    int i = CONFIG_GROUP_FEE_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) (enableProgramFee ? 1 : 0);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record ConfigGroupFeeIxData(Discriminator discriminator, boolean enableProgramFee) implements Borsh {  

    public static ConfigGroupFeeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static ConfigGroupFeeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var enableProgramFee = _data[i] == 1;
      return new ConfigGroupFeeIxData(discriminator, enableProgramFee);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) (enableProgramFee ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator EDIT_GLOBAL_FEE_STATE_DISCRIMINATOR = toDiscriminator(52, 62, 35, 129, 93, 69, 165, 202);

  // (global fee admin only) Adjust fees, admin, or the destination wallet
  public static Instruction editGlobalFeeState(final AccountMeta invokedMarginfiProgramMeta,
                                               // Admin of the global FeeState
                                               final PublicKey globalFeeAdminKey,
                                               final PublicKey feeStateKey,
                                               final PublicKey admin,
                                               final PublicKey feeWallet,
                                               final int bankInitFlatSolFee,
                                               final WrappedI80F48 programFeeFixed,
                                               final WrappedI80F48 programFeeRate) {
    final var keys = List.of(
      createWritableSigner(globalFeeAdminKey),
      createWrite(feeStateKey)
    );

    final byte[] _data = new byte[76 + Borsh.len(programFeeFixed) + Borsh.len(programFeeRate)];
    int i = EDIT_GLOBAL_FEE_STATE_DISCRIMINATOR.write(_data, 0);
    admin.write(_data, i);
    i += 32;
    feeWallet.write(_data, i);
    i += 32;
    putInt32LE(_data, i, bankInitFlatSolFee);
    i += 4;
    i += Borsh.write(programFeeFixed, _data, i);
    Borsh.write(programFeeRate, _data, i);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record EditGlobalFeeStateIxData(Discriminator discriminator,
                                         PublicKey admin,
                                         PublicKey feeWallet,
                                         int bankInitFlatSolFee,
                                         WrappedI80F48 programFeeFixed,
                                         WrappedI80F48 programFeeRate) implements Borsh {  

    public static EditGlobalFeeStateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 108;

    public static EditGlobalFeeStateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var admin = readPubKey(_data, i);
      i += 32;
      final var feeWallet = readPubKey(_data, i);
      i += 32;
      final var bankInitFlatSolFee = getInt32LE(_data, i);
      i += 4;
      final var programFeeFixed = WrappedI80F48.read(_data, i);
      i += Borsh.len(programFeeFixed);
      final var programFeeRate = WrappedI80F48.read(_data, i);
      return new EditGlobalFeeStateIxData(discriminator,
                                          admin,
                                          feeWallet,
                                          bankInitFlatSolFee,
                                          programFeeFixed,
                                          programFeeRate);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      admin.write(_data, i);
      i += 32;
      feeWallet.write(_data, i);
      i += 32;
      putInt32LE(_data, i, bankInitFlatSolFee);
      i += 4;
      i += Borsh.write(programFeeFixed, _data, i);
      i += Borsh.write(programFeeRate, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator EDIT_STAKED_SETTINGS_DISCRIMINATOR = toDiscriminator(11, 108, 215, 87, 240, 9, 66, 241);

  public static Instruction editStakedSettings(final AccountMeta invokedMarginfiProgramMeta,
                                               final PublicKey marginfiGroupKey,
                                               final PublicKey adminKey,
                                               final PublicKey stakedSettingsKey,
                                               final StakedSettingsEditConfig settings) {
    final var keys = List.of(
      createRead(marginfiGroupKey),
      createReadOnlySigner(adminKey),
      createWrite(stakedSettingsKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(settings)];
    int i = EDIT_STAKED_SETTINGS_DISCRIMINATOR.write(_data, 0);
    Borsh.write(settings, _data, i);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record EditStakedSettingsIxData(Discriminator discriminator, StakedSettingsEditConfig settings) implements Borsh {  

    public static EditStakedSettingsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static EditStakedSettingsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var settings = StakedSettingsEditConfig.read(_data, i);
      return new EditStakedSettingsIxData(discriminator, settings);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(settings, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(settings);
    }
  }

  public static final Discriminator INIT_GLOBAL_FEE_STATE_DISCRIMINATOR = toDiscriminator(82, 48, 247, 59, 220, 109, 231, 44);

  // (Runs once per program) Configures the fee state account, where the global admin sets fees
  // that are assessed to the protocol
  public static Instruction initGlobalFeeState(final AccountMeta invokedMarginfiProgramMeta,
                                               final SolanaAccounts solanaAccounts,
                                               // Pays the init fee
                                               final PublicKey payerKey,
                                               final PublicKey feeStateKey,
                                               final PublicKey admin,
                                               final PublicKey feeWallet,
                                               final int bankInitFlatSolFee,
                                               final WrappedI80F48 programFeeFixed,
                                               final WrappedI80F48 programFeeRate) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createWrite(feeStateKey),
      createRead(solanaAccounts.rentSysVar()),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[76 + Borsh.len(programFeeFixed) + Borsh.len(programFeeRate)];
    int i = INIT_GLOBAL_FEE_STATE_DISCRIMINATOR.write(_data, 0);
    admin.write(_data, i);
    i += 32;
    feeWallet.write(_data, i);
    i += 32;
    putInt32LE(_data, i, bankInitFlatSolFee);
    i += 4;
    i += Borsh.write(programFeeFixed, _data, i);
    Borsh.write(programFeeRate, _data, i);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record InitGlobalFeeStateIxData(Discriminator discriminator,
                                         PublicKey admin,
                                         PublicKey feeWallet,
                                         int bankInitFlatSolFee,
                                         WrappedI80F48 programFeeFixed,
                                         WrappedI80F48 programFeeRate) implements Borsh {  

    public static InitGlobalFeeStateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 108;

    public static InitGlobalFeeStateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var admin = readPubKey(_data, i);
      i += 32;
      final var feeWallet = readPubKey(_data, i);
      i += 32;
      final var bankInitFlatSolFee = getInt32LE(_data, i);
      i += 4;
      final var programFeeFixed = WrappedI80F48.read(_data, i);
      i += Borsh.len(programFeeFixed);
      final var programFeeRate = WrappedI80F48.read(_data, i);
      return new InitGlobalFeeStateIxData(discriminator,
                                          admin,
                                          feeWallet,
                                          bankInitFlatSolFee,
                                          programFeeFixed,
                                          programFeeRate);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      admin.write(_data, i);
      i += 32;
      feeWallet.write(_data, i);
      i += 32;
      putInt32LE(_data, i, bankInitFlatSolFee);
      i += 4;
      i += Borsh.write(programFeeFixed, _data, i);
      i += Borsh.write(programFeeRate, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INIT_STAKED_SETTINGS_DISCRIMINATOR = toDiscriminator(52, 35, 149, 44, 69, 86, 69, 80);

  // (group admin only) Init the Staked Settings account, which is used to create staked
  // collateral banks, and must run before any staked collateral bank can be created with
  // `add_pool_permissionless`. Running this ix effectively opts the group into the staked
  // collateral feature.
  public static Instruction initStakedSettings(final AccountMeta invokedMarginfiProgramMeta,
                                               final SolanaAccounts solanaAccounts,
                                               final PublicKey marginfiGroupKey,
                                               final PublicKey adminKey,
                                               // Pays the init fee
                                               final PublicKey feePayerKey,
                                               final PublicKey stakedSettingsKey,
                                               final StakedSettingsConfig settings) {
    final var keys = List.of(
      createRead(marginfiGroupKey),
      createReadOnlySigner(adminKey),
      createWritableSigner(feePayerKey),
      createWrite(stakedSettingsKey),
      createRead(solanaAccounts.rentSysVar()),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(settings)];
    int i = INIT_STAKED_SETTINGS_DISCRIMINATOR.write(_data, 0);
    Borsh.write(settings, _data, i);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record InitStakedSettingsIxData(Discriminator discriminator, StakedSettingsConfig settings) implements Borsh {  

    public static InitStakedSettingsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 91;

    public static InitStakedSettingsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var settings = StakedSettingsConfig.read(_data, i);
      return new InitStakedSettingsIxData(discriminator, settings);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(settings, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator LENDING_ACCOUNT_BORROW_DISCRIMINATOR = toDiscriminator(4, 126, 116, 53, 48, 5, 212, 31);

  public static Instruction lendingAccountBorrow(final AccountMeta invokedMarginfiProgramMeta,
                                                 final PublicKey groupKey,
                                                 final PublicKey marginfiAccountKey,
                                                 final PublicKey authorityKey,
                                                 final PublicKey bankKey,
                                                 final PublicKey destinationTokenAccountKey,
                                                 final PublicKey bankLiquidityVaultAuthorityKey,
                                                 final PublicKey liquidityVaultKey,
                                                 final PublicKey tokenProgramKey,
                                                 final long amount) {
    final var keys = List.of(
      createRead(groupKey),
      createWrite(marginfiAccountKey),
      createReadOnlySigner(authorityKey),
      createWrite(bankKey),
      createWrite(destinationTokenAccountKey),
      createWrite(bankLiquidityVaultAuthorityKey),
      createWrite(liquidityVaultKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = LENDING_ACCOUNT_BORROW_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record LendingAccountBorrowIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static LendingAccountBorrowIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static LendingAccountBorrowIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new LendingAccountBorrowIxData(discriminator, amount);
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

  public static final Discriminator LENDING_ACCOUNT_CLOSE_BALANCE_DISCRIMINATOR = toDiscriminator(245, 54, 41, 4, 243, 202, 31, 17);

  public static Instruction lendingAccountCloseBalance(final AccountMeta invokedMarginfiProgramMeta,
                                                       final PublicKey groupKey,
                                                       final PublicKey marginfiAccountKey,
                                                       final PublicKey authorityKey,
                                                       final PublicKey bankKey) {
    final var keys = List.of(
      createRead(groupKey),
      createWrite(marginfiAccountKey),
      createReadOnlySigner(authorityKey),
      createWrite(bankKey)
    );

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, LENDING_ACCOUNT_CLOSE_BALANCE_DISCRIMINATOR);
  }

  public static final Discriminator LENDING_ACCOUNT_DEPOSIT_DISCRIMINATOR = toDiscriminator(171, 94, 235, 103, 82, 64, 212, 140);

  public static Instruction lendingAccountDeposit(final AccountMeta invokedMarginfiProgramMeta,
                                                  final PublicKey groupKey,
                                                  final PublicKey marginfiAccountKey,
                                                  final PublicKey authorityKey,
                                                  final PublicKey bankKey,
                                                  final PublicKey signerTokenAccountKey,
                                                  final PublicKey liquidityVaultKey,
                                                  final PublicKey tokenProgramKey,
                                                  final long amount,
                                                  final Boolean depositUpToLimit) {
    final var keys = List.of(
      createRead(groupKey),
      createWrite(marginfiAccountKey),
      createReadOnlySigner(authorityKey),
      createWrite(bankKey),
      createWrite(signerTokenAccountKey),
      createWrite(liquidityVaultKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[
        16
        + (depositUpToLimit == null ? 1 : 2)
    ];
    int i = LENDING_ACCOUNT_DEPOSIT_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    Borsh.writeOptional(depositUpToLimit, _data, i);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record LendingAccountDepositIxData(Discriminator discriminator, long amount, Boolean depositUpToLimit) implements Borsh {  

    public static LendingAccountDepositIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static LendingAccountDepositIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var depositUpToLimit = _data[i++] == 0 ? null : _data[i] == 1;
      return new LendingAccountDepositIxData(discriminator, amount, depositUpToLimit);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      i += Borsh.writeOptional(depositUpToLimit, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + (depositUpToLimit == null ? 1 : (1 + 1));
    }
  }

  public static final Discriminator LENDING_ACCOUNT_END_FLASHLOAN_DISCRIMINATOR = toDiscriminator(105, 124, 201, 106, 153, 2, 8, 156);

  public static Instruction lendingAccountEndFlashloan(final AccountMeta invokedMarginfiProgramMeta, final PublicKey marginfiAccountKey, final PublicKey authorityKey) {
    final var keys = List.of(
      createWrite(marginfiAccountKey),
      createReadOnlySigner(authorityKey)
    );

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, LENDING_ACCOUNT_END_FLASHLOAN_DISCRIMINATOR);
  }

  public static final Discriminator LENDING_ACCOUNT_LIQUIDATE_DISCRIMINATOR = toDiscriminator(214, 169, 151, 213, 251, 167, 86, 219);

  // Liquidate a lending account balance of an unhealthy marginfi account
  public static Instruction lendingAccountLiquidate(final AccountMeta invokedMarginfiProgramMeta,
                                                    final PublicKey groupKey,
                                                    final PublicKey assetBankKey,
                                                    final PublicKey liabBankKey,
                                                    final PublicKey liquidatorMarginfiAccountKey,
                                                    final PublicKey authorityKey,
                                                    final PublicKey liquidateeMarginfiAccountKey,
                                                    final PublicKey bankLiquidityVaultAuthorityKey,
                                                    final PublicKey bankLiquidityVaultKey,
                                                    final PublicKey bankInsuranceVaultKey,
                                                    final PublicKey tokenProgramKey,
                                                    final long assetAmount) {
    final var keys = List.of(
      createRead(groupKey),
      createWrite(assetBankKey),
      createWrite(liabBankKey),
      createWrite(liquidatorMarginfiAccountKey),
      createReadOnlySigner(authorityKey),
      createWrite(liquidateeMarginfiAccountKey),
      createWrite(bankLiquidityVaultAuthorityKey),
      createWrite(bankLiquidityVaultKey),
      createWrite(bankInsuranceVaultKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = LENDING_ACCOUNT_LIQUIDATE_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, assetAmount);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record LendingAccountLiquidateIxData(Discriminator discriminator, long assetAmount) implements Borsh {  

    public static LendingAccountLiquidateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static LendingAccountLiquidateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var assetAmount = getInt64LE(_data, i);
      return new LendingAccountLiquidateIxData(discriminator, assetAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, assetAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator LENDING_ACCOUNT_PULSE_HEALTH_DISCRIMINATOR = toDiscriminator(186, 52, 117, 97, 34, 74, 39, 253);

  // (Permissionless) Refresh the internal risk engine health cache. Useful for liquidators and
  // other consumers that want to see the internal risk state of a user account. This cache is
  // read-only and serves no purpose except being populated by this ix.
  // * remaining accounts expected in the same order as borrow, etc. I.e., for each balance the
  // user has, pass bank and oracle: <bank1, oracle1, bank2, oracle2>
  public static Instruction lendingAccountPulseHealth(final AccountMeta invokedMarginfiProgramMeta, final PublicKey marginfiAccountKey) {
    final var keys = List.of(
      createWrite(marginfiAccountKey)
    );

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, LENDING_ACCOUNT_PULSE_HEALTH_DISCRIMINATOR);
  }

  public static final Discriminator LENDING_ACCOUNT_REPAY_DISCRIMINATOR = toDiscriminator(79, 209, 172, 177, 222, 51, 173, 151);

  public static Instruction lendingAccountRepay(final AccountMeta invokedMarginfiProgramMeta,
                                                final PublicKey groupKey,
                                                final PublicKey marginfiAccountKey,
                                                final PublicKey authorityKey,
                                                final PublicKey bankKey,
                                                final PublicKey signerTokenAccountKey,
                                                final PublicKey liquidityVaultKey,
                                                final PublicKey tokenProgramKey,
                                                final long amount,
                                                final Boolean repayAll) {
    final var keys = List.of(
      createRead(groupKey),
      createWrite(marginfiAccountKey),
      createReadOnlySigner(authorityKey),
      createWrite(bankKey),
      createWrite(signerTokenAccountKey),
      createWrite(liquidityVaultKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[
        16
        + (repayAll == null ? 1 : 2)
    ];
    int i = LENDING_ACCOUNT_REPAY_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    Borsh.writeOptional(repayAll, _data, i);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record LendingAccountRepayIxData(Discriminator discriminator, long amount, Boolean repayAll) implements Borsh {  

    public static LendingAccountRepayIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static LendingAccountRepayIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var repayAll = _data[i++] == 0 ? null : _data[i] == 1;
      return new LendingAccountRepayIxData(discriminator, amount, repayAll);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      i += Borsh.writeOptional(repayAll, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + (repayAll == null ? 1 : (1 + 1));
    }
  }

  public static final Discriminator LENDING_ACCOUNT_SETTLE_EMISSIONS_DISCRIMINATOR = toDiscriminator(161, 58, 136, 174, 242, 223, 156, 176);

  public static Instruction lendingAccountSettleEmissions(final AccountMeta invokedMarginfiProgramMeta, final PublicKey marginfiAccountKey, final PublicKey bankKey) {
    final var keys = List.of(
      createWrite(marginfiAccountKey),
      createWrite(bankKey)
    );

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, LENDING_ACCOUNT_SETTLE_EMISSIONS_DISCRIMINATOR);
  }

  public static final Discriminator LENDING_ACCOUNT_SORT_BALANCES_DISCRIMINATOR = toDiscriminator(187, 194, 110, 84, 82, 170, 204, 9);

  // (Permissionless) Sorts the lending account balances in descending order and removes the "gaps"
  // (i.e. inactive balances in between the active ones), if any.
  // This is necessary to ensure any legacy marginfi accounts are compliant with the
  // "gapless and sorted" requirements we now have.
  public static Instruction lendingAccountSortBalances(final AccountMeta invokedMarginfiProgramMeta, final PublicKey marginfiAccountKey) {
    final var keys = List.of(
      createWrite(marginfiAccountKey)
    );

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, LENDING_ACCOUNT_SORT_BALANCES_DISCRIMINATOR);
  }

  public static final Discriminator LENDING_ACCOUNT_START_FLASHLOAN_DISCRIMINATOR = toDiscriminator(14, 131, 33, 220, 81, 186, 180, 107);

  public static Instruction lendingAccountStartFlashloan(final AccountMeta invokedMarginfiProgramMeta,
                                                         final SolanaAccounts solanaAccounts,
                                                         final PublicKey marginfiAccountKey,
                                                         final PublicKey authorityKey,
                                                         final long endIndex) {
    final var keys = List.of(
      createWrite(marginfiAccountKey),
      createReadOnlySigner(authorityKey),
      createRead(solanaAccounts.instructionsSysVar())
    );

    final byte[] _data = new byte[16];
    int i = LENDING_ACCOUNT_START_FLASHLOAN_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, endIndex);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record LendingAccountStartFlashloanIxData(Discriminator discriminator, long endIndex) implements Borsh {  

    public static LendingAccountStartFlashloanIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static LendingAccountStartFlashloanIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var endIndex = getInt64LE(_data, i);
      return new LendingAccountStartFlashloanIxData(discriminator, endIndex);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, endIndex);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator LENDING_ACCOUNT_WITHDRAW_DISCRIMINATOR = toDiscriminator(36, 72, 74, 19, 210, 210, 192, 192);

  public static Instruction lendingAccountWithdraw(final AccountMeta invokedMarginfiProgramMeta,
                                                   final PublicKey groupKey,
                                                   final PublicKey marginfiAccountKey,
                                                   final PublicKey authorityKey,
                                                   final PublicKey bankKey,
                                                   final PublicKey destinationTokenAccountKey,
                                                   final PublicKey bankLiquidityVaultAuthorityKey,
                                                   final PublicKey liquidityVaultKey,
                                                   final PublicKey tokenProgramKey,
                                                   final long amount,
                                                   final Boolean withdrawAll) {
    final var keys = List.of(
      createRead(groupKey),
      createWrite(marginfiAccountKey),
      createReadOnlySigner(authorityKey),
      createWrite(bankKey),
      createWrite(destinationTokenAccountKey),
      createRead(bankLiquidityVaultAuthorityKey),
      createWrite(liquidityVaultKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[
        16
        + (withdrawAll == null ? 1 : 2)
    ];
    int i = LENDING_ACCOUNT_WITHDRAW_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    Borsh.writeOptional(withdrawAll, _data, i);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record LendingAccountWithdrawIxData(Discriminator discriminator, long amount, Boolean withdrawAll) implements Borsh {  

    public static LendingAccountWithdrawIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static LendingAccountWithdrawIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var withdrawAll = _data[i++] == 0 ? null : _data[i] == 1;
      return new LendingAccountWithdrawIxData(discriminator, amount, withdrawAll);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      i += Borsh.writeOptional(withdrawAll, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + (withdrawAll == null ? 1 : (1 + 1));
    }
  }

  public static final Discriminator LENDING_ACCOUNT_WITHDRAW_EMISSIONS_DISCRIMINATOR = toDiscriminator(234, 22, 84, 214, 118, 176, 140, 170);

  public static Instruction lendingAccountWithdrawEmissions(final AccountMeta invokedMarginfiProgramMeta,
                                                            final PublicKey groupKey,
                                                            final PublicKey marginfiAccountKey,
                                                            final PublicKey authorityKey,
                                                            final PublicKey bankKey,
                                                            final PublicKey emissionsMintKey,
                                                            final PublicKey emissionsAuthKey,
                                                            final PublicKey emissionsVaultKey,
                                                            final PublicKey destinationAccountKey,
                                                            final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(groupKey),
      createWrite(marginfiAccountKey),
      createReadOnlySigner(authorityKey),
      createWrite(bankKey),
      createRead(emissionsMintKey),
      createRead(emissionsAuthKey),
      createWrite(emissionsVaultKey),
      createWrite(destinationAccountKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, LENDING_ACCOUNT_WITHDRAW_EMISSIONS_DISCRIMINATOR);
  }

  public static final Discriminator LENDING_ACCOUNT_WITHDRAW_EMISSIONS_PERMISSIONLESS_DISCRIMINATOR = toDiscriminator(4, 174, 124, 203, 44, 49, 145, 150);

  public static Instruction lendingAccountWithdrawEmissionsPermissionless(final AccountMeta invokedMarginfiProgramMeta,
                                                                          final PublicKey groupKey,
                                                                          final PublicKey marginfiAccountKey,
                                                                          final PublicKey bankKey,
                                                                          final PublicKey emissionsMintKey,
                                                                          final PublicKey emissionsAuthKey,
                                                                          final PublicKey emissionsVaultKey,
                                                                          // registered on `marginfi_account`
                                                                          final PublicKey destinationAccountKey,
                                                                          final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(groupKey),
      createWrite(marginfiAccountKey),
      createWrite(bankKey),
      createRead(emissionsMintKey),
      createRead(emissionsAuthKey),
      createWrite(emissionsVaultKey),
      createWrite(destinationAccountKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, LENDING_ACCOUNT_WITHDRAW_EMISSIONS_PERMISSIONLESS_DISCRIMINATOR);
  }

  public static final Discriminator LENDING_POOL_ACCRUE_BANK_INTEREST_DISCRIMINATOR = toDiscriminator(108, 201, 30, 87, 47, 65, 97, 188);

  public static Instruction lendingPoolAccrueBankInterest(final AccountMeta invokedMarginfiProgramMeta, final PublicKey groupKey, final PublicKey bankKey) {
    final var keys = List.of(
      createRead(groupKey),
      createWrite(bankKey)
    );

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, LENDING_POOL_ACCRUE_BANK_INTEREST_DISCRIMINATOR);
  }

  public static final Discriminator LENDING_POOL_ADD_BANK_DISCRIMINATOR = toDiscriminator(215, 68, 72, 78, 208, 218, 103, 182);

  public static Instruction lendingPoolAddBank(final AccountMeta invokedMarginfiProgramMeta,
                                               final SolanaAccounts solanaAccounts,
                                               final PublicKey marginfiGroupKey,
                                               final PublicKey adminKey,
                                               // Pays to init accounts and pays `fee_state.bank_init_flat_sol_fee` lamports to the protocol
                                               final PublicKey feePayerKey,
                                               final PublicKey feeStateKey,
                                               final PublicKey globalFeeWalletKey,
                                               final PublicKey bankMintKey,
                                               final PublicKey bankKey,
                                               final PublicKey liquidityVaultAuthorityKey,
                                               final PublicKey liquidityVaultKey,
                                               final PublicKey insuranceVaultAuthorityKey,
                                               final PublicKey insuranceVaultKey,
                                               final PublicKey feeVaultAuthorityKey,
                                               final PublicKey feeVaultKey,
                                               final PublicKey tokenProgramKey,
                                               final BankConfigCompact bankConfig) {
    final var keys = List.of(
      createWrite(marginfiGroupKey),
      createWritableSigner(adminKey),
      createWritableSigner(feePayerKey),
      createRead(feeStateKey),
      createWrite(globalFeeWalletKey),
      createRead(bankMintKey),
      createWritableSigner(bankKey),
      createRead(liquidityVaultAuthorityKey),
      createWrite(liquidityVaultKey),
      createRead(insuranceVaultAuthorityKey),
      createWrite(insuranceVaultKey),
      createRead(feeVaultAuthorityKey),
      createWrite(feeVaultKey),
      createRead(solanaAccounts.rentSysVar()),
      createRead(tokenProgramKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(bankConfig)];
    int i = LENDING_POOL_ADD_BANK_DISCRIMINATOR.write(_data, 0);
    Borsh.write(bankConfig, _data, i);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record LendingPoolAddBankIxData(Discriminator discriminator, BankConfigCompact bankConfig) implements Borsh {  

    public static LendingPoolAddBankIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 239;

    public static LendingPoolAddBankIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var bankConfig = BankConfigCompact.read(_data, i);
      return new LendingPoolAddBankIxData(discriminator, bankConfig);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(bankConfig, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator LENDING_POOL_ADD_BANK_PERMISSIONLESS_DISCRIMINATOR = toDiscriminator(127, 187, 121, 34, 187, 167, 238, 102);

  public static Instruction lendingPoolAddBankPermissionless(final AccountMeta invokedMarginfiProgramMeta,
                                                             final SolanaAccounts solanaAccounts,
                                                             final PublicKey marginfiGroupKey,
                                                             final PublicKey stakedSettingsKey,
                                                             final PublicKey feePayerKey,
                                                             // Mint of the spl-single-pool LST (a PDA derived from `stake_pool`)
                                                             // 
                                                             // because the sol_pool and stake_pool will not derive to a valid PDA which is also owned by
                                                             // the staking program and spl-single-pool program.
                                                             final PublicKey bankMintKey,
                                                             final PublicKey solPoolKey,
                                                             // this key.
                                                             // 
                                                             // If derives the same `bank_mint`, then this must be the correct stake pool for that mint, and
                                                             // we can subsequently use it to validate the `sol_pool`
                                                             final PublicKey stakePoolKey,
                                                             final PublicKey bankKey,
                                                             final PublicKey liquidityVaultAuthorityKey,
                                                             final PublicKey liquidityVaultKey,
                                                             final PublicKey insuranceVaultAuthorityKey,
                                                             final PublicKey insuranceVaultKey,
                                                             final PublicKey feeVaultAuthorityKey,
                                                             final PublicKey feeVaultKey,
                                                             final PublicKey tokenProgramKey,
                                                             final long bankSeed) {
    final var keys = List.of(
      createWrite(marginfiGroupKey),
      createRead(stakedSettingsKey),
      createWritableSigner(feePayerKey),
      createRead(bankMintKey),
      createRead(solPoolKey),
      createRead(stakePoolKey),
      createWrite(bankKey),
      createRead(liquidityVaultAuthorityKey),
      createWrite(liquidityVaultKey),
      createRead(insuranceVaultAuthorityKey),
      createWrite(insuranceVaultKey),
      createRead(feeVaultAuthorityKey),
      createWrite(feeVaultKey),
      createRead(solanaAccounts.rentSysVar()),
      createRead(tokenProgramKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[16];
    int i = LENDING_POOL_ADD_BANK_PERMISSIONLESS_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, bankSeed);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record LendingPoolAddBankPermissionlessIxData(Discriminator discriminator, long bankSeed) implements Borsh {  

    public static LendingPoolAddBankPermissionlessIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static LendingPoolAddBankPermissionlessIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var bankSeed = getInt64LE(_data, i);
      return new LendingPoolAddBankPermissionlessIxData(discriminator, bankSeed);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, bankSeed);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator LENDING_POOL_ADD_BANK_WITH_SEED_DISCRIMINATOR = toDiscriminator(76, 211, 213, 171, 117, 78, 158, 76);

  // A copy of lending_pool_add_bank with an additional bank seed.
  // This seed is used to create a PDA for the bank's signature.
  // lending_pool_add_bank is preserved for backwards compatibility.
  public static Instruction lendingPoolAddBankWithSeed(final AccountMeta invokedMarginfiProgramMeta,
                                                       final SolanaAccounts solanaAccounts,
                                                       final PublicKey marginfiGroupKey,
                                                       final PublicKey adminKey,
                                                       // Pays to init accounts and pays `fee_state.bank_init_flat_sol_fee` lamports to the protocol
                                                       final PublicKey feePayerKey,
                                                       final PublicKey feeStateKey,
                                                       final PublicKey globalFeeWalletKey,
                                                       final PublicKey bankMintKey,
                                                       final PublicKey bankKey,
                                                       final PublicKey liquidityVaultAuthorityKey,
                                                       final PublicKey liquidityVaultKey,
                                                       final PublicKey insuranceVaultAuthorityKey,
                                                       final PublicKey insuranceVaultKey,
                                                       final PublicKey feeVaultAuthorityKey,
                                                       final PublicKey feeVaultKey,
                                                       final PublicKey tokenProgramKey,
                                                       final BankConfigCompact bankConfig,
                                                       final long bankSeed) {
    final var keys = List.of(
      createWrite(marginfiGroupKey),
      createWritableSigner(adminKey),
      createWritableSigner(feePayerKey),
      createRead(feeStateKey),
      createWrite(globalFeeWalletKey),
      createRead(bankMintKey),
      createWrite(bankKey),
      createRead(liquidityVaultAuthorityKey),
      createWrite(liquidityVaultKey),
      createRead(insuranceVaultAuthorityKey),
      createWrite(insuranceVaultKey),
      createRead(feeVaultAuthorityKey),
      createWrite(feeVaultKey),
      createRead(solanaAccounts.rentSysVar()),
      createRead(tokenProgramKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[16 + Borsh.len(bankConfig)];
    int i = LENDING_POOL_ADD_BANK_WITH_SEED_DISCRIMINATOR.write(_data, 0);
    i += Borsh.write(bankConfig, _data, i);
    putInt64LE(_data, i, bankSeed);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record LendingPoolAddBankWithSeedIxData(Discriminator discriminator, BankConfigCompact bankConfig, long bankSeed) implements Borsh {  

    public static LendingPoolAddBankWithSeedIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 247;

    public static LendingPoolAddBankWithSeedIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var bankConfig = BankConfigCompact.read(_data, i);
      i += Borsh.len(bankConfig);
      final var bankSeed = getInt64LE(_data, i);
      return new LendingPoolAddBankWithSeedIxData(discriminator, bankConfig, bankSeed);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(bankConfig, _data, i);
      putInt64LE(_data, i, bankSeed);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator LENDING_POOL_CLOSE_BANK_DISCRIMINATOR = toDiscriminator(22, 115, 7, 130, 227, 85, 0, 47);

  public static Instruction lendingPoolCloseBank(final AccountMeta invokedMarginfiProgramMeta,
                                                 final PublicKey groupKey,
                                                 final PublicKey bankKey,
                                                 final PublicKey adminKey) {
    final var keys = List.of(
      createWrite(groupKey),
      createWrite(bankKey),
      createWritableSigner(adminKey)
    );

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, LENDING_POOL_CLOSE_BANK_DISCRIMINATOR);
  }

  public static final Discriminator LENDING_POOL_COLLECT_BANK_FEES_DISCRIMINATOR = toDiscriminator(201, 5, 215, 116, 230, 92, 75, 150);

  public static Instruction lendingPoolCollectBankFees(final AccountMeta invokedMarginfiProgramMeta,
                                                       final PublicKey groupKey,
                                                       final PublicKey bankKey,
                                                       final PublicKey liquidityVaultAuthorityKey,
                                                       final PublicKey liquidityVaultKey,
                                                       final PublicKey insuranceVaultKey,
                                                       final PublicKey feeVaultKey,
                                                       final PublicKey feeStateKey,
                                                       // (validated in handler). Must already exist, may require initializing the ATA if it does not
                                                       // already exist prior to this ix.
                                                       final PublicKey feeAtaKey,
                                                       final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(groupKey),
      createWrite(bankKey),
      createRead(liquidityVaultAuthorityKey),
      createWrite(liquidityVaultKey),
      createWrite(insuranceVaultKey),
      createWrite(feeVaultKey),
      createRead(feeStateKey),
      createWrite(feeAtaKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, LENDING_POOL_COLLECT_BANK_FEES_DISCRIMINATOR);
  }

  public static final Discriminator LENDING_POOL_CONFIGURE_BANK_DISCRIMINATOR = toDiscriminator(121, 173, 156, 40, 93, 148, 56, 237);

  // (admin only)
  public static Instruction lendingPoolConfigureBank(final AccountMeta invokedMarginfiProgramMeta,
                                                     final PublicKey groupKey,
                                                     final PublicKey adminKey,
                                                     final PublicKey bankKey,
                                                     final BankConfigOpt bankConfigOpt) {
    final var keys = List.of(
      createWrite(groupKey),
      createReadOnlySigner(adminKey),
      createWrite(bankKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(bankConfigOpt)];
    int i = LENDING_POOL_CONFIGURE_BANK_DISCRIMINATOR.write(_data, 0);
    Borsh.write(bankConfigOpt, _data, i);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record LendingPoolConfigureBankIxData(Discriminator discriminator, BankConfigOpt bankConfigOpt) implements Borsh {  

    public static LendingPoolConfigureBankIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static LendingPoolConfigureBankIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var bankConfigOpt = BankConfigOpt.read(_data, i);
      return new LendingPoolConfigureBankIxData(discriminator, bankConfigOpt);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(bankConfigOpt, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(bankConfigOpt);
    }
  }

  public static final Discriminator LENDING_POOL_CONFIGURE_BANK_EMODE_DISCRIMINATOR = toDiscriminator(17, 175, 91, 57, 239, 86, 49, 71);

  // (emode_admin only)
  public static Instruction lendingPoolConfigureBankEmode(final AccountMeta invokedMarginfiProgramMeta,
                                                          final PublicKey groupKey,
                                                          final PublicKey emodeAdminKey,
                                                          final PublicKey bankKey,
                                                          final int emodeTag,
                                                          final EmodeEntry[] entries) {
    final var keys = List.of(
      createRead(groupKey),
      createReadOnlySigner(emodeAdminKey),
      createWrite(bankKey)
    );

    final byte[] _data = new byte[10 + Borsh.lenArray(entries)];
    int i = LENDING_POOL_CONFIGURE_BANK_EMODE_DISCRIMINATOR.write(_data, 0);
    putInt16LE(_data, i, emodeTag);
    i += 2;
    Borsh.writeArrayChecked(entries, 10, _data, i);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record LendingPoolConfigureBankEmodeIxData(Discriminator discriminator, int emodeTag, EmodeEntry[] entries) implements Borsh {  

    public static LendingPoolConfigureBankEmodeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 410;
    public static final int ENTRIES_LEN = 10;

    public static LendingPoolConfigureBankEmodeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var emodeTag = getInt16LE(_data, i);
      i += 2;
      final var entries = new EmodeEntry[10];
      Borsh.readArray(entries, EmodeEntry::read, _data, i);
      return new LendingPoolConfigureBankEmodeIxData(discriminator, emodeTag, entries);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, emodeTag);
      i += 2;
      i += Borsh.writeArrayChecked(entries, 10, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator LENDING_POOL_CONFIGURE_BANK_INTEREST_ONLY_DISCRIMINATOR = toDiscriminator(245, 107, 83, 38, 103, 219, 163, 241);

  // (delegate_curve_admin only)
  public static Instruction lendingPoolConfigureBankInterestOnly(final AccountMeta invokedMarginfiProgramMeta,
                                                                 final PublicKey groupKey,
                                                                 final PublicKey delegateCurveAdminKey,
                                                                 final PublicKey bankKey,
                                                                 final InterestRateConfigOpt interestRateConfig) {
    final var keys = List.of(
      createWrite(groupKey),
      createReadOnlySigner(delegateCurveAdminKey),
      createWrite(bankKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(interestRateConfig)];
    int i = LENDING_POOL_CONFIGURE_BANK_INTEREST_ONLY_DISCRIMINATOR.write(_data, 0);
    Borsh.write(interestRateConfig, _data, i);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record LendingPoolConfigureBankInterestOnlyIxData(Discriminator discriminator, InterestRateConfigOpt interestRateConfig) implements Borsh {  

    public static LendingPoolConfigureBankInterestOnlyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static LendingPoolConfigureBankInterestOnlyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var interestRateConfig = InterestRateConfigOpt.read(_data, i);
      return new LendingPoolConfigureBankInterestOnlyIxData(discriminator, interestRateConfig);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(interestRateConfig, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(interestRateConfig);
    }
  }

  public static final Discriminator LENDING_POOL_CONFIGURE_BANK_LIMITS_ONLY_DISCRIMINATOR = toDiscriminator(157, 196, 221, 200, 202, 62, 84, 21);

  // (delegate_limits_admin only)
  public static Instruction lendingPoolConfigureBankLimitsOnly(final AccountMeta invokedMarginfiProgramMeta,
                                                               final PublicKey groupKey,
                                                               final PublicKey delegateLimitAdminKey,
                                                               final PublicKey bankKey,
                                                               final OptionalLong depositLimit,
                                                               final OptionalLong borrowLimit,
                                                               final OptionalLong totalAssetValueInitLimit) {
    final var keys = List.of(
      createWrite(groupKey),
      createReadOnlySigner(delegateLimitAdminKey),
      createWrite(bankKey)
    );

    final byte[] _data = new byte[
        8
        + (depositLimit == null || depositLimit.isEmpty() ? 1 : 9)
        + (borrowLimit == null || borrowLimit.isEmpty() ? 1 : 9)
        + (totalAssetValueInitLimit == null || totalAssetValueInitLimit.isEmpty() ? 1 : 9)
    ];
    int i = LENDING_POOL_CONFIGURE_BANK_LIMITS_ONLY_DISCRIMINATOR.write(_data, 0);
    i += Borsh.writeOptional(depositLimit, _data, i);
    i += Borsh.writeOptional(borrowLimit, _data, i);
    Borsh.writeOptional(totalAssetValueInitLimit, _data, i);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record LendingPoolConfigureBankLimitsOnlyIxData(Discriminator discriminator,
                                                         OptionalLong depositLimit,
                                                         OptionalLong borrowLimit,
                                                         OptionalLong totalAssetValueInitLimit) implements Borsh {  

    public static LendingPoolConfigureBankLimitsOnlyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static LendingPoolConfigureBankLimitsOnlyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var depositLimit = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
      if (depositLimit.isPresent()) {
        i += 8;
      }
      final var borrowLimit = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
      if (borrowLimit.isPresent()) {
        i += 8;
      }
      final var totalAssetValueInitLimit = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
      return new LendingPoolConfigureBankLimitsOnlyIxData(discriminator, depositLimit, borrowLimit, totalAssetValueInitLimit);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeOptional(depositLimit, _data, i);
      i += Borsh.writeOptional(borrowLimit, _data, i);
      i += Borsh.writeOptional(totalAssetValueInitLimit, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + (depositLimit == null || depositLimit.isEmpty() ? 1 : (1 + 8)) + (borrowLimit == null || borrowLimit.isEmpty() ? 1 : (1 + 8)) + (totalAssetValueInitLimit == null || totalAssetValueInitLimit.isEmpty() ? 1 : (1 + 8));
    }
  }

  public static final Discriminator LENDING_POOL_CONFIGURE_BANK_ORACLE_DISCRIMINATOR = toDiscriminator(209, 82, 255, 171, 124, 21, 71, 81);

  // (admin only)
  public static Instruction lendingPoolConfigureBankOracle(final AccountMeta invokedMarginfiProgramMeta,
                                                           final PublicKey groupKey,
                                                           final PublicKey adminKey,
                                                           final PublicKey bankKey,
                                                           final int setup,
                                                           final PublicKey oracle) {
    final var keys = List.of(
      createRead(groupKey),
      createReadOnlySigner(adminKey),
      createWrite(bankKey)
    );

    final byte[] _data = new byte[41];
    int i = LENDING_POOL_CONFIGURE_BANK_ORACLE_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) setup;
    ++i;
    oracle.write(_data, i);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record LendingPoolConfigureBankOracleIxData(Discriminator discriminator, int setup, PublicKey oracle) implements Borsh {  

    public static LendingPoolConfigureBankOracleIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 41;

    public static LendingPoolConfigureBankOracleIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var setup = _data[i] & 0xFF;
      ++i;
      final var oracle = readPubKey(_data, i);
      return new LendingPoolConfigureBankOracleIxData(discriminator, setup, oracle);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) setup;
      ++i;
      oracle.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator LENDING_POOL_HANDLE_BANKRUPTCY_DISCRIMINATOR = toDiscriminator(162, 11, 56, 139, 90, 128, 70, 173);

  // Handle bad debt of a bankrupt marginfi account for a given bank.
  public static Instruction lendingPoolHandleBankruptcy(final AccountMeta invokedMarginfiProgramMeta,
                                                        final PublicKey groupKey,
                                                        // PERMISSIONLESS_BAD_DEBT_SETTLEMENT_FLAG is not set
                                                        final PublicKey signerKey,
                                                        final PublicKey bankKey,
                                                        final PublicKey marginfiAccountKey,
                                                        final PublicKey liquidityVaultKey,
                                                        final PublicKey insuranceVaultKey,
                                                        final PublicKey insuranceVaultAuthorityKey,
                                                        final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(groupKey),
      createReadOnlySigner(signerKey),
      createWrite(bankKey),
      createWrite(marginfiAccountKey),
      createWrite(liquidityVaultKey),
      createWrite(insuranceVaultKey),
      createRead(insuranceVaultAuthorityKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, LENDING_POOL_HANDLE_BANKRUPTCY_DISCRIMINATOR);
  }

  public static final Discriminator LENDING_POOL_SETUP_EMISSIONS_DISCRIMINATOR = toDiscriminator(206, 97, 120, 172, 113, 204, 169, 70);

  // (delegate_emissions_admin only)
  public static Instruction lendingPoolSetupEmissions(final AccountMeta invokedMarginfiProgramMeta,
                                                      final SolanaAccounts solanaAccounts,
                                                      final PublicKey groupKey,
                                                      final PublicKey delegateEmissionsAdminKey,
                                                      final PublicKey bankKey,
                                                      final PublicKey emissionsMintKey,
                                                      final PublicKey emissionsAuthKey,
                                                      final PublicKey emissionsTokenAccountKey,
                                                      // NOTE: This is a TokenAccount, spl transfer will validate it.
                                                      // 
                                                      final PublicKey emissionsFundingAccountKey,
                                                      final PublicKey tokenProgramKey,
                                                      final long flags,
                                                      final long rate,
                                                      final long totalEmissions) {
    final var keys = List.of(
      createWrite(groupKey),
      createWritableSigner(delegateEmissionsAdminKey),
      createWrite(bankKey),
      createRead(emissionsMintKey),
      createRead(emissionsAuthKey),
      createWrite(emissionsTokenAccountKey),
      createWrite(emissionsFundingAccountKey),
      createRead(tokenProgramKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[32];
    int i = LENDING_POOL_SETUP_EMISSIONS_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, flags);
    i += 8;
    putInt64LE(_data, i, rate);
    i += 8;
    putInt64LE(_data, i, totalEmissions);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record LendingPoolSetupEmissionsIxData(Discriminator discriminator,
                                                long flags,
                                                long rate,
                                                long totalEmissions) implements Borsh {  

    public static LendingPoolSetupEmissionsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 32;

    public static LendingPoolSetupEmissionsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var flags = getInt64LE(_data, i);
      i += 8;
      final var rate = getInt64LE(_data, i);
      i += 8;
      final var totalEmissions = getInt64LE(_data, i);
      return new LendingPoolSetupEmissionsIxData(discriminator, flags, rate, totalEmissions);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, flags);
      i += 8;
      putInt64LE(_data, i, rate);
      i += 8;
      putInt64LE(_data, i, totalEmissions);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator LENDING_POOL_UPDATE_EMISSIONS_PARAMETERS_DISCRIMINATOR = toDiscriminator(55, 213, 224, 168, 153, 53, 197, 40);

  // (delegate_emissions_admin only)
  public static Instruction lendingPoolUpdateEmissionsParameters(final AccountMeta invokedMarginfiProgramMeta,
                                                                 final PublicKey groupKey,
                                                                 final PublicKey delegateEmissionsAdminKey,
                                                                 final PublicKey bankKey,
                                                                 final PublicKey emissionsMintKey,
                                                                 final PublicKey emissionsTokenAccountKey,
                                                                 final PublicKey emissionsFundingAccountKey,
                                                                 final PublicKey tokenProgramKey,
                                                                 final OptionalLong emissionsFlags,
                                                                 final OptionalLong emissionsRate,
                                                                 final OptionalLong additionalEmissions) {
    final var keys = List.of(
      createWrite(groupKey),
      createWritableSigner(delegateEmissionsAdminKey),
      createWrite(bankKey),
      createRead(emissionsMintKey),
      createWrite(emissionsTokenAccountKey),
      createWrite(emissionsFundingAccountKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[
        8
        + (emissionsFlags == null || emissionsFlags.isEmpty() ? 1 : 9)
        + (emissionsRate == null || emissionsRate.isEmpty() ? 1 : 9)
        + (additionalEmissions == null || additionalEmissions.isEmpty() ? 1 : 9)
    ];
    int i = LENDING_POOL_UPDATE_EMISSIONS_PARAMETERS_DISCRIMINATOR.write(_data, 0);
    i += Borsh.writeOptional(emissionsFlags, _data, i);
    i += Borsh.writeOptional(emissionsRate, _data, i);
    Borsh.writeOptional(additionalEmissions, _data, i);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record LendingPoolUpdateEmissionsParametersIxData(Discriminator discriminator,
                                                           OptionalLong emissionsFlags,
                                                           OptionalLong emissionsRate,
                                                           OptionalLong additionalEmissions) implements Borsh {  

    public static LendingPoolUpdateEmissionsParametersIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static LendingPoolUpdateEmissionsParametersIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var emissionsFlags = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
      if (emissionsFlags.isPresent()) {
        i += 8;
      }
      final var emissionsRate = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
      if (emissionsRate.isPresent()) {
        i += 8;
      }
      final var additionalEmissions = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
      return new LendingPoolUpdateEmissionsParametersIxData(discriminator, emissionsFlags, emissionsRate, additionalEmissions);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeOptional(emissionsFlags, _data, i);
      i += Borsh.writeOptional(emissionsRate, _data, i);
      i += Borsh.writeOptional(additionalEmissions, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + (emissionsFlags == null || emissionsFlags.isEmpty() ? 1 : (1 + 8)) + (emissionsRate == null || emissionsRate.isEmpty() ? 1 : (1 + 8)) + (additionalEmissions == null || additionalEmissions.isEmpty() ? 1 : (1 + 8));
    }
  }

  public static final Discriminator LENDING_POOL_UPDATE_FEES_DESTINATION_ACCOUNT_DISCRIMINATOR = toDiscriminator(102, 4, 121, 243, 237, 110, 95, 13);

  public static Instruction lendingPoolUpdateFeesDestinationAccount(final AccountMeta invokedMarginfiProgramMeta,
                                                                    final PublicKey groupKey,
                                                                    final PublicKey bankKey,
                                                                    final PublicKey adminKey,
                                                                    // Bank fees will be sent to this account which must be an ATA of the bank's mint.
                                                                    final PublicKey destinationAccountKey) {
    final var keys = List.of(
      createRead(groupKey),
      createWrite(bankKey),
      createReadOnlySigner(adminKey),
      createRead(destinationAccountKey)
    );

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, LENDING_POOL_UPDATE_FEES_DESTINATION_ACCOUNT_DISCRIMINATOR);
  }

  public static final Discriminator LENDING_POOL_WITHDRAW_FEES_DISCRIMINATOR = toDiscriminator(92, 140, 215, 254, 170, 0, 83, 174);

  public static Instruction lendingPoolWithdrawFees(final AccountMeta invokedMarginfiProgramMeta,
                                                    final PublicKey groupKey,
                                                    final PublicKey bankKey,
                                                    final PublicKey adminKey,
                                                    final PublicKey feeVaultKey,
                                                    final PublicKey feeVaultAuthorityKey,
                                                    final PublicKey dstTokenAccountKey,
                                                    final PublicKey tokenProgramKey,
                                                    final long amount) {
    final var keys = List.of(
      createRead(groupKey),
      createRead(bankKey),
      createReadOnlySigner(adminKey),
      createWrite(feeVaultKey),
      createRead(feeVaultAuthorityKey),
      createWrite(dstTokenAccountKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = LENDING_POOL_WITHDRAW_FEES_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record LendingPoolWithdrawFeesIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static LendingPoolWithdrawFeesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static LendingPoolWithdrawFeesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new LendingPoolWithdrawFeesIxData(discriminator, amount);
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

  public static final Discriminator LENDING_POOL_WITHDRAW_FEES_PERMISSIONLESS_DISCRIMINATOR = toDiscriminator(57, 245, 1, 208, 130, 18, 145, 113);

  public static Instruction lendingPoolWithdrawFeesPermissionless(final AccountMeta invokedMarginfiProgramMeta,
                                                                  final PublicKey groupKey,
                                                                  final PublicKey bankKey,
                                                                  final PublicKey feeVaultKey,
                                                                  final PublicKey feeVaultAuthorityKey,
                                                                  final PublicKey feesDestinationAccountKey,
                                                                  final PublicKey tokenProgramKey,
                                                                  final long amount) {
    final var keys = List.of(
      createRead(groupKey),
      createRead(bankKey),
      createWrite(feeVaultKey),
      createRead(feeVaultAuthorityKey),
      createWrite(feesDestinationAccountKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = LENDING_POOL_WITHDRAW_FEES_PERMISSIONLESS_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record LendingPoolWithdrawFeesPermissionlessIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static LendingPoolWithdrawFeesPermissionlessIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static LendingPoolWithdrawFeesPermissionlessIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new LendingPoolWithdrawFeesPermissionlessIxData(discriminator, amount);
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

  public static final Discriminator LENDING_POOL_WITHDRAW_INSURANCE_DISCRIMINATOR = toDiscriminator(108, 60, 60, 246, 104, 79, 159, 243);

  public static Instruction lendingPoolWithdrawInsurance(final AccountMeta invokedMarginfiProgramMeta,
                                                         final PublicKey groupKey,
                                                         final PublicKey bankKey,
                                                         final PublicKey adminKey,
                                                         final PublicKey insuranceVaultKey,
                                                         final PublicKey insuranceVaultAuthorityKey,
                                                         final PublicKey dstTokenAccountKey,
                                                         final PublicKey tokenProgramKey,
                                                         final long amount) {
    final var keys = List.of(
      createRead(groupKey),
      createRead(bankKey),
      createReadOnlySigner(adminKey),
      createWrite(insuranceVaultKey),
      createRead(insuranceVaultAuthorityKey),
      createWrite(dstTokenAccountKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = LENDING_POOL_WITHDRAW_INSURANCE_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record LendingPoolWithdrawInsuranceIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static LendingPoolWithdrawInsuranceIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static LendingPoolWithdrawInsuranceIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new LendingPoolWithdrawInsuranceIxData(discriminator, amount);
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

  public static final Discriminator MARGINFI_ACCOUNT_CLOSE_DISCRIMINATOR = toDiscriminator(186, 221, 93, 34, 50, 97, 194, 241);

  public static Instruction marginfiAccountClose(final AccountMeta invokedMarginfiProgramMeta,
                                                 final PublicKey marginfiAccountKey,
                                                 final PublicKey authorityKey,
                                                 final PublicKey feePayerKey) {
    final var keys = List.of(
      createWrite(marginfiAccountKey),
      createReadOnlySigner(authorityKey),
      createWritableSigner(feePayerKey)
    );

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, MARGINFI_ACCOUNT_CLOSE_DISCRIMINATOR);
  }

  public static final Discriminator MARGINFI_ACCOUNT_INITIALIZE_DISCRIMINATOR = toDiscriminator(43, 78, 61, 255, 148, 52, 249, 154);

  // Initialize a marginfi account for a given group
  public static Instruction marginfiAccountInitialize(final AccountMeta invokedMarginfiProgramMeta,
                                                      final SolanaAccounts solanaAccounts,
                                                      final PublicKey marginfiGroupKey,
                                                      final PublicKey marginfiAccountKey,
                                                      final PublicKey authorityKey,
                                                      final PublicKey feePayerKey) {
    final var keys = List.of(
      createRead(marginfiGroupKey),
      createWritableSigner(marginfiAccountKey),
      createReadOnlySigner(authorityKey),
      createWritableSigner(feePayerKey),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, MARGINFI_ACCOUNT_INITIALIZE_DISCRIMINATOR);
  }

  public static final Discriminator MARGINFI_ACCOUNT_UPDATE_EMISSIONS_DESTINATION_ACCOUNT_DISCRIMINATOR = toDiscriminator(73, 185, 162, 201, 111, 24, 116, 185);

  public static Instruction marginfiAccountUpdateEmissionsDestinationAccount(final AccountMeta invokedMarginfiProgramMeta,
                                                                             final PublicKey marginfiAccountKey,
                                                                             final PublicKey authorityKey,
                                                                             // User's earned emissions will be sent to the canonical ATA of this wallet.
                                                                             // 
                                                                             final PublicKey destinationAccountKey) {
    final var keys = List.of(
      createWrite(marginfiAccountKey),
      createReadOnlySigner(authorityKey),
      createRead(destinationAccountKey)
    );

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, MARGINFI_ACCOUNT_UPDATE_EMISSIONS_DESTINATION_ACCOUNT_DISCRIMINATOR);
  }

  public static final Discriminator MARGINFI_GROUP_CONFIGURE_DISCRIMINATOR = toDiscriminator(62, 199, 81, 78, 33, 13, 236, 61);

  public static Instruction marginfiGroupConfigure(final AccountMeta invokedMarginfiProgramMeta,
                                                   final PublicKey marginfiGroupKey,
                                                   final PublicKey adminKey,
                                                   final PublicKey newAdmin,
                                                   final PublicKey newEmodeAdmin,
                                                   final PublicKey newCurveAdmin,
                                                   final PublicKey newLimitAdmin,
                                                   final PublicKey newEmissionsAdmin,
                                                   final boolean isArenaGroup) {
    final var keys = List.of(
      createWrite(marginfiGroupKey),
      createReadOnlySigner(adminKey)
    );

    final byte[] _data = new byte[169];
    int i = MARGINFI_GROUP_CONFIGURE_DISCRIMINATOR.write(_data, 0);
    newAdmin.write(_data, i);
    i += 32;
    newEmodeAdmin.write(_data, i);
    i += 32;
    newCurveAdmin.write(_data, i);
    i += 32;
    newLimitAdmin.write(_data, i);
    i += 32;
    newEmissionsAdmin.write(_data, i);
    i += 32;
    _data[i] = (byte) (isArenaGroup ? 1 : 0);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record MarginfiGroupConfigureIxData(Discriminator discriminator,
                                             PublicKey newAdmin,
                                             PublicKey newEmodeAdmin,
                                             PublicKey newCurveAdmin,
                                             PublicKey newLimitAdmin,
                                             PublicKey newEmissionsAdmin,
                                             boolean isArenaGroup) implements Borsh {  

    public static MarginfiGroupConfigureIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 169;

    public static MarginfiGroupConfigureIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var newAdmin = readPubKey(_data, i);
      i += 32;
      final var newEmodeAdmin = readPubKey(_data, i);
      i += 32;
      final var newCurveAdmin = readPubKey(_data, i);
      i += 32;
      final var newLimitAdmin = readPubKey(_data, i);
      i += 32;
      final var newEmissionsAdmin = readPubKey(_data, i);
      i += 32;
      final var isArenaGroup = _data[i] == 1;
      return new MarginfiGroupConfigureIxData(discriminator,
                                              newAdmin,
                                              newEmodeAdmin,
                                              newCurveAdmin,
                                              newLimitAdmin,
                                              newEmissionsAdmin,
                                              isArenaGroup);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      newAdmin.write(_data, i);
      i += 32;
      newEmodeAdmin.write(_data, i);
      i += 32;
      newCurveAdmin.write(_data, i);
      i += 32;
      newLimitAdmin.write(_data, i);
      i += 32;
      newEmissionsAdmin.write(_data, i);
      i += 32;
      _data[i] = (byte) (isArenaGroup ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator MARGINFI_GROUP_INITIALIZE_DISCRIMINATOR = toDiscriminator(255, 67, 67, 26, 94, 31, 34, 20);

  public static Instruction marginfiGroupInitialize(final AccountMeta invokedMarginfiProgramMeta,
                                                    final SolanaAccounts solanaAccounts,
                                                    final PublicKey marginfiGroupKey,
                                                    final PublicKey adminKey,
                                                    final PublicKey feeStateKey,
                                                    final boolean isArenaGroup) {
    final var keys = List.of(
      createWritableSigner(marginfiGroupKey),
      createWritableSigner(adminKey),
      createRead(feeStateKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[9];
    int i = MARGINFI_GROUP_INITIALIZE_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) (isArenaGroup ? 1 : 0);

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, _data);
  }

  public record MarginfiGroupInitializeIxData(Discriminator discriminator, boolean isArenaGroup) implements Borsh {  

    public static MarginfiGroupInitializeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static MarginfiGroupInitializeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var isArenaGroup = _data[i] == 1;
      return new MarginfiGroupInitializeIxData(discriminator, isArenaGroup);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) (isArenaGroup ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator MIGRATE_PYTH_PUSH_ORACLE_DISCRIMINATOR = toDiscriminator(139, 58, 192, 167, 217, 110, 247, 152);

  public static Instruction migratePythPushOracle(final AccountMeta invokedMarginfiProgramMeta,
                                                  final PublicKey groupKey,
                                                  final PublicKey bankKey,
                                                  // Must use the Pyth Sponsored shard ID (0) or mrgn's (3301)
                                                  // 
                                                  final PublicKey oracleKey) {
    final var keys = List.of(
      createRead(groupKey),
      createWrite(bankKey),
      createRead(oracleKey)
    );

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, MIGRATE_PYTH_PUSH_ORACLE_DISCRIMINATOR);
  }

  public static final Discriminator PROPAGATE_FEE_STATE_DISCRIMINATOR = toDiscriminator(64, 3, 166, 194, 129, 21, 101, 155);

  // (Permissionless) Force any group to adopt the current FeeState settings
  public static Instruction propagateFeeState(final AccountMeta invokedMarginfiProgramMeta,
                                              final PublicKey feeStateKey,
                                              // Any group, this ix is permisionless and can propogate the fee to any group
                                              final PublicKey marginfiGroupKey) {
    final var keys = List.of(
      createRead(feeStateKey),
      createWrite(marginfiGroupKey)
    );

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, PROPAGATE_FEE_STATE_DISCRIMINATOR);
  }

  public static final Discriminator PROPAGATE_STAKED_SETTINGS_DISCRIMINATOR = toDiscriminator(210, 30, 152, 69, 130, 99, 222, 170);

  public static Instruction propagateStakedSettings(final AccountMeta invokedMarginfiProgramMeta,
                                                    final PublicKey marginfiGroupKey,
                                                    final PublicKey stakedSettingsKey,
                                                    final PublicKey bankKey) {
    final var keys = List.of(
      createRead(marginfiGroupKey),
      createRead(stakedSettingsKey),
      createWrite(bankKey)
    );

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, PROPAGATE_STAKED_SETTINGS_DISCRIMINATOR);
  }

  public static final Discriminator TRANSFER_TO_NEW_ACCOUNT_DISCRIMINATOR = toDiscriminator(28, 79, 129, 231, 169, 69, 69, 65);

  public static Instruction transferToNewAccount(final AccountMeta invokedMarginfiProgramMeta,
                                                 final SolanaAccounts solanaAccounts,
                                                 final PublicKey groupKey,
                                                 final PublicKey oldMarginfiAccountKey,
                                                 final PublicKey newMarginfiAccountKey,
                                                 final PublicKey authorityKey,
                                                 final PublicKey feePayerKey,
                                                 final PublicKey newAuthorityKey,
                                                 final PublicKey globalFeeWalletKey) {
    final var keys = List.of(
      createRead(groupKey),
      createWrite(oldMarginfiAccountKey),
      createWritableSigner(newMarginfiAccountKey),
      createReadOnlySigner(authorityKey),
      createWritableSigner(feePayerKey),
      createRead(newAuthorityKey),
      createWrite(globalFeeWalletKey),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedMarginfiProgramMeta, keys, TRANSFER_TO_NEW_ACCOUNT_DISCRIMINATOR);
  }

  private MarginfiProgram() {
  }
}
