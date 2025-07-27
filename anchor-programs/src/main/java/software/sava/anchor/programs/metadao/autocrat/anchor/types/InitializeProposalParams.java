package software.sava.anchor.programs.metadao.autocrat.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InitializeProposalParams(String descriptionUrl, byte[] _descriptionUrl,
                                       long passLpTokensToLock,
                                       long failLpTokensToLock) implements Borsh {

  public static InitializeProposalParams createRecord(final String descriptionUrl,
                                                      final long passLpTokensToLock,
                                                      final long failLpTokensToLock) {
    return new InitializeProposalParams(descriptionUrl, descriptionUrl.getBytes(UTF_8), passLpTokensToLock, failLpTokensToLock);
  }

  public static InitializeProposalParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var descriptionUrl = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var passLpTokensToLock = getInt64LE(_data, i);
    i += 8;
    final var failLpTokensToLock = getInt64LE(_data, i);
    return new InitializeProposalParams(descriptionUrl, descriptionUrl.getBytes(UTF_8), passLpTokensToLock, failLpTokensToLock);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_descriptionUrl, _data, i);
    putInt64LE(_data, i, passLpTokensToLock);
    i += 8;
    putInt64LE(_data, i, failLpTokensToLock);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_descriptionUrl) + 8 + 8;
  }
}
