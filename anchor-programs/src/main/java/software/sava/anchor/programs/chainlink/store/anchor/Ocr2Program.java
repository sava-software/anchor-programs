package software.sava.anchor.programs.chainlink.store.anchor;

import java.math.BigInteger;

import java.util.List;

import software.sava.anchor.programs.chainlink.store.anchor.types.NewOracle;
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
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class Ocr2Program {

  public static final Discriminator INITIALIZE_DISCRIMINATOR = toDiscriminator(175, 175, 109, 31, 13, 152, 155, 237);

  public static Instruction initialize(final AccountMeta invokedOcr2ProgramMeta,
                                       final PublicKey stateKey,
                                       final PublicKey feedKey,
                                       final PublicKey payerKey,
                                       final PublicKey ownerKey,
                                       final PublicKey tokenMintKey,
                                       final PublicKey tokenVaultKey,
                                       final PublicKey vaultAuthorityKey,
                                       final PublicKey requesterAccessControllerKey,
                                       final PublicKey billingAccessControllerKey,
                                       final PublicKey rentKey,
                                       final PublicKey systemProgramKey,
                                       final PublicKey tokenProgramKey,
                                       final PublicKey associatedTokenProgramKey,
                                       final BigInteger minAnswer,
                                       final BigInteger maxAnswer) {
    final var keys = List.of(
      createWrite(stateKey),
      createRead(feedKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(ownerKey),
      createRead(tokenMintKey),
      createWrite(tokenVaultKey),
      createRead(vaultAuthorityKey),
      createRead(requesterAccessControllerKey),
      createRead(billingAccessControllerKey),
      createRead(rentKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(INITIALIZE_DISCRIMINATOR, _data, 0);
    putInt128LE(_data, i, minAnswer);
    i += 16;
    putInt128LE(_data, i, maxAnswer);

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, _data);
  }

  public record InitializeIxData(Discriminator discriminator, BigInteger minAnswer, BigInteger maxAnswer) implements Borsh {  

    public static InitializeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static InitializeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var minAnswer = getInt128LE(_data, i);
      i += 16;
      final var maxAnswer = getInt128LE(_data, i);
      return new InitializeIxData(discriminator, minAnswer, maxAnswer);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt128LE(_data, i, minAnswer);
      i += 16;
      putInt128LE(_data, i, maxAnswer);
      i += 16;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CLOSE_DISCRIMINATOR = toDiscriminator(98, 165, 201, 177, 108, 65, 206, 96);

  public static Instruction close(final AccountMeta invokedOcr2ProgramMeta,
                                  final PublicKey stateKey,
                                  final PublicKey receiverKey,
                                  final PublicKey authorityKey,
                                  final PublicKey tokenVaultKey,
                                  final PublicKey vaultAuthorityKey,
                                  final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWrite(stateKey),
      createWrite(receiverKey),
      createReadOnlySigner(authorityKey),
      createWrite(tokenVaultKey),
      createRead(vaultAuthorityKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, CLOSE_DISCRIMINATOR);
  }

  public static final Discriminator TRANSFER_OWNERSHIP_DISCRIMINATOR = toDiscriminator(65, 177, 215, 73, 53, 45, 99, 47);

  public static Instruction transferOwnership(final AccountMeta invokedOcr2ProgramMeta,
                                              final PublicKey stateKey,
                                              final PublicKey authorityKey,
                                              final PublicKey proposedOwner) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(TRANSFER_OWNERSHIP_DISCRIMINATOR, _data, 0);
    proposedOwner.write(_data, i);

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, _data);
  }

  public record TransferOwnershipIxData(Discriminator discriminator, PublicKey proposedOwner) implements Borsh {  

    public static TransferOwnershipIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static TransferOwnershipIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var proposedOwner = readPubKey(_data, i);
      return new TransferOwnershipIxData(discriminator, proposedOwner);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      proposedOwner.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ACCEPT_OWNERSHIP_DISCRIMINATOR = toDiscriminator(172, 23, 43, 13, 238, 213, 85, 150);

  public static Instruction acceptOwnership(final AccountMeta invokedOcr2ProgramMeta, final PublicKey stateKey, final PublicKey authorityKey) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(authorityKey)
    );

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, ACCEPT_OWNERSHIP_DISCRIMINATOR);
  }

  public static final Discriminator CREATE_PROPOSAL_DISCRIMINATOR = toDiscriminator(132, 116, 68, 174, 216, 160, 198, 22);

  public static Instruction createProposal(final AccountMeta invokedOcr2ProgramMeta,
                                           final PublicKey proposalKey,
                                           final PublicKey authorityKey,
                                           final long offchainConfigVersion) {
    final var keys = List.of(
      createWrite(proposalKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(CREATE_PROPOSAL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, offchainConfigVersion);

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, _data);
  }

  public record CreateProposalIxData(Discriminator discriminator, long offchainConfigVersion) implements Borsh {  

    public static CreateProposalIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static CreateProposalIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var offchainConfigVersion = getInt64LE(_data, i);
      return new CreateProposalIxData(discriminator, offchainConfigVersion);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, offchainConfigVersion);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator WRITE_OFFCHAIN_CONFIG_DISCRIMINATOR = toDiscriminator(171, 64, 173, 138, 151, 188, 68, 168);

  public static Instruction writeOffchainConfig(final AccountMeta invokedOcr2ProgramMeta,
                                                final PublicKey proposalKey,
                                                final PublicKey authorityKey,
                                                final byte[] offchainConfig) {
    final var keys = List.of(
      createWrite(proposalKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[12 + Borsh.lenVector(offchainConfig)];
    int i = writeDiscriminator(WRITE_OFFCHAIN_CONFIG_DISCRIMINATOR, _data, 0);
    Borsh.writeVector(offchainConfig, _data, i);

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, _data);
  }

  public record WriteOffchainConfigIxData(Discriminator discriminator, byte[] offchainConfig) implements Borsh {  

    public static WriteOffchainConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static WriteOffchainConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final byte[] offchainConfig = Borsh.readbyteVector(_data, i);
      return new WriteOffchainConfigIxData(discriminator, offchainConfig);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(offchainConfig, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(offchainConfig);
    }
  }

  public static final Discriminator FINALIZE_PROPOSAL_DISCRIMINATOR = toDiscriminator(23, 68, 51, 167, 109, 173, 187, 164);

  public static Instruction finalizeProposal(final AccountMeta invokedOcr2ProgramMeta, final PublicKey proposalKey, final PublicKey authorityKey) {
    final var keys = List.of(
      createWrite(proposalKey),
      createReadOnlySigner(authorityKey)
    );

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, FINALIZE_PROPOSAL_DISCRIMINATOR);
  }

  public static final Discriminator CLOSE_PROPOSAL_DISCRIMINATOR = toDiscriminator(213, 178, 139, 19, 50, 191, 82, 245);

  public static Instruction closeProposal(final AccountMeta invokedOcr2ProgramMeta,
                                          final PublicKey proposalKey,
                                          final PublicKey receiverKey,
                                          final PublicKey authorityKey) {
    final var keys = List.of(
      createWrite(proposalKey),
      createWrite(receiverKey),
      createReadOnlySigner(authorityKey)
    );

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, CLOSE_PROPOSAL_DISCRIMINATOR);
  }

  public static final Discriminator ACCEPT_PROPOSAL_DISCRIMINATOR = toDiscriminator(33, 190, 130, 178, 27, 12, 168, 238);

  public static Instruction acceptProposal(final AccountMeta invokedOcr2ProgramMeta,
                                           final PublicKey stateKey,
                                           final PublicKey proposalKey,
                                           final PublicKey receiverKey,
                                           final PublicKey authorityKey,
                                           final PublicKey tokenVaultKey,
                                           final PublicKey vaultAuthorityKey,
                                           final PublicKey tokenProgramKey,
                                           final byte[] digest) {
    final var keys = List.of(
      createWrite(stateKey),
      createWrite(proposalKey),
      createWrite(receiverKey),
      createReadOnlySigner(authorityKey),
      createWrite(tokenVaultKey),
      createRead(vaultAuthorityKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[12 + Borsh.lenVector(digest)];
    int i = writeDiscriminator(ACCEPT_PROPOSAL_DISCRIMINATOR, _data, 0);
    Borsh.writeVector(digest, _data, i);

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, _data);
  }

  public record AcceptProposalIxData(Discriminator discriminator, byte[] digest) implements Borsh {  

    public static AcceptProposalIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static AcceptProposalIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final byte[] digest = Borsh.readbyteVector(_data, i);
      return new AcceptProposalIxData(discriminator, digest);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(digest, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(digest);
    }
  }

  public static final Discriminator PROPOSE_CONFIG_DISCRIMINATOR = toDiscriminator(163, 247, 238, 160, 236, 129, 153, 160);

  public static Instruction proposeConfig(final AccountMeta invokedOcr2ProgramMeta,
                                          final PublicKey proposalKey,
                                          final PublicKey authorityKey,
                                          final NewOracle[] newOracles,
                                          final int f) {
    final var keys = List.of(
      createWrite(proposalKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[9 + Borsh.lenVector(newOracles)];
    int i = writeDiscriminator(PROPOSE_CONFIG_DISCRIMINATOR, _data, 0);
    i += Borsh.writeVector(newOracles, _data, i);
    _data[i] = (byte) f;

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, _data);
  }

  public record ProposeConfigIxData(Discriminator discriminator, NewOracle[] newOracles, int f) implements Borsh {  

    public static ProposeConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ProposeConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var newOracles = Borsh.readVector(NewOracle.class, NewOracle::read, _data, i);
      i += Borsh.lenVector(newOracles);
      final var f = _data[i] & 0xFF;
      return new ProposeConfigIxData(discriminator, newOracles, f);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(newOracles, _data, i);
      _data[i] = (byte) f;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(newOracles) + 1;
    }
  }

  public static final Discriminator PROPOSE_PAYEES_DISCRIMINATOR = toDiscriminator(76, 228, 45, 220, 157, 7, 182, 228);

  public static Instruction proposePayees(final AccountMeta invokedOcr2ProgramMeta,
                                          final PublicKey proposalKey,
                                          final PublicKey authorityKey,
                                          final PublicKey tokenMint,
                                          final PublicKey[] payees) {
    final var keys = List.of(
      createWrite(proposalKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[40 + Borsh.lenVector(payees)];
    int i = writeDiscriminator(PROPOSE_PAYEES_DISCRIMINATOR, _data, 0);
    tokenMint.write(_data, i);
    i += 32;
    Borsh.writeVector(payees, _data, i);

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, _data);
  }

  public record ProposePayeesIxData(Discriminator discriminator, PublicKey tokenMint, PublicKey[] payees) implements Borsh {  

    public static ProposePayeesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ProposePayeesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var tokenMint = readPubKey(_data, i);
      i += 32;
      final var payees = Borsh.readPublicKeyVector(_data, i);
      return new ProposePayeesIxData(discriminator, tokenMint, payees);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      tokenMint.write(_data, i);
      i += 32;
      i += Borsh.writeVector(payees, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 32 + Borsh.lenVector(payees);
    }
  }

  public static final Discriminator SET_REQUESTER_ACCESS_CONTROLLER_DISCRIMINATOR = toDiscriminator(182, 229, 210, 202, 190, 116, 92, 236);

  public static Instruction setRequesterAccessController(final AccountMeta invokedOcr2ProgramMeta,
                                                         final PublicKey stateKey,
                                                         final PublicKey authorityKey,
                                                         final PublicKey accessControllerKey) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(authorityKey),
      createRead(accessControllerKey)
    );

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, SET_REQUESTER_ACCESS_CONTROLLER_DISCRIMINATOR);
  }

  public static final Discriminator REQUEST_NEW_ROUND_DISCRIMINATOR = toDiscriminator(79, 230, 6, 173, 193, 109, 226, 61);

  public static Instruction requestNewRound(final AccountMeta invokedOcr2ProgramMeta,
                                            final PublicKey stateKey,
                                            final PublicKey authorityKey,
                                            final PublicKey accessControllerKey) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(authorityKey),
      createRead(accessControllerKey)
    );

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, REQUEST_NEW_ROUND_DISCRIMINATOR);
  }

  public static final Discriminator SET_BILLING_ACCESS_CONTROLLER_DISCRIMINATOR = toDiscriminator(176, 167, 195, 39, 175, 182, 51, 23);

  public static Instruction setBillingAccessController(final AccountMeta invokedOcr2ProgramMeta,
                                                       final PublicKey stateKey,
                                                       final PublicKey authorityKey,
                                                       final PublicKey accessControllerKey) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(authorityKey),
      createRead(accessControllerKey)
    );

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, SET_BILLING_ACCESS_CONTROLLER_DISCRIMINATOR);
  }

  public static final Discriminator SET_BILLING_DISCRIMINATOR = toDiscriminator(58, 131, 213, 166, 230, 120, 88, 95);

  public static Instruction setBilling(final AccountMeta invokedOcr2ProgramMeta,
                                       final PublicKey stateKey,
                                       final PublicKey authorityKey,
                                       final PublicKey accessControllerKey,
                                       final PublicKey tokenVaultKey,
                                       final PublicKey vaultAuthorityKey,
                                       final PublicKey tokenProgramKey,
                                       final int observationPaymentGjuels,
                                       final int transmissionPaymentGjuels) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(authorityKey),
      createRead(accessControllerKey),
      createWrite(tokenVaultKey),
      createRead(vaultAuthorityKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(SET_BILLING_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, observationPaymentGjuels);
    i += 4;
    putInt32LE(_data, i, transmissionPaymentGjuels);

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, _data);
  }

  public record SetBillingIxData(Discriminator discriminator, int observationPaymentGjuels, int transmissionPaymentGjuels) implements Borsh {  

    public static SetBillingIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SetBillingIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var observationPaymentGjuels = getInt32LE(_data, i);
      i += 4;
      final var transmissionPaymentGjuels = getInt32LE(_data, i);
      return new SetBillingIxData(discriminator, observationPaymentGjuels, transmissionPaymentGjuels);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt32LE(_data, i, observationPaymentGjuels);
      i += 4;
      putInt32LE(_data, i, transmissionPaymentGjuels);
      i += 4;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator WITHDRAW_FUNDS_DISCRIMINATOR = toDiscriminator(241, 36, 29, 111, 208, 31, 104, 217);

  public static Instruction withdrawFunds(final AccountMeta invokedOcr2ProgramMeta,
                                          final PublicKey stateKey,
                                          final PublicKey authorityKey,
                                          final PublicKey accessControllerKey,
                                          final PublicKey tokenVaultKey,
                                          final PublicKey vaultAuthorityKey,
                                          final PublicKey recipientKey,
                                          final PublicKey tokenProgramKey,
                                          final long amountGjuels) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(authorityKey),
      createRead(accessControllerKey),
      createWrite(tokenVaultKey),
      createRead(vaultAuthorityKey),
      createWrite(recipientKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(WITHDRAW_FUNDS_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountGjuels);

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, _data);
  }

  public record WithdrawFundsIxData(Discriminator discriminator, long amountGjuels) implements Borsh {  

    public static WithdrawFundsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static WithdrawFundsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amountGjuels = getInt64LE(_data, i);
      return new WithdrawFundsIxData(discriminator, amountGjuels);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amountGjuels);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator WITHDRAW_PAYMENT_DISCRIMINATOR = toDiscriminator(118, 231, 133, 187, 151, 154, 111, 95);

  public static Instruction withdrawPayment(final AccountMeta invokedOcr2ProgramMeta,
                                            final PublicKey stateKey,
                                            final PublicKey authorityKey,
                                            final PublicKey tokenVaultKey,
                                            final PublicKey vaultAuthorityKey,
                                            final PublicKey payeeKey,
                                            final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(authorityKey),
      createWrite(tokenVaultKey),
      createRead(vaultAuthorityKey),
      createWrite(payeeKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, WITHDRAW_PAYMENT_DISCRIMINATOR);
  }

  public static final Discriminator PAY_ORACLES_DISCRIMINATOR = toDiscriminator(150, 220, 13, 20, 104, 214, 61, 89);

  public static Instruction payOracles(final AccountMeta invokedOcr2ProgramMeta,
                                       final PublicKey stateKey,
                                       final PublicKey authorityKey,
                                       final PublicKey accessControllerKey,
                                       final PublicKey tokenVaultKey,
                                       final PublicKey vaultAuthorityKey,
                                       final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(authorityKey),
      createRead(accessControllerKey),
      createWrite(tokenVaultKey),
      createRead(vaultAuthorityKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, PAY_ORACLES_DISCRIMINATOR);
  }

  public static final Discriminator TRANSFER_PAYEESHIP_DISCRIMINATOR = toDiscriminator(116, 68, 213, 225, 193, 225, 171, 206);

  public static Instruction transferPayeeship(final AccountMeta invokedOcr2ProgramMeta,
                                              final PublicKey stateKey,
                                              final PublicKey authorityKey,
                                              final PublicKey transmitterKey,
                                              final PublicKey payeeKey,
                                              final PublicKey proposedPayeeKey) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(authorityKey),
      createRead(transmitterKey),
      createRead(payeeKey),
      createRead(proposedPayeeKey)
    );

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, TRANSFER_PAYEESHIP_DISCRIMINATOR);
  }

  public static final Discriminator ACCEPT_PAYEESHIP_DISCRIMINATOR = toDiscriminator(142, 208, 219, 62, 82, 13, 189, 70);

  public static Instruction acceptPayeeship(final AccountMeta invokedOcr2ProgramMeta,
                                            final PublicKey stateKey,
                                            final PublicKey authorityKey,
                                            final PublicKey transmitterKey,
                                            final PublicKey proposedPayeeKey) {
    final var keys = List.of(
      createWrite(stateKey),
      createReadOnlySigner(authorityKey),
      createRead(transmitterKey),
      createRead(proposedPayeeKey)
    );

    return Instruction.createInstruction(invokedOcr2ProgramMeta, keys, ACCEPT_PAYEESHIP_DISCRIMINATOR);
  }

  private Ocr2Program() {
  }
}
