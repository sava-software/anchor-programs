package software.sava.anchor.programs.glam.anchor;

import java.util.List;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.tx.Instruction;

import software.sava.anchor.programs.glam.anchor.types.FundModel;
import software.sava.anchor.programs.glam.anchor.types.ShareClassModel;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.anchor.AnchorUtil.writeDiscriminator;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.ProgramUtil.toDiscriminator;

public final class GlamProgram {

  public static final byte[] INITIALIZE_DISCRIMINATOR = toDiscriminator(175, 175, 109, 31, 13, 152, 155, 237);

  public static Instruction initialize(final AccountMeta invokedGlamProgramMeta,
                                       final AccountMeta managerKey,
                                       final PublicKey fundKey,
                                       final PublicKey openfundsKey,
                                       final PublicKey treasuryKey,
                                       final PublicKey systemProgramKey,
                                       final FundModel fund) {
    final var keys = List.of(
      createWrite(fundKey),
      createWrite(openfundsKey),
      createWrite(treasuryKey),
      managerKey,
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(fund)];
    int i = writeDiscriminator(INITIALIZE_DISCRIMINATOR, _data, 0);
    Borsh.write(fund, _data, i);
  
    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public static final byte[] ADD_SHARE_CLASS_DISCRIMINATOR = toDiscriminator(34, 49, 47, 6, 204, 166, 51, 204);

  public static Instruction addShareClass(final AccountMeta invokedGlamProgramMeta,
                                          final AccountMeta managerKey,
                                          final PublicKey shareClassMintKey,
                                          final PublicKey fundKey,
                                          final PublicKey openfundsKey,
                                          final PublicKey systemProgramKey,
                                          final PublicKey tokenProgramKey,
                                          final ShareClassModel shareClassMetadata) {
    final var keys = List.of(
      createWrite(shareClassMintKey),
      createWrite(fundKey),
      createWrite(openfundsKey),
      managerKey,
      createRead(systemProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(shareClassMetadata)];
    int i = writeDiscriminator(ADD_SHARE_CLASS_DISCRIMINATOR, _data, 0);
    Borsh.write(shareClassMetadata, _data, i);
  
    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public static final byte[] UPDATE_DISCRIMINATOR = toDiscriminator(219, 200, 88, 176, 158, 63, 253, 127);

  public static Instruction update(final AccountMeta invokedGlamProgramMeta,
                                   final AccountMeta signerKey,
                                   final PublicKey fundKey,
                                   final FundModel fund) {
    final var keys = List.of(
      createWrite(fundKey),
      signerKey
    );

    final byte[] _data = new byte[8 + Borsh.len(fund)];
    int i = writeDiscriminator(UPDATE_DISCRIMINATOR, _data, 0);
    Borsh.write(fund, _data, i);
  
    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public static final byte[] CLOSE_DISCRIMINATOR = toDiscriminator(98, 165, 201, 177, 108, 65, 206, 96);

  public static Instruction close(final AccountMeta invokedGlamProgramMeta, final AccountMeta managerKey, final PublicKey fundKey) {
    final var keys = List.of(
      createWrite(fundKey),
      managerKey
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, CLOSE_DISCRIMINATOR);
  }

  public static final byte[] SUBSCRIBE_DISCRIMINATOR = toDiscriminator(254, 28, 191, 138, 156, 179, 183, 53);

  public static Instruction subscribe(final AccountMeta invokedGlamProgramMeta,
                                      final AccountMeta signerKey,
                                      final PublicKey fundKey,
                                      final PublicKey treasuryKey,
                                      final PublicKey shareClassKey,
                                      final PublicKey signerShareAtaKey,
                                      final PublicKey assetKey,
                                      final PublicKey treasuryAtaKey,
                                      final PublicKey signerAssetAtaKey,
                                      final PublicKey systemProgramKey,
                                      final PublicKey associatedTokenProgramKey,
                                      final PublicKey tokenProgramKey,
                                      final PublicKey token2022ProgramKey,
                                      final long amount,
                                      final boolean skipState) {
    final var keys = List.of(
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(shareClassKey),
      createWrite(signerShareAtaKey),
      createWrite(assetKey),
      createWrite(treasuryAtaKey),
      createWrite(signerAssetAtaKey),
      signerKey,
      createRead(systemProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(tokenProgramKey),
      createRead(token2022ProgramKey)
    );

    final byte[] _data = new byte[17];
    int i = writeDiscriminator(SUBSCRIBE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) (skipState ? 1 : 0);
  
    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public static final byte[] REDEEM_DISCRIMINATOR = toDiscriminator(184, 12, 86, 149, 70, 196, 97, 225);

  public static Instruction redeem(final AccountMeta invokedGlamProgramMeta,
                                   final AccountMeta signerKey,
                                   final PublicKey fundKey,
                                   final PublicKey shareClassKey,
                                   final PublicKey signerShareAtaKey,
                                   final PublicKey treasuryKey,
                                   final PublicKey tokenProgramKey,
                                   final PublicKey token2022ProgramKey,
                                   final long amount,
                                   final boolean inKind,
                                   final boolean skipState) {
    final var keys = List.of(
      createRead(fundKey),
      createWrite(shareClassKey),
      createWrite(signerShareAtaKey),
      signerKey,
      createRead(treasuryKey),
      createRead(tokenProgramKey),
      createRead(token2022ProgramKey)
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

  public static final byte[] DRIFT_INITIALIZE_DISCRIMINATOR = toDiscriminator(21, 21, 69, 55, 41, 129, 44, 198);

  public static Instruction driftInitialize(final AccountMeta invokedGlamProgramMeta,
                                            final AccountMeta managerKey,
                                            final PublicKey fundKey,
                                            final PublicKey treasuryKey,
                                            final PublicKey userStatsKey,
                                            final PublicKey userKey,
                                            final PublicKey stateKey,
                                            final PublicKey driftProgramKey,
                                            final PublicKey rentKey,
                                            final PublicKey systemProgramKey,
                                            final PublicKey trader) {
    final var keys = List.of(
      createRead(fundKey),
      createRead(treasuryKey),
      createWrite(userStatsKey),
      createWrite(userKey),
      createWrite(stateKey),
      managerKey,
      createRead(driftProgramKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[41];
    int i = writeDiscriminator(DRIFT_INITIALIZE_DISCRIMINATOR, _data, 0);
    Borsh.writeOptional(trader, _data, i);
  
    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public static final byte[] DRIFT_UPDATE_DELEGATED_TRADER_DISCRIMINATOR = toDiscriminator(98, 66, 206, 146, 109, 215, 206, 57);

  public static Instruction driftUpdateDelegatedTrader(final AccountMeta invokedGlamProgramMeta,
                                                       final AccountMeta managerKey,
                                                       final PublicKey fundKey,
                                                       final PublicKey treasuryKey,
                                                       final PublicKey userKey,
                                                       final PublicKey driftProgramKey,
                                                       final PublicKey trader) {
    final var keys = List.of(
      createRead(fundKey),
      createRead(treasuryKey),
      createWrite(userKey),
      managerKey,
      createRead(driftProgramKey)
    );

    final byte[] _data = new byte[41];
    int i = writeDiscriminator(DRIFT_UPDATE_DELEGATED_TRADER_DISCRIMINATOR, _data, 0);
    Borsh.writeOptional(trader, _data, i);
  
    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public static final byte[] DRIFT_DEPOSIT_DISCRIMINATOR = toDiscriminator(252, 63, 250, 201, 98, 55, 130, 12);

  public static Instruction driftDeposit(final AccountMeta invokedGlamProgramMeta,
                                         final AccountMeta managerKey,
                                         final PublicKey fundKey,
                                         final PublicKey treasuryKey,
                                         final PublicKey userStatsKey,
                                         final PublicKey userKey,
                                         final PublicKey stateKey,
                                         final PublicKey treasuryAtaKey,
                                         final PublicKey driftAtaKey,
                                         final PublicKey driftProgramKey,
                                         final PublicKey tokenProgramKey,
                                         final long amount) {
    final var keys = List.of(
      createRead(fundKey),
      createRead(treasuryKey),
      createWrite(userStatsKey),
      createWrite(userKey),
      createWrite(stateKey),
      createWrite(treasuryAtaKey),
      createWrite(driftAtaKey),
      managerKey,
      createRead(driftProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(DRIFT_DEPOSIT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);
  
    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public static final byte[] DRIFT_WITHDRAW_DISCRIMINATOR = toDiscriminator(86, 59, 186, 123, 183, 181, 234, 137);

  public static Instruction driftWithdraw(final AccountMeta invokedGlamProgramMeta,
                                          final AccountMeta managerKey,
                                          final PublicKey fundKey,
                                          final PublicKey treasuryKey,
                                          final PublicKey userStatsKey,
                                          final PublicKey userKey,
                                          final PublicKey stateKey,
                                          final PublicKey driftSignerKey,
                                          final PublicKey treasuryAtaKey,
                                          final PublicKey driftAtaKey,
                                          final PublicKey driftProgramKey,
                                          final PublicKey tokenProgramKey,
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
      managerKey,
      createRead(driftProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(DRIFT_WITHDRAW_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);
  
    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public static final byte[] DRIFT_CLOSE_DISCRIMINATOR = toDiscriminator(23, 133, 219, 157, 137, 34, 93, 58);

  public static Instruction driftClose(final AccountMeta invokedGlamProgramMeta,
                                       final AccountMeta managerKey,
                                       final PublicKey fundKey,
                                       final PublicKey treasuryKey,
                                       final PublicKey userStatsKey,
                                       final PublicKey userKey,
                                       final PublicKey stateKey,
                                       final PublicKey driftProgramKey,
                                       final PublicKey systemProgramKey) {
    final var keys = List.of(
      createRead(fundKey),
      createRead(treasuryKey),
      createWrite(userStatsKey),
      createWrite(userKey),
      createWrite(stateKey),
      managerKey,
      createRead(driftProgramKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, DRIFT_CLOSE_DISCRIMINATOR);
  }

  public static final byte[] MARINADE_DEPOSIT_DISCRIMINATOR = toDiscriminator(62, 236, 248, 28, 222, 232, 182, 73);

  public static Instruction marinadeDeposit(final AccountMeta invokedGlamProgramMeta,
                                            final AccountMeta managerKey,
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
                                            final PublicKey associatedTokenProgramKey,
                                            final PublicKey systemProgramKey,
                                            final PublicKey tokenProgramKey,
                                            final long solAmount) {
    final var keys = List.of(
      managerKey,
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
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(MARINADE_DEPOSIT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, solAmount);
  
    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public static final byte[] MARINADE_LIQUID_UNSTAKE_DISCRIMINATOR = toDiscriminator(29, 146, 34, 21, 26, 68, 141, 161);

  public static Instruction marinadeLiquidUnstake(final AccountMeta invokedGlamProgramMeta,
                                                  final AccountMeta managerKey,
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
                                                  final PublicKey tokenProgramKey,
                                                  final PublicKey systemProgramKey,
                                                  final long solAmount) {
    final var keys = List.of(
      managerKey,
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
      createRead(tokenProgramKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(MARINADE_LIQUID_UNSTAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, solAmount);
  
    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public static final byte[] MARINADE_DELAYED_UNSTAKE_DISCRIMINATOR = toDiscriminator(117, 66, 3, 222, 230, 94, 129, 95);

  public static Instruction marinadeDelayedUnstake(final AccountMeta invokedGlamProgramMeta,
                                                   final AccountMeta managerKey,
                                                   final PublicKey fundKey,
                                                   final PublicKey treasuryKey,
                                                   final PublicKey ticketKey,
                                                   final PublicKey msolMintKey,
                                                   final PublicKey burnMsolFromKey,
                                                   final PublicKey marinadeStateKey,
                                                   final PublicKey reservePdaKey,
                                                   final PublicKey rentKey,
                                                   final PublicKey clockKey,
                                                   final PublicKey systemProgramKey,
                                                   final PublicKey tokenProgramKey,
                                                   final PublicKey marinadeProgramKey,
                                                   final long amount,
                                                   final int bump,
                                                   final String ticketId) {
    final var keys = List.of(
      managerKey,
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(ticketKey),
      createWrite(msolMintKey),
      createWrite(burnMsolFromKey),
      createWrite(marinadeStateKey),
      createWrite(reservePdaKey),
      createRead(rentKey),
      createRead(clockKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(marinadeProgramKey)
    );

    final byte[] _ticketId = ticketId.getBytes(UTF_8);
    final byte[] _data = new byte[21 + Borsh.len(_ticketId)];
    int i = writeDiscriminator(MARINADE_DELAYED_UNSTAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) bump;
    ++i;
    Borsh.write(_ticketId, _data, i);
  
    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public static final byte[] MARINADE_CLAIM_DISCRIMINATOR = toDiscriminator(54, 44, 48, 204, 218, 141, 36, 5);

  public static Instruction marinadeClaim(final AccountMeta invokedGlamProgramMeta,
                                          final AccountMeta managerKey,
                                          final PublicKey fundKey,
                                          final PublicKey treasuryKey,
                                          final PublicKey marinadeStateKey,
                                          final PublicKey reservePdaKey,
                                          final PublicKey rentKey,
                                          final PublicKey clockKey,
                                          final PublicKey systemProgramKey,
                                          final PublicKey tokenProgramKey,
                                          final PublicKey marinadeProgramKey) {
    final var keys = List.of(
      managerKey,
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(marinadeStateKey),
      createWrite(reservePdaKey),
      createRead(rentKey),
      createRead(clockKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(marinadeProgramKey)
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, MARINADE_CLAIM_DISCRIMINATOR);
  }

  public static final byte[] STAKE_POOL_DEPOSIT_DISCRIMINATOR = toDiscriminator(183, 251, 150, 32, 82, 82, 252, 225);

  public static Instruction stakePoolDeposit(final AccountMeta invokedGlamProgramMeta,
                                             final AccountMeta managerKey,
                                             final PublicKey fundKey,
                                             final PublicKey treasuryKey,
                                             final PublicKey stakePoolProgramKey,
                                             final PublicKey stakePoolKey,
                                             final PublicKey withdrawAuthorityKey,
                                             final PublicKey reserveStakeKey,
                                             final PublicKey poolMintKey,
                                             final PublicKey feeAccountKey,
                                             final PublicKey mintToKey,
                                             final PublicKey associatedTokenProgramKey,
                                             final PublicKey systemProgramKey,
                                             final PublicKey tokenProgramKey,
                                             final long lamports) {
    final var keys = List.of(
      managerKey,
      createRead(fundKey),
      createWrite(treasuryKey),
      createRead(stakePoolProgramKey),
      createWrite(stakePoolKey),
      createRead(withdrawAuthorityKey),
      createWrite(reserveStakeKey),
      createWrite(poolMintKey),
      createWrite(feeAccountKey),
      createWrite(mintToKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(STAKE_POOL_DEPOSIT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);
  
    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public static final byte[] STAKE_POOL_WITHDRAW_SOL_DISCRIMINATOR = toDiscriminator(179, 100, 204, 0, 192, 46, 233, 181);

  public static Instruction stakePoolWithdrawSol(final AccountMeta invokedGlamProgramMeta,
                                                 final AccountMeta managerKey,
                                                 final PublicKey fundKey,
                                                 final PublicKey treasuryKey,
                                                 final PublicKey stakePoolProgramKey,
                                                 final PublicKey stakePoolKey,
                                                 final PublicKey withdrawAuthorityKey,
                                                 final PublicKey reserveStakeKey,
                                                 final PublicKey poolMintKey,
                                                 final PublicKey feeAccountKey,
                                                 final PublicKey poolTokenAtaKey,
                                                 final PublicKey clockKey,
                                                 final PublicKey stakeHistoryKey,
                                                 final PublicKey systemProgramKey,
                                                 final PublicKey tokenProgramKey,
                                                 final PublicKey stakeProgramKey,
                                                 final long lamports) {
    final var keys = List.of(
      managerKey,
      createRead(fundKey),
      createWrite(treasuryKey),
      createRead(stakePoolProgramKey),
      createWrite(stakePoolKey),
      createRead(withdrawAuthorityKey),
      createWrite(reserveStakeKey),
      createWrite(poolMintKey),
      createWrite(feeAccountKey),
      createWrite(poolTokenAtaKey),
      createRead(clockKey),
      createRead(stakeHistoryKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(stakeProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(STAKE_POOL_WITHDRAW_SOL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);
  
    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public static final byte[] STAKE_POOL_WITHDRAW_STAKE_DISCRIMINATOR = toDiscriminator(7, 70, 250, 22, 49, 1, 143, 1);

  public static Instruction stakePoolWithdrawStake(final AccountMeta invokedGlamProgramMeta,
                                                   final AccountMeta managerKey,
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
                                                   final PublicKey clockKey,
                                                   final PublicKey systemProgramKey,
                                                   final PublicKey tokenProgramKey,
                                                   final PublicKey stakeProgramKey,
                                                   final long poolTokenAmount,
                                                   final String stakeAccountId,
                                                   final int stakeAccountBump) {
    final var keys = List.of(
      managerKey,
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
      createRead(clockKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(stakeProgramKey)
    );

    final byte[] _stakeAccountId = stakeAccountId.getBytes(UTF_8);
    final byte[] _data = new byte[21 + Borsh.len(_stakeAccountId)];
    int i = writeDiscriminator(STAKE_POOL_WITHDRAW_STAKE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, poolTokenAmount);
    i += 8;
    i += Borsh.write(_stakeAccountId, _data, i);
    _data[i] = (byte) stakeAccountBump;
  
    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public static final byte[] NATIVE_STAKE_DEPOSIT_DISCRIMINATOR = toDiscriminator(195, 238, 183, 139, 247, 59, 243, 149);

  public static Instruction nativeStakeDeposit(final AccountMeta invokedGlamProgramMeta,
                                               final AccountMeta managerKey,
                                               final PublicKey fundKey,
                                               final PublicKey treasuryKey,
                                               final PublicKey treasuryStakeAccountKey,
                                               final PublicKey voteKey,
                                               final PublicKey stakeConfigKey,
                                               final PublicKey clockKey,
                                               final PublicKey rentKey,
                                               final PublicKey stakeHistoryKey,
                                               final PublicKey stakeProgramKey,
                                               final PublicKey systemProgramKey,
                                               final long lamports,
                                               final String stakeAccountId,
                                               final int stakeAccountBump) {
    final var keys = List.of(
      managerKey,
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(treasuryStakeAccountKey),
      createRead(voteKey),
      createRead(stakeConfigKey),
      createRead(clockKey),
      createRead(rentKey),
      createRead(stakeHistoryKey),
      createRead(stakeProgramKey),
      createRead(systemProgramKey)
    );

    final byte[] _stakeAccountId = stakeAccountId.getBytes(UTF_8);
    final byte[] _data = new byte[21 + Borsh.len(_stakeAccountId)];
    int i = writeDiscriminator(NATIVE_STAKE_DEPOSIT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lamports);
    i += 8;
    i += Borsh.write(_stakeAccountId, _data, i);
    _data[i] = (byte) stakeAccountBump;
  
    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public static final byte[] DEACTIVATE_STAKE_ACCOUNTS_DISCRIMINATOR = toDiscriminator(58, 18, 6, 22, 226, 216, 161, 193);

  public static Instruction deactivateStakeAccounts(final AccountMeta invokedGlamProgramMeta,
                                                    final AccountMeta managerKey,
                                                    final PublicKey fundKey,
                                                    final PublicKey treasuryKey,
                                                    final PublicKey clockKey,
                                                    final PublicKey stakeProgramKey) {
    final var keys = List.of(
      managerKey,
      createRead(fundKey),
      createWrite(treasuryKey),
      createRead(clockKey),
      createRead(stakeProgramKey)
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, DEACTIVATE_STAKE_ACCOUNTS_DISCRIMINATOR);
  }

  public static final byte[] WITHDRAW_FROM_STAKE_ACCOUNTS_DISCRIMINATOR = toDiscriminator(93, 209, 100, 231, 169, 160, 192, 197);

  public static Instruction withdrawFromStakeAccounts(final AccountMeta invokedGlamProgramMeta,
                                                      final AccountMeta managerKey,
                                                      final PublicKey fundKey,
                                                      final PublicKey treasuryKey,
                                                      final PublicKey clockKey,
                                                      final PublicKey stakeHistoryKey,
                                                      final PublicKey stakeProgramKey) {
    final var keys = List.of(
      managerKey,
      createRead(fundKey),
      createWrite(treasuryKey),
      createRead(clockKey),
      createRead(stakeHistoryKey),
      createRead(stakeProgramKey)
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, WITHDRAW_FROM_STAKE_ACCOUNTS_DISCRIMINATOR);
  }

  public static final byte[] JUPITER_SWAP_DISCRIMINATOR = toDiscriminator(116, 207, 0, 196, 252, 120, 243, 18);

  public static Instruction jupiterSwap(final AccountMeta invokedGlamProgramMeta,
                                        final AccountMeta signerKey,
                                        final PublicKey fundKey,
                                        final PublicKey treasuryKey,
                                        // input_treasury_ata to input_signer_ata
                                        final PublicKey inputTreasuryAtaKey,
                                        final PublicKey inputSignerAtaKey,
                                        final PublicKey outputSignerAtaKey,
                                        final PublicKey outputTreasuryAtaKey,
                                        final PublicKey inputMintKey,
                                        final PublicKey outputMintKey,
                                        final PublicKey systemProgramKey,
                                        final PublicKey jupiterProgramKey,
                                        final PublicKey associatedTokenProgramKey,
                                        final PublicKey tokenProgramKey,
                                        final PublicKey token2022ProgramKey,
                                        final long amount,
                                        final byte[] data) {
    final var keys = List.of(
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(inputTreasuryAtaKey),
      createWrite(inputSignerAtaKey),
      createWrite(outputSignerAtaKey),
      createWrite(outputTreasuryAtaKey),
      createRead(inputMintKey),
      createRead(outputMintKey),
      signerKey,
      createRead(systemProgramKey),
      createRead(jupiterProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(tokenProgramKey),
      createRead(token2022ProgramKey)
    );

    final byte[] _data = new byte[20 + Borsh.len(data)];
    int i = writeDiscriminator(JUPITER_SWAP_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    Borsh.write(data, _data, i);
  
    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public static final byte[] WSOL_WRAP_DISCRIMINATOR = toDiscriminator(26, 2, 139, 159, 239, 195, 193, 9);

  public static Instruction wsolWrap(final AccountMeta invokedGlamProgramMeta,
                                     final AccountMeta signerKey,
                                     final PublicKey fundKey,
                                     final PublicKey treasuryKey,
                                     final PublicKey treasuryWsolAtaKey,
                                     final PublicKey wsolMintKey,
                                     final PublicKey systemProgramKey,
                                     final PublicKey tokenProgramKey,
                                     final PublicKey associatedTokenProgramKey,
                                     final long amount) {
    final var keys = List.of(
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(treasuryWsolAtaKey),
      createRead(wsolMintKey),
      signerKey,
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(WSOL_WRAP_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);
  
    return Instruction.createInstruction(invokedGlamProgramMeta, keys, _data);
  }

  public static final byte[] WSOL_UNWRAP_DISCRIMINATOR = toDiscriminator(123, 189, 16, 96, 233, 186, 54, 215);

  public static Instruction wsolUnwrap(final AccountMeta invokedGlamProgramMeta,
                                       final AccountMeta signerKey,
                                       final PublicKey fundKey,
                                       final PublicKey treasuryKey,
                                       final PublicKey treasuryWsolAtaKey,
                                       final PublicKey wsolMintKey,
                                       final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createRead(fundKey),
      createWrite(treasuryKey),
      createWrite(treasuryWsolAtaKey),
      createRead(wsolMintKey),
      signerKey,
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedGlamProgramMeta, keys, WSOL_UNWRAP_DISCRIMINATOR);
  }
  
  private GlamProgram() {
  }
}