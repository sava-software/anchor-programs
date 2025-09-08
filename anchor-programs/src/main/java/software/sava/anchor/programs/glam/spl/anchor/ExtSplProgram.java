package software.sava.anchor.programs.glam.spl.anchor;

import java.util.List;

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

public final class ExtSplProgram {

  public static final Discriminator TOKEN_CLOSE_ACCOUNT_DISCRIMINATOR = toDiscriminator(240, 32, 179, 154, 96, 110, 43, 79);

  public static Instruction tokenCloseAccount(final AccountMeta invokedExtSplProgramMeta,
                                              final SolanaAccounts solanaAccounts,
                                              final PublicKey glamStateKey,
                                              final PublicKey glamVaultKey,
                                              final PublicKey glamSignerKey,
                                              final PublicKey integrationAuthorityKey,
                                              final PublicKey cpiProgramKey,
                                              final PublicKey glamProtocolProgramKey,
                                              final PublicKey tokenAccountKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(integrationAuthorityKey),
      createRead(cpiProgramKey),
      createRead(glamProtocolProgramKey),
      createRead(solanaAccounts.systemProgram()),
      createWrite(tokenAccountKey)
    );

    return Instruction.createInstruction(invokedExtSplProgramMeta, keys, TOKEN_CLOSE_ACCOUNT_DISCRIMINATOR);
  }

  public static final Discriminator TOKEN_TRANSFER_CHECKED_DISCRIMINATOR = toDiscriminator(169, 178, 117, 156, 169, 191, 199, 116);

  public static Instruction tokenTransferChecked(final AccountMeta invokedExtSplProgramMeta,
                                                 final PublicKey glamStateKey,
                                                 final PublicKey glamVaultKey,
                                                 final PublicKey glamSignerKey,
                                                 final PublicKey integrationAuthorityKey,
                                                 final PublicKey cpiProgramKey,
                                                 final PublicKey glamProtocolProgramKey,
                                                 final PublicKey fromKey,
                                                 final PublicKey mintKey,
                                                 final PublicKey toKey,
                                                 final long amount,
                                                 final int decimals) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(integrationAuthorityKey),
      createRead(cpiProgramKey),
      createRead(glamProtocolProgramKey),
      createWrite(fromKey),
      createRead(mintKey),
      createWrite(toKey)
    );

    final byte[] _data = new byte[17];
    int i = TOKEN_TRANSFER_CHECKED_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) decimals;

    return Instruction.createInstruction(invokedExtSplProgramMeta, keys, _data);
  }

  public record TokenTransferCheckedIxData(Discriminator discriminator, long amount, int decimals) implements Borsh {  

    public static TokenTransferCheckedIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static TokenTransferCheckedIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var decimals = _data[i] & 0xFF;
      return new TokenTransferCheckedIxData(discriminator, amount, decimals);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      _data[i] = (byte) decimals;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  private ExtSplProgram() {
  }
}
