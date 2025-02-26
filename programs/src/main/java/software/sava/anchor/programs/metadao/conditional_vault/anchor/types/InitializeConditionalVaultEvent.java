package software.sava.anchor.programs.metadao.conditional_vault.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InitializeConditionalVaultEvent(CommonFields common,
                                              PublicKey vault,
                                              PublicKey question,
                                              PublicKey underlyingTokenMint,
                                              PublicKey vaultUnderlyingTokenAccount,
                                              PublicKey[] conditionalTokenMints,
                                              int pdaBump,
                                              long seqNum) implements Borsh {

  public static InitializeConditionalVaultEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var common = CommonFields.read(_data, i);
    i += Borsh.len(common);
    final var vault = readPubKey(_data, i);
    i += 32;
    final var question = readPubKey(_data, i);
    i += 32;
    final var underlyingTokenMint = readPubKey(_data, i);
    i += 32;
    final var vaultUnderlyingTokenAccount = readPubKey(_data, i);
    i += 32;
    final var conditionalTokenMints = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(conditionalTokenMints);
    final var pdaBump = _data[i] & 0xFF;
    ++i;
    final var seqNum = getInt64LE(_data, i);
    return new InitializeConditionalVaultEvent(common,
                                               vault,
                                               question,
                                               underlyingTokenMint,
                                               vaultUnderlyingTokenAccount,
                                               conditionalTokenMints,
                                               pdaBump,
                                               seqNum);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(common, _data, i);
    vault.write(_data, i);
    i += 32;
    question.write(_data, i);
    i += 32;
    underlyingTokenMint.write(_data, i);
    i += 32;
    vaultUnderlyingTokenAccount.write(_data, i);
    i += 32;
    i += Borsh.writeVector(conditionalTokenMints, _data, i);
    _data[i] = (byte) pdaBump;
    ++i;
    putInt64LE(_data, i, seqNum);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(common)
         + 32
         + 32
         + 32
         + 32
         + Borsh.lenVector(conditionalTokenMints)
         + 1
         + 8;
  }
}
