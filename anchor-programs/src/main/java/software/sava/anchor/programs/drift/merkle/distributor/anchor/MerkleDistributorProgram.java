package software.sava.anchor.programs.drift.merkle.distributor.anchor;

import java.util.List;

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
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class MerkleDistributorProgram {

  public static final Discriminator NEW_DISTRIBUTOR_DISCRIMINATOR = toDiscriminator(32, 139, 112, 171, 0, 2, 225, 155);

  public static Instruction newDistributor(final AccountMeta invokedMerkleDistributorProgramMeta,
                                           // [MerkleDistributor].
                                           final PublicKey distributorKey,
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
                                           // The [Associated Token] program.
                                           final PublicKey associatedTokenProgramKey,
                                           // The [Token] program.
                                           final PublicKey tokenProgramKey,
                                           final long version,
                                           final byte[] root,
                                           final long maxTotalClaim,
                                           final long maxNumNodes,
                                           final long startVestingTs,
                                           final long endVestingTs,
                                           final long clawbackStartTs,
                                           final long enableSlot,
                                           final boolean closable) {
    final var keys = List.of(
      createWrite(distributorKey),
      createWrite(clawbackReceiverKey),
      createRead(mintKey),
      createRead(tokenVaultKey),
      createWritableSigner(adminKey),
      createRead(systemProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[65 + Borsh.lenArray(root)];
    int i = writeDiscriminator(NEW_DISTRIBUTOR_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, version);
    i += 8;
    i += Borsh.writeArray(root, _data, i);
    putInt64LE(_data, i, maxTotalClaim);
    i += 8;
    putInt64LE(_data, i, maxNumNodes);
    i += 8;
    putInt64LE(_data, i, startVestingTs);
    i += 8;
    putInt64LE(_data, i, endVestingTs);
    i += 8;
    putInt64LE(_data, i, clawbackStartTs);
    i += 8;
    putInt64LE(_data, i, enableSlot);
    i += 8;
    _data[i] = (byte) (closable ? 1 : 0);

    return Instruction.createInstruction(invokedMerkleDistributorProgramMeta, keys, _data);
  }

  public record NewDistributorIxData(Discriminator discriminator,
                                     long version,
                                     byte[] root,
                                     long maxTotalClaim,
                                     long maxNumNodes,
                                     long startVestingTs,
                                     long endVestingTs,
                                     long clawbackStartTs,
                                     long enableSlot,
                                     boolean closable) implements Borsh {  

    public static NewDistributorIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 97;

    public static NewDistributorIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var version = getInt64LE(_data, i);
      i += 8;
      final var root = new byte[32];
      i += Borsh.readArray(root, _data, i);
      final var maxTotalClaim = getInt64LE(_data, i);
      i += 8;
      final var maxNumNodes = getInt64LE(_data, i);
      i += 8;
      final var startVestingTs = getInt64LE(_data, i);
      i += 8;
      final var endVestingTs = getInt64LE(_data, i);
      i += 8;
      final var clawbackStartTs = getInt64LE(_data, i);
      i += 8;
      final var enableSlot = getInt64LE(_data, i);
      i += 8;
      final var closable = _data[i] == 1;
      return new NewDistributorIxData(discriminator,
                                      version,
                                      root,
                                      maxTotalClaim,
                                      maxNumNodes,
                                      startVestingTs,
                                      endVestingTs,
                                      clawbackStartTs,
                                      enableSlot,
                                      closable);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, version);
      i += 8;
      i += Borsh.writeArray(root, _data, i);
      putInt64LE(_data, i, maxTotalClaim);
      i += 8;
      putInt64LE(_data, i, maxNumNodes);
      i += 8;
      putInt64LE(_data, i, startVestingTs);
      i += 8;
      putInt64LE(_data, i, endVestingTs);
      i += 8;
      putInt64LE(_data, i, clawbackStartTs);
      i += 8;
      putInt64LE(_data, i, enableSlot);
      i += 8;
      _data[i] = (byte) (closable ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CLOSE_DISTRIBUTOR_DISCRIMINATOR = toDiscriminator(202, 56, 180, 143, 46, 104, 106, 112);

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

  public static Instruction closeClaimStatus(final AccountMeta invokedMerkleDistributorProgramMeta,
                                             final PublicKey claimStatusKey,
                                             final PublicKey claimantKey,
                                             final PublicKey adminKey,
                                             final PublicKey distributorKey) {
    final var keys = List.of(
      createWrite(claimStatusKey),
      createWrite(claimantKey),
      createReadOnlySigner(adminKey),
      createRead(distributorKey)
    );

    return Instruction.createInstruction(invokedMerkleDistributorProgramMeta, keys, CLOSE_CLAIM_STATUS_DISCRIMINATOR);
  }

  public static final Discriminator SET_ENABLE_SLOT_DISCRIMINATOR = toDiscriminator(5, 52, 73, 33, 150, 115, 97, 206);

  public static Instruction setEnableSlot(final AccountMeta invokedMerkleDistributorProgramMeta,
                                          // [MerkleDistributor].
                                          final PublicKey distributorKey,
                                          // Payer to create the distributor.
                                          final PublicKey adminKey,
                                          final long enableSlot) {
    final var keys = List.of(
      createWrite(distributorKey),
      createWritableSigner(adminKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(SET_ENABLE_SLOT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, enableSlot);

    return Instruction.createInstruction(invokedMerkleDistributorProgramMeta, keys, _data);
  }

  public record SetEnableSlotIxData(Discriminator discriminator, long enableSlot) implements Borsh {  

    public static SetEnableSlotIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SetEnableSlotIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var enableSlot = getInt64LE(_data, i);
      return new SetEnableSlotIxData(discriminator, enableSlot);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, enableSlot);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator NEW_CLAIM_DISCRIMINATOR = toDiscriminator(78, 177, 98, 123, 210, 21, 187, 83);

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
      createRead(tokenProgramKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[24 + Borsh.lenVectorArray(proof)];
    int i = writeDiscriminator(NEW_CLAIM_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountUnlocked);
    i += 8;
    putInt64LE(_data, i, amountLocked);
    i += 8;
    Borsh.writeVectorArray(proof, _data, i);

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
      final var discriminator = parseDiscriminator(_data, offset);
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
      i += Borsh.writeVectorArray(proof, _data, i);
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
                                        // Claimant must sign the transaction and can only claim on behalf of themself
                                        final PublicKey toKey,
                                        // Who is claiming the tokens.
                                        final PublicKey claimantKey,
                                        // SPL [Token] program.
                                        final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWrite(distributorKey),
      createWrite(claimStatusKey),
      createWrite(fromKey),
      createWrite(toKey),
      createWritableSigner(claimantKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedMerkleDistributorProgramMeta, keys, CLAIM_LOCKED_DISCRIMINATOR);
  }

  public static final Discriminator CLAWBACK_DISCRIMINATOR = toDiscriminator(111, 92, 142, 79, 33, 234, 82, 27);

  public static Instruction clawback(final AccountMeta invokedMerkleDistributorProgramMeta,
                                     // The [MerkleDistributor].
                                     final PublicKey distributorKey,
                                     // Distributor ATA containing the tokens to distribute.
                                     final PublicKey fromKey,
                                     // The Clawback token account.
                                     final PublicKey toKey,
                                     // Claimant account
                                     // Anyone can claw back the funds
                                     final PublicKey claimantKey,
                                     // The [System] program.
                                     final PublicKey systemProgramKey,
                                     // SPL [Token] program.
                                     final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWrite(distributorKey),
      createWrite(fromKey),
      createWrite(toKey),
      createReadOnlySigner(claimantKey),
      createRead(systemProgramKey),
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
      createWritableSigner(adminKey)
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
      createWritableSigner(adminKey),
      createWrite(newAdminKey)
    );

    return Instruction.createInstruction(invokedMerkleDistributorProgramMeta, keys, SET_ADMIN_DISCRIMINATOR);
  }

  private MerkleDistributorProgram() {
  }
}
