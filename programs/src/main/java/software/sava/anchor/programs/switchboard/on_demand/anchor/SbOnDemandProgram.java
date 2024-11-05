package software.sava.anchor.programs.switchboard.on_demand.anchor;

import java.util.List;

import software.sava.anchor.programs.switchboard.on_demand.anchor.types.GuardianQuoteVerifyParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.GuardianRegisterParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.GuardianUnregisterParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.OracleHeartbeatParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.OracleInitParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.OracleSetConfigsParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.OracleUpdateDelegationParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.PermissionSetParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.PullFeedCloseParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.PullFeedInitParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.PullFeedSetConfigsParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.PullFeedSubmitResponseManyParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.PullFeedSubmitResponseParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueueAddMrEnclaveParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueueAllowSubsidiesParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueueGarbageCollectParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueueInitDelegationGroupParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueueInitParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueueRemoveMrEnclaveParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.QueueSetConfigsParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.RandomnessCommitParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.RandomnessInitParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.RandomnessRevealParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.StateInitParams;
import software.sava.anchor.programs.switchboard.on_demand.anchor.types.StateSetConfigsParams;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.anchor.AnchorUtil.writeDiscriminator;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class SbOnDemandProgram {

  public static final Discriminator GUARDIAN_QUOTE_VERIFY_DISCRIMINATOR = toDiscriminator(168, 36, 93, 156, 157, 150, 148, 45);

  public static Instruction guardianQuoteVerify(final AccountMeta invokedSbOnDemandProgramMeta,
                                                final SolanaAccounts solanaAccounts,
                                                final PublicKey guardianKey,
                                                final PublicKey oracleKey,
                                                final PublicKey authorityKey,
                                                final PublicKey guardianQueueKey,
                                                final PublicKey stateKey,
                                                final GuardianQuoteVerifyParams params) {
    final var keys = List.of(
      createWrite(guardianKey),
      createWrite(oracleKey),
      createReadOnlySigner(authorityKey),
      createWrite(guardianQueueKey),
      createRead(stateKey),
      createRead(solanaAccounts.slotHashesSysVar())
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(GUARDIAN_QUOTE_VERIFY_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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
    int i = writeDiscriminator(GUARDIAN_REGISTER_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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
    int i = writeDiscriminator(GUARDIAN_UNREGISTER_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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
      createWrite(delegationPoolKey),
      createWrite(delegationGroupKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(ORACLE_HEARTBEAT_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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

  public static final Discriminator ORACLE_INIT_DISCRIMINATOR = toDiscriminator(21, 158, 66, 65, 60, 221, 148, 61);

  public static Instruction oracleInit(final AccountMeta invokedSbOnDemandProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey oracleKey,
                                       final PublicKey oracleStatsKey,
                                       final PublicKey programStateKey,
                                       final PublicKey payerKey,
                                       final PublicKey lutSignerKey,
                                       final PublicKey lutKey,
                                       final PublicKey stakeProgramKey,
                                       final PublicKey stakePoolKey,
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
      createRead(solanaAccounts.addressLookupTableProgram()),
      createRead(stakeProgramKey),
      createRead(stakePoolKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(ORACLE_INIT_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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
    int i = writeDiscriminator(ORACLE_SET_CONFIGS_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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

  public static final Discriminator ORACLE_UPDATE_DELEGATION_DISCRIMINATOR = toDiscriminator(46, 198, 113, 223, 160, 189, 118, 90);

  public static Instruction oracleUpdateDelegation(final AccountMeta invokedSbOnDemandProgramMeta,
                                                   final SolanaAccounts solanaAccounts,
                                                   final PublicKey oracleKey,
                                                   final PublicKey oracleStatsKey,
                                                   final PublicKey queueKey,
                                                   final PublicKey authorityKey,
                                                   final PublicKey programStateKey,
                                                   final PublicKey payerKey,
                                                   final PublicKey delegationPoolKey,
                                                   final PublicKey lutSignerKey,
                                                   final PublicKey lutKey,
                                                   final PublicKey switchMintKey,
                                                   final PublicKey wsolVaultKey,
                                                   final PublicKey switchVaultKey,
                                                   final PublicKey stakeProgramKey,
                                                   final PublicKey stakePoolKey,
                                                   final PublicKey delegationGroupKey,
                                                   final OracleUpdateDelegationParams params) {
    final var keys = List.of(
      createWrite(oracleKey),
      createRead(oracleStatsKey),
      createRead(queueKey),
      createReadOnlySigner(authorityKey),
      createWrite(programStateKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createWrite(delegationPoolKey),
      createRead(lutSignerKey),
      createWrite(lutKey),
      createRead(solanaAccounts.addressLookupTableProgram()),
      createRead(switchMintKey),
      createRead(solanaAccounts.wrappedSolTokenMint()),
      createWrite(wsolVaultKey),
      createWrite(switchVaultKey),
      createRead(stakeProgramKey),
      createRead(stakePoolKey),
      createRead(delegationGroupKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(ORACLE_UPDATE_DELEGATION_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record OracleUpdateDelegationIxData(Discriminator discriminator, OracleUpdateDelegationParams params) implements Borsh {  

    public static OracleUpdateDelegationIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static OracleUpdateDelegationIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = OracleUpdateDelegationParams.read(_data, i);
      return new OracleUpdateDelegationIxData(discriminator, params);
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
    int i = writeDiscriminator(PERMISSION_SET_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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
    int i = writeDiscriminator(PULL_FEED_CLOSE_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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
    int i = writeDiscriminator(PULL_FEED_INIT_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record PullFeedInitIxData(Discriminator discriminator, PullFeedInitParams params) implements Borsh {  

    public static PullFeedInitIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 129;

    public static PullFeedInitIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
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
      return BYTES;
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
    int i = writeDiscriminator(PULL_FEED_SET_CONFIGS_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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
    int i = writeDiscriminator(PULL_FEED_SUBMIT_RESPONSE_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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
    int i = writeDiscriminator(PULL_FEED_SUBMIT_RESPONSE_MANY_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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
    int i = writeDiscriminator(QUEUE_ADD_MR_ENCLAVE_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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
    int i = writeDiscriminator(QUEUE_ALLOW_SUBSIDIES_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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
    int i = writeDiscriminator(QUEUE_GARBAGE_COLLECT_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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
    int i = writeDiscriminator(QUEUE_INIT_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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

  public static final Discriminator QUEUE_INIT_DELEGATION_GROUP_DISCRIMINATOR = toDiscriminator(239, 146, 75, 158, 20, 166, 159, 14);

  public static Instruction queueInitDelegationGroup(final AccountMeta invokedSbOnDemandProgramMeta,
                                                     final SolanaAccounts solanaAccounts,
                                                     final PublicKey queueKey,
                                                     final PublicKey payerKey,
                                                     final PublicKey programStateKey,
                                                     final PublicKey lutSignerKey,
                                                     final PublicKey lutKey,
                                                     final PublicKey delegationGroupKey,
                                                     final PublicKey stakeProgramKey,
                                                     final PublicKey stakePoolKey,
                                                     final QueueInitDelegationGroupParams params) {
    final var keys = List.of(
      createWrite(queueKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(programStateKey),
      createRead(lutSignerKey),
      createWrite(lutKey),
      createRead(solanaAccounts.addressLookupTableProgram()),
      createWrite(delegationGroupKey),
      createRead(stakeProgramKey),
      createRead(stakePoolKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(QUEUE_INIT_DELEGATION_GROUP_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record QueueInitDelegationGroupIxData(Discriminator discriminator, QueueInitDelegationGroupParams params) implements Borsh {  

    public static QueueInitDelegationGroupIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static QueueInitDelegationGroupIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = QueueInitDelegationGroupParams.read(_data, i);
      return new QueueInitDelegationGroupIxData(discriminator, params);
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
    int i = writeDiscriminator(QUEUE_REMOVE_MR_ENCLAVE_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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
    int i = writeDiscriminator(QUEUE_SET_CONFIGS_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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
    int i = writeDiscriminator(RANDOMNESS_COMMIT_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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
    int i = writeDiscriminator(RANDOMNESS_INIT_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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
    int i = writeDiscriminator(RANDOMNESS_REVEAL_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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
    int i = writeDiscriminator(STATE_INIT_DISCRIMINATOR, _data, 0);
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
      final var discriminator = parseDiscriminator(_data, offset);
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
    int i = writeDiscriminator(STATE_SET_CONFIGS_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedSbOnDemandProgramMeta, keys, _data);
  }

  public record StateSetConfigsIxData(Discriminator discriminator, StateSetConfigsParams params) implements Borsh {  

    public static StateSetConfigsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 219;

    public static StateSetConfigsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
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

  private SbOnDemandProgram() {
  }
}