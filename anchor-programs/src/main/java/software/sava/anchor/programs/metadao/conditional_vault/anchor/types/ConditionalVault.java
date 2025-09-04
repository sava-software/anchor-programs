package software.sava.anchor.programs.metadao.conditional_vault.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record ConditionalVault(PublicKey _address,
                               Discriminator discriminator,
                               PublicKey question,
                               PublicKey underlyingTokenMint,
                               PublicKey underlyingTokenAccount,
                               PublicKey[] conditionalTokenMints,
                               int pdaBump,
                               int decimals,
                               long seqNum) implements Borsh {

  public static final int QUESTION_OFFSET = 8;
  public static final int UNDERLYING_TOKEN_MINT_OFFSET = 40;
  public static final int UNDERLYING_TOKEN_ACCOUNT_OFFSET = 72;
  public static final int CONDITIONAL_TOKEN_MINTS_OFFSET = 104;

  public static Filter createQuestionFilter(final PublicKey question) {
    return Filter.createMemCompFilter(QUESTION_OFFSET, question);
  }

  public static Filter createUnderlyingTokenMintFilter(final PublicKey underlyingTokenMint) {
    return Filter.createMemCompFilter(UNDERLYING_TOKEN_MINT_OFFSET, underlyingTokenMint);
  }

  public static Filter createUnderlyingTokenAccountFilter(final PublicKey underlyingTokenAccount) {
    return Filter.createMemCompFilter(UNDERLYING_TOKEN_ACCOUNT_OFFSET, underlyingTokenAccount);
  }

  public static ConditionalVault read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static ConditionalVault read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static ConditionalVault read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], ConditionalVault> FACTORY = ConditionalVault::read;

  public static ConditionalVault read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var question = readPubKey(_data, i);
    i += 32;
    final var underlyingTokenMint = readPubKey(_data, i);
    i += 32;
    final var underlyingTokenAccount = readPubKey(_data, i);
    i += 32;
    final var conditionalTokenMints = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(conditionalTokenMints);
    final var pdaBump = _data[i] & 0xFF;
    ++i;
    final var decimals = _data[i] & 0xFF;
    ++i;
    final var seqNum = getInt64LE(_data, i);
    return new ConditionalVault(_address,
                                discriminator,
                                question,
                                underlyingTokenMint,
                                underlyingTokenAccount,
                                conditionalTokenMints,
                                pdaBump,
                                decimals,
                                seqNum);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    question.write(_data, i);
    i += 32;
    underlyingTokenMint.write(_data, i);
    i += 32;
    underlyingTokenAccount.write(_data, i);
    i += 32;
    i += Borsh.writeVector(conditionalTokenMints, _data, i);
    _data[i] = (byte) pdaBump;
    ++i;
    _data[i] = (byte) decimals;
    ++i;
    putInt64LE(_data, i, seqNum);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32
         + 32
         + 32
         + Borsh.lenVector(conditionalTokenMints)
         + 1
         + 1
         + 8;
  }
}
