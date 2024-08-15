package software.sava.anchor.programs.glam.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

public record ShareClassField(ShareClassFieldName name, String value, byte[] _value) implements Borsh {

  public static ShareClassField createRecord(final ShareClassFieldName name, final String value) {
    return new ShareClassField(name, value, value.getBytes(UTF_8));
  }

  public static ShareClassField read(final byte[] _data, final int offset) {
    int i = offset;
    final var name = ShareClassFieldName.read(_data, i);
    i += Borsh.len(name);
    final var value = Borsh.string(_data, i);
    return new ShareClassField(name, value, value.getBytes(UTF_8));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(name, _data, i);
    i += Borsh.write(_value, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(name) + Borsh.len(_value);
  }
}
