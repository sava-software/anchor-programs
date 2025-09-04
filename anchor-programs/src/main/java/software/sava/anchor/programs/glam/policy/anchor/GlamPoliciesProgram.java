package software.sava.anchor.programs.glam.policy.anchor;

import java.util.List;

import software.sava.anchor.programs.glam.policy.anchor.types.AnchorExtraAccountMeta;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class GlamPoliciesProgram {

  public static final Discriminator CLOSE_EXTRA_METAS_ACCOUNT_DISCRIMINATOR = toDiscriminator(67, 72, 24, 239, 222, 207, 240, 177);

  public static Instruction closeExtraMetasAccount(final AccountMeta invokedGlamPoliciesProgramMeta,
                                                   final SolanaAccounts solanaAccounts,
                                                   final PublicKey extraMetasAccountKey,
                                                   final PublicKey mintKey,
                                                   final PublicKey authorityKey,
                                                   final PublicKey destinationKey) {
    final var keys = List.of(
      createWrite(extraMetasAccountKey),
      createRead(mintKey),
      createWritableSigner(authorityKey),
      createWrite(destinationKey),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedGlamPoliciesProgramMeta, keys, CLOSE_EXTRA_METAS_ACCOUNT_DISCRIMINATOR);
  }

  public static final Discriminator CLOSE_POLICY_DISCRIMINATOR = toDiscriminator(55, 42, 248, 229, 222, 138, 26, 252);

  public static Instruction closePolicy(final AccountMeta invokedGlamPoliciesProgramMeta,
                                        final SolanaAccounts solanaAccounts,
                                        // lamports will be refunded to the owner
                                        final PublicKey policyAccountKey,
                                        final PublicKey signerKey,
                                        final PublicKey subjectKey) {
    final var keys = List.of(
      createWrite(policyAccountKey),
      createWritableSigner(signerKey),
      createWrite(subjectKey),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedGlamPoliciesProgramMeta, keys, CLOSE_POLICY_DISCRIMINATOR);
  }

  public static final Discriminator CREATE_POLICY_DISCRIMINATOR = toDiscriminator(27, 81, 33, 27, 196, 103, 246, 53);

  public static Instruction createPolicy(final AccountMeta invokedGlamPoliciesProgramMeta,
                                         final SolanaAccounts solanaAccounts,
                                         final PublicKey policyAccountKey,
                                         // Must be the mint authority or permanent delegate
                                         final PublicKey authorityKey,
                                         final PublicKey subjectKey,
                                         final PublicKey payerKey,
                                         final PublicKey mintKey,
                                         final PublicKey subjectTokenAccountKey,
                                         final long lockedUntilTs) {
    final var keys = List.of(
      createWrite(policyAccountKey),
      createWritableSigner(authorityKey),
      createRead(subjectKey),
      createWritableSigner(payerKey),
      createRead(mintKey),
      createRead(subjectTokenAccountKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[16];
    int i = CREATE_POLICY_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, lockedUntilTs);

    return Instruction.createInstruction(invokedGlamPoliciesProgramMeta, keys, _data);
  }

  public record CreatePolicyIxData(Discriminator discriminator, long lockedUntilTs) implements Borsh {  

    public static CreatePolicyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static CreatePolicyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lockedUntilTs = getInt64LE(_data, i);
      return new CreatePolicyIxData(discriminator, lockedUntilTs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lockedUntilTs);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator EXECUTE_DISCRIMINATOR = toDiscriminator(105, 37, 101, 197, 75, 251, 102, 26);

  public static Instruction execute(final AccountMeta invokedGlamPoliciesProgramMeta,
                                    final PublicKey srcAccountKey,
                                    final PublicKey mintKey,
                                    final PublicKey dstAccountKey,
                                    final PublicKey srcAccountAuthorityKey,
                                    final PublicKey extraMetasAccountKey,
                                    final PublicKey srcPolicyAccountKey,
                                    final PublicKey dstPolicyAccountKey,
                                    final long amount) {
    final var keys = List.of(
      createRead(srcAccountKey),
      createRead(mintKey),
      createRead(dstAccountKey),
      createRead(srcAccountAuthorityKey),
      createRead(extraMetasAccountKey),
      createRead(srcPolicyAccountKey),
      createRead(dstPolicyAccountKey)
    );

    final byte[] _data = new byte[16];
    int i = EXECUTE_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedGlamPoliciesProgramMeta, keys, _data);
  }

  public record ExecuteIxData(Discriminator discriminator, long amount) implements Borsh {  

    public static ExecuteIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static ExecuteIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      return new ExecuteIxData(discriminator, amount);
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

  public static final Discriminator INITIALIZE_EXTRA_METAS_ACCOUNT_DISCRIMINATOR = toDiscriminator(43, 34, 13, 49, 167, 88, 235, 235);

  public static Instruction initializeExtraMetasAccount(final AccountMeta invokedGlamPoliciesProgramMeta,
                                                        final SolanaAccounts solanaAccounts,
                                                        final PublicKey extraMetasAccountKey,
                                                        final PublicKey mintKey,
                                                        final PublicKey authorityKey,
                                                        final PublicKey payerKey,
                                                        final AnchorExtraAccountMeta[] metas) {
    final var keys = List.of(
      createWrite(extraMetasAccountKey),
      createRead(mintKey),
      createWritableSigner(authorityKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[8 + Borsh.lenVector(metas)];
    int i = INITIALIZE_EXTRA_METAS_ACCOUNT_DISCRIMINATOR.write(_data, 0);
    Borsh.writeVector(metas, _data, i);

    return Instruction.createInstruction(invokedGlamPoliciesProgramMeta, keys, _data);
  }

  public record InitializeExtraMetasAccountIxData(Discriminator discriminator, AnchorExtraAccountMeta[] metas) implements Borsh {  

    public static InitializeExtraMetasAccountIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static InitializeExtraMetasAccountIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var metas = Borsh.readVector(AnchorExtraAccountMeta.class, AnchorExtraAccountMeta::read, _data, i);
      return new InitializeExtraMetasAccountIxData(discriminator, metas);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(metas, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(metas);
    }
  }

  private GlamPoliciesProgram() {
  }
}
