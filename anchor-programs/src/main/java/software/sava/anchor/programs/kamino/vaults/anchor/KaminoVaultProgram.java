package software.sava.anchor.programs.kamino.vaults.anchor;

import java.lang.String;

import java.util.List;

import software.sava.anchor.programs.kamino.vaults.anchor.types.VaultConfigField;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.anchor.AnchorUtil.writeDiscriminator;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class KaminoVaultProgram {

  public static final Discriminator INIT_VAULT_DISCRIMINATOR = toDiscriminator(77, 79, 85, 150, 33, 217, 52, 106);

  public static Instruction initVault(final AccountMeta invokedKaminoVaultProgramMeta,
                                      final PublicKey adminAuthorityKey,
                                      final PublicKey vaultStateKey,
                                      final PublicKey baseVaultAuthorityKey,
                                      final PublicKey tokenVaultKey,
                                      final PublicKey baseTokenMintKey,
                                      final PublicKey sharesMintKey,
                                      final PublicKey adminTokenAccountKey,
                                      final PublicKey systemProgramKey,
                                      final PublicKey rentKey,
                                      final PublicKey tokenProgramKey,
                                      final PublicKey sharesTokenProgramKey) {
    final var keys = List.of(
      createWritableSigner(adminAuthorityKey),
      createWrite(vaultStateKey),
      createRead(baseVaultAuthorityKey),
      createWrite(tokenVaultKey),
      createRead(baseTokenMintKey),
      createWrite(sharesMintKey),
      createWrite(adminTokenAccountKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(tokenProgramKey),
      createRead(sharesTokenProgramKey)
    );

    return Instruction.createInstruction(invokedKaminoVaultProgramMeta, keys, INIT_VAULT_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_RESERVE_ALLOCATION_DISCRIMINATOR = toDiscriminator(5, 54, 213, 112, 75, 232, 117, 37);

  public static Instruction updateReserveAllocation(final AccountMeta invokedKaminoVaultProgramMeta,
                                                    final PublicKey signerKey,
                                                    final PublicKey vaultStateKey,
                                                    final PublicKey baseVaultAuthorityKey,
                                                    final PublicKey reserveCollateralMintKey,
                                                    final PublicKey reserveKey,
                                                    final PublicKey ctokenVaultKey,
                                                    final PublicKey reserveCollateralTokenProgramKey,
                                                    final PublicKey systemProgramKey,
                                                    final PublicKey rentKey,
                                                    final long weight,
                                                    final long cap) {
    final var keys = List.of(
      createWritableSigner(signerKey),
      createWrite(vaultStateKey),
      createRead(baseVaultAuthorityKey),
      createWrite(reserveCollateralMintKey),
      createRead(reserveKey),
      createWrite(ctokenVaultKey),
      createRead(reserveCollateralTokenProgramKey),
      createRead(systemProgramKey),
      createRead(rentKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(UPDATE_RESERVE_ALLOCATION_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, weight);
    i += 8;
    putInt64LE(_data, i, cap);

    return Instruction.createInstruction(invokedKaminoVaultProgramMeta, keys, _data);
  }

  public record UpdateReserveAllocationIxData(Discriminator discriminator, long weight, long cap) implements Borsh {  

    public static UpdateReserveAllocationIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static UpdateReserveAllocationIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var weight = getInt64LE(_data, i);
      i += 8;
      final var cap = getInt64LE(_data, i);
      return new UpdateReserveAllocationIxData(discriminator, weight, cap);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, weight);
      i += 8;
      putInt64LE(_data, i, cap);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DEPOSIT_DISCRIMINATOR = toDiscriminator(242, 35, 198, 137, 82, 225, 242, 182);

  public static Instruction deposit(final AccountMeta invokedKaminoVaultProgramMeta,
                                    final PublicKey userKey,
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
      createWritableSigner(userKey),
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
    int i = writeDiscriminator(DEPOSIT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, maxAmount);

    return Instruction.createInstruction(invokedKaminoVaultProgramMeta, keys, _data);
  }

  public record DepositIxData(Discriminator discriminator, long maxAmount) implements Borsh {  

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
      final var maxAmount = getInt64LE(_data, i);
      return new DepositIxData(discriminator, maxAmount);
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

  public static final Discriminator WITHDRAW_DISCRIMINATOR = toDiscriminator(183, 18, 70, 156, 148, 109, 161, 34);

  public static Instruction withdraw(final AccountMeta invokedKaminoVaultProgramMeta,
                                     final PublicKey withdrawFromAvailableUserKey,
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
                                     final PublicKey withdrawFromReserveAccountsVaultStateKey,
                                     final PublicKey withdrawFromReserveAccountsReserveKey,
                                     final PublicKey withdrawFromReserveAccountsCtokenVaultKey,
                                     final PublicKey withdrawFromReserveAccountsLendingMarketKey,
                                     final PublicKey withdrawFromReserveAccountsLendingMarketAuthorityKey,
                                     final PublicKey withdrawFromReserveAccountsReserveLiquiditySupplyKey,
                                     final PublicKey withdrawFromReserveAccountsReserveCollateralMintKey,
                                     final PublicKey withdrawFromReserveAccountsReserveCollateralTokenProgramKey,
                                     final PublicKey withdrawFromReserveAccountsInstructionSysvarAccountKey,
                                     final PublicKey eventAuthorityKey,
                                     final PublicKey programKey,
                                     final long sharesAmount) {
    final var keys = List.of(
      createWritableSigner(withdrawFromAvailableUserKey),
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
      createWrite(withdrawFromReserveAccountsVaultStateKey),
      createWrite(withdrawFromReserveAccountsReserveKey),
      createWrite(withdrawFromReserveAccountsCtokenVaultKey),
      createRead(withdrawFromReserveAccountsLendingMarketKey),
      createRead(withdrawFromReserveAccountsLendingMarketAuthorityKey),
      createWrite(withdrawFromReserveAccountsReserveLiquiditySupplyKey),
      createWrite(withdrawFromReserveAccountsReserveCollateralMintKey),
      createRead(withdrawFromReserveAccountsReserveCollateralTokenProgramKey),
      createRead(withdrawFromReserveAccountsInstructionSysvarAccountKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(WITHDRAW_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, sharesAmount);

    return Instruction.createInstruction(invokedKaminoVaultProgramMeta, keys, _data);
  }

  public record WithdrawIxData(Discriminator discriminator, long sharesAmount) implements Borsh {  

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
      final var sharesAmount = getInt64LE(_data, i);
      return new WithdrawIxData(discriminator, sharesAmount);
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

  public static final Discriminator INVEST_DISCRIMINATOR = toDiscriminator(13, 245, 180, 103, 254, 182, 121, 4);

  public static Instruction invest(final AccountMeta invokedKaminoVaultProgramMeta,
                                   final PublicKey payerKey,
                                   final PublicKey payerTokenAccountKey,
                                   final PublicKey vaultStateKey,
                                   final PublicKey tokenVaultKey,
                                   final PublicKey tokenMintKey,
                                   final PublicKey baseVaultAuthorityKey,
                                   final PublicKey ctokenVaultKey,
                                   // CPI accounts
                                   final PublicKey reserveKey,
                                   final PublicKey lendingMarketKey,
                                   final PublicKey lendingMarketAuthorityKey,
                                   final PublicKey reserveLiquiditySupplyKey,
                                   final PublicKey reserveCollateralMintKey,
                                   final PublicKey klendProgramKey,
                                   final PublicKey reserveCollateralTokenProgramKey,
                                   final PublicKey tokenProgramKey,
                                   final PublicKey instructionSysvarAccountKey) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createWrite(payerTokenAccountKey),
      createWrite(vaultStateKey),
      createWrite(tokenVaultKey),
      createWrite(tokenMintKey),
      createWrite(baseVaultAuthorityKey),
      createWrite(ctokenVaultKey),
      createWrite(reserveKey),
      createRead(lendingMarketKey),
      createRead(lendingMarketAuthorityKey),
      createWrite(reserveLiquiditySupplyKey),
      createWrite(reserveCollateralMintKey),
      createRead(klendProgramKey),
      createRead(reserveCollateralTokenProgramKey),
      createRead(tokenProgramKey),
      createRead(instructionSysvarAccountKey)
    );

    return Instruction.createInstruction(invokedKaminoVaultProgramMeta, keys, INVEST_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_VAULT_CONFIG_DISCRIMINATOR = toDiscriminator(122, 3, 21, 222, 158, 255, 238, 157);

  public static Instruction updateVaultConfig(final AccountMeta invokedKaminoVaultProgramMeta,
                                              final PublicKey vaultAdminAuthorityKey,
                                              final PublicKey vaultStateKey,
                                              final PublicKey klendProgramKey,
                                              final VaultConfigField entry,
                                              final byte[] data) {
    final var keys = List.of(
      createReadOnlySigner(vaultAdminAuthorityKey),
      createWrite(vaultStateKey),
      createRead(klendProgramKey)
    );

    final byte[] _data = new byte[12 + Borsh.len(entry) + Borsh.lenVector(data)];
    int i = writeDiscriminator(UPDATE_VAULT_CONFIG_DISCRIMINATOR, _data, 0);
    i += Borsh.write(entry, _data, i);
    Borsh.writeVector(data, _data, i);

    return Instruction.createInstruction(invokedKaminoVaultProgramMeta, keys, _data);
  }

  public record UpdateVaultConfigIxData(Discriminator discriminator, VaultConfigField entry, byte[] data) implements Borsh {  

    public static UpdateVaultConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateVaultConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var entry = VaultConfigField.read(_data, i);
      i += Borsh.len(entry);
      final byte[] data = Borsh.readbyteVector(_data, i);
      return new UpdateVaultConfigIxData(discriminator, entry, data);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(entry, _data, i);
      i += Borsh.writeVector(data, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(entry) + Borsh.lenVector(data);
    }
  }

  public static final Discriminator WITHDRAW_PENDING_FEES_DISCRIMINATOR = toDiscriminator(131, 194, 200, 140, 175, 244, 217, 183);

  public static Instruction withdrawPendingFees(final AccountMeta invokedKaminoVaultProgramMeta,
                                                final PublicKey vaultAdminAuthorityKey,
                                                final PublicKey vaultStateKey,
                                                final PublicKey reserveKey,
                                                final PublicKey tokenVaultKey,
                                                final PublicKey ctokenVaultKey,
                                                final PublicKey baseVaultAuthorityKey,
                                                final PublicKey tokenAtaKey,
                                                final PublicKey tokenMintKey,
                                                // CPI accounts
                                                final PublicKey lendingMarketKey,
                                                final PublicKey lendingMarketAuthorityKey,
                                                final PublicKey reserveLiquiditySupplyKey,
                                                final PublicKey reserveCollateralMintKey,
                                                final PublicKey klendProgramKey,
                                                final PublicKey tokenProgramKey,
                                                final PublicKey reserveCollateralTokenProgramKey,
                                                final PublicKey instructionSysvarAccountKey) {
    final var keys = List.of(
      createWritableSigner(vaultAdminAuthorityKey),
      createWrite(vaultStateKey),
      createWrite(reserveKey),
      createWrite(tokenVaultKey),
      createWrite(ctokenVaultKey),
      createWrite(baseVaultAuthorityKey),
      createWrite(tokenAtaKey),
      createWrite(tokenMintKey),
      createRead(lendingMarketKey),
      createRead(lendingMarketAuthorityKey),
      createWrite(reserveLiquiditySupplyKey),
      createWrite(reserveCollateralMintKey),
      createRead(klendProgramKey),
      createRead(tokenProgramKey),
      createRead(reserveCollateralTokenProgramKey),
      createRead(instructionSysvarAccountKey)
    );

    return Instruction.createInstruction(invokedKaminoVaultProgramMeta, keys, WITHDRAW_PENDING_FEES_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_ADMIN_DISCRIMINATOR = toDiscriminator(161, 176, 40, 213, 60, 184, 179, 228);

  public static Instruction updateAdmin(final AccountMeta invokedKaminoVaultProgramMeta, final PublicKey pendingAdminKey, final PublicKey vaultStateKey) {
    final var keys = List.of(
      createWritableSigner(pendingAdminKey),
      createWrite(vaultStateKey)
    );

    return Instruction.createInstruction(invokedKaminoVaultProgramMeta, keys, UPDATE_ADMIN_DISCRIMINATOR);
  }

  public static final Discriminator GIVE_UP_PENDING_FEES_DISCRIMINATOR = toDiscriminator(177, 200, 120, 134, 110, 217, 147, 81);

  public static Instruction giveUpPendingFees(final AccountMeta invokedKaminoVaultProgramMeta,
                                              final PublicKey vaultAdminAuthorityKey,
                                              final PublicKey vaultStateKey,
                                              final PublicKey klendProgramKey,
                                              final long maxAmountToGiveUp) {
    final var keys = List.of(
      createWritableSigner(vaultAdminAuthorityKey),
      createWrite(vaultStateKey),
      createRead(klendProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(GIVE_UP_PENDING_FEES_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, maxAmountToGiveUp);

    return Instruction.createInstruction(invokedKaminoVaultProgramMeta, keys, _data);
  }

  public record GiveUpPendingFeesIxData(Discriminator discriminator, long maxAmountToGiveUp) implements Borsh {  

    public static GiveUpPendingFeesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static GiveUpPendingFeesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var maxAmountToGiveUp = getInt64LE(_data, i);
      return new GiveUpPendingFeesIxData(discriminator, maxAmountToGiveUp);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, maxAmountToGiveUp);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_SHARES_METADATA_DISCRIMINATOR = toDiscriminator(3, 15, 172, 114, 200, 0, 131, 32);

  public static Instruction initializeSharesMetadata(final AccountMeta invokedKaminoVaultProgramMeta,
                                                     final PublicKey vaultAdminAuthorityKey,
                                                     final PublicKey vaultStateKey,
                                                     final PublicKey sharesMintKey,
                                                     final PublicKey baseVaultAuthorityKey,
                                                     final PublicKey sharesMetadataKey,
                                                     final PublicKey systemProgramKey,
                                                     final PublicKey rentKey,
                                                     final PublicKey metadataProgramKey,
                                                     final String name,
                                                     final String symbol,
                                                     final String uri) {
    final var keys = List.of(
      createWritableSigner(vaultAdminAuthorityKey),
      createRead(vaultStateKey),
      createRead(sharesMintKey),
      createRead(baseVaultAuthorityKey),
      createWrite(sharesMetadataKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(metadataProgramKey)
    );

    final byte[] _name = name.getBytes(UTF_8);
    final byte[] _symbol = symbol.getBytes(UTF_8);
    final byte[] _uri = uri.getBytes(UTF_8);
    final byte[] _data = new byte[20 + Borsh.lenVector(_name) + Borsh.lenVector(_symbol) + Borsh.lenVector(_uri)];
    int i = writeDiscriminator(INITIALIZE_SHARES_METADATA_DISCRIMINATOR, _data, 0);
    i += Borsh.writeVector(_name, _data, i);
    i += Borsh.writeVector(_symbol, _data, i);
    Borsh.writeVector(_uri, _data, i);

    return Instruction.createInstruction(invokedKaminoVaultProgramMeta, keys, _data);
  }

  public record InitializeSharesMetadataIxData(Discriminator discriminator,
                                               String name, byte[] _name,
                                               String symbol, byte[] _symbol,
                                               String uri, byte[] _uri) implements Borsh {  

    public static InitializeSharesMetadataIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static InitializeSharesMetadataIxData createRecord(final Discriminator discriminator,
                                                              final String name,
                                                              final String symbol,
                                                              final String uri) {
      return new InitializeSharesMetadataIxData(discriminator, name, name.getBytes(UTF_8), symbol, symbol.getBytes(UTF_8), uri, uri.getBytes(UTF_8));
    }

    public static InitializeSharesMetadataIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var name = Borsh.string(_data, i);
      i += (Integer.BYTES + getInt32LE(_data, i));
      final var symbol = Borsh.string(_data, i);
      i += (Integer.BYTES + getInt32LE(_data, i));
      final var uri = Borsh.string(_data, i);
      return new InitializeSharesMetadataIxData(discriminator, name, name.getBytes(UTF_8), symbol, symbol.getBytes(UTF_8), uri, uri.getBytes(UTF_8));
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(_name, _data, i);
      i += Borsh.writeVector(_symbol, _data, i);
      i += Borsh.writeVector(_uri, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(_name) + Borsh.lenVector(_symbol) + Borsh.lenVector(_uri);
    }
  }

  public static final Discriminator UPDATE_SHARES_METADATA_DISCRIMINATOR = toDiscriminator(155, 34, 122, 165, 245, 137, 147, 107);

  public static Instruction updateSharesMetadata(final AccountMeta invokedKaminoVaultProgramMeta,
                                                 final PublicKey vaultAdminAuthorityKey,
                                                 final PublicKey vaultStateKey,
                                                 final PublicKey baseVaultAuthorityKey,
                                                 final PublicKey sharesMetadataKey,
                                                 final PublicKey metadataProgramKey,
                                                 final String name,
                                                 final String symbol,
                                                 final String uri) {
    final var keys = List.of(
      createWritableSigner(vaultAdminAuthorityKey),
      createRead(vaultStateKey),
      createRead(baseVaultAuthorityKey),
      createWrite(sharesMetadataKey),
      createRead(metadataProgramKey)
    );

    final byte[] _name = name.getBytes(UTF_8);
    final byte[] _symbol = symbol.getBytes(UTF_8);
    final byte[] _uri = uri.getBytes(UTF_8);
    final byte[] _data = new byte[20 + Borsh.lenVector(_name) + Borsh.lenVector(_symbol) + Borsh.lenVector(_uri)];
    int i = writeDiscriminator(UPDATE_SHARES_METADATA_DISCRIMINATOR, _data, 0);
    i += Borsh.writeVector(_name, _data, i);
    i += Borsh.writeVector(_symbol, _data, i);
    Borsh.writeVector(_uri, _data, i);

    return Instruction.createInstruction(invokedKaminoVaultProgramMeta, keys, _data);
  }

  public record UpdateSharesMetadataIxData(Discriminator discriminator,
                                           String name, byte[] _name,
                                           String symbol, byte[] _symbol,
                                           String uri, byte[] _uri) implements Borsh {  

    public static UpdateSharesMetadataIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateSharesMetadataIxData createRecord(final Discriminator discriminator,
                                                          final String name,
                                                          final String symbol,
                                                          final String uri) {
      return new UpdateSharesMetadataIxData(discriminator, name, name.getBytes(UTF_8), symbol, symbol.getBytes(UTF_8), uri, uri.getBytes(UTF_8));
    }

    public static UpdateSharesMetadataIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var name = Borsh.string(_data, i);
      i += (Integer.BYTES + getInt32LE(_data, i));
      final var symbol = Borsh.string(_data, i);
      i += (Integer.BYTES + getInt32LE(_data, i));
      final var uri = Borsh.string(_data, i);
      return new UpdateSharesMetadataIxData(discriminator, name, name.getBytes(UTF_8), symbol, symbol.getBytes(UTF_8), uri, uri.getBytes(UTF_8));
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(_name, _data, i);
      i += Borsh.writeVector(_symbol, _data, i);
      i += Borsh.writeVector(_uri, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(_name) + Borsh.lenVector(_symbol) + Borsh.lenVector(_uri);
    }
  }

  public static final Discriminator WITHDRAW_FROM_AVAILABLE_DISCRIMINATOR = toDiscriminator(19, 131, 112, 155, 170, 220, 34, 57);

  public static Instruction withdrawFromAvailable(final AccountMeta invokedKaminoVaultProgramMeta,
                                                  final PublicKey userKey,
                                                  final PublicKey vaultStateKey,
                                                  final PublicKey tokenVaultKey,
                                                  final PublicKey baseVaultAuthorityKey,
                                                  final PublicKey userTokenAtaKey,
                                                  final PublicKey tokenMintKey,
                                                  final PublicKey userSharesAtaKey,
                                                  final PublicKey sharesMintKey,
                                                  final PublicKey tokenProgramKey,
                                                  final PublicKey sharesTokenProgramKey,
                                                  final PublicKey klendProgramKey,
                                                  final PublicKey eventAuthorityKey,
                                                  final PublicKey programKey,
                                                  final long sharesAmount) {
    final var keys = List.of(
      createWritableSigner(userKey),
      createWrite(vaultStateKey),
      createWrite(tokenVaultKey),
      createRead(baseVaultAuthorityKey),
      createWrite(userTokenAtaKey),
      createWrite(tokenMintKey),
      createWrite(userSharesAtaKey),
      createWrite(sharesMintKey),
      createRead(tokenProgramKey),
      createRead(sharesTokenProgramKey),
      createRead(klendProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(WITHDRAW_FROM_AVAILABLE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, sharesAmount);

    return Instruction.createInstruction(invokedKaminoVaultProgramMeta, keys, _data);
  }

  public record WithdrawFromAvailableIxData(Discriminator discriminator, long sharesAmount) implements Borsh {  

    public static WithdrawFromAvailableIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static WithdrawFromAvailableIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var sharesAmount = getInt64LE(_data, i);
      return new WithdrawFromAvailableIxData(discriminator, sharesAmount);
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

  public static final Discriminator REMOVE_ALLOCATION_DISCRIMINATOR = toDiscriminator(32, 220, 211, 141, 209, 231, 73, 76);

  public static Instruction removeAllocation(final AccountMeta invokedKaminoVaultProgramMeta,
                                             final PublicKey vaultAdminAuthorityKey,
                                             final PublicKey vaultStateKey,
                                             final PublicKey reserveKey) {
    final var keys = List.of(
      createWritableSigner(vaultAdminAuthorityKey),
      createWrite(vaultStateKey),
      createRead(reserveKey)
    );

    return Instruction.createInstruction(invokedKaminoVaultProgramMeta, keys, REMOVE_ALLOCATION_DISCRIMINATOR);
  }

  private KaminoVaultProgram() {
  }
}
