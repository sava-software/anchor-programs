package software.sava.anchor.programs.glam.mint.anchor;

import java.util.List;
import java.util.OptionalInt;

import software.sava.anchor.programs.glam.mint.anchor.types.AccountType;
import software.sava.anchor.programs.glam.mint.anchor.types.MintModel;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static java.util.Objects.requireNonNullElse;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.anchor.AnchorUtil.writeDiscriminator;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class GlamMintProgram {

  public static final Discriminator BURN_TOKENS_DISCRIMINATOR = toDiscriminator(76, 15, 51, 254, 229, 215, 121, 66);

  public static Instruction burnTokens(final AccountMeta invokedGlamMintProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey glamStateKey,
                                       final PublicKey glamSignerKey,
                                       final PublicKey glamMintKey,
                                       final PublicKey fromAtaKey,
                                       final PublicKey fromKey,
                                       final long amount) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createWrite(fromAtaKey),
      createRead(fromKey),
      createRead(solanaAccounts.token2022Program())
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(BURN_TOKENS_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, _data);
  }

  public record BurnTokensIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static BurnTokensIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static BurnTokensIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new BurnTokensIxData(discriminator, amount);
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

  public static final Discriminator CANCEL_DISCRIMINATOR = toDiscriminator(232, 219, 223, 41, 219, 236, 220, 190);

  public static Instruction cancel(final AccountMeta invokedGlamMintProgramMeta,
                                   final SolanaAccounts solanaAccounts,
                                   final PublicKey glamStateKey,
                                   final PublicKey glamMintKey,
                                   final PublicKey glamEscrowKey,
                                   final PublicKey requestQueueKey,
                                   final PublicKey signerKey,
                                   final PublicKey recoverTokenMintKey,
                                   final PublicKey signerAtaKey,
                                   final PublicKey escrowAtaKey,
                                   final PublicKey recoverTokenProgramKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamMintKey),
      createRead(glamEscrowKey),
      createWrite(requestQueueKey),
      createWritableSigner(signerKey),
      createRead(recoverTokenMintKey),
      createWrite(signerAtaKey),
      createWrite(escrowAtaKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(recoverTokenProgramKey),
      createRead(solanaAccounts.associatedTokenAccountProgram())
    );

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, CANCEL_DISCRIMINATOR);
  }

  public static final Discriminator CLAIM_DISCRIMINATOR = toDiscriminator(62, 198, 214, 193, 213, 159, 108, 210);

  public static Instruction claim(final AccountMeta invokedGlamMintProgramMeta,
                                  final SolanaAccounts solanaAccounts,
                                  final PublicKey glamStateKey,
                                  final PublicKey glamMintKey,
                                  final PublicKey glamEscrowKey,
                                  final PublicKey requestQueueKey,
                                  final PublicKey signerKey,
                                  final PublicKey claimTokenMintKey,
                                  final PublicKey signerAtaKey,
                                  final PublicKey escrowAtaKey,
                                  final PublicKey signerPolicyKey,
                                  final PublicKey claimTokenProgramKey,
                                  final PublicKey glamPoliciesProgramKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamMintKey),
      createRead(glamEscrowKey),
      createWrite(requestQueueKey),
      createWritableSigner(signerKey),
      createRead(claimTokenMintKey),
      createWrite(signerAtaKey),
      createWrite(escrowAtaKey),
      createWrite(requireNonNullElse(signerPolicyKey, invokedGlamMintProgramMeta.publicKey())),
      createRead(solanaAccounts.systemProgram()),
      createRead(claimTokenProgramKey),
      createRead(glamPoliciesProgramKey),
      createRead(solanaAccounts.associatedTokenAccountProgram())
    );

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, CLAIM_DISCRIMINATOR);
  }

  public static final Discriminator CLAIM_FEES_DISCRIMINATOR = toDiscriminator(82, 251, 233, 156, 12, 52, 184, 202);

  public static Instruction claimFees(final AccountMeta invokedGlamMintProgramMeta,
                                      final SolanaAccounts solanaAccounts,
                                      final PublicKey glamStateKey,
                                      final PublicKey glamVaultKey,
                                      final PublicKey glamMintKey,
                                      final PublicKey glamEscrowKey,
                                      final PublicKey escrowMintAtaKey,
                                      final PublicKey signerKey,
                                      final PublicKey depositAssetKey,
                                      // To pay out fees
                                      final PublicKey vaultDepositAtaKey,
                                      // To receive protocol fee
                                      final PublicKey protocolFeeAuthorityKey,
                                      final PublicKey protocolFeeAuthorityAtaKey,
                                      // To receive manager fee
                                      final PublicKey managerFeeAuthorityKey,
                                      final PublicKey managerFeeAuthorityAtaKey,
                                      final PublicKey glamConfigKey,
                                      final PublicKey glamProtocolProgramKey,
                                      final PublicKey depositTokenProgramKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWrite(glamMintKey),
      createRead(glamEscrowKey),
      createWrite(escrowMintAtaKey),
      createWritableSigner(signerKey),
      createRead(depositAssetKey),
      createWrite(vaultDepositAtaKey),
      createRead(protocolFeeAuthorityKey),
      createWrite(protocolFeeAuthorityAtaKey),
      createRead(managerFeeAuthorityKey),
      createWrite(managerFeeAuthorityAtaKey),
      createRead(glamConfigKey),
      createRead(glamProtocolProgramKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(depositTokenProgramKey),
      createRead(solanaAccounts.token2022Program())
    );

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, CLAIM_FEES_DISCRIMINATOR);
  }

  public static final Discriminator CLOSE_MINT_DISCRIMINATOR = toDiscriminator(149, 251, 157, 212, 65, 181, 235, 129);

  public static Instruction closeMint(final AccountMeta invokedGlamMintProgramMeta,
                                      final SolanaAccounts solanaAccounts,
                                      final PublicKey glamStateKey,
                                      final PublicKey glamSignerKey,
                                      final PublicKey glamMintKey,
                                      final PublicKey extraMetasAccountKey,
                                      final PublicKey policiesProgramKey,
                                      final PublicKey glamProtocolKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createWrite(requireNonNullElse(extraMetasAccountKey, invokedGlamMintProgramMeta.publicKey())),
      createRead(solanaAccounts.systemProgram()),
      createRead(policiesProgramKey),
      createRead(solanaAccounts.token2022Program()),
      createRead(glamProtocolKey)
    );

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, CLOSE_MINT_DISCRIMINATOR);
  }

  public static final Discriminator CRYSTALLIZE_FEES_DISCRIMINATOR = toDiscriminator(78, 0, 111, 26, 7, 12, 41, 249);

  public static Instruction crystallizeFees(final AccountMeta invokedGlamMintProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamEscrowKey,
                                            final PublicKey glamMintKey,
                                            final PublicKey escrowMintAtaKey,
                                            final PublicKey signerKey,
                                            final PublicKey glamProtocolProgramKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamEscrowKey),
      createWrite(glamMintKey),
      createWrite(escrowMintAtaKey),
      createWritableSigner(signerKey),
      createRead(glamProtocolProgramKey),
      createRead(solanaAccounts.token2022Program())
    );

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, CRYSTALLIZE_FEES_DISCRIMINATOR);
  }

  public static final Discriminator EMERGENCY_UPDATE_MINT_DISCRIMINATOR = toDiscriminator(141, 210, 26, 160, 120, 140, 28, 239);

  public static Instruction emergencyUpdateMint(final AccountMeta invokedGlamMintProgramMeta,
                                                final SolanaAccounts solanaAccounts,
                                                final PublicKey glamStateKey,
                                                final PublicKey glamSignerKey,
                                                final PublicKey glamMintKey,
                                                final PublicKey policiesProgramKey,
                                                final PublicKey glamProtocolKey,
                                                final MintModel mintModel) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.token2022Program()),
      createRead(policiesProgramKey),
      createRead(glamProtocolKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(mintModel)];
    int i = writeDiscriminator(EMERGENCY_UPDATE_MINT_DISCRIMINATOR, _data, 0);
    Borsh.write(mintModel, _data, i);

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, _data);
  }

  public record EmergencyUpdateMintIxData(Discriminator discriminator, MintModel mintModel) implements Borsh {  

    public static EmergencyUpdateMintIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static EmergencyUpdateMintIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintModel = MintModel.read(_data, i);
      return new EmergencyUpdateMintIxData(discriminator, mintModel);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(mintModel, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(mintModel);
    }
  }

  public static final Discriminator FORCE_TRANSFER_TOKENS_DISCRIMINATOR = toDiscriminator(185, 34, 78, 211, 192, 13, 160, 37);

  public static Instruction forceTransferTokens(final AccountMeta invokedGlamMintProgramMeta,
                                                final SolanaAccounts solanaAccounts,
                                                final PublicKey glamStateKey,
                                                final PublicKey glamSignerKey,
                                                final PublicKey glamMintKey,
                                                final PublicKey fromAtaKey,
                                                final PublicKey toAtaKey,
                                                final PublicKey fromKey,
                                                final PublicKey toKey,
                                                final PublicKey toPolicyAccountKey,
                                                final PublicKey policiesProgramKey,
                                                final long amount) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createWrite(fromAtaKey),
      createWrite(toAtaKey),
      createRead(fromKey),
      createRead(toKey),
      createWrite(requireNonNullElse(toPolicyAccountKey, invokedGlamMintProgramMeta.publicKey())),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.token2022Program()),
      createRead(policiesProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(FORCE_TRANSFER_TOKENS_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, _data);
  }

  public record ForceTransferTokensIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static ForceTransferTokensIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static ForceTransferTokensIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new ForceTransferTokensIxData(discriminator, amount);
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

  public static final Discriminator FULFILL_DISCRIMINATOR = toDiscriminator(143, 2, 52, 206, 174, 164, 247, 72);

  public static Instruction fulfill(final AccountMeta invokedGlamMintProgramMeta,
                                    final SolanaAccounts solanaAccounts,
                                    final PublicKey glamStateKey,
                                    final PublicKey glamVaultKey,
                                    final PublicKey glamMintKey,
                                    final PublicKey glamEscrowKey,
                                    final PublicKey requestQueueKey,
                                    final PublicKey signerKey,
                                    final PublicKey escrowMintAtaKey,
                                    final PublicKey assetKey,
                                    final PublicKey vaultAssetAtaKey,
                                    final PublicKey escrowAssetAtaKey,
                                    final PublicKey depositTokenProgramKey,
                                    final PublicKey glamProtocolProgramKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWrite(glamMintKey),
      createRead(glamEscrowKey),
      createWrite(requestQueueKey),
      createWritableSigner(signerKey),
      createWrite(escrowMintAtaKey),
      createRead(assetKey),
      createWrite(vaultAssetAtaKey),
      createWrite(escrowAssetAtaKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(depositTokenProgramKey),
      createRead(solanaAccounts.token2022Program()),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(glamProtocolProgramKey)
    );

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, FULFILL_DISCRIMINATOR);
  }

  public static final Discriminator INITIALIZE_MINT_DISCRIMINATOR = toDiscriminator(209, 42, 195, 4, 129, 85, 209, 44);

  // Initialize a new GLAM mint with extensions and metadata.
  // 
  // * `mint_model` - Configuration model containing mint parameters and metadata
  // * `created_key` - 8-byte key used in the GLAM state PDA derivation
  // * `account_type` - Fund (for tokenized vault mint) or Mint
  // * `decimals` - Decimals of new mint
  public static Instruction initializeMint(final AccountMeta invokedGlamMintProgramMeta,
                                           final SolanaAccounts solanaAccounts,
                                           final PublicKey glamStateKey,
                                           final PublicKey signerKey,
                                           final PublicKey newMintKey,
                                           final PublicKey requestQueueKey,
                                           final PublicKey extraMetasAccountKey,
                                           final PublicKey baseAssetMintKey,
                                           final PublicKey policiesProgramKey,
                                           final PublicKey glamProtocolKey,
                                           final MintModel mintModel,
                                           final byte[] createdKey,
                                           final AccountType accountType,
                                           final OptionalInt decimals) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(signerKey),
      createWrite(newMintKey),
      createWrite(requestQueueKey),
      createWrite(extraMetasAccountKey),
      createRead(baseAssetMintKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.token2022Program()),
      createRead(policiesProgramKey),
      createRead(glamProtocolKey)
    );

    final byte[] _data = new byte[
        8 + Borsh.len(mintModel) + Borsh.lenArray(createdKey) + Borsh.len(accountType)
        + (decimals == null || decimals.isEmpty() ? 1 : 2)
    ];
    int i = writeDiscriminator(INITIALIZE_MINT_DISCRIMINATOR, _data, 0);
    i += Borsh.write(mintModel, _data, i);
    i += Borsh.writeArray(createdKey, _data, i);
    i += Borsh.write(accountType, _data, i);
    Borsh.writeOptionalbyte(decimals, _data, i);

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, _data);
  }

  public record InitializeMintIxData(Discriminator discriminator,
                                     MintModel mintModel,
                                     byte[] createdKey,
                                     AccountType accountType,
                                     OptionalInt decimals) implements Borsh {  

    public static InitializeMintIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int CREATED_KEY_LEN = 8;
    public static InitializeMintIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintModel = MintModel.read(_data, i);
      i += Borsh.len(mintModel);
      final var createdKey = new byte[8];
      i += Borsh.readArray(createdKey, _data, i);
      final var accountType = AccountType.read(_data, i);
      i += Borsh.len(accountType);
      final var decimals = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
      return new InitializeMintIxData(discriminator,
                                      mintModel,
                                      createdKey,
                                      accountType,
                                      decimals);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(mintModel, _data, i);
      i += Borsh.writeArray(createdKey, _data, i);
      i += Borsh.write(accountType, _data, i);
      i += Borsh.writeOptionalbyte(decimals, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(mintModel) + Borsh.lenArray(createdKey) + Borsh.len(accountType) + (decimals == null || decimals.isEmpty() ? 1 : (1 + 1));
    }
  }

  public static final Discriminator MINT_TOKENS_DISCRIMINATOR = toDiscriminator(59, 132, 24, 246, 122, 39, 8, 243);

  public static Instruction mintTokens(final AccountMeta invokedGlamMintProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey glamStateKey,
                                       final PublicKey glamSignerKey,
                                       final PublicKey glamMintKey,
                                       final PublicKey mintToKey,
                                       final PublicKey recipientKey,
                                       final PublicKey policyAccountKey,
                                       final PublicKey policiesProgramKey,
                                       final long amount) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createWrite(mintToKey),
      createWrite(recipientKey),
      createWrite(requireNonNullElse(policyAccountKey, invokedGlamMintProgramMeta.publicKey())),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.token2022Program()),
      createRead(policiesProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(MINT_TOKENS_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, _data);
  }

  public record MintTokensIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static MintTokensIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static MintTokensIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new MintTokensIxData(discriminator, amount);
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

  public static final Discriminator QUEUED_REDEEM_DISCRIMINATOR = toDiscriminator(82, 242, 202, 93, 170, 196, 215, 113);

  public static Instruction queuedRedeem(final AccountMeta invokedGlamMintProgramMeta,
                                         final SolanaAccounts solanaAccounts,
                                         final PublicKey glamStateKey,
                                         final PublicKey glamMintKey,
                                         final PublicKey glamEscrowKey,
                                         final PublicKey requestQueueKey,
                                         final PublicKey signerKey,
                                         final PublicKey signerMintAtaKey,
                                         final PublicKey escrowMintAtaKey,
                                         final long amountIn) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamMintKey),
      createRead(glamEscrowKey),
      createWrite(requestQueueKey),
      createWritableSigner(signerKey),
      createWrite(signerMintAtaKey),
      createWrite(escrowMintAtaKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.token2022Program()),
      createRead(solanaAccounts.associatedTokenAccountProgram())
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(QUEUED_REDEEM_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountIn);

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, _data);
  }

  public record QueuedRedeemIxData(Discriminator discriminator, long amountIn) implements Borsh {  

    public static QueuedRedeemIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static QueuedRedeemIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amountIn = getInt64LE(_data, i);
      return new QueuedRedeemIxData(discriminator, amountIn);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amountIn);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator QUEUED_SUBSCRIBE_DISCRIMINATOR = toDiscriminator(107, 180, 212, 63, 146, 0, 159, 255);

  public static Instruction queuedSubscribe(final AccountMeta invokedGlamMintProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamMintKey,
                                            final PublicKey glamEscrowKey,
                                            final PublicKey requestQueueKey,
                                            final PublicKey signerKey,
                                            final PublicKey depositAssetKey,
                                            final PublicKey escrowDepositAtaKey,
                                            final PublicKey signerDepositAtaKey,
                                            final PublicKey depositTokenProgramKey,
                                            final long amountIn) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamMintKey),
      createRead(glamEscrowKey),
      createWrite(requestQueueKey),
      createWritableSigner(signerKey),
      createRead(depositAssetKey),
      createWrite(escrowDepositAtaKey),
      createWrite(signerDepositAtaKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(depositTokenProgramKey),
      createRead(solanaAccounts.associatedTokenAccountProgram())
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(QUEUED_SUBSCRIBE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountIn);

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, _data);
  }

  public record QueuedSubscribeIxData(Discriminator discriminator, long amountIn) implements Borsh {  

    public static QueuedSubscribeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static QueuedSubscribeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amountIn = getInt64LE(_data, i);
      return new QueuedSubscribeIxData(discriminator, amountIn);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amountIn);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_PROTOCOL_FEES_DISCRIMINATOR = toDiscriminator(49, 143, 189, 18, 56, 206, 158, 226);

  public static Instruction setProtocolFees(final AccountMeta invokedGlamMintProgramMeta,
                                            final PublicKey glamStateKey,
                                            final PublicKey signerKey,
                                            final PublicKey glamConfigKey,
                                            final PublicKey glamProtocolProgramKey,
                                            final int baseFeeBps,
                                            final int flowFeeBps) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(signerKey),
      createRead(glamConfigKey),
      createRead(glamProtocolProgramKey)
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(SET_PROTOCOL_FEES_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, baseFeeBps);
    i += 2;
    putInt16LE(_data, i, flowFeeBps);

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, _data);
  }

  public record SetProtocolFeesIxData(Discriminator discriminator, int baseFeeBps, int flowFeeBps) implements Borsh {  

    public static SetProtocolFeesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 12;

    public static SetProtocolFeesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var baseFeeBps = getInt16LE(_data, i);
      i += 2;
      final var flowFeeBps = getInt16LE(_data, i);
      return new SetProtocolFeesIxData(discriminator, baseFeeBps, flowFeeBps);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, baseFeeBps);
      i += 2;
      putInt16LE(_data, i, flowFeeBps);
      i += 2;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_TOKEN_ACCOUNTS_STATES_DISCRIMINATOR = toDiscriminator(50, 133, 45, 86, 117, 66, 115, 195);

  public static Instruction setTokenAccountsStates(final AccountMeta invokedGlamMintProgramMeta,
                                                   final SolanaAccounts solanaAccounts,
                                                   final PublicKey glamStateKey,
                                                   final PublicKey glamSignerKey,
                                                   final PublicKey glamMintKey,
                                                   final boolean frozen) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createRead(solanaAccounts.token2022Program())
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(SET_TOKEN_ACCOUNTS_STATES_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) (frozen ? 1 : 0);

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, _data);
  }

  public record SetTokenAccountsStatesIxData(Discriminator discriminator, boolean frozen) implements Borsh {  

    public static SetTokenAccountsStatesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static SetTokenAccountsStatesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var frozen = _data[i] == 1;
      return new SetTokenAccountsStatesIxData(discriminator, frozen);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) (frozen ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SUBSCRIBE_DISCRIMINATOR = toDiscriminator(254, 28, 191, 138, 156, 179, 183, 53);

  public static Instruction subscribe(final AccountMeta invokedGlamMintProgramMeta,
                                      final SolanaAccounts solanaAccounts,
                                      final PublicKey glamStateKey,
                                      final PublicKey glamVaultKey,
                                      final PublicKey glamMintKey,
                                      final PublicKey glamEscrowKey,
                                      final PublicKey requestQueueKey,
                                      final PublicKey signerKey,
                                      final PublicKey signerMintAtaKey,
                                      final PublicKey escrowMintAtaKey,
                                      final PublicKey depositAssetKey,
                                      final PublicKey vaultDepositAtaKey,
                                      final PublicKey signerDepositAtaKey,
                                      final PublicKey signerPolicyKey,
                                      final PublicKey depositTokenProgramKey,
                                      final PublicKey policiesProgramKey,
                                      final PublicKey glamProtocolProgramKey,
                                      final long amountIn) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWrite(glamMintKey),
      createRead(glamEscrowKey),
      createRead(requestQueueKey),
      createWritableSigner(signerKey),
      createWrite(signerMintAtaKey),
      createWrite(escrowMintAtaKey),
      createRead(depositAssetKey),
      createWrite(vaultDepositAtaKey),
      createWrite(signerDepositAtaKey),
      createWrite(requireNonNullElse(signerPolicyKey, invokedGlamMintProgramMeta.publicKey())),
      createRead(solanaAccounts.systemProgram()),
      createRead(depositTokenProgramKey),
      createRead(solanaAccounts.token2022Program()),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(policiesProgramKey),
      createRead(glamProtocolProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(SUBSCRIBE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountIn);

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, _data);
  }

  public record SubscribeIxData(Discriminator discriminator, long amountIn) implements Borsh {  

    public static SubscribeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SubscribeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amountIn = getInt64LE(_data, i);
      return new SubscribeIxData(discriminator, amountIn);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amountIn);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_MINT_DISCRIMINATOR = toDiscriminator(212, 203, 57, 78, 75, 245, 222, 5);

  public static Instruction updateMint(final AccountMeta invokedGlamMintProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey glamStateKey,
                                       final PublicKey glamSignerKey,
                                       final PublicKey glamMintKey,
                                       final PublicKey policiesProgramKey,
                                       final PublicKey glamProtocolKey,
                                       final MintModel mintModel) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.token2022Program()),
      createRead(policiesProgramKey),
      createRead(glamProtocolKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(mintModel)];
    int i = writeDiscriminator(UPDATE_MINT_DISCRIMINATOR, _data, 0);
    Borsh.write(mintModel, _data, i);

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, _data);
  }

  public record UpdateMintIxData(Discriminator discriminator, MintModel mintModel) implements Borsh {  

    public static UpdateMintIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateMintIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintModel = MintModel.read(_data, i);
      return new UpdateMintIxData(discriminator, mintModel);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(mintModel, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(mintModel);
    }
  }

  public static final Discriminator UPDATE_MINT_APPLY_TIMELOCK_DISCRIMINATOR = toDiscriminator(223, 241, 80, 24, 120, 25, 82, 134);

  public static Instruction updateMintApplyTimelock(final AccountMeta invokedGlamMintProgramMeta,
                                                    final SolanaAccounts solanaAccounts,
                                                    final PublicKey glamStateKey,
                                                    final PublicKey glamSignerKey,
                                                    final PublicKey glamMintKey,
                                                    final PublicKey policiesProgramKey,
                                                    final PublicKey glamProtocolKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.token2022Program()),
      createRead(policiesProgramKey),
      createRead(glamProtocolKey)
    );

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, UPDATE_MINT_APPLY_TIMELOCK_DISCRIMINATOR);
  }

  private GlamMintProgram() {
  }
}
