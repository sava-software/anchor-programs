package software.sava.anchor.programs.metadao.conditional_vault.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.encoding.ByteUtil.getInt32LE;

public record AddMetadataToConditionalTokensArgs(String name, byte[] _name,
                                                 String symbol, byte[] _symbol,
                                                 String uri, byte[] _uri) implements Borsh {

  public static AddMetadataToConditionalTokensArgs createRecord(final String name,
                                                                final String symbol,
                                                                final String uri) {
    return new AddMetadataToConditionalTokensArgs(name, name.getBytes(UTF_8), symbol, symbol.getBytes(UTF_8), uri, uri.getBytes(UTF_8));
  }

  public static AddMetadataToConditionalTokensArgs read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var name = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var symbol = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var uri = Borsh.string(_data, i);
    return new AddMetadataToConditionalTokensArgs(name, name.getBytes(UTF_8), symbol, symbol.getBytes(UTF_8), uri, uri.getBytes(UTF_8));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_name, _data, i);
    i += Borsh.writeVector(_symbol, _data, i);
    i += Borsh.writeVector(_uri, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_name) + Borsh.lenVector(_symbol) + Borsh.lenVector(_uri);
  }
}
