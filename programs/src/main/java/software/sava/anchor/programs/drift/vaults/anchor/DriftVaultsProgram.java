package software.sava.anchor.programs.drift.vaults.anchor;

import java.util.List;

import software.sava.anchor.programs.drift.vaults.anchor.types.UpdateVaultParams;
import software.sava.anchor.programs.drift.vaults.anchor.types.VaultParams;
import software.sava.anchor.programs.drift.vaults.anchor.types.WithdrawUnit;
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
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class DriftVaultsProgram {

  public static final Discriminator INITIALIZE_VAULT_DISCRIMINATOR = toDiscriminator(48, 191, 163, 44, 71, 129, 63, 164);

  public static Instruction initializeVault(final AccountMeta invokedDriftVaultsProgramMeta,
                                            final PublicKey vaultKey,
                                            final PublicKey tokenAccountKey,
                                            final PublicKey driftUserStatsKey,
                                            final PublicKey driftUserKey,
                                            final PublicKey driftStateKey,
                                            final PublicKey driftSpotMarketKey,
                                            final PublicKey driftSpotMarketMintKey,
                                            final PublicKey managerKey,
                                            final PublicKey payerKey,
                                            final PublicKey rentKey,
                                            final PublicKey systemProgramKey,
                                            final PublicKey driftProgramKey,
                                            final PublicKey tokenProgramKey,
                                            final VaultParams params) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(tokenAccountKey),
      createWrite(driftUserStatsKey),
      createWrite(driftUserKey),
      createWrite(driftStateKey),
      createRead(driftSpotMarketKey),
      createRead(driftSpotMarketMintKey),
      createReadOnlySigner(managerKey),
      createWritableSigner(payerKey),
      createRead(rentKey),
      createRead(systemProgramKey),
      createRead(driftProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(INITIALIZE_VAULT_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record InitializeVaultIxData(Discriminator discriminator, VaultParams params) implements Borsh {  

    public static InitializeVaultIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 83;

    public static InitializeVaultIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = VaultParams.read(_data, i);
      return new InitializeVaultIxData(discriminator, params);
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

  public static final Discriminator UPDATE_DELEGATE_DISCRIMINATOR = toDiscriminator(190, 202, 103, 138, 167, 197, 25, 9);

  public static Instruction updateDelegate(final AccountMeta invokedDriftVaultsProgramMeta,
                                           final PublicKey vaultKey,
                                           final PublicKey managerKey,
                                           final PublicKey driftUserKey,
                                           final PublicKey driftProgramKey,
                                           final PublicKey delegate) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(managerKey),
      createWrite(driftUserKey),
      createRead(driftProgramKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(UPDATE_DELEGATE_DISCRIMINATOR, _data, 0);
    delegate.write(_data, i);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record UpdateDelegateIxData(Discriminator discriminator, PublicKey delegate) implements Borsh {  

    public static UpdateDelegateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static UpdateDelegateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var delegate = readPubKey(_data, i);
      return new UpdateDelegateIxData(discriminator, delegate);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      delegate.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_MARGIN_TRADING_ENABLED_DISCRIMINATOR = toDiscriminator(244, 34, 229, 140, 91, 65, 200, 67);

  public static Instruction updateMarginTradingEnabled(final AccountMeta invokedDriftVaultsProgramMeta,
                                                       final PublicKey vaultKey,
                                                       final PublicKey managerKey,
                                                       final PublicKey driftUserKey,
                                                       final PublicKey driftProgramKey,
                                                       final boolean enabled) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(managerKey),
      createWrite(driftUserKey),
      createRead(driftProgramKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(UPDATE_MARGIN_TRADING_ENABLED_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) (enabled ? 1 : 0);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record UpdateMarginTradingEnabledIxData(Discriminator discriminator, boolean enabled) implements Borsh {  

    public static UpdateMarginTradingEnabledIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static UpdateMarginTradingEnabledIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var enabled = _data[i] == 1;
      return new UpdateMarginTradingEnabledIxData(discriminator, enabled);
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

  public static final Discriminator UPDATE_VAULT_DISCRIMINATOR = toDiscriminator(67, 229, 185, 188, 226, 11, 210, 60);

  public static Instruction updateVault(final AccountMeta invokedDriftVaultsProgramMeta,
                                        final PublicKey vaultKey,
                                        final PublicKey managerKey,
                                        final UpdateVaultParams params) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(managerKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(UPDATE_VAULT_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record UpdateVaultIxData(Discriminator discriminator, UpdateVaultParams params) implements Borsh {  

    public static UpdateVaultIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateVaultIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = UpdateVaultParams.read(_data, i);
      return new UpdateVaultIxData(discriminator, params);
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

  public static final Discriminator INITIALIZE_VAULT_DEPOSITOR_DISCRIMINATOR = toDiscriminator(112, 174, 162, 232, 89, 92, 205, 168);

  public static Instruction initializeVaultDepositor(final AccountMeta invokedDriftVaultsProgramMeta,
                                                     final PublicKey vaultKey,
                                                     final PublicKey vaultDepositorKey,
                                                     final PublicKey authorityKey,
                                                     final PublicKey payerKey,
                                                     final PublicKey rentKey,
                                                     final PublicKey systemProgramKey) {
    final var keys = List.of(
      createRead(vaultKey),
      createWrite(vaultDepositorKey),
      createRead(authorityKey),
      createWritableSigner(payerKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, INITIALIZE_VAULT_DEPOSITOR_DISCRIMINATOR);
  }

  public static final Discriminator DEPOSIT_DISCRIMINATOR = toDiscriminator(242, 35, 198, 137, 82, 225, 242, 182);

  public static Instruction deposit(final AccountMeta invokedDriftVaultsProgramMeta,
                                    final PublicKey vaultKey,
                                    final PublicKey vaultDepositorKey,
                                    final PublicKey authorityKey,
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
      createWrite(vaultKey),
      createWrite(vaultDepositorKey),
      createReadOnlySigner(authorityKey),
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
    int i = writeDiscriminator(DEPOSIT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record DepositIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static DepositIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static DepositIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new DepositIxData(discriminator, amount);
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

  public static final Discriminator REQUEST_WITHDRAW_DISCRIMINATOR = toDiscriminator(137, 95, 187, 96, 250, 138, 31, 182);

  public static Instruction requestWithdraw(final AccountMeta invokedDriftVaultsProgramMeta,
                                            final PublicKey vaultKey,
                                            final PublicKey vaultDepositorKey,
                                            final PublicKey authorityKey,
                                            final PublicKey driftUserStatsKey,
                                            final PublicKey driftUserKey,
                                            final PublicKey driftStateKey,
                                            final long withdrawAmount,
                                            final WithdrawUnit withdrawUnit) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(vaultDepositorKey),
      createReadOnlySigner(authorityKey),
      createRead(driftUserStatsKey),
      createRead(driftUserKey),
      createRead(driftStateKey)
    );

    final byte[] _data = new byte[16 + Borsh.len(withdrawUnit)];
    int i = writeDiscriminator(REQUEST_WITHDRAW_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, withdrawAmount);
    i += 8;
    Borsh.write(withdrawUnit, _data, i);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record RequestWithdrawIxData(Discriminator discriminator, long withdrawAmount, WithdrawUnit withdrawUnit) implements Borsh {  

    public static RequestWithdrawIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static RequestWithdrawIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var withdrawAmount = getInt64LE(_data, i);
      i += 8;
      final var withdrawUnit = WithdrawUnit.read(_data, i);
      return new RequestWithdrawIxData(discriminator, withdrawAmount, withdrawUnit);
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

  public static final Discriminator CANCEL_REQUEST_WITHDRAW_DISCRIMINATOR = toDiscriminator(26, 109, 1, 81, 102, 15, 6, 106);

  public static Instruction cancelRequestWithdraw(final AccountMeta invokedDriftVaultsProgramMeta,
                                                  final PublicKey vaultKey,
                                                  final PublicKey vaultDepositorKey,
                                                  final PublicKey authorityKey,
                                                  final PublicKey driftUserStatsKey,
                                                  final PublicKey driftUserKey,
                                                  final PublicKey driftStateKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(vaultDepositorKey),
      createReadOnlySigner(authorityKey),
      createRead(driftUserStatsKey),
      createRead(driftUserKey),
      createRead(driftStateKey)
    );

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, CANCEL_REQUEST_WITHDRAW_DISCRIMINATOR);
  }

  public static final Discriminator WITHDRAW_DISCRIMINATOR = toDiscriminator(183, 18, 70, 156, 148, 109, 161, 34);

  public static Instruction withdraw(final AccountMeta invokedDriftVaultsProgramMeta,
                                     final PublicKey vaultKey,
                                     final PublicKey vaultDepositorKey,
                                     final PublicKey authorityKey,
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
      createWrite(vaultKey),
      createWrite(vaultDepositorKey),
      createReadOnlySigner(authorityKey),
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

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, WITHDRAW_DISCRIMINATOR);
  }

  public static final Discriminator LIQUIDATE_DISCRIMINATOR = toDiscriminator(223, 179, 226, 125, 48, 46, 39, 74);

  public static Instruction liquidate(final AccountMeta invokedDriftVaultsProgramMeta,
                                      final PublicKey vaultKey,
                                      final PublicKey vaultDepositorKey,
                                      final PublicKey authorityKey,
                                      final PublicKey driftUserStatsKey,
                                      final PublicKey driftUserKey,
                                      final PublicKey driftStateKey,
                                      final PublicKey driftProgramKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(vaultDepositorKey),
      createReadOnlySigner(authorityKey),
      createWrite(driftUserStatsKey),
      createWrite(driftUserKey),
      createRead(driftStateKey),
      createRead(driftProgramKey)
    );

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, LIQUIDATE_DISCRIMINATOR);
  }

  public static final Discriminator RESET_DELEGATE_DISCRIMINATOR = toDiscriminator(204, 13, 61, 153, 97, 83, 146, 98);

  public static Instruction resetDelegate(final AccountMeta invokedDriftVaultsProgramMeta,
                                          final PublicKey vaultKey,
                                          final PublicKey authorityKey,
                                          final PublicKey driftUserKey,
                                          final PublicKey driftProgramKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(authorityKey),
      createWrite(driftUserKey),
      createRead(driftProgramKey)
    );

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, RESET_DELEGATE_DISCRIMINATOR);
  }

  public static final Discriminator MANAGER_DEPOSIT_DISCRIMINATOR = toDiscriminator(73, 3, 16, 168, 143, 226, 201, 254);

  public static Instruction managerDeposit(final AccountMeta invokedDriftVaultsProgramMeta,
                                           final PublicKey vaultKey,
                                           final PublicKey managerKey,
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
      createWrite(vaultKey),
      createReadOnlySigner(managerKey),
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
    int i = writeDiscriminator(MANAGER_DEPOSIT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record ManagerDepositIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static ManagerDepositIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static ManagerDepositIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new ManagerDepositIxData(discriminator, amount);
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

  public static final Discriminator MANAGER_REQUEST_WITHDRAW_DISCRIMINATOR = toDiscriminator(10, 238, 194, 232, 76, 55, 68, 4);

  public static Instruction managerRequestWithdraw(final AccountMeta invokedDriftVaultsProgramMeta,
                                                   final PublicKey vaultKey,
                                                   final PublicKey managerKey,
                                                   final PublicKey driftUserStatsKey,
                                                   final PublicKey driftUserKey,
                                                   final PublicKey driftStateKey,
                                                   final long withdrawAmount,
                                                   final WithdrawUnit withdrawUnit) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(managerKey),
      createRead(driftUserStatsKey),
      createRead(driftUserKey),
      createRead(driftStateKey)
    );

    final byte[] _data = new byte[16 + Borsh.len(withdrawUnit)];
    int i = writeDiscriminator(MANAGER_REQUEST_WITHDRAW_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, withdrawAmount);
    i += 8;
    Borsh.write(withdrawUnit, _data, i);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record ManagerRequestWithdrawIxData(Discriminator discriminator, long withdrawAmount, WithdrawUnit withdrawUnit) implements Borsh {  

    public static ManagerRequestWithdrawIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static ManagerRequestWithdrawIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var withdrawAmount = getInt64LE(_data, i);
      i += 8;
      final var withdrawUnit = WithdrawUnit.read(_data, i);
      return new ManagerRequestWithdrawIxData(discriminator, withdrawAmount, withdrawUnit);
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

  public static final Discriminator MANGER_CANCEL_WITHDRAW_REQUEST_DISCRIMINATOR = toDiscriminator(235, 253, 32, 176, 145, 94, 162, 244);

  public static Instruction mangerCancelWithdrawRequest(final AccountMeta invokedDriftVaultsProgramMeta,
                                                        final PublicKey vaultKey,
                                                        final PublicKey managerKey,
                                                        final PublicKey driftUserStatsKey,
                                                        final PublicKey driftUserKey,
                                                        final PublicKey driftStateKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(managerKey),
      createRead(driftUserStatsKey),
      createRead(driftUserKey),
      createRead(driftStateKey)
    );

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, MANGER_CANCEL_WITHDRAW_REQUEST_DISCRIMINATOR);
  }

  public static final Discriminator MANAGER_WITHDRAW_DISCRIMINATOR = toDiscriminator(201, 248, 190, 143, 86, 43, 183, 254);

  public static Instruction managerWithdraw(final AccountMeta invokedDriftVaultsProgramMeta,
                                            final PublicKey vaultKey,
                                            final PublicKey managerKey,
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
      createWrite(vaultKey),
      createReadOnlySigner(managerKey),
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

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, MANAGER_WITHDRAW_DISCRIMINATOR);
  }

  public static final Discriminator APPLY_PROFIT_SHARE_DISCRIMINATOR = toDiscriminator(112, 235, 54, 165, 178, 81, 25, 10);

  public static Instruction applyProfitShare(final AccountMeta invokedDriftVaultsProgramMeta,
                                             final PublicKey vaultKey,
                                             final PublicKey vaultDepositorKey,
                                             final PublicKey managerKey,
                                             final PublicKey driftUserStatsKey,
                                             final PublicKey driftUserKey,
                                             final PublicKey driftStateKey,
                                             final PublicKey driftSignerKey,
                                             final PublicKey driftProgramKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(vaultDepositorKey),
      createReadOnlySigner(managerKey),
      createWrite(driftUserStatsKey),
      createWrite(driftUserKey),
      createRead(driftStateKey),
      createRead(driftSignerKey),
      createRead(driftProgramKey)
    );

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, APPLY_PROFIT_SHARE_DISCRIMINATOR);
  }

  public static final Discriminator FORCE_WITHDRAW_DISCRIMINATOR = toDiscriminator(106, 41, 34, 48, 17, 177, 59, 255);

  public static Instruction forceWithdraw(final AccountMeta invokedDriftVaultsProgramMeta,
                                          final PublicKey vaultKey,
                                          final PublicKey managerKey,
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
      createWrite(vaultKey),
      createReadOnlySigner(managerKey),
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

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, FORCE_WITHDRAW_DISCRIMINATOR);
  }

  private DriftVaultsProgram() {
  }
}
