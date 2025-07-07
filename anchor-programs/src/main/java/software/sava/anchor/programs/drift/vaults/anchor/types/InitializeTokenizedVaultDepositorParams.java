package software.sava.anchor.programs.drift.vaults.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.encoding.ByteUtil.getInt32LE;

public record InitializeTokenizedVaultDepositorParams(String tokenName, byte[] _tokenName,
                                                      String tokenSymbol, byte[] _tokenSymbol,
                                                      String tokenUri, byte[] _tokenUri,
                                                      int decimals) implements Borsh {

  public static InitializeTokenizedVaultDepositorParams createRecord(final String tokenName,
                                                                     final String tokenSymbol,
                                                                     final String tokenUri,
                                                                     final int decimals) {
    return new InitializeTokenizedVaultDepositorParams(tokenName, tokenName.getBytes(UTF_8),
                                                       tokenSymbol, tokenSymbol.getBytes(UTF_8),
                                                       tokenUri, tokenUri.getBytes(UTF_8),
                                                       decimals);
  }

  public static InitializeTokenizedVaultDepositorParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var tokenName = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var tokenSymbol = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var tokenUri = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var decimals = _data[i] & 0xFF;
    return new InitializeTokenizedVaultDepositorParams(tokenName, tokenName.getBytes(UTF_8),
                                                       tokenSymbol, tokenSymbol.getBytes(UTF_8),
                                                       tokenUri, tokenUri.getBytes(UTF_8),
                                                       decimals);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_tokenName, _data, i);
    i += Borsh.writeVector(_tokenSymbol, _data, i);
    i += Borsh.writeVector(_tokenUri, _data, i);
    _data[i] = (byte) decimals;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_tokenName) + Borsh.lenVector(_tokenSymbol) + Borsh.lenVector(_tokenUri) + 1;
  }
}
