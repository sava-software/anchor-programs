package software.sava.anchor.programs.switchboard.on_demand.anchor;

import java.util.List;

import software.sava.anchor.programs.switchboard.on_demand.anchor.types.GuardianQuoteVerifyParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.GuardianRegisterParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.GuardianUnregisterParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.OracleHeartbeatParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.OracleHeartbeatV2Params;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.OracleInitParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.OracleInitSVMParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.OracleResetLutParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.OracleSetConfigsParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.OracleSetOperatorParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.OracleSyncLutParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.PermissionSetParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.PullFeedCloseParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.PullFeedInitParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.PullFeedSetConfigsParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.PullFeedSubmitResponseConsensusLightParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.PullFeedSubmitResponseConsensusParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.PullFeedSubmitResponseManyParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.PullFeedSubmitResponseParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.PullFeedSubmitResponseSVMParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueueAddMrEnclaveParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueueAllowSubsidiesParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueueGarbageCollectParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueueInitParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueueInitSVMParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueueOverrideSVMParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueuePayRewardsParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueuePaySubsidyParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueueRemoveMrEnclaveParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueueResetLutParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueueResetVaultParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueueSetConfigsParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueueSetNcnParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueueSetVaultParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.RandomnessCloseParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.RandomnessCommitParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.RandomnessInitParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.RandomnessRevealParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.StateInitParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.StateSetConfigsParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.TestUpdateOracleStatsParams;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class SbOnDemandProgram {

  public static final Discriminator GUARDIAN_QUOTE_VERIFY_DISCRIMINATOR = toDiscriminator(168, 36, 93, 156, 157, 150, 148, 45);

  public static Instruction guardianQuoteVerify(final AccountMeta invokedSbOnDemandProgramMeta,
                                                final SolanaAccounts solanaAccounts,
                                                final PublicKey guardianKey,
                                                final PublicKey oracleKey,
                                                final PublicKey authorityKey,
                                                final PublicKey oracleQueueKey,
                                                final PublicKey guardianQueueKey,
                                                final PublicKey stateKey,
                                                final GuardianQuoteVerifyParams params) {
    final var keys = List.of(
      createWrite(guardianKey),
      createWrite(oracleKey),
      createReadOnlySigner(authorityKey),
      createWrite(oracleQueueKey),
      createWrite(guardianQueueKey),
      createRead(stateKey),
      createRead(solanaAccounts.slotHashesSysVar())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GUARDIAN_QUOTE_VERIFY_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record GuardianQuoteVerifyIxData(Discriminator discriminator, GuardianQuoteVerifyParams params) implements Borsh {  

    public static GuardianQuoteVerifyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static GuardianQuoteVerifyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GuardianQuoteVerifyParams.read(_data, i);
      return new GuardianQuoteVerifyIxData(discriminator, params);
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

  public static final Discriminator GUARDIAN_REGISTER_DISCRIMINATOR = toDiscriminator(159, 76, 53, 117, 219, 29, 116, 135);

  public static Instruction guardianRegister(final AccountMeta invokedSbOnDemandProgramMeta,
                                             final PublicKey oracleKey,
                                             final PublicKey stateKey,
                                             final PublicKey guardianQueueKey,
                                             final PublicKey authorityKey,
                                             final GuardianRegisterParams params) {
    final var keys = List.of(
      createWrite(oracleKey),
      createRead(stateKey),
      createRead(guardianQueueKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GUARDIAN_REGISTER_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record GuardianRegisterIxData(Discriminator discriminator, GuardianRegisterParams params) implements Borsh {  

    public static GuardianRegisterIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static GuardianRegisterIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GuardianRegisterParams.read(_data, i);
      return new GuardianRegisterIxData(discriminator, params);
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

  public static final Discriminator GUARDIAN_UNREGISTER_DISCRIMINATOR = toDiscriminator(215, 19, 61, 120, 155, 224, 120, 60);

  public static Instruction guardianUnregister(final AccountMeta invokedSbOnDemandProgramMeta,
                                               final PublicKey oracleKey,
                                               final PublicKey stateKey,
                                               final PublicKey guardianQueueKey,
                                               final PublicKey authorityKey,
                                               final GuardianUnregisterParams params) {
    final var keys = List.of(
      createWrite(oracleKey),
      createRead(stateKey),
      createWrite(guardianQueueKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GUARDIAN_UNREGISTER_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record GuardianUnregisterIxData(Discriminator discriminator, GuardianUnregisterParams params) implements Borsh {  

    public static GuardianUnregisterIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static GuardianUnregisterIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GuardianUnregisterParams.read(_data, i);
      return new GuardianUnregisterIxData(discriminator, params);
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

  public static final Discriminator ORACLE_HEARTBEAT_DISCRIMINATOR = toDiscriminator(10, 175, 217, 130, 111, 35, 117, 54);

  public static Instruction oracleHeartbeat(final AccountMeta invokedSbOnDemandProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey oracleKey,
                                            final PublicKey oracleStatsKey,
                                            final PublicKey oracleSignerKey,
                                            final PublicKey queueKey,
                                            final PublicKey gcNodeKey,
                                            final PublicKey programStateKey,
                                            final PublicKey payerKey,
                                            final PublicKey queueEscrowKey,
                                            final PublicKey stakeProgramKey,
                                            final PublicKey delegationPoolKey,
                                            final PublicKey delegationGroupKey,
                                            final OracleHeartbeatParams params) {
    final var keys = List.of(
      createWrite(oracleKey),
      createWrite(oracleStatsKey),
      createReadOnlySigner(oracleSignerKey),
      createWrite(queueKey),
      createWrite(gcNodeKey),
      createWrite(programStateKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.wrappedSolTokenMint()),
      createWrite(queueEscrowKey),
      createRead(stakeProgramKey),
      createRead(delegationPoolKey),
      createRead(delegationGroupKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = ORACLE_HEARTBEAT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record OracleHeartbeatIxData(Discriminator discriminator, OracleHeartbeatParams params) implements Borsh {  

    public static OracleHeartbeatIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static OracleHeartbeatIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = OracleHeartbeatParams.read(_data, i);
      return new OracleHeartbeatIxData(discriminator, params);
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

  public static final Discriminator ORACLE_HEARTBEAT_V_2_DISCRIMINATOR = toDiscriminator(122, 231, 66, 32, 226, 62, 144, 103);

  public static Instruction oracleHeartbeatV2(final AccountMeta invokedSbOnDemandProgramMeta,
                                              final PublicKey oracleKey,
                                              final PublicKey oracleStatsKey,
                                              final PublicKey oracleSignerKey,
                                              final PublicKey queueKey,
                                              final PublicKey gcNodeKey,
                                              final PublicKey programStateKey,
                                              final OracleHeartbeatV2Params params) {
    final var keys = List.of(
      createWrite(oracleKey),
      createWrite(oracleStatsKey),
      createReadOnlySigner(oracleSignerKey),
      createWrite(queueKey),
      createWrite(gcNodeKey),
      createWrite(programStateKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = ORACLE_HEARTBEAT_V_2_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record OracleHeartbeatV2IxData(Discriminator discriminator, OracleHeartbeatV2Params params) implements Borsh {  

    public static OracleHeartbeatV2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static OracleHeartbeatV2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = OracleHeartbeatV2Params.read(_data, i);
      return new OracleHeartbeatV2IxData(discriminator, params);
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

  public static final Discriminator ORACLE_INIT_DISCRIMINATOR = toDiscriminator(21, 158, 66, 65, 60, 221, 148, 61);

  public static Instruction oracleInit(final AccountMeta invokedSbOnDemandProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey oracleKey,
                                       final PublicKey oracleStatsKey,
                                       final PublicKey programStateKey,
                                       final PublicKey payerKey,
                                       final PublicKey lutSignerKey,
                                       final PublicKey lutKey,
                                       final OracleInitParams params) {
    final var keys = List.of(
      createWritableSigner(oracleKey),
      createWrite(oracleStatsKey),
      createWrite(programStateKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(lutSignerKey),
      createWrite(lutKey),
      createRead(solanaAccounts.addressLookupTableProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = ORACLE_INIT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record OracleInitIxData(Discriminator discriminator, OracleInitParams params) implements Borsh {  

    public static OracleInitIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static OracleInitIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = OracleInitParams.read(_data, i);
      return new OracleInitIxData(discriminator, params);
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

  public static final Discriminator ORACLE_INIT_SVM_DISCRIMINATOR = toDiscriminator(106, 20, 36, 117, 166, 175, 131, 83);

  public static Instruction oracleInitSvm(final AccountMeta invokedSbOnDemandProgramMeta,
                                          final SolanaAccounts solanaAccounts,
                                          final PublicKey oracleKey,
                                          final PublicKey oracleStatsKey,
                                          final PublicKey programStateKey,
                                          final PublicKey payerKey,
                                          final PublicKey lutSignerKey,
                                          final PublicKey lutKey,
                                          final OracleInitSVMParams params) {
    final var keys = List.of(
      createWrite(oracleKey),
      createWrite(oracleStatsKey),
      createWrite(programStateKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(lutSignerKey),
      createWrite(lutKey),
      createRead(solanaAccounts.addressLookupTableProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = ORACLE_INIT_SVM_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record OracleInitSvmIxData(Discriminator discriminator, OracleInitSVMParams params) implements Borsh {  

    public static OracleInitSvmIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static OracleInitSvmIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = OracleInitSVMParams.read(_data, i);
      return new OracleInitSvmIxData(discriminator, params);
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

  public static final Discriminator ORACLE_RESET_LUT_DISCRIMINATOR = toDiscriminator(147, 244, 108, 198, 152, 219, 0, 22);

  public static Instruction oracleResetLut(final AccountMeta invokedSbOnDemandProgramMeta,
                                           final SolanaAccounts solanaAccounts,
                                           final PublicKey oracleKey,
                                           final PublicKey authorityKey,
                                           final PublicKey payerKey,
                                           final PublicKey programStateKey,
                                           final PublicKey lutSignerKey,
                                           final PublicKey lutKey,
                                           final OracleResetLutParams params) {
    final var keys = List.of(
      createWrite(oracleKey),
      createReadOnlySigner(authorityKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(programStateKey),
      createRead(lutSignerKey),
      createWrite(lutKey),
      createRead(solanaAccounts.addressLookupTableProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = ORACLE_RESET_LUT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record OracleResetLutIxData(Discriminator discriminator, OracleResetLutParams params) implements Borsh {  

    public static OracleResetLutIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static OracleResetLutIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = OracleResetLutParams.read(_data, i);
      return new OracleResetLutIxData(discriminator, params);
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

  public static final Discriminator ORACLE_SET_CONFIGS_DISCRIMINATOR = toDiscriminator(129, 111, 223, 4, 191, 188, 70, 180);

  public static Instruction oracleSetConfigs(final AccountMeta invokedSbOnDemandProgramMeta,
                                             final PublicKey oracleKey,
                                             final PublicKey authorityKey,
                                             final OracleSetConfigsParams params) {
    final var keys = List.of(
      createRead(oracleKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = ORACLE_SET_CONFIGS_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record OracleSetConfigsIxData(Discriminator discriminator, OracleSetConfigsParams params) implements Borsh {  

    public static OracleSetConfigsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static OracleSetConfigsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = OracleSetConfigsParams.read(_data, i);
      return new OracleSetConfigsIxData(discriminator, params);
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

  public static final Discriminator ORACLE_SET_OPERATOR_DISCRIMINATOR = toDiscriminator(210, 232, 155, 124, 69, 176, 242, 133);

  public static Instruction oracleSetOperator(final AccountMeta invokedSbOnDemandProgramMeta,
                                              final PublicKey oracleKey,
                                              final PublicKey authorityKey,
                                              final PublicKey operatorKey,
                                              final OracleSetOperatorParams params) {
    final var keys = List.of(
      createWrite(oracleKey),
      createReadOnlySigner(authorityKey),
      createRead(operatorKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = ORACLE_SET_OPERATOR_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record OracleSetOperatorIxData(Discriminator discriminator, OracleSetOperatorParams params) implements Borsh {  

    public static OracleSetOperatorIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static OracleSetOperatorIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = OracleSetOperatorParams.read(_data, i);
      return new OracleSetOperatorIxData(discriminator, params);
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

  public static final Discriminator ORACLE_SYNC_LUT_DISCRIMINATOR = toDiscriminator(138, 99, 12, 59, 18, 170, 171, 45);

  public static Instruction oracleSyncLut(final AccountMeta invokedSbOnDemandProgramMeta,
                                          final SolanaAccounts solanaAccounts,
                                          final PublicKey oracleKey,
                                          final PublicKey queueKey,
                                          final PublicKey ncnKey,
                                          final PublicKey vaultKey,
                                          final PublicKey stateKey,
                                          final PublicKey authorityKey,
                                          final PublicKey operatorKey,
                                          final PublicKey ncnOperatorStateKey,
                                          final PublicKey operatorVaultTicketKey,
                                          final PublicKey vaultOperatorDelegationKey,
                                          final PublicKey lutSignerKey,
                                          final PublicKey lutKey,
                                          final PublicKey payerKey,
                                          final OracleSyncLutParams params) {
    final var keys = List.of(
      createRead(oracleKey),
      createRead(queueKey),
      createRead(ncnKey),
      createRead(vaultKey),
      createRead(stateKey),
      createReadOnlySigner(authorityKey),
      createRead(operatorKey),
      createRead(ncnOperatorStateKey),
      createRead(operatorVaultTicketKey),
      createRead(vaultOperatorDelegationKey),
      createRead(lutSignerKey),
      createWrite(lutKey),
      createRead(solanaAccounts.addressLookupTableProgram()),
      createReadOnlySigner(payerKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = ORACLE_SYNC_LUT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record OracleSyncLutIxData(Discriminator discriminator, OracleSyncLutParams params) implements Borsh {  

    public static OracleSyncLutIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static OracleSyncLutIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = OracleSyncLutParams.read(_data, i);
      return new OracleSyncLutIxData(discriminator, params);
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

  public static final Discriminator PERMISSION_SET_DISCRIMINATOR = toDiscriminator(211, 122, 185, 120, 129, 182, 55, 103);

  public static Instruction permissionSet(final AccountMeta invokedSbOnDemandProgramMeta,
                                          final PublicKey authorityKey,
                                          final PublicKey granterKey,
                                          final PermissionSetParams params) {
    final var keys = List.of(
      createReadOnlySigner(authorityKey),
      createRead(granterKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = PERMISSION_SET_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record PermissionSetIxData(Discriminator discriminator, PermissionSetParams params) implements Borsh {  

    public static PermissionSetIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static PermissionSetIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = PermissionSetParams.read(_data, i);
      return new PermissionSetIxData(discriminator, params);
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

  public static final Discriminator PULL_FEED_CLOSE_DISCRIMINATOR = toDiscriminator(19, 134, 50, 142, 177, 215, 196, 83);

  public static Instruction pullFeedClose(final AccountMeta invokedSbOnDemandProgramMeta,
                                          final SolanaAccounts solanaAccounts,
                                          final PublicKey pullFeedKey,
                                          final PublicKey rewardEscrowKey,
                                          final PublicKey lutKey,
                                          final PublicKey lutSignerKey,
                                          final PublicKey payerKey,
                                          final PublicKey stateKey,
                                          final PublicKey authorityKey,
                                          final PullFeedCloseParams params) {
    final var keys = List.of(
      createWrite(pullFeedKey),
      createWrite(rewardEscrowKey),
      createWrite(lutKey),
      createRead(lutSignerKey),
      createWritableSigner(payerKey),
      createRead(stateKey),
      createWritableSigner(authorityKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.addressLookupTableProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = PULL_FEED_CLOSE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record PullFeedCloseIxData(Discriminator discriminator, PullFeedCloseParams params) implements Borsh {  

    public static PullFeedCloseIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static PullFeedCloseIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = PullFeedCloseParams.read(_data, i);
      return new PullFeedCloseIxData(discriminator, params);
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

  public static final Discriminator PULL_FEED_INIT_DISCRIMINATOR = toDiscriminator(198, 130, 53, 198, 235, 61, 143, 40);

  public static Instruction pullFeedInit(final AccountMeta invokedSbOnDemandProgramMeta,
                                         final SolanaAccounts solanaAccounts,
                                         final PublicKey pullFeedKey,
                                         final PublicKey queueKey,
                                         final PublicKey authorityKey,
                                         final PublicKey payerKey,
                                         final PublicKey programStateKey,
                                         final PublicKey rewardEscrowKey,
                                         final PublicKey lutSignerKey,
                                         final PublicKey lutKey,
                                         final PullFeedInitParams params) {
    final var keys = List.of(
      createWritableSigner(pullFeedKey),
      createRead(queueKey),
      createRead(authorityKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(programStateKey),
      createWrite(rewardEscrowKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(solanaAccounts.wrappedSolTokenMint()),
      createRead(lutSignerKey),
      createWrite(lutKey),
      createRead(solanaAccounts.addressLookupTableProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = PULL_FEED_INIT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record PullFeedInitIxData(Discriminator discriminator, PullFeedInitParams params) implements Borsh {  

    public static PullFeedInitIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static PullFeedInitIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = PullFeedInitParams.read(_data, i);
      return new PullFeedInitIxData(discriminator, params);
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

  public static final Discriminator PULL_FEED_SET_CONFIGS_DISCRIMINATOR = toDiscriminator(217, 45, 11, 246, 64, 26, 82, 168);

  public static Instruction pullFeedSetConfigs(final AccountMeta invokedSbOnDemandProgramMeta,
                                               final PublicKey pullFeedKey,
                                               final PublicKey authorityKey,
                                               final PullFeedSetConfigsParams params) {
    final var keys = List.of(
      createWrite(pullFeedKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = PULL_FEED_SET_CONFIGS_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record PullFeedSetConfigsIxData(Discriminator discriminator, PullFeedSetConfigsParams params) implements Borsh {  

    public static PullFeedSetConfigsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static PullFeedSetConfigsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = PullFeedSetConfigsParams.read(_data, i);
      return new PullFeedSetConfigsIxData(discriminator, params);
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

  public static final Discriminator PULL_FEED_SUBMIT_RESPONSE_DISCRIMINATOR = toDiscriminator(150, 22, 215, 166, 143, 93, 48, 137);

  public static Instruction pullFeedSubmitResponse(final AccountMeta invokedSbOnDemandProgramMeta,
                                                   final SolanaAccounts solanaAccounts,
                                                   final PublicKey feedKey,
                                                   final PublicKey queueKey,
                                                   final PublicKey programStateKey,
                                                   final PublicKey payerKey,
                                                   final PublicKey rewardVaultKey,
                                                   final PullFeedSubmitResponseParams params) {
    final var keys = List.of(
      createWrite(feedKey),
      createRead(queueKey),
      createRead(programStateKey),
      createRead(solanaAccounts.slotHashesSysVar()),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram()),
      createWrite(rewardVaultKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.wrappedSolTokenMint())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = PULL_FEED_SUBMIT_RESPONSE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record PullFeedSubmitResponseIxData(Discriminator discriminator, PullFeedSubmitResponseParams params) implements Borsh {  

    public static PullFeedSubmitResponseIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static PullFeedSubmitResponseIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = PullFeedSubmitResponseParams.read(_data, i);
      return new PullFeedSubmitResponseIxData(discriminator, params);
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

  public static final Discriminator PULL_FEED_SUBMIT_RESPONSE_CONSENSUS_DISCRIMINATOR = toDiscriminator(239, 124, 39, 184, 147, 222, 16, 248);

  public static Instruction pullFeedSubmitResponseConsensus(final AccountMeta invokedSbOnDemandProgramMeta,
                                                            final SolanaAccounts solanaAccounts,
                                                            final PublicKey queueKey,
                                                            final PublicKey programStateKey,
                                                            final PublicKey payerKey,
                                                            final PublicKey rewardVaultKey,
                                                            final PullFeedSubmitResponseConsensusParams params) {
    final var keys = List.of(
      createRead(queueKey),
      createRead(programStateKey),
      createRead(solanaAccounts.slotHashesSysVar()),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram()),
      createWrite(rewardVaultKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.wrappedSolTokenMint()),
      createRead(solanaAccounts.instructionsSysVar())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = PULL_FEED_SUBMIT_RESPONSE_CONSENSUS_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record PullFeedSubmitResponseConsensusIxData(Discriminator discriminator, PullFeedSubmitResponseConsensusParams params) implements Borsh {  

    public static PullFeedSubmitResponseConsensusIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static PullFeedSubmitResponseConsensusIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = PullFeedSubmitResponseConsensusParams.read(_data, i);
      return new PullFeedSubmitResponseConsensusIxData(discriminator, params);
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

  public static final Discriminator PULL_FEED_SUBMIT_RESPONSE_CONSENSUS_LIGHT_DISCRIMINATOR = toDiscriminator(178, 179, 88, 144, 175, 130, 157, 87);

  public static Instruction pullFeedSubmitResponseConsensusLight(final AccountMeta invokedSbOnDemandProgramMeta,
                                                                 final SolanaAccounts solanaAccounts,
                                                                 final PublicKey queueKey,
                                                                 final PublicKey programStateKey,
                                                                 final PullFeedSubmitResponseConsensusLightParams params) {
    final var keys = List.of(
      createRead(queueKey),
      createRead(programStateKey),
      createRead(solanaAccounts.slotHashesSysVar()),
      createRead(solanaAccounts.instructionsSysVar())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = PULL_FEED_SUBMIT_RESPONSE_CONSENSUS_LIGHT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record PullFeedSubmitResponseConsensusLightIxData(Discriminator discriminator, PullFeedSubmitResponseConsensusLightParams params) implements Borsh {  

    public static PullFeedSubmitResponseConsensusLightIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static PullFeedSubmitResponseConsensusLightIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = PullFeedSubmitResponseConsensusLightParams.read(_data, i);
      return new PullFeedSubmitResponseConsensusLightIxData(discriminator, params);
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

  public static final Discriminator PULL_FEED_SUBMIT_RESPONSE_MANY_DISCRIMINATOR = toDiscriminator(47, 156, 45, 25, 200, 71, 37, 215);

  public static Instruction pullFeedSubmitResponseMany(final AccountMeta invokedSbOnDemandProgramMeta,
                                                       final SolanaAccounts solanaAccounts,
                                                       final PublicKey queueKey,
                                                       final PublicKey programStateKey,
                                                       final PublicKey payerKey,
                                                       final PublicKey rewardVaultKey,
                                                       final PullFeedSubmitResponseManyParams params) {
    final var keys = List.of(
      createRead(queueKey),
      createRead(programStateKey),
      createRead(solanaAccounts.slotHashesSysVar()),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram()),
      createWrite(rewardVaultKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.wrappedSolTokenMint())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = PULL_FEED_SUBMIT_RESPONSE_MANY_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record PullFeedSubmitResponseManyIxData(Discriminator discriminator, PullFeedSubmitResponseManyParams params) implements Borsh {  

    public static PullFeedSubmitResponseManyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static PullFeedSubmitResponseManyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = PullFeedSubmitResponseManyParams.read(_data, i);
      return new PullFeedSubmitResponseManyIxData(discriminator, params);
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

  public static final Discriminator PULL_FEED_SUBMIT_RESPONSE_SVM_DISCRIMINATOR = toDiscriminator(123, 7, 190, 12, 220, 230, 198, 148);

  public static Instruction pullFeedSubmitResponseSvm(final AccountMeta invokedSbOnDemandProgramMeta,
                                                      final SolanaAccounts solanaAccounts,
                                                      final PublicKey feedKey,
                                                      final PublicKey queueKey,
                                                      final PublicKey programStateKey,
                                                      final PublicKey payerKey,
                                                      final PublicKey rewardVaultKey,
                                                      final PullFeedSubmitResponseSVMParams params) {
    final var keys = List.of(
      createWrite(feedKey),
      createRead(queueKey),
      createRead(programStateKey),
      createRead(solanaAccounts.slotHashesSysVar()),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram()),
      createWrite(rewardVaultKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.wrappedSolTokenMint())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = PULL_FEED_SUBMIT_RESPONSE_SVM_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record PullFeedSubmitResponseSvmIxData(Discriminator discriminator, PullFeedSubmitResponseSVMParams params) implements Borsh {  

    public static PullFeedSubmitResponseSvmIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static PullFeedSubmitResponseSvmIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = PullFeedSubmitResponseSVMParams.read(_data, i);
      return new PullFeedSubmitResponseSvmIxData(discriminator, params);
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

  public static final Discriminator QUEUE_ADD_MR_ENCLAVE_DISCRIMINATOR = toDiscriminator(199, 255, 81, 50, 60, 133, 171, 138);

  public static Instruction queueAddMrEnclave(final AccountMeta invokedSbOnDemandProgramMeta,
                                              final PublicKey queueKey,
                                              final PublicKey authorityKey,
                                              final PublicKey programAuthorityKey,
                                              final PublicKey stateKey,
                                              final QueueAddMrEnclaveParams params) {
    final var keys = List.of(
      createWrite(queueKey),
      createReadOnlySigner(authorityKey),
      createRead(programAuthorityKey),
      createRead(stateKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = QUEUE_ADD_MR_ENCLAVE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record QueueAddMrEnclaveIxData(Discriminator discriminator, QueueAddMrEnclaveParams params) implements Borsh {  

    public static QueueAddMrEnclaveIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static QueueAddMrEnclaveIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = QueueAddMrEnclaveParams.read(_data, i);
      return new QueueAddMrEnclaveIxData(discriminator, params);
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

  public static final Discriminator QUEUE_ALLOW_SUBSIDIES_DISCRIMINATOR = toDiscriminator(94, 203, 82, 157, 188, 138, 202, 108);

  public static Instruction queueAllowSubsidies(final AccountMeta invokedSbOnDemandProgramMeta,
                                                final PublicKey queueKey,
                                                final PublicKey authorityKey,
                                                final PublicKey stateKey,
                                                final QueueAllowSubsidiesParams params) {
    final var keys = List.of(
      createWrite(queueKey),
      createReadOnlySigner(authorityKey),
      createWrite(stateKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = QUEUE_ALLOW_SUBSIDIES_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record QueueAllowSubsidiesIxData(Discriminator discriminator, QueueAllowSubsidiesParams params) implements Borsh {  

    public static QueueAllowSubsidiesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static QueueAllowSubsidiesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = QueueAllowSubsidiesParams.read(_data, i);
      return new QueueAllowSubsidiesIxData(discriminator, params);
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

  public static final Discriminator QUEUE_GARBAGE_COLLECT_DISCRIMINATOR = toDiscriminator(187, 208, 104, 247, 16, 91, 96, 98);

  public static Instruction queueGarbageCollect(final AccountMeta invokedSbOnDemandProgramMeta,
                                                final PublicKey queueKey,
                                                final PublicKey oracleKey,
                                                final PublicKey authorityKey,
                                                final PublicKey stateKey,
                                                final QueueGarbageCollectParams params) {
    final var keys = List.of(
      createWrite(queueKey),
      createWrite(oracleKey),
      createReadOnlySigner(authorityKey),
      createRead(stateKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = QUEUE_GARBAGE_COLLECT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record QueueGarbageCollectIxData(Discriminator discriminator, QueueGarbageCollectParams params) implements Borsh {  

    public static QueueGarbageCollectIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 12;

    public static QueueGarbageCollectIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = QueueGarbageCollectParams.read(_data, i);
      return new QueueGarbageCollectIxData(discriminator, params);
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

  public static final Discriminator QUEUE_INIT_DISCRIMINATOR = toDiscriminator(144, 18, 99, 145, 133, 27, 207, 13);

  public static Instruction queueInit(final AccountMeta invokedSbOnDemandProgramMeta,
                                      final SolanaAccounts solanaAccounts,
                                      final PublicKey queueKey,
                                      final PublicKey queueEscrowKey,
                                      final PublicKey authorityKey,
                                      final PublicKey payerKey,
                                      final PublicKey programStateKey,
                                      final PublicKey lutSignerKey,
                                      final PublicKey lutKey,
                                      final QueueInitParams params) {
    final var keys = List.of(
      createWritableSigner(queueKey),
      createWrite(queueEscrowKey),
      createRead(authorityKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.wrappedSolTokenMint()),
      createRead(programStateKey),
      createWrite(lutSignerKey),
      createWrite(lutKey),
      createRead(solanaAccounts.addressLookupTableProgram()),
      createRead(solanaAccounts.associatedTokenAccountProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = QUEUE_INIT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record QueueInitIxData(Discriminator discriminator, QueueInitParams params) implements Borsh {  

    public static QueueInitIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 34;

    public static QueueInitIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = QueueInitParams.read(_data, i);
      return new QueueInitIxData(discriminator, params);
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

  public static final Discriminator QUEUE_INIT_SVM_DISCRIMINATOR = toDiscriminator(175, 94, 119, 151, 45, 144, 173, 235);

  public static Instruction queueInitSvm(final AccountMeta invokedSbOnDemandProgramMeta,
                                         final SolanaAccounts solanaAccounts,
                                         final PublicKey queueKey,
                                         final PublicKey queueEscrowKey,
                                         final PublicKey authorityKey,
                                         final PublicKey payerKey,
                                         final PublicKey programStateKey,
                                         final PublicKey lutSignerKey,
                                         final PublicKey lutKey,
                                         final QueueInitSVMParams params) {
    final var keys = List.of(
      createWrite(queueKey),
      createWrite(queueEscrowKey),
      createRead(authorityKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.wrappedSolTokenMint()),
      createRead(programStateKey),
      createWrite(lutSignerKey),
      createWrite(lutKey),
      createRead(solanaAccounts.addressLookupTableProgram()),
      createRead(solanaAccounts.associatedTokenAccountProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = QUEUE_INIT_SVM_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record QueueInitSvmIxData(Discriminator discriminator, QueueInitSVMParams params) implements Borsh {  

    public static QueueInitSvmIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 66;

    public static QueueInitSvmIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = QueueInitSVMParams.read(_data, i);
      return new QueueInitSvmIxData(discriminator, params);
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

  public static final Discriminator QUEUE_OVERRIDE_SVM_DISCRIMINATOR = toDiscriminator(43, 103, 15, 35, 89, 14, 244, 165);

  public static Instruction queueOverrideSvm(final AccountMeta invokedSbOnDemandProgramMeta,
                                             final PublicKey oracleKey,
                                             final PublicKey queueKey,
                                             final PublicKey authorityKey,
                                             final PublicKey stateKey,
                                             final QueueOverrideSVMParams params) {
    final var keys = List.of(
      createWrite(oracleKey),
      createWrite(queueKey),
      createReadOnlySigner(authorityKey),
      createRead(stateKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = QUEUE_OVERRIDE_SVM_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record QueueOverrideSvmIxData(Discriminator discriminator, QueueOverrideSVMParams params) implements Borsh {  

    public static QueueOverrideSvmIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 120;

    public static QueueOverrideSvmIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = QueueOverrideSVMParams.read(_data, i);
      return new QueueOverrideSvmIxData(discriminator, params);
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

  public static final Discriminator QUEUE_PAY_REWARDS_DISCRIMINATOR = toDiscriminator(67, 87, 149, 166, 56, 112, 20, 12);

  public static Instruction queuePayRewards(final AccountMeta invokedSbOnDemandProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey queueKey,
                                            final PublicKey programStateKey,
                                            final PublicKey vaultKey,
                                            final PublicKey rewardVaultKey,
                                            final PublicKey payerKey,
                                            final PublicKey escrowKey,
                                            final QueuePayRewardsParams params) {
    final var keys = List.of(
      createWrite(queueKey),
      createRead(programStateKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(vaultKey),
      createWrite(rewardVaultKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(solanaAccounts.wrappedSolTokenMint()),
      createWritableSigner(payerKey),
      createWrite(escrowKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = QUEUE_PAY_REWARDS_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record QueuePayRewardsIxData(Discriminator discriminator, QueuePayRewardsParams params) implements Borsh {  

    public static QueuePayRewardsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static QueuePayRewardsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = QueuePayRewardsParams.read(_data, i);
      return new QueuePayRewardsIxData(discriminator, params);
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

  public static final Discriminator QUEUE_PAY_SUBSIDY_DISCRIMINATOR = toDiscriminator(85, 84, 51, 251, 144, 57, 105, 200);

  public static Instruction queuePaySubsidy(final AccountMeta invokedSbOnDemandProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey queueKey,
                                            final PublicKey programStateKey,
                                            final PublicKey vaultKey,
                                            final PublicKey rewardVaultKey,
                                            final PublicKey subsidyVaultKey,
                                            final PublicKey switchMintKey,
                                            final PublicKey vaultConfigKey,
                                            final PublicKey payerKey,
                                            final QueuePaySubsidyParams params) {
    final var keys = List.of(
      createWrite(queueKey),
      createRead(programStateKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(vaultKey),
      createWrite(rewardVaultKey),
      createWrite(subsidyVaultKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(solanaAccounts.wrappedSolTokenMint()),
      createRead(switchMintKey),
      createRead(vaultConfigKey),
      createWritableSigner(payerKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = QUEUE_PAY_SUBSIDY_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record QueuePaySubsidyIxData(Discriminator discriminator, QueuePaySubsidyParams params) implements Borsh {  

    public static QueuePaySubsidyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static QueuePaySubsidyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = QueuePaySubsidyParams.read(_data, i);
      return new QueuePaySubsidyIxData(discriminator, params);
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

  public static final Discriminator QUEUE_REMOVE_MR_ENCLAVE_DISCRIMINATOR = toDiscriminator(3, 64, 135, 33, 190, 133, 68, 252);

  public static Instruction queueRemoveMrEnclave(final AccountMeta invokedSbOnDemandProgramMeta,
                                                 final PublicKey queueKey,
                                                 final PublicKey authorityKey,
                                                 final PublicKey programAuthorityKey,
                                                 final PublicKey stateKey,
                                                 final QueueRemoveMrEnclaveParams params) {
    final var keys = List.of(
      createWrite(queueKey),
      createReadOnlySigner(authorityKey),
      createRead(programAuthorityKey),
      createRead(stateKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = QUEUE_REMOVE_MR_ENCLAVE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record QueueRemoveMrEnclaveIxData(Discriminator discriminator, QueueRemoveMrEnclaveParams params) implements Borsh {  

    public static QueueRemoveMrEnclaveIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static QueueRemoveMrEnclaveIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = QueueRemoveMrEnclaveParams.read(_data, i);
      return new QueueRemoveMrEnclaveIxData(discriminator, params);
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

  public static final Discriminator QUEUE_RESET_LUT_DISCRIMINATOR = toDiscriminator(224, 85, 0, 204, 71, 42, 11, 242);

  public static Instruction queueResetLut(final AccountMeta invokedSbOnDemandProgramMeta,
                                          final SolanaAccounts solanaAccounts,
                                          final PublicKey queueKey,
                                          final PublicKey authorityKey,
                                          final PublicKey payerKey,
                                          final PublicKey programStateKey,
                                          final PublicKey lutSignerKey,
                                          final PublicKey lutKey,
                                          final QueueResetLutParams params) {
    final var keys = List.of(
      createWrite(queueKey),
      createReadOnlySigner(authorityKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(programStateKey),
      createRead(lutSignerKey),
      createWrite(lutKey),
      createRead(solanaAccounts.addressLookupTableProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = QUEUE_RESET_LUT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record QueueResetLutIxData(Discriminator discriminator, QueueResetLutParams params) implements Borsh {  

    public static QueueResetLutIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static QueueResetLutIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = QueueResetLutParams.read(_data, i);
      return new QueueResetLutIxData(discriminator, params);
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

  public static final Discriminator QUEUE_RESET_VAULT_DISCRIMINATOR = toDiscriminator(232, 255, 48, 111, 240, 168, 253, 40);

  public static Instruction queueResetVault(final AccountMeta invokedSbOnDemandProgramMeta,
                                            final PublicKey queueKey,
                                            final PublicKey authorityKey,
                                            final PublicKey stateKey,
                                            final PublicKey ncnKey,
                                            final PublicKey vaultKey,
                                            final QueueResetVaultParams params) {
    final var keys = List.of(
      createWrite(queueKey),
      createReadOnlySigner(authorityKey),
      createRead(stateKey),
      createRead(ncnKey),
      createRead(vaultKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = QUEUE_RESET_VAULT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record QueueResetVaultIxData(Discriminator discriminator, QueueResetVaultParams params) implements Borsh {  

    public static QueueResetVaultIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static QueueResetVaultIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = QueueResetVaultParams.read(_data, i);
      return new QueueResetVaultIxData(discriminator, params);
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

  public static final Discriminator QUEUE_SET_CONFIGS_DISCRIMINATOR = toDiscriminator(54, 183, 243, 199, 49, 103, 142, 48);

  public static Instruction queueSetConfigs(final AccountMeta invokedSbOnDemandProgramMeta,
                                            final PublicKey queueKey,
                                            final PublicKey authorityKey,
                                            final PublicKey stateKey,
                                            final QueueSetConfigsParams params) {
    final var keys = List.of(
      createWrite(queueKey),
      createReadOnlySigner(authorityKey),
      createRead(stateKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = QUEUE_SET_CONFIGS_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record QueueSetConfigsIxData(Discriminator discriminator, QueueSetConfigsParams params) implements Borsh {  

    public static QueueSetConfigsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static QueueSetConfigsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = QueueSetConfigsParams.read(_data, i);
      return new QueueSetConfigsIxData(discriminator, params);
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

  public static final Discriminator QUEUE_SET_NCN_DISCRIMINATOR = toDiscriminator(232, 223, 179, 12, 20, 136, 181, 219);

  public static Instruction queueSetNcn(final AccountMeta invokedSbOnDemandProgramMeta,
                                        final PublicKey queueKey,
                                        final PublicKey authorityKey,
                                        final PublicKey stateKey,
                                        final PublicKey ncnKey,
                                        final QueueSetNcnParams params) {
    final var keys = List.of(
      createWrite(queueKey),
      createReadOnlySigner(authorityKey),
      createRead(stateKey),
      createRead(ncnKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = QUEUE_SET_NCN_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record QueueSetNcnIxData(Discriminator discriminator, QueueSetNcnParams params) implements Borsh {  

    public static QueueSetNcnIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static QueueSetNcnIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = QueueSetNcnParams.read(_data, i);
      return new QueueSetNcnIxData(discriminator, params);
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

  public static final Discriminator QUEUE_SET_VAULT_DISCRIMINATOR = toDiscriminator(48, 47, 102, 99, 241, 249, 196, 246);

  public static Instruction queueSetVault(final AccountMeta invokedSbOnDemandProgramMeta,
                                          final PublicKey queueKey,
                                          final PublicKey authorityKey,
                                          final PublicKey stateKey,
                                          final PublicKey ncnKey,
                                          final PublicKey vaultKey,
                                          final QueueSetVaultParams params) {
    final var keys = List.of(
      createWrite(queueKey),
      createReadOnlySigner(authorityKey),
      createRead(stateKey),
      createRead(ncnKey),
      createRead(vaultKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = QUEUE_SET_VAULT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record QueueSetVaultIxData(Discriminator discriminator, QueueSetVaultParams params) implements Borsh {  

    public static QueueSetVaultIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static QueueSetVaultIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = QueueSetVaultParams.read(_data, i);
      return new QueueSetVaultIxData(discriminator, params);
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

  public static final Discriminator RANDOMNESS_CLOSE_DISCRIMINATOR = toDiscriminator(146, 101, 14, 74, 225, 246, 0, 156);

  public static Instruction randomnessClose(final AccountMeta invokedSbOnDemandProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey randomnessKey,
                                            final PublicKey rewardEscrowKey,
                                            final PublicKey authorityKey,
                                            final PublicKey programStateKey,
                                            final PublicKey lutKey,
                                            final PublicKey lutSignerKey,
                                            final RandomnessCloseParams params) {
    final var keys = List.of(
      createWrite(randomnessKey),
      createWrite(rewardEscrowKey),
      createWritableSigner(authorityKey),
      createRead(programStateKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.wrappedSolTokenMint()),
      createWrite(lutKey),
      createRead(lutSignerKey),
      createRead(solanaAccounts.addressLookupTableProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = RANDOMNESS_CLOSE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record RandomnessCloseIxData(Discriminator discriminator, RandomnessCloseParams params) implements Borsh {  

    public static RandomnessCloseIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static RandomnessCloseIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = RandomnessCloseParams.read(_data, i);
      return new RandomnessCloseIxData(discriminator, params);
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

  public static final Discriminator RANDOMNESS_COMMIT_DISCRIMINATOR = toDiscriminator(52, 170, 152, 201, 179, 133, 242, 141);

  public static Instruction randomnessCommit(final AccountMeta invokedSbOnDemandProgramMeta,
                                             final SolanaAccounts solanaAccounts,
                                             final PublicKey randomnessKey,
                                             final PublicKey queueKey,
                                             final PublicKey oracleKey,
                                             final PublicKey authorityKey,
                                             final RandomnessCommitParams params) {
    final var keys = List.of(
      createWrite(randomnessKey),
      createRead(queueKey),
      createWrite(oracleKey),
      createRead(solanaAccounts.slotHashesSysVar()),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = RANDOMNESS_COMMIT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record RandomnessCommitIxData(Discriminator discriminator, RandomnessCommitParams params) implements Borsh {  

    public static RandomnessCommitIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static RandomnessCommitIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = RandomnessCommitParams.read(_data, i);
      return new RandomnessCommitIxData(discriminator, params);
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

  public static final Discriminator RANDOMNESS_INIT_DISCRIMINATOR = toDiscriminator(9, 9, 204, 33, 50, 116, 113, 15);

  public static Instruction randomnessInit(final AccountMeta invokedSbOnDemandProgramMeta,
                                           final SolanaAccounts solanaAccounts,
                                           final PublicKey randomnessKey,
                                           final PublicKey rewardEscrowKey,
                                           final PublicKey authorityKey,
                                           final PublicKey queueKey,
                                           final PublicKey payerKey,
                                           final PublicKey programStateKey,
                                           final PublicKey lutSignerKey,
                                           final PublicKey lutKey,
                                           final RandomnessInitParams params) {
    final var keys = List.of(
      createWritableSigner(randomnessKey),
      createWrite(rewardEscrowKey),
      createReadOnlySigner(authorityKey),
      createWrite(queueKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(solanaAccounts.wrappedSolTokenMint()),
      createRead(programStateKey),
      createRead(lutSignerKey),
      createWrite(lutKey),
      createRead(solanaAccounts.addressLookupTableProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = RANDOMNESS_INIT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record RandomnessInitIxData(Discriminator discriminator, RandomnessInitParams params) implements Borsh {  

    public static RandomnessInitIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static RandomnessInitIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = RandomnessInitParams.read(_data, i);
      return new RandomnessInitIxData(discriminator, params);
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

  public static final Discriminator RANDOMNESS_REVEAL_DISCRIMINATOR = toDiscriminator(197, 181, 187, 10, 30, 58, 20, 73);

  public static Instruction randomnessReveal(final AccountMeta invokedSbOnDemandProgramMeta,
                                             final SolanaAccounts solanaAccounts,
                                             final PublicKey randomnessKey,
                                             final PublicKey oracleKey,
                                             final PublicKey queueKey,
                                             final PublicKey statsKey,
                                             final PublicKey authorityKey,
                                             final PublicKey payerKey,
                                             final PublicKey rewardEscrowKey,
                                             final PublicKey programStateKey,
                                             final RandomnessRevealParams params) {
    final var keys = List.of(
      createWrite(randomnessKey),
      createRead(oracleKey),
      createRead(queueKey),
      createWrite(statsKey),
      createReadOnlySigner(authorityKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.slotHashesSysVar()),
      createRead(solanaAccounts.systemProgram()),
      createWrite(rewardEscrowKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.wrappedSolTokenMint()),
      createRead(programStateKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = RANDOMNESS_REVEAL_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record RandomnessRevealIxData(Discriminator discriminator, RandomnessRevealParams params) implements Borsh {  

    public static RandomnessRevealIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 105;

    public static RandomnessRevealIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = RandomnessRevealParams.read(_data, i);
      return new RandomnessRevealIxData(discriminator, params);
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

  public static final Discriminator STATE_INIT_DISCRIMINATOR = toDiscriminator(103, 241, 106, 190, 217, 153, 87, 105);

  public static Instruction stateInit(final AccountMeta invokedSbOnDemandProgramMeta,
                                      final SolanaAccounts solanaAccounts,
                                      final PublicKey stateKey,
                                      final PublicKey payerKey,
                                      final StateInitParams params) {
    final var keys = List.of(
      createWrite(stateKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = STATE_INIT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record StateInitIxData(Discriminator discriminator, StateInitParams params) implements Borsh {  

    public static StateInitIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static StateInitIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = StateInitParams.read(_data, i);
      return new StateInitIxData(discriminator, params);
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

  public static final Discriminator STATE_SET_CONFIGS_DISCRIMINATOR = toDiscriminator(40, 98, 76, 37, 206, 9, 47, 144);

  public static Instruction stateSetConfigs(final AccountMeta invokedSbOnDemandProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey stateKey,
                                            final PublicKey authorityKey,
                                            final PublicKey queueKey,
                                            final PublicKey payerKey,
                                            final StateSetConfigsParams params) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(authorityKey),
      createWrite(queueKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = STATE_SET_CONFIGS_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record StateSetConfigsIxData(Discriminator discriminator, StateSetConfigsParams params) implements Borsh {  

    public static StateSetConfigsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 153;

    public static StateSetConfigsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = StateSetConfigsParams.read(_data, i);
      return new StateSetConfigsIxData(discriminator, params);
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

  public static final Discriminator TEST_UPDATE_ORACLE_STATS_DISCRIMINATOR = toDiscriminator(175, 48, 162, 252, 154, 197, 149, 187);

  public static Instruction testUpdateOracleStats(final AccountMeta invokedSbOnDemandProgramMeta,
                                                  // The OracleStats account to update.
                                                  final PublicKey oracleStatsKey,
                                                  final TestUpdateOracleStatsParams params) {
    final var keys = List.of(
      createWrite(oracleStatsKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = TEST_UPDATE_ORACLE_STATS_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record TestUpdateOracleStatsIxData(Discriminator discriminator, TestUpdateOracleStatsParams params) implements Borsh {  

    public static TestUpdateOracleStatsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static TestUpdateOracleStatsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = TestUpdateOracleStatsParams.read(_data, i);
      return new TestUpdateOracleStatsIxData(discriminator, params);
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

  private SbOnDemandProgram() {
  }
}
