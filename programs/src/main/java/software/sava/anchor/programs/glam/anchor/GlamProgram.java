package software.sava.anchor.programs.glam.anchor;

import java.lang.String;

import java.util.List;

import software.sava.anchor.programs.glam.anchor.types.FundModel;
import software.sava.anchor.programs.glam.anchor.types.ShareClassModel;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
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
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class GlamProgram {

  public static final Discriminator ADD_SHARE_CLASS_DISCRIMINATOR = toDiscriminator(34, 49, 47, 6, 204, 166, 51, 204);

  public static Instruction addShareClass(final AccountMeta invokedGlamProgramMeta,
                                          final SolanaAccounts solanaAccounts,
                                          final PublicKey shareClassMintKey,
                                          final PublicKey fundKey,
                                          final PublicKey openfundsKey,
                                          final PublicKey managerKey,
                                          final ShareClassModel shareClassMetadata) {
    final var keys = List.of(
      createWrite(shareClassMintKey),
      createWrite(fundKey),
      createWrite(openfundsKey),
      createWritableSigner(managerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.token2022Program())
    );

    final byte[] _data = new byte[8 + Borsh.len(shareClassMetadata)];
    int i = writeDiscriminator(ADD_SHARE_CLASS_DISCRIMINATOR, _data, 0);
    Borsh.write(shareClassMetadata, _data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record AddShareClassIxData(Discriminator discriminator, ShareClassModel shareClassMetadata) implements Borsh {

    public static AddShareClassIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var shareClassMetadata = ShareClassModel.read(_data, i);
      return new AddShareClassIxData(discriminator, shareClassMetadata);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(shareClassMetadata, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(shareClassMetadata);
    }
  }

  public static final Discriminator CLOSE_FUND_DISCRIMINATOR = toDiscriminator(230, 183, 3, 112, 236, 252, 5, 185);

  public static Instruction closeFund(final AccountMeta invokedGlamProgramMeta,
                                      final SolanaAccounts solanaAccounts,
                                      final PublicKey fundKey,
                                      final PublicKey openfundsKey,
                                      final PublicKey treasuryKey,
                                      final PublicKey managerKey) {
    final var keys = List.of(
      createWrite(fundKey),
      createWrite(openfundsKey),
      createWrite(treasuryKey),
      createWritableSigner(managerKey),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, CLOSE_FUND_DISCRIMINATOR);
  }

  public static final Discriminator CLOSE_SHARE_CLASS_DISCRIMINATOR = toDiscriminator(35, 248, 168, 150, 244, 251, 61, 91);

  public static Instruction closeShareClass(final AccountMeta invokedGlamProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey fundKey,
                                            final PublicKey shareClassKey,
                                            final PublicKey managerKey,
                                            final int shareClassId) {
    final var keys = List.of(
      createWrite(fundKey),
      createWrite(shareClassKey),
      createWritableSigner(managerKey),
      createRead(solanaAccounts.token2022Program())
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(CLOSE_SHARE_CLASS_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) shareClassId;

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record CloseShareClassIxData(Discriminator discriminator, int shareClassId) implements Borsh {

    public static final int BYTES = 9;

    public static CloseShareClassIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var shareClassId = _data[i] & 0xFF;
      return new CloseShareClassIxData(discriminator, shareClassId);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) shareClassId;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DEACTIVATE_STAKE_ACCOUNTS_DISCRIMINATOR = toDiscriminator(58, 18, 6, 22, 226, 216, 161, 193);

  public static Instruction deactivateStakeAccounts(final AccountMeta invokedGlamProgramMeta,
                                                    final SolanaAccounts solanaAccounts,
                                                    final PublicKey managerKey,
                                                    final PublicKey fundKey,
                                                    final PublicKey treasuryKey) {
    final var keys = List.of(
      createWritableSigner(managerKey),
      createRead(fundKey),
      createWrite(treasuryKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeProgram())
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, DEACTIVATE_STAKE_ACCOUNTS_DISCRIMINATOR);
  }

  public static final Discriminator DRIFT_CLOSE_DISCRIMINATOR = toDiscriminator(23, 133, 219, 157, 137, 34, 93, 58);

  public static Instruction driftClose(final AccountMeta invokedGlamProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey fundKey,
                                       final PublicKey treasuryKey,
                                       final PublicKey userStatsKey,
                                       final PublicKey userKey,
                                       final PublicKey stateKey,
                                       final PublicKey managerKey,
                                       final PublicKey driftProgramKey) {
    final var keys = List.of(
      createRead(fundKey),
      createRead(treasuryKey),
      createWrite(userStatsKey),
      createWrite(userKey),
      createWrite(stateKey),
      createWritableSigner(managerKey),
      createRead(driftProgramKey),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, DRIFT_CLOSE_DISCRIMINATOR);
  }

  public static final Discriminator DRIFT_DEPOSIT_DISCRIMINATOR = toDiscriminator(252, 63, 250, 201, 98, 55, 130, 12);

  public static Instruction driftDeposit(final AccountMeta invokedGlamProgramMeta,
                                         final SolanaAccounts solanaAccounts,
                                         final PublicKey fundKey,
                                         final PublicKey treasuryKey,
                                         final PublicKey userStatsKey,
                                         final PublicKey userKey,
                                         final PublicKey stateKey,
                                         final PublicKey treasuryAtaKey,
                                         final PublicKey driftAtaKey,
                                         final PublicKey managerKey,
                                         final PublicKey driftProgramKey,
                                         final long amount) {
    final var keys = List.of(
      createRead(fundKey),
      createRead(treasuryKey),
      createWrite(userStatsKey),
      createWrite(userKey),
      createWrite(stateKey),
      createWrite(treasuryAtaKey),
      createWrite(driftAtaKey),
      createWritableSigner(managerKey),
      createRead(driftProgramKey),
      createRead(solanaAccounts.tokenProgram())
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(DRIFT_DEPOSIT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record DriftDepositIxData(Discriminator discriminator, long amount) implements Borsh {

    public static final int BYTES = 16;

    public static DriftDepositIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new DriftDepositIxData(discriminator, amount);
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

  public static final Discriminator DRIFT_INITIALIZE_DISCRIMINATOR = toDiscriminator(21, 21, 69, 55, 41, 129, 44, 198);

  public static Instruction driftInitialize(final AccountMeta invokedGlamProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey fundKey,
                                            final PublicKey treasuryKey,
                                            final PublicKey userStatsKey,
                                            final PublicKey userKey,
                                            final PublicKey stateKey,
                                            final PublicKey managerKey,
                                            final PublicKey driftProgramKey,
                                            final PublicKey trader) {
    final var keys = List.of(
      createRead(fundKey),
      createRead(treasuryKey),
      createWrite(userStatsKey),
      createWrite(userKey),
      createWrite(stateKey),
      createWritableSigner(managerKey),
      createRead(driftProgramKey),
      createRead(solanaAccounts.rentSysVar()),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[
        8
        + (trader == null ? 1 : 33)
    ];
    int i = writeDiscriminator(DRIFT_INITIALIZE_DISCRIMINATOR, _data, 0);
    Borsh.writeOptional(trader, _data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record DriftInitializeIxData(Discriminator discriminator, PublicKey trader) implements Borsh {

    public static DriftInitializeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var trader = _data[i++] == 0 ? null : readPubKey(_data, i);
      return new DriftInitializeIxData(discriminator, trader);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeOptional(trader, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + (trader == null ? 1 : (1 + 32));
    }
  }

  public static final Discriminator DRIFT_UPDATE_DELEGATED_TRADER_DISCRIMINATOR = toDiscriminator(98, 66, 206, 146, 109, 215, 206, 57);

  public static Instruction driftUpdateDelegatedTrader(final AccountMeta invokedGlamProgramMeta,
                                                       final PublicKey fundKey,
                                                       final PublicKey treasuryKey,
                                                       final PublicKey userKey,
                                                       final PublicKey managerKey,
                                                       final PublicKey driftProgramKey,
                                                       final PublicKey trader) {
    final var keys = List.of(
      createRead(fundKey),
      createRead(treasuryKey),
      createWrite(userKey),
      createWritableSigner(managerKey),
      createRead(driftProgramKey)
    );

    final byte[] _data = new byte[
        8
        + (trader == null ? 1 : 33)
    ];
    int i = writeDiscriminator(DRIFT_UPDATE_DELEGATED_TRADER_DISCRIMINATOR, _data, 0);
    Borsh.writeOptional(trader, _data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record DriftUpdateDelegatedTraderIxData(Discriminator discriminator, PublicKey trader) implements Borsh {

    public static DriftUpdateDelegatedTraderIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var trader = _data[i++] == 0 ? null : readPubKey(_data, i);
      return new DriftUpdateDelegatedTraderIxData(discriminator, trader);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeOptional(trader, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + (trader == null ? 1 : (1 + 32));
    }
  }

  public static final Discriminator DRIFT_WITHDRAW_DISCRIMINATOR = toDiscriminator(86, 59, 186, 123, 183, 181, 234, 137);

  public static Instruction driftWithdraw(final AccountMeta invokedGlamProgramMeta,
                                          final SolanaAccounts solanaAccounts,
                                          final PublicKey fundKey,
                                          final PublicKey treasuryKey,
                                          final PublicKey userStatsKey,
                                          final PublicKey userKey,
                                          final PublicKey stateKey,
                                          final PublicKey driftSignerKey,
                                          final PublicKey treasuryAtaKey,
                                          final PublicKey driftAtaKey,
                                          final PublicKey managerKey,
                                          final PublicKey driftProgramKey,
                                          final long amount) {
    final var keys = List.of(
      createRead(fundKey),
      createRead(treasuryKey),
      createWrite(userStatsKey),
      createWrite(userKey),
      createWrite(stateKey),
      createRead(driftSignerKey),
      createWrite(treasuryAtaKey),
      createWrite(driftAtaKey),
      createWritableSigner(managerKey),
      createRead(driftProgramKey),
      createRead(solanaAccounts.tokenProgram())
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(DRIFT_WITHDRAW_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record DriftWithdrawIxData(Discriminator discriminator, long amount) implements Borsh {

    public static final int BYTES = 16;

    public static DriftWithdrawIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new DriftWithdrawIxData(discriminator, amount);
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

  public static final Discriminator INITIALIZE_AND_DELEGATE_STAKE_DISCRIMINATOR = toDiscriminator(71, 101, 230, 157, 50, 23, 47, 1);

  public static Instruction initializeAndDelegateStake(final AccountMeta invokedGlamProgramMeta,
                                                       final SolanaAccounts solanaAccounts,
                                                       final PublicKey managerKey,
                                                       final PublicKey fundKey,
                                                       final PublicKey treasuryKey,
                                                       final PublicKey treasuryStakeAccountKey,
                                                       final PublicKey voteKey,
                                                       final PublicKey stakeConfigKey,
                                                       final long lamports,
                                                       final String stakeAccountId,
                                                       final int stakeAccountBump) {
    final var keys = List.of(
      createWritableSigner(managerKey),
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(treasuryStakeAccountKey),
      createRead(voteKey),
      createRead(stakeConfigKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.rentSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar()),
      createRead(solanaAccounts.stakeProgram()),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _stakeAccountId = stakeAccountId.getBytes(UTF_8);
    final byte[] _data = new byte[21 + Borsh.lenVector(_stakeAccountId)];
    int i = writeDiscriminator(INITIALIZE_AND_DELEGATE_STAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);
    i += 8;
    i += Borsh.writeVector(_stakeAccountId, _data, i);
    _data[i] = (byte) stakeAccountBump;

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record InitializeAndDelegateStakeIxData(Discriminator discriminator,
                                                 long lamports,
                                                 String stakeAccountId, byte[] _stakeAccountId,
                                                 int stakeAccountBump) implements Borsh {

    public static InitializeAndDelegateStakeIxData createRecord(final Discriminator discriminator,
                                                                final long lamports,
                                                                final String stakeAccountId,
                                                                final int stakeAccountBump) {
      return new InitializeAndDelegateStakeIxData(discriminator, lamports, stakeAccountId, stakeAccountId.getBytes(UTF_8), stakeAccountBump);
    }

    public static InitializeAndDelegateStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      i += 8;
      final var stakeAccountId = Borsh.string(_data, i);
      i += (Integer.BYTES + getInt32LE(_data, i));
      final var stakeAccountBump = _data[i] & 0xFF;
      return new InitializeAndDelegateStakeIxData(discriminator, lamports, stakeAccountId, stakeAccountId.getBytes(UTF_8), stakeAccountBump);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lamports);
      i += 8;
      i += Borsh.writeVector(_stakeAccountId, _data, i);
      _data[i] = (byte) stakeAccountBump;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + Borsh.lenVector(_stakeAccountId) + 1;
    }
  }

  public static final Discriminator INITIALIZE_FUND_DISCRIMINATOR = toDiscriminator(212, 42, 24, 245, 146, 141, 78, 198);

  public static Instruction initializeFund(final AccountMeta invokedGlamProgramMeta,
                                           final SolanaAccounts solanaAccounts,
                                           final PublicKey fundKey,
                                           final PublicKey openfundsKey,
                                           final PublicKey treasuryKey,
                                           final PublicKey managerKey,
                                           final FundModel fund) {
    final var keys = List.of(
      createWrite(fundKey),
      createWrite(openfundsKey),
      createWrite(treasuryKey),
      createWritableSigner(managerKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(fund)];
    int i = writeDiscriminator(INITIALIZE_FUND_DISCRIMINATOR, _data, 0);
    Borsh.write(fund, _data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record InitializeFundIxData(Discriminator discriminator, FundModel fund) implements Borsh {

    public static InitializeFundIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var fund = FundModel.read(_data, i);
      return new InitializeFundIxData(discriminator, fund);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(fund, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(fund);
    }
  }

  public static final Discriminator JUPITER_SWAP_DISCRIMINATOR = toDiscriminator(116, 207, 0, 196, 252, 120, 243, 18);

  public static Instruction jupiterSwap(final AccountMeta invokedGlamProgramMeta,
                                        final SolanaAccounts solanaAccounts,
                                        final PublicKey fundKey,
                                        final PublicKey treasuryKey,
                                        // input_treasury_ata to input_signer_ata
                                        final PublicKey inputTreasuryAtaKey,
                                        final PublicKey inputSignerAtaKey,
                                        final PublicKey outputSignerAtaKey,
                                        final PublicKey outputTreasuryAtaKey,
                                        final PublicKey inputMintKey,
                                        final PublicKey outputMintKey,
                                        final PublicKey signerKey,
                                        final PublicKey jupiterProgramKey,
                                        final long amount,
                                        final byte[] data) {
    final var keys = List.of(
      createWrite(fundKey),
      createWrite(treasuryKey),
      createWrite(inputTreasuryAtaKey),
      createWrite(inputSignerAtaKey),
      createWrite(outputSignerAtaKey),
      createWrite(outputTreasuryAtaKey),
      createRead(inputMintKey),
      createRead(outputMintKey),
      createWritableSigner(signerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(jupiterProgramKey),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.token2022Program())
    );

    final byte[] _data = new byte[20 + Borsh.lenVector(data)];
    int i = writeDiscriminator(JUPITER_SWAP_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    Borsh.writeVector(data, _data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record JupiterSwapIxData(Discriminator discriminator, long amount, byte[] data) implements Borsh {

    public static JupiterSwapIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      i += 8;
      final byte[] data = Borsh.readbyteVector(_data, i);
      return new JupiterSwapIxData(discriminator, amount, data);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      i += Borsh.writeVector(data, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + Borsh.lenVector(data);
    }
  }

  public static final Discriminator MARINADE_CLAIM_TICKETS_DISCRIMINATOR = toDiscriminator(14, 146, 182, 30, 205, 47, 134, 189);

  public static Instruction marinadeClaimTickets(final AccountMeta invokedGlamProgramMeta,
                                                 final SolanaAccounts solanaAccounts,
                                                 final PublicKey managerKey,
                                                 final PublicKey fundKey,
                                                 final PublicKey treasuryKey,
                                                 final PublicKey marinadeStateKey,
                                                 final PublicKey reservePdaKey,
                                                 final PublicKey marinadeProgramKey) {
    final var keys = List.of(
      createWritableSigner(managerKey),
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(marinadeStateKey),
      createWrite(reservePdaKey),
      createRead(solanaAccounts.rentSysVar()),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(marinadeProgramKey)
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, MARINADE_CLAIM_TICKETS_DISCRIMINATOR);
  }

  public static final Discriminator MARINADE_DELAYED_UNSTAKE_DISCRIMINATOR = toDiscriminator(117, 66, 3, 222, 230, 94, 129, 95);

  public static Instruction marinadeDelayedUnstake(final AccountMeta invokedGlamProgramMeta,
                                                   final SolanaAccounts solanaAccounts,
                                                   final PublicKey managerKey,
                                                   final PublicKey fundKey,
                                                   final PublicKey treasuryKey,
                                                   final PublicKey ticketKey,
                                                   final PublicKey msolMintKey,
                                                   final PublicKey burnMsolFromKey,
                                                   final PublicKey marinadeStateKey,
                                                   final PublicKey reservePdaKey,
                                                   final PublicKey marinadeProgramKey,
                                                   final long msolAmount,
                                                   final String ticketId,
                                                   final int bump) {
    final var keys = List.of(
      createWritableSigner(managerKey),
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(ticketKey),
      createWrite(msolMintKey),
      createWrite(burnMsolFromKey),
      createWrite(marinadeStateKey),
      createWrite(reservePdaKey),
      createRead(solanaAccounts.rentSysVar()),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(marinadeProgramKey)
    );

    final byte[] _ticketId = ticketId.getBytes(UTF_8);
    final byte[] _data = new byte[21 + Borsh.lenVector(_ticketId)];
    int i = writeDiscriminator(MARINADE_DELAYED_UNSTAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, msolAmount);
    i += 8;
    i += Borsh.writeVector(_ticketId, _data, i);
    _data[i] = (byte) bump;

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record MarinadeDelayedUnstakeIxData(Discriminator discriminator,
                                             long msolAmount,
                                             String ticketId, byte[] _ticketId,
                                             int bump) implements Borsh {

    public static MarinadeDelayedUnstakeIxData createRecord(final Discriminator discriminator,
                                                            final long msolAmount,
                                                            final String ticketId,
                                                            final int bump) {
      return new MarinadeDelayedUnstakeIxData(discriminator, msolAmount, ticketId, ticketId.getBytes(UTF_8), bump);
    }

    public static MarinadeDelayedUnstakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var msolAmount = getInt64LE(_data, i);
      i += 8;
      final var ticketId = Borsh.string(_data, i);
      i += (Integer.BYTES + getInt32LE(_data, i));
      final var bump = _data[i] & 0xFF;
      return new MarinadeDelayedUnstakeIxData(discriminator, msolAmount, ticketId, ticketId.getBytes(UTF_8), bump);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, msolAmount);
      i += 8;
      i += Borsh.writeVector(_ticketId, _data, i);
      _data[i] = (byte) bump;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + Borsh.lenVector(_ticketId) + 1;
    }
  }

  public static final Discriminator MARINADE_DEPOSIT_SOL_DISCRIMINATOR = toDiscriminator(64, 140, 200, 40, 56, 218, 181, 68);

  public static Instruction marinadeDepositSol(final AccountMeta invokedGlamProgramMeta,
                                               final SolanaAccounts solanaAccounts,
                                               final PublicKey managerKey,
                                               final PublicKey fundKey,
                                               final PublicKey treasuryKey,
                                               final PublicKey marinadeStateKey,
                                               final PublicKey reservePdaKey,
                                               final PublicKey msolMintKey,
                                               final PublicKey msolMintAuthorityKey,
                                               final PublicKey liqPoolMsolLegKey,
                                               final PublicKey liqPoolMsolLegAuthorityKey,
                                               final PublicKey liqPoolSolLegPdaKey,
                                               final PublicKey mintToKey,
                                               final PublicKey marinadeProgramKey,
                                               final long lamports) {
    final var keys = List.of(
      createWritableSigner(managerKey),
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(marinadeStateKey),
      createWrite(reservePdaKey),
      createWrite(msolMintKey),
      createWrite(msolMintAuthorityKey),
      createWrite(liqPoolMsolLegKey),
      createWrite(liqPoolMsolLegAuthorityKey),
      createWrite(liqPoolSolLegPdaKey),
      createWrite(mintToKey),
      createRead(marinadeProgramKey),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.tokenProgram())
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(MARINADE_DEPOSIT_SOL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record MarinadeDepositSolIxData(Discriminator discriminator, long lamports) implements Borsh {

    public static final int BYTES = 16;

    public static MarinadeDepositSolIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      return new MarinadeDepositSolIxData(discriminator, lamports);
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

  public static final Discriminator MARINADE_DEPOSIT_STAKE_DISCRIMINATOR = toDiscriminator(69, 207, 194, 211, 186, 55, 199, 130);

  public static Instruction marinadeDepositStake(final AccountMeta invokedGlamProgramMeta,
                                                 final SolanaAccounts solanaAccounts,
                                                 final PublicKey managerKey,
                                                 final PublicKey fundKey,
                                                 final PublicKey treasuryKey,
                                                 final PublicKey marinadeStateKey,
                                                 final PublicKey validatorListKey,
                                                 final PublicKey stakeListKey,
                                                 final PublicKey treasuryStakeAccountKey,
                                                 final PublicKey duplicationFlagKey,
                                                 final PublicKey msolMintKey,
                                                 final PublicKey msolMintAuthorityKey,
                                                 final PublicKey mintToKey,
                                                 final PublicKey marinadeProgramKey,
                                                 final int validatorIdx) {
    final var keys = List.of(
      createWritableSigner(managerKey),
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(marinadeStateKey),
      createWrite(validatorListKey),
      createWrite(stakeListKey),
      createWrite(treasuryStakeAccountKey),
      createWrite(duplicationFlagKey),
      createWrite(msolMintKey),
      createRead(msolMintAuthorityKey),
      createWrite(mintToKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.rentSysVar()),
      createRead(marinadeProgramKey),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.stakeProgram())
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(MARINADE_DEPOSIT_STAKE_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, validatorIdx);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record MarinadeDepositStakeIxData(Discriminator discriminator, int validatorIdx) implements Borsh {

    public static final int BYTES = 12;

    public static MarinadeDepositStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var validatorIdx = getInt32LE(_data, i);
      return new MarinadeDepositStakeIxData(discriminator, validatorIdx);
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
                                                  final PublicKey managerKey,
                                                  final PublicKey fundKey,
                                                  final PublicKey treasuryKey,
                                                  final PublicKey marinadeStateKey,
                                                  final PublicKey msolMintKey,
                                                  final PublicKey liqPoolSolLegPdaKey,
                                                  final PublicKey liqPoolMsolLegKey,
                                                  final PublicKey treasuryMsolAccountKey,
                                                  final PublicKey getMsolFromKey,
                                                  final PublicKey getMsolFromAuthorityKey,
                                                  final PublicKey marinadeProgramKey,
                                                  final long msolAmount) {
    final var keys = List.of(
      createReadOnlySigner(managerKey),
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(marinadeStateKey),
      createWrite(msolMintKey),
      createWrite(liqPoolSolLegPdaKey),
      createWrite(liqPoolMsolLegKey),
      createWrite(treasuryMsolAccountKey),
      createWrite(getMsolFromKey),
      createWrite(getMsolFromAuthorityKey),
      createRead(marinadeProgramKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(MARINADE_LIQUID_UNSTAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, msolAmount);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record MarinadeLiquidUnstakeIxData(Discriminator discriminator, long msolAmount) implements Borsh {

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

  public static final Discriminator MERGE_STAKE_ACCOUNTS_DISCRIMINATOR = toDiscriminator(173, 206, 10, 246, 109, 50, 244, 110);

  public static Instruction mergeStakeAccounts(final AccountMeta invokedGlamProgramMeta,
                                               final SolanaAccounts solanaAccounts,
                                               final PublicKey managerKey,
                                               final PublicKey fundKey,
                                               final PublicKey treasuryKey,
                                               final PublicKey toStakeKey,
                                               final PublicKey fromStakeKey) {
    final var keys = List.of(
      createWritableSigner(managerKey),
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(toStakeKey),
      createWrite(fromStakeKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar()),
      createRead(solanaAccounts.stakeProgram()),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, MERGE_STAKE_ACCOUNTS_DISCRIMINATOR);
  }

  public static final Discriminator REDEEM_DISCRIMINATOR = toDiscriminator(184, 12, 86, 149, 70, 196, 97, 225);

  public static Instruction redeem(final AccountMeta invokedGlamProgramMeta,
                                   final SolanaAccounts solanaAccounts,
                                   final PublicKey fundKey,
                                   final PublicKey shareClassKey,
                                   final PublicKey signerShareAtaKey,
                                   final PublicKey signerKey,
                                   final PublicKey treasuryKey,
                                   final long amount,
                                   final boolean inKind,
                                   final boolean skipState) {
    final var keys = List.of(
      createRead(fundKey),
      createWrite(shareClassKey),
      createWrite(signerShareAtaKey),
      createWritableSigner(signerKey),
      createWrite(treasuryKey),
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

  public static final Discriminator SPLIT_STAKE_ACCOUNT_DISCRIMINATOR = toDiscriminator(130, 42, 33, 89, 117, 77, 105, 194);

  public static Instruction splitStakeAccount(final AccountMeta invokedGlamProgramMeta,
                                              final SolanaAccounts solanaAccounts,
                                              final PublicKey managerKey,
                                              final PublicKey fundKey,
                                              final PublicKey treasuryKey,
                                              final PublicKey existingStakeKey,
                                              final PublicKey newStakeKey,
                                              final long lamports,
                                              final String newStakeAccountId,
                                              final int newStakeAccountBump) {
    final var keys = List.of(
      createWritableSigner(managerKey),
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(existingStakeKey),
      createWrite(newStakeKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeProgram()),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _newStakeAccountId = newStakeAccountId.getBytes(UTF_8);
    final byte[] _data = new byte[21 + Borsh.lenVector(_newStakeAccountId)];
    int i = writeDiscriminator(SPLIT_STAKE_ACCOUNT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);
    i += 8;
    i += Borsh.writeVector(_newStakeAccountId, _data, i);
    _data[i] = (byte) newStakeAccountBump;

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record SplitStakeAccountIxData(Discriminator discriminator,
                                        long lamports,
                                        String newStakeAccountId, byte[] _newStakeAccountId,
                                        int newStakeAccountBump) implements Borsh {

    public static SplitStakeAccountIxData createRecord(final Discriminator discriminator,
                                                       final long lamports,
                                                       final String newStakeAccountId,
                                                       final int newStakeAccountBump) {
      return new SplitStakeAccountIxData(discriminator, lamports, newStakeAccountId, newStakeAccountId.getBytes(UTF_8), newStakeAccountBump);
    }

    public static SplitStakeAccountIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      i += 8;
      final var newStakeAccountId = Borsh.string(_data, i);
      i += (Integer.BYTES + getInt32LE(_data, i));
      final var newStakeAccountBump = _data[i] & 0xFF;
      return new SplitStakeAccountIxData(discriminator, lamports, newStakeAccountId, newStakeAccountId.getBytes(UTF_8), newStakeAccountBump);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lamports);
      i += 8;
      i += Borsh.writeVector(_newStakeAccountId, _data, i);
      _data[i] = (byte) newStakeAccountBump;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + Borsh.lenVector(_newStakeAccountId) + 1;
    }
  }

  public static final Discriminator STAKE_POOL_DEPOSIT_SOL_DISCRIMINATOR = toDiscriminator(147, 187, 91, 151, 158, 187, 247, 79);

  public static Instruction stakePoolDepositSol(final AccountMeta invokedGlamProgramMeta,
                                                final SolanaAccounts solanaAccounts,
                                                final PublicKey managerKey,
                                                final PublicKey fundKey,
                                                final PublicKey treasuryKey,
                                                final PublicKey stakePoolProgramKey,
                                                final PublicKey stakePoolKey,
                                                final PublicKey withdrawAuthorityKey,
                                                final PublicKey reserveStakeKey,
                                                final PublicKey poolMintKey,
                                                final PublicKey feeAccountKey,
                                                final PublicKey mintToKey,
                                                final PublicKey tokenProgramKey,
                                                final long lamports) {
    final var keys = List.of(
      createWritableSigner(managerKey),
      createRead(fundKey),
      createWrite(treasuryKey),
      createRead(stakePoolProgramKey),
      createWrite(stakePoolKey),
      createRead(withdrawAuthorityKey),
      createWrite(reserveStakeKey),
      createWrite(poolMintKey),
      createWrite(feeAccountKey),
      createWrite(mintToKey),
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
                                                  final PublicKey managerKey,
                                                  final PublicKey fundKey,
                                                  final PublicKey treasuryKey,
                                                  final PublicKey treasuryStakeAccountKey,
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
      createWritableSigner(managerKey),
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(treasuryStakeAccountKey),
      createWrite(mintToKey),
      createWrite(poolMintKey),
      createWrite(feeAccountKey),
      createWrite(stakePoolKey),
      createRead(depositAuthorityKey),
      createRead(withdrawAuthorityKey),
      createWrite(validatorListKey),
      createWrite(validatorStakeAccountKey),
      createWrite(reserveStakeAccountKey),
      createRead(stakePoolProgramKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar()),
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
                                                 final PublicKey managerKey,
                                                 final PublicKey fundKey,
                                                 final PublicKey treasuryKey,
                                                 final PublicKey stakePoolProgramKey,
                                                 final PublicKey stakePoolKey,
                                                 final PublicKey withdrawAuthorityKey,
                                                 final PublicKey reserveStakeKey,
                                                 final PublicKey poolMintKey,
                                                 final PublicKey feeAccountKey,
                                                 final PublicKey poolTokenAtaKey,
                                                 final PublicKey tokenProgramKey,
                                                 final long poolTokenAmount) {
    final var keys = List.of(
      createWritableSigner(managerKey),
      createRead(fundKey),
      createWrite(treasuryKey),
      createRead(stakePoolProgramKey),
      createWrite(stakePoolKey),
      createRead(withdrawAuthorityKey),
      createWrite(reserveStakeKey),
      createWrite(poolMintKey),
      createWrite(feeAccountKey),
      createWrite(poolTokenAtaKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar()),
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
                                                   final PublicKey managerKey,
                                                   final PublicKey fundKey,
                                                   final PublicKey treasuryKey,
                                                   final PublicKey treasuryStakeAccountKey,
                                                   final PublicKey poolMintKey,
                                                   final PublicKey feeAccountKey,
                                                   final PublicKey stakePoolKey,
                                                   final PublicKey withdrawAuthorityKey,
                                                   final PublicKey validatorListKey,
                                                   final PublicKey validatorStakeAccountKey,
                                                   final PublicKey poolTokenAtaKey,
                                                   final PublicKey stakePoolProgramKey,
                                                   final PublicKey tokenProgramKey,
                                                   final long poolTokenAmount,
                                                   final String stakeAccountId,
                                                   final int stakeAccountBump) {
    final var keys = List.of(
      createWritableSigner(managerKey),
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(treasuryStakeAccountKey),
      createWrite(poolMintKey),
      createWrite(feeAccountKey),
      createWrite(stakePoolKey),
      createRead(withdrawAuthorityKey),
      createWrite(validatorListKey),
      createWrite(validatorStakeAccountKey),
      createWrite(poolTokenAtaKey),
      createRead(stakePoolProgramKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.systemProgram()),
      createRead(tokenProgramKey),
      createRead(solanaAccounts.stakeProgram())
    );

    final byte[] _stakeAccountId = stakeAccountId.getBytes(UTF_8);
    final byte[] _data = new byte[21 + Borsh.lenVector(_stakeAccountId)];
    int i = writeDiscriminator(STAKE_POOL_WITHDRAW_STAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, poolTokenAmount);
    i += 8;
    i += Borsh.writeVector(_stakeAccountId, _data, i);
    _data[i] = (byte) stakeAccountBump;

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record StakePoolWithdrawStakeIxData(Discriminator discriminator,
                                             long poolTokenAmount,
                                             String stakeAccountId, byte[] _stakeAccountId,
                                             int stakeAccountBump) implements Borsh {

    public static StakePoolWithdrawStakeIxData createRecord(final Discriminator discriminator,
                                                            final long poolTokenAmount,
                                                            final String stakeAccountId,
                                                            final int stakeAccountBump) {
      return new StakePoolWithdrawStakeIxData(discriminator, poolTokenAmount, stakeAccountId, stakeAccountId.getBytes(UTF_8), stakeAccountBump);
    }

    public static StakePoolWithdrawStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var poolTokenAmount = getInt64LE(_data, i);
      i += 8;
      final var stakeAccountId = Borsh.string(_data, i);
      i += (Integer.BYTES + getInt32LE(_data, i));
      final var stakeAccountBump = _data[i] & 0xFF;
      return new StakePoolWithdrawStakeIxData(discriminator, poolTokenAmount, stakeAccountId, stakeAccountId.getBytes(UTF_8), stakeAccountBump);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, poolTokenAmount);
      i += 8;
      i += Borsh.writeVector(_stakeAccountId, _data, i);
      _data[i] = (byte) stakeAccountBump;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + Borsh.lenVector(_stakeAccountId) + 1;
    }
  }

  public static final Discriminator SUBSCRIBE_DISCRIMINATOR = toDiscriminator(254, 28, 191, 138, 156, 179, 183, 53);

  public static Instruction subscribe(final AccountMeta invokedGlamProgramMeta,
                                      final SolanaAccounts solanaAccounts,
                                      final PublicKey fundKey,
                                      final PublicKey treasuryKey,
                                      final PublicKey shareClassKey,
                                      final PublicKey signerShareAtaKey,
                                      final PublicKey assetKey,
                                      final PublicKey treasuryAtaKey,
                                      final PublicKey signerAssetAtaKey,
                                      final PublicKey signerKey,
                                      final long amount,
                                      final boolean skipState) {
    final var keys = List.of(
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(shareClassKey),
      createWrite(signerShareAtaKey),
      createRead(assetKey),
      createWrite(treasuryAtaKey),
      createWrite(signerAssetAtaKey),
      createWritableSigner(signerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.token2022Program())
    );

    final byte[] _data = new byte[17];
    int i = writeDiscriminator(SUBSCRIBE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) (skipState ? 1 : 0);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record SubscribeIxData(Discriminator discriminator, long amount, boolean skipState) implements Borsh {

    public static final int BYTES = 17;

    public static SubscribeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var skipState = _data[i] == 1;
      return new SubscribeIxData(discriminator, amount, skipState);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
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

  public static final Discriminator UPDATE_FUND_DISCRIMINATOR = toDiscriminator(132, 171, 13, 83, 34, 122, 82, 155);

  public static Instruction updateFund(final AccountMeta invokedGlamProgramMeta,
                                       final PublicKey fundKey,
                                       final PublicKey signerKey,
                                       final FundModel fund) {
    final var keys = List.of(
      createWrite(fundKey),
      createWritableSigner(signerKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(fund)];
    int i = writeDiscriminator(UPDATE_FUND_DISCRIMINATOR, _data, 0);
    Borsh.write(fund, _data, i);

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public record UpdateFundIxData(Discriminator discriminator, FundModel fund) implements Borsh {

    public static UpdateFundIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var fund = FundModel.read(_data, i);
      return new UpdateFundIxData(discriminator, fund);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(fund, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(fund);
    }
  }

  public static final Discriminator WITHDRAW_FROM_STAKE_ACCOUNTS_DISCRIMINATOR = toDiscriminator(93, 209, 100, 231, 169, 160, 192, 197);

  public static Instruction withdrawFromStakeAccounts(final AccountMeta invokedGlamProgramMeta,
                                                      final SolanaAccounts solanaAccounts,
                                                      final PublicKey managerKey,
                                                      final PublicKey fundKey,
                                                      final PublicKey treasuryKey) {
    final var keys = List.of(
      createWritableSigner(managerKey),
      createRead(fundKey),
      createWrite(treasuryKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar()),
      createRead(solanaAccounts.stakeProgram())
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, WITHDRAW_FROM_STAKE_ACCOUNTS_DISCRIMINATOR);
  }

  public static final Discriminator WSOL_UNWRAP_DISCRIMINATOR = toDiscriminator(123, 189, 16, 96, 233, 186, 54, 215);

  public static Instruction wsolUnwrap(final AccountMeta invokedGlamProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey fundKey,
                                       final PublicKey treasuryKey,
                                       final PublicKey treasuryWsolAtaKey,
                                       final PublicKey signerKey) {
    final var keys = List.of(
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(treasuryWsolAtaKey),
      createRead(solanaAccounts.wrappedSolTokenMint()),
      createWritableSigner(signerKey),
      createRead(solanaAccounts.tokenProgram())
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, WSOL_UNWRAP_DISCRIMINATOR);
  }

  public static final Discriminator WSOL_WRAP_DISCRIMINATOR = toDiscriminator(26, 2, 139, 159, 239, 195, 193, 9);

  public static Instruction wsolWrap(final AccountMeta invokedGlamProgramMeta,
                                     final SolanaAccounts solanaAccounts,
                                     final PublicKey fundKey,
                                     final PublicKey treasuryKey,
                                     final PublicKey treasuryWsolAtaKey,
                                     final PublicKey signerKey,
                                     final long lamports) {
    final var keys = List.of(
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(treasuryWsolAtaKey),
      createRead(solanaAccounts.wrappedSolTokenMint()),
      createWritableSigner(signerKey),
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
