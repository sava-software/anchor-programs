package software.sava.anchor.programs.glam.mint.anchor;

import java.util.List;
import java.util.OptionalInt;

import software.sava.anchor.programs.glam.mint.anchor.types.EmergencyUpdateMintArgs;
import software.sava.anchor.programs.glam.mint.anchor.types.MintModel;
import software.sava.anchor.programs.glam.mint.anchor.types.MintPolicy;
import software.sava.anchor.programs.glam.protocol.anchor.types.AccountType;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static java.util.Objects.requireNonNullElse;

import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class GlamMintProgram {

  public static final Discriminator BURN_TOKENS_DISCRIMINATOR = toDiscriminator(76, 15, 51, 254, 229, 215, 121, 66);

  public static Instruction burnTokens(final AccountMeta invokedGlamMintProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey glamStateKey,
                                       final PublicKey glamSignerKey,
                                       final PublicKey glamMintKey,
                                       final PublicKey fromTokenAccountKey,
                                       final PublicKey fromKey,
                                       final long amount) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createWrite(fromTokenAccountKey),
      createRead(fromKey),
      createRead(solanaAccounts.token2022Program())
    );

    final byte[] _data = new byte[16];
    int i = BURN_TOKENS_DISCRIMINATOR.write(_data, 0);
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
      final var discriminator = createAnchorDiscriminator(_data, offset);
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
                                   final PublicKey userKey,
                                   final PublicKey recoverTokenMintKey,
                                   final PublicKey userAtaKey,
                                   final PublicKey escrowAtaKey,
                                   final PublicKey recoverTokenProgramKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamMintKey),
      createRead(glamEscrowKey),
      createWrite(requestQueueKey),
      createWritableSigner(signerKey),
      createRead(userKey),
      createRead(recoverTokenMintKey),
      createWrite(userAtaKey),
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
                                  final PublicKey claimUserKey,
                                  final PublicKey claimTokenMintKey,
                                  final PublicKey claimUserAtaKey,
                                  final PublicKey escrowAtaKey,
                                  final PublicKey claimUserPolicyKey,
                                  final PublicKey claimTokenProgramKey,
                                  final PublicKey glamPoliciesProgramKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamMintKey),
      createRead(glamEscrowKey),
      createWrite(requestQueueKey),
      createWritableSigner(signerKey),
      createRead(claimUserKey),
      createRead(claimTokenMintKey),
      createWrite(claimUserAtaKey),
      createWrite(escrowAtaKey),
      createWrite(requireNonNullElse(claimUserPolicyKey, invokedGlamMintProgramMeta.publicKey())),
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
                                      final PublicKey requestQueueKey,
                                      final PublicKey extraMetasAccountKey,
                                      final PublicKey policiesProgramKey,
                                      final PublicKey glamProtocolKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createWrite(requireNonNullElse(requestQueueKey, invokedGlamMintProgramMeta.publicKey())),
      createWrite(extraMetasAccountKey),
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
                                                final PublicKey glamStateKey,
                                                final PublicKey glamSignerKey,
                                                final PublicKey glamMintKey,
                                                final PublicKey requestQueueKey,
                                                final EmergencyUpdateMintArgs args) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWritableSigner(glamSignerKey),
      createRead(glamMintKey),
      createWrite(requestQueueKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(args)];
    int i = EMERGENCY_UPDATE_MINT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(args, _data, i);

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, _data);
  }

  public record EmergencyUpdateMintIxData(Discriminator discriminator, EmergencyUpdateMintArgs args) implements Borsh {  

    public static EmergencyUpdateMintIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static EmergencyUpdateMintIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var args = EmergencyUpdateMintArgs.read(_data, i);
      return new EmergencyUpdateMintIxData(discriminator, args);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(args, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator FORCE_TRANSFER_TOKENS_DISCRIMINATOR = toDiscriminator(185, 34, 78, 211, 192, 13, 160, 37);

  public static Instruction forceTransferTokens(final AccountMeta invokedGlamMintProgramMeta,
                                                final SolanaAccounts solanaAccounts,
                                                final PublicKey glamStateKey,
                                                final PublicKey glamSignerKey,
                                                final PublicKey glamMintKey,
                                                final PublicKey fromTokenAccountKey,
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
      createWrite(fromTokenAccountKey),
      createWrite(toAtaKey),
      createRead(fromKey),
      createRead(toKey),
      createWrite(requireNonNullElse(toPolicyAccountKey, invokedGlamMintProgramMeta.publicKey())),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.token2022Program()),
      createRead(policiesProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = FORCE_TRANSFER_TOKENS_DISCRIMINATOR.write(_data, 0);
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
      final var discriminator = createAnchorDiscriminator(_data, offset);
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
                                    final PublicKey glamProtocolProgramKey,
                                    final OptionalInt limit) {
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

    final byte[] _data = new byte[
        8
        + (limit == null || limit.isEmpty() ? 1 : 5)
    ];
    int i = FULFILL_DISCRIMINATOR.write(_data, 0);
    Borsh.writeOptional(limit, _data, i);

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, _data);
  }

  public record FulfillIxData(Discriminator discriminator, OptionalInt limit) implements Borsh {  

    public static FulfillIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static FulfillIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var limit = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
      return new FulfillIxData(discriminator, limit);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeOptional(limit, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + (limit == null || limit.isEmpty() ? 1 : (1 + 4));
    }
  }

  public static final Discriminator INITIALIZE_MINT_DISCRIMINATOR = toDiscriminator(209, 42, 195, 4, 129, 85, 209, 44);

  // Initialize a new GLAM mint with extensions and metadata.
  // 
  // - `mint_model` - Configuration model containing mint parameters and metadata
  // - `created_key` - 8-byte key used in the GLAM state PDA derivation
  // - `account_type` - Fund (for tokenized vault mint) or Mint
  // - `decimals` - Decimals of new mint
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
      createWrite(requireNonNullElse(requestQueueKey, invokedGlamMintProgramMeta.publicKey())),
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
    int i = INITIALIZE_MINT_DISCRIMINATOR.write(_data, 0);
    i += Borsh.write(mintModel, _data, i);
    i += Borsh.writeArrayChecked(createdKey, 8, _data, i);
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
      final var discriminator = createAnchorDiscriminator(_data, offset);
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
      i += Borsh.writeArrayChecked(createdKey, 8, _data, i);
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
    int i = MINT_TOKENS_DISCRIMINATOR.write(_data, 0);
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
      final var discriminator = createAnchorDiscriminator(_data, offset);
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

  public static final Discriminator PRICE_DRIFT_USERS_DISCRIMINATOR = toDiscriminator(12, 5, 143, 51, 101, 81, 200, 150);

  // Extra accounts for pricing N drift users under the same user stats:
  // - user_stats x 1
  // - drift_user x N
  // - markets and oracles used by all drift users (no specific order)
  public static Instruction priceDriftUsers(final AccountMeta invokedGlamMintProgramMeta,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamVaultKey,
                                            final PublicKey signerKey,
                                            final PublicKey solUsdOracleKey,
                                            final PublicKey baseAssetOracleKey,
                                            final PublicKey integrationAuthorityKey,
                                            final PublicKey glamConfigKey,
                                            final PublicKey glamProtocolKey,
                                            final PublicKey eventAuthorityKey,
                                            final PublicKey eventProgramKey,
                                            final int numUsers) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(signerKey),
      createRead(solUsdOracleKey),
      createRead(baseAssetOracleKey),
      createRead(integrationAuthorityKey),
      createRead(glamConfigKey),
      createRead(glamProtocolKey),
      createRead(requireNonNullElse(eventAuthorityKey, invokedGlamMintProgramMeta.publicKey())),
      createRead(requireNonNullElse(eventProgramKey, invokedGlamMintProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[9];
    int i = PRICE_DRIFT_USERS_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) numUsers;

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, _data);
  }

  public record PriceDriftUsersIxData(Discriminator discriminator, int numUsers) implements Borsh {  

    public static PriceDriftUsersIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static PriceDriftUsersIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var numUsers = _data[i] & 0xFF;
      return new PriceDriftUsersIxData(discriminator, numUsers);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) numUsers;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator PRICE_DRIFT_VAULT_DEPOSITORS_DISCRIMINATOR = toDiscriminator(234, 16, 238, 70, 189, 23, 98, 160);

  // Extra accounts for pricing N vault depositors:
  // - (vault_depositor, drift_vault, drift_user) x N
  // - spot_market used by drift users of vaults (no specific order)
  // - perp markets used by drift users of vaults (no specific order)
  // - oracles of spot markets and perp markets (no specific order)
  public static Instruction priceDriftVaultDepositors(final AccountMeta invokedGlamMintProgramMeta,
                                                      final PublicKey glamStateKey,
                                                      final PublicKey glamVaultKey,
                                                      final PublicKey signerKey,
                                                      final PublicKey solUsdOracleKey,
                                                      final PublicKey baseAssetOracleKey,
                                                      final PublicKey integrationAuthorityKey,
                                                      final PublicKey glamConfigKey,
                                                      final PublicKey glamProtocolKey,
                                                      final PublicKey eventAuthorityKey,
                                                      final PublicKey eventProgramKey,
                                                      final int numVaultDepositors,
                                                      final int numSpotMarkets,
                                                      final int numPerpMarkets) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(signerKey),
      createRead(solUsdOracleKey),
      createRead(baseAssetOracleKey),
      createRead(integrationAuthorityKey),
      createRead(glamConfigKey),
      createRead(glamProtocolKey),
      createRead(requireNonNullElse(eventAuthorityKey, invokedGlamMintProgramMeta.publicKey())),
      createRead(requireNonNullElse(eventProgramKey, invokedGlamMintProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[11];
    int i = PRICE_DRIFT_VAULT_DEPOSITORS_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) numVaultDepositors;
    ++i;
    _data[i] = (byte) numSpotMarkets;
    ++i;
    _data[i] = (byte) numPerpMarkets;

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, _data);
  }

  public record PriceDriftVaultDepositorsIxData(Discriminator discriminator,
                                                int numVaultDepositors,
                                                int numSpotMarkets,
                                                int numPerpMarkets) implements Borsh {  

    public static PriceDriftVaultDepositorsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 11;

    public static PriceDriftVaultDepositorsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var numVaultDepositors = _data[i] & 0xFF;
      ++i;
      final var numSpotMarkets = _data[i] & 0xFF;
      ++i;
      final var numPerpMarkets = _data[i] & 0xFF;
      return new PriceDriftVaultDepositorsIxData(discriminator, numVaultDepositors, numSpotMarkets, numPerpMarkets);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) numVaultDepositors;
      ++i;
      _data[i] = (byte) numSpotMarkets;
      ++i;
      _data[i] = (byte) numPerpMarkets;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator PRICE_KAMINO_OBLIGATIONS_DISCRIMINATOR = toDiscriminator(166, 110, 234, 179, 240, 179, 69, 246);

  // Prices Kamino obligations.
  // - `num_obligations` Number of kamino obligations to price.
  // - `num_markets` Number of unique markets used by obligations.
  // - `num_reserves` Number of unique reserves used by obligations.
  // 
  // Extra accounts for pricing N kamino obligations:
  // - obligation x num_obligations
  // - reserve x num_reserves: no specific order
  // - market x num_markets: no specific order
  public static Instruction priceKaminoObligations(final AccountMeta invokedGlamMintProgramMeta,
                                                   final PublicKey glamStateKey,
                                                   final PublicKey glamVaultKey,
                                                   final PublicKey signerKey,
                                                   final PublicKey kaminoLendingProgramKey,
                                                   final PublicKey solUsdOracleKey,
                                                   final PublicKey baseAssetOracleKey,
                                                   final PublicKey integrationAuthorityKey,
                                                   final PublicKey glamConfigKey,
                                                   final PublicKey glamProtocolKey,
                                                   final PublicKey eventAuthorityKey,
                                                   final PublicKey eventProgramKey,
                                                   final PublicKey pythOracleKey,
                                                   final PublicKey switchboardPriceOracleKey,
                                                   final PublicKey switchboardTwapOracleKey,
                                                   final PublicKey scopePricesKey,
                                                   final int numObligations,
                                                   final int numMarkets,
                                                   final int numReserves) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(signerKey),
      createRead(kaminoLendingProgramKey),
      createRead(solUsdOracleKey),
      createRead(baseAssetOracleKey),
      createRead(integrationAuthorityKey),
      createRead(glamConfigKey),
      createRead(glamProtocolKey),
      createRead(requireNonNullElse(eventAuthorityKey, invokedGlamMintProgramMeta.publicKey())),
      createRead(requireNonNullElse(eventProgramKey, invokedGlamMintProgramMeta.publicKey())),
      createRead(requireNonNullElse(pythOracleKey, invokedGlamMintProgramMeta.publicKey())),
      createRead(requireNonNullElse(switchboardPriceOracleKey, invokedGlamMintProgramMeta.publicKey())),
      createRead(requireNonNullElse(switchboardTwapOracleKey, invokedGlamMintProgramMeta.publicKey())),
      createRead(requireNonNullElse(scopePricesKey, invokedGlamMintProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[11];
    int i = PRICE_KAMINO_OBLIGATIONS_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) numObligations;
    ++i;
    _data[i] = (byte) numMarkets;
    ++i;
    _data[i] = (byte) numReserves;

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, _data);
  }

  public record PriceKaminoObligationsIxData(Discriminator discriminator,
                                             int numObligations,
                                             int numMarkets,
                                             int numReserves) implements Borsh {  

    public static PriceKaminoObligationsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 11;

    public static PriceKaminoObligationsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var numObligations = _data[i] & 0xFF;
      ++i;
      final var numMarkets = _data[i] & 0xFF;
      ++i;
      final var numReserves = _data[i] & 0xFF;
      return new PriceKaminoObligationsIxData(discriminator, numObligations, numMarkets, numReserves);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) numObligations;
      ++i;
      _data[i] = (byte) numMarkets;
      ++i;
      _data[i] = (byte) numReserves;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator PRICE_KAMINO_VAULT_SHARES_DISCRIMINATOR = toDiscriminator(112, 92, 238, 224, 145, 105, 38, 249);

  // Prices Kamino vault shares.
  // - `num_vaults` Number of kamino vaults to price.
  // 
  // Extra accounts for pricing N kamino vault shares:
  // - (kvault_share_ata, kvault_share_mint, kvault_state, kvault_deposit_token_oracle) x N
  // - reserve x M
  // - M = number of reserves used by all kvaults' allocations
  // - reserve pubkeys must follow the same order of reserves used by each allocation
  public static Instruction priceKaminoVaultShares(final AccountMeta invokedGlamMintProgramMeta,
                                                   final PublicKey glamStateKey,
                                                   final PublicKey glamVaultKey,
                                                   final PublicKey signerKey,
                                                   final PublicKey solUsdOracleKey,
                                                   final PublicKey baseAssetOracleKey,
                                                   final PublicKey integrationAuthorityKey,
                                                   final PublicKey glamConfigKey,
                                                   final PublicKey glamProtocolKey,
                                                   final PublicKey eventAuthorityKey,
                                                   final PublicKey eventProgramKey,
                                                   final int numVaults) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(signerKey),
      createRead(solUsdOracleKey),
      createRead(baseAssetOracleKey),
      createRead(integrationAuthorityKey),
      createRead(glamConfigKey),
      createRead(glamProtocolKey),
      createRead(requireNonNullElse(eventAuthorityKey, invokedGlamMintProgramMeta.publicKey())),
      createRead(requireNonNullElse(eventProgramKey, invokedGlamMintProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[9];
    int i = PRICE_KAMINO_VAULT_SHARES_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) numVaults;

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, _data);
  }

  public record PriceKaminoVaultSharesIxData(Discriminator discriminator, int numVaults) implements Borsh {  

    public static PriceKaminoVaultSharesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static PriceKaminoVaultSharesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var numVaults = _data[i] & 0xFF;
      return new PriceKaminoVaultSharesIxData(discriminator, numVaults);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) numVaults;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator PRICE_VAULT_TOKENS_DISCRIMINATOR = toDiscriminator(54, 42, 16, 199, 20, 183, 50, 137);

  // Prices vault SOL balance and tokens it holds.
  // 
  // Args:
  // - `denom`: Denomination of the price.
  // - `agg_indexes`: Indexes of the aggregation oracles for the tokens (must follow the same order of mints in extra accounts). If aggregation oracle is not used for token #`i`, `agg_indexes[i]` should be set to -1.
  // 
  // Extra accounts for pricing N tokens:
  // - (ata, mint, oracle) x N
  // - optional oracle mapping (only add it if any token uses an agg oracle)
  public static Instruction priceVaultTokens(final AccountMeta invokedGlamMintProgramMeta,
                                             final PublicKey glamStateKey,
                                             final PublicKey glamVaultKey,
                                             final PublicKey signerKey,
                                             final PublicKey solUsdOracleKey,
                                             final PublicKey baseAssetOracleKey,
                                             final PublicKey integrationAuthorityKey,
                                             final PublicKey glamConfigKey,
                                             final PublicKey glamProtocolKey,
                                             final PublicKey eventAuthorityKey,
                                             final PublicKey eventProgramKey,
                                             final short[][] aggIndexes) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(signerKey),
      createRead(solUsdOracleKey),
      createRead(baseAssetOracleKey),
      createRead(integrationAuthorityKey),
      createRead(glamConfigKey),
      createRead(glamProtocolKey),
      createRead(requireNonNullElse(eventAuthorityKey, invokedGlamMintProgramMeta.publicKey())),
      createRead(requireNonNullElse(eventProgramKey, invokedGlamMintProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.lenVectorArray(aggIndexes)];
    int i = PRICE_VAULT_TOKENS_DISCRIMINATOR.write(_data, 0);
    Borsh.writeVectorArrayChecked(aggIndexes, 4, _data, i);

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, _data);
  }

  public record PriceVaultTokensIxData(Discriminator discriminator, short[][] aggIndexes) implements Borsh {  

    public static PriceVaultTokensIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static PriceVaultTokensIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var aggIndexes = Borsh.readMultiDimensionshortVectorArray(4, _data, i);
      return new PriceVaultTokensIxData(discriminator, aggIndexes);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVectorArrayChecked(aggIndexes, 4, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVectorArray(aggIndexes);
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
    int i = QUEUED_REDEEM_DISCRIMINATOR.write(_data, 0);
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
      final var discriminator = createAnchorDiscriminator(_data, offset);
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
    int i = QUEUED_SUBSCRIBE_DISCRIMINATOR.write(_data, 0);
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
      final var discriminator = createAnchorDiscriminator(_data, offset);
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

  public static final Discriminator SET_MINT_POLICY_DISCRIMINATOR = toDiscriminator(12, 208, 252, 52, 166, 250, 137, 169);

  public static Instruction setMintPolicy(final AccountMeta invokedGlamMintProgramMeta,
                                          final PublicKey glamStateKey,
                                          final PublicKey glamSignerKey,
                                          final PublicKey glamProtocolProgramKey,
                                          final MintPolicy policy) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createRead(glamProtocolProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(policy)];
    int i = SET_MINT_POLICY_DISCRIMINATOR.write(_data, 0);
    Borsh.write(policy, _data, i);

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, _data);
  }

  public record SetMintPolicyIxData(Discriminator discriminator, MintPolicy policy) implements Borsh {  

    public static SetMintPolicyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static SetMintPolicyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var policy = MintPolicy.read(_data, i);
      return new SetMintPolicyIxData(discriminator, policy);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(policy, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(policy);
    }
  }

  public static final Discriminator SET_PROTOCOL_FEES_DISCRIMINATOR = toDiscriminator(49, 143, 189, 18, 56, 206, 158, 226);

  public static Instruction setProtocolFees(final AccountMeta invokedGlamMintProgramMeta,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamMintKey,
                                            final PublicKey signerKey,
                                            final PublicKey glamConfigKey,
                                            final PublicKey glamProtocolProgramKey,
                                            final int baseFeeBps,
                                            final int flowFeeBps) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamMintKey),
      createWritableSigner(signerKey),
      createRead(glamConfigKey),
      createRead(glamProtocolProgramKey)
    );

    final byte[] _data = new byte[12];
    int i = SET_PROTOCOL_FEES_DISCRIMINATOR.write(_data, 0);
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
      final var discriminator = createAnchorDiscriminator(_data, offset);
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
    int i = SET_TOKEN_ACCOUNTS_STATES_DISCRIMINATOR.write(_data, 0);
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
      final var discriminator = createAnchorDiscriminator(_data, offset);
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
    int i = SUBSCRIBE_DISCRIMINATOR.write(_data, 0);
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
      final var discriminator = createAnchorDiscriminator(_data, offset);
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
                                       final PublicKey glamProtocolKey,
                                       final MintModel mintModel) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.token2022Program()),
      createRead(glamProtocolKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(mintModel)];
    int i = UPDATE_MINT_DISCRIMINATOR.write(_data, 0);
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
      final var discriminator = createAnchorDiscriminator(_data, offset);
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
                                                    final PublicKey requestQueueKey,
                                                    final PublicKey glamProtocolKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createWrite(glamMintKey),
      createRead(requireNonNullElse(requestQueueKey, invokedGlamMintProgramMeta.publicKey())),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.token2022Program()),
      createRead(glamProtocolKey)
    );

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, UPDATE_MINT_APPLY_TIMELOCK_DISCRIMINATOR);
  }

  public static final Discriminator VALIDATE_AUM_DISCRIMINATOR = toDiscriminator(101, 15, 233, 89, 134, 123, 224, 99);

  // Validates AUM of the vault and emits AumRecord event.
  public static Instruction validateAum(final AccountMeta invokedGlamMintProgramMeta,
                                        final PublicKey glamStateKey,
                                        final PublicKey signerKey,
                                        final PublicKey eventAuthorityKey,
                                        final PublicKey eventProgramKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWritableSigner(signerKey),
      createRead(requireNonNullElse(eventAuthorityKey, invokedGlamMintProgramMeta.publicKey())),
      createRead(requireNonNullElse(eventProgramKey, invokedGlamMintProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedGlamMintProgramMeta, keys, VALIDATE_AUM_DISCRIMINATOR);
  }

  private GlamMintProgram() {
  }
}
