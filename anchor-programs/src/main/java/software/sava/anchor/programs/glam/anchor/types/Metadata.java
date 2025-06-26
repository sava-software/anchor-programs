package software.sava.anchor.programs.glam.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record Metadata(MetadataTemplate template,
                       PublicKey pubkey,
                       String uri, byte[] _uri) implements Borsh {

  public static Metadata createRecord(final MetadataTemplate template,
                                      final PublicKey pubkey,
                                      final String uri) {
    return new Metadata(template, pubkey, uri, uri.getBytes(UTF_8));
  }

  public static Metadata read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var template = MetadataTemplate.read(_data, i);
    i += Borsh.len(template);
    final var pubkey = readPubKey(_data, i);
    i += 32;
    final var uri = Borsh.string(_data, i);
    return new Metadata(template, pubkey, uri, uri.getBytes(UTF_8));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(template, _data, i);
    pubkey.write(_data, i);
    i += 32;
    i += Borsh.writeVector(_uri, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(template) + 32 + Borsh.lenVector(_uri);
  }
}
