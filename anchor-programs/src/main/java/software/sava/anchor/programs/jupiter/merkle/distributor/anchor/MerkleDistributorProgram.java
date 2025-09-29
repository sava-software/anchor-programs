package software.sava.anchor.programs.jupiter.merkle.distributor.anchor;

import java.util.List;

import software.sava.anchor.programs.jupiter.merkle.distributor.anchor.types.NewDistributorParams;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static java.util.Objects.requireNonNullElse;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class MerkleDistributorProgram {

  public static final Discriminator NEW_DISTRIBUTOR_DISCRIMINATOR = toDiscriminator(32, 139, 112, 171, 0, 2, 225, 155);

  // ADMIN FUNCTIONS ////
  public static Instruction newDistributor(final AccountMeta invokedMerkleDistributorProgramMeta,
                                           // [MerkleDistributor].
                                           final PublicKey distributorKey,
                                           // Base key of the distributor.
                                           final PublicKey baseKey,
                                           // Clawback receiver token account
                                           final PublicKey clawbackReceiverKey,
                                           // The mint to distribute.
                                           final PublicKey mintKey,
                                           // Token vault
                                           // Should create previously
                                           final PublicKey tokenVaultKey,
                                           // Admin wallet, responsible for creating the distributor and paying for the transaction.
                                           // Also has the authority to set the clawback receiver and change itself.
                                           final PublicKey adminKey,
                                           // The [System] program.
                                           final PublicKey systemProgramKey,
                                           // The [Token] program.
                                           final PublicKey tokenProgramKey,
                                           final NewDistributorParams params) {
    final var keys = List.of(
      createWrite(distributorKey),
      createReadOnlySigner(baseKey),
      createWrite(clawbackReceiverKey),
      createRead(mintKey),
      createRead(tokenVaultKey),
      createWritableSigner(adminKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = NEW_DISTRIBUTOR_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedMerkleDistributorProgramMeta, keys, _data);
  }

  public record NewDistributorIxData(Discriminator discriminator, NewDistributorParams params) implements Borsh {  

    public static NewDistributorIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 179;

    public static NewDistributorIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = NewDistributorParams.read(_data, i);
      return new NewDistributorIxData(discriminator, params);
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

  public static final Discriminator CLOSE_DISTRIBUTOR_DISCRIMINATOR = toDiscriminator(202, 56, 180, 143, 46, 104, 106, 112);

  // only available in test phase
  public static Instruction closeDistributor(final AccountMeta invokedMerkleDistributorProgramMeta,
                                             // [MerkleDistributor].
                                             final PublicKey distributorKey,
                                             // Clawback receiver token account
                                             final PublicKey tokenVaultKey,
                                             // Admin wallet, responsible for creating the distributor and paying for the transaction.
                                             // Also has the authority to set the clawback receiver and change itself.
                                             final PublicKey adminKey,
                                             // account receive token back
                                             final PublicKey destinationTokenAccountKey,
                                             // The [Token] program.
                                             final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWrite(distributorKey),
      createWrite(tokenVaultKey),
      createWritableSigner(adminKey),
      createWrite(destinationTokenAccountKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedMerkleDistributorProgramMeta, keys, CLOSE_DISTRIBUTOR_DISCRIMINATOR);
  }

  public static final Discriminator CLOSE_CLAIM_STATUS_DISCRIMINATOR = toDiscriminator(163, 214, 191, 165, 245, 188, 17, 185);

  // only available in test phase
  public static Instruction closeClaimStatus(final AccountMeta invokedMerkleDistributorProgramMeta,
                                             final PublicKey claimStatusKey,
                                             final PublicKey claimantKey,
                                             final PublicKey adminKey) {
    final var keys = List.of(
      createWrite(claimStatusKey),
      createWrite(claimantKey),
      createReadOnlySigner(adminKey)
    );

    return Instruction.createInstruction(invokedMerkleDistributorProgramMeta, keys, CLOSE_CLAIM_STATUS_DISCRIMINATOR);
  }

  public static final Discriminator SET_ACTIVATION_POINT_DISCRIMINATOR = toDiscriminator(91, 249, 15, 165, 26, 129, 254, 125);

  public static Instruction setActivationPoint(final AccountMeta invokedMerkleDistributorProgramMeta,
                                               // [MerkleDistributor].
                                               final PublicKey distributorKey,
                                               // Payer to create the distributor.
                                               final PublicKey adminKey,
                                               final long activationPoint) {
    final var keys = List.of(
      createWrite(distributorKey),
      createWritableSigner(adminKey)
    );

    final byte[] _data = new byte[16];
    int i = SET_ACTIVATION_POINT_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, activationPoint);

    return Instruction.createInstruction(invokedMerkleDistributorProgramMeta, keys, _data);
  }

  public record SetActivationPointIxData(Discriminator discriminator, long activationPoint) implements Borsh {  

    public static SetActivationPointIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SetActivationPointIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var activationPoint = getInt64LE(_data, i);
      return new SetActivationPointIxData(discriminator, activationPoint);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, activationPoint);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CLAWBACK_DISCRIMINATOR = toDiscriminator(111, 92, 142, 79, 33, 234, 82, 27);

  public static Instruction clawback(final AccountMeta invokedMerkleDistributorProgramMeta,
                                     // The [MerkleDistributor].
                                     final PublicKey distributorKey,
                                     // Distributor ATA containing the tokens to distribute.
                                     final PublicKey fromKey,
                                     // The Clawback token account.
                                     final PublicKey clawbackReceiverKey,
                                     // SPL [Token] program.
                                     final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWrite(distributorKey),
      createWrite(fromKey),
      createWrite(clawbackReceiverKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedMerkleDistributorProgramMeta, keys, CLAWBACK_DISCRIMINATOR);
  }

  public static final Discriminator SET_CLAWBACK_RECEIVER_DISCRIMINATOR = toDiscriminator(153, 217, 34, 20, 19, 29, 229, 75);

  public static Instruction setClawbackReceiver(final AccountMeta invokedMerkleDistributorProgramMeta,
                                                // The [MerkleDistributor].
                                                final PublicKey distributorKey,
                                                // New clawback account
                                                final PublicKey newClawbackAccountKey,
                                                // Admin signer
                                                final PublicKey adminKey) {
    final var keys = List.of(
      createWrite(distributorKey),
      createRead(newClawbackAccountKey),
      createReadOnlySigner(adminKey)
    );

    return Instruction.createInstruction(invokedMerkleDistributorProgramMeta, keys, SET_CLAWBACK_RECEIVER_DISCRIMINATOR);
  }

  public static final Discriminator SET_ADMIN_DISCRIMINATOR = toDiscriminator(251, 163, 0, 52, 91, 194, 187, 92);

  public static Instruction setAdmin(final AccountMeta invokedMerkleDistributorProgramMeta,
                                     // The [MerkleDistributor].
                                     final PublicKey distributorKey,
                                     // Admin signer
                                     final PublicKey adminKey,
                                     // New admin account
                                     final PublicKey newAdminKey) {
    final var keys = List.of(
      createWrite(distributorKey),
      createReadOnlySigner(adminKey),
      createRead(newAdminKey)
    );

    return Instruction.createInstruction(invokedMerkleDistributorProgramMeta, keys, SET_ADMIN_DISCRIMINATOR);
  }

  public static final Discriminator SET_OPERATOR_DISCRIMINATOR = toDiscriminator(238, 153, 101, 169, 243, 131, 36, 1);

  public static Instruction setOperator(final AccountMeta invokedMerkleDistributorProgramMeta,
                                        // The [MerkleDistributor].
                                        final PublicKey distributorKey,
                                        // Admin signer
                                        final PublicKey adminKey,
                                        final PublicKey newOperator) {
    final var keys = List.of(
      createWrite(distributorKey),
      createReadOnlySigner(adminKey)
    );

    final byte[] _data = new byte[40];
    int i = SET_OPERATOR_DISCRIMINATOR.write(_data, 0);
    newOperator.write(_data, i);

    return Instruction.createInstruction(invokedMerkleDistributorProgramMeta, keys, _data);
  }

  public record SetOperatorIxData(Discriminator discriminator, PublicKey newOperator) implements Borsh {  

    public static SetOperatorIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static SetOperatorIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var newOperator = readPubKey(_data, i);
      return new SetOperatorIxData(discriminator, newOperator);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      newOperator.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator NEW_CLAIM_DISCRIMINATOR = toDiscriminator(78, 177, 98, 123, 210, 21, 187, 83);

  // USER FUNCTIONS /////
  public static Instruction newClaim(final AccountMeta invokedMerkleDistributorProgramMeta,
                                     // The [MerkleDistributor].
                                     final PublicKey distributorKey,
                                     // Claim status PDA
                                     final PublicKey claimStatusKey,
                                     // Distributor ATA containing the tokens to distribute.
                                     final PublicKey fromKey,
                                     // Account to send the claimed tokens to.
                                     final PublicKey toKey,
                                     // Who is claiming the tokens.
                                     final PublicKey claimantKey,
                                     // operator
                                     final PublicKey operatorKey,
                                     // SPL [Token] program.
                                     final PublicKey tokenProgramKey,
                                     // The [System] program.
                                     final PublicKey systemProgramKey,
                                     final long amountUnlocked,
                                     final long amountLocked,
                                     final byte[][] proof) {
    final var keys = List.of(
      createWrite(distributorKey),
      createWrite(claimStatusKey),
      createWrite(fromKey),
      createWrite(toKey),
      createWritableSigner(claimantKey),
      createReadOnlySigner(requireNonNullElse(operatorKey, invokedMerkleDistributorProgramMeta.publicKey())),
      createRead(tokenProgramKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[24 + Borsh.lenVectorArray(proof)];
    int i = NEW_CLAIM_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amountUnlocked);
    i += 8;
    putInt64LE(_data, i, amountLocked);
    i += 8;
    Borsh.writeVectorArrayChecked(proof, 32, _data, i);

    return Instruction.createInstruction(invokedMerkleDistributorProgramMeta, keys, _data);
  }

  public record NewClaimIxData(Discriminator discriminator,
                               long amountUnlocked,
                               long amountLocked,
                               byte[][] proof) implements Borsh {  

    public static NewClaimIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static NewClaimIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amountUnlocked = getInt64LE(_data, i);
      i += 8;
      final var amountLocked = getInt64LE(_data, i);
      i += 8;
      final var proof = Borsh.readMultiDimensionbyteVectorArray(32, _data, i);
      return new NewClaimIxData(discriminator, amountUnlocked, amountLocked, proof);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amountUnlocked);
      i += 8;
      putInt64LE(_data, i, amountLocked);
      i += 8;
      i += Borsh.writeVectorArrayChecked(proof, 32, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + 8 + Borsh.lenVectorArray(proof);
    }
  }

  public static final Discriminator CLAIM_LOCKED_DISCRIMINATOR = toDiscriminator(34, 206, 181, 23, 11, 207, 147, 90);

  public static Instruction claimLocked(final AccountMeta invokedMerkleDistributorProgramMeta,
                                        // The [MerkleDistributor].
                                        final PublicKey distributorKey,
                                        // Claim Status PDA
                                        final PublicKey claimStatusKey,
                                        // Distributor ATA containing the tokens to distribute.
                                        final PublicKey fromKey,
                                        // Account to send the claimed tokens to.
                                        final PublicKey toKey,
                                        // Who is claiming the tokens.
                                        final PublicKey claimantKey,
                                        // operator
                                        final PublicKey operatorKey,
                                        // SPL [Token] program.
                                        final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWrite(distributorKey),
      createWrite(claimStatusKey),
      createWrite(fromKey),
      createWrite(toKey),
      createReadOnlySigner(claimantKey),
      createReadOnlySigner(requireNonNullElse(operatorKey, invokedMerkleDistributorProgramMeta.publicKey())),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedMerkleDistributorProgramMeta, keys, CLAIM_LOCKED_DISCRIMINATOR);
  }

  public static final Discriminator NEW_CLAIM_AND_STAKE_DISCRIMINATOR = toDiscriminator(50, 111, 242, 118, 51, 250, 141, 187);

  public static Instruction newClaimAndStake(final AccountMeta invokedMerkleDistributorProgramMeta,
                                             // The [MerkleDistributor].
                                             final PublicKey distributorKey,
                                             // Claim status PDA
                                             final PublicKey claimStatusKey,
                                             // Distributor ATA containing the tokens to distribute.
                                             final PublicKey fromKey,
                                             // Who is claiming the tokens.
                                             final PublicKey claimantKey,
                                             // operator
                                             final PublicKey operatorKey,
                                             // SPL [Token] program.
                                             final PublicKey tokenProgramKey,
                                             // The [System] program.
                                             final PublicKey systemProgramKey,
                                             // Voter program
                                             final PublicKey voterProgramKey,
                                             final PublicKey lockerKey,
                                             final PublicKey escrowKey,
                                             final PublicKey escrowTokensKey,
                                             final long amountUnlocked,
                                             final long amountLocked,
                                             final byte[][] proof) {
    final var keys = List.of(
      createWrite(distributorKey),
      createWrite(claimStatusKey),
      createWrite(fromKey),
      createWritableSigner(claimantKey),
      createReadOnlySigner(requireNonNullElse(operatorKey, invokedMerkleDistributorProgramMeta.publicKey())),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(voterProgramKey),
      createWrite(lockerKey),
      createWrite(escrowKey),
      createWrite(escrowTokensKey)
    );

    final byte[] _data = new byte[24 + Borsh.lenVectorArray(proof)];
    int i = NEW_CLAIM_AND_STAKE_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amountUnlocked);
    i += 8;
    putInt64LE(_data, i, amountLocked);
    i += 8;
    Borsh.writeVectorArrayChecked(proof, 32, _data, i);

    return Instruction.createInstruction(invokedMerkleDistributorProgramMeta, keys, _data);
  }

  public record NewClaimAndStakeIxData(Discriminator discriminator,
                                       long amountUnlocked,
                                       long amountLocked,
                                       byte[][] proof) implements Borsh {  

    public static NewClaimAndStakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static NewClaimAndStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amountUnlocked = getInt64LE(_data, i);
      i += 8;
      final var amountLocked = getInt64LE(_data, i);
      i += 8;
      final var proof = Borsh.readMultiDimensionbyteVectorArray(32, _data, i);
      return new NewClaimAndStakeIxData(discriminator, amountUnlocked, amountLocked, proof);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amountUnlocked);
      i += 8;
      putInt64LE(_data, i, amountLocked);
      i += 8;
      i += Borsh.writeVectorArrayChecked(proof, 32, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + 8 + Borsh.lenVectorArray(proof);
    }
  }

  public static final Discriminator CLAIM_LOCKED_AND_STAKE_DISCRIMINATOR = toDiscriminator(173, 208, 81, 8, 13, 19, 202, 150);

  public static Instruction claimLockedAndStake(final AccountMeta invokedMerkleDistributorProgramMeta,
                                                // The [MerkleDistributor].
                                                final PublicKey distributorKey,
                                                // Claim Status PDA
                                                final PublicKey claimStatusKey,
                                                // Distributor ATA containing the tokens to distribute.
                                                final PublicKey fromKey,
                                                // Who is claiming the tokens.
                                                final PublicKey claimantKey,
                                                // operator
                                                final PublicKey operatorKey,
                                                // SPL [Token] program.
                                                final PublicKey tokenProgramKey,
                                                // Voter program
                                                final PublicKey voterProgramKey,
                                                final PublicKey lockerKey,
                                                final PublicKey escrowKey,
                                                final PublicKey escrowTokensKey) {
    final var keys = List.of(
      createWrite(distributorKey),
      createWrite(claimStatusKey),
      createWrite(fromKey),
      createReadOnlySigner(claimantKey),
      createReadOnlySigner(requireNonNullElse(operatorKey, invokedMerkleDistributorProgramMeta.publicKey())),
      createRead(tokenProgramKey),
      createRead(voterProgramKey),
      createWrite(lockerKey),
      createWrite(escrowKey),
      createWrite(escrowTokensKey)
    );

    return Instruction.createInstruction(invokedMerkleDistributorProgramMeta, keys, CLAIM_LOCKED_AND_STAKE_DISCRIMINATOR);
  }

  private MerkleDistributorProgram() {
  }
}
