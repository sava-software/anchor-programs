package software.sava.anchor.programs.metadao.autocrat.anchor;

import java.util.List;

import software.sava.anchor.programs.metadao.autocrat.anchor.types.InitializeDaoParams;
import software.sava.anchor.programs.metadao.autocrat.anchor.types.InitializeProposalParams;
import software.sava.anchor.programs.metadao.autocrat.anchor.types.UpdateDaoParams;
import software.sava.core.accounts.PublicKey;
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

public final class AutocratProgram {

  public static final Discriminator INITIALIZE_DAO_DISCRIMINATOR = toDiscriminator(128, 226, 96, 90, 39, 56, 24, 196);

  public static Instruction initializeDao(final AccountMeta invokedAutocratProgramMeta,
                                          final PublicKey daoKey,
                                          final PublicKey payerKey,
                                          final PublicKey systemProgramKey,
                                          final PublicKey tokenMintKey,
                                          final PublicKey usdcMintKey,
                                          final PublicKey eventAuthorityKey,
                                          final PublicKey programKey,
                                          final InitializeDaoParams params) {
    final var keys = List.of(
      createWritableSigner(daoKey),
      createWritableSigner(payerKey),
      createRead(systemProgramKey),
      createRead(tokenMintKey),
      createRead(usdcMintKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(INITIALIZE_DAO_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedAutocratProgramMeta, keys, _data);
  }

  public record InitializeDaoIxData(Discriminator discriminator, InitializeDaoParams params) implements Borsh {  

    public static InitializeDaoIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static InitializeDaoIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = InitializeDaoParams.read(_data, i);
      return new InitializeDaoIxData(discriminator, params);
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

  public static final Discriminator INITIALIZE_PROPOSAL_DISCRIMINATOR = toDiscriminator(50, 73, 156, 98, 129, 149, 21, 158);

  public static Instruction initializeProposal(final AccountMeta invokedAutocratProgramMeta,
                                               final PublicKey proposalKey,
                                               final PublicKey daoKey,
                                               final PublicKey questionKey,
                                               final PublicKey quoteVaultKey,
                                               final PublicKey baseVaultKey,
                                               final PublicKey passAmmKey,
                                               final PublicKey passLpMintKey,
                                               final PublicKey failLpMintKey,
                                               final PublicKey failAmmKey,
                                               final PublicKey passLpUserAccountKey,
                                               final PublicKey failLpUserAccountKey,
                                               final PublicKey passLpVaultAccountKey,
                                               final PublicKey failLpVaultAccountKey,
                                               final PublicKey proposerKey,
                                               final PublicKey tokenProgramKey,
                                               final PublicKey systemProgramKey,
                                               final PublicKey eventAuthorityKey,
                                               final PublicKey programKey,
                                               final InitializeProposalParams params) {
    final var keys = List.of(
      createWrite(proposalKey),
      createWrite(daoKey),
      createRead(questionKey),
      createRead(quoteVaultKey),
      createRead(baseVaultKey),
      createRead(passAmmKey),
      createRead(passLpMintKey),
      createRead(failLpMintKey),
      createRead(failAmmKey),
      createWrite(passLpUserAccountKey),
      createWrite(failLpUserAccountKey),
      createWrite(passLpVaultAccountKey),
      createWrite(failLpVaultAccountKey),
      createWritableSigner(proposerKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(INITIALIZE_PROPOSAL_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedAutocratProgramMeta, keys, _data);
  }

  public record InitializeProposalIxData(Discriminator discriminator, InitializeProposalParams params) implements Borsh {  

    public static InitializeProposalIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static InitializeProposalIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = InitializeProposalParams.read(_data, i);
      return new InitializeProposalIxData(discriminator, params);
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

  public static final Discriminator FINALIZE_PROPOSAL_DISCRIMINATOR = toDiscriminator(23, 68, 51, 167, 109, 173, 187, 164);

  public static Instruction finalizeProposal(final AccountMeta invokedAutocratProgramMeta,
                                             final PublicKey proposalKey,
                                             final PublicKey passAmmKey,
                                             final PublicKey failAmmKey,
                                             final PublicKey daoKey,
                                             final PublicKey questionKey,
                                             final PublicKey treasuryKey,
                                             final PublicKey passLpUserAccountKey,
                                             final PublicKey failLpUserAccountKey,
                                             final PublicKey passLpVaultAccountKey,
                                             final PublicKey failLpVaultAccountKey,
                                             final PublicKey tokenProgramKey,
                                             final PublicKey vaultProgramKey,
                                             final PublicKey vaultEventAuthorityKey,
                                             final PublicKey eventAuthorityKey,
                                             final PublicKey programKey) {
    final var keys = List.of(
      createWrite(proposalKey),
      createRead(passAmmKey),
      createRead(failAmmKey),
      createRead(daoKey),
      createWrite(questionKey),
      createRead(treasuryKey),
      createWrite(passLpUserAccountKey),
      createWrite(failLpUserAccountKey),
      createWrite(passLpVaultAccountKey),
      createWrite(failLpVaultAccountKey),
      createRead(tokenProgramKey),
      createRead(vaultProgramKey),
      createRead(vaultEventAuthorityKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedAutocratProgramMeta, keys, FINALIZE_PROPOSAL_DISCRIMINATOR);
  }

  public static final Discriminator EXECUTE_PROPOSAL_DISCRIMINATOR = toDiscriminator(186, 60, 116, 133, 108, 128, 111, 28);

  public static Instruction executeProposal(final AccountMeta invokedAutocratProgramMeta,
                                            final PublicKey proposalKey,
                                            final PublicKey daoKey,
                                            final PublicKey eventAuthorityKey,
                                            final PublicKey programKey) {
    final var keys = List.of(
      createWrite(proposalKey),
      createRead(daoKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedAutocratProgramMeta, keys, EXECUTE_PROPOSAL_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_DAO_DISCRIMINATOR = toDiscriminator(131, 72, 75, 25, 112, 210, 109, 2);

  public static Instruction updateDao(final AccountMeta invokedAutocratProgramMeta,
                                      final PublicKey daoKey,
                                      final PublicKey treasuryKey,
                                      final PublicKey eventAuthorityKey,
                                      final PublicKey programKey,
                                      final UpdateDaoParams daoParams) {
    final var keys = List.of(
      createWrite(daoKey),
      createReadOnlySigner(treasuryKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(daoParams)];
    int i = writeDiscriminator(UPDATE_DAO_DISCRIMINATOR, _data, 0);
    Borsh.write(daoParams, _data, i);

    return Instruction.createInstruction(invokedAutocratProgramMeta, keys, _data);
  }

  public record UpdateDaoIxData(Discriminator discriminator, UpdateDaoParams daoParams) implements Borsh {  

    public static UpdateDaoIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateDaoIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var daoParams = UpdateDaoParams.read(_data, i);
      return new UpdateDaoIxData(discriminator, daoParams);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(daoParams, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(daoParams);
    }
  }

  private AutocratProgram() {
  }
}
