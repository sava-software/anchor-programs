package software.sava.anchor.programs.metadao.conditional_vault.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record MergeTokensEvent(CommonFields common,
                               PublicKey user,
                               PublicKey vault,
                               long amount,
                               long postUserUnderlyingBalance,
                               long postVaultUnderlyingBalance,
                               long[] postUserConditionalTokenBalances,
                               long[] postConditionalTokenSupplies,
                               long seqNum) implements Borsh {

  public static MergeTokensEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var common = CommonFields.read(_data, i);
    i += Borsh.len(common);
    final var user = readPubKey(_data, i);
    i += 32;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var postUserUnderlyingBalance = getInt64LE(_data, i);
    i += 8;
    final var postVaultUnderlyingBalance = getInt64LE(_data, i);
    i += 8;
    final var postUserConditionalTokenBalances = Borsh.readlongVector(_data, i);
    i += Borsh.lenVector(postUserConditionalTokenBalances);
    final var postConditionalTokenSupplies = Borsh.readlongVector(_data, i);
    i += Borsh.lenVector(postConditionalTokenSupplies);
    final var seqNum = getInt64LE(_data, i);
    return new MergeTokensEvent(common,
                                user,
                                vault,
                                amount,
                                postUserUnderlyingBalance,
                                postVaultUnderlyingBalance,
                                postUserConditionalTokenBalances,
                                postConditionalTokenSupplies,
                                seqNum);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(common, _data, i);
    user.write(_data, i);
    i += 32;
    vault.write(_data, i);
    i += 32;
    putInt64LE(_data, i, amount);
    i += 8;
    putInt64LE(_data, i, postUserUnderlyingBalance);
    i += 8;
    putInt64LE(_data, i, postVaultUnderlyingBalance);
    i += 8;
    i += Borsh.writeVector(postUserConditionalTokenBalances, _data, i);
    i += Borsh.writeVector(postConditionalTokenSupplies, _data, i);
    putInt64LE(_data, i, seqNum);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(common)
         + 32
         + 32
         + 8
         + 8
         + 8
         + Borsh.lenVector(postUserConditionalTokenBalances)
         + Borsh.lenVector(postConditionalTokenSupplies)
         + 8;
  }
}
