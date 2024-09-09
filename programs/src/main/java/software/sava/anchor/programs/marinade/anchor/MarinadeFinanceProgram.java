package software.sava.anchor.programs.marinade.anchor;

import java.util.List;

import software.sava.anchor.programs.marinade.anchor.types.ChangeAuthorityData;
import software.sava.anchor.programs.marinade.anchor.types.ConfigLpParams;
import software.sava.anchor.programs.marinade.anchor.types.ConfigMarinadeParams;
import software.sava.anchor.programs.marinade.anchor.types.InitializeData;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

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

public final class MarinadeFinanceProgram {

  public static final Discriminator INITIALIZE_DISCRIMINATOR = toDiscriminator(175, 175, 109, 31, 13, 152, 155, 237);

  public static Instruction initialize(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                       final PublicKey stateKey,
                                       final PublicKey reservePdaKey,
                                       final PublicKey stakeListKey,
                                       final PublicKey validatorListKey,
                                       final PublicKey msolMintKey,
                                       final PublicKey operationalSolAccountKey,
                                       final PublicKey liqPoolKey,
                                       final PublicKey treasuryMsolAccountKey,
                                       final PublicKey clockKey,
                                       final PublicKey rentKey,
                                       final InitializeData data) {
    final var keys = List.of(
      createWrite(stateKey),
      createRead(reservePdaKey),
      createWrite(stakeListKey),
      createWrite(validatorListKey),
      createRead(msolMintKey),
      createRead(operationalSolAccountKey),
      createRead(liqPoolKey),
      createRead(treasuryMsolAccountKey),
      createRead(clockKey),
      createRead(rentKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(data)];
    int i = writeDiscriminator(INITIALIZE_DISCRIMINATOR, _data, 0);
    Borsh.write(data, _data, i);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record InitializeData(InitializeData data) implements Borsh {

  public static final int BYTES = 144;

  public static InitializeData read(final byte[] _data, final int offset) {
    final var data = InitializeData.read(_data, offset);
    return new InitializeData(data);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(data, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator CHANGE_AUTHORITY_DISCRIMINATOR = toDiscriminator(50, 106, 66, 104, 99, 118, 145, 88);

  public static Instruction changeAuthority(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                            final PublicKey stateKey,
                                            final PublicKey adminAuthorityKey,
                                            final ChangeAuthorityData data) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(adminAuthorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(data)];
    int i = writeDiscriminator(CHANGE_AUTHORITY_DISCRIMINATOR, _data, 0);
    Borsh.write(data, _data, i);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record ChangeAuthorityData(ChangeAuthorityData data) implements Borsh {

  public static ChangeAuthorityData read(final byte[] _data, final int offset) {
    final var data = ChangeAuthorityData.read(_data, offset);
    return new ChangeAuthorityData(data);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(data, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(data);
  }
}

  public static final Discriminator ADD_VALIDATOR_DISCRIMINATOR = toDiscriminator(250, 113, 53, 54, 141, 117, 215, 185);

  public static Instruction addValidator(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                         final PublicKey stateKey,
                                         final PublicKey managerAuthorityKey,
                                         final PublicKey validatorListKey,
                                         final PublicKey validatorVoteKey,
                                         // by initializing this account we mark the validator as added
                                         final PublicKey duplicationFlagKey,
                                         final PublicKey rentPayerKey,
                                         final PublicKey clockKey,
                                         final PublicKey rentKey,
                                         final PublicKey systemProgramKey,
                                         final int score) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(managerAuthorityKey),
      createWrite(validatorListKey),
      createRead(validatorVoteKey),
      createWrite(duplicationFlagKey),
      createWritableSigner(rentPayerKey),
      createRead(clockKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(ADD_VALIDATOR_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, score);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record AddValidatorData(int score) implements Borsh {

  public static final int BYTES = 4;

  public static AddValidatorData read(final byte[] _data, final int offset) {
    final var score = getInt32LE(_data, offset);
    return new AddValidatorData(score);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, score);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator REMOVE_VALIDATOR_DISCRIMINATOR = toDiscriminator(25, 96, 211, 155, 161, 14, 168, 188);

  public static Instruction removeValidator(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                            final PublicKey stateKey,
                                            final PublicKey managerAuthorityKey,
                                            final PublicKey validatorListKey,
                                            final PublicKey duplicationFlagKey,
                                            final PublicKey operationalSolAccountKey,
                                            final int index,
                                            final PublicKey validatorVote) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(managerAuthorityKey),
      createWrite(validatorListKey),
      createWrite(duplicationFlagKey),
      createWrite(operationalSolAccountKey)
    );

    final byte[] _data = new byte[44];
    int i = writeDiscriminator(REMOVE_VALIDATOR_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, index);
    i += 4;
    validatorVote.write(_data, i);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record RemoveValidatorData(int index, PublicKey validatorVote) implements Borsh {

  public static final int BYTES = 36;

  public static RemoveValidatorData read(final byte[] _data, final int offset) {
    int i = offset;
    final var index = getInt32LE(_data, i);
    i += 4;
    final var validatorVote = readPubKey(_data, i);
    return new RemoveValidatorData(index, validatorVote);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, index);
    i += 4;
    validatorVote.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator SET_VALIDATOR_SCORE_DISCRIMINATOR = toDiscriminator(101, 41, 206, 33, 216, 111, 25, 78);

  public static Instruction setValidatorScore(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                              final PublicKey stateKey,
                                              final PublicKey managerAuthorityKey,
                                              final PublicKey validatorListKey,
                                              final int index,
                                              final PublicKey validatorVote,
                                              final int score) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(managerAuthorityKey),
      createWrite(validatorListKey)
    );

    final byte[] _data = new byte[48];
    int i = writeDiscriminator(SET_VALIDATOR_SCORE_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, index);
    i += 4;
    validatorVote.write(_data, i);
    i += 32;
    putInt32LE(_data, i, score);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record SetValidatorScoreData(int index,
                                    PublicKey validatorVote,
                                    int score) implements Borsh {

  public static final int BYTES = 40;

  public static SetValidatorScoreData read(final byte[] _data, final int offset) {
    int i = offset;
    final var index = getInt32LE(_data, i);
    i += 4;
    final var validatorVote = readPubKey(_data, i);
    i += 32;
    final var score = getInt32LE(_data, i);
    return new SetValidatorScoreData(index, validatorVote, score);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, index);
    i += 4;
    validatorVote.write(_data, i);
    i += 32;
    putInt32LE(_data, i, score);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator CONFIG_VALIDATOR_SYSTEM_DISCRIMINATOR = toDiscriminator(27, 90, 97, 209, 17, 115, 7, 40);

  public static Instruction configValidatorSystem(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                                  final PublicKey stateKey,
                                                  final PublicKey managerAuthorityKey,
                                                  final int extraRuns) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(managerAuthorityKey)
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(CONFIG_VALIDATOR_SYSTEM_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, extraRuns);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record ConfigValidatorSystemData(int extraRuns) implements Borsh {

  public static final int BYTES = 4;

  public static ConfigValidatorSystemData read(final byte[] _data, final int offset) {
    final var extraRuns = getInt32LE(_data, offset);
    return new ConfigValidatorSystemData(extraRuns);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, extraRuns);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator DEPOSIT_DISCRIMINATOR = toDiscriminator(242, 35, 198, 137, 82, 225, 242, 182);

  public static Instruction deposit(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                    final PublicKey stateKey,
                                    final PublicKey msolMintKey,
                                    final PublicKey liqPoolSolLegPdaKey,
                                    final PublicKey liqPoolMsolLegKey,
                                    final PublicKey liqPoolMsolLegAuthorityKey,
                                    final PublicKey reservePdaKey,
                                    final PublicKey transferFromKey,
                                    // user mSOL Token account to send the mSOL
                                    final PublicKey mintToKey,
                                    final PublicKey msolMintAuthorityKey,
                                    final PublicKey systemProgramKey,
                                    final PublicKey tokenProgramKey,
                                    final long lamports) {
    final var keys = List.of(
      createWrite(stateKey),
      createWrite(msolMintKey),
      createWrite(liqPoolSolLegPdaKey),
      createWrite(liqPoolMsolLegKey),
      createRead(liqPoolMsolLegAuthorityKey),
      createWrite(reservePdaKey),
      createWritableSigner(transferFromKey),
      createWrite(mintToKey),
      createRead(msolMintAuthorityKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(DEPOSIT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record DepositData(long lamports) implements Borsh {

  public static final int BYTES = 8;

  public static DepositData read(final byte[] _data, final int offset) {
    final var lamports = getInt64LE(_data, offset);
    return new DepositData(lamports);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, lamports);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator DEPOSIT_STAKE_ACCOUNT_DISCRIMINATOR = toDiscriminator(110, 130, 115, 41, 164, 102, 2, 59);

  public static Instruction depositStakeAccount(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                                final PublicKey stateKey,
                                                final PublicKey validatorListKey,
                                                final PublicKey stakeListKey,
                                                final PublicKey stakeAccountKey,
                                                final PublicKey stakeAuthorityKey,
                                                final PublicKey duplicationFlagKey,
                                                final PublicKey rentPayerKey,
                                                final PublicKey msolMintKey,
                                                // user mSOL Token account to send the mSOL
                                                final PublicKey mintToKey,
                                                final PublicKey msolMintAuthorityKey,
                                                final PublicKey clockKey,
                                                final PublicKey rentKey,
                                                final PublicKey systemProgramKey,
                                                final PublicKey tokenProgramKey,
                                                final PublicKey stakeProgramKey,
                                                final int validatorIndex) {
    final var keys = List.of(
      createWrite(stateKey),
      createWrite(validatorListKey),
      createWrite(stakeListKey),
      createWrite(stakeAccountKey),
      createReadOnlySigner(stakeAuthorityKey),
      createWrite(duplicationFlagKey),
      createWritableSigner(rentPayerKey),
      createWrite(msolMintKey),
      createWrite(mintToKey),
      createRead(msolMintAuthorityKey),
      createRead(clockKey),
      createRead(rentKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(stakeProgramKey)
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(DEPOSIT_STAKE_ACCOUNT_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, validatorIndex);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record DepositStakeAccountData(int validatorIndex) implements Borsh {

  public static final int BYTES = 4;

  public static DepositStakeAccountData read(final byte[] _data, final int offset) {
    final var validatorIndex = getInt32LE(_data, offset);
    return new DepositStakeAccountData(validatorIndex);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, validatorIndex);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator LIQUID_UNSTAKE_DISCRIMINATOR = toDiscriminator(30, 30, 119, 240, 191, 227, 12, 16);

  public static Instruction liquidUnstake(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                          final PublicKey stateKey,
                                          final PublicKey msolMintKey,
                                          final PublicKey liqPoolSolLegPdaKey,
                                          final PublicKey liqPoolMsolLegKey,
                                          final PublicKey treasuryMsolAccountKey,
                                          final PublicKey getMsolFromKey,
                                          final PublicKey getMsolFromAuthorityKey,
                                          final PublicKey transferSolToKey,
                                          final PublicKey systemProgramKey,
                                          final PublicKey tokenProgramKey,
                                          final long msolAmount) {
    final var keys = List.of(
      createWrite(stateKey),
      createWrite(msolMintKey),
      createWrite(liqPoolSolLegPdaKey),
      createWrite(liqPoolMsolLegKey),
      createWrite(treasuryMsolAccountKey),
      createWrite(getMsolFromKey),
      createReadOnlySigner(getMsolFromAuthorityKey),
      createWrite(transferSolToKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(LIQUID_UNSTAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, msolAmount);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record LiquidUnstakeData(long msolAmount) implements Borsh {

  public static final int BYTES = 8;

  public static LiquidUnstakeData read(final byte[] _data, final int offset) {
    final var msolAmount = getInt64LE(_data, offset);
    return new LiquidUnstakeData(msolAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, msolAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator ADD_LIQUIDITY_DISCRIMINATOR = toDiscriminator(181, 157, 89, 67, 143, 182, 52, 72);

  public static Instruction addLiquidity(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                         final PublicKey stateKey,
                                         final PublicKey lpMintKey,
                                         final PublicKey lpMintAuthorityKey,
                                         final PublicKey liqPoolMsolLegKey,
                                         final PublicKey liqPoolSolLegPdaKey,
                                         final PublicKey transferFromKey,
                                         final PublicKey mintToKey,
                                         final PublicKey systemProgramKey,
                                         final PublicKey tokenProgramKey,
                                         final long lamports) {
    final var keys = List.of(
      createWrite(stateKey),
      createWrite(lpMintKey),
      createRead(lpMintAuthorityKey),
      createRead(liqPoolMsolLegKey),
      createWrite(liqPoolSolLegPdaKey),
      createWritableSigner(transferFromKey),
      createWrite(mintToKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(ADD_LIQUIDITY_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record AddLiquidityData(long lamports) implements Borsh {

  public static final int BYTES = 8;

  public static AddLiquidityData read(final byte[] _data, final int offset) {
    final var lamports = getInt64LE(_data, offset);
    return new AddLiquidityData(lamports);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, lamports);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator REMOVE_LIQUIDITY_DISCRIMINATOR = toDiscriminator(80, 85, 209, 72, 24, 206, 177, 108);

  public static Instruction removeLiquidity(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                            final PublicKey stateKey,
                                            final PublicKey lpMintKey,
                                            final PublicKey burnFromKey,
                                            final PublicKey burnFromAuthorityKey,
                                            final PublicKey transferSolToKey,
                                            final PublicKey transferMsolToKey,
                                            final PublicKey liqPoolSolLegPdaKey,
                                            final PublicKey liqPoolMsolLegKey,
                                            final PublicKey liqPoolMsolLegAuthorityKey,
                                            final PublicKey systemProgramKey,
                                            final PublicKey tokenProgramKey,
                                            final long tokens) {
    final var keys = List.of(
      createWrite(stateKey),
      createWrite(lpMintKey),
      createWrite(burnFromKey),
      createReadOnlySigner(burnFromAuthorityKey),
      createWrite(transferSolToKey),
      createWrite(transferMsolToKey),
      createWrite(liqPoolSolLegPdaKey),
      createWrite(liqPoolMsolLegKey),
      createRead(liqPoolMsolLegAuthorityKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(REMOVE_LIQUIDITY_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, tokens);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record RemoveLiquidityData(long tokens) implements Borsh {

  public static final int BYTES = 8;

  public static RemoveLiquidityData read(final byte[] _data, final int offset) {
    final var tokens = getInt64LE(_data, offset);
    return new RemoveLiquidityData(tokens);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, tokens);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator CONFIG_LP_DISCRIMINATOR = toDiscriminator(10, 24, 168, 119, 86, 48, 225, 17);

  public static Instruction configLp(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                     final PublicKey stateKey,
                                     final PublicKey adminAuthorityKey,
                                     final ConfigLpParams params) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(adminAuthorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(CONFIG_LP_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record ConfigLpData(ConfigLpParams params) implements Borsh {

  public static ConfigLpData read(final byte[] _data, final int offset) {
    final var params = ConfigLpParams.read(_data, offset);
    return new ConfigLpData(params);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(params, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(params);
  }
}

  public static final Discriminator CONFIG_MARINADE_DISCRIMINATOR = toDiscriminator(67, 3, 34, 114, 190, 185, 17, 62);

  public static Instruction configMarinade(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                           final PublicKey stateKey,
                                           final PublicKey adminAuthorityKey,
                                           final ConfigMarinadeParams params) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(adminAuthorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(CONFIG_MARINADE_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record ConfigMarinadeData(ConfigMarinadeParams params) implements Borsh {

  public static ConfigMarinadeData read(final byte[] _data, final int offset) {
    final var params = ConfigMarinadeParams.read(_data, offset);
    return new ConfigMarinadeData(params);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(params, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(params);
  }
}

  public static final Discriminator ORDER_UNSTAKE_DISCRIMINATOR = toDiscriminator(97, 167, 144, 107, 117, 190, 128, 36);

  public static Instruction orderUnstake(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                         final PublicKey stateKey,
                                         final PublicKey msolMintKey,
                                         final PublicKey burnMsolFromKey,
                                         final PublicKey burnMsolAuthorityKey,
                                         final PublicKey newTicketAccountKey,
                                         final PublicKey clockKey,
                                         final PublicKey rentKey,
                                         final PublicKey tokenProgramKey,
                                         final long msolAmount) {
    final var keys = List.of(
      createWrite(stateKey),
      createWrite(msolMintKey),
      createWrite(burnMsolFromKey),
      createReadOnlySigner(burnMsolAuthorityKey),
      createWrite(newTicketAccountKey),
      createRead(clockKey),
      createRead(rentKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(ORDER_UNSTAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, msolAmount);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record OrderUnstakeData(long msolAmount) implements Borsh {

  public static final int BYTES = 8;

  public static OrderUnstakeData read(final byte[] _data, final int offset) {
    final var msolAmount = getInt64LE(_data, offset);
    return new OrderUnstakeData(msolAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, msolAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator CLAIM_DISCRIMINATOR = toDiscriminator(62, 198, 214, 193, 213, 159, 108, 210);

  public static Instruction claim(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                  final PublicKey stateKey,
                                  final PublicKey reservePdaKey,
                                  final PublicKey ticketAccountKey,
                                  final PublicKey transferSolToKey,
                                  final PublicKey clockKey,
                                  final PublicKey systemProgramKey) {
    final var keys = List.of(
      createWrite(stateKey),
      createWrite(reservePdaKey),
      createWrite(ticketAccountKey),
      createWrite(transferSolToKey),
      createRead(clockKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, CLAIM_DISCRIMINATOR);
  }

  public static final Discriminator STAKE_RESERVE_DISCRIMINATOR = toDiscriminator(87, 217, 23, 179, 205, 25, 113, 129);

  public static Instruction stakeReserve(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                         final PublicKey stateKey,
                                         final PublicKey validatorListKey,
                                         final PublicKey stakeListKey,
                                         final PublicKey validatorVoteKey,
                                         final PublicKey reservePdaKey,
                                         final PublicKey stakeAccountKey,
                                         final PublicKey stakeDepositAuthorityKey,
                                         final PublicKey rentPayerKey,
                                         final PublicKey clockKey,
                                         final PublicKey epochScheduleKey,
                                         final PublicKey rentKey,
                                         final PublicKey stakeHistoryKey,
                                         final PublicKey stakeConfigKey,
                                         final PublicKey systemProgramKey,
                                         final PublicKey stakeProgramKey,
                                         final int validatorIndex) {
    final var keys = List.of(
      createWrite(stateKey),
      createWrite(validatorListKey),
      createWrite(stakeListKey),
      createWrite(validatorVoteKey),
      createWrite(reservePdaKey),
      createWritableSigner(stakeAccountKey),
      createRead(stakeDepositAuthorityKey),
      createWritableSigner(rentPayerKey),
      createRead(clockKey),
      createRead(epochScheduleKey),
      createRead(rentKey),
      createRead(stakeHistoryKey),
      createRead(stakeConfigKey),
      createRead(systemProgramKey),
      createRead(stakeProgramKey)
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(STAKE_RESERVE_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, validatorIndex);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record StakeReserveData(int validatorIndex) implements Borsh {

  public static final int BYTES = 4;

  public static StakeReserveData read(final byte[] _data, final int offset) {
    final var validatorIndex = getInt32LE(_data, offset);
    return new StakeReserveData(validatorIndex);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, validatorIndex);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator UPDATE_ACTIVE_DISCRIMINATOR = toDiscriminator(4, 67, 81, 64, 136, 245, 93, 152);

  public static Instruction updateActive(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                         final PublicKey commonKey,
                                         final PublicKey validatorListKey,
                                         final int stakeIndex,
                                         final int validatorIndex) {
    final var keys = List.of(
      createRead(commonKey),
      createWrite(validatorListKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(UPDATE_ACTIVE_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, stakeIndex);
    i += 4;
    putInt32LE(_data, i, validatorIndex);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record UpdateActiveData(int stakeIndex, int validatorIndex) implements Borsh {

  public static final int BYTES = 8;

  public static UpdateActiveData read(final byte[] _data, final int offset) {
    int i = offset;
    final var stakeIndex = getInt32LE(_data, i);
    i += 4;
    final var validatorIndex = getInt32LE(_data, i);
    return new UpdateActiveData(stakeIndex, validatorIndex);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, stakeIndex);
    i += 4;
    putInt32LE(_data, i, validatorIndex);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator UPDATE_DEACTIVATED_DISCRIMINATOR = toDiscriminator(16, 232, 131, 115, 156, 100, 239, 50);

  public static Instruction updateDeactivated(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                              final PublicKey commonKey,
                                              final PublicKey operationalSolAccountKey,
                                              final PublicKey systemProgramKey,
                                              final int stakeIndex) {
    final var keys = List.of(
      createRead(commonKey),
      createWrite(operationalSolAccountKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(UPDATE_DEACTIVATED_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, stakeIndex);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record UpdateDeactivatedData(int stakeIndex) implements Borsh {

  public static final int BYTES = 4;

  public static UpdateDeactivatedData read(final byte[] _data, final int offset) {
    final var stakeIndex = getInt32LE(_data, offset);
    return new UpdateDeactivatedData(stakeIndex);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, stakeIndex);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator DEACTIVATE_STAKE_DISCRIMINATOR = toDiscriminator(165, 158, 229, 97, 168, 220, 187, 225);

  public static Instruction deactivateStake(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                            final PublicKey stateKey,
                                            final PublicKey reservePdaKey,
                                            final PublicKey validatorListKey,
                                            final PublicKey stakeListKey,
                                            final PublicKey stakeAccountKey,
                                            final PublicKey stakeDepositAuthorityKey,
                                            final PublicKey splitStakeAccountKey,
                                            final PublicKey splitStakeRentPayerKey,
                                            final PublicKey clockKey,
                                            final PublicKey rentKey,
                                            final PublicKey epochScheduleKey,
                                            final PublicKey stakeHistoryKey,
                                            final PublicKey systemProgramKey,
                                            final PublicKey stakeProgramKey,
                                            final int stakeIndex,
                                            final int validatorIndex) {
    final var keys = List.of(
      createWrite(stateKey),
      createRead(reservePdaKey),
      createWrite(validatorListKey),
      createWrite(stakeListKey),
      createWrite(stakeAccountKey),
      createRead(stakeDepositAuthorityKey),
      createWritableSigner(splitStakeAccountKey),
      createWritableSigner(splitStakeRentPayerKey),
      createRead(clockKey),
      createRead(rentKey),
      createRead(epochScheduleKey),
      createRead(stakeHistoryKey),
      createRead(systemProgramKey),
      createRead(stakeProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(DEACTIVATE_STAKE_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, stakeIndex);
    i += 4;
    putInt32LE(_data, i, validatorIndex);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record DeactivateStakeData(int stakeIndex, int validatorIndex) implements Borsh {

  public static final int BYTES = 8;

  public static DeactivateStakeData read(final byte[] _data, final int offset) {
    int i = offset;
    final var stakeIndex = getInt32LE(_data, i);
    i += 4;
    final var validatorIndex = getInt32LE(_data, i);
    return new DeactivateStakeData(stakeIndex, validatorIndex);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, stakeIndex);
    i += 4;
    putInt32LE(_data, i, validatorIndex);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator EMERGENCY_UNSTAKE_DISCRIMINATOR = toDiscriminator(123, 69, 168, 195, 183, 213, 199, 214);

  public static Instruction emergencyUnstake(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                             final PublicKey stateKey,
                                             final PublicKey validatorManagerAuthorityKey,
                                             final PublicKey validatorListKey,
                                             final PublicKey stakeListKey,
                                             final PublicKey stakeAccountKey,
                                             final PublicKey stakeDepositAuthorityKey,
                                             final PublicKey clockKey,
                                             final PublicKey stakeProgramKey,
                                             final int stakeIndex,
                                             final int validatorIndex) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(validatorManagerAuthorityKey),
      createWrite(validatorListKey),
      createWrite(stakeListKey),
      createWrite(stakeAccountKey),
      createRead(stakeDepositAuthorityKey),
      createRead(clockKey),
      createRead(stakeProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(EMERGENCY_UNSTAKE_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, stakeIndex);
    i += 4;
    putInt32LE(_data, i, validatorIndex);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record EmergencyUnstakeData(int stakeIndex, int validatorIndex) implements Borsh {

  public static final int BYTES = 8;

  public static EmergencyUnstakeData read(final byte[] _data, final int offset) {
    int i = offset;
    final var stakeIndex = getInt32LE(_data, i);
    i += 4;
    final var validatorIndex = getInt32LE(_data, i);
    return new EmergencyUnstakeData(stakeIndex, validatorIndex);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, stakeIndex);
    i += 4;
    putInt32LE(_data, i, validatorIndex);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator PARTIAL_UNSTAKE_DISCRIMINATOR = toDiscriminator(55, 241, 205, 221, 45, 114, 205, 163);

  public static Instruction partialUnstake(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                           final PublicKey stateKey,
                                           final PublicKey validatorManagerAuthorityKey,
                                           final PublicKey validatorListKey,
                                           final PublicKey stakeListKey,
                                           final PublicKey stakeAccountKey,
                                           final PublicKey stakeDepositAuthorityKey,
                                           final PublicKey reservePdaKey,
                                           final PublicKey splitStakeAccountKey,
                                           final PublicKey splitStakeRentPayerKey,
                                           final PublicKey clockKey,
                                           final PublicKey rentKey,
                                           final PublicKey stakeHistoryKey,
                                           final PublicKey systemProgramKey,
                                           final PublicKey stakeProgramKey,
                                           final int stakeIndex,
                                           final int validatorIndex,
                                           final long desiredUnstakeAmount) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(validatorManagerAuthorityKey),
      createWrite(validatorListKey),
      createWrite(stakeListKey),
      createWrite(stakeAccountKey),
      createRead(stakeDepositAuthorityKey),
      createRead(reservePdaKey),
      createWritableSigner(splitStakeAccountKey),
      createWritableSigner(splitStakeRentPayerKey),
      createRead(clockKey),
      createRead(rentKey),
      createRead(stakeHistoryKey),
      createRead(systemProgramKey),
      createRead(stakeProgramKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(PARTIAL_UNSTAKE_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, stakeIndex);
    i += 4;
    putInt32LE(_data, i, validatorIndex);
    i += 4;
    putInt64LE(_data, i, desiredUnstakeAmount);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record PartialUnstakeData(int stakeIndex,
                                 int validatorIndex,
                                 long desiredUnstakeAmount) implements Borsh {

  public static final int BYTES = 16;

  public static PartialUnstakeData read(final byte[] _data, final int offset) {
    int i = offset;
    final var stakeIndex = getInt32LE(_data, i);
    i += 4;
    final var validatorIndex = getInt32LE(_data, i);
    i += 4;
    final var desiredUnstakeAmount = getInt64LE(_data, i);
    return new PartialUnstakeData(stakeIndex, validatorIndex, desiredUnstakeAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, stakeIndex);
    i += 4;
    putInt32LE(_data, i, validatorIndex);
    i += 4;
    putInt64LE(_data, i, desiredUnstakeAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator MERGE_STAKES_DISCRIMINATOR = toDiscriminator(216, 36, 141, 225, 243, 78, 125, 237);

  public static Instruction mergeStakes(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                        final PublicKey stateKey,
                                        final PublicKey stakeListKey,
                                        final PublicKey validatorListKey,
                                        final PublicKey destinationStakeKey,
                                        final PublicKey sourceStakeKey,
                                        final PublicKey stakeDepositAuthorityKey,
                                        final PublicKey stakeWithdrawAuthorityKey,
                                        final PublicKey operationalSolAccountKey,
                                        final PublicKey clockKey,
                                        final PublicKey stakeHistoryKey,
                                        final PublicKey stakeProgramKey,
                                        final int destinationStakeIndex,
                                        final int sourceStakeIndex,
                                        final int validatorIndex) {
    final var keys = List.of(
      createWrite(stateKey),
      createWrite(stakeListKey),
      createWrite(validatorListKey),
      createWrite(destinationStakeKey),
      createWrite(sourceStakeKey),
      createRead(stakeDepositAuthorityKey),
      createRead(stakeWithdrawAuthorityKey),
      createWrite(operationalSolAccountKey),
      createRead(clockKey),
      createRead(stakeHistoryKey),
      createRead(stakeProgramKey)
    );

    final byte[] _data = new byte[20];
    int i = writeDiscriminator(MERGE_STAKES_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, destinationStakeIndex);
    i += 4;
    putInt32LE(_data, i, sourceStakeIndex);
    i += 4;
    putInt32LE(_data, i, validatorIndex);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record MergeStakesData(int destinationStakeIndex,
                              int sourceStakeIndex,
                              int validatorIndex) implements Borsh {

  public static final int BYTES = 12;

  public static MergeStakesData read(final byte[] _data, final int offset) {
    int i = offset;
    final var destinationStakeIndex = getInt32LE(_data, i);
    i += 4;
    final var sourceStakeIndex = getInt32LE(_data, i);
    i += 4;
    final var validatorIndex = getInt32LE(_data, i);
    return new MergeStakesData(destinationStakeIndex, sourceStakeIndex, validatorIndex);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, destinationStakeIndex);
    i += 4;
    putInt32LE(_data, i, sourceStakeIndex);
    i += 4;
    putInt32LE(_data, i, validatorIndex);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator REDELEGATE_DISCRIMINATOR = toDiscriminator(212, 82, 51, 160, 228, 80, 116, 35);

  public static Instruction redelegate(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                       final PublicKey stateKey,
                                       final PublicKey validatorListKey,
                                       final PublicKey stakeListKey,
                                       final PublicKey stakeAccountKey,
                                       final PublicKey stakeDepositAuthorityKey,
                                       final PublicKey reservePdaKey,
                                       final PublicKey splitStakeAccountKey,
                                       final PublicKey splitStakeRentPayerKey,
                                       final PublicKey destValidatorAccountKey,
                                       final PublicKey redelegateStakeAccountKey,
                                       final PublicKey clockKey,
                                       final PublicKey stakeHistoryKey,
                                       final PublicKey stakeConfigKey,
                                       final PublicKey systemProgramKey,
                                       final PublicKey stakeProgramKey,
                                       final int stakeIndex,
                                       final int sourceValidatorIndex,
                                       final int destValidatorIndex) {
    final var keys = List.of(
      createWrite(stateKey),
      createWrite(validatorListKey),
      createWrite(stakeListKey),
      createWrite(stakeAccountKey),
      createRead(stakeDepositAuthorityKey),
      createRead(reservePdaKey),
      createWritableSigner(splitStakeAccountKey),
      createWritableSigner(splitStakeRentPayerKey),
      createRead(destValidatorAccountKey),
      createWritableSigner(redelegateStakeAccountKey),
      createRead(clockKey),
      createRead(stakeHistoryKey),
      createRead(stakeConfigKey),
      createRead(systemProgramKey),
      createRead(stakeProgramKey)
    );

    final byte[] _data = new byte[20];
    int i = writeDiscriminator(REDELEGATE_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, stakeIndex);
    i += 4;
    putInt32LE(_data, i, sourceValidatorIndex);
    i += 4;
    putInt32LE(_data, i, destValidatorIndex);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record RedelegateData(int stakeIndex,
                             int sourceValidatorIndex,
                             int destValidatorIndex) implements Borsh {

  public static final int BYTES = 12;

  public static RedelegateData read(final byte[] _data, final int offset) {
    int i = offset;
    final var stakeIndex = getInt32LE(_data, i);
    i += 4;
    final var sourceValidatorIndex = getInt32LE(_data, i);
    i += 4;
    final var destValidatorIndex = getInt32LE(_data, i);
    return new RedelegateData(stakeIndex, sourceValidatorIndex, destValidatorIndex);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, stakeIndex);
    i += 4;
    putInt32LE(_data, i, sourceValidatorIndex);
    i += 4;
    putInt32LE(_data, i, destValidatorIndex);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator PAUSE_DISCRIMINATOR = toDiscriminator(211, 22, 221, 251, 74, 121, 193, 47);

  public static Instruction pause(final AccountMeta invokedMarinadeFinanceProgramMeta, final PublicKey stateKey, final PublicKey pauseAuthorityKey) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(pauseAuthorityKey)
    );

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, PAUSE_DISCRIMINATOR);
  }

  public static final Discriminator RESUME_DISCRIMINATOR = toDiscriminator(1, 166, 51, 170, 127, 32, 141, 206);

  public static Instruction resume(final AccountMeta invokedMarinadeFinanceProgramMeta, final PublicKey stateKey, final PublicKey pauseAuthorityKey) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(pauseAuthorityKey)
    );

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, RESUME_DISCRIMINATOR);
  }

  public static final Discriminator WITHDRAW_STAKE_ACCOUNT_DISCRIMINATOR = toDiscriminator(211, 85, 184, 65, 183, 177, 233, 217);

  public static Instruction withdrawStakeAccount(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                                 final PublicKey stateKey,
                                                 final PublicKey msolMintKey,
                                                 final PublicKey burnMsolFromKey,
                                                 final PublicKey burnMsolAuthorityKey,
                                                 final PublicKey treasuryMsolAccountKey,
                                                 final PublicKey validatorListKey,
                                                 final PublicKey stakeListKey,
                                                 final PublicKey stakeWithdrawAuthorityKey,
                                                 final PublicKey stakeDepositAuthorityKey,
                                                 final PublicKey stakeAccountKey,
                                                 final PublicKey splitStakeAccountKey,
                                                 final PublicKey splitStakeRentPayerKey,
                                                 final PublicKey clockKey,
                                                 final PublicKey systemProgramKey,
                                                 final PublicKey tokenProgramKey,
                                                 final PublicKey stakeProgramKey,
                                                 final int stakeIndex,
                                                 final int validatorIndex,
                                                 final long msolAmount,
                                                 final PublicKey beneficiary) {
    final var keys = List.of(
      createWrite(stateKey),
      createWrite(msolMintKey),
      createWrite(burnMsolFromKey),
      createWritableSigner(burnMsolAuthorityKey),
      createWrite(treasuryMsolAccountKey),
      createWrite(validatorListKey),
      createWrite(stakeListKey),
      createRead(stakeWithdrawAuthorityKey),
      createRead(stakeDepositAuthorityKey),
      createWrite(stakeAccountKey),
      createWritableSigner(splitStakeAccountKey),
      createWritableSigner(splitStakeRentPayerKey),
      createRead(clockKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(stakeProgramKey)
    );

    final byte[] _data = new byte[56];
    int i = writeDiscriminator(WITHDRAW_STAKE_ACCOUNT_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, stakeIndex);
    i += 4;
    putInt32LE(_data, i, validatorIndex);
    i += 4;
    putInt64LE(_data, i, msolAmount);
    i += 8;
    beneficiary.write(_data, i);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record WithdrawStakeAccountData(int stakeIndex,
                                       int validatorIndex,
                                       long msolAmount,
                                       PublicKey beneficiary) implements Borsh {

  public static final int BYTES = 48;

  public static WithdrawStakeAccountData read(final byte[] _data, final int offset) {
    int i = offset;
    final var stakeIndex = getInt32LE(_data, i);
    i += 4;
    final var validatorIndex = getInt32LE(_data, i);
    i += 4;
    final var msolAmount = getInt64LE(_data, i);
    i += 8;
    final var beneficiary = readPubKey(_data, i);
    return new WithdrawStakeAccountData(stakeIndex,
                                        validatorIndex,
                                        msolAmount,
                                        beneficiary);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
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

  public static final Discriminator REALLOC_VALIDATOR_LIST_DISCRIMINATOR = toDiscriminator(215, 59, 218, 133, 93, 138, 60, 123);

  public static Instruction reallocValidatorList(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                                 final PublicKey stateKey,
                                                 final PublicKey adminAuthorityKey,
                                                 final PublicKey validatorListKey,
                                                 final PublicKey rentFundsKey,
                                                 final PublicKey systemProgramKey,
                                                 final int capacity) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(adminAuthorityKey),
      createWrite(validatorListKey),
      createWritableSigner(rentFundsKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(REALLOC_VALIDATOR_LIST_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, capacity);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record ReallocValidatorListData(int capacity) implements Borsh {

  public static final int BYTES = 4;

  public static ReallocValidatorListData read(final byte[] _data, final int offset) {
    final var capacity = getInt32LE(_data, offset);
    return new ReallocValidatorListData(capacity);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, capacity);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  public static final Discriminator REALLOC_STAKE_LIST_DISCRIMINATOR = toDiscriminator(12, 36, 124, 27, 128, 96, 85, 199);

  public static Instruction reallocStakeList(final AccountMeta invokedMarinadeFinanceProgramMeta,
                                             final PublicKey stateKey,
                                             final PublicKey adminAuthorityKey,
                                             final PublicKey stakeListKey,
                                             final PublicKey rentFundsKey,
                                             final PublicKey systemProgramKey,
                                             final int capacity) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(adminAuthorityKey),
      createWrite(stakeListKey),
      createWritableSigner(rentFundsKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(REALLOC_STAKE_LIST_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, capacity);

    return Instruction.createInstruction(invokedMarinadeFinanceProgramMeta, keys, _data);
  }


public record ReallocStakeListData(int capacity) implements Borsh {

  public static final int BYTES = 4;

  public static ReallocStakeListData read(final byte[] _data, final int offset) {
    final var capacity = getInt32LE(_data, offset);
    return new ReallocStakeListData(capacity);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, capacity);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}

  private MarinadeFinanceProgram() {
  }
}
