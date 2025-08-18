package software.sava.anchor.programs.loopscale.anchor;

import java.util.List;

import software.sava.anchor.programs.loopscale.anchor.types.BorrowPrincipalParams;
import software.sava.anchor.programs.loopscale.anchor.types.ClaimVaultFeeParams;
import software.sava.anchor.programs.loopscale.anchor.types.CollateralAllocationParam;
import software.sava.anchor.programs.loopscale.anchor.types.CreateLoanParams;
import software.sava.anchor.programs.loopscale.anchor.types.CreateMarketInformationParams;
import software.sava.anchor.programs.loopscale.anchor.types.CreateRewardsScheduleParams;
import software.sava.anchor.programs.loopscale.anchor.types.CreateStrategyParams;
import software.sava.anchor.programs.loopscale.anchor.types.CreateVaultParams;
import software.sava.anchor.programs.loopscale.anchor.types.DepositCollateralParams;
import software.sava.anchor.programs.loopscale.anchor.types.LiquidateLedgerParams;
import software.sava.anchor.programs.loopscale.anchor.types.LoanUnlockParams;
import software.sava.anchor.programs.loopscale.anchor.types.LockLoanParams;
import software.sava.anchor.programs.loopscale.anchor.types.LpParams;
import software.sava.anchor.programs.loopscale.anchor.types.ManageLiquidityParams;
import software.sava.anchor.programs.loopscale.anchor.types.ManageRaydiumLiquidityParams;
import software.sava.anchor.programs.loopscale.anchor.types.MultiCollateralTermsUpdateParams;
import software.sava.anchor.programs.loopscale.anchor.types.RefinanceLedgerParams;
import software.sava.anchor.programs.loopscale.anchor.types.RepayPrincipalParams;
import software.sava.anchor.programs.loopscale.anchor.types.SellLedgerParams;
import software.sava.anchor.programs.loopscale.anchor.types.TimelockUpdateParams;
import software.sava.anchor.programs.loopscale.anchor.types.TransferPositionParams;
import software.sava.anchor.programs.loopscale.anchor.types.UpdateAssetDataParams;
import software.sava.anchor.programs.loopscale.anchor.types.UpdateCapsParams;
import software.sava.anchor.programs.loopscale.anchor.types.UpdateRewardsScheduleParams;
import software.sava.anchor.programs.loopscale.anchor.types.UpdateStrategyParams;
import software.sava.anchor.programs.loopscale.anchor.types.UpdateVaultParams;
import software.sava.anchor.programs.loopscale.anchor.types.UpdateWeightMatrixParams;
import software.sava.anchor.programs.loopscale.anchor.types.VaultStakeParams;
import software.sava.anchor.programs.loopscale.anchor.types.VaultUnstakeParams;
import software.sava.anchor.programs.loopscale.anchor.types.WithdrawCollateralParams;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static java.util.Objects.requireNonNullElse;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.anchor.AnchorUtil.writeDiscriminator;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class LoopscaleProgram {

  public static final Discriminator BORROW_PRINCIPAL_DISCRIMINATOR = toDiscriminator(106, 10, 38, 204, 139, 188, 124, 50);

  // principal instructions
  // 6.
  // 6.1. borrow principal
  public static Instruction borrowPrincipal(final AccountMeta invokedLoopscaleProgramMeta,
                                            final PublicKey bsAuthKey,
                                            final PublicKey payerKey,
                                            final PublicKey borrowerKey,
                                            final PublicKey loanKey,
                                            final PublicKey strategyKey,
                                            final PublicKey marketInformationKey,
                                            final PublicKey principalMintKey,
                                            final PublicKey borrowerTaKey,
                                            final PublicKey strategyTaKey,
                                            final PublicKey associatedTokenProgramKey,
                                            final PublicKey tokenProgramKey,
                                            final PublicKey systemProgramKey,
                                            final PublicKey eventAuthorityKey,
                                            final PublicKey programKey,
                                            final BorrowPrincipalParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(borrowerKey),
      createWrite(loanKey),
      createWrite(strategyKey),
      createWrite(marketInformationKey),
      createRead(principalMintKey),
      createWrite(borrowerTaKey),
      createWrite(strategyTaKey),
      createRead(associatedTokenProgramKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(BORROW_PRINCIPAL_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record BorrowPrincipalIxData(Discriminator discriminator, BorrowPrincipalParams params) implements Borsh {  

    public static BorrowPrincipalIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static BorrowPrincipalIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = BorrowPrincipalParams.read(_data, i);
      return new BorrowPrincipalIxData(discriminator, params);
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

  public static final Discriminator CANCEL_TIMELOCK_DISCRIMINATOR = toDiscriminator(158, 180, 47, 81, 133, 231, 168, 238);

  // 9.2.3 timelock cancel
  public static Instruction cancelTimelock(final AccountMeta invokedLoopscaleProgramMeta,
                                           final PublicKey bsAuthKey,
                                           final PublicKey payerKey,
                                           final PublicKey managerKey,
                                           final PublicKey vaultKey,
                                           final PublicKey timelockKey,
                                           final PublicKey systemProgramKey,
                                           final PublicKey eventAuthorityKey,
                                           final PublicKey programKey) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(managerKey),
      createWrite(vaultKey),
      createWrite(timelockKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, CANCEL_TIMELOCK_DISCRIMINATOR);
  }

  public static final Discriminator CLAIM_VAULT_FEE_DISCRIMINATOR = toDiscriminator(38, 40, 51, 195, 130, 248, 134, 247);

  // 9.1.2 vault manager actions
  public static Instruction claimVaultFee(final AccountMeta invokedLoopscaleProgramMeta,
                                          final PublicKey bsAuthKey,
                                          final PublicKey payerKey,
                                          final PublicKey managerKey,
                                          final PublicKey vaultKey,
                                          final PublicKey strategyKey,
                                          final PublicKey principalMintKey,
                                          final PublicKey marketInformationKey,
                                          final PublicKey managerPrincipalTaKey,
                                          final PublicKey strategyPrincipalTaKey,
                                          final PublicKey tokenProgramKey,
                                          final PublicKey associatedTokenProgramKey,
                                          final PublicKey systemProgramKey,
                                          final PublicKey eventAuthorityKey,
                                          final PublicKey programKey,
                                          final ClaimVaultFeeParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(managerKey),
      createWrite(vaultKey),
      createWrite(strategyKey),
      createRead(principalMintKey),
      createRead(marketInformationKey),
      createWrite(managerPrincipalTaKey),
      createWrite(strategyPrincipalTaKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(CLAIM_VAULT_FEE_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record ClaimVaultFeeIxData(Discriminator discriminator, ClaimVaultFeeParams params) implements Borsh {  

    public static ClaimVaultFeeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static ClaimVaultFeeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = ClaimVaultFeeParams.read(_data, i);
      return new ClaimVaultFeeIxData(discriminator, params);
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

  public static final Discriminator CLAIM_VAULT_REWARDS_DISCRIMINATOR = toDiscriminator(0, 152, 75, 29, 195, 223, 12, 101);

  // 9.1.1.5 vault user claim rewards
  public static Instruction claimVaultRewards(final AccountMeta invokedLoopscaleProgramMeta,
                                              final PublicKey bsAuthKey,
                                              final PublicKey payerKey,
                                              final PublicKey userKey,
                                              final PublicKey vaultKey,
                                              final PublicKey vaultRewardsInfoKey,
                                              final PublicKey userRewardsInfoKey,
                                              final PublicKey stakeAccountKey,
                                              final PublicKey associatedTokenProgramKey,
                                              final PublicKey systemProgramKey,
                                              final PublicKey eventAuthorityKey,
                                              final PublicKey programKey,
                                              final PublicKey[] mints) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createRead(userKey),
      createRead(vaultKey),
      createWrite(vaultRewardsInfoKey),
      createWrite(userRewardsInfoKey),
      createWrite(stakeAccountKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.lenVector(mints)];
    int i = writeDiscriminator(CLAIM_VAULT_REWARDS_DISCRIMINATOR, _data, 0);
    Borsh.writeVector(mints, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record ClaimVaultRewardsIxData(Discriminator discriminator, PublicKey[] mints) implements Borsh {  

    public static ClaimVaultRewardsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ClaimVaultRewardsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mints = Borsh.readPublicKeyVector(_data, i);
      return new ClaimVaultRewardsIxData(discriminator, mints);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(mints, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(mints);
    }
  }

  public static final Discriminator CLOSE_LOAN_DISCRIMINATOR = toDiscriminator(96, 114, 111, 204, 149, 228, 235, 124);

  // 1.4 close loan
  public static Instruction closeLoan(final AccountMeta invokedLoopscaleProgramMeta,
                                      final PublicKey bsAuthKey,
                                      final PublicKey payerKey,
                                      final PublicKey borrowerKey,
                                      final PublicKey loanKey,
                                      final PublicKey systemProgramKey,
                                      final PublicKey eventAuthorityKey,
                                      final PublicKey programKey) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(borrowerKey),
      createWrite(loanKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, CLOSE_LOAN_DISCRIMINATOR);
  }

  public static final Discriminator CLOSE_STRATEGY_DISCRIMINATOR = toDiscriminator(56, 247, 170, 246, 89, 221, 134, 200);

  // 8.5 close strategy
  public static Instruction closeStrategy(final AccountMeta invokedLoopscaleProgramMeta,
                                          final PublicKey bsAuthKey,
                                          final PublicKey payerKey,
                                          final PublicKey lenderKey,
                                          final PublicKey strategyKey,
                                          final PublicKey principalMintKey,
                                          final PublicKey tokenProgramKey,
                                          final PublicKey associatedTokenProgramKey,
                                          final PublicKey systemProgramKey,
                                          final PublicKey eventAuthorityKey,
                                          final PublicKey programKey) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(lenderKey),
      createWrite(strategyKey),
      createRead(principalMintKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, CLOSE_STRATEGY_DISCRIMINATOR);
  }

  public static final Discriminator CREATE_LOAN_DISCRIMINATOR = toDiscriminator(166, 131, 118, 219, 138, 218, 206, 140);

  // creditbook instructionss
  // 
  // 1. loan instructions
  // 1.1 create loan
  public static Instruction createLoan(final AccountMeta invokedLoopscaleProgramMeta,
                                       final PublicKey bsAuthKey,
                                       final PublicKey payerKey,
                                       final PublicKey borrowerKey,
                                       final PublicKey loanKey,
                                       final PublicKey systemProgramKey,
                                       final PublicKey eventAuthorityKey,
                                       final PublicKey programKey,
                                       final CreateLoanParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(borrowerKey),
      createWrite(loanKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(CREATE_LOAN_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record CreateLoanIxData(Discriminator discriminator, CreateLoanParams params) implements Borsh {  

    public static CreateLoanIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static CreateLoanIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = CreateLoanParams.read(_data, i);
      return new CreateLoanIxData(discriminator, params);
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

  public static final Discriminator CREATE_MARKET_INFORMATION_DISCRIMINATOR = toDiscriminator(246, 45, 227, 173, 15, 51, 85, 1);

  // 7. oracle instructions
  // 7.1 create market information account
  public static Instruction createMarketInformation(final AccountMeta invokedLoopscaleProgramMeta,
                                                    final PublicKey bsAuthKey,
                                                    final PublicKey marketInformationKey,
                                                    final PublicKey systemProgramKey,
                                                    final CreateMarketInformationParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWrite(marketInformationKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(CREATE_MARKET_INFORMATION_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record CreateMarketInformationIxData(Discriminator discriminator, CreateMarketInformationParams params) implements Borsh {  

    public static CreateMarketInformationIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 72;

    public static CreateMarketInformationIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = CreateMarketInformationParams.read(_data, i);
      return new CreateMarketInformationIxData(discriminator, params);
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

  public static final Discriminator CREATE_REWARDS_SCHEDULE_DISCRIMINATOR = toDiscriminator(201, 90, 205, 53, 85, 60, 229, 176);

  // 9.5 reward management instructions
  // 9.5.1 create rewards schedule
  public static Instruction createRewardsSchedule(final AccountMeta invokedLoopscaleProgramMeta,
                                                  final PublicKey bsAuthKey,
                                                  final PublicKey payerKey,
                                                  final PublicKey managerKey,
                                                  final PublicKey rewardsSourceKey,
                                                  final PublicKey vaultKey,
                                                  final PublicKey vaultRewardsInfoKey,
                                                  final PublicKey vaultRewardsMintKey,
                                                  final PublicKey vaultRewardsTaKey,
                                                  final PublicKey rewardsSourceTaKey,
                                                  final PublicKey tokenProgramKey,
                                                  final PublicKey associatedTokenProgramKey,
                                                  final PublicKey systemProgramKey,
                                                  final PublicKey eventAuthorityKey,
                                                  final PublicKey programKey,
                                                  final CreateRewardsScheduleParams params,
                                                  final long amountToTransfer) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(managerKey),
      createReadOnlySigner(rewardsSourceKey),
      createWrite(vaultKey),
      createWrite(vaultRewardsInfoKey),
      createRead(vaultRewardsMintKey),
      createWrite(vaultRewardsTaKey),
      createWrite(rewardsSourceTaKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16 + Borsh.len(params)];
    int i = writeDiscriminator(CREATE_REWARDS_SCHEDULE_DISCRIMINATOR, _data, 0);
    i += Borsh.write(params, _data, i);
    putInt64LE(_data, i, amountToTransfer);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record CreateRewardsScheduleIxData(Discriminator discriminator, CreateRewardsScheduleParams params, long amountToTransfer) implements Borsh {  

    public static CreateRewardsScheduleIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 76;

    public static CreateRewardsScheduleIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = CreateRewardsScheduleParams.read(_data, i);
      i += Borsh.len(params);
      final var amountToTransfer = getInt64LE(_data, i);
      return new CreateRewardsScheduleIxData(discriminator, params, amountToTransfer);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      putInt64LE(_data, i, amountToTransfer);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CREATE_STRATEGY_DISCRIMINATOR = toDiscriminator(152, 160, 107, 148, 245, 190, 127, 224);

  // 8. strategy instructions
  // 8.1 create strategy
  public static Instruction createStrategy(final AccountMeta invokedLoopscaleProgramMeta,
                                           final PublicKey bsAuthKey,
                                           final PublicKey payerKey,
                                           final PublicKey nonceKey,
                                           final PublicKey strategyKey,
                                           final PublicKey marketInformationKey,
                                           final PublicKey principalMintKey,
                                           final PublicKey systemProgramKey,
                                           final PublicKey eventAuthorityKey,
                                           final PublicKey programKey,
                                           final CreateStrategyParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(nonceKey),
      createWrite(strategyKey),
      createRead(marketInformationKey),
      createRead(principalMintKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(CREATE_STRATEGY_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record CreateStrategyIxData(Discriminator discriminator, CreateStrategyParams params) implements Borsh {  

    public static CreateStrategyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static CreateStrategyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = CreateStrategyParams.read(_data, i);
      return new CreateStrategyIxData(discriminator, params);
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

  public static final Discriminator CREATE_TIMELOCK_DISCRIMINATOR = toDiscriminator(243, 10, 110, 170, 71, 251, 210, 87);

  // 9.2 timelock instructions
  // 9.2.1 timelock create
  public static Instruction createTimelock(final AccountMeta invokedLoopscaleProgramMeta,
                                           final PublicKey bsAuthKey,
                                           final PublicKey payerKey,
                                           final PublicKey managerKey,
                                           final PublicKey vaultKey,
                                           final PublicKey timelockKey,
                                           final PublicKey vaultMarketInformationKey,
                                           final PublicKey externalMarketInformationKey,
                                           final PublicKey systemProgramKey,
                                           final PublicKey eventAuthorityKey,
                                           final PublicKey programKey,
                                           final TimelockUpdateParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(managerKey),
      createWrite(vaultKey),
      createWritableSigner(timelockKey),
      createWrite(vaultMarketInformationKey),
      createRead(externalMarketInformationKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(CREATE_TIMELOCK_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record CreateTimelockIxData(Discriminator discriminator, TimelockUpdateParams params) implements Borsh {  

    public static CreateTimelockIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static CreateTimelockIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = TimelockUpdateParams.read(_data, i);
      return new CreateTimelockIxData(discriminator, params);
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

  public static final Discriminator CREATE_VAULT_DISCRIMINATOR = toDiscriminator(29, 237, 247, 208, 193, 82, 54, 135);

  // 9.3 create vault
  public static Instruction createVault(final AccountMeta invokedLoopscaleProgramMeta,
                                        final PublicKey bsAuthKey,
                                        final PublicKey payerKey,
                                        final PublicKey nonceKey,
                                        final PublicKey principalMintKey,
                                        final PublicKey lpMintKey,
                                        final PublicKey vaultKey,
                                        final PublicKey strategyKey,
                                        final PublicKey marketInformationKey,
                                        final PublicKey tokenProgramKey,
                                        final PublicKey systemProgramKey,
                                        final PublicKey eventAuthorityKey,
                                        final PublicKey programKey,
                                        final CreateVaultParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(nonceKey),
      createRead(principalMintKey),
      createWritableSigner(lpMintKey),
      createWrite(vaultKey),
      createWrite(strategyKey),
      createWrite(marketInformationKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(CREATE_VAULT_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record CreateVaultIxData(Discriminator discriminator, CreateVaultParams params) implements Borsh {  

    public static CreateVaultIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static CreateVaultIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = CreateVaultParams.read(_data, i);
      return new CreateVaultIxData(discriminator, params);
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

  public static final Discriminator DEPOSIT_COLLATERAL_DISCRIMINATOR = toDiscriminator(156, 131, 142, 116, 146, 247, 162, 120);

  // collateral instructions
  // 5.
  // 5.1. deposit collateral
  public static Instruction depositCollateral(final AccountMeta invokedLoopscaleProgramMeta,
                                              final PublicKey bsAuthKey,
                                              final PublicKey payerKey,
                                              final PublicKey borrowerKey,
                                              final PublicKey loanKey,
                                              final PublicKey borrowerCollateralTaKey,
                                              final PublicKey loanCollateralTaKey,
                                              final PublicKey depositMintKey,
                                              // CHECKs: checks in constraint
                                              final PublicKey assetIdentifierKey,
                                              final PublicKey systemProgramKey,
                                              final PublicKey tokenProgramKey,
                                              final PublicKey associatedTokenProgramKey,
                                              final PublicKey eventAuthorityKey,
                                              final PublicKey programKey,
                                              final DepositCollateralParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createWritableSigner(borrowerKey),
      createWrite(loanKey),
      createWrite(borrowerCollateralTaKey),
      createWrite(loanCollateralTaKey),
      createRead(depositMintKey),
      createRead(assetIdentifierKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(DEPOSIT_COLLATERAL_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record DepositCollateralIxData(Discriminator discriminator, DepositCollateralParams params) implements Borsh {  

    public static DepositCollateralIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static DepositCollateralIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = DepositCollateralParams.read(_data, i);
      return new DepositCollateralIxData(discriminator, params);
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

  public static final Discriminator DEPOSIT_STRATEGY_DISCRIMINATOR = toDiscriminator(246, 82, 57, 226, 131, 222, 253, 249);

  // 8.2 deposit strategy
  public static Instruction depositStrategy(final AccountMeta invokedLoopscaleProgramMeta,
                                            final PublicKey bsAuthKey,
                                            final PublicKey payerKey,
                                            final PublicKey lenderKey,
                                            final PublicKey strategyKey,
                                            final PublicKey principalMintKey,
                                            final PublicKey marketInformationKey,
                                            final PublicKey lenderTaKey,
                                            final PublicKey strategyTaKey,
                                            final PublicKey tokenProgramKey,
                                            final PublicKey associatedTokenProgramKey,
                                            final PublicKey systemProgramKey,
                                            final PublicKey eventAuthorityKey,
                                            final PublicKey programKey,
                                            final long amount) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createWritableSigner(lenderKey),
      createWrite(strategyKey),
      createRead(principalMintKey),
      createRead(marketInformationKey),
      createWrite(lenderTaKey),
      createWrite(strategyTaKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(DEPOSIT_STRATEGY_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record DepositStrategyIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static DepositStrategyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static DepositStrategyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new DepositStrategyIxData(discriminator, amount);
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

  public static final Discriminator DEPOSIT_USER_VAULT_DISCRIMINATOR = toDiscriminator(204, 190, 182, 224, 15, 219, 247, 121);

  // 9. vault instructions
  // 9.1 vault actions
  // 9.1.1 vault user actions
  // 9.1.1.1 vault user deposit
  public static Instruction depositUserVault(final AccountMeta invokedLoopscaleProgramMeta,
                                             final PublicKey bsAuthKey,
                                             final PublicKey payerKey,
                                             final PublicKey userKey,
                                             final PublicKey vaultKey,
                                             final PublicKey strategyKey,
                                             final PublicKey marketInformationKey,
                                             final PublicKey lpMintKey,
                                             final PublicKey userLpTaKey,
                                             final PublicKey userPrincipalTaKey,
                                             final PublicKey strategyPrincipalTaKey,
                                             final PublicKey principalMintKey,
                                             final PublicKey principalTokenProgramKey,
                                             final PublicKey token2022ProgramKey,
                                             final PublicKey associatedTokenProgramKey,
                                             final PublicKey systemProgramKey,
                                             final PublicKey eventAuthorityKey,
                                             final PublicKey programKey,
                                             final LpParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createWritableSigner(userKey),
      createWrite(vaultKey),
      createWrite(strategyKey),
      createRead(marketInformationKey),
      createWrite(lpMintKey),
      createWrite(userLpTaKey),
      createWrite(userPrincipalTaKey),
      createWrite(strategyPrincipalTaKey),
      createRead(principalMintKey),
      createRead(principalTokenProgramKey),
      createRead(token2022ProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(DEPOSIT_USER_VAULT_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record DepositUserVaultIxData(Discriminator discriminator, LpParams params) implements Borsh {  

    public static DepositUserVaultIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static DepositUserVaultIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = LpParams.read(_data, i);
      return new DepositUserVaultIxData(discriminator, params);
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

  public static final Discriminator EXECUTE_TIMELOCK_DISCRIMINATOR = toDiscriminator(160, 194, 240, 8, 212, 93, 157, 221);

  // 9.2.2 timelock execute
  public static Instruction executeTimelock(final AccountMeta invokedLoopscaleProgramMeta,
                                            final PublicKey bsAuthKey,
                                            final PublicKey payerKey,
                                            final PublicKey vaultKey,
                                            final PublicKey timelockKey,
                                            final PublicKey strategyKey,
                                            final PublicKey marketInformationKey,
                                            final PublicKey strategyTaKey,
                                            final PublicKey systemProgramKey,
                                            final PublicKey tokenProgramKey,
                                            final PublicKey associatedTokenProgramKey,
                                            final PublicKey principalMintKey,
                                            final PublicKey eventAuthorityKey,
                                            final PublicKey programKey) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createWrite(vaultKey),
      createWrite(timelockKey),
      createWrite(strategyKey),
      createWrite(marketInformationKey),
      createWrite(strategyTaKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(principalMintKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, EXECUTE_TIMELOCK_DISCRIMINATOR);
  }

  public static final Discriminator LIQUIDATE_LEDGER_DISCRIMINATOR = toDiscriminator(5, 124, 101, 85, 254, 175, 184, 249);

  // 2. liquidate ledger
  public static Instruction liquidateLedger(final AccountMeta invokedLoopscaleProgramMeta,
                                            final PublicKey payerKey,
                                            final PublicKey liquidatorKey,
                                            final PublicKey borrowerKey,
                                            final PublicKey loanKey,
                                            final PublicKey strategyKey,
                                            final PublicKey marketInformationKey,
                                            final PublicKey liquidatorTaKey,
                                            final PublicKey strategyTaKey,
                                            final PublicKey principalMintKey,
                                            final PublicKey associatedTokenProgramKey,
                                            final PublicKey tokenProgramKey,
                                            final PublicKey token2022ProgramKey,
                                            final PublicKey systemProgramKey,
                                            final PublicKey eventAuthorityKey,
                                            final PublicKey programKey,
                                            final LiquidateLedgerParams params) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createWritableSigner(liquidatorKey),
      createWrite(borrowerKey),
      createWrite(loanKey),
      createWrite(strategyKey),
      createWrite(marketInformationKey),
      createWrite(liquidatorTaKey),
      createWrite(strategyTaKey),
      createRead(principalMintKey),
      createRead(associatedTokenProgramKey),
      createRead(tokenProgramKey),
      createRead(token2022ProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(LIQUIDATE_LEDGER_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record LiquidateLedgerIxData(Discriminator discriminator, LiquidateLedgerParams params) implements Borsh {  

    public static LiquidateLedgerIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static LiquidateLedgerIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = LiquidateLedgerParams.read(_data, i);
      return new LiquidateLedgerIxData(discriminator, params);
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

  public static final Discriminator LOCK_LOAN_DISCRIMINATOR = toDiscriminator(28, 101, 52, 240, 146, 230, 95, 22);

  // 1.2 lock loan
  public static Instruction lockLoan(final AccountMeta invokedLoopscaleProgramMeta,
                                     final PublicKey bsAuthKey,
                                     final PublicKey payerKey,
                                     final PublicKey borrowerKey,
                                     final PublicKey loanKey,
                                     final PublicKey instructionsSysvarKey,
                                     final PublicKey systemProgramKey,
                                     final LockLoanParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(borrowerKey),
      createWrite(loanKey),
      createRead(instructionsSysvarKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(LOCK_LOAN_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record LockLoanIxData(Discriminator discriminator, LockLoanParams params) implements Borsh {  

    public static LockLoanIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static LockLoanIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = LockLoanParams.read(_data, i);
      return new LockLoanIxData(discriminator, params);
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

  public static final Discriminator MANAGE_COLLATERAL_CLAIM_ORCA_FEE_DISCRIMINATOR = toDiscriminator(242, 48, 127, 91, 24, 187, 211, 234);

  // 5.3. orca deposit collateral
  // manage collateral instructions
  // 5.3
  // 5.3.1 orca claim fee
  public static Instruction manageCollateralClaimOrcaFee(final AccountMeta invokedLoopscaleProgramMeta,
                                                         final PublicKey bsAuthKey,
                                                         final PublicKey payerKey,
                                                         final PublicKey borrowerKey,
                                                         final PublicKey loanKey,
                                                         final PublicKey whirlpoolKey,
                                                         final PublicKey tokenVaultAKey,
                                                         final PublicKey tokenVaultBKey,
                                                         final PublicKey tickArrayLowerKey,
                                                         final PublicKey tickArrayUpperKey,
                                                         final PublicKey positionKey,
                                                         final PublicKey positionTokenAccountKey,
                                                         final PublicKey borrowerTaAKey,
                                                         final PublicKey borrowerTaBKey,
                                                         final PublicKey loanTaAKey,
                                                         final PublicKey loanTaBKey,
                                                         final PublicKey mintAKey,
                                                         final PublicKey mintBKey,
                                                         final PublicKey whirlpoolProgramKey,
                                                         final PublicKey tokenProgramKey,
                                                         final PublicKey token2022ProgramKey,
                                                         final PublicKey associatedTokenProgramKey,
                                                         final PublicKey systemProgramKey,
                                                         final PublicKey memoProgramKey,
                                                         final PublicKey eventAuthorityKey,
                                                         final PublicKey programKey,
                                                         final boolean closeTa) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(borrowerKey),
      createRead(loanKey),
      createWrite(whirlpoolKey),
      createWrite(tokenVaultAKey),
      createWrite(tokenVaultBKey),
      createWrite(tickArrayLowerKey),
      createWrite(tickArrayUpperKey),
      createWrite(positionKey),
      createWrite(positionTokenAccountKey),
      createWrite(borrowerTaAKey),
      createWrite(borrowerTaBKey),
      createWrite(loanTaAKey),
      createWrite(loanTaBKey),
      createRead(mintAKey),
      createRead(mintBKey),
      createRead(whirlpoolProgramKey),
      createRead(tokenProgramKey),
      createRead(token2022ProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(memoProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(MANAGE_COLLATERAL_CLAIM_ORCA_FEE_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) (closeTa ? 1 : 0);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record ManageCollateralClaimOrcaFeeIxData(Discriminator discriminator, boolean closeTa) implements Borsh {  

    public static ManageCollateralClaimOrcaFeeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static ManageCollateralClaimOrcaFeeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var closeTa = _data[i] == 1;
      return new ManageCollateralClaimOrcaFeeIxData(discriminator, closeTa);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) (closeTa ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator MANAGE_COLLATERAL_DECREASE_RAYDIUM_LIQUIDITY_DISCRIMINATOR = toDiscriminator(203, 192, 202, 215, 108, 54, 90, 202);

  // 5.3.5 raydium decrease liquidity
  public static Instruction manageCollateralDecreaseRaydiumLiquidity(final AccountMeta invokedLoopscaleProgramMeta,
                                                                     final PublicKey bsAuthKey,
                                                                     final PublicKey payerKey,
                                                                     final PublicKey borrowerKey,
                                                                     final PublicKey loanKey,
                                                                     final PublicKey poolKey,
                                                                     final PublicKey protocolPositionKey,
                                                                     final PublicKey positionKey,
                                                                     final PublicKey positionTokenAccountKey,
                                                                     final PublicKey tickArrayLowerKey,
                                                                     final PublicKey tickArrayUpperKey,
                                                                     final PublicKey tokenVault0Key,
                                                                     final PublicKey tokenVault1Key,
                                                                     final PublicKey borrowerTa0Key,
                                                                     final PublicKey borrowerTa1Key,
                                                                     final PublicKey loanTa0Key,
                                                                     final PublicKey loanTa1Key,
                                                                     final PublicKey mint0Key,
                                                                     final PublicKey mint1Key,
                                                                     final PublicKey raydiumProgramKey,
                                                                     final PublicKey tokenProgramKey,
                                                                     final PublicKey token2022ProgramKey,
                                                                     final PublicKey associatedTokenProgramKey,
                                                                     final PublicKey systemProgramKey,
                                                                     final PublicKey memoProgramKey,
                                                                     final PublicKey eventAuthorityKey,
                                                                     final PublicKey programKey,
                                                                     final ManageRaydiumLiquidityParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(borrowerKey),
      createRead(loanKey),
      createWrite(poolKey),
      createWrite(protocolPositionKey),
      createWrite(positionKey),
      createRead(positionTokenAccountKey),
      createWrite(tickArrayLowerKey),
      createWrite(tickArrayUpperKey),
      createWrite(tokenVault0Key),
      createWrite(tokenVault1Key),
      createWrite(borrowerTa0Key),
      createWrite(borrowerTa1Key),
      createWrite(loanTa0Key),
      createWrite(loanTa1Key),
      createRead(mint0Key),
      createRead(mint1Key),
      createRead(raydiumProgramKey),
      createRead(tokenProgramKey),
      createRead(token2022ProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(memoProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(MANAGE_COLLATERAL_DECREASE_RAYDIUM_LIQUIDITY_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record ManageCollateralDecreaseRaydiumLiquidityIxData(Discriminator discriminator, ManageRaydiumLiquidityParams params) implements Borsh {  

    public static ManageCollateralDecreaseRaydiumLiquidityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ManageCollateralDecreaseRaydiumLiquidityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = ManageRaydiumLiquidityParams.read(_data, i);
      return new ManageCollateralDecreaseRaydiumLiquidityIxData(discriminator, params);
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

  public static final Discriminator MANAGE_COLLATERAL_INCREASE_ORCA_LIQUIDITY_DISCRIMINATOR = toDiscriminator(173, 62, 204, 113, 206, 27, 147, 128);

  // 5.3.2 orca increase liquidity
  public static Instruction manageCollateralIncreaseOrcaLiquidity(final AccountMeta invokedLoopscaleProgramMeta,
                                                                  final PublicKey bsAuthKey,
                                                                  final PublicKey payerKey,
                                                                  final PublicKey borrowerKey,
                                                                  final PublicKey loanKey,
                                                                  final PublicKey whirlpoolKey,
                                                                  final PublicKey positionKey,
                                                                  final PublicKey positionTokenAccountKey,
                                                                  final PublicKey tickArrayLowerKey,
                                                                  final PublicKey tickArrayUpperKey,
                                                                  final PublicKey tokenVaultAKey,
                                                                  final PublicKey tokenVaultBKey,
                                                                  final PublicKey borrowerTaAKey,
                                                                  final PublicKey borrowerTaBKey,
                                                                  final PublicKey loanTaAKey,
                                                                  final PublicKey loanTaBKey,
                                                                  final PublicKey mintAKey,
                                                                  final PublicKey mintBKey,
                                                                  final PublicKey whirlpoolProgramKey,
                                                                  final PublicKey tokenProgramKey,
                                                                  final PublicKey token2022ProgramKey,
                                                                  final PublicKey associatedTokenProgramKey,
                                                                  final PublicKey systemProgramKey,
                                                                  final PublicKey memoProgramKey,
                                                                  final PublicKey eventAuthorityKey,
                                                                  final PublicKey programKey,
                                                                  final ManageLiquidityParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(borrowerKey),
      createRead(loanKey),
      createWrite(whirlpoolKey),
      createWrite(positionKey),
      createRead(positionTokenAccountKey),
      createWrite(tickArrayLowerKey),
      createWrite(tickArrayUpperKey),
      createWrite(tokenVaultAKey),
      createWrite(tokenVaultBKey),
      createWrite(borrowerTaAKey),
      createWrite(borrowerTaBKey),
      createWrite(loanTaAKey),
      createWrite(loanTaBKey),
      createRead(mintAKey),
      createRead(mintBKey),
      createRead(whirlpoolProgramKey),
      createRead(tokenProgramKey),
      createRead(token2022ProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(memoProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(MANAGE_COLLATERAL_INCREASE_ORCA_LIQUIDITY_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record ManageCollateralIncreaseOrcaLiquidityIxData(Discriminator discriminator, ManageLiquidityParams params) implements Borsh {  

    public static ManageCollateralIncreaseOrcaLiquidityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ManageCollateralIncreaseOrcaLiquidityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = ManageLiquidityParams.read(_data, i);
      return new ManageCollateralIncreaseOrcaLiquidityIxData(discriminator, params);
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

  public static final Discriminator MANAGE_COLLATERAL_INCREASE_RAYDIUM_LIQUIDITY_DISCRIMINATOR = toDiscriminator(54, 205, 137, 146, 40, 175, 141, 20);

  // 5.3.6 raydium increase liquidity
  public static Instruction manageCollateralIncreaseRaydiumLiquidity(final AccountMeta invokedLoopscaleProgramMeta,
                                                                     final PublicKey bsAuthKey,
                                                                     final PublicKey payerKey,
                                                                     final PublicKey borrowerKey,
                                                                     final PublicKey loanKey,
                                                                     final PublicKey poolKey,
                                                                     final PublicKey protocolPositionKey,
                                                                     final PublicKey positionKey,
                                                                     final PublicKey positionTokenAccountKey,
                                                                     final PublicKey tickArrayLowerKey,
                                                                     final PublicKey tickArrayUpperKey,
                                                                     final PublicKey tokenVault0Key,
                                                                     final PublicKey tokenVault1Key,
                                                                     final PublicKey borrowerTa0Key,
                                                                     final PublicKey borrowerTa1Key,
                                                                     final PublicKey loanTa0Key,
                                                                     final PublicKey loanTa1Key,
                                                                     final PublicKey mint0Key,
                                                                     final PublicKey mint1Key,
                                                                     final PublicKey raydiumProgramKey,
                                                                     final PublicKey tokenProgramKey,
                                                                     final PublicKey token2022ProgramKey,
                                                                     final PublicKey associatedTokenProgramKey,
                                                                     final PublicKey systemProgramKey,
                                                                     final PublicKey memoProgramKey,
                                                                     final PublicKey eventAuthorityKey,
                                                                     final PublicKey programKey,
                                                                     final ManageRaydiumLiquidityParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(borrowerKey),
      createRead(loanKey),
      createWrite(poolKey),
      createWrite(protocolPositionKey),
      createWrite(positionKey),
      createRead(positionTokenAccountKey),
      createWrite(tickArrayLowerKey),
      createWrite(tickArrayUpperKey),
      createWrite(tokenVault0Key),
      createWrite(tokenVault1Key),
      createWrite(borrowerTa0Key),
      createWrite(borrowerTa1Key),
      createWrite(loanTa0Key),
      createWrite(loanTa1Key),
      createRead(mint0Key),
      createRead(mint1Key),
      createRead(raydiumProgramKey),
      createRead(tokenProgramKey),
      createRead(token2022ProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(memoProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(MANAGE_COLLATERAL_INCREASE_RAYDIUM_LIQUIDITY_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record ManageCollateralIncreaseRaydiumLiquidityIxData(Discriminator discriminator, ManageRaydiumLiquidityParams params) implements Borsh {  

    public static ManageCollateralIncreaseRaydiumLiquidityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ManageCollateralIncreaseRaydiumLiquidityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = ManageRaydiumLiquidityParams.read(_data, i);
      return new ManageCollateralIncreaseRaydiumLiquidityIxData(discriminator, params);
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

  public static final Discriminator MANAGE_COLLATERAL_TRANSFER_ORCA_POSITION_DISCRIMINATOR = toDiscriminator(85, 151, 110, 243, 164, 41, 62, 238);

  // 5.3.4 orca transfer position
  public static Instruction manageCollateralTransferOrcaPosition(final AccountMeta invokedLoopscaleProgramMeta,
                                                                 final PublicKey bsAuthKey,
                                                                 final PublicKey payerKey,
                                                                 final PublicKey borrowerKey,
                                                                 final PublicKey loanKey,
                                                                 final PublicKey whirlpoolKey,
                                                                 final PublicKey positionKey,
                                                                 final PublicKey positionTokenAccountKey,
                                                                 final PublicKey positionMintKey,
                                                                 final PublicKey tickArrayLowerKey,
                                                                 final PublicKey tickArrayUpperKey,
                                                                 final PublicKey newTickArrayLowerKey,
                                                                 final PublicKey newTickArrayUpperKey,
                                                                 final PublicKey tokenVaultAKey,
                                                                 final PublicKey tokenVaultBKey,
                                                                 final PublicKey borrowerTaAKey,
                                                                 final PublicKey borrowerTaBKey,
                                                                 final PublicKey loanTaAKey,
                                                                 final PublicKey loanTaBKey,
                                                                 final PublicKey newPositionKey,
                                                                 final PublicKey newPositionMintKey,
                                                                 final PublicKey newPositionTokenAccountKey,
                                                                 final PublicKey mintAKey,
                                                                 final PublicKey mintBKey,
                                                                 final PublicKey metadataUpdateAuthKey,
                                                                 final PublicKey whirlpoolProgramKey,
                                                                 final PublicKey tokenProgramKey,
                                                                 final PublicKey token2022ProgramKey,
                                                                 final PublicKey associatedTokenProgramKey,
                                                                 final PublicKey systemProgramKey,
                                                                 final PublicKey memoProgramKey,
                                                                 final PublicKey eventAuthorityKey,
                                                                 final PublicKey programKey,
                                                                 final TransferPositionParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(borrowerKey),
      createWrite(loanKey),
      createWrite(whirlpoolKey),
      createWrite(positionKey),
      createWrite(positionTokenAccountKey),
      createWrite(positionMintKey),
      createWrite(tickArrayLowerKey),
      createWrite(tickArrayUpperKey),
      createWrite(newTickArrayLowerKey),
      createWrite(newTickArrayUpperKey),
      createWrite(tokenVaultAKey),
      createWrite(tokenVaultBKey),
      createWrite(borrowerTaAKey),
      createWrite(borrowerTaBKey),
      createWrite(loanTaAKey),
      createWrite(loanTaBKey),
      createWrite(newPositionKey),
      createWritableSigner(newPositionMintKey),
      createWrite(newPositionTokenAccountKey),
      createRead(mintAKey),
      createRead(mintBKey),
      createRead(metadataUpdateAuthKey),
      createRead(whirlpoolProgramKey),
      createRead(tokenProgramKey),
      createRead(token2022ProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(memoProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(MANAGE_COLLATERAL_TRANSFER_ORCA_POSITION_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record ManageCollateralTransferOrcaPositionIxData(Discriminator discriminator, TransferPositionParams params) implements Borsh {  

    public static ManageCollateralTransferOrcaPositionIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ManageCollateralTransferOrcaPositionIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = TransferPositionParams.read(_data, i);
      return new ManageCollateralTransferOrcaPositionIxData(discriminator, params);
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

  public static final Discriminator MANAGE_COLLATERAL_TRANSFER_RAYDIUM_POSITION_DISCRIMINATOR = toDiscriminator(183, 215, 111, 172, 104, 250, 55, 219);

  // 5.3.7 raydium transfer position
  public static Instruction manageCollateralTransferRaydiumPosition(final AccountMeta invokedLoopscaleProgramMeta,
                                                                    final PublicKey bsAuthKey,
                                                                    final PublicKey payerKey,
                                                                    final PublicKey borrowerKey,
                                                                    final PublicKey loanKey,
                                                                    final PublicKey poolKey,
                                                                    final PublicKey protocolPositionOldKey,
                                                                    final PublicKey protocolPositionNewKey,
                                                                    final PublicKey positionKey,
                                                                    final PublicKey positionTokenAccountKey,
                                                                    final PublicKey positionMintKey,
                                                                    final PublicKey tickArrayLowerKey,
                                                                    final PublicKey tickArrayUpperKey,
                                                                    final PublicKey newTickArrayLowerKey,
                                                                    final PublicKey newTickArrayUpperKey,
                                                                    final PublicKey tokenVault0Key,
                                                                    final PublicKey tokenVault1Key,
                                                                    final PublicKey borrowerTa0Key,
                                                                    final PublicKey borrowerTa1Key,
                                                                    final PublicKey loanTa0Key,
                                                                    final PublicKey loanTa1Key,
                                                                    final PublicKey newPositionKey,
                                                                    final PublicKey newPositionMintKey,
                                                                    final PublicKey newPositionTokenAccountKey,
                                                                    final PublicKey mint0Key,
                                                                    final PublicKey mint1Key,
                                                                    final PublicKey raydiumProgramKey,
                                                                    final PublicKey tokenProgramKey,
                                                                    final PublicKey token2022ProgramKey,
                                                                    final PublicKey associatedTokenProgramKey,
                                                                    final PublicKey systemProgramKey,
                                                                    final PublicKey rentKey,
                                                                    final PublicKey memoProgramKey,
                                                                    final TransferPositionParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(borrowerKey),
      createWrite(loanKey),
      createWrite(poolKey),
      createWrite(protocolPositionOldKey),
      createWrite(protocolPositionNewKey),
      createWrite(positionKey),
      createWrite(positionTokenAccountKey),
      createWrite(positionMintKey),
      createWrite(tickArrayLowerKey),
      createWrite(tickArrayUpperKey),
      createWrite(newTickArrayLowerKey),
      createWrite(newTickArrayUpperKey),
      createWrite(tokenVault0Key),
      createWrite(tokenVault1Key),
      createWrite(borrowerTa0Key),
      createWrite(borrowerTa1Key),
      createWrite(loanTa0Key),
      createWrite(loanTa1Key),
      createWrite(newPositionKey),
      createWritableSigner(newPositionMintKey),
      createWrite(newPositionTokenAccountKey),
      createRead(mint0Key),
      createRead(mint1Key),
      createRead(raydiumProgramKey),
      createRead(tokenProgramKey),
      createRead(token2022ProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createRead(memoProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(MANAGE_COLLATERAL_TRANSFER_RAYDIUM_POSITION_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record ManageCollateralTransferRaydiumPositionIxData(Discriminator discriminator, TransferPositionParams params) implements Borsh {  

    public static ManageCollateralTransferRaydiumPositionIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ManageCollateralTransferRaydiumPositionIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = TransferPositionParams.read(_data, i);
      return new ManageCollateralTransferRaydiumPositionIxData(discriminator, params);
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

  public static final Discriminator MANAGE_COLLATERAL_WITHDRAW_ORCA_LIQUIDITY_DISCRIMINATOR = toDiscriminator(219, 89, 35, 158, 224, 12, 200, 90);

  // 5.3.3 orca withdraw liquidity
  public static Instruction manageCollateralWithdrawOrcaLiquidity(final AccountMeta invokedLoopscaleProgramMeta,
                                                                  final PublicKey bsAuthKey,
                                                                  final PublicKey payerKey,
                                                                  final PublicKey borrowerKey,
                                                                  final PublicKey loanKey,
                                                                  final PublicKey whirlpoolKey,
                                                                  final PublicKey positionKey,
                                                                  final PublicKey positionTokenAccountKey,
                                                                  final PublicKey tickArrayLowerKey,
                                                                  final PublicKey tickArrayUpperKey,
                                                                  final PublicKey tokenVaultAKey,
                                                                  final PublicKey tokenVaultBKey,
                                                                  final PublicKey borrowerTaAKey,
                                                                  final PublicKey borrowerTaBKey,
                                                                  final PublicKey loanTaAKey,
                                                                  final PublicKey loanTaBKey,
                                                                  final PublicKey mintAKey,
                                                                  final PublicKey mintBKey,
                                                                  final PublicKey whirlpoolProgramKey,
                                                                  final PublicKey tokenProgramKey,
                                                                  final PublicKey token2022ProgramKey,
                                                                  final PublicKey associatedTokenProgramKey,
                                                                  final PublicKey systemProgramKey,
                                                                  final PublicKey memoProgramKey,
                                                                  final PublicKey eventAuthorityKey,
                                                                  final PublicKey programKey,
                                                                  final ManageLiquidityParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(borrowerKey),
      createRead(loanKey),
      createWrite(whirlpoolKey),
      createWrite(positionKey),
      createRead(positionTokenAccountKey),
      createWrite(tickArrayLowerKey),
      createWrite(tickArrayUpperKey),
      createWrite(tokenVaultAKey),
      createWrite(tokenVaultBKey),
      createWrite(borrowerTaAKey),
      createWrite(borrowerTaBKey),
      createWrite(loanTaAKey),
      createWrite(loanTaBKey),
      createRead(mintAKey),
      createRead(mintBKey),
      createRead(whirlpoolProgramKey),
      createRead(tokenProgramKey),
      createRead(token2022ProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(memoProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(MANAGE_COLLATERAL_WITHDRAW_ORCA_LIQUIDITY_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record ManageCollateralWithdrawOrcaLiquidityIxData(Discriminator discriminator, ManageLiquidityParams params) implements Borsh {  

    public static ManageCollateralWithdrawOrcaLiquidityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ManageCollateralWithdrawOrcaLiquidityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = ManageLiquidityParams.read(_data, i);
      return new ManageCollateralWithdrawOrcaLiquidityIxData(discriminator, params);
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

  public static final Discriminator MIGRATE_MARKET_INFO_ALLOCATION_DISCRIMINATOR = toDiscriminator(112, 172, 64, 213, 227, 1, 86, 2);

  public static Instruction migrateMarketInfoAllocation(final AccountMeta invokedLoopscaleProgramMeta,
                                                        final PublicKey bsAuthKey,
                                                        final PublicKey marketInfoKey,
                                                        final PublicKey eventAuthorityKey,
                                                        final PublicKey programKey,
                                                        final CollateralAllocationParam[] allocations) {
    final var keys = List.of(
      createWritableSigner(bsAuthKey),
      createWrite(marketInfoKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.lenVector(allocations)];
    int i = writeDiscriminator(MIGRATE_MARKET_INFO_ALLOCATION_DISCRIMINATOR, _data, 0);
    Borsh.writeVector(allocations, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record MigrateMarketInfoAllocationIxData(Discriminator discriminator, CollateralAllocationParam[] allocations) implements Borsh {  

    public static MigrateMarketInfoAllocationIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static MigrateMarketInfoAllocationIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var allocations = Borsh.readVector(CollateralAllocationParam.class, CollateralAllocationParam::read, _data, i);
      return new MigrateMarketInfoAllocationIxData(discriminator, allocations);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(allocations, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(allocations);
    }
  }

  public static final Discriminator REFINANCE_LEDGER_DISCRIMINATOR = toDiscriminator(103, 41, 134, 43, 140, 152, 253, 74);

  // 3. refinance ledger
  public static Instruction refinanceLedger(final AccountMeta invokedLoopscaleProgramMeta,
                                            final PublicKey bsAuthKey,
                                            final PublicKey payerKey,
                                            final PublicKey loanKey,
                                            final PublicKey oldStrategyKey,
                                            final PublicKey newStrategyKey,
                                            final PublicKey oldStrategyTaKey,
                                            final PublicKey newStrategyTaKey,
                                            final PublicKey oldStrategyMarketInformationKey,
                                            final PublicKey newStrategyMarketInformationKey,
                                            final PublicKey principalMintKey,
                                            final PublicKey tokenProgramKey,
                                            final PublicKey associatedTokenProgramKey,
                                            final PublicKey systemProgramKey,
                                            final PublicKey eventAuthorityKey,
                                            final PublicKey programKey,
                                            final RefinanceLedgerParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createWrite(loanKey),
      createWrite(oldStrategyKey),
      createWrite(newStrategyKey),
      createWrite(oldStrategyTaKey),
      createWrite(newStrategyTaKey),
      createWrite(oldStrategyMarketInformationKey),
      createWrite(newStrategyMarketInformationKey),
      createRead(principalMintKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(REFINANCE_LEDGER_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record RefinanceLedgerIxData(Discriminator discriminator, RefinanceLedgerParams params) implements Borsh {  

    public static RefinanceLedgerIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static RefinanceLedgerIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = RefinanceLedgerParams.read(_data, i);
      return new RefinanceLedgerIxData(discriminator, params);
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

  public static final Discriminator REPAY_PRINCIPAL_DISCRIMINATOR = toDiscriminator(229, 67, 83, 65, 77, 84, 80, 141);

  // 6.2. repay principal
  public static Instruction repayPrincipal(final AccountMeta invokedLoopscaleProgramMeta,
                                           final PublicKey bsAuthKey,
                                           final PublicKey payerKey,
                                           final PublicKey borrowerKey,
                                           final PublicKey loanKey,
                                           final PublicKey strategyKey,
                                           final PublicKey marketInformationKey,
                                           final PublicKey principalMintKey,
                                           final PublicKey borrowerTaKey,
                                           final PublicKey strategyTaKey,
                                           final PublicKey associatedTokenProgramKey,
                                           final PublicKey tokenProgramKey,
                                           final PublicKey systemProgramKey,
                                           final PublicKey eventAuthorityKey,
                                           final PublicKey programKey,
                                           final RepayPrincipalParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(borrowerKey),
      createWrite(loanKey),
      createWrite(strategyKey),
      createWrite(marketInformationKey),
      createRead(principalMintKey),
      createWrite(borrowerTaKey),
      createWrite(strategyTaKey),
      createRead(associatedTokenProgramKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(REPAY_PRINCIPAL_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record RepayPrincipalIxData(Discriminator discriminator, RepayPrincipalParams params) implements Borsh {  

    public static RepayPrincipalIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 18;

    public static RepayPrincipalIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = RepayPrincipalParams.read(_data, i);
      return new RepayPrincipalIxData(discriminator, params);
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

  public static final Discriminator SELL_LEDGER_DISCRIMINATOR = toDiscriminator(55, 17, 153, 148, 120, 242, 80, 5);

  // 4. sell ledger
  public static Instruction sellLedger(final AccountMeta invokedLoopscaleProgramMeta,
                                       final PublicKey bsAuthKey,
                                       final PublicKey payerKey,
                                       final PublicKey lenderAuthKey,
                                       final PublicKey loanKey,
                                       final PublicKey newStrategyTaKey,
                                       final PublicKey lenderAuthTaKey,
                                       final PublicKey oldStrategyKey,
                                       final PublicKey newStrategyKey,
                                       final PublicKey oldStrategyMarketInformationKey,
                                       final PublicKey newStrategyMarketInformationKey,
                                       final PublicKey principalMintKey,
                                       final PublicKey tokenProgramKey,
                                       final PublicKey associatedTokenProgramKey,
                                       final PublicKey systemProgramKey,
                                       final PublicKey vaultKey,
                                       final PublicKey oldStrategyTaKey,
                                       final PublicKey eventAuthorityKey,
                                       final PublicKey programKey,
                                       final SellLedgerParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(lenderAuthKey),
      createWrite(loanKey),
      createWrite(newStrategyTaKey),
      createWrite(lenderAuthTaKey),
      createWrite(oldStrategyKey),
      createWrite(newStrategyKey),
      createWrite(oldStrategyMarketInformationKey),
      createWrite(newStrategyMarketInformationKey),
      createRead(principalMintKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createWrite(requireNonNullElse(vaultKey, invokedLoopscaleProgramMeta.publicKey())),
      createWrite(requireNonNullElse(oldStrategyTaKey, invokedLoopscaleProgramMeta.publicKey())),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(SELL_LEDGER_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record SellLedgerIxData(Discriminator discriminator, SellLedgerParams params) implements Borsh {  

    public static SellLedgerIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static SellLedgerIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SellLedgerParams.read(_data, i);
      return new SellLedgerIxData(discriminator, params);
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

  public static final Discriminator STAKE_USER_VAULT_LP_DISCRIMINATOR = toDiscriminator(114, 132, 194, 209, 208, 149, 43, 136);

  // 9.1.1.3 vault user stake
  public static Instruction stakeUserVaultLp(final AccountMeta invokedLoopscaleProgramMeta,
                                             final PublicKey bsAuthKey,
                                             final PublicKey payerKey,
                                             final PublicKey userKey,
                                             final PublicKey nonceKey,
                                             final PublicKey vaultKey,
                                             final PublicKey vaultStakeKey,
                                             final PublicKey lpMintKey,
                                             final PublicKey userLpTaKey,
                                             final PublicKey vaultStakeLpTaKey,
                                             final PublicKey vaultRewardsInfoKey,
                                             final PublicKey userRewardsInfoKey,
                                             final PublicKey tokenProgramKey,
                                             final PublicKey associatedTokenProgramKey,
                                             final PublicKey systemProgramKey,
                                             final PublicKey eventAuthorityKey,
                                             final PublicKey programKey,
                                             final VaultStakeParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(userKey),
      createReadOnlySigner(nonceKey),
      createWrite(vaultKey),
      createWrite(vaultStakeKey),
      createRead(lpMintKey),
      createWrite(userLpTaKey),
      createWrite(vaultStakeLpTaKey),
      createWrite(vaultRewardsInfoKey),
      createWrite(userRewardsInfoKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(STAKE_USER_VAULT_LP_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record StakeUserVaultLpIxData(Discriminator discriminator, VaultStakeParams params) implements Borsh {  

    public static StakeUserVaultLpIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static StakeUserVaultLpIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = VaultStakeParams.read(_data, i);
      return new StakeUserVaultLpIxData(discriminator, params);
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

  public static final Discriminator UNLOCK_LOAN_DISCRIMINATOR = toDiscriminator(121, 226, 178, 98, 215, 209, 240, 38);

  // 1.3 unlock loan
  public static Instruction unlockLoan(final AccountMeta invokedLoopscaleProgramMeta,
                                       final PublicKey borrowerKey,
                                       final PublicKey loanKey,
                                       final PublicKey payerKey,
                                       final PublicKey bsAuthKey,
                                       final PublicKey systemProgramKey,
                                       final LoanUnlockParams params) {
    final var keys = List.of(
      createReadOnlySigner(borrowerKey),
      createWrite(loanKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(bsAuthKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(UNLOCK_LOAN_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record UnlockLoanIxData(Discriminator discriminator, LoanUnlockParams params) implements Borsh {  

    public static UnlockLoanIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UnlockLoanIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = LoanUnlockParams.read(_data, i);
      return new UnlockLoanIxData(discriminator, params);
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

  public static final Discriminator UNSTAKE_USER_VAULT_LP_DISCRIMINATOR = toDiscriminator(83, 78, 230, 123, 226, 40, 158, 97);

  // 9.1.1.4 vault user unstake
  public static Instruction unstakeUserVaultLp(final AccountMeta invokedLoopscaleProgramMeta,
                                               final PublicKey bsAuthKey,
                                               final PublicKey payerKey,
                                               final PublicKey userKey,
                                               final PublicKey vaultKey,
                                               final PublicKey lpMintKey,
                                               final PublicKey vaultStakeKey,
                                               final PublicKey userLpTaKey,
                                               final PublicKey vaultStakeLpTaKey,
                                               final PublicKey vaultRewardsInfoKey,
                                               final PublicKey userRewardsInfoKey,
                                               final PublicKey tokenProgramKey,
                                               final PublicKey associatedTokenProgramKey,
                                               final PublicKey systemProgramKey,
                                               final PublicKey eventAuthorityKey,
                                               final PublicKey programKey,
                                               final VaultUnstakeParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(userKey),
      createWrite(vaultKey),
      createWrite(lpMintKey),
      createWrite(vaultStakeKey),
      createWrite(userLpTaKey),
      createWrite(vaultStakeLpTaKey),
      createWrite(vaultRewardsInfoKey),
      createWrite(userRewardsInfoKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(UNSTAKE_USER_VAULT_LP_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record UnstakeUserVaultLpIxData(Discriminator discriminator, VaultUnstakeParams params) implements Borsh {  

    public static UnstakeUserVaultLpIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static UnstakeUserVaultLpIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = VaultUnstakeParams.read(_data, i);
      return new UnstakeUserVaultLpIxData(discriminator, params);
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

  public static final Discriminator UPDATE_MARKET_INFORMATION_DISCRIMINATOR = toDiscriminator(186, 195, 82, 187, 196, 199, 135, 158);

  // 7.2 update market information account
  public static Instruction updateMarketInformation(final AccountMeta invokedLoopscaleProgramMeta,
                                                    final PublicKey authorityKey,
                                                    final PublicKey marketInformationKey,
                                                    final UpdateAssetDataParams[] assetUpdateParams,
                                                    final UpdateCapsParams updateCapParams) {
    final var keys = List.of(
      createReadOnlySigner(authorityKey),
      createWrite(marketInformationKey)
    );

    final byte[] _data = new byte[
        8
        + (assetUpdateParams == null || assetUpdateParams.length == 0 ? 1 : (1 + Borsh.lenVector(assetUpdateParams)))
        + (updateCapParams == null ? 1 : (1 + Borsh.len(updateCapParams)))
    ];
    int i = writeDiscriminator(UPDATE_MARKET_INFORMATION_DISCRIMINATOR, _data, 0);
    if (assetUpdateParams == null || assetUpdateParams.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(assetUpdateParams, _data, i);
    }
    Borsh.writeOptional(updateCapParams, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record UpdateMarketInformationIxData(Discriminator discriminator, UpdateAssetDataParams[] assetUpdateParams, UpdateCapsParams updateCapParams) implements Borsh {  

    public static UpdateMarketInformationIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateMarketInformationIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var assetUpdateParams = _data[i++] == 0 ? null : Borsh.readVector(UpdateAssetDataParams.class, UpdateAssetDataParams::read, _data, i);
      if (assetUpdateParams != null) {
        i += Borsh.lenVector(assetUpdateParams);
      }
      final var updateCapParams = _data[i++] == 0 ? null : UpdateCapsParams.read(_data, i);
      return new UpdateMarketInformationIxData(discriminator, assetUpdateParams, updateCapParams);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      if (assetUpdateParams == null || assetUpdateParams.length == 0) {
        _data[i++] = 0;
      } else {
        _data[i++] = 1;
        i += Borsh.writeVector(assetUpdateParams, _data, i);
      }
      i += Borsh.writeOptional(updateCapParams, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + (assetUpdateParams == null || assetUpdateParams.length == 0 ? 1 : (1 + Borsh.lenVector(assetUpdateParams))) + (updateCapParams == null ? 1 : (1 + Borsh.len(updateCapParams)));
    }
  }

  public static final Discriminator UPDATE_REWARDS_SCHEDULE_DISCRIMINATOR = toDiscriminator(226, 238, 15, 86, 66, 219, 13, 232);

  public static Instruction updateRewardsSchedule(final AccountMeta invokedLoopscaleProgramMeta,
                                                  final PublicKey bsAuthKey,
                                                  final PublicKey payerKey,
                                                  final PublicKey managerKey,
                                                  final PublicKey rewardsSourceKey,
                                                  final PublicKey vaultKey,
                                                  final PublicKey vaultRewardsInfoKey,
                                                  final PublicKey vaultRewardsMintKey,
                                                  final PublicKey vaultRewardsTaKey,
                                                  final PublicKey rewardsSourceTaKey,
                                                  final PublicKey tokenProgramKey,
                                                  final PublicKey associatedTokenProgramKey,
                                                  final PublicKey systemProgramKey,
                                                  final PublicKey eventAuthorityKey,
                                                  final PublicKey programKey,
                                                  final UpdateRewardsScheduleParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(managerKey),
      createReadOnlySigner(rewardsSourceKey),
      createWrite(vaultKey),
      createWrite(vaultRewardsInfoKey),
      createRead(vaultRewardsMintKey),
      createWrite(vaultRewardsTaKey),
      createWrite(rewardsSourceTaKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(UPDATE_REWARDS_SCHEDULE_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record UpdateRewardsScheduleIxData(Discriminator discriminator, UpdateRewardsScheduleParams params) implements Borsh {  

    public static UpdateRewardsScheduleIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateRewardsScheduleIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = UpdateRewardsScheduleParams.read(_data, i);
      return new UpdateRewardsScheduleIxData(discriminator, params);
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

  public static final Discriminator UPDATE_STRATEGY_DISCRIMINATOR = toDiscriminator(16, 76, 138, 179, 171, 112, 196, 21);

  // 8.3 update strategy
  public static Instruction updateStrategy(final AccountMeta invokedLoopscaleProgramMeta,
                                           final PublicKey bsAuthKey,
                                           final PublicKey payerKey,
                                           final PublicKey lenderKey,
                                           final PublicKey strategyKey,
                                           final PublicKey principalMintKey,
                                           final PublicKey strategyTaKey,
                                           final PublicKey systemProgramKey,
                                           final PublicKey associatedTokenProgramKey,
                                           final PublicKey tokenProgramKey,
                                           final PublicKey eventAuthorityKey,
                                           final PublicKey programKey,
                                           final MultiCollateralTermsUpdateParams[] collateralTerms,
                                           final UpdateStrategyParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(lenderKey),
      createWrite(strategyKey),
      createRead(principalMintKey),
      createWrite(strategyTaKey),
      createRead(systemProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[
        8 + Borsh.lenVector(collateralTerms)
        + (params == null ? 1 : (1 + Borsh.len(params)))
    ];
    int i = writeDiscriminator(UPDATE_STRATEGY_DISCRIMINATOR, _data, 0);
    i += Borsh.writeVector(collateralTerms, _data, i);
    Borsh.writeOptional(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record UpdateStrategyIxData(Discriminator discriminator, MultiCollateralTermsUpdateParams[] collateralTerms, UpdateStrategyParams params) implements Borsh {  

    public static UpdateStrategyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateStrategyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var collateralTerms = Borsh.readVector(MultiCollateralTermsUpdateParams.class, MultiCollateralTermsUpdateParams::read, _data, i);
      i += Borsh.lenVector(collateralTerms);
      final var params = _data[i++] == 0 ? null : UpdateStrategyParams.read(_data, i);
      return new UpdateStrategyIxData(discriminator, collateralTerms, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(collateralTerms, _data, i);
      i += Borsh.writeOptional(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(collateralTerms) + (params == null ? 1 : (1 + Borsh.len(params)));
    }
  }

  public static final Discriminator UPDATE_VAULT_DISCRIMINATOR = toDiscriminator(67, 229, 185, 188, 226, 11, 210, 60);

  // 9.4 toggle vault deposits
  public static Instruction updateVault(final AccountMeta invokedLoopscaleProgramMeta,
                                        final PublicKey bsAuthKey,
                                        final PublicKey managerKey,
                                        final PublicKey payerKey,
                                        final PublicKey vaultKey,
                                        final PublicKey marketInformationKey,
                                        final PublicKey vaultRewardsInfoKey,
                                        final PublicKey systemProgramKey,
                                        final UpdateVaultParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createReadOnlySigner(managerKey),
      createWritableSigner(payerKey),
      createWrite(vaultKey),
      createRead(marketInformationKey),
      createWrite(vaultRewardsInfoKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(UPDATE_VAULT_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record UpdateVaultIxData(Discriminator discriminator, UpdateVaultParams params) implements Borsh {  

    public static UpdateVaultIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

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
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_WEIGHT_MATRIX_DISCRIMINATOR = toDiscriminator(252, 166, 37, 207, 154, 83, 187, 128);

  // 10. update weight matrix
  public static Instruction updateWeightMatrix(final AccountMeta invokedLoopscaleProgramMeta,
                                               final PublicKey bsAuthKey,
                                               final PublicKey borrowerKey,
                                               final PublicKey loanKey,
                                               final UpdateWeightMatrixParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createReadOnlySigner(borrowerKey),
      createWrite(loanKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(UPDATE_WEIGHT_MATRIX_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record UpdateWeightMatrixIxData(Discriminator discriminator, UpdateWeightMatrixParams params) implements Borsh {  

    public static UpdateWeightMatrixIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateWeightMatrixIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = UpdateWeightMatrixParams.read(_data, i);
      return new UpdateWeightMatrixIxData(discriminator, params);
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

  public static final Discriminator WITHDRAW_COLLATERAL_DISCRIMINATOR = toDiscriminator(115, 135, 168, 106, 139, 214, 138, 150);

  // 5.2. withdraw collateral
  public static Instruction withdrawCollateral(final AccountMeta invokedLoopscaleProgramMeta,
                                               final PublicKey bsAuthKey,
                                               final PublicKey payerKey,
                                               final PublicKey borrowerKey,
                                               final PublicKey loanKey,
                                               final PublicKey borrowerTaKey,
                                               final PublicKey loanTaKey,
                                               final PublicKey systemProgramKey,
                                               final PublicKey assetMintKey,
                                               final PublicKey tokenProgramKey,
                                               final PublicKey associatedTokenProgramKey,
                                               final PublicKey eventAuthorityKey,
                                               final PublicKey programKey,
                                               final WithdrawCollateralParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(borrowerKey),
      createWrite(loanKey),
      createWrite(borrowerTaKey),
      createWrite(loanTaKey),
      createRead(systemProgramKey),
      createRead(assetMintKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(WITHDRAW_COLLATERAL_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record WithdrawCollateralIxData(Discriminator discriminator, WithdrawCollateralParams params) implements Borsh {  

    public static WithdrawCollateralIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static WithdrawCollateralIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = WithdrawCollateralParams.read(_data, i);
      return new WithdrawCollateralIxData(discriminator, params);
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

  public static final Discriminator WITHDRAW_STRATEGY_DISCRIMINATOR = toDiscriminator(31, 45, 162, 5, 193, 217, 134, 188);

  // 8.4 withdraw strategy
  public static Instruction withdrawStrategy(final AccountMeta invokedLoopscaleProgramMeta,
                                             final PublicKey bsAuthKey,
                                             final PublicKey payerKey,
                                             final PublicKey lenderKey,
                                             final PublicKey strategyKey,
                                             final PublicKey principalMintKey,
                                             final PublicKey marketInformationKey,
                                             final PublicKey lenderTaKey,
                                             final PublicKey strategyTaKey,
                                             final PublicKey associatedTokenProgramKey,
                                             final PublicKey tokenProgramKey,
                                             final PublicKey systemProgramKey,
                                             final PublicKey eventAuthorityKey,
                                             final PublicKey programKey,
                                             final long amount,
                                             final boolean withdrawAll) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createWritableSigner(lenderKey),
      createWrite(strategyKey),
      createRead(principalMintKey),
      createRead(marketInformationKey),
      createWrite(lenderTaKey),
      createWrite(strategyTaKey),
      createRead(associatedTokenProgramKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[17];
    int i = writeDiscriminator(WITHDRAW_STRATEGY_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) (withdrawAll ? 1 : 0);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record WithdrawStrategyIxData(Discriminator discriminator, long amount, boolean withdrawAll) implements Borsh {  

    public static WithdrawStrategyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static WithdrawStrategyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var withdrawAll = _data[i] == 1;
      return new WithdrawStrategyIxData(discriminator, amount, withdrawAll);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      _data[i] = (byte) (withdrawAll ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator WITHDRAW_USER_VAULT_DISCRIMINATOR = toDiscriminator(9, 80, 134, 138, 212, 20, 61, 42);

  // 9.1.1.2 vault user withdraw
  public static Instruction withdrawUserVault(final AccountMeta invokedLoopscaleProgramMeta,
                                              final PublicKey bsAuthKey,
                                              final PublicKey payerKey,
                                              final PublicKey userKey,
                                              final PublicKey vaultKey,
                                              final PublicKey strategyKey,
                                              final PublicKey marketInformationKey,
                                              final PublicKey lpMintKey,
                                              final PublicKey userLpTaKey,
                                              final PublicKey userPrincipalTaKey,
                                              final PublicKey strategyPrincipalTaKey,
                                              final PublicKey principalMintKey,
                                              final PublicKey principalTokenProgramKey,
                                              final PublicKey token2022ProgramKey,
                                              final PublicKey associatedTokenProgramKey,
                                              final PublicKey systemProgramKey,
                                              final PublicKey eventAuthorityKey,
                                              final PublicKey programKey,
                                              final LpParams params) {
    final var keys = List.of(
      createReadOnlySigner(bsAuthKey),
      createWritableSigner(payerKey),
      createWritableSigner(userKey),
      createWrite(vaultKey),
      createWrite(strategyKey),
      createRead(marketInformationKey),
      createWrite(lpMintKey),
      createWrite(userLpTaKey),
      createWrite(userPrincipalTaKey),
      createWrite(strategyPrincipalTaKey),
      createRead(principalMintKey),
      createRead(principalTokenProgramKey),
      createRead(token2022ProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(WITHDRAW_USER_VAULT_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedLoopscaleProgramMeta, keys, _data);
  }

  public record WithdrawUserVaultIxData(Discriminator discriminator, LpParams params) implements Borsh {  

    public static WithdrawUserVaultIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static WithdrawUserVaultIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = LpParams.read(_data, i);
      return new WithdrawUserVaultIxData(discriminator, params);
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

  private LoopscaleProgram() {
  }
}
