package software.sava.anchor.programs.drift.vaults.anchor;

import java.util.List;
import java.util.OptionalLong;

import software.sava.anchor.programs.drift.vaults.anchor.types.InitializeTokenizedVaultDepositorParams;
import software.sava.anchor.programs.drift.vaults.anchor.types.ManagerUpdateFeesParams;
import software.sava.anchor.programs.drift.vaults.anchor.types.UpdateVaultParams;
import software.sava.anchor.programs.drift.vaults.anchor.types.UpdateVaultProtocolParams;
import software.sava.anchor.programs.drift.vaults.anchor.types.VaultParams;
import software.sava.anchor.programs.drift.vaults.anchor.types.VaultWithProtocolParams;
import software.sava.anchor.programs.drift.vaults.anchor.types.WithdrawUnit;
import software.sava.core.accounts.PublicKey;
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
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
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
    int i = INITIALIZE_VAULT_DISCRIMINATOR.write(_data, 0);
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
      final var discriminator = createAnchorDiscriminator(_data, offset);
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

  public static final Discriminator INITIALIZE_VAULT_WITH_PROTOCOL_DISCRIMINATOR = toDiscriminator(176, 2, 248, 66, 116, 82, 52, 112);

  public static Instruction initializeVaultWithProtocol(final AccountMeta invokedDriftVaultsProgramMeta,
                                                        final PublicKey vaultKey,
                                                        final PublicKey vaultProtocolKey,
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
                                                        final VaultWithProtocolParams params) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(vaultProtocolKey),
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
    int i = INITIALIZE_VAULT_WITH_PROTOCOL_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record InitializeVaultWithProtocolIxData(Discriminator discriminator, VaultWithProtocolParams params) implements Borsh {  

    public static InitializeVaultWithProtocolIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 127;

    public static InitializeVaultWithProtocolIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = VaultWithProtocolParams.read(_data, i);
      return new InitializeVaultWithProtocolIxData(discriminator, params);
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
    int i = UPDATE_DELEGATE_DISCRIMINATOR.write(_data, 0);
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
      final var discriminator = createAnchorDiscriminator(_data, offset);
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
    int i = UPDATE_MARGIN_TRADING_ENABLED_DISCRIMINATOR.write(_data, 0);
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
      final var discriminator = createAnchorDiscriminator(_data, offset);
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

  public static final Discriminator UPDATE_USER_POOL_ID_DISCRIMINATOR = toDiscriminator(219, 86, 73, 106, 56, 218, 128, 109);

  public static Instruction updateUserPoolId(final AccountMeta invokedDriftVaultsProgramMeta,
                                             final PublicKey vaultKey,
                                             final PublicKey managerKey,
                                             final PublicKey driftUserKey,
                                             final PublicKey driftProgramKey,
                                             final int poolId) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(managerKey),
      createWrite(driftUserKey),
      createRead(driftProgramKey)
    );

    final byte[] _data = new byte[9];
    int i = UPDATE_USER_POOL_ID_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) poolId;

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record UpdateUserPoolIdIxData(Discriminator discriminator, int poolId) implements Borsh {  

    public static UpdateUserPoolIdIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static UpdateUserPoolIdIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var poolId = _data[i] & 0xFF;
      return new UpdateUserPoolIdIxData(discriminator, poolId);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) poolId;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_VAULT_PROTOCOL_DISCRIMINATOR = toDiscriminator(205, 248, 117, 191, 35, 252, 172, 133);

  public static Instruction updateVaultProtocol(final AccountMeta invokedDriftVaultsProgramMeta,
                                                final PublicKey vaultKey,
                                                final PublicKey protocolKey,
                                                final PublicKey vaultProtocolKey,
                                                final UpdateVaultProtocolParams params) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(protocolKey),
      createWrite(vaultProtocolKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = UPDATE_VAULT_PROTOCOL_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record UpdateVaultProtocolIxData(Discriminator discriminator, UpdateVaultProtocolParams params) implements Borsh {  

    public static UpdateVaultProtocolIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateVaultProtocolIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = UpdateVaultProtocolParams.read(_data, i);
      return new UpdateVaultProtocolIxData(discriminator, params);
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
    int i = UPDATE_VAULT_DISCRIMINATOR.write(_data, 0);
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
      final var discriminator = createAnchorDiscriminator(_data, offset);
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

  public static final Discriminator UPDATE_VAULT_MANAGER_DISCRIMINATOR = toDiscriminator(246, 80, 162, 207, 228, 28, 133, 170);

  public static Instruction updateVaultManager(final AccountMeta invokedDriftVaultsProgramMeta,
                                               final PublicKey vaultKey,
                                               final PublicKey managerKey,
                                               final PublicKey manager) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(managerKey)
    );

    final byte[] _data = new byte[40];
    int i = UPDATE_VAULT_MANAGER_DISCRIMINATOR.write(_data, 0);
    manager.write(_data, i);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record UpdateVaultManagerIxData(Discriminator discriminator, PublicKey manager) implements Borsh {  

    public static UpdateVaultManagerIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static UpdateVaultManagerIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var manager = readPubKey(_data, i);
      return new UpdateVaultManagerIxData(discriminator, manager);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      manager.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_CUMULATIVE_FUEL_AMOUNT_DISCRIMINATOR = toDiscriminator(90, 71, 219, 233, 12, 81, 211, 11);

  public static Instruction updateCumulativeFuelAmount(final AccountMeta invokedDriftVaultsProgramMeta,
                                                       final PublicKey vaultKey,
                                                       final PublicKey vaultDepositorKey,
                                                       final PublicKey signerKey,
                                                       final PublicKey driftUserStatsKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(vaultDepositorKey),
      createReadOnlySigner(signerKey),
      createWrite(driftUserStatsKey)
    );

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, UPDATE_CUMULATIVE_FUEL_AMOUNT_DISCRIMINATOR);
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

  public static final Discriminator INITIALIZE_TOKENIZED_VAULT_DEPOSITOR_DISCRIMINATOR = toDiscriminator(50, 183, 239, 21, 59, 150, 51, 227);

  public static Instruction initializeTokenizedVaultDepositor(final AccountMeta invokedDriftVaultsProgramMeta,
                                                              final PublicKey vaultKey,
                                                              final PublicKey vaultDepositorKey,
                                                              final PublicKey mintAccountKey,
                                                              final PublicKey metadataAccountKey,
                                                              final PublicKey payerKey,
                                                              final PublicKey tokenProgramKey,
                                                              final PublicKey tokenMetadataProgramKey,
                                                              final PublicKey rentKey,
                                                              final PublicKey systemProgramKey,
                                                              final InitializeTokenizedVaultDepositorParams params) {
    final var keys = List.of(
      createRead(vaultKey),
      createWrite(vaultDepositorKey),
      createWrite(mintAccountKey),
      createWrite(metadataAccountKey),
      createWritableSigner(payerKey),
      createRead(tokenProgramKey),
      createRead(tokenMetadataProgramKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = INITIALIZE_TOKENIZED_VAULT_DEPOSITOR_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record InitializeTokenizedVaultDepositorIxData(Discriminator discriminator, InitializeTokenizedVaultDepositorParams params) implements Borsh {  

    public static InitializeTokenizedVaultDepositorIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static InitializeTokenizedVaultDepositorIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = InitializeTokenizedVaultDepositorParams.read(_data, i);
      return new InitializeTokenizedVaultDepositorIxData(discriminator, params);
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

  public static final Discriminator TOKENIZE_SHARES_DISCRIMINATOR = toDiscriminator(166, 4, 14, 227, 21, 161, 121, 122);

  public static Instruction tokenizeShares(final AccountMeta invokedDriftVaultsProgramMeta,
                                           final PublicKey vaultKey,
                                           final PublicKey vaultDepositorKey,
                                           final PublicKey authorityKey,
                                           final PublicKey tokenizedVaultDepositorKey,
                                           final PublicKey mintKey,
                                           final PublicKey userTokenAccountKey,
                                           final PublicKey driftUserKey,
                                           final PublicKey tokenProgramKey,
                                           final long amount,
                                           final WithdrawUnit unit) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(vaultDepositorKey),
      createReadOnlySigner(authorityKey),
      createWrite(tokenizedVaultDepositorKey),
      createWrite(mintKey),
      createRead(userTokenAccountKey),
      createWrite(driftUserKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16 + Borsh.len(unit)];
    int i = TOKENIZE_SHARES_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    Borsh.write(unit, _data, i);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record TokenizeSharesIxData(Discriminator discriminator, long amount, WithdrawUnit unit) implements Borsh {  

    public static TokenizeSharesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static TokenizeSharesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var unit = WithdrawUnit.read(_data, i);
      return new TokenizeSharesIxData(discriminator, amount, unit);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      i += Borsh.write(unit, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REDEEM_TOKENS_DISCRIMINATOR = toDiscriminator(246, 98, 134, 41, 152, 33, 120, 69);

  public static Instruction redeemTokens(final AccountMeta invokedDriftVaultsProgramMeta,
                                         final PublicKey vaultKey,
                                         final PublicKey vaultDepositorKey,
                                         final PublicKey authorityKey,
                                         final PublicKey tokenizedVaultDepositorKey,
                                         final PublicKey mintKey,
                                         final PublicKey userTokenAccountKey,
                                         final PublicKey vaultTokenAccountKey,
                                         final PublicKey driftUserKey,
                                         final PublicKey tokenProgramKey,
                                         final long tokensToBurn) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(vaultDepositorKey),
      createReadOnlySigner(authorityKey),
      createWrite(tokenizedVaultDepositorKey),
      createWrite(mintKey),
      createWrite(userTokenAccountKey),
      createWrite(vaultTokenAccountKey),
      createWrite(driftUserKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = REDEEM_TOKENS_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, tokensToBurn);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record RedeemTokensIxData(Discriminator discriminator, long tokensToBurn) implements Borsh {  

    public static RedeemTokensIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static RedeemTokensIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var tokensToBurn = getInt64LE(_data, i);
      return new RedeemTokensIxData(discriminator, tokensToBurn);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, tokensToBurn);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
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
    int i = DEPOSIT_DISCRIMINATOR.write(_data, 0);
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
      final var discriminator = createAnchorDiscriminator(_data, offset);
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
                                            final long withdrawAmount,
                                            final WithdrawUnit withdrawUnit) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(vaultDepositorKey),
      createReadOnlySigner(authorityKey),
      createRead(driftUserStatsKey),
      createRead(driftUserKey)
    );

    final byte[] _data = new byte[16 + Borsh.len(withdrawUnit)];
    int i = REQUEST_WITHDRAW_DISCRIMINATOR.write(_data, 0);
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
      final var discriminator = createAnchorDiscriminator(_data, offset);
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
                                                  final PublicKey driftUserKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(vaultDepositorKey),
      createReadOnlySigner(authorityKey),
      createRead(driftUserStatsKey),
      createRead(driftUserKey)
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
                                      final PublicKey adminKey,
                                      final PublicKey driftUserStatsKey,
                                      final PublicKey driftUserKey,
                                      final PublicKey driftProgramKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(vaultDepositorKey),
      createRead(authorityKey),
      createWritableSigner(adminKey),
      createWrite(driftUserStatsKey),
      createWrite(driftUserKey),
      createRead(driftProgramKey)
    );

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, LIQUIDATE_DISCRIMINATOR);
  }

  public static final Discriminator RESET_DELEGATE_DISCRIMINATOR = toDiscriminator(204, 13, 61, 153, 97, 83, 146, 98);

  public static Instruction resetDelegate(final AccountMeta invokedDriftVaultsProgramMeta,
                                          final PublicKey vaultKey,
                                          final PublicKey driftUserKey,
                                          final PublicKey driftProgramKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(driftUserKey),
      createRead(driftProgramKey)
    );

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, RESET_DELEGATE_DISCRIMINATOR);
  }

  public static final Discriminator RESET_FUEL_SEASON_DISCRIMINATOR = toDiscriminator(199, 122, 192, 255, 32, 99, 63, 200);

  public static Instruction resetFuelSeason(final AccountMeta invokedDriftVaultsProgramMeta,
                                            final PublicKey vaultKey,
                                            final PublicKey vaultDepositorKey,
                                            final PublicKey adminKey,
                                            final PublicKey driftUserStatsKey,
                                            final PublicKey driftStateKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(vaultDepositorKey),
      createReadOnlySigner(adminKey),
      createWrite(driftUserStatsKey),
      createRead(driftStateKey)
    );

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, RESET_FUEL_SEASON_DISCRIMINATOR);
  }

  public static final Discriminator RESET_VAULT_FUEL_SEASON_DISCRIMINATOR = toDiscriminator(190, 107, 13, 176, 10, 102, 134, 168);

  public static Instruction resetVaultFuelSeason(final AccountMeta invokedDriftVaultsProgramMeta,
                                                 final PublicKey vaultKey,
                                                 final PublicKey adminKey,
                                                 final PublicKey driftStateKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(adminKey),
      createRead(driftStateKey)
    );

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, RESET_VAULT_FUEL_SEASON_DISCRIMINATOR);
  }

  public static final Discriminator MANAGER_BORROW_DISCRIMINATOR = toDiscriminator(176, 237, 83, 189, 102, 73, 14, 153);

  public static Instruction managerBorrow(final AccountMeta invokedDriftVaultsProgramMeta,
                                          final PublicKey vaultKey,
                                          final PublicKey vaultTokenAccountKey,
                                          final PublicKey managerKey,
                                          final PublicKey driftUserStatsKey,
                                          final PublicKey driftUserKey,
                                          final PublicKey driftStateKey,
                                          final PublicKey driftSpotMarketVaultKey,
                                          final PublicKey driftSignerKey,
                                          final PublicKey userTokenAccountKey,
                                          final PublicKey driftProgramKey,
                                          final PublicKey tokenProgramKey,
                                          final int borrowSpotMarketIndex,
                                          final long borrowAmount) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(vaultTokenAccountKey),
      createReadOnlySigner(managerKey),
      createWrite(driftUserStatsKey),
      createWrite(driftUserKey),
      createRead(driftStateKey),
      createWrite(driftSpotMarketVaultKey),
      createRead(driftSignerKey),
      createWrite(userTokenAccountKey),
      createRead(driftProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[18];
    int i = MANAGER_BORROW_DISCRIMINATOR.write(_data, 0);
    putInt16LE(_data, i, borrowSpotMarketIndex);
    i += 2;
    putInt64LE(_data, i, borrowAmount);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record ManagerBorrowIxData(Discriminator discriminator, int borrowSpotMarketIndex, long borrowAmount) implements Borsh {  

    public static ManagerBorrowIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 18;

    public static ManagerBorrowIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var borrowSpotMarketIndex = getInt16LE(_data, i);
      i += 2;
      final var borrowAmount = getInt64LE(_data, i);
      return new ManagerBorrowIxData(discriminator, borrowSpotMarketIndex, borrowAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, borrowSpotMarketIndex);
      i += 2;
      putInt64LE(_data, i, borrowAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator MANAGER_REPAY_DISCRIMINATOR = toDiscriminator(202, 56, 50, 3, 1, 40, 93, 128);

  public static Instruction managerRepay(final AccountMeta invokedDriftVaultsProgramMeta,
                                         final PublicKey vaultKey,
                                         final PublicKey vaultTokenAccountKey,
                                         final PublicKey managerKey,
                                         final PublicKey driftUserStatsKey,
                                         final PublicKey driftUserKey,
                                         final PublicKey driftStateKey,
                                         final PublicKey driftSpotMarketVaultKey,
                                         final PublicKey driftSignerKey,
                                         final PublicKey userTokenAccountKey,
                                         final PublicKey driftProgramKey,
                                         final PublicKey tokenProgramKey,
                                         final int repaySpotMarketIndex,
                                         final long repayAmount,
                                         final OptionalLong repayValue) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(vaultTokenAccountKey),
      createReadOnlySigner(managerKey),
      createWrite(driftUserStatsKey),
      createWrite(driftUserKey),
      createRead(driftStateKey),
      createWrite(driftSpotMarketVaultKey),
      createRead(driftSignerKey),
      createWrite(userTokenAccountKey),
      createRead(driftProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[
        18
        + (repayValue == null || repayValue.isEmpty() ? 1 : 9)
    ];
    int i = MANAGER_REPAY_DISCRIMINATOR.write(_data, 0);
    putInt16LE(_data, i, repaySpotMarketIndex);
    i += 2;
    putInt64LE(_data, i, repayAmount);
    i += 8;
    Borsh.writeOptional(repayValue, _data, i);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record ManagerRepayIxData(Discriminator discriminator,
                                   int repaySpotMarketIndex,
                                   long repayAmount,
                                   OptionalLong repayValue) implements Borsh {  

    public static ManagerRepayIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ManagerRepayIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var repaySpotMarketIndex = getInt16LE(_data, i);
      i += 2;
      final var repayAmount = getInt64LE(_data, i);
      i += 8;
      final var repayValue = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
      return new ManagerRepayIxData(discriminator, repaySpotMarketIndex, repayAmount, repayValue);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, repaySpotMarketIndex);
      i += 2;
      putInt64LE(_data, i, repayAmount);
      i += 8;
      i += Borsh.writeOptional(repayValue, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 2 + 8 + (repayValue == null || repayValue.isEmpty() ? 1 : (1 + 8));
    }
  }

  public static final Discriminator MANAGER_UPDATE_BORROW_DISCRIMINATOR = toDiscriminator(193, 183, 210, 205, 223, 11, 240, 138);

  public static Instruction managerUpdateBorrow(final AccountMeta invokedDriftVaultsProgramMeta,
                                                final PublicKey vaultKey,
                                                final PublicKey managerKey,
                                                final PublicKey driftUserStatsKey,
                                                final PublicKey driftUserKey,
                                                final long newBorrowValue) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(managerKey),
      createWrite(driftUserStatsKey),
      createWrite(driftUserKey)
    );

    final byte[] _data = new byte[16];
    int i = MANAGER_UPDATE_BORROW_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, newBorrowValue);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record ManagerUpdateBorrowIxData(Discriminator discriminator, long newBorrowValue) implements Borsh {  

    public static ManagerUpdateBorrowIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static ManagerUpdateBorrowIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var newBorrowValue = getInt64LE(_data, i);
      return new ManagerUpdateBorrowIxData(discriminator, newBorrowValue);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, newBorrowValue);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
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
    int i = MANAGER_DEPOSIT_DISCRIMINATOR.write(_data, 0);
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
      final var discriminator = createAnchorDiscriminator(_data, offset);
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
                                                   final long withdrawAmount,
                                                   final WithdrawUnit withdrawUnit) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(managerKey),
      createRead(driftUserStatsKey),
      createRead(driftUserKey)
    );

    final byte[] _data = new byte[16 + Borsh.len(withdrawUnit)];
    int i = MANAGER_REQUEST_WITHDRAW_DISCRIMINATOR.write(_data, 0);
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
      final var discriminator = createAnchorDiscriminator(_data, offset);
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
                                                        final PublicKey driftUserKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(managerKey),
      createRead(driftUserStatsKey),
      createRead(driftUserKey)
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

  public static final Discriminator MANAGER_UPDATE_FUEL_DISTRIBUTION_MODE_DISCRIMINATOR = toDiscriminator(23, 201, 108, 210, 88, 53, 123, 91);

  public static Instruction managerUpdateFuelDistributionMode(final AccountMeta invokedDriftVaultsProgramMeta,
                                                              final PublicKey vaultKey,
                                                              final PublicKey managerKey,
                                                              final int fuelDistributionMode) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(managerKey)
    );

    final byte[] _data = new byte[9];
    int i = MANAGER_UPDATE_FUEL_DISTRIBUTION_MODE_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) fuelDistributionMode;

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record ManagerUpdateFuelDistributionModeIxData(Discriminator discriminator, int fuelDistributionMode) implements Borsh {  

    public static ManagerUpdateFuelDistributionModeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static ManagerUpdateFuelDistributionModeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var fuelDistributionMode = _data[i] & 0xFF;
      return new ManagerUpdateFuelDistributionModeIxData(discriminator, fuelDistributionMode);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) fuelDistributionMode;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ADMIN_INIT_FEE_UPDATE_DISCRIMINATOR = toDiscriminator(39, 31, 253, 244, 241, 5, 72, 152);

  public static Instruction adminInitFeeUpdate(final AccountMeta invokedDriftVaultsProgramMeta,
                                               final PublicKey vaultKey,
                                               final PublicKey adminKey,
                                               final PublicKey feeUpdateKey,
                                               final PublicKey systemProgramKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWritableSigner(adminKey),
      createWrite(feeUpdateKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, ADMIN_INIT_FEE_UPDATE_DISCRIMINATOR);
  }

  public static final Discriminator ADMIN_DELETE_FEE_UPDATE_DISCRIMINATOR = toDiscriminator(189, 83, 182, 110, 132, 133, 77, 36);

  public static Instruction adminDeleteFeeUpdate(final AccountMeta invokedDriftVaultsProgramMeta,
                                                 final PublicKey vaultKey,
                                                 final PublicKey adminKey,
                                                 final PublicKey feeUpdateKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWritableSigner(adminKey),
      createWrite(feeUpdateKey)
    );

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, ADMIN_DELETE_FEE_UPDATE_DISCRIMINATOR);
  }

  public static final Discriminator ADMIN_UPDATE_VAULT_CLASS_DISCRIMINATOR = toDiscriminator(103, 11, 101, 120, 92, 136, 230, 215);

  public static Instruction adminUpdateVaultClass(final AccountMeta invokedDriftVaultsProgramMeta,
                                                  final PublicKey vaultKey,
                                                  final PublicKey adminKey,
                                                  final PublicKey systemProgramKey,
                                                  final int newVaultClass) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWritableSigner(adminKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[9];
    int i = ADMIN_UPDATE_VAULT_CLASS_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) newVaultClass;

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record AdminUpdateVaultClassIxData(Discriminator discriminator, int newVaultClass) implements Borsh {  

    public static AdminUpdateVaultClassIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static AdminUpdateVaultClassIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var newVaultClass = _data[i] & 0xFF;
      return new AdminUpdateVaultClassIxData(discriminator, newVaultClass);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) newVaultClass;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator MANAGER_UPDATE_FEES_DISCRIMINATOR = toDiscriminator(205, 156, 240, 90, 150, 60, 144, 53);

  public static Instruction managerUpdateFees(final AccountMeta invokedDriftVaultsProgramMeta,
                                              final PublicKey vaultKey,
                                              final PublicKey managerKey,
                                              final PublicKey feeUpdateKey,
                                              final ManagerUpdateFeesParams params) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(managerKey),
      createWrite(feeUpdateKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = MANAGER_UPDATE_FEES_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record ManagerUpdateFeesIxData(Discriminator discriminator, ManagerUpdateFeesParams params) implements Borsh {  

    public static ManagerUpdateFeesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ManagerUpdateFeesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = ManagerUpdateFeesParams.read(_data, i);
      return new ManagerUpdateFeesIxData(discriminator, params);
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

  public static final Discriminator MANAGER_CANCEL_FEE_UPDATE_DISCRIMINATOR = toDiscriminator(176, 204, 109, 177, 90, 244, 69, 156);

  public static Instruction managerCancelFeeUpdate(final AccountMeta invokedDriftVaultsProgramMeta,
                                                   final PublicKey vaultKey,
                                                   final PublicKey managerKey,
                                                   final PublicKey feeUpdateKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(managerKey),
      createWrite(feeUpdateKey)
    );

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, MANAGER_CANCEL_FEE_UPDATE_DISCRIMINATOR);
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

  public static final Discriminator APPLY_REBASE_DISCRIMINATOR = toDiscriminator(161, 115, 9, 131, 136, 29, 147, 155);

  public static Instruction applyRebase(final AccountMeta invokedDriftVaultsProgramMeta,
                                        final PublicKey vaultKey,
                                        final PublicKey vaultDepositorKey,
                                        final PublicKey driftUserKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(vaultDepositorKey),
      createWrite(driftUserKey)
    );

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, APPLY_REBASE_DISCRIMINATOR);
  }

  public static final Discriminator APPLY_REBASE_TOKENIZED_DEPOSITOR_DISCRIMINATOR = toDiscriminator(218, 169, 190, 71, 150, 184, 77, 166);

  public static Instruction applyRebaseTokenizedDepositor(final AccountMeta invokedDriftVaultsProgramMeta,
                                                          final PublicKey vaultKey,
                                                          final PublicKey tokenizedVaultDepositorKey,
                                                          final PublicKey driftUserKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(tokenizedVaultDepositorKey),
      createWrite(driftUserKey)
    );

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, APPLY_REBASE_TOKENIZED_DEPOSITOR_DISCRIMINATOR);
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

  public static final Discriminator INITIALIZE_INSURANCE_FUND_STAKE_DISCRIMINATOR = toDiscriminator(187, 179, 243, 70, 248, 90, 92, 147);

  public static Instruction initializeInsuranceFundStake(final AccountMeta invokedDriftVaultsProgramMeta,
                                                         final PublicKey vaultKey,
                                                         final PublicKey managerKey,
                                                         final PublicKey payerKey,
                                                         final PublicKey rentKey,
                                                         final PublicKey driftSpotMarketKey,
                                                         final PublicKey driftSpotMarketMintKey,
                                                         final PublicKey vaultTokenAccountKey,
                                                         final PublicKey insuranceFundStakeKey,
                                                         final PublicKey driftUserStatsKey,
                                                         final PublicKey driftStateKey,
                                                         final PublicKey driftProgramKey,
                                                         final PublicKey tokenProgramKey,
                                                         final PublicKey systemProgramKey,
                                                         final int marketIndex) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(managerKey),
      createWritableSigner(payerKey),
      createRead(rentKey),
      createWrite(driftSpotMarketKey),
      createRead(driftSpotMarketMintKey),
      createWrite(vaultTokenAccountKey),
      createWrite(insuranceFundStakeKey),
      createWrite(driftUserStatsKey),
      createRead(driftStateKey),
      createRead(driftProgramKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[10];
    int i = INITIALIZE_INSURANCE_FUND_STAKE_DISCRIMINATOR.write(_data, 0);
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record InitializeInsuranceFundStakeIxData(Discriminator discriminator, int marketIndex) implements Borsh {  

    public static InitializeInsuranceFundStakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static InitializeInsuranceFundStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var marketIndex = getInt16LE(_data, i);
      return new InitializeInsuranceFundStakeIxData(discriminator, marketIndex);
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

  public static final Discriminator ADD_INSURANCE_FUND_STAKE_DISCRIMINATOR = toDiscriminator(251, 144, 115, 11, 222, 47, 62, 236);

  public static Instruction addInsuranceFundStake(final AccountMeta invokedDriftVaultsProgramMeta,
                                                  final PublicKey vaultKey,
                                                  final PublicKey managerKey,
                                                  final PublicKey driftSpotMarketKey,
                                                  final PublicKey driftSpotMarketVaultKey,
                                                  final PublicKey insuranceFundStakeKey,
                                                  final PublicKey insuranceFundVaultKey,
                                                  final PublicKey managerTokenAccountKey,
                                                  final PublicKey vaultIfTokenAccountKey,
                                                  final PublicKey driftUserStatsKey,
                                                  final PublicKey driftStateKey,
                                                  final PublicKey driftSignerKey,
                                                  final PublicKey driftProgramKey,
                                                  final PublicKey tokenProgramKey,
                                                  final int marketIndex,
                                                  final long amount) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWritableSigner(managerKey),
      createWrite(driftSpotMarketKey),
      createWrite(driftSpotMarketVaultKey),
      createWrite(insuranceFundStakeKey),
      createWrite(insuranceFundVaultKey),
      createWrite(managerTokenAccountKey),
      createWrite(vaultIfTokenAccountKey),
      createWrite(driftUserStatsKey),
      createRead(driftStateKey),
      createRead(driftSignerKey),
      createRead(driftProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[18];
    int i = ADD_INSURANCE_FUND_STAKE_DISCRIMINATOR.write(_data, 0);
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record AddInsuranceFundStakeIxData(Discriminator discriminator, int marketIndex, long amount) implements Borsh {  

    public static AddInsuranceFundStakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 18;

    public static AddInsuranceFundStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var marketIndex = getInt16LE(_data, i);
      i += 2;
      final var amount = getInt64LE(_data, i);
      return new AddInsuranceFundStakeIxData(discriminator, marketIndex, amount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, marketIndex);
      i += 2;
      putInt64LE(_data, i, amount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REQUEST_REMOVE_INSURANCE_FUND_STAKE_DISCRIMINATOR = toDiscriminator(142, 70, 204, 92, 73, 106, 180, 52);

  public static Instruction requestRemoveInsuranceFundStake(final AccountMeta invokedDriftVaultsProgramMeta,
                                                            final PublicKey vaultKey,
                                                            final PublicKey managerKey,
                                                            final PublicKey driftSpotMarketKey,
                                                            final PublicKey insuranceFundStakeKey,
                                                            final PublicKey insuranceFundVaultKey,
                                                            final PublicKey driftUserStatsKey,
                                                            final PublicKey driftProgramKey,
                                                            final int marketIndex,
                                                            final long amount) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(managerKey),
      createWrite(driftSpotMarketKey),
      createWrite(insuranceFundStakeKey),
      createWrite(insuranceFundVaultKey),
      createWrite(driftUserStatsKey),
      createRead(driftProgramKey)
    );

    final byte[] _data = new byte[18];
    int i = REQUEST_REMOVE_INSURANCE_FUND_STAKE_DISCRIMINATOR.write(_data, 0);
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record RequestRemoveInsuranceFundStakeIxData(Discriminator discriminator, int marketIndex, long amount) implements Borsh {  

    public static RequestRemoveInsuranceFundStakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 18;

    public static RequestRemoveInsuranceFundStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var marketIndex = getInt16LE(_data, i);
      i += 2;
      final var amount = getInt64LE(_data, i);
      return new RequestRemoveInsuranceFundStakeIxData(discriminator, marketIndex, amount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, marketIndex);
      i += 2;
      putInt64LE(_data, i, amount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REMOVE_INSURANCE_FUND_STAKE_DISCRIMINATOR = toDiscriminator(128, 166, 142, 9, 254, 187, 143, 174);

  public static Instruction removeInsuranceFundStake(final AccountMeta invokedDriftVaultsProgramMeta,
                                                     final PublicKey vaultKey,
                                                     final PublicKey managerKey,
                                                     final PublicKey driftSpotMarketKey,
                                                     final PublicKey insuranceFundStakeKey,
                                                     final PublicKey insuranceFundVaultKey,
                                                     final PublicKey managerTokenAccountKey,
                                                     final PublicKey vaultIfTokenAccountKey,
                                                     final PublicKey driftUserStatsKey,
                                                     final PublicKey driftStateKey,
                                                     final PublicKey driftSignerKey,
                                                     final PublicKey driftProgramKey,
                                                     final PublicKey tokenProgramKey,
                                                     final int marketIndex) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(managerKey),
      createWrite(driftSpotMarketKey),
      createWrite(insuranceFundStakeKey),
      createWrite(insuranceFundVaultKey),
      createWrite(managerTokenAccountKey),
      createWrite(vaultIfTokenAccountKey),
      createWrite(driftUserStatsKey),
      createRead(driftStateKey),
      createRead(driftSignerKey),
      createRead(driftProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[10];
    int i = REMOVE_INSURANCE_FUND_STAKE_DISCRIMINATOR.write(_data, 0);
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record RemoveInsuranceFundStakeIxData(Discriminator discriminator, int marketIndex) implements Borsh {  

    public static RemoveInsuranceFundStakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static RemoveInsuranceFundStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var marketIndex = getInt16LE(_data, i);
      return new RemoveInsuranceFundStakeIxData(discriminator, marketIndex);
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

  public static final Discriminator CANCEL_REQUEST_REMOVE_INSURANCE_FUND_STAKE_DISCRIMINATOR = toDiscriminator(97, 235, 78, 62, 212, 42, 241, 127);

  public static Instruction cancelRequestRemoveInsuranceFundStake(final AccountMeta invokedDriftVaultsProgramMeta,
                                                                  final PublicKey vaultKey,
                                                                  final PublicKey managerKey,
                                                                  final PublicKey driftSpotMarketKey,
                                                                  final PublicKey insuranceFundStakeKey,
                                                                  final PublicKey insuranceFundVaultKey,
                                                                  final PublicKey driftUserStatsKey,
                                                                  final PublicKey driftProgramKey,
                                                                  final int marketIndex) {
    final var keys = List.of(
      createWrite(vaultKey),
      createReadOnlySigner(managerKey),
      createWrite(driftSpotMarketKey),
      createWrite(insuranceFundStakeKey),
      createWrite(insuranceFundVaultKey),
      createWrite(driftUserStatsKey),
      createRead(driftProgramKey)
    );

    final byte[] _data = new byte[10];
    int i = CANCEL_REQUEST_REMOVE_INSURANCE_FUND_STAKE_DISCRIMINATOR.write(_data, 0);
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record CancelRequestRemoveInsuranceFundStakeIxData(Discriminator discriminator, int marketIndex) implements Borsh {  

    public static CancelRequestRemoveInsuranceFundStakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static CancelRequestRemoveInsuranceFundStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var marketIndex = getInt16LE(_data, i);
      return new CancelRequestRemoveInsuranceFundStakeIxData(discriminator, marketIndex);
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

  public static final Discriminator PROTOCOL_REQUEST_WITHDRAW_DISCRIMINATOR = toDiscriminator(189, 46, 14, 31, 7, 254, 150, 132);

  public static Instruction protocolRequestWithdraw(final AccountMeta invokedDriftVaultsProgramMeta,
                                                    final PublicKey vaultKey,
                                                    final PublicKey vaultProtocolKey,
                                                    final PublicKey protocolKey,
                                                    final PublicKey driftUserStatsKey,
                                                    final PublicKey driftUserKey,
                                                    final long withdrawAmount,
                                                    final WithdrawUnit withdrawUnit) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(vaultProtocolKey),
      createReadOnlySigner(protocolKey),
      createRead(driftUserStatsKey),
      createRead(driftUserKey)
    );

    final byte[] _data = new byte[16 + Borsh.len(withdrawUnit)];
    int i = PROTOCOL_REQUEST_WITHDRAW_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, withdrawAmount);
    i += 8;
    Borsh.write(withdrawUnit, _data, i);

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, _data);
  }

  public record ProtocolRequestWithdrawIxData(Discriminator discriminator, long withdrawAmount, WithdrawUnit withdrawUnit) implements Borsh {  

    public static ProtocolRequestWithdrawIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static ProtocolRequestWithdrawIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var withdrawAmount = getInt64LE(_data, i);
      i += 8;
      final var withdrawUnit = WithdrawUnit.read(_data, i);
      return new ProtocolRequestWithdrawIxData(discriminator, withdrawAmount, withdrawUnit);
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

  public static final Discriminator PROTOCOL_CANCEL_WITHDRAW_REQUEST_DISCRIMINATOR = toDiscriminator(194, 217, 171, 94, 56, 253, 179, 242);

  public static Instruction protocolCancelWithdrawRequest(final AccountMeta invokedDriftVaultsProgramMeta,
                                                          final PublicKey vaultKey,
                                                          final PublicKey vaultProtocolKey,
                                                          final PublicKey protocolKey,
                                                          final PublicKey driftUserStatsKey,
                                                          final PublicKey driftUserKey) {
    final var keys = List.of(
      createWrite(vaultKey),
      createWrite(vaultProtocolKey),
      createReadOnlySigner(protocolKey),
      createRead(driftUserStatsKey),
      createRead(driftUserKey)
    );

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, PROTOCOL_CANCEL_WITHDRAW_REQUEST_DISCRIMINATOR);
  }

  public static final Discriminator PROTOCOL_WITHDRAW_DISCRIMINATOR = toDiscriminator(166, 24, 188, 209, 21, 251, 63, 199);

  public static Instruction protocolWithdraw(final AccountMeta invokedDriftVaultsProgramMeta,
                                             final PublicKey vaultKey,
                                             final PublicKey vaultProtocolKey,
                                             final PublicKey protocolKey,
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
      createWrite(vaultProtocolKey),
      createReadOnlySigner(protocolKey),
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

    return Instruction.createInstruction(invokedDriftVaultsProgramMeta, keys, PROTOCOL_WITHDRAW_DISCRIMINATOR);
  }

  private DriftVaultsProgram() {
  }
}
